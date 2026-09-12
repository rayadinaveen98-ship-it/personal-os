package com.navin.personalos.core.domain

import com.navin.personalos.core.database.*
import java.time.*
import java.time.temporal.TemporalAdjusters

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
    fun week(date: LocalDate, tasks: List<Task>, sessions: List<Session>, journals: List<JournalEntry>, milestones: List<Milestone>, ideas: List<Idea>, zone: ZoneId): WeekEvidence {
        val start=date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)); val end=start.plusDays(6)
        fun contains(d: String) = d >= start.toString() && d <= end.toString()
        fun time(t: Long?) = t != null && contains(Instant.ofEpochMilli(t).atZone(zone).toLocalDate().toString())
        val ss=sessions.filter { contains(it.localDate) }
        return WeekEvidence(start,end,tasks.count { it.status==TaskStatus.COMPLETED && time(it.completedAt) },ss.size,ss.sumOf { it.durationMinutes ?: 0 },journals.filter { contains(it.entryLocalDate) }.map { it.entryLocalDate }.distinct().size,milestones.count { it.status==MilestoneStatus.COMPLETED && time(it.completedAt) },ideas.count { time(it.createdAt) })
    }
}
