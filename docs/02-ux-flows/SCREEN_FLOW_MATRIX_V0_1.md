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
| Setup — privacy/app lock | 🟡 | security direction exists; final onboarding placement undecided |
| Setup completion / first Today | ✅ | handoff behavior drafted |

## Primary navigation
| Surface | Status | Notes |
|---|---:|---|
| Today / Home | ✅ | deep draft exists; several open decisions |
| Plan | ✅ | core draft exists |
| Universal Capture | ✅ | core draft exists |
| Journey | ✅ | core draft exists |
| Me | ✅ | core draft exists |
| Bottom navigation | 🟡 | mental model + component rules drafted; exact dimensions/freeze later |
| Global Search | ✅ | dedicated local-first search spec exists |
| Settings | ✅ | dedicated settings spec exists |

## Action/detail surfaces
| Surface | Status | Notes |
|---|---:|---|
| Task Detail | ✅ | covered in core-detail draft; needs final dedicated visual layout |
| Reminder Detail | ✅ | task/reminder behavior + detail rules drafted; final merged/separate UI decision pending |
| Project List | ✅ | Plan Projects behavior drafted |
| Project Create/Edit | 🟡 | behavior defined, exact create/edit screen layout pending |
| Project Detail | ✅ | core-detail + project behavior drafted |
| Goal List | 🟡 | behavior defined; dedicated list layout pending |
| Goal Create/Edit | 🟡 | behavior defined; exact screen pending |
| Goal Detail | ✅ | core-detail + goal behavior drafted |
| Habit/Routine List | 🟡 | behavior defined; exact list/screen pending |
| Habit/Routine Detail | ✅ | core-detail + habit behavior drafted |
| Life Area Detail | ⬜ | still needs dedicated specification |
| Hobby/Skill Detail | ✅ | core-detail + growth behavior drafted |
| Journal Detail/Edit | ✅ | core-detail + journal behavior drafted |
| Idea Detail/Edit | ✅ | core-detail + idea behavior drafted |
| Activity/Session Detail | ✅ | core-detail + session behavior drafted |
| Daily Reflection | ✅ | contextual behavior defined through Capture/Journey |
| Weekly Review | ✅ | dedicated screen spec exists |
| Life Timeline / archive | 🟡 | Journey direction exists; dedicated archive/timeline spec pending |
| Memories | 🟡 | behavior direction exists; exact entity/UI choice pending |
| Chapters | 🟡 | concept bounded; V1 exact scope pending |

## System surfaces
| Surface | Status | Notes |
|---|---:|---|
| Notification permission education | ✅ | contextual rules defined |
| Exact-alarm repair flow | ✅ | behavior/architecture defined; exact UI pending |
| Microphone permission education | ✅ | contextual rules defined |
| Biometric/app lock setup | 🟡 | architecture/security direction exists; final V1 inclusion/policy pending |
| Backup/export | ✅ | behavior/security direction defined; exact UI pending |
| Data restore | ✅ | transactional restore direction defined; exact UI pending |
| Theme settings | ✅ | System/Light/Dark defined |
| Companion settings | ✅ | visibility/motion controls direction defined |
| Error/retry patterns | 🟡 | system principles exist; per-screen final copy/states still needed |
| Offline behavior | ✅ | local-first architecture and acceptance rules defined |

## Design-system surfaces
| Area | Status | Notes |
|---|---:|---|
| Core design language | ✅ | Warm Personal Observatory |
| App icon / brand mark | ✅ | concept-selection process defined |
| Motion / micro-interactions | ✅ | timing/motion system drafted |
| 2D companion system | ✅ | character behavior/staging drafted |
| Semantic light palette | ✅ | working semantic values exist; final contrast audit pending |
| Semantic dark palette | ✅ | working semantic values exist; final contrast audit pending |
| Typography family/metrics | 🟡 | candidates + scale exist; licensing/final family pending |
| Buttons | ✅ | component rules drafted |
| Inputs | ✅ | component rules drafted |
| Chips / selectors | ✅ | component rules drafted |
| Lists / rows | ✅ | component rules drafted |
| Sheets / dialogs | ✅ | component rules drafted |
| Snackbar / transient feedback | ✅ | component rules drafted |
| Date/time picker approach | 🟡 | behavior need defined; exact Android component treatment pending |
| Accessibility contrast audit | ⬜ | final palette/component audit pending |

## Behavior systems
| System | Status | Notes |
|---|---:|---|
| Tasks / subtasks | ✅ | lifecycle and core behavior drafted |
| Reminders / recurrence | 🟡 | reminder behavior drafted; recurrence engine still needs exact rule spec |
| Projects / milestones | ✅ | behavior drafted |
| Goals | ✅ | behavior drafted |
| Habits / routines | ✅ | behavior drafted |
| Hobbies / skills / sessions | ✅ | behavior drafted |
| Journal / memories | ✅ | behavior drafted; Memory representation still open |
| Ideas | ✅ | behavior drafted |
| Activity/session tracking | ✅ | behavior drafted |
| Daily Reflection | ✅ | behavior drafted |
| Weekly Review | ✅ | behavior + screen drafted |
| Search | ✅ | screen behavior drafted |
| Personal Intelligence / prioritization | ✅ | deterministic v0.1 drafted |
| Morning Brief | ✅ | behavior defined inside Intelligence/Today; exact presentation still open |
| Evening Reflection | ✅ | behavior defined |
| relationship/link graph | 🟡 | data-model direction exists; exact generic-relation scope pending |
| attachments | 🟡 | limited V1 direction; exact subset/storage pending |
| achievements | 🟡 | must be real if included; exact V1 inclusion pending |
| backup/export | ✅ | privacy/data direction drafted |
| app lock/privacy | 🟡 | privacy architecture drafted; final App Lock V1 inclusion pending |

## Data / engineering / QA
| Area | Status | Notes |
|---|---:|---|
| V1 data model | ✅ | working domain/schema draft exists |
| Android architecture | ✅ | stack/layers/platform services/test direction drafted |
| Privacy/security | ✅ | local-first privacy spec exists |
| Backup/restore | ✅ | versioned transactional direction exists |
| V1 acceptance test plan | ✅ | broad release acceptance plan exists |
| Exact dependency versions | ⬜ | freeze immediately before implementation |
| Application ID | ⬜ | needs founder/product decision or safe convention |
| CI workflow | ⬜ | to be created before Astra build begins |
| Release signing strategy | ⬜ | internal/release path pending |

## Highest-priority remaining specification work
1. Life Area Detail / management.
2. Exact recurrence engine semantics.
3. Dedicated create/edit forms for Projects, Goals, Habits.
4. Life Timeline/archive and Memories/Chapters V1 boundary.
5. App Lock final inclusion/policy.
6. Attachments exact V1 subset.
7. Typography final font verification + accessibility contrast audit.
8. Application ID, Android SDK/dependency freeze, CI/build contract.
9. Final visual mockups for the highest-value screens.
10. Resolve all open decisions and mark implementation documents frozen.

## Handoff rule
Astra must not receive the final end-to-end build instruction while high-impact rows above are still unspecified in ways that require Astra to invent product behavior.
