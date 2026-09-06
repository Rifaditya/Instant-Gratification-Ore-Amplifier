// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.oreamplifier;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;

@Environment(EnvType.CLIENT)
public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        if (FabricLoader.getInstance().isModLoaded("yet_another_config_lib_v3")) {
            try {
                // Defer classloading by using Class.forName
                Class<?> helperClass = Class.forName("net.instantgratification.oreamplifier.YaclScreenHelper");
                java.lang.reflect.Method method = helperClass.getMethod("createScreen");
                return (ConfigScreenFactory<?>) method.invoke(null);
            } catch (Exception e) {
                // Fail gracefully: fallback to returning a no-op factory
            }
        }
        return parent -> null; // Returns null screen if YACL is missing
    }
}
