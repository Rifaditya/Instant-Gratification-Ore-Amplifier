# History

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
