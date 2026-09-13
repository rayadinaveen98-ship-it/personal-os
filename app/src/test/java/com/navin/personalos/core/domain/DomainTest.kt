package com.navin.personalos.core.domain

import com.navin.personalos.core.database.*
import org.junit.Assert.*
import org.junit.Test
import java.time.*

class DomainTest {
    private val recurrence=RecurrenceEngine()
    @Test fun monthEndDoesNotDrift() {
        val r=RecurrenceRule(frequency=Frequency.MONTHLY,dayOfMonth=31,startLocalDate="2026-01-31")
        assertEquals(listOf("2026-01-31","2026-02-28","2026-03-31","2026-04-30"),recurrence.dates(r,LocalDate.parse("2026-01-01"),LocalDate.parse("2026-04-30")).map { it.toString() })
    }
    @Test fun weekdayIntervalAndCountRespectOriginalAnchor() {
        val r=RecurrenceRule(frequency=Frequency.WEEKLY,interval=2,weekdaysMask=5,startLocalDate="2026-09-07",occurrenceCountLimit=3)
        assertEquals(listOf("2026-09-07","2026-09-09","2026-09-21"),recurrence.dates(r,LocalDate.parse("2026-09-01"),LocalDate.parse("2026-10-30")).map { it.toString() })
    }
    @Test fun latestOccurrenceDoesNotMultiplyMissedDebt() {
        val r=RecurrenceRule(startLocalDate="2026-09-01")
        assertEquals(LocalDate.parse("2026-09-20"),recurrence.latestDue(r,LocalDate.parse("2026-09-20")))
    }
    @Test fun skipDoesNotMutateSeries() {
        val r=RecurrenceRule(startLocalDate="2026-09-01")
        val ex=RecurrenceException(recurrenceRuleId=r.id,occurrenceLocalDate="2026-09-02")
        assertEquals(LocalDate.parse("2026-09-03"),recurrence.next(r,LocalDate.parse("2026-09-01"),listOf(ex)))
        assertEquals(LocalDate.parse("2026-09-02"),recurrence.next(r,LocalDate.parse("2026-09-01")))
    }
    @Test fun daylightSavingKeepsWallClockIntent() {
        val r=RecurrenceRule(startLocalDate="2026-03-07",localTimeMinutes=540)
        val zone=ZoneId.of("America/New_York")
        val a=recurrence.instant(r,LocalDate.parse("2026-03-07"),zone)
        val b=recurrence.instant(r,LocalDate.parse("2026-03-08"),zone)
        assertEquals(23,Duration.between(a,b).toHours())
    }
    @Test fun parserKeepsMissingTimeAmbiguous() {
        val p=CaptureParser().parse("Remind me tomorrow to call",ZonedDateTime.parse("2026-09-12T10:00:00+05:30"))
        assertEquals(EntityType.REMINDER,p.type);assertNull(p.time);assertNotNull(p.ambiguity);assertEquals(LocalDate.parse("2026-09-13"),p.date)
    }
    @Test fun parserReturnsEditableSessionContextOnlyForKnownNames() {
        val project=Project(title="Garden")
        val p=CaptureParser().parse("Worked on Garden for 45 minutes",ZonedDateTime.parse("2026-09-12T10:00:00Z"),listOf(project))
        assertEquals(EntityType.SESSION,p.type);assertEquals(45,p.durationMinutes);assertEquals(project.id,p.projectId)
        assertNull(CaptureParser().parse("Worked on unknown for 20 minutes",ZonedDateTime.parse("2026-09-12T10:00:00Z"),listOf(project)).projectId)
    }
    @Test fun pinWinsAndCompletionInvalidatesFocus() {
        val pinned=Task(title="Synthetic pinned task",pinnedFocus=true)
        val urgent=Task(title="Synthetic urgent task",priority=Priority.URGENT,dueLocalDate="2026-09-01")
        val engine=PersonalIntelligence();val now=LocalDate.parse("2026-09-12")
        assertEquals(pinned.id,engine.focus(listOf(urgent,pinned),emptyList(),now)?.task?.id)
        assertEquals(urgent.id,engine.focus(listOf(urgent,pinned.copy(status=TaskStatus.COMPLETED)),emptyList(),now)?.task?.id)
        assertNull(engine.focus(emptyList(),emptyList(),now))
    }
    @Test fun quietWeekHasNoInventedEvidence() {
        val result=PersonalIntelligence().week(LocalDate.parse("2026-09-13"),emptyList(),emptyList(),emptyList(),emptyList(),emptyList(),ZoneId.of("UTC"))
        assertEquals("2026-09-07",result.start.toString());assertEquals(0,result.completedTasks);assertEquals(0,result.minutes)
    }
    @Test fun distantYearlyRuleHasNoArtificialEightYearCutoff() {
        val r=RecurrenceRule(frequency=Frequency.YEARLY,interval=10,startLocalDate="2026-09-13")
        assertEquals(LocalDate.parse("2036-09-13"),recurrence.next(r,LocalDate.parse("2026-09-13")))
    }
    @Test fun unrelatedExceptionDoesNotSkipThisSeries() {
        val r=RecurrenceRule(startLocalDate="2026-09-13")
        val other=RecurrenceException(recurrenceRuleId="other",occurrenceLocalDate="2026-09-14")
        assertEquals(LocalDate.parse("2026-09-14"),recurrence.next(r,LocalDate.parse("2026-09-13"),listOf(other)))
    }

}
