# Personal OS — Repository & Build Policy v0.1

**Status:** Working lock for implementation handoff.

## 1. Source of truth
`rayadinaveen98-ship-it/personal-os` is authoritative.

`main` represents the latest accepted project state.

Specifications in `docs/` are product requirements, not optional commentary.

## 2. Branch strategy
Keep the build process simple.

Recommended:
- `main` — accepted specification + integrated working app
- temporary feature/fix branches created by Astra when useful
- pull requests for substantial risky changes once implementation begins

Do not create a permanent maze of alpha branches.

For the initial end-to-end Astra build, it may work on one named implementation branch such as:
`build/v1-foundation`

After CI is green and acceptance checkpoints are met, merge/fast-forward intentionally.

## 3. Commit behavior
Commits should describe actual changes.

Good examples:
- `Implement onboarding persistence and setup flow`
- `Add Room entities and migration v1`
- `Wire exact reminder scheduling and recovery`

Avoid meaningless commit messages such as:
- `update`
- `fix stuff`
- `final final`

## 4. CI stages once Android code exists
Required on implementation branch/main:
1. repository/spec validation
2. compile
3. unit tests
4. lint/static checks
5. debug APK assembly
6. artifact upload

Add instrumented/emulator tests for critical flows as practical.

A build is not green if only `assembleDebug` succeeds while tests fail.

## 5. APK artifact naming
Working convention:

`PersonalOS-v<version>-<milestone>-debug.apk`

Examples:
- `PersonalOS-v0.1-foundation-alpha-debug.apk`
- `PersonalOS-v0.5-functional-alpha-debug.apk`
- `PersonalOS-v1.0.0-debug.apk`

CI artifact bundle should use the same root name without accidental spaces/random IDs.

## 6. Definition of delivery
An Android milestone is delivered only when:
- source is committed
- CI is green
- APK artifact exists
- exact APK can be downloaded
- archive/APK integrity has been checked
- versionName/versionCode are reported
- known limitations are reported honestly

## 7. Secrets
No secret/API key belongs in Git history.

Core V1 should not require a paid AI/API secret.

If a future integration needs credentials:
- GitHub Actions secret or local secure configuration
- documented env/config name
- never commit literal secret values

## 8. Generated design assets
Store production assets in the Android resources/assets structure once approved.

For large external design-source files that do not belong in Git:
- store stable Canva/design ID/link in the visual manifest
- store approved exports needed by the app when licensing permits
- document source/license/approval version

## 9. Binary policy
Do not commit APK/AAB build outputs to Git history.
Use GitHub Actions artifacts/releases for binaries.

## 10. Documentation changes during implementation
Astra may discover an implementation constraint that requires a spec change.
It must:
1. identify the conflict
2. preserve the user's product intent
3. update the relevant spec/decision log
4. avoid silently redesigning behavior in code

## 11. Release milestones
Working milestones:
- v0.1 Foundation — app shell, design system, persistence architecture
- v0.2 Personal Setup — launch/onboarding/personalization
- v0.3 Core Daily Loop — Today/Plan/Capture
- v0.4 Memory & Growth — Journey/Me/core detail surfaces
- v0.5 Functional Alpha — all V1 domain systems wired
- v0.6 Intelligence & Reviews — deterministic intelligence/reviews/search polish
- v0.7 Reliability — reminders, permissions, backup, app lock, migrations
- v0.8 Premium Polish — character/motion/dark mode/accessibility visual pass
- v0.9 Release Candidate — full acceptance audit
- v1.0 Personal OS

Astra may implement several milestones in one Work run, but the checkpoints are useful for testing and APK delivery.

## 12. Rule
Do not call a milestone complete because screens exist. Completion is behavior + persistence + state + navigation + tests + visual fidelity + APK.
