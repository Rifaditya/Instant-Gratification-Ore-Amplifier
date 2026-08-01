// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.oreamplifier;

import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.resources.Identifier;
import net.dasik.social.api.gamerule.DynamicGameRuleManager;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import java.util.Set;

public class OreLogic {

    public static final TagKey<PlacedFeature> CONVENTION_BLACKLIST_FEATURE = TagKey.create(
            Registries.PLACED_FEATURE, Identifier.fromNamespaceAndPath("c", "ore_amplifier_blacklist"));
    public static final TagKey<PlacedFeature> MOD_BLACKLIST_FEATURE = TagKey.create(
            Registries.PLACED_FEATURE, Identifier.fromNamespaceAndPath("ore-amplifier", "blacklist"));

    public static final TagKey<Block> CONVENTION_BLACKLIST_BLOCK = TagKey.create(
            Registries.BLOCK, Identifier.fromNamespaceAndPath("c", "ore_amplifier_blacklist"));
    public static final TagKey<Block> MOD_BLACKLIST_BLOCK = TagKey.create(
            Registries.BLOCK, Identifier.fromNamespaceAndPath("ore-amplifier", "blacklist"));

    private static final Set<String> STATIC_BLACKLIST = Set.of(
            "minecraft:amethyst_geode",
            "minecraft:monster_room",
            "minecraft:monster_room_deep");

    /**
     * Returns true if the given feature/block ID represents an ore that should be
     * amplified.
     * Checks dynamic tag blacklist and static blacklist first; then applies name-based heuristics.
     */
    public static boolean shouldAmplify(Identifier id) {
        if (id == null) return false;

        if (STATIC_BLACKLIST.contains(id.toString()))
            return false;

        // Check Block tag membership safely (handles early init before tags are bound)
        var blockOpt = BuiltInRegistries.BLOCK.get(id);
        if (blockOpt.isPresent()) {
            try {
                if (blockOpt.get().is(CONVENTION_BLACKLIST_BLOCK) || blockOpt.get().is(MOD_BLACKLIST_BLOCK)) {
                    return false;
                }
            } catch (IllegalStateException e) {
                // Tags are not bound yet during early initialization phase; fall back to static checks & name heuristics
            }
        }

        String path = id.getPath();
        boolean isOre = path.endsWith("_ore") || path.contains("_ore_") || path.startsWith("ore_")
                || path.equals("ore");
        boolean isDebris = id.getNamespace().equals("minecraft") && path.contains("debris");
        return isOre || isDebris;
    }

    /**
     * Returns the permille multiplier (100 = 1x) for the given feature ID.
     * Lookup order (concept §2 — Auto-Classification):
     * 1. Specific per-feature GameRule (e.g. ig:ore_minecraft_iron_ore)
     * 2. Namespace fallback (vanilla / modded global)
     */
    public static int getMultiplier(Identifier featureId, GameRules rules) {
        if (featureId == null)
            return 100;

        // 0. Check JSON config per-ore override (set via main menu GUI)
        int configOverride = OreAmplifierConfig.get().getOreOverride(featureId.toString());
        if (configOverride >= 0) {
            return configOverride;
        }

        // 1. Check specific dynamic rule (pre-registered at init; safe after registry
        // freeze)
        String dynamicRuleName = "ig:ore_" + featureId.getNamespace() + "_" + featureId.getPath();
        GameRule<Integer> dynamicRule = DynamicGameRuleManager.registerInteger(
                dynamicRuleName, OreAmplifierFabric.ORE_AMPLIFIER_CATEGORY, 100);
        if (dynamicRule != null) {
            int val = rules.get(dynamicRule);
            if (val != 100)
                return val; // Only use override if player explicitly changed it
        }

        // 2. Namespace fallback
        if (featureId.getNamespace().equals("minecraft")) {
            return rules.get(OreAmplifierFabric.IG_ORE_VANILLA_GLOBAL);
        } else {
            return rules.get(OreAmplifierFabric.IG_ORE_MODDED_GLOBAL);
        }
    }

    /**
     * Sets the multiplier for a specific feature ID by updating its dynamic GameRule.
     */
    public static boolean setMultiplier(Identifier featureId, int value, net.minecraft.server.MinecraftServer server) {
        if (featureId == null || server == null) return false;

        String dynamicRuleName = "ig:ore_" + featureId.getNamespace() + "_" + featureId.getPath();
        GameRule<Integer> dynamicRule = DynamicGameRuleManager.integerRule(
                dynamicRuleName, OreAmplifierFabric.ORE_AMPLIFIER_CATEGORY, 100)
                .name(DynamicGameRuleManager.generateReadableName(dynamicRuleName))
                .description("Multiplier for " + featureId.getPath().replace('_', ' ') + ". Default is 100 (1x).")
                .register();

        if (dynamicRule != null) {
            server.getGameRules().set(dynamicRule, value, server);
            return true;
        }
        return false;
    }

    /**
     * Returns descriptive information about whether a feature uses a specific override or global fallback.
     */
    public static String getMultiplierDetails(Identifier featureId, GameRules rules) {
        if (featureId == null) return "Invalid Ore ID";

        String dynamicRuleName = "ig:ore_" + featureId.getNamespace() + "_" + featureId.getPath();
        GameRule<Integer> dynamicRule = DynamicGameRuleManager.registerInteger(
                dynamicRuleName, OreAmplifierFabric.ORE_AMPLIFIER_CATEGORY, 100);

        if (dynamicRule != null) {
            int val = rules.get(dynamicRule);
            if (val != 100) {
                return val + "% (Specific Override)";
            }
        }

        if (featureId.getNamespace().equals("minecraft")) {
            int val = rules.get(OreAmplifierFabric.IG_ORE_VANILLA_GLOBAL);
            return val + "% (Global Vanilla Fallback)";
        } else {
            int val = rules.get(OreAmplifierFabric.IG_ORE_MODDED_GLOBAL);
            return val + "% (Global Modded Fallback)";
        }
    }

    private static int logCount = 0;

    public static int getAmplifiedCount(int originalCount, int multiplierPercent,
            net.minecraft.util.RandomSource random) {
        return net.dasik.social.util.StochasticUtil.getAmplifiedCount(originalCount, multiplierPercent, random);
    }

    public static void logAmplification(Identifier id, int original, int modified, int multiplier) {
        if (logCount < 10) {
            OreAmplifierFabric.LOGGER.info("OreAmplifier: Amplified {} from {} to {} (Mult: {}%)", id, original,
                    modified, multiplier);
            logCount++;
        }
    }
}
