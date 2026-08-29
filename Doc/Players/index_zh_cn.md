# ⛏️ 矿石增幅器 (Ore Amplifier) 玩家使用指南

欢迎使用 **Ore Amplifier**（矿石增幅器）！本模组旨在成倍提升 Minecraft 原版及第三方模组矿石的世界生成数量与矿脉体积，支持通过游戏规则、指令和可视化界面进行无缝配置。

---

## 🚀 快速上手 (Quick Start)

1. **安装**：将 `ore-amplifier` 模组 JAR 文件放入 `.minecraft/mods` 目录，确保已安装对应版本的 **Fabric Loader**、**Fabric API** 以及 **Dasik Library**。
2. **进入游戏**：在任何新创建或新探索的世界区块中，矿石生成会自动按照配置倍率倍增生效（原版默认 200% / 双倍，模组默认 120% / 1.2倍）。
3. **已有存档**：已生成的区块不会自动重新生成矿石。如需在已有世界中应用倍率，建议前往未探索的新区块，或使用 [MCA Selector](https://github.com/Querz/mcaselector) 工具重置指定区块。

---

## ⚙️ 游戏规则调节 (In-Game GameRules)

在单人游戏中按 `Esc` $\rightarrow$ `对局域网开放`（允许作弊）$\rightarrow$ `游戏规则`，滑动至 **矿石增幅器 (Ore Amplifier)** 分类：

| 游戏规则键名 | 默认值 | 作用说明 |
| :--- | :---: | :--- |
| `ig:ore_vanilla_global` | `200` | 原版所有矿石的全局基础倍率 (200 = 2.0倍) |
| `ig:ore_modded_global` | `120` | 所有第三方模组矿石的全局基础倍率 (120 = 1.2倍) |
| `ig:ore_vein_size_multiplier` | `100` | 矿脉单次生成的方块体积大小倍率 (100%–500%) |
| `ig:ore_<namespace>_<ore>` | `100` | 针对特定矿石的独立规则（若不为 100 则覆盖全局倍率） |

---

## 💬 游戏内指令 (/oreamp)

模组内置了具备自动补全功能的完整 Brigadier 指令树：

```bash
# 查看当前运行状态与全局倍率
/oreamp status

# 查询特定矿石的当前有效倍率
/oreamp get minecraft:diamond_ore

# 设置原版所有矿石全局倍率为 3 倍 (300%)
/oreamp set global vanilla 300

# 单独将钻石矿石倍率设置为 5 倍 (500%)
/oreamp set minecraft:diamond_ore 500

# 重置所有倍率回出厂默认值
/oreamp reset

# 从 config/ore-amplifier.json 重新加载配置
/oreamp reload
```

---

## 🖥️ 可视化配置界面 (YACL & ModMenu)

如果你安装了 **YACL v3** 和 **Mod Menu** 模组，可以在客户端主菜单通过 `模组` $\rightarrow$ `Ore Amplifier` $\rightarrow$ `配置` 打开可视化界面：
- **常规设置**：滑动调整原版全局倍率、模组全局倍率与矿脉体积大小。
- **单矿石独立倍率**：可视化微调 18 种原版矿石的专属生成倍率（0%–1000%）。
