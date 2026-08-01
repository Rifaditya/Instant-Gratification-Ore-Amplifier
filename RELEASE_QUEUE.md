# 📋 Ore Amplifier Release Queue & Backlog

This file tracks which built versions (from `/Archive/builds/`) have been manually uploaded to Modrinth/CurseForge.
Open this file in your editor and change `[ ]` to `[x]` when you publish a version.

## 🚀 Published & Backlog Queue

- [ ] **`1.3.0+26.2`** (2026-08-01) - Vein Size Amplification Option (100%-500% vein size scaling).
- [ ] **`1.2.1+26.2`** (2026-08-01) - 2-Way GameRule & Command & Config Synchronization.
- [ ] **`1.2.0+26.2`** (2026-08-01) - Expanded YACL / ModMenu Config GUI (Per-Ore Multipliers Category).
- [ ] **`1.1.9+26.2`** (2026-08-01) - Automated Test Integration (JUnit 5 / ./gradlew test suite).
- [ ] **`1.1.8+26.2`** (2026-08-01) - IG Concept Default Alignment (200% Vanilla / 120% Modded defaults).
- [ ] **`1.1.7+26.2`** (2026-08-01) - Fix early init unbound tag exception (try-catch guard).
- [ ] **`1.1.6+26.2`** (2026-08-01) - Fix startup tag binding crash (isBound guard).
- [ ] ⛔ **`1.1.5+26.2`** (2026-08-01) - **[CRITICAL BUG / DO NOT PUBLISH]** Dynamic tag blacklist startup crash (`IllegalStateException: Tags not bound`).
- [ ] **`1.1.4+26.2`** (2026-08-01) - Enhanced `/oreamp` command suite with tab-completion and status/get/set commands.
- [x] **`1.1.3+26.2`** (2026-07-22) - ModVersionGuard Knot ClassLoader Fix.
- [x] **`1.1.2+26.2`** (2026-07-22) - Forward Compatibility & Version Guard.
- [x] **`1.1.1+26.1.2`** (2026-07-11) - - Standardized Config Warning. - - Appended gold warning notice to option descriptions inside en_us.json to clarify config-only-defaults behavior.
- [x] **`1.1.0+26.1.2`** (2026-07-11) - - **Optional GUI Configuration**: Implemented YACL & ModMenu config screen. - - **JSON Config Templates**: Added global config template with synchronization to new worlds. - - **Server Safety**: Built using reflection isolation to prevent crashes.
- [x] **`1.0.0`** (2026-02-19) - - **Dynamic Ore Generation**: Scans and amplifies detected ores. - - **GameRule Config**: Pure in-game configuration via GameRules.
- [x] **`1.0.0+build.61`** (2026-02-20) - - **API Migration**: Delegated all dynamic GameRule generation and caching to DasikLibrary's `DynamicGameRuleManager`. - - **Optimization**: Eliminated redundent pre-world initialization memory overhead.
- [x] **`1.0.0+build.63`** (2026-02-20) - - **Assets**: Added mod icon (`icon.png`).
- [x] **`1.0.0+build.64`** (2026-02-21) - - **Documentation**: Added features banner image to Modrinth Description Page.
- [x] **`1.0.0+build.65`** (2026-02-21) - - **Documentation**: Added features banner image to CurseForge Description Page.
- [x] **`1.0.0+build.66`** (2026-02-21) - - **Documentation**: Added GameRules menu image to Modrinth and CurseForge Description Pages.
- [x] **`1.0.0+build.67`** (2026-02-21) - - **Documentation**: Replaced "Architect" with "Creator" in Platform Page Author roles.
- [x] **`1.0.0+build.68`** (2026-02-21) - - **Documentation**: Updated all READMEs and platform pages to highlight the new "Dynamic Translations" feature making GameRules human-readable via DasikLibrary.
- [x] **`1.0.0+build.69`** (2026-02-21) - - **Compatibility**: Reverted Mixin compatibility level from `JAVA_25` to `JAVA_22` to resolve warning.
- [x] **`1.0.0+build.70`** (2026-02-21) - - **Stability**: Resolved a critical `IllegalStateException: Registry is already frozen` crash during world generation. `OreLogic.getMultiplier` is now null-safe, falling back to global multipliers if JIT GameRule registration fails during the feature placement phase.
- [x] **`1.0.0+build.72`** (2026-03-01) - - **CRITICAL — Crash on World Creation**: Added `BuiltInRegistries.PLACED_FEATURE` pre-registration scan in `onInitialize()`. The world generator uses placed-feature IDs (e.g. `minecraft:ore_dirt`) rather than block IDs; these were never pre-registered by the old block-only scan, causing `IllegalStateException: Registry is already frozen` on the first chunk generation. - - **`/oreamp reset` Silent No-Op**: The dynamic-rule filter in `OreCommand` used prefix `"ig_ore_"` (no namespace) but rule keys are stored as `"ig:ore_..."`. Reset now correctly resets all per-ore multipliers.
- [x] **`1.0.0+build.73`** (2026-03-03) - - **Removed Safety Cap**: Deleted `MAX_VEINS` (32) limit per user request. The mod no longer restricts amplified ore counts, granting full control to the user. - - **Detailed Crash Diagnostics**: Implemented a `ReportedException` diagnostic hook. If a runaway multiplier (over 100,000 veins) would crash the game, a detailed Crash Report is generated identifying the offending Ore ID, Raw Count, and Multiplier.
