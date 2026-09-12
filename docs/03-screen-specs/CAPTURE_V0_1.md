# Personal OS — Universal Capture v0.1

**Status:** Working draft

Universal Capture is the fastest way to get something out of the user's head and into Personal OS without deciding first where it belongs.

## 1. Purpose
Capture should accept messy human input and turn it into useful structured personal data.

Possible outcomes:
- Task
- Reminder
- Idea
- Journal
- Activity / session log
- Project note
- Goal-related note
- Habit/routine input when supported

## 2. Entry points
Capture is reachable from:
- central bottom-navigation action
- Today contextual actions
- Plan add actions
- Journey write/reflect actions
- Project/Goal context
- notification actions where appropriate

Each entry can provide context without creating separate capture systems.

## 3. Composer
Primary surface:
- large text input
- voice action
- optional context indicator
- `Understand` / smart parse action where needed

The user should be able to type naturally, e.g.:
- `Remind me tomorrow at 9 to work on Personal OS`
- `Idea: make weekly review more visual`
- `Worked on Blender for 45 minutes`
- `Today felt calmer after finishing the setup spec`

## 4. Intelligence strategy
V1 should be useful without paid AI.

Use deterministic/rules-based parsing first for:
- intent type
- dates
- times
- relative time
- priority words
- recurrence phrases where supported
- duration
- known project/life-area hints

Architecture should allow an optional smarter AI layer later, but core capture must not break without it.

## 5. Parsed result
After understanding input, show a calm structured preview.

Fields depend on type.

### Task / Reminder
- title
- type
- project
- life area
- due date/time
- reminder time
- priority
- recurrence
- notes

### Journal
- text/body
- date/time
- optional linked context/tags

### Idea
- title/body
- optional linked project/life area

### Activity
- title/context
- duration
- completed timestamp
- optional project/skill/hobby link

## 6. Correction rule
Every field inferred by Personal OS must be editable before save.

The user should never need to rewrite the entire sentence just to fix one parsed field.

Editable controls may use:
- chips
- inline fields
- date/time picker
- project picker
- priority selector

## 7. Save behavior
Save must:
- persist exactly once
- show a short success acknowledgement
- schedule reminders if required
- return to originating context appropriately

Repeated taps after persistence must not create duplicates.

## 8. Permission behavior
### Microphone
Ask only when Voice Capture is first invoked.

### Notifications
Ask only when saving a reminder/brief requires notification delivery.

### Exact alarms
If exact scheduling requires special Android access:
- explain why
- provide direct `Open settings`
- keep the saved item truthful about its scheduling state

No permission dead-end.

## 9. Voice
Voice capture should:
- display partial/final transcription when supported
- allow text correction before parse
- never auto-save a mistaken transcript
- degrade gracefully if speech recognition is unavailable

## 10. Contextual modes
Capture can open with a lightweight context banner such as:
- `Evening Reflection`
- `Add task for Saturday`
- `Add to Personal OS project`
- `Log learning session`

Context can prefill type or linked entity but must remain editable unless the workflow explicitly requires it.

## 11. Evening Reflection
When opened from Today/Journey:
- default type: Journal / Daily Reflection
- gentle prompt such as `What was worth remembering today?`
- optional prompts can be skipped
- saved entry becomes real Journey history

## 12. Empty / ambiguous parse
If Personal OS is unsure:
- say so calmly
- show likely type(s)
- let user choose

Do not silently force low-confidence input into the wrong entity.

## 13. Character behavior
Companion presence should be minimal in Capture because typing requires focus.
Possible use:
- tiny resting illustration in empty composer state
- subtle acknowledgement after save

Do not animate near the keyboard while the user is typing.

## 14. Premium experience
- composer feels spacious, not form-heavy
- parsed fields appear progressively
- correction is easy
- transitions avoid layout jumps
- keyboard behavior is polished
- no technical parser language shown to user

## 15. Safety / truthfulness
- no invented project links
- no fabricated dates
- ambiguous date interpretation should be visible/editable
- save state should reflect whether reminder scheduling actually succeeded

## 16. Accessibility
- voice is optional, never required
- all parsed controls have accessible labels
- selected state not conveyed by color only
- date/time controls support keyboard/screen-reader paths

## 17. Open decisions
1. Whether parse happens automatically after pause or only on explicit action.
2. Exact type-selection UI.
3. Confidence/ambiguity presentation.
4. Whether recurring reminders are in V1 or V1.1.
5. Whether attachments/photos belong in first V1 Capture.
