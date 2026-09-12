# Personal OS — Astra Medium Execution Brief V1

**Status:** FINAL / AUTHORIZED  
**Date:** 2026-09-12  
**Repository:** `rayadinaveen98-ship-it/personal-os`  
**Execution target:** Work — Astra Medium

# AUTHORIZATION

> **AUTHORIZED: Execute Personal OS end-to-end. Continue until the build satisfies the frozen specifications and acceptance contract, fixing implementation and CI issues encountered along the way.**

Do not stop at planning, scaffolding, mockups, partial screens, or the first compiling APK. The required outcome is a working native Android implementation in this repository with green required CI and a verified installable APK.

## 1. Mission

Build **Personal OS** from scratch. It is a calm, premium personal operating system for planning, living, remembering, reflecting, and growing—not a generic task manager.

Core loop: **Capture → Understand → Act → Reflect → Progress**.

Primary mental model: **Today / Plan / central Capture / Journey / Me**.

The experience must feel premium, rich, calm, peaceful, personal, warm, and alive while remaining fast, truthful, accessible, and production-realistic.

## 2. Read the repository before coding

Read `docs/INDEX.md` first. Then read the complete relevant specification package, including Foundation, UX flows, Screen Specs, Behavior, Intelligence, Data/Privacy, Android Architecture, QA, Decision Log, and visual-reference package. Read this execution brief last.

Frozen decision files have precedence over historical v0.1 questions. Follow the precedence order in `docs/INDEX.md` and `docs/decisions/DECISION_LOG.md`.

Critical frozen files include:
- `docs/01-information-architecture/ROUTE_HIERARCHY_FROZEN_V1.md`
- `docs/03-screen-specs/SCREEN_SPEC_RESOLUTIONS_V1_FROZEN.md`
- `docs/04-design-system/VISUAL_REFERENCE_MANIFEST.md`
- `docs/04-design-system/DESIGN_TOKENS_V1_FROZEN.md`
- `docs/04-design-system/ASSET_LICENSE_MANIFEST_V1.md`
- `docs/07-data/DATA_SCHEMA_V1_FROZEN.md`
- `docs/08-android/ANDROID_BUILD_BASELINE_FROZEN_2026_09_12.md`
- `docs/09-qa/RC_DEVICE_MATRIX_V1.md`
- `docs/10-execution/FINAL_CONSISTENCY_AUDIT_V1.md`

## 3. Non-negotiables

Never ship:
- fabricated personal data, history, progress, achievements, or streaks
- controls that look interactive but do nothing
- destructive production migration fallback
- a paid cloud/AI dependency for the core daily loop
- engagement bait or punitive habit language
- generic Material-demo styling in place of the frozen visual identity
- an unrelated mascot or launcher icon
- silent product redesigns made for engineering convenience

Every visible interactive control must have real documented behavior.

## 4. Clean rebuild

Do not use the old Personal Life OS codebase as the implementation base. This is a clean project under:

`com.navin.personalos`

The old prototype contributed lessons/specification only.

## 5. Android baseline

Use the frozen build baseline. Start from:
- minSdk 26
- compileSdk 37
- targetSdk 36
- JDK 17
- AGP 9.4.0
- Gradle 9.6.0
- Kotlin 2.4.20
- KSP 2.3.12
- Compose BOM 2026.08.00
- the stable AndroidX/Hilt versions listed in the frozen baseline

One official-source compatibility check is allowed before coding. Do not broadly refresh dependencies just because something newer exists. Any necessary stable adjustment must be minimal and recorded in the Decision Log.

## 6. Architecture

Use native Kotlin + Jetpack Compose with pragmatic production architecture:
- Hilt
- ViewModel + StateFlow
- Room / SQLite
- DataStore
- Navigation Compose
- AlarmManager
- WorkManager
- NotificationManager
- SpeechRecognizer
- BiometricPrompt/device credential
- Android-safe picker/storage semantics for attachments

Do not create unnecessary Gradle-module complexity merely for architecture theatre.

## 7. Data

Implement `DATA_SCHEMA_V1_FROZEN.md` faithfully. Critical rules:
- Room schema begins at version 1
- UUID/String durable IDs
- epoch millis for instants and local-date semantics for calendar grouping
- first-class Reminder
- RecurrenceRule + RecurrenceException
- first-class Memory + Chapter
- separate Hobby / Skill / Session
- explicit LifeArea
- SearchDocument + FTS search layer
- archive/delete semantics as frozen
- explicit migrations for every post-release schema bump
- `fallbackToDestructiveMigration()` forbidden in production

Do not replace the structured domain with a generic JSON store.

## 8. Navigation

Implement the frozen semantic graph. Primary nav is Today / Plan / Capture / Journey / Me. Plan modes are Today / Week / Projects.

Specific cards, search results, notifications, and timeline items deep-link to the exact entity detail when one exists. Task completion is separate from row navigation. Back stacks must remain sensible for edit/create/deep-link flows.

## 9. First-run experience

Implement: **Splash → Welcome → What matters → Life Areas → first real item → personalization/rhythm → completion → Today**.

It must:
- respect system insets
- work on compact devices and large font scales
- feel like a guided conversation rather than a settings form
- create real selected data, not demo history
- enter Today with real personalized context

## 10. Today / Plan / Capture

### Today
Use real persisted data for name, time-aware greeting, date, optional Morning Brief, primary focus, tasks/reminders, project continuation, habits/routines when relevant, recent real context, and Evening Reflection entry.

Pinned focus outranks deterministic suggestions. Empty Today remains premium without invented work.

### Plan
Implement real Today / Week / Projects behavior, real tasks/reminders/recurrence, project/milestone/next-action state, separate completion controls, detail navigation, add/edit/reopen/reschedule actions, and calm overdue treatment.

### Capture
Implement fast deterministic/local interpretation of common task/reminder/date/time/priority/recurrence/duration/project/life-area/journal/idea/session language. Voice is optional and degrades gracefully. Every inferred field must be correctable before save. Save exactly once.

## 11. Reminders

A Room row is not a completed reminder feature. Implement runtime notification permission, exact-alarm capability/repair, AlarmManager scheduling, WorkManager reconciliation, reboot/package/time/timezone recovery where required, update/delete cleanup, recurrence, and deep links.

If delivery cannot be scheduled, UI must say so truthfully and offer repair. Never claim success merely because persistence succeeded.

## 12. Journey / Me / details

### Journey
Real date navigation, journals, chronological activity, completed-work evidence, ideas/memories, Timeline/Archive, explicit Chapters, Weekly Review, contextual writing/reflection. No fake feed/history.

### Me
Real user identity, Life Areas, active project/goal context, Hobbies/Skills/Sessions evidence, grounded metrics only, and Settings gateway. No seeded fake hours/percentages/achievements.

### Detail screens
Implement functioning detail/edit flows for Task, Reminder where needed, Project, Goal, Habit, Hobby, Skill, Session, Journal, Idea, Memory, Chapter, Life Area, and milestones. Back/edit/archive/delete/link/history controls must be real and safe.

## 13. Search and intelligence

Implement local SearchDocument/FTS search for the frozen V1 domains and return entity type/id for exact navigation.

V1 Personal Intelligence is deterministic, local, and explainable first. Implement Today prioritization, focus suggestion, carry-forward, next action, project resumption, inactive context, weekly evidence, and Capture rules from `docs/06-intelligence/`.

Do not invent pseudo-insights.

## 14. Visual system

Use `VISUAL_REFERENCE_MANIFEST.md` for screen targets and `DESIGN_TOKENS_V1_FROZEN.md` as semantic implementation truth.

Implement real Compose UI, not screenshots. Preserve:
- Warm Personal Observatory identity
- Warm Ivory / Soft Cream / Moss Sage system
- intentional warm dark mode
- Manrope operational typography
- Newsreader selective reflective typography
- frozen spacing/radius/touch targets
- subtle borders/shadows and quiet motion
- responsive text and scrolling

Accessibility/runtime truth outranks pixel-copying a static reference when they conflict.

## 15. Brand and companion assets

Create production-ready **Minimal Leaf** adaptive/monochrome/splash assets from the frozen identity.

Create/recreate the **Tiny Observatory Friend** runtime state set from the frozen character model:
- idle
- wave/tap hello
- curious/thinking
- subtle completion
- reading/learning
- focused/work
- reflective/evening
- sleepy

Tap reactions are short, varied, return to idle, respect reduced motion, and never block workflow. Dense operational screens normally omit the companion.

Do not substitute stock mascot art. Record final asset provenance in `ASSET_LICENSE_MANIFEST_V1.md`.

## 16. Settings / privacy / backup / attachments

Implement System/Light/Dark, preferred name, Morning Brief, Evening Reflection, companion enable/disable, reduced motion behavior, notification repair, optional App Lock, privacy mode, backup/export/restore, and attachment semantics from the specs.

App Lock uses BiometricPrompt/device credential. Backup restore validates before destructive replacement. Core user data is not silently uploaded to third parties.

## 17. Accessibility / RC

Meet `RC_DEVICE_MATRIX_V1.md`:
- compact ~360dp, reference ~390dp, larger phone widths
- Android font scaling 1.0× / ~1.3× / ~1.5×
- Light / Dark / System
- reduced motion
- offline/airplane mode
- permission denial/repair
- reboot/time/timezone recovery
- app-lock/privacy states

Use >=48dp touch targets and semantic accessibility labels. No priority/status by color alone.

## 18. Implementation sequence

Recommended checkpoints:
1. Gradle/project foundation + spec CI + Android CI + design tokens + navigation shell
2. Room schema/repositories + DataStore
3. icon/splash/onboarding
4. Today + Plan + Task/Reminder core
5. Capture + parser + voice
6. Project/Goal/Habit
7. Journey/Journal/Idea/Memory/Chapter/Timeline
8. Me/Life Areas/Hobbies/Skills/Sessions
9. Search + Weekly Review + Personal Intelligence
10. reminders/permissions/recovery hardening
11. privacy/app-lock/backup/attachments
12. dark mode + companion + motion/accessibility polish
13. full QA/RC

Do not build all UI against fake repositories and postpone persistence until the end.

## 19. Tests and CI

Implement and run appropriate tests for deterministic rules, Capture parsing, recurrence, repositories/domain, search/FTS, exactly-once save, reminder/reconciliation logic, permission-state behavior, navigation, and critical Compose interactions.

Every future Room schema bump requires migration tests.

Final required GitHub Actions pipeline:
- specification validation
- unit tests
- lint/static checks
- assembleDebug
- upload APK

Do not report success while required CI is red.

## 20. Git and autonomy

Keep real work in this repository throughout execution. Use meaningful commits. Do not delete/alter frozen specs to make implementation easier.

Proceed autonomously on low-level engineering choices that preserve the frozen product contract. Do not repeatedly ask for implementation decisions already answered by the repo.

Only stop for a genuine unavailable external dependency/credential or an irreconcilable spec conflict that precedence rules cannot resolve. Preserve all completed work in Git before reporting a blocker.

## 21. Milestone and final APK contract

Produce installable debug APKs at meaningful milestones whenever technically possible.

Final delivery must include:
- source on `main`
- green required CI
- installable APK
- versionName and versionCode
- exact APK size
- APK SHA-256
- archive-integrity check
- implementation/QA summary
- known limitations, if any
- updated asset/license manifest
- confirmation that no fake personal history ships as real user data

## 22. No fake completion

Do not silently omit difficult requirements. Do not leave placeholder buttons. Do not claim reminder support without real delivery scheduling, backup without restore validation, App Lock without privacy testing, or visual fidelity while using generic scaffolding.

Before declaring V1 ready, audit every screen and ask:

> **Does every visible control do something real, does every displayed personal fact come from real stored data, and does this actually feel like the premium calm Personal OS defined in the repository?**

If the answer is no anywhere important, continue working.

# FINAL EXECUTION INSTRUCTION

**Build Personal OS now from this repository and carry the implementation through to a verified APK. Do not stop at planning. Keep GitHub current, make CI green, fix failures, honor the frozen product and visual contract, and deliver the highest-fidelity production-realistic implementation possible.**
