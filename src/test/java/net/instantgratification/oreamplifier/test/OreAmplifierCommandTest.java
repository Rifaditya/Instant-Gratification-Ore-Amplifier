// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.oreamplifier.test;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.CommandNode;
import net.dasik.social.api.gamerule.DynamicGameRuleManager;
import net.instantgratification.oreamplifier.OreAmplifierFabric;
import net.instantgratification.oreamplifier.OreCommand;
import net.instantgratification.oreamplifier.OreLogic;
import net.minecraft.SharedConstants;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.Bootstrap;
import net.minecraft.server.permissions.PermissionSet;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pillar 3: Brigadier AST & GameRule Verification Suite for Ore Amplifier.
 * Asserts command syntax tree structure, permission levels (OP level 2 vs guest level 0),
 * static GameRule handles, and blacklist tag keys.
 */
public class OreAmplifierCommandTest {

    @BeforeAll
    public static void setup() {
        SharedConstants.tryDetectVersion();
        Bootstrap.bootStrap();

        if (OreAmplifierFabric.ORE_AMPLIFIER_CATEGORY == null) {
            OreAmplifierFabric.ORE_AMPLIFIER_CATEGORY = DynamicGameRuleManager.registerCategory(
                    Identifier.fromNamespaceAndPath("minecraft", "ore_amplifier"));
        }
        if (OreAmplifierFabric.IG_ORE_VANILLA_GLOBAL == null) {
            OreAmplifierFabric.IG_ORE_VANILLA_GLOBAL = DynamicGameRuleManager.integerRule(
                    "ig:ore_vanilla_global", OreAmplifierFabric.ORE_AMPLIFIER_CATEGORY, 200)
                    .range(1, 1000).name("Vanilla Ore Multiplier").description("Global multiplier for vanilla ores.").register();
        }
        if (OreAmplifierFabric.IG_ORE_MODDED_GLOBAL == null) {
            OreAmplifierFabric.IG_ORE_MODDED_GLOBAL = DynamicGameRuleManager.integerRule(
                    "ig:ore_modded_global", OreAmplifierFabric.ORE_AMPLIFIER_CATEGORY, 120)
                    .range(1, 1000).name("Modded Ore Multiplier").description("Global multiplier for modded ores.").register();
        }
        if (OreAmplifierFabric.IG_ORE_VEIN_SIZE == null) {
            OreAmplifierFabric.IG_ORE_VEIN_SIZE = DynamicGameRuleManager.integerRule(
                    "ig:ore_vein_size_multiplier", OreAmplifierFabric.ORE_AMPLIFIER_CATEGORY, 100)
                    .range(100, 500).name("Ore Vein Size Multiplier").description("Multiplier for vein size.").register();
        }
    }

    private static CommandSourceStack createSource(PermissionSet perms, String name) {
        return new CommandSourceStack(
                CommandSource.NULL,
                Vec3.ZERO,
                Vec2.ZERO,
                null,
                perms,
                Component.literal(name),
                null
        );
    }

    @Test
    @DisplayName("Pillar 3: Brigadier AST Registration and Subcommand Hierarchy")
    void testCommandTreeHierarchy() {
        CommandDispatcher<CommandSourceStack> dispatcher = new CommandDispatcher<>();
        OreCommand.register(dispatcher);

        CommandNode<CommandSourceStack> root = dispatcher.getRoot().getChild("oreamp");
        assertNotNull(root, "Root literal /oreamp must be registered in dispatcher");

        String[] subcommands = {"help", "status", "get", "set", "reload", "reset"};
        for (String sub : subcommands) {
            CommandNode<CommandSourceStack> child = root.getChild(sub);
            assertNotNull(child, "Subcommand /oreamp " + sub + " must exist in tree");
        }
    }

    @Test
    @DisplayName("Pillar 3: Permission Gating Verification (Gamemaster Level 2 vs Guest Level 0)")
    void testPermissionGating() {
        CommandDispatcher<CommandSourceStack> dispatcher = new CommandDispatcher<>();
        OreCommand.register(dispatcher);

        CommandNode<CommandSourceStack> root = dispatcher.getRoot().getChild("oreamp");
        assertNotNull(root);

        CommandSourceStack adminSource = createSource(PermissionSet.ALL_PERMISSIONS, "AdminTester");
        CommandSourceStack unprivilegedSource = createSource(PermissionSet.NO_PERMISSIONS, "GuestTester");

        // Level 2 (Gamemasters) commands: set, reload, reset
        CommandNode<CommandSourceStack> setNode = root.getChild("set");
        CommandNode<CommandSourceStack> reloadNode = root.getChild("reload");
        CommandNode<CommandSourceStack> resetNode = root.getChild("reset");

        assertTrue(setNode.getRequirement().test(adminSource), "Admin must pass 'set' permission check");
        assertFalse(setNode.getRequirement().test(unprivilegedSource), "Guest must fail 'set' permission check");

        assertTrue(reloadNode.getRequirement().test(adminSource), "Admin must pass 'reload' permission check");
        assertFalse(reloadNode.getRequirement().test(unprivilegedSource), "Guest must fail 'reload' permission check");

        assertTrue(resetNode.getRequirement().test(adminSource), "Admin must pass 'reset' permission check");
        assertFalse(resetNode.getRequirement().test(unprivilegedSource), "Guest must fail 'reset' permission check");

        // Level 0 (All players) commands: help, status, get
        CommandNode<CommandSourceStack> helpNode = root.getChild("help");
        CommandNode<CommandSourceStack> statusNode = root.getChild("status");
        CommandNode<CommandSourceStack> getNode = root.getChild("get");

        assertTrue(helpNode.getRequirement().test(unprivilegedSource), "Guest must pass 'help' check");
        assertTrue(statusNode.getRequirement().test(unprivilegedSource), "Guest must pass 'status' check");
        assertTrue(getNode.getRequirement().test(unprivilegedSource), "Guest must pass 'get' check");
    }

    @Test
    @DisplayName("Pillar 3: Static GameRule Handles Verification")
    void testStaticGameRuleHandles() {
        assertNotNull(OreAmplifierFabric.ORE_AMPLIFIER_CATEGORY, "ORE_AMPLIFIER_CATEGORY must be registered");
        assertNotNull(OreAmplifierFabric.IG_ORE_VANILLA_GLOBAL, "IG_ORE_VANILLA_GLOBAL must be registered");
        assertNotNull(OreAmplifierFabric.IG_ORE_MODDED_GLOBAL, "IG_ORE_MODDED_GLOBAL must be registered");
        assertNotNull(OreAmplifierFabric.IG_ORE_VEIN_SIZE, "IG_ORE_VEIN_SIZE must be registered");
    }

    @Test
    @DisplayName("Pillar 3: Conventional and Modded Blacklist Tag Key Integrity")
    void testBlacklistTagKeys() {
        assertNotNull(OreLogic.CONVENTION_BLACKLIST_FEATURE);
        assertEquals("c", OreLogic.CONVENTION_BLACKLIST_FEATURE.location().getNamespace());
        assertEquals("ore_amplifier_blacklist", OreLogic.CONVENTION_BLACKLIST_FEATURE.location().getPath());

        assertNotNull(OreLogic.MOD_BLACKLIST_FEATURE);
        assertEquals("ore-amplifier", OreLogic.MOD_BLACKLIST_FEATURE.location().getNamespace());
        assertEquals("blacklist", OreLogic.MOD_BLACKLIST_FEATURE.location().getPath());

        assertNotNull(OreLogic.CONVENTION_BLACKLIST_BLOCK);
        assertEquals("c", OreLogic.CONVENTION_BLACKLIST_BLOCK.location().getNamespace());
        assertEquals("ore_amplifier_blacklist", OreLogic.CONVENTION_BLACKLIST_BLOCK.location().getPath());

        assertNotNull(OreLogic.MOD_BLACKLIST_BLOCK);
        assertEquals("ore-amplifier", OreLogic.MOD_BLACKLIST_BLOCK.location().getNamespace());
        assertEquals("blacklist", OreLogic.MOD_BLACKLIST_BLOCK.location().getPath());
    }
}
