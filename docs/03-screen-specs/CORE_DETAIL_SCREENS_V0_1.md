# Personal OS — Core Detail Screens v0.1

**Status:** Working draft

This document defines the shared behavioral structure for the core detail screens. Final visual layouts may differ by domain, but each must provide truthful context and real actions.

## 1. Shared detail-screen rules
Every detail screen should answer:
- What is this?
- What state is it in?
- What can I do next?
- What is it connected to?
- What happened recently?

Shared requirements:
- clear back behavior
- edit path
- safe delete/archive path
- linked-entity navigation
- real timestamps/status where useful
- no inert chevrons/buttons
- no fake history

## 2. Task Detail
### Header
- title
- status
- project/life-area context

### Editable fields
- title
- notes
- priority
- due date/time
- reminder
- recurrence
- project
- goal
- life area
- subtasks

### Actions
- complete / reopen
- pin/clear Today focus
- duplicate optionally later
- delete/cancel safely

### Context/history
- created date
- completed date if complete
- linked project/goal
- subtask progress if applicable

## 3. Project Detail
### Header
- project title
- status
- life area / linked goal

### Hero content
- outcome/description
- next action

### Sections
- milestones
- open tasks
- recent activity
- notes/context
- linked goal

### Actions
- add task
- add milestone
- set next action
- edit
- pause/resume
- complete
- archive

## 4. Goal Detail
### Header
- title
- status
- target date if any

### Core sections
- why it matters
- success definition
- progress/evidence
- linked projects
- supporting habits
- milestones
- recent activity
- next meaningful action

### Progress rule
If no valid measurement exists, show evidence/milestones rather than percentage.

### Actions
- edit goal
- link/unlink project
- add milestone
- pause/resume
- mark achieved
- archive

## 5. Habit Detail
### Header
- title
- active/paused state
- schedule summary

### Sections
- today state
- recent completion history
- weekly/monthly consistency evidence
- linked goal/life area
- reminder settings

### Actions
- complete/log
- edit schedule
- pause/resume
- archive

No guilt-oriented missed-day design.

## 6. Hobby / Skill Detail
### Header
- name
- active/paused state
- current focus

### Sections
- recent sessions
- time/sessions this week/month
- notes/resources
- linked project/goal

### Actions
- log session
- edit current focus
- add note
- pause/archive

No fake proficiency level.

## 7. Journal Detail
### Header
- entry date/time
- optional title

### Body
- full journal content
- tags/linked context if present

### Actions
- edit
- save as memory / remove memory status if memory system ships
- link context
- delete

## 8. Idea Detail
### Header
- idea title/body
- created date
- status if used

### Actions
- edit
- archive
- link to project/life area
- convert/link to task or project
- delete safely

Original idea should remain traceable after conversion unless user explicitly deletes it.

## 9. Activity / Session Detail
### Content
- activity title
- date/time
- duration
- linked project/hobby/skill
- notes

### Actions
- edit
- link/unlink context
- delete

Session totals elsewhere must update after edits.

## 10. Detail-screen visual philosophy
- one strong identity/header area
- actions grouped by importance
- edit controls not always exposed as a giant form
- real history lower in hierarchy
- destructive actions visually separated
- companion presence generally omitted from dense edit/detail screens except meaningful empty states or major completion moments

## 11. Save behavior
Structured editing can use:
- full edit screen
- bottom sheet for one field
- inline control where clear

After save:
- persist before claiming success
- update all observing surfaces
- return to detail or remain in place with confirmation

## 12. Delete/archive behavior
- irreversible delete requires clear intent
- archive preferred for projects/goals/hobbies where history matters
- deleting linked records must not silently corrupt relationships
- reminders/alarms cleaned up when task/reminder deleted

## 13. Loading/error
Local detail should usually render quickly.
If record no longer exists:
> **This item is no longer available.**
Return user to sensible previous context.

## 14. Acceptance criteria
For every detail screen shipped:
- every visible action works
- editing persists
- navigation to linked entities works
- back stack is sensible
- deleted/archived state is safe
- related summaries update
- no fake or placeholder metadata appears as user data
