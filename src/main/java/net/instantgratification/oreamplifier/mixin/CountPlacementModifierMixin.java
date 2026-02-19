package net.instantgratification.oreamplifier.mixin;

import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.world.level.levelgen.placement.CountPlacement;

@Mixin(CountPlacement.class)
public class CountPlacementModifierMixin {
    // Logic moved to RepeatingPlacementMixin
}
