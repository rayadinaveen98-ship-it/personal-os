# Personal OS — Hobbies, Skills & Sessions Behavior v0.1

**Status:** Working draft

Personal OS should treat learning, creativity and hobbies as first-class life areas rather than secondary productivity metadata.

## 1. Definitions
### Hobby
An ongoing interest or practice the user enjoys or wants to nurture.
Examples:
- filmmaking
- reading
- piano
- game development

### Skill
A capability the user is deliberately developing.
Examples:
- Blender lighting
- Kotlin
- cinematography

A hobby can contain multiple skills.

### Session
A real block of time spent practicing/learning/creating.

## 2. Working hobby fields
Potential fields:
- id
- title
- description optional
- status: active / paused / archived
- lifeAreaId optional
- createdAt
- updatedAt
- visual accent/icon optional

## 3. Working skill fields
Potential fields:
- id
- hobbyId optional
- title
- description optional
- status
- currentFocus optional
- createdAt
- updatedAt

Avoid fake levels such as `Level 7` unless the user explicitly defines a scale/curriculum.

## 4. Session fields
Potential fields:
- id
- title/context
- start timestamp or completed timestamp
- duration
- hobbyId optional
- skillId optional
- projectId optional
- notes optional
- source: manual / capture / timer if timer ships later

## 5. Logging a session
User can log through:
- Universal Capture natural language
- Hobby/Skill Detail
- optional session timer later

Examples:
- `Practiced Blender lighting for 45 minutes`
- `Read Pride and Prejudice for 30 minutes`

Parser may infer hobby/skill only when a real known match exists or user confirms it.

## 6. Hobby/Skill Detail
Should answer:
- what am I currently exploring?
- when did I last practice?
- how much real time/activity have I logged?
- what is my current focus?
- what should I continue next?

Possible sections:
- current focus
- recent sessions
- total recent time
- notes/resources
- related project/goal

## 7. Progress philosophy
Prefer real evidence:
- sessions this week/month
- time logged
- user-defined milestones
- current focus

Avoid fake mastery percentages.

## 8. Continue learning card
Today may show a hobby/skill card only if:
- it is active
- there is real recent history or explicit user intent
- there is a meaningful continuation target

Example:
> **Continue learning**
> Blender · last session 2 days ago

No invented lesson numbers.

## 9. Pausing
Pausing a hobby/skill:
- preserves history
- removes it from automatic Today suggestions
- does not delete sessions

## 10. Notes/resources
V1 may support lightweight notes/links attached to hobby/skill.
Do not turn V1 into a full LMS/bookmark manager.

## 11. Goal/project links
Examples:
- skill supports a goal
- hobby session contributes to a creative project

Links are optional and should reduce friction rather than force categorization.

## 12. Companion behavior
Suitable contexts:
- empty hobby/skill detail
- session completion
- meaningful practice milestone

Reaction should remain calm and never judge low practice volume.

## 13. Empty state
> **Nothing you're actively learning here yet.**
> Add a hobby or skill when there's something you want to keep returning to.

## 14. Acceptance criteria
- create/edit/pause/archive works
- sessions persist with real duration/timestamps
- session totals derive from stored events
- Today continuation is truthful
- no fake lesson/level/progress values
- history survives status changes
