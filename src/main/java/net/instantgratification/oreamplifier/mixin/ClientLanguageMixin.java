// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.oreamplifier.mixin;

import net.instantgratification.oreamplifier.OreAmplifierClient;
import net.minecraft.client.resources.language.ClientLanguage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Map;

@Mixin(ClientLanguage.class)
public class ClientLanguageMixin {

    // Redirect the 'Map.copyOf(translations)' call inside 'loadFrom'.
    // This allows us to modify the map right before it is made immutable and properly returned.
    @Redirect(method = "loadFrom", at = @At(value = "INVOKE", target = "java/util/Map.copyOf(Ljava/util/Map;)Ljava/util/Map;"))
    private static Map<String, String> modded$injectDynamicTranslations(Map<String, String> original) {
        // Collect our dynamic translations into the existing map
        OreAmplifierClient.collectTranslations(original);
        
        // Return the immutable copy as expected by the original code
        return Map.copyOf(original);
    }
}
