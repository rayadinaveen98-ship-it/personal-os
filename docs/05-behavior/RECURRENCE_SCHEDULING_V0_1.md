# Personal OS — Recurrence & Scheduling v0.1

**Status:** Working specification; intended to remove implementation ambiguity before Astra handoff.

## 1. Principle
Recurring items represent a user's explicit repeating intent. Personal OS must never silently create an endless stream of future records or punish missed occurrences.

## 2. Supported V1 recurrence
V1 supports:
- daily
- selected weekdays
- weekly on one or more weekdays
- monthly by day-of-month
- yearly by month/day
- custom interval: every N days/weeks/months

Not required in V1:
- natural-language recurrence exceptions as a full calendar engine
- complex RFC recurrence editing UI
- business-calendar rules such as third working day

## 3. Recurrence ownership
A recurrence definition belongs to the source entity (Task, Reminder, Habit/Routine). Occurrences are derived from that definition.

Do not pre-create hundreds of future rows.

## 4. Task occurrence behavior
A recurring task occurrence has its own completion state and occurrence date while retaining a link to the recurrence definition.

Completing today's occurrence must not mark the series complete.

Editing choices when changing a recurring item:
- This occurrence only
- This and future occurrences
- Entire series

V1 may initially support only `This occurrence` and `Entire series` if `This and future` materially increases migration/recurrence complexity, but the UI must not pretend the missing option exists.

## 5. Missed occurrences
Missed recurring tasks are not multiplied indefinitely.

Default behavior:
- Habit/Routine: mark the scheduled day as missed/unrecorded; do not create overdue debt.
- Recurring Task: surface the most recent unresolved occurrence and allow Skip / Complete / Reschedule.
- Reminder-only recurrence: expired reminder is historical; schedule the next valid occurrence.

Never create 14 overdue copies because a user did not open the app for two weeks.

## 6. Skip semantics
`Skip` means the user intentionally does not intend to complete that occurrence.

A skipped occurrence:
- remains in history as skipped where useful
- does not count as completed
- does not generate punitive language
- does not block generation of the next occurrence

## 7. Reminder relationship
A recurring task can optionally have a recurring reminder relative to each occurrence.

Examples:
- Task every weekday at 9:00 AM
- Reminder at occurrence time
- Reminder 30 minutes before

Exact alarm scheduling should schedule only the next necessary alarm(s), then advance/reconcile as occurrences change.

## 8. Time zones and device time changes
Store scheduling semantics in local-time-aware form where appropriate, plus timestamps for concrete occurrences.

On timezone change, device reboot, package replacement, or app launch, reconcile future reminder alarms from persisted recurrence definitions.

## 9. Monthly edge cases
For a day-of-month recurrence such as the 31st:
- default V1 rule: use the last valid day of shorter months
- the UI must explain this when creating/editing the rule

## 10. End conditions
Supported:
- never ends
- ends on date
- optional count limit if straightforward

A user can pause or archive a recurrence without deleting its history.

## 11. Habits and routines
Habit recurrence is schedule evidence, not task debt.

A routine due Monday/Wednesday/Friday should show only the relevant scheduled day. Missing Monday should not create a permanently overdue Monday task on Tuesday.

## 12. Editing and deletion
Deleting a recurrence definition must require clear intent.

Prefer:
- Pause series
- Archive series
- Delete series

Deleting the series must not silently delete historical completion/activity evidence unless the user explicitly chooses destructive history deletion.

## 13. Intelligence behavior
Personal Intelligence may surface a recurring item because it is due or relevant, but it must not alter recurrence rules automatically.

## 14. Acceptance cases
Implementation must test:
- daily task completion creates/advances correctly
- selected weekdays skip non-selected dates
- monthly 31st handles shorter month
- missed habit does not create debt
- recurring reminder survives reboot
- timezone change reconciles the next alarm
- editing one occurrence does not unexpectedly mutate the whole series
- pausing stops future alerts while preserving history
