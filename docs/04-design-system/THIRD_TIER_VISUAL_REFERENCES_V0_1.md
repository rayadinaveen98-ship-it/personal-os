# Personal OS — Third-Tier Detail Visual References V0.1

**Date:** 2026-09-12  
**Status:** Candidate visual package — review/freeze pending  
**Purpose:** Complete visual coverage for the remaining core life/detail surfaces before final consistency audit and Astra handoff.

These screens inherit the Warm Personal Observatory system. The written screen/behavior specs remain authoritative for behavior; these references define production-realistic visual hierarchy and interaction placement.

## Source-level reference stored in GitHub

Exact structural bundle:
- `reference-html/tier3/detail_screens_v0_1.html`

The bundle contains six 390×844 reference pages: Goal Detail, Habit Detail, Hobby/Skill Detail, Journal Detail, Idea Detail, and Life Area Detail.

---

## 1. Goal Detail

**Canva:** `DAHU9R7yozQ`  
**Edit:** https://www.canva.com/d/v3Y_LKf-b2Ev52T  
**View:** https://www.canva.com/d/FnrJYTmoGsvr8S3

### Intent
A goal is a direction with meaning and evidence—not an arbitrary percentage meter.

### Hierarchy
- goal identity/status/life area
- next meaningful action
- why it matters
- evidence of progress
- milestones
- linked projects/habits/activity lower in hierarchy

### Rules
- never show a percentage unless measurement is explicitly valid
- milestones/evidence preferred for qualitative goals
- user-authored “why it matters” remains primary
- pause/resume/achieve/archive are real state changes

---

## 2. Habit Detail

**Canva:** `DAHU9Q1Fx9M`  
**Edit:** https://www.canva.com/d/5Gq_1CXwwAJNhsO  
**View:** https://www.canva.com/d/NM3mpbDzxheLnZv

### Intent
A calm routine history that records reality without guilt, debt, or punishment.

### Hierarchy
- habit identity and schedule
- today state + explicit Log action
- weekly evidence
- recent completion history
- linked goal/life area
- reminder/schedule editing

### Rules
- missed days do not become overdue debt
- no shame language or broken-streak dramatics
- completion history is factual
- pause/resume/archive preserve history

---

## 3. Hobby / Skill Detail

**Canva:** `DAHU9Y1NxEQ`  
**Edit:** https://www.canva.com/d/4JJBkOK4_BNSrOe  
**View:** https://www.canva.com/d/6b8F30YUbEPsHCa

### Intent
Make growth visible through real sessions, time, notes and current focus without inventing proficiency levels.

### Hierarchy
- skill/hobby identity
- current focus
- real session/time evidence
- recent sessions
- notes/resources
- linked projects/goals
- Log Session CTA

### Rules
- no fake “Level 7” or unexplained mastery score
- session totals recompute after edits/deletes
- current focus is user-editable
- time metrics only appear when session data exists

---

## 4. Journal Detail

**Canva:** `DAHU9adUM0I`  
**Edit:** https://www.canva.com/d/F_XjA1k7mztF53K  
**View:** https://www.canva.com/d/iTXUTDgB__TDnDY

### Intent
A quiet, readable home for one real journal/reflection entry.

### Hierarchy
- date/time/context
- optional title
- full journal body
- tags/linked context
- linked activity
- Edit / Link context actions
- Save as Memory where the memory system applies

### Rules
- full text remains readable and editable
- memory promotion never fabricates new autobiographical content
- deletion requires clear intent
- linked context navigates to real source records

---

## 5. Idea Detail

**Canva:** `DAHU9RwwgSY`  
**Edit:** https://www.canva.com/d/TaG395VbX1U7wbz  
**View:** https://www.canva.com/d/CHv1iFxGczAewAV

### Intent
Preserve the original thought while making it easy to turn into action.

### Hierarchy
- idea content/date
- connected context
- convert/link actions
- traceability explanation
- edit/archive/delete paths

### Rules
- converting an idea does not silently destroy the original
- Create Task / Add to Project creates traceable relationships
- no fake status/progress unless user explicitly sets one

---

## 6. Life Area Detail

**Canva:** `DAHU9ehV5MA`  
**Edit:** https://www.canva.com/d/3s0Nc8EnhWKE4bR  
**View:** https://www.canva.com/d/iiq1xLq_NFWW3gV

### Intent
A contextual home for one broad part of life without creating a separate mini-app or arbitrary scorecard.

### Hierarchy
- life area identity/description
- Active Now projects/goals
- Next Actions
- Routines where real
- Growth (hobbies/skills/sessions)
- Recent Context (journal/ideas/memories/activity)
- History route
- contextual Create action

### Rules
- only render sections backed by real data
- no Life Area percentage/score
- contextual creation inherits Life Area by default but remains editable
- archive preserves linked records

---

## Shared interaction rules

All six screens must provide:
- real back navigation
- working edit path
- safe archive/delete semantics
- deep links to linked entities
- persistence before success confirmation
- truthful timestamps/status
- no inert chevrons, chips, overflow items or CTAs

## Companion presence

These are dense detail surfaces. Default companion presence is **none**.

Allowed exceptions:
- calm empty state
- major goal-achieved completion moment
- habit/skill empty state where the illustration does not compete with data

The Tiny Observatory Friend must remain secondary to utility.

## Visual rules

- Warm Ivory / Soft Cream base
- Moss Sage primary action/accent
- Gold reserved for meaningful highlight / idea warmth
- Lavender reserved for reflection/journal moments
- 20dp page padding
- 18–24dp surfaces
- 48dp+ touch targets
- serif only for selected reflective/editorial content
- dark-mode equivalents inherit semantic tokens rather than hard-coded inversion
