# Architecture & Symbol Index: Ore Amplifier

## 1. Mod Metadata & Entrypoint
- **Mod ID**: `ore-amplifier`
- **Main Entrypoint**: `net.instantgratification.oreamplifier.OreAmplifierFabric` (`net.fabricmc.api.ModInitializer`)
- **Client Entrypoint**: `net.instantgratification.oreamplifier.OreAmplifierClient`

## 2. Bytecode Mixin Target Registry
| Target Vanilla Class | Mixin Class | Purpose |
| :--- | :--- | :--- |
| `Vanilla Class` | `net.instantgratification.oreamplifier.mixin.RepeatingPlacementMixin` | Core mixin hook |
| `Vanilla Class` | `net.instantgratification.oreamplifier.mixin.RarityFilterMixin` | Core mixin hook |
| `Vanilla Class` | `net.instantgratification.oreamplifier.mixin.OreFeatureMixin` | Core mixin hook |

## 3. Core Mechanics & Subsystems
- **Source Root**: `src/main/java/`
- **Resource Root**: `src/main/resources/`

## 4. Dynamic GameRules & Commands
- **GameRules / Commands**: Configured dynamically via namespaced keys (`ore-amplifier:*`).

## 5. Configuration & Sidedness Isolation
- **Sidedness**: Server-safe logic in main, client isolated in `src/client/java` or client entrypoint.
