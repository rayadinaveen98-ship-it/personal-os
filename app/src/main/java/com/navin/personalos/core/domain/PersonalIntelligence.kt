package com.navin.personalos.core.domain

import com.navin.personalos.core.database.*
import java.time.*
import java.time.temporal.TemporalAdjusters

data class FocusSuggestion(val type: EntityType,val id: String,val title: String,val reason: String)
data class Focus(val task: Task, val reason: String)
data class WeekEvidence(val start: LocalDate, val end: LocalDate, val completedTasks: Int, val sessions: Int, val minutes: Int, val reflectionDays: Int, val milestones: Int, val ideas: Int)
class PersonalIntelligence {
    fun focus(tasks: List<Task>, projects: List<Project>, today: LocalDate): Focus? {
        val active = projects.filter { it.status == ProjectStatus.ACTIVE }.map { it.id }.toSet()
        val open = tasks.filter { it.status == TaskStatus.OPEN }
        fun rank(t: Task): Int = when {
            t.pinnedFocus -> 0
            t.priority >= Priority.HIGH && t.dueLocalDate != null && t.dueLocalDate < today.toString() -> 1
            t.priority >= Priority.HIGH && t.dueLocalDate == today.toString() -> 2
            t.dueLocalDate == today.toString() -> 3
            t.projectId in active -> 4
            t.dueLocalDate != null -> 5
            else -> 6
        }
        val task = open.minWithOrNull(compareBy<Task> { rank(it) }.thenBy { it.dueAt ?: Long.MAX_VALUE }.thenBy { it.dueLocalDate ?: "9999" }.thenByDescending { it.priority }.thenBy { it.createdAt }.thenBy { it.id }) ?: return null
        return Focus(task, when(rank(task)) { 0 -> "You pinned this focus."; 1 -> "High priority and still open past its due date."; 2 -> "High priority and due today."; 3 -> "Due today."; 4 -> "An open action in an active project."; 5 -> "Your earliest dated open action."; else -> "An open action you captured." })
    }
    fun suggest(tasks: List<Task>,projects: List<Project>,reminders: List<Reminder>,habits: List<Habit>,rules: List<RecurrenceRule>,links: List<EntityLink>,now: ZonedDateTime): FocusSuggestion? {
        val today=now.toLocalDate()
        val open=tasks.filter {it.status==TaskStatus.OPEN}
        fun task(t: Task,reason: String)=FocusSuggestion(EntityType.TASK,t.id,t.title,reason)
        val preferred=focus(open,projects,today)
        if(preferred!=null && (preferred.task.pinnedFocus || preferred.task.priority>=Priority.HIGH && preferred.task.dueLocalDate?.let {it<=today.toString()}==true)) return task(preferred.task,preferred.reason)
        val imminent=reminders.filter {it.state==ReminderState.SCHEDULED && it.triggerAt?.let {at -> at>=now.toInstant().toEpochMilli() && at<=now.plusHours(24).toInstant().toEpochMilli()}==true && (it.ownerType!=OwnerType.TASK || open.any {t -> t.id==it.ownerId})}.minByOrNull {it.triggerAt!!}
        if(imminent!=null) {
            val owner=open.firstOrNull {it.id==imminent.ownerId && imminent.ownerType==OwnerType.TASK}
            return FocusSuggestion(if(owner!=null) EntityType.TASK else EntityType.REMINDER,owner?.id ?: imminent.id,owner?.title ?: imminent.title,"You set a reminder within the next 24 hours. Check its delivery status for Android permissions.")
        }
        val active=projects.filter {it.status==ProjectStatus.ACTIVE}.map {it.id}.toSet()
        val explicit=links.filter {it.fromType==EntityType.PROJECT && it.fromId in active && it.toType==EntityType.TASK && it.relationType=="NEXT_ACTION"}.sortedByDescending {it.updatedAt}.firstNotNullOfOrNull {link -> open.firstOrNull {it.id==link.toId}}
        if(explicit!=null) return task(explicit,"You chose this as an active project's next action.")
        val dated=open.filter {it.dueLocalDate!=null}.minWithOrNull(compareBy<Task> {it.dueLocalDate}.thenByDescending {it.priority}.thenBy {it.id})
        if(dated!=null) return task(dated,"Your earliest dated open action.")
        val habit=habits.filter {it.status==ActiveStatus.ACTIVE}.sortedBy {it.createdAt}.firstOrNull {h -> rules.firstOrNull {it.id==h.scheduleRuleId}?.let {RecurrenceEngine().dates(it,today,today).isNotEmpty()}==true}
        if(habit!=null) return FocusSuggestion(EntityType.HABIT,habit.id,habit.title,"This rhythm is scheduled for today.")
        return preferred?.let {task(it.task,it.reason)}
    }
    fun week(date: LocalDate, tasks: List<Task>, sessions: List<Session>, journals: List<JournalEntry>, milestones: List<Milestone>, ideas: List<Idea>, zone: ZoneId): WeekEvidence {
        val start=date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)); val end=start.plusDays(6)
        fun contains(d: String) = d >= start.toString() && d <= end.toString()
        fun time(t: Long?) = t != null && contains(Instant.ofEpochMilli(t).atZone(zone).toLocalDate().toString())
        val ss=sessions.filter { contains(it.localDate) }
        return WeekEvidence(start,end,tasks.count { it.status==TaskStatus.COMPLETED && time(it.completedAt) },ss.size,ss.sumOf { it.durationMinutes ?: 0 },journals.filter { contains(it.entryLocalDate) }.map { it.entryLocalDate }.distinct().size,milestones.count { it.status==MilestoneStatus.COMPLETED && time(it.completedAt) },ideas.count { time(it.createdAt) })
    }
}
