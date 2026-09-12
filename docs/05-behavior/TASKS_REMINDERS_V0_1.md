# Personal OS — Tasks & Reminders Behavior v0.1

**Status:** Working draft

Tasks and reminders are core action primitives. They must be reliable, editable, understandable, and safe from accidental state changes.

## 1. Task entity — working fields
Potential V1 fields:
- id
- title
- notes
- createdAt
- updatedAt
- status: open / completed / cancelled
- completedAt
- priority: low / normal / high / urgent
- dueDateTime optional
- reminderDateTime optional
- recurrence optional
- projectId optional
- goalId optional
- lifeAreaId optional
- parentTaskId optional for subtasks
- pinnedFocus boolean or explicit focus relation

Exact schema will be frozen in data-model phase.

## 2. Creation
Tasks can be created from:
- Universal Capture
- Plan contextual add
- Project detail
- Goal detail
- Today focus/empty-state action

All routes should use one domain creation path to avoid divergent behavior.

## 3. Completion
Completion must:
- persist immediately
- set completedAt
- update related project/goal summaries
- remove pending reminder alarms if no longer appropriate
- appear in Journey as a real completion event if timeline rules include task completions
- allow undo/reopen

Row navigation and completion control must remain separate.

## 4. Reopen
Reopening:
- returns status to open
- clears completedAt
- reschedules reminder only if reminder time is still in the future or user explicitly reschedules

Do not silently create a reminder in the past.

## 5. Editing
Task Detail should support:
- title
- notes
- priority
- due date/time
- reminder
- recurrence
- project/goal/life area
- subtasks
- focus pinning
- completion/reopen
- delete/cancel

## 6. Delete vs cancel
Working distinction:
- Delete → removes task; confirmation/recovery policy required
- Cancel → keeps historical record but no longer actionable

For V1, if this distinction adds too much complexity, use safe Delete + completed/open states and revisit cancellation later.

## 7. Due date vs reminder
A due date is the commitment/deadline.
A reminder is a notification trigger.
They are not the same field.

A task may have:
- due date only
- reminder only
- both
- neither

UI copy must preserve this distinction.

## 8. Reminder scheduling
When a reminder is saved:
- persist reminder metadata first
- schedule AlarmManager/appropriate Android mechanism
- record scheduling state
- if exact scheduling is unavailable, explain and offer repair path

The database remains the source of truth for intended reminders; OS alarms are a delivery mechanism.

## 9. Notification permission
If Android runtime notification permission is required:
- educate contextually
- request permission
- if denied, keep the reminder record but clearly mark notification delivery as unavailable until fixed

No silent failure.

## 10. Exact-alarm access
If the chosen reminder requires exact timing and the platform requires special access:
- show concise explanation
- direct to system settings
- re-check when returning
- schedule once granted

Do not repeatedly duplicate task/reminder records during repair attempts.

## 11. Reboot/app update recovery
Personal OS must reconcile future reminder records after:
- device boot
- relevant package/app update events
- app startup where needed

Only future, open reminders should be rescheduled.

## 12. Notification tap
Notification tap should deep-link to the exact Task/Reminder Detail, not merely open the app home.

## 13. Notification actions — preferred V1
If reliable within scope:
- Done
- Snooze
- Open

Snooze options could include:
- 10 min
- 1 hour
- later today
- custom

If notification actions cannot be implemented robustly, omit rather than ship dead buttons.

## 14. Recurrence
Preferred behavior:
- recurrence describes generation/scheduling rule
- completing one occurrence does not destroy the series
- next occurrence is calculated predictably

Need dedicated recurrence-engine design before freeze.

## 15. Subtasks
Parent task may contain ordered subtasks.

Rules:
- parent can be completed manually even if subtasks remain only after explicit confirmation, or completion policy can require all subtasks; final choice pending
- subtask completion counts are truthful
- no arbitrary percentage unless `completed / total` is explicitly shown as such

## 16. Priority
Priority should influence:
- Today focus candidate ordering
- Plan ordering

But urgency/due time can override lower-priority distant work according to Personal Intelligence rules.

Priority is never shown only by color.

## 17. Focus pinning
User can explicitly make a task today's focus.
Pinned focus should normally outrank algorithmic suggestions until:
- completed
- cleared
- no longer actionable

## 18. Overdue
Overdue tasks remain actionable.
UI should be calm, not punitive.

Avoid copy like:
- You failed
- You're behind

Use factual wording:
- `Due yesterday`
- `Overdue by 2 days`

## 19. Empty / no-date tasks
Tasks without date remain in an Open/Inbox-like pool accessible through Plan/Search/Project.
They should not disappear from the system simply because they are not due today.

## 20. Data safety
- all edits are transactional where needed
- reminder cancellation/rescheduling follows task updates
- migrations preserve data
- duplicate-save prevention required

## 21. Acceptance criteria
A task/reminder system is not done until:
- create works
- edit works
- complete/reopen works
- delete/cancel behavior is defined
- reminder permission states work
- exact-alarm repair works where required
- reboot recovery works
- notification deep link works
- duplicate save is prevented
- Plan/Today/Journey stay consistent
