# ⛏️ Guia do Jogador do Mod Ore Amplifier (Amplificador de Minérios)

Bem-vindo ao **Ore Amplifier**! Este mod multiplica a geração de minérios Vanilla e de mods pelo mundo (quantidade de veios e tamanho dos veios), com opções de configuração simples via GameRules, comandos e telas visuais.

---

## 🚀 Início Rápido (Quick Start)

1. **Instalação**: Coloque o arquivo JAR `ore-amplifier` na pasta `.minecraft/mods`. Certifique-se de ter instalado o **Fabric Loader**, **Fabric API** e **Dasik Library**.
2. **Jogando**: Em qualquer chunk recém-gerado ou explorado, os minérios surgirão multiplicados de acordo com a configuração (padrão Vanilla: **200% / 2.0x**, mods: **120% / 1.2x**).
3. **Mundos Existentes**: Chunks já gerados anteriormente não sofrem alteração retroativa. Para aplicar multiplicadores, explore novos territórios ou use o [MCA Selector](https://github.com/Querz/mcaselector) para resetar os chunks desejados.

---

## ⚙️ Configuração via GameRules

No modo um jogador, pressione `Esc` $\rightarrow$ `Abrir para LAN` (permitir cheats) $\rightarrow$ `Regras do Jogo`, e role até **Amplificador de Minérios (Ore Amplifier)**:

| Regra do Jogo | Valor Padrão | Descrição |
| :--- | :---: | :--- |
| `ig:ore_vanilla_global` | `200` | Multiplicador base para todos os minérios Vanilla (200 = 2.0x) |
| `ig:ore_modded_global` | `120` | Multiplicador base para minérios de mods externos (120 = 1.2x) |
| `ig:ore_vein_size_multiplier` | `100` | Multiplicador do tamanho dos veios em blocos (100%–500%) |
| `ig:ore_<namespace>_<ore>` | `100` | Regra individual para minério específico (sobrescreve a global) |

---

## 💬 Comandos no Jogo (/oreamp)

O mod possui uma árvore completa de comandos Brigadier com preenchimento automático:

```bash
# Ver status atual e multiplicadores ativos
/oreamp status

# Ver multiplicador efetivo para um minério específico
/oreamp get minecraft:diamond_ore

# Definir multiplicador global Vanilla para 3.0x (300%)
/oreamp set global vanilla 300

# Definir minério de diamante para 5.0x (500%)
/oreamp set minecraft:diamond_ore 500

# Redefinir todas as opções para os valores padrão
/oreamp reset

# Recarregar configuração de config/ore-amplifier.json
/oreamp reload
```

---

## 🖥️ Interface Gráfica YACL & ModMenu

Com os mods **YACL v3** e **Mod Menu** instalados, acerte as opções pelo menu visual (`Mods` $\rightarrow$ `Ore Amplifier` $\rightarrow$ `Configurar`):
- **Configurações Gerais**: Sliders para multiplicadores globais e tamanho de veios.
- **Multiplicadores por Minério**: Ajuste individual para 18 minérios Vanilla (0%–1000%).
