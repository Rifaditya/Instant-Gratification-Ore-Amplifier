// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.oreamplifier;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRuleCategory;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.dasik.social.api.gamerule.DynamicGameRuleManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OreAmplifierFabric implements ModInitializer {
    public static final String MOD_ID = "ore-amplifier";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static MinecraftServer SERVER_INSTANCE;

    // Static GameRule handles registered at init time
    public static GameRuleCategory ORE_AMPLIFIER_CATEGORY;
    public static GameRule<Integer> IG_ORE_VANILLA_GLOBAL;
    public static GameRule<Integer> IG_ORE_MODDED_GLOBAL;
    public static GameRule<Integer> IG_ORE_VEIN_SIZE;

    @Override
    public void onInitialize() {
        net.instantgratification.oreamplifier.util.ModVersionGuard.checkClass("Ore Amplifier", "net.minecraft.world.level.levelgen.feature.Feature");
        LOGGER.info("Ore Amplifier Initializing (Snapshot 26.1 Native)...");

        // Load global JSON configuration
        OreAmplifierConfig.load(net.fabricmc.loader.api.FabricLoader.getInstance().getConfigDir());

        // Register custom GameRule category
        ORE_AMPLIFIER_CATEGORY = DynamicGameRuleManager
                .registerCategory(Identifier.fromNamespaceAndPath("minecraft", "ore_amplifier"));

        // Register global fallback rules
        IG_ORE_VANILLA_GLOBAL = DynamicGameRuleManager.integerRule("ig:ore_vanilla_global", ORE_AMPLIFIER_CATEGORY, OreAmplifierConfig.get().vanillaGlobalMultiplier)
                .range(1, 1000)
                .name("Vanilla Ore Multiplier")
                .description("Global multiplier for vanilla ores. Default is 200 (2.0x).")
                .register();
        IG_ORE_MODDED_GLOBAL = DynamicGameRuleManager.integerRule("ig:ore_modded_global", ORE_AMPLIFIER_CATEGORY, OreAmplifierConfig.get().moddedGlobalMultiplier)
                .range(1, 1000)
                .name("Modded Ore Multiplier")
                .description("Global multiplier for modded ores. Default is 120 (1.2x).")
                .register();
        IG_ORE_VEIN_SIZE = DynamicGameRuleManager.integerRule("ig:ore_vein_size_multiplier", ORE_AMPLIFIER_CATEGORY, OreAmplifierConfig.get().veinSizeMultiplier)
                .range(100, 500)
                .name("Ore Vein Size Multiplier")
                .description("Multiplier for individual vein block volume. Default is 100 (1.0x).")
                .register();

        // Block-based pre-registration (catches modded ore blocks by name)
        // Filter aligned with OreLogic.shouldAmplify() to prevent ghost rules.
        BuiltInRegistries.BLOCK.stream().forEach(block -> {
            Identifier id = BuiltInRegistries.BLOCK.getKey(block);
            if (id != null && OreLogic.shouldAmplify(id)) {
                String ruleName = "ig:ore_" + id.getNamespace() + "_" + id.getPath();
                DynamicGameRuleManager.integerRule(ruleName, ORE_AMPLIFIER_CATEGORY, 100)
                        .range(0, 1000)
                        .name(DynamicGameRuleManager.generateReadableName(ruleName))
                        .description("Multiplier for " + id.getPath().replace('_', ' ') + ". Default is 100 (1x).")
                        .register();
            }
        });

        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            SERVER_INSTANCE = server;

            // Auto-heal: if global rules are 0 they would disable all generation; reset to
            // normal
            GameRules rules = server.getGameRules();
            if (rules.get(IG_ORE_VANILLA_GLOBAL) == 0) {
                rules.set(IG_ORE_VANILLA_GLOBAL, 200, server);
                LOGGER.info("OreAmplifier: Auto-healed vanilla global multiplier to 200.");
            }
            if (rules.get(IG_ORE_MODDED_GLOBAL) == 0) {
                rules.set(IG_ORE_MODDED_GLOBAL, 120, server);
                LOGGER.info("OreAmplifier: Auto-healed modded global multiplier to 120.");
            }
        });

        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            // Reload the config to catch any main menu customization
            OreAmplifierConfig.load(net.fabricmc.loader.api.FabricLoader.getInstance().getConfigDir());

            // Write defaults to new worlds on initialization
            if (!server.getWorldData().overworldData().isInitialized()) {
                GameRules rules = server.getGameRules();
                rules.set(IG_ORE_VANILLA_GLOBAL, OreAmplifierConfig.get().vanillaGlobalMultiplier, server);
                rules.set(IG_ORE_MODDED_GLOBAL, OreAmplifierConfig.get().moddedGlobalMultiplier, server);
                LOGGER.info("OreAmplifier: Initialized new world GameRules from config template.");
            }
        });

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            OreCommand.register(dispatcher);
        });
    }
}
