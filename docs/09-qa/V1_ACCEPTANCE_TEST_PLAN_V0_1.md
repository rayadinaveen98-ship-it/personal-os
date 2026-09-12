# Personal OS — V1 Acceptance & Test Plan v0.1

**Status:** Working draft

This document defines what `done` means for the Personal OS V1 build. A visually impressive APK that compiles is not sufficient.

## 1. Release philosophy
V1 should be usable for several weeks as a real personal system without major dead ends, fabricated data, data loss, or critical non-responsive controls.

## 2. Universal acceptance rules
Every shipped screen must satisfy:
- all visible interactive controls work
- back behavior is sensible
- loading/empty/error states exist where needed
- no fake personal data
- data persists across restart
- light and dark mode work
- status/navigation insets are correct
- compact-height phones remain usable
- text scaling does not catastrophically clip content
- screen-reader semantics exist for critical controls

## 3. Visual acceptance
### Premium quality
Reject if final result feels like:
- stock Material sample
- generic to-do app
- unstyled form collection
- inconsistent card/button system

Required:
- Warm Personal Observatory identity is recognizable
- spacing/radius/type hierarchy is consistent
- app icon/splash/welcome/setup feel coherent
- companion, if included, feels integrated rather than pasted on
- motion is smooth and restrained

## 4. Onboarding acceptance
Test:
- fresh install
- name blank and named path
- zero focus selections
- multiple focus selections
- custom life area
- skip optional steps
- initial project/task/goal creation if enabled
- Morning Brief on/off
- Evening Reflection on/off
- back navigation preserves choices
- setup persistence failure handling

Critical rule: Skip must not silently save fake/default personal choices.

## 5. Today acceptance
Test:
- no data
- one due task
- multiple priorities
- overdue high priority
- pinned focus
- next reminder
- active project next action
- morning state
- evening reflection state
- user with no preferred name

Verify focus logic matches Personal Intelligence rules and links open exact detail screens.

## 6. Plan acceptance
Test:
- Today mode
- Week mode
- Projects mode
- add task
- row tap → detail
- completion control separate
- reopen
- overdue items
- no tasks
- no projects
- many tasks (pagination/scroll behavior)

No hidden tasks due to arbitrary `take(3)`-style truncation without a real view-all path.

## 7. Capture acceptance
Test representative language:
- reminder tomorrow at explicit time
- task due weekday without reminder
- urgent/high-priority task
- journal sentence
- idea sentence
- activity with duration
- known project hint
- unknown project hint
- ambiguous date/time

Verify:
- inferred fields editable
- save exactly once
- voice path works or fails gracefully
- permission repair works
- duplicate save blocked
- originating context restored after save

## 8. Task/reminder acceptance
- create/edit/delete
- complete/reopen
- subtasks
- due date
- reminder date/time
- recurrence
- project link
- focus pin
- notification runtime permission
- exact alarm repair
- notification fires on device
- notification opens exact detail
- reboot recovery
- app update/restart reconciliation
- snooze/done actions if shipped

## 9. Projects/goals acceptance
- create/edit
- active/paused/completed/archived states
- next action
- milestones
- linked tasks
- last-touched based on real activity
- goal links
- truthful progress modes
- historical preservation after completion

## 10. Habits acceptance
- create/edit/pause/archive
- schedule rules
- completion event history
- reminder if enabled
- weekly/monthly evidence
- schedule change preserves old history
- missed day does not generate punitive UI

## 11. Hobbies/skills/sessions acceptance
- create hobby/skill
- log session manually
- log through Capture
- duration totals
- current focus
- pause/archive
- Today continue-learning card only when supported by real data

## 12. Journal/ideas/memories acceptance
- journal create/edit/delete
- evening reflection saves real journal entry
- idea create/edit/archive
- idea conversion/link preservation
- memory save/unsave if feature ships
- Journey date accuracy
- Search finds text

## 13. Journey acceptance
- all 7 days/date navigation
- selected date filtering
- journal opens exact entry
- timeline timestamps correct
- no fabricated history
- no-data day state
- historical navigation
- weekly review access

## 14. Search acceptance
- matches each documented entity type
- result type visible
- exact detail navigation
- edits reflected in index
- deleted records removed appropriately
- works offline
- large local dataset remains responsive

## 15. Weekly Review acceptance
- period boundaries correct in local timezone
- metrics derive from real data
- open-loop actions modify real records
- reflection persists
- review history searchable
- low-activity week remains valid

## 16. Me/Settings acceptance
- preferred name updates Today and Me
- theme System/Light/Dark
- Morning Brief setting changes real behavior
- Evening Reflection setting changes real behavior
- permission statuses refresh
- companion toggle if shipped
- app lock if shipped
- About displays correct version/build

## 17. Backup/restore acceptance
Test:
- backup empty/new profile
- backup populated profile
- restore after reinstall/test DB clear
- corrupted backup
- wrong format version
- restore with reminders
- attachments if supported

Verify failed restore leaves current data intact.

## 18. Migration acceptance
For every DB schema bump after baseline:
- automated migration test
- old fixture → new schema
- row counts/relationships preserved
- no destructive fallback

## 19. Privacy acceptance
- no journal/body text in production logs
- core functions work offline
- permissions only requested contextually
- app lock prevents content flash if included
- delete-all removes data and alarms

## 20. Character acceptance
If companion ships:
- tap triggers varied short reaction
- returns to idle
- rapid tapping does not stack/break state
- disabled/reduced-motion path works
- no essential content depends on character
- no layout obstruction
- animations remain smooth on mid-range device

## 21. Device matrix — minimum
Before V1 sign-off test at least:
- compact phone height
- common 1080p Android phone
- current modern Android version
- at least one older supported Android version
- gesture navigation
- dark mode
- font scale > default

## 22. CI gate
Required on main/release candidate:
- unit tests green
- migration tests green
- lint or defined static checks green
- assembleDebug green
- milestone APK artifact uploaded

Instrumentation subset should run if reliable enough not to create meaningless flaky gates.

## 23. Manual release audit
A human/device audit must tap through every visible interactive control before V1.

Audit output categorizes:
- Works
- Partial
- Static/dead
- Incorrect data
- Visual issue
- Accessibility issue

Release blocker: any unlabeled dead/static control that looks interactive.

## 24. Definition of done
V1 is ready only when:
- critical flows pass
- no P0/P1 data-loss/reminder/navigation bugs
- no fabricated personal data
- backup exists
- migrations are safe
- APK installs and launches cleanly
- UI feels coherent/premium
- user can genuinely run daily life in the app
