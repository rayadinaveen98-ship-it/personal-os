# Personal OS — Visual Reference Package v0.1

**Status:** Working visual-production plan. These references must exist before Astra receives the full end-to-end implementation brief.

## 1. Purpose
Text specs define behavior, but Astra also needs visual references strong enough that it does not default to generic Material UI.

The visual reference package should demonstrate:
- premium richness
- calmness
- hierarchy
- spacing
- companion-character integration
- realistic Jetpack Compose implementation
- light and dark mood
- empty states
- interaction states

These are implementation references, not fantasy art.

## 2. Required hero references
Create polished high-fidelity references for at least:

### A. App icon + launch relationship
Show:
- adaptive icon foreground/background concept
- monochrome icon concept
- launch/splash use
- no baked-in text dependency

### B. Welcome screen
Must communicate in one glance:
- this is a personal space, not a corporate productivity tool
- calm 2D companion presence
- strong warm editorial hierarchy
- one primary CTA
- privacy/local-first reassurance without legal clutter

### C. Setup — “What matters to you?”
Show:
- progress
- premium selectable cards
- companion used sparingly
- fixed primary CTA
- no giant scrolling form

### D. Setup — “What’s already in your world?”
Show Life Areas with enough richness to feel alive while still clearly interactive.

### E. Setup completion
A rewarding but quiet “Your Personal OS is ready” moment.
The companion may welcome the user with a short gesture.

### F. Today / Home — morning populated state
The single most important product reference.
Must show:
- greeting
- real-data hierarchy
- focus card
- a small set of continuation cards
- upcoming context
- companion integration without stealing attention
- bottom navigation

### G. Today / Home — truthful empty state
Must prove that the screen remains premium when there is no task/project history.
No fake data.

### H. Today / Home — evening state
Show how tone subtly changes:
- evening greeting
- completed/remaining context if real
- reflection entry point
- calmer/sleepier companion state

### I. Capture
Show:
- large thought-first input
- voice affordance
- structured interpretation
- editable fields before save
- project/priority/date context

### J. Journey
Show:
- selected day
- journal excerpt
- meaningful timeline
- Memory/Chapter entry without fabricated history
- optional reflective companion presence

### K. Me
Show:
- identity
- Life Areas
- real goals/growth evidence
- settings/privacy access
- no fake achievement metrics

### L. Dark mode
At minimum Today + Journey in dark mode to prove the system is intentionally designed rather than automatically inverted.

## 3. Required component reference sheet
One reference should show the production component family:
- primary/secondary/tertiary buttons
- chips
- task rows
- project rows
- input fields
- search field
- switches
- checkboxes
- reminder row
- focus card
- journal card
- empty-state card
- bottom nav
- sheet/dialog
- snackbar

## 4. Companion reference sheet
Before implementation, create one character reference covering:
- neutral idle
- blink/smile
- wave
- stretch
- tiny celebratory reaction
- seated/reflection state
- sleepy/evening state

The same silhouette/character identity must remain consistent across poses.

## 5. Companion composition rules
Suggested relative presence:
- Welcome: medium/prominent
- Setup: small-medium
- Today: small, secondary to information
- Empty states: medium
- Reflection: small-medium
- Dense lists/forms: absent or very small

Do not place the companion next to every card.

## 6. Richness without clutter
Premium richness should come from:
- typography
- layout rhythm
- illustration
- subtle depth
- meaningful color
- motion
- contextual states

Not from:
- dozens of widgets
- gradients everywhere
- fake charts
- dense icon decoration
- unnecessary glass effects

## 7. Reference dimensions
Primary phone reference frame should target a modern Android portrait around a 390–412dp logical width.

Also validate compact-height behavior separately.

## 8. Production-reality test
Every visual reference must answer:
> Can Astra reproduce this closely in Jetpack Compose without rasterizing the whole screen or relying on impossible effects?

If no, revise the reference.

## 9. Source-of-truth handling
Final approved reference exports and/or editable design links should be recorded in this repo under a manifest document.

If large design binaries cannot be stored directly, store:
- design ID/link
- purpose
- status
- exported preview path when appropriate
- approval date/version

## 10. Approval sequence
Recommended review order:
1. companion character identity
2. app icon
3. Welcome
4. setup screens
5. Today morning
6. Today empty/evening
7. Capture
8. Journey
9. Me
10. dark mode
11. component sheet

Once these establish a coherent system, detail screens can inherit rather than each requiring separate art direction.

## 11. Freeze condition
Visual package is ready for Astra only when:
- references feel like one product
- companion identity is consistent
- light/dark direction is coherent
- empty states remain premium
- every apparent control corresponds to real behavior
- no fake user data is used to make the mockups look richer
- implementation realism has been reviewed
