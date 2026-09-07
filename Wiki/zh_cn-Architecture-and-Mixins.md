# 架构与 Mixin 机制 (Architecture & Mixins)

| 属性 | 详细信息 |
| :--- | :--- |
| **模组 ID** | `ore-amplifier` |
| **根包名** | `net.instantgratification.oreamplifier` |
| **源码文件数** | 14 个 Java 类 + 1 个 JUnit 测试类 |
| **活动 Mixin** | 4 个活跃注入 (+ 2 个历史存根) |
| **字节码级别** | `JAVA_25` |
| **配置文件** | `ore-amplifier.mixins.json` |

---

## 📦 代码包结构图

```
net.instantgratification.oreamplifier/
├── OreAmplifierFabric.java          ModInitializer — 核心主入口点
├── OreAmplifierClient.java          ClientModInitializer — 客户端动态翻译注入
├── OreLogic.java                    核心业务逻辑 — 启发式检测、倍率解析、随机数计算
├── OreAmplifierConfig.java          JSON 配置模型类 (ConfigHelper 驱动)
├── OreCommand.java                  Brigadier 指令注册与双向同步逻辑
├── ModMenuIntegration.java          ModMenuApi — 安全反射隔离的配置屏构建
├── YaclScreenHelper.java            YACL v3 配置屏构建器
├── mixin/
│   ├── RepeatingPlacementMixin      ⚡ 活跃 — 拦截 count() 实现矿脉数量倍增
│   ├── RarityFilterMixin            ⚡ 活跃 — 拦截 shouldPlace() 实现稀有度几率倍增
│   ├── OreFeatureMixin              ⚡ 活跃 — 拦截 size 实现矿脉方块体积倍增
│   ├── ClientLanguageMixin          ⚡ 活跃 — 拦截 loadFrom() 实现客户端名称动态注入
│   ├── CountPlacementModifierMixin  💤 存根 — 历史逻辑已整合至 RepeatingPlacementMixin
│   └── PlacedFeatureMixin           💤 存根 — 历史逻辑已整合至 RepeatingPlacementMixin
└── util/
    └── ModVersionGuard.java         Knot ClassLoader 零依赖运行环境校验器
```

---

## ⚡ 核心 Mixin 注入原理

```
地形区块生成流水线 (Worldgen Chunk Generation)
  │
  ▼
PlacedFeature.place()
  │
  ├──► RepeatingPlacement.getPositions()
  │       └──► count() ◄── [RepeatingPlacementMixin @Redirect]
  │              调用 OreLogic.getMultiplier() + StochasticUtil.getAmplifiedCount()
  │
  ├──► RarityFilter.shouldPlace() ◄── [RarityFilterMixin @Inject HEAD]
  │       调用 StochasticUtil.getAmplifiedProbability() 动态修正稀有放置几率
  │
  └──► OreFeature.place()
          └──► OreConfiguration.size ◄── [OreFeatureMixin @Redirect]
                 当倍率 > 100 时按比例放大矿脉方块体积
```
