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

3. **Multiplier Application (`RepeatingPlacementMixin`)**:
    - Inject into `RepeatingPlacement.count()`.
    - Retrieve multiplier from `ig_ore_...` rules (Stateless).
    - **Stochastic Rounding**: Uses **DasikLibrary**'s `StochasticUtil.getAmplifiedCount()`.
    - If multiplier < 100, generation is probabilistically reduced (e.g., 50% = 50% chance for 1 vein).
    - If multiplier > 100, generation is increased as normal.
    - If multiplier = 100 on a specific ore, it falls back to the Category Global Rule.

## Thread Safety

We use a **stateless** approach. Logic is computed on-the-fly based on the `PlacedFeature` context passed directly to the mixin, avoiding `ThreadLocal` context leaks.

## Mod Compatibility

Compatibility is achieved via **Dynamic Detection**. We do not hardcode support for specific mods. Instead, we rely on the convention that modded ores include "ore" in their registry name.
