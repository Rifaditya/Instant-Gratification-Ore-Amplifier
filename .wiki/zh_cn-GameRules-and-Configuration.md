# 游戏规则与配置 (GameRules & Configuration)

| 属性 | 详细信息 |
| :--- | :--- |
| **游戏规则分类** | `minecraft:ore_amplifier` |
| **分类翻译键** | `gamerule.category.ore_amplifier` → "Ore Amplifier" |
| **配置文件路径** | `config/ore-amplifier.json` |
| **配置引擎** | DasikLibrary `ConfigHelper` |
| **图形界面** | YACL v3 (可选) |

---

## 📋 游戏规则完整参考表

### 静态核心规则

| 游戏规则键名 | 中文名称 | 类型 | 范围 | 默认值 | 说明 |
| :--- | :--- | :--- | :--- | :--- | :--- |
| `ig:ore_vanilla_global` | 原版矿石全局倍率 (%) | 整数 | 1–1000 | **200** | 适用于所有 `minecraft:` 命名空间矿石（200 = 2.0×） |
| `ig:ore_modded_global` | 模组矿石全局倍率 (%) | 整数 | 1–1000 | **120** | 适用于所有第三方模组矿石（120 = 1.2×） |
| `ig:ore_vein_size_multiplier` | 矿脉体积倍率 (%) | 整数 | 100–500 | **100** | 单条矿脉包含的方块总数倍率（100 = 原版体积） |

### 动态单矿石规则

系统为注册表中检测到的所有矿石方块动态生成独立规则：

| 规则命名格式 | 示例 | 类型 | 范围 | 默认值 |
| :--- | :--- | :--- | :--- | :--- |
| `ig:ore_<namespace>_<path>` | `ig:ore_minecraft_iron_ore` | 整数 | 0–1000 | **100** |
| | `ig:ore_minecraft_diamond_ore` | 整数 | 0–1000 | **100** |
| | `ig:ore_minecraft_ancient_debris` | 整数 | 0–1000 | **100** |

> 💡 将某矿石的独立规则设为 `0` 可完全关闭该矿石的增幅。

---

## 📄 JSON 配置文件格式 (`config/ore-amplifier.json`)

```json
{
  "version": 1,
  "vanillaGlobalMultiplier": 200,
  "moddedGlobalMultiplier": 120,
  "veinSizeMultiplier": 100,
  "perOreMultipliers": {
    "minecraft:diamond_ore": 500,
    "minecraft:deepslate_diamond_ore": 500,
    "minecraft:ancient_debris": 300
  }
}
```

---

## 🔄 双向实时同步体系 (2-Way Sync)

- 使用 `/oreamp set` 指令修改：**同时更新当前世界 GameRule 与 JSON 配置文件**。
- 在 YACL 图形界面或手动编辑 JSON 文件后：执行 `/oreamp reload` 可将配置**一键同步至当前运行的世界中**。
