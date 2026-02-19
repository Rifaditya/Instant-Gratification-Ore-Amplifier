package net.instantgratification.oreamplifier;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.Commands;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;

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
                        })));
    }
}
