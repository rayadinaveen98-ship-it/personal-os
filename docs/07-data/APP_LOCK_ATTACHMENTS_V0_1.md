# Personal OS — App Lock & Attachments v0.1

**Status:** Working V1 decision draft.

## 1. App Lock — V1 decision
Personal OS V1 should include an **optional app lock** because the product stores journal entries, memories, goals, routines and other personal information.

Default: **Off**.

When enabled:
- authenticate on cold app open / return after configurable timeout
- use Android BiometricPrompt where available
- allow device credential fallback when supported and user-approved
- never invent a custom insecure PIN store when platform credential can be used

## 2. Lock timeout
Working options:
- Immediately
- After 1 minute
- After 5 minutes
- After 15 minutes
- When app is fully closed / device policy equivalent if technically clean

Default after enabling: **After 1 minute**.

## 3. Privacy during lock
While locked:
- no personal content visible behind the lock surface
- app switcher preview should be obscured when privacy mode/app lock is enabled if practical through Android secure-window behavior
- notifications should respect user-selected privacy mode (full text vs hidden details)

## 4. Recovery
Personal OS should rely on platform biometric/device credential recovery. No cloud recovery is required for local-only V1.

If a device has no supported secure credential, explain that app lock cannot be enabled safely.

## 5. Attachments — V1 scope
Attachments are **limited but real** in V1.

Supported:
- images from device picker
- general files through Android document picker where URI access can be persisted safely

Not required in V1:
- video editing/playback suite
- audio recorder/file management system
- cloud uploads
- OCR
- automatic media analysis

## 6. Where attachments are allowed
V1 attachments may be linked to:
- Journal entries
- Memories
- Ideas
- Projects / project notes
- optionally Tasks if implementation remains clean

They should not be added to every entity merely because the schema can support it.

## 7. Storage rule
Prefer Android Storage Access Framework/document-picker URIs and persisted URI permissions where reliable.

If the app copies content into private app storage, that behavior must be explicit and included in backup/export rules.

## 8. Deletion
Deleting a Personal OS attachment relationship must not delete the user's original external source file.

If the app created a private copy, deleting the private copy is allowed only after explicit record/attachment deletion.

## 9. Backup/export
Export should include:
- attachment metadata
- private app-owned attachment copies when feasible
- clear manifest references for external URIs that cannot be embedded

Restore must never claim an inaccessible external file was restored successfully.

## 10. Security rules
- never execute attachments
- validate MIME/category for preview paths
- no arbitrary filesystem path assumptions
- no hidden background upload
- no attachment leaves the device without explicit future sync/share action

## 11. UX
Attachment UI should be simple:
- Add attachment
- image thumbnail or file row
- open
- remove relationship

Avoid a file-manager-within-the-app experience.

## 12. Acceptance
- App Lock can be enabled/disabled safely.
- Lock hides personal content.
- Device credential/biometric fallback works appropriately.
- Image/file attachment can be added and reopened after app restart.
- Removing an attachment does not delete an unrelated original device file.
- Export accurately describes included and unavailable attachments.
