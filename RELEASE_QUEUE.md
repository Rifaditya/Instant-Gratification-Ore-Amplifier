# 📋 Ore Amplifier 26.3 Release Queue & Parity Hold

This file tracks release versions for **Ore Amplifier 26.3** (`Minecraft 26.3` Modern Lead).

## 🚀 Published & Backlog Queue

* ⏸️ **`1.3.20+26.3`** (Parity Hold) - Verified working lead build on Minecraft 26.3-pre-2; waiting on parity.

* ⏸️ **`1.3.14+26.3`** ⛔ (BUGGED / ON-HOLD / DO NOT PUBLISH) - **⛔ BUGGED / ON-HOLD (CRITICAL)** `[ERR-20260907-001]` / `[BL-OREAMP-001]`: RepeatingPlacement is an interface in 26.3. Complete feature milestone codebase compiled for Minecraft 26.3 with native Feature architecture.
  - **Status**: ⛔ BUGGED / ON-HOLD (CRITICAL) - DO NOT PUBLISH
  - **Blocker**: `[ERR-20260907-001]` / `[BL-OREAMP-001]` (`RepeatingPlacementModifier` incompatible; `RepeatingPlacement` converted to interface in MC 26.3).
  - **Why on hold**: Runtime/compilation failure in MC 26.3 plus generational parity hold against predecessor MC 26.2.
  - **Until when**: Holds until `[ERR-20260907-001]` is resolved and MC 26.2 reaches `1.3.14+26.2` on Modrinth and CurseForge.
  - **Resume action**: Resolve bug, verify in-game, and convert to active `- [ ]` once predecessor reaches parity.

