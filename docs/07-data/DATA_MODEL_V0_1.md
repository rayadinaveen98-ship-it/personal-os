# Personal OS — Data Model v0.1

**Status:** Working draft — domain model direction, exact Room schema not yet frozen

The data model must preserve the user's personal history safely while supporting Today, Plan, Capture, Journey, Search, Reviews and future intelligence.

## 1. Principles
- local-first
- user-owned data
- stable IDs
- explicit relationships
- truthful timestamps
- safe migrations
- archive over destructive delete where history matters
- no UI-only fake state stored as personal history
- entities designed for search/export/restore

## 2. Persistence split
### Room / SQLite
Use for structured durable domain data and history.

### DataStore
Use for lightweight preferences such as:
- onboarding complete
- preferred name
- theme mode
- Morning Brief enabled/time
- Evening Reflection enabled/time
- companion visibility
- app-lock preferences that are safe to store there

Sensitive cryptographic secrets must use appropriate Android secure storage, not plain DataStore.

## 3. Core entities

### LifeArea
Working fields:
- id: String/UUID
- name
- description optional
- iconKey optional
- accentKey optional
- status: active / archived
- createdAt
- updatedAt

### Goal
- id
- title
- whyItMatters optional
- successDefinition optional
- status: active / paused / achieved / archived
- targetDate optional
- measurementType optional
- currentValue optional
- targetValue optional
- unit optional
- lifeAreaId optional
- createdAt / updatedAt / achievedAt optional

### Project
- id
- title
- description optional
- status: active / paused / completed / archived
- lifeAreaId optional
- goalId optional
- targetDate optional
- nextActionTaskId optional
- createdAt / updatedAt / completedAt optional

### Milestone
- id
- title
- projectId optional
- goalId optional
- orderIndex
- targetDate optional
- completedAt optional
- createdAt / updatedAt

At least one of projectId/goalId must be valid according to domain rule.

### Task
- id
- title
- notes optional
- status: open / completed / cancelled
- priority: low / normal / high / urgent
- dueAt optional
- reminderAt optional
- reminderState optional
- recurrenceRule optional
- projectId optional
- goalId optional
- lifeAreaId optional
- parentTaskId optional
- orderIndex optional
- pinnedFocus flag or separate focus relation
- createdAt / updatedAt / completedAt optional

### Habit
- id
- title
- description optional
- status: active / paused / archived
- scheduleRule
- targetType optional
- targetValue optional
- unit optional
- preferredTime optional
- reminderAt/time rule optional
- goalId optional
- lifeAreaId optional
- createdAt / updatedAt

### HabitEvent
- id
- habitId
- eventDate/localDate
- value optional
- state: completed / skipped / partial if supported
- createdAt / updatedAt

Historical events must remain stable if habit schedule later changes.

### Hobby
- id
- title
- description optional
- status
- lifeAreaId optional
- createdAt / updatedAt

### Skill
- id
- title
- description optional
- status
- hobbyId optional
- lifeAreaId optional
- currentFocus optional
- createdAt / updatedAt

### Session
- id
- title
- occurredAt
- durationMinutes optional
- notes optional
- hobbyId optional
- skillId optional
- projectId optional
- lifeAreaId optional
- createdAt / updatedAt

### JournalEntry
- id
- title optional
- body
- entryDate/localDate
- occurredAt/createdAt
- updatedAt
- reflectionType optional: free / daily / weekly-linked
- lifeAreaId optional
- projectId optional
- goalId optional

### Idea
- id
- title optional
- body
- status: inbox / active / archived / converted
- projectId optional
- lifeAreaId optional
- createdAt / updatedAt

### Memory
Working V1 choice still open:
A) first-class Memory entity, or
B) explicit saved-memory relation to Journal/Session/Event.

If first-class:
- id
- title
- body optional
- memoryDate
- sourceType/sourceId optional
- createdAt / updatedAt

### WeeklyReview
- id
- periodStartLocalDate
- periodEndLocalDate
- reflectionBody optional
- primaryFocusId/type optional
- createdAt / updatedAt

Carry-forward decisions should modify referenced entities rather than be stored only as prose.

## 4. Timeline events
Journey needs cross-domain chronology.

Preferred approach: derive most timeline events from authoritative entities/timestamps rather than duplicate every action into an event table.

A lightweight `ActivityEvent` / audit-domain event table may still be useful for events that are not otherwise reconstructable, such as:
- project status change
- milestone achieved
- explicit focus changed

Before introducing it, define which events need immutable historical capture.

## 5. Tags and links
### Tag
- id
- name
- normalizedName

### EntityTagCrossRef
- tagId
- entityType
- entityId

### EntityLink
Optional generic relation table for links not modeled directly:
- id
- fromType/fromId
- toType/toId
- relationType
- createdAt

Use generic relations carefully; direct foreign keys are preferred for common core relationships.

## 6. Attachments
If V1 attachments ship:
### Attachment
- id
- ownerType
- ownerId
- uri/storagePath
- mimeType
- displayName optional
- createdAt
- size optional
- checksum optional

Backup/export must include attachment metadata and bytes or a documented portable representation.

## 7. Preferences
DataStore keys should include at minimum:
- onboardingComplete
- preferredName
- themeMode
- morningBriefEnabled
- morningBriefTime optional
- eveningReflectionEnabled
- eveningReflectionTime optional
- companionEnabled
- reducedMotionOverride optional

Life Areas must not be stored only as string sets if they are first-class entities.

## 8. Relationships
Working relationship map:
- LifeArea 1→N Goal
- LifeArea 1→N Project
- LifeArea 1→N Habit/Hobby/Skill/Task/Session optional
- Goal 1→N Project
- Goal 1→N Habit optional
- Goal 1→N Milestone optional
- Project 1→N Task
- Project 1→N Milestone
- Project 1→N Session
- Task 1→N subtasks via parentTaskId
- Hobby 1→N Skill
- Hobby/Skill 1→N Session

Not every relationship should be mandatory.

## 9. IDs
Use stable UUID/string IDs generated once at entity creation.
Do not use list position as identity.

## 10. Time handling
Store timestamps in an unambiguous instant representation (epoch millis or Instant-compatible field) plus local-date fields where calendar grouping semantics require them.

Important:
- Today/Week/Review grouping follows user's local timezone
- historical entry date should not shift because device timezone changes
- reminder scheduling uses exact intended instant after resolving local date/time

Final conversion policy must be documented in Android architecture.

## 11. Search
Use Room FTS or another local index for searchable text domains.

Candidate indexed content:
- Task title/notes
- Project title/description
- Goal title/why/success definition
- Journal title/body
- Idea title/body
- Hobby/Skill title/notes
- Review text

Index updates must track edits/deletes reliably.

## 12. Archive vs delete
Archive preserves history for:
- projects
- goals
- habits
- hobbies/skills

Delete may be allowed for user mistakes but must handle links safely.

Tasks/journal/ideas may support delete with confirmation/undo policy.

## 13. Migration policy
Production database must have explicit migrations.

Forbidden:
- `fallbackToDestructiveMigration()` in production
- resetting DB because schema changed

Every schema change after V1 data exists requires:
- migration
- migration test
- backup compatibility consideration

## 14. Backup/export format
Preferred V1 direction:
- versioned export manifest
- structured JSON for entities/relationships/preferences
- attachment directory if supported
- export version number
- app/database schema version

Restore must validate before replacing live data.

## 15. Referential integrity
Rules:
- deleting/archive parent must not orphan required children
- links use foreign keys or verified references where practical
- archive should generally preserve children
- hard delete requires explicit cascade/null behavior per entity

## 16. Transaction boundaries
Use Room transactions for multi-step changes such as:
- complete task + reminder cleanup metadata
- project completion + milestone/task updates if product behavior requires it
- restore import
- conversion/link flows where consistency matters

## 17. Data truthfulness
Derived metrics are queries/calculations over authoritative rows.
Never persist arbitrary UI progress values unless they are explicit user-entered measurements.

## 18. Open decisions before schema freeze
1. First-class Memory entity vs saved-memory relation.
2. Generic ActivityEvent/event log scope.
3. Recurrence schema representation.
4. Reminder delivery-state fields.
5. Tag/relation model exact scope.
6. Attachments V1 subset.
7. Soft-delete vs hard-delete policy per entity.
8. Whether LifeArea can nest.
9. WeeklyReview normalized fields beyond reflection body.
