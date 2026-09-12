# Personal OS — Screen Specification Resolutions V1

**Status:** FROZEN override for V1  
**Date:** 2026-09-12

Several earlier `*_V0_1.md` screen documents intentionally ended with open questions. Those files remain valuable design history, but the decisions below resolve those questions for Astra implementation.

If an earlier screen document says `Open decisions`, use this file plus the Route/Data/Design frozen documents as the answer.

## 1. Today

- Focus selection is deterministic and explainable, with explicit user-pinned focus taking precedence.
- A specific entity card opens that entity's detail route; generic-tab fallback is not the normal behavior.
- Evening Reflection opens Capture in `evening-reflection` context, not blank universal capture.
- Empty Today remains visually rich but contains no invented work/history.
- Morning Brief is optional and user-controlled; it is not a mandatory notification engagement loop.

## 2. Plan

### Internal modes
Frozen V1 segmented modes:
- Today
- Week
- Projects

Habits/routines appear in Today/Week context and their own child list/detail flows; there is no fourth permanent `Routines` segment in V1.

### Week presentation
Use a calm grouped-by-day list for the next 7 days, with overdue/unscheduled context where relevant. Avoid a dense hourly calendar unless required for a specific scheduled item.

### Completed items
Completed-today items may remain in a collapsed/lower `Completed` section through the day. They must be reopenable. Do not instantly disappear in a way that makes accidental completion hard to recover.

### Focus pinning
Focus can be changed/cleared from Task Detail and from the Plan focus surface. Both paths update the same source of truth.

### Overdue treatment
Overdue work requires a decision, not shame styling. Offer open/reschedule/complete/cancel as appropriate. Do not accumulate recurring-task debt indefinitely.

### Project cards
Show status + next action + nearest meaningful milestone/deadline + last-touched context. Do not show arbitrary percentage.

## 3. Universal Capture

### Parse trigger
V1 supports explicit `Understand`/parse action as the reliable baseline. Optional lightweight auto-parse after an idle pause is allowed only if it never saves automatically and does not make correction harder.

### Type selection
Use compact selectable type chips/cards after interpretation. The user can change type before save.

### Confidence
Do not expose technical ML/parser confidence percentages. For ambiguity, use human language such as `I’m not completely sure — choose what this should be` and offer likely types/fields.

### Recurrence
Recurring task/reminder semantics are V1 and follow the frozen recurrence model.

### Attachments
Attachments are in V1 for supported domains. Universal Capture may offer attachment add after/alongside type selection where it does not clutter the first-input experience.

### Save
Exactly-once persistence. After an entity is saved, permission repair cannot create a duplicate on a second tap.

## 4. Journey

### Date navigation
Use full-week strip for quick daily movement plus a secondary calendar/date-picker route for older dates. No missing day of week.

### Memory
Memory is a first-class V1 entity as defined in the frozen schema.

### Chapters
Chapters ship in V1 as explicit user-created/named periods with user-controlled membership. Personal Intelligence may suggest a chapter but cannot silently author one as autobiographical fact.

### Completed tasks
Real completed tasks may appear as timeline history. On dense days, visual summarization is allowed while source events remain traceable.

### Journal typography
Reflective journal excerpts may use Newsreader. Operational metadata/date/actions remain Manrope.

## 5. Me

### Roles / self-description
User-authored. Setup may offer suggestions but Personal OS does not silently assign identity labels as fact.

### Current chapter
The reflective `Current chapter` concept maps to real Chapter data or an explicitly user-authored profile statement. It is not an inferred motivational sentence presented as fact.

### Achievements
No standalone gamified achievement/badge engine is required for V1. Meaningful completed goals/milestones/projects can be surfaced as real evidence/history.

### Life Areas
Life Areas are managed from Me → Life Areas and each has a V1 detail screen.

### Companion preference
V1 Settings includes companion enabled/disabled. Reduced-motion/system preference modifies animation independently.

## 6. Goals

- Plan is the primary planning home; Me may surface a concise goal overview.
- Progress percentage appears only when the user chose a legitimate measurable goal model.
- Qualitative goals use evidence, milestones, linked projects, habits and next action.

## 7. Habits

- Habits are distinct V1 records.
- One scheduled day normally has one HabitEvent summary state.
- Missed days do not become overdue debt and do not trigger guilt language.
- Streak may be computed as optional factual evidence only if later visually useful; V1 does not make streak preservation the core experience.

## 8. Hobbies and Skills

- Hobby and Skill are separate V1 object types.
- Skill may optionally belong to Hobby.
- Growth evidence is based on Session records/time/count, never fake proficiency levels.

## 9. Ideas

No permanent top-level Ideas tab/library in V1.

Ideas are accessible through:
- Search
- Journey/context history
- Project/Life Area links
- Idea Detail after capture/search

Converting/linking an idea preserves the original unless the user explicitly deletes it.

## 10. Search

V1 search indexes:
- LifeArea
- Goal
- Project
- Task
- JournalEntry
- Idea
- Memory
- Chapter
- Hobby
- Skill
- Session
- WeeklyReview

Result always retains source type/id for exact deep navigation.

## 11. Weekly Review

- review period is one calendar week according to local date semantics
- summaries are calculated from real records
- carry-forward changes source Task state/date; it does not exist only as review prose
- reflection is optional
- no engagement score or punitive weekly grade

## 12. Timeline / Archive

- chronological, real-data only
- ordinary source entities can be derived directly
- TimelineEvent is reserved for historical facts that edits would otherwise erase
- filtered archive does not duplicate/store a second history copy

## 13. Companion presence

Prominent/interactive on:
- Welcome
- selected Setup moments
- Today low-density hero/empty contexts
- optional Weekly Review close
- selected reflection/empty states

Normally absent on:
- Search result density
- Task/Project/Goal/Habit detail forms
- Capture while typing/parsing
- Settings lists

Tap reactions rotate through short approved states and return to idle. Companion never becomes a required workflow step.

## 14. Small-screen precedence

When a static visual uses a fixed composition that would clip at small height or large font scale:
- preserve hierarchy/colors/identity
- make content scroll
- keep primary CTA reachable
- collapse optional two-column layouts

Do not preserve screenshot geometry at the expense of usability.

## 15. Authority

For V1 implementation, decision precedence is:
1. `docs/decisions/DECISION_LOG.md`
2. frozen V1 docs (`*_FROZEN.md`, frozen visual manifest, frozen schema/baseline)
3. this resolution file
4. feature/screen working specs
5. source/reference HTML and Canva visual composition
6. earlier design-history docs

Astra must not reopen a resolved question merely because an older v0.1 file contains the original question text.
