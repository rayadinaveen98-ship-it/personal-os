package com.navin.personalos.core.reminders

import android.Manifest
import android.app.*
import android.content.*
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.work.*
import com.navin.personalos.MainActivity
import com.navin.personalos.R
import com.navin.personalos.core.data.PreferenceStore
import com.navin.personalos.core.database.*
import com.navin.personalos.core.domain.RecurrenceEngine
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.time.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReminderService @Inject constructor(@ApplicationContext private val context: Context,private val dao: PersonalDao,private val preferences: PreferenceStore,private val clock: Clock) {
    private val mutex=Mutex()
    private val alarms=context.getSystemService(AlarmManager::class.java)
    private val notifications=context.getSystemService(NotificationManager::class.java)
    private val engine=RecurrenceEngine()
    fun notificationsAllowed(): Boolean = NotificationManagerCompat.from(context).areNotificationsEnabled() && (Build.VERSION.SDK_INT<33 || ContextCompat.checkSelfPermission(context,Manifest.permission.POST_NOTIFICATIONS)==PackageManager.PERMISSION_GRANTED)
    fun exactAllowed(): Boolean = Build.VERSION.SDK_INT<31 || alarms.canScheduleExactAlarms()
    private fun channel() { notifications.createNotificationChannel(NotificationChannel("intent-reminders","Your reminders",NotificationManager.IMPORTANCE_DEFAULT).apply {description="Only reminders and daily rhythms you choose."}) }
    private fun pending(id: String,triggerAt: Long? = null): PendingIntent = PendingIntent.getBroadcast(context,0,Intent(context,ReminderReceiver::class.java).setAction("com.navin.personalos.REMIND").setData(Uri.parse("personalos://reminder/$id")).putExtra("id",id).putExtra("triggerAt",triggerAt ?: -1L),PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
    fun enqueue() {
        WorkManager.getInstance(context).enqueueUniqueWork("reconcile-reminders",ExistingWorkPolicy.REPLACE,OneTimeWorkRequestBuilder<ReconcileWorker>().build())
        WorkManager.getInstance(context).enqueueUniquePeriodicWork("reminder-maintenance",ExistingPeriodicWorkPolicy.KEEP,PeriodicWorkRequestBuilder<ReconcileWorker>(15,TimeUnit.MINUTES).build())
    }
    suspend fun reconcile() = mutex.withLock {
        channel();val now=clock.millis();syncRhythms()
        for(record in dao.allReminder()) {
            var r=record
            if(r.state!=ReminderState.SCHEDULED) {alarms.cancel(pending(r.id));continue}
            val ownerActive=when(r.ownerType) {OwnerType.TASK -> r.ownerId?.let {dao.getTask(it)?.status==TaskStatus.OPEN} ?: false;OwnerType.HABIT -> r.ownerId?.let {dao.getHabit(it)?.status==ActiveStatus.ACTIVE} ?: false;else -> true}
            if(!ownerActive) {alarms.cancel(pending(r.id));dao.put(r.copy(state=ReminderState.CANCELLED,updatedAt=now));continue}
            if((r.triggerAt ?: 0)<=now) {
                val rule=r.recurrenceRuleId?.let {dao.getRecurrenceRule(it)}
                if(rule!=null) {
                    val localNow=Instant.ofEpochMilli(now).atZone(clock.zone)
                    val candidates=engine.dates(rule,localNow.toLocalDate(),localNow.toLocalDate().plusYears(8))
                    val skips=dao.allRecurrenceException().filter {it.recurrenceRuleId==rule.id && it.type==ExceptionType.SKIP}.map {it.occurrenceLocalDate}.toSet()
                    val next=candidates.firstOrNull {it.toString() !in skips && engine.instant(rule,it,clock.zone).toEpochMilli()>now}
                    if(next!=null) {r=r.copy(triggerAt=engine.instant(rule,next,clock.zone).toEpochMilli(),deliveryState=DeliveryState.PENDING,updatedAt=now);dao.put(r)}
                    else {dao.put(r.copy(state=ReminderState.SKIPPED,deliveryState=DeliveryState.EXPIRED,updatedAt=now));alarms.cancel(pending(r.id));continue}
                } else {dao.put(r.copy(deliveryState=DeliveryState.EXPIRED,updatedAt=now));alarms.cancel(pending(r.id));continue}
            }
            val delivery=when {
                !notificationsAllowed() -> DeliveryState.NOTIFICATIONS_BLOCKED
                notifications.getNotificationChannel("intent-reminders")?.importance==NotificationManager.IMPORTANCE_NONE -> DeliveryState.CHANNEL_BLOCKED
                r.exactRequired && !exactAllowed() -> DeliveryState.EXACT_ACCESS_BLOCKED
                else -> try {
                    val trigger=requireNotNull(r.triggerAt)
                    if(r.exactRequired) alarms.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,trigger,pending(r.id,trigger)) else alarms.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,trigger,pending(r.id,trigger))
                    DeliveryState.SCHEDULED
                } catch(_: SecurityException) {DeliveryState.EXACT_ACCESS_BLOCKED} catch(_: Exception) {DeliveryState.ERROR}
            }
            if(delivery!=DeliveryState.SCHEDULED) alarms.cancel(pending(r.id))
            if(r.deliveryState!=delivery || r.scheduledAt!=r.triggerAt) dao.put(r.copy(deliveryState=delivery,scheduledAt=if(delivery==DeliveryState.SCHEDULED) r.triggerAt else null,updatedAt=now))
        }
    }
    private suspend fun syncRhythms() {
        val p=preferences.flow.first()
        for((key,enabled,minutes) in listOf(Triple("morning",p.morning,p.morningMinutes),Triple("evening",p.evening,p.eveningMinutes))) {
            val id="rhythm-$key";val old=dao.getReminder(id)
            if(!enabled) {if(old!=null && old.state!=ReminderState.CANCELLED) dao.put(old.copy(state=ReminderState.CANCELLED,updatedAt=clock.millis()));continue}
            val rid="rhythm-rule-$key";val current=dao.getRecurrenceRule(rid)
            val rule=(current ?: RecurrenceRule(id=rid,startLocalDate=LocalDate.now(clock).toString())).copy(localTimeMinutes=minutes)
            if(rule!=current) dao.put(rule)
            val changed=current?.localTimeMinutes!=minutes || old?.state==ReminderState.CANCELLED
            if(old==null || changed) dao.put(Reminder(id=id,ownerType=OwnerType.JOURNAL_PROMPT,ownerId=key,title=if(key=="morning") "Your morning brief" else "What was worth remembering?",recurrenceRuleId=rid,exactRequired=false,triggerAt=0,createdAt=old?.createdAt ?: clock.millis()))
        }
    }
    suspend fun deliver(id: String,expectedTrigger: Long) {
        mutex.withLock {
            val r=dao.getReminder(id) ?: return@withLock
            if(r.state!=ReminderState.SCHEDULED || r.triggerAt!=expectedTrigger || r.deliveredAt==expectedTrigger) return@withLock
            if(r.ownerType==OwnerType.TASK && r.ownerId?.let {dao.getTask(it)?.status}!=TaskStatus.OPEN) return@withLock
            channel()
            if(!notificationsAllowed()) {dao.put(r.copy(deliveryState=DeliveryState.NOTIFICATIONS_BLOCKED));return@withLock}
            if(notifications.getNotificationChannel("intent-reminders")?.importance==NotificationManager.IMPORTANCE_NONE) {dao.put(r.copy(deliveryState=DeliveryState.CHANNEL_BLOCKED));return@withLock}
            val p=preferences.flow.first()
            val type=when(r.ownerType) {OwnerType.TASK -> EntityType.TASK;OwnerType.HABIT -> EntityType.HABIT;else -> EntityType.REMINDER}
            val target=if(type==EntityType.REMINDER) r.id else r.ownerId ?: r.id
            val intent=Intent(context,MainActivity::class.java).setData(Uri.parse("personalos://open/${type.name}/$target")).putExtra("entityType",type.name).putExtra("entityId",target).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            val open=PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
            val title=if(p.hideNotificationText) "A reminder you set" else r.title
            try {
                notifications.notify(r.id.hashCode(),NotificationCompat.Builder(context,"intent-reminders").setSmallIcon(R.drawable.leaf).setContentTitle(title).setContentText(if(p.hideNotificationText) "Open Personal OS to view it." else "Open your saved reminder.").setContentIntent(open).setAutoCancel(true).setVisibility(if(p.hideNotificationText) NotificationCompat.VISIBILITY_SECRET else NotificationCompat.VISIBILITY_PRIVATE).build())
                dao.put(r.copy(state=if(r.recurrenceRuleId==null) ReminderState.DELIVERED else ReminderState.SCHEDULED,deliveredAt=expectedTrigger,deliveryState=DeliveryState.PENDING,updatedAt=clock.millis()))
            } catch(_: SecurityException) {dao.put(r.copy(deliveryState=DeliveryState.NOTIFICATIONS_BLOCKED))}
        }
        reconcile()
    }
    suspend fun cancelAll() {mutex.withLock {dao.allReminder().forEach {alarms.cancel(pending(it.id))};notifications.cancelAll();WorkManager.getInstance(context).cancelUniqueWork("reminder-maintenance");WorkManager.getInstance(context).cancelUniqueWork("reconcile-reminders")}}
}
@EntryPoint @InstallIn(SingletonComponent::class)
interface ReminderEntryPoint {fun reminders(): ReminderService}
class ReminderReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context,intent: Intent) {
        val pending=goAsync()
        CoroutineScope(SupervisorJob()+Dispatchers.IO).launch {
            try {withTimeout(8500) {EntryPointAccessors.fromApplication(context.applicationContext,ReminderEntryPoint::class.java).reminders().deliver(intent.getStringExtra("id") ?: return@withTimeout,intent.getLongExtra("triggerAt",-1))}}
            finally {pending.finish()}
        }
    }
}
class RecoveryReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context,intent: Intent) {EntryPointAccessors.fromApplication(context.applicationContext,ReminderEntryPoint::class.java).reminders().enqueue()}
}
class ReconcileWorker(context: Context,params: WorkerParameters): CoroutineWorker(context,params) {
    override suspend fun doWork(): Result = try {EntryPointAccessors.fromApplication(applicationContext,ReminderEntryPoint::class.java).reminders().reconcile();Result.success()} catch(_: Exception) {Result.retry()}
}
