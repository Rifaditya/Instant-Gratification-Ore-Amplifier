# 광물 감지 및 생성 증폭 메커니즘 (Ore Detection & Amplification)

| 속성 | 세부 정보 |
| :--- | :--- |
| **핵심 로직 클래스** | `OreLogic.java` |
| **블랙리스트 태그** | `#c:ore_amplifier_blacklist`, `#ore-amplifier:blacklist` |

$$\text{amplifiedCount} = \text{StochasticUtil.getAmplifiedCount}(\text{original}, \text{multiplier\%}, \text{random})$$

$$\text{amplifiedSize} = \left\lfloor \text{originalSize} \times \frac{\text{multiplier}}{100.0} \right\rceil$$
