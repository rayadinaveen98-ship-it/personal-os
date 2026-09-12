# Personal OS — Privacy, Backup & Security v0.1

**Status:** Working draft

Personal OS contains unusually personal data. Privacy and ownership are product features, not legal footnotes.

## 1. Default philosophy
- local-first
- no account required for core V1
- no cloud upload by default
- no selling/sharing personal content
- no ad-tech SDKs
- no hidden behavioral profiling

## 2. Sensitive content
Potentially sensitive domains include:
- journal
- memories
- goals
- habits
- personal projects
- health-adjacent routines
- notes/ideas
- search history

Production logs must not print content bodies.

## 3. App lock
If included in V1:
- optional
- uses Android BiometricPrompt/device credential
- supports graceful fallback on unsupported devices
- prevents protected content from flashing before authentication
- lock policy documented (on app foreground / timeout)

## 4. Database storage
Use app-private Android storage.
Do not expose raw database files through public external storage.

Encryption-at-rest beyond Android sandbox can be evaluated, but must not be implemented superficially in a way that risks data loss.

## 5. Backup
User-controlled local backup is required for V1.

Backup should contain:
- versioned manifest
- all domain entities
- relationships
- relevant user preferences
- attachments if V1 supports them

Backups must be portable enough to restore after reinstall/update according to documented compatibility rules.

## 6. Export
Export is for user ownership/readability.
Possible V1 package:
- JSON machine-readable data
- optional human-readable Markdown/CSV subsets later

Backup and export may be separate operations:
- Backup = full restore package
- Export = readable/portable copy

## 7. Restore
Restore flow:
1. user selects file
2. app validates manifest/version/checksums
3. preview summary
4. explicit confirmation
5. transactional import/replace/merge policy
6. reconcile reminders after success

Failed validation/import must leave current data untouched.

## 8. Merge vs replace
Simplest safe V1 direction may be `replace current data from backup` with strong warning, unless merge can be implemented deterministically.

Do not promise smart merge without conflict semantics.

## 9. Delete all data
Settings destructive action must:
- explain that local Personal OS data will be removed
- require deliberate confirmation
- cancel alarms/notifications
- clear domain DB and preferences according to documented behavior
- leave no false success if cleanup partly failed

## 10. Individual delete
Deleting entities must follow relationship rules.
Historical/relational domains should prefer archive where appropriate.

## 11. Permissions
Request only in context:
- microphone → voice capture
- notifications → reminder/brief delivery
- exact alarms → exact reminder need
- biometrics → app lock
- storage/document access → user-initiated backup/export/restore through system picker

## 12. Network
Core V1 should not require internet.
If any network feature is later added, network calls must be visible in architecture and privacy documentation.

## 13. AI
No personal content should be sent to external AI services by default.
Future optional AI must:
- be opt-in or clearly invoked
- explain what data leaves device
- avoid irreversible actions without confirmation
- degrade gracefully when unavailable

## 14. Screenshots / recent-app preview
Evaluate whether App Lock mode should obscure app preview in Android Recents for stronger privacy.
This is an open implementation decision.

## 15. Clipboard
Avoid copying sensitive content automatically.
If user explicitly copies journal text, normal Android clipboard behavior applies.

## 16. Notifications privacy
Reminder notification content may expose task titles on lock screen depending system settings.
Future option may allow:
- Full content
- Hide sensitive content

At minimum, respect Android notification privacy controls.

## 17. Versioned backup compatibility
Each backup includes:
- formatVersion
- appVersion
- schemaVersion
- export timestamp

Restore code must reject unsupported future/incompatible formats cleanly.

## 18. Migration safety
Before DB version changes:
- write migration
- test migration
- verify backup/restore path

Never use destructive fallback in production.

## 19. Acceptance criteria
- core app usable offline
- backup created successfully
- restore validated transactionally
- failed restore preserves existing data
- reminders reconcile after restore
- app-lock behavior does not leak content before unlock if included
- logs contain no sensitive bodies
- permissions are contextual
- delete-all cancels scheduled alarms and clears data
