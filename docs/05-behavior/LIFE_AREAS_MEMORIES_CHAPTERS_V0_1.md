# Personal OS — Life Areas, Memories & Chapters v0.1

**Status:** Working specification.

## 1. Life Areas
Life Areas are broad user-defined parts of life that provide context across the system.

Examples:
- App Building
- Game Development
- Filmmaking
- Reading
- Health
- Learning

These are examples only. Personal OS must not force a universal category set.

## 2. Life Area behavior
A Life Area may contain or relate to:
- Projects
- Goals
- Habits/Routines
- Hobbies
- Skills
- Journal entries
- Ideas
- Sessions/Activity

A record may belong to zero, one, or multiple Life Areas depending on final schema simplicity. V1 should support at least one primary Life Area and optional additional links if implementation remains clean.

## 3. Life Area screen
Each Life Area Detail should show truthful, useful context rather than dashboard filler:
- title and optional icon/accent
- active projects
- active goals
- recent sessions/activity
- relevant habits
- recent journal/ideas where explicitly linked
- recently touched state

Actions:
- edit name/icon/accent
- create project/goal/habit in this area
- view history
- archive area

Archiving a Life Area must not delete linked records.

## 4. Memories
A Memory is a user-preserved meaningful moment or artifact, not every event in the timeline.

V1 memory creation paths:
- explicitly save a journal entry or activity as a Memory
- create a Memory directly from Capture
- optionally attach an image/file if attachment scope permits

Memory fields may include:
- title
- note/body
- date/time
- related people as plain user-entered text only in V1 if needed
- related Life Area/project
- optional attachment
- favorite/pinned state

Personal OS must not auto-label arbitrary activity as a meaningful memory without user confirmation.

## 5. Journey relationship
Journey contains chronological truth. Memories are curated highlights inside that truth.

A timeline event can exist without being a Memory.
A Memory can link back to its originating journal/activity record.

## 6. Chapters — V1 decision
Chapters are **included in V1 only as user-created/explicitly named periods**, not as AI-invented autobiographical eras.

Examples:
- Building Personal OS
- Learning Blender
- Our summer trip
- First year of FrameByNavin

A Chapter can have:
- title
- optional description
- start date
- optional end date
- related Life Areas/projects/goals
- selected Memories/journal entries/activity links

## 7. Chapter creation
The user creates a Chapter intentionally.

Personal Intelligence may suggest:
> You have a lot of activity around Personal OS this month. Create a chapter?

But it must never silently create a life chapter or write a narrative about the user's life as fact.

## 8. Chapter presentation
Chapters should feel editorial and rich in Journey:
- restrained cover/accent treatment
- title
- date range
- selected meaningful items
- optional user-written summary

No fake milestone counts or fabricated story copy.

## 9. Empty states
No memories:
> Nothing has been saved as a memory yet.
> When something feels worth keeping, you can preserve it here.

No chapters:
> Chapters help you group meaningful periods of your life.
> Create one when a period starts to feel like a story worth remembering.

## 10. Delete/archive semantics
Deleting a Memory removes the Memory wrapper/link but should not automatically delete the original Journal/Activity record unless that original itself is explicitly deleted.

Archiving a Chapter hides it from active presentation but preserves its linked history.

## 11. Companion use
The calm companion may appear in empty states or Chapter opening moments, but must not narrate or interpret sensitive memories on its own.

## 12. V1 acceptance
- Life Areas can be created/edited/archived.
- New records can be assigned to a Life Area.
- Life Area Detail shows real linked data.
- Memories are explicit, user-controlled highlights.
- Chapters are explicit/user-created, not fabricated.
- Removing a wrapper does not unexpectedly destroy source history.
