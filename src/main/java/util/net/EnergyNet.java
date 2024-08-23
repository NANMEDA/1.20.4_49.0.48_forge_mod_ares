package util.net;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.HashSet;
import java.util.Set;

import machine.energy.consumer.ConsumerEntity;
import machine.energy.producer.ProducerEntity;
import machine.energy.storage.StorageEntity;

/**
 * 能量网络
 * 这是能量网络的基本结构
 * 一个能量网络里面分成5种组成成分
 * supplyLevel是当前的供电能力，以100为满供电
 * 没有结构，导致无法正确的将电网分成两部分
 * @author NANMEDA
 * */
public class EnergyNet {
    private long id;
    private int supplyLevel; //100 is full
    protected Set<BlockPos> consumerSet;
    protected Set<BlockPos> producerSet;
    protected Set<BlockPos> storageSet;
    protected Set<BlockPos> transSet;
    protected Set<BlockPos> nullSet;

    public EnergyNet(long id) {
        this.id = id;
        this.supplyLevel = 100;
        this.consumerSet = new HashSet<>();
        this.producerSet = new HashSet<>();
        this.storageSet = new HashSet<>();
        this.transSet = new HashSet<>();
        this.nullSet = new HashSet<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    public int getSupplyLevel() {
        return supplyLevel;
    }

    public void setSupplyLevel(int supply) {
        this.supplyLevel = supply;
    }

    public Set<BlockPos> getSet(EnergyEnum enumValue) {
        switch (enumValue) {
			case CONSUMER: return consumerSet;
			case PRODUCER: return producerSet;
			case STORAGE: return storageSet;
			case TRANS: return transSet;
			case NULL:
			default:
			return nullSet;
		}
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
    
	public void addBlockPos(BlockPos pos, BlockEntity blockEntity) {
		addBlockPos(pos,getEnum(blockEntity));
	}
	
	public void removeBlockPos(BlockPos pos, BlockEntity blockEntity) {
		removeBlockPos(pos, getEnum(blockEntity));
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

    public enum EnergyEnum {
        CONSUMER, PRODUCER, STORAGE, TRANS, NULL
    }
    
    private EnergyEnum getEnum(BlockEntity blockEntity) {
    	if(blockEntity instanceof ConsumerEntity) {
    		return EnergyEnum.CONSUMER;
    	}else if(blockEntity instanceof ProducerEntity) {
    		return EnergyEnum.PRODUCER;
    	}else if(blockEntity instanceof StorageEntity) {
    		return EnergyEnum.STORAGE;
    	}else if(blockEntity instanceof ConsumerEntity) {
    		return EnergyEnum.TRANS;
    	}else {
    		return EnergyEnum.NULL;
    	}
    }
    
    // Serialize the EnergyNet to NBT
    public CompoundTag writeToNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putLong("Id", this.id);
        tag.putInt("SupplyLevel", this.supplyLevel);

        // Serialize block position sets
        tag.put("ConsumerSet", serializeBlockPosSet(consumerSet));
        tag.put("ProducerSet", serializeBlockPosSet(producerSet));
        tag.put("StorageSet", serializeBlockPosSet(storageSet));
        tag.put("TransSet", serializeBlockPosSet(transSet));
        tag.put("NullSet", serializeBlockPosSet(nullSet));

        return tag;
    }

    // Deserialize the EnergyNet from NBT
    public void readFromNBT(CompoundTag tag) {
        this.id = tag.getLong("Id");
        this.supplyLevel = tag.getInt("SupplyLevel");

        // Deserialize block position sets
        this.consumerSet = deserializeBlockPosSet(tag.getList("ConsumerSet", 10));
        this.producerSet = deserializeBlockPosSet(tag.getList("ProducerSet", 10));
        this.storageSet = deserializeBlockPosSet(tag.getList("StorageSet", 10));
        this.transSet = deserializeBlockPosSet(tag.getList("TransSet", 10));
        this.nullSet = deserializeBlockPosSet(tag.getList("NullSet", 10));
    }

    private ListTag serializeBlockPosSet(Set<BlockPos> set) {
        ListTag listTag = new ListTag();
        for (BlockPos pos : set) {
            listTag.add(NbtUtils.writeBlockPos(pos));
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

}
