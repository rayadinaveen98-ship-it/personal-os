# Personal OS — Launch, Welcome & Setup Flow v0.1

**Status:** Working draft — premium experience direction locked, exact copy/illustration layout not yet frozen

This flow defines the first minutes of Personal OS. It must establish trust, calm, richness, and personal ownership before the user reaches Today.

## 1. Experience objective
The first-run experience must feel like entering a private, carefully prepared personal space — not filling out a corporate form.

The user should feel:
- welcomed
- curious
- calm
- understood
- in control
- never rushed

The setup flow should gather only information that immediately improves the first real Today screen.

## 2. Launch sequence
### Normal launch after setup
1. Android launch surface / splash
2. short branded transition
3. Today opens immediately

Target: no decorative delay beyond what is necessary for smooth startup.

### First launch
1. Android launch surface
2. branded Personal OS moment
3. Welcome
4. guided setup
5. optional first-life-context creation
6. “Preparing your space” transition
7. personalized Today

## 3. App launch visual
The launch surface should use the Personal OS visual identity.

Working direction:
- Warm Ivory or theme-aware background
- centered Personal OS mark/icon
- extremely subtle entrance or scale/fade if technically appropriate
- no long logo animation
- no progress spinner unless startup genuinely requires it

If the companion character appears on launch, it should be very restrained: e.g. sitting beside the mark or gently waking. It must not delay launch.

## 4. Welcome screen
### Purpose
Explain the emotional promise of Personal OS in one screen.

### Working hierarchy
- small Personal OS mark / icon
- optional calm companion illustration
- hero line
- one short supporting paragraph
- primary CTA
- privacy reassurance

### Working copy direction
Hero options to refine later:
- **A calm place for the life you're building.**
- **Your life, clearer. Your progress, remembered.**
- **A personal space for what matters now and what you're becoming.**

Supporting copy direction:
> Personal OS brings your plans, projects, memories, ideas, habits and growth into one private place that stays with you.

Primary CTA:
- `Make it mine`
- or `Set up my space`

Avoid generic onboarding language like `Next` on the first screen if a warmer action fits.

Privacy reassurance:
> Your core life data stays on your device by default.

## 5. Companion behavior on Welcome
If companion is included:
- initial state: calm seated/standing idle
- first tap: wave
- second tap: small stretch or smile
- third reaction rotates from a tiny pool
- always returns to idle
- no speech bubble required

The companion should never compete with the hero copy.

## 6. Setup philosophy
The setup should feel like a short conversation, not a questionnaire.

Rules:
- one meaningful decision per screen
- visible lightweight progress
- generous whitespace
- large choices with short explanations
- Skip only when the step is truly optional
- no permission wall
- no fake defaults silently saved when the user skips
- fixed/stable CTA area where practical
- all system insets respected

## 7. Proposed setup sequence
### Step 1 — Preferred name
Question:
> **What should Personal OS call you?**

Input:
- preferred name

Behavior:
- optional; blank is valid
- no forced legal/full name
- preview can subtly show `Good morning, Navin.` after entry

CTA: `Continue`

### Step 2 — What do you want Personal OS to help with?
Question:
> **What would you like more clarity around?**

Working choices:
- Build projects
- Stay organized
- Learn & grow
- Build habits
- Remember my life
- Explore ideas

Multi-select.

Each option includes a concise one-line explanation.

No selections should be preselected unless explicitly presented as suggestions and confirmed by user action.

CTA: `Continue · N selected`

Skip behavior:
- continues with zero selections
- does not silently insert defaults

### Step 3 — Your life areas
Question:
> **What is already part of your world?**

Suggested choices may include:
- Work / Career
- App Building
- Game Development
- Filmmaking / Creative Work
- Health
- Reading
- Learning
- Family
- Personal

These are suggestions, not hard-coded user truth.

User can:
- select suggestions
- add custom area
- continue with none

### Step 4 — Add something real
Purpose: make Today useful immediately without forcing data creation.

Question direction:
> **Is there something you're actively moving forward right now?**

Options:
- Add a project
- Add a goal
- Add a task
- Not now

This step is optional but valuable.

If user creates an item here, it must create the real production entity, not onboarding-only sample state.

### Step 5 — Daily rhythm
Question:
> **How should Personal OS meet you each day?**

Controls:
- Morning Brief toggle
- Evening Reflection toggle

Each has plain-language explanation.

Morning Brief exposed only if the actual behavior exists in V1.
Evening Reflection exposed only if the actual behavior exists in V1.

No notification permission requested yet unless enabling a behavior immediately requires it; education should be contextual.

### Step 6 — Privacy / protection preference
Working optional choices:
- Enable app lock later
- continue without lock

Do not force biometric setup during onboarding unless design review later strongly justifies it.

## 8. Setup character behavior
Character presence can evolve gently:
- Welcome: wave / greet
- Name step: looks toward the input
- Life areas: holds/observes tiny cards or objects, if illustration remains simple
- Project/goal step: attentive pose
- Daily rhythm: morning/evening variant
- Ready screen: subtle happy/welcoming reaction

Rules:
- character remains secondary
- character never encodes essential instructions
- animation pauses/reduces when system reduced-motion is enabled if supported

## 9. Setup progress
Use a quiet progress treatment such as:
- short bars
- subtle dots
- `2 of 5`

Do not use a heavy percentage meter.

Progress must not imply mandatory completion of optional steps.

## 10. Back and Skip
### Back
- returns to previous setup step
- preserves current in-memory selections

### Skip
Only shown for genuinely optional steps.

Skip must mean exactly that.
It may not save unconfirmed defaults.

## 11. “Preparing your space” transition
After final confirmation:
- persist setup state
- create any real initial entities
- ensure first Today can render truthfully
- show a short rewarding transition

Working copy:
> **Your space is ready.**

Optional secondary line:
> Personal OS is preparing today around what matters to you.

Companion can give a subtle welcome gesture.

Duration should feel intentional but short; do not block for arbitrary animation time if initialization is already complete.

## 12. First Today handoff
The transition into Today should make the setup feel worthwhile.

Today should immediately use:
- preferred name
- selected life areas
- any real project/goal/task created
- Morning Brief preference
- Evening Reflection preference

If nothing actionable was created, Today must be truthful:
> **Nothing is asking for your attention yet.**

Then offer real next actions.

## 13. Permission strategy
Permissions are requested contextually:
- microphone → first Voice Capture use
- notifications → first reminder / enabled brief that needs notification delivery
- exact alarms → when exact reminder scheduling requires special access
- biometrics → when user enables App Lock

No first-run permission wall.

## 14. Loading / failure handling
If setup persistence fails:
- do not silently mark onboarding complete
- explain calmly
- allow retry
- preserve user-entered state in memory where possible

If a custom life area fails to save, show actionable retry rather than dropping it silently.

## 15. Accessibility
- 48dp minimum touch targets
- all choices have semantic labels
- selected state conveyed by more than color
- keyboard/input handling does not cover CTA
- text scaling supported
- companion is decorative unless explicitly interactive; decorative states should not produce noisy screen-reader output

## 16. Premium-quality acceptance criteria
This flow is not considered complete merely because all screens navigate.
It must also pass:
- no clipped content on compact-height phones
- polished light and dark presentation
- smooth keyboard transitions
- consistent motion
- clear press feedback
- no generic placeholder illustrations
- no fake data
- no inert controls
- no permission dead ends
- no screen that feels like a stock Material form

## 17. Open decisions before freeze
1. Final Welcome hero copy.
2. Exact number of setup steps after usability review.
3. Whether initial project/goal/task creation is one combined step or three optional paths.
4. Whether app-lock choice belongs in onboarding or Settings only.
5. Final companion art form and exact appearances.
6. Final launch icon/mark treatment.
7. Whether the final transition says `Your space is ready` or a more distinctive Personal OS phrase.
