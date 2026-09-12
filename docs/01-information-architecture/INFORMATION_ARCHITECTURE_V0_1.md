# Personal OS — Information Architecture v0.1

Status: **Working draft — requires product review before freeze**

This document defines where things live and how the major parts of Personal OS relate.

## 1. Primary navigation

Working primary navigation:

1. **Today**
2. **Plan**
3. **Capture** — central primary action
4. **Journey**
5. **Me**

Rationale:

- **Today** = present tense
- **Plan** = future / intentional work
- **Capture** = immediate input
- **Journey** = past / memory / reflection
- **Me** = identity / growth / system configuration

This gives the product a temporal and personal structure rather than a feature-menu structure.

## 2. Today

Purpose: answer what matters now.

Today may surface:

- time-aware greeting
- current date/context
- primary focus / next action
- important tasks
- upcoming reminder/deadline
- active project continuation
- routine/habit context when relevant
- recent context worth remembering
- morning brief state
- evening reflection entry

Today should link deeply to specific records rather than sending every interaction to a generic tab.

### Child routes from Today

- Task Detail
- Reminder/Task Detail
- Project Detail
- Goal Detail
- Habit Detail
- Journal Detail / reflection
- Capture with contextual mode

## 3. Plan

Purpose: organize intentional future work.

Plan contains four conceptual layers:

### 3.1 Today plan

- tasks due/selected today
- overdue work requiring a decision
- reminders
- routines/habits due today
- time-sensitive next actions

### 3.2 Upcoming

- week view
- upcoming due dates
- reminders
- recurring items
- milestones

### 3.3 Projects & Goals

- active projects
- paused projects
- archived projects
- goals
- milestones
- next actions

### 3.4 Routines

- habits
- recurring routines
- completion history

Working UI may use segmented views/tabs within Plan, but the exact presentation will be frozen in the Plan screen spec.

### Plan child routes

- Task Detail
- Task Create/Edit
- Project List
- Project Detail
- Project Create/Edit
- Goal List
- Goal Detail
- Goal Create/Edit
- Habit/Routine List
- Habit Detail
- Habit Create/Edit
- Milestone Detail/Edit

## 4. Capture

Purpose: quickest safe entry into the system.

Capture is a global action, not a database category.

Supported conceptual capture types:

- Task
- Reminder
- Idea
- Journal
- Activity / Session
- Project note
- Learning note
- Memory

The user should be able to type or speak naturally, inspect the interpretation, correct important fields, and save.

### Capture modes

- Universal Capture
- Quick Task
- Quick Reminder
- Evening Reflection
- Journal Entry
- Log Activity

The same underlying capture architecture may serve these contexts with different preselected defaults.

## 5. Journey

Purpose: remember, reflect, and understand what actually happened.

Journey contains:

### 5.1 Day view

- selected date
- journal entries
- meaningful activity
- completed work
- memories/notes actually created that day

### 5.2 Timeline

Chronological cross-domain history derived from real data/events.

### 5.3 Journal

- journal entries
- daily reflections
- searchable history

### 5.4 Reviews

- daily close
- weekly review
- later monthly/annual reflection if approved

### 5.5 Chapters / meaningful periods

Not a V1 fake-content feature. If included, chapters must be created explicitly or derived transparently from real history. Exact V1 status to be decided later.

### Journey child routes

- Journal Detail/Edit
- Day Detail
- Timeline
- Weekly Review
- Review Detail
- Activity/Session Detail
- Search Result Detail

## 6. Me

Purpose: the user's personal identity, life structure, growth contexts, and system preferences.

Me contains:

### 6.1 Identity

- preferred name
- optional avatar
- self-description / roles if user chooses

### 6.2 Life Areas

Examples may include:

- App Building
- Game Development
- Filmmaking
- Reading
- Health
- Learning

Life areas are user-controlled context containers, not fixed categories.

### 6.3 Hobbies & Skills

- hobbies
- skills
- recent sessions
- learning context
- real evidence/history

No fake proficiency percentage by default.

### 6.4 Goals overview

A concise view of active goals with truthful state.

### 6.5 Personal settings

- name
- appearance
- morning brief
- evening reflection
- notification status
- permissions repair
- privacy / app lock
- backup/export
- data management

### Me child routes

- Life Area Detail
- Hobby Detail
- Skill Detail
- Goal Detail
- Settings
- Privacy & Security
- Backup & Export

## 7. Global destinations

These destinations should be reachable contextually and may not need permanent bottom-nav icons.

### Search

Global cross-domain search over:

- tasks
- projects
- goals
- journal
- ideas
- activities/sessions
- skills/hobbies
- notes
- reviews

Search results must indicate object type and navigate to the correct detail screen.

### Settings

Primary entry from Me.

### Notifications / reminder repair

May appear contextually when a reminder cannot function due to missing permission or exact-alarm access.

## 8. Core domain ownership

| Domain | Primary home | May surface in |
|---|---|---|
| Task | Plan | Today, Search, Journey when completed |
| Reminder | Plan | Today, notifications, Search, Journey event history |
| Project | Plan | Today, Me, Search, Journey |
| Goal | Plan / Me | Today, Search, Journey |
| Habit/Routine | Plan | Today, Me, Journey |
| Journal | Journey | Today, Search |
| Idea | Journey or Capture history | Today contextually, Search, Project Detail |
| Activity/Session | Journey | Today contextually, Me, Project/Skill Detail |
| Hobby | Me | Today contextually, Journey, Search |
| Skill | Me | Today contextually, Journey, Search |
| Weekly Review | Journey | Today when due, Search |
| Life Area | Me | Today, Plan filters, Search |

This table describes the user's mental home, not necessarily the database ownership.

## 9. Detail-first navigation rule

When a visible card represents a specific entity, tapping it should normally open that entity's detail screen.

Examples:

- tapping a reminder opens its Task/Reminder Detail
- tapping an active project opens Project Detail
- tapping recent journal context opens Journal Detail
- tapping a goal opens Goal Detail

Avoid sending specific cards to generic tabs unless the card itself is a section shortcut.

## 10. Create versus detail behavior

### Create actions

Use dedicated create/edit screens or sheets where structured input matters.

Universal Capture remains the fastest path, but it must not be the only way to manage structured records.

### Detail screens

Detail screens are where the user can:

- inspect context
- edit
- complete/archive where appropriate
- see relationships
- see real history
- navigate to linked entities

## 11. Destructive-action rule

Delete/archive actions should not be hidden behind arbitrary full-row taps.

- completion control should be distinct from opening the item
- destructive actions should require clear intent
- archive should be preferred over deletion for historical entities where preservation is meaningful
- exact behavior will be defined per feature

## 12. Back-navigation rule

General rule:

- back returns to the immediately previous meaningful context
- edit screens return to the object detail after save
- create screens return to the invoking context after successful creation
- Capture invoked globally may return to the originating primary tab when save completes
- deep links should reconstruct a sensible back stack

Exact Android Navigation behavior will be specified later.

## 13. Empty-state architecture

Empty states must belong to the real feature.

Examples:

### No projects

Show:

- concise explanation
- **Create your first project**
- optional example text clearly labeled as an example, never stored as user history

### No journal entries

Show:

- reflection invitation
- **Write your first entry**

### No tasks today

Do not invent tasks. Show a calm clear state such as:

> Nothing is asking for your attention right now.

Then offer Capture or Plan.

## 14. Open questions for v0.2

These must be resolved before IA freeze:

1. Should Plan expose `Today / Week / Projects / Routines`, or use a different internal navigation structure?
2. Should Ideas have a dedicated library view or live primarily through Search, Projects, and Journey?
3. Should Goals primarily live under Plan or Me, while still being reachable from both?
4. Are Hobbies and Skills separate user-facing object types or a unified Growth model with subtypes?
5. Does V1 include user-created Life Area detail pages?
6. What exact role should Chapters play in V1 Journey?
7. Should completed tasks appear directly in the Journey timeline by default, or only meaningful activity/events?
8. What should global Search include in its first V1 implementation?

These questions should be settled through screen and feature design, not left for Astra to decide during coding.
