# GameRules Reference

**Ore Amplifier** uses GameRules for all configuration. This allows server admins to change settings on the fly without restarting.

## Global Rules

| Rule Name | Description | Default |
| :--- | :--- | :--- |
| `ig_ore_vanilla_global` | Global multiplier for all Vanilla ores (Coal, Iron, etc.). Percentage based (100 = 1x). | `100` |
| `ig_ore_modded_global` | Global multiplier for all Modded ores. Percentage based. Used if no specific rule is set. | `100` |

## Dynamic Rules

The mod automatically generates rules for every detected ore block, and dynamically registers readable names for them via DasikLibrary.

**Internal Format**: `ig_ore_<namespace>_<path>`
**In-Game Example**: "Minecraft Diamond Ore Multiplier"

**Raw Examples**:

- `ig_ore_minecraft_diamond_ore`
- `ig_ore_techmod_tin_ore`
- `ig_ore_mythicmetals_adamantite_ore`

**Values**:

- `100`: Use the Global fallback (Vanilla or Modded). This is the **Default**.
- `!= 100`: Force this specific multiplier.
  - `< 100`: Reduces generation (e.g. `50` = 50% chance).
  - `0`: Disables the ore.

> **Warning**: Values >5000 (50x) may cause world generation freezes.

## Category

All rules are located in the custom **Ore Amplifier** category in the Edit Game Rules screen.
