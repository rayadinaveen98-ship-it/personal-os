# Personal OS — Timeline & Archive v0.1

**Status:** Working screen specification.

## Purpose
Timeline/Archive is the deeper historical view behind Journey. It lets the user retrieve truthful past context without turning every event into a fake story.

## Entry points
- Journey → See all / Timeline
- Life Area → History
- Project/Goal → History
- Search result filters

## Default timeline
Chronological, newest first, derived from real records/events.

Possible entries:
- Journal entries
- Sessions/activity
- Memories
- meaningful task completion events when configured
- milestone completion
- project status changes
- explicit focus changes
- weekly/daily reviews

Do not show low-value technical events such as database edits or every field change.

## Filtering
V1 filters:
- date range
- type
- Life Area
- Project/Goal where relevant

Search within history routes through the global local search engine.

## Day grouping
Timeline groups by local calendar date using preserved local-date semantics.

Each day may show a concise chronological list. No generated prose summary is required.

## Archive
Archived structures are available through an Archive view/filter:
- Projects
- Goals
- Life Areas
- Habits
- Hobbies/Skills
- Chapters

Archived does not mean deleted.

## Restore
Archived entities can be restored to an active/paused state where valid.

Restore must not recreate old reminders automatically without checking current scheduling rules and asking/behaving according to the entity spec.

## Historical item navigation
Each entry opens the source detail when it still exists.

If the original source was deleted but an immutable TimelineEvent snapshot remains, show a read-only historical detail with clear wording rather than a broken route.

## Empty state
> Your history will appear here as you use Personal OS.

No sample life events are inserted.

## Companion
Optional only in a fully empty archive/timeline state. Avoid placing the companion repeatedly in dense chronology.

## Performance
Use paging/lazy lists. Do not load years of history into memory at once.

## Accessibility
- semantic date headings
- clear object-type labels/icons
- no meaning conveyed only by accent color
- large-text-friendly rows
