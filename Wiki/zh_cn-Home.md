🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

---
# ⛏️ 矿石增幅器 (Ore Amplifier)

> **倍增原版与模组矿石的世界生成数量（矿脉数量与矿脉体积）。**
> 支持通过游戏规则（GameRules）、游戏内指令、YACL 图形配置界面及 JSON 配置文件进行完整配置。
> 本模组属于 **Dasik (Rifaditya)** 开发的 **即时满足系列 (Instant Gratification Collection)**。

---

| 属性 | 详细信息 |
| :--- | :--- |
| **模组 ID** | `ore-amplifier` |
| **当前版本** | `1.3.0+26.2` |
| **支持 Minecraft 版本** | 26.2+ |
| **运行环境** | 客户端与服务端 (`*`) |
| **模组加载器** | Fabric (需 Fabric API) |
| **必要依赖** | [Dasik Library](https://modrinth.com/mod/dasik-library) (≥1.8.2), Fabric API |
| **可选依赖** | [YACL v3](https://modrinth.com/mod/yacl), [Mod Menu](https://modrinth.com/mod/modmenu) |
| **开源协议** | GNU General Public License v3.0 (GPLv3) |
| **作者** | Dasik (Rifaditya) |

---

## ✨ 核心特性

### 🔢 矿脉生成数量倍增 (Vein Count Multiplication)
成倍提升每个区块中生成的矿脉数量。原版矿石默认 **2×** (200%)，模组矿石默认 **1.2×** (120%)。支持全局或单矿石在 1% 至 1000% 之间自由调节。

### 📐 矿脉体积大小倍增 (Vein Size Amplification)
支持将单条矿脉中的方块体积从 100% 提升至最高 500%。例如通常生成 8 颗钻石的矿脉可增幅生成多达 40 颗。

### 🎯 单矿石独立精准调控 (Per-Ore Control)
可通过游戏规则、指令、YACL GUI 或 JSON 配置为特定矿石单独设定倍率（如钻石 5×、铁矿 1×）。所有配置渠道保持双向即时同步。

### 🚫 黑名单过滤机制 (Blacklist System)
通过数据驱动的通用标签（`#c:ore_amplifier_blacklist`）排除特定结构或特征，极为适合整合包作者进行深度定制。

### 🖥️ YACL 图形化配置界面
提供可视化滑动条配置界面，内置 18 种原版矿石与全局倍率调节。需安装 YACL v3 与 Mod Menu（仅客户端）。

### 💬 `/oreamp` 完整指令体系
具备动态补全功能的完整 Brigadier 指令树：`status`、`get`、`set`、`reset`、`reload`。

---

## 🚀 快速入门

1. **安装**：将模组 JAR 文件连同 **Fabric API** 和 **Dasik Library** 一同放入 `mods/` 文件夹。
2. **启动游戏**：进入世界后，新生成区块中的矿石将自动按照 **原版 2× / 模组 1.2×** 的倍率生成。
3. **自定义调节**：
   - **游戏规则界面**：按 `Esc` → `对局域网开放` → `游戏规则` → **Ore Amplifier** 分类
   - **控制台指令**：`/oreamp set global vanilla 500`（原版矿石设为 5×）
   - **单矿石指令**：`/oreamp set minecraft:diamond_ore 1000`（钻石矿石设为 10×）
4. **旧区块刷新**：推荐使用 [MCA Selector](https://github.com/Querz/mcaselector) 重新生成已探索区块以应用最新矿石倍率。

> ⚠️ **重要提示**：矿石增幅机制仅对**新生成的区块**生效，已探索生成的区块会保留原有的矿石分布。

---

## 📖 Wiki 导航

### 玩家指南
- [[矿石检测与增幅机制|zh_cn-Ore-Detection-and-Amplification]] — 核心工作机制、黑名单系统与随机数舍入数学原理
- [[游戏规则与配置|zh_cn-GameRules-and-Configuration]] — 完整 GameRules 列表、YACL GUI、JSON 配置模式与解析层级
- [[指令参考|zh_cn-Commands]] — `/oreamp` 指令完整树状图与权限说明

### 技术与开发
- [[架构与Mixin机制|zh_cn-Architecture-and-Mixins]] — 代码包结构、6 大 Mixin 注入点详解与 DasikLibrary 联动
- [[开发者环境与构建|zh_cn-Developer-Setup-and-Building]] — JDK 25、Gradle 编译、JUnit 测试与数据包黑名单配置
- [[版本兼容性|zh_cn-Version-Compatibility]] — 12 个历史归档版本演进与依赖矩阵

---

> 📌 **源码库说明**：本 Wiki 内容基于**当前代码仓库的最新源码状态**编写，可能包含领先于 CurseForge 与 Modrinth 正式发布版本的开发中特性。
