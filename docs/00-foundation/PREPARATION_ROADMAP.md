# Personal OS — Pre-Build Preparation Roadmap

The purpose of this roadmap is to remove ambiguity before handing the project to a high-capability Work model for end-to-end implementation.

The project should not jump straight from idea to code.

## Phase 0 — Product Foundation

Goal: establish what Personal OS is and what it must never become.

Deliverables:

- Product Foundation
- Product Principles
- V1 boundary / non-goals
- privacy/local-first philosophy
- core loop
- user model
- source-of-truth rules
- initial decision log

Exit condition:

A new contributor can explain the product correctly without relying on chat history.

## Phase 1 — Information Architecture

Goal: define the complete structure of the app.

Deliverables:

- primary navigation
- route hierarchy
- feature map
- object ownership
- relationship between Today, Plan, Capture, Journey, and Me
- detail-screen hierarchy
- global entry points such as Search and Settings
- first-run vs returning-user structure

Questions to resolve include:

- which concepts deserve top-level navigation?
- what belongs inside Plan versus Journey versus Me?
- where do goals, habits, hobbies, skills, reviews, and projects live?
- what should be reachable in one tap versus two or three?

Exit condition:

Every V1 feature has exactly one clear home and a known navigation path.

## Phase 2 — End-to-End UX Flows

Goal: design the product as continuous user journeys, not isolated screens.

Required flows include:

- first install and onboarding
- create first project
- create first goal
- create task
- create reminder
- capture idea
- capture journal entry
- voice capture
- morning open
- evening close
- task completion
- project continuation
- habit completion
- weekly review
- search and retrieve old information
- reminder notification interaction
- edit/delete/recover common data
- settings and permissions repair
- empty-state recovery

Exit condition:

Every important user action has a documented start, intermediate state, result, and recovery path.

## Phase 3 — Screen-by-Screen Specification

Goal: remove visual and behavioral ambiguity from every screen.

Each screen specification must include:

- purpose
- entry routes
- top hierarchy
- exact content regions
- primary CTA
- secondary actions
- all visible controls
- data sources
- loading state
- empty state
- error state
- first-use state
- populated state
- navigation destinations
- destructive-action handling
- accessibility/touch considerations
- light/dark behavior
- implementation notes where necessary

Priority screens:

1. Onboarding
2. Today / Home
3. Plan
4. Universal Capture
5. Journey
6. Me
7. Task Detail
8. Project Detail
9. Project Create/Edit
10. Goal Detail
11. Habit Detail
12. Hobby / Skill Detail
13. Journal Entry / Detail
14. Idea Detail
15. Search
16. Weekly Review
17. Life Timeline
18. Settings
19. Reminder permission/repair states

Exit condition:

The implementation model should not need to invent screen behavior.

## Phase 4 — Visual Design System

Goal: freeze a production-realistic design language.

Deliverables:

- design identity
- colors
- light palette
- dark palette
- typography
- spacing scale
- card styles
- radius system
- button hierarchy
- chip system
- inputs
- bottom navigation
- dialogs/sheets
- iconography rules
- empty-state visual language
- motion principles
- state colors
- progress visualization
- accessibility constraints
- responsive/small-screen rules
- status/navigation bar behavior

Working identity:

**Warm Personal Observatory**

Exit condition:

A Compose developer can reproduce the product without inventing a new visual style screen by screen.

## Phase 5 — Feature Behavior Specifications

Goal: define deep behavior independently of any one UI screen.

Feature specs required for:

- tasks
- subtasks
- reminders
- recurrence
- projects
- milestones
- goals
- habits/routines
- journal
- ideas
- hobbies
- skills
- learning/activity sessions
- universal capture
- search
- daily reflection
- weekly review
- timeline
- morning brief
- evening close
- notifications
- attachments
- backup/export
- app lock/privacy controls

Each feature must define creation, update, deletion/archive behavior, relationships, edge cases, and history implications.

Exit condition:

No core behavior depends on the implementation model guessing product semantics.

## Phase 6 — Personal Intelligence Specification

Goal: define useful intelligence without vague “AI” requirements.

Deliverables:

- Today's prioritization rules
- carry-forward rules
- next-action rules
- project-resumption context
- neglected-goal logic
- weekly pattern summaries
- capture classification behavior
- explainability rules
- confidence/fallback behavior
- what may be deterministic
- where optional AI could later enhance results

Exit condition:

Every V1 intelligent behavior has an input, rule, output, and fallback.

## Phase 7 — Data Architecture

Goal: freeze the local data model before large-scale coding.

Deliverables:

- entities
- IDs
- timestamps
- relationships
- lifecycle/archive semantics
- recurrence storage
- completion history
- activity/event model
- tags and links
- attachment model
- migrations policy
- deletion semantics
- search indexing strategy
- backup/export representation

Likely domains include:

- LifeArea
- Goal
- Hobby
- Skill
- Project
- Milestone
- Task
- Reminder
- Habit/Routine
- Session
- JournalEntry
- Idea
- Artifact/Creation
- Achievement
- KnowledgeNote
- MediaLog
- DailyReview
- WeeklyReview
- Tag
- Relation/Link
- Attachment

Exact V1 entities will be frozen here rather than assumed from this preliminary list.

Exit condition:

The implementation model can build Room schemas without redesigning the product.

## Phase 8 — Android Technical Architecture

Goal: define how the product should be engineered.

Current intended stack:

- native Android
- Kotlin
- Jetpack Compose
- Room / SQLite
- DataStore
- Hilt
- ViewModel + Flow/StateFlow
- WorkManager
- AlarmManager
- NotificationManager
- SpeechRecognizer
- BiometricPrompt where approved
- Android-first local-first architecture

Deliverables:

- module/package structure
- navigation architecture
- repositories
- domain/use-case boundary
- state ownership
- dependency injection
- date/time handling
- reminder scheduling
- reboot recovery
- permission architecture
- database migrations
- backup/export strategy
- error handling
- logging policy
- test boundaries

Exit condition:

The build model has clear engineering constraints but retains freedom for low-level implementation choices that do not change product behavior.

## Phase 9 — Quality and Acceptance Contract

Goal: define what “done” means before implementation begins.

Required acceptance areas:

- all controls functional
- navigation completeness
- persistence
- restart behavior
- reboot recovery
- notification behavior
- permission denial/recovery
- database migration safety
- no fake personal data
- no placeholder controls
- no clipped UI
- small-screen behavior
- light/dark modes
- accessibility basics
- unit tests
- integration tests where practical
- CI green
- installable APK mandatory

Exit condition:

The implementation model cannot claim completion solely because the project compiles.

## Phase 10 — Manual Repository and Build Preparation

Goal: prepare everything the implementation model needs before coding.

Manual preparation includes:

- repository structure established
- all frozen specs committed
- approved visual references committed or clearly linked
- package/application ID frozen
- Android SDK/build versions frozen
- GitHub Actions workflow defined
- no secrets committed
- branch/build policy documented
- asset licensing checked
- app icon/splash direction approved
- definition of APK artifact naming
- implementation checklist created

Exit condition:

A fresh Work session can open the repository and begin without needing hidden chat context.

## Phase 11 — Astra Execution Handoff

Target model: **Astra Medium** for the primary end-to-end implementation.

The final execution brief should instruct the model to:

1. read the entire repository specification package before writing code
2. treat frozen product/design specifications as authoritative
3. build the Android app end-to-end, not stop at planning or mockups
4. avoid fake or placeholder functionality
5. implement tests alongside behavior
6. keep the repository updated throughout execution
7. run CI and fix failures
8. preserve user data through migrations
9. produce an installable APK
10. finish with an implementation audit against the acceptance contract

Higher reasoning effort may be used selectively for difficult architecture or debugging work rather than by default for the entire build.

## Current position

We are currently in **Phase 0**.

Production implementation has intentionally not started in this repository.
