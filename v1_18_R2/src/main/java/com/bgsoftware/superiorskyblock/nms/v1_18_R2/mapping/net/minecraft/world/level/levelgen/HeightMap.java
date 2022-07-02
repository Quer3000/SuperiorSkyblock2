package com.bgsoftware.superiorskyblock.nms.v1_18_R2.mapping.net.minecraft.world.level.levelgen;

import com.bgsoftware.superiorskyblock.nms.v1_18_R2.mapping.MappedObject;
import com.bgsoftware.superiorskyblock.nms.v1_18_R2.mapping.net.minecraft.world.level.block.state.BlockData;
import org.jetbrains.annotations.Nullable;

public class HeightMap extends MappedObject<net.minecraft.world.level.levelgen.HeightMap> {

    public HeightMap(net.minecraft.world.level.levelgen.HeightMap handle) {
        super(handle);
    }

    @Nullable
    public static HeightMap ofNullable(net.minecraft.world.level.levelgen.HeightMap handle) {
        return handle == null ? null : new HeightMap(handle);
    }

    public void setBlock(int x, int y, int z, BlockData blockData) {
        handle.a(x, y, z, blockData.getHandle());
    }

}