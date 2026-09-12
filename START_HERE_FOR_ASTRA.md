# START HERE — Personal OS Astra Build Handoff

**Status:** AUTHORIZED FOR IMPLEMENTATION  
**Primary executor:** Work — Astra Medium  
**Repository:** `rayadinaveen98-ship-it/personal-os`

This file is the single entrypoint for the implementation session.

## 1. Mission

Build **Personal OS** from scratch as the complete native Android product defined by this repository.

Do **not** stop at analysis, planning, scaffolding, static mockups, partial screens, or the first compiling APK.

The expected outcome is:
- complete source implementation in this repository
- working V1 behavior across the frozen product scope
- faithful premium visual implementation
- green required CI
- verified installable APK
- final QA/implementation report

## 2. Required reading order

Before writing production code:

1. Read `docs/INDEX.md`.
2. Follow its precedence/read-order rules.
3. Read the frozen product/route/screen/design/data/architecture/QA documents it references.
4. Read `docs/decisions/DECISION_LOG.md`.
5. Read `docs/10-execution/FINAL_CONSISTENCY_AUDIT_V1.md`.
6. Read `docs/10-execution/ASTRA_EXECUTION_BRIEF_V1.md` **last**.
7. Then begin implementation.

Do not infer major product behavior from this entrypoint alone. The repository specification is the contract.

## 3. Frozen product truths

Personal OS is:
- Android-first
- native Kotlin + Jetpack Compose
- local-first
- package `com.navin.personalos`
- built around **Today / Plan / central Capture / Journey / Me**
- built around **Capture → Understand → Act → Reflect → Progress**
- visually defined by **Warm Personal Observatory**
- branded with the **Minimal Leaf** launcher/splash direction
- supported by the **Tiny Observatory Friend** companion system

It must feel premium, rich, calm, peaceful, warm, personal and alive while remaining practical, accessible and production-realistic.

## 4. Non-negotiable truthfulness

Never ship:
- fabricated personal history
- fake task/project/goal progress
- fake streaks
- fake achievements
- decorative controls that imply unsupported actions
- static tabs/buttons pretending to work
- generic Material-demo styling as a substitute for the design contract
- destructive production Room migrations

Every visible interactive element must perform its documented behavior.

## 5. Engineering autonomy

Proceed autonomously on low-level implementation choices when they preserve the frozen product contract.

Do not repeatedly ask the founder to re-decide questions already resolved in the repository.

Only stop for:
- a genuinely unavailable required external dependency/credential, or
- a real specification conflict that cannot be resolved using the documented precedence rules.

Before reporting any blocker, preserve completed work in Git.

## 6. Git / CI rule

Keep implementation work in this repository throughout execution.

Use meaningful commits and keep `main` in a recoverable state.

Do not weaken/delete frozen specs or tests merely to make CI green.

The existing specification-validation workflow must remain green.

Add Android CI for the implementation as defined by the execution brief, including:
- unit tests
- lint/static checks
- debug APK assembly
- APK artifact upload

## 7. Milestone APK rule

At meaningful implementation milestones, produce installable debug APK artifacts whenever technically possible.

A milestone is not complete merely because source was committed.

## 8. Final delivery contract

Before declaring V1 complete, deliver:
- source on `main`
- green required CI
- installable APK
- `versionName`
- `versionCode`
- exact APK size
- APK SHA-256
- archive/APK integrity verification
- implementation summary
- QA summary
- known limitations, if any
- updated asset/license provenance
- confirmation that no fake personal history ships as real user data

## 9. Launch instruction

> **Read this repository completely according to the required reading order, then execute `docs/10-execution/ASTRA_EXECUTION_BRIEF_V1.md` end-to-end. Build Personal OS from scratch, keep GitHub current, implement the full frozen V1 contract, run and repair tests/CI, produce milestone APKs where practical, and continue until a verified final APK and QA report are delivered. Do not stop at planning.**

If implementation details are underspecified, prefer the smallest production-safe choice that preserves the documented product philosophy, data truthfulness, accessibility and premium visual identity.