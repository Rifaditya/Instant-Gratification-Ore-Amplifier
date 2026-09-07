🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

---
# ⛏️ Усилитель руд (Ore Amplifier)

> **Умножает генерацию ванильных и модифицированных руд (частоту и размер жил).**
> Полная настройка через игровые правила (GameRules), внутриигровые команды, меню YACL GUI и файл конфигурации JSON.
> Является частью коллекции **Instant Gratification Collection** от автора **Dasik (Rifaditya)**.

---

| Свойство | Информация |
| :--- | :--- |
| **ID мода** | `ore-amplifier` |
| **Текущая версия** | `1.3.0+26.2` |
| **Версия Minecraft** | 26.2+ |
| **Среда** | Клиент и Сервер (`*`) |
| **Загрузчик модов** | Fabric (требуется Fabric API) |
| **Зависимости** | [Dasik Library](https://modrinth.com/mod/dasik-library) (≥1.8.2), Fabric API |
| **Опционально** | [YACL v3](https://modrinth.com/mod/yacl), [Mod Menu](https://modrinth.com/mod/modmenu) |
| **Лицензия** | GNU General Public License v3.0 (GPLv3) |
| **Автор** | Dasik (Rifaditya) |

---

## ✨ Ключевые особенности

### 🔢 Умножение количества жил (Vein Count Multiplication)
Увеличивает количество генерируемых рудных жил в каждом чанке. По умолчанию для ванильных руд установлено **2×** (200%), для руд из модов — **1.2×** (120%). Диапазон регулировки: от 1% до 1000%.

### 📐 Увеличение размера жил (Vein Size Amplification)
Позволяет масштабировать количество блоков в отдельной жиле от 100% до 500%.

### 🎯 Индивидуальная настройка руд (Per-Ore Control)
Возможность задавать множители отдельно для каждой руды через GameRules, команды, графический интерфейс YACL или JSON. Все способы настройки синхронизируются в реальном времени.

### 🚫 Система черного списка (Blacklist System)
Исключение определенных структур и генераций через датапак-теги (`#c:ore_amplifier_blacklist`).

---

## 📖 Навигация по Wiki

### Руководство игрока
- [[Обнаружение и умножение руд|ru_ru-Ore-Detection-and-Amplification]] — Алгоритмы распознавания, стохастическое округление и формулы
- [[Игровые правила и конфигурация|ru_ru-GameRules-and-Configuration]] — Справочник GameRules, YACL GUI и JSON
- [[Команды|ru_ru-Commands]] — Полная справка по командам `/oreamp`

### Для разработчиков
- [[Архитектура и миксины|ru_ru-Architecture-and-Mixins]] — Структура пакетов, миксины и интеграция с DasikLibrary
- [[Сборка и разработка|ru_ru-Developer-Setup-and-Building]] — Настройка среды JDK 25, сборка Gradle, тесты
- [[Совместимость версий|ru_ru-Version-Compatibility]] — История 12 версий и матрица зависимостей
