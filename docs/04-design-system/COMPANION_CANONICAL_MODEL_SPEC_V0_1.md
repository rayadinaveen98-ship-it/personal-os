# Personal OS — Tiny Observatory Friend Canonical Model Spec v0.1

**Status:** Visual production spec — canonical image/model sheet pending final founder approval.

## Identity
The Personal OS companion is the **Tiny Observatory Friend**: a small human-like 2D character representing quiet presence, curiosity and supportive companionship.

## Canonical appearance
- human-like, neutral-gender presentation
- visually reads as a young adult / timeless companion, not a child mascot
- soft dark-brown to near-black tousled hair
- rounded but not baby-like face
- minimal warm facial features
- small dark eyes
- subtle warm blush only when useful
- moss/sage long-sleeve top or hoodie with minimal detailing
- warm cream / stone trousers
- simple neutral footwear or seated sock treatment
- no branding text on clothing
- no futuristic accessories
- no loud jewelry
- no permanent pet required in the base character design

## Proportions
- compact stylized 2D proportions
- head slightly oversized for emotional readability, but significantly less chibi than a children's game mascot
- body must remain easy to recognize at small in-app sizes
- hands/arms large enough to read gestures such as wave/stretch
- silhouette should remain consistent across poses

## Rendering
- warm editorial 2D illustration
- soft low-contrast shading
- controlled outline or soft edge treatment
- no photorealism
- no 3D render look
- no anime-specific costume language
- no hyper-saturated cartoon palette
- must fit Warm Personal Observatory palette

## Required canonical views/states
1. neutral standing/front or near-front reference
2. neutral 3/4 reference
3. seated idle / peaceful
4. gentle wave
5. blink + small smile
6. curious/thinking look
7. stretch
8. subtle completion celebration
9. morning/awake state
10. focused/attentive state
11. quiet-empty-state companion
12. reflective/evening state
13. sleepy/night state
14. setup-complete welcoming state

## Tap response set
Repeated taps may rotate among:
- wave
- smile/blink
- curious head tilt
- tiny stretch
- small nod
- brief happy hand gesture

Every response returns to the previous contextual idle state after a short animation.

## Motion target
- calm and responsive
- reaction animations roughly 300–900 ms where possible
- subtle idle breathing/blink loop
- no constant bouncing
- no exaggerated squash/stretch comedy
- no noisy particles except very restrained completion accents

## Scale usage
### Hero use
Welcome/setup-completion/selected empty state: larger illustration allowed.

### Embedded use
Today cards/reflection: small companion vignette or cropped upper-body illustration.

### Dense functional screens
Plan lists, Search results, forms: character absent or extremely minimal.

## Accessibility
- companion never communicates required information alone
- reduced motion replaces animated reactions with static state changes
- character cannot cover controls or text
- companion visibility can be disabled in Settings without breaking layout

## Implementation constraint
Astra must use one consistent canonical character system. It must not regenerate or redesign the character independently per screen.

Preferred eventual implementation options, to be evaluated at build time:
- Rive state machine
- Lottie state clips
- lightweight vector/frame animations

Final choice should prioritize consistency, performance and maintainability on mid-range Android devices.
