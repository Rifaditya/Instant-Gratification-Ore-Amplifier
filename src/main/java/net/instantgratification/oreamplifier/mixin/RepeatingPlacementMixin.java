// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.oreamplifier.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.instantgratification.oreamplifier.OreLogic;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.OreFeature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.RepeatingPlacement;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Optional;

@Mixin(RepeatingPlacement.class)
public abstract class RepeatingPlacementMixin {

    @ModifyExpressionValue(
        method = "getPositions",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/levelgen/placement/RepeatingPlacement;count(Lnet/minecraft/util/RandomSource;Lnet/minecraft/core/BlockPos;)I")
    )
    private int modded$amplifyCount(int originalCount, PlacementContext context, RandomSource random, BlockPos origin) {
        Optional<PlacedFeature> topFeatureOpt = context.topFeature();
        if (topFeatureOpt.isEmpty()) {
            return originalCount;
        }

        PlacedFeature placedFeature = topFeatureOpt.get();
        Identifier featureId = null;

        if (placedFeature.feature().unwrapKey().isPresent()) {
            featureId = placedFeature.feature().unwrapKey().get().identifier();
        } else {
            ConfiguredFeature<?, ?> configured = placedFeature.feature().value();
            if (configured.feature() instanceof OreFeature) {
                OreConfiguration config = (OreConfiguration) configured.config();
                if (!config.targetStates.isEmpty()) {
                    featureId = net.minecraft.core.registries.BuiltInRegistries.BLOCK
                            .getKey(config.targetStates.get(0).state.getBlock());
                }
            }
        }

        if (featureId != null && OreLogic.shouldAmplify(featureId)) {
            int multiplier = OreLogic.getMultiplier(featureId, context.getLevel().getLevel().getGameRules());
            int rawCount = OreLogic.getAmplifiedCount(originalCount, multiplier, random);

            if (rawCount != originalCount) {
                OreLogic.logAmplification(featureId, originalCount, rawCount, multiplier);
            }
            return rawCount;
        }

        return originalCount;
    }
}
