# Personal OS — Motion & Micro-Interactions v0.1

**Status:** Working draft

Motion in Personal OS must make the interface feel tactile, calm, premium and alive without slowing the user down.

## 1. Motion philosophy
Motion exists to communicate:
- cause and effect
- continuity
- selection
- completion
- hierarchy
- emotional warmth

Motion does not exist to chase attention.

## 2. Emotional target
The motion language should feel:
- soft
- responsive
- grounded
- deliberate
- quiet
- lightly playful where appropriate

Avoid:
- bouncy engagement loops
- casino-like celebration
- long hero intros
- aggressive spring effects
- constant background animation
- motion that competes with reading

## 3. Timing bands
Working timing guidance:
- direct press feedback: ~80–140 ms
- compact state change: ~160–240 ms
- card/sheet transition: ~220–360 ms
- richer character reaction: ~300–900 ms
- page-level transition: usually under ~450 ms

Exact timings/easing will be frozen after implementation prototypes.

## 4. Press behavior
Every tappable control should acknowledge touch immediately.

Possible treatment:
- slight scale reduction
- tonal surface change
- ripple/indication harmonized with theme
- subtle elevation change

Do not use all effects simultaneously.

## 5. Cards
Interactive cards should have:
- clear pressed state
- smooth destination transition where possible
- no hover-only affordance assumptions

Completion cards may briefly acknowledge completion before state updates.

## 6. Bottom navigation
Tab changes:
- active state transitions quickly
- icon/label emphasis changes smoothly
- no exaggerated jumping icons

Central Capture:
- immediate press feedback
- sheet/screen entrance should feel deliberate and fast

## 7. Capture
Capture is a high-frequency action.

Desired behaviors:
- composer enters cleanly
- type detection changes animate subtly
- parsed fields appear without jarring layout jumps
- successful save gets a short completion acknowledgement
- user is returned to the right context without unnecessary delay

## 8. Task completion
Task completion should feel satisfying but peaceful.

Recommended sequence:
1. user taps completion control
2. check state transitions
3. text/state updates
4. optional tiny companion reaction if visible
5. item moves/disappears according to list rules

Do not use confetti for ordinary task completion.

## 9. Empty states
If a screen changes from empty → populated, content should enter gently rather than flashing abruptly.

## 10. Companion motion
Idle motion:
- extremely subtle breathing/blink/sway
- not constant large movement

Tap reaction:
- rotate through small reaction set
- return to idle automatically
- prevent reaction stacking from rapid taps

Context reaction:
- may occur once on meaningful state change
- never loop celebratory motion

## 11. Time-of-day mood
Motion can subtly change emotional tone without changing functionality:
- morning: slightly fresher, more upright character state
- evening: slower/softer companion pose
- late night: reduced visual stimulation

This should remain subtle and should not alter information availability.

## 12. Reduced motion
Where Android/system accessibility signals are available:
- minimize non-essential movement
- replace animated transitions with fast fades/state changes
- preserve all functionality

No essential meaning should depend on motion.

## 13. Loading
Prefer:
- local-first instant content
- skeletons only when truly necessary
- restrained progress indicators

Avoid decorative loading sequences that hide fast local reads.

## 14. Success and celebration levels
Use three levels:

### Level 1 — ordinary completion
Check transition / tiny tone shift.

### Level 2 — meaningful milestone
Small richer animation or companion reaction.

### Level 3 — rare major achievement
More expressive but still elegant celebration.

The app must not turn every tap into a celebration.

## 15. Error motion
Errors should not shake the entire screen aggressively.
A small field emphasis, icon transition, or error message entrance is enough.

## 16. Performance rule
Motion must remain smooth on normal mid-range Android hardware.
Character/illustration systems must not cause dropped frames, excessive battery use, or large memory spikes.

## 17. Acceptance criteria
- touch feedback feels immediate
- no visible control appears dead
- no animation blocks task completion
- no repeated launch animation wastes time
- companion reactions are non-intrusive
- reduced-motion path remains usable
- transitions maintain context rather than feeling random
