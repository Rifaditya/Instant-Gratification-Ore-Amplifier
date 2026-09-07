# ⛏️ Ore Amplifier (鉱石増幅器) プレイヤー向けガイド

**Ore Amplifier** へようこそ！このMODは、Minecraftのバニラ鉱石およびMOD鉱石のワールド生成（鉱脈数と鉱脈サイズ）を倍増させ、ゲームルール、コマンド、グラフィカル設定画面から直感的にカスタマイズできる機能を提供します。

---

## 🚀 クイックスタート (Quick Start)

1. **導入**：`ore-amplifier` のJARファイルを `.minecraft/mods` フォルダに配置します。前提MODである **Fabric Loader**、**Fabric API**、**Dasik Library** が導入されていることを確認してください。
2. **プレイ**：新しく生成・探索されるすべてのチャンクで、設定された倍率に従って鉱石が自動的に倍増して生成されます（デフォルト設定：バニラ鉱石 **200% / 2.0倍**、MOD鉱石 **120% / 1.2倍**）。
3. **既存ワールド**：すでに生成済みのチャンクは自動的には再生成されません。倍率を反映させるには、未探索の新しいチャンクへ移動するか、[MCA Selector](https://github.com/Querz/mcaselector) などのツールで対象チャンクをリセットしてください。

---

## ⚙️ ゲームルール設定 (In-Game GameRules)

シングルプレイで `Esc` $\rightarrow$ `LANに公開`（チート許可）$\rightarrow$ `ゲームルール` を開き、**鉱石増幅器 (Ore Amplifier)** カテゴリまでスクロールします：

| ゲームルール名 | デフォルト値 | 説明 |
| :--- | :---: | :--- |
| `ig:ore_vanilla_global` | `200` | すべてのバニラ鉱石の基本生成倍率 (200 = 2.0倍) |
| `ig:ore_modded_global` | `120` | 他MOD鉱石の基本生成倍率 (120 = 1.2倍) |
| `ig:ore_vein_size_multiplier` | `100` | 鉱脈1つあたりのブロック体積サイズ倍率 (100%–500%) |
| `ig:ore_<namespace>_<ore>` | `100` | 特定鉱石の個別ルール（100以外の場合、全体倍率を上書き） |

---

## 💬 ゲーム内コマンド (/oreamp)

本MODにはオートコンプリート対応の Brigadier コマンドが用意されています：

```bash
# 現在の動作ステータスとアクティブ倍率を表示
/oreamp status

# 特定鉱石の有効倍率を確認
/oreamp get minecraft:diamond_ore

# バニラ鉱石の全体倍率を 3.0倍 (300%) に設定
/oreamp set global vanilla 300

# ダイヤモンド鉱石の倍率を単独で 5.0倍 (500%) に設定
/oreamp set minecraft:diamond_ore 500

# すべての設定をデフォルト値にリセット
/oreamp reset

# config/ore-amplifier.json から設定を再読み込み
/oreamp reload
```

---

## 🖥️ YACL & ModMenu 設定画面

**YACL v3** と **Mod Menu** が導入されている場合、クライアントのMOD一覧からグラフィカル設定画面を開けます（`MOD` $\rightarrow$ `Ore Amplifier` $\rightarrow$ `設定`）：
- **一般設定**：全体倍率と鉱脈サイズのスライダー調整。
- **個別鉱石設定**：18種類のバニラ鉱石の生成倍率を 0%～1000% で視覚的にスライダー調整。
