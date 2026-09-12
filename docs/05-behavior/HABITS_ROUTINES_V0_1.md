# Personal OS — Habits & Routines Behavior v0.1

**Status:** Working draft

Habits and routines should support consistency without guilt-driven streak mechanics.

## 1. Definitions
### Habit
A behavior the user wants to repeat over time.
Examples:
- walk 8,000 steps
- read 20 minutes
- practice piano

### Routine
A repeatable group or sequence tied to a context/time.
Examples:
- morning reset
- evening shutdown
- weekly planning

V1 may implement Habits first and treat grouped Routines as a light extension if scope becomes large.

## 2. Working habit fields
Potential fields:
- id
- title
- description optional
- status: active / paused / archived
- frequency rule
- target count/duration/value optional
- preferred time/window optional
- reminder optional
- lifeAreaId optional
- goalId optional
- createdAt
- startDate

Completion/event history should live separately from habit definition.

## 3. Frequency
Supported V1 candidates:
- every day
- selected weekdays
- N times per week
- selected dates / simple interval if reliable

Avoid building a huge recurrence engine before the common cases are excellent.

## 4. Daily state
A habit due today can be:
- not started
- completed
- partially logged where measurement applies
- skipped intentionally if skip semantics are included

Do not equate a missed day with failure.

## 5. Completion
Completing a habit:
- creates a real habit event/history record
- updates Today/Plan state
- may contribute to goal evidence if explicitly linked
- can produce subtle completion feedback

## 6. Streak philosophy
Streaks are optional, not the emotional center of Personal OS.

If streaks are shown:
- they must be mathematically correct
- they must never use shame language
- missing a day should not visually punish the user

Alternative preferred summaries:
- `4 of 5 planned days this week`
- `12 sessions this month`
- `Consistent for 3 weeks`

## 7. Pause
User can pause a habit without losing history.
Paused habits:
- stop appearing as due
- stop reminders
- remain in history/search

## 8. Edit
Editing schedule should preserve past completion history.
Future expected occurrences follow the new rule.

## 9. Reminders
Habit reminders use the same reliable reminder infrastructure as tasks.
Do not build a separate unreliable notification system.

## 10. Routine grouping
If routines ship in V1:
- routine contains ordered habit/action steps
- user can run routine from one screen
- each step remains truthful and persisted

Do not use routines merely as static checklist templates without state.

## 11. Today/Plan behavior
Only habits genuinely due/relevant today should appear.

Today should not be overwhelmed by all active habits.
Personal Intelligence may select a small relevant subset or one routine context.

## 12. Goal linking
A habit may support a goal.
The goal can show real habit evidence such as:
- `8 sessions this month`

Do not convert habit events into arbitrary goal percentage unless measurement supports it.

## 13. Companion behavior
Suitable:
- subtle happy reaction on completion
- calm encouragement after intentionally resuming a paused habit

Not suitable:
- sad/disappointed character for missed days
- guilt animations

## 14. Empty state
> **No habits yet.**
> Add one when there's something you want to practice consistently—not perfectly.

## 15. Acceptance criteria
- create/edit/pause/archive works
- schedule calculation is deterministic
- completion history is durable
- reminders work through shared infrastructure
- no destructive schedule edit
- Today/Plan show only real due state
- history survives pauses
- no punitive streak UX
