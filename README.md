# Personal OS

**Personal OS** is an Android-first, local-first personal operating system designed to answer three questions every day:

1. **What am I working toward?**
2. **What should I do today?**
3. **Am I actually making progress?**

The product should feel like a private, intelligent life cockpit and journal rather than a corporate productivity dashboard.

## Current status

> **V1 PRE-BUILD PACKAGE FROZEN — READY FOR ASTRA EXECUTION**

The major product, UX, visual, behavior, data, Android architecture, privacy and acceptance decisions are now frozen sufficiently for autonomous implementation.

The final authorized build instruction is:

- `docs/10-execution/ASTRA_EXECUTION_BRIEF_V1.md`

Astra/Work must read `docs/INDEX.md` and the referenced frozen specification package before reading the final execution brief last.

## Source of truth

This repository is the authoritative source of truth for the Personal OS rebuild.

Durable product decisions live here, including:
- product philosophy and scope
- information architecture and exact V1 route hierarchy
- UX flows and screen specifications
- frozen visual references / Canva IDs
- frozen semantic design tokens
- behavior rules
- deterministic Personal Intelligence rules
- frozen Room/FTS data architecture
- Android build/toolchain baseline
- permissions, reminders and notification behavior
- privacy, App Lock, backup/export and attachments
- QA / RC device matrix
- execution contract and build status

Chat can be used for discussion, but implementation must follow the repository.

## Product direction

Tagline:

> **A second brain that actually knows what I’m trying to do with my life.**

Core loop:

> **Capture → Understand → Act → Reflect → Progress**

Primary mental model:

> **Today → Plan → Capture → Journey → Me**

The app becomes more personal as it accumulates truthful history. It must never fabricate personal progress, achievements, memories, scores, activity, or project movement.

## Experience identity

**Warm Personal Observatory**

Personal OS must feel:
- premium
- rich
- calm
- peaceful
- emotionally warm
- editorial and lightly cinematic
- production-realistic rather than concept-art-only

Frozen brand directions:
- **Minimal Leaf** app icon / splash identity
- **Tiny Observatory Friend** calm 2D companion identity

The frozen visual targets and Canva references are listed in:

- `docs/04-design-system/VISUAL_REFERENCE_MANIFEST.md`
- `docs/04-design-system/DESIGN_TOKENS_V1_FROZEN.md`

## Build strategy

This is a **clean rebuild from scratch** rather than a continuation of the previous Personal Life OS codebase.

Target implementation:
- Android native
- Kotlin + Jetpack Compose
- package: `com.navin.personalos`
- Room / DataStore
- Hilt
- Navigation Compose
- AlarmManager / WorkManager / notifications
- SpeechRecognizer
- BiometricPrompt
- local-first core

Primary execution model: **Astra Medium in Work**.

## Implementation authorization

The project is authorized for end-to-end build.

The final brief explicitly instructs Astra to continue through:
- project foundation
- persistence
- core flows
- all V1 screens
- reminder delivery/recovery
- privacy/App Lock/backup
- visual fidelity / dark mode / companion
- testing
- CI repair
- APK generation and verification

Do not stop at planning, mockups, scaffolding or a merely compiling APK.

## Non-negotiable implementation rules

A screen is **not complete** because it renders or compiles.

Every visible control must perform its documented action or be clearly non-interactive.

Never ship:
- fake buttons
- fake metrics
- fake memories
- fake achievements
- fake progress
- fake streaks
- fake project activity
- decorative controls that imply functionality
- destructive production Room migration fallback

## Repository map

Start at:

- `docs/INDEX.md`

Important frozen V1 files include:
- `docs/decisions/DECISION_LOG.md`
- `docs/01-information-architecture/ROUTE_HIERARCHY_FROZEN_V1.md`
- `docs/03-screen-specs/SCREEN_SPEC_RESOLUTIONS_V1_FROZEN.md`
- `docs/04-design-system/VISUAL_REFERENCE_MANIFEST.md`
- `docs/04-design-system/DESIGN_TOKENS_V1_FROZEN.md`
- `docs/04-design-system/ASSET_LICENSE_MANIFEST_V1.md`
- `docs/07-data/DATA_SCHEMA_V1_FROZEN.md`
- `docs/08-android/ANDROID_BUILD_BASELINE_FROZEN_2026_09_12.md`
- `docs/09-qa/RC_DEVICE_MATRIX_V1.md`
- `docs/10-execution/FINAL_CONSISTENCY_AUDIT_V1.md`
- `docs/10-execution/ASTRA_EXECUTION_BRIEF_V1.md`

## Security rule

This repository is public.

Never commit:
- API keys
- credentials
- private tokens
- passwords
- personal secrets
- signing keys
- private account data
- real private journal fixtures

The pre-build secrets audit is recorded in:
- `docs/10-execution/PRE_BUILD_SECRETS_AUDIT_2026_09_12.md`

## Definition of done

The final V1 build is not done until:
- required CI is green
- critical behavior is implemented and tested
- no important visible control is dead
- personal data shown is real/persisted
- frozen visual identity is faithfully implemented
- reminders are genuinely scheduled/delivered or truthfully report inability
- APK is generated and integrity-checked
- version metadata, exact size and SHA-256 are reported
