<div align="center">

<!-- Banner placeholder — replace URL when banner is uploaded -->
<!-- ![Ore Amplifier Banner](https://media.forgecdn.net/attachments/XXXX/XXXX/banner.jpg) -->

</div>
<p align="center">
    <a href="https://www.curseforge.com/minecraft/mc-mods/fabric-api"><img src="https://img.shields.io/badge/Requires-Fabric_API-blue?style=for-the-badge&logo=fabric" alt="Requires Fabric API"></a>
    <img src="https://img.shields.io/badge/Language-Java-orange?style=for-the-badge&logo=java" alt="Java">
    <img src="https://img.shields.io/badge/License-GPLv3-green?style=for-the-badge" alt="License">
    <img src="https://img.shields.io/badge/Minecraft-26.1+-brightgreen?style=for-the-badge" alt="Minecraft 26.1+">
</p>

# 💎 Ore Amplifier

**No Backports:** This mod targets **Minecraft 26.1+** (Snapshot 8). Older versions are unsupported.

> **Stop grinding. Start crafting.**

**Ore Amplifier** solves the resource scarcity problem. Whether you want slightly more iron or an explosion of diamonds, it's just a GameRule away. Designed for players who value their time. Part of the **Instant Gratification Collection**.

---

## ✨ Features

### 🔧 Amplified Generation

Multiply ore vein generation by any factor. 2x? 10x? 100x? You decide.

- **Global Multipliers**: Set a baseline for all Vanilla or Modded ores.
- **Granular Control**: Specific rules are automatically generated for every detected ore.
- **Vein Count**: Increases the *number* of veins per chunk, ensuring dense, rich worlds.

### 🧩 Modded Ore Support

Built with compatibility in mind.

- **Auto-Detection**: Scans your instance for any block with "ore" in its name.
- **Dynamic Rules**: Automatically creates GameRules for `techmod:tin_ore`, `magicmod:mythril_ore`, etc.
- **Fail-Safe**: If no specific rule is set, it falls back to the Global Modded multiplier.

### ⚙️ Pure GameRules

<p align="center">
  <!-- Replace with screenshot of Ore Amplifier GameRules screen -->
  <img src="https://example.com/screenshot.png" alt="GameRules Screen">
</p>

No config files to manage. Everything lives in the **Edit Game Rules** screen or `/gamerule` commands. Adjust settings in real-time without restarting.

- **`ig_ore_vanilla_global`**: Multiplier for Minecraft ores (Coal, Iron, Diamond, etc.)
- **`ig_ore_modded_global`**: Multiplier for non-Minecraft ores.
- **`ig_ore_<mod>_<ore>`**: Specific multiplier for a specific ore.

### 🌍 Existing Worlds

Does this mod work on old worlds? **Yes**, but only for **new chunks**.

- Ores in chunks you have already explored are "baked in".
- To see changes in old areas, you must **regenerate the chunks**.
- **Note**: There is no vanilla command for this. You must delete the chunk files (e.g., using MCA Selector) so the game is forced to recreate them.

> **⚠️ Warning**: High multipliers (>5000) may cause world generation to freeze or crash due to excessive placement attempts. Use with caution!

---

## 📋 Quick Start

```
/gamerule ig_ore_vanilla_global 200     → Double all vanilla ores
/gamerule ig_ore_modded_global 500      → 5x all modded ores
/gamerule ig_ore_minecraft_diamond_ore 1000 → 10x Diamonds specifically
```

---

## 📦 Install

1. Install **[Fabric API](https://www.curseforge.com/minecraft/mc-mods/fabric-api)**.
2. Download `ore-amplifier-1.0.0.jar` and place it in your `mods` folder.
3. Launch the game — default settings are **100% (Vanilla rates)**. Tune them to your liking!

---

## ☕ Support

If you enjoy the **Instant Gratification** collection, consider fueling the next update!

[![Ko-fi](https://img.shields.io/badge/Ko--fi-Support%20Me-FF5E5B?style=for-the-badge&logo=ko-fi&logoColor=white)](https://ko-fi.com/dasikigaijin/tip)
[![SocioBuzz](https://img.shields.io/badge/SocioBuzz-Local_Support-7BB32E?style=for-the-badge)](https://sociabuzz.com/dasikigaijin/tribe)

> [!NOTE]
> **Indonesian Users:** SocioBuzz supports local payment methods (Gopay, OVO, Dana, etc.) if you want to support me without using PayPal/Ko-fi!

---

## 📜 Credits

| Role | Author |
| :--- | :--- |
| **Architect** | **Rifaditya** (Dasik) |
| **Collection** | Instant Gratification |
| **License** | GPLv3 |

---

> [!IMPORTANT]
> **Modpack Permissions:** You are free to include this mod in modpacks, **provided the modpack is hosted on the same platform** (e.g. CurseForge).
>
> **Cross-platform distribution is not permitted.** If you download this mod from CurseForge, your modpack must also be published on CurseForge.

---

<div align="center">

**Made with ❤️ for the Minecraft community**

*Part of the Instant Gratification Collection*

</div>
