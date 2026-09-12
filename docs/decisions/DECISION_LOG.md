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

**Decision:** Android first, native.

**Initial stack direction:** Kotlin + Jetpack Compose.

**Why:** The product needs deep Android behavior such as reminders, notifications, reboot recovery, speech recognition, local storage, and polished mobile UX.

**Status:** Locked at product level; exact versions will be frozen later.

---

## D-007 — Local-first philosophy

**Decision:** Core Personal OS functionality must be useful without a cloud account, paid AI API, or constant internet connection.

**Why:** Reliability, privacy, ownership, and cost control are central to a personal operating system.

**Status:** Locked.

---

## D-008 — Core loop

**Decision:** The product loop is:

> **Capture → Understand → Act → Reflect → Progress**

**Status:** Locked.

---

## D-009 — Top-level mental model

**Decision:** The working experiential structure is:

- Today
- Plan
- Capture
- Journey
- Me

This is a mental model, not yet the final route hierarchy. Phase 1 will decide exact navigation and detail routes.

**Status:** Direction locked; route implementation not frozen.

---

## D-010 — Design identity

**Decision:** Working visual identity is **Warm Personal Observatory**.

**Characteristics:** warm, premium, quiet, editorial, personal, production-realistic, lightly cinematic.

**Status:** Direction locked; full design system not yet frozen.

---

## D-011 — No fabricated personal data

**Decision:** Personal OS must never display fake progress, fake memories, fake achievements, fake activity, fake streaks, fake project movement, or fake user history as if it were real.

**Why:** Trust is foundational to the product.

**Status:** Non-negotiable.

---

## D-012 — Interaction truthfulness

**Decision:** Anything that looks interactive must perform a documented action. Unimplemented features must not masquerade as completed controls.

**Status:** Non-negotiable.

---

## D-013 — Notification philosophy

**Decision:** Notifications should serve user-created intent, not manufacture engagement.

Examples allowed:

- reminders
- explicitly enabled routines
- optional morning brief
- optional evening reflection
- time-sensitive commitments

Generic “come back to the app” notifications are not part of the product philosophy.

**Status:** Locked.

---

## D-014 — No destructive production migrations

**Decision:** Production database upgrades must use explicit migrations or another data-preserving strategy. Destructive migration is unacceptable for journal/history data.

**Status:** Non-negotiable.

---

## D-015 — Build definition of done

**Decision:** A successful compile or APK generation is not sufficient to call a feature complete.

Feature completion requires behavior, persistence, navigation, states, permissions where relevant, tests, and approved UI fidelity.

**Status:** Locked.

---

## D-016 — Premium experience is a product requirement

**Decision:** Personal OS must deliver a premium, rich, calm, peaceful, emotionally warm experience across the complete product — from app icon and launch through setup, Today, deep screens, transitions, empty states and success states.

**Why:** Personal OS is intended to feel like a private digital life space, not a generic productivity utility.

**Implication:** Generic stock-Material presentation is not acceptable as the final visual result. Visual polish and interaction quality are part of the definition of done.

**Status:** Locked founder direction.

---

## D-017 — Calm 2D companion system

**Decision:** Personal OS should be designed to support a small calm 2D companion-character layer. If technically feasible without weakening core reliability, at least one tap-reactive companion should appear on selected high-value surfaces such as Welcome, onboarding, Today, empty states and reflection moments.

**Behavior:** The companion may idle gently, react to taps with varied short actions, respond subtly to meaningful context such as task completion or evening reflection, and then return to idle.

**Constraints:** It must remain secondary to utility, never block workflows, never carry essential information alone, and must not make the product feel childish or noisy.

**Status:** Direction locked; exact character form and animation technology not yet frozen.
