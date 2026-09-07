# 鉱石の検出と増幅の仕組み (Ore Detection & Amplification)

| 項目 | 詳細 |
| :--- | :--- |
| **主要ロジッククラス** | `OreLogic.java` |
| **ブラックリストタグ** | `#c:ore_amplifier_blacklist`, `#ore-amplifier:blacklist` |

$$\text{amplifiedCount} = \text{StochasticUtil.getAmplifiedCount}(\text{original}, \text{multiplier\%}, \text{random})$$

$$\text{amplifiedSize} = \left\lfloor \text{originalSize} \times \frac{\text{multiplier}}{100.0} \right\rceil$$
