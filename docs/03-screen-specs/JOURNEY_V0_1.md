# Personal OS — Journey Screen v0.1

**Status:** Working draft

Journey is the memory and reflection side of Personal OS. It should help the user see where their time, attention, work, ideas and meaningful moments actually went.

## 1. Purpose
Journey answers:
1. What happened today / recently?
2. What did I write or remember?
3. What did I move forward?
4. What patterns or chapters are emerging over time?

Journey is not a social feed.

## 2. Core content sources
Only real stored data may appear:
- journal entries
- activity/session logs
- completed tasks
- project milestones/events
- captured ideas when relevant
- goal progress events
- meaningful habit/routine events if implemented
- manually saved memories

No fabricated timeline events, chapter counts or memories.

## 3. Date navigation
Primary date selector should include the full week or another clearly complete date-navigation pattern.

Tap a date → Journey filters to that date.

User should also be able to move backward through history and return to Today quickly.

## 4. Daily page
For selected date, show truthful sections based on available data.

Potential sections:
- Journal / reflection
- Timeline
- Completed / moved forward
- Ideas captured
- Meaningful moments

If a section has no data, omit it unless its empty state offers a useful action.

## 5. Journal hero
If a journal/daily reflection exists:
- show a readable excerpt
- tap → Journal Detail
- actions from detail: edit, delete, link context/tags where supported

If multiple journal entries exist, show the most relevant or a compact stack/list.

## 6. Timeline
Chronological events for the selected date.

Each event must have a real timestamp or defensible ordering.

Examples:
- completed task
- logged work session
- saved journal entry
- reached milestone

Tap event → relevant entity/detail when possible.

## 7. Write / reflect
`Write` or `Reflect` opens Universal Capture in Journal/Reflection context for the selected date.

Do not open an uncontextualized generic capture screen.

## 8. Search
Search icon opens global Search with Journey-friendly results available.
Search may match:
- journal text
- project/activity title
- idea content
- task content
- tags/notes where supported

## 9. Memories & chapters
This is a richer long-term layer, not fake decoration.

### Memories
A memory can be:
- explicitly saved by the user
- promoted from a journal/moment
- linked to media/attachment later

### Chapters
A chapter should be based on real date ranges and user-defined or intelligently suggested life context.
Never show invented `7 moments · 3 milestones` counts.

V1 may begin with a simpler month/period archive if full Chapters are not ready.

## 10. Empty states
### No data for selected day
> **A quiet day here.**
> Nothing has been recorded for this date yet.

Actions:
- Write something
- Capture a memory

A calm companion can appear here in a resting/observing pose.

### New user
Do not show fake example history as though it belongs to the user.
Educational examples, if needed, must be visibly labeled as examples/onboarding guidance.

## 11. Evening experience
In evening hours, Today may link to Journey reflection.
Journey can present a softer visual mood but should not hide daytime history.

Companion may use a seated/sleepier state.

## 12. Premium experience
Journey can be slightly more editorial than Plan:
- more breathing room
- selective serif use for reflective text
- calm date transitions
- restrained accent colors
- rich but truthful empty states

## 13. Privacy
Journey may contain the most sensitive personal data in the product.
Design must make privacy/app-lock/export/delete controls easy to discover through Me/Settings.

## 14. Accessibility
- timeline order announced clearly
- dates have complete accessible labels
- decorative companion/illustrations do not pollute screen-reader output
- text remains readable at larger scaling

## 15. Open decisions
1. Full-week strip vs calendar/date picker hybrid.
2. Exact definition of a `Memory` entity in V1.
3. Whether Chapters ship in V1 or begin as monthly archive.
4. Whether completed tasks appear individually or summarized in dense days.
5. Whether journal excerpts use serif typography by default.
