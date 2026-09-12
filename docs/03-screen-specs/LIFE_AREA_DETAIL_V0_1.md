# Personal OS — Life Area Detail v0.1

**Status:** Working screen specification.

## Purpose
Life Area Detail is the user's contextual home for one broad part of life without turning the app into separate silos.

Examples are user-defined: App Building, Game Development, Filmmaking, Reading, Health, Learning.

## Header
- back
- icon/accent if chosen
- Life Area name
- optional description
- overflow/edit action

No progress percentage unless the user explicitly defined a meaningful measurable concept elsewhere.

## Content hierarchy
Show only sections with real data:
1. **Active now** — up to a few relevant Projects/Goals
2. **Next actions** — real open Tasks linked to this Life Area
3. **Routines** — active Habits/Routines
4. **Growth** — related Hobbies/Skills and recent Sessions
5. **Recent context** — real Journal/Idea/Memory/Activity links
6. **History** — route to filtered Journey/Timeline

## Actions
Primary contextual create action opens a sheet/menu:
- New project
- New goal
- New task
- New habit/routine
- Log session
- Write/capture something

Created records inherit the Life Area unless the user changes it.

## Editing
Edit:
- name
- description
- icon
- accent

Archive is available from edit/overflow with confirmation explaining that linked records are preserved.

## Empty state
If a Life Area exists but has no linked records:
> This part of your life is ready when you are.

Actions:
- Start a project
- Add a goal
- Capture something

Companion may appear here in a quiet resting state.

## Navigation
- Project card → Project Detail
- Goal card → Goal Detail
- Task → Task Detail
- Habit → Habit Detail
- Hobby/Skill → Detail
- Journal/Idea/Memory → respective detail
- History → Journey filtered to this Life Area

## Truth rules
- no fabricated activity
- no fake hours
- no generic “you are improving” claim without evidence
- no arbitrary Life Area score

## Dark mode / accessibility
Inherit global design rules; Life Area accent must maintain readable contrast in both themes.

## Small screens
Vertical scroll. No fixed-height grids that clip content. Two-column compact cards may collapse to one column at large text scale.
