# Architecture and Mixins

| Property | Details |
| :--- | :--- |
| **Mod ID** | `ore-amplifier` |
| **Package Root** | `net.instantgratification.oreamplifier` |
| **Source Files** | 14 Java classes + 1 JUnit test |
| **Active Mixins** | 4 (+ 2 legacy stubs) |
| **Required Dependency** | [Dasik Library](https://modrinth.com/mod/dasik-library) (≥1.8.2) |
| **Mixin Compatibility** | `JAVA_25` |
| **Mixin Config** | `ore-amplifier.mixins.json` |

---

## Package Architecture

```
net.instantgratification.oreamplifier/
├── OreAmplifierFabric.java          ModInitializer — core entrypoint
│                                    Registers GameRules, lifecycle hooks, commands
├── OreAmplifierClient.java          ClientModInitializer — dynamic translation injection
│                                    Builds Title Case GameRule display names
├── OreAmplifierFabricClient.java    ClientModInitializer — stub (no-op)
├── OreLogic.java                    Utility — ore detection, multiplier lookup,
│                                    blacklist gating, stochastic calculation
├── OreAmplifierConfig.java          JSON config model (config/ore-amplifier.json)
│                                    Backed by DasikLibrary ConfigHelper
├── OreCommand.java                  Brigadier /oreamp command suite
│                                    Tab-completion, 2-way sync
├── ModMenuIntegration.java          ModMenuApi — safe reflection YACL screen factory
│                                    Class.forName prevents server classloader crashes
├── YaclScreenHelper.java            YACL v3 GUI builder — General + Per-Ore categories
│                                    18 vanilla ore sliders (0–1000%)
├── mixin/
│   ├── RepeatingPlacementMixin      ⚡ ACTIVE — Vein count amplification
│   ├── RarityFilterMixin            ⚡ ACTIVE — Rarity probability amplification
│   ├── OreFeatureMixin              ⚡ ACTIVE — Vein size amplification
│   ├── ClientLanguageMixin          ⚡ ACTIVE — Client translation injection
│   ├── CountPlacementModifierMixin  💤 STUB — Legacy (logic migrated)
│   └── PlacedFeatureMixin           💤 STUB — Legacy (logic migrated)
└── util/
    └── ModVersionGuard.java         Zero-dep Knot ClassLoader guard
                                     Checks: Feature.class at runtime
```

---

## Mixin Breakdown

### Worldgen Pipeline Interception

The mod intercepts Minecraft's worldgen placement pipeline at three distinct points to amplify ore generation:

```
World Chunk Generation
  │
  ▼
PlacedFeature.place()
  │
  ├──► RepeatingPlacement.getPositions()
  │       │
  │       └──► count(random, pos) ◄── [REDIRECTED by RepeatingPlacementMixin]
  │              Multiplies vein count using OreLogic.getMultiplier()
  │              + StochasticUtil.getAmplifiedCount()
  │
  ├──► RarityFilter.shouldPlace()  ◄── [INJECTED by RarityFilterMixin]
  │       Amplifies rare ore probability using
  │       StochasticUtil.getAmplifiedProbability()
  │
  └──► OreFeature.place()
          │
          └──► OreConfiguration.size ◄── [REDIRECTED by OreFeatureMixin]
                 Multiplies vein block volume:
                 Math.round(originalSize × (multiplier / 100.0f))
```

### Complete Mixin Reference Table

| # | Mixin Class | Target Class | Injection | Target Method | Status | Purpose |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| 1 | `RepeatingPlacementMixin` | `RepeatingPlacement` | `@Redirect` | `count()` in `getPositions()` | ⚡ Active | Vein count multiplication |
| 2 | `RarityFilterMixin` | `RarityFilter` | `@Inject` (HEAD, cancellable) | `shouldPlace()` | ⚡ Active | Rarity probability amplification |
| 3 | `OreFeatureMixin` | `OreFeature` | `@Redirect` | `OreConfiguration.size` in `place()` | ⚡ Active | Vein size multiplication |
| 4 | `ClientLanguageMixin` | `ClientLanguage` | `@Redirect` | `Map.copyOf()` in `loadFrom()` | ⚡ Active | Dynamic translation injection |
| 5 | `CountPlacementModifierMixin` | `CountPlacement` | — | — | 💤 Stub | Legacy (migrated to #1) |
| 6 | `PlacedFeatureMixin` | `PlacedFeature` | — | — | 💤 Stub | Legacy (migrated to #1) |

---

## Mixin Detail: RepeatingPlacementMixin

The core amplification mixin. Intercepts `RepeatingPlacement.getPositions()` by redirecting the `count()` call:

1. Resolves the top-level `PlacedFeature` from the `PlacementContext`
2. Extracts the feature's `Identifier` (e.g., `minecraft:ore_iron_upper`)
3. Checks `OreLogic.shouldAmplify(id)` — applies blacklist filtering and ore name heuristics
4. Queries `OreLogic.getMultiplier(id, gameRules)` — 3-tier resolution hierarchy
5. Calculates amplified count via `StochasticUtil.getAmplifiedCount(original, multiplier%, random)`
6. **Safety**: If `rawCount > 100,000`, throws `ReportedException` with diagnostic crash report

---

## Mixin Detail: RarityFilterMixin

Handles ores that use chance-based placement (e.g., emerald ore with `chance: 6`):

1. Injects at `HEAD` of `shouldPlace()` (cancellable)
2. Converts base probability: $p_{\text{base}} = \frac{1.0}{\text{chance}}$
3. Amplifies using `StochasticUtil.getAmplifiedProbability(pBase, multiplier, random)`
4. Sets return value to `true` (place) or `false` (skip) based on amplified probability

---

## Mixin Detail: OreFeatureMixin

Controls vein block volume (how many blocks per vein):

1. Redirects access to `OreConfiguration.size` inside `OreFeature.place()`
2. Reads `ig:ore_vein_size_multiplier` GameRule
3. Only activates when multiplier > 100%
4. Formula: $\text{amplifiedSize} = \lfloor \text{originalSize} \times \frac{\text{multiplier}}{100.0} \rceil$

---

## Mixin Detail: ClientLanguageMixin

Injects human-readable GameRule names into the client language system:

1. Redirects `Map.copyOf()` in `ClientLanguage.loadFrom()`
2. Before the language map is frozen to an immutable copy, inserts dynamically generated translations
3. `OreAmplifierClient.collectTranslations()` converts identifiers to Title Case display names
4. Example: `ig:ore_minecraft_iron_ore` → "Minecraft Iron Ore Multiplier"

---

## DasikLibrary Integration

Ore Amplifier depends on [Dasik Library](https://modrinth.com/mod/dasik-library) (≥1.8.2) for the following shared utilities:

| DasikLibrary Utility | Usage in Ore Amplifier |
| :--- | :--- |
| `DynamicGameRuleManager` | Registers namespaced GameRules (`ig:ore_*`) with unfrozen `MappedRegistry` |
| `ConfigHelper` | Loads/saves `config/ore-amplifier.json` with version migration support |
| `StochasticUtil.getAmplifiedCount()` | Probabilistic integer rounding for fractional multipliers |
| `StochasticUtil.getAmplifiedProbability()` | Amplified chance-based placement probability |

---

## ModVersionGuard

The `ModVersionGuard` class performs a zero-dependency Knot ClassLoader check at startup:

```java
ModVersionGuard.checkClass(
    "Ore Amplifier",
    "net.minecraft.world.level.levelgen.feature.Feature"
);
```

- Uses `Thread.currentThread().getContextClassLoader()` for Knot ClassLoader resolution
- If the target class is not found, prints a detailed warning banner and halts mod initialization
- Prevents cryptic `NoClassDefFoundError` crashes when loaded on incompatible Minecraft versions

---

## Client-Side Safety: ModMenu + YACL Reflection Isolation

`ModMenuIntegration` uses `Class.forName("dev.isxander.yacl3....")` to safely check for YACL v3 availability:

- If YACL v3 is present: Opens `YaclScreenHelper.createScreen()` config GUI
- If YACL v3 is absent: Returns `null` (no crash, no config screen)
- This reflection barrier prevents `ClassNotFoundException` on dedicated servers or clients without YACL
