package com.navin.personalos.core.data

import android.content.Context
import android.database.Cursor
import android.net.Uri
import androidx.room.Room
import androidx.room.withTransaction
import com.navin.personalos.BuildConfig
import com.navin.personalos.core.database.*
import com.navin.personalos.core.domain.RecurrenceEngine
import com.navin.personalos.core.reminders.ReminderService
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.security.MessageDigest
import java.time.LocalDate
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

class RestorePreview internal constructor(internal val payload: String,val records: Int,val externalAttachments: Int,val exportedAt: Long)

/** Versioned JSON transport; Room remains the typed live domain store. */
@Singleton class BackupService @Inject constructor(@ApplicationContext private val context: Context,private val db: PersonalDatabase,private val preferences: PreferenceStore,private val reminders: ReminderService) {
    private val tables=listOf("LifeArea","Goal","Project","Milestone","Task","Reminder","RecurrenceRule","RecurrenceException","Habit","HabitEvent","Hobby","Skill","Session","JournalEntry","Idea","Memory","Chapter","ChapterItem","DailyReview","WeeklyReview","Tag","EntityLink","Attachment","TimelineEvent","EntityTagCrossRef","SearchDocument")
    private fun sha(value: String)=MessageDigest.getInstance("SHA-256").digest(value.toByteArray(Charsets.UTF_8)).joinToString("") { "%02x".format(it) }
    suspend fun export(uri: Uri) = withContext(Dispatchers.IO) {
        val rows=JSONObject()
        db.withTransaction { for(table in tables) {
            val array=JSONArray()
            db.openHelper.readableDatabase.query("SELECT * FROM `$table`").use { c -> while(c.moveToNext()) {
                val row=JSONObject()
                for(i in 0 until c.columnCount) row.put(c.getColumnName(i),when(c.getType(i)) { Cursor.FIELD_TYPE_NULL -> JSONObject.NULL;Cursor.FIELD_TYPE_INTEGER -> c.getLong(i);Cursor.FIELD_TYPE_FLOAT -> c.getDouble(i);Cursor.FIELD_TYPE_STRING -> c.getString(i);else -> error("Unsupported binary domain field") })
                array.put(row)
            } };rows.put(table,array)
        } }
        val payload=JSONObject().put("formatVersion",1).put("schemaVersion",1).put("appVersion",BuildConfig.VERSION_NAME).put("exportedAt",System.currentTimeMillis()).put("tables",rows).put("preferences",preferences.export()).put("attachmentPolicy","External URI metadata only. Files are not embedded; access must be checked after restore.").toString()
        val envelope=JSONObject().put("format","personal-os-backup").put("sha256",sha(payload)).put("payload",payload).toString(2)
        require(envelope.toByteArray().size<=64*1024*1024) { "This backup exceeds the supported 64 MB package limit." }
        requireNotNull(context.contentResolver.openOutputStream(uri,"wt")) { "Couldn't open the selected destination." }.use { it.write(envelope.toByteArray(Charsets.UTF_8));it.flush() }
        preferences.backupTime(System.currentTimeMillis())
    }
    suspend fun preview(uri: Uri): RestorePreview = withContext(Dispatchers.IO) {
        val bytes=requireNotNull(context.contentResolver.openInputStream(uri)).use { stream ->
            val out=java.io.ByteArrayOutputStream();val buffer=ByteArray(8192)
            while(true) { val count=stream.read(buffer);if(count<0) break;require(out.size()+count<=64*1024*1024) { "Backup is larger than the supported 64 MB limit." };out.write(buffer,0,count) };out.toByteArray()
        }
        val envelope=JSONObject(String(bytes,Charsets.UTF_8))
        require(envelope.getString("format")=="personal-os-backup") { "This is not a Personal OS backup." }
        val payload=envelope.getString("payload")
        require(sha(payload)==envelope.getString("sha256")) { "Backup checksum failed. Your records have not been changed." }
        val json=JSONObject(payload)
        require(json.getInt("formatVersion")==1 && json.getInt("schemaVersion")==1) { "This backup uses an unsupported format or database version." }
        preferences.validate(json.getJSONObject("preferences"))
        val rows=json.getJSONObject("tables")
        require(rows.keys().asSequence().toSet()==tables.toSet()) { "Backup tables are incomplete or unsupported." }
        val stageName="restore-validation-${UUID.randomUUID()}.db"
        val staging=Room.databaseBuilder(context,PersonalDatabase::class.java,stageName).build()
        try { replace(staging,rows);validateDomain(staging) } finally { staging.close();context.deleteDatabase(stageName) }
        RestorePreview(payload,tables.filter { it!="SearchDocument" }.sumOf { rows.getJSONArray(it).length() },rows.getJSONArray("Attachment").length(),json.getLong("exportedAt"))
    }
    suspend fun restore(preview: RestorePreview) = withContext(Dispatchers.IO) {
        val json=JSONObject(preview.payload);val oldPreferences=preferences.export()
        reminders.cancelAll()
        try { db.withTransaction { replace(db,json.getJSONObject("tables"));preferences.restore(json.getJSONObject("preferences")) } }
        catch(e: Exception) { preferences.restore(oldPreferences,preserveSecurity=false);throw e }
        finally { reminders.enqueue() }
    }
    suspend fun deleteAll() = withContext(Dispatchers.IO) {
        reminders.cancelAll();val old=preferences.export()
        try { db.withTransaction { val empty=JSONObject();tables.forEach { empty.put(it,JSONArray()) };replace(db,empty);preferences.clear() };context.contentResolver.persistedUriPermissions.forEach {grant -> runCatching {context.contentResolver.releasePersistableUriPermission(grant.uri,android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION)}} }
        catch(e: Exception) { preferences.restore(old,preserveSecurity=false);throw e }
        finally { reminders.enqueue() }
    }
    private suspend fun replace(target: PersonalDatabase,rows: JSONObject) = target.withTransaction {
        val sql=target.openHelper.writableDatabase
        sql.execSQL("PRAGMA defer_foreign_keys = ON")
        tables.reversed().forEach { sql.execSQL("DELETE FROM `$it`") }
        for(table in tables) {
            val columns=mutableMapOf<String,String>()
            sql.query("PRAGMA table_info(`$table`)").use { c -> while(c.moveToNext()) columns[c.getString(c.getColumnIndexOrThrow("name"))]=c.getString(c.getColumnIndexOrThrow("type")) }
            val array=rows.getJSONArray(table)
            require(array.length()<=1_000_000) { "Too many records in this backup." }
            for(i in 0 until array.length()) {
                val row=array.getJSONObject(i)
                require(row.keys().asSequence().toSet()==columns.keys) { "Unexpected fields in $table." }
                val args=columns.map { (name,type) ->
                    val value=row.get(name)
                    if(value===JSONObject.NULL) null else {
                        require(when(type) { "INTEGER" -> value is Int || value is Long;"REAL" -> value is Number;"TEXT" -> value is String;else -> false }) { "Invalid field type in $table." }
                        if(value is String && (name=="localDate" || name.endsWith("LocalDate") || name=="targetDate")) LocalDate.parse(value)
                        value
                    }
                }.toTypedArray()
                sql.execSQL("INSERT INTO `$table` (${columns.keys.joinToString { "`$it`" }}) VALUES (${columns.map { "?" }.joinToString()})",args)
            }
        }
        sql.query("PRAGMA foreign_key_check").use { require(!it.moveToFirst()) { "Backup has broken relationships." } }
        sql.query("PRAGMA integrity_check").use { require(it.moveToFirst() && it.getString(0)=="ok") { "Backup integrity check failed." } }
    }
    private suspend fun validateDomain(target: PersonalDatabase) {
        val d=target.dao()
        // Decode all domain enums before replacing the live database.
        d.allLifeArea();d.allGoal();d.allProject();d.allMilestone();d.allTask();d.allReminder();d.allRecurrenceException();d.allHabit();d.allHabitEvent();d.allHobby();d.allSkill();d.allSession();d.allJournalEntry();d.allIdea();d.allMemory();d.allChapter();d.allChapterItem();d.allDailyReview();d.allWeeklyReview();d.allTag();d.allEntityLink();d.allTimelineEvent();d.allTags()
        d.allRecurrenceRule().forEach { RecurrenceEngine().validate(it) }
        d.allAttachment().forEach { require(it.storageMode==StorageMode.EXTERNAL_URI && Uri.parse(it.uri).scheme=="content") { "Unsupported attachment storage. Files have not been imported." } }
        d.allSession().forEach { require(it.durationMinutes==null || it.durationMinutes in 1..1440) }
        val ids=mapOf(
            EntityType.LIFE_AREA to d.allLifeArea().map {it.id}.toSet(),EntityType.GOAL to d.allGoal().map {it.id}.toSet(),
            EntityType.PROJECT to d.allProject().map {it.id}.toSet(),EntityType.MILESTONE to d.allMilestone().map {it.id}.toSet(),
            EntityType.TASK to d.allTask().map {it.id}.toSet(),EntityType.REMINDER to d.allReminder().map {it.id}.toSet(),
            EntityType.HABIT to d.allHabit().map {it.id}.toSet(),EntityType.HOBBY to d.allHobby().map {it.id}.toSet(),
            EntityType.SKILL to d.allSkill().map {it.id}.toSet(),EntityType.SESSION to d.allSession().map {it.id}.toSet(),
            EntityType.JOURNAL to d.allJournalEntry().map {it.id}.toSet(),EntityType.IDEA to d.allIdea().map {it.id}.toSet(),
            EntityType.MEMORY to d.allMemory().map {it.id}.toSet(),EntityType.CHAPTER to d.allChapter().map {it.id}.toSet(),
            EntityType.DAILY_REVIEW to d.allDailyReview().map {it.id}.toSet(),EntityType.WEEKLY_REVIEW to d.allWeeklyReview().map {it.id}.toSet())
        fun exists(type: EntityType,id: String)=id in ids.getValue(type)
        require(ids.values.all {set -> set.all {it.isNotBlank() && it.length<=256}}) {"Invalid record identity in backup."}
        d.allEntityLink().forEach {require(exists(it.fromType,it.fromId) && exists(it.toType,it.toId)) {"Backup contains a broken record connection."}}
        d.allChapterItem().forEach {require(exists(it.entityType,it.entityId)) {"Backup contains an unavailable chapter member."}}
        d.allTags().forEach {require(exists(it.entityType,it.entityId)) {"Backup contains a tag for an unavailable record."}}
        d.allAttachment().forEach {require(exists(it.ownerType,it.ownerId)) {"Backup contains an attachment without an owner."}}
        d.allReminder().filter {it.state==ReminderState.SCHEDULED}.forEach {r ->
            if(r.ownerType==OwnerType.TASK) require(r.ownerId!=null && exists(EntityType.TASK,r.ownerId)) {"Reminder task is missing."}
            if(r.ownerType==OwnerType.HABIT) require(r.ownerId!=null && exists(EntityType.HABIT,r.ownerId)) {"Reminder habit is missing."}
        }
        target.openHelper.readableDatabase.query("SELECT entityType,entityId FROM SearchDocument").use {c -> while(c.moveToNext()) require(exists(EntityType.valueOf(c.getString(0)),c.getString(1))) {"Search references an unavailable record."} }

    }
}
