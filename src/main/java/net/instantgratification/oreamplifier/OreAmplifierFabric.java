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

    public static final Map<String, GameRule<Integer>> DYNAMIC_RULES = new ConcurrentHashMap<>();

    @Override
    public void onInitialize() {
        LOGGER.info("Ore Amplifier Initializing (Snapshot 26.1 Native)...");

        // Register Custom Category
        ORE_AMPLIFIER_CATEGORY = GameRuleCategory.register(Identifier.fromNamespaceAndPath("minecraft", "ore_amplifier"));

        // Register Global Rules
        IG_ORE_VANILLA_GLOBAL = registerInteger("ig_ore_vanilla_global", ORE_AMPLIFIER_CATEGORY, 100);
        IG_ORE_MODDED_GLOBAL = registerInteger("ig_ore_modded_global", ORE_AMPLIFIER_CATEGORY, 100);

        // Dynamic Registration
        BuiltInRegistries.BLOCK.stream().forEach(block -> {
            Identifier id = BuiltInRegistries.BLOCK.getKey(block);
            if (id.getPath().contains("ore")) {
                String ruleName = "ig_ore_" + id.getNamespace() + "_" + id.getPath();
                // Check if already registered (unlikely unless collision)
                if (!DYNAMIC_RULES.containsKey(ruleName)) {
                     // Note: We are registering late in onInitialize. 
                     // Ideally GameRules are registered statically but Fabric/Mixin allows late registration usually.
                     // If this fails, we might need a different hook.
                     GameRule<Integer> rule = registerInteger(ruleName, ORE_AMPLIFIER_CATEGORY, 0); // Default 0 means use global
                     DYNAMIC_RULES.put(ruleName, rule);
                }
            }
        });

        ServerLifecycleEvents.SERVER_STARTING.register(server -> SERVER_INSTANCE = server);

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            OreCommand.register(dispatcher);
        });
    }

    public static GameRule<Integer> getDynamicRule(String name) {
        return DYNAMIC_RULES.get(name);
    }

    private static GameRule<Integer> registerInteger(String id, GameRuleCategory category, int defaultValue) {
        return Registry.register(BuiltInRegistries.GAME_RULE, id, new GameRule<>(
                category,
                GameRuleType.INT,
                IntegerArgumentType.integer(0),
                GameRuleTypeVisitor::visitInteger,
                Codec.INT,
                i -> i,
                defaultValue,
                FeatureFlagSet.of()));
    }
}
