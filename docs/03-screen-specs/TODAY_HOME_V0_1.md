# Personal OS — Today / Home Screen v0.1

Status: **Working draft — intentionally detailed, not yet frozen**

Today is the most important screen in Personal OS. It should feel like the user's personal command center for the current day, not a generic productivity dashboard.

## 1. Screen purpose

Today should answer, in order:

1. **Where am I today?**
2. **What matters most right now?**
3. **What should I do next?**
4. **What is coming soon?**
5. **What context should I remember before continuing?**
6. **Is there anything worth reflecting on or closing out?**

The screen should reduce cognitive load. It must not become a wall of widgets.

## 2. Entry behavior

Today is the default primary destination after:

- successful onboarding
- normal app launch
- returning from a completed global Capture when the originating context was Today

Deep links from notifications may open a detail screen instead of Today.

## 3. Time-aware greeting

The first major text element is the greeting.

### Greeting logic

Suggested local-time ranges:

- 05:00–11:59 → **Good morning, {preferredName}.**
- 12:00–16:59 → **Good afternoon, {preferredName}.**
- 17:00–22:59 → **Good evening, {preferredName}.**
- 23:00–04:59 → **Still awake, {preferredName}?** or another gentle late-night phrase to be reviewed before freeze

If preferred name is absent:

- **Good morning.**
- **Good afternoon.**
- **Good evening.**

Do not hard-code a person's name.

### Tone rule

Greeting copy should be calm and personal. Avoid motivational clichés such as:

- Crush your goals!
- Let's dominate today!
- You're falling behind!

## 4. Header hierarchy

Working visual hierarchy:

1. small eyebrow — `YOUR DAY` or equivalent
2. large greeting
3. local date
4. optional small contextual line only when useful

Example:

```text
YOUR DAY

Good morning,
Navin.

Saturday, 12 September
```

The greeting may wrap naturally on small screens.

## 5. Top status / identity access

A small identity/avatar control may appear at top-right.

Tap behavior:

- opens Me or Settings/Profile depending final navigation decision

Do not display a fake profile image. Use initials or a user-selected asset.

## 6. Primary Focus card

This is the strongest card on Today.

### Purpose

Surface one truthful item that most deserves attention now.

### Possible source types

- high-priority task
- task due soon
- reminder due soon
- explicit user-selected focus
- project's next action
- routine/habit due today if appropriate

### Selection hierarchy — working rule

Preference order should be explainable and later formalized in the intelligence spec. A possible starting order:

1. explicit user-pinned focus
2. overdue high-priority item
3. due-today high-priority item
4. imminent reminder/deadline
5. active project's clear next action
6. earliest meaningful due item

If no valid item exists, do not invent one.

### Card content

Possible populated structure:

```text
TODAY'S FOCUS

Finish onboarding behavior spec

Personal OS · High priority
Due today

[Open] / row tap
```

### Tap behavior

If the focus represents a specific entity:

- Task → Task Detail
- Project next action → Task Detail or Project Detail according to entity ownership
- Reminder → Reminder/Task Detail
- Habit → Habit Detail

### Empty state

If there is no current focus:

```text
TODAY'S FOCUS

Nothing is asking for your attention right now.

[Choose a focus]   [Capture something]
```

No sample task should be inserted into the user's data.

## 7. “Keep moving” / next actions section

Purpose: show a small set of useful continuation cards without overwhelming Today.

Working maximum: **2–4 cards**, selected from available real contexts.

Potential card types:

### Tasks

Shows truthful count/state such as:

- `3 open today`
- `1 completed · 2 left`
- `Nothing due today`

Tap → Plan filtered to Today or appropriate task list.

### Active project

Shows:

- project name
- latest meaningful context or next action

Example:

```text
CONTINUE PROJECT
Personal OS
Last touched yesterday
```

Tap → Project Detail.

### Continue learning / skill

Only shown if real recent skill/session history exists.

Example:

```text
CONTINUE LEARNING
Blender
Last session 2 days ago
```

Tap → Skill/Hobby Detail.

Do not fabricate lesson numbers.

### Reminder

Shows the nearest meaningful upcoming reminder.

Example:

```text
NEXT REMINDER
Call studio
6:30 PM
```

Tap → Reminder/Task Detail.

### Habit / Routine

Shown only when a real routine is due and relevant.

Tap → Habit Detail.

### Selection rule

Cards should be chosen from actual data according to usefulness, not hard-coded positions that create empty filler.

## 8. Upcoming section

A compact section may show the next 1–3 time-sensitive items.

Possible content:

- reminder later today
- task deadline
- scheduled routine
- project milestone

If nothing is upcoming, omit this section or show a small calm empty state. Do not create fake scheduled content.

## 9. Recent context / “Remember where you left off”

Purpose: reduce restart friction.

This card may show one truthful recent event such as:

- last project activity
- recent journal insight linked to current work
- unfinished session note
- recent idea tied to an active project

Example:

```text
PICK UP WHERE YOU LEFT OFF

Personal OS
You last worked on the onboarding flow yesterday evening.

[Continue]
```

The exact language must be derived from stored events/content, not generated from assumptions.

Tap → relevant detail screen.

If insufficient history exists, omit the section.

## 10. Reflection / close-the-day card

Evening reflection is optional and controlled by user preference.

Suggested visibility:

- primarily evening
- may remain available manually from Journey at any time

Example:

```text
CLOSE THE DAY

What was worth remembering today?

[Reflect]
```

Tap → Capture in **Evening Reflection** mode or dedicated Daily Reflection screen, depending final UX decision.

It should not open an empty generic capture state with no context.

If the user disables Evening Reflection, do not show this card automatically.

## 11. Morning Brief state

If Morning Brief is enabled, Today may present a subtle morning-only summary near the top.

Possible contents:

- number of important actions
- nearest reminder
- one active project needing attention

Example:

```text
You have 3 things that matter today.
Your first reminder is at 10:30 AM.
```

This should be generated from real data.

Morning Brief is not a separate fake feature flag: if we expose the setting in V1, the behavior must exist.

## 12. Universal Capture affordance

The central primary navigation action is Capture.

Today may also include contextual capture entry points when useful:

- `+ Add one`
- `Capture something`
- `Reflect`

All of these should open Universal Capture with the appropriate context rather than duplicating separate capture systems.

## 13. Bottom navigation

Working navigation:

- Today
- Plan
- central Capture
- Journey
- Me

Today is visually active on this screen.

The Capture action should be visually prominent but still harmonize with the design system.

## 14. Loading state

Today should avoid a long blocking splash after initial application startup.

If local data is still loading:

- render the stable page structure
- use subtle skeleton/placeholders only where necessary
- do not display fake textual values

## 15. First-use populated state

After onboarding, Today should already feel personal using the information the user explicitly supplied.

Allowed sources:

- preferred name
- chosen life areas
- initial project/goal if onboarding captured one
- morning/evening preferences

If the user did not create a task/project/goal, Today must remain truthful and guide the next action.

Possible first-use state:

```text
Good morning, Navin.

Your space is ready.

TODAY'S FOCUS
Nothing is asking for your attention yet.
[Capture something]

START HERE
Create your first project
Add something you want to remember
```

The guidance cards are onboarding prompts, clearly not user history.

## 16. No-data state

A returning user can legitimately have no due work.

Do not treat this as a problem.

Possible copy:

> **Nothing is asking for your attention right now.**
> You can choose what deserves focus, or simply leave the day open.

Actions:

- Choose a focus
- Capture something

## 17. Error state

Because Today is based primarily on local data, full-screen fatal errors should be rare.

If one section fails to resolve:

- keep the rest of Today usable
- show a small retry state for that section if necessary
- log the failure according to the technical logging policy

Do not replace broken data with fabricated fallback content.

## 18. Visual direction

Today should embody **Warm Personal Observatory**.

Working light palette inherited from validated direction:

- background — Warm Ivory `#F5F1E8`
- card — Soft Cream `#FCF9F3`
- primary text — Ink `#252521`
- primary accent — Moss Sage `#667A61`
- restrained gold — `#C89B58`
- lavender — `#9186A5`
- terracotta — `#A06B59`

Working dark direction:

- background — `#171816`
- card — `#22231F`

Exact contrast values and dark surfaces will be audited before design freeze.

## 19. Working typography hierarchy

Subject to final design-system freeze:

- eyebrow: ~11–12sp
- greeting: ~30–34sp, strong weight
- section heading: ~14–16sp or editorial equivalent
- card title: ~18–24sp depending importance
- body: ~14sp
- metadata: ~11–12sp

Typography should prioritize clarity and warmth, not novelty.

## 20. Working layout rhythm

Subject to design-system freeze:

- page horizontal padding: ~20dp
- grid base: 8dp
- typical card gap: 16dp
- large card radius: ~24dp
- standard card radius: ~18–20dp
- touch targets: at least 48dp
- central Capture action: approximately 56–64dp

## 21. Motion

Motion should be subtle and implementable:

- small press feedback
- gentle card transitions
- short content-state transitions
- no decorative animation that delays action
- no fake physics or impossible lighting

## 22. Accessibility

Today must support:

- minimum 48dp touch targets
- readable contrast in both themes
- text scaling without severe clipping
- content descriptions for non-text controls
- semantic state for completed/due/priority indicators
- no interaction dependent only on color

## 23. Small-screen behavior

The screen must be tested on compact heights.

Rules:

- greeting may wrap
- cards must not require fixed heights that clip text
- Today content may scroll vertically
- bottom navigation remains stable
- system status/navigation insets must be respected
- no content should render beneath status bars unintentionally

## 24. Things explicitly forbidden on Today

Do not show:

- fake progress percentages
- fake completed-task counts
- fake learning hours
- fake streaks
- fabricated motivational insights
- placeholder project names presented as real
- more than one competing primary focus
- excessive red warning states
- ads
- engagement bait

## 25. Open decisions before freeze

1. Final late-night greeting copy.
2. Whether Morning Brief is a distinct card or integrated into the top summary.
3. Exact maximum number of continuation cards.
4. Whether explicit user-pinned focus overrides all urgency rules.
5. Exact treatment of overdue low-priority items.
6. Whether completed items appear on Today after completion or disappear immediately.
7. Whether Today should expose a compact “done today” section in the evening.
8. Final avatar/Profile tap destination.

These decisions should be resolved before the screen is marked **FROZEN FOR IMPLEMENTATION**.
