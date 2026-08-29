# ⛏️ Panduan Pemain Mod Ore Amplifier (Penguat Bijih)

Selamat datang di **Ore Amplifier**! Mod ini melipatgandakan pembuatan bijih Vanilla maupun bijih mod pihak ketiga di dunia Minecraft (jumlah urat dan ukuran urat bijih), dilengkapi konfigurasi fleksibel melalui GameRules, perintah chat, dan tampilan GUI visual.

---

## 🚀 Panduan Cepat (Quick Start)

1. **Pemasangan**: Salin file JAR `ore-amplifier` ke folder `.minecraft/mods`. Pastikan **Fabric Loader**, **Fabric API**, dan **Dasik Library** sudah terpasang.
2. **Bermain**: Di setiap chunk baru yang dihasilkan atau dijelajahi, bijih akan otomatis muncul berlipat ganda sesuai pengaturan (bawaan Vanilla: **200% / 2.0x**, mod: **120% / 1.2x**).
3. **Dunia yang Sudah Ada**: Chunk yang sudah dibuat sebelumnya tidak akan diperbarui otomatis. Untuk menerapkan penggandaan, jelajahi area baru atau gunakan alat [MCA Selector](https://github.com/Querz/mcaselector) untuk mereset chunk yang diinginkan.

---

## ⚙️ Pengaturan Melalui GameRules

Di mode singleplayer tekan `Esc` $\rightarrow$ `Buka ke LAN` (izinkan cheat) $\rightarrow$ `Aturan Permainan (GameRules)`, lalu gulir ke kategori **Penguat Bijih (Ore Amplifier)**:

| Nama GameRule | Nilai Bawaan | Keterangan |
| :--- | :---: | :--- |
| `ig:ore_vanilla_global` | `200` | Pengali dasar untuk seluruh bijih Vanilla (200 = 2.0x) |
| `ig:ore_modded_global` | `120` | Pengali dasar untuk bijih dari mod pihak ketiga (120 = 1.2x) |
| `ig:ore_vein_size_multiplier` | `100` | Pengali ukuran blok per urat bijih (100%–500%) |
| `ig:ore_<namespace>_<ore>` | `100` | Aturan per bijih khusus (menimpa global bila $\neq$ 100) |

---

## 💬 Perintah Dalam Game (/oreamp)

Mod ini dilengkapi pohon perintah Brigadier lengkap dengan pelengkapan otomatis (tab-complete):

```bash
# Cek status dan pengali aktif saat ini
/oreamp status

# Lihat nilai pengali efektif untuk bijih tertentu
/oreamp get minecraft:diamond_ore

# Set pengali global Vanilla menjadi 3.0x (300%)
/oreamp set global vanilla 300

# Set pengali bijih intan (diamond) menjadi 5.0x (500%)
/oreamp set minecraft:diamond_ore 500

# Reset seluruh pengaturan ke nilai awal bawaan
/oreamp reset

# Muat ulang konfigurasi dari config/ore-amplifier.json
/oreamp reload
```

---

## 🖥️ Menu GUI YACL & ModMenu

Bila Anda memasang mod **YACL v3** dan **Mod Menu**, Anda dapat mengakses menu konfigurasi visual (`Mods` $\rightarrow$ `Ore Amplifier` $\rightarrow$ `Konfigurasi`):
- **Pengaturan Umum**: Slider untuk mengatur pengali global dan ukuran urat.
- **Pengali Per Bijih**: Pengaturan slider visual untuk 18 jenis bijih Vanilla (0%–1000%).
