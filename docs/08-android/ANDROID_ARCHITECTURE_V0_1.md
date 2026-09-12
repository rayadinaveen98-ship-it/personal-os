# Personal OS — Android Architecture v0.1

**Status:** Working draft — architecture direction, exact dependency versions not yet frozen

## 1. Platform
- Android first
- native Kotlin
- Jetpack Compose UI
- minimum/target SDK to be frozen immediately before implementation based on current stable toolchain and device support goals

## 2. Architecture goals
- local-first
- reliable offline behavior
- modular enough for Astra to implement safely
- simple enough to maintain
- testable domain logic
- lifecycle-safe
- data-preserving upgrades
- no paid backend required for V1

## 3. Suggested layers
### UI
Compose screens/components, navigation, UI state.

### ViewModel
Screen/state orchestration using StateFlow and coroutines.

### Domain
Use cases/services for behavior that should not live in composables or DAOs, e.g.:
- Today focus selection
- recurrence calculation
- reminder reconciliation
- weekly review calculations
- capture parsing
- backup/restore validation

### Data
Repositories over:
- Room
- DataStore
- Android platform services

## 4. Dependency injection
Hilt is preferred for:
- repositories
- database
- schedulers
- parsers
- clocks/time providers
- backup services

Test code should be able to replace time/platform dependencies.

## 5. Local database
Room/SQLite.

Requirements:
- explicit migrations
- migration tests
- foreign keys/indices where beneficial
- FTS for Search if appropriate
- observable queries via Flow
- no destructive production fallback

## 6. Preferences
Android DataStore for lightweight preferences.

Do not duplicate first-class domain data such as Life Areas only in DataStore.

## 7. Navigation
Navigation Compose with typed/structured routes where feasible.

Required route classes/categories:
- primary tabs
- entity detail routes by stable ID
- create/edit flows
- Search
- Settings
- Weekly Review
- permission-repair flows

### Deep links
Notification tap must reconstruct a sensible back stack and open the exact entity.

## 8. State management
- ViewModel per meaningful screen/flow
- immutable UI state where practical
- StateFlow / Flow
- one-off events handled carefully to avoid duplicate navigation/toasts after recomposition
- saved-state support for important transient user input where needed

## 9. Time abstraction
Provide injectable time service / Clock wrapper.

Why:
- deterministic tests
- Today/date calculations
- recurrence rules
- weekly-review boundaries
- greeting logic
- reminder scheduling

Use user/device local timezone for calendar semantics while storing unambiguous instants for scheduled events.

## 10. Reminders
### Scheduling
AlarmManager for exact time-sensitive reminders where justified.
WorkManager for deferrable reconciliation/background maintenance.

### Required components
- ReminderScheduler abstraction
- BroadcastReceiver for alarm delivery
- Notification channel manager
- reboot/package-update receiver or WorkManager reconciliation path
- startup reconciliation where needed

### Permission handling
- `POST_NOTIFICATIONS` runtime permission where applicable
- `canScheduleExactAlarms()` check on relevant Android versions
- direct system-settings repair path

## 11. Notifications
Notifications should include stable entity identifiers.

Preferred V1 actions if robust:
- Done
- Snooze
- Open

Notification processing must update Room first/consistently and remain idempotent.

## 12. Voice Capture
Use Android SpeechRecognizer or supported native recognition path.

Requirements:
- runtime microphone permission
- graceful unavailable/error state
- user can edit transcription before save
- no assumption that every OEM offers identical offline behavior

## 13. Capture parser
Rules-based deterministic parser in domain layer.

Testable inputs include:
- relative dates
- weekdays
- AM/PM and 24h time
- priority phrases
- duration
- project/life-area hints
- task/reminder/idea/journal/activity intent

The parser should return structured confidence/ambiguity state rather than directly writing database rows.

## 14. Personal Intelligence engine
Local domain service consuming repositories/read models.

Responsibilities:
- Today focus candidate
- continuation candidates
- Morning Brief facts
- Weekly Review evidence
- neglected-project suggestion rules

Rules should be separately unit tested.

## 15. Search
Prefer Room FTS or efficient local indexed queries.
Search should return typed result models with stable IDs for detail navigation.

## 16. Backup/export
Dedicated service layer.

Requirements:
- versioned format
- serialize all core entities/relationships/preferences
- include attachments if V1 supports them
- validation before restore
- transactional restore
- never corrupt existing DB on failed import

Use Android Storage Access Framework / document picker for user-controlled file destinations where suitable.

## 17. App lock / biometrics
If included:
- Android BiometricPrompt
- do not store raw biometric data
- prevent protected content flash before lock gate
- handle unsupported/unenrolled devices gracefully

Exact lock/session policy needs privacy spec.

## 18. Theme
Compose Material theming may be used as implementation foundation, but final UI must follow the Personal OS design system rather than looking like stock Material defaults.

Modes:
- System
- Light
- Dark

System bars and navigation bars must harmonize with theme and edge-to-edge/insets behavior.

## 19. Companion/character implementation
Architecture should isolate companion rendering/state from business logic.

Possible implementation technologies to evaluate before freeze:
- Compose vector/frame animation
- Lottie
- Rive

Evaluation criteria:
- licensing
- APK size
- performance
- ease of producing 3–5 reactions
- state-machine support
- reduced-motion handling

Business functionality must not depend on companion rendering.

## 20. Accessibility
Compose semantics required for:
- controls
- selected states
- task completion
- progress values
- companion decorative/interactive distinction

Minimum touch targets and contrast rules come from design system.

## 21. Error handling
Use domain-specific recoverable errors where possible.

Examples:
- reminder permission missing
- exact alarm access missing
- speech recognition unavailable
- backup validation failed

Do not show raw exceptions to user.

## 22. Logging
V1 should use minimal local/debug logging.
Do not log sensitive journal/body content in production logs.

If crash reporting is added later, it must respect privacy policy and explicit configuration decisions.

## 23. Build structure — working recommendation
Single Android app module may be acceptable for V1 if packages are feature/domain structured and compile times remain reasonable.

Suggested packages/modules conceptually:
- app/navigation
- core/designsystem
- core/data
- core/domain
- core/reminders
- core/backup
- feature/onboarding
- feature/today
- feature/plan
- feature/capture
- feature/journey
- feature/me
- feature/search
- feature/settings

Astra should not create dozens of Gradle modules without a clear benefit.

## 24. Testing
### Unit
- parser
- recurrence
- focus selection
- weekly metrics
- reminder reconciliation decisions
- backup validation

### Room tests
- DAO queries
- FTS
- migrations
- relationship behavior

### UI tests
Critical flows:
- onboarding
- create/complete task
- reminder permission flow
- project creation
- capture correction
- Journey date navigation
- search result navigation
- backup/export entry

### Manual/device
- notifications on current Android versions
- reboot recovery
- exact-alarm special access
- microphone behavior
- dark mode
- compact phones
- text scaling

## 25. CI
GitHub Actions or equivalent should run:
- unit tests
- lint where stable
- assembleDebug
- optional instrumentation/emulator subset if reliable
- upload installable APK artifact for milestones

A milestone is not complete until APK artifact exists when technically possible.

## 26. Release safety
Before V1:
- release signing path documented
- backup before risky migration considered
- no debug-only secrets
- no hard-coded API keys
- no destructive migration
- ProGuard/R8 behavior validated if release minification enabled

## 27. Open decisions before freeze
1. Application ID/package name.
2. minSdk/targetSdk/compileSdk.
3. exact library versions.
4. single module vs a few feature/core Gradle modules.
5. companion animation technology.
6. App Lock inclusion in V1.
7. exact attachment storage strategy.
8. recurrence rule representation.
9. release signing/distribution process for internal builds.
