# Personal OS — Android Build Baseline Freeze

**Status:** FROZEN for Astra implementation start  
**Freeze date:** 2026-09-12

This is the stable production baseline Astra should start from. Do not opportunistically adopt alpha/beta/RC libraries unless a real blocker requires it and the change is documented.

## 1. App identity

- App name: **Personal OS**
- applicationId: `com.navin.personalos`
- minSdk: **26**
- compileSdk: **37**
- targetSdk: **36**
- Java/JVM toolchain: **JDK 17**

### Why compile 37 / target 36
Android 17 / API 37 is still a preview/beta platform at freeze time. Compiling against API 37 allows current SDK/tooling compatibility where needed while V1 remains targeted to the current stable production target behavior, API 36. Re-evaluate targetSdk only when Android 17 reaches stable and the migration is deliberate.

## 2. Core build toolchain

- Android Gradle Plugin: **9.4.0**
- Gradle: **9.6.0**
- Kotlin: **2.4.20**
- Kotlin Symbol Processing (KSP): **2.3.12**
- Compose BOM: **2026.08.00**

KSP has its own release numbering; do not assume its version must numerically match Kotlin.

## 3. Frozen AndroidX / DI lines

Start with these stable versions:
- Activity / activity-compose: **1.13.0**
- Lifecycle: **2.11.0**
- Navigation Compose: **2.9.8**
- Room: **2.8.5**
- DataStore: **1.2.1**
- WorkManager: **2.11.2**
- AndroidX Hilt integration: **1.4.0**
- Dagger / Hilt: **2.60.1**
- Biometric: **1.1.0**

Compose UI/material dependencies should be governed through the frozen Compose BOM instead of independently pinning every Compose artifact unless required.

## 4. Stability rule

V1 starts on stable releases only.

Do not choose:
- Navigation 2.10 RC merely because it is newer
- beta/alpha Compose artifacts outside the BOM
- preview AGP/Gradle lines
- Android preview behavior dependencies unless required to compile

If a frozen version has an actual incompatibility at implementation time:
1. reproduce the blocker
2. use the smallest stable compatible version adjustment
3. document it in `docs/decisions/DECISION_LOG.md`
4. keep CI green

## 5. Android architecture

Native stack:
- Kotlin
- Jetpack Compose
- Hilt
- MVVM / state-holder architecture
- StateFlow
- Room / SQLite
- DataStore Preferences
- Navigation Compose
- AlarmManager
- WorkManager
- NotificationManager
- SpeechRecognizer
- BiometricPrompt
- Android Storage Access Framework / picker semantics for attachments

Core V1 daily loop must not depend on a backend account, external AI API, or permanent internet connection.

## 6. Compiler / language rules

- use Kotlin/JVM target compatible with JDK 17
- use the Compose compiler integration appropriate for Kotlin 2.4.20
- enable KSP for Room/Hilt processors where supported
- avoid KAPT unless a required dependency still genuinely needs it

## 7. Namespace / package structure

Working namespace: `com.navin.personalos`.

Recommended high-level source organization:
- `app`
- `core/designsystem`
- `core/database`
- `core/data`
- `core/domain`
- `core/navigation`
- `core/notifications`
- `core/reminders`
- `feature/onboarding`
- `feature/today`
- `feature/plan`
- `feature/capture`
- `feature/journey`
- `feature/me`
- feature/detail packages as justified

Astra may choose a pragmatic single-module-first layout for speed if boundaries remain clear and tests/migrations are not compromised. Do not create needless Gradle modules merely for architecture theatre.

## 8. Build variants

At minimum:
- `debug`
- `release`

Debug build must remain directly installable for milestone APKs.

Release signing secrets must never be committed. If production signing is unavailable during the build, produce verified debug/release-unsigned artifacts as permitted and state the limitation truthfully.

## 9. CI minimum

GitHub Actions must run:
1. spec validation
2. unit tests
3. Android lint/static checks
4. debug assemble
5. upload APK artifact

Later RC workflow may add instrumentation/emulator coverage where reliable.

## 10. Dependency-lock rule

Astra must create a centralized version catalog (`libs.versions.toml`) or an equally clear dependency source of truth.

No duplicate hidden dependency versions across Gradle files.

## 11. Pre-build recheck policy

Because Android tooling can move quickly, Astra may perform one **pre-coding verification** of the frozen versions against official release notes.

If all still resolve, use them exactly.
If one no longer resolves or has a known critical incompatibility, update only the affected stable dependency and record the decision.

The recheck is not permission to refresh the entire project to whatever is newest that day.
