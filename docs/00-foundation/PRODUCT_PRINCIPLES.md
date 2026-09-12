# Personal OS — Product Principles

These principles are constraints, not marketing language. They should be used to resolve design, product, and engineering decisions throughout the project.

## 1. Truth over decoration

Personal OS must never fabricate personal progress, achievements, journal history, activity, streaks, life metrics, project movement, or memories.

If the user has no data, show a thoughtful empty state.

## 2. Personal before productive

The product should help the user build, remember, learn, reflect, and organize. Productivity is one part of life, not the definition of life.

## 3. Capture should feel instant

The universal capture path should be available from anywhere in the primary app and should require minimal effort before the thought is safely stored.

## 4. Today should reduce cognitive load

Today must not become an everything-dashboard. It should surface only the most relevant current context and give the user a clear next step.

## 5. The system should remember context

When the user returns to a project, skill, goal, or life area, Personal OS should help them recover where they left off.

## 6. Reflection is first-class

Diary entries, memories, learning notes, milestones, and meaningful moments deserve the same product quality as task management.

## 7. Calm intelligence

Personal OS should help without constantly announcing that it is intelligent. Useful prioritization and context are better than flashy AI labels.

## 8. Explainable suggestions

The user should be able to understand why something is being surfaced. Avoid opaque life scores or mysterious rankings.

## 9. No shame mechanics

Avoid punitive streaks, guilt copy, failure colors, or language implying moral failure because a user skipped a habit, task, or reflection.

## 10. Local-first reliability

Core functionality must work offline and must not depend on a paid AI API, cloud connection, or account login.

## 11. Data preservation is sacred

Journal entries, memories, tasks, projects, and history must survive version upgrades. Destructive database migration is not acceptable for production.

## 12. Contextual permissions only

Ask for microphone, notifications, exact alarms, biometrics, or storage access only when the user invokes a feature that needs them or when the value is immediately understandable.

## 13. Every visible interaction must be truthful

A control that looks tappable must work. A feature that does not exist must not be represented as if it does.

## 14. Progressive complexity

A new user should not face the full database on day one. Advanced depth should emerge naturally as the user creates projects, goals, history, and routines.

## 15. One coherent system

Do not build separate mini-apps for tasks, journal, habits, skills, and projects. Their relationships are part of the product value.

## 16. Design must be production-realistic

All approved UI should be realistically implementable in Jetpack Compose without relying on impossible image-generation effects, fake materials, or decorative interactions that cannot be reproduced faithfully.

## 17. Empty states are product states

Every major screen must specify meaningful zero-data behavior. Empty states should teach, invite, or orient without pretending data exists.

## 18. Notifications serve intent, not engagement

Do not use notifications to manufacture retention. Notifications should correspond to user-created commitments or explicitly enabled daily rhythms.

## 19. Details matter because trust matters

Dates, counts, reminders, completion state, project relationships, and history must stay consistent across Today, Plan, Journey, Search, and detail screens.

## 20. Completion means behavior, not compilation

A feature is complete only when:

- its visible controls work
- its data persists
- expected empty/error states work
- navigation is coherent
- relevant permissions are handled
- unit/integration tests cover the important behavior
- the feature survives restart where applicable
- the UI matches the approved specification closely

A successful APK build alone is not feature completion.
