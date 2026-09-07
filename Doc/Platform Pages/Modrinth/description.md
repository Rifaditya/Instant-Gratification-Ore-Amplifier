<p align="center">
  <a href="https://modrinth.com/mod/fabric-api"><img src="https://img.shields.io/badge/Requires-Fabric_API-blue?style=for-the-badge&logo=fabric" alt="Requires Fabric API"></a>
  <a href="https://modrinth.com/mod/dasik-library"><img src="https://img.shields.io/badge/Requires-Dasik_Library-8A2BE2?style=for-the-badge" alt="Requires Dasik Library"></a>
  <img src="https://img.shields.io/badge/Language-Java_25-orange?style=for-the-badge&logo=java" alt="Java 25">
  <img src="https://img.shields.io/badge/License-GPLv3-green?style=for-the-badge" alt="License GPLv3">
  <img src="https://img.shields.io/badge/Minecraft-26.2+-brightgreen?style=for-the-badge" alt="Minecraft 26.2+">
</p>

# ⛏️ Ore Amplifier

> **"Stop Grinding. Start Crafting. Supercharge Your World's Mineral Wealth."**

> [!NOTE]
> **1 Jar 1 Version Policy:** I build **1 dedicated JAR for each Minecraft version** (e.g. MC 26.2, MC 26.3). Please download the exact build that matches your Minecraft installation.
> <br><br>
> **Dependency Requirement:** For modern Minecraft 26.x releases (26.1.2, 26.2, 26.3+), this mod requires both **Fabric API** and **Dasik Library** (`v1.7.4+`).

How many hours have you spent branch mining at Y=-58, wearing out diamond pickaxes through hundreds of deepslate blocks without finding a single diamond or redstone vein? Searching for Ancient Debris in the Nether can feel like an unending, exhausting chore that pulls you away from building, exploring, and creating.

**Ore Amplifier** solves resource scarcity permanently. It gives you complete mathematical authority over ore generation in newly generated chunks. Double vanilla ores, quintuple modded copper or tin, make Ancient Debris plentiful, or tweak individual ore rates down to custom percentages—all dynamically controlled via in-game GameRules and Brigadier commands with zero world-save modification!

Part of the **Instant Gratification Collection** — mods that respect the player's time.

---

## ✨ Features

<p align="center">
  <img src="https://raw.githubusercontent.com/Rifaditya/Instant-Gratification-Ore-Amplifier/refs/heads/main/Images/image.png" alt="Dense Ore Generation Demonstration" width="85%"><br>
  <em>Supercharged ore vein distribution: find rich mineral seams naturally while exploring caves</em>
</p>

### 📈 Stochastic Vein Placement Scaling
Rather than crude block-replacement hacks, Ore Amplifier hooks directly into Minecraft's native feature placement engine (`RepeatingPlacementMixin`). It multiplies the number of attempts and successful vein placements per chunk:
- **Default 2x Vanilla Ores (`200%`)**: Finds twice as many iron, gold, coal, lapis, redstone, diamond, and emerald veins.
- **Default 1.2x Modded Ores (`120%`)**: Gives companion tech and magic mods a sensible head start.
- **Stochastic Rounding**: Fractional percentages (like `150%`) use probabilistic mathematical rounding, guaranteeing fair distribution across chunks without statistical drift.

### 🏔️ Nether & Deepslate Scaling
Ancient Debris, Nether Quartz, Nether Gold, and deepslate minerals are fully recognized and scaled. Strike gold in the Nether wastes or uncover abundant Ancient Debris seams without spending days mining beds into netherrack.

### ⛏️ Native Fortune & Smelting Synergy
Because amplified generation creates real, authentic vanilla ore blocks in the world, your vanilla tools, Fortune enchantments, and Silk Touch pickaxes work with 100% vanilla parity. Break amplified raw iron veins with Fortune III to reap massive mineral yields, or smelt raw ore blocks in your blast furnaces.

### 🔍 Automatic Modded Ore Discovery
Zero tedious manual configuration:
- Scans registry registries upon initialization and automatically identifies any registered block containing `"ore"` or `"debris"` in its name.
- Dynamically creates independent GameRules for every detected ore (e.g. `ig:ore_techreborn_tin_ore`, `ig:ore_create_zinc_ore`).
- Applies global fallback rules (`ig:ore_modded_global`) to any newly discovered modded ores.

### 🛡️ Convention Tag Blacklisting & Anti-Crash Safety
- **Tag Registries**: Supports `c:ore_amplifier_blacklist` and `ore-amplifier:blacklist` across both placed features and blocks, allowing datapacks and modpacks to protect sensitive custom worldgen features.
- **Anti-Crash Guard**: Automatically blacklists non-ore structures like Amethyst Geodes and Monster Rooms (`minecraft:monster_room`).
- **Auto-Heal Safeguard**: Detects if global multipliers are accidentally set to `0` and heals them to default baseline values on world start to prevent missing ore generation.

### 🧩 Compatibility & HUD Integration
- **Server-Side Native**: Generated blocks are 100% vanilla. Players connecting to a dedicated server running Ore Amplifier do not need the mod installed on their client!
- **YetAnotherConfigLib (YACL) & ModMenu**: Modify global default templates in singleplayer via an optional main-menu graphical settings screen.
- **Exploration & Chunk Pruning**: Applies to newly generated chunks. Existing chunks can be updated with tools like MCA Selector or by exploring new horizons.

<p align="center">
  <img src="https://raw.githubusercontent.com/Rifaditya/Instant-Gratification-Ore-Amplifier/refs/heads/main/Images/2026-02-21_01.36.22.png" alt="Native GameRules UI with Ore Amplifier Category" width="85%"><br>
  <em>Native Edit Game Rules screen showing human-readable names generated by DasikLibrary</em>
</p>

---

## 📊 Quick Reference & Mechanics Matrix

| Ore Category / Feature | Vanilla Default | Ore Amplifier Default | Maximum Safe Limit | Tuning Mechanic |
| :--- | :---: | :---: | :---: | :--- |
| **Vanilla Ores Global** | `100%` (1.0x) | **`200%` (2.0x)** | `1000%` (10.0x) | Controlled by `ig:ore_vanilla_global` |
| **Modded Ores Global** | `100%` (1.0x) | **`120%` (1.2x)** | `1000%` (10.0x) | Controlled by `ig:ore_modded_global` |
| **Vein Size Multiplier** | `100%` (1.0x) | **`100%` (1.0x)** | `500%` (5.0x) | Controlled by `ig:ore_vein_size_multiplier` |
| **Diamond Ore** | `100%` (1.0x) | Inherits Vanilla (2x) | `1000%` (10.0x) | Override: `ig:ore_minecraft_diamond_ore` |
| **Ancient Debris** | `100%` (1.0x) | Inherits Vanilla (2x) | `1000%` (10.0x) | Override: `ig:ore_minecraft_ancient_debris` |
| **Iron & Deepslate Iron** | `100%` (1.0x) | Inherits Vanilla (2x) | `1000%` (10.0x) | Override: `ig:ore_minecraft_iron_ore` |
| **Copper & Deepslate Copper** | `100%` (1.0x) | Inherits Vanilla (2x) | `1000%` (10.0x) | Override: `ig:ore_minecraft_copper_ore` |
| **Modded Ores (Tin, Lead, Silver)** | `100%` (1.0x) | Inherits Modded (1.2x) | `1000%` (10.0x) | Auto-registered dynamic GameRule per ore |

---

## 🚀 In-Game Commands & Quick Start

Ore Amplifier includes a dedicated Brigadier command suite under `/oreamp` with tab completion:

```text
/oreamp help                                   → Display in-game help manual and command list
/oreamp status                                 → View current global rates and active custom override count
/oreamp get global                             → View vanilla, modded, and vein size global percentages
/oreamp get <ore_id>                           → Query the exact multiplier for a specific ore block
/oreamp set global vanilla <0-1000>            → Update vanilla global multiplier (e.g. 300 for 3x)
/oreamp set global modded <0-1000>             → Update modded global multiplier (e.g. 200 for 2x)
/oreamp set <ore_id> <0-1000>                  → Override a specific ore (e.g. /oreamp set minecraft:diamond_ore 500)
/oreamp reset                                  → Reset all multipliers back to official defaults (200/120/100)
/oreamp reload                                 → Reload JSON config templates & re-sync active GameRules
```

---

## ⚙️ Configuration (Native GameRules)

> [!IMPORTANT]
> **💡 Config vs. In-Game GameRules:** The global configuration file (`config/ore-amplifier.json`) only defines default values for newly created worlds. In existing worlds, change settings in-game via the **Edit Game Rules** UI screen or the `/oreamp` / `/gamerule` commands.

| GameRule Name | Type | Default | Valid Range | Description |
| :--- | :---: | :---: | :---: | :--- |
| `ig:ore_vanilla_global` | `Integer` | `200` | `1` to `1000` | Global multiplier for all vanilla ores (permille: `200` = 2.0x, `500` = 5.0x). |
| `ig:ore_modded_global` | `Integer` | `120` | `1` to `1000` | Global multiplier for all modded ores (permille: `120` = 1.2x, `200` = 2.0x). |
| `ig:ore_vein_size_multiplier` | `Integer` | `100` | `100` to `500` | Vein volume multiplier scaling individual cluster size (`100` = 1.0x). |
| `ig:ore_<namespace>_<path>` | `Integer` | `100` | `0` to `1000` | Granular per-ore rule. `100` inherits global fallback; other values override specifically. |

---

## 📖 In-Depth How-To & Operational Playbook

### 1. Drop-In Setup & Baseline Initialization
1. Place `ore-amplifier-*.jar` along with **Fabric API** and **Dasik Library** into your `mods` directory.
2. On first startup, the mod creates `config/ore-amplifier.json` with recommended baseline defaults (`vanilla: 200`, `modded: 120`, `veinSize: 100`).

### 2. Live In-Game Tuning vs. Global Template
- **For New Worlds**: Configure global baseline defaults via ModMenu + YACL or edit `config/ore-amplifier.json` before creating your world.
- **For Existing Worlds**: Use `/oreamp set global <vanilla|modded> <val>` or `/gamerule ig:ore_vanilla_global <val>`. Settings apply to all newly generated chunks immediately with zero server restart!

### 3. Dialing In Custom Mineral Economies
- Want diamonds to be common while keeping iron scarce? Run `/oreamp set minecraft:diamond_ore 400` and `/oreamp set minecraft:iron_ore 100`.
- Want massive mega-veins? Increase `ig:ore_vein_size_multiplier` to `200` or `300`. This multiplies the block count inside each individual vein cluster.

### 4. Exploring & Verifying Amplification in New Chunks
- Remember that Minecraft generates world terrain once per chunk. Travel past your existing chunk boundaries to discover freshly generated terrain containing your amplified ore density.
- On established servers, server admins can prune unused border chunks with tools like MCA Selector to regenerate surrounding lands with updated ore multipliers.

### 5. Troubleshooting & Blacklists
- To exclude a modded block from amplification, add it to the datapack tag `#c:ore_amplifier_blacklist` or `#ore-amplifier:blacklist`.
- If you ever want to return to clean defaults, simply type `/oreamp reset`.

---

## 🧩 Recommended Sister Mods

If you enjoy **Ore Amplifier**, these companion mods from the **Instant Gratification Collection** plug in seamlessly:

* 🧲 [**Magnet (Let Me Get That!)**](https://modrinth.com/mod/instant-gratification-magnet,-let-me-get-that!): Automatically suction freshly mined ores and experience orbs into your inventory before they fall into lava or deep caves.
* 📦 [**Item Clumps**](https://modrinth.com/mod/ig-item-clumps): Merges massive mining drops into single lightweight entities with holographic count labels, eliminating entity lag in strip mines.
* ⚒️ [**Durability Multiplier**](https://modrinth.com/mod/instant-gratification-durability-multiplier): Multiply tool durability or activate God Mode so your pickaxes and maces never shatter during extended mining runs.

> 🌟 *Explore the full [**Instant Gratification Collection**](https://modrinth.com/collection/instant-gratification) for more high-convenience enhancements.*

---

## ☕ Support

If you enjoy the **Instant Gratification Collection**, consider fueling future development!

<p align="center">
  <a href="https://ko-fi.com/dasikigaijin/tip"><img src="https://img.shields.io/badge/Ko--fi-Support%20Me-FF5E5B?style=for-the-badge&logo=ko-fi&logoColor=white" alt="Ko-fi"></a>
  <a href="https://sociabuzz.com/dasikigaijin/tribe"><img src="https://img.shields.io/badge/SocioBuzz-Local_Support-7BB32E?style=for-the-badge" alt="SocioBuzz"></a>
  <a href="https://saweria.co/DasikIgaijinn"><img src="https://img.shields.io/badge/Saweria-Local_Support-FFA500?style=for-the-badge" alt="Saweria"></a>
</p>

> [!NOTE]
> **🇮🇩 Indonesian Users:** SocioBuzz and Saweria support local payment methods (Gopay, OVO, Dana, etc.) if you want to support me without using PayPal/Ko-fi!

> [!TIP]
> **Dedicated Server Hosting Partner:**
> Looking for a reliable server to play with friends? Check out **BisectHosting** for 1-click modpack installations, automated backups, and 24/7 dedicated customer support.

---

## 📜 Credits & Modpack Permissions

| Property | Information |
| :--- | :--- |
| **Creator / Author** | **Dasik** (Rifaditya) |
| **Collection** | Instant Gratification Collection |
| **License** | [GNU General Public License v3.0 (GPLv3)](https://www.gnu.org/licenses/gpl-3.0.html) |
| **Source Code** | [GitHub - Rifaditya/Instant-Gratification-Ore-Amplifier](https://github.com/Rifaditya/Instant-Gratification-Ore-Amplifier) |
| **Issue Tracker** | [GitHub Issues](https://github.com/Rifaditya/Instant-Gratification-Ore-Amplifier/issues) |
| **Documentation / Wiki** | [GitHub Wiki](https://github.com/Rifaditya/Instant-Gratification-Ore-Amplifier/wiki) |

> [!IMPORTANT]
> **📦 Modpack Permissions & Distribution:**<br>
> You are fully welcome to include this mod in any modpack on any platform! However, the mod file must be downloaded directly through official distribution channels (**Modrinth** or **CurseForge**). Re-uploading, mirroring, or redistributing the original mod JAR to third-party mirror sites, scraper portals, or unauthorized launchers is strictly prohibited.
> <br><br>
> **⚖️ License & Fork Guidelines (No Zero-Change Re-uploads):**<br>
> This project is open-source under the **GNU GPLv3**. You are fully encouraged to inspect the code, learn from it, and fork the repository to create genuine modifications, substantial feature expansions, or community ports—provided your project remains open-source under GPLv3 with proper attribution.<br>
> **However, straight 1:1 re-uploads, clone forks with no meaningful functional changes, or re-publishing identical builds under different project names (e.g. to farm downloads or rewards) are strictly forbidden.**

---

<p align="center">
  <strong>Made with ❤️ for the Minecraft community</strong><br>
  <em>Part of the Instant Gratification Collection</em>
</p>
