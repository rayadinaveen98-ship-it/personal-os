# Personal OS — Screen & Flow Matrix v0.1

**Status:** Working control document

This matrix tracks the product surfaces that must be specified before Astra receives the full build handoff.

Legend:
- ✅ draft exists
- 🟡 needs deeper behavior/state detail
- ⬜ not yet specified
- 🔒 frozen for implementation

## Entry / setup
| Surface | Status | Notes |
|---|---:|---|
| App icon / adaptive icon | ✅ | brand direction drafted |
| Splash / launch | ✅ | first-run + normal launch behavior drafted |
| Welcome | ✅ | premium experience + companion behavior drafted |
| Setup — name | ✅ | behavior drafted |
| Setup — desired help | ✅ | behavior drafted |
| Setup — life areas | ✅ | behavior drafted |
| Setup — initial real item | ✅ | behavior drafted |
| Setup — daily rhythm | ✅ | behavior drafted |
| Setup — privacy/app lock | 🟡 | final placement undecided |
| Setup completion / first Today | ✅ | handoff behavior drafted |

## Primary navigation
| Surface | Status | Notes |
|---|---:|---|
| Today / Home | ✅ | deep draft exists; several open decisions |
| Plan | ✅ | core draft exists |
| Universal Capture | ✅ | core draft exists |
| Journey | ✅ | core draft exists |
| Me | ✅ | core draft exists |
| Bottom navigation | 🟡 | mental model locked; exact component freeze later |
| Global Search | ⬜ | needs dedicated spec |
| Settings | ⬜ | needs dedicated spec |

## Action/detail surfaces
| Surface | Status |
|---|---:|
| Task Detail | ⬜ |
| Reminder Detail | ⬜ |
| Project List | 🟡 |
| Project Create/Edit | ⬜ |
| Project Detail | ⬜ |
| Goal List | ⬜ |
| Goal Create/Edit | ⬜ |
| Goal Detail | ⬜ |
| Habit/Routine List | ⬜ |
| Habit/Routine Detail | ⬜ |
| Life Area Detail | ⬜ |
| Hobby/Skill Detail | ⬜ |
| Journal Detail/Edit | ⬜ |
| Idea Detail/Edit | ⬜ |
| Activity/Session Detail | ⬜ |
| Daily Reflection | 🟡 | contextual behavior defined through Capture/Journey, exact surface undecided |
| Weekly Review | ⬜ |
| Life Timeline / archive | ⬜ |
| Memories | ⬜ |
| Chapters | ⬜ |

## System surfaces
| Surface | Status |
|---|---:|
| Notification permission education | 🟡 |
| Exact-alarm repair flow | 🟡 |
| Microphone permission education | 🟡 |
| Biometric/app lock setup | ⬜ |
| Backup/export | ⬜ |
| Data restore | ⬜ |
| Theme settings | ⬜ |
| Companion settings | ⬜ |
| Error/retry patterns | 🟡 |
| Offline behavior | 🟡 |

## Design-system surfaces
| Area | Status |
|---|---:|
| Core design language | ✅ |
| App icon / brand mark | ✅ |
| Motion / micro-interactions | ✅ |
| 2D companion system | ✅ |
| Semantic light palette | ⬜ |
| Semantic dark palette | ⬜ |
| Typography family/metrics | ⬜ |
| Buttons | ⬜ |
| Inputs | ⬜ |
| Chips / selectors | ⬜ |
| Lists / rows | ⬜ |
| Sheets / dialogs | ⬜ |
| Snackbar / transient feedback | ⬜ |
| Date/time picker approach | ⬜ |
| Accessibility contrast audit | ⬜ |

## Behavior systems still requiring dedicated specs
- Tasks and subtasks
- Reminders and recurring schedules
- Projects and milestones
- Goals
- Habits/routines
- Hobbies and skills
- Journal / memories
- Ideas
- Activity/session tracking
- Daily Review
- Weekly Review
- Search
- Personal Intelligence / prioritization
- Morning Brief
- Evening Reflection
- relationship/link graph
- attachments
- achievements if included
- backup/export
- app lock/privacy

## Handoff rule
Astra must not receive the final end-to-end build instruction while high-impact rows above are still unspecified in ways that require Astra to invent product behavior.
