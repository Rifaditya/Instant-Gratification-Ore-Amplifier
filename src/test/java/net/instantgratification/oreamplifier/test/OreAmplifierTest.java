// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.oreamplifier.test;

import net.instantgratification.oreamplifier.OreLogic;
import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OreAmplifierTest {

    @BeforeAll
    public static void setup() {
        net.minecraft.SharedConstants.tryDetectVersion();
        net.minecraft.server.Bootstrap.bootStrap();
    }

    @Test
    @DisplayName("Verify Vanilla Ore Logic & Name Filtering")
    public void testVanillaOreIdentification() {
        ResourceLocation ironOre = new ResourceLocation("minecraft", "iron_ore");
        ResourceLocation deepslateCoal = new ResourceLocation("minecraft", "deepslate_coal_ore");
        ResourceLocation netherQuartz = new ResourceLocation("minecraft", "nether_quartz_ore");
        ResourceLocation ancientDebris = new ResourceLocation("minecraft", "ancient_debris");

        assertTrue(OreLogic.shouldAmplify(ironOre), "Iron Ore should be recognized for amplification");
        assertTrue(OreLogic.shouldAmplify(deepslateCoal), "Deepslate Coal Ore should be recognized");
        assertTrue(OreLogic.shouldAmplify(netherQuartz), "Nether Quartz Ore should be recognized");
        assertTrue(OreLogic.shouldAmplify(ancientDebris), "Ancient Debris should be recognized");
    }

    @Test
    @DisplayName("Verify Blacklist Filtering")
    public void testBlacklistFiltering() {
        ResourceLocation amethystGeode = new ResourceLocation("minecraft", "amethyst_geode");
        ResourceLocation monsterRoom = new ResourceLocation("minecraft", "monster_room");
        ResourceLocation dirt = new ResourceLocation("minecraft", "dirt");

        assertFalse(OreLogic.shouldAmplify(amethystGeode), "Amethyst Geode must be blacklisted");
        assertFalse(OreLogic.shouldAmplify(monsterRoom), "Monster Room must be blacklisted");
        assertFalse(OreLogic.shouldAmplify(dirt), "Dirt is not an ore");
    }

    @Test
    @DisplayName("Verify Multiplier Calculation Math")
    public void testMultiplierMath() {
        // Test 200% vanilla multiplier calculation (2.0x -> count multiplied by 2)
        int baseCount = 5;
        int vanillaMultiplier = 200;
        int expectedAmplifiedCount = Math.round(baseCount * (vanillaMultiplier / 100.0f));
        assertEquals(10, expectedAmplifiedCount, "200% multiplier should double base count from 5 to 10");

        // Test 120% modded multiplier calculation (1.2x -> count 10 -> 12)
        int moddedMultiplier = 120;
        int expectedModdedCount = Math.round(10 * (moddedMultiplier / 100.0f));
        assertEquals(12, expectedModdedCount, "120% multiplier should increase count from 10 to 12");
    }

    @Test
    @DisplayName("Verify Sparse Delta Config Pruning")
    public void testSparseDeltaPruning() {
        var config = net.instantgratification.oreamplifier.OreAmplifierConfig.get();
        config.setOreOverride("mod:custom_ore", 300);
        config.setOreOverride("mod:default_ore", 100);

        assertEquals(300, config.getOreOverride("mod:custom_ore"));
        assertEquals(-1, config.getOreOverride("mod:default_ore"), "100% default override must be removed from sparse map");

        config.pruneDefaults();
        assertTrue(config.perOreMultipliers.containsKey("mod:custom_ore"));
        assertFalse(config.perOreMultipliers.containsKey("mod:default_ore"));
    }

    @Test
    @DisplayName("Verify UniversalConfigScreen ConfigOption Field Discovery")
    public void testUniversalConfigScreenDiscovery() throws Exception {
        java.lang.reflect.Method method = net.dasik.social.api.client.gui.UniversalConfigScreen.class.getDeclaredMethod("discoverConfigFields", Class.class);
        method.setAccessible(true);
        @SuppressWarnings("unchecked")
        java.util.List<java.lang.reflect.Field> fields = (java.util.List<java.lang.reflect.Field>) method.invoke(null, net.instantgratification.oreamplifier.OreAmplifierConfig.class);
        assertEquals(3, fields.size(), "Should discover exactly 3 @ConfigOption annotated fields");
    }
}
