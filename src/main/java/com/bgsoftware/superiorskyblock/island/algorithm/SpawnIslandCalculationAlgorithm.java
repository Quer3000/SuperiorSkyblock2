package com.bgsoftware.superiorskyblock.island.algorithm;

import com.bgsoftware.common.collections.Maps;
import com.bgsoftware.superiorskyblock.api.island.Island;
import com.bgsoftware.superiorskyblock.api.island.algorithms.IslandCalculationAlgorithm;

import java.util.concurrent.CompletableFuture;

public class SpawnIslandCalculationAlgorithm implements IslandCalculationAlgorithm {

    private static final SpawnIslandCalculationAlgorithm INSTANCE = new SpawnIslandCalculationAlgorithm();
    private static final IslandCalculationResult RESULT = Maps::emptyMap;

    private SpawnIslandCalculationAlgorithm() {

    }

    public static SpawnIslandCalculationAlgorithm getInstance() {
        return INSTANCE;
    }

    @Override
    public CompletableFuture<IslandCalculationResult> calculateIsland(Island island) {
        return CompletableFuture.completedFuture(RESULT);
    }

}
