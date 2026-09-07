# Detecção e amplificação de minérios (Ore Detection & Amplification)

| Propriedade | Detalhes |
| :--- | :--- |
| **Classe de Lógica** | `OreLogic.java` |
| **Tags de Lista Negra** | `#c:ore_amplifier_blacklist`, `#ore-amplifier:blacklist` |

$$\text{amplifiedCount} = \text{StochasticUtil.getAmplifiedCount}(\text{original}, \text{multiplier\%}, \text{random})$$

$$\text{amplifiedSize} = \left\lfloor \text{originalSize} \times \frac{\text{multiplier}}{100.0} \right\rceil$$
