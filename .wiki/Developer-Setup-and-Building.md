# Developer Setup and Building

| Property | Details |
| :--- | :--- |
| **Java Version** | JDK 25 (`release = 25`) |
| **Build System** | Gradle 9.x with Fabric Loom 1.15+ |
| **Target Minecraft** | 26.2 |
| **Fabric Loader** | ≥0.19.1 |
| **Test Framework** | JUnit 5 |
| **Source Repository** | [GitHub](https://github.com/Rifaditya/Instant-Gratification-Ore-Amplifier) |

---

## Prerequisites

- **JDK 25** (or later) — download from [Oracle](https://www.oracle.com/java/technologies/javase-downloads.html) or [Adoptium](https://adoptium.net/)
- **Gradle 9.x** — bundled via Gradle Wrapper (`gradlew`)
- **Git** — for cloning the repository

---

## Clone and Build

```bash
git clone https://github.com/Rifaditya/Instant-Gratification-Ore-Amplifier.git
cd Instant-Gratification-Ore-Amplifier/ore-amplifier-v2
./gradlew build --no-daemon
```

The compiled JAR will be output to `build/libs/ore-amplifier-<version>.jar`.

---

## Run Development Client

```bash
./gradlew runClient --no-daemon
```

Launches a Minecraft client with the mod and all dependencies loaded. Useful for testing GameRules, YACL GUI, and in-game commands.

---

## Run Development Server

```bash
./gradlew runServer --no-daemon
```

Launches a dedicated server instance for testing server-side worldgen amplification, GameRule syncing, and command permissions.

---

## Running Automated Tests

```bash
./gradlew test --no-daemon
```

The test suite (`src/test/java/`) uses **JUnit 5** and verifies core mod logic without a full Minecraft runtime:

### Test Methods

| Test Method | What It Verifies |
| :--- | :--- |
| `testVanillaOreIdentification()` | `OreLogic.shouldAmplify()` correctly identifies vanilla ores: `iron_ore`, `deepslate_coal_ore`, `nether_quartz_ore`, `ancient_debris` → `true` |
| `testBlacklistFiltering()` | Blacklisted features are correctly rejected: `amethyst_geode`, `monster_room`, `dirt` → `false` |
| `testMultiplierMath()` | Multiplier math: 200% vanilla doubles count (5 → 10), 120% modded scales (10 → 12) |

The test bootstraps `SharedConstants.tryDetectVersion()` and `Bootstrap.bootStrap()` before executing test methods.

---

## Project Directory Structure

```
ore-amplifier-v2/
├── src/
│   ├── main/
│   │   ├── java/net/instantgratification/oreamplifier/
│   │   │   ├── OreAmplifierFabric.java       # Main entrypoint
│   │   │   ├── OreAmplifierClient.java       # Client entrypoint
│   │   │   ├── OreLogic.java                 # Core ore detection & math
│   │   │   ├── OreAmplifierConfig.java       # JSON config model
│   │   │   ├── OreCommand.java               # /oreamp commands
│   │   │   ├── ModMenuIntegration.java       # ModMenu screen factory
│   │   │   ├── YaclScreenHelper.java         # YACL GUI builder
│   │   │   ├── mixin/                        # 6 Mixin classes
│   │   │   └── util/ModVersionGuard.java     # ClassLoader guard
│   │   └── resources/
│   │       ├── fabric.mod.json               # Mod manifest
│   │       ├── ore-amplifier.mixins.json     # Mixin registry
│   │       ├── assets/ore-amplifier/
│   │       │   ├── icon.png                  # Mod icon
│   │       │   └── lang/en_us.json           # Translations
│   │       └── data/
│   │           ├── c/tags/worldgen/placed_feature/
│   │           │   └── ore_amplifier_blacklist.json
│   │           └── ore-amplifier/tags/worldgen/placed_feature/
│   │               └── blacklist.json
│   └── test/
│       └── java/.../test/OreAmplifierTest.java
├── build.gradle
├── gradle.properties
├── CHANGELOG.md
├── RELEASE_QUEUE.md
└── LICENSE
```

---

## Adding Custom Ore Blacklist Entries (Datapack Guide)

Modpack creators and addon developers can exclude specific worldgen features from amplification by adding entries to the convention blacklist tag.

### Step 1: Create the Datapack Structure

```
my_datapack/
└── data/
    └── c/
        └── tags/
            └── worldgen/
                └── placed_feature/
                    └── ore_amplifier_blacklist.json
```

### Step 2: Add Feature Identifiers

```json
{
  "replace": false,
  "values": [
    "mymod:custom_crystal_vein",
    "mymod:rare_gem_placement"
  ]
}
```

Setting `"replace": false` ensures your entries are **appended** to the existing blacklist without removing the default entries (`amethyst_geode`, `monster_room`, `monster_room_deep`).

### Step 3: Apply the Datapack

Place the datapack in your world's `datapacks/` folder. The blacklist will take effect on the next world load — newly generated chunks will skip amplification for the listed features.

### Alternative: Mod-Specific Tag

You can also use the mod-specific tag namespace:

```
data/ore-amplifier/tags/worldgen/placed_feature/blacklist.json
```

Both `#c:ore_amplifier_blacklist` and `#ore-amplifier:blacklist` are checked during ore detection.

---

## Extending with Addon Mods

Addon mods can interact with Ore Amplifier's systems:

1. **Blacklist Tags**: Register entries in `#c:ore_amplifier_blacklist` to exclude custom features
2. **Dynamic GameRules**: Ore Amplifier auto-detects modded ores and registers `ig:ore_<namespace>_<path>` GameRules for each detected ore block
3. **JSON Config Overrides**: Set per-ore multipliers in `config/ore-amplifier.json` under `perOreMultipliers`

For more details on the internal architecture, see [[Architecture and Mixins|Architecture-and-Mixins]].
