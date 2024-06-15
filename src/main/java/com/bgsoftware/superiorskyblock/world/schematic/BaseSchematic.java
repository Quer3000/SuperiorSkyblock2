package com.bgsoftware.superiorskyblock.world.schematic;

import com.bgsoftware.common.collections.Maps;
import com.bgsoftware.superiorskyblock.SuperiorSkyblockPlugin;
import com.bgsoftware.superiorskyblock.api.key.Key;
import com.bgsoftware.superiorskyblock.api.key.KeyMap;
import com.bgsoftware.superiorskyblock.api.schematic.Schematic;
import com.bgsoftware.superiorskyblock.core.ChunkPosition;
import com.bgsoftware.superiorskyblock.core.key.KeyIndicator;
import com.bgsoftware.superiorskyblock.core.key.KeyMaps;

import java.util.List;
import java.util.Map;

@SuppressWarnings("WeakerAccess")
public abstract class BaseSchematic implements Schematic {

    protected static final SuperiorSkyblockPlugin plugin = SuperiorSkyblockPlugin.getPlugin();

    protected final String name;

    protected final KeyMap<Integer> cachedCounts;

    protected BaseSchematic(String name) {
        this(name, KeyMaps.createArrayMap(KeyIndicator.MATERIAL));
    }

    protected BaseSchematic(String name, KeyMap<Integer> cachedCounts) {
        this.name = name;
        this.cachedCounts = cachedCounts;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Map<Key, Integer> getBlockCounts() {
        return Maps.unmodifiable(cachedCounts);
    }

    public abstract List<ChunkPosition> getAffectedChunks();

}
