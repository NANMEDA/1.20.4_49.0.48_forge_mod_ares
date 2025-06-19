package com.main.maring.machine.energy.storage.battery;

import com.main.maring.machine.energy.storage.CapacitySize;
import com.main.maring.machine.energy.storage.StorageEntity;
import com.main.maring.machine.registry.MBlockEntityRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.state.BlockState;

public class BatteryCapacityEntity extends StorageEntity {

	private int storage;
	private int storageLevel;
	private static int maxLevel = 2;

	public BatteryCapacityEntity(BlockPos pos, BlockState pBlockState) {
		super(MBlockEntityRegister.BATTERYCAPACITY_BE.get(), pos, pBlockState);
		this.storage = 0;
		this.storageLevel = 0;
	}

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        savedata(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        loaddata(tag);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        savedata(tag);
        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        if (tag != null) {
            loaddata(tag);
        }
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection connection, ClientboundBlockEntityDataPacket packet) {
        CompoundTag tag = packet.getTag();
        if (tag != null) {
            loaddata(tag);
        }
    }
	
	private static final String TAG_S = "storage";
	private static final String TAG_L = "level";
	
	protected void savedata(CompoundTag tag) {
		tag.putInt(TAG_S, this.storage);
		tag.putInt(TAG_L, this.storageLevel);
	}
	
	protected void loaddata(CompoundTag tag) {
		if(tag.contains(TAG_S)) {
			this.storage = tag.getInt(TAG_S);
		}
		if(tag.contains(TAG_L)) {
			this.storageLevel = tag.getInt(TAG_L);
		}
	}
	
	@Override
	protected void servertick() {
	}

	@Override
	protected void clienttick() {
	}

	public int getStorage() {
		return this.storage;
	}


	public void addStorage(int e) {
		this.storage += e;
		setChanged();
	}
	
	public void setStorage(int e) {
		this.storage = e;
		setChanged();
	}
	
	public void cleanStorage() {
		setStorage(0);
	}
	
	public boolean addLevel() {
		if(this.storageLevel < BatteryCapacityEntity.maxLevel) { 
			this.storageLevel++;
			setChanged();
			return true;
		}
		return false;
	}
	public boolean removeLevel() {
		if(this.storageLevel> 0 ) { 
			this.storageLevel--;
			setChanged();
			return true;
		}
		return false;

	}
	public int getStorageLevel() {
		return this.storageLevel;
	}

	public int getCapacity() {
		return (this.storageLevel+1)*this.getCapacitySize().getCapacity();
	}

	public CapacitySize getCapacitySize() {
		return CapacitySize.SMALL;
	} 
	
    @Override
    public boolean isConnectable() {
    	return false;
    }
    
	
}
