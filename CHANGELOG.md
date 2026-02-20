# Changelog

## [1.0.0+build.61] - 2026-02-20

### Changed

- **API Migration**: Delegated all dynamic GameRule generation and caching to DasikLibrary's `DynamicGameRuleManager`.
- **Optimization**: Eliminated redundent pre-world initialization memory overhead.

## [1.0.0] - 2026-02-19

- **Dynamic Ore Generation**: Scans and amplifies detected ores.
- **GameRule Config**: Pure in-game configuration via GameRules.
- **Mod Compatibility**: Auto-detects modded ores using "ore" naming convention.
