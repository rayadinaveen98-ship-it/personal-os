# Personal OS — Frozen Specification Index

**State:** PRE-BUILD PACKAGE COMPLETE  
**Date:** 2026-09-12  
**Repository:** `rayadinaveen98-ship-it/personal-os`

This is the navigation map and precedence guide for the Personal OS V1 implementation package.

## Read this first

Astra/Work must read the repository broadly before coding, but the following frozen files resolve ambiguity from older working drafts.

### V1 frozen decision layer
1. [`decisions/DECISION_LOG.md`](decisions/DECISION_LOG.md)
2. [`01-information-architecture/ROUTE_HIERARCHY_FROZEN_V1.md`](01-information-architecture/ROUTE_HIERARCHY_FROZEN_V1.md)
3. [`03-screen-specs/SCREEN_SPEC_RESOLUTIONS_V1_FROZEN.md`](03-screen-specs/SCREEN_SPEC_RESOLUTIONS_V1_FROZEN.md)
4. [`04-design-system/VISUAL_REFERENCE_MANIFEST.md`](04-design-system/VISUAL_REFERENCE_MANIFEST.md)
5. [`04-design-system/DESIGN_TOKENS_V1_FROZEN.md`](04-design-system/DESIGN_TOKENS_V1_FROZEN.md)
6. [`04-design-system/ASSET_LICENSE_MANIFEST_V1.md`](04-design-system/ASSET_LICENSE_MANIFEST_V1.md)
7. [`07-data/DATA_SCHEMA_V1_FROZEN.md`](07-data/DATA_SCHEMA_V1_FROZEN.md)
8. [`08-android/ANDROID_BUILD_BASELINE_FROZEN_2026_09_12.md`](08-android/ANDROID_BUILD_BASELINE_FROZEN_2026_09_12.md)
9. [`09-qa/RC_DEVICE_MATRIX_V1.md`](09-qa/RC_DEVICE_MATRIX_V1.md)
10. [`10-execution/FINAL_CONSISTENCY_AUDIT_V1.md`](10-execution/FINAL_CONSISTENCY_AUDIT_V1.md)

## Foundation
- [`00-foundation/PRODUCT_FOUNDATION_V1.md`](00-foundation/PRODUCT_FOUNDATION_V1.md)
- [`00-foundation/PRODUCT_PRINCIPLES.md`](00-foundation/PRODUCT_PRINCIPLES.md)
- [`00-foundation/V1_SCOPE_MATRIX.md`](00-foundation/V1_SCOPE_MATRIX.md)
- [`00-foundation/PREPARATION_ROADMAP.md`](00-foundation/PREPARATION_ROADMAP.md)
- [`00-foundation/FOUNDER_DIRECTION_PREMIUM_EXPERIENCE_2026_09_12.md`](00-foundation/FOUNDER_DIRECTION_PREMIUM_EXPERIENCE_2026_09_12.md)

## Information architecture
- [`01-information-architecture/INFORMATION_ARCHITECTURE_V0_1.md`](01-information-architecture/INFORMATION_ARCHITECTURE_V0_1.md) — design history / conceptual explanation
- [`01-information-architecture/ROUTE_HIERARCHY_FROZEN_V1.md`](01-information-architecture/ROUTE_HIERARCHY_FROZEN_V1.md) — **V1 authority**

## UX flows
- [`02-ux-flows/LAUNCH_WELCOME_SETUP_V0_1.md`](02-ux-flows/LAUNCH_WELCOME_SETUP_V0_1.md)
- [`02-ux-flows/SCREEN_FLOW_MATRIX_V0_1.md`](02-ux-flows/SCREEN_FLOW_MATRIX_V0_1.md)

## Screen specifications
- [`03-screen-specs/TODAY_HOME_V0_1.md`](03-screen-specs/TODAY_HOME_V0_1.md)
- [`03-screen-specs/PLAN_V0_1.md`](03-screen-specs/PLAN_V0_1.md)
- [`03-screen-specs/CAPTURE_V0_1.md`](03-screen-specs/CAPTURE_V0_1.md)
- [`03-screen-specs/JOURNEY_V0_1.md`](03-screen-specs/JOURNEY_V0_1.md)
- [`03-screen-specs/ME_V0_1.md`](03-screen-specs/ME_V0_1.md)
- [`03-screen-specs/SEARCH_V0_1.md`](03-screen-specs/SEARCH_V0_1.md)
- [`03-screen-specs/SETTINGS_V0_1.md`](03-screen-specs/SETTINGS_V0_1.md)
- [`03-screen-specs/WEEKLY_REVIEW_V0_1.md`](03-screen-specs/WEEKLY_REVIEW_V0_1.md)
- [`03-screen-specs/CORE_DETAIL_SCREENS_V0_1.md`](03-screen-specs/CORE_DETAIL_SCREENS_V0_1.md)
- [`03-screen-specs/LIFE_AREA_DETAIL_V0_1.md`](03-screen-specs/LIFE_AREA_DETAIL_V0_1.md)
- [`03-screen-specs/TIMELINE_ARCHIVE_V0_1.md`](03-screen-specs/TIMELINE_ARCHIVE_V0_1.md)
- [`03-screen-specs/SCREEN_SPEC_RESOLUTIONS_V1_FROZEN.md`](03-screen-specs/SCREEN_SPEC_RESOLUTIONS_V1_FROZEN.md) — **resolves historical open questions**

## Design system & visual references
- [`04-design-system/DESIGN_LANGUAGE_V0_1.md`](04-design-system/DESIGN_LANGUAGE_V0_1.md)
- [`04-design-system/COLOR_TYPOGRAPHY_V0_1.md`](04-design-system/COLOR_TYPOGRAPHY_V0_1.md) — historical working palette/type reasoning
- [`04-design-system/COMPONENT_SYSTEM_V0_1.md`](04-design-system/COMPONENT_SYSTEM_V0_1.md)
- [`04-design-system/APP_ICON_BRAND_V0_1.md`](04-design-system/APP_ICON_BRAND_V0_1.md)
- [`04-design-system/MOTION_MICROINTERACTIONS_V0_1.md`](04-design-system/MOTION_MICROINTERACTIONS_V0_1.md)
- [`04-design-system/CHARACTER_SYSTEM_V0_1.md`](04-design-system/CHARACTER_SYSTEM_V0_1.md)
- [`04-design-system/VISUAL_REFERENCE_PACKAGE_V0_1.md`](04-design-system/VISUAL_REFERENCE_PACKAGE_V0_1.md)
- [`04-design-system/SECOND_TIER_VISUAL_REFERENCES_V0_1.md`](04-design-system/SECOND_TIER_VISUAL_REFERENCES_V0_1.md)
- [`04-design-system/THIRD_TIER_VISUAL_REFERENCES_V0_1.md`](04-design-system/THIRD_TIER_VISUAL_REFERENCES_V0_1.md)
- [`04-design-system/VISUAL_REFERENCE_MANIFEST.md`](04-design-system/VISUAL_REFERENCE_MANIFEST.md) — **frozen screen targets / Canva IDs**
- [`04-design-system/DESIGN_TOKENS_V1_FROZEN.md`](04-design-system/DESIGN_TOKENS_V1_FROZEN.md) — **frozen implementation tokens**
- [`04-design-system/ASSET_LICENSE_MANIFEST_V1.md`](04-design-system/ASSET_LICENSE_MANIFEST_V1.md) — **asset provenance policy**
- `04-design-system/reference-html/` — source-level layout references

## Feature behavior
- [`05-behavior/TASKS_REMINDERS_V0_1.md`](05-behavior/TASKS_REMINDERS_V0_1.md)
- [`05-behavior/PROJECTS_GOALS_V0_1.md`](05-behavior/PROJECTS_GOALS_V0_1.md)
- [`05-behavior/HABITS_ROUTINES_V0_1.md`](05-behavior/HABITS_ROUTINES_V0_1.md)
- [`05-behavior/HOBBIES_SKILLS_SESSIONS_V0_1.md`](05-behavior/HOBBIES_SKILLS_SESSIONS_V0_1.md)
- [`05-behavior/JOURNAL_IDEAS_MEMORIES_V0_1.md`](05-behavior/JOURNAL_IDEAS_MEMORIES_V0_1.md)
- [`05-behavior/RECURRENCE_SCHEDULING_V0_1.md`](05-behavior/RECURRENCE_SCHEDULING_V0_1.md)
- [`05-behavior/LIFE_AREAS_MEMORIES_CHAPTERS_V0_1.md`](05-behavior/LIFE_AREAS_MEMORIES_CHAPTERS_V0_1.md)

## Personal Intelligence
- [`06-intelligence/PERSONAL_INTELLIGENCE_V0_1.md`](06-intelligence/PERSONAL_INTELLIGENCE_V0_1.md)
- [`06-intelligence/CARRY_FORWARD_RULES_V0_1.md`](06-intelligence/CARRY_FORWARD_RULES_V0_1.md)

## Data / privacy / security
- [`07-data/DATA_MODEL_V0_1.md`](07-data/DATA_MODEL_V0_1.md) — design history
- [`07-data/DATA_SCHEMA_FREEZE_CANDIDATE_V0_2.md`](07-data/DATA_SCHEMA_FREEZE_CANDIDATE_V0_2.md) — freeze candidate/history
- [`07-data/DATA_SCHEMA_V1_FROZEN.md`](07-data/DATA_SCHEMA_V1_FROZEN.md) — **V1 authority**
- [`07-data/PRIVACY_BACKUP_SECURITY_V0_1.md`](07-data/PRIVACY_BACKUP_SECURITY_V0_1.md)
- [`07-data/APP_LOCK_ATTACHMENTS_V0_1.md`](07-data/APP_LOCK_ATTACHMENTS_V0_1.md)

## Android architecture / build
- [`08-android/ANDROID_ARCHITECTURE_V0_1.md`](08-android/ANDROID_ARCHITECTURE_V0_1.md)
- [`08-android/ANDROID_BUILD_BASELINE_V0_1.md`](08-android/ANDROID_BUILD_BASELINE_V0_1.md) — research/history
- [`08-android/ANDROID_BUILD_BASELINE_FROZEN_2026_09_12.md`](08-android/ANDROID_BUILD_BASELINE_FROZEN_2026_09_12.md) — **V1 authority**
- [`08-android/REPOSITORY_BUILD_POLICY_V0_1.md`](08-android/REPOSITORY_BUILD_POLICY_V0_1.md)

## QA / acceptance
- [`09-qa/V1_ACCEPTANCE_TEST_PLAN_V0_1.md`](09-qa/V1_ACCEPTANCE_TEST_PLAN_V0_1.md)
- [`09-qa/RC_DEVICE_MATRIX_V1.md`](09-qa/RC_DEVICE_MATRIX_V1.md)

## Execution / handoff
- [`10-execution/PRE_BUILD_SECRETS_AUDIT_2026_09_12.md`](10-execution/PRE_BUILD_SECRETS_AUDIT_2026_09_12.md)
- [`10-execution/FINAL_CONSISTENCY_AUDIT_V1.md`](10-execution/FINAL_CONSISTENCY_AUDIT_V1.md)
- [`10-execution/BUILD_HANDOFF_READINESS.md`](10-execution/BUILD_HANDOFF_READINESS.md)
- [`10-execution/ASTRA_EXECUTION_BRIEF_DRAFT_V0_1.md`](10-execution/ASTRA_EXECUTION_BRIEF_DRAFT_V0_1.md) — historical draft; do not execute once final brief exists
- `10-execution/ASTRA_EXECUTION_BRIEF_V1.md` — final instruction after authorization

## Repository safeguards
- `/.gitignore` — ignores signing/secrets/local build files and generated APK/AAB/ZIP files
- `/.github/workflows/spec-validation.yml` — validates specification package safeguards

## Implementation precedence

When two docs appear to disagree:
1. `decisions/DECISION_LOG.md`
2. explicitly frozen V1 documents
3. frozen `VISUAL_REFERENCE_MANIFEST.md` + `DESIGN_TOKENS_V1_FROZEN.md`
4. `SCREEN_SPEC_RESOLUTIONS_V1_FROZEN.md`
5. behavior/intelligence/privacy specs
6. working v0.1 docs
7. static/reference composition

Accessibility, privacy, data truthfulness and actual interactive behavior must never be sacrificed merely to mimic a static screenshot.

## Rule for Astra/Work
Read the specification package before coding. Read the final Astra execution brief **last**. Once authorized, continue through implementation, CI repair, QA and verified APK delivery instead of stopping at planning/mockups.
