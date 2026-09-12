package com.navin.personalos.core.domain

import com.navin.personalos.core.database.*
import java.time.*
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalAdjusters

/** Calendar rules are anchored to the original start, so a February clamp cannot drift March. */
class RecurrenceEngine {
    fun dates(rule: RecurrenceRule, from: LocalDate, through: LocalDate): List<LocalDate> {
        validate(rule)
        val start = LocalDate.parse(rule.startLocalDate)
        val end = rule.endLocalDate?.let(LocalDate::parse)?.let { minOf(it, through) } ?: through
        if (end < start || end < from) return emptyList()
        val result = mutableListOf<LocalDate>()
        var count = 0
        var cursor = start
        while (cursor <= end) {
            if (matches(rule, start, cursor)) {
                count++
                if (rule.occurrenceCountLimit != null && count > rule.occurrenceCountLimit) break
                if (cursor >= from) result.add(cursor)
            }
            cursor = cursor.plusDays(1)
        }
        return result
    }
    fun next(rule: RecurrenceRule, after: LocalDate, exceptions: List<RecurrenceException> = emptyList()): LocalDate? {
        val skipped = exceptions.filter { it.type == ExceptionType.SKIP }.map { it.occurrenceLocalDate }.toSet()
        return dates(rule, after.plusDays(1), after.plusYears(8)).firstOrNull { it.toString() !in skipped }
    }
    fun latestDue(rule: RecurrenceRule, today: LocalDate, exceptions: List<RecurrenceException> = emptyList()): LocalDate? {
        val skipped = exceptions.filter { it.type == ExceptionType.SKIP }.map { it.occurrenceLocalDate }.toSet()
        return dates(rule, LocalDate.parse(rule.startLocalDate), today).lastOrNull { it.toString() !in skipped }
    }
    fun instant(rule: RecurrenceRule, date: LocalDate, zone: ZoneId): Instant =
        date.atTime(LocalTime.ofSecondOfDay((rule.localTimeMinutes ?: 540) * 60L)).atZone(zone).toInstant()
    fun validate(rule: RecurrenceRule) {
        require(rule.interval in 1..1000) { "Choose an interval between 1 and 1000." }
        require(rule.localTimeMinutes == null || rule.localTimeMinutes in 0..1439)
        require(rule.dayOfMonth == null || rule.dayOfMonth in 1..31)
        require(rule.monthOfYear == null || rule.monthOfYear in 1..12)
        require(rule.weekdaysMask == null || rule.weekdaysMask in 1..127)
        require(rule.occurrenceCountLimit == null || rule.occurrenceCountLimit > 0)
        val start = LocalDate.parse(rule.startLocalDate)
        require(rule.endLocalDate == null || LocalDate.parse(rule.endLocalDate) >= start)
    }
    private fun matches(r: RecurrenceRule, start: LocalDate, day: LocalDate): Boolean = when (r.frequency) {
        Frequency.DAILY -> ChronoUnit.DAYS.between(start, day) % r.interval == 0L
        Frequency.WEEKLY -> {
            val a = start.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
            val b = day.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
            val mask = r.weekdaysMask ?: (1 shl (start.dayOfWeek.value - 1))
            ChronoUnit.WEEKS.between(a, b) % r.interval == 0L && mask and (1 shl (day.dayOfWeek.value - 1)) != 0
        }
        Frequency.MONTHLY -> ChronoUnit.MONTHS.between(YearMonth.from(start), YearMonth.from(day)) % r.interval == 0L &&
            day.dayOfMonth == minOf(r.dayOfMonth ?: start.dayOfMonth, day.lengthOfMonth())
        Frequency.YEARLY -> (day.year - start.year) % r.interval == 0 && day.monthValue == (r.monthOfYear ?: start.monthValue) &&
            day.dayOfMonth == minOf(r.dayOfMonth ?: start.dayOfMonth, day.lengthOfMonth())
    }
}
