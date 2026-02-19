package net.instantgratification.oreamplifier.mixin;

import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

@Mixin(PlacedFeature.class)
public class PlacedFeatureMixin {
    // Logic moved to RepeatingPlacementMixin
}
