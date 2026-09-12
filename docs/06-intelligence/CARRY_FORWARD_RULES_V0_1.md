# Personal OS — Carry-Forward Rules v0.1

**Status:** Working intelligence specification.

## Principle
Personal OS should help unfinished intent survive without turning yesterday into guilt.

Carry-forward must be explicit, explainable and conservative.

## Tasks
### Due yesterday and still open
Today may surface the task as overdue.

Do not automatically change its due date.

Available user actions:
- Complete
- Reschedule
- Keep overdue
- Cancel

### No due date, manually selected/pinned for yesterday
If still open:
- keep the task open
- remove yesterday-specific selection after the day boundary unless explicitly pinned as ongoing focus
- Today may offer `Continue` if recent activity/context makes it relevant

### User-selected Today item not completed
At next day start, it becomes a decision candidate:
> You left this open yesterday. Keep it in focus today?

Default behavior should not silently move the due date.

## Recurring tasks
Use recurrence rules. Do not carry every missed occurrence forward.
Surface the latest unresolved occurrence according to the recurrence spec.

## Habits/routines
Never carry a missed habit as overdue debt.
The historical day remains missed/unrecorded and the new scheduled day is treated independently.

## Project next actions
An unfinished project task remains available as the project next-action candidate.
If the project has had no activity for a configurable meaningful period, Personal Intelligence may surface:
> Pick up where you left off?

It must not declare that the project is failing or neglected in judgmental language.

## Reminders
Expired reminder alarms are not simply moved into the future.
If the owning task is still open, the task remains actionable and the reminder may show as missed/delivered history.
The user chooses a new reminder time when needed.

## Morning Brief
Morning Brief may say:
- `1 item is still open from yesterday`
- `2 things are due today`

It must not say work was carried forward if the app silently changed nothing.

## Weekly Review
Unfinished items are grouped for decision, not bulk-rescheduled automatically.
Actions:
- keep active
- reschedule
- move to project backlog/no-date
- cancel

## Focus selection
A manually pinned ongoing focus can persist across days until:
- completed
- unpinned
- cancelled
- its source entity becomes archived/invalid

A day-only selection expires at local day boundary.

## Time boundary
Day transitions use user's current local timezone while preserving historical local dates.

## Acceptance
- overdue task remains overdue unless user changes it
- yesterday-selected no-date task is not silently assigned today
- missed habits do not create overdue copies
- recurring tasks do not multiply debt
- pinned ongoing focus persists appropriately
- Morning Brief describes actual state accurately
