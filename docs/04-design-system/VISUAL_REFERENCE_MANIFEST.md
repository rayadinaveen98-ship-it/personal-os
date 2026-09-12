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
| Companion direction — Tiny Observatory Friend | **APPROVED DIRECTION** | Concept B selected by founder on 2026-09-12 | Human-like calm 2D companion; dark soft hair, sage clothing, warm low-contrast editorial style. Character remains secondary to utility. |
| App icon / adaptive icon — Minimal Leaf | **APPROVED / FROZEN DIRECTION** | Direction locked 2026-09-12 · Decision D-024 | Two simple leaves on one centered stem; moss/forest foreground on warm ivory/cream background. Must include adaptive foreground/background and monochrome/themed variants. Final vector geometry/export assets pending. |
| Launch / splash | **DIRECTION LOCKED** | Uses Minimal Leaf mark from D-024 | Splash must reuse the launcher mark, stay brief, and transition into Welcome without a separate logo identity or long forced animation. Final exact screen reference pending. |
| Welcome | **CANDIDATE — REVIEW REQUIRED** | Canva `DAHU9JyFTdo` · Edit: https://www.canva.com/d/_AsZGIIO09bwT8b · View: https://www.canva.com/d/mpngUsQ_mvlvveT · Source: `reference-html/personal_os_welcome_reference_v0_1.html` | 390×844 production-realistic light reference. Warm Ivory/Cream/Sage, editorial headline, private-by-default copy, large calm Tiny Observatory Friend scene, one primary CTA. |
| Setup — what matters | Pending | — | Selectable intent cards; companion may observe/respond subtly. |
| Setup — life areas | **CANDIDATE — REVIEW REQUIRED** | Canva `DAHU9MXQcdc` · Edit: https://www.canva.com/d/mDMsY-JGZe1eUWj · View: https://www.canva.com/d/To6EJOCo60dr8iK · Source: `reference-html/personal_os_setup_reference_v0_1.html` | 390×844 fixed-viewport setup reference with progress, two-column Life Area cards, sticky CTA, calm companion micro-presence and no score language. |
| Setup — first real item | Pending | — | First project/goal/task path. |
| Setup completion | Pending | — | Quiet rewarding transition; companion welcome/wave state. |
| Today — morning populated | **CANDIDATE — REVIEW REQUIRED** | Canva `DAHU9EBBcv0` · Edit: https://www.canva.com/d/JWgA57nbddhZaa8 · View: https://www.canva.com/d/FdkoDXanUS449bb · Source: `reference-html/personal_os_today_reference_v0_1.html` | 390×844 primary Home benchmark. Time-aware greeting, real-data Morning Brief, one focus card, continuation cards, upcoming items and stable Today/Plan/Capture/Journey/Me nav. Companion is integrated but secondary. |
| Today — truthful empty | Pending | — | Must remain rich without fabricated data; relaxed/quiet companion state. |
| Today — evening | Pending | — | Reflection and sleepy/reflective companion state. |
| Plan — Today mode | **CANDIDATE — REVIEW REQUIRED** | Canva `DAHU9b9Sba4` · Edit: https://www.canva.com/d/3eCQyf18DFoDgaE · View: https://www.canva.com/d/WCrhn7wpudP86vW` | 390×844 production reference. Real Today/Week/Projects segmented control, focus hero, task rows with separate completion controls, real coming-up strip, central Capture action. Calm and capable, not spreadsheet-like. |
| Capture — parsed reminder | **CANDIDATE — REVIEW REQUIRED** | Canva `DAHU9ajepWE` · Edit: https://www.canva.com/d/swodZIFEOqlj3Vp · View: https://www.canva.com/d/j9CROUZRq_cfx6Z` | 390×844 Universal Capture reference. Spacious composer, voice action, type correction chips, editable structured result, explicit single-save CTA. Companion intentionally absent from dense input state. |
| Journey — daily history | **CANDIDATE — REVIEW REQUIRED** | Canva `DAHU9UNm_i8` · Edit: https://www.canva.com/d/i8vV1R01-188QbQ · View: https://www.canva.com/d/U3reavcwsoDj4Li` | 390×844 editorial Journey reference. Full-week date navigation, real journal hero, truthful chronological events, real Memories/Chapters gateway, reflective serif used selectively. |
| Me — populated truthful state | **CANDIDATE — REVIEW REQUIRED** | Canva `DAHU9Uou__U` · Edit: https://www.canva.com/d/lplJ4AFAjpub7e0 · View: https://www.canva.com/d/YoqDpM9xY5P8W_b` | 390×844 identity/profile reference. Preferred name, user-authored roles/chapter, traceable metrics, Life Areas, measurable goal state, settings gateway and subtle companion prompt. No fabricated KPIs. |
| Today — dark | Pending | — | Intentional dark design. |
| Journey — dark | Pending | — | Intentional dark design. |
| Component sheet | Pending | — | Buttons, rows, inputs, cards, nav, sheets, feedback. |
| Companion canonical model sheet | **DIRECTION ACCEPTED — ASSET FREEZE PENDING** | Tiny Observatory Friend studio/model sheet generated 2026-09-12 | Lock human-like companion identity: soft dark hair, sage hoodie/clothing, warm cream/earth palette, calm rounded proportions. Required states: idle, wave, curious/thinking, completion, reading/learning, focused/work, reflective/evening, sleepy. Decorative animal/cat elements shown in concept art are optional scene decoration, not a second required companion. |
| Companion interaction pose sheet | Pending | — | Tap-response variations and contextual states after canonical vector/animation asset is finalized. |

## Brand implementation target

The approved icon direction is defined in:
- `APP_ICON_BRAND_V0_1.md`
- `decisions/DECISION_LOG.md` → D-024

Astra must not substitute a mascot portrait, generic checkmark, calendar, AI sparkle/brain or unrelated monogram for the launcher identity.

## Companion implementation target

The approved character direction is defined in:
- `CHARACTER_SYSTEM_V0_1.md`
- `decisions/DECISION_LOG.md` → D-023

No other companion concept should be substituted during implementation without an explicit design decision update.

## Candidate-vs-approved rule

A Canva design being created does **not** mean it is frozen. Candidate references remain non-authoritative until the founder explicitly approves them or explicitly instructs the team to continue past that review gate. Once approved, update the row to **APPROVED / FROZEN VISUAL TARGET**, add the approval date, and treat the Canva design plus corresponding implementation notes/source as the target.

## Rule
Astra must not infer final visual styling from textual prose alone once this manifest contains approved references. Approved references become implementation targets together with the written specs.
