# Personal OS — Plan Screen v0.1

**Status:** Working draft

Plan is where intent becomes structured action. It must feel calm and capable, not like a dense project-management board.

## 1. Purpose
Plan answers:
1. What needs doing today?
2. What is coming this week?
3. Which projects are active?
4. What can I move, edit, complete, postpone, or clarify?

## 2. Primary modes
Working segmented modes:
- Today
- Week
- Projects

Every segment is fully functional. No visual-only tabs.

## 3. Today mode
Shows real actionable items for the selected day.

Priority order should be explainable and consistent with Today/Home intelligence.

Sections may include:
- Focus
- Due today
- Scheduled reminders
- Next actions
- Habits/routines due today when implemented
- Completed today (collapsed by default or shown lower)

## 4. Task row behavior
Task row tap → Task Detail.
Completion control is separate from row navigation.

Visible data may include:
- title
- project/life-area context
- due time/date
- priority marker
- reminder marker
- recurring indicator

Do not overload every row with all metadata.

## 5. Add task
`+ Add task` opens Universal Capture in Task context.
A direct structured quick-add can be considered later, but Capture remains the shared creation engine.

## 6. Week mode
Shows the next 7 days using real due/scheduled items.

Potential structure:
- day headers
- item counts
- expandable day groups
- unscheduled / overdue group

Interactions:
- tap day → day-focused Plan state
- tap task/reminder → detail
- add for selected day → Capture with date context

## 7. Projects mode
Shows active projects with truthful status derived from real project data.

Project card may contain:
- project title
- current status
- next action
- nearest milestone/deadline
- last touched time

No fake percentages unless project progress has a defined measurable basis.

Tap project → Project Detail.
Primary CTA → Create Project.

## 8. Focus behavior
User can explicitly choose/pin a focus.
If a focus exists, it appears consistently on Today and Plan.

Potential actions:
- Open
- Change focus
- Clear focus

Focus must never be silently invented from fake content.

## 9. Coming up
Use real future data only:
- next reminder
- next deadline
- milestone
- scheduled routine

If no upcoming data exists, omit section or show a calm truthful empty state.

## 10. Completed items
Completing a task:
- updates persistence immediately
- triggers subtle completion feedback
- optionally keeps the item visible briefly before moving to Completed
- never navigates unexpectedly

Reopen must be possible from detail or completed section.

## 11. Gestures
Optional swipe gestures may be added only if discoverable and never the sole path.
Possible future actions:
- complete
- snooze/reschedule

Do not implement destructive swipe-delete without confirmation/recovery.

## 12. Empty states
### Today empty
> **Nothing is scheduled for today.**
> Leave the day open, or choose something that deserves attention.

Actions:
- Capture something
- Choose from open tasks

### Week empty
> **Your week is open.**

### Projects empty
> **No projects yet.**
> Projects give ongoing work a home and help Personal OS remember where you left off.

Action: `Create your first project`

Companion may appear in these empty states with a quiet resting/observing pose.

## 13. Search/filter
Plan may expose filters for:
- life area
- project
- priority
- status

Avoid a heavy filter bar by default. Search can remain global.

## 14. Premium experience
- generous spacing
- no spreadsheet feel
- section transitions subtle
- completed-state animation restrained
- companion only in low-density/empty moments, not every list row

## 15. Accessibility
- minimum 48dp completion controls
- completion state announced semantically
- drag/reorder functionality, if added, must have accessible alternatives
- priority not conveyed by color alone

## 16. Open decisions
1. Whether Week is timeline-style or grouped list.
2. Whether completed items remain visible until day end.
3. Whether focus pinning lives in Task Detail or Plan directly.
4. Exact overdue treatment.
5. Whether project cards show milestone count or next-action-first layout.
