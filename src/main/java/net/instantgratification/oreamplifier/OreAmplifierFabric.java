package net.instantgratification.oreamplifier;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.gamerules.GameRules; // Updated package
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRuleCategory;
import net.minecraft.world.level.gamerules.GameRuleType;
import net.minecraft.world.level.gamerules.GameRuleTypeVisitor;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer; // Added missing import
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.serialization.Codec;
import net.dasik.social.api.gamerule.DynamicGameRuleManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.core.registries.BuiltInRegistries;

public class OreAmplifierFabric implements ModInitializer {
    public static final String MOD_ID = "ore-amplifier";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static MinecraftServer SERVER_INSTANCE;

    // Direct GameRule Registration
    public static GameRuleCategory ORE_AMPLIFIER_CATEGORY;
    public static GameRule<Integer> IG_ORE_VANILLA_GLOBAL;
    public static GameRule<Integer> IG_ORE_MODDED_GLOBAL;

    @Override
    public void onInitialize() {
        LOGGER.info("Ore Amplifier Initializing (Snapshot 26.1 Native)...");

        // Register Custom Category
        ORE_AMPLIFIER_CATEGORY = DynamicGameRuleManager.registerCategory(Identifier.fromNamespaceAndPath("minecraft", "ore_amplifier"));

        // Register Global Rules
        IG_ORE_VANILLA_GLOBAL = DynamicGameRuleManager.registerInteger("ig_ore_vanilla_global", ORE_AMPLIFIER_CATEGORY, 100);
        IG_ORE_MODDED_GLOBAL = DynamicGameRuleManager.registerInteger("ig_ore_modded_global", ORE_AMPLIFIER_CATEGORY, 100);

        // Dynamic Registration upfront so `/gamerule` autocomplete works before chunk gen
        BuiltInRegistries.BLOCK.stream().forEach(block -> {
            Identifier id = BuiltInRegistries.BLOCK.getKey(block);
            if (id.getPath().contains("ore") || id.getPath().contains("debris")) {
                String ruleName = "ig_ore_" + id.getNamespace() + "_" + id.getPath();
                DynamicGameRuleManager.registerInteger(ruleName, ORE_AMPLIFIER_CATEGORY, 100); // Default 100
            }
        });

        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            SERVER_INSTANCE = server;
            
            // Auto-Healing: If global rules are 0 (disabling all generation), reset to 100 (Normal)
            GameRules rules = server.getGameRules();
            if (rules.get(IG_ORE_VANILLA_GLOBAL) == 0) {
                rules.set(IG_ORE_VANILLA_GLOBAL, 100, server);
                LOGGER.info("OreAmplifier: Auto-healed vanilla global multiplier to 100.");
            }
            if (rules.get(IG_ORE_MODDED_GLOBAL) == 0) {
                rules.set(IG_ORE_MODDED_GLOBAL, 100, server);
                LOGGER.info("OreAmplifier: Auto-healed modded global multiplier to 100.");
            }
        });

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            OreCommand.register(dispatcher);
        });
    }
}
