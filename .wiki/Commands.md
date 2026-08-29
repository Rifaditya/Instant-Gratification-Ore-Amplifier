# Commands

| Property | Details |
| :--- | :--- |
| **Command Root** | `/oreamp` |
| **Java Implementation** | `OreCommand.java` |
| **Permission Level** | Read: 0 (all players), Write: 2 (Game Masters) |
| **Tab Completion** | Dynamic block suggestions for ore identifiers |
| **2-Way Sync** | Commands sync to both GameRules and JSON config |

---

## Command Tree

```
/oreamp
├── help                              (Permission: 0)
├── status                            (Permission: 0)
├── get
│   ├── global                        (Permission: 0)
│   └── <ore_id>                      (Permission: 0)
└── set
    ├── global
    │   ├── vanilla <value>           (Permission: 2)
    │   └── modded <value>            (Permission: 2)
    ├── <ore_id> <value>              (Permission: 2)
    ├── reset                         (Permission: 2)
    └── reload                        (Permission: 2)
```

---

## Command Reference

### `/oreamp` / `/oreamp help`

Displays the command help listing with all available subcommands.

**Permission**: All players (Level 0)

---

### `/oreamp status`

Displays the current active configuration overview:
- Vanilla Global Multiplier and effective multiplier (e.g., "200% (2.0×)")
- Modded Global Multiplier
- Vein Size Multiplier
- Number of active per-ore overrides

**Permission**: All players (Level 0)

**Example Output**:
```
[Ore Amplifier] Status:
  Vanilla Global: 200% (2.0×)
  Modded Global: 120% (1.2×)
  Vein Size: 100% (1.0×)
  Active Overrides: 2
```

---

### `/oreamp get global`

Displays the current values of all three global multiplier GameRules:
- `ig:ore_vanilla_global`
- `ig:ore_modded_global`
- `ig:ore_vein_size_multiplier`

**Permission**: All players (Level 0)

---

### `/oreamp get <ore_id>`

Queries the effective multiplier for a specific ore, showing which tier of the resolution hierarchy is active.

**Permission**: All players (Level 0)

**Arguments**:

| Argument | Type | Tab Completion |
| :--- | :--- | :--- |
| `<ore_id>` | Resource Location | Dynamic block suggestions (e.g., `minecraft:diamond_ore`) |

**Example**:
```
/oreamp get minecraft:diamond_ore
```

**Example Output**:
```
[Ore Amplifier] minecraft:diamond_ore:
  Effective Multiplier: 500% (5.0×)
  Source: JSON Config Override
  GameRule (ig:ore_minecraft_diamond_ore): 100%
  Global Fallback (vanilla): 200%
```

---

### `/oreamp set global <vanilla|modded> <value>`

Sets the global multiplier for vanilla or modded ores. Updates both the active world GameRule and the JSON config file.

**Permission**: Game Masters (Level 2)

**Arguments**:

| Argument | Type | Values |
| :--- | :--- | :--- |
| `<vanilla\|modded>` | Literal | `vanilla` or `modded` |
| `<value>` | Integer | 1–1000 (percentage) |

**Examples**:
```
/oreamp set global vanilla 500    → Sets vanilla ores to 5.0×
/oreamp set global modded 100     → Disables modded ore amplification
```

---

### `/oreamp set <ore_id> <value>`

Sets a specific per-ore multiplier. Creates a dynamic GameRule override and saves to the JSON config's `perOreMultipliers`.

**Permission**: Game Masters (Level 2)

**Arguments**:

| Argument | Type | Tab Completion |
| :--- | :--- | :--- |
| `<ore_id>` | Resource Location | Dynamic block suggestions |
| `<value>` | Integer | 0–1000 (percentage) |

**Examples**:
```
/oreamp set minecraft:diamond_ore 1000    → 10× diamonds
/oreamp set minecraft:iron_ore 50         → 0.5× iron (halved)
/oreamp set minecraft:coal_ore 0          → Disable coal amplification
```

---

### `/oreamp reset`

Resets ALL multipliers back to factory defaults:
- `ig:ore_vanilla_global` → **200**
- `ig:ore_modded_global` → **120**
- `ig:ore_vein_size_multiplier` → **100**
- All per-ore overrides → **cleared**
- JSON config → **reset to defaults**

**Permission**: Game Masters (Level 2)

---

### `/oreamp reload`

Reloads `config/ore-amplifier.json` from disk and syncs all values into the active world's GameRules.

Useful after manually editing the JSON config file or after changing values in the YACL GUI.

**Permission**: Game Masters (Level 2)

---

## 2-Way Synchronization

All four configuration methods stay synchronized:

```
/oreamp set ──────┐
                   ▼
         ┌─────────────────┐
         │  Active World   │
         │  GameRules      │◄──── GameRules Screen
         └────────┬────────┘
                  │
                  ▼
         ┌─────────────────┐
         │  JSON Config    │
         │  ore-amplifier  │◄──── YACL GUI
         │  .json          │
         └─────────────────┘
                  │
                  ▼
         /oreamp reload (reverse sync)
```

| Action | Updates GameRules? | Updates JSON Config? |
| :--- | :--- | :--- |
| `/oreamp set` | ✅ Yes | ✅ Yes |
| `/oreamp reset` | ✅ Yes | ✅ Yes |
| `/oreamp reload` | ✅ Yes | ❌ Reads only |
| GameRules Screen | ✅ Yes | ❌ No |
| YACL GUI | ❌ No | ✅ Yes |

> 💡 After changing values in the YACL GUI or manually editing `ore-amplifier.json`, run `/oreamp reload` to push changes into the active world's GameRules.

For the full GameRules reference, see [[GameRules and Configuration|GameRules-and-Configuration]].
For the technical architecture, see [[Architecture and Mixins|Architecture-and-Mixins]].
