# Personal OS — Color & Typography v0.1

**Status:** Working draft — semantic direction strong, exact font package and full contrast audit pending

## 1. Color philosophy
Personal OS should feel warm and calm, with color used to communicate meaning and emotional tone rather than decorate every card.

The base remains **Warm Personal Observatory**.

## 2. Light semantic palette — working
| Role | Name | Working value |
|---|---|---|
| background | Warm Ivory | `#F5F1E8` |
| surface | Soft Cream | `#FCF9F3` |
| surfaceMuted | Quiet Sand | `#EEE9DE` |
| primaryText | Ink | `#252521` |
| secondaryText | Stone | `#4E4E48` |
| mutedText | Soft Stone | `#6B6A63` |
| border | Linen | `#E1DCD1` |
| primaryAccent | Moss Sage | `#667A61` |
| primaryAccentSoft | Soft Sage | `#DDE4DA` |
| goldAccent | Muted Gold | `#C89B58` |
| goldSoft | Pale Gold | `#EFE3CE` |
| reflectionAccent | Lavender | `#9186A5` |
| reflectionSoft | Mist Lavender | `#E6E1EA` |
| warmAccent | Terracotta | `#A06B59` |
| warmSoft | Blush Clay | `#EBDDD7` |

Working state colors should remain muted rather than neon.
Exact success/warning/error values require final accessibility audit.

## 3. Dark semantic palette — working
| Role | Name | Working value |
|---|---|---|
| background | Night Ink | `#171816` |
| surface | Deep Moss Charcoal | `#22231F` |
| surfaceMuted | Quiet Charcoal | `#2B2C27` |
| primaryText | Warm Ivory | `#F5F1E8` |
| secondaryText | Warm Gray | `#C7C5BC` |
| mutedText | Quiet Gray | `#A7A69E` |
| border | Dark Linen | `#3A3B35` |
| primaryAccent | Soft Moss | `#9BAE95` |
| goldAccent | Warm Gold | `#C8A96B` |
| reflectionAccent | Soft Lavender | `#A89DB6` |
| warmAccent | Soft Terracotta | `#B98170` |

Dark mode should feel intimate and warm, not pure-black/techy.

## 4. Contrast direction
Main text/background pairings must meet or exceed normal accessibility expectations.
The working palette has been chosen with strong contrast in mind, but a full WCAG/Android contrast audit is required before freeze.

Do not use accent text on accent backgrounds without explicit contrast testing.

## 5. Color meaning
### Moss Sage
Primary action, focus, calm progress, active navigation.

### Gold
Milestones, meaningful highlights, rare warmth.
Never use as generic status color everywhere.

### Lavender
Reflection, memory, Journey, gentle contemplative surfaces.

### Terracotta
Warm caution/destructive context, emotional warmth, rare emphasis.
Not the default error red replacement if contrast/semantics become unclear.

## 6. Color restraint rule
A typical screen should have:
- dominant neutral background
- neutral cards
- one primary accent family
- at most one or two secondary accent moments

Do not create rainbow dashboards.

## 7. Typography philosophy
Typography should feel modern, warm, editorial and easy to read.

Functional UI uses one primary sans family.
Reflective/editorial content may use one secondary serif sparingly.

## 8. Font candidates — not yet frozen
### Primary sans candidate
**Manrope**
Reasons:
- clean and modern without feeling corporate
- strong numerical legibility
- warm rounded character
- broad weight range

Fallback:
- Android/system sans if bundling/licensing/tooling becomes a problem

### Reflective serif candidate
**Newsreader**
Possible use only for:
- journal excerpts
- Journey chapter/memory titles
- reflective hero copy

Fallback:
- keep all UI in primary sans rather than introducing an inconsistent serif.

Licensing and Android bundling must be verified before freeze.

## 9. Type scale — working
| Role | Size | Weight direction |
|---|---:|---|
| Hero greeting | 32–34sp | 650–700 |
| Major page title | 28–32sp | 650–700 |
| Section title | 18–22sp | 600–700 |
| Hero card title | 22–26sp | 600–700 |
| Standard card title | 16–19sp | 600–650 |
| Body large | 16sp | 400–500 |
| Body | 14–15sp | 400–500 |
| Button | 14–16sp | 600 |
| Metadata | 12–13sp | 450–550 |
| Eyebrow | 11–12sp | 600–700 |

Exact Compose TextStyle values, line heights and letter spacing will be frozen later.

## 10. Line-height direction
- hero: compact but never cramped
- body: comfortable reading, ~1.35–1.5×
- journal/reflection: more generous
- metadata: compact

## 11. Case rules
Use sentence case for nearly all labels.
Small eyebrows may use uppercase sparingly, e.g. `YOUR DAY`, but should not create a loud dashboard feel.

## 12. Numbers
Numbers in metrics/dates/times must remain highly legible.
Avoid stylized serif numerals for operational UI.

## 13. Truncation
- never truncate the main task/project title if a detail screen can reasonably allow wrapping
- compact cards may use 2-line limits
- metadata can ellipsize when secondary

## 14. Text scaling
Designs must remain usable at increased Android font scale.
Avoid fixed-height cards around variable text.

## 15. Companion and typography
Character presence should not require playful/cartoon typography.
The premium typography system stays consistent; the companion provides warmth through illustration and motion, not novelty fonts.

## 16. Acceptance criteria before freeze
- final sans/serif licensing verified
- font files/tooling available to build pipeline if bundled
- full light/dark contrast audit
- TextStyle tokens defined in Compose
- semantic color tokens defined centrally
- no screen hard-codes arbitrary colors/text styles outside design system without justification
