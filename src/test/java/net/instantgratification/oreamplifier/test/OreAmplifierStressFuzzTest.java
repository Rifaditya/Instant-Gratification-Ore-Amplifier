// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.oreamplifier.test;

import net.instantgratification.oreamplifier.OreAmplifierConfig;
import net.instantgratification.oreamplifier.OreLogic;
import net.minecraft.SharedConstants;
import net.minecraft.resources.Identifier;
import net.minecraft.server.Bootstrap;
import net.minecraft.util.RandomSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pillar 1: Chaos Property Fuzzing Suite for Ore Amplifier.
 * Asserts mathematical bounds, non-overflow guarantees, blacklist recognition,
 * and config resilience under heavy randomized load.
 */
public class OreAmplifierStressFuzzTest {

    private static final int FUZZ_ITERATIONS = 20_000;
    private static final int CONFIG_ITERATIONS = 5_000;
    private static final long DETERMINISTIC_SEED = 0xDA51C263L;

    private static final int[] BOUNDARY_VALUES = {
            Integer.MIN_VALUE,
            -100,
            -1,
            0,
            1,
            2,
            50,
            100,
            120,
            200,
            500,
            1000,
            10_000_000,
            Integer.MAX_VALUE
    };

    @BeforeAll
    public static void setup() {
        SharedConstants.tryDetectVersion();
        Bootstrap.bootStrap();
    }

    @Test
    @DisplayName("Pillar 1: Chaos Fuzzing of getAmplifiedCount Math Bounds (20,000 iterations)")
    void testGetAmplifiedCountChaosFuzzing() {
        RandomSource random = RandomSource.create(DETERMINISTIC_SEED);
        Random javaRand = new Random(DETERMINISTIC_SEED);

        // Deterministic baseline assertions
        assertEquals(2000, OreLogic.getAmplifiedCount(1000, 200, random), "200% multiplier on 1000 must yield 2000");
        assertEquals(1000, OreLogic.getAmplifiedCount(1000, 100, random), "100% multiplier on 1000 must preserve 1000");
        assertEquals(500, OreLogic.getAmplifiedCount(1000, 50, random), "50% multiplier on 1000 must yield 500");

        for (int i = 0; i < FUZZ_ITERATIONS; i++) {
            int originalCount = (i < BOUNDARY_VALUES.length)
                    ? BOUNDARY_VALUES[i]
                    : (javaRand.nextBoolean() ? BOUNDARY_VALUES[javaRand.nextInt(BOUNDARY_VALUES.length)] : javaRand.nextInt(20_000) - 5_000);

            int multiplierPercent = (i < BOUNDARY_VALUES.length)
                    ? BOUNDARY_VALUES[i]
                    : (javaRand.nextBoolean() ? BOUNDARY_VALUES[javaRand.nextInt(BOUNDARY_VALUES.length)] : javaRand.nextInt(2_000) - 500);

            int result = OreLogic.getAmplifiedCount(originalCount, multiplierPercent, random);

            // Property: Non-positive input count yields non-positive result
            if (originalCount <= 0) {
                assertTrue(result <= 0, "Non-positive originalCount (" + originalCount + ") must yield non-positive result: " + result);
            }

            // Property: Positive count and positive multiplier must never wrap around to negative
            if (originalCount > 0 && multiplierPercent > 0) {
                assertTrue(result >= 0, "Positive count (" + originalCount + ") with positive multiplier (" + multiplierPercent + "%) must not overflow to negative: " + result);
            }

            // Property: 100% multiplier preserves positive count up to reasonable bounds
            if (multiplierPercent == 100 && originalCount > 0 && originalCount <= 10_000_000) {
                assertEquals(originalCount, result, "100% multiplier must exactly preserve originalCount: " + originalCount);
            }
        }
    }

    @Test
    @DisplayName("Pillar 1: Chaos Fuzzing of shouldAmplify Identifier Heuristics and Blacklists")
    void testShouldAmplifyChaosFuzzing() {
        // Null safety
        assertFalse(OreLogic.shouldAmplify(null), "Null identifier must safely return false");

        // Static blacklist check
        assertFalse(OreLogic.shouldAmplify(Identifier.fromNamespaceAndPath("minecraft", "amethyst_geode")));
        assertFalse(OreLogic.shouldAmplify(Identifier.fromNamespaceAndPath("minecraft", "monster_room")));
        assertFalse(OreLogic.shouldAmplify(Identifier.fromNamespaceAndPath("minecraft", "monster_room_deep")));

        // Prefix/Suffix ores
        assertTrue(OreLogic.shouldAmplify(Identifier.fromNamespaceAndPath("minecraft", "iron_ore")));
        assertTrue(OreLogic.shouldAmplify(Identifier.fromNamespaceAndPath("minecraft", "deepslate_gold_ore")));
        assertTrue(OreLogic.shouldAmplify(Identifier.fromNamespaceAndPath("minecraft", "ancient_debris")));
        assertTrue(OreLogic.shouldAmplify(Identifier.fromNamespaceAndPath("custom", "ruby_ore")));
        assertTrue(OreLogic.shouldAmplify(Identifier.fromNamespaceAndPath("mod", "ore_copper")));

        // Non-ore blocks
        assertFalse(OreLogic.shouldAmplify(Identifier.fromNamespaceAndPath("minecraft", "stone")));
        assertFalse(OreLogic.shouldAmplify(Identifier.fromNamespaceAndPath("minecraft", "dirt")));
        assertFalse(OreLogic.shouldAmplify(Identifier.fromNamespaceAndPath("minecraft", "oak_planks")));

        // Randomized Identifier fuzzing
        Random rand = new Random(DETERMINISTIC_SEED);
        String[] oreKeywords = {"ore", "debris", "_ore", "ore_", "_ore_"};
        String[] nonOreKeywords = {"stone", "dirt", "wood", "glass", "air", "water", "lava"};

        for (int i = 0; i < 5_000; i++) {
            boolean injectOre = rand.nextBoolean();
            String ns = rand.nextBoolean() ? "minecraft" : "mod" + rand.nextInt(100);
            String path;
            if (injectOre) {
                String kw = oreKeywords[rand.nextInt(oreKeywords.length)];
                path = "sample_" + kw + "_" + rand.nextInt(1000);
            } else {
                String kw = nonOreKeywords[rand.nextInt(nonOreKeywords.length)];
                path = "block_" + kw + "_" + rand.nextInt(1000);
            }

            Identifier id = Identifier.fromNamespaceAndPath(ns, path);
            boolean result = OreLogic.shouldAmplify(id);
            if (injectOre && (path.contains("_ore") || path.contains("ore_") || path.equals("ore") || (ns.equals("minecraft") && path.contains("debris")))) {
                assertTrue(result, "Synthesized ore identifier must be amplified: " + id);
            }
        }
    }

    @Test
    @DisplayName("Pillar 1: Chaos Fuzzing of OreAmplifierConfig Overrides and Sparse Pruning (5,000 operations)")
    void testConfigFuzzingAndPruning() {
        OreAmplifierConfig config = new OreAmplifierConfig();
        Random rand = new Random(DETERMINISTIC_SEED);

        for (int i = 0; i < CONFIG_ITERATIONS; i++) {
            String oreKey = "mod" + (i % 50) + ":ore_" + (i % 20);
            int op = rand.nextInt(3);

            if (op == 0) {
                int mult = rand.nextInt(1500) - 200; // [-200, 1299]
                config.setOreOverride(oreKey, mult);
                if (mult <= 0 || mult == 100) {
                    assertEquals(-1, config.getOreOverride(oreKey), "Values <= 0 or == 100 must not be stored");
                } else {
                    assertEquals(mult, config.getOreOverride(oreKey));
                }
            } else if (op == 1) {
                config.getOreOverride(oreKey);
            } else {
                config.pruneDefaults();
                for (var entry : config.perOreMultipliers.entrySet()) {
                    assertNotNull(entry.getValue());
                    assertTrue(entry.getValue() > 0 && entry.getValue() != 100, "Pruned map must not contain <= 0 or 100 values");
                }
            }
        }
    }
}
