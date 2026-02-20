# 💎 Ore Amplifier

> "Stop grinding. Start crafting."

**Ore Amplifier** gives you complete control over resource scarcity. Whether you want slightly more iron or an explosion of diamonds, it's just a GameRule away.

**Part of the Instant Gratification Collection** — Respect the Player's Time, Not the Game's Rules.

## Features

- **Amplified Generation**: Multiply **ore vein frequency** by up to **50x**.
  - *Note: This increases the number of veins per chunk, NOT the size of the veins.*
- **Safety Cap**: Built-in protection prevents game freezes if you accidentally type 99999.
- **Dynamic Naming**: Rules appear as **"Iron Ore Multiplier"** instead of raw code.
- **Modded Ore Support**: Automatically detects modded ores and creates specific rules for them.
- **Granular Control**: Set global defaults or tweak specific ores individually.
- **Library Powered**: Uses **DasikLibrary** for high-precision stochastic generation.
- **Pure GameRules**: No config files. Everything is adjustable in-game.

> **Note**: Changes only affect **newly generated chunks**. Existing chunks must be regenerated to see updated ore counts. Use an external tool like MCA Selector or delete the region files manually.

## Configuration

All settings are `GameRules` — changeable via `/gamerule` or the Edit Game Rules screen.

| Rule Name | Internal ID | Default | Description |
|---|---|---|---|
| **Vanilla Ore Multiplier** | `ig_ore_vanilla_global` | 100 | Base multiplier for all Vanilla ores (100 = 1x). Used if specific rule is 100. |
| **Modded Ore Multiplier** | `ig_ore_modded_global` | 100 | Base multiplier for Modded ores. Used if specific rule is 100. |
| **[Ore Name] Multiplier** | `ig_ore_<mod>_<ore>` | 100 | Specific multiplier. 100 = Fallback to Global. |

*Safety Limit:* Multipliers are **uncapped**, but values above **5000 (50x)** may cause world generation to hang or freeze.

## Building

```bash
./gradlew build
```

Output JAR: `build/libs/ore-amplifier-<version>.jar`

## Requirements

- **Minecraft**: 26.1 Snapshot 8
- **Fabric Loader**: 0.18.4+
- **DasikLibrary**: 1.6.9+build.1+
- **Java**: 25+

## License

GPL-3.0-or-later
