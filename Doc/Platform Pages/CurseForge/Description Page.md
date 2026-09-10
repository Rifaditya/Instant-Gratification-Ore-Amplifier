<div align="center">

<!-- Banner placeholder — replace URL when banner is uploaded -->
<!-- ![Ore Amplifier Banner](https://example.com/banner.jpg) -->

</div>
<p align="center">
    <a href="https://discord.gg/EV99bgAFqb" target="_blank" rel="noopener"><img src="https://img.shields.io/badge/Discord-Join_Community-5865F2?style=for-the-badge&logo=discord&logoColor=white" alt="Join Discord"></a>
    <a href="https://www.curseforge.com/minecraft/mc-mods/fabric-api"><img src="https://img.shields.io/badge/Requires-Fabric_API-blue?style=for-the-badge&logo=fabric" alt="Requires Fabric API"></a>
    <a href="https://www.curseforge.com/minecraft/mc-mods/dasik-library"><img src="https://img.shields.io/badge/Requires-Dasik_Library-orange?style=for-the-badge&logo=curseforge" alt="CurseForge: Dasik Library"></a>
    <img src="https://img.shields.io/badge/Language-Java-orange?style=for-the-badge&logo=java" alt="Java">
    <img src="https://img.shields.io/badge/License-GPLv3-green?style=for-the-badge" alt="License">
    <img src="https://img.shields.io/badge/Minecraft-26.2+-brightgreen?style=for-the-badge" alt="Minecraft 26.2+">
</p>

# 💎 Ore Amplifier

**Active Version Policy:** I build **1 JAR for 1 Version**. I only update and maintain the latest active Minecraft version (e.g. when 26.3 is released, 26.2 is retired). No backports or legacy version maintenance. Please do not ask.

> **"Stop grinding. Start crafting."**

**Ore Amplifier** solves the resource scarcity problem. Whether you want slightly more iron or an explosion of diamonds, it's just a config option or GameRule away. Designed for players who value their time.

Part of the **Instant Gratification Collection** — mods that respect the player's time.

<blockquote>
<strong>⚠️ CONFIGURATION WARNING:</strong> The global configuration file (and the ModMenu/YACL configuration screen) only defines default values for <strong>NEWLY GENERATED WORLDS</strong>. Settings for existing/active worlds must be adjusted in-game using the <code>/gamerule</code> command or the <strong>Edit Game Rules</strong> GUI screen. Changing values in the global config after a world has been created will not affect that world.
</blockquote>

---

## ✨ Features

<p align="center">
  <img src="https://raw.githubusercontent.com/Rifaditya/Instant-Gratification-Ore-Amplifier/refs/heads/main/Images/image.png" alt="Ore Generation Demonstration">
</p>

### 🔧 Amplified Generation
Multiply ore vein generation by any factor. 2x? 10x? 100x? You decide.
- **Global Multipliers**: Set a baseline for all Vanilla or Modded ores.
- **Granular Control**: Specific rules are automatically generated for every detected ore.
- **Vein Count**: Increases the *number* of veins per chunk, ensuring dense, rich worlds.

### 🧩 Modded Ore Support
Built with compatibility in mind.
- **Auto-Detection**: Scans your instance for any block with "ore" in its name.
- **Dynamic Rules**: Automatically creates GameRules for any modded ore (e.g., Tin, Lead, Silver).
- **Fail-Safe**: If no specific rule is set, it falls back to the Global Modded multiplier.

### ⚙️ Easy Configuration & GameRules Control
- **In-Game GUI**: Includes optional configuration screen support via **ModMenu** and **YetAnotherConfigLib (YACL) v3**. Modify global baseline templates directly from the main menu!
- **Real-Time Tuning**: Adjust settings inside your active world without restarting.
- **Dynamic Naming**: GameRules show up as readable text (e.g., "Iron Ore Multiplier") thanks to DasikLibrary.
- **Fail-Safe Crash Reports**: Includes protection against extreme multipliers that could crash worldgen.

<p align="center">
  <img src="https://raw.githubusercontent.com/Rifaditya/Instant-Gratification-Ore-Amplifier/refs/heads/main/Images/2026-02-21_01.36.22.png" alt="Native GameRules UI">
</p>

### 🌍 Existing Worlds
- **New Chunks Only**: Works instantly on unexplored areas.
- **Regeneration Compatible**: Use tools like MCA Selector to reset explored chunks if you want to see changes there.

---

## 📋 Quick Start

```sql
/gamerule ig_ore_vanilla_global 200     → Double all vanilla ores
/gamerule ig_ore_modded_global 500      → 5x all modded ores
/gamerule ig_ore_minecraft_diamond_ore 1000 → 10x Diamonds specifically
```

<hr>

<h2>☕ Support</h2>

<p>If you enjoy the <strong>Instant Gratification</strong> collection, consider fueling future updates!</p>

<p align="center">
  <a href="https://ko-fi.com/dasikigaijin/tip"><img src="https://img.shields.io/badge/Ko--fi-Support%20Me-FF5E5B?style=for-the-badge&amp;logo=ko-fi&amp;logoColor=white" alt="Ko-fi"></a>
  <a href="https://sociabuzz.com/dasikigaijin/tribe"><img src="https://img.shields.io/badge/SocioBuzz-Local_Support-7BB32E?style=for-the-badge" alt="SocioBuzz"></a>
  <a href="https://saweria.co/DasikIgaijinn"><img src="https://img.shields.io/badge/Saweria-Local_Support-FFA500?style=for-the-badge" alt="Saweria"></a>
</p>

<blockquote><p><strong>🇮🇩 Indonesian Users:</strong> SocioBuzz and Saweria support local payment methods (Gopay, OVO, Dana, etc.) if you want to support me without using PayPal/Ko-fi!</p></blockquote>

<blockquote><p><strong>Dedicated Server Hosting Partner:</strong><br>Looking for a high-performance server to host your community or play with friends? Check out <strong>BisectHosting</strong> for 1-click modpack installations, automated backups, and 24/7 dedicated customer support. Use promo code <strong><code>Dasik</code></strong> for 25% off your first month!</p></blockquote>

<h3>💬 Join the Community &amp; Get Support</h3>
<p>Looking for help, want to test early beta builds, or vote on upcoming features? Join our official Discord community!</p>
<p align="center">
  <a href="https://discord.gg/EV99bgAFqb" target="_blank" rel="noopener">
    <img src="https://img.shields.io/badge/Discord-Join_Community-5865F2?style=for-the-badge&amp;logo=discord&amp;logoColor=white" alt="Join Official Discord">
  </a>
</p>

<hr>

<h2>📜 Credits &amp; Modpack Permissions</h2>

<table>
  <thead>
    <tr>
      <th>Property</th>
      <th>Information</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td><strong>Creator / Author</strong></td>
      <td><strong>Dasik</strong> (Rifaditya)</td>
    </tr>
    <tr>
      <td><strong>Community</strong></td>
      <td><a href="https://discord.gg/EV99bgAFqb" target="_blank" rel="noopener">Official Discord</a></td>
    </tr>
    <tr>
      <td><strong>Collection</strong></td>
      <td><a href="https://www.curseforge.com/members/dasikigaijin/projects">Instant Gratification</a></td>
    </tr>
    <tr>
      <td><strong>License</strong></td>
      <td><a href="https://www.gnu.org/licenses/gpl-3.0.html">GNU General Public License v3.0 (GPLv3)</a></td>
    </tr>
    <tr>
      <td><strong>Source Code</strong></td>
      <td><a href="https://github.com/Rifaditya/Instant-Gratification-Ore-Amplifier">GitHub - Rifaditya/Instant-Gratification-Ore-Amplifier</a></td>
    </tr>
    <tr>
      <td><strong>Issue Tracker</strong></td>
      <td><a href="https://github.com/Rifaditya/Instant-Gratification-Ore-Amplifier/issues">GitHub Issues</a></td>
    </tr>
    <tr>
      <td><strong>Documentation / Wiki</strong></td>
      <td><a href="https://github.com/Rifaditya/Instant-Gratification-Ore-Amplifier/wiki">GitHub Wiki</a></td>
    </tr>
  </tbody>
</table>

<blockquote>
  <p><strong>📦 Modpack Permissions &amp; Distribution:</strong><br>
  You are fully welcome to include this mod in any modpack on any platform! However, the mod file must be downloaded directly through official distribution channels (<strong>CurseForge</strong> or <strong>Modrinth</strong>). Re-uploading, mirroring, or redistributing the original mod JAR to third-party mirror sites, scraper portals, or unauthorized launchers is strictly prohibited.</p>
  <p><strong>⚖️ License &amp; Fork Guidelines (No Zero-Change Re-uploads):</strong><br>
  This project is open-source under the <strong>GNU GPLv3</strong>. You are fully encouraged to inspect the code, learn from it, and fork the repository to create genuine modifications, substantial feature expansions, or community ports&mdash;provided your project remains open-source under GPLv3 with proper attribution.<br>
  <strong>However, straight 1:1 re-uploads, clone forks with no meaningful functional changes, or re-publishing identical builds under different project names (e.g. to farm downloads or rewards) are strictly forbidden.</strong></p>
</blockquote>

<hr>

<p align="center">
  <strong>Made with ❤️ for the Minecraft community</strong><br>
  <em>Part of the Instant Gratification Collection</em>
</p>
