// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.oreamplifier.mixin;

import net.instantgratification.oreamplifier.OreAmplifierFabric;
import net.minecraft.world.level.levelgen.feature.OreFeature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(OreFeature.class)
public class OreFeatureMixin {

    @Redirect(method = "place", at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/levelgen/feature/configurations/OreConfiguration;size:I"))
    private int modded$scaleOreVeinSize(OreConfiguration config, FeaturePlaceContext<OreConfiguration> context) {
        int originalSize = config.size;
        if (context != null && context.level() != null) {
            try {
                var level = context.level().getLevel();
                if (level != null && OreAmplifierFabric.IG_ORE_VEIN_SIZE != null) {
                    int multiplier = level.getGameRules().get(OreAmplifierFabric.IG_ORE_VEIN_SIZE);
                    if (multiplier > 100) {
                        return Math.round(originalSize * (multiplier / 100.0f));
                    }
                }
            } catch (Exception ignored) {
                // Fallback gracefully to original vein size if level/gamerules not available
            }
        }
        return originalSize;
    }
}
