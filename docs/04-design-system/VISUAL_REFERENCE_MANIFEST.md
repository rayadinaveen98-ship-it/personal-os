# Personal OS — Visual Reference Manifest

**Status:** Visual freeze in progress.

This file records the exact visual references Astra must use during implementation.

## Required fields for every approved reference
- reference name
- purpose
- design source/tool
- editable design ID/link if available
- exported preview path if stored in repo
- light/dark mode
- companion state if present
- approval status
- approval date
- implementation notes

## Manifest

| Reference | Status | Source / ID | Notes |
|---|---|---|---|
| Companion direction — Tiny Observatory Friend | **APPROVED DIRECTION** | Concept B selected by founder on 2026-09-12 | Human-like calm 2D companion; dark soft hair, sage clothing, warm low-contrast editorial style. Canonical model/pose sheet still pending visual freeze. |
| App icon / adaptive icon | Pending | — | Must work at Android launcher sizes and monochrome treatment; should harmonize with Warm Personal Observatory and companion world without becoming a character portrait icon by default. |
| Launch / splash | Pending | — | Must connect brand mark to Welcome without a long forced animation. |
| Welcome | **CANDIDATE — REVIEW REQUIRED** | Canva `DAHU9JyFTdo` · Edit: https://www.canva.com/d/_AsZGIIO09bwT8b · View: https://www.canva.com/d/mpngUsQ_mvlvveT · Source: `reference-html/personal_os_welcome_reference_v0_1.html` | 390×844 production-realistic light reference. Warm Ivory/Cream/Sage, editorial headline, private-by-default copy, large calm Tiny Observatory Friend scene, one primary CTA. Not frozen until founder approves. |
| Setup — what matters | Pending | — | Selectable intent cards; companion may observe/respond subtly. |
| Setup — life areas | **CANDIDATE — REVIEW REQUIRED** | Canva `DAHU9MXQcdc` · Edit: https://www.canva.com/d/mDMsY-JGZe1eUWj · View: https://www.canva.com/d/To6EJOCo60dr8iK · Source: `reference-html/personal_os_setup_reference_v0_1.html` | 390×844 fixed-viewport setup reference with progress, two-column Life Area cards, sticky CTA, calm companion micro-presence and no score language. Not frozen until founder approves. |
| Setup — first real item | Pending | — | First project/goal/task path. |
| Setup completion | Pending | — | Quiet rewarding transition; companion welcome/wave state. |
| Today — morning populated | **CANDIDATE — REVIEW REQUIRED** | Canva `DAHU9EBBcv0` · Edit: https://www.canva.com/d/JWgA57nbddhZaa8 · View: https://www.canva.com/d/FdkoDXanUS449bb · Source: `reference-html/personal_os_today_reference_v0_1.html` | 390×844 primary Home benchmark. Time-aware greeting, real-data Morning Brief, one focus card, continuation cards, upcoming items and stable Today/Plan/Capture/Journey/Me nav. Companion is integrated but secondary. Not frozen until founder approves. |
| Today — truthful empty | Pending | — | Must remain rich without fabricated data; relaxed/quiet companion state. |
| Today — evening | Pending | — | Reflection and sleepy/reflective companion state. |
| Plan | Pending | — | Dense enough to be useful, still calm; minimal companion prominence. |
| Capture | Pending | — | Thought-first input + structured correction; companion generally absent from dense input state. |
| Journey | Pending | — | Editorial history/reflection. |
| Me | Pending | — | Personal identity/life structure. |
| Today — dark | Pending | — | Intentional dark design. |
| Journey — dark | Pending | — | Intentional dark design. |
| Component sheet | Pending | — | Buttons, rows, inputs, cards, nav, sheets, feedback. |
| Companion canonical model sheet | **IN PROGRESS** | Based on approved Concept B | Needs front/3-quarter identity, seated idle, wave, smile/blink, stretch, completion, morning, reflective/evening, empty-calm states. |
| Companion interaction pose sheet | Pending | — | Tap-response variations and contextual states after canonical model is frozen. |

## Companion implementation target

The approved character direction is defined in:
- `CHARACTER_SYSTEM_V0_1.md` (updated to Tiny Observatory Friend direction)
- `decisions/DECISION_LOG.md` → D-023

No other companion concept should be substituted during implementation without an explicit design decision update.

## Candidate-vs-approved rule

A Canva design being created does **not** mean it is frozen. Candidate references remain non-authoritative until the founder explicitly approves them. Once approved, update the row to **APPROVED / FROZEN VISUAL TARGET**, add the approval date, and treat both the Canva design and corresponding HTML source as implementation references.

## Rule
Astra must not infer final visual styling from textual prose alone once this manifest contains approved references. Approved references become implementation targets together with the written specs.
