package util.net;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
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

    public static EnergyNet createEnergyNet(long id, ResourceLocation dimension) {
        if (existingIds.contains(id)) {
            id = generateUniqueId();
        }
        EnergyNet energyNet = new EnergyNet(id, dimension);
        existingIds.add(id);
        energyNetMap.put(id, energyNet);
        saveData();
        return energyNet;
    }

    public static EnergyNet createEnergyNet(ResourceLocation dimension) {
        long newId = id + 1;
        if (existingIds.contains(newId)) {
            newId = generateUniqueId();
        } else {
            id = newId;
        }
        EnergyNet energyNet = new EnergyNet(newId, dimension);
        existingIds.add(newId);
        energyNetMap.put(newId, energyNet);
        saveData();
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
            saveData();
        }
    }

    public static EnergyNet getEnergyNet(long id) {
        if (existingIds.contains(id)) {
            return energyNetMap.get(id);
        } else {
            return createEnergyNet(null);
        }
    }
    
    public static boolean EnergyNetExist(long id) {
        return existingIds.contains(id);
    }

    /**
     * 混合两个电网，混合后序号变成第一个传入的序号
     * @param id1
     * @param id2
     * @throws IllegalArgumentException 某个id不存在
     */
    public static void mergeEnergyNets(long id1, long id2) {
        if (!existingIds.contains(id1) || !existingIds.contains(id2)) {
            throw new IllegalArgumentException("IDs is not exist");
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
            saveData();
        }
    }

    /**
     * 只有当EnergyNetMap存在且不为空时，才有非null返回
     */
    public static Collection<EnergyNet> getAllEnergyNets() {
        if (energyNetMap != null && !energyNetMap.isEmpty())
            return energyNetMap.values();
        return null;
    }

    private static void saveData() {
        if (savedData != null) {
            savedData.setDirty();  // Mark the data as dirty, so it will be saved automatically?
        }
    }
    
    public static String netDisplay() {
        StringBuilder sb = new StringBuilder();

        // 遍历所有的 EnergyNet
        for (Map.Entry<Long, EnergyNet> entry : energyNetMap.entrySet()) {
            long energyNetId = entry.getKey();
            EnergyNet energyNet = entry.getValue();
            
            // 添加 EnergyNet 的 id
            sb.append("EnergyNet ID: ").append(energyNetId).append("\n");
            
            // 添加 EnergyNet 的 supplyLevel
            sb.append("Supply Level: ").append(energyNet.getSupplyLevel()).append("\n");
            
            // 添加每个 Set 的内容
            sb.append("Consumer Set: ").append(energyNet.consumerSet).append("\n");
            sb.append("Producer Set: ").append(energyNet.producerSet).append("\n");
            sb.append("Storage Set: ").append(energyNet.storageSet).append("\n");
            sb.append("Trans Set: ").append(energyNet.transSet).append("\n");
            sb.append("Null Set: ").append(energyNet.nullSet).append("\n");
            
            // 添加 edgeMap 的内容 (每个键值对)
            sb.append("Edge Map: \n");
            for (Map.Entry<BlockPos, BlockPos> edgeEntry : energyNet.edgeMap.entrySet()) {
                sb.append("  From: ").append(edgeEntry.getKey()).append(" To: ").append(edgeEntry.getValue()).append("\n");
            }

            // 输出分隔符，方便查看
            sb.append("---------------------------------------------------\n");
        }
        
        return sb.toString();
    }

}
