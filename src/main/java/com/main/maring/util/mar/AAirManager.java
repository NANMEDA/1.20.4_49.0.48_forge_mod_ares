package com.main.maring.util.mar;

import com.main.maring.block.norm.AAirState;
import com.main.maring.block.norm.BlockAAIR;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class AAirManager extends SavedData {
    private static final String DATA_NAME = "a_air_data";

    private final Set<BlockPos> activateSet = ConcurrentHashMap.newKeySet();
    private final Set<BlockPos> deactivateSet = ConcurrentHashMap.newKeySet();

    private Set<BlockPos> getSetForState(AAirState state) {
        return (state == AAirState.ACTIVATE) ? activateSet : deactivateSet;
    }

    public void add(BlockPos pos, AAirState state) {
        getSetForState(state).add(pos);
    }

    public void remove(BlockPos pos, AAirState state) {
        getSetForState(state).remove(pos);
    }

    public Set<BlockPos> getActivateSet() {
        return Set.copyOf(activateSet);
    }

    public Set<BlockPos> getDeactivateSet() {
        return Set.copyOf(deactivateSet);
    }

    public Set<BlockPos> getSet(AAirState state) {
        return getSetForState(state);
    }

    @Override
    public CompoundTag save(CompoundTag compound) {
        compound.put("activate_a_air_Set", serializePositions(activateSet));
        compound.put("deactivate_a_air_Set", serializePositions(deactivateSet));
        return compound;
    }

    private ListTag serializePositions(Set<BlockPos> positions) {
        ListTag list = new ListTag();
        for (BlockPos pos : positions) {
            CompoundTag tag = new CompoundTag();
            tag.putInt("x", pos.getX());
            tag.putInt("y", pos.getY());
            tag.putInt("z", pos.getZ());
            list.add(tag);
        }
        return list;
    }

    public static AAirManager load(CompoundTag compound) {
        AAirManager data = new AAirManager();
        if (compound != null) {
            data.activateSet.addAll(data.deserializePositions(compound.getList("activateSet", Tag.TAG_COMPOUND)));
            data.deactivateSet.addAll(data.deserializePositions(compound.getList("deactivateSet", Tag.TAG_COMPOUND)));
        }
        return data;
    }

    private Set<BlockPos> deserializePositions(ListTag list) {
        Set<BlockPos> positions = ConcurrentHashMap.newKeySet();
        for (int i = 0; i < list.size(); i++) {
            CompoundTag tag = list.getCompound(i);
            int x = tag.getInt("x");
            int y = tag.getInt("y");
            int z = tag.getInt("z");
            positions.add(new BlockPos(x, y, z));
        }
        return positions;
    }

    public static AAirManager getOrCreate(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(AAirManager::load, AAirManager::new, DATA_NAME);
    }
}