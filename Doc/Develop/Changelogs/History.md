# History

## [1.3.9+26.2]

### Indonesian & Malay Localization & Player Guide
* **What**: Added full Indonesian and Malay language dictionaries (`id_id.json`, `ms_my.json`) and player guide (`Doc/Players/index_id_id.md`).
* **Why**: Provides native in-game localization for Indonesian and Malaysian players across GameRules, category names, YACL v3 GUI configuration screens, warnings, and descriptions.
* **How**: Created `src/main/resources/assets/ore-amplifier/lang/id_id.json` and `ms_my.json` with 24 translated keys and added `Doc/Players/index_id_id.md`.

## [1.3.8+26.2]

### Japanese Localization & Player Guide
* **What**: Added full Japanese language dictionary (`ja_jp.json`) and player guide (`Doc/Players/index_ja_jp.md`).
* **Why**: Provides native in-game localization for Japanese-speaking players across GameRules, category names, YACL v3 GUI configuration screens, warnings, and descriptions.
* **How**: Created `src/main/resources/assets/ore-amplifier/lang/ja_jp.json` with 24 translated keys and added `Doc/Players/index_ja_jp.md`.

## [1.3.7+26.2]

### Portuguese Localization & Player Guide
* **What**: Added full Portuguese language dictionaries (`pt_br.json`, `pt_pt.json`) and player guide (`Doc/Players/index_pt_br.md`).
* **Why**: Provides native in-game localization for Portuguese-speaking players (Brazil & Portugal) across GameRules, category names, YACL v3 GUI configuration screens, warnings, and descriptions.
* **How**: Created `src/main/resources/assets/ore-amplifier/lang/pt_br.json` and `pt_pt.json` with 24 translated keys and added `Doc/Players/index_pt_br.md`.

## [1.3.6+26.2]

### French Localization & Player Guide
* **What**: Added full French language dictionaries (`fr_fr.json`, `fr_ca.json`) and player guide (`Doc/Players/index_fr_fr.md`).
* **Why**: Provides native in-game localization for French-speaking players (France & Canada) across GameRules, category names, YACL v3 GUI configuration screens, warnings, and descriptions.
* **How**: Created `src/main/resources/assets/ore-amplifier/lang/fr_fr.json` and `fr_ca.json` with 24 translated keys and added `Doc/Players/index_fr_fr.md`.

## [1.3.5+26.2]

### German Localization & Player Guide
* **What**: Added full German language dictionaries (`de_de.json`, `de_at.json`) and player guide (`Doc/Players/index_de_de.md`).
* **Why**: Provides native in-game localization for German-speaking players (Germany & Austria) across GameRules, category names, YACL v3 GUI configuration screens, warnings, and descriptions.
* **How**: Created `src/main/resources/assets/ore-amplifier/lang/de_de.json` and `de_at.json` with 24 translated keys and added `Doc/Players/index_de_de.md`.

## [1.3.4+26.2]

### Spanish Localization & Player Guide
* **What**: Added full Spanish language dictionaries (`es_es.json`, `es_mx.json`) and player guide (`Doc/Players/index_es_es.md`).
* **Why**: Provides native in-game localization for Spanish-speaking players (Spain & Latin America) across GameRules, category names, YACL v3 GUI configuration screens, warnings, and descriptions.
* **How**: Created `src/main/resources/assets/ore-amplifier/lang/es_es.json` and `es_mx.json` with 24 translated keys and added `Doc/Players/index_es_es.md`.

## [1.3.3+26.2]

### Russian Localization & Player Guide
* **What**: Added full Russian language dictionary (`ru_ru.json`) and player guide (`Doc/Players/index_ru_ru.md`).
* **Why**: Provides native in-game localization for Russian-speaking players across GameRules, category names, YACL v3 GUI configuration screens, warnings, and descriptions.
* **How**: Created `src/main/resources/assets/ore-amplifier/lang/ru_ru.json` with 24 translated keys and added `Doc/Players/index_ru_ru.md`.

## [1.3.2+26.2]

### Traditional Chinese Localization & Player Guide
* **What**: Added full Traditional Chinese language dictionaries (`zh_tw.json`, `zh_hk.json`) and player guide (`Doc/Players/index_zh_tw.md`).
* **Why**: Provides native in-game localization for Traditional Chinese players (Taiwan, Hong Kong, Macau) across GameRules, category names, YACL v3 GUI configuration screens, warnings, and descriptions.
* **How**: Created `src/main/resources/assets/ore-amplifier/lang/zh_tw.json` and `zh_hk.json` with 24 translated keys and added `Doc/Players/index_zh_tw.md`.

## [1.3.1+26.2]

### Simplified Chinese Localization & Player Guide
* **What**: Added full Simplified Chinese language dictionary (`zh_cn.json`) and player guide (`Doc/Players/index_zh_cn.md`).
* **Why**: Provides native in-game localization for Simplified Chinese players across GameRules, category names, YACL v3 GUI configuration screens, warnings, and descriptions.
* **How**: Created `src/main/resources/assets/ore-amplifier/lang/zh_cn.json` with 26 translated keys and added `Doc/Players/index_zh_cn.md`.

## [1.3.0+26.2] - 2026-08-01

### Vein Size Amplification Option
* **What**: Added `ig:ore_vein_size_multiplier` GameRule (100%–500%) and `OreFeatureMixin` to multiply individual vein block volume during feature placement.
* **Why**: Allows scaling individual vein sizes (e.g. 200% double-sized veins) alongside vein frequency count.
* **How**: Created `OreFeatureMixin.java`, updated `OreAmplifierFabric.java`, `OreCommand.java`, `YaclScreenHelper.java`, and `en_us.json`.

## [1.2.1+26.2] - 2026-08-01

### 2-Way GameRule & Command & Config Synchronization
* **What**: Updated `/oreamp set`, `/oreamp reset`, and `/oreamp reload` command handlers in `OreCommand.java` to synchronize changes to `config/ore-amplifier.json` and active world GameRules concurrently.
* **Why**: Prevents desync between in-game Brigadier command modifications, world GameRules, and main menu YACL config settings.
* **How**: Added `OreAmplifierConfig.save()` calls on `/oreamp set` and `/oreamp reset`, and GameRule updates on `/oreamp reload`.

## [1.2.0+26.2] - 2026-08-01

### Expanded YACL / ModMenu Config GUI
* **What**: Added per-ore persistent configuration map `perOreMultipliers` and a new **Per-Ore Multipliers Category** to the YACL main menu config screen.
* **Why**: Provides players and server admins with dynamic client-side GUI controls (0%–1000% sliders) for individual ore types (Iron, Gold, Diamond, Netherite, Coal, Copper, Lapis, Redstone, Emerald, Quartz).
* **How**: Updated `OreAmplifierConfig.java`, `OreLogic.java`, `YaclScreenHelper.java`, and `en_us.json`.

## [1.1.9+26.2] - 2026-08-01

### Automated Test Integration
* **What**: Added JUnit 5 support in `build.gradle` and created unit test suite `OreAmplifierTest.java`.
* **Why**: Complies with Automated GameTest Verification Law and `gradle-tester` skill requirement to support headless automated testing (`./gradlew test`).
* **How**: Implemented test cases for feature ID recognition, blacklist filtering, and multiplier math calculations.

## [1.1.8+26.2] - 2026-08-01

### Instant Gratification Concept Default Alignment
* **What**: Aligned baseline global ore multipliers with Instant Gratification concept doc specifications: Vanilla default updated to **200% (2.0x)** and Modded default updated to **120% (1.2x)**.
* **Why**: Enforces Instant Gratification design principle ("*Abundance is the default state; one vein should fuel a project*").
* **How**: Updated `OreAmplifierConfig.java` default field values and `OreAmplifierFabric.java` GameRule registration defaults & auto-heal logic.

## [1.1.7+26.2] - 2026-08-01

### Early Init Unbound Tag Exception Guard
* **What**: Wrapped `blockOpt.get().is(...)` in a `try-catch (IllegalStateException e)` block in `OreLogic.shouldAmplify()`.
* **Why**: `Holder.Reference.isBound()` checks object binding, but `Holder.Reference.is(TagKey)` queries `this.tags` which is null before Minecraft binds datapack tags during startup.
* **How**: Caught `IllegalStateException` during early init, falling back to static blacklist and name heuristics until tags bind.

## [1.1.6+26.2] - 2026-08-01

### Startup Tag Binding Guard
* **What**: Added `blockOpt.get().isBound()` check prior to calling `.is(TagKey)` in `OreLogic.shouldAmplify()`.
* **Why**: To prevent `IllegalStateException: Tags not bound` during pre-registration scan in `onInitialize()` before Minecraft tag binding phase.
* **How**: Guarded block holder tag checks with `isBound()`.

## [1.1.5+26.2] - 2026-08-01 (🚨 CRITICAL BUG / DO NOT PUBLISH)

### Tag-Driven Blacklist Support (DO NOT PUBLISH BUILD)
* **Status**: 🚨 **CRITICAL BUG / DO NOT PUBLISH**. This build introduced un-guarded dynamic tag checks during `onInitialize()` before Minecraft tag binding, causing an `IllegalStateException: Tags not bound` crash on startup. Use `v1.1.6+26.2` or newer instead.
* **What**: Replaced static set in `OreLogic.java` with dynamic TagKey checks (`TagKey.create(Registries.PLACED_FEATURE, ...)` and `TagKey.create(Registries.BLOCK, ...)`).
* **Why**: To empower modpack creators and datapack authors to configure worldgen feature exclusions dynamically via standard Minecraft JSON tags (`#c:ore_amplifier_blacklist`).
* **How**: Added default convention tag JSON files under `data/c/tags/worldgen/placed_feature/` and updated `OreLogic.shouldAmplify()` to query registry tag membership.

## [1.1.4+26.2] - 2026-08-01

### Enhanced `/oreamp` Command Suite & Tab-Completion
* **What**: Added `/oreamp help`, `/oreamp status`, `/oreamp get`, `/oreamp set`, `/oreamp reset`, and `/oreamp reload` subcommands with Brigadier auto-completion for registered ore IDs.
* **Why**: To provide server operators with full in-game inspection and control over ore generation rates without editing JSON files or manually typing complex GameRule keys.
* **How**: Implemented structured Brigadier tree in `OreCommand.java` utilizing `OreLogic` dynamic GameRule lookup and `SharedSuggestionProvider` filtering.

## [1.1.3+26.2] - 2026-07-22

### Standardized Warning Notice Tooltips
* **What**: Appended `§6Notice:§r` warning text into option descriptions inside `en_us.json`.
* **Why**: To clearly warn players that changing configuration settings only defines default values for newly generated worlds, and existing worlds must be changed in-game.
* **How**: Modified translations inside `en_us.json` for config option description keys.

## [1.1.0+26.1.2] - 2026-07-11

### Added
- **YACL & ModMenu GUI Config**: Added optional in-game configuration screen via ModMenu and YetAnotherConfigLib (YACL) v3.
  - *Why*: Provides users with a clean main-menu GUI to customize baseline fallback multiplier settings without needing to be inside a loaded world.
  - *How*: Implemented `ModMenuIntegration` registered under the `modmenu` entrypoint in `fabric.mod.json`, using deferred reflection isolation (`Class.forName`) to call `YaclScreenHelper.createScreen()` to shield classes and prevent dedicated server startup crashes when YACL/ModMenu are absent.
- **Global Config Templates**: Created `ore-amplifier.json` template stored in the client `config/` directory.
  - *Why*: Holds default parameters globally rather than world-locally.
  - *How*: Added `OreAmplifierConfig` utilizing `net.dasik.social.api.config.ConfigHelper` from `dasik-library` to load and save `ore-amplifier.json` cleanly with atomic temporary swaps.
- **New World Sync**: Synchronized global templates into active GameRules on new world generation.
  - *Why*: World-specific level data overrides are required by the engine, but new worlds should inherit the user's template.
  - *How*: Registered `ServerLifecycleEvents.SERVER_STARTED` event to reload the JSON configuration, and if `!server.getWorldData().overworldData().isInitialized()`, copy the template values (`vanillaGlobalMultiplier` and `moddedGlobalMultiplier`) into active GameRules.

## [1.0.0+build.70] - 2026-02-21

### Fixed

- **Stability**: Resolved a critical `IllegalStateException: Registry is already frozen` crash during world generation. `OreLogic.getMultiplier` is now null-safe, falling back to global multipliers if JIT GameRule registration fails during the feature placement phase.

## [1.0.0+build.69] - 2026-02-21

### Fixed

- **Compatibility**: Reverted Mixin compatibility level from `JAVA_25` to `JAVA_22` to resolve warning.

## [1.0.0+build.68] - 2026-02-21

### Added

- **Documentation**: Swept and updated all project documentation (README, Platform Pages, Player docs) to accurately describe the new generated human-readable GameRule string integration via DasikLibrary 1.6.9+build.5.

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

## [1.0.0+build.58] - 2026-02-19

### Changed

- **Default Behavior**: Dynamic Ore rules now default to `100` (which falls back to Global settings) instead of `0`.
- **Logic**: Setting a specific ore to `100` uses the Global Multiplier. Setting it to anything else (e.g., `50` or `0`) overrides the global setting.

## [1.0.0+build.57] - 2026-02-19

### Changed

- **Versioning**: Switched to Build Number policy.
- **Documentation**: Shortened Modrinth summary to <200 chars.
- **Fallback Logic**: Renamed "Vanilla Global" to "Vanilla Ore Fallback Multiplier" to clarify it only applies when specific rules are set to default (0).
- **Stateless Logic**: Rewrote `OreLogic` to be completely stateless, fixing reliability issues with `ThreadLocal` variables.

### Added

- **Safety Cap**: Removed hard limit. Added strong warnings for high values (>5000%) to prevent freezes.
- **Ore Reduction**: Multipliers < 100% now work using stochastic reduction (e.g., 50% = 50% chance to generate).
- **Dynamic Localization**: GameRules now display friendly names (e.g., "Copper Ore Multiplier") instead of raw identifiers.
- **Rarity Support**: Added support for amplifying `RarityFilter` based ores (like Diamond in certain biomes).
- **Dynamic Ore Detection**: Automatically scans and creates GameRules for all ores (Vanilla + Modded).
- **Granular Control**: Specific GameRules for every ore (`ig_ore_techmod_copper_ore`).
- **Global Fallbacks**: Separate global multipliers for Vanilla and Modded ores.
- **World Gen Hook**: Modifies `CountPlacement` to increase vein frequency.
- **Snapshot Support**: Native compatibility with Minecraft 26.1 Snapshot 8.
