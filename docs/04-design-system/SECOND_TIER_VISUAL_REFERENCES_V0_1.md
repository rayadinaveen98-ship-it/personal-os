# Personal OS — Second-Tier Visual References V0.1

**Date:** 2026-09-12  
**Status:** Candidate visual package — review/freeze pending  
**Purpose:** Define production-realistic visual targets for high-value screens outside the five primary tabs.

These screens extend the Warm Personal Observatory system without introducing a new visual language. All content shown must be traceable to real data or clearly treated as example/reference content during design review.

## Source-level references stored in GitHub

The exact HTML layouts used to build/review this tier are stored in:

- `reference-html/tier2/search_v0_1.html`
- `reference-html/tier2/settings_v0_1.html`
- `reference-html/tier2/task_detail_v0_1.html`
- `reference-html/tier2/project_detail_v0_1.html`
- `reference-html/tier2/weekly_review_v0_1.html`
- `reference-html/tier2/life_timeline_v0_1.html`

These HTML files are implementation-friendly structural references. Canva remains the editable visual review surface; the written product/screen specs remain authoritative for behavior.

## 1. Search

**Canva:** `DAHU9QAsesE`  
**Edit:** https://www.canva.com/d/Zq5fxT3Iy1rG0YH  
**View:** https://www.canva.com/d/J2atiZDnJ04oINm

### Intent
Global local-first retrieval across tasks, projects, journal, ideas and Journey content.

### Visual hierarchy
- eyebrow: `FIND ANYTHING`
- Search title
- large search field
- compact type filters
- Recent section
- Results section

### Rules
- no fake search results in product
- type filters remain calm and compact
- result rows show just enough context to disambiguate entity type/date/project
- tapping a result opens the exact detail destination

---

## 2. Settings

**Canva:** `DAHU9bzWaa8`  
**Edit:** https://www.canva.com/d/vRQUJ69JAPZcGEp  
**View:** https://www.canva.com/d/bBwE1Z0qnyhUjBZ

### Intent
A calm, discoverable control center for identity, appearance, companion preferences, daily rhythm, notifications, App Lock, backup/export and privacy.

### Visual hierarchy
- `YOUR SPACE`
- Settings title
- Personal
- Rhythm & reminders
- Privacy & data

### Rules
- settings must not feel like a dense system preferences page
- all privacy/data controls must be easy to discover
- Morning Brief and Evening Reflection are explicit user choices
- Companion visibility is configurable

---

## 3. Task Detail

**Canva:** `DAHU9doCl8I`  
**Edit:** https://www.canva.com/d/U4dwcIxO1unGsi6  
**View:** https://www.canva.com/d/V2fpNd2vSim78aM

### Intent
Give one task a truthful, fully editable home instead of making task-row taps complete the item immediately.

### Visual hierarchy
- back / overflow
- type eyebrow
- task title
- priority/project context chips
- Today's Action / next action treatment
- due/reminder/project/life-area details
- notes
- explicit completion CTA

### Interaction rules
- row navigation and completion are separate concepts
- due date, reminder, project, priority and notes must be editable
- completion must persist immediately and be reversible
- destructive delete remains behind overflow + confirmation/recovery path

---

## 4. Project Detail

**Canva:** `DAHU9bkP3X4`  
**Edit:** https://www.canva.com/d/VIM2vwzhL7IpTX_  
**View:** https://www.canva.com/d/C-f0tA4Vkjl3e_q

### Intent
A project remembers where the user left off and presents the next useful action before generic metrics.

### Visual hierarchy
- project identity/status/life area
- prominent Next Action
- milestones
- open work
- recent project activity

### Rules
- next-action-first, not arbitrary percentage-first
- milestone completion must be real
- `Touched today` must be derived from actual activity
- project can be edited/archived without losing history

---

## 5. Weekly Review

**Canva:** `DAHU9dx70_U`  
**Edit:** https://www.canva.com/d/p2tl7fBJzAIYdOA  
**View:** https://www.canva.com/d/y0aGuzWMfflmgmv

### Intent
A truthful weekly closing ritual based on evidence, not app engagement or punishment.

### Visual hierarchy
- week range
- `Your week, truthfully.`
- grounded summary
- What moved
- What stayed open
- reflection prompt
- Close the week

### Rules
- counts come from stored tasks/sessions/milestones
- carry-forward is a user decision
- no guilt language, streak punishment or engagement metrics
- reflection can be skipped

---

## 6. Life Timeline / Archive

**Canva:** `DAHU9QnjzNI`  
**Edit:** https://www.canva.com/d/9F-mfPiuwMAMrN4  
**View:** https://www.canva.com/d/BJFe2JYGIDr4AQ9

### Intent
A chronological archive of real personal history across projects, journal, memories, ideas and meaningful events.

### Visual hierarchy
- `YOUR HISTORY`
- Life Timeline
- type filters
- month sections
- dated event rows
- privacy/truthfulness reminder

### Rules
- never fabricate history
- every event must have a source record/event
- archive filters should not create duplicate content
- tapping an event opens its source entity when possible

---

## Shared visual rules

All six references follow:
- Warm Ivory `#F5F1E8`
- Soft Cream `#FCF9F3`
- Ink `#252521`
- Moss Sage `#667A61`
- Soft Sage `#DDE4DA`
- Mist Lavender `#E6E1EA`
- 20dp working page padding
- 18–24dp surface radii
- minimum 48dp touch targets
- serif only for selected editorial/reflective headings
- no rainbow dashboards
- no fake interactive controls
- no fabricated personal data

## Companion usage

The Tiny Observatory Friend should remain low-presence on these screens.

Recommended:
- Search: absent
- Settings: absent or very small companion preference preview only
- Task Detail: absent
- Project Detail: absent by default
- Weekly Review: optional small reflective/closing state
- Timeline: optional quiet empty-state presence only

The companion must not compete with dense operational content.
