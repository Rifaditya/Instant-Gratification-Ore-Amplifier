# Concept: Ore Amplifier

> **Philosophy**: "Stop grinding. Start crafting."

## Core Mechanics

### 1. Vein Frequency Multiplication

Instead of making veins *larger* (which looks unnatural), we make them *more frequent*.

- **Vanilla**: 1 vein per chunk.
- **Amplified (10x)**: 10 veins per chunk.

### 2. Stochastic Reduction (New v1.1)

Allows for fractional multipliers (e.g., 0.5x).

- **Logic**: `Count * Multiplier`.
- **Example**: 1 vein * 0.5 = 0.5 veins.
- **Implementation**: 50% chance to generate 1 vein, 50% chance to generate 0.

### 3. Modded Ore Support

- **Auto-Detection**: Scans registry for "ore".
- **Dynamic Rules**: Creates `ig_ore_<mod>_<ore>` for every block found (Default: `100`).
- **Fallbacks**: If a specific rule is set to `100`, it uses the `ig_ore_modded_global` setting.
- **Overrides**: Any value other than `100` overrides the global setting (e.g., `0` disables it).

## Configuration

- **Pure GameRules**: No config files.
- **Hierarchy**: Specific Rule > Global Category (Vanilla/Modded).

## Safety

- **Uncapped**: No hard limits.
- **Warning**: >50x may freeze the game.
