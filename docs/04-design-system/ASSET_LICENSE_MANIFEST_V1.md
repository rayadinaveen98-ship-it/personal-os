# Personal OS — Asset & License Manifest V1

**Status:** FROZEN policy / implementation manifest  
**Date:** 2026-09-12

Personal OS must be buildable and distributable without ambiguous third-party asset rights. Every production visual/audio/font asset must have known provenance.

## 1. Fonts

### Manrope
Role: primary UI sans.

License: **SIL Open Font License 1.1**.

Production rule:
- obtain from the official Google Fonts / upstream source during implementation
- keep required license text in the project where redistribution requires it
- do not expose raw font files as user-download artifacts

### Newsreader
Role: selective reflective/editorial serif.

License: **SIL Open Font License 1.1**.

Production rule:
- obtain from official upstream/Google Fonts-compatible source
- keep required license text
- use only where design tokens specify

## 2. Personal OS brand mark — Minimal Leaf

Status: **project-owned original design direction**.

Production asset must be recreated as a clean project-owned vector/adaptive icon from the frozen specification:
- two leaves
- one centered stem
- moss/forest foreground
- warm ivory/cream background
- adaptive foreground/background layers
- monochrome/themed variant

Do not copy a third-party leaf logo, stock mark or trademarked icon.

Record final source file path and creator/tool in this manifest when implementation produces the vector.

## 3. Tiny Observatory Friend

Current model/concept sheets are **AI-generated design ideation/reference material produced for this Personal OS project**.

The shipping companion asset must be a production recreation based on the approved identity rather than an unexplained third-party character asset.

Required provenance for shipping asset:
- source tool/workflow
- editable/vector/animation source path
- exported runtime asset paths
- date created
- confirmation that no third-party character artwork was copied

Preferred production implementation:
- original vector/state illustrations created for Personal OS
- Compose vector/drawable states, Rive/Lottie authored for the project, or an equivalent maintainable pipeline

Do not download a lookalike mascot from stock libraries merely to save time.

## 4. Companion decorative scene elements

Plants, room objects, stars, books, laptop, etc. should be:
- project-created vectors/illustrations
- simple Compose/vector geometry
- or licensed assets with explicit recorded rights

The optional cat/animal shown in exploratory concept boards is **not a required second companion** and should not become a production dependency unless separately approved and sourced.

## 5. Icons

Preferred:
- Android/Material icon assets available under their applicable open-source license
- project-owned simple vector icons

If Material icons/symbols are used, retain attribution/license material required by the upstream Apache 2.0 distribution as appropriate.

Rules:
- one coherent icon family
- do not use random icon websites without a recorded license
- do not use trademarked product logos as generic feature icons

## 6. Canva references

Canva designs listed in `VISUAL_REFERENCE_MANIFEST.md` are **design/implementation references**.

They are not permission to ship arbitrary Canva stock elements that may appear in an exploratory board.

Production implementation should recreate the approved layouts using:
- Compose UI
- project-owned brand vectors
- project-owned companion assets
- properly licensed fonts/icons

Any third-party Canva element intended for direct export into the APK must have its license/provenance verified and recorded here first.

## 7. AI-generated concept images

AI-generated concept boards/images are reference material for:
- mood
- character identity
- pose direction
- composition
- brand exploration

They should not automatically be bundled as opaque full-screen production screenshots/assets.

Where a production illustration is needed, create a clean asset intentionally for the target size/state and record its source.

## 8. User-created attachments

User-selected images/files remain user content. Personal OS does not claim ownership.

The app stores references/private copies according to the attachment specification and must not upload them to a third party by default.

## 9. Audio

V1 should not depend on copyrighted music/sound packs.

If subtle haptic/audio feedback is later introduced:
- prefer platform haptics/system sounds
- or project-created/open-licensed sounds with license recorded here

## 10. No ambiguous asset rule

Do not ship an asset if the team cannot answer:
1. where did this come from?
2. are we allowed to redistribute it?
3. is the editable/original source available if we need to revise it?

## 11. Implementation-time completion table

Astra should append final production assets here as they are created:

| Asset | Runtime path | Editable/source path | Provenance/license | Status |
|---|---|---|---|---|
| Minimal Leaf adaptive icon | `app/src/main/res/mipmap-anydpi-v26/ic_launcher.xml` | `app/src/main/res/drawable/leaf.xml` | Personal OS original vector | Implemented; device appearance pending QA |
| Minimal Leaf monochrome/splash | `app/src/main/res/drawable/leaf.xml` | Same editable Android vector | Personal OS original | Implemented; device appearance pending QA |
| Tiny Observatory Friend state set | `core/designsystem/Components.kt` | Kotlin Canvas paths and pose state machine in the same file | Personal OS original recreation from approved direction | Eight source states; fidelity/motion QA pending |
| Manrope | `app/src/main/res/font/manrope.ttf` | Google Fonts `google/fonts`, `ofl/manrope/Manrope[wght].ttf` | SIL OFL 1.1, bundled at `app/src/main/assets/licenses/Manrope-OFL.txt` | Bundled |
| Newsreader | `app/src/main/res/font/newsreader.ttf` | Google Fonts `google/fonts`, `ofl/newsreader/Newsreader[opsz,wght].ttf` | SIL OFL 1.1, bundled at `app/src/main/assets/licenses/Newsreader-OFL.txt` | Bundled |
| UI symbols | Native text/Compose controls | Kotlin UI source | Platform text rendering; no downloaded icon pack | Coherence/accessibility polish pending |

Implementation paths under `core/` are relative to `app/src/main/java/com/navin/personalos/`. No Canva stock artwork or opaque screen mockups are bundled in the app. External attachments remain user content.
