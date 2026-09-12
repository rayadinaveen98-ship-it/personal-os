# Personal OS — Personal Intelligence v0.1

**Status:** Working draft

Personal Intelligence is the layer that helps Personal OS decide what to surface, what to connect, and how to reduce restart friction. V1 should begin with explainable deterministic logic rather than opaque AI dependency.

## 1. Core principle
Personal OS should be intelligently helpful without pretending to know more than the stored evidence supports.

Intelligence should be:
- explainable
- deterministic where possible
- privacy-preserving
- local-first
- reversible by user choice
- non-judgmental

## 2. First V1 responsibilities
The intelligence layer should help with:
- Today's focus candidate
- ordering important actions
- selecting continuation cards
- surfacing nearest reminder/deadline
- showing `pick up where you left off`
- basic Morning Brief summary
- evening reflection context
- identifying neglected active work carefully
- generating truthful weekly summaries from stored events

## 3. Explicit user choice beats inference
If the user explicitly pins a focus, project, or priority, that should usually outrank automatic suggestions until no longer actionable.

Never override a clear user choice silently.

## 4. Today focus candidate — working hierarchy
Candidate order:
1. explicit pinned focus
2. overdue urgent/high-priority actionable task
3. due-today urgent/high-priority task
4. imminent reminder/deadline
5. active project's explicit next action
6. earliest meaningful due item
7. optional routine/habit due today when habit engine is implemented

Tie-breakers may consider:
- due time
- priority
- user-selected project context
- recency of project activity

The final scoring/order must be documented before freeze.

## 5. Avoid urgency inflation
Do not mark everything urgent.
Overdue low-priority items should not necessarily displace meaningful current work.

The system should distinguish:
- urgent
- important
- merely old

without introducing unsupported psychological scoring.

## 6. Continue / pick-up logic
A continuation candidate may come from:
- recently active project with open next action
- recent learning session
- unfinished session/note
- active project touched recently but not completed

A continuation card must have a real target entity.

Example:
> **Pick up where you left off**
> Personal OS · last touched yesterday
> Next: finalize setup behavior

The `Next` line must come from a real task/next-action record.

## 7. Morning Brief
If enabled, Morning Brief is generated from actual current-day data.

Possible components:
- count of genuinely important actions
- first scheduled reminder
- focus candidate
- one relevant active project

Example:
> You have 3 things that matter today. Your first reminder is at 10:30 AM.

Avoid:
- inspirational filler
- generic weather-like narration unless weather is an explicit future integration
- fake productivity scores

## 8. Evening reflection context
If enabled, Personal OS may summarize concrete events from the day before prompting reflection.

Example:
> You completed 2 tasks and spent 45 minutes on Personal OS today.

Only if those records exist.

Then prompt:
> What was worth remembering?

## 9. Neglected work
A future/optional rule may identify an active project that has not been touched for a meaningful period.

Requirements:
- only active projects
- configurable / conservative threshold
- no guilt language

Possible wording:
> Personal OS hasn't been touched in 8 days. Still active?

Actions:
- Continue
- Pause project
- Not now

## 10. Weekly Review intelligence
Weekly Review should compute, not invent:
- tasks completed
- projects touched
- sessions logged
- journal/reflection days
- milestones completed
- ideas captured
- open loops moving into next week

It may suggest questions based on these facts.

## 11. Relationships
Intelligence may use explicit relations such as:
- task → project
- project → goal
- project → life area
- activity → project/skill
- journal → project/tag

Do not infer permanent relationships from one ambiguous sentence without user confirmation.

## 12. Suggestions vs facts
UI must clearly distinguish:
- **Fact:** `You completed 3 tasks this week.`
- **Suggestion:** `You may want to continue Personal OS tomorrow.`

Do not render suggestions in a way that looks like historical truth.

## 13. Local-first implementation
V1 logic should run locally over Room/domain data where feasible.

No paid API should be required for:
- focus selection
- Morning Brief counts
- recent activity
- continuation logic
- weekly metrics

## 14. Optional AI later
Future AI can help with:
- richer journal summarization
- semantic search
- relationship suggestions
- natural-language planning
- summarizing long project context

AI output must remain:
- clearly identified when it is a suggestion
- editable
- privacy-conscious
- non-essential to core functionality

## 15. No life score
Personal OS must not reduce the user's life to one numerical score.

Avoid:
- productivity score
- life score
- discipline score
- generic `82% better this week`

Use concrete evidence instead.

## 16. Calm language
Intelligence copy must avoid guilt or manipulation.

Avoid:
- You're falling behind
- Don't break your streak
- You failed your goal

Prefer:
- `This is still open.`
- `You haven't returned to this project recently.`
- `Move it, pause it, or leave it for later.`

## 17. Explainability
Where a suggestion may surprise the user, allow simple rationale.

Example:
> Suggested because it is high priority and due today.

This may live behind a small `Why this?` affordance rather than cluttering default UI.

## 18. User control
Users should be able to:
- pin/clear focus
- dismiss a suggestion
- pause a project
- disable Morning Brief / Evening Reflection
- correct relationships/context

The system should learn only from explicit stored state in V1, not hidden behavioral profiling.

## 19. Acceptance criteria
The intelligence layer is not done until:
- outputs can be reproduced from stored data
- no fake personal facts are generated
- user overrides are respected
- empty data produces truthful empty guidance
- Today and Plan use consistent prioritization rules
- Morning Brief exists if the setting exists
- Weekly Review metrics trace to real records
