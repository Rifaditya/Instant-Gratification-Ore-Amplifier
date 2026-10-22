# Architecture & Symbol Index: Ore Amplifier (MC 1.20.1)

## 1. Mod Metadata & Entrypoint
- **Mod ID**: `ore-amplifier`
- **Target Minecraft Version**: `1.20.1` (Java 17)
- **Mod Version**: `1.3.21+1.20.1`
- **Main Entrypoint**: `net.instantgratification.oreamplifier.OreAmplifierFabric` (`net.fabricmc.api.ModInitializer`)
- **Client Entrypoint**: `net.instantgratification.oreamplifier.OreAmplifierClient` (`net.fabricmc.api.ClientModInitializer`)
- **ModMenu Integration**: `net.instantgratification.oreamplifier.ModMenuIntegration` (`com.terraformersmc.modmenu.api.ModMenuApi`)

## 2. Bytecode Mixin Target Registry
| Target Vanilla Class | Mixin Class | Purpose |
| :--- | :--- | :--- |
| `net.minecraft.world.level.levelgen.placement.RepeatingPlacement` | `net.instantgratification.oreamplifier.mixin.RepeatingPlacementMixin` | Intercepts `count(...)` via `@ModifyExpressionValue` to amplify placement attempts |
| `net.minecraft.world.level.levelgen.placement.RarityFilter` | `net.instantgratification.oreamplifier.mixin.RarityFilterMixin` | Modulates `shouldPlace(...)` via `@Inject` for rare ore probability amplification |
| `net.minecraft.world.level.levelgen.feature.OreFeature` | `net.minecraft.world.level.levelgen.feature.OreFeatureMixin` | Intercepts `OreConfiguration.size` via `@ModifyExpressionValue` to scale vein block count |

## 3. Core Mechanics & Subsystems
- **Configuration Framework**: `OreAmplifierConfig` backed by DasikLibrary's `ConfigHelper` with sparse delta persistence.
- **Dynamic GameRules**: Dynamic integer rules registered per detected ore and global category `DynamicGameRuleManager`.
- **Command Dispatcher**: `/oreamp` Brigadier suite in `OreCommand` supporting help, status, get, set, reset, reload.

## 4. Verification & Testing
- `OreAmplifierTest`: Unit tests for vanilla ore heuristics, blacklist, stochastic rounding math, sparse delta pruning, and `@ConfigOption` reflection.
- `OreAmplifierSidednessContractTest`: Pillar 5 verification ensuring zero client classes in common sources and GNU GPLv3 headers.
