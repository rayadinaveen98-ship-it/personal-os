package com.navin.personalos.core.data

import androidx.room.withTransaction
import com.navin.personalos.core.database.*
import com.navin.personalos.core.domain.*
import kotlinx.coroutines.flow.*
import java.time.*
import javax.inject.Inject
import javax.inject.Singleton

data class Record(
    val type: EntityType, val id: String, val title: String, val body: String = "", val status: String = "",
    val date: String? = null, val updatedAt: Long = 0, val lifeAreaId: String? = null, val projectId: String? = null,
    val goalId: String? = null, val duration: Int? = null, val pinned: Boolean = false,
)
data class Draft(
    val id: String, val type: EntityType, val title: String = "", val body: String = "", val date: String = "",
    val time: String = "", val priority: Priority = Priority.NORMAL, val lifeAreaId: String? = null,
    val projectId: String? = null, val goalId: String? = null, val hobbyId: String? = null, val skillId: String? = null,
    val parentTaskId: String? = null, val duration: String = "", val why: String = "", val success: String = "",
    val current: String = "", val target: String = "", val unit: String = "", val measurement: MeasurementType = MeasurementType.NONE,
    val frequency: Frequency? = null, val interval: Int = 1, val weekdaysMask: Int? = null, val endDate: String = "", val countLimit: String = "",
    val habitTarget: TargetType = TargetType.CHECK, val reflection: ReflectionType = ReflectionType.FREE,
    val reminderDate: String = "", val reminderTime: String = "", val currentFocus: String = "", val icon: String = "", val accent: String = "",
) : java.io.Serializable

@Singleton
class PersonalRepository @Inject constructor(val db: PersonalDatabase, val dao: PersonalDao, private val clock: Clock) {
    private val engine = RecurrenceEngine()
    val records: Flow<List<Record>> = combine(listOf(
        dao.observeLifeArea().map { rows -> rows.map { Record(EntityType.LIFE_AREA,it.id,it.name,it.description.orEmpty(),it.status.name,updatedAt=it.updatedAt) } },
        dao.observeTask().map { rows -> rows.map { Record(EntityType.TASK,it.id,it.title,it.notes.orEmpty(),it.status.name,it.dueLocalDate,it.updatedAt,it.lifeAreaId,it.projectId,it.goalId,pinned=it.pinnedFocus) } },
        dao.observeProject().map { rows -> rows.map { Record(EntityType.PROJECT,it.id,it.title,it.description.orEmpty(),it.status.name,it.targetDate,it.updatedAt,it.lifeAreaId,goalId=it.goalId) } },
        dao.observeGoal().map { rows -> rows.map { Record(EntityType.GOAL,it.id,it.title,it.whyItMatters.orEmpty(),it.status.name,it.targetDate,it.updatedAt,it.lifeAreaId) } },
        dao.observeMilestone().map { rows -> rows.map { Record(EntityType.MILESTONE,it.id,it.title,status=it.status.name,date=it.targetDate,updatedAt=it.updatedAt,projectId=it.projectId,goalId=it.goalId) } },
        dao.observeReminder().map { rows -> rows.map { Record(EntityType.REMINDER,it.id,it.title,status=if(it.state==ReminderState.SCHEDULED) it.deliveryState.name else it.state.name,date=it.triggerAt?.let { t -> Instant.ofEpochMilli(t).atZone(clock.zone).toLocalDate().toString() },updatedAt=it.updatedAt) } },
        dao.observeHabit().map { rows -> rows.map { Record(EntityType.HABIT,it.id,it.title,it.description.orEmpty(),it.status.name,updatedAt=it.updatedAt,lifeAreaId=it.lifeAreaId,goalId=it.goalId) } },
        dao.observeHobby().map { rows -> rows.map { Record(EntityType.HOBBY,it.id,it.title,it.description.orEmpty(),it.status.name,updatedAt=it.updatedAt,lifeAreaId=it.lifeAreaId) } },
        dao.observeSkill().map { rows -> rows.map { Record(EntityType.SKILL,it.id,it.title,it.description.orEmpty(),it.status.name,updatedAt=it.updatedAt,lifeAreaId=it.lifeAreaId) } },
        dao.observeSession().map { rows -> rows.map { Record(EntityType.SESSION,it.id,it.title,it.notes.orEmpty(),date=it.localDate,updatedAt=it.updatedAt,lifeAreaId=it.lifeAreaId,projectId=it.projectId,duration=it.durationMinutes) } },
        dao.observeJournalEntry().map { rows -> rows.map { Record(EntityType.JOURNAL,it.id,it.title ?: it.body.take(100),it.body,it.reflectionType.name,it.entryLocalDate,it.updatedAt,it.lifeAreaId,it.projectId,it.goalId) } },
        dao.observeIdea().map { rows -> rows.map { Record(EntityType.IDEA,it.id,it.title ?: it.body.take(100),it.body,it.status.name,Instant.ofEpochMilli(it.createdAt).atZone(clock.zone).toLocalDate().toString(),it.updatedAt,it.lifeAreaId,it.projectId) } },
        dao.observeMemory().map { rows -> rows.map { Record(EntityType.MEMORY,it.id,it.title,it.body.orEmpty(),date=it.memoryLocalDate,updatedAt=it.updatedAt,lifeAreaId=it.lifeAreaId,projectId=it.projectId) } },
        dao.observeChapter().map { rows -> rows.map { Record(EntityType.CHAPTER,it.id,it.title,it.description.orEmpty(),it.status.name,it.startLocalDate,it.updatedAt,it.lifeAreaId) } },
        dao.observeWeeklyReview().map { rows -> rows.map { Record(EntityType.WEEKLY_REVIEW,it.id,"Week of ${it.periodStartLocalDate}",it.reflectionBody.orEmpty(),date=it.periodStartLocalDate,updatedAt=it.updatedAt) } },
    )) { it.flatMap { list -> list }.sortedByDescending { r -> r.updatedAt } }

    suspend fun save(d: Draft, reflection: ReflectionType = d.reflection): String = db.withTransaction {
        require(d.type==EntityType.WEEKLY_REVIEW || d.title.isNotBlank() || d.type in listOf(EntityType.JOURNAL,EntityType.IDEA) && d.body.isNotBlank()) { "Add a title or a thought before saving." }
        require(d.title.length <= 500) { "Keep the title within 500 characters." }
        val date=d.date.takeIf { it.isNotBlank() }?.let(LocalDate::parse)
        val time=d.time.takeIf { it.isNotBlank() }?.let(LocalTime::parse)
        val now=clock.millis(); val today=LocalDate.now(clock)
        val existed=edit(d.type,d.id)!=null
        var savedId=d.id
        val at=date?.let { if(time!=null) it.atTime(time).atZone(clock.zone).toInstant().toEpochMilli() else null }
        d.lifeAreaId?.let { requireNotNull(dao.getLifeArea(it)) { "Choose an existing life area." } }
        d.projectId?.let { requireNotNull(dao.getProject(it)) { "Choose an existing project." } }
        d.goalId?.let { requireNotNull(dao.getGoal(it)) { "Choose an existing goal." } }
        val duration=d.duration.takeIf { it.isNotBlank() }?.toIntOrNull()
        require(d.duration.isBlank() || duration != null && duration in 1..1440) { "Duration must be between 1 and 1440 minutes." }
        suspend fun rule(oldId: String?): String? {
            if(d.frequency==null) return null
            val existing=oldId?.let {dao.getRecurrenceRule(it)}
            val task=if(d.type==EntityType.TASK) dao.getTask(d.id) else null
            val keepAnchor=existing!=null && task!=null && task.dueLocalDate==d.date && existing.frequency==d.frequency && existing.interval==d.interval
            val start=if(keepAnchor) LocalDate.parse(existing!!.startLocalDate) else date ?: today
            val r=(existing ?: RecurrenceRule(startLocalDate=start.toString())).copy(
                frequency=d.frequency,interval=d.interval,weekdaysMask=d.weekdaysMask,dayOfMonth=start.dayOfMonth,monthOfYear=start.monthValue,
                localTimeMinutes=time?.let { it.hour*60+it.minute },startLocalDate=start.toString(),endLocalDate=d.endDate.takeIf { it.isNotBlank() },
                occurrenceCountLimit=d.countLimit.takeIf { it.isNotBlank() }?.toInt(),updatedAt=now)
            engine.validate(r);dao.put(r);return r.id
        }
        when(d.type) {
            EntityType.LIFE_AREA -> dao.put((dao.getLifeArea(d.id) ?: LifeArea(id=d.id,name=d.title)).copy(name=d.title.trim(),description=d.body,iconKey=d.icon,accentKey=d.accent,updatedAt=now))
            EntityType.TASK -> {
                val old=dao.getTask(d.id) ?: Task(id=d.id,title=d.title)
                require(d.parentTaskId != d.id) { "A task cannot be its own subtask." }
                d.parentTaskId?.let { requireNotNull(dao.getTask(it)); require(dao.getTask(it)?.parentTaskId==null) { "Subtasks use one level." } }
                dao.put(old.copy(title=d.title.trim(),notes=d.body,priority=d.priority,dueAt=at,dueLocalDate=date?.toString(),projectId=d.projectId,goalId=d.goalId,lifeAreaId=d.lifeAreaId,parentTaskId=d.parentTaskId,recurrenceRuleId=rule(old.recurrenceRuleId),updatedAt=now))
            }
            EntityType.REMINDER -> {
                require(at != null && at > now) { "Choose a future reminder date and time." }
                val old=dao.getReminder(d.id) ?: Reminder(id=d.id,title=d.title)
                dao.put(old.copy(title=d.title.trim(),triggerAt=at,localTimeMinutes=time?.let { it.hour*60+it.minute },recurrenceRuleId=rule(old.recurrenceRuleId),deliveryState=DeliveryState.PENDING,state=ReminderState.SCHEDULED,updatedAt=now))
            }
            EntityType.PROJECT -> dao.put((dao.getProject(d.id) ?: Project(id=d.id,title=d.title)).copy(title=d.title.trim(),description=d.body,lifeAreaId=d.lifeAreaId,goalId=d.goalId,targetDate=date?.toString(),updatedAt=now))
            EntityType.GOAL -> {
                val target=d.target.toDoubleOrNull();val current=d.current.toDoubleOrNull()
                require(d.measurement==MeasurementType.NONE || target!=null && target.isFinite() && target>0 && current!=null && current.isFinite() && current>=0) { "Add a valid current value and a target greater than zero." }
                dao.put((dao.getGoal(d.id) ?: Goal(id=d.id,title=d.title)).copy(title=d.title.trim(),whyItMatters=d.why,successDefinition=d.success,measurementType=d.measurement,targetValue=target,currentValue=current,unit=d.unit,lifeAreaId=d.lifeAreaId,targetDate=date?.toString(),updatedAt=now))
            }
            EntityType.MILESTONE -> {
                require(d.projectId!=null || d.goalId!=null) { "A milestone belongs to a project or goal." }
                dao.put((dao.getMilestone(d.id) ?: Milestone(id=d.id,title=d.title)).copy(title=d.title.trim(),projectId=d.projectId,goalId=d.goalId,targetDate=date?.toString(),updatedAt=now))
            }
            EntityType.HABIT -> {
                require(d.frequency!=null) { "Choose when this habit repeats." }
                val old=dao.getHabit(d.id);val rid=requireNotNull(rule(old?.scheduleRuleId))
                val target=d.target.toDoubleOrNull();require(d.habitTarget==TargetType.CHECK || target!=null && target.isFinite() && target>0) {"Choose a positive habit target."}
                dao.put((old ?: Habit(id=d.id,title=d.title,scheduleRuleId=rid)).copy(title=d.title.trim(),description=d.body,scheduleRuleId=rid,targetType=d.habitTarget,targetValue=target,unit=d.unit,preferredTimeMinutes=time?.let {it.hour*60+it.minute},lifeAreaId=d.lifeAreaId,goalId=d.goalId,updatedAt=now))
            }
            EntityType.HOBBY -> dao.put((dao.getHobby(d.id) ?: Hobby(id=d.id,title=d.title)).copy(title=d.title.trim(),description=d.body,lifeAreaId=d.lifeAreaId,updatedAt=now))
            EntityType.SKILL -> dao.put((dao.getSkill(d.id) ?: Skill(id=d.id,title=d.title)).copy(title=d.title.trim(),description=d.body,lifeAreaId=d.lifeAreaId,hobbyId=d.hobbyId,currentFocus=d.currentFocus,updatedAt=now))
            EntityType.SESSION -> dao.put((dao.getSession(d.id) ?: Session(id=d.id,title=d.title,occurredAt=at ?: now,localDate=(date ?: today).toString())).copy(title=d.title.trim(),notes=d.body,durationMinutes=duration,localDate=(date ?: today).toString(),occurredAt=at ?: dao.getSession(d.id)?.occurredAt ?: (date ?: today).atStartOfDay(clock.zone).toInstant().toEpochMilli(),lifeAreaId=d.lifeAreaId,projectId=d.projectId,hobbyId=d.hobbyId,skillId=d.skillId,updatedAt=now))
            EntityType.JOURNAL -> {
                require(d.body.isNotBlank()) { "Write something before saving." }
                val old=dao.getJournalEntry(d.id)
                dao.put((old ?: JournalEntry(id=d.id,body=d.body,entryLocalDate=(date ?: today).toString(),occurredAt=at ?: now,reflectionType=reflection)).copy(title=d.title.takeIf { it.isNotBlank() },body=d.body,entryLocalDate=(date ?: today).toString(),lifeAreaId=d.lifeAreaId,projectId=d.projectId,goalId=d.goalId,updatedAt=now))
            }
            EntityType.IDEA -> dao.put((dao.getIdea(d.id) ?: Idea(id=d.id,body=d.body)).copy(title=d.title.takeIf { it.isNotBlank() },body=d.body.ifBlank { d.title },lifeAreaId=d.lifeAreaId,projectId=d.projectId,updatedAt=now))
            EntityType.MEMORY -> dao.put((dao.getMemory(d.id) ?: Memory(id=d.id,title=d.title,memoryLocalDate=(date ?: today).toString())).copy(title=d.title.trim(),body=d.body,memoryLocalDate=(date ?: today).toString(),lifeAreaId=d.lifeAreaId,projectId=d.projectId,updatedAt=now))
            EntityType.CHAPTER -> {
                require(date!=null) { "Choose the chapter's start date." };require(d.endDate.isBlank() || LocalDate.parse(d.endDate)>=date)
                dao.put((dao.getChapter(d.id) ?: Chapter(id=d.id,title=d.title,startLocalDate=date.toString())).copy(title=d.title.trim(),description=d.body,startLocalDate=date.toString(),endLocalDate=d.endDate.takeIf { it.isNotBlank() },lifeAreaId=d.lifeAreaId,updatedAt=now))
            }
            EntityType.WEEKLY_REVIEW -> {
                val start=(date ?: today).with(java.time.temporal.TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
                val old=dao.allWeeklyReview().firstOrNull { it.periodStartLocalDate==start.toString() }
                savedId=old?.id ?: d.id
                dao.put((old ?: WeeklyReview(id=d.id,periodStartLocalDate=start.toString(),periodEndLocalDate=start.plusDays(6).toString())).copy(reflectionBody=d.body,highlight=d.title,updatedAt=now))
            }
            EntityType.DAILY_REVIEW -> error("Daily reflection is captured as a journal entry.")
        }
        if(d.type==EntityType.TASK && d.reminderDate.isNotBlank()) {
            val trigger=LocalDate.parse(d.reminderDate).atTime(LocalTime.parse(d.reminderTime)).atZone(clock.zone).toInstant().toEpochMilli()
            require(trigger>now) { "Choose a future reminder time." }
            val old=dao.allReminder().firstOrNull { it.ownerType==OwnerType.TASK && it.ownerId==d.id }
            dao.put((old ?: Reminder(title=d.title,ownerType=OwnerType.TASK,ownerId=d.id)).copy(title=d.title,triggerAt=trigger,state=ReminderState.SCHEDULED,deliveryState=DeliveryState.PENDING,updatedAt=now))
        }
        if(d.type==EntityType.HABIT) {
            val habit=requireNotNull(dao.getHabit(d.id))
            val old=dao.allReminder().firstOrNull {it.ownerType==OwnerType.HABIT && it.ownerId==d.id}
            if(time==null) cancelReminders(EntityType.HABIT,d.id)
            else dao.put((old ?: Reminder(title=d.title,ownerType=OwnerType.HABIT,ownerId=d.id)).copy(
                title=d.title,recurrenceRuleId=habit.scheduleRuleId,localTimeMinutes=time.hour*60+time.minute,
                triggerAt=at ?: now,state=if(habit.status==ActiveStatus.ACTIVE) ReminderState.SCHEDULED else ReminderState.CANCELLED,
                deliveryState=DeliveryState.PENDING,scheduledAt=null,updatedAt=now))
        }
        if(d.type==EntityType.TASK && d.reminderDate.isBlank()) cancelReminders(EntityType.TASK,d.id)
        index(d.type,savedId)
        if(existed && d.type in setOf(EntityType.LIFE_AREA,EntityType.PROJECT,EntityType.GOAL)) {
            for(document in dao.allDocuments()) {
                val linked=edit(document.entityType,document.entityId) ?: continue
                val depends=when(d.type) {EntityType.LIFE_AREA -> linked.lifeAreaId==savedId;EntityType.PROJECT -> linked.projectId==savedId;EntityType.GOAL -> linked.goalId==savedId;else -> false}
                if(depends) index(document.entityType,document.entityId)
            }
        }
        savedId
    }
    suspend fun index(type: EntityType, id: String) {
        if(type in setOf(EntityType.REMINDER,EntityType.HABIT,EntityType.MILESTONE,EntityType.DAILY_REVIEW)) return
        val d=edit(type,id) ?: return
        val old=dao.document(type,id)
        val tagIds=dao.allTags().filter {it.entityType==type && it.entityId==id}.map {it.tagId}.toSet()
        val tags=dao.allTag().filter {it.id in tagIds}.map {it.name}
        val keywords=(tags+listOfNotNull(d.projectId?.let { dao.getProject(it)?.title },d.goalId?.let { dao.getGoal(it)?.title },d.lifeAreaId?.let { dao.getLifeArea(it)?.name })).joinToString(" ")
        dao.put(SearchDocument(rowId=old?.rowId ?: 0,entityType=type,entityId=id,title=if(type==EntityType.WEEKLY_REVIEW) "Week of ${d.date}: ${d.title}" else d.title,body=listOf(d.body,d.why,d.success,d.currentFocus).joinToString(" "),keywords=keywords,updatedAt=clock.millis()))
    }
    suspend fun search(query: String): List<SearchDocument> {
        val tokens=Regex("[\\p{L}\\p{N}]+").findAll(query).map { "\"${it.value}\"*" }.toList()
        return if(tokens.isEmpty()) emptyList() else dao.search(tokens.joinToString(" AND "))
    }
    suspend fun edit(type: EntityType, id: String): Draft? {
        var d: Draft? = when(type) {
            EntityType.LIFE_AREA -> dao.getLifeArea(id)?.let { Draft(id,type,it.name,it.description.orEmpty(),icon=it.iconKey.orEmpty(),accent=it.accentKey.orEmpty()) }
            EntityType.TASK -> dao.getTask(id)?.let { Draft(id,type,it.title,it.notes.orEmpty(),it.dueLocalDate.orEmpty(),it.dueAt?.let { a -> Instant.ofEpochMilli(a).atZone(clock.zone).toLocalTime().toString() }.orEmpty(),it.priority,it.lifeAreaId,it.projectId,it.goalId,parentTaskId=it.parentTaskId) }
            EntityType.REMINDER -> dao.getReminder(id)?.let { val z=it.triggerAt?.let { t -> Instant.ofEpochMilli(t).atZone(clock.zone) };Draft(id,type,it.title,date=z?.toLocalDate()?.toString().orEmpty(),time=z?.toLocalTime()?.toString().orEmpty()) }
            EntityType.PROJECT -> dao.getProject(id)?.let { Draft(id,type,it.title,it.description.orEmpty(),date=it.targetDate.orEmpty(),lifeAreaId=it.lifeAreaId,goalId=it.goalId) }
            EntityType.GOAL -> dao.getGoal(id)?.let { Draft(id,type,it.title,date=it.targetDate.orEmpty(),why=it.whyItMatters.orEmpty(),success=it.successDefinition.orEmpty(),current=it.currentValue?.toString().orEmpty(),target=it.targetValue?.toString().orEmpty(),unit=it.unit.orEmpty(),measurement=it.measurementType,lifeAreaId=it.lifeAreaId) }
            EntityType.MILESTONE -> dao.getMilestone(id)?.let { Draft(id,type,it.title,date=it.targetDate.orEmpty(),projectId=it.projectId,goalId=it.goalId) }
            EntityType.HABIT -> dao.getHabit(id)?.let { Draft(id,type,it.title,it.description.orEmpty(),lifeAreaId=it.lifeAreaId,goalId=it.goalId,habitTarget=it.targetType,target=it.targetValue?.toString().orEmpty(),unit=it.unit.orEmpty(),time=it.preferredTimeMinutes?.let {m -> LocalTime.ofSecondOfDay(m*60L).toString()}.orEmpty()) }
            EntityType.HOBBY -> dao.getHobby(id)?.let { Draft(id,type,it.title,it.description.orEmpty(),lifeAreaId=it.lifeAreaId) }
            EntityType.SKILL -> dao.getSkill(id)?.let { Draft(id,type,it.title,it.description.orEmpty(),lifeAreaId=it.lifeAreaId,hobbyId=it.hobbyId,currentFocus=it.currentFocus.orEmpty()) }
            EntityType.SESSION -> dao.getSession(id)?.let { Draft(id,type,it.title,it.notes.orEmpty(),date=it.localDate,lifeAreaId=it.lifeAreaId,projectId=it.projectId,hobbyId=it.hobbyId,skillId=it.skillId,duration=it.durationMinutes?.toString().orEmpty()) }
            EntityType.JOURNAL -> dao.getJournalEntry(id)?.let { Draft(id,type,it.title.orEmpty(),it.body,date=it.entryLocalDate,lifeAreaId=it.lifeAreaId,projectId=it.projectId,goalId=it.goalId) }
            EntityType.IDEA -> dao.getIdea(id)?.let { Draft(id,type,it.title.orEmpty(),it.body,lifeAreaId=it.lifeAreaId,projectId=it.projectId) }
            EntityType.MEMORY -> dao.getMemory(id)?.let { Draft(id,type,it.title,it.body.orEmpty(),date=it.memoryLocalDate,lifeAreaId=it.lifeAreaId,projectId=it.projectId) }
            EntityType.CHAPTER -> dao.getChapter(id)?.let { Draft(id,type,it.title,it.description.orEmpty(),date=it.startLocalDate,endDate=it.endLocalDate.orEmpty(),lifeAreaId=it.lifeAreaId) }
            EntityType.WEEKLY_REVIEW -> dao.getWeeklyReview(id)?.let { Draft(id,type,it.highlight.orEmpty(),it.reflectionBody.orEmpty(),date=it.periodStartLocalDate) }
            else -> null
        }
        val rid=when(type) { EntityType.TASK -> dao.getTask(id)?.recurrenceRuleId;EntityType.REMINDER -> dao.getReminder(id)?.recurrenceRuleId;EntityType.HABIT -> dao.getHabit(id)?.scheduleRuleId;else -> null }
        rid?.let { dao.getRecurrenceRule(it) }?.let { r -> d=d?.copy(frequency=r.frequency,interval=r.interval,weekdaysMask=r.weekdaysMask,endDate=r.endLocalDate.orEmpty(),countLimit=r.occurrenceCountLimit?.toString().orEmpty(),date=d?.date?.ifBlank { r.startLocalDate } ?: r.startLocalDate) }
        if(type==EntityType.TASK) dao.allReminder().firstOrNull { it.ownerType==OwnerType.TASK && it.ownerId==id && it.state==ReminderState.SCHEDULED }?.triggerAt?.let {
            val z=Instant.ofEpochMilli(it).atZone(clock.zone);d=d?.copy(reminderDate=z.toLocalDate().toString(),reminderTime=z.toLocalTime().toString())
        }
        return d
    }
    suspend fun refreshCalendar() = db.withTransaction {
        val today=LocalDate.now(clock)
        for(task in dao.allTask().filter {it.status==TaskStatus.OPEN && it.recurrenceRuleId!=null}) {
            val rule=dao.getRecurrenceRule(requireNotNull(task.recurrenceRuleId)) ?: continue
            val oldDate=task.dueLocalDate?.let(LocalDate::parse) ?: LocalDate.parse(rule.startLocalDate)
            if(oldDate>=today) continue
            val exceptions=dao.allRecurrenceException().filter {it.recurrenceRuleId==rule.id}
            val latest=engine.latestDue(rule,today,exceptions) ?: continue
            if(latest<=oldDate) continue
            dao.put(task.copy(dueLocalDate=latest.toString(),occurrenceLocalDate=latest.toString(),dueAt=rule.localTimeMinutes?.let {engine.instant(rule,latest,clock.zone).toEpochMilli()},updatedAt=clock.millis()))
            val days=java.time.temporal.ChronoUnit.DAYS.between(oldDate,latest)
            dao.allReminder().filter {it.ownerType==OwnerType.TASK && it.ownerId==task.id && it.state!=ReminderState.CANCELLED}.forEach {reminder ->
                reminder.triggerAt?.let {old -> val next=Instant.ofEpochMilli(old).atZone(clock.zone).plusDays(days).toInstant().toEpochMilli()
                    dao.put(reminder.copy(triggerAt=next,state=ReminderState.SCHEDULED,deliveryState=if(next>clock.millis()) DeliveryState.PENDING else DeliveryState.EXPIRED,scheduledAt=null,updatedAt=clock.millis()))
                }
            }
            index(EntityType.TASK,task.id)
        }
    }
    suspend fun completeTask(id: String, expectedDate: String? = null, confirmOpenChildren: Boolean = false) = db.withTransaction {
        val t=requireNotNull(dao.getTask(id));if(t.status!=TaskStatus.OPEN) return@withTransaction
        require(confirmOpenChildren || dao.allTask().none { it.parentTaskId==id && it.status==TaskStatus.OPEN }) { "Complete or cancel the open subtasks first." }
        if(expectedDate!=null && t.dueLocalDate!=expectedDate) return@withTransaction
        val previousReminders=dao.allReminder().filter {it.ownerType==OwnerType.TASK && it.ownerId==id && it.state!=ReminderState.CANCELLED}
        val now=clock.millis();event("TASK_COMPLETED",EntityType.TASK,id,t.title)
        cancelReminders(EntityType.TASK,id)
        val r=t.recurrenceRuleId?.let { dao.getRecurrenceRule(it) }
        if(r==null) dao.put(t.copy(status=TaskStatus.COMPLETED,completedAt=now,pinnedFocus=false,updatedAt=now))
        else {
            val date=(t.occurrenceLocalDate ?: t.dueLocalDate)?.let(LocalDate::parse) ?: LocalDate.now(clock)
            val next=engine.next(r,maxOf(date,LocalDate.now(clock)),dao.allRecurrenceException().filter { it.recurrenceRuleId==r.id })
            if(next==null) dao.put(t.copy(status=TaskStatus.COMPLETED,completedAt=now,pinnedFocus=false,updatedAt=now))
            else {
                dao.put(t.copy(dueLocalDate=next.toString(),dueAt=r.localTimeMinutes?.let { engine.instant(r,next,clock.zone).toEpochMilli() },occurrenceLocalDate=next.toString(),completedAt=null,pinnedFocus=false,updatedAt=now))
                previousReminders.forEach { reminder -> reminder.triggerAt?.let { trigger ->
                    val oldTime=Instant.ofEpochMilli(trigger).atZone(clock.zone)
                    val dayOffset=java.time.temporal.ChronoUnit.DAYS.between(date,oldTime.toLocalDate())
                    val nextTrigger=next.plusDays(dayOffset).atTime(oldTime.toLocalTime()).atZone(clock.zone).toInstant().toEpochMilli()
                    if(nextTrigger>now) dao.put(reminder.copy(triggerAt=nextTrigger,state=ReminderState.SCHEDULED,deliveryState=DeliveryState.PENDING,deliveredAt=null,scheduledAt=null,updatedAt=now))
                } }
            }
        }
        index(EntityType.TASK,id)
    }
    suspend fun skipOccurrence(id: String,expectedDate: String?) = db.withTransaction {
        val task=requireNotNull(dao.getTask(id));require(task.status==TaskStatus.OPEN)
        if(task.dueLocalDate!=expectedDate) return@withTransaction
        val rule=requireNotNull(task.recurrenceRuleId?.let {dao.getRecurrenceRule(it)})
        val original=task.occurrenceLocalDate ?: task.dueLocalDate ?: rule.startLocalDate
        val old=dao.allRecurrenceException().firstOrNull {it.recurrenceRuleId==rule.id && it.occurrenceLocalDate==original}
        dao.put((old ?: RecurrenceException(recurrenceRuleId=rule.id,occurrenceLocalDate=original)).copy(type=ExceptionType.SKIP,updatedAt=clock.millis()))
        val next=engine.next(rule,maxOf(LocalDate.parse(original),LocalDate.now(clock)),dao.allRecurrenceException())
        shiftTaskReminders(id,LocalDate.parse(requireNotNull(task.dueLocalDate)),next)
        dao.put(task.copy(dueLocalDate=next?.toString(),occurrenceLocalDate=next?.toString(),dueAt=next?.let {if(rule.localTimeMinutes!=null) engine.instant(rule,it,clock.zone).toEpochMilli() else null},status=if(next==null) TaskStatus.CANCELLED else TaskStatus.OPEN,pinnedFocus=false,updatedAt=clock.millis()))
        event("OCCURRENCE_SKIPPED",EntityType.TASK,id,task.title);index(EntityType.TASK,id)
    }
    suspend fun overrideOccurrence(id: String,date: LocalDate) = db.withTransaction {
        val task=requireNotNull(dao.getTask(id));require(task.status==TaskStatus.OPEN)
        val rule=requireNotNull(task.recurrenceRuleId?.let {dao.getRecurrenceRule(it)})
        val original=task.occurrenceLocalDate ?: task.dueLocalDate ?: rule.startLocalDate
        val old=dao.allRecurrenceException().firstOrNull {it.recurrenceRuleId==rule.id && it.occurrenceLocalDate==original}
        shiftTaskReminders(id,LocalDate.parse(requireNotNull(task.dueLocalDate)),date)
        val at=engine.instant(rule,date,clock.zone).toEpochMilli()
        dao.put((old ?: RecurrenceException(recurrenceRuleId=rule.id,occurrenceLocalDate=original)).copy(type=ExceptionType.OVERRIDE,overrideDueAt=at,updatedAt=clock.millis()))
        dao.put(task.copy(dueLocalDate=date.toString(),dueAt=if(rule.localTimeMinutes!=null) at else null,occurrenceLocalDate=original,updatedAt=clock.millis()))
        event("OCCURRENCE_RESCHEDULED",EntityType.TASK,id,task.title);index(EntityType.TASK,id)
    }
    private suspend fun shiftTaskReminders(id: String,oldDate: LocalDate,nextDate: LocalDate?) {
        if(nextDate==null) {cancelReminders(EntityType.TASK,id);return}
        val days=java.time.temporal.ChronoUnit.DAYS.between(oldDate,nextDate)
        dao.allReminder().filter {it.ownerType==OwnerType.TASK && it.ownerId==id && it.state!=ReminderState.CANCELLED}.forEach {reminder ->
            reminder.triggerAt?.let {trigger ->
                val shifted=Instant.ofEpochMilli(trigger).atZone(clock.zone).plusDays(days).toInstant().toEpochMilli()
                dao.put(reminder.copy(triggerAt=shifted,state=ReminderState.SCHEDULED,
                    deliveryState=if(shifted>clock.millis()) DeliveryState.PENDING else DeliveryState.EXPIRED,
                    deliveredAt=null,scheduledAt=null,updatedAt=clock.millis()))
            }
        }
    }
    suspend fun reopenTask(id: String) = db.withTransaction {
        val t=requireNotNull(dao.getTask(id));if(t.status==TaskStatus.OPEN) return@withTransaction
        dao.put(t.copy(status=TaskStatus.OPEN,completedAt=null,updatedAt=clock.millis()));event("TASK_REOPENED",EntityType.TASK,id,t.title);index(EntityType.TASK,id)
        dao.allReminder().filter { it.ownerType==OwnerType.TASK && it.ownerId==id && (it.triggerAt ?: 0)>clock.millis() }.forEach { dao.put(it.copy(state=ReminderState.SCHEDULED,deliveryState=DeliveryState.PENDING,updatedAt=clock.millis())) }
    }
    suspend fun pin(id: String, pinned: Boolean) = db.withTransaction {
        dao.allTask().filter { it.pinnedFocus && (it.id!=id || !pinned) }.forEach { dao.put(it.copy(pinnedFocus=false,updatedAt=clock.millis()));event("FOCUS_CLEARED",EntityType.TASK,it.id,it.title) }
        val t=requireNotNull(dao.getTask(id));require(t.status==TaskStatus.OPEN)
        dao.put(t.copy(pinnedFocus=pinned,updatedAt=clock.millis()));if(pinned) event("FOCUS_PINNED",EntityType.TASK,id,t.title)
    }
    suspend fun reschedule(id: String, date: LocalDate?) = db.withTransaction {
        val t=requireNotNull(dao.getTask(id));require(t.status==TaskStatus.OPEN)
        if(t.recurrenceRuleId!=null && date!=null) {overrideOccurrence(id,date);return@withTransaction}
        if(t.dueLocalDate!=null) shiftTaskReminders(id,LocalDate.parse(t.dueLocalDate),date)
        val time=t.dueAt?.let { Instant.ofEpochMilli(it).atZone(clock.zone).toLocalTime() }
        dao.put(t.copy(dueLocalDate=date?.toString(),dueAt=if(date!=null && time!=null) date.atTime(time).atZone(clock.zone).toInstant().toEpochMilli() else null,updatedAt=clock.millis()));index(EntityType.TASK,id)
    }
    suspend fun status(type: EntityType, id: String, status: String) = db.withTransaction {
        val now=clock.millis();var title=""
        when(type) {
            EntityType.PROJECT -> { val r=requireNotNull(dao.getProject(id));title=r.title;dao.put(r.copy(status=ProjectStatus.valueOf(status),completedAt=if(status=="COMPLETED") r.completedAt ?: now else if(status=="ARCHIVED") r.completedAt else null,updatedAt=now)) }
            EntityType.GOAL -> { val r=requireNotNull(dao.getGoal(id));title=r.title;dao.put(r.copy(status=GoalStatus.valueOf(status),achievedAt=if(status=="ACHIEVED") r.achievedAt ?: now else if(status=="ARCHIVED") r.achievedAt else null,updatedAt=now)) }
            EntityType.LIFE_AREA -> { val r=requireNotNull(dao.getLifeArea(id));title=r.name;dao.put(r.copy(status=LifeAreaStatus.valueOf(status),updatedAt=now)) }
            EntityType.HABIT -> { val r=requireNotNull(dao.getHabit(id));title=r.title;dao.put(r.copy(status=ActiveStatus.valueOf(status),updatedAt=now));if(status!="ACTIVE") cancelReminders(type,id)
                else dao.allReminder().filter {it.ownerType==OwnerType.HABIT && it.ownerId==id && it.localTimeMinutes!=null}.forEach {dao.put(it.copy(state=ReminderState.SCHEDULED,deliveryState=DeliveryState.PENDING,scheduledAt=null,updatedAt=now))}
            }
            EntityType.HOBBY -> { val r=requireNotNull(dao.getHobby(id));title=r.title;dao.put(r.copy(status=ActiveStatus.valueOf(status),updatedAt=now)) }
            EntityType.SKILL -> { val r=requireNotNull(dao.getSkill(id));title=r.title;dao.put(r.copy(status=ActiveStatus.valueOf(status),updatedAt=now)) }
            EntityType.CHAPTER -> { val r=requireNotNull(dao.getChapter(id));title=r.title;dao.put(r.copy(status=ChapterStatus.valueOf(status),updatedAt=now)) }
            EntityType.IDEA -> { val r=requireNotNull(dao.getIdea(id));title=r.title.orEmpty();dao.put(r.copy(status=IdeaStatus.valueOf(status),updatedAt=now)) }
            EntityType.MILESTONE -> { val r=requireNotNull(dao.getMilestone(id));title=r.title;dao.put(r.copy(status=MilestoneStatus.valueOf(status),completedAt=if(status=="COMPLETED") r.completedAt ?: now else if(status=="ARCHIVED") r.completedAt else null,updatedAt=now)) }
            EntityType.TASK -> { val r=requireNotNull(dao.getTask(id));title=r.title;require(status=="CANCELLED");dao.put(r.copy(status=TaskStatus.CANCELLED,pinnedFocus=false,updatedAt=now));cancelReminders(type,id) }
            EntityType.REMINDER -> { val r=requireNotNull(dao.getReminder(id));title=r.title;dao.put(r.copy(state=ReminderState.CANCELLED,updatedAt=now)) }
            else -> error("This record does not have that status action.")
        }
        event("STATUS_$status",type,id,title);index(type,id)
    }
    suspend fun logHabit(id: String, date: LocalDate, state: HabitEventState, value: Double? = null) = db.withTransaction {
        val h=requireNotNull(dao.getHabit(id));require(h.status==ActiveStatus.ACTIVE)
        require(date<=LocalDate.now(clock)) {"Log a day that has already begun."}
        require(value==null || value.isFinite() && value>=0) {"Use a valid non-negative amount."}
        require(h.targetType==TargetType.CHECK || state==HabitEventState.SKIPPED || value!=null) {"Enter the recorded amount."}
        val r=requireNotNull(dao.getRecurrenceRule(h.scheduleRuleId));require(engine.dates(r,date,date).isNotEmpty()) { "This habit is not scheduled for that day." }
        val old=dao.allHabitEvent().firstOrNull { it.habitId==id && it.localDate==date.toString() }
        dao.put((old ?: HabitEvent(habitId=id,localDate=date.toString())).copy(state=state,value=value,updatedAt=clock.millis()))
    }
    suspend fun convertIdea(id: String, target: EntityType): String = db.withTransaction {
        require(target in listOf(EntityType.TASK,EntityType.PROJECT))
        val idea=requireNotNull(dao.getIdea(id))
        if(idea.convertedToType==target && idea.convertedToId!=null) return@withTransaction idea.convertedToId
        val targetId=java.util.UUID.randomUUID().toString()
        save(Draft(targetId,target,idea.title ?: idea.body.take(100),idea.body,projectId=if(target==EntityType.TASK) idea.projectId else null,lifeAreaId=idea.lifeAreaId))
        dao.put(idea.copy(status=IdeaStatus.CONVERTED,convertedToType=target,convertedToId=targetId,updatedAt=clock.millis()))
        dao.put(EntityLink(fromType=EntityType.IDEA,fromId=id,toType=target,toId=targetId,relationType="CONVERTED_TO"))
        event("IDEA_CONVERTED",EntityType.IDEA,id,idea.title ?: idea.body.take(100));index(EntityType.IDEA,id);targetId
    }
    suspend fun saveMemory(type: EntityType,id: String): String = db.withTransaction {
        require(type==EntityType.JOURNAL || type==EntityType.SESSION)
        dao.allMemory().firstOrNull { it.sourceType==type && it.sourceId==id }?.let { return@withTransaction it.id }
        val source=requireNotNull(edit(type,id));val m=Memory(title=source.title.ifBlank { source.body.take(100) },body=source.body,memoryLocalDate=source.date,sourceType=type,sourceId=id,projectId=source.projectId,lifeAreaId=source.lifeAreaId)
        dao.put(m);index(EntityType.MEMORY,m.id);m.id
    }
    suspend fun chapterMember(chapterId: String,type: EntityType,id: String,selected: Boolean) = db.withTransaction {
        requireNotNull(dao.getChapter(chapterId));require(type in listOf(EntityType.MEMORY,EntityType.JOURNAL,EntityType.SESSION,EntityType.PROJECT,EntityType.GOAL,EntityType.MILESTONE));requireNotNull(edit(type,id))
        val old=dao.allChapterItem().firstOrNull { it.chapterId==chapterId && it.entityType==type && it.entityId==id }
        if(selected && old==null) dao.put(ChapterItem(chapterId=chapterId,entityType=type,entityId=id,orderIndex=dao.allChapterItem().count { it.chapterId==chapterId }))
        if(!selected && old!=null) dao.deleteChapterItem(old.id)
    }
    suspend fun reorderChapter(chapterId: String, ids: List<String>) = db.withTransaction {
        val members=dao.allChapterItem().filter { it.chapterId==chapterId };require(ids.toSet()==members.map { it.id }.toSet() && ids.size==members.size)
        ids.forEachIndexed { i,id -> dao.put(members.first { it.id==id }.copy(orderIndex=i)) }
    }
    suspend fun delete(type: EntityType,id: String): Unit = db.withTransaction {
        require(type in setOf(EntityType.TASK,EntityType.JOURNAL,EntityType.IDEA,EntityType.MEMORY,EntityType.SESSION)) { "Archive this item to preserve its history." }
        if(type==EntityType.TASK) dao.allTask().filter {it.parentTaskId==id}.forEach {delete(EntityType.TASK,it.id)}
        cancelReminders(type,id)
        dao.allEntityLink().filter { (it.fromType==type && it.fromId==id)||(it.toType==type && it.toId==id) }.forEach { dao.deleteEntityLink(it.id) }
        dao.allChapterItem().filter { it.entityType==type && it.entityId==id }.forEach { dao.deleteChapterItem(it.id) }
        dao.allAttachment().filter { it.ownerType==type && it.ownerId==id }.forEach { dao.deleteAttachment(it.id) }
        dao.removeTags(type,id);dao.removeDocument(type,id)
        when(type) { EntityType.TASK -> dao.deleteTask(id);EntityType.JOURNAL -> dao.deleteJournalEntry(id);EntityType.IDEA -> dao.deleteIdea(id);EntityType.MEMORY -> dao.deleteMemory(id);EntityType.SESSION -> dao.deleteSession(id);else -> Unit }
    }
    suspend fun nextAction(projectId: String,taskId: String) = db.withTransaction {
        requireNotNull(dao.getProject(projectId));val task=requireNotNull(dao.getTask(taskId));require(task.projectId==projectId && task.status==TaskStatus.OPEN)
        dao.allEntityLink().filter {it.fromType==EntityType.PROJECT && it.fromId==projectId && it.relationType=="NEXT_ACTION"}.forEach {dao.deleteEntityLink(it.id)}
        dao.put(EntityLink(fromType=EntityType.PROJECT,fromId=projectId,toType=EntityType.TASK,toId=taskId,relationType="NEXT_ACTION"))
        event("NEXT_ACTION_SELECTED",EntityType.PROJECT,projectId,task.title)
    }
    suspend fun tag(type: EntityType,id: String,name: String,add: Boolean) = db.withTransaction {
        requireNotNull(edit(type,id));val clean=name.trim();require(clean.isNotBlank() && clean.length<=60) {"Use a tag between 1 and 60 characters."}
        val normalized=java.text.Normalizer.normalize(clean,java.text.Normalizer.Form.NFKC).lowercase(java.util.Locale.ROOT)
        val old=dao.allTag().firstOrNull {it.normalizedName==normalized}
        if(add) {
            val tag=old ?: Tag(name=clean,normalizedName=normalized);if(old==null) dao.put(tag)
            dao.put(EntityTagCrossRef(tag.id,type,id))
        } else if(old!=null) dao.remove(EntityTagCrossRef(old.id,type,id))
        index(type,id)
    }
    suspend fun link(fromType: EntityType,fromId: String,toType: EntityType,toId: String) = db.withTransaction {
        require(fromType!=toType || fromId!=toId) {"Choose another record."};requireNotNull(edit(fromType,fromId));requireNotNull(edit(toType,toId))
        if(dao.allEntityLink().none {it.fromType==fromType && it.fromId==fromId && it.toType==toType && it.toId==toId || it.fromType==toType && it.fromId==toId && it.toType==fromType && it.toId==fromId})
            dao.put(EntityLink(fromType=fromType,fromId=fromId,toType=toType,toId=toId,relationType="RELATED"))
    }
    private suspend fun cancelReminders(type: EntityType,id: String) {
        val owner=when(type) { EntityType.TASK -> OwnerType.TASK;EntityType.HABIT -> OwnerType.HABIT;else -> null }
        if(owner!=null) dao.allReminder().filter { it.ownerType==owner && it.ownerId==id }.forEach { dao.put(it.copy(state=ReminderState.CANCELLED,updatedAt=clock.millis())) }
    }
    private suspend fun event(name: String,type: EntityType,id: String,title: String) {
        val now=clock.millis();dao.put(TimelineEvent(eventType=name,occurredAt=now,localDate=LocalDate.now(clock).toString(),sourceType=type,sourceId=id,titleSnapshot=title))
    }
}

