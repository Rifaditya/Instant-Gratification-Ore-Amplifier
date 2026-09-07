// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.oreamplifier;

import java.nio.file.Path;

public class OreAmplifierConfig {
    private static OreAmplifierConfig INSTANCE = new OreAmplifierConfig();
    private static Path CONFIG_PATH;

    public static final int VERSION = 1;
    public int configVersion = VERSION;

    public int vanillaGlobalMultiplier = 200;
    public int moddedGlobalMultiplier = 120;
    public int veinSizeMultiplier = 100;
    public java.util.Map<String, Integer> perOreMultipliers = new java.util.HashMap<>();

    public int getOreOverride(String oreId) {
        return perOreMultipliers.getOrDefault(oreId, -1);
    }

    public void setOreOverride(String oreId, int multiplier) {
        if (multiplier < 0) {
            perOreMultipliers.remove(oreId);
        } else {
            perOreMultipliers.put(oreId, multiplier);
        }
    }

    public static synchronized void load(Path configDir) {
        CONFIG_PATH = configDir.resolve("ore-amplifier.json");
        INSTANCE = net.dasik.social.api.config.ConfigHelper.load(
                CONFIG_PATH,
                INSTANCE,
                OreAmplifierConfig.class,
                VERSION,
                config -> config.configVersion,
                (config, ver) -> config.configVersion = ver,
                null,
                org.slf4j.LoggerFactory.getLogger("OreAmplifier")
        );
    }

    public static synchronized void save() {
        if (CONFIG_PATH == null) return;
        net.dasik.social.api.config.ConfigHelper.save(
                CONFIG_PATH,
                INSTANCE,
                org.slf4j.LoggerFactory.getLogger("OreAmplifier")
        );
    }

    public static OreAmplifierConfig get() {
        return INSTANCE;
    }
}
