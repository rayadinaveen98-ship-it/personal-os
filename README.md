# Personal OS

**Personal OS** is an Android-first, local-first personal operating system designed to answer three questions every day:

1. **What am I working toward?**
2. **What should I do today?**
3. **Am I actually making progress?**

The product should feel like a private, intelligent life cockpit and journal rather than a corporate productivity dashboard.

## Current status

**Phase 0 — Product Foundation**

No production app should be implemented until the product, UX, behavior, design, data model, engineering rules, and acceptance contract are sufficiently frozen for a high-capability Work model to execute with minimal ambiguity.

## Source of truth

This repository is the authoritative source of truth for the Personal OS rebuild.

Anything that becomes a real product decision should be written here. Chat conversations can help us think, but the repository should contain the durable version of:

- product philosophy
- scope and non-goals
- information architecture
- UX flows
- screen specifications
- visual design system
- behavior rules
- data architecture
- Android architecture
- permissions and notification behavior
- acceptance criteria
- test strategy
- execution briefs
- build status and implementation decisions

When documents conflict, the most recently approved document in the relevant section wins. Architectural or product changes should also be recorded in the decision log.

## Product direction

Working tagline:

> **A second brain that actually knows what I’m trying to do with my life.**

Core experience:

> **Capture → Understand → Act → Reflect → Progress**

The app should gradually become more personal and useful as it accumulates truthful history. It must never fabricate personal progress, achievements, memories, scores, or activity.

## Build strategy

The app will be rebuilt from scratch rather than continuing the old Personal Life OS implementation. We may reuse lessons, validated design direction, and successful interaction patterns, but not inherit accidental architecture or placeholder behavior.

The intended implementation handoff is to a higher-capability Work model after the specification package is complete. Current target: **Astra Medium** for the main build, with higher effort reserved for unusually difficult architecture or debugging tasks if needed.

## Planned repository structure

```text
docs/
  00-foundation/
  01-information-architecture/
  02-ux-flows/
  03-screen-specs/
  04-design-system/
  05-feature-behavior/
  06-intelligence/
  07-data-architecture/
  08-android-architecture/
  09-quality-and-testing/
  10-execution/
  decisions/
```

The folders will be populated as each layer is researched, discussed, and frozen.

## Non-negotiable implementation rule

A screen is **not complete** because it renders or compiles. Every visible control must either perform its documented behavior or be intentionally and clearly non-interactive. No fake buttons, fake metrics, fake memories, fake achievements, fake progress, or decorative controls that imply functionality.

## Security rule

This repository is public. Never commit API keys, credentials, private tokens, passwords, personal secrets, signing secrets, private account data, or other sensitive material.
