# Personal OS — Decision Log

**V1 freeze updated:** 2026-09-12

This is the durable decision record for Personal OS. Later numbered decisions supersede older working-status wording where necessary.

## D-001 — Product name
**Decision:** The clean rebuild is named **Personal OS**.  
**Status:** Locked.

## D-002 — Rebuild strategy
**Decision:** Build from scratch rather than continue the previous Personal Life OS codebase. Reuse validated lessons/specs, not accidental architecture/static placeholder behavior.  
**Status:** Locked.

## D-003 — Repository source of truth
**Decision:** `rayadinaveen98-ship-it/personal-os` is the authoritative source of truth. The project must be recoverable without chat history.  
**Status:** Locked.

## D-004 — Specification before implementation
**Decision:** Major product/UX/data decisions are frozen before the autonomous implementation handoff.  
**Status:** Satisfied for V1 handoff.

## D-005 — Primary build model
**Decision:** Use **Astra Medium in Work** for the main end-to-end Android implementation. Higher effort is reserved for genuine hard blockers if necessary.  
**Status:** Locked for handoff.

## D-006 — Platform
**Decision:** Android first, native Kotlin + Jetpack Compose.  
**Status:** Locked.

## D-007 — Local-first core
**Decision:** Core daily functionality works without a cloud account, paid AI API or permanent internet connection.  
**Status:** Locked.

## D-008 — Core loop
**Decision:** **Capture → Understand → Act → Reflect → Progress.**  
**Status:** Locked.

## D-009 — Primary mental model
**Decision:** Today / Plan / central Capture / Journey / Me.  
**Status:** Locked. Exact destinations are frozen in `ROUTE_HIERARCHY_FROZEN_V1.md`.

## D-010 — Visual identity
**Decision:** **Warm Personal Observatory** — premium, calm, intimate, editorial, warm, production-realistic and lightly cinematic.  
**Status:** Frozen with `VISUAL_REFERENCE_MANIFEST.md` + `DESIGN_TOKENS_V1_FROZEN.md`.

## D-011 — No fabricated personal data
**Decision:** Never present fake progress, memories, activity, achievements, streaks, project movement or history as user truth.  
**Status:** Non-negotiable.

## D-012 — Interaction truthfulness
**Decision:** Anything styled as interactive must perform a documented real action.  
**Status:** Non-negotiable.

## D-013 — Notification philosophy
**Decision:** Notifications serve user-created intent: reminders, explicitly enabled routines/brief/reflection and time-sensitive commitments. No engagement bait.  
**Status:** Locked.

## D-014 — Migration safety
**Decision:** Explicit data-preserving Room migrations. `fallbackToDestructiveMigration()` is forbidden for production user data.  
**Status:** Non-negotiable.

## D-015 — Definition of Done
**Decision:** Compilation/APK alone is insufficient. Done requires behavior, persistence, navigation, permissions, tests, approved visual fidelity, green CI and verified APK.  
**Status:** Locked.

## D-016 — Premium experience requirement
**Decision:** Rich/premium/calm quality is required from icon and splash through setup, Today, deep screens, empty states, transitions and dark mode. Generic Material-demo appearance is unacceptable.  
**Status:** Locked founder direction.

## D-017 — Calm 2D companion
**Decision:** Personal OS includes a subtle tap-reactive 2D companion layer where it improves warmth without harming utility.  
**Status:** Locked.

## D-018 — App Lock
**Decision:** Optional local App Lock is in V1 using BiometricPrompt/device credential semantics. Default off.  
**Status:** Locked.

## D-019 — Attachments
**Decision:** V1 supports images/general files for selected domains through Android-safe picker/storage semantics. No OCR/cloud/media-suite requirement.  
**Status:** Locked.

## D-020 — Memories and Chapters
**Decision:** Memory is first-class. Chapters are explicit user-created/named periods with user-controlled membership; intelligence can suggest but cannot silently author autobiographical facts.  
**Status:** Locked.

## D-021 — Recurrence without punishment debt
**Decision:** Missed habits do not accumulate guilt/debt. Recurring tasks do not produce unlimited overdue copies.  
**Status:** Locked.

## D-022 — Android identity/toolchain
**Decision:** applicationId `com.navin.personalos`; minSdk 26; compileSdk 37; targetSdk 36; JDK 17; stable dependencies only by default. Exact versions are frozen in `ANDROID_BUILD_BASELINE_FROZEN_2026_09_12.md`.  
**Status:** Frozen after pre-build recheck on 2026-09-12.

## D-023 — Companion identity
**Decision:** **Tiny Observatory Friend** — small human-like 2D companion, soft dark hair, minimal calm facial features, sage/moss clothing, warm low-contrast editorial style; curious, kind, quiet and slightly playful when invited.  
**Interaction:** short rotating tap reactions, contextual morning/evening/reflection/completion states, always returns to idle, respects reduced motion.  
**Status:** Identity frozen. Production runtime assets are implementation work.

## D-024 — App icon identity
**Decision:** **Minimal Leaf** — two simple leaves on one centered stem, restrained moss/forest foreground on warm ivory/cream. Same mark drives launcher and splash; companion is not the launcher logo.  
**Status:** Direction frozen. Production adaptive/monochrome vectors are implementation work.

## D-025 — Visual-reference freeze
**Decision:** The staged Canva/reference package is frozen as the V1 implementation target after the founder repeatedly instructed continuation past each review gate. Static visuals yield to accessibility, runtime behavior, data truthfulness and frozen semantic tokens where necessary.  
**Source:** `VISUAL_REFERENCE_MANIFEST.md`.  
**Status:** Frozen.

## D-026 — V1 route hierarchy
**Decision:** Exact semantic Android navigation hierarchy is frozen in `ROUTE_HIERARCHY_FROZEN_V1.md`; Plan modes are Today / Week / Projects.  
**Status:** Frozen.

## D-027 — V1 data schema
**Decision:** V1 entity set, foreign-key policy, recurrence exceptions, SearchDocument/FTS strategy, archive/delete semantics and migration policy are frozen in `DATA_SCHEMA_V1_FROZEN.md`.  
**Status:** Frozen.

## D-028 — Typography and semantic design tokens
**Decision:** Manrope is primary UI sans; Newsreader is selective reflective serif. Frozen light/dark colors, contrast rules, geometry, touch targets and motion are defined in `DESIGN_TOKENS_V1_FROZEN.md`.  
**Status:** Frozen.

## D-029 — Asset provenance
**Decision:** Shipping icon/character assets must be project-owned recreations from the frozen identity or explicitly licensed. Manrope and Newsreader use SIL OFL 1.1. No ambiguous stock/Canva asset may be shipped without recorded rights.  
**Source:** `ASSET_LICENSE_MANIFEST_V1.md`.  
**Status:** Locked.

## D-030 — Handoff interpretation rule
**Decision:** Earlier v0.1 docs are preserved as design history. They cannot reopen questions explicitly resolved by later frozen V1 documents.  
**Precedence:** this Decision Log → frozen V1 docs → Visual Manifest/Design Tokens → Screen Spec Resolutions → working feature specs → old drafts/reference composition.  
**Status:** Locked.

## Change policy
Any post-handoff change to a frozen product decision must be recorded here with a new decision number and rationale. Low-level implementation choices that preserve the frozen contract do not require founder approval.
