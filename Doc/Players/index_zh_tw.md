# ⛏️ 礦石增幅器 (Ore Amplifier) 玩家使用指南

歡迎使用 **Ore Amplifier**（礦石增幅器）！本模組旨在成倍提升 Minecraft 原版及第三方模組礦石的世界生成數量與礦脈體積，支援透過遊戲規則、指令和視覺化介面進行無縫設定。

---

## 🚀 快速上手 (Quick Start)

1. **安裝**：將 `ore-amplifier` 模組 JAR 檔案放入 `.minecraft/mods` 目錄，確保已安裝對應版本的 **Fabric Loader**、**Fabric API** 以及 **Dasik Library**。
2. **進入遊戲**：在任何新創建或新探索的世界區塊中，礦石生成會自動按照設定倍率倍增生效（原版預設 200% / 雙倍，模組預設 120% / 1.2倍）。
3. **已有存檔**：已生成的區塊不會自動重新生成礦石。如需在已有世界中套用倍率，建議前往未探索的新區塊，或使用 [MCA Selector](https://github.com/Querz/mcaselector) 工具重置指定區塊。

---

## ⚙️ 遊戲規則調節 (In-Game GameRules)

在單人遊戲中按 `Esc` $\rightarrow$ `對區域網路開放`（允許作弊）$\rightarrow$ `遊戲規則`，滑動至 **礦石增幅器 (Ore Amplifier)** 分類：

| 遊戲規則鍵名 | 預設值 | 作用說明 |
| :--- | :---: | :--- |
| `ig:ore_vanilla_global` | `200` | 原版所有礦石的全域基礎倍率 (200 = 2.0倍) |
| `ig:ore_modded_global` | `120` | 所有第三方模組礦石的全域基礎倍率 (120 = 1.2倍) |
| `ig:ore_vein_size_multiplier` | `100` | 礦脈單次生成的方塊體積大小倍率 (100%–500%) |
| `ig:ore_<namespace>_<ore>` | `100` | 針對特定礦石的獨立規則（若不為 100 則覆蓋全域倍率） |

---

## 💬 遊戲內指令 (/oreamp)

模組內建了具備自動補全功能的完整 Brigadier 指令樹：

```bash
# 查看當前運行狀態與全域倍率
/oreamp status

# 查詢特定礦石的當前有效倍率
/oreamp get minecraft:diamond_ore

# 設定原版所有礦石全域倍率為 3 倍 (300%)
/oreamp set global vanilla 300

# 單獨將鑽石礦石倍率設定為 5 倍 (500%)
/oreamp set minecraft:diamond_ore 500

# 重設所有倍率回出廠預設值
/oreamp reset

# 從 config/ore-amplifier.json 重新載入設定
/oreamp reload
```

---

## 🖥️ 視覺化設定介面 (YACL & ModMenu)

如果你安裝了 **YACL v3** 和 **Mod Menu** 模組，可以在客戶端主選單透過 `模組` $\rightarrow$ `Ore Amplifier` $\rightarrow$ `設定` 開啟視覺化介面：
- **一般設定**：滑動調整原版全域倍率、模組全域倍率與礦脈體積大小。
- **單一礦石獨立倍率**：視覺化微調 18 種原版礦石的專屬生成倍率（0%–1000%）。
