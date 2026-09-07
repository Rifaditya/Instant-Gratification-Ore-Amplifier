# Developer Hub

Welcome to the **Ore Amplifier** development documentation.

## Sections

- **[Getting Started](Getting-Started/index.md)**: Requirements, Building, and Project Structure.
- **[Architecture](Architecture/Architecture.md)**: System flow, module responsibilities, design decisions.
- **[GameRules Reference](gamerules_reference.md)**: Complete reference for Global and Dynamic GameRules.
- **[Changelogs](Changelogs/History.md)**: Full release history.

## Architecture

| Component | Purpose |
| :--- | :--- |
| `OreAmplifierFabric` | Main entrypoint. Registers GameRules and Categories. Scans for ores to generate dynamic rules. |
| `OreLogic` | Central logic. Determines the correct multiplier for a given feature by checking specific vs global rules. |
| `PlacedFeatureMixin` | Intercepts feature placement to identify if the current feature is an ore. |
| `CountPlacementModifierMixin` | Intercepts the count (veins per chunk) logic to apply the multiplier. |

## Key Design Decisions

1. **Vein Count vs Size**: We multiply the *count* of veins, not the size. This prevents "blobby" non-vanilla generation and ensures better distribution.
2. **Dynamic Registry Scanning**: Instead of hardcoding mod support, we scan `BuiltInRegistries.BLOCK` for any ID containing "ore". This provides instant compatibility with 99% of mods.
