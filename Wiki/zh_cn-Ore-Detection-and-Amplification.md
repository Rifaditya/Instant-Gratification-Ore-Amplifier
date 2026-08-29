# 矿石检测与增幅机制 (Ore Detection & Amplification)

| 属性 | 详细信息 |
| :--- | :--- |
| **核心逻辑类** | `OreLogic.java` |
| **增幅 Mixin** | `RepeatingPlacementMixin`, `RarityFilterMixin`, `OreFeatureMixin` |
| **黑名单标签** | `#c:ore_amplifier_blacklist`, `#ore-amplifier:blacklist` |
| **静态黑名单** | `amethyst_geode`, `monster_room`, `monster_room_deep` |
| **随机数数学引擎** | `StochasticUtil` (DasikLibrary) |
| **核心控制游戏规则** | `ig:ore_vanilla_global`, `ig:ore_modded_global`, `ig:ore_vein_size_multiplier` |

---

## 🔍 矿石检测流程

在世界地形生成期间，Ore Amplifier 通过解析特征（Feature）与方块标识符来动态判断矿石。检测判定按照以下流水线执行：

### 步骤 1：黑名单过滤 (Blacklist Filtering)

在进行任何增幅前，系统会优先校验三个黑名单来源：
1. **通用规范标签**：`#c:ore_amplifier_blacklist`（跨模组通用标准）
2. **模组专属标签**：`#ore-amplifier:blacklist`
3. **静态硬编码黑名单**：
   - `minecraft:amethyst_geode`（紫水晶洞）
   - `minecraft:monster_room`（刷怪地牢）
   - `minecraft:monster_room_deep`（深层地牢）

> 💡 **早期初始化安全机制**：标签解析采用 `try-catch(IllegalStateException)` 防护，防止在世界加载最初阶段标签尚未绑定时发生崩溃。

### 步骤 2：启发式名称匹配 (Name Heuristics)

若未被黑名单拦截，系统将比对标识符路径：

| 匹配模式 | 示例匹配项 |
| :--- | :--- |
| `_ore`（后缀） | `iron_ore`, `deepslate_gold_ore`, `nether_quartz_ore` |
| `ore_`（前缀） | `ore_iron_upper`, `ore_diamond_large` |
| `ore`（完全匹配） | `ore` |
| `debris`（包含） | `ancient_debris`（远古残骸） |

只要符合上述任意模式，该特征即被识别为矿石并进入增幅计算。

---

## ⚖️ 三层倍率解析层级 (Resolution Hierarchy)

确定为矿石后，最终生效的倍率将由**三级优先级体系**决出：

```
┌─────────────────────────────────────────────────────┐
│  第 1 级：JSON 单矿石独立配置 (最高优先级)            │
│  config/ore-amplifier.json → perOreMultipliers      │
│  (显式用户设定，非 -1 时直接生效)                    │
├─────────────────────────────────────────────────────┤
│         ↓ (若未配置或为 -1)                         │
├─────────────────────────────────────────────────────┤
│  第 2 级：动态 GameRule 规则覆盖                    │
│  ig:ore_<namespace>_<path>                          │
│  (如 ig:ore_minecraft_iron_ore，非 100% 时生效)      │
├─────────────────────────────────────────────────────┤
│         ↓ (若为默认 100%)                           │
├─────────────────────────────────────────────────────┤
│  第 3 级：命名空间全局回退 (兜底默认)                │
│  minecraft: → ig:ore_vanilla_global (默认: 200%)    │
│  其他模组:   → ig:ore_modded_global  (默认: 120%)    │
└─────────────────────────────────────────────────────┘
```

---

## 🧮 增幅数学模型 (Amplification Math)

### 1. 矿脉数量随机数舍入 (Vein Count Stochastic Scaling)

为了平滑处理非整数倍率生成，模组采用 **随机数概率舍入算法 (Stochastic Rounding)**：

$$	ext{amplifiedCount} = 	ext{StochasticUtil.getAmplifiedCount}(	ext{original}, 	ext{multiplier\%}, 	ext{random})$$

算法流程：
1. 计算浮点放大值：$	ext{rawValue} = 	ext{original} 	imes rac{	ext{multiplier}}{100}$
2. 取整数部分：$	ext{intPart} = \lfloor 	ext{rawValue} floor$
3. 取小数余数部分：$	ext{frac} = 	ext{rawValue} - 	ext{intPart}$
4. 以 $	ext{frac}$ 的概率决定是否额外 $+1$：$	ext{result} = 	ext{intPart} + (	ext{random.nextFloat()} < 	ext{frac}\ ?\ 1 : 0)$

#### 实例计算：
- **5 条矿脉 × 200% (2.0×)** = 10.0 $ightarrow$ **100% 生成 10 条**
- **1 条矿脉 × 120% (1.2×)** = 1.2 $ightarrow$ **80% 生成 1 条，20% 生成 2 条**
- **3 条矿脉 × 150% (1.5×)** = 4.5 $ightarrow$ **50% 生成 4 条，50% 生成 5 条**

---

### 2. 矿脉体积倍增 (Vein Size Amplification)

通过 `ig:ore_vein_size_multiplier` 游戏规则控制单条矿脉包含的方块总数：

$$	ext{amplifiedSize} = \left\lfloor 	ext{originalSize} 	imes rac{	ext{multiplier}}{100.0} ightceil$$

> ⚠️ 矿脉体积倍增仅在倍率 **> 100** 时生效。默认 100% 时不改变方块大小。

---

### 3. 稀有度概率修正 (Rarity Filter Amplification)

针对采用几率放置过滤的矿石（如每 6 次尝试成功 1 次的绿宝石矿），`RarityFilterMixin` 修正基础概率：

$$p_{	ext{base}} = rac{1.0}{	ext{chance}}, \quad p_{	ext{amplified}} = 	ext{StochasticUtil.getAmplifiedProbability}(p_{	ext{base}}, 	ext{multiplier}, 	ext{random})$$

---

## 💎 与时运魔咒 (Fortune) 的乘算叠加

Ore Amplifier 与原版时运魔咒作用于**完全不同的层级**，二者效果**乘算叠加**：

- **Ore Amplifier**：在**地形生成阶段**决定矿脉数量与每脉方块数。
- **时运魔咒**：在**玩家挖掘阶段**决定每个方块掉落的物品数。

---

## 🛡️ 防雪崩崩溃诊断保护 (Safety Threshold)

若任何单项特征计算出的单次放置数量超过 **100,000**，模组将主动触发 `ReportedException` 诊断报告，防止因极端数值组合导致服务端无响应或内存耗尽。
