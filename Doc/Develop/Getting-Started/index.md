# Getting Started

## Requirements

- **JDK**: Java 25
- **Minecraft**: 26.1 Snapshot 8
- **Fabric Loader**: 0.16.9+

## Build Setup

The project uses a standard Fabric example mod structure.

### 1. Clone & Setup

```bash
git clone <repo>
cd ore-amplifier
```

### 2. Build

```bash
./gradlew build
```

The output JAR will be in `build/libs/`.

### 3. Run Client

```bash
./gradlew runClient
```

## Project Structure

- `src/main/java`: Source code
  - `OreAmplifierFabric`: Entrypoint & Registry Logic
  - `OreLogic`: Helper logic for multipliers
  - `mixin`: Mixins for World Gen hooks
- `src/main/resources`: Assets & Data
  - `fabric.mod.json`: Metadata
  - `lang/en_us.json`: Localization
