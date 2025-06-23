package com.main.maring.util.net;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.LongTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * TODO 非正常退出会导致数据丢失,需要修改
 * 把EnergyNet序列化的代码在这里
 * 然后被用于Save和Load
 */
public class EnergyNetSavedData extends SavedData {
    private static final String DATA_NAME = "energy_nets";

    private final Set<Long> existingIds = new HashSet<>();
    private final Map<Long, EnergyNet> energyNetMap = new HashMap<>();

    public EnergyNetSavedData() {}

    public Set<Long> getExistingIds() {
        return existingIds;
    }

    public Map<Long, EnergyNet> getEnergyNetMap() {
        return energyNetMap;
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        // Save existingIds
        ListTag idList = new ListTag();
        for (Long id : existingIds) {
            idList.add(LongTag.valueOf(id));
        }
        tag.put("ExistingIds", idList);

        // Save energyNetMap
        CompoundTag netMapTag = new CompoundTag();
        for (Map.Entry<Long, EnergyNet> entry : energyNetMap.entrySet()) {
            netMapTag.put(entry.getKey().toString(), entry.getValue().writeToNBT());
        }
        tag.put("EnergyNetMap", netMapTag);
        
        return tag;
    }
    
    public static EnergyNetSavedData load(CompoundTag tag) {
        EnergyNetSavedData data = new EnergyNetSavedData();

        // Load existingIds
        ListTag idList = tag.getList("ExistingIds", ListTag.TAG_LONG);
        for (int i = 0; i < idList.size(); i++) {
            data.existingIds.add(((LongTag) idList.get(i)).getAsLong());
        }

        // Load energyNetMap
        CompoundTag netMapTag = tag.getCompound("EnergyNetMap");
        for (String key : netMapTag.getAllKeys()) {
            long id = Long.parseLong(key);
            EnergyNet energyNet = new EnergyNet(id,null);
            energyNet.readFromNBT(netMapTag.getCompound(key));
            data.energyNetMap.put(id, energyNet);
        }

        return data;
    }

    public static EnergyNetSavedData get(ServerLevel level) {
        DimensionDataStorage storage = level.getDataStorage();

        return storage.computeIfAbsent(EnergyNetSavedData::load, EnergyNetSavedData::new ,DATA_NAME
        );
    }
}

