# Personal OS — UI Component System v0.1

**Status:** Working draft

This document turns the Warm Personal Observatory direction into repeatable UI rules so Astra does not invent a different visual style on each screen.

## 1. Core principle
Personal OS should use a small, coherent component family with generous rhythm and clear interaction states.

No component may look interactive unless it is interactive.

## 2. Page scaffold
Default phone page:
- theme-aware app background
- 20dp horizontal content padding
- status-bar inset respected
- vertical scrolling for content that can grow
- bottom-nav inset/reservation where applicable

Primary-screen top spacing should feel breathable, not like a dense toolbar.

## 3. Typography roles — working
- Eyebrow: 11–12sp, medium/semibold, restrained letter spacing
- Large greeting/hero: 30–34sp, strong but warm
- Page title: 28–34sp depending context
- Section heading: 16–20sp
- Card title: 16–20sp
- Body: 14–16sp
- Metadata: 11–13sp
- Button label: 14–16sp medium/semibold

A selective serif/editorial role may be used for Journey/reflection excerpts only after final font decision.

## 4. Card family
### Hero card
Use for one dominant context such as Today's Focus.
- radius ~24dp
- generous padding ~20–24dp
- strong accent/tonal surface
- one primary purpose
- maximum 1 dominant hero per local screen section

### Standard card
- radius ~20dp
- padding ~16–20dp
- neutral/cream surface
- optional subtle border/shadow

### Compact card
- radius ~18dp
- padding ~14–16dp
- used in small grids/continuation items

### Reflection/editorial card
- quieter accent or neutral surface
- may use selective serif text
- more whitespace

## 5. Card interaction
Interactive card:
- immediate press state
- entire tappable area navigates to represented entity unless internal controls require separate semantics
- chevron only if it actually navigates

Non-interactive summary card:
- no fake chevron
- no button-like text

## 6. Buttons
### Primary
- high-contrast filled button
- radius ~16–18dp or pill depending context
- 52–56dp typical height
- one dominant CTA per local context when possible

### Secondary tonal
- quieter filled surface
- same minimum touch size

### Outline
Use sparingly for secondary structured actions.

### Text / tertiary
Only for low-priority actions such as Skip, Edit, See all when the action truly exists.

### Destructive
Use terracotta/error role, not bright alarming red by default unless accessibility/semantics require stronger signal.
Keep separated from normal actions.

## 7. Icon buttons
- minimum 48dp touch target
- icon typically 20–24dp
- round or soft-square container when the control needs stronger affordance
- clear content description

## 8. Inputs
### Standard text field
- soft neutral/cream or theme surface
- radius ~16–18dp
- visible focus state
- label/hint never substitutes for persistent meaning when ambiguity matters

### Large Capture composer
- larger multiline surface
- comfortable internal padding
- voice action placed consistently
- parsed context appears beneath or within a distinct interpretation region

Error state should explain what user can do next.

## 9. Selection cards
Used in onboarding and low-density choices.

Requirements:
- large tap area
- icon/illustration optional
- title + short explanation
- selected state uses at least two signals (e.g. color + check/border)
- multi-select count visible when useful

## 10. Chips
Use for:
- type selection
- filters
- tags
- compact context

Rules:
- do not turn entire app into chip soup
- selected state clear
- minimum comfortable touch target
- truncated text handled gracefully

## 11. Segmented control
Use for small mutually exclusive primary modes such as Plan: Today / Week / Projects.

Requirements:
- all segments functional
- active state obvious
- no more than ~3–4 labels before switching to another navigation pattern
- smooth but quick selection transition

## 12. List rows
Task/entity rows should support:
- 56dp+ minimum height; often larger with metadata
- clear primary text
- optional secondary context
- dedicated leading/trailing control only when necessary

For tasks:
- completion target separate from row navigation
- priority/due state not conveyed only by color

## 13. Bottom navigation
Working items:
- Today
- Plan
- Capture
- Journey
- Me

Requirements:
- active state visually clear
- labels remain available for clarity
- central Capture is emphasized but not oversized enough to dominate
- safe-area inset respected
- no hidden functionality under the floating button

Working central Capture size: ~56–64dp.

## 14. Top app bars
Primary tabs should avoid generic dense app bars where a calm editorial header is more appropriate.

Detail screens may use:
- back button
- title/context
- trailing overflow/edit only when real

## 15. Bottom sheets
Use for:
- choosing project/life area
- date/time selection wrapper
- quick field editing
- filters
- small contextual actions

Rules:
- drag handle optional
- safe-area aware
- clear title
- destructive actions separated

## 16. Dialogs
Reserve for decisions that genuinely interrupt:
- destructive delete
- restore/replace data
- irreversible reset

Do not use dialogs for ordinary navigation.

## 17. Snackbars / transient feedback
Use for:
- saved
- completed + Undo
- archived + Undo where safe
- recoverable minor status

Do not rely on transient snackbar for critical permission failure or destructive consequences.

## 18. Progress
Allowed:
- `3 of 5 milestones`
- `4 of 5 planned habit days`
- determinate bar for explicit numeric target

Avoid decorative circular percentages with no clear source.

## 19. Empty-state component
Structure:
- optional small illustration/companion
- clear title
- 1–2 line explanation
- one primary action
- optional secondary action

Keep it visually rich but truthful.

## 20. Companion slot
Reusable optional container that can host the calm 2D companion.

Variants:
- compact corner
- empty-state centered
- welcome hero
- reflection side/foot

Rules:
- layout reserves space intentionally
- never floats over important content
- can be disabled without leaving broken spacing

## 21. Dividers/borders
Prefer spacing and tonal surfaces over heavy divider lines.
Use subtle borders only where needed for state/hierarchy.

## 22. Elevation/shadows
Keep soft and restrained.
Most hierarchy should come from:
- tone
- spacing
- radius
- typography

Avoid floating-everything shadow stacks.

## 23. Color semantics — roles
Final exact variants pending, but components must use semantic roles rather than hard-coded arbitrary colors:
- background
- surface
- elevatedSurface
- primaryText
- secondaryText
- mutedText
- primaryAccent
- secondaryAccentGold
- reflectionLavender
- warmTerracotta
- success
- warning
- error
- border
- disabledSurface
- disabledText

## 24. Dark-mode component rule
Every component must have an intentional dark variant.
No light-only hard-coded background/text values inside screen implementations.

## 25. Loading components
- skeleton only for genuinely asynchronous visible data
- local Room reads should generally settle fast
- no fake textual placeholders

## 26. Accessibility
- 48dp+ tap targets
- contrast audited
- selection indicated by more than color
- semantic labels/states
- text scaling
- companion decoration excluded from accessibility tree unless interactive

## 27. Build acceptance
Reject a component implementation if:
- it is visually inconsistent with the rest of Personal OS
- it looks like unmodified stock Material
- it has no pressed/focus/disabled state
- it uses fake affordances
- dark mode breaks it
- it clips on compact phone/font scaling
