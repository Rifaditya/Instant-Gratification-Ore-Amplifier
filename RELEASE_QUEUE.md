# 📋 Ore Amplifier 26.3 Release Queue & Parity Hold

This file tracks release versions for **Ore Amplifier 26.3** (`Minecraft 26.3` Modern Lead).

## 🚀 Published & Backlog Queue

* ⏸️ **`1.3.20+26.3`** (Parity Hold) - Verified working lead build on Minecraft 26.3-pre-2; waiting on parity.

* ⏸️ **`1.3.14+26.3`** ⛔ (BUGGED / ON-HOLD / DO NOT PUBLISH) - **⛔ BUGGED / ON-HOLD (CRITICAL)** `[ERR-20260907-001]` / `[BL-OREAMP-001]`: RepeatingPlacement is an interface in 26.3. Complete feature milestone codebase compiled for Minecraft 26.3 with native Feature architecture.
  - **Status**: ⛔ BUGGED / ON-HOLD (CRITICAL) - DO NOT PUBLISH
  - **Blocker**: `[ERR-20260907-001]` / `[BL-OREAMP-001]` (`RepeatingPlacementModifier` incompatible; `RepeatingPlacement` converted to interface in MC 26.3).
  - **Why on hold**: Runtime/compilation failure in MC 26.3 plus generational parity hold against predecessor MC 26.2.
  - **Until when**: Holds until `[ERR-20260907-001]` is resolved and MC 26.2 reaches `1.3.14+26.2` on Modrinth and CurseForge.
  - **Resume action**: Resolve bug, verify in-game, and convert to active `- [ ]` once predecessor reaches parity.

- [ ] **`1.1.8+26.2`** (2026-08-01) - IG Concept Default Alignment (200% Vanilla / 120% Modded defaults).
- [x] **`1.1.7+26.2`** (2026-08-01) - Fix early init unbound tag exception (try-catch guard).
- [x] **`1.1.6+26.2`** (2026-08-01) - Fix startup tag binding crash (isBound guard).
- [x] ⛔ (SUPERSEDED) **`1.1.5+26.2`** (2026-08-01) - **[CRITICAL BUG / DO NOT PUBLISH]** Dynamic tag blacklist startup crash (`IllegalStateException: Tags not bound`).
- [x] **`1.1.4+26.2`** (2026-08-01) - Enhanced `/oreamp` command suite with tab-completion and status/get/set commands.
- [x] **`1.1.3+26.2`** (2026-07-22) - ModVersionGuard Knot ClassLoader Fix.
- [x] **`1.1.2+26.2`** (SUPERSEDED) (2026-07-22) - Forward Compatibility & Version Guard.
- [x] **`1.1.1+26.1.2`** (2026-07-11) - - Standardized Config Warning. - - Appended gold warning notice to option descriptions inside en_us.json to clarify config-only-defaults behavior.
- [x] **`1.1.0+26.1.2`** (SUPERSEDED) (2026-07-11) - - **Optional GUI Configuration**: Implemented YACL & ModMenu config screen. - - **JSON Config Templates**: Added global config template with synchronization to new worlds. - - **Server Safety**: Built using reflection isolation to prevent crashes.
- [x] **`1.0.0`** (SUPERSEDED) (2026-02-19) - - **Dynamic Ore Generation**: Scans and amplifies detected ores. - - **GameRule Config**: Pure in-game configuration via GameRules.
- [x] **`1.0.0+build.61`** (SUPERSEDED) (2026-02-20) - - **API Migration**: Delegated all dynamic GameRule generation and caching to DasikLibrary's `DynamicGameRuleManager`. - - **Optimization**: Eliminated redundent pre-world initialization memory overhead.
- [x] **`1.0.0+build.63`** (2026-02-20) - - **Assets**: Added mod icon (`icon.png`).
- [x] **`1.0.0+build.64`** (SUPERSEDED) (2026-02-21) - - **Documentation**: Added features banner image to Modrinth Description Page.
- [x] **`1.0.0+build.65`** (SUPERSEDED) (2026-02-21) - - **Documentation**: Added features banner image to CurseForge Description Page.
- [x] **`1.0.0+build.66`** (SUPERSEDED) (2026-02-21) - - **Documentation**: Added GameRules menu image to Modrinth and CurseForge Description Pages.
- [x] **`1.0.0+build.67`** (SUPERSEDED) (2026-02-21) - - **Documentation**: Replaced "Architect" with "Creator" in Platform Page Author roles.
- [x] **`1.0.0+build.68`** (SUPERSEDED) (2026-02-21) - - **Documentation**: Updated all READMEs and platform pages to highlight the new "Dynamic Translations" feature making GameRules human-readable via DasikLibrary.
- [x] **`1.0.0+build.69`** (SUPERSEDED) (2026-02-21) - - **Compatibility**: Reverted Mixin compatibility level from `JAVA_25` to `JAVA_22` to resolve warning.
- [x] **`1.0.0+build.70`** (2026-02-21) - - **Stability**: Resolved a critical `IllegalStateException: Registry is already frozen` crash during world generation. `OreLogic.getMultiplier` is now null-safe, falling back to global multipliers if JIT GameRule registration fails during the feature placement phase.
- [x] **`1.0.0+build.72`** (SUPERSEDED) (2026-03-01) - - **CRITICAL — Crash on World Creation**: Added `BuiltInRegistries.PLACED_FEATURE` pre-registration scan in `onInitialize()`. The world generator uses placed-feature IDs (e.g. `minecraft:ore_dirt`) rather than block IDs; these were never pre-registered by the old block-only scan, causing `IllegalStateException: Registry is already frozen` on the first chunk generation. - - **`/oreamp reset` Silent No-Op**: The dynamic-rule filter in `OreCommand` used prefix `"ig_ore_"` (no namespace) but rule keys are stored as `"ig:ore_..."`. Reset now correctly resets all per-ore multipliers.
- [x] **`1.0.0+build.73`** (SUPERSEDED) (2026-03-03) - - **Removed Safety Cap**: Deleted `MAX_VEINS` (32) limit per user request. The mod no longer restricts amplified ore counts, granting full control to the user. - - **Detailed Crash Diagnostics**: Implemented a `ReportedException` diagnostic hook. If a runaway multiplier (over 100,000 veins) would crash the game, a detailed Crash Report is generated identifying the offending Ore ID, Raw Count, and Multiplier.
