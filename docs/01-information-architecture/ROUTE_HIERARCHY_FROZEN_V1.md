# Personal OS — Android Route Hierarchy V1

**Status:** FROZEN for V1 implementation  
**Date:** 2026-09-12

This resolves the remaining navigation ambiguity from `INFORMATION_ARCHITECTURE_V0_1.md`. Astra may implement these destinations with Navigation Compose typed destinations or equivalent safe routing, but the user-facing hierarchy and semantics must remain the same.

## 1. Primary graph

Persistent primary destinations:
- `today`
- `plan`
- `capture`
- `journey`
- `me`

Bottom navigation order:
1. Today
2. Plan
3. central Capture
4. Journey
5. Me

Primary-tab state should be preserved when switching tabs where practical.

## 2. First-run graph

- `welcome`
- `setup/intent`
- `setup/life-areas`
- `setup/first-item`
- `setup/personalize`
- `setup/complete`

After successful setup completion, clear the onboarding graph from the back stack and enter `today`.

## 3. Today routes

- `today`
- `today?date={localDate}` only if implementation needs explicit non-today preview; normal Today opens current local date

Specific cards deep-link to their exact entity detail destination rather than generic tabs.

## 4. Plan routes

The V1 Plan segmented control is locked to:
- `plan?mode=today`
- `plan?mode=week`
- `plan?mode=projects`

There is no separate fourth `Routines` primary segment in V1. Habits/routines appear contextually in Today/Week and through dedicated child lists/details.

Child destinations:
- `tasks`
- `task/{taskId}`
- `task/create`
- `task/{taskId}/edit`
- `reminder/{reminderId}`
- `projects`
- `project/{projectId}`
- `project/create`
- `project/{projectId}/edit`
- `goals`
- `goal/{goalId}`
- `goal/create`
- `goal/{goalId}/edit`
- `habits`
- `habit/{habitId}`
- `habit/create`
- `habit/{habitId}/edit`
- `milestone/{milestoneId}`
- `milestone/{milestoneId}/edit`

## 5. Capture routes

Single underlying Capture architecture with contextual modes:
- `capture?mode=universal`
- `capture?mode=task`
- `capture?mode=reminder`
- `capture?mode=journal`
- `capture?mode=evening-reflection`
- `capture?mode=session`
- `capture?mode=memory`

Optional context arguments:
- `origin`
- `projectId`
- `goalId`
- `lifeAreaId`
- `selectedLocalDate`

Context may prefill intent/linkage but remains editable unless the initiating workflow requires a fixed parent.

## 6. Journey routes

- `journey?date={localDate}`
- `timeline`
- `journal/{journalId}`
- `journal/{journalId}/edit`
- `memory/{memoryId}`
- `memory/{memoryId}/edit`
- `chapter/{chapterId}`
- `chapter/create`
- `chapter/{chapterId}/edit`
- `idea/{ideaId}`
- `idea/{ideaId}/edit`
- `session/{sessionId}`
- `session/{sessionId}/edit`
- `weekly-review/{periodStartLocalDate}`

Completed tasks may appear in Journey as real derived history. On dense days they may be summarized visually, but the underlying facts remain traceable.

## 7. Me routes

- `me`
- `life-areas`
- `life-area/{lifeAreaId}`
- `life-area/create`
- `life-area/{lifeAreaId}/edit`
- `hobbies`
- `hobby/{hobbyId}`
- `hobby/create`
- `hobby/{hobbyId}/edit`
- `skills`
- `skill/{skillId}`
- `skill/create`
- `skill/{skillId}/edit`

Hobby and Skill remain separate V1 object types. A Skill may optionally belong to a Hobby.

## 8. Global routes

- `search`
- `settings`
- `settings/profile`
- `settings/appearance`
- `settings/companion`
- `settings/rhythm`
- `settings/notifications`
- `settings/privacy`
- `settings/app-lock`
- `settings/backup-export`
- `permission-repair/notifications`
- `permission-repair/exact-alarms`

## 9. Resolved IA decisions

1. **Plan modes:** Today / Week / Projects.
2. **Ideas:** no separate top-level Idea Library in V1; discoverable through Search, Journey and linked project/life-area context.
3. **Goals:** primary planning home is Plan; Me shows truthful overview/context.
4. **Hobbies vs Skills:** separate V1 entities and user-facing detail screens.
5. **Life Area Detail:** ships in V1.
6. **Chapters:** explicit user-created/named V1 periods with user-controlled membership; Personal Intelligence may suggest but never silently author one as fact.
7. **Completed tasks in Journey:** allowed as truthful derived history, with summarization on dense days.
8. **Global Search V1:** Task, Project, Goal, JournalEntry, Idea, Memory, Chapter, Hobby, Skill, Session, WeeklyReview and LifeArea. Search results always retain source type/id for exact navigation.

## 10. Back-stack rules

- detail → previous meaningful context
- edit → detail after save/cancel
- create → invoking context after successful creation
- global Capture → originating primary tab/context after successful save
- notification deep link → target detail with a sensible primary parent behind it
- onboarding completion → Today with onboarding removed from history

## 11. Deep-link truthfulness

Every notification/search/timeline/result that visually represents a real entity must resolve to its exact entity detail when that detail exists.

Generic-tab fallback is allowed only when the represented content is a section shortcut rather than a specific record.

## 12. Implementation freedom

Astra may use sealed/serializable typed destinations instead of literal string routes. The literal names above define the semantic graph, not a requirement to use string interpolation internally.
