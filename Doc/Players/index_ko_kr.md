# ⛏️ Ore Amplifier (광석 증폭기) 플레이어 가이드

**Ore Amplifier** 모드에 오신 것을 환영합니다! 이 모드는 마인크래프트 바닐라 및 외부 모드 광석의 월드 생성(광맥 수 및 광맥 크기)을 배가시키며, 게임 규칙(GameRules), 명령어, 그래픽 설정 화면을 통해 직관적으로 설정할 수 있습니다.

---

## 🚀 빠른 시작 (Quick Start)

1. **설치**: `ore-amplifier` 모드 JAR 파일을 `.minecraft/mods` 폴더에 넣습니다. 필수 모드인 **Fabric Loader**, **Fabric API**, **Dasik Library**가 설치되어 있는지 확인하세요.
2. **플레이**: 새로 생성되거나 탐험하는 모든 청크에서 광석이 설정된 배율에 따라 자동으로 증가하여 생성됩니다 (기본값: 바닐라 광석 **200% / 2.0배**, 모드 광석 **120% / 1.2배**).
3. **기존 월드**: 이미 생성된 청크는 자동으로 재생성되지 않습니다. 배율을 적용하려면 새로운 청크를 탐험하거나 [MCA Selector](https://github.com/Querz/mcaselector) 등의 도구로 원하는 청크를 초기화하세요.

---

## ⚙️ 게임 규칙 설정 (In-Game GameRules)

싱글 플레이에서 `Esc` $\rightarrow$ `LAN 서버 열기` (치트 허용) $\rightarrow$ `게임 규칙`을 열고, **광석 증폭기 (Ore Amplifier)** 항목으로 스크롤합니다:

| 게임 규칙 이름 | 기본값 | 설명 |
| :--- | :---: | :--- |
| `ig:ore_vanilla_global` | `200` | 모든 바닐라 광석의 기본 생성 배율 (200 = 2.0배) |
| `ig:ore_modded_global` | `120` | 타 모드 광석의 기본 생성 배율 (120 = 1.2배) |
| `ig:ore_vein_size_multiplier` | `100` | 광맥당 블록 부피 크기 배율 (100%–500%) |
| `ig:ore_<namespace>_<ore>` | `100` | 특정 광석 개별 규칙 (100이 아닌 경우 전체 배율 덮어씀) |

---

## 💬 인게임 명령어 (/oreamp)

이 모드는 자동 완성을 지원하는 완벽한 Brigadier 명령어 트리를 제공합니다:

```bash
# 현재 상태 및 활성 배율 확인
/oreamp status

# 특정 광석의 유효 배율 확인
/oreamp get minecraft:diamond_ore

# 바닐라 광석 전체 배율을 3.0배 (300%)로 설정
/oreamp set global vanilla 300

# 다이아몬드 광석 배율을 5.0배 (500%)로 단독 설정
/oreamp set minecraft:diamond_ore 500

# 모든 설정을 공장 초기 기본값으로 리셋
/oreamp reset

# config/ore-amplifier.json 파일에서 설정 다시 불러오기
/oreamp reload
```

---

## 🖥️ YACL & ModMenu 그래픽 설정 화면

**YACL v3** 및 **Mod Menu**가 설치되어 있는 경우 모드 목록에서 그래픽 설정 화면을 열 수 있습니다 (`모드` $\rightarrow$ `Ore Amplifier` $\rightarrow$ `설정`):
- **일반 설정**: 전체 배율 및 광맥 크기 슬라이더 조절.
- **개별 광석 배율**: 18종 바닐라 광석의 생성 배율을 0%~1000% 범위로 슬라이더 미세 조정.
