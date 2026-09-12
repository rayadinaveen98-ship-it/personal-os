# Personal OS — Projects & Goals Behavior v0.1

**Status:** Working draft

Projects and goals are different concepts and must not be collapsed into one vague progress card.

## 1. Definitions
### Project
A project is a finite body of work with a desired outcome and actionable next steps.

Examples:
- Build Personal OS V1
- Publish a short film
- Prepare a portfolio

### Goal
A goal is a direction or outcome the user wants to reach, often spanning multiple projects, habits or milestones.

Examples:
- Publish my first Android game
- Become consistent with strength training
- Build a sustainable creator workflow

A project can support a goal, but not every project needs a goal.

## 2. Project — working fields
Potential fields:
- id
- title
- description
- status: active / paused / completed / archived
- createdAt
- updatedAt
- startDate optional
- targetDate optional
- lifeAreaId optional
- goalId optional
- nextActionTaskId optional
- notes
- color/icon optional, restrained

Progress is derived from real milestones/tasks only when mathematically meaningful.

## 3. Project creation
Create Project should ask for only the minimum:
- title
- optional description
- optional life area
- optional target date

After creation, project detail can collect richer structure.

No giant setup form.

## 4. Project detail
Project Detail should answer:
- What is this project trying to accomplish?
- Why does it matter?
- What is the next action?
- What has already happened?
- What is blocking or coming next?

Potential sections:
- project header/status
- next action
- milestones
- open tasks
- recent activity
- notes / context
- linked goal/life area

## 5. Next action
A project should ideally have one explicit next action.

Rules:
- must reference a real open task or explicitly stored next-action text/entity
- Today can surface it only if project is active
- completing the next action should prompt/suggest choosing the next one rather than inventing it silently

## 6. Milestones
Milestones represent meaningful checkpoints.

Fields may include:
- title
- completed state
- target date optional
- ordering

Project progress can show milestone completion only if the UI makes the basis explicit, e.g. `3 of 5 milestones`.

## 7. Project status
### Active
Eligible for Today continuation/focus suggestions.

### Paused
Preserved but normally excluded from Today suggestions.

### Completed
Remains in history/Journey.

### Archived
Removed from active planning views but searchable/history-safe.

## 8. Last touched
`Last touched` must derive from real activity:
- task update/completion
- project edit/note
- logged session
- milestone update

Never calculate it from list position or mock values.

## 9. Goal — working fields
Potential fields:
- id
- title
- whyItMatters optional
- status: active / paused / achieved / archived
- createdAt
- targetDate optional
- measurementType optional
- targetValue optional
- currentValue optional
- unit optional
- lifeAreaId optional

## 10. Goal progress
Three preferred modes:

### Milestone-based
Example: `2 of 4 milestones complete`.

### Numeric
Example: `8 / 20 sessions` or `₹X / ₹Y` if future domains support it.

### Qualitative
No percentage. Show current evidence / next commitment instead.

If no valid measurement exists, never manufacture a percentage.

## 11. Goal detail
Potential sections:
- why it matters
- success definition
- evidence/progress
- linked projects
- supporting habits
- milestones
- next meaningful action
- recent activity

Goal Detail should emphasize evidence, not scorekeeping.

## 12. Linking
Possible links:
- Project → Goal
- Project → Life Area
- Task → Project
- Task → Goal optional
- Habit → Goal
- Skill/Hobby → Goal optional

Links should be useful for context and intelligence, not create mandatory bureaucracy.

## 13. Completion/achievement
Completing a project:
- records completion timestamp
- preserves tasks/activity/history
- can create a meaningful Journey event

Achieving a goal:
- preserves evidence/history
- may produce a richer celebration moment
- does not erase supporting projects/habits

## 14. Companion behavior
Suitable moments:
- first project created → subtle approving/welcome gesture
- milestone completed → small reaction
- project completed / goal achieved → richer but calm celebration

Never animate continuously inside dense project task lists.

## 15. Empty states
### Projects
> **No projects yet.**
> Give ongoing work a home so Personal OS can remember what matters and where you left off.

### Goals
> **No active goals yet.**
> Add one when there's something you genuinely want to move toward.

No fake sample projects presented as user-owned.

## 16. Archive/history
Completed/archived projects and achieved goals must remain searchable and available in Journey/history.

## 17. Acceptance criteria
Projects/goals are not done until:
- create/edit/status transitions work
- tasks can link/unlink
- project next action works
- real activity determines `last touched`
- milestone behavior works if shown
- goal progress basis is truthful
- history is preserved
- Today/Plan/Journey/Me reflect consistent data
