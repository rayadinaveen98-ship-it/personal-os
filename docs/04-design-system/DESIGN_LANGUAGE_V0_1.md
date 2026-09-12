# Personal OS — Design Language v0.1

Status: **Working draft — visual direction approved, exact system not yet frozen**

Working identity:

> **Warm Personal Observatory**

Personal OS should feel like a private life cockpit blended with a beautiful journal: warm, calm, intelligent, premium, personal, and realistically implementable in Jetpack Compose.

## 1. Design goals

The interface should feel:

- calm
- intimate
- premium
- editorial
- quietly cinematic
- trustworthy
- modern without chasing trends
- visually warm in light mode
- quiet and focused in dark mode

The interface should not feel:

- corporate
- sterile
- like enterprise project-management software
- neon/cyberpunk
- aggressively gamified
- glassmorphic for decoration
- overloaded with gradients
- like an AI chatbot skin
- visually impossible to reproduce in native Compose

## 2. Production realism rule

Every approved design must pass this question:

> **Could this realistically be built almost exactly like this in Jetpack Compose?**

If no, the design should not be approved.

Allowed:

- mild gradients
- subtle shadows/elevation
- simple vector decoration
- restrained texture
- circles/rings/lines
- real blur where platform support and performance allow it
- subtle motion

Avoid:

- fantasy glass materials
- impossible reflections
- fake 3D scenes embedded in functional cards
- lighting effects dependent on image generation
- text rendered as part of decorative images
- interactions that only exist in mockups

## 3. Light palette — working values

| Role | Name | Hex |
|---|---|---|
| App background | Warm Ivory | `#F5F1E8` |
| Primary card | Soft Cream | `#FCF9F3` |
| Primary text | Ink | `#252521` |
| Primary accent | Moss Sage | `#667A61` |
| Secondary warm accent | Muted Gold | `#C89B58` |
| Reflective accent | Lavender | `#9186A5` |
| Warm accent | Terracotta | `#A06B59` |

Exact semantic variants for borders, disabled states, errors, success, warning, and text hierarchy will be frozen later.

## 4. Dark palette — working direction

| Role | Working value |
|---|---|
| App background | `#171816` |
| Primary card | `#22231F` |

Dark mode must be designed intentionally, not produced by simply swapping white for black.

Requirements:

- preserve warm/personal character
- avoid pure black as the dominant surface unless accessibility/performance requires it
- reduce accent saturation where necessary
- preserve readable contrast
- keep card hierarchy visible without excessive borders

## 5. Layout system

Working grid: **8dp**.

Working spacing anchors:

- page horizontal padding: **20dp**
- typical inter-card spacing: **16dp**
- tight internal spacing: **8dp**
- medium internal spacing: **12–16dp**
- major section spacing: **24–32dp**

Avoid screens where every element uses the same spacing. Hierarchy should be visible through rhythm.

## 6. Radius system

Working direction:

- hero/large cards: **24dp**
- standard cards: **20dp**
- compact cards: **18dp**
- chips: fully rounded / pill
- central Capture: rounded square or soft circular treatment, final shape to be frozen

Use a small set of radii consistently.

## 7. Typography

The product should use a clean, warm sans-serif system for functional UI.

A serif may be used selectively for reflective/editorial moments such as journal excerpts or Journey chapter titles, but never so broadly that the app becomes inconsistent.

Working scale:

- Hero / major greeting: ~34sp
- Section title: ~24sp where large editorial hierarchy is justified
- Card title: ~18sp
- Body: ~14sp
- Metadata / eyebrow: ~11–12sp

Exact font family, weights, line heights, and letter spacing must be frozen before implementation.

## 8. Card philosophy

Cards are meaningful containers, not decoration.

A card should generally represent one of:

- a specific entity
- a current context
- a grouped section
- an actionable prompt
- a truthful summary

Do not create cards merely to fill space.

### Large focus card

Used sparingly for the highest-priority context.

Characteristics:

- strong but calm accent surface
- generous whitespace
- clear label/title/metadata hierarchy
- one obvious interaction

### Standard cards

Cream/light neutral in light mode, quiet dark surface in dark mode.

### Accent cards

Lavender/gold/terracotta should be used selectively to differentiate meaning, not randomly.

## 9. Buttons

Button hierarchy should be obvious.

### Primary CTA

- solid, high-contrast
- one dominant primary CTA per local context when possible

### Secondary CTA

- quieter filled/tonal or bordered style

### Tertiary action

- text/icon action only when hierarchy is clear

Never style plain text to look like a button without an actual action.

## 10. Chips

Chips may represent:

- filters
- tags
- selected contexts
- lightweight choices

Chips must not become the default solution for every concept.

Selection state should be visible through more than subtle color alone when possible.

## 11. Navigation

Working bottom navigation:

- Today
- Plan
- central Capture
- Journey
- Me

The active item should be clear without excessive animation.

Central Capture should be visually prominent because capture is the global action, but it should not dominate the entire screen.

## 12. Icons

Use a consistent native/vector icon family.

Rules:

- icons support meaning; they do not replace clear text when ambiguity matters
- avoid mixing radically different icon styles
- avoid decorative 3D icon packs
- icons should remain legible at normal Android sizes

## 13. Motion

Motion should communicate state and continuity.

Allowed examples:

- button press feedback
- selected tab transition
- card expansion
- sheet entrance/exit
- small check/completion transition
- gentle content change

Avoid:

- long intro animation on every launch
- bouncing engagement animation
- excessive parallax
- animation that delays a user action

## 14. Progress visualization

Progress must be based on real measurable data.

Prefer:

- milestone state
- completed vs remaining items
- recent activity evidence
- explicitly defined measurable goal units

Avoid generic 68%/82% progress unless the percentage has a real mathematical meaning the user understands.

## 15. Empty states

Empty screens should feel intentional and premium.

Structure:

1. clear state
2. short explanation
3. one useful next action
4. optional secondary action

Example:

```text
No projects yet

Projects give your work a home and preserve where you left off.

[Create your first project]
```

Do not populate the empty state with fake personal examples that look real.

## 16. Error states

Errors should be calm and actionable.

Example:

```text
Personal OS couldn't schedule this reminder exactly.

Allow exact alarms so it can notify you at the time you chose.

[Open settings]
```

Avoid technical stack traces or vague “Something went wrong” when a useful explanation is available.

## 17. Permission UI

Permission education should happen when the feature is invoked.

Examples:

- microphone explanation when voice Capture is tapped
- notifications when the user creates/activates their first reminder or enables a daily brief
- exact alarm access when exact scheduling is required
- biometric permission when app lock is enabled

Do not build a first-launch permission wall.

## 18. Status/navigation bars

All screens must respect Android system insets.

Requirements:

- no content clipped under the status bar
- no bottom actions obstructed by gesture/navigation areas
- system bar appearance should harmonize with current light/dark theme

## 19. Responsive rules

Primary target is phone portrait.

Requirements:

- work on compact-height phones
- support text scaling without catastrophic clipping
- avoid hard-coded card heights unless the content is genuinely fixed
- scrolling screens should scroll naturally
- sticky CTAs must account for navigation insets

Tablet/foldable optimization may follow later but layouts should not be architected in a way that prevents it.

## 20. Visual truthfulness

The visual system must not imply features that are absent.

Examples:

- a segmented control must actually switch content
- a chevron implies navigation and must navigate
- a checkbox/toggle must change state
- a progress ring must represent real progress
- a search icon must open Search
- a `Manage` label must open management controls

## 21. Current design-system tasks before freeze

Still to define:

- final font family
- complete semantic light palette
- complete semantic dark palette
- text color hierarchy
- border/divider system
- disabled-state palette
- success/warning/error colors
- exact component elevation/shadow rules
- button specifications
- input specifications
- switch/radio/checkbox specifications
- dialog/sheet specifications
- snackbar/toast strategy
- date/time picker strategy
- list-row pattern
- progress component rules
- final bottom-nav dimensions
- final Capture-button geometry
- animation timings/easing
- accessibility contrast audit
