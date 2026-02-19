package net.instantgratification.oreamplifier;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.Identifier;
import net.minecraft.util.FormattedCharSequence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class OreAmplifierClient implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("ore-amplifier-client");

    @Override
    public void onInitializeClient() {
        LOGGER.info("Ore Amplifier Client: Initializing Dynamic Translations...");
        
        // Initial Injection (for immediate availability/dev)
        Map<String, String> translations = new HashMap<>();
        collectTranslations(translations);
        
        Language current = Language.getInstance();
        Language.inject(new DynamicLanguage(current, translations));
        LOGGER.info("Ore Amplifier Client: Injected {} translations (Early Phase).", translations.size());
    }

    public static void collectTranslations(Map<String, String> translations) {
        // 1. Category Name
        translations.put("gamerule.category.minecraft.ore_amplifier", "Ore Amplifier");
        
        // 2. Global Rules
        translations.put("gamerule.minecraft.ig_ore_vanilla_global", "Vanilla Ore Fallback Multiplier (%)");
        translations.put("gamerule.minecraft.ig_ore_vanilla_global.description", "The base multiplier for all Vanilla ores. Increases VEIN COUNT. Warning: Values >5000 may freeze world gen.");
        
        translations.put("gamerule.minecraft.ig_ore_modded_global", "Modded Ore Multiplier (%)");
        translations.put("gamerule.minecraft.ig_ore_modded_global.description", "Global multiplier for all Modded ores (Fallback). Increases VEIN COUNT. Warning: High values may freeze game.");

        // 3. Dynamic Rules
        BuiltInRegistries.BLOCK.stream().forEach(block -> {
            Identifier id = BuiltInRegistries.BLOCK.getKey(block);
            if (id.getPath().contains("ore")) {
                String ruleName = "ig_ore_" + id.getNamespace() + "_" + id.getPath();
                String keyName = "gamerule.minecraft." + ruleName;
                String keyDesc = keyName + ".description";
                
                // Generate Readable Name
                String readableName = generateReadableName(id);
                
                translations.put(keyName, readableName + " Multiplier");
                translations.put(keyDesc, "Multiplier for " + readableName + ". Increases VEIN COUNT. 0 = Global. Warning: >5000 may freeze gen.");
            }
        });
    }

    private static String generateReadableName(Identifier id) {
        String path = id.getPath();
        // snake_case to Title Case
        String[] parts = path.split("_");
        StringBuilder sb = new StringBuilder();
        
        // If not minecraft, maybe prepend namespace? e.g. "TechMod Copper"
        if (!id.getNamespace().equals("minecraft")) {
             String namespace = id.getNamespace();
             // Capitalize namespace?
             sb.append(capitalize(namespace)).append(" ");
        }
        
        for (String part : parts) {
            sb.append(capitalize(part)).append(" ");
        }
        return sb.toString().trim();
    }
    
    private static String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    // Wrapper for Language
    public static class DynamicLanguage extends Language {
        private final Language parent;
        private final Map<String, String> translations;

        public DynamicLanguage(Language parent, Map<String, String> translations) {
            this.parent = parent;
            this.translations = translations;
        }

        @Override
        public String getOrDefault(String elementId, String defaultValue) {
            if (translations.containsKey(elementId)) {
                return translations.get(elementId);
            }
            return parent.getOrDefault(elementId, defaultValue);
        }

        @Override
        public boolean has(String elementId) {
            return translations.containsKey(elementId) || parent.has(elementId);
        }

        @Override
        public boolean isDefaultRightToLeft() {
            return parent.isDefaultRightToLeft();
        }

        @Override
        public FormattedCharSequence getVisualOrder(FormattedText logicalOrderText) {
            return parent.getVisualOrder(logicalOrderText);
        }
    }
}
