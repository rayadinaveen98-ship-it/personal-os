# Character System V0.1 — Calm 2D Life Companions

**Project:** Personal OS  
**Status:** Working draft  
**Priority:** High design differentiator, lower than core functionality reliability

## Purpose
This document defines how Personal OS can include cute, calm, responsive 2D characters without making the product childish, distracting, or gimmicky.

## Why a character system exists
The founder wants the app to feel rich, warm, and alive. A small 2D character system can help Personal OS feel emotionally memorable and unique.

The character system should:
- soften the productivity experience
- make onboarding and empty states feel welcoming
- add delight to tapping and completing actions
- reinforce a calm emotional atmosphere
- create identity for Personal OS

## Product position
The character is not a mascot pasted everywhere.
It is a **gentle companion presence**.

The system should feel more like:
- a quiet companion
- a tiny inhabitant of the interface
- a soft emotional bridge

Not like:
- a game HUD pet
- a noisy cartoon overlay
- an interruptive assistant
- an always-talking coach

## Recommended art direction
### Core style
- 2D illustrated characters
- soft outlines or outline-free depending on final art tests
- rounded shapes
- warm, low-contrast palette
- minimal facial features
- emotionally legible poses
- peaceful idle expression
- small-scale presence relative to layout

### Emotional tone
Characters should feel:
- cute
- gentle
- safe
- calm
- observant
- supportive
- never overexcited

### Avoid
- loud saturated color explosions
- meme-like expressions
- exaggerated squash-and-stretch comedy
- overly childish schoolbook style
- overly anime-specific identity unless intentionally chosen later
- cluttered accessories

## Where characters may appear
Best candidate surfaces:
- splash / launch
- welcome screen
- onboarding/setup steps
- empty states
- Today home
- evening reflection prompt
- celebration / success moments
- no-data states in Journey or Plan
- weekly review intro / completion moments

Characters should generally not obstruct:
- dense task lists
- text entry zones
- detail forms
- search results
- accessibility-critical content

## Character interaction model
### Idle
The character rests quietly.
Examples:
- breathing
- blinking
- tiny swaying motion
- looking at a nearby card

### Tap response
When tapped, the character briefly reacts and returns to idle.
Tap reactions can rotate.
Examples:
- wave
- blink + smile
- stretch
- little hop
- look around
- tiny bow

### Context reaction
Character state can change subtly based on context.
Examples:
- morning: fresh / awake
- evening: sleepy / softer pose
- after task completion: subtle happy gesture
- after setup completion: welcoming pose
- reflection mode: calm seated pose

### Return behavior
After the interaction, the character must smoothly return to its resting state.

## Interaction rules
- animation duration should usually stay within ~300–900 ms
- no long loops that distract from reading
- never cover important text or buttons
- do not autoplay loud or exaggerated celebration motions
- no mandatory interaction with the character to complete a workflow
- tapping the character should never navigate away unexpectedly

## Technical recommendation
### V1 build recommendation
For Astra / Android implementation, use a staged approach:

#### Stage 1 — character-ready architecture
- reserve optional character slots on key screens
- support idle state + tap reactions
- keep state driven by screen/time/context

#### Stage 2 — one character, limited presence
- implement one calm companion
- surfaces: welcome, onboarding, Today, empty states, reflection
- 3 to 5 idle/tap states

#### Stage 3 — richer context awareness
- greeting variants
- task completion reactions
- evening reflection state
- no-data comforting state

### Rendering suggestion
Potential approaches:
- lightweight frame animation
- Lottie / Rive if licensing and tooling fit
- custom Compose animation with asset states

Need later engineering choice based on:
- APK size
- performance on mid-range Android devices
- ability to maintain multiple states
- tooling simplicity for Astra

## Accessibility and UX rules
- character animation should respect reduced-motion preference if supported later
- no essential information conveyed only by the character
- all important actions must remain understandable without the character
- the app must still feel premium if character rendering is disabled

## Open decisions later
These are not locked yet:
- exact character species/form (human-like, blob-like, animal-like, spirit-like)
- single character vs tiny family of companions
- illustration tool pipeline
- animation toolchain
- final placement conventions per screen

## Working recommendation right now
Start with **one small calm companion character**.
It should be simple enough to animate economically and flexible enough to appear on multiple screens.

Preferred traits:
- soft round silhouette
- neutral-gender presentation
- peaceful face
- adaptable to morning/evening moods
- can sit, wave, stretch, smile, observe

## Acceptance rule
If characters are added, they must make the app feel **more calm, more premium, and more alive**.
If they make it feel cluttered, childish, distracting, or technically fragile, they should be reduced or deferred.
