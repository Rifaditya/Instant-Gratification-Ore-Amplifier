🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

---
# ⛏️ 礦石增幅器 (Ore Amplifier)

> **倍增原版與模組礦石的世界生成數量（礦脈數量與礦脈體積）。**
> 支援透過遊戲規則（GameRules）、遊戲內指令、YACL 圖形化設定介面及 JSON 設定檔案進行完整設定。
> 本模組屬於 **Dasik (Rifaditya)** 開發的 **即時滿足系列 (Instant Gratification Collection)**。

---

| 屬性 | 詳細資訊 |
| :--- | :--- |
| **模組 ID** | `ore-amplifier` |
| **當前版本** | `1.3.0+26.2` |
| **支援 Minecraft 版本** | 26.2+ |
| **運行環境** | 客戶端與伺服器端 (`*`) |
| **模組載入器** | Fabric (需 Fabric API) |
| **必要依賴** | [Dasik Library](https://modrinth.com/mod/dasik-library) (≥1.8.2), Fabric API |
| **可選依賴** | [YACL v3](https://modrinth.com/mod/yacl), [Mod Menu](https://modrinth.com/mod/modmenu) |
| **開源協議** | GNU General Public License v3.0 (GPLv3) |
| **作者** | Dasik (Rifaditya) |

---

## ✨ 核心特色

### 🔢 礦脈生成數量倍增 (Vein Count Multiplication)
成倍提升每個區塊中生成的礦脈數量。原版礦石預設 **2×** (200%)，模組礦石預設 **1.2×** (120%)。支援全域或單一礦石在 1% 至 1000% 之間自由調節。

### 📐 礦脈體積大小倍增 (Vein Size Amplification)
支援將單條礦脈中的方塊體積從 100% 提升至最高 500%。例如通常生成 8 顆鑽石的礦脈可增幅生成多達 40 顆。

### 🎯 單一礦石獨立精準調控 (Per-Ore Control)
可透過遊戲規則、指令、YACL GUI 或 JSON 設定為特定礦石單獨設定倍率（如鑽石 5×、鐵礦 1×）。所有設定管道保持雙向即時同步。

### 🚫 黑名單過濾機制 (Blacklist System)
透過資料驅動的通用標籤（`#c:ore_amplifier_blacklist`）排除特定結構或特徵，極為適合整合包作者進行深度客製化。

---

## 📖 Wiki 導覽

### 玩家指南
- [[礦石檢測與增幅機制|zh_tw-Ore-Detection-and-Amplification]] — 核心運作機制、黑名單系統與隨機數捨入數學原理
- [[遊戲規則與配置|zh_tw-GameRules-and-Configuration]] — 完整 GameRules 列表、YACL GUI、JSON 設定模式與解析層級
- [[指令參考|zh_tw-Commands]] — `/oreamp` 指令完整樹狀圖與權限說明

### 技術與開發
- [[架構與Mixin機制|zh_tw-Architecture-and-Mixins]] — 程式碼套件結構、6 大 Mixin 注入點詳解與 DasikLibrary 連動
- [[開發者環境與構建|zh_tw-Developer-Setup-and-Building]] — JDK 25、Gradle 編譯、JUnit 測試與資料包黑名單設定
- [[版本相容性|zh_tw-Version-Compatibility]] — 12 個歷史歸檔版本演進與依賴矩陣
