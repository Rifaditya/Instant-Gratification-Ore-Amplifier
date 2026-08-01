# Concept: Instant Gratification: Ore Amplifier (Ironbound)

> Original source: User Request / `concept_ore_amplifier.md`

## Philosophy Fit

**Collection**: Instant Gratification
**Axiom**: *"Abundance is the default state."*

**Reasoning**:
Strip mining is a "Time Tax" that violates the core tenet of Instant Gratification. Players should not spend 80% of their time essentially gambling with RNG for basic resources.
Ore Amplifier does not "give" items; it **respects the successful find**. When a player locates an ore vein, the reward should be substantial enough to minimize the need for repetitive searching.

> "One vein should fuel a project. Two should fuel a city."

## Core Mechanics

### 1. Dynamic Placement Injection (The "Vein Multiplier")

- **Description**: Intercepts the world generation pipeline to multiply the *number of veins* generated per chunk.
- **Target**: `PlacementModifier` (specifically `CountPlacement` and likely `RarityFilter`).
- **Logic**:

    ```java
    // Abstract Logic
    float multiplier = getMultiplier(featureId); // e.g. 2.0, 0.76, 5.0
    int originalCount = feature.getCount();
    int newCount = ProbabilisticRound(originalCount * multiplier);
    return newCount;
    ```

- **Float Precision**: Supports multipliers like `0.76`.
  - *Probabilistic Rounding*: `10 * 0.76 = 7.6`. 60% chance of 8, 40% chance of 7. Over time, averages exactly 7.6.
- **Fortune Interaction**: This happens at **World Gen**. Fortune applies at **Mining**.
- *Result*: Multiplicative Stacking. `(Base Veins * Amplifier) * (Fortune Drop)`.
- *Experience*: Breaking one block yields Fortune drops. Finding one "vein" yields multiple blocks.

### 2. Auto-Classification & Granularity

- **Granular Lookup**: System checks for specific overrides *before* falling back to categories.
    1. **Specific ID**: `minecraft:coal_ore`
    2. **Tag Group**: `#c:tin_ores`
    3. **Namespace**: `minecraft` (Vanilla) vs `techreborn` (Modded)
    4. **Global Fallback**: `ig_ore_amp_global`

### 3. Data-Driven Exclusion (The Blacklist via Tags)

- **Mechanism**: A convention tag `#c:ore_amplifier_blacklist` (or inner mod tag `ore_amplifier:blacklist`).
- **Usage**: Any `PlacedFeature` in this tag is **ignored** by the multiplier.
- **Defaults**:
  - Geodes (`minecraft:amethyst_geode`) - Structures, not veins.
  - Monster Spawners - Not ores.
  - Infested Stone (Silverfish) - Dangerous to multiply.

## Configuration (Hybrid System)

To support "Infinite Granularity" while keeping "Instant Gratification" simplicity, we use a **Hybrid System**:

### A. GameRules (The "Knobs")

Standard rules for broad control. Accessible in-game via the "Edit Game Rules" screen.

| Rule | Default | Description |
| :--- | :--- | :--- |
| `ig_ore_vanilla_global` | `2.0` | **Global Multiplier (Vanilla)**<br>Multiplies vein count for all `minecraft:` ores that don't have a specific override.<br>*"Abundance is the default."* |
| `ig_ore_modded_global` | `1.2` | **Global Multiplier (Modded)**<br>Multiplies vein count for non-vanilla ores.<br>*"New ores, new abundance."* |
| `ig_ore_coal` | `2.0` | **Coal Multiplier**<br>Specific override for Coal Ore.<br>*"Fuel for the furnaces."* |
| `ig_ore_copper` | `1.0` | **Copper Multiplier**<br>Specific override for Copper Ore.<br>*"The pipes must flow."* |
| `ig_ore_iron` | `1.0` | **Iron Multiplier**<br>Specific override for Iron Ore.<br>*"The backbone of industry."* |
| `ig_ore_gold` | `1.0` | **Gold Multiplier**<br>Specific override for Gold Ore.<br>*"Conductive and divine."* |
| `ig_ore_diamond` | `1.0` | **Diamond Multiplier**<br>Specific override for Diamond Ore.<br>*"Forever abundant."* |

### Localization Requirements (`en_us.json`)

The GameRule screen must show these detailed descriptions when hovering or recognizing the rule.

```json
{
  "gamerule.ig_ore_vanilla_global.description": "Multiplier for all Vanilla ores (namespace 'minecraft'). Applied when no specific override exists.\nDefault: 2.0x\nNote: Decimal values (e.g. 1.5) use probabilistic rounding.",
  "gamerule.ig_ore_modded_global.description": "Multiplier for all identifiable Modded ores. Applied when no specific override exists.\nDefault: 1.2x",
  "gamerule.ig_ore_coal.description": "Specific multiplier for Coal Ore. Overrides the Global Vanilla setting.",
  "gamerule.ig_ore_copper.description": "Specific multiplier for Copper Ore. Overrides the Global Vanilla setting.",
  "gamerule.ig_ore_iron.description": "Specific multiplier for Iron Ore. Overrides the Global Vanilla setting.",
  "gamerule.ig_ore_gold.description": "Specific multiplier for Gold Ore. Overrides the Global Vanilla setting.",
  "gamerule.ig_ore_diamond.description": "Specific multiplier for Diamond Ore. Overrides the Global Vanilla setting."
}
```

### B. JSON Configuration (The "Fine Tuning")

For modded ores or negative values (reducing rarity).
`config/ore_amplifier.json`:

```json
{
  "overrides": {
    "minecraft:copper_ore": 0.76,
    "techreborn:tin_ore": 3.0,
    "#c:rubies": 5.0
  }
}
```

### C. Dynamic Naming Translations

Generating GameRules for every modded ore used to clutter the UI with raw identifiers (e.g., `ig_ore_techreborn_tin_ore`). Thanks to DasikLibrary's dynamic translation injection, these GameRules now automatically display with human-readable names (e.g., "Techreborn Tin Ore Multiplier").

This effectively solves the "Clean UI" problem without needing a complex in-game command system or external JSON files, keeping configuration 100% within the native GameRules screen.

### Resolution Order

1. **JSON Override** (Managed via Commands) → `minecraft:copper_ore` = `0.76`
2. **JSON Tag** (Managed via Commands) → `#c:tin_ores` = `3.0`
3. **GameRule Override** (Specific Rule) → `ig_ore_coal` = `2.0`
4. **Namespace Default** (Vanilla/Modded Rule) → `ig_ore_vanilla_global`

## Edge Case Handling

1. **Safety Cap (Performance Protection)**:
    - *Risk*: A multiplier of `100.0x` could crash the world generator or corrupt chunk data.
    - *Mechanism*: Hard internal cap of `MAX_VEINS = 32` (or reasonable density limit).
    - *Logic*: `finalCount = Math.min(calculatedCount, 32);`
    - *Feedback*: Warning log if a chunk hits the cap.
2. **Dense Ores**: Some mods add "Dense" variants. These are treated as normal ores. Multiplied Dense Ores = Extreme Wealth. This is *intended* ("Instant Gratification").
3. **Large Veins (Copper/Iron)**: Vanilla 1.18+ adds massive ore veins.
    - *Risk*: Multiplying a 2000-block copper vein by 10x might crash the generator.
    - *Mitigation*: The `CountPlacement` modifier usually controls *frequency*, not *size*. More frequent large veins is acceptable. The Safety Cap (`MAX_VEINS`) prevents infinite loops.
4. **Structure Ores**: Ores in Bastions/Ancient Cities.
    - *Behavior*: These uses `Structure` generation, not `PlacedFeature` modifiers usually. They will likely be **ignored** automatically. This is desired.

## Implementation Checklist

- [ ] **Project Setup**: Use `project-scaffolder` skill.
- [ ] **Registry**: Register 3 GameRules in `IOreAmpRules`.
- [ ] **Tagging**: Create `data/c/tags/worldgen/placed_feature/ore_amplifier_blacklist.json`.
- [ ] **Mixin**:
  - Target: `net.minecraft.world.gen.placementmodifier.CountPlacementModifier` (or equivalent in Mapping 26.1).
  - Injection: `getPositions` or `getCount`.
- [ ] **Logic**:
  - Implement `OreDetector` helper class.
  - Implement "Namespace Sniffer" for Vanilla vs Modded.
- [ ] **Logging**:
  - Log `[Ore Amplifier] Hooking X features in biome Y` (Debug level) to verify generic hook success.

## Verification Plan

1. **Litematica/WorldEdit Count**:
    - Gen Chunk A (Rules=1). Count Diamond Ores.
    - Gen Chunk B (Rules=10). Count Diamond Ores.
    - Expect ~10x difference.
2. **Mod Compat Test**:
    - Install `Mythic Metals` (or similar).
    - Verify modded ores are multiplied.

## Archive / Future Ideas Do not Do It WARNING

- **Retrogen (Retroactive Generation)**:
  - *Idea*: Re-scan existing chunks and inject multiplied ores.
  - *Status*: **Deferred / High Risk**.
  - *Reasoning*: Requires complex chunk parsing and state tracking. High risk of corruption or "double-gen" if usage data is lost. Saved for v2.0 or separate utility mod.
Do not Do It WARNING
