# Personal OS — Design Tokens V1 Freeze

**Status:** FROZEN for V1 implementation  
**Date:** 2026-09-12

This file converts the Warm Personal Observatory direction into implementation-safe V1 tokens. Visual references remain the screen-level target; these tokens are the shared system Astra must use to avoid screen-by-screen improvisation.

## 1. Typography

### Primary sans — Manrope
Use **Manrope** for operational UI, navigation, buttons, inputs, task/project metadata, metrics and most headings.

License: SIL Open Font License 1.1.

### Reflective serif — Newsreader
Use **Newsreader** selectively for reflective/editorial moments only:
- Journey/journal hero text
- selected reflective page titles
- memory/chapter titles
- quiet quotes / evening reflection copy

Do not use serif for operational numbers, dense forms, task rows or navigation.

License: SIL Open Font License 1.1.

### Fallback rule
If bundled-font delivery causes a genuine blocker, preserve hierarchy using the Android system sans first. Do not substitute a novelty font. Newsreader may be omitted before Manrope if required for reliability.

## 2. Frozen light semantic colors

| Token | Value | Use |
|---|---|---|
| `background` | `#F5F1E8` | Warm Ivory page background |
| `surface` | `#FCF9F3` | Soft Cream cards/sheets |
| `surfaceMuted` | `#EEE9DE` | Quiet Sand secondary surfaces |
| `textPrimary` | `#252521` | Ink |
| `textSecondary` | `#4E4E48` | Secondary body |
| `textMuted` | `#6B6A63` | Metadata/quiet text |
| `border` | `#E1DCD1` | Linen |
| `primary` | `#667A61` | Moss Sage actions/focus |
| `primarySoft` | `#DDE4DA` | Soft Sage surfaces |
| `gold` | `#C89B58` | Milestone/rare warmth |
| `goldSoft` | `#EFE3CE` | Pale Gold surface |
| `lavender` | `#9186A5` | Reflection accent |
| `lavenderSoft` | `#E6E1EA` | Reflection surface |
| `terracotta` | `#A06B59` | Warm accent, not filled destructive CTA |
| `terracottaSoft` | `#EBDDD7` | Warm contextual surface |
| `danger` | `#8E5147` | Destructive filled action where needed |
| `onPrimary` | `#FFFFFF` | Text/icons on Moss Sage |
| `onDanger` | `#FFFFFF` | Text/icons on danger fill |

## 3. Frozen dark semantic colors

| Token | Value | Use |
|---|---|---|
| `background` | `#171816` | Night Ink |
| `surface` | `#22231F` | Deep Moss Charcoal |
| `surfaceMuted` | `#2B2C27` | Quiet Charcoal |
| `textPrimary` | `#F5F1E8` | Warm Ivory |
| `textSecondary` | `#C7C5BC` | Warm Gray |
| `textMuted` | `#A7A69E` | Quiet Gray |
| `border` | `#3A3B35` | Dark Linen |
| `primary` | `#9BAE95` | Soft Moss |
| `gold` | `#C8A96B` | Warm Gold |
| `lavender` | `#A89DB6` | Soft Lavender |
| `terracotta` | `#B98170` | Soft Terracotta |
| `onAccentDark` | `#171816` | Text/icons on light dark-theme accents |

Dark mode is a designed warm theme, not a mechanical inversion.

## 4. Contrast rules

Measured V1 pairings:
- Ink on Warm Ivory: ~13.65:1
- Secondary text on Warm Ivory: ~7.43:1
- Muted text on Warm Ivory: ~4.82:1
- Ink on Soft Cream: ~14.64:1
- White on Moss Sage: ~4.64:1 — approved for normal filled-button text
- Ink on Soft Sage: ~11.85:1
- Ink on Pale Gold: ~12.12:1
- Ink on Mist Lavender: ~11.96:1
- Ink on Blush Clay: ~11.61:1
- Warm Ivory on Night Ink: ~15.81:1
- Warm Gray on Night Ink: ~10.30:1
- Quiet Gray on Night Ink: ~7.29:1
- Night Ink on Soft Moss: ~7.54:1
- Night Ink on Warm Gold: ~7.93:1
- Night Ink on Soft Lavender: ~6.93:1
- Night Ink on Soft Terracotta: ~5.46:1
- White on Danger `#8E5147`: ~6.14:1

### Forbidden pairings for normal text
- Warm Ivory on Moss Sage (~4.12:1)
- Soft Cream on Moss Sage (~4.42:1)
- White on Terracotta `#A06B59` (~4.43:1)
- White on Lavender `#9186A5` (~3.41:1)

Therefore:
- Moss Sage filled buttons use **pure white** text/icons.
- Lavender surfaces/actions use **Ink** text in light mode.
- Gold surfaces use **Ink** text.
- Terracotta normally uses Ink on a soft surface; destructive filled CTA uses darker `danger` with white.
- Dark-theme accent fills use Night Ink text unless a tested alternative is explicitly defined.

Do not introduce arbitrary hard-coded colors outside semantic tokens without a documented reason and contrast check.

## 5. Spacing

Base grid: **8dp**.

Frozen common spacing:
- screen horizontal padding: **20dp**
- standard section gap: **24dp**
- standard card-to-card gap: **16dp**
- compact row gap: **8–12dp**
- card internal padding: **16dp** default
- hero/card generous padding: **18–20dp** where the visual reference calls for it

Insets must respect status/navigation bars. Do not fake safe areas with hard-coded device-specific padding.

## 6. Shape system

- hero / major card radius: **24dp**
- standard card radius: **20dp**
- compact card / input radius: **18dp**
- button radius: **16–18dp** depending on reference
- chips: fully rounded / pill
- central Capture action: rounded-square/circle treatment per approved component/nav reference

Avoid a different radius on every screen.

## 7. Touch targets

- minimum interactive target: **48×48dp**
- primary CTA height: typically **52–56dp**
- completion controls must satisfy 48dp interaction area even if the visible glyph is smaller
- icon-only controls require accessible labels/content descriptions

## 8. Type scale

| Role | Frozen range / target |
|---|---|
| hero greeting | 32–34sp, 650–700 |
| major page title | 28–32sp, 650–700 |
| section title | 18–22sp, 600–700 |
| hero card title | 22–26sp, 600–700 |
| card title | 16–19sp, 600–650 |
| body large | 16sp, 400–500 |
| body | 14–15sp, 400–500 |
| button | 14–16sp, 600 |
| metadata | 12–13sp, 450–550 |
| eyebrow | 11–12sp, 600–700 |

Use responsive line height and wrapping. Do not fix card heights around variable user text.

## 9. Elevation and borders

Personal OS uses restrained depth:
- most cards rely on subtle tonal separation + Linen border
- soft low-opacity shadows only on elevated/hero/floating elements
- no heavy Material shadow stack
- no glassmorphism dependency

## 10. Motion tokens

- micro response: **120–180ms**
- standard state transition: **220–300ms**
- sheet/navigation polish: roughly **250–350ms** where platform behavior allows
- companion reaction: **300–900ms**, then return to idle

Motion qualities:
- ease, settle, fade, small translation/scale
- no bouncy arcade motion
- no long blocking intro animation
- task completion feedback remains subtle

Reduced-motion mode/system preference:
- remove decorative movement
- keep essential state change understandable through opacity/color/layout
- companion falls back to static pose/state swaps

## 11. Companion slots

Tiny Observatory Friend can appear prominently only on low-density emotional surfaces such as Welcome, selected Setup steps, Today hero/empty state, Weekly Review close, and reflection moments.

Dense operational/detail surfaces default to no companion.

Character never carries essential information alone.

## 12. Iconography

Use one consistent simple line/filled icon family, preferably Material Symbols/Icons or project-owned vector equivalents.

Rules:
- 20–24dp visible icons in standard controls
- semantic meaning before decoration
- active nav state may use filled/stronger treatment
- priority/status is never conveyed by color alone
- the Minimal Leaf brand mark is not reused as a generic feature icon

## 13. Component truthfulness

Anything styled as interactive must have a real action in the final product.

No decorative chevrons, toggles, tabs, chips or buttons that do nothing.

## 14. Font-scale / small-screen rule

V1 must remain usable at 1.0×, 1.3× and 1.5× Android font scaling.

- prefer vertical scroll over clipping
- two-column optional cards may collapse to one column at large text scale
- fixed-height text containers are discouraged
- primary CTA must remain reachable with IME shown where relevant

## 15. Freeze rule

Astra may create Compose semantic tokens/classes around these values. It may make minor optical adjustments required by Android rendering, but must not silently replace the palette, typography identity, spacing rhythm, or motion character with default Material values.
