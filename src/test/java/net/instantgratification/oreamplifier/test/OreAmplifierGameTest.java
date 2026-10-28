// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.oreamplifier.test;

import net.instantgratification.oreamplifier.OreAmplifierConfig;
import net.instantgratification.oreamplifier.OreLogic;
import net.minecraft.SharedConstants;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.Bootstrap;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pillar 4: Headless State Simulation GameTest Suite for Ore Amplifier.
 * Asserts BuiltInRegistries ore identification, static blacklist filtering,
 * Monte Carlo statistical convergence of stochastic multipliers, and sparse delta config lifecycle.
 */
public class OreAmplifierGameTest {

    private static final long SEED = 0xDA51C263L;

    @BeforeAll
    public static void setup() {
        SharedConstants.tryDetectVersion();
        Bootstrap.bootStrap();
    }

    @Test
    @DisplayName("Pillar 4: BuiltInRegistries.BLOCK Vanilla Ore Identification")
    void testVanillaOreIdentification() {
        Identifier ironOre = BuiltInRegistries.BLOCK.getKey(Blocks.IRON_ORE);
        Identifier deepslateIronOre = BuiltInRegistries.BLOCK.getKey(Blocks.DEEPSLATE_IRON_ORE);
        Identifier goldOre = BuiltInRegistries.BLOCK.getKey(Blocks.GOLD_ORE);
        Identifier diamondOre = BuiltInRegistries.BLOCK.getKey(Blocks.DIAMOND_ORE);
        Identifier ancientDebris = BuiltInRegistries.BLOCK.getKey(Blocks.ANCIENT_DEBRIS);

        assertTrue(OreLogic.shouldAmplify(ironOre), "iron_ore must be recognized for amplification");
        assertTrue(OreLogic.shouldAmplify(deepslateIronOre), "deepslate_iron_ore must be recognized for amplification");
        assertTrue(OreLogic.shouldAmplify(goldOre), "gold_ore must be recognized for amplification");
        assertTrue(OreLogic.shouldAmplify(diamondOre), "diamond_ore must be recognized for amplification");
        assertTrue(OreLogic.shouldAmplify(ancientDebris), "ancient_debris must be recognized for amplification");
    }

    @Test
    @DisplayName("Pillar 4: Blacklist Filtering and Non-Ore Rejection")
    void testBlacklistFiltering() {
        Identifier amethystGeode = Identifier.fromNamespaceAndPath("minecraft", "amethyst_geode");
        Identifier monsterRoom = Identifier.fromNamespaceAndPath("minecraft", "monster_room");
        Identifier monsterRoomDeep = Identifier.fromNamespaceAndPath("minecraft", "monster_room_deep");
        Identifier dirt = BuiltInRegistries.BLOCK.getKey(Blocks.DIRT);
        Identifier stone = BuiltInRegistries.BLOCK.getKey(Blocks.STONE);

        assertFalse(OreLogic.shouldAmplify(amethystGeode), "amethyst_geode must be blacklisted");
        assertFalse(OreLogic.shouldAmplify(monsterRoom), "monster_room must be blacklisted");
        assertFalse(OreLogic.shouldAmplify(monsterRoomDeep), "monster_room_deep must be blacklisted");
        assertFalse(OreLogic.shouldAmplify(dirt), "dirt is not an ore block");
        assertFalse(OreLogic.shouldAmplify(stone), "stone is not an ore block");
    }

    @Test
    @DisplayName("Pillar 4: Monte Carlo Statistical Convergence of Stochastic Multiplier (10,000 trials)")
    void testStatisticalConvergence() {
        RandomSource random = RandomSource.create(SEED);
        int trials = 10_000;

        // 10,000 trials of 200% multiplier on count 1 -> mean converges to 2.0 (+/- 0.05)
        long sum200 = 0;
        for (int i = 0; i < trials; i++) {
            sum200 += OreLogic.getAmplifiedCount(1, 200, random);
        }
        double mean200 = (double) sum200 / trials;
        assertEquals(2.0, mean200, 0.05, "Mean of 200% multiplier must converge to 2.0 (+/- 0.05)");

        // 10,000 trials of 150% multiplier on count 1 -> mean converges to 1.5 (+/- 0.05)
        long sum150 = 0;
        for (int i = 0; i < trials; i++) {
            sum150 += OreLogic.getAmplifiedCount(1, 150, random);
        }
        double mean150 = (double) sum150 / trials;
        assertEquals(1.5, mean150, 0.05, "Mean of 150% multiplier must converge to 1.5 (+/- 0.05)");
    }

    @Test
    @DisplayName("Pillar 4: Sparse Delta Config Lifecycle and Pruning")
    void testSparseDeltaLifecycle() {
        OreAmplifierConfig config = new OreAmplifierConfig();
        config.setOreOverride("mod:custom_ore", 300);
        config.setOreOverride("mod:default_ore", 100);
        config.setOreOverride("mod:disabled_ore", 0);
        config.setOreOverride("mod:negative_ore", -50);

        assertEquals(300, config.getOreOverride("mod:custom_ore"));
        assertEquals(-1, config.getOreOverride("mod:default_ore"), "100% default override must not be stored in map");
        assertEquals(-1, config.getOreOverride("mod:disabled_ore"), "0% override must not be stored in map");
        assertEquals(-1, config.getOreOverride("mod:negative_ore"), "Negative override must not be stored in map");

        config.pruneDefaults();
        assertTrue(config.perOreMultipliers.containsKey("mod:custom_ore"), "Custom 300% override must be retained");
        assertFalse(config.perOreMultipliers.containsKey("mod:default_ore"), "100% override must be absent from map");
        assertFalse(config.perOreMultipliers.containsKey("mod:disabled_ore"), "0% override must be absent from map");
        assertFalse(config.perOreMultipliers.containsKey("mod:negative_ore"), "Negative override must be absent from map");
    }

    @Test
    @DisplayName("Pillar 4: UniversalConfigScreen Reflection Discovery")
    void testUniversalConfigScreenDiscovery() throws Exception {
        java.lang.reflect.Method method = net.dasik.social.api.client.gui.UniversalConfigScreen.class.getDeclaredMethod("discoverConfigFields", Class.class);
        method.setAccessible(true);
        @SuppressWarnings("unchecked")
        java.util.List<java.lang.reflect.Field> fields = (java.util.List<java.lang.reflect.Field>) method.invoke(null, OreAmplifierConfig.class);
        assertEquals(3, fields.size(), "Should discover exactly 3 @ConfigOption annotated fields");
    }
}
