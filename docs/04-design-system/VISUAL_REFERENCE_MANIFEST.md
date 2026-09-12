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
| Welcome | Pending | — | Premium calm entry; first controlled appearance of Tiny Observatory Friend. |
| Setup — what matters | Pending | — | Selectable intent cards; companion may observe/respond subtly. |
| Setup — life areas | Pending | — | Personal world setup; character remains secondary. |
| Setup — first real item | Pending | — | First project/goal/task path. |
| Setup completion | Pending | — | Quiet rewarding transition; companion welcome/wave state. |
| Today — morning populated | Pending | — | Primary visual benchmark; morning companion state. |
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
- `CHARACTER_SYSTEM_V0_1.md` (now updated to Tiny Observatory Friend direction)
- `decisions/DECISION_LOG.md` → D-023

No other companion concept should be substituted during implementation without an explicit design decision update.

## Rule
Astra must not infer final visual styling from textual prose alone once this manifest contains approved references. Approved references become implementation targets together with the written specs.
