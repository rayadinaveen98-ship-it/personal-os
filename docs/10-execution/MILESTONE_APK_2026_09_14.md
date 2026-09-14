# Personal OS milestone APK verification — 2026-09-14

This is an interim debug build, not the final V1 release. Runtime device acceptance and visual review remain incomplete.

- Source: `ecf378445f9babf928fb73dc723f60b59a82a1e2`
- Workflow: https://github.com/rayadinaveen98-ship-it/personal-os/actions/runs/34747266612
- Artifact: https://github.com/rayadinaveen98-ship-it/personal-os/actions/runs/34747266612/artifacts/10314701699
- Package: `com.navin.personalos`
- versionName: `0.1.0-foundation-alpha`
- versionCode: `1`
- APK size: **15,670,826 bytes**
- APK SHA-256: `a187eb3855bef20782607bf75fe2634f899c0cb7424787150ecd4970bd791887`
- Artifact ZIP SHA-256: `282c2f1006e03f23d5f3bb2c616388656d3dc84157caa03daee2f97af278c146`
- Python ZipFile.testzip: passed for archive and APK.
- Android build-tools 36.0.0 apksigner verify --verbose: passed, APK Signature Scheme v2, one signer.
- aapt dump badging: min SDK 26, target SDK 36, compile SDK 37.
- CI unit tests, lint and debug assembly passed. Device jobs failed before tests because the emulator could not locate its AVD; repair is underway.

Subsequent commits fix habit reminder creation, occurrence reminder changes, linked-context search, type filtering and Weekly Review decisions. Those changes are not in this milestone APK and require fresh verification.

The production application seeds no personal history. Synthetic fixtures are confined to tests.

## Newer milestone — source 4f3ae97

- Source: `4f3ae97b51297ddc35b76f8922dda92ab64520e4`
- Workflow: https://github.com/rayadinaveen98-ship-it/personal-os/actions/runs/34801117365
- Artifact: https://github.com/rayadinaveen98-ship-it/personal-os/actions/runs/34801117365/artifacts/10331583659
- Same package/versionName/versionCode as above.
- APK size: **15,719,978 bytes**
- APK SHA-256: `fcc76709449af6fc61dd2300244b93db311d0eb46bccaee7919084856122ddc7`
- Artifact ZIP SHA-256: `9aac8a92fde6f79d06b88ce3c6bb6ec725becab4d484df9497602b9595953645`
- ZIP and APK CRC checks passed; apksigner (build-tools 37.0.0) verified v2 signing with one signer; aapt verified package/version/SDK values.
- Includes the reminder, search and Weekly Review fixes. All 11 persistence tests passed on all five required API targets.
- UI acceptance remains incomplete due to the lazy-list test helper failure. Do not treat this as a release candidate.
