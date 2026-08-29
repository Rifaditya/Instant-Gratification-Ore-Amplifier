# Detección y amplificación de minerales (Ore Detection & Amplification)

| Propiedad | Detalles |
| :--- | :--- |
| **Clase principal** | `OreLogic.java` |
| **Mixins de amplificación** | `RepeatingPlacementMixin`, `RarityFilterMixin`, `OreFeatureMixin` |
| **Etiquetas de lista negra** | `#c:ore_amplifier_blacklist`, `#ore-amplifier:blacklist` |

$$\text{amplifiedCount} = \text{StochasticUtil.getAmplifiedCount}(\text{original}, \text{multiplier\%}, \text{random})$$

$$\text{amplifiedSize} = \left\lfloor \text{originalSize} \times \frac{\text{multiplier}}{100.0} \right\rceil$$
