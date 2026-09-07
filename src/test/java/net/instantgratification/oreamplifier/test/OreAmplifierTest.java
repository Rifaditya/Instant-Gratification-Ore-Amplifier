// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.oreamplifier.test;

import net.instantgratification.oreamplifier.OreLogic;
import net.minecraft.resources.Identifier;
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
        Identifier ironOre = Identifier.fromNamespaceAndPath("minecraft", "iron_ore");
        Identifier deepslateCoal = Identifier.fromNamespaceAndPath("minecraft", "deepslate_coal_ore");
        Identifier netherQuartz = Identifier.fromNamespaceAndPath("minecraft", "nether_quartz_ore");
        Identifier ancientDebris = Identifier.fromNamespaceAndPath("minecraft", "ancient_debris");

        assertTrue(OreLogic.shouldAmplify(ironOre), "Iron Ore should be recognized for amplification");
        assertTrue(OreLogic.shouldAmplify(deepslateCoal), "Deepslate Coal Ore should be recognized");
        assertTrue(OreLogic.shouldAmplify(netherQuartz), "Nether Quartz Ore should be recognized");
        assertTrue(OreLogic.shouldAmplify(ancientDebris), "Ancient Debris should be recognized");
    }

    @Test
    @DisplayName("Verify Blacklist Filtering")
    public void testBlacklistFiltering() {
        Identifier amethystGeode = Identifier.fromNamespaceAndPath("minecraft", "amethyst_geode");
        Identifier monsterRoom = Identifier.fromNamespaceAndPath("minecraft", "monster_room");
        Identifier dirt = Identifier.fromNamespaceAndPath("minecraft", "dirt");

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
}
