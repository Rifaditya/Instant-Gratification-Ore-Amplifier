// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.oreamplifier;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Environment(EnvType.CLIENT)
public class OreAmplifierClient implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("ore-amplifier-client");

    @Override
    public void onInitializeClient() {
        LOGGER.info("Ore Amplifier Client Initialized (Dynamic GameRules handled via DasikLibrary).");
    }
}
