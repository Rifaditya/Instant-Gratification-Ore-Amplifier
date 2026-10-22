// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.oreamplifier;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.GameRules;
import net.dasik.social.api.gamerule.DynamicGameRuleManager;
import net.dasik.social.api.config.DasikSupportHelper;

import java.util.concurrent.atomic.AtomicInteger;

public class OreCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("oreamp")
                .executes(context -> {
                    context.getSource().sendSuccess(
                            () -> Component.literal("§e[Ore Amplifier]§r Use §a/oreamp help§r for command list."),
                            false);
                    return 1;
                })
                .then(Commands.literal("help")
                        .executes(context -> {
                            CommandSourceStack source = context.getSource();
                            source.sendSuccess(() -> Component.literal(
                                    "§e--- Ore Amplifier Commands ---§r\n" +
                                    "§a/oreamp status§r - Show current global and override stats\n" +
                                    "§a/oreamp get global§r - View vanilla & modded global multipliers\n" +
                                    "§a/oreamp get <ore_id>§r - Query multiplier for a specific ore\n" +
                                    "§a/oreamp set global <vanilla|modded> <val>§r - Set global multiplier (0-1000%)\n" +
                                    "§a/oreamp set <ore_id> <val>§r - Set specific ore multiplier (0-1000%)\n" +
                                    "§a/oreamp reset§r - Reset all multipliers back to 100% (Default)\n" +
                                    "§a/oreamp reload§r - Reload configuration templates"
                            ), false);
                            source.sendSuccess(DasikSupportHelper::getCommandFooter, false);
                            return 1;
                        }))
                .then(Commands.literal("status")
                        .executes(context -> {
                            CommandSourceStack source = context.getSource();
                            MinecraftServer server = source.getServer();
                            GameRules rules = server != null && server.overworld() != null ? server.overworld().getGameRules() : source.getLevel().getGameRules();

                            int vanillaGlobal = rules.getInt(OreAmplifierFabric.IG_ORE_VANILLA_GLOBAL);
                            int moddedGlobal = rules.getInt(OreAmplifierFabric.IG_ORE_MODDED_GLOBAL);
                            int veinSize = rules.getInt(OreAmplifierFabric.IG_ORE_VEIN_SIZE);

                            AtomicInteger activeOverrides = new AtomicInteger(0);
                            DynamicGameRuleManager.getDynamicRules().forEach((key, rule) -> {
                                if (key.startsWith("ig:ore_")) {
                                    @SuppressWarnings("unchecked")
                                    GameRules.Key<GameRules.IntegerValue> intRule = (GameRules.Key<GameRules.IntegerValue>) rule;
                                    if (rules.getInt(intRule) != 100) {
                                        activeOverrides.incrementAndGet();
                                    }
                                }
                            });

                            source.sendSuccess(() -> Component.literal(
                                    "§e[Ore Amplifier Status]§r\n" +
                                    " • Vanilla Global Multiplier: §a" + vanillaGlobal + "%§r\n" +
                                    " • Modded Global Multiplier: §a" + moddedGlobal + "%§r\n" +
                                    " • Vein Size Multiplier: §a" + veinSize + "%§r\n" +
                                    " • Custom Per-Ore Overrides: §a" + activeOverrides.get() + " active§r"
                            ), false);
                            source.sendSuccess(DasikSupportHelper::getCommandFooter, false);
                            return 1;
                        }))
                .then(Commands.literal("get")
                        .then(Commands.literal("global")
                                 .executes(context -> {
                                    CommandSourceStack source = context.getSource();
                                    GameRules rules = source.getLevel().getGameRules();
                                    int vanilla = rules.getInt(OreAmplifierFabric.IG_ORE_VANILLA_GLOBAL);
                                    int modded = rules.getInt(OreAmplifierFabric.IG_ORE_MODDED_GLOBAL);
                                    int veinSize = rules.getInt(OreAmplifierFabric.IG_ORE_VEIN_SIZE);

                                    source.sendSuccess(() -> Component.literal(
                                            "§e[Ore Amplifier Globals]§r\n" +
                                            " • Vanilla Global: §a" + vanilla + "%§r\n" +
                                            " • Modded Global: §a" + modded + "%§r\n" +
                                            " • Vein Size: §a" + veinSize + "%§r"
                                    ), false);
                                    return 1;
                                }))
                        .then(Commands.argument("ore", ResourceLocationArgument.id())
                                .suggests((context, builder) -> SharedSuggestionProvider.suggestResource(
                                        BuiltInRegistries.BLOCK.keySet().stream().filter(OreLogic::shouldAmplify), builder))
                                .executes(context -> {
                                    CommandSourceStack source = context.getSource();
                                    ResourceLocation id = ResourceLocationArgument.getId(context, "ore");

                                    if (!OreLogic.shouldAmplify(id)) {
                                        source.sendFailure(Component.literal("§c" + id + " is not recognized as an amplifiable ore block.§r"));
                                        return 0;
                                    }

                                    int multiplier = OreLogic.getMultiplier(id, source.getLevel().getGameRules());
                                    String details = OreLogic.getMultiplierDetails(id, source.getLevel().getGameRules());

                                    source.sendSuccess(() -> Component.literal(
                                            "§e[Ore Multiplier]§r §a" + id + "§r: §b" + multiplier + "%§r (" + details + ")"
                                    ), false);
                                    return 1;
                                })))
                .then(Commands.literal("set")
                        .requires(source -> source.hasPermission(2))
                        .then(Commands.literal("global")
                                .then(Commands.literal("vanilla")
                                        .then(Commands.argument("multiplier", IntegerArgumentType.integer(0, 1000))
                                                .executes(context -> {
                                                    CommandSourceStack source = context.getSource();
                                                    int val = IntegerArgumentType.getInteger(context, "multiplier");
                                                    source.getLevel().getGameRules().getRule(OreAmplifierFabric.IG_ORE_VANILLA_GLOBAL).set(val, source.getServer());
                                                    OreAmplifierConfig.get().vanillaGlobalMultiplier = val;
                                                    OreAmplifierConfig.save();
                                                    source.sendSuccess(() -> Component.literal("§e[Ore Amplifier]§r Vanilla Global set to §a" + val + "%§r"), true);
                                                    return 1;
                                                })))
                                .then(Commands.literal("modded")
                                        .then(Commands.argument("multiplier", IntegerArgumentType.integer(0, 1000))
                                                .executes(context -> {
                                                    CommandSourceStack source = context.getSource();
                                                    int val = IntegerArgumentType.getInteger(context, "multiplier");
                                                    source.getLevel().getGameRules().getRule(OreAmplifierFabric.IG_ORE_MODDED_GLOBAL).set(val, source.getServer());
                                                    OreAmplifierConfig.get().moddedGlobalMultiplier = val;
                                                    OreAmplifierConfig.save();
                                                    source.sendSuccess(() -> Component.literal("§e[Ore Amplifier]§r Modded Global set to §a" + val + "%§r"), true);
                                                    return 1;
                                                }))))
                        .then(Commands.argument("ore", ResourceLocationArgument.id())
                                .suggests((context, builder) -> SharedSuggestionProvider.suggestResource(
                                        BuiltInRegistries.BLOCK.keySet().stream().filter(OreLogic::shouldAmplify), builder))
                                .then(Commands.argument("multiplier", IntegerArgumentType.integer(0, 1000))
                                        .executes(context -> {
                                            CommandSourceStack source = context.getSource();
                                            ResourceLocation id = ResourceLocationArgument.getId(context, "ore");
                                            int val = IntegerArgumentType.getInteger(context, "multiplier");

                                            if (!OreLogic.shouldAmplify(id)) {
                                                source.sendFailure(Component.literal("§c" + id + " is not recognized as an amplifiable ore block.§r"));
                                                return 0;
                                            }

                                            OreLogic.setMultiplier(id, val, source.getServer());
                                            OreAmplifierConfig.get().setOreOverride(id.toString(), val);
                                            OreAmplifierConfig.save();
                                            source.sendSuccess(() -> Component.literal("§e[Ore Amplifier]§r Multiplier for §a" + id + "§r set to §b" + val + "%§r"), true);
                                            return 1;
                                        }))))
                .then(Commands.literal("reload")
                        .requires(source -> source.hasPermission(2))
                        .executes(context -> {
                            OreAmplifierConfig.load(net.fabricmc.loader.api.FabricLoader.getInstance().getConfigDir());
                            CommandSourceStack source = context.getSource();
                            if (source.getServer() != null) {
                                GameRules rules = source.getLevel().getGameRules();
                                rules.getRule(OreAmplifierFabric.IG_ORE_VANILLA_GLOBAL).set(OreAmplifierConfig.get().vanillaGlobalMultiplier, source.getServer());
                                rules.getRule(OreAmplifierFabric.IG_ORE_MODDED_GLOBAL).set(OreAmplifierConfig.get().moddedGlobalMultiplier, source.getServer());
                                rules.getRule(OreAmplifierFabric.IG_ORE_VEIN_SIZE).set(OreAmplifierConfig.get().veinSizeMultiplier, source.getServer());
                            }
                            context.getSource().sendSuccess(() -> Component.literal("§e[Ore Amplifier]§r Configuration templates reloaded & GameRules synced!"), true);
                            return 1;
                        }))
                .then(Commands.literal("reset")
                        .requires(source -> source.hasPermission(2))
                        .executes(context -> {
                            CommandSourceStack source = context.getSource();
                            MinecraftServer server = source.getServer();
                            GameRules rules = server != null && server.overworld() != null ? server.overworld().getGameRules() : source.getLevel().getGameRules();

                            // 1. Reset GameRules
                            rules.getRule(OreAmplifierFabric.IG_ORE_VANILLA_GLOBAL).set(200, server);
                            rules.getRule(OreAmplifierFabric.IG_ORE_MODDED_GLOBAL).set(120, server);
                            rules.getRule(OreAmplifierFabric.IG_ORE_VEIN_SIZE).set(100, server);

                            DynamicGameRuleManager.getDynamicRules().entrySet().forEach(entry -> {
                                if (entry.getKey().startsWith("ig:ore_")) {
                                    @SuppressWarnings("unchecked")
                                    GameRules.Key<GameRules.IntegerValue> intRule = (GameRules.Key<GameRules.IntegerValue>) entry.getValue();
                                    rules.getRule(intRule).set(100, server);
                                }
                            });

                            // 2. Reset JSON Config Template
                            OreAmplifierConfig.get().vanillaGlobalMultiplier = 200;
                            OreAmplifierConfig.get().moddedGlobalMultiplier = 120;
                            OreAmplifierConfig.get().veinSizeMultiplier = 100;
                            OreAmplifierConfig.get().perOreMultipliers.clear();
                            OreAmplifierConfig.save();

                            source.sendSuccess(
                                    () -> Component
                                            .literal("§e[Ore Amplifier]§r All multipliers reset to defaults (§a200% Vanilla§r / §a120% Modded§r / §a100% Vein Size§r)."),
                                    true);
                            return 1;
                        })));
    }
}
