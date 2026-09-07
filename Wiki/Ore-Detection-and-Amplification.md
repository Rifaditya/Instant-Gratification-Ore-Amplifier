# Ore Detection and Amplification

| Property | Details |
| :--- | :--- |
| **Core Logic Class** | `OreLogic.java` |
| **Amplification Mixins** | `RepeatingPlacementMixin`, `RarityFilterMixin`, `OreFeatureMixin` |
| **Blacklist Tags** | `#c:ore_amplifier_blacklist`, `#ore-amplifier:blacklist` |
| **Static Blacklist** | `amethyst_geode`, `monster_room`, `monster_room_deep` |
| **Stochastic Engine** | `StochasticUtil` (DasikLibrary) |
| **Controlling GameRules** | `ig:ore_vanilla_global`, `ig:ore_modded_global`, `ig:ore_vein_size_multiplier` |

---

## How Ore Detection Works

Ore Amplifier dynamically detects ores during world generation by analyzing feature and block identifiers. The detection pipeline runs in this order:

### Step 1: Blacklist Filtering

Before any amplification, the feature identifier is checked against three blacklist sources:

1. **Convention Tag**: `#c:ore_amplifier_blacklist` — community-standard tag for cross-mod compatibility
2. **Mod Tag**: `#ore-amplifier:blacklist` — mod-specific tag (references the convention tag)
3. **Static Blacklist**: Hardcoded exclusions:
   - `minecraft:amethyst_geode`
   - `minecraft:monster_room`
   - `minecraft:monster_room_deep`

> 💡 **Early-Init Safety**: Tag resolution uses a `try-catch(IllegalStateException)` guard to handle cases where tags haven't been bound yet during early world generation.

### Step 2: Ore Name Heuristics

If not blacklisted, the identifier is checked against name patterns:

| Pattern | Example Matches |
| :--- | :--- |
| `_ore` (suffix) | `iron_ore`, `deepslate_gold_ore`, `nether_quartz_ore` |
| `ore_` (prefix) | `ore_iron_upper`, `ore_diamond_large` |
| `ore` (exact) | `ore` |
| `debris` (contains) | `ancient_debris` |

If the identifier matches any pattern, it is considered an ore and eligible for amplification.

```
Identifier Check Pipeline:
  │
  ▼
[Blacklist Tags?] ──Yes──► SKIP (return original count)
  │ No
  ▼
[Static Blacklist?] ──Yes──► SKIP
  │ No
  ▼
[Name Matches Heuristic?] ──No──► SKIP
  │ Yes
  ▼
[AMPLIFY] ──► Proceed to multiplier lookup
```

---

## Multiplier Resolution Hierarchy

When an ore is detected, the multiplier is resolved through a **3-tier priority hierarchy**:

```
┌─────────────────────────────────────────────────────┐
│  Tier 1: JSON Config Per-Ore Override               │
│  config/ore-amplifier.json → perOreMultipliers      │
│  (Highest priority — explicit user intent)           │
├─────────────────────────────────────────────────────┤
│         ↓ (if not set / returns -1)                 │
├─────────────────────────────────────────────────────┤
│  Tier 2: Dynamic GameRule Override                  │
│  ig:ore_<namespace>_<path>                          │
│  (e.g., ig:ore_minecraft_iron_ore)                  │
├─────────────────────────────────────────────────────┤
│         ↓ (if default 100%)                         │
├─────────────────────────────────────────────────────┤
│  Tier 3: Namespace Global Fallback                  │
│  minecraft: → ig:ore_vanilla_global (default 200%)  │
│  other:     → ig:ore_modded_global  (default 120%)  │
│  (Lowest priority — catch-all defaults)             │
└─────────────────────────────────────────────────────┘
```

### Resolution Examples

| Ore | Tier 1 (JSON) | Tier 2 (GameRule) | Tier 3 (Global) | **Effective** |
| :--- | :--- | :--- | :--- | :--- |
| `minecraft:diamond_ore` | 500 | 100 | 200 | **500** (Tier 1) |
| `minecraft:iron_ore` | — | 300 | 200 | **300** (Tier 2) |
| `minecraft:coal_ore` | — | 100 | 200 | **200** (Tier 3: vanilla) |
| `techmod:tin_ore` | — | 100 | 120 | **120** (Tier 3: modded) |

---

## Amplification Math

### Vein Count Multiplication

The core amplification uses **stochastic (probabilistic) rounding** to handle non-integer multiplier results:

$$\text{amplifiedCount} = \text{StochasticUtil.getAmplifiedCount}(\text{original}, \text{multiplier\%}, \text{random})$$

The stochastic algorithm:
1. Calculate the raw scaled value: $\text{rawValue} = \text{original} \times \frac{\text{multiplier}}{100}$
2. Take the integer floor: $\text{intPart} = \lfloor \text{rawValue} \rfloor$
3. Calculate fractional remainder: $\text{frac} = \text{rawValue} - \text{intPart}$
4. With probability $\text{frac}$, add 1: $\text{result} = \text{intPart} + (\text{random.nextFloat()} < \text{frac}\ ?\ 1 : 0)$

### Stochastic Rounding Examples

| Original Count | Multiplier | Raw Value | Result | Probability |
| :--- | :--- | :--- | :--- | :--- |
| 5 veins | 200% (2.0×) | 10.0 | **10** | 100% deterministic |
| 10 veins | 120% (1.2×) | 12.0 | **12** | 100% deterministic |
| 1 vein | 200% (2.0×) | 2.0 | **2** | 100% deterministic |
| 1 vein | 120% (1.2×) | 1.2 | **1** (80%) or **2** (20%) | Probabilistic |
| 3 veins | 150% (1.5×) | 4.5 | **4** (50%) or **5** (50%) | Probabilistic |

> 💡 **Why Stochastic?** When a multiplier produces a fractional result (e.g., 1 vein × 1.2 = 1.2), the game can't place 0.2 of a vein. Stochastic rounding preserves the statistical average over many chunks while giving natural-feeling results.

### Vein Size Multiplication

Separate from vein count, the **vein size** (blocks per vein) can be amplified via `ig:ore_vein_size_multiplier`:

$$\text{amplifiedSize} = \left\lfloor \text{originalSize} \times \frac{\text{multiplier}}{100.0} \right\rceil$$

| Original Size | Multiplier | Amplified Size |
| :--- | :--- | :--- |
| 8 blocks (diamond) | 100% | 8 blocks (no change) |
| 8 blocks (diamond) | 200% | 16 blocks |
| 8 blocks (diamond) | 500% | 40 blocks |
| 33 blocks (iron) | 150% | 50 blocks |

> ⚠️ **Vein size amplification** only activates when `ig:ore_vein_size_multiplier > 100`. At the default value of 100%, no vein size changes occur.

---

## Rarity Filter Amplification

Some ores use a **chance-based** placement filter (e.g., emerald ore has `chance: 6`, meaning only 1 in 6 attempts succeeds). The `RarityFilterMixin` amplifies this probability:

$$p_{\text{base}} = \frac{1.0}{\text{chance}}$$

$$p_{\text{amplified}} = \text{StochasticUtil.getAmplifiedProbability}(p_{\text{base}}, \text{multiplier}, \text{random})$$

This means at 200% multiplier, a 1-in-6 chance effectively becomes a 2-in-6 chance (1 in 3).

---

## Interaction with Fortune Enchantment

Ore Amplifier and Fortune enchantment operate on **different layers** and stack **multiplicatively**:

| Layer | What It Affects | When It Applies |
| :--- | :--- | :--- |
| **Ore Amplifier** | Number of ore veins generated per chunk + blocks per vein | During world generation (chunk creation) |
| **Fortune** | Number of items dropped per block mined | During block breaking |

Example with 200% Ore Amplifier + Fortune III:
- Normal: 4 diamond veins × 8 blocks × ~2.2 drops = **~70 diamonds per chunk**
- Amplified: 8 diamond veins × 8 blocks × ~2.2 drops = **~141 diamonds per chunk**

---

## Safety Protection: Runaway Crash Diagnostic

If the amplified vein count exceeds **100,000** for any single feature, the mod triggers a `ReportedException` with a detailed crash report. This prevents:

- Server freezes from extreme multiplier combinations
- Memory exhaustion during chunk generation
- Cascading worldgen failures

The crash report includes the feature identifier, original count, multiplier, and calculated result to help diagnose the issue.

---

## Blacklist Customization (Datapacks)

Modpack creators can exclude custom features from amplification by adding entries to the convention blacklist tag. See [[Developer Setup and Building|Developer-Setup-and-Building]] for a complete datapack guide.

### Quick Example

Create `data/c/tags/worldgen/placed_feature/ore_amplifier_blacklist.json`:

```json
{
  "replace": false,
  "values": [
    "mymod:custom_crystal_vein",
    "mymod:rare_gem_placement"
  ]
}
```

For the full configuration reference, see [[GameRules and Configuration|GameRules-and-Configuration]].
