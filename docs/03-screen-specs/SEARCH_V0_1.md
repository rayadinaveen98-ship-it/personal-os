# Personal OS — Global Search v0.1

**Status:** Working draft

Search is the retrieval layer for the user's personal system. It must feel fast, private, and useful across domains without requiring cloud AI.

## 1. Purpose
Search answers:
- Where did I put that thought/task/project/journal entry?
- What have I written about this topic before?
- Which records are connected to this project/life area?

## 2. Entry points
Search is reachable from:
- Journey
- Me or top-level contextual icon where appropriate
- optional global action/command surface later

Do not add a permanent bottom-nav item unless later usability evidence justifies it.

## 3. Searchable V1 domains
At minimum:
- tasks
- reminders
- projects
- goals
- journal entries
- ideas
- activities/sessions
- hobbies
- skills
- weekly reviews
- life areas

Optional if implemented robustly:
- notes
- milestones
- attachments metadata

## 4. Result presentation
Each result shows:
- object type
- primary title/excerpt
- useful context
- date/status where meaningful

Examples:
- `TASK · Personal OS` — Finish setup behavior spec · Due today
- `JOURNAL · 10 Sep` — “…felt calmer after…”
- `PROJECT` — Personal OS · Active

Tap result → exact detail screen.

## 5. Query behavior
V1 should support local text search over normalized fields.

Preferred matching order:
1. exact/strong title match
2. title prefix/contains
3. body/notes match
4. tag/context match
5. linked entity names

Semantic AI search is future optional; search must remain useful offline.

## 6. Filters
Lightweight filters may include:
- All
- Tasks
- Projects
- Journal
- Ideas
- Growth

More filters can appear in a sheet if needed.
Avoid a complex desktop-style filter panel.

## 7. Recent searches
Optional local recent-search history can be shown.
User should be able to clear it.
Do not sync by default.

## 8. Empty query state
Possible content:
- recent searches
- recently opened records
- useful category shortcuts

Do not show fake user records.

## 9. No results state
> **Nothing matched that search.**
> Try another phrase or look within a specific area.

Optional companion can appear subtly.

## 10. Privacy
Search runs locally for V1.
Journal/body content must not be sent to external services for basic search.

## 11. Performance
Search should feel instant on a normal V1-sized local database.
Use indexing/FTS where appropriate in the final data architecture.

## 12. Accessibility
- search field has clear label
- filters announce selected state
- result type and title readable in order
- keyboard focus behavior is predictable

## 13. Acceptance criteria
- all documented V1 domains are discoverable
- result opens exact entity
- deleted/archived records follow their defined visibility rules
- no fake result previews
- works offline
- empty/no-result states are polished
