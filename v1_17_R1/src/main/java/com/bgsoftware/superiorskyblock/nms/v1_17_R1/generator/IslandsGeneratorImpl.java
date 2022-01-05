package com.bgsoftware.superiorskyblock.nms.v1_17_R1.generator;

import com.bgsoftware.superiorskyblock.SuperiorSkyblockPlugin;
import com.bgsoftware.superiorskyblock.world.generator.IslandsGenerator;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.BlockPopulator;

import java.util.Collections;
import java.util.List;
import java.util.Random;

@SuppressWarnings({"unused", "NullableProblems"})
public final class IslandsGeneratorImpl extends IslandsGenerator {

    private final SuperiorSkyblockPlugin plugin;

    public IslandsGeneratorImpl(SuperiorSkyblockPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public ChunkData generateChunkData(World world, Random random, int chunkX, int chunkZ, BiomeGrid biomeGrid) {
        ChunkData chunkData = createChunkData(world);

        switch (world.getEnvironment()) {
            case NORMAL: {
                plugin.getNMSWorld().setBiome(biomeGrid, Biome.PLAINS);
                break;
            }
            case NETHER: {
                plugin.getNMSWorld().setBiome(biomeGrid, Biome.NETHER_WASTES);
                break;
            }
            case THE_END: {
                plugin.getNMSWorld().setBiome(biomeGrid, Biome.THE_END);
                break;
            }
        }

        if (chunkX == 0 && chunkZ == 0 && world.getEnvironment() == plugin.getSettings().getWorlds().getDefaultWorld()) {
            chunkData.setBlock(0, 99, 0, Material.BEDROCK);
        }

        return chunkData;
    }

    @Override
    public List<BlockPopulator> getDefaultPopulators(World world) {
        return Collections.emptyList();
    }

    @Override
    public Location getFixedSpawnLocation(World world, Random random) {
        return new Location(world, 0, 100, 0);
    }

}