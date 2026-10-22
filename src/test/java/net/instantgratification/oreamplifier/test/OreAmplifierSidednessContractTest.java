// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.oreamplifier.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * OreAmplifierSidednessContractTest: Pillar 5 Sidedness & Mixin Contract Verification Suite.
 *
 * Verifies:
 * - Zero references to net.minecraft.client.* or com.mojang.blaze3d.* in common / non-client classes.
 * - Single-line GNU GPLv3 license headers on line 1 across all Java source files.
 */
public class OreAmplifierSidednessContractTest {

    private static final String EXPECTED_LICENSE_HEADER = "// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3";

    private Path resolvePath(String relative) {
        Path path = Paths.get(relative);
        if (Files.exists(path)) {
            return path;
        }
        Path fallback = Paths.get("Instant Gratification Collection/Ore Amplifier/Ore Amplifier v1.21.11/ore-amplifier", relative);
        if (Files.exists(fallback)) {
            return fallback;
        }
        return path;
    }

    @Test
    @DisplayName("Pillar 5: Common Non-Client Classes Contain Zero Client or Blaze3D References")
    void testNoClientReferencesInCommonCode() throws IOException {
        Path srcRoot = resolvePath("src/main/java");
        assertTrue(Files.exists(srcRoot), "src/main/java directory must exist at: " + srcRoot.toAbsolutePath());

        try (Stream<Path> stream = Files.walk(srcRoot)) {
            List<Path> javaFiles = stream.filter(p -> p.toString().endsWith(".java")).toList();
            assertFalse(javaFiles.isEmpty(), "Must find Java source files in: " + srcRoot);

            for (Path file : javaFiles) {
                String fileName = file.getFileName().toString();
                // Skip client-only integration classes
                if (fileName.equals("OreAmplifierClient.java") || fileName.equals("ModMenuIntegration.java")) {
                    continue;
                }

                List<String> lines = Files.readAllLines(file, StandardCharsets.UTF_8);
                for (int i = 0; i < lines.size(); i++) {
                    String line = lines.get(i);
                    assertFalse(line.contains("net.minecraft.client") && !line.contains("@Environment"),
                            "Forbidden net.minecraft.client reference found in non-client class " + fileName + ":" + (i + 1) + " -> " + line.trim());
                    assertFalse(line.contains("com.mojang.blaze3d"),
                            "Forbidden Blaze3D reference found in non-client class " + fileName + ":" + (i + 1) + " -> " + line.trim());
                }
            }
        }
    }

    @Test
    @DisplayName("Pillar 5: Exact Single-Line GPLv3 License Header Present On Line 1")
    void testGplv3LicenseHeadersPresent() throws IOException {
        Path[] roots = new Path[] {
                resolvePath("src/main/java"),
                resolvePath("src/test/java")
        };

        for (Path root : roots) {
            assertTrue(Files.exists(root), "Source root must exist: " + root.toAbsolutePath());
            try (Stream<Path> stream = Files.walk(root)) {
                List<Path> javaFiles = stream.filter(p -> p.toString().endsWith(".java")).toList();
                assertFalse(javaFiles.isEmpty(), "Java files must exist in: " + root);

                for (Path file : javaFiles) {
                    List<String> lines = Files.readAllLines(file, StandardCharsets.UTF_8);
                    assertFalse(lines.isEmpty(), "Java source file must not be empty: " + file);
                    String line1 = lines.get(0).trim();
                    assertEquals(EXPECTED_LICENSE_HEADER, line1,
                            "Missing or invalid GPLv3 license header on line 1 in: " + file.toString().replace('\\', '/'));
                }
            }
        }
    }
}
