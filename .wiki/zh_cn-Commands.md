# 指令参考 (/oreamp Commands)

| 属性 | 详细信息 |
| :--- | :--- |
| **根指令** | `/oreamp` |
| **Java 实现类** | `OreCommand.java` |
| **权限等级** | 查询类：0 级（所有玩家）；修改类：2 级（管理员/作弊开启） |
| **自动补全** | 支持对矿石标识符的动态方块提示 |

---

## 🌲 指令树状图

```
/oreamp
├── help                              (权限: 0) — 查看指令帮助
├── status                            (权限: 0) — 查看当前各全局倍率与覆盖项状态
├── get
│   ├── global                        (权限: 0) — 查询原版/模组/体积全局倍率
│   └── <ore_id>                      (权限: 0) — 查询指定矿石的实际生效倍率与解析来源
└── set
    ├── global
    │   ├── vanilla <value>           (权限: 2) — 设置原版全局倍率 (1-1000)
    │   └── modded <value>            (权限: 2) — 设置模组全局倍率 (1-1000)
    ├── <ore_id> <value>              (权限: 2) — 设置指定矿石倍率 (0-1000)
    ├── reset                         (权限: 2) — 重置所有倍率与配置回出厂默认
    └── reload                        (权限: 2) — 从磁盘重载 config/ore-amplifier.json
```

---

## 💡 常用指令范例

```bash
# 查询当前模组全局运行状态
/oreamp status

# 将钻石矿石倍率提升为 5 倍 (500%)
/oreamp set minecraft:diamond_ore 500

# 将原版所有矿石全局倍率调整为 3 倍 (300%)
/oreamp set global vanilla 300

# 从配置文件重载设定
/oreamp reload
```
