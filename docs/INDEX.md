# Personal OS — Specification Index

This file is the navigation map for the product-definition package.

## Current project state

- **Phase 0 Product Foundation:** baseline established
- **Phase 1 Information Architecture:** working draft started
- **Phase 2 UX Flows:** launch/welcome/setup + screen/flow matrix started
- **Phase 3 Screen Specs:** core primary screens, Search, Settings, Weekly Review, and core detail-screen behavior drafted
- **Phase 4 Design System:** visual language, color/type, reusable components, brand/icon, motion, and companion-character drafts started
- **Phase 5 Behavior:** core action/life domains drafted
- **Phase 6 Personal Intelligence:** deterministic V1 draft started
- **Phase 7 Data:** data model + privacy/backup/security drafts started
- **Phase 8 Android:** architecture draft started
- **Phase 9 QA:** V1 acceptance plan drafted
- **Astra build handoff:** not ready yet; remaining decisions tracked in the flow matrix

## Foundation

- [`00-foundation/PRODUCT_FOUNDATION_V1.md`](00-foundation/PRODUCT_FOUNDATION_V1.md) — product purpose, promise, philosophy, V1 direction, privacy and intelligence principles
- [`00-foundation/PRODUCT_PRINCIPLES.md`](00-foundation/PRODUCT_PRINCIPLES.md) — non-negotiable decision rules
- [`00-foundation/V1_SCOPE_MATRIX.md`](00-foundation/V1_SCOPE_MATRIX.md) — what belongs in and outside V1
- [`00-foundation/PREPARATION_ROADMAP.md`](00-foundation/PREPARATION_ROADMAP.md) — staged pre-build work before Astra
- [`00-foundation/FOUNDER_DIRECTION_PREMIUM_EXPERIENCE_2026_09_12.md`](00-foundation/FOUNDER_DIRECTION_PREMIUM_EXPERIENCE_2026_09_12.md) — locked founder requirement for a premium, rich, calm, peaceful and emotionally warm experience

## Information Architecture

- [`01-information-architecture/INFORMATION_ARCHITECTURE_V0_1.md`](01-information-architecture/INFORMATION_ARCHITECTURE_V0_1.md) — working app hierarchy and ownership model

## UX Flows

- [`02-ux-flows/LAUNCH_WELCOME_SETUP_V0_1.md`](02-ux-flows/LAUNCH_WELCOME_SETUP_V0_1.md) — detailed first-launch, welcome, setup, personalization, permission and first-Today handoff behavior
- [`02-ux-flows/SCREEN_FLOW_MATRIX_V0_1.md`](02-ux-flows/SCREEN_FLOW_MATRIX_V0_1.md) — master surface/readiness map for all screens and flows still needed before Astra handoff

## Screen Specifications

- [`03-screen-specs/TODAY_HOME_V0_1.md`](03-screen-specs/TODAY_HOME_V0_1.md) — detailed working specification for the most important screen
- [`03-screen-specs/PLAN_V0_1.md`](03-screen-specs/PLAN_V0_1.md) — Today/Week/Projects planning behavior, task interactions, coming-up and empty states
- [`03-screen-specs/CAPTURE_V0_1.md`](03-screen-specs/CAPTURE_V0_1.md) — universal natural-language/voice capture, structured correction, permissions and save behavior
- [`03-screen-specs/JOURNEY_V0_1.md`](03-screen-specs/JOURNEY_V0_1.md) — truthful daily history, journal, timeline, memories and reflection behavior
- [`03-screen-specs/ME_V0_1.md`](03-screen-specs/ME_V0_1.md) — identity, life areas, projects, goals/growth truthfulness, privacy and settings gateway
- [`03-screen-specs/SEARCH_V0_1.md`](03-screen-specs/SEARCH_V0_1.md) — local-first global retrieval across V1 domains
- [`03-screen-specs/SETTINGS_V0_1.md`](03-screen-specs/SETTINGS_V0_1.md) — identity, appearance, rhythm, permissions, privacy, backup and companion controls
- [`03-screen-specs/WEEKLY_REVIEW_V0_1.md`](03-screen-specs/WEEKLY_REVIEW_V0_1.md) — truthful weekly evidence, carry-forward decisions and reflection
- [`03-screen-specs/CORE_DETAIL_SCREENS_V0_1.md`](03-screen-specs/CORE_DETAIL_SCREENS_V0_1.md) — shared behavior for Task, Project, Goal, Habit, Hobby/Skill, Journal, Idea and Session detail screens

## Design System

- [`04-design-system/DESIGN_LANGUAGE_V0_1.md`](04-design-system/DESIGN_LANGUAGE_V0_1.md) — Warm Personal Observatory visual direction
- [`04-design-system/COLOR_TYPOGRAPHY_V0_1.md`](04-design-system/COLOR_TYPOGRAPHY_V0_1.md) — working semantic light/dark palette and typography direction
- [`04-design-system/COMPONENT_SYSTEM_V0_1.md`](04-design-system/COMPONENT_SYSTEM_V0_1.md) — reusable premium UI rules for cards, buttons, inputs, rows, navigation, sheets, dialogs and empty states
- [`04-design-system/APP_ICON_BRAND_V0_1.md`](04-design-system/APP_ICON_BRAND_V0_1.md) — app icon, brand-mark, adaptive-icon and splash relationship
- [`04-design-system/MOTION_MICROINTERACTIONS_V0_1.md`](04-design-system/MOTION_MICROINTERACTIONS_V0_1.md) — premium motion language, timing bands, feedback, task completion and reduced-motion rules
- [`04-design-system/CHARACTER_SYSTEM_V0_1.md`](04-design-system/CHARACTER_SYSTEM_V0_1.md) — calm 2D companion-character purpose, art direction, interaction model, placement and staged implementation strategy

## Behavior Specifications

- [`05-behavior/TASKS_REMINDERS_V0_1.md`](05-behavior/TASKS_REMINDERS_V0_1.md) — task lifecycle, reminders, alarms, permissions, deep links and recovery
- [`05-behavior/PROJECTS_GOALS_V0_1.md`](05-behavior/PROJECTS_GOALS_V0_1.md) — project/goal distinction, next actions, milestones, measurable progress and history
- [`05-behavior/HABITS_ROUTINES_V0_1.md`](05-behavior/HABITS_ROUTINES_V0_1.md) — calm consistency, schedules, history, reminders and non-punitive streak philosophy
- [`05-behavior/HOBBIES_SKILLS_SESSIONS_V0_1.md`](05-behavior/HOBBIES_SKILLS_SESSIONS_V0_1.md) — hobbies/skills as first-class life areas with real practice/session evidence
- [`05-behavior/JOURNAL_IDEAS_MEMORIES_V0_1.md`](05-behavior/JOURNAL_IDEAS_MEMORIES_V0_1.md) — journal, reflection, ideas, memory preservation and privacy behavior

## Personal Intelligence

- [`06-intelligence/PERSONAL_INTELLIGENCE_V0_1.md`](06-intelligence/PERSONAL_INTELLIGENCE_V0_1.md) — explainable local-first focus selection, continuation, Morning Brief, weekly evidence and suggestion-vs-fact rules

## Data, Privacy & Backup

- [`07-data/DATA_MODEL_V0_1.md`](07-data/DATA_MODEL_V0_1.md) — working Room/DataStore domain model, relationships, search indexing, time semantics and migration rules
- [`07-data/PRIVACY_BACKUP_SECURITY_V0_1.md`](07-data/PRIVACY_BACKUP_SECURITY_V0_1.md) — local-first privacy, app-lock direction, backup/export/restore and security requirements

## Android Architecture

- [`08-android/ANDROID_ARCHITECTURE_V0_1.md`](08-android/ANDROID_ARCHITECTURE_V0_1.md) — native Kotlin/Compose architecture, Room/DataStore, reminders, voice, search, backup, testing and CI direction

## Quality & Acceptance

- [`09-qa/V1_ACCEPTANCE_TEST_PLAN_V0_1.md`](09-qa/V1_ACCEPTANCE_TEST_PLAN_V0_1.md) — end-to-end release acceptance, device/manual audit, migration safety, reminder tests, visual quality and companion checks

## Decisions

- [`decisions/DECISION_LOG.md`](decisions/DECISION_LOG.md) — durable product/architecture decisions

## Execution Readiness

- [`10-execution/BUILD_HANDOFF_READINESS.md`](10-execution/BUILD_HANDOFF_READINESS.md) — master checklist that determines when Astra can begin the end-to-end build

## Status labels

### Working draft
Direction has been written down but may still change during product review.

### Approved
The user has approved the direction, but connected documents may still need detail.

### Frozen for implementation
The specification is authoritative for the build model. Changing it should require an explicit decision-log entry or version update.

## Rule for Astra/Work
Astra should eventually begin with this INDEX, then read the complete frozen specification set before writing production code.
