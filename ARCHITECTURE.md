# Architecture & Symbol Index: Ore Amplifier (MC 1.21.11)

## 1. Mod Metadata & Entrypoint
- **Mod ID**: `ore-amplifier`
- **Main Entrypoint**: `net.instantgratification.oreamplifier.OreAmplifierFabric` (`net.fabricmc.api.ModInitializer`)
- **Client Entrypoint**: `net.instantgratification.oreamplifier.OreAmplifierClient` (`net.fabricmc.api.ClientModInitializer`)
- **ModMenu Entrypoint**: `net.instantgratification.oreamplifier.ModMenuIntegration` (`com.terraformersmc.modmenu.api.ModMenuApi`)

## 2. Bytecode Mixin Target Registry
| Target Vanilla Class | Mixin Class | Purpose |
| :--- | :--- | :--- |
| `RepeatingPlacement` | `net.instantgratification.oreamplifier.mixin.RepeatingPlacementMixin` | Amplifies vein count via `@ModifyExpressionValue` on `count()` |
| `RarityFilter` | `net.instantgratification.oreamplifier.mixin.RarityFilterMixin` | Amplifies rare ore generation probability via `@Inject` on `shouldPlace()` |
| `OreFeature` | `net.instantgratification.oreamplifier.mixin.OreFeatureMixin` | Scales individual ore vein block volume via `@ModifyExpressionValue` on `OreConfiguration.size` |

## 3. Core Mechanics & Subsystems
- **`OreLogic`**: Core amplification calculations, heuristic and tag-based ore detection, and dynamic stochastic math fallback.
- **`OreAmplifierConfig`**: Sparse delta JSON configuration (`config/ore-amplifier.json`) integrated with DasikLibrary's `UniversalConfigScreen` and `@ConfigOption`.
- **`OreCommand`**: Full `/oreamp` Brigadier command tree (status, get, set, reload, reset).

## 4. Dynamic GameRules & Commands
- **Category**: `minecraft:ore_amplifier`
- **Globals**: `ig:ore_vanilla_global`, `ig:ore_modded_global`, `ig:ore_vein_size_multiplier`
- **Per-Ore Rules**: `ig:ore_<namespace>_<path>` dynamically registered via `DynamicGameRuleManager`

## 5. Configuration & Sidedness Isolation
- **Sidedness**: Server-safe common code; client GUI entrypoints gated via `@Environment(EnvType.CLIENT)`. Verified by Pillar 5 `OreAmplifierSidednessContractTest`.
