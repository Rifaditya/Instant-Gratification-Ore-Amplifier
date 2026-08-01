# 💎 Ore Amplifier

> "Stop grinding. Start crafting."

**Ore Amplifier** gives you complete control over resource scarcity. Whether you want slightly more iron or an explosion of diamonds, it's just a GameRule away.

**Part of the Instant Gratification Collection** — Respect the Player's Time, Not the Game's Rules.

## Features

- **Amplified Generation**: Multiply **ore vein frequency** with no hard limits.
  - *Note: This increases the number of veins per chunk, NOT the size of the veins.*
- **Unrestricted Control**: No built-in safety caps. You have full control over the world generator's limits.
- **Dynamic Naming**: Rules appear as **"Iron Ore Multiplier"** instead of raw code (`ig_ore_minecraft_iron_ore`), thanks to `DasikLibrary`'s dynamic translation injection.
- **Modded Ore Support**: Automatically detects modded ores and creates specific rules for them.
- **Granular Control**: Set global defaults or tweak specific ores individually.
- **Library Powered**: Uses **DasikLibrary** for high-precision stochastic generation.
- **Flexible Configuration**: Adjust settings in-game via native `GameRules` or using **ModMenu** + **YetAnotherConfigLib (YACL) v3** GUI. Defines global defaults in `config/ore-amplifier.json`.

> **Note**: Changes only affect **newly generated chunks**. Existing chunks must be regenerated to see updated ore counts. Use an external tool like MCA Selector or delete the region files manually.

## Configuration

All settings are `GameRules` — changeable via `/gamerule`, the Edit Game Rules screen, or the ModMenu GUI config screen.

| Rule Name | Internal ID | Default | Description |
|---|---|---|---|
| **Vanilla Ore Multiplier** | `ig_ore_vanilla_global` | 100 | Base multiplier for all Vanilla ores (100 = 1x). Used if specific rule is 100. |
| **Modded Ore Multiplier** | `ig_ore_modded_global` | 100 | Base multiplier for Modded ores. Used if specific rule is 100. |
| **[Ore Name] Multiplier** | `ig_ore_<mod>_<ore>` | 100 | Specific multiplier. 100 = Fallback to Global. Shows as readable text in-game! |

*Safety Limit:* Multipliers are **completely uncapped**. However, extremely high values (e.g. 5000+) will significantly strain world generation. If the multiplier exceeds **1,000x (100,000+ veins)** for a single ore, the mod will trigger a **Detailed Crash Report** to help you identify the offending multiplier.

## Building

```bash
./gradlew build
```

Output JAR: `build/libs/ore-amplifier-<version>.jar`

## Requirements

- **Minecraft**: 26.2 / 26.1.2
- **Fabric Loader**: 0.18.4+
- **DasikLibrary**: 1.8.0+
- **Java**: 25+

## License

GPL-3.0-or-later
