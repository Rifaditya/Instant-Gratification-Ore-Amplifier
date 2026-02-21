# Changelog

## [1.0.0+build.70] - 2026-02-21

### Fixed

- **Stability**: Resolved a critical `IllegalStateException: Registry is already frozen` crash during world generation. `OreLogic.getMultiplier` is now null-safe, falling back to global multipliers if JIT GameRule registration fails during the feature placement phase.

## [1.0.0+build.69] - 2026-02-21

### Fixed

- **Compatibility**: Reverted Mixin compatibility level from `JAVA_25` to `JAVA_22` to resolve warning.

## [1.0.0+build.68] - 2026-02-21

### Added

- **Documentation**: Updated all READMEs and platform pages to highlight the new "Dynamic Translations" feature making GameRules human-readable via DasikLibrary.

## [1.0.0+build.67] - 2026-02-21

### Changed

- **Documentation**: Replaced "Architect" with "Creator" in Platform Page Author roles.

## [1.0.0+build.66] - 2026-02-21

### Changed

- **Documentation**: Added GameRules menu image to Modrinth and CurseForge Description Pages.

## [1.0.0+build.65] - 2026-02-21

### Changed

- **Documentation**: Added features banner image to CurseForge Description Page.

## [1.0.0+build.64] - 2026-02-21

### Changed

- **Documentation**: Added features banner image to Modrinth Description Page.

## [1.0.0+build.63] - 2026-02-20

### Added

- **Assets**: Added mod icon (`icon.png`).

## [1.0.0+build.61] - 2026-02-20

### Changed

- **API Migration**: Delegated all dynamic GameRule generation and caching to DasikLibrary's `DynamicGameRuleManager`.
- **Optimization**: Eliminated redundent pre-world initialization memory overhead.

## [1.0.0] - 2026-02-19

- **Dynamic Ore Generation**: Scans and amplifies detected ores.
- **GameRule Config**: Pure in-game configuration via GameRules.
- **Mod Compatibility**: Auto-detects modded ores using "ore" naming convention.
