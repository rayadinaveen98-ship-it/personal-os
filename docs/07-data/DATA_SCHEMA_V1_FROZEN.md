# Personal OS — V1 Data Schema Freeze

**Status:** FROZEN for initial implementation  
**Date:** 2026-09-12

This file finalizes `DATA_SCHEMA_FREEZE_CANDIDATE_V0_2.md`. The candidate's entity/field definitions remain authoritative unless overridden below.

## 1. Frozen entity set

V1 ships with:
- LifeArea
- Goal
- Project
- Milestone
- Task
- Reminder
- RecurrenceRule
- RecurrenceException
- Habit
- HabitEvent
- Hobby
- Skill
- Session
- JournalEntry
- Idea
- Memory
- Chapter
- ChapterItem
- DailyReview
- WeeklyReview
- Tag
- EntityTagCrossRef
- EntityLink
- Attachment
- TimelineEvent

`RecurrenceException` is **included in V1**, not deferred. It supports skip/override-one-occurrence behavior without mutating the entire series.

## 2. Storage primitives

- Durable IDs: UUID/string
- Instants: INTEGER epoch millis
- Calendar-local dates: ISO `YYYY-MM-DD` TEXT
- Time-of-day recurrence values: integer minutes after midnight
- Enums: persisted as stable TEXT names through explicit Room TypeConverters
- Booleans: Room boolean/integer representation

Enum persisted names become migration-sensitive API. Do not casually rename persisted enum constants after release.

## 3. Foreign-key policy

### Owned-child relationships — CASCADE
Use `ON DELETE CASCADE` where the child has no independent meaning:
- HabitEvent → Habit
- RecurrenceException → RecurrenceRule
- ChapterItem → Chapter
- EntityTagCrossRef → Tag

### Optional context relationships — SET NULL
Use `ON DELETE SET NULL` where the child record remains meaningful without the parent:
- Task → Project / Goal / LifeArea / parent Task
- Project → Goal / LifeArea
- Goal → LifeArea
- Habit → Goal / LifeArea
- Hobby → LifeArea
- Skill → Hobby / LifeArea
- Session → Hobby / Skill / Project / LifeArea
- JournalEntry → Project / Goal / LifeArea
- Idea → Project / LifeArea
- Memory → Project / LifeArea
- Chapter → LifeArea

Long-lived structures are normally archived rather than hard-deleted, so parent deletion should be rare.

### Polymorphic relationships
Reminder owner, Attachment owner, EntityLink endpoints, EntityTagCrossRef entity endpoint, ChapterItem entity endpoint and TimelineEvent source are validated in repository/domain code because Room cannot enforce polymorphic foreign keys safely.

## 4. Project next action

Do **not** store a circular `Project.nextActionTaskId` in schema V1.

Resolve project next action from real Task data using deterministic rules/pinning. If implementation performance later requires denormalization, it must be documented as a compatible schema change.

## 5. Search / FTS architecture

V1 uses a consolidated index layer rather than N unrelated search implementations.

### SearchDocument
Normal Room table:
- rowId: auto-generated Long PK
- entityType: TEXT
- entityId: String
- title: String nullable
- body: String nullable
- keywords: String nullable
- updatedAt: epoch millis

Unique index:
- `(entityType, entityId)`

### SearchDocumentFts
Use Room FTS4/SQLite FTS over the searchable text fields with `SearchDocument` as the content/source table where practical.

Indexed domains:
- LifeArea
- Goal
- Project
- Task
- JournalEntry
- Idea
- Memory
- Chapter
- Hobby
- Skill
- Session
- WeeklyReview

Search result mapping always returns `entityType + entityId` for exact deep-link navigation.

Search index updates must happen through repository/domain write paths in the same logical transaction when possible. Rebuild-index utility is allowed for recovery/testing.

## 6. Index freeze

Required indexes include:
- Task: status, dueAt, dueLocalDate, projectId, goalId, lifeAreaId, recurrenceRuleId, pinnedFocus
- Reminder: state, triggerAt, recurrenceRuleId, ownerType+ownerId
- Project/Goal/Habit/Hobby/Skill: status, lifeAreaId
- Milestone: projectId, goalId, status, targetDate
- HabitEvent: unique `(habitId, localDate)`
- Session: localDate, projectId, hobbyId, skillId, lifeAreaId
- JournalEntry: entryLocalDate, projectId, goalId, lifeAreaId
- Idea/Memory/Chapter: status/date/context fields used in lists
- TimelineEvent: localDate, occurredAt, sourceType+sourceId
- Tag: unique normalizedName
- SearchDocument: unique `(entityType, entityId)`

Avoid speculative indexes that are not backed by a real V1 query.

## 7. Recurrence freeze

V1 supports:
- daily
- weekly
- monthly
- yearly
- interval > 1
- weekday selection
- day-of-month
- optional end date
- optional occurrence count limit
- one-occurrence skip/override via RecurrenceException

V1 does not need full RFC5545/complex BYSETPOS-style recurrence.

Recurring habits do not create overdue debt. Recurring tasks surface the latest unresolved occurrence according to the recurrence behavior specification.

## 8. Reminder model freeze

Reminder remains first-class and separate from Task.

Reminder delivery truth must distinguish:
- persisted reminder record
- scheduled Android alarm/work state
- delivered/cancelled/skipped state

A saved record must not imply notification delivery succeeded if permission/exact-alarm capability blocked scheduling.

## 9. Timeline freeze

Do not duplicate ordinary source rows into TimelineEvent merely to populate Journey.

Derive ordinary journal/session/completed-task history directly from authoritative tables.

TimelineEvent exists for historical facts that edits would otherwise erase, such as:
- task completed / reopened
- project status changed
- milestone achieved
- focus pinned / cleared
- idea converted

## 10. Archive/delete freeze

Archive by default:
- LifeArea
- Goal
- Project
- Habit
- Hobby
- Skill
- Chapter

Hard delete with confirmation allowed:
- Task
- JournalEntry
- Idea
- Memory
- Session
- attachments/attachment links

Before hard delete, clean reminders/alarms and optional link/index records transactionally.

## 11. Transaction boundaries

Required transactional operations:
- task complete/reopen + TimelineEvent + reminder/alarm reconciliation
- reminder create/update/delete + scheduling metadata
- idea conversion + target creation/link + source update + TimelineEvent
- chapter membership reorder
- backup restore/import
- hard delete with link/search/alarm cleanup

## 12. Migrations

Room DB version starts at `1` for the clean rebuild.

For every production schema bump:
- explicit Migration object
- migration fixture from previous released schema
- migration test
- data verification after open

`fallbackToDestructiveMigration()` is forbidden in production.

## 13. Implementation-change policy

Astra may make naming/mechanical adjustments required by Room/KSP, but must not remove entities, collapse important domains, replace explicit relationships with an opaque JSON blob, or redesign the schema wholesale without documenting a genuine blocker and compatible alternative in the Decision Log.
