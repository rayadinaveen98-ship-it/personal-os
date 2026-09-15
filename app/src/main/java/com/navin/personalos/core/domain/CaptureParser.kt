package com.navin.personalos.core.domain

import com.navin.personalos.core.database.*
import java.time.*

/** A proposal only. Parsing never writes a record or invents a context. */
data class CaptureProposal(
    val type: EntityType,
    val title: String,
    val body: String,
    val date: LocalDate?,
    val time: LocalTime?,
    val priority: Priority,
    val durationMinutes: Int?,
    val frequency: Frequency?,
    val projectId: String?,
    val lifeAreaId: String?,
    val ambiguity: String?,
    val weekdaysMask: Int? = null,
)
class CaptureParser {
    fun parse(input: String, now: ZonedDateTime, projects: List<Project> = emptyList(), areas: List<LifeArea> = emptyList()): CaptureProposal {
        val text = input.trim()
        require(text.isNotEmpty()) { "Write something to capture first." }
        val lower = text.lowercase()
        val durationMatch = Regex("(\\d+)\\s*(minutes?|mins?|hours?|hrs?)\\b").find(lower)
        val duration = durationMatch?.let { it.groupValues[1].toIntOrNull()?.let { n -> if (it.groupValues[2].startsWith("h")) n * 60 else n } }
        val type = when {
            Regex("\\b(remind|reminder)\\b").containsMatchIn(lower) -> EntityType.REMINDER
            lower.startsWith("idea") -> EntityType.IDEA
            lower.startsWith("memory") || lower.startsWith("remember when") -> EntityType.MEMORY
            duration != null && Regex("\\b(worked|practiced|practised|read|studied|learned|spent)\\b").containsMatchIn(lower) -> EntityType.SESSION
            Regex("\\b(felt|feeling|grateful|journal|reflection)\\b").containsMatchIn(lower) -> EntityType.JOURNAL
            else -> EntityType.TASK
        }
        var date: LocalDate? = when {
            "day after tomorrow" in lower -> now.toLocalDate().plusDays(2)
            "tomorrow" in lower -> now.toLocalDate().plusDays(1)
            "yesterday" in lower -> now.toLocalDate().minusDays(1)
            "today" in lower -> now.toLocalDate()
            else -> null
        }
        val iso = Regex("\\b\\d{4}-\\d{2}-\\d{2}\\b").find(lower)
        var ambiguity: String? = null
        if (iso != null) { date = runCatching { LocalDate.parse(iso.value) }.getOrNull(); if (date == null) ambiguity = "Choose a valid date." }
        if (date == null) {
            DayOfWeek.entries.firstOrNull { Regex("\\b${it.name.lowercase()}\\b").containsMatchIn(lower) }?.let {
                var delta = (it.value - now.dayOfWeek.value + 7) % 7
                if (delta == 0 || "next ${it.name.lowercase()}" in lower) delta = if (delta == 0) 7 else delta
                date = now.toLocalDate().plusDays(delta.toLong())
            }
        }
        val timeMatch = Regex("\\b(?:at\\s+)?(\\d{1,2})(?::(\\d{2}))?\\s*(am|pm)\\b|\\bat\\s+(\\d{1,2})(?::(\\d{2}))?\\b").find(lower)
        var time: LocalTime? = null
        timeMatch?.let {
            val ampm = it.groupValues[3]
            var hour = (it.groupValues[1].ifEmpty { it.groupValues[4] }).toInt()
            val minute = (it.groupValues[2].ifEmpty { it.groupValues[5] }).ifEmpty { "0" }.toInt()
            if (ampm.isNotEmpty()) { if (hour !in 1..12) ambiguity = "Choose a valid time."; hour = hour % 12 + if (ampm == "pm") 12 else 0 }
            time = runCatching { LocalTime.of(hour, minute) }.getOrNull()
            if (time == null) ambiguity = "Choose a valid time."
            if (ampm.isEmpty() && it.groupValues[5].isEmpty() && hour in 1..12) ambiguity = "Check whether you meant morning or evening."
        }
        val relative = Regex("\\bin\\s+(\\d+)\\s*(minutes?|mins?|hours?|hrs?)\\b").find(lower)
        relative?.let {
            val value = it.groupValues[1].toLongOrNull() ?: 0
            if (value in 1..525600) {
                val future = now.plusMinutes(value * if (it.groupValues[2].startsWith("h")) 60 else 1)
                date = future.toLocalDate(); time = future.toLocalTime().withSecond(0).withNano(0)
            }
        }
        val namedDays=DayOfWeek.entries.filter {Regex("\\b${it.name.lowercase()}\\b").containsMatchIn(lower)}
        val weekdaysMask=when {"every weekday" in lower -> 31;"every" in lower && namedDays.isNotEmpty() -> namedDays.fold(0) {mask,day -> mask or (1 shl(day.value-1))};else -> null}
        val frequency = when {
            weekdaysMask!=null -> Frequency.WEEKLY
            "daily" in lower || "every day" in lower -> Frequency.DAILY
            "weekly" in lower || "every week" in lower || "every weekday" in lower -> Frequency.WEEKLY
            "monthly" in lower || "every month" in lower -> Frequency.MONTHLY
            "yearly" in lower || "every year" in lower -> Frequency.YEARLY
            else -> null
        }
        val priority = when { "urgent" in lower -> Priority.URGENT; "high priority" in lower || "important" in lower -> Priority.HIGH; "low priority" in lower -> Priority.LOW; else -> Priority.NORMAL }
        val matches = projects.filter { lower.contains(it.title.lowercase()) }
        val areaMatches = areas.filter { lower.contains(it.name.lowercase()) }
        if (matches.size > 1 || areaMatches.size > 1) ambiguity = "Choose which context this belongs to."
        if (type == EntityType.REMINDER && (date == null || time == null)) ambiguity = ambiguity ?: "Choose the reminder date and time."
        if (type == EntityType.TASK && date == null && frequency == null) ambiguity = ambiguity ?: "Choose what this should be if Task isn't right."
        val title = text.replace(Regex("^(idea|memory|journal|reflection)\\s*:\\s*", RegexOption.IGNORE_CASE), "").take(500)
        return CaptureProposal(type, title, text, date, time, priority, duration, frequency, matches.singleOrNull()?.id, areaMatches.singleOrNull()?.id, ambiguity, weekdaysMask)
    }
}
