// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.oreamplifier;

import net.dasik.social.api.config.ConfigOption;
import java.nio.file.Path;

public class OreAmplifierConfig {
    private static OreAmplifierConfig INSTANCE = new OreAmplifierConfig();
    private static Path CONFIG_PATH;

    public static final int VERSION = 1;
    public int configVersion = VERSION;

    @ConfigOption(
            category = "general",
            group = "options",
            min = 1,
            max = 1000,
            step = 10,
            slider = true,
            syncGameRule = true,
            gameRuleName = "ig:ore_vanilla_global",
            nameKey = "config.ore-amplifier.vanillaGlobalMultiplier",
            descriptionKey = "config.ore-amplifier.vanillaGlobalMultiplier.description"
    )
    public int vanillaGlobalMultiplier = 200;

    @ConfigOption(
            category = "general",
            group = "options",
            min = 1,
            max = 1000,
            step = 10,
            slider = true,
            syncGameRule = true,
            gameRuleName = "ig:ore_modded_global",
            nameKey = "config.ore-amplifier.moddedGlobalMultiplier",
            descriptionKey = "config.ore-amplifier.moddedGlobalMultiplier.description"
    )
    public int moddedGlobalMultiplier = 120;

    @ConfigOption(
            category = "general",
            group = "options",
            min = 100,
            max = 500,
            step = 10,
            slider = true,
            syncGameRule = true,
            gameRuleName = "ig:ore_vein_size_multiplier",
            nameKey = "config.ore-amplifier.veinSizeMultiplier",
            descriptionKey = "config.ore-amplifier.veinSizeMultiplier.description"
    )
    public int veinSizeMultiplier = 100;
    public java.util.Map<String, Integer> perOreMultipliers = new java.util.HashMap<>();

    public int getOreOverride(String oreId) {
        return perOreMultipliers.getOrDefault(oreId, -1);
    }

    public void setOreOverride(String oreId, int multiplier) {
        if (multiplier <= 0 || multiplier == 100) {
            perOreMultipliers.remove(oreId);
        } else {
            perOreMultipliers.put(oreId, multiplier);
        }
    }

    public static synchronized void load(Path configDir) {
        CONFIG_PATH = configDir.resolve("ore-amplifier.json");
        INSTANCE = net.dasik.social.api.config.ConfigHelper.loadOrCreate(
                CONFIG_PATH,
                OreAmplifierConfig.class,
                OreAmplifierConfig::new
        );
    }

    public static synchronized void save() {
        if (CONFIG_PATH == null) return;
        INSTANCE.pruneDefaults();
        net.dasik.social.api.config.ConfigHelper.save(
                CONFIG_PATH,
                INSTANCE
        );
    }

    public void pruneDefaults() {
        if (perOreMultipliers != null) {
            perOreMultipliers.entrySet().removeIf(e -> e.getValue() == null || e.getValue() <= 0 || e.getValue() == 100);
        }
    }

    public static OreAmplifierConfig get() {
        return INSTANCE;
    }
}
