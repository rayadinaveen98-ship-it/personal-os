# Character System V0.2 — Tiny Observatory Friend

**Project:** Personal OS  
**Status:** Founder-approved direction; visual details still being refined  
**Priority:** High design differentiator, lower than core functionality reliability

## 1. Locked companion direction

The founder selected **Concept B — Tiny Observatory Friend** on 2026-09-12.

This replaces the earlier open-ended choice between spirit-like, human-like, and animal-inspired companions.

The companion is now defined as a **small, peaceful, human-like 2D friend** who quietly inhabits Personal OS and makes the product feel emotionally warm, alive, and personal.

The character must remain:
- calm
- gentle
- observant
- warm
- neutral enough to belong to any user
- cute without becoming childish
- expressive without becoming noisy

## 2. Product role

The Tiny Observatory Friend is not a mascot pasted everywhere and not a virtual pet game.

It is a **gentle companion presence** — a small inhabitant of the user's private digital life space.

It should feel like:
- someone quietly sitting with you while you plan
- a soft witness to progress
- a calm presence during reflection
- a tiny source of delight when tapped

It must never feel like:
- a game HUD pet
- a noisy cartoon overlay
- an interruptive assistant
- a chat avatar constantly demanding attention
- a gamification mechanic that pressures the user

## 3. Approved visual foundation

The selected concept uses:
- warm 2D illustration
- small human-like proportions
- rounded friendly silhouette
- dark soft hair
- calm minimal facial features
- moss/sage clothing
- warm cream/ivory environment
- subtle plant/book/lantern/moon visual language
- gentle low-contrast shading
- editorial, peaceful presentation

The final character should preserve this emotional identity while being simplified enough for reliable Android animation and multiple reusable poses.

## 4. Character personality

Working personality:
- curious
- kind
- quiet
- patient
- never judgmental
- slightly playful when invited
- peaceful by default

The companion does not speak constantly. Most of its personality should come from posture, expression, small motion, and context.

## 5. Core states

### Idle
Default resting state.
Possible behavior:
- slow breathing
- occasional blink
- tiny head movement
- looking toward nearby content
- relaxed seated posture

### Tap reaction rotation
A tap should trigger one short reaction and then return to idle.
Possible reactions:
1. small wave
2. blink + smile
3. gentle stretch
4. tiny celebratory hand gesture
5. curious look left/right
6. small nod

Repeated taps should rotate or pseudo-randomize reactions so the character does not repeat the exact same motion every time.

### Morning
- alert but calm
- brighter posture
- subtle stretch / cup / morning-light motif if appropriate

### Focus / working
- attentive pose
- may look toward the primary focus card
- should not animate continuously while the user reads

### Completion
After a meaningful completion:
- subtle smile
- small clap / raised hand / happy nod
- no confetti explosion by default

### Empty / quiet day
- relaxed peaceful pose
- communicates that having nothing urgent is acceptable
- never implies guilt or inactivity failure

### Evening / reflection
- softer posture
- sleepy/calm expression
- optionally holding a warm cup / sitting beside a small lantern
- lower-energy motion

### Setup completion
- welcoming wave / tiny celebratory gesture
- then return to relaxed idle before Today appears

## 6. Placement strategy

Preferred V1 surfaces:
- launch/welcome
- selected onboarding screens
- onboarding completion
- Today / Home
- truthful empty states
- Evening Reflection
- Weekly Review intro/completion

Use sparingly on:
- Plan
- Journey
- Me

Avoid or minimize on:
- dense task lists
- structured forms
- search results
- date/time pickers
- destructive confirmation dialogs
- screens where it competes with reading or editing

## 7. Today integration

The companion may occupy a small intentional area near the greeting/focus region or another dedicated non-obstructive slot.

Behavior can respond to real context:
- morning → awake calm pose
- no urgent work → relaxed pose
- focus selected → attentive pose
- task completion → short happy response
- evening → quieter/sleepier pose

It must never cover cards or consume so much vertical space that Today becomes less useful.

## 8. Interaction rules

- tap target should be accessible, roughly 48dp minimum where feasible
- reaction should begin quickly after tap
- normal tap reactions approximately 300–900ms
- slightly longer contextual transitions may reach ~1200ms when justified
- reaction always settles back into a stable resting state
- no infinite distracting motion loops beyond extremely subtle idle breathing/blinking
- no sound required
- no haptic requirement, though very subtle haptics may be evaluated later
- tapping the companion should not navigate away unexpectedly

## 9. Reduced motion

If Android reduced-motion/accessibility preferences indicate reduced motion:
- use static pose changes or tiny fades
- disable unnecessary loops
- preserve tap acknowledgment without energetic movement

No essential information may be communicated only through animation.

## 10. Technical implementation direction

V1 should support a dedicated companion state model separate from business logic.

Working states can include:
- `Idle`
- `Morning`
- `Attentive`
- `Celebrate`
- `Reflective`
- `Sleepy`
- `EmptyCalm`
- transient tap reactions

Implementation options to evaluate before final build:
- Rive state machine
- Lottie compositions
- lightweight frame/vector animation
- Compose-driven pose assets

Selection criteria:
- performance on mid-range Android devices
- offline operation
- package size
- state-machine reliability
- ease of maintaining and extending poses
- licensing

The rest of the app must not depend on the animation engine being available.

## 11. Asset requirements before Astra execution

The approved visual package should eventually contain:
- canonical front/3-quarter idle character
- seated idle pose
- wave
- smile/blink
- stretch
- completion reaction
- morning pose
- evening/reflection pose
- empty-state pose
- reduced-motion/static equivalents where necessary

## 12. Things to avoid

- exaggerated anime proportions
- childish school-app styling
- loud saturated clothing
- massive eyes or meme expressions
- constant bouncing
- guilt/sadness when tasks are missed
- angry/disappointed reactions
- streak-loss reactions
- excessive speech bubbles
- using the character as a substitute for actual UI clarity

## 13. Acceptance rule

The Tiny Observatory Friend is successful only if it makes Personal OS feel:
- calmer
- warmer
- more premium
- more personal
- more alive

If any implementation makes the app feel cluttered, childish, distracting, or technically fragile, reduce the character's prominence rather than compromising the core product.

## 14. Current visual status

**Concept B is founder-approved.**

Next visual work:
1. refine the character into a canonical model sheet
2. freeze proportions/clothing/colors
3. create tap/context pose sheet
4. integrate it into Welcome, setup, Today and Evening Reflection references
5. record the resulting approved references in `VISUAL_REFERENCE_MANIFEST.md`
