# Personal OS — V1 Data Schema Freeze Candidate v0.2

**Status:** Candidate to freeze after final review. This replaces major open choices from `DATA_MODEL_V0_1.md` but does not delete that design-history document.

## 1. Schema philosophy
The V1 Room schema should be explicit, understandable, migration-safe and not over-generalized.

Rules:
- UUID/String IDs for durable entities
- epoch millis for instants
- ISO local-date strings (`YYYY-MM-DD`) where calendar grouping must not shift with timezone
- explicit status fields
- archive long-lived structures instead of deleting history
- direct foreign keys for common relationships
- generic relations only where the domain is genuinely cross-cutting
- no arbitrary progress percentages

## 2. V1 entities

### LifeArea
Fields:
- id
- name
- description nullable
- iconKey nullable
- accentKey nullable
- status: ACTIVE / ARCHIVED
- createdAt
- updatedAt

V1 uses one optional `lifeAreaId` as the primary Life Area on core records. Multi-area graph relationships may be added later through explicit links; V1 should not make every query depend on polymorphic cross-reference tables.

### Goal
- id
- title
- whyItMatters nullable
- successDefinition nullable
- status: ACTIVE / PAUSED / ACHIEVED / ARCHIVED
- targetDate nullable
- measurementType: NONE / COUNT / DURATION / NUMBER / PERCENT_EXPLICIT
- currentValue nullable
- targetValue nullable
- unit nullable
- lifeAreaId nullable FK
- createdAt
- updatedAt
- achievedAt nullable

`PERCENT_EXPLICIT` is only allowed when the user deliberately defines a percentage-based measure; Personal OS must not manufacture one.

### Project
- id
- title
- description nullable
- status: ACTIVE / PAUSED / COMPLETED / ARCHIVED
- lifeAreaId nullable FK
- goalId nullable FK
- targetDate nullable
- createdAt
- updatedAt
- completedAt nullable

The current next action is resolved from Task data/pinning rather than stored as a fragile circular `nextActionTaskId` unless implementation review proves a direct field is safer.

### Milestone
- id
- title
- projectId nullable FK
- goalId nullable FK
- orderIndex
- targetDate nullable
- status: OPEN / COMPLETED / SKIPPED
- completedAt nullable
- createdAt
- updatedAt

Domain validation requires projectId or goalId.

### Task
- id
- title
- notes nullable
- status: OPEN / COMPLETED / CANCELLED
- priority: LOW / NORMAL / HIGH / URGENT
- dueAt nullable
- dueLocalDate nullable
- projectId nullable FK
- goalId nullable FK
- lifeAreaId nullable FK
- parentTaskId nullable self-FK
- recurrenceRuleId nullable FK
- pinnedFocus boolean
- orderIndex nullable
- createdAt
- updatedAt
- completedAt nullable

A reminder is not embedded in Task; reminders are first-class scheduling records.

### Reminder
- id
- ownerType: TASK / HABIT / ROUTINE / JOURNAL_PROMPT / STANDALONE
- ownerId nullable
- title
- triggerAt nullable for concrete occurrence
- localTimeMinutes nullable for recurring semantics
- recurrenceRuleId nullable FK
- state: SCHEDULED / DELIVERED / CANCELLED / SKIPPED
- exactRequired boolean
- createdAt
- updatedAt
- deliveredAt nullable

A standalone reminder is allowed. Owner links are validated in repository/domain code because Room cannot foreign-key a polymorphic owner safely.

### RecurrenceRule
- id
- frequency: DAILY / WEEKLY / MONTHLY / YEARLY
- interval: Int >= 1
- weekdaysMask nullable
- dayOfMonth nullable
- monthOfYear nullable
- localTimeMinutes nullable
- startLocalDate
- endLocalDate nullable
- occurrenceCountLimit nullable
- createdAt
- updatedAt

Complex exceptions are not in V1.

### RecurrenceException
Optional but recommended for V1 to support editing/skipping one occurrence without mutating the series:
- id
- recurrenceRuleId FK
- occurrenceLocalDate
- type: SKIP / OVERRIDE
- overrideDueAt nullable
- overrideTitle nullable
- createdAt

### Habit
- id
- title
- description nullable
- status: ACTIVE / PAUSED / ARCHIVED
- scheduleRuleId FK to RecurrenceRule
- targetType: CHECK / COUNT / DURATION
- targetValue nullable
- unit nullable
- preferredTimeMinutes nullable
- goalId nullable FK
- lifeAreaId nullable FK
- createdAt
- updatedAt

### HabitEvent
- id
- habitId FK
- localDate
- state: COMPLETED / SKIPPED / PARTIAL
- value nullable
- note nullable
- createdAt
- updatedAt

Unique index on `(habitId, localDate)` unless multiple events/day are intentionally supported later.

### Hobby
- id
- title
- description nullable
- status: ACTIVE / PAUSED / ARCHIVED
- lifeAreaId nullable FK
- createdAt
- updatedAt

### Skill
- id
- title
- description nullable
- status: ACTIVE / PAUSED / ARCHIVED
- hobbyId nullable FK
- lifeAreaId nullable FK
- currentFocus nullable
- createdAt
- updatedAt

### Session
- id
- title
- occurredAt
- localDate
- durationMinutes nullable
- notes nullable
- hobbyId nullable FK
- skillId nullable FK
- projectId nullable FK
- lifeAreaId nullable FK
- createdAt
- updatedAt

### JournalEntry
- id
- title nullable
- body
- entryLocalDate
- occurredAt
- reflectionType: FREE / DAILY / EVENING / WEEKLY_LINKED
- lifeAreaId nullable FK
- projectId nullable FK
- goalId nullable FK
- createdAt
- updatedAt

### Idea
- id
- title nullable
- body
- status: INBOX / ACTIVE / ARCHIVED / CONVERTED
- projectId nullable FK
- lifeAreaId nullable FK
- convertedToType nullable
- convertedToId nullable
- createdAt
- updatedAt

### Memory
Memory is **first-class in V1**.
- id
- title
- body nullable
- memoryLocalDate
- occurredAt nullable
- sourceType nullable
- sourceId nullable
- lifeAreaId nullable FK
- projectId nullable FK
- isFavorite boolean
- createdAt
- updatedAt

Deleting a Memory must not cascade-delete the source record.

### Chapter
- id
- title
- description nullable
- startLocalDate
- endLocalDate nullable
- status: ACTIVE / COMPLETED / ARCHIVED
- lifeAreaId nullable FK
- createdAt
- updatedAt

### ChapterItem
- id
- chapterId FK
- entityType: MEMORY / JOURNAL / SESSION / PROJECT / GOAL / MILESTONE
- entityId
- orderIndex nullable
- createdAt

Chapter membership is user-controlled.

### DailyReview
- id
- localDate unique
- reflectionBody nullable
- highlight nullable
- createdAt
- updatedAt

### WeeklyReview
- id
- periodStartLocalDate unique
- periodEndLocalDate
- reflectionBody nullable
- highlight nullable
- createdAt
- updatedAt

Carry-forward decisions should update real source entities, not live only inside review prose.

### Tag
- id
- name
- normalizedName unique
- createdAt

### EntityTagCrossRef
- tagId FK
- entityType
- entityId

### EntityLink
Use only for optional cross-domain relationships that are not common enough for direct FKs:
- id
- fromType
- fromId
- toType
- toId
- relationType
- createdAt

### Attachment
- id
- ownerType
- ownerId
- uri
- storageMode: EXTERNAL_URI / PRIVATE_COPY
- mimeType
- displayName nullable
- sizeBytes nullable
- checksum nullable
- createdAt

### TimelineEvent
V1 includes a lightweight immutable event table only for historical facts that are not reliably reconstructable after edits.

Fields:
- id
- eventType
- occurredAt
- localDate
- sourceType nullable
- sourceId nullable
- titleSnapshot nullable
- metadataJson nullable
- createdAt

Examples to record:
- task completed/reopened
- project status changed
- milestone achieved
- explicit focus pinned/unpinned
- idea converted

Do not duplicate journal/session/task rows merely to populate Journey; derive ordinary timeline items directly from authoritative entities.

## 3. DataStore preferences
At minimum:
- onboardingComplete
- preferredName
- themeMode: SYSTEM / LIGHT / DARK
- morningBriefEnabled
- morningBriefTimeMinutes nullable
- eveningReflectionEnabled
- eveningReflectionTimeMinutes nullable
- companionEnabled
- reducedMotionEnabled / system-follow mode
- appLockEnabled
- appLockTimeout
- notificationPrivacyMode
- lastSuccessfulBackupAt nullable

Do not store first-class Life Areas as string sets.

## 4. Search
Use Room FTS tables or a consolidated FTS index for:
- Task title/notes
- Project title/description
- Goal text
- Journal title/body
- Idea title/body
- Memory title/body
- Chapter title/description
- Hobby/Skill title/description
- Review text

Search results must retain source ID/type for detail navigation.

## 5. Hard-delete / archive policy
Archive by default:
- LifeArea
- Goal
- Project
- Habit
- Hobby
- Skill
- Chapter

Hard delete permitted with confirmation:
- Task
- JournalEntry
- Idea
- Memory
- Session
- Attachment relationship

Before hard delete, repository/domain layer must define what happens to optional links and immutable timeline snapshots.

## 6. Indexes
At minimum index:
- statuses frequently queried by Today/Plan
- dueAt / dueLocalDate
- localDate fields used by Journey
- projectId / goalId / lifeAreaId
- recurrenceRuleId
- sourceType/sourceId where used
- tag normalizedName

Avoid premature indexing on every column.

## 7. Transactions
Use transactions for:
- task completion/reopen + TimelineEvent + alarm cleanup/reschedule
- reminder creation/update + alarm scheduling metadata
- idea conversion + source status + target entity + TimelineEvent
- backup restore/import
- chapter item reorder/update if multiple tables change

## 8. Migration policy
Database version starts at `1` for the clean rebuild.

Every production schema bump requires:
- explicit Room migration
- migration test fixture from previous schema
- successful open and data-verification test

`fallbackToDestructiveMigration()` is forbidden outside disposable developer-only experiments.

## 9. Remaining review before freeze
Only implementation-level review remains:
- exact SQL/Room types for enums
- KSP converters for local date/time semantics
- exact FTS architecture
- foreign-key delete actions
- whether `RecurrenceException` ships in V1 or is deferred with equivalent one-occurrence behavior

Astra must not redesign the domain model wholesale without documenting a blocker and proposing a compatible alternative.
