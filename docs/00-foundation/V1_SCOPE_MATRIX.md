# Personal OS — V1 Scope Matrix

Status: **Working V1 boundary — product direction approved; detailed feature semantics still to be frozen**

The purpose of this document is to stop scope drift before implementation.

## In V1 — Core daily system

| Capability | V1 | Notes |
|---|---:|---|
| Onboarding & personalization | Yes | Preferred name, reasons for use, life areas, initial context, daily rhythm |
| Today / Home | Yes | Most important screen; truthful, context-aware command center |
| Universal Capture | Yes | Text first, voice supported; structured correction before save |
| Tasks | Yes | Create/edit/delete/archive/complete/reopen, priority, due date |
| Subtasks | Yes | Lightweight hierarchy; behavior to be specified |
| Reminders | Yes | Exact/local reminder behavior with permission recovery |
| Recurring tasks/reminders | Yes | Clear recurrence rules and history |
| Projects | Yes | Create/edit/archive, status, next action, milestones, related tasks |
| Milestones | Yes | Project-oriented and optionally goal-related |
| Goals | Yes | Meaningful outcome model, not arbitrary score |
| Habits / routines | Yes | Non-punitive; completion history without shame mechanics |
| Journal / diary | Yes | Rich text not required for first V1 unless justified; edit/delete/search |
| Ideas | Yes | Capture, organize, connect to projects/goals/actions |
| Hobbies | Yes | First-class life context, not merely tags |
| Skills | Yes | Growth/session context; no fabricated proficiency percentages |
| Activity / sessions | Yes | Log real work/learning/creative sessions |
| Search | Yes | Cross-domain local search |
| Journey / life timeline | Yes | Truthful chronology of meaningful user data |
| Daily reflection | Yes | Optional, lightweight, context-aware |
| Weekly review | Yes | Truthful summaries, decisions, carry-forward |
| Morning brief | Yes | Optional; derived from actual commitments/context |
| Evening close | Yes | Optional; reflection + unfinished decisions |
| Me / personal space | Yes | Identity, life areas, goals, growth, settings entry |
| Settings | Yes | Theme, rhythm preferences, permissions/status, privacy/export |
| Light mode | Yes | Fully supported |
| Dark mode | Yes | Fully supported, not a partial recolor |
| Offline use | Yes | Core app must work offline |
| Local data persistence | Yes | Room/DataStore or equivalent approved Android stack |
| Safe DB migrations | Yes | Required; no destructive production fallback |
| Backup / export | Yes | Minimum viable user-controlled local export/backup path |
| Notification runtime permission | Yes | Contextual request and repair |
| Exact alarm access | Yes | Contextual request/repair where platform requires |
| Reboot reminder recovery | Yes | Required |
| Voice capture | Yes | Android SpeechRecognizer path; must degrade gracefully |
| Biometric/app lock | Likely | Final V1 inclusion to be decided in privacy specification |
| Attachments | Limited | Images/files only if implementation cost stays reasonable; exact V1 subset TBD |

## In V1 — Personal intelligence

These behaviors are in scope, but must be transparent and reliable:

- prioritize today's work from real due dates, priority, reminders, and user intent
- carry forward explicitly unfinished work
- show useful recent project context
- surface next actions
- detect inactive/neglected active goals using clear rules
- classify common captures locally where possible
- link captures to known projects/life areas when confidence is high
- summarize truthful weekly activity
- show explainable reasons for surfaced items

The V1 app must remain useful if no cloud AI is available.

## Not in V1

| Capability | V1 | Reason |
|---|---:|---|
| Social feed | No | Opposes private personal-core focus |
| Messaging/chat between users | No | Out of scope |
| Community | No | Out of scope |
| Marketplace | No | Out of scope |
| Team collaboration | No | Personal product first |
| Shared projects | No | Future consideration |
| Enterprise workspace | No | Out of scope |
| Full financial management | No | Separate high-complexity domain |
| Medical diagnosis | No | Not the product |
| Detailed health platform | No | Future integrations possible, not V1 |
| Full email client | No | Integration may come later |
| Full calendar replacement | No | V1 can represent commitments; full calendar product is not required |
| Cloud-first sync | No | Local-first V1 |
| Multi-device real-time sync | No | Future phase |
| Web app | No | Android first |
| Desktop app | No | Future phase |
| Public profiles | No | Opposes privacy model |
| Plugin marketplace | No | Premature |
| Autonomous agent making irreversible changes | No | User approval required for meaningful actions |
| AI-generated fake life summaries | Never | Violates truth principle |
| Life score | No | Avoid reductive/judgmental scoring |
| Punitive streak system | No | Violates product principles |

## V1 completion standard

V1 is not complete until the user can realistically use Personal OS for several weeks as their daily personal system without encountering major dead ends.

At minimum, they must be able to:

1. set up their personal space
2. capture thoughts/actions quickly
3. organize real projects/goals/life areas
4. manage today and upcoming work
5. receive reliable reminders
6. record journal entries and activity
7. maintain habits/routines without punishment mechanics
8. search past information
9. review their day/week
10. see truthful personal history and progress
11. edit/delete/archive core records
12. retain data across restarts, reboots, and app upgrades
13. export/backup their personal data through the documented V1 path

## Features that must not appear before they are real

Do not ship a visual section for any of the following unless its backing behavior is implemented:

- achievements
- proficiency levels
- project percentages
- goal percentages
- streaks
- learning-hour summaries
- milestone counts
- weekly insights
- memories/chapters
- “AI suggestions”
- relationship graphs

If a future feature is not ready, omit it or show an explicitly labeled preview—not fake personal data.
