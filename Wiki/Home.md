🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

---
# ⛏️ Ore Amplifier

> **Multiply ore generation (vein count and vein size) for both Vanilla and Modded ores.**
> Fully configurable via GameRules, in-game commands, YACL GUI, and JSON config.
> Part of the **Instant Gratification Collection** by **Dasik (Rifaditya)**.

---

| Property | Details |
| :--- | :--- |
| **Mod ID** | `ore-amplifier` |
| **Current Version** | `1.3.0+26.2` |
| **Minecraft Version** | 26.2+ |
| **Environment** | Client & Server (`*`) |
| **Mod Loader** | Fabric (Fabric API required) |
| **Dependencies** | [Dasik Library](https://modrinth.com/mod/dasik-library) (≥1.8.2), Fabric API |
| **Optional** | [YACL v3](https://modrinth.com/mod/yacl), [Mod Menu](https://modrinth.com/mod/modmenu) |
| **License** | GNU General Public License v3.0 (GPLv3) |
| **Author** | Dasik (Rifaditya) |

---

## ✨ Feature Highlights

### 🔢 Vein Count Multiplication
Multiply how many ore veins spawn per chunk. Vanilla ores default to **2×** (200%) and modded ores to **1.2×** (120%). Configurable from 1% to 1000% per ore or globally.

### 📐 Vein Size Amplification
Increase the number of blocks per individual vein from 100% up to 500%. A diamond vein that normally spawns 8 blocks can grow to 40 blocks.

### 🎯 Per-Ore Control
Set specific multipliers for individual ores (e.g., 5× diamonds, 1× iron) via GameRules, commands, YACL GUI, or JSON config. All configuration methods stay in 2-way sync.

### 🚫 Blacklist System
Exclude specific features from amplification using data-driven convention tags (`#c:ore_amplifier_blacklist`) — perfect for modpack creators.

### 🖥️ YACL Configuration GUI
Optional graphical config screen with sliders for 18 vanilla ores and global multipliers. Requires YACL v3 and Mod Menu (client-side only).

### 💬 `/oreamp` Command Suite
Full Brigadier command tree with tab-completion: `status`, `get`, `set`, `reset`, `reload`.

---

## 🚀 Quick Start

1. **Install** the mod JAR alongside **Fabric API** and **Dasik Library** into your `mods/` folder.
2. **Launch** a world — ores in newly generated chunks will automatically spawn at **2× vanilla / 1.2× modded** rates.
3. **Customize** multipliers:
   - **GameRules Screen**: Press `Esc` → `Open to LAN` → `Game Rules` → **Ore Amplifier** category
   - **Commands**: `/oreamp set global vanilla 500` (5× vanilla ores)
   - **Per-Ore**: `/oreamp set minecraft:diamond_ore 1000` (10× diamonds)
4. **Regenerate chunks** with [MCA Selector](https://github.com/Querz/mcaselector) to apply changes to already-explored areas.

> ⚠️ **Important**: Ore amplification only affects **newly generated chunks**. Existing chunks retain their original ore distribution.

---

## 📖 Wiki Pages

### Guide
- [[Ore Detection and Amplification|Ore-Detection-and-Amplification]] — Core mechanics, blacklist system, stochastic math
- [[GameRules and Configuration|GameRules-and-Configuration]] — All GameRules, YACL GUI, JSON config, resolution hierarchy
- [[Commands|Commands]] — `/oreamp` command reference

### Technical
- [[Architecture and Mixins|Architecture-and-Mixins]] — Package structure, Mixin breakdown, DasikLibrary integration
- [[Developer Setup and Building|Developer-Setup-and-Building]] — JDK 25, Gradle, Loom, testing
- [[Version Compatibility|Version-Compatibility]] — Version history, dependency matrix

---

## 🔗 Links

- **Modrinth**: [Ore Amplifier on Modrinth](https://modrinth.com/mod/instant-gratification-ore-amplifier)
- **CurseForge**: [Ore Amplifier on CurseForge](https://www.curseforge.com/minecraft/mc-mods/instant-gratification-ore-amplifier)
- **Source Code**: [GitHub](https://github.com/Rifaditya/Instant-Gratification-Ore-Amplifier)
- **Issue Tracker**: [GitHub Issues](https://github.com/Rifaditya/Instant-Gratification-Ore-Amplifier/issues)

---

> 📌 **Repository Source Disclaimer**: The documentation in this Wiki reflects the **current source code state in the repository**, which may include recent unreleased commits or developmental features ahead of public release builds on CurseForge and Modrinth.
