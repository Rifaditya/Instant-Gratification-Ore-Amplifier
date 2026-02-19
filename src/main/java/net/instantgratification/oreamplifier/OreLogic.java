package net.instantgratification.oreamplifier;

import net.minecraft.util.RandomSource;
import net.minecraft.world.level.gamerules.GameRules; // Updated package
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.server.level.ServerLevel;

import net.minecraft.resources.Identifier;

public class OreLogic {
    public static boolean shouldAmplify(Identifier id) {
        return id.getPath().contains("ore") || id.getNamespace().equals("minecraft") && id.getPath().contains("debris");
    }

    public static int getMultiplier(Identifier featureId, GameRules rules) {
        if (featureId == null) return 100;

        // 1. Check Specific Dynamic Rule
        String dynamicRuleName = "ig_ore_" + featureId.getNamespace() + "_" + featureId.getPath();
        
        GameRule<Integer> dynamicRule = OreAmplifierFabric.getDynamicRule(dynamicRuleName);
        if (dynamicRule != null) {
            int val = rules.get(dynamicRule);
            if (val != 0) return val; // Use if set (>0)
        }

        // 2. Fallback to Global
        if (featureId.getNamespace().equals("minecraft")) {
            return rules.get(OreAmplifierFabric.IG_ORE_VANILLA_GLOBAL);
        } else {
             return rules.get(OreAmplifierFabric.IG_ORE_MODDED_GLOBAL);
        }
    }

    private static int logCount = 0;
    
    /**
     * Calculates the new count using stochastic rounding for fractional results.
     * e.g. Count 1 * 50% = 0.5 -> 50% chance of 1, 50% chance of 0.
     */
    public static int getAmplifiedCount(int originalCount, int multiplierPercent, net.minecraft.util.RandomSource random) {
        float multiplier = multiplierPercent / 100.0F;
        float targetCount = originalCount * multiplier;
        
        int baseCount = (int) targetCount;
        float residue = targetCount - baseCount;
        
        if (random.nextFloat() < residue) {
            baseCount++;
        }
        
        return baseCount;
    }

    public static void logAmplification(Identifier id, int original, int modified, int multiplier) {
        if (logCount < 10) { // Limit to 10 logs per session to verify functionality without spam
            OreAmplifierFabric.LOGGER.info("OreAmplifier: Amplified {} from {} to {} (Mult: {})", id, original, modified, multiplier);
            logCount++;
        }
    }
}
