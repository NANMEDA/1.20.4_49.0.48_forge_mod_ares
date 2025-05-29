package com.main.maring.machine.energy.viewer;

import com.main.maring.machine.energy.EnergyEntity;
import com.main.maring.machine.energy.IEnergy;
import com.main.maring.machine.registry.MBlockEntityRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import com.main.maring.util.net.EnergyNet.EnergyEnum;

public class EnergyViewerEntity extends EnergyEntity implements IEnergy {

	private long energySupply,energyConsume,storage,capacity;
	
	public EnergyViewerEntity(BlockPos pos, BlockState pBlockState) {
		super(MBlockEntityRegister.ENERGYVIEWER_BE.get(), pos, pBlockState);
		this.NET = 0;
		this.energyConsume=0;
		this.energySupply=0;
		this.storage=0;
		this.capacity=0;
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

	private static final String TAG_NET = "net";
	private static final String TAG_ENERGY_SUPPLY = "energySupply";
	private static final String TAG_ENERGY_CONSUME = "energyConsume";
	private static final String TAG_STORAGE = "storage";
	private static final String TAG_CAPACITY = "capacity";
	
	protected void savedata(CompoundTag tag) {
	    tag.putLong(TAG_NET, this.getNet());
	    tag.putLong(TAG_ENERGY_SUPPLY, this.energySupply);
	    tag.putLong(TAG_ENERGY_CONSUME, this.energyConsume);
	    tag.putLong(TAG_STORAGE, this.storage);
	    tag.putLong(TAG_CAPACITY, this.capacity);
	}
	
	protected void loaddata(CompoundTag tag) {
	    if (tag.contains(TAG_NET)) {
	        this.setNet(tag.getLong(TAG_NET));
	    }
	    if (tag.contains(TAG_ENERGY_SUPPLY)) {
	        this.energySupply = tag.getLong(TAG_ENERGY_SUPPLY);
	    }
	    if (tag.contains(TAG_ENERGY_CONSUME)) {
	        this.energyConsume = tag.getLong(TAG_ENERGY_CONSUME);
	    }
	    if (tag.contains(TAG_STORAGE)) {
	        this.storage = tag.getLong(TAG_STORAGE);
	    }
	    if (tag.contains(TAG_CAPACITY)) {
	        this.capacity = tag.getLong(TAG_CAPACITY);
	    }
	}
	
	@Override
	protected void servertick() {
	}

	@Override
	protected void clienttick() {
	}
	
	public void setInformation(long supply,long consume, long storage,long capacity) {
		this.energySupply = supply;
		this.energyConsume = consume;
		this.capacity = capacity;
		this.storage = storage;
		setChanged();
		this.level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
	}
	
	/**
	 * @return net,supply,consume,s-c,storage,cap,c-s
	 */
	public long[] getInformation() {
		if(!this.level.isClientSide)
		return new long[] {this.NET,this.energySupply,this.energyConsume,this.energySupply-this.energyConsume,
				this.storage,this.capacity,this.capacity-this.storage
				};
		else {
			return new long[] {this.NET,this.energySupply,this.energyConsume,this.energySupply-this.energyConsume,
					this.storage,this.capacity,this.capacity-this.storage
					};
		}
	}

	@Override
	public EnergyEnum getEnergyKind() {
		return EnergyEnum.NULL;
	}
    
	
}
