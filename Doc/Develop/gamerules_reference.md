# GameRules Reference

**Ore Amplifier** uses GameRules for all configuration. This allows server admins to change settings on the fly without restarting.

## Global Rules

| Rule Name | Description | Default |
| :--- | :--- | :--- |
| `ig_ore_vanilla_global` | Global multiplier for all Vanilla ores (Coal, Iron, etc.). Percentage based (100 = 1x). | `100` |
| `ig_ore_modded_global` | Global multiplier for all Modded ores. Percentage based. Used if no specific rule is set. | `100` |

## Dynamic Rules

The mod automatically generates rules for every detected ore block.

**Format**: `ig_ore_<namespace>_<path>`

**Examples**:

- `ig_ore_minecraft_diamond_ore`
- `ig_ore_techmod_tin_ore`
- `ig_ore_mythicmetals_adamantite_ore`

**Values**:

- `0`: Use the Global fallback (Vanilla or Modded).
- `> 0`: Force this specific multiplier.

> **Warning**: Values >5000 (50x) may cause world generation freezes.

## Category

All rules are located in the custom **Ore Amplifier** category in the Edit Game Rules screen.
