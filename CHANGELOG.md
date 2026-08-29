## [1.3.4+26.2]

### Added
- **Spanish Localization (`es_es`, `es_mx`)**: Added comprehensive Spanish (Spain & Latin America) translations (`es_es.json` & `es_mx.json`) covering all static GameRules, category titles, YACL v3 GUI configuration screens, warnings, and descriptions.
- **Spanish Player Guide**: Added `Doc/Players/index_es_es.md` player guide.

## [1.3.3+26.2]

### Added
- **Russian Localization (`ru_ru`)**: Added comprehensive Russian translations (`ru_ru.json`) covering all static GameRules, category titles, YACL v3 GUI configuration screens, warnings, and descriptions.
- **Russian Player Guide**: Added `Doc/Players/index_ru_ru.md` player guide.

## [1.3.2+26.2]

### Added
- **Traditional Chinese Localization (`zh_tw`, `zh_hk`)**: Added comprehensive Traditional Chinese translations (`zh_tw.json` & `zh_hk.json`) covering all static GameRules, category titles, YACL v3 GUI configuration screens, warnings, and descriptions.
- **Traditional Chinese Player Guide**: Added `Doc/Players/index_zh_tw.md` player guide.

## [1.3.1+26.2]

### Added
- **Simplified Chinese Localization (`zh_cn`)**: Added comprehensive Simplified Chinese translations (`zh_cn.json`) covering all static GameRules, category titles, YACL v3 GUI configuration screens, warnings, and descriptions.
- **Simplified Chinese Player Guide**: Added `Doc/Players/index_zh_cn.md` player guide.

## [1.3.0+26.2] - 2026-08-01

### Added
- **Vein Size Amplification Option**: Introduced `ig:ore_vein_size_multiplier` GameRule (100%–500%) and `veinSizeMultiplier` config option backed by `OreFeatureMixin` to multiply individual vein block volume alongside vein placement frequency. Added `/oreamp set global vein_size <val>` subcommand.

## [1.2.1+26.2] - 2026-08-01

### Fixed
- **2-Way GameRule & Command & Config Synchronization**: `/oreamp set`, `/oreamp reset`, and `/oreamp reload` commands now automatically sync changes to `config/ore-amplifier.json` and active world GameRules concurrently.

## [1.2.0+26.2] - 2026-08-01

### Added
- **Expanded YACL / ModMenu GUI Screen**: Added a dedicated **Per-Ore Multipliers Category** to the optional YACL main menu config screen, providing dynamic slider controls (0%–1000%) for core ores (Iron, Gold, Diamond, Netherite, Copper, Coal, Lapis, Redstone, Emerald, Quartz) with persistent JSON storage.

## [1.1.9+26.2] - 2026-08-01

### Added
- **Automated Test Integration**: Created `OreAmplifierTest` unit test suite supporting `./gradlew test` execution for feature ID matching, blacklist filtering, and multiplier math verification.

## [1.1.8+26.2] - 2026-08-01

### Changed
- **IG Concept Default Alignment**: Updated default global vanilla multiplier to **200% (2.0x)** and modded multiplier to **120% (1.2x)** to align with Instant Gratification concept design specifications ("*One vein should fuel a project*").

## [1.1.7+26.2] - 2026-08-01

### Fixed
- **Early Init Unbound Tag Exception**: Wrapped `Holder.is(TagKey)` queries in a `try-catch` for `IllegalStateException` during mod initialization, eliminating early startup crashes before Minecraft tag binding.

## [1.1.6+26.2] - 2026-08-01

### Fixed
- **Startup Tag Binding Guard**: Added `Holder.isBound()` and early init checks to prevent startup crashes.

## [1.1.5+26.2] - 2026-08-01 (🚨 CRITICAL BUG / DO NOT PUBLISH)

> [!CAUTION]
> **CRITICAL STARTUP CRASH**: This build introduced dynamic tag checks before tags were bound by Minecraft, causing an `IllegalStateException: Tags not bound` crash on startup. **DO NOT PUBLISH OR DISTRIBUTE THIS BUILD.** Use `v1.1.6+26.2` or newer instead.

### Added
- **Tag-Driven Blacklist**: Replaced hardcoded Java blacklist with dynamic Minecraft placed feature tags (`#c:ore_amplifier_blacklist` and `#ore-amplifier:blacklist`).
- **Datapack Customization**: Modpack creators and datapack authors can now blacklist non-ore worldgen features without editing mod source code.

## [1.1.4+26.2] - 2026-08-01

### Added
- **Enhanced `/oreamp` Command Suite**: Implemented comprehensive command interface (`/oreamp help`, `/oreamp status`, `/oreamp get`, `/oreamp set`, `/oreamp reset`, `/oreamp reload`).
- **Brigadier Tab-Completion**: Auto-completes registered ore identifiers in `/oreamp set <ore_id>` and `/oreamp get <ore_id>`.
- **On-The-Fly Overrides**: Allows server operators to query and update both global multipliers and per-ore dynamic GameRules directly in-game.

## [1.1.3+26.2] - 2026-07-22

### ⚠️ Version Guard Notice
- Includes zero-dependency ModVersionGuard pre-release protection. Halts startup with an explicit warning banner if run on incompatible Minecraft drops or missing core dependencies to prevent world save corruption.

### Fixed
- **ModVersionGuard Protection Banner**: Updated ModVersionGuard.java to use Knot ClassLoader resolution (Thread.currentThread().getContextClassLoader()) and display explicit pre-release protection warnings upon an API mismatch.

## [1.1.3+26.2] - 2026-07-22

### ⚠️ Version Guard Notice
- Includes zero-dependency `ModVersionGuard` pre-release protection. Halts startup with an explicit warning banner if run on incompatible Minecraft drops or missing core dependencies to prevent world save corruption.

### Fixed
- **ModVersionGuard Protection Banner**: Updated `ModVersionGuard.java` to use Knot ClassLoader resolution (`Thread.currentThread().getContextClassLoader()`) and display explicit pre-release protection warnings upon an API mismatch.

## [1.1.2+26.2] - 2026-07-22

### Added
- **Forward Compatibility & Version Guard**: Configured `fabric.mod.json` with `"minecraft": ">=26.2-"` for open-ended forward compatibility. Added zero-dependency `ModVersionGuard` check on startup to display human-readable guidance if an incompatible Minecraft API version is encountered.

## [1.1.1+26.1.2] - 2026-07-11

### Added
- Appended `§6Notice:§r` warning notice to config option descriptions inside `en_us.json` to warn players about the config-only-defaults behavior.

## [1.1.0+26.1.2] - 2026-07-11

### Added
- **YACL & ModMenu GUI Config**: Added optional in-game configuration screen via ModMenu and YetAnotherConfigLib (YACL) v3.
- **Global Config Templates**: Created persistent JSON config (`config/ore-amplifier.json`) to define baseline ore multiplier settings.
- **New World Sync**: Automatic synchronization of global config baseline values to GameRules on new world generation.
- **Dedicated Server Compatibility**: Implemented deferred classloading reflection isolation to prevent crashes when YACL/ModMenu are absent.

## [1.0.0+build.73] - 2026-03-03

### Changed
- **Removed Safety Cap**: Deleted `MAX_VEINS` (32) limit per user request. The mod no longer restricts amplified ore counts, granting full control to the user.

### Added
- **Detailed Crash Diagnostics**: Implemented a `ReportedException` diagnostic hook. If a runaway multiplier (over 100,000 veins) would crash the game, a detailed Crash Report is generated identifying the offending Ore ID, Raw Count, and Multiplier.

## [1.0.0+build.72] - 2026-03-01

### Fixed

- **CRITICAL — Crash on World Creation**: Added `BuiltInRegistries.PLACED_FEATURE` pre-registration scan in `onInitialize()`. The world generator uses placed-feature IDs (e.g. `minecraft:ore_dirt`) rather than block IDs; these were never pre-registered by the old block-only scan, causing `IllegalStateException: Registry is already frozen` on the first chunk generation.
- **`/oreamp reset` Silent No-Op**: The dynamic-rule filter in `OreCommand` used prefix `"ig_ore_"` (no namespace) but rule keys are stored as `"ig:ore_..."`. Reset now correctly resets all per-ore multipliers.
- **Client Translation Gap**: `OreAmplifierClient.collectTranslations()` now also scans `BuiltInRegistries.PLACED_FEATURE`, ensuring newly pre-registered placed-feature rules (e.g. `ig:ore_minecraft_ore_dirt`) display human-readable names in the GameRules UI instead of raw identifiers.
- **Scan Filter Misalignment**: Block scan in `onInitialize()` now uses `OreLogic.shouldAmplify()` instead of a wider `contains("ore")` check, preventing ghost rules for non-ore blocks.
- **Duplicate Import**: Removed duplicate `import net.minecraft.core.registries.BuiltInRegistries` in `OreAmplifierFabric.java`.
- **Unused Imports**: Removed 5 unused imports (`Map`, `ConcurrentHashMap`, `FeatureFlagSet`, `GameRuleType`, `GameRuleTypeVisitor`, `IntegerArgumentType`, `Codec`) from `OreAmplifierFabric.java`.

### Added

- **Safety Cap** (`MAX_VEINS = 32`): `RepeatingPlacementMixin` now clamps amplified vein counts to 32 per placement call. A `WARN`-level log fires when the cap is triggered, identifying the feature ID and raw vs. capped count.
- **Blacklist**: `OreLogic.shouldAmplify()` now rejects `minecraft:amethyst_geode`, `minecraft:monster_room`, and `minecraft:monster_room_deep` regardless of name heuristics (concept §3 — Data-Driven Exclusion).


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
