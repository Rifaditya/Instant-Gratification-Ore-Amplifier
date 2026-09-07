# ⛏️ Spieler-Leitfaden für die Ore Amplifier Mod (Erzverstärker)

Willkommen bei **Ore Amplifier**! Diese Mod vervielfacht die Erzgenerierung von Vanilla- und Mod-Erzen in der Welt (Aderanzahl und Adergröße) und bietet komfortable Konfigurationsmöglichkeiten über GameRules, Befehle und grafische Menüs.

---

## 🚀 Schnellstart (Quick Start)

1. **Installation**: Platziere die `ore-amplifier`-JAR-Datei im Ordner `.minecraft/mods`. Stelle sicher, dass **Fabric Loader**, **Fabric API** und **Dasik Library** installiert sind.
2. **Spielen**: In allen neu generierten oder erkundeten Chunks spawnen Erze automatisch mit dem konfigurierten Multiplikator (Standard für Vanilla: **200% / 2.0x**, für Mods: **120% / 1.2x**).
3. **Bestehende Welten**: Bereits generierte Chunks werden nicht automatisch neu generiert. Erkunde neue Chunks oder nutze [MCA Selector](https://github.com/Querz/mcaselector), um Chunks zurückzusetzen.

---

## ⚙️ Konfiguration über GameRules

Im Einzelspieler drücke `Esc` $\rightarrow$ `Im LAN öffnen` (Cheats erlauben) $\rightarrow$ `Spielregeln`, und suche die Kategorie **Erzverstärker (Ore Amplifier)**:

| Spielregel | Standard | Beschreibung |
| :--- | :---: | :--- |
| `ig:ore_vanilla_global` | `200` | Basis-Multiplikator für alle Vanilla-Erze (200 = 2.0x) |
| `ig:ore_modded_global` | `120` | Basis-Multiplikator für Erze von Drittanbieter-Mods (120 = 1.2x) |
| `ig:ore_vein_size_multiplier` | `100` | Multiplikator für Adergröße in Blöcken (100%–500%) |
| `ig:ore_<namespace>_<ore>` | `100` | Spezifische Regel für einzelnes Erz (überschreibt Globalwert) |

---

## 💬 Befehle im Spiel (/oreamp)

Die Mod bietet einen vollständigen Brigadier-Befehlsbaum mit automatischer Tab-Vervollständigung:

```bash
# Aktuellen Status und aktive Multiplikatoren anzeigen
/oreamp status

# Effektiven Multiplikator für ein bestimmtes Erz abfragen
/oreamp get minecraft:diamond_ore

# Globalen Vanilla-Multiplikator auf 3.0x (300%) setzen
/oreamp set global vanilla 300

# Diamanterz-Multiplikator auf 5.0x (500%) setzen
/oreamp set minecraft:diamond_ore 500

# Alle Einstellungen auf Standardwerte zurücksetzen
/oreamp reset

# Konfiguration aus config/ore-amplifier.json neu laden
/oreamp reload
```

---

## 🖥️ YACL & ModMenu Konfigurationsoberfläche

Mit den Mods **YACL v3** und **Mod Menu** kannst du die grafische Oberfläche nutzen (`Mods` $\rightarrow$ `Ore Amplifier` $\rightarrow$ `Konfigurieren`):
- **Allgemeine Einstellungen**: Schieberegler für globale Multiplikatoren und Adergröße.
- **Individuelle Erz-Multiplikatoren**: Feineinstellung für 18 Vanilla-Erze (0%–1000%).
