// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.oreamplifier;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.gui.controllers.slider.IntegerSliderController;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.List;

public class YaclScreenHelper {
    public static ConfigScreenFactory<?> createScreen() {
        return YaclScreenHelper::buildScreen;
    }

    private static Screen buildScreen(Screen parent) {
        OreAmplifierConfig config = OreAmplifierConfig.get();

        OptionGroup.Builder perOreGroup = OptionGroup.createBuilder()
                .name(Component.translatable("config.ore-amplifier.group.per_ore"));

        // Major Vanilla Ores list
        List<String> coreOres = List.of(
                "minecraft:iron_ore",
                "minecraft:deepslate_iron_ore",
                "minecraft:coal_ore",
                "minecraft:deepslate_coal_ore",
                "minecraft:copper_ore",
                "minecraft:deepslate_copper_ore",
                "minecraft:gold_ore",
                "minecraft:deepslate_gold_ore",
                "minecraft:redstone_ore",
                "minecraft:deepslate_redstone_ore",
                "minecraft:lapis_ore",
                "minecraft:deepslate_lapis_ore",
                "minecraft:emerald_ore",
                "minecraft:deepslate_emerald_ore",
                "minecraft:diamond_ore",
                "minecraft:deepslate_diamond_ore",
                "minecraft:nether_quartz_ore",
                "minecraft:ancient_debris"
        );

        for (String oreId : coreOres) {
            String readableName = generateReadableOreName(oreId);
            perOreGroup.option(Option.<Integer>createBuilder()
                    .name(Component.literal(readableName))
                    .description(OptionDescription.of(Component.literal("Custom multiplier for " + readableName + " (0% = global fallback, 200% = 2x).")))
                    .binding(
                            0,
                            () -> Math.max(0, config.getOreOverride(oreId)),
                            val -> config.setOreOverride(oreId, val == 0 ? -1 : val)
                    )
                    .customController(opt -> new IntegerSliderController(opt, 0, 1000, 10))
                    .build());
        }

        return YetAnotherConfigLib.createBuilder()
                .title(Component.translatable("config.ore-amplifier.title"))
                .category(ConfigCategory.createBuilder()
                        .name(Component.translatable("config.ore-amplifier.category.general"))
                        .group(OptionGroup.createBuilder()
                                .name(Component.translatable("config.ore-amplifier.group.options"))
                                .option(LabelOption.create(Component.translatable("config.ore-amplifier.warning")))
                                .option(Option.<Integer>createBuilder()
                                        .name(Component.translatable("config.ore-amplifier.vanillaGlobalMultiplier"))
                                        .description(OptionDescription.of(Component.translatable("config.ore-amplifier.vanillaGlobalMultiplier.description")))
                                        .binding(
                                                200,
                                                () -> config.vanillaGlobalMultiplier,
                                                val -> config.vanillaGlobalMultiplier = val
                                        )
                                        .customController(opt -> new IntegerSliderController(opt, 1, 1000, 10))
                                        .build())
                                .option(Option.<Integer>createBuilder()
                                        .name(Component.translatable("config.ore-amplifier.moddedGlobalMultiplier"))
                                        .description(OptionDescription.of(Component.translatable("config.ore-amplifier.moddedGlobalMultiplier.description")))
                                        .binding(
                                                120,
                                                () -> config.moddedGlobalMultiplier,
                                                val -> config.moddedGlobalMultiplier = val
                                        )
                                        .customController(opt -> new IntegerSliderController(opt, 1, 1000, 10))
                                        .build())
                                .option(Option.<Integer>createBuilder()
                                        .name(Component.translatable("config.ore-amplifier.veinSizeMultiplier"))
                                        .description(OptionDescription.of(Component.translatable("config.ore-amplifier.veinSizeMultiplier.description")))
                                        .binding(
                                                100,
                                                () -> config.veinSizeMultiplier,
                                                val -> config.veinSizeMultiplier = val
                                        )
                                        .customController(opt -> new IntegerSliderController(opt, 100, 500, 10))
                                        .build())
                                .build())
                        .build())
                .category(ConfigCategory.createBuilder()
                        .name(Component.translatable("config.ore-amplifier.category.per_ore"))
                        .group(perOreGroup.build())
                        .build())
                .save(OreAmplifierConfig::save)
                .build()
                .generateScreen(parent);
    }

    private static String generateReadableOreName(String oreId) {
        String path = oreId.contains(":") ? oreId.split(":")[1] : oreId;
        String[] words = path.split("_");
        StringBuilder sb = new StringBuilder();
        for (String w : words) {
            if (!w.isEmpty()) {
                sb.append(Character.toUpperCase(w.charAt(0))).append(w.substring(1)).append(" ");
            }
        }
        return sb.toString().trim();
    }
}
