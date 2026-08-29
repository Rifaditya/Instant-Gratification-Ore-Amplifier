# Deteksi dan Penggandaan Bijih (Ore Detection & Amplification)

| Properti | Detail |
| :--- | :--- |
| **Kelas Logika Utama** | `OreLogic.java` |
| **Tag Blacklist** | `#c:ore_amplifier_blacklist`, `#ore-amplifier:blacklist` |

$$\text{amplifiedCount} = \text{StochasticUtil.getAmplifiedCount}(\text{original}, \text{multiplier\%}, \text{random})$$

$$\text{amplifiedSize} = \left\lfloor \text{originalSize} \times \frac{\text{multiplier}}{100.0} \right\rceil$$
