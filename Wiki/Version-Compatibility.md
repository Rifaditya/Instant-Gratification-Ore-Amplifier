# Version Compatibility

| Property | Details |
| :--- | :--- |
| **Mod ID** | `ore-amplifier` |
| **Latest Version** | `1.3.0+26.2` |
| **Target Minecraft** | 26.2+ (open-ended `>=26.2-`) |
| **Java Version** | Java 25 (`release = 25`) |
| **Fabric Loader** | ≥0.19.1 |
| **Build System** | Fabric Loom 1.15+, Gradle 9.x |

---

## Dependency Matrix

| Dependency | Version Requirement | Type |
| :--- | :--- | :--- |
| **Minecraft** | `>=26.2-` | Required |
| **Java** | `>=25` | Required |
| **Fabric Loader** | `>=0.19.1` | Required |
| **Fabric API** | `*` (any) | Required |
| **Dasik Library** | `>=1.8.2` | Required |
| **YACL v3** | `*` (any) | Optional (client GUI) |
| **Mod Menu** | `*` (any) | Optional (config screen entry) |

---

## Version History

| Version | MC | Key Changes |
| :--- | :--- | :--- |
| **1.3.0+26.2** | 26.2 | ✨ Vein size amplification (`ig:ore_vein_size_multiplier` 100–500%), `OreFeatureMixin` |
| **1.2.1+26.2** | 26.2 | 🔄 2-way sync: Brigadier commands ↔ GameRules ↔ JSON config |
| **1.2.0+26.2** | 26.2 | 🖥️ Expanded YACL GUI with dedicated Per-Ore Multipliers category (18 vanilla ores) |
| **1.1.9+26.2** | 26.2 | 🧪 JUnit 5 automated test integration (`OreAmplifierTest`) |
| **1.1.8+26.2** | 26.2 | ⚖️ Default alignment: Vanilla 200% (2.0×), Modded 120% (1.2×) |
| **1.1.7+26.2** | 26.2 | 🐛 Tag blacklist crash fix (safe `try-catch` guard for early-init) |
| **1.1.6+26.2** | 26.2 | 🐛 Tag blacklist stability fix |
| **1.1.5+26.2** | 26.2 | ⚠️ **BROKEN** — Unguarded tag check caused world generation crash. Do not use. |
| **1.1.4+26.2** | 26.2 | 💬 Complete `/oreamp` Brigadier command suite with dynamic tab-completion |
| **1.1.3+26.2** | 26.2 | 🔧 Minor fixes and cleanup |
| **1.1.2+26.2** | 26.2 | 🔄 MC 26.2 upgrade, `ModVersionGuard` Knot ClassLoader validation |
| **1.1.1+26.1.2** | 26.1.2 | 🖥️ YACL v3 + ModMenu GUI config, JSON config template sync |
| **1.0.0** | 26.1 (snapshot) | 🎉 Initial release — dynamic ore scan, GameRule categories, `DynamicGameRuleManager` |

---

## Archived Build Artifacts

All production JAR files are preserved in `Archive Jar of all versions/`:

| File | Size | Notes |
| :--- | :--- | :--- |
| `ore-amplifier-1.1.1+26.1.2.jar` | 90.3 KB | First stable release (MC 26.1.2) |
| `ore-amplifier-1.1.2+26.2.jar` | 91.6 KB | MC 26.2 port |
| `ore-amplifier-1.1.3+26.2.jar` | 92.1 KB | |
| `ore-amplifier-1.1.4+26.2.jar` | 96.1 KB | Command suite added |
| `ore-amplifier-1.1.5+26.2.jar` | 98.8 KB | 🚨 **Critical crash bug** — do not use |
| `ore-amplifier-1.1.6+26.2.jar` | 98.8 KB | Crash fix |
| `ore-amplifier-1.1.7+26.2.jar` | 98.8 KB | Tag safety fix |
| `ore-amplifier-1.1.8+26.2.jar` | 98.8 KB | Default multiplier alignment |
| `ore-amplifier-1.1.9+26.2.jar` | 98.8 KB | JUnit 5 tests |
| `ore-amplifier-1.2.0+26.2.jar` | 100.9 KB | Per-ore YACL GUI |
| `ore-amplifier-1.2.1+26.2.jar` | 101.2 KB | 2-way sync |
| `ore-amplifier-1.3.0+26.2.jar` | 103.1 KB | Vein size amplification (**latest**) |

---

> 📌 **Repository Source Disclaimer**: The documentation in this Wiki reflects the **current source code state in the repository**, which may include recent unreleased commits or developmental features ahead of public release builds on CurseForge and Modrinth.
