# 指令參考 (/oreamp Commands)

| 屬性 | 詳細資訊 |
| :--- | :--- |
| **根指令** | `/oreamp` |
| **Java 實作類別** | `OreCommand.java` |

---

## 🌲 指令清單

- `/oreamp status` — 查看目前各全域倍率與覆蓋項狀態
- `/oreamp get global` — 查詢原版/模組/體積全域倍率
- `/oreamp get <ore_id>` — 查詢指定礦石的實際生效倍率
- `/oreamp set global vanilla <value>` — 設定原版全域倍率 (1-1000)
- `/oreamp set <ore_id> <value>` — 設定指定礦石倍率 (0-1000)
- `/oreamp reload` — 從磁碟重載 config/ore-amplifier.json
- `/oreamp reset` — 重設所有倍率回出廠預設
