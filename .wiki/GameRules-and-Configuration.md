# GameRules and Configuration

| Property | Details |
| :--- | :--- |
| **GameRule Category** | `minecraft:ore_amplifier` |
| **Category Translation** | `gamerule.category.ore_amplifier` → "Ore Amplifier" |
| **Config File** | `config/ore-amplifier.json` |
| **Config Engine** | DasikLibrary `ConfigHelper` |
| **GUI Framework** | YACL v3 (optional) |
| **GUI Entry** | Mod Menu (optional) |

---

## Configuration Methods Overview

Ore Amplifier provides **four** ways to configure multipliers, all kept in **2-way sync**:

| Method | Access | Scope | Persistence |
| :--- | :--- | :--- | :--- |
| **GameRules Screen** | `Esc` → `Open to LAN` → `Game Rules` → Ore Amplifier | Per-world | World save |
| **`/oreamp` Commands** | In-game chat | Per-world + JSON | Both |
| **YACL GUI** | Mod Menu → Ore Amplifier → Configure | Template config | JSON file |
| **JSON Config** | `config/ore-amplifier.json` | Global template | JSON file |

> 💡 **2-Way Sync**: When you change a value via `/oreamp set`, it updates both the active world's GameRule AND the JSON config file. Similarly, `/oreamp reload` reads the JSON config and pushes values into GameRules.

---

## GameRules Reference

### Static GameRules

| GameRule Key | Display Name | Type | Range | Default | Description |
| :--- | :--- | :--- | :--- | :--- | :--- |
| `ig:ore_vanilla_global` | Vanilla Ore Fallback Multiplier (%) | Integer | 1–1000 | **200** | Global multiplier for all `minecraft:` namespace ores. 200 = 2.0× |
| `ig:ore_modded_global` | Modded Ore Multiplier (%) | Integer | 1–1000 | **120** | Global multiplier for all non-`minecraft:` namespace ores. 120 = 1.2× |
| `ig:ore_vein_size_multiplier` | Ore Vein Size Multiplier (%) | Integer | 100–500 | **100** | Multiplier for individual vein block volume. 100 = no change |

### Dynamic Per-Ore GameRules

Auto-registered for every ore block detected in `BuiltInRegistries.BLOCK`:

| Pattern | Example | Type | Range | Default |
| :--- | :--- | :--- | :--- | :--- |
| `ig:ore_<namespace>_<path>` | `ig:ore_minecraft_iron_ore` | Integer | 0–1000 | **100** |
| | `ig:ore_minecraft_diamond_ore` | Integer | 0–1000 | **100** |
| | `ig:ore_minecraft_ancient_debris` | Integer | 0–1000 | **100** |
| | `ig:ore_techmod_tin_ore` | Integer | 0–1000 | **100** |

Dynamic GameRule translations are generated at runtime as **Title Case** display names:
- `ig:ore_minecraft_iron_ore` → "Minecraft Iron Ore Multiplier"
- `ig:ore_minecraft_diamond_ore` → "Minecraft Diamond Ore Multiplier"

> 💡 **Setting a per-ore GameRule to 0** disables amplification for that specific ore entirely.

---

## Resolution Priority Hierarchy

When determining the effective multiplier for any ore, the system checks three tiers in order:

```
┌───────────────────────────────────────────────────┐
│  TIER 1: JSON Config Per-Ore Override (Highest)   │
│  config/ore-amplifier.json → perOreMultipliers    │
│  Key: "minecraft:diamond_ore": 500                │
├───────────────────────────────────────────────────┤
│              ↓ (if not set)                       │
├───────────────────────────────────────────────────┤
│  TIER 2: Dynamic GameRule Override                │
│  ig:ore_minecraft_diamond_ore = 300               │
├───────────────────────────────────────────────────┤
│              ↓ (if default 100%)                  │
├───────────────────────────────────────────────────┤
│  TIER 3: Namespace Global Fallback (Lowest)       │
│  minecraft: → ig:ore_vanilla_global (200%)        │
│  other:     → ig:ore_modded_global  (120%)        │
└───────────────────────────────────────────────────┘
```

For the full math and examples, see [[Ore Detection and Amplification|Ore-Detection-and-Amplification]].

---

## JSON Config File

### Location

```
<minecraft_directory>/config/ore-amplifier.json
```

### Schema

```json
{
  "version": 1,
  "vanillaGlobalMultiplier": 200,
  "moddedGlobalMultiplier": 120,
  "veinSizeMultiplier": 100,
  "perOreMultipliers": {}
}
```

### Field Reference

| Field | Type | Default | Description |
| :--- | :--- | :--- | :--- |
| `version` | Integer | `1` | Config schema version (for future migrations) |
| `vanillaGlobalMultiplier` | Integer | `200` | Global vanilla ore multiplier (%) |
| `moddedGlobalMultiplier` | Integer | `120` | Global modded ore multiplier (%) |
| `veinSizeMultiplier` | Integer | `100` | Vein block volume multiplier (%) |
| `perOreMultipliers` | Map\<String, Integer\> | `{}` | Per-ore overrides keyed by identifier |

### Example: Custom Per-Ore Overrides

```json
{
  "version": 1,
  "vanillaGlobalMultiplier": 200,
  "moddedGlobalMultiplier": 120,
  "veinSizeMultiplier": 150,
  "perOreMultipliers": {
    "minecraft:diamond_ore": 500,
    "minecraft:deepslate_diamond_ore": 500,
    "minecraft:ancient_debris": 300,
    "techmod:tin_ore": 200
  }
}
```

---

## YACL Configuration GUI

Requires **YACL v3** and **Mod Menu** (both optional, client-side only).

Access: **Mod Menu** → **Ore Amplifier** → **Configure**

### General Settings Category

| Option | Type | Range | Default | Description |
| :--- | :--- | :--- | :--- | :--- |
| Vanilla Ore Fallback Multiplier | Slider (%) | 1–1000 | 200 | Global vanilla ore multiplier |
| Modded Ore Fallback Multiplier | Slider (%) | 1–1000 | 120 | Global modded ore multiplier |
| Ore Vein Size Multiplier | Slider (%) | 100–500 | 100 | Blocks per vein multiplier |

> ⚠️ **Warning**: Changes made in the YACL GUI update the **template config** (`config/ore-amplifier.json`). These values are synced to new worlds on creation. To apply to an existing world, use `/oreamp reload` after changing the config.

### Per-Ore Multipliers Category

18 vanilla ores with individual 0–1000% sliders:

| Ore | Default | Range |
| :--- | :--- | :--- |
| Iron Ore | 100% | 0–1000% |
| Deepslate Iron Ore | 100% | 0–1000% |
| Coal Ore | 100% | 0–1000% |
| Deepslate Coal Ore | 100% | 0–1000% |
| Copper Ore | 100% | 0–1000% |
| Deepslate Copper Ore | 100% | 0–1000% |
| Gold Ore | 100% | 0–1000% |
| Deepslate Gold Ore | 100% | 0–1000% |
| Redstone Ore | 100% | 0–1000% |
| Deepslate Redstone Ore | 100% | 0–1000% |
| Lapis Lazuli Ore | 100% | 0–1000% |
| Deepslate Lapis Lazuli Ore | 100% | 0–1000% |
| Emerald Ore | 100% | 0–1000% |
| Deepslate Emerald Ore | 100% | 0–1000% |
| Diamond Ore | 100% | 0–1000% |
| Deepslate Diamond Ore | 100% | 0–1000% |
| Nether Quartz Ore | 100% | 0–1000% |
| Ancient Debris | 100% | 0–1000% |

---

## Server Startup Behavior

### Auto-Heal Zeroed Multipliers

On `SERVER_STARTING`, the mod checks if global multipliers have been accidentally set to 0 and resets them to defaults:
- `ig:ore_vanilla_global`: 0 → 200
- `ig:ore_modded_global`: 0 → 120

### Template Config Sync

On `SERVER_STARTED`, if a world has never been initialized with Ore Amplifier config values (new world or first install), the template config from `config/ore-amplifier.json` is synced into the world's GameRules.

---

## Translation Keys

| Key | Value |
| :--- | :--- |
| `gamerule.category.ore_amplifier` | Ore Amplifier |
| `gamerule.ig:ore_vanilla_global` | Vanilla Ore Fallback Multiplier (%) |
| `gamerule.ig:ore_modded_global` | Modded Ore Multiplier (%) |
| `gamerule.ig:ore_vein_size_multiplier` | Ore Vein Size Multiplier (%) |
| `config.ore-amplifier.title` | Ore Amplifier Config |
| `config.ore-amplifier.category.general` | General Settings |
| `config.ore-amplifier.category.per_ore` | Per-Ore Multipliers |
| `config.ore-amplifier.group.options` | Multiplier Options |
| `config.ore-amplifier.group.per_ore` | Specific Ore Controls |

---

## Common Configuration Recipes

### 5× Diamonds Only

```
/oreamp set minecraft:diamond_ore 500
/oreamp set minecraft:deepslate_diamond_ore 500
```

### 3× All Ores

```
/oreamp set global vanilla 300
/oreamp set global modded 300
```

### Disable Modded Ore Amplification

```
/oreamp set global modded 100
```

### Maximum Vein Size (5×)

```
/gamerule ig:ore_vein_size_multiplier 500
```

For the complete command reference, see [[Commands]].
