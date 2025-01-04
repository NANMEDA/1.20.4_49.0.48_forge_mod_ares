package util.net;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import util.net.EnergyNet.EnergyEnum;

import java.util.Queue;
import java.util.LinkedList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import machine.energy.EnergyEntity;

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

    /**
     * 自动补id
     * */
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

         // 合并 edgeMap
            for (Map.Entry<BlockPos, Set<BlockPos>> entry : net2.edgeMap.entrySet()) {
                BlockPos from = entry.getKey();
                Set<BlockPos> toSet = entry.getValue();

                // 如果 net1 中已经包含该 from 点，直接合并 to 集合
                if (net1.edgeMap.containsKey(from)) {
                    net1.edgeMap.get(from).addAll(toSet);
                } else {
                    // 如果 net1 中没有该 from 点，直接添加新的边
                    net1.edgeMap.put(from, new HashSet<>(toSet));
                }

                // 双向边：确保每个目标点的 from 也添加反向边
                for (BlockPos to : toSet) {
                    net1.edgeMap.putIfAbsent(to, new HashSet<>());
                    net1.edgeMap.get(to).add(from);
                }
            }
            
            deleteEnergyNet(id2);
            saveData();
        }
    }
    
    public static EnergyNet splitEnergyNet(BlockPos pos1, Level level, EnergyNet energyNet) {
        // 创建新的 EnergyNet
        EnergyNet net2 = createEnergyNet(energyNet.getDimension());

        // 获取 pos1 的能量类型
        BlockEntity energyEntity = level.getBlockEntity(pos1);
        if(energyEntity instanceof EnergyEntity e) {
        	e.setNet(net2.getId());
        }
        EnergyEnum energyKind = energyNet.getEnergyKind(energyEntity);
        
        // 1. 将 pos1 从 energyNet 中移除，并将其对应的所有相关节点和集合转移到 net2
        transferBlockPos(energyNet, net2, pos1, energyKind);

        // 2. 递归地处理 pos1 的所有相邻节点，直到遍历完所有相关节点
        Set<BlockPos> visited = new HashSet<>();
        bfsAndTransfer(level , energyNet, net2, pos1, visited);

        // 3. 从 energyNet 中删除 pos1 和它所有相关的集合
        energyNet.removeBlockPos(pos1, energyKind);
        
        // 4. 将 pos1 相关的所有边从 energyNet 中删除，并添加到 net2 中
        transferEdges(energyNet, net2, pos1);

        // 返回新的 EnergyNet (net2)
        return net2;
    }

    private static void transferBlockPos(EnergyNet sourceNet, EnergyNet targetNet, BlockPos pos, EnergyEnum energyKind) {
        // 从 sourceNet 转移 pos 到 targetNet
        Set<BlockPos> sourceSet = sourceNet.getSet(energyKind);
        Set<BlockPos> targetSet = targetNet.getSet(energyKind);
        targetSet.add(pos);
        sourceSet.remove(pos);
    }

    private static void bfsAndTransfer(Level level, EnergyNet sourceNet, EnergyNet targetNet, BlockPos startPos, Set<BlockPos> visited) {
        // 广度优先遍历相邻节点，并将相应节点转移到 net2
        Queue<BlockPos> queue = new LinkedList<>();
        queue.add(startPos);
        visited.add(startPos);
        long targetId = targetNet.getId();
        
        while (!queue.isEmpty()) {
            BlockPos current = queue.poll();

            // 遍历相邻节点并转移
            Set<BlockPos> neighbors = sourceNet.getEdgeMap().getOrDefault(current, Collections.emptySet());
            for (BlockPos neighbor : neighbors) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    // 获取 neighbor 的能量类型
                    BlockEntity energyEntity = level.getBlockEntity(neighbor);
                    if(energyEntity instanceof EnergyEntity e) {
                    	e.setNet(targetId);
                        EnergyEnum energyKind = sourceNet.getEnergyKind(e);
                        transferBlockPos(sourceNet, targetNet, neighbor, energyKind);
                    }
                    queue.add(neighbor);
                }
            }
        }
    }

    private static void transferEdges(EnergyNet sourceNet, EnergyNet targetNet, BlockPos pos) {
        // 将与 pos 相关的所有边从 sourceNet 转移到 targetNet
        Set<BlockPos> neighbors = sourceNet.getEdgeMap().getOrDefault(pos, Collections.emptySet());

        for (BlockPos neighbor : neighbors) {
            // 从 sourceNet 中删除连接并添加到 targetNet
            sourceNet.removeEdge(pos, neighbor);
            targetNet.addEdge(pos, neighbor);
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
    
    /**
     * 没有用到
     * */
    @Deprecated
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
            for (Map.Entry<BlockPos, Set<BlockPos>> edgeEntry : energyNet.edgeMap.entrySet()) {
                sb.append("  From: ").append(edgeEntry.getKey()).append(" To: ");
                
                // 遍历当前 BlockPos 对应的 Set<BlockPos>
                for (BlockPos to : edgeEntry.getValue()) {
                    sb.append(to).append(" ");
                }
                sb.append("\n");
            }

            // 输出分隔符，方便查看
            sb.append("---\n");
        }
        
        return sb.toString();
    }

}
