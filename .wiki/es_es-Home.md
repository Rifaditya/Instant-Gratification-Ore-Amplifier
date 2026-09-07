🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

---
# ⛏️ Amplificador de minerales (Ore Amplifier)

> **Multiplica la generación de minerales de Vanilla y de Mods (frecuencia y tamaño de vetas).**
> Totalmente configurable mediante GameRules, comandos en el juego, interfaz gráfica YACL y archivo JSON.
> Parte de la colección **Instant Gratification Collection** creada por **Dasik (Rifaditya)**.

---

| Propiedad | Detalles |
| :--- | :--- |
| **ID del Mod** | `ore-amplifier` |
| **Versión actual** | `1.3.0+26.2` |
| **Versión de Minecraft** | 26.2+ |
| **Entorno** | Cliente y Servidor (`*`) |
| **Cargador de Mods** | Fabric (requiere Fabric API) |
| **Dependencias** | [Dasik Library](https://modrinth.com/mod/dasik-library) (≥1.8.2), Fabric API |
| **Opcional** | [YACL v3](https://modrinth.com/mod/yacl), [Mod Menu](https://modrinth.com/mod/modmenu) |
| **Licencia** | GNU General Public License v3.0 (GPLv3) |
| **Autor** | Dasik (Rifaditya) |

---

## ✨ Características principales

### 🔢 Multiplicación de vetas (Vein Count Multiplication)
Multiplica el número de vetas generadas por chunk. Por defecto, los minerales de Vanilla están al **2×** (200%) y los de mods al **1.2×** (120%).

### 📐 Amplificación del tamaño de veta (Vein Size Amplification)
Aumenta la cantidad de bloques por veta individual de 100% a 500%.

### 🎯 Control individual por mineral (Per-Ore Control)
Configura multiplicadores específicos para cada mineral por separado con sincronización bidireccional.

---

## 📖 Navegación de la Wiki

### Guía del jugador
- [[Detección y amplificación de minerales|es_es-Ore-Detection-and-Amplification]] — Mecánicas, sistema de lista negra y fórmulas matemáticas
- [[Reglas de juego y configuración|es_es-GameRules-and-Configuration]] — Referencia de GameRules, interfaz YACL y JSON
- [[Comandos|es_es-Commands]] — Árbol de comandos `/oreamp`

### Técnico
- [[Arquitectura y Mixins|es_es-Architecture-and-Mixins]] — Paquetes, Mixins e integración con DasikLibrary
- [[Configuración de desarrollador y compilación|es_es-Developer-Setup-and-Building]] — Configuración de JDK 25, Gradle y tests
- [[Compatibilidad de versiones|es_es-Version-Compatibility]] — Historial de 12 versiones y matriz de dependencias
