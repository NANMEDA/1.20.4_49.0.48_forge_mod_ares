package util.net;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 这里主要处理Net之间的变化
 * @author NANMEDA
 * ***/
public class EnergyNetProcess {
    private static long id = 0;
    private static Set<Long> existingIds;
    private static Map<Long, EnergyNet> energyNetMap;

    private static EnergyNetSavedData savedData;

    public static void init(Level level) {
        if (!level.isClientSide) {
            savedData = EnergyNetSavedData.get((ServerLevel) level);
            existingIds = savedData.getExistingIds();
            energyNetMap = savedData.getEnergyNetMap();
            if (existingIds == null) {
                existingIds = new HashSet<Long>();
            }
            if (energyNetMap == null) {
                energyNetMap = new HashMap<Long, EnergyNet>();
            }
            if (!existingIds.isEmpty()) {
                id = existingIds.stream().max(Long::compare).orElse(0L);
            }
        }
    }

    public static EnergyNet createEnergyNet(long id) {
        if (existingIds.contains(id)) {
            id = generateUniqueId();
        }
        EnergyNet energyNet = new EnergyNet(id);
        existingIds.add(id);
        energyNetMap.put(id, energyNet);
        saveData();  // Force save
        return energyNet;
    }

    public static EnergyNet createEnergyNet() {
        long newId = id + 1;
        if (existingIds.contains(newId)) {
            newId = generateUniqueId();
        } else {
            id = newId;
        }
        EnergyNet energyNet = new EnergyNet(newId);
        existingIds.add(newId);
        energyNetMap.put(newId, energyNet);
        saveData();  // Force save
        return energyNet;
    }

    private static long generateUniqueId() {
        long newId = id + 1;
        while (existingIds.contains(newId)) {
            newId++;
        }
        id = newId;
        return newId;
    }

    public static void deleteEnergyNet(long id) {
        if (existingIds.contains(id)) {
            existingIds.remove(id);
            energyNetMap.remove(id);
            saveData();  // Force save
        }
    }

    public static EnergyNet getEnergyNet(long id) {
        if (existingIds.contains(id)) {
            return energyNetMap.get(id);
        } else {
            return createEnergyNet();
        }
    }

    public static void mergeEnergyNets(long id1, long id2) {
        if (!existingIds.contains(id1) || !existingIds.contains(id2)) {
            throw new IllegalArgumentException("Both IDs must exist");
        }

        EnergyNet net1 = energyNetMap.get(id1);
        EnergyNet net2 = energyNetMap.get(id2);

        if (net1 != null && net2 != null) {
            net1.consumerSet.addAll(net2.consumerSet);
            net1.producerSet.addAll(net2.producerSet);
            net1.storageSet.addAll(net2.storageSet);
            net1.transSet.addAll(net2.transSet);
            net1.nullSet.addAll(net2.nullSet);

            deleteEnergyNet(id2);
        }
    }

    public static Collection<EnergyNet> getAllEnergyNets() {
        if (energyNetMap != null)
            return energyNetMap.values();
        return null;
    }

    private static void saveData() {
        if (savedData != null) {
            savedData.setDirty();  // Mark the data as dirty, so it will be saved automatically
        }
    }
}
