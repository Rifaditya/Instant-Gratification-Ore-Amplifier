package net.instantgratification.oreamplifier;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.Commands;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.gamerules.GameRule;
import net.dasik.social.api.gamerule.DynamicGameRuleManager;

public class OreCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("oreamp")
                // .requires(source -> source.hasPermission(2))
                .executes(context -> {
                    context.getSource().sendSuccess(
                            () -> Component.literal("Ore Amplifier: Use /oreamp help for commands"),
                            false);
                    return 1;
                })
                .then(Commands.literal("reload")
                        .executes(context -> {
                            // Config reload logic if needed
                            context.getSource().sendSuccess(() -> Component.literal("Configuration reloaded!"), true);
                            return 1;
                        }))
                .then(Commands.literal("reset")
                        .executes(context -> {
                            CommandSourceStack source = context.getSource();
                            MinecraftServer server = source.getServer();
                            GameRules rules = server.getGameRules();
                            
                            // 1. Reset Globals
                            rules.set(OreAmplifierFabric.IG_ORE_VANILLA_GLOBAL, 100, server);
                            rules.set(OreAmplifierFabric.IG_ORE_MODDED_GLOBAL, 100, server);
                            
                            // 2. Reset Dynamics
                            DynamicGameRuleManager.getDynamicRules().entrySet().forEach(entry -> {
                                if (entry.getKey().startsWith("ig_ore_")) {
                                    @SuppressWarnings("unchecked")
                                    GameRule<Integer> intRule = (GameRule<Integer>) entry.getValue();
                                    rules.set(intRule, 100, server);
                                }
                            });
                            
                            source.sendSuccess(() -> Component.literal("Ore Amplifier: All multipliers have been reset to 100 (Normal)."), true);
                            return 1;
                        })));
    }
}
