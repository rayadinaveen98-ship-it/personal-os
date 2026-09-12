# Personal OS — Settings v0.1

**Status:** Working draft

Settings is where the user controls identity, appearance, daily rhythm, permissions, privacy, data ownership and app behavior. It should feel calm and understandable, not like an Android system dump.

## 1. Entry
Primary entry: Me → Settings.
Secondary contextual entries may deep-link into a relevant section for permission repair.

## 2. Sections
Working groups:
- Profile
- Appearance
- Daily rhythm
- Notifications & reminders
- Privacy & security
- Data & backup
- Companion & motion
- About

## 3. Profile
Controls:
- preferred name
- optional avatar/initials
- optional self-description/roles if feature exists

Changes should update Today/Me immediately.

## 4. Appearance
Controls:
- Theme: System / Light / Dark
- reduced motion override only if product needs an app-level setting in addition to system preference

Dark mode must be complete, not partial recoloring.

## 5. Daily rhythm
Controls:
- Morning Brief on/off
- Evening Reflection on/off
- preferred brief/reflection time only if scheduled notification behavior is implemented

Never expose a toggle whose actual behavior does not exist.

## 6. Notifications & reminders
Show understandable states:
- Notifications: Allowed / Not allowed
- Exact reminder access: Ready / Needs access where relevant

Actions:
- request/repair notification permission
- open exact-alarm settings when required
- optional test notification later

No dead-end error messages.

## 7. Privacy & security
Potential V1:
- App Lock on/off
- biometric authentication when supported
- lock timeout preference
- privacy explanation

If App Lock ships, protected content should not flash before authentication.

## 8. Data & backup
Required V1 controls:
- Export data
- Create local backup
- Restore backup
- data format/version explanation
- destructive reset/delete-all action with strong confirmation

Export/backup should cover all core entities and preserve relationships.

## 9. Companion & motion
Potential controls:
- Companion visible on/off
- Companion animations on/off if needed
- Reduced motion follows system by default

The app must remain coherent if companion is disabled.

## 10. About
- app name
- version
- build number
- privacy summary/link if applicable
- open-source licenses where needed

## 11. Destructive actions
`Reset Personal OS` / `Delete all local data` must:
- clearly state impact
- require deliberate confirmation
- never be placed beside routine toggles
- cancel reminders/alarms as part of reset

## 12. Premium experience
Settings should use grouped warm surfaces and clear explanatory copy.
Avoid dense divider-heavy enterprise styling.

## 13. Accessibility
- toggles expose state semantically
- system settings deep links have explanatory labels
- destructive actions are clear
- all controls meet touch-target requirements

## 14. Acceptance criteria
- every displayed setting changes real behavior
- settings persist across restart
- theme applies app-wide
- permission status refreshes after returning from system settings
- backup/export works before V1 handoff
- reset is safe and comprehensive
