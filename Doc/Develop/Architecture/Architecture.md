# Architecture

## Overview

**Ore Amplifier** operates by hooking into Minecraft's world generation features during the "Placement" phase.

## Logic Flow

1. **Registry Scan (`OreAmplifierFabric`)**:
    - On startup, iterate `BuiltInRegistries.BLOCK`.
    - Filter for blocks containing "ore" in `path`.
    - Register dynamic `GameRule` for each match.
    - Key: `ig_ore_<namespace>_<path>` (e.g., `ig_ore_minecraft_diamond_ore`).

2. **Feature Identification (`PlacedFeatureMixin`)**:
    - Inject into `PlacedFeature.place()`.
    - Check if the feature being placed is associated with a block ID containing "ore".
    - Store the `Identifier` of the feature in a `ThreadLocal` context (`OreLogic.CURRENT_FEATURE_ID`).

3. **Multiplier Application (`CountPlacementModifierMixin`)**:
    - Inject into `CountPlacement.count()`.
    - Check if the placed feature's registry key contains "ore".
    - Call `OreLogic.getMultiplier(featureId, gameRules)`.
    - Retrieve value:
        - Specific Rule (`ig_ore_...`)
        - Fallback Rule (`ig_ore_vanilla/modded_global`)
    - Return `originalCount * (multiplier / 100)`.

## Thread Safety

We use a **stateless** approach. Logic is computed on-the-fly based on the `PlacedFeature` context passed directly to the mixin, avoiding `ThreadLocal` context leaks.

## Mod Compatibility

Compatibility is achieved via **Dynamic Detection**. We do not hardcode support for specific mods. Instead, we rely on the convention that modded ores include "ore" in their registry name.
