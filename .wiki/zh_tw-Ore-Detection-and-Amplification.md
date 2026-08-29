# 礦石檢測與增幅機制 (Ore Detection & Amplification)

| 屬性 | 詳細資訊 |
| :--- | :--- |
| **核心邏輯類別** | `OreLogic.java` |
| **增幅 Mixin** | `RepeatingPlacementMixin`, `RarityFilterMixin`, `OreFeatureMixin` |
| **黑名單標籤** | `#c:ore_amplifier_blacklist`, `#ore-amplifier:blacklist` |
| **靜態黑名單** | `amethyst_geode`, `monster_room`, `monster_room_deep` |
| **隨機數數學引擎** | `StochasticUtil` (DasikLibrary) |
| **核心控制遊戲規則** | `ig:ore_vanilla_global`, `ig:ore_modded_global`, `ig:ore_vein_size_multiplier` |

---

## 🔍 礦石檢測流程

在世界地形生成期間，Ore Amplifier 透過解析特徵（Feature）與方塊識別碼來動態判斷礦石。

1. **黑名單過濾**：優先檢查 `#c:ore_amplifier_blacklist` 與靜態地牢/紫晶洞。
2. **名稱比對**：比對 `_ore`、`ore_`、`ore`、`debris` 模式。
3. **倍率解析**：按照 `JSON 設定檔 > 獨立 GameRule > 全域兜底預設` 的順序決出生效倍率。

---

## 🧮 增幅數學模型

$$	ext{amplifiedCount} = 	ext{StochasticUtil.getAmplifiedCount}(	ext{original}, 	ext{multiplier\%}, 	ext{random})$$

$$	ext{amplifiedSize} = \left\lfloor 	ext{originalSize} 	imes rac{	ext{multiplier}}{100.0} 
ight
ceil$$
