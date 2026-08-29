# ⛏️ Guía del jugador para el mod Ore Amplifier (Amplificador de minerales)

¡Bienvenido a **Ore Amplifier**! Este mod multiplica la generación de minerales tanto de Vanilla como de mods en el mundo (frecuencia y tamaño de vetas), permitiendo una configuración sencilla mediante reglas de juego, comandos y menús visuales.

---

## 🚀 Inicio rápido (Quick Start)

1. **Instalación**: Coloca el archivo JAR `ore-amplifier` en la carpeta `.minecraft/mods`. Asegúrate de tener instalado **Fabric Loader**, **Fabric API** y **Dasik Library**.
2. **Jugar**: En cualquier chunk nuevo generado o explorado, los minerales aparecerán multiplicados según la configuración (por defecto en Vanilla: **200% / 2.0x**, en mods: **120% / 1.2x**).
3. **Mundos existentes**: Los chunks ya generados no se reemplazan automáticamente. Para aplicar multiplicadores, explora nuevos territorios o utiliza [MCA Selector](https://github.com/Querz/mcaselector) para reiniciar los chunks deseados.

---

## ⚙️ Configuración mediante GameRules

En un jugador, presiona `Esc` $\rightarrow$ `Abrir en LAN` (permitir trucos) $\rightarrow$ `Reglas de juego`, y busca la categoría **Amplificador de minerales (Ore Amplifier)**:

| Regla de juego | Valor por defecto | Descripción |
| :--- | :---: | :--- |
| `ig:ore_vanilla_global` | `200` | Multiplicador base para todos los minerales de Vanilla (200 = 2.0x) |
| `ig:ore_modded_global` | `120` | Multiplicador base para minerales de mods externos (120 = 1.2x) |
| `ig:ore_vein_size_multiplier` | `100` | Multiplicador de bloques por veta (100%–500%) |
| `ig:ore_<namespace>_<ore>` | `100` | Regla individual para mineral específico (anula el global si no es 100) |

---

## 💬 Comandos en el juego (/oreamp)

El mod incluye un árbol completo de comandos Brigadier con autocompletado:

```bash
# Ver estado actual y multiplicadores activos
/oreamp status

# Ver multiplicador efectivo de un mineral específico
/oreamp get minecraft:diamond_ore

# Establecer multiplicador global de Vanilla en 3.0x (300%)
/oreamp set global vanilla 300

# Establecer mineral de diamante en 5.0x (500%)
/oreamp set minecraft:diamond_ore 500

# Restablecer todos los valores a los predeterminados
/oreamp reset

# Recargar configuración desde config/ore-amplifier.json
/oreamp reload
```

---

## 🖥️ Interfaz gráfica YACL & ModMenu

Con los mods **YACL v3** y **Mod Menu** instalados, accede al menú gráfico (`Mods` $\rightarrow$ `Ore Amplifier` $\rightarrow$ `Configurar`):
- **Configuración general**: Sliders para multiplicadores globales y tamaño de vetas.
- **Multiplicadores individuales**: Ajuste visual de 18 minerales de Vanilla (0%–1000%).
