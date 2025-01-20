package util.net;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Queue;
import java.util.LinkedList;

import machine.energy.IEnergy;

/**
 * 能量网络
 * 这是能量网络的基本结构
 * 一个能量网络里面分成5种组成成分
 * supplyLevel是当前的供电能力，以100为满供电,过高过低都不行
 * 没有结构，导致无法正确的将电网分成两部分<br>
 *  TODO 非正常退出会导致数据丢失,需要修改
 * @author NANMEDA
 * */
public class EnergyNet {
    private long id;
    private int supplyLevel; //100 is full
    private ResourceLocation dimension;
    protected Set<BlockPos> consumerSet;
    protected Set<BlockPos> producerSet;
    protected Set<BlockPos> storageSet;
    protected Set<BlockPos> transSet;
    protected Set<BlockPos> nullSet;
    
    protected Map<BlockPos, Set<BlockPos>> edgeMap;

    public EnergyNet(long id, ResourceLocation dimension) {
        this.id = id;
        this.dimension = dimension;
        this.supplyLevel = 100;
        this.consumerSet = new HashSet<>();
        this.producerSet = new HashSet<>();
        this.storageSet = new HashSet<>();
        this.transSet = new HashSet<>();
        this.nullSet = new HashSet<>();
        this.edgeMap = new HashMap<>();
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    public ResourceLocation getDimension() {
        return this.dimension;
    }
    
    public void setDimension(ResourceLocation pDimension) {
        this.dimension = pDimension;
    }
    
    public boolean haveDimension() {
        return this.dimension!=null;
    }
    
    public void addEdge(BlockPos from, BlockPos to) {
        // 添加 from -> to
        edgeMap.putIfAbsent(from, new HashSet<>());
        edgeMap.get(from).add(to);
        // 添加 to -> from
        edgeMap.putIfAbsent(to, new HashSet<>());
        edgeMap.get(to).add(from);
        
        
    }
    
    public void removeEdge(BlockPos from, BlockPos to) {
        // 移除 from -> to
        if (edgeMap.containsKey(from)) {
            edgeMap.get(from).remove(to);
            if (edgeMap.get(from).isEmpty()) {
                edgeMap.remove(from); // 如果集合为空，删除该键
            }
        }
        // 移除 to -> from
        if (edgeMap.containsKey(to)) {
            edgeMap.get(to).remove(from);
            if (edgeMap.get(to).isEmpty()) {
                edgeMap.remove(to); // 如果集合为空，删除该键
            }
        }
    }
    
    public void removeAllEdgesFromPoint(BlockPos point) {
        // 检查是否存在与该点相关的边
        if (!edgeMap.containsKey(point)) {
            return;
        }
        // 获取所有与该点相连的目标点集合
        Set<BlockPos> connectedPoints = edgeMap.get(point);
        // 遍历所有目标点，移除它们与当前点的反向边
        for (BlockPos to : connectedPoints) {
            if (edgeMap.containsKey(to)) {
                edgeMap.get(to).remove(point);
                // 如果目标点的集合为空，移除该目标点键
                if (edgeMap.get(to).isEmpty()) {
                    edgeMap.remove(to);
                }
            }
        }
        // 移除当前点的所有边
        edgeMap.remove(point);
    }
    
    
    /**
     * 删除两个点之后，是否还是相互连接（在同一张图上）
     * */
    public static boolean canStillConnect(Map<BlockPos, Set<BlockPos>> edge,BlockPos from, BlockPos to) {
        // 如果 from 或 to 不存在于 edgeMap 中，直接返回 false
        if (!edge.containsKey(from) || !edge.containsKey(to)) {
            return false;
        }

        // 暂时移除边 from -> to 和 to -> from
        edge.get(from).remove(to);
        edge.get(to).remove(from);

        // 判断 from 和 to 是否仍然连通
        boolean connected = bfs(edge,from, to);

        // 恢复边 from -> to 和 to -> from
        edge.get(from).add(to);
        edge.get(to).add(from);

        return connected;
    }
    
    public boolean canStillConnect(BlockPos from, BlockPos to) {
    	return canStillConnect(this.edgeMap, from, to);
    }

    private static boolean bfs(Map<BlockPos, Set<BlockPos>> edge,BlockPos start, BlockPos target) {
        // 使用队列进行广度优先搜索
        Queue<BlockPos> queue = new LinkedList<>();
        Set<BlockPos> visited = new HashSet<>();

        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            BlockPos current = queue.poll();

            if (current.equals(target)) {
                return true; // 如果找到目标节点，说明连通
            }

            // 遍历当前节点的所有邻接节点
            for (BlockPos neighbor : edge.getOrDefault(current, Collections.emptySet())) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }

        return false; // 如果遍历完成没有找到目标节点，说明不连通
    }
    
    public Set<BlockPos> getEdges(BlockPos from) {
        return edgeMap.getOrDefault(from, Collections.emptySet());
    }
   
    public Map<BlockPos, Set<BlockPos>> getEdgeMap(){
    	return this.edgeMap;
    }
    
    public int getSupplyLevel() {
        return this.supplyLevel;   
    }

    public void setSupplyLevel(int supply) {
        this.supplyLevel = supply;
    }

    public Set<BlockPos> getSet(EnergyEnum enumValue) {
        switch (enumValue) {
			case CONSUMER: return this.consumerSet;
			case PRODUCER: return this.producerSet;
			case STORAGE: return this.storageSet;
			case TRANS: return this.transSet;
			case NULL:
			default:
			return this.nullSet;
		}
    }
    
	public void addBlockPos(BlockPos pos, BlockEntity blockEntity) {
		addBlockPos(pos,getEnergyKind(blockEntity));
	}
	

    public void addBlockPos(BlockPos pos, EnergyEnum enumValue) {
        switch (enumValue) {
			case CONSUMER: 
				consumerSet.add(pos);
				return;
			case PRODUCER: 
				producerSet.add(pos);
				return;
			case STORAGE: 
				storageSet.add(pos);
				return;
			case TRANS: 
				transSet.add(pos);
				return;
			case NULL:
			default:
				nullSet.add(pos);
				return;
		}
    }
    
	public void removeBlockPos(BlockPos pos, BlockEntity blockEntity) {
		if(blockEntity!=null&&!blockEntity.getLevel().isClientSide)
		removeBlockPos(pos, getEnergyKind(blockEntity));
		if(consumerSet.isEmpty()&&producerSet.isEmpty()&&storageSet.isEmpty()&&nullSet.isEmpty()) {
			EnergyNetProcess.deleteEnergyNet(this.id);
		}
	}

    public void removeBlockPos(BlockPos pos, EnergyEnum enumValue) {
        switch (enumValue) {
			case CONSUMER: 
				consumerSet.remove(pos);
				return;
			case PRODUCER: 
				producerSet.remove(pos);
				return;
			case STORAGE: 
				storageSet.remove(pos);
				return;
			case TRANS: 
				transSet.remove(pos);
				return;
			case NULL:
			default:
				nullSet.remove(pos);
				return;
		}
    }

    /**
     * @return true-Empty
     */
    public boolean checkEmpty() {
    	return producerSet.isEmpty()&&consumerSet.isEmpty()&&storageSet.isEmpty()&&transSet.isEmpty()&&nullSet.isEmpty();
    }
    
    public enum EnergyEnum {
        CONSUMER, PRODUCER, STORAGE, TRANS, NULL
    }
    
    public EnergyEnum getEnergyKind(BlockEntity blockEntity) {
    	if(blockEntity instanceof IEnergy eEntity) {
    		return eEntity.getEnergyKind();
    	}else {
    		System.err.println("Maring Waring: BlockEntity does not implement IEnergy. Returning EnergyEnum.NULL. At pos " + blockEntity.getBlockPos());
    		return EnergyEnum.NULL;
    	}
    }
    
    public CompoundTag writeToNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putLong("Id", this.id);
        tag.putInt("SupplyLevel", this.supplyLevel);
        tag.putString("Dimension", this.dimension.toString());

        tag.put("ConsumerSet", serializeBlockPosSet(consumerSet));
        tag.put("ProducerSet", serializeBlockPosSet(producerSet));
        tag.put("StorageSet", serializeBlockPosSet(storageSet));
        tag.put("TransSet", serializeBlockPosSet(transSet));
        tag.put("NullSet", serializeBlockPosSet(nullSet));
        tag.put("EdgeMap", serializeBlockPosMap(edgeMap));

        return tag;
    }

    public void readFromNBT(CompoundTag tag) {
        this.id = tag.getLong("Id");
        this.supplyLevel = tag.getInt("SupplyLevel");
        this.dimension = new ResourceLocation(tag.getString("Dimension"));

        this.consumerSet = deserializeBlockPosSet(tag.getList("ConsumerSet", 10));
        this.producerSet = deserializeBlockPosSet(tag.getList("ProducerSet", 10));
        this.storageSet = deserializeBlockPosSet(tag.getList("StorageSet", 10));
        this.transSet = deserializeBlockPosSet(tag.getList("TransSet", 10));
        this.nullSet = deserializeBlockPosSet(tag.getList("NullSet", 10));
        this.edgeMap = deserializeBlockPosMap(tag.getList("EdgeMap",10));
    }

    private ListTag serializeBlockPosSet(Set<BlockPos> set) {
        ListTag listTag = new ListTag();
        for (BlockPos pos : set) {
            listTag.add(NbtUtils.writeBlockPos(pos));
        }
        return listTag;
    }
    

    private ListTag serializeBlockPosMap(Map<BlockPos, Set<BlockPos>> map) {
        ListTag listTag = new ListTag();
        for (Map.Entry<BlockPos, Set<BlockPos>> entry : map.entrySet()) {
            CompoundTag pairTag = new CompoundTag();
            pairTag.put("Key", NbtUtils.writeBlockPos(entry.getKey()));

            ListTag valuesTag = new ListTag();
            for (BlockPos value : entry.getValue()) {
                valuesTag.add(NbtUtils.writeBlockPos(value));
            }
            pairTag.put("Values", valuesTag);

            listTag.add(pairTag);
        }
        return listTag;
    }

    private Set<BlockPos> deserializeBlockPosSet(ListTag listTag) {
        Set<BlockPos> set = new HashSet<>();
        for (int i = 0; i < listTag.size(); i++) {
            set.add(NbtUtils.readBlockPos(listTag.getCompound(i)));
        }
        return set;
    }
    
    private Map<BlockPos, Set<BlockPos>> deserializeBlockPosMap(ListTag listTag) {
        Map<BlockPos, Set<BlockPos>> map = new HashMap<>();
        for (int i = 0; i < listTag.size(); i++) {
            CompoundTag pairTag = listTag.getCompound(i);
            BlockPos key = NbtUtils.readBlockPos(pairTag.getCompound("Key"));

            ListTag valuesTag = pairTag.getList("Values", 10);
            Set<BlockPos> values = new HashSet<>();
            for (int j = 0; j < valuesTag.size(); j++) {
                values.add(NbtUtils.readBlockPos(valuesTag.getCompound(j)));
            }

            map.put(key, values);
        }
        return map;
    }


}
