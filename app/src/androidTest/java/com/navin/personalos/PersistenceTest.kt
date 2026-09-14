package com.navin.personalos

import android.content.Context
import android.net.Uri
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.navin.personalos.core.data.*
import com.navin.personalos.core.database.*
import com.navin.personalos.core.reminders.ReminderService
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.flow.first
import org.junit.*
import org.junit.Assert.*
import org.junit.runner.RunWith
import java.io.File
import java.time.*
import java.util.UUID

@RunWith(AndroidJUnit4::class)
class PersistenceTest {
    private lateinit var context: Context
    private lateinit var db: PersonalDatabase
    private lateinit var repo: PersonalRepository
    private lateinit var prefs: PreferenceStore
    private lateinit var backup: BackupService
    private lateinit var name: String
    private val clock=Clock.fixed(Instant.parse("2026-09-13T10:00:00Z"),ZoneId.of("UTC"))
    @Before fun setup() = runBlocking {
        context=ApplicationProvider.getApplicationContext();name="test-${UUID.randomUUID()}.db"
        db=Room.databaseBuilder(context,PersonalDatabase::class.java,name).build();repo=PersonalRepository(db,db.dao(),clock)
        prefs=PreferenceStore(context);prefs.clear();backup=BackupService(context,db,prefs,ReminderService(context,db.dao(),prefs,clock))
    }
    @After fun cleanup() = runBlocking { db.close();context.deleteDatabase(name);prefs.clear() }
    @Test fun searchSurvivesRestartAndDeleteRemovesResult() = runBlocking {
        val id=UUID.randomUUID().toString();repo.save(Draft(id,EntityType.JOURNAL,title="Synthetic orchard",body="A written test reflection",date="2026-09-12"))
        assertEquals(id,repo.search("orchard").single().entityId)
        db.close();db=Room.databaseBuilder(context,PersonalDatabase::class.java,name).build();repo=PersonalRepository(db,db.dao(),clock)
        assertEquals(id,repo.search("reflection").single().entityId)
        repo.delete(EntityType.JOURNAL,id);assertTrue(repo.search("orchard").isEmpty())
    }
    @Test fun repeatedSaveUsesStableIdentityAndCompletionIsIdempotent() = runBlocking {
        val d=Draft(UUID.randomUUID().toString(),EntityType.TASK,title="Synthetic recurring action",date="2026-09-13",frequency=Frequency.DAILY)
        repo.save(d);repo.save(d)
        assertEquals(1,db.dao().allTask().size)
        repo.completeTask(d.id,"2026-09-13");repo.completeTask(d.id,"2026-09-13")
        assertEquals("2026-09-14",db.dao().getTask(d.id)!!.dueLocalDate)
        assertEquals(1,db.dao().allTimelineEvent().count {it.eventType=="TASK_COMPLETED"})
    }
    @Test fun clearingTaskReminderCancelsPersistedIntent() = runBlocking {
        val d=Draft(UUID.randomUUID().toString(),EntityType.TASK,title="Synthetic reminder",reminderDate="2026-09-14",reminderTime="09:00")
        repo.save(d);repo.save(d.copy(reminderDate="",reminderTime=""))
        assertEquals(ReminderState.CANCELLED,db.dao().allReminder().single().state)
    }
    @Test fun invalidParentRollsBackWholeSave() = runBlocking {
        val d=Draft(UUID.randomUUID().toString(),EntityType.TASK,title="Synthetic orphan",projectId="missing")
        try {repo.save(d);fail("Missing parent must fail")} catch(_: IllegalArgumentException) { }
        assertTrue(db.dao().allTask().isEmpty());assertTrue(repo.search("orphan").isEmpty())
    }
    @Test fun backupRoundTripAndCorruptionPreservesCurrentSpace() = runBlocking {
        val file=File(context.cacheDir,"test-backup-${UUID.randomUUID()}.json")
        try {
            val id=UUID.randomUUID().toString();repo.save(Draft(id,EntityType.JOURNAL,title="Synthetic backup",body="Backup evidence",date="2026-09-12"));prefs.text("name","Synthetic test name")
            backup.export(Uri.fromFile(file));val preview=backup.preview(Uri.fromFile(file))
            repo.delete(EntityType.JOURNAL,id);prefs.text("name","Changed name");backup.restore(preview)
            assertEquals("Backup evidence",db.dao().getJournalEntry(id)!!.body);assertEquals("Synthetic test name",prefs.flow.first().name)
            assertEquals(id,repo.search("evidence").single().entityId)
            file.writeText(file.readText().replace("Backup evidence","Corrupt evidence"))
            try {backup.preview(Uri.fromFile(file));fail("Corruption must fail")} catch(_: IllegalArgumentException) { }
            assertEquals("Backup evidence",db.dao().getJournalEntry(id)!!.body)
        } finally {file.delete()}
    }
    @Test fun editingSessionKeepsRecordedInstant() = runBlocking {
        val id=UUID.randomUUID().toString();val original=Instant.parse("2026-09-01T12:30:00Z").toEpochMilli()
        db.dao().put(Session(id=id,title="Synthetic session",occurredAt=original,localDate="2026-09-01",durationMinutes=20))
        repo.save(repo.edit(EntityType.SESSION,id)!!.copy(body="Edited note"))
        assertEquals(original,db.dao().getSession(id)!!.occurredAt)
    }
    @Test fun rolloverKeepsOneRecurringOccurrenceWithoutMovingOneOffTasks() = runBlocking {
        val recurring=Draft(UUID.randomUUID().toString(),EntityType.TASK,title="Synthetic daily",date="2026-09-01",frequency=Frequency.DAILY)
        val oneOff=Draft(UUID.randomUUID().toString(),EntityType.TASK,title="Synthetic one-off",date="2026-09-01")
        repo.save(recurring);repo.save(oneOff);repo.refreshCalendar();repo.refreshCalendar()
        assertEquals(2,db.dao().allTask().size)
        assertEquals("2026-09-13",db.dao().getTask(recurring.id)!!.dueLocalDate)
        assertEquals("2026-09-01",db.dao().getTask(oneOff.id)!!.dueLocalDate)
        assertTrue(db.dao().allTimelineEvent().none {it.eventType=="TASK_COMPLETED"})
    }

    @Test fun habitReminderFollowsTimePauseResumeAndRemoval() = runBlocking {
        val d=Draft(UUID.randomUUID().toString(),EntityType.HABIT,title="Synthetic habit",frequency=Frequency.DAILY,time="18:00")
        repo.save(d);repo.save(d)
        assertEquals(1,db.dao().allReminder().size)
        assertEquals(db.dao().getHabit(d.id)!!.scheduleRuleId,db.dao().allReminder().single().recurrenceRuleId)
        repo.status(EntityType.HABIT,d.id,"PAUSED")
        assertEquals(ReminderState.CANCELLED,db.dao().allReminder().single().state)
        repo.status(EntityType.HABIT,d.id,"ACTIVE")
        assertEquals(ReminderState.SCHEDULED,db.dao().allReminder().single().state)
        repo.save(d.copy(time=""))
        assertEquals(ReminderState.CANCELLED,db.dao().allReminder().single().state)
    }
    @Test fun movingAndSkippingOccurrencePreservesReminderWallTime() = runBlocking {
        val d=Draft(UUID.randomUUID().toString(),EntityType.TASK,title="Synthetic recurrence",date="2026-09-14",frequency=Frequency.DAILY,reminderDate="2026-09-14",reminderTime="09:00")
        repo.save(d);repo.overrideOccurrence(d.id,LocalDate.parse("2026-09-16"))
        assertEquals(Instant.parse("2026-09-16T09:00:00Z").toEpochMilli(),db.dao().allReminder().single().triggerAt)
        repo.skipOccurrence(d.id,"2026-09-16")
        assertEquals("2026-09-15",db.dao().getTask(d.id)!!.dueLocalDate)
        assertEquals(Instant.parse("2026-09-15T09:00:00Z").toEpochMilli(),db.dao().allReminder().single().triggerAt)
        assertEquals(ReminderState.SCHEDULED,db.dao().allReminder().single().state)
    }

    @Test fun searchUpdatesLinkedContextAndFiltersBeforeLimiting() = runBlocking {
        val project=Draft(UUID.randomUUID().toString(),EntityType.PROJECT,title="Synthetic orchard")
        repo.save(project)
        val task=Draft(UUID.randomUUID().toString(),EntityType.TASK,title="Synthetic action",projectId=project.id)
        repo.save(task)
        assertTrue(repo.search("orchard").any {it.entityId==task.id})
        repo.save(project.copy(title="Synthetic garden"))
        assertFalse(repo.search("orchard").any {it.entityId==task.id})
        assertEquals(task.id,db.dao().searchFiltered("garden*",EntityType.TASK,1).single().entityId)
    }

}
