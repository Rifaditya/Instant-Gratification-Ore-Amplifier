// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.oreamplifier.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.instantgratification.oreamplifier.OreAmplifierFabric;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.OreFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(OreFeature.class)
public class OreFeatureMixin {

    @ModifyExpressionValue(method = "place", at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/levelgen/feature/OreFeature;size:I"))
    private int modded$scaleOreVeinSize(int originalSize, WorldGenLevel level) {
        if (level != null) {
            try {
                var serverLevel = level.getLevel();
                if (serverLevel != null && OreAmplifierFabric.IG_ORE_VEIN_SIZE != null) {
                    int multiplier = serverLevel.getGameRules().get(OreAmplifierFabric.IG_ORE_VEIN_SIZE);
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
