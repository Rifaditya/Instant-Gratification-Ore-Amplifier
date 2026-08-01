// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.oreamplifier.mixin;

import net.instantgratification.oreamplifier.OreAmplifierFabric;
import net.instantgratification.oreamplifier.OreLogic;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.RepeatingPlacement;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.OreFeature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.resources.Identifier;
import java.util.Optional;

@Mixin(RepeatingPlacement.class)
public abstract class RepeatingPlacementMixin {

    @Shadow
    protected abstract int count(RandomSource random, BlockPos origin);

    @Redirect(method = "getPositions", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/levelgen/placement/RepeatingPlacement;count(Lnet/minecraft/util/RandomSource;Lnet/minecraft/core/BlockPos;)I"))
    private int modded$redirectCount(RepeatingPlacement instance, RandomSource random, BlockPos origin,
            PlacementContext context) {
        // 1. Calculate original count by calling the shadowed method on the instance
        // Note: 'instance' is the object being called. We need to cast it to the Mixin
        // type to call the shadow?
        // Actually, pure Mixin shadow works on 'this'.
        // But Redirect provides 'instance'.
        // If 'instance' == 'this', we can just call 'count(random, origin)'.
        // RepeatingPlacement is the target class.

        // However, since 'count' is protected, we can't call 'instance.count' directly
        // from here unless we are in the same package.
        // But we are not.
        // We will assume 'instance' supports the call if we cast, or use the shadow if
        // 'instance' is 'this'.
        // In a Redirect for a virtual call 'this.count(...)', 'instance' IS 'this'.

        int originalCount = ((RepeatingPlacementMixin) (Object) instance).count(random, origin);

        // 2. Identify Feature from Context
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

        // 3. Apply Multiplier
        if (featureId != null && OreLogic.shouldAmplify(featureId)) {
            int multiplier = OreLogic.getMultiplier(featureId, context.getLevel().getLevel().getGameRules());
            int rawCount = OreLogic.getAmplifiedCount(originalCount, multiplier, random);

            // Safety cap removed: passing rawCount directly.
            // However, we implement a diagnostic crash for extreme values to provide the requested detail.
            if (rawCount > 100000) {
                net.minecraft.CrashReport crashReport = net.minecraft.CrashReport.forThrowable(
                        new RuntimeException("Runaway Ore Amplification detected"),
                        "Generating amplified ore veins");
                net.minecraft.CrashReportCategory category = crashReport.addCategory("Amplification Details");
                category.setDetail("Ore ID", featureId.toString());
                category.setDetail("Raw Count Requested", rawCount);
                category.setDetail("Multiplier Applied", multiplier + "%");
                category.setDetail("Original Count", originalCount);
                category.setDetail("Context", context.toString());

                throw new net.minecraft.ReportedException(crashReport);
            }

            if (rawCount != originalCount) {
                OreLogic.logAmplification(featureId, originalCount, rawCount, multiplier);
            }
            return rawCount;
        }

        return originalCount;
    }
}
