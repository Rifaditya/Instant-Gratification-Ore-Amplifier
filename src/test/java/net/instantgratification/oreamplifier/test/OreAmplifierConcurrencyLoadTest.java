// Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3
package net.instantgratification.oreamplifier.test;

import net.instantgratification.oreamplifier.OreAmplifierConfig;
import net.instantgratification.oreamplifier.OreLogic;
import net.minecraft.SharedConstants;
import net.minecraft.resources.Identifier;
import net.minecraft.server.Bootstrap;
import net.minecraft.util.RandomSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pillar 2: Virtual Multi-Thread Concurrency Load Suite for Ore Amplifier.
 * Asserts thread safety, lack of deadlocks, zero ConcurrentModificationException,
 * and clean executor termination under 50 simultaneous threads (20,000 operations total).
 */
public class OreAmplifierConcurrencyLoadTest {

    private static final int THREAD_COUNT = 50;
    private static final int OPS_PER_THREAD = 400; // 50 * 400 = 20,000 operations
    private static final long SEED = 0xDA51C263L;

    @BeforeAll
    public static void setup() {
        SharedConstants.tryDetectVersion();
        Bootstrap.bootStrap();
    }

    @Test
    @DisplayName("Pillar 2: 50 Worker Threads x 400 Operations (20,000 total) Concurrency Stress")
    void testConcurrentExecutionLoad() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
        CountDownLatch startGate = new CountDownLatch(1);
        AtomicInteger successCounter = new AtomicInteger(0);
        AtomicInteger cmeCounter = new AtomicInteger(0);
        AtomicInteger exceptionCounter = new AtomicInteger(0);

        List<Future<?>> futures = new ArrayList<>();
        Identifier[] testIds = {
                Identifier.fromNamespaceAndPath("minecraft", "iron_ore"),
                Identifier.fromNamespaceAndPath("minecraft", "amethyst_geode"),
                Identifier.fromNamespaceAndPath("minecraft", "deepslate_gold_ore"),
                Identifier.fromNamespaceAndPath("mod", "custom_copper_ore"),
                Identifier.fromNamespaceAndPath("minecraft", "stone")
        };

        for (int t = 0; t < THREAD_COUNT; t++) {
            final int threadId = t;
            futures.add(executor.submit(() -> {
                RandomSource threadRandom = RandomSource.create(SEED + threadId);
                try {
                    // Await simultaneous release
                    startGate.await();

                    for (int op = 0; op < OPS_PER_THREAD; op++) {
                        // 1. Concurrent calculation
                        int amount = (op % 10) + 1;
                        int mult = 50 + ((op * 25) % 400);
                        int amplified = OreLogic.getAmplifiedCount(amount, mult, threadRandom);
                        assertTrue(amplified >= 0, "Amplified count must never be negative");

                        // 2. Concurrent feature identification
                        Identifier id = testIds[(threadId + op) % testIds.length];
                        OreLogic.shouldAmplify(id);

                        // 3. Concurrent config mutations, reads, and pruning
                        OreAmplifierConfig config = OreAmplifierConfig.get();
                        String oreKey = "concurrent_mod:ore_" + (op % 25);
                        if (op % 3 == 0) {
                            config.setOreOverride(oreKey, mult);
                        } else if (op % 3 == 1) {
                            config.getOreOverride(oreKey);
                        } else {
                            config.pruneDefaults();
                        }

                        successCounter.incrementAndGet();
                    }
                } catch (ConcurrentModificationException cme) {
                    cmeCounter.incrementAndGet();
                } catch (Throwable t1) {
                    exceptionCounter.incrementAndGet();
                }
            }));
        }

        // Release all threads simultaneously
        startGate.countDown();

        // Join all futures with timeout
        for (Future<?> f : futures) {
            assertDoesNotThrow(() -> f.get(10, TimeUnit.SECONDS), "Thread execution must finish without deadlock");
        }

        executor.shutdown();
        boolean terminated = executor.awaitTermination(10, TimeUnit.SECONDS);

        assertTrue(terminated, "Executor service must cleanly terminate within 10 seconds");
        assertEquals(0, cmeCounter.get(), "Zero ConcurrentModificationException must occur");
        assertEquals(0, exceptionCounter.get(), "Zero unexpected exceptions must occur during concurrent load");
        assertEquals(THREAD_COUNT * OPS_PER_THREAD, successCounter.get(), "All 20,000 concurrent operations must complete successfully");
    }
}
