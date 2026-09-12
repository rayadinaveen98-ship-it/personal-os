# Personal OS — Journal, Ideas & Memories Behavior v0.1

**Status:** Working draft

Personal OS should preserve reflective and creative life context with the same seriousness as tasks and projects.

## 1. Journal
A journal entry is user-authored reflection or record.

Working fields:
- id
- title optional
- body
- createdAt
- entryDate
- updatedAt
- mood optional only if user chooses
- lifeArea/project/goal links optional
- tags optional

## 2. Journal creation
Entry points:
- Universal Capture
- Journey Write/Reflect
- Evening Reflection
- Project/Goal note context where appropriate

The same journal data model should support all entry points.

## 3. Journal editing/deletion
User can:
- edit body/title/date where allowed
- update tags/links
- delete with confirmation/recovery policy

Editing must preserve truthful timestamps/updatedAt metadata.

## 4. Daily Reflection
Daily Reflection is a contextual journal flow, not a separate fake data silo.

Possible prompts:
- What was worth remembering today?
- What moved forward?
- What do you want to carry into tomorrow?

Prompts are optional. User can write freely.

## 5. Ideas
An idea is a lightweight captured thought that may later connect to a project/task/story/content concept.

Working fields:
- id
- title/body
- createdAt
- updatedAt
- status optional: inbox / active / archived / converted
- project/lifeArea links optional

## 6. Idea conversion
A future/useful action may convert or link an idea to:
- task
- project
- journal note

Conversion must preserve the original idea/history rather than silently deleting it.

## 7. Memories
A Memory is a deliberately preserved meaningful moment.

V1 options:
- explicit user-created Memory entity
- or a `saved as memory` flag/relationship on journal/activity content

Final data-model choice pending.

Potential memory fields:
- title
- date
- note
- linked journal/activity/project
- attachment later

## 8. Journey integration
Journey shows real journal/activity/memory content by date.

Rules:
- no fabricated entries
- no fake chapter labels
- dates derive from stored timestamps
- edited historical journal remains on the intended entry date while updatedAt is retained separately

## 9. Search
Search must include journal and ideas.

Preferred V1:
- local text search
- title/body match
- tags/context match where available

Semantic search can be future optional AI.

## 10. Privacy
Journal/memory content is highly personal.
Requirements:
- local-first storage
- app-lock integration when enabled
- safe backup/export strategy
- explicit delete behavior
- no cloud upload by default

## 11. Companion behavior
Reflection screens may use a small calm companion presence.

Suitable:
- seated/resting pose
- subtle acknowledgement after save

Avoid:
- reacting dramatically to journal content
- pretending to understand emotion beyond explicit user input

## 12. Empty states
### Journal
> **Nothing written here yet.**
> Write when there's something you want to remember—not because you have to.

### Ideas
> **No ideas waiting here.**
> Capture one the moment it appears.

### Memories
> **No saved memories yet.**
> Meaningful moments can be kept here when you choose.

## 13. Chapters
Long-term Chapters may group real memories/activity across a period.

A chapter may be:
- user-created
- suggested later and explicitly accepted

V1 should not auto-generate confident narrative labels from weak evidence.

## 14. Attachments
Photos/files/audio may be valuable later.
If included in V1:
- ownership and storage path must be durable
- export/backup must include them
- no broken references after restore

If not robust, defer rather than partially ship.

## 15. Acceptance criteria
- journal create/edit/delete works
- evening reflection creates real journal data
- ideas create/edit/archive/convert behavior is defined
- memories are explicit, not inferred as fact
- search finds saved content
- Journey date filtering is correct
- privacy controls apply
- no fabricated reflective content/history
