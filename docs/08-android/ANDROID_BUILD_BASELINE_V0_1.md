# Personal OS — Android Build Baseline v0.1

**Date checked:** 2026-09-12  
**Status:** Freeze candidate for Astra handoff. Recheck once immediately before implementation begins.

## 1. Package identity
Working package/application ID:

`com.navin.personalos`

Reason:
- short
- stable
- independent of the old Personal Life OS package
- appropriate for a clean rebuild

Changing the application ID after real installs/backups begin should be avoided.

## 2. Platform baseline
Recommended V1 baseline:
- **minSdk: 26** (Android 8.0)
- **compileSdk: 37**
- **targetSdk: 36** until Android 17/API 37 becomes stable and the behavior-change audit is complete
- **JDK: 17**

Why:
- Android 16/API 36 is the current stable platform as of this check.
- Android 17/API 37 is currently beta, but compiling against 37 allows access/tooling compatibility without opting the production app into all target-37 behavior changes prematurely.
- minSdk 26 materially reduces compatibility complexity while retaining broad Android coverage.

Before final production release, re-evaluate targetSdk against current Google Play requirements and Android 17 stable status.

## 3. Build tooling candidate
As of 2026-09-12:
- Android Gradle Plugin: **9.4.0 stable**
- Gradle: **9.6.0** (AGP 9.4 default/minimum baseline)
- Kotlin: **2.4.20 stable**
- Compose BOM: **2026.08.00 stable**
- Compose UI/Foundation/Runtime stable line: **1.12.1**
- Compose Material3 stable: **1.4.0**

No alpha/beta dependency should be introduced merely for visual novelty.

## 4. Version-management rule
Use a Gradle version catalog (`libs.versions.toml`) so dependencies are centralized and auditable.

Use the stable Compose BOM rather than pinning every Compose artifact independently unless a specific compatibility reason exists.

## 5. Core libraries — intended direction
Exact versions should be rechecked immediately before coding, but the architecture expects stable AndroidX libraries for:
- Core KTX
- Activity Compose
- Lifecycle Runtime / ViewModel Compose
- Navigation Compose
- Room + KSP where compatible
- DataStore Preferences
- WorkManager
- Hilt / AndroidX Hilt integration
- Biometric
- Material3
- Compose Animation
- AndroidX test stack

Prefer KSP over kapt for Room where the chosen Hilt/toolchain combination is clean and stable. Astra must not force a migration to experimental tooling if it creates build instability.

## 6. Build variants
Minimum V1:
- `debug`
- `release`

Debug:
- debuggable
- local diagnostic logging allowed
- no production secrets

Release:
- non-debuggable
- minification/shrinking decision based on stability testing
- no sensitive logs

## 7. Version naming
Initial build line:
- versionCode starts at 1 for the clean rebuild
- versionName pattern: `0.x.y-<milestone>` until V1

Examples:
- `0.1.0-foundation-alpha`
- `0.5.0-functional-alpha`
- `1.0.0`

## 8. Android feature requirements
The build must be compatible with:
- Room local persistence
- DataStore preferences
- AlarmManager exact reminders where permitted
- WorkManager reconciliation/background maintenance
- runtime notification permission
- exact-alarm special access flow where Android requires it
- SpeechRecognizer / voice capture
- BiometricPrompt
- Storage Access Framework/document picker
- edge-to-edge/inset-safe Compose UI

## 9. CI environment
GitHub Actions baseline:
- Ubuntu stable hosted runner
- JDK 17
- Android SDK platform 37 installed
- stable build tools compatible with AGP 9.4
- Gradle cache enabled

Required CI stages:
1. checkout
2. JDK setup
3. Android SDK setup
4. unit tests
5. lint/static checks once configured
6. `assembleDebug`
7. optional connected/instrumented tests when runner/emulator strategy is added
8. upload debug APK artifact

## 10. Dependency policy
- stable releases by default
- no dependency added solely for a trivial helper that can be cleanly implemented in Kotlin/Compose
- every animation/character library must justify APK size, licensing, maintenance and offline operation
- no cloud SDK required for core V1

## 11. Character rendering implication
Preferred implementation order:
1. Compose-native/vector/static assets where enough
2. lightweight animation format/library only if it materially improves companion quality
3. avoid heavy game engines or webviews

Rive/Lottie may be evaluated later, but they are not automatically required by the character specification.

## 12. Release safety rule
Immediately before Astra starts production implementation, recheck:
- Android stable platform status
- targetSdk/Play policy
- AGP stable release
- Kotlin stable release
- Compose BOM stable release

If versions moved only slightly, prefer the stable current line. Do not chase preview releases during a large end-to-end implementation unless required.
