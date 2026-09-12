# Personal OS — Astra Execution Brief Draft v0.1

**Status:** DRAFT ONLY — NOT YET AUTHORIZED FOR FULL BUILD

This file is the skeleton of the final Work/Astra handoff. It must not be used as the final execution instruction until `BUILD_HANDOFF_READINESS.md` says `READY FOR ASTRA EXECUTION`.

## 1. Mission
Build **Personal OS** from scratch as a native Android application that faithfully implements the frozen specification package in this repository.

The app must feel premium, rich, calm, peaceful, personal, and emotionally warm while remaining fast, reliable, truthful and production-realistic.

## 2. First action before coding
Read, in this order:
1. `docs/INDEX.md`
2. all Foundation documents
3. Information Architecture
4. UX flows
5. all Screen Specs
6. Design System + visual-reference manifest
7. Behavior specs
8. Personal Intelligence
9. Data/privacy/schema docs
10. Android architecture/build baseline
11. QA acceptance plan
12. Decision Log
13. this execution brief last

Do not begin implementation after reading only a subset.

## 3. Product constraints
Non-negotiable:
- no fake personal data
- no fake progress or achievements
- no dead/static controls styled as interactive
- no destructive production migration fallback
- no cloud dependency required for the core daily loop
- no ad/engagement-bait behavior
- no punitive habit/streak language
- no generic Material demo aesthetic
- no hidden arbitrary product redesign

## 4. Experience requirement
The implementation must feel like a **Warm Personal Observatory** rather than a productivity template.

Visual polish is part of Definition of Done.

The final implementation must cover:
- app icon/adaptive icon
- launch/splash
- welcome/setup
- Today
- Plan
- Capture
- Journey
- Me
- all V1 detail flows
- empty/loading/error states
- light + dark theme
- tactile motion/micro-interactions
- calm companion-character support according to the approved visual package

## 5. Build approach
Recommended implementation checkpoints:
1. Android project foundation + design tokens + navigation + persistence shell
2. launch/onboarding/personalization
3. domain schema/repositories/migrations
4. Today/Plan/Capture core daily loop
5. Project/Goal/Task/Reminder details
6. Journey/Journal/Idea/Memory/Chapter
7. Me/Life Areas/Hobbies/Skills/Habits
8. Search/Weekly Review/Personal Intelligence
9. reminders/permissions/recovery
10. privacy/app lock/backup/export
11. dark mode/companion/motion/accessibility polish
12. full QA + RC

Do not build all UI first and postpone persistence/behavior until the end.

## 6. Android baseline
Use the frozen/current baseline in `docs/08-android/ANDROID_BUILD_BASELINE_V0_1.md`, rechecking only if the final brief explicitly permits a version refresh.

Working package ID: `com.navin.personalos`.

## 7. Data safety
- Room schema version starts clean at 1.
- Explicit migrations for every production schema bump.
- Migration tests required.
- Backup/restore must validate data before destructive replacement.
- App lock uses secure Android platform APIs.

## 8. Reminders
Implement actual Android reminder behavior, including:
- runtime notifications permission where required
- exact-alarm capability/repair when required
- AlarmManager scheduling
- reboot/package/time-change reconciliation where applicable
- WorkManager for deferred reconciliation
- deep-link to the relevant entity
- recurring rules from the recurrence specification

A reminder feature is not complete merely because a row can be saved in Room.

## 9. Universal Capture
Capture must remain fast.
Implement deterministic/local interpretation first for common V1 language.
The user must be able to inspect and correct structured fields before save.
Voice capture must degrade gracefully if speech recognition is unavailable/denied.

## 10. Companion character
Do not invent a random mascot.
Use only approved companion assets/direction in the visual-reference package.

Companion behavior:
- idle
- short tap responses
- return to idle
- contextual morning/evening/success/reflection states when approved
- no workflow dependency
- reduced-motion respect

If an approved animation asset cannot be reproduced safely, implement the closest production-realistic supported subset and document the limitation rather than substituting an unrelated character.

## 11. Testing contract
At minimum:
- unit tests for deterministic logic
- parser/classifier tests
- recurrence tests
- repository/domain tests
- migration tests
- reminder scheduling/reconciliation tests where possible
- Compose/UI tests for critical interactions
- manual acceptance checklist from `docs/09-qa/V1_ACCEPTANCE_TEST_PLAN_V0_1.md`

## 12. CI contract
GitHub Actions must become green.
Required final pipeline:
- spec validation
- test
- lint/static checks
- assembleDebug
- upload APK

Do not report success while CI is red.

## 13. Git behavior
Keep the repository updated throughout the build.
Use meaningful commits.
Do not leave the real work only in a temporary cloud workspace.

## 14. APK delivery
Every major Android milestone should produce an installable APK when technically possible.

Final delivery must include:
- APK file
- versionName
- versionCode
- APK SHA-256
- CI run/build result
- known limitations
- concise implementation audit

## 15. Blocker policy
Do not fake completion.

If blocked:
- identify exact blocker
- explain what has already been completed
- provide the safest next action
- preserve source state in Git

Do not quietly omit a requirement because it was difficult.

## 16. Product-decision policy
Low-level engineering decisions may be made autonomously if they preserve the frozen specs.

Do not autonomously change:
- core navigation mental model
- feature ownership
- user-facing semantics
- privacy philosophy
- data truthfulness
- visual identity
- companion identity
- acceptance requirements

Any necessary exception must be documented in `docs/decisions/DECISION_LOG.md` with rationale.

## 17. Final authorization placeholder
The final version of this section will explicitly say:

> **AUTHORIZED: Execute Personal OS end-to-end. Continue until the build satisfies the frozen specifications and acceptance contract, fixing implementation/CI issues encountered along the way.**

That authorization is intentionally absent from this draft.
