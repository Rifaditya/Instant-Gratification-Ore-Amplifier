package net.instantgratification.oreamplifier;

import net.minecraft.util.RandomSource;
import net.minecraft.world.level.gamerules.GameRules; // Updated package
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.Identifier;
import net.dasik.social.api.gamerule.DynamicGameRuleManager;

public class OreLogic {
    public static boolean shouldAmplify(Identifier id) {
        String path = id.getPath();
        boolean isOre = path.endsWith("_ore") || path.contains("_ore_") || path.startsWith("ore_")
                || path.equals("ore");
        boolean isDebris = id.getNamespace().equals("minecraft") && path.contains("debris");
        return isOre || isDebris;
    }

    public static int getMultiplier(Identifier featureId, GameRules rules) {
        if (featureId == null)
            return 100;

        // 1. Check Specific Dynamic Rule (JIT Fallback Registration)
        String dynamicRuleName = "ig_ore_" + featureId.getNamespace() + "_" + featureId.getPath();

        GameRule<Integer> dynamicRule = DynamicGameRuleManager.registerInteger(dynamicRuleName,
                OreAmplifierFabric.ORE_AMPLIFIER_CATEGORY, 100);
        if (dynamicRule != null) {
            int val = rules.get(dynamicRule);
            if (val != 100)
                return val; // Use if set (not default 100)
        }

        // 2. Fallback to Global
        if (featureId.getNamespace().equals("minecraft")) {
            return rules.get(OreAmplifierFabric.IG_ORE_VANILLA_GLOBAL);
        } else {
            return rules.get(OreAmplifierFabric.IG_ORE_MODDED_GLOBAL);
        }
    }

    private static int logCount = 0;

    public static int getAmplifiedCount(int originalCount, int multiplierPercent,
            net.minecraft.util.RandomSource random) {
        return net.dasik.social.util.StochasticUtil.getAmplifiedCount(originalCount, multiplierPercent, random);
    }

    public static void logAmplification(Identifier id, int original, int modified, int multiplier) {
        if (logCount < 10) { // Limit to 10 logs per session to verify functionality without spam
            OreAmplifierFabric.LOGGER.info("OreAmplifier: Amplified {} from {} to {} (Mult: {})", id, original,
                    modified, multiplier);
            logCount++;
        }
    }
}
