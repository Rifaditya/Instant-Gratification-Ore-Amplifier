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
 * Pillar 5: Sidedness & Mixin Contract Verification Suite for Ore Amplifier.
 * Asserts complete client separation in common sources and bytecode,
 * and verifies single-line GNU GPLv3 license headers on line 1 across all Java source files.
 */
public class OreAmplifierSidednessContractTest {

    private static final String EXPECTED_LICENSE_HEADER = "// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3";

    // Dynamic byte arrays to prevent constant pool self-matching (ERR-SELF-MATCH)
    private static final String CLIENT_SRC_FORBIDDEN = new String(new byte[]{
            110, 101, 116, 46, 109, 105, 110, 101, 99, 114, 97, 102, 116, 46, 99, 108, 105, 101, 110, 116, 46
    }); // "net.minecraft.client."

    private static final String BLAZE3D_SRC_FORBIDDEN = new String(new byte[]{
            99, 111, 109, 46, 109, 111, 106, 97, 110, 103, 46, 98, 108, 97, 122, 101, 51, 100, 46
    }); // "com.mojang.blaze3d."

    private static final String CLIENT_BYTECODE_FORBIDDEN = new String(new byte[]{
            110, 101, 116, 47, 109, 105, 110, 101, 99, 114, 97, 102, 116, 47, 99, 108, 105, 101, 110, 116, 47
    }); // "net/minecraft/client/"

    private static final String BLAZE3D_BYTECODE_FORBIDDEN = new String(new byte[]{
            99, 111, 109, 47, 109, 111, 106, 97, 110, 103, 47, 98, 108, 97, 122, 101, 51, 100, 47
    }); // "com/mojang/blaze3d/"

    private Path resolvePath(String relative) {
        Path direct = Paths.get(relative);
        if (Files.exists(direct)) {
            return direct;
        }
        Path sub12111 = Paths.get("Instant Gratification Collection/Ore Amplifier/Ore Amplifier v1.21.11/ore-amplifier", relative);
        if (Files.exists(sub12111)) {
            return sub12111;
        }
        Path cur = Paths.get("").toAbsolutePath();
        for (int i = 0; i < 5; i++) {
            Path check = cur.resolve(relative);
            if (Files.exists(check)) {
                return check;
            }
            if (cur.getParent() == null) break;
            cur = cur.getParent();
        }
        return direct;
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
                    assertFalse(line.contains(CLIENT_SRC_FORBIDDEN) && !line.contains("@Environment"),
                            "Forbidden client reference found in non-client class " + fileName + ":" + (i + 1) + " -> " + line.trim());
                    assertFalse(line.contains(BLAZE3D_SRC_FORBIDDEN),
                            "Forbidden Blaze3D reference found in non-client class " + fileName + ":" + (i + 1) + " -> " + line.trim());
                }
            }
        }
    }

    @Test
    @DisplayName("Pillar 5: Common Non-Client Bytecode Contains Zero Constant Pool Client References")
    void testNoClientReferencesInBytecode() throws IOException {
        Path classesRoot = resolvePath("build/classes/java/main");
        if (!Files.exists(classesRoot)) {
            return;
        }

        try (Stream<Path> stream = Files.walk(classesRoot)) {
            List<Path> classFiles = stream.filter(p -> p.toString().endsWith(".class")).toList();
            for (Path classFile : classFiles) {
                String fileName = classFile.getFileName().toString();
                if (fileName.equals("OreAmplifierClient.class") || fileName.equals("ModMenuIntegration.class")) {
                    continue;
                }

                String raw = new String(Files.readAllBytes(classFile), StandardCharsets.ISO_8859_1);
                assertFalse(raw.contains(CLIENT_BYTECODE_FORBIDDEN),
                        "Forbidden client constant pool reference in bytecode: " + fileName);
                assertFalse(raw.contains(BLAZE3D_BYTECODE_FORBIDDEN),
                        "Forbidden Blaze3D constant pool reference in bytecode: " + fileName);
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
