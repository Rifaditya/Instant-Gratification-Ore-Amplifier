# ⛏️ Guide du joueur pour le mod Ore Amplifier (Amplificateur de minerais)

Bienvenue dans **Ore Amplifier** ! Ce mod multiplie la génération de minerais Vanilla et de mods dans le monde (quantité et taille des filons), tout en offrant des options de configuration simples via les GameRules, commandes et interfaces visuelles.

---

## 🚀 Démarrage rapide (Quick Start)

1. **Installation** : Placez le fichier JAR `ore-amplifier` dans votre dossier `.minecraft/mods`. Assurez-vous d'avoir installé **Fabric Loader**, **Fabric API** et **Dasik Library**.
2. **Jouer** : Dans tous les nouveaux chunks générés ou explorés, les minerais apparaîtront automatiquement multipliés selon la configuration (par défaut Vanilla : **200% / 2.0x**, mods : **120% / 1.2x**).
3. **Mondes existants** : Les chunks déjà générés ne sont pas modifiés rétroactivement. Pour appliquer les multiplicateurs, explorez de nouvelles zones ou utilisez [MCA Selector](https://github.com/Querz/mcaselector) pour réinitialiser les chunks ciblés.

---

## ⚙️ Configuration via GameRules

En solo, appuyez sur `Échap` $\rightarrow$ `Ouvrir au réseau local` (activer les cheats) $\rightarrow$ `Règles du jeu`, et faites défiler jusqu'à **Amplificateur de minerais (Ore Amplifier)** :

| Règle du jeu | Valeur par défaut | Description |
| :--- | :---: | :--- |
| `ig:ore_vanilla_global` | `200` | Multiplicateur de base pour tous les minerais Vanilla (200 = 2.0x) |
| `ig:ore_modded_global` | `120` | Multiplicateur de base pour les minerais issus de mods (120 = 1.2x) |
| `ig:ore_vein_size_multiplier` | `100` | Multiplicateur de taille des filons en blocs (100%–500%) |
| `ig:ore_<namespace>_<ore>` | `100` | Règle spécifique à un minerai (remplace le global si $\neq$ 100) |

---

## 💬 Commandes en jeu (/oreamp)

Le mod inclut une arborescence complète de commandes Brigadier avec auto-complétion :

```bash
# Afficher l'état actuel et les multiplicateurs actifs
/oreamp status

# Consulter le multiplicateur effectif pour un minerai précis
/oreamp get minecraft:diamond_ore

# Régler le multiplicateur global Vanilla sur 3.0x (300%)
/oreamp set global vanilla 300

# Régler le minerai de diamant sur 5.0x (500%)
/oreamp set minecraft:diamond_ore 500

# Réinitialiser toutes les valeurs par défaut
/oreamp reset

# Recharger la configuration depuis config/ore-amplifier.json
/oreamp reload
```

---

## 🖥️ Interface graphique YACL & ModMenu

Avec les mods **YACL v3** et **Mod Menu** installés, accédez à l'écran de configuration graphique (`Mods` $\rightarrow$ `Ore Amplifier` $\rightarrow$ `Configurer`) :
- **Paramètres généraux** : Curseurs pour les multiplicateurs globaux et la taille des filons.
- **Multiplicateurs par minerai** : Ajustement visuel pour 18 minerais Vanilla (0%–1000%).
