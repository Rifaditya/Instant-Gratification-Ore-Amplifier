// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.oreamplifier;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import java.util.List;
import net.dasik.social.api.command.GameRuleCommandBridge;
import net.dasik.social.api.command.ModCommandDispatcher;
import net.dasik.social.api.command.UniversalCommandRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.GameRules;

public final class OreCommand {
    public static final String ROOT_LITERAL = "oreamp";
    public static final String MOD_NAME = "Ore Amplifier";

    private static volatile GameRuleCommandBridge activeBridge;

    private OreCommand() {}

    public static void init() {
        ModCommandDispatcher.init();
        activeBridge = createBridge();
        ModCommandDispatcher.registerSuite(ROOT_LITERAL, builder -> configureBuilder(builder, activeBridge));
    }

    public static UniversalCommandRegistry.Builder configureBuilder(UniversalCommandRegistry.Builder builder, GameRuleCommandBridge bridge) {
        return builder
            .withRootLiteral(ROOT_LITERAL)
            .withModName(MOD_NAME)
            .withBridge(bridge)
            .withStatusSupplier(level -> {
                int vanilla = OreAmplifierConfig.get().vanillaGlobalMultiplier;
                int modded = OreAmplifierConfig.get().moddedGlobalMultiplier;
                int veinSize = OreAmplifierConfig.get().veinSizeMultiplier;
                int activeOverrides = OreAmplifierConfig.get().perOreMultipliers.size();
                return List.of(
                    "Vanilla Global Multiplier: " + vanilla + "%",
                    "Modded Global Multiplier: " + modded + "%",
                    "Vein Size Multiplier: " + veinSize + "%",
                    "Custom Per-Ore Overrides: " + activeOverrides + " active"
                );
            })
            .withReloadAction(server -> {
                OreAmplifierConfig.load(FabricLoader.getInstance().getConfigDir());
                if (server != null && server.overworld() != null) {
                    GameRules rules = server.overworld().getGameRules();
                    rules.getRule(OreAmplifierFabric.IG_ORE_VANILLA_GLOBAL).set(OreAmplifierConfig.get().vanillaGlobalMultiplier, server);
                    rules.getRule(OreAmplifierFabric.IG_ORE_MODDED_GLOBAL).set(OreAmplifierConfig.get().moddedGlobalMultiplier, server);
                    rules.getRule(OreAmplifierFabric.IG_ORE_VEIN_SIZE).set(OreAmplifierConfig.get().veinSizeMultiplier, server);
                }
            })
            .withCustomSubcommand(Commands.literal("ore")
                .then(Commands.argument("ore", ResourceLocationArgument.id())
                    .suggests((context, suggestionsBuilder) -> SharedSuggestionProvider.suggestResource(
                        BuiltInRegistries.BLOCK.keySet().stream().filter(OreLogic::shouldAmplify), suggestionsBuilder))
                    .executes(context -> {
                        CommandSourceStack source = context.getSource();
                        ResourceLocation id = ResourceLocationArgument.getId(context, "ore");
                        if (!OreLogic.shouldAmplify(id)) {
                            source.sendFailure(Component.literal("§c" + id + " is not recognized as an amplifiable ore block.§r"));
                            return 0;
                        }
                        GameRules rules = source.getLevel() != null ? source.getLevel().getGameRules() : null;
                        int mult = OreLogic.getMultiplier(id, rules);
                        String details = OreLogic.getMultiplierDetails(id, rules);
                        source.sendSuccess(() -> Component.literal("§e[Ore Multiplier]§r §a" + id + "§r: §b" + mult + "%§r (" + details + ")"), false);
                        return 1;
                    })
                    .then(Commands.argument("multiplier", IntegerArgumentType.integer(0, 1000))
                        .requires(source -> source.hasPermission(2))
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
                        }))));
    }

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        if (activeBridge == null) {
            activeBridge = createBridge();
        }
        configureBuilder(UniversalCommandRegistry.builder(ROOT_LITERAL, dispatcher), activeBridge).register();
    }

    public static GameRuleCommandBridge createBridge() {
        GameRuleCommandBridge bridge = new GameRuleCommandBridge();
        OreAmplifierConfig config = OreAmplifierConfig.get();

        bridge.registerInteger("vanilla", OreAmplifierFabric.IG_ORE_VANILLA_GLOBAL, config.vanillaGlobalMultiplier, 1, 1000);
        bridge.registerInteger("modded", OreAmplifierFabric.IG_ORE_MODDED_GLOBAL, config.moddedGlobalMultiplier, 1, 1000);
        bridge.registerInteger("vein_size", OreAmplifierFabric.IG_ORE_VEIN_SIZE, config.veinSizeMultiplier, 100, 500);

        bridge.setSaveCallback(server -> {
            Object v = bridge.getRuleEntry("vanilla") != null ? bridge.getRuleEntry("vanilla").getCurrentValue() : null;
            if (v instanceof Number n) config.vanillaGlobalMultiplier = n.intValue();

            Object m = bridge.getRuleEntry("modded") != null ? bridge.getRuleEntry("modded").getCurrentValue() : null;
            if (m instanceof Number n) config.moddedGlobalMultiplier = n.intValue();

            Object s = bridge.getRuleEntry("vein_size") != null ? bridge.getRuleEntry("vein_size").getCurrentValue() : null;
            if (s instanceof Number n) config.veinSizeMultiplier = n.intValue();

            OreAmplifierConfig.save();
        });

        return bridge;
    }

    public static GameRuleCommandBridge getBridge() {
        if (activeBridge == null) {
            activeBridge = createBridge();
        }
        return activeBridge;
    }
}
