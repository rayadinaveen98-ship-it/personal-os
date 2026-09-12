# Personal OS — Me Screen v0.1

**Status:** Working draft

Me is the user's personal identity, direction, growth, and settings gateway. It must summarize real life context without inventing flattering statistics.

## 1. Purpose
Me answers:
1. Who am I in this system?
2. What am I currently building or becoming?
3. Which areas of life are active?
4. What progress is actually supported by data?
5. Where do I change Personal OS preferences and privacy?

## 2. Identity header
Use real user-provided data only.

Possible contents:
- preferred name
- initials/avatar
- optional self-description / roles
- current chapter or focus statement if user explicitly creates one

Do not hard-code names, roles or profile achievements.

Header tap/edit action should be clear.

## 3. Current chapter
A reflective personal statement may be shown if:
- the user wrote one
- or a future suggestion is explicitly accepted by the user

Do not auto-present inferred life philosophy as fact.

## 4. Life areas
Show user-created/selected life areas such as:
- work
- learning
- creativity
- health
- family
- custom areas

Tap → Life Area detail/filter if that entity ships in V1.

## 5. Active projects
Show truthful project count and recent project context.

Possible data:
- active project count
- projects touched this week based on actual activity timestamps
- nearest project next action

Tap → Projects / project detail.

## 6. Goals
If Goal engine exists in V1:
- show real active goals
- progress only when measurable
- milestone state preferred over arbitrary percentage

If Goal engine is not implemented, do not display decorative goal cards.

## 7. Skills / hobbies / growth
Only show learning/growth metrics when supported by actual sessions or logs.

Examples:
- `Blender · 4 sessions this month`
- `Reading · 3h logged`

Do not seed fake hours or skill levels.

Tap → Skill/Hobby detail.

## 8. Achievements
Achievements must have a real source.

Possible sources:
- explicitly recorded personal achievement
- completed project milestone
- completed goal

Avoid gamified badges for ordinary app usage unless product philosophy later explicitly supports them.

## 9. Personal patterns
Future intelligence layer may show grounded summaries such as:
- most active project this week
- number of reflection days
- average sessions per week

Every pattern must be mathematically traceable to stored data.

No motivational pseudo-insights.

## 10. Settings gateway
A visible Settings action opens Settings.

Settings should eventually include:
- preferred name / profile
- theme: System / Light / Dark
- Morning Brief
- Evening Reflection
- notifications
- app lock / biometrics
- data backup/export
- privacy
- reduce motion where relevant
- companion visibility / animation preference if needed
- about/version

## 11. Companion behavior
Me can host a small companion presence because it is a reflective low-density screen.

Possible states:
- calm idle beside identity area
- subtle wave when entering occasionally, not every time
- tap reactions from the character pool

The companion must never cover metrics/actions.

## 12. Empty / early-use state
A new user may have almost no history.

Instead of fake metrics:
> **This space will grow with you.**
> As you capture projects, reflections and sessions, Personal OS will build a truthful picture of your progress.

Actions can point to:
- Create a project
- Add a goal
- Capture something

## 13. Premium experience
Me can feel like a personal profile/journal cover rather than a settings dashboard.

Use:
- strong identity hero
- carefully grouped sections
- restrained metrics
- editorial breathing room

Avoid:
- dense KPI dashboard
- excessive progress rings
- achievement-wall aesthetics

## 14. Privacy
Me/Settings is the primary discoverable place for:
- app lock
- export
- backup
- deletion controls
- privacy explanation

These controls must not be hidden behind obscure menus.

## 15. Accessibility
- metrics have text equivalents
- progress bars expose values semantically
- avatar has meaningful content description if interactive
- all settings have clear labels and state

## 16. Open decisions
1. Whether roles/self-description are user-authored or selected from suggestions.
2. Whether `Current chapter` is a first-class entity.
3. Whether achievement system ships in V1.
4. Exact home for Life Areas management.
5. Whether companion visibility gets a user toggle in first release.
