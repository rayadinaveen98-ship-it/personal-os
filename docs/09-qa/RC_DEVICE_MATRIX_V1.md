# Personal OS — V1 RC Device & State Matrix

**Status:** FROZEN acceptance matrix  
**Date:** 2026-09-12

A single successful emulator/device is not enough for Personal OS. RC acceptance must cover platform age, modern permission behavior, small layouts, font scaling, theme, offline use and recovery behavior.

## 1. Core API matrix

### A — API 26 / Android 8 baseline
Purpose:
- validate minSdk behavior
- small/older device constraints
- legacy notification/alarm/storage compatibility

Target viewport: roughly 360×640dp class where practical.

Must test:
- onboarding
- Today/Plan/Capture/Journey/Me
- Room persistence/restart
- reminder delivery basics
- no layout clipping at normal font scale

### B — API 30 or 31 mid-range
Purpose:
- common older-modern Android behavior
- process recreation/reboot behavior
- storage/picker paths

Must test:
- task/project/journal lifecycle
- attachment picker
- WorkManager reminder reconciliation
- app-lock resume behavior

### C — API 33 / Android 13
Purpose:
- runtime notification permission boundary

Must test:
- first reminder save with notifications not yet granted
- deny / grant later
- truthful scheduling/delivery state
- permission repair route

### D — API 34 / Android 14
Purpose:
- exact-alarm/privacy/background behavior

Must test:
- exact alarm capability checks
- direct repair settings path
- reboot/time-change reconciliation
- notification deep-link

### E — API 35 or 36 modern primary device
Purpose:
- primary production experience
- current system UI/insets/navigation behavior

Reference visual viewport: around 390×844 class, while remaining responsive.

Must test full acceptance suite.

### F — API 37 preview emulator smoke, when available
Purpose:
- compileSdk 37 smoke compatibility only while API 37 remains preview/beta

This is not a V1 production-target acceptance requirement while targetSdk remains 36, but crashes/obvious platform regressions should be recorded.

## 2. Screen-size matrix

At minimum:
- compact phone: ~360dp width
- reference phone: ~390dp width
- larger phone: ~411–430dp width

Requirements:
- no horizontal clipping
- no status/navigation-bar collision
- keyboard does not permanently hide required save/CTA actions
- scrolling is used instead of fixed-height overflow

Tablet/foldable optimization is not required for V1, but the app must not crash or become unusable on a larger window.

## 3. Font-scale matrix

Test:
- 1.0×
- ~1.3×
- ~1.5×

Critical screens:
- Welcome/setup
- Today
- Plan task rows
- Capture parsed fields
- Journey journal text
- Settings
- all detail screens

Two-column card layouts may collapse to one column at larger scales.

## 4. Theme matrix

Test:
- System theme
- Light
- Dark
- switch theme while app is open

Verify:
- intentional Warm Personal Observatory dark palette
- no unreadable hard-coded light colors
- system bars/insets remain coherent
- companion/static assets do not show ugly opaque boxes

## 5. Motion matrix

Test normal motion and reduced-motion behavior.

Reduced motion must:
- remove nonessential companion movement
- avoid exaggerated navigation transitions
- keep state changes understandable
- preserve all functionality

## 6. Connectivity matrix

Core daily loop must be tested:
- fully offline
- airplane mode
- internet restored later

Required offline features:
- create/edit/complete tasks
- projects/goals/habits
- journal/ideas/memories
- search local data
- Today/Plan/Journey/Me
- reminder scheduling where Android permits

No core screen may hang waiting for network.

## 7. Permission matrix

### Notifications
- not asked yet
- denied
- granted
- denied then granted from settings

### Exact alarms / special access
- available
- unavailable
- repair path followed

### Microphone
- not asked yet
- denied
- granted
- SpeechRecognizer unavailable

### Biometrics/device credential
- hardware available
- no enrolled biometric but device credential exists
- user cancels unlock
- app lock disabled

### Attachments
- picker cancelled
- URI persisted successfully
- referenced file becomes unavailable later

Every denial path must degrade gracefully without corrupting data.

## 8. Time/recovery matrix

Test:
- app process killed and reopened
- device reboot
- package update/reinstall-over-existing debug build where safe
- timezone change
- manual clock change where feasible
- daylight-saving transition emulator scenario if tooling permits

Verify reminders remain truthful and are reconciled rather than silently duplicated.

## 9. Data/migration matrix

Before V1 release:
- fresh install DB v1
- seeded realistic data set
- empty DB
- large local history sample
- backup/export + restore validation

For every post-V1 schema version, add explicit previous-version migration fixtures.

No destructive fallback.

## 10. App-lock/privacy matrix

Verify:
- cold open locked
- resume after configured timeout
- notification privacy mode hides sensitive details when configured
- screenshots/recent-app behavior follows frozen privacy specification where technically feasible
- unlock cancellation does not leak content underneath

## 11. Companion matrix

Test:
- companion enabled
- disabled
- reduced motion
- repeated taps
- morning/evening/context state transitions

The companion must always return to idle and must never block a required control.

## 12. RC blocker definition

V1 RC is blocked by any reproducible issue involving:
- data loss/corruption
- reminder silently failing after the app claimed it was scheduled
- unusable navigation/back stack
- dead visible controls
- fabricated user data
- app-lock privacy leak
- crash in a core flow
- unreadable light/dark contrast
- setup/Today clipping on the compact reference device
- migration failure

Cosmetic issues may be triaged only when they do not violate the frozen premium visual target or accessibility baseline.
