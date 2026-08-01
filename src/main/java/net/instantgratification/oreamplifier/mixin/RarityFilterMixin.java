// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.oreamplifier.mixin;

import net.instantgratification.oreamplifier.OreAmplifierFabric;
import net.instantgratification.oreamplifier.OreLogic;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.OreFeature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(RarityFilter.class)
public class RarityFilterMixin {

    @Shadow
    private int chance;

    @Inject(method = "shouldPlace", at = @At("HEAD"), cancellable = true)
    private void modded$amplifyRarity(PlacementContext context, RandomSource random, BlockPos origin, CallbackInfoReturnable<Boolean> cir) {
        Optional<PlacedFeature> topFeatureOpt = context.topFeature();
        if (topFeatureOpt.isEmpty()) {
            return;
        }

        PlacedFeature placedFeature = topFeatureOpt.get();
        Identifier featureId = null;

        // Identify Feature
        if (placedFeature.feature().unwrapKey().isPresent()) {
            featureId = placedFeature.feature().unwrapKey().get().identifier();
        } else {
             ConfiguredFeature<?, ?> configured = placedFeature.feature().value();
             if (configured.feature() instanceof OreFeature) {
                  OreConfiguration config = (OreConfiguration) configured.config();
                  if (!config.targetStates.isEmpty()) {
                       featureId = net.minecraft.core.registries.BuiltInRegistries.BLOCK.getKey(config.targetStates.get(0).state.getBlock());
                  }
             }
        }

        if (featureId != null && OreLogic.shouldAmplify(featureId)) {
             int multiplier = OreLogic.getMultiplier(featureId, context.getLevel().getLevel().getGameRules());
             if (multiplier != 100) {  // Changed from > 100 to != 100 to support reduction
                 float baseProbability = 1.0F / (float) this.chance;
                 boolean success = net.dasik.social.util.StochasticUtil.getAmplifiedProbability(baseProbability, multiplier, random);
                 
                 if (success) {
                      OreLogic.logAmplification(featureId, 1, Math.max(1, (int)(multiplier / 100.0F * this.chance)), multiplier); // Rough logging
                      cir.setReturnValue(true);
                 } else {
                      cir.setReturnValue(false);
                 }
             }
        }
    }
}
