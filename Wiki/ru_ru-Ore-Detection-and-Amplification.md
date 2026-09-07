# Обнаружение и умножение руд (Ore Detection & Amplification)

| Свойство | Информация |
| :--- | :--- |
| **Основной класс логики** | `OreLogic.java` |
| **Миксины генерации** | `RepeatingPlacementMixin`, `RarityFilterMixin`, `OreFeatureMixin` |
| **Теги черного списка** | `#c:ore_amplifier_blacklist`, `#ore-amplifier:blacklist` |
| **Статический список** | `amethyst_geode`, `monster_room`, `monster_room_deep` |
| **Математический движок** | `StochasticUtil` (DasikLibrary) |

---

## 🔍 Процесс обнаружения руд

1. **Проверка черного списка**: Исключение `#c:ore_amplifier_blacklist` и статических комнат монстров/геод.
2. **Эвристический поиск по имени**: Сопоставление суффиксов и префиксов `_ore`, `ore_`, `ore`, `debris`.
3. **Иерархия разрешения множителя**:
   - **Уровень 1**: Переопределение в `config/ore-amplifier.json`
   - **Уровень 2**: Динамическое правило GameRule `ig:ore_<namespace>_<path>`
   - **Уровень 3**: Глобальный множитель (`ig:ore_vanilla_global` или `ig:ore_modded_global`)

---

## 🧮 Математическая модель

$$\text{amplifiedCount} = \text{StochasticUtil.getAmplifiedCount}(\text{original}, \text{multiplier\%}, \text{random})$$

$$\text{amplifiedSize} = \left\lfloor \text{originalSize} \times \frac{\text{multiplier}}{100.0} \right\rceil$$
