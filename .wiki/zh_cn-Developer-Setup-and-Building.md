# 开发者环境与构建 (Developer Setup & Building)

| 属性 | 详细信息 |
| :--- | :--- |
| **Java 版本** | JDK 25 (`release = 25`) |
| **构建系统** | Gradle 9.x + Fabric Loom 1.15+ |
| **目标 Minecraft** | 26.2 |
| **测试框架** | JUnit 5 |
| **GitHub 仓库** | [Instant-Gratification-Ore-Amplifier](https://github.com/Rifaditya/Instant-Gratification-Ore-Amplifier) |

---

## 🛠️ 编译与运行指令

```bash
# 克隆仓库
git clone https://github.com/Rifaditya/Instant-Gratification-Ore-Amplifier.git
cd Instant-Gratification-Ore-Amplifier/ore-amplifier-v2

# 编译生成模组 JAR
./gradlew build --no-daemon

# 运行开发客户端
./gradlew runClient --no-daemon

# 运行自动化测试
./gradlew test --no-daemon
```

---

## 📦 通过数据包自定义黑名单 (Datapack Guide)

整合包作者可创建数据包排除特定生成特征：

文件路径：`data/c/tags/worldgen/placed_feature/ore_amplifier_blacklist.json`

```json
{
  "replace": false,
  "values": [
    "mymod:custom_crystal_vein",
    "mymod:rare_gem_placement"
  ]
}
```
