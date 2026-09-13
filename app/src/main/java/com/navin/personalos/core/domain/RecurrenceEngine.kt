package com.navin.personalos.core.domain

import com.navin.personalos.core.database.*
import java.time.*
import java.time.temporal.TemporalAdjusters

/** Calendar candidates remain anchored to the original rule, including after a short month. */
class RecurrenceEngine {
    private fun occurrences(rule: RecurrenceRule): Sequence<LocalDate> {
        validate(rule)
        val start=LocalDate.parse(rule.startLocalDate)
        val end=rule.endLocalDate?.let(LocalDate::parse) ?: LocalDate.MAX
        return sequence {
            var period=0L;var count=0
            while(true) {
                val candidates=try { when(rule.frequency) {
                    Frequency.DAILY -> listOf(start.plusDays(period*rule.interval))
                    Frequency.WEEKLY -> {
                        val monday=start.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).plusWeeks(period*rule.interval)
                        val mask=rule.weekdaysMask ?: (1 shl(start.dayOfWeek.value-1))
                        (0..6).filter { mask and (1 shl it)!=0 }.map { monday.plusDays(it.toLong()) }
                    }
                    Frequency.MONTHLY -> { val month=YearMonth.from(start).plusMonths(period*rule.interval);listOf(month.atDay(minOf(rule.dayOfMonth ?: start.dayOfMonth,month.lengthOfMonth()))) }
                    Frequency.YEARLY -> { val year=Math.toIntExact(start.year+period*rule.interval);val month=YearMonth.of(year,rule.monthOfYear ?: start.monthValue);listOf(month.atDay(minOf(rule.dayOfMonth ?: start.dayOfMonth,month.lengthOfMonth()))) }
                } } catch(_: DateTimeException) { break } catch(_: ArithmeticException) { break }
                for(day in candidates) {
                    if(day>end) return@sequence
                    if(day<start) continue
                    if(rule.occurrenceCountLimit!=null && count>=rule.occurrenceCountLimit) return@sequence
                    count++;yield(day)
                }
                period++
            }
        }
    }
    fun dates(rule: RecurrenceRule,from: LocalDate,through: LocalDate): List<LocalDate> =
        if(through<from) emptyList() else occurrences(rule).takeWhile {it<=through}.filter {it>=from}.toList()
    fun next(rule: RecurrenceRule,after: LocalDate,exceptions: List<RecurrenceException> = emptyList()): LocalDate? {
        val skipped=exceptions.filter {it.recurrenceRuleId==rule.id && it.type==ExceptionType.SKIP}.map {it.occurrenceLocalDate}.toSet()
        return occurrences(rule).firstOrNull {it>after && it.toString() !in skipped}
    }
    fun latestDue(rule: RecurrenceRule,today: LocalDate,exceptions: List<RecurrenceException> = emptyList()): LocalDate? {
        val skipped=exceptions.filter {it.recurrenceRuleId==rule.id && it.type==ExceptionType.SKIP}.map {it.occurrenceLocalDate}.toSet()
        return occurrences(rule).takeWhile {it<=today}.lastOrNull {it.toString() !in skipped}
    }
    fun instant(rule: RecurrenceRule,date: LocalDate,zone: ZoneId): Instant = date.atTime(LocalTime.ofSecondOfDay((rule.localTimeMinutes ?: 540)*60L)).atZone(zone).toInstant()
    fun validate(rule: RecurrenceRule) {
        require(rule.interval in 1..1000) {"Choose an interval between 1 and 1000."}
        require(rule.localTimeMinutes==null || rule.localTimeMinutes in 0..1439)
        require(rule.dayOfMonth==null || rule.dayOfMonth in 1..31)
        require(rule.monthOfYear==null || rule.monthOfYear in 1..12)
        require(rule.weekdaysMask==null || rule.weekdaysMask in 1..127)
        require(rule.occurrenceCountLimit==null || rule.occurrenceCountLimit>0)
        val start=LocalDate.parse(rule.startLocalDate)
        require(rule.endLocalDate==null || LocalDate.parse(rule.endLocalDate)>=start)
    }
}
