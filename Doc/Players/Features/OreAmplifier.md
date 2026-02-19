# Ore Amplifier

**Ore Amplifier** is a mod designed to give you precise control over the abundance of ores in your world.

## Core Concepts

### 1. Vein Count Multiplication

Unlike other mods that might increase the *size* of a vein (making huge blobs of ore), Ore Amplifier increases the **number of veins** that generate per chunk.

- **Vanilla**: A chunk might have 1 vein of Diamond Ore.
- **Amplified (10x)**: That same chunk will attempt to generate 10 veins of Diamond Ore.

This results in a much more natural distribution of resources, maintaining the feeling of "mining" while significantly increasing yields.

> **⚠️ Warning**: High multipliers (>5000) may cause world generation to freeze or crash due to excessive placement attempts. Use with caution!

### 2. Modded Ore Support

The mod is built to be "Instantly Compatible". It scans your game for any block with "ore" in its name.

- **No Config Files**: You don't need to manually add block IDs to a config file.
- **Dynamic Rules**: The mod automatically creates a unique GameRule for every detected ore.
- **Modded Fallback**: If you add a new mod and don't touch the settings, its ores will use the `ig_ore_modded_global` multiplier.

## Configuration Hierarchy

The mod decides which multiplier to use based on a strict priority:

1. **Specific Rule**: Is `ig_ore_<mod>_<ore>` set to something other than 0? **Use it.**
2. **Namespace Check**:
    - Is the ore from Minecraft? Use `ig_ore_vanilla_global`.
    - Is the ore from a mod? Use `ig_ore_modded_global`.

## Examples

### Scenario A: Double Everything

- `ig_ore_vanilla_global` = 200
- `ig_ore_modded_global` = 200

### Scenario B: Rare Magic Ores, Common Tech Ores

- `ig_ore_modded_global` = 100 (Default)
- `ig_ore_techmod_tin_ore` = 500 (5x Tin)
- `ig_ore_magicmod_mithril_ore` = 50 (0.5x Mithril - rare!)

## Existing Worlds

Ore Amplifier modifies **world generation**. This means:

1. **New Chunks**: Any new chunk you explore will use your current GameRule settings.
2. **Old Chunks**: Chunks you have already visited *will not change*. The ores there have already been placed.

### How to Regenerate Old Chunks

**Note: There is no in-game Vanilla command to regenerate chunks.** You must use one of the following methods:

- **Option A (Manual File Deletion)**:
  1. Backup your world.
  2. Use a tool like **MCA Selector** (recommended) or manually delete `.mca` region files in `world/region`.
  3. When you reload the world, Minecraft will see the missing chunks and regenerate them with the new Ore Amplifier rules.
  
- **Option B (Modded Retrogen)**:
  - Use a mod dedicated to "Retrogen" (Retroactive Generation) if available for your version. Ore Amplifier does not include built-in retrogen.
