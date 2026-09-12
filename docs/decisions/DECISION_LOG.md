# Personal OS — Decision Log

This file records product and architecture decisions that future contributors or Work sessions should not have to reconstruct from chat history.

## D-001 — Product name

**Decision:** The rebuilt app is named **Personal OS**.

**Why:** The product is intended to be a broader personal operating system, not merely a life organizer or task manager.

**Status:** Locked.

---

## D-002 — Rebuild strategy

**Decision:** Personal OS will be rebuilt from scratch rather than continuing the previous Personal Life OS codebase.

**Why:** The earlier prototype produced useful lessons and validated design direction, but also accumulated static UI, placeholder behavior, and architecture that should not define the new product.

**What may be reused:**
- validated product philosophy
- successful UX lessons
- approved visual direction
- known failure patterns
- feature behavior lessons
- Android implementation knowledge

**What should not be blindly copied:**
- old navigation structure
- placeholder metrics
- hard-coded user history
- accidental schemas
- static controls
- incomplete permission behavior

**Status:** Locked.

---

## D-003 — Repository source of truth

**Decision:** `rayadinaveen98-ship-it/personal-os` is the authoritative source of truth for the rebuild.

**Why:** The project must be recoverable from GitHub without relying on one conversation thread.

**Status:** Locked.

---

## D-004 — Implementation timing

**Decision:** Full production coding must not begin until the specification package is sufficiently complete.

**Why:** The goal is to hand a high-capability model an execution-ready product definition rather than ask it to invent major product decisions during implementation.

**Status:** Locked.

---

## D-005 — Primary build model

**Decision:** Target **Astra Medium** in Work for the main end-to-end implementation handoff.

**Why:** The project contains substantial interconnected Android architecture and UX behavior. Medium reasoning is the intended balance for a large software build; higher effort can be used selectively for unusually difficult problems.

**Status:** Working lock; may be revisited if model availability/capability changes before handoff.

---

## D-006 — Platform priority

**Decision:** Android first, native Kotlin + Jetpack Compose.

**Why:** The product needs deep Android behavior such as reminders, notifications, reboot recovery, speech recognition, local storage, and polished mobile UX.

**Status:** Locked.

---

## D-007 — Local-first philosophy

**Decision:** Core Personal OS functionality must be useful without a cloud account, paid AI API, or constant internet connection.

**Status:** Locked.

---

## D-008 — Core loop

**Decision:**

> **Capture → Understand → Act → Reflect → Progress**

**Status:** Locked.

---

## D-009 — Top-level mental model

**Decision:**
- Today
- Plan
- Capture
- Journey
- Me

**Status:** Direction locked; detailed routes are specified separately.

---

## D-010 — Design identity

**Decision:** Working visual identity is **Warm Personal Observatory**.

**Characteristics:** warm, premium, quiet, editorial, personal, production-realistic, lightly cinematic.

**Status:** Direction locked; final visual references still pending approval.

---

## D-011 — No fabricated personal data

**Decision:** Personal OS must never display fake progress, fake memories, fake achievements, fake activity, fake streaks, fake project movement, or fake user history as if it were real.

**Status:** Non-negotiable.

---

## D-012 — Interaction truthfulness

**Decision:** Anything that looks interactive must perform a documented action.

**Status:** Non-negotiable.

---

## D-013 — Notification philosophy

**Decision:** Notifications should serve user-created intent, not manufacture engagement.

Examples allowed:
- reminders
- explicitly enabled routines
- optional Morning Brief
- optional Evening Reflection
- time-sensitive commitments

**Status:** Locked.

---

## D-014 — No destructive production migrations

**Decision:** Production database upgrades require explicit data-preserving migrations. `fallbackToDestructiveMigration()` is forbidden for real user data.

**Status:** Non-negotiable.

---

## D-015 — Build definition of done

**Decision:** Compile/APK generation alone is insufficient. Completion requires behavior, persistence, navigation, states, permissions, tests, approved UI fidelity and a verified APK.

**Status:** Locked.

---

## D-016 — Premium experience is a product requirement

**Decision:** Personal OS must deliver a premium, rich, calm, peaceful, emotionally warm experience from icon/launch through setup, Today, deep screens, transitions, empty states and success states.

**Status:** Locked founder direction.

---

## D-017 — Calm 2D companion system

**Decision:** Personal OS supports a small calm 2D companion layer. At least one tap-reactive companion should appear on selected high-value surfaces such as Welcome, onboarding, Today, empty states and reflection moments when it can be implemented without weakening core reliability.

**Constraints:** Secondary to utility, never blocks workflows, never carries essential information alone, respects reduced motion, and must not feel childish/noisy.

**Status:** Locked.

---

## D-018 — App Lock is part of V1

**Decision:** V1 includes optional local app lock using Android BiometricPrompt/device credential behavior.

**Default:** Off.

**Privacy:** Locked state obscures personal content; notification detail privacy is user-selectable.

**Status:** Locked V1 decision.

---

## D-019 — Attachments are limited but real in V1

**Decision:** V1 supports images and general files through Android-safe picker/storage semantics for selected domains such as Journal, Memory, Idea and Project context.

**Not V1:** media-management suite, OCR, cloud upload, video/audio editing.

**Status:** Locked V1 scope.

---

## D-020 — Memories and Chapters are explicit, not invented

**Decision:** Memory is a first-class curated user record. Chapters are user-created/named periods with explicitly selected or linked real records.

Personal Intelligence may suggest creating a Chapter but cannot silently write autobiographical chapters as fact.

**Status:** Locked.

---

## D-021 — Recurrence must not create punishment debt

**Decision:** Recurring habits/routines record scheduled history; missed days do not accumulate as overdue debt. Recurring tasks surface the latest unresolved occurrence rather than producing unlimited overdue copies.

**Status:** Locked.

---

## D-022 — Android clean-rebuild identity and platform baseline

**Decision:** Working application ID is `com.navin.personalos`.

Freeze candidate as of 2026-09-12:
- minSdk 26
- compileSdk 37
- targetSdk 36 while Android 17/API 37 remains beta
- JDK 17
- stable dependency lines only by default

Versions must be rechecked immediately before Astra starts without opportunistically adopting preview dependencies.

**Status:** Working lock pending final pre-build version recheck.

---

## D-023 — Companion visual identity: Tiny Observatory Friend

**Decision:** The founder selected **Concept B — Tiny Observatory Friend** from the companion direction sheet on 2026-09-12.

**Visual identity:** A small, peaceful, human-like 2D companion with soft dark hair, calm minimal facial features, moss/sage clothing, warm low-contrast illustration, and a gentle editorial atmosphere.

**Personality:** curious, kind, quiet, patient, non-judgmental and slightly playful when invited.

**Usage:** Welcome, selected onboarding moments, Today, truthful empty states, setup completion, Evening Reflection and Weekly Review moments. It must remain subtle on dense functional screens.

**Interaction:** tap reactions rotate between short gestures such as wave, smile/blink, stretch, nod or curious look, then always return to idle. Contextual states may include morning, attentive/focus, completion, quiet day, reflective and sleepy/evening.

**Status:** Locked character direction. Canonical model/pose sheet still requires visual approval before implementation.
