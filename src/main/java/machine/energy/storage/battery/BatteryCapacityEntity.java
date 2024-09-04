package machine.energy.storage.battery;

import machine.energy.storage.CapacitySize;
import machine.energy.storage.StorageEntity;
import machine.registry.MBlockEntityRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.state.BlockState;

public class BatteryCapacityEntity extends StorageEntity {

	private int storage;

	public BatteryCapacityEntity(BlockPos pos, BlockState pBlockState) {
		super(MBlockEntityRegister.BATTERYCAPACITY_BE.get(), pos, pBlockState);
		this.storage = 0;
		this.NET = 0;
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
	
	protected void savedata(CompoundTag tag) {
		tag.putInt(TAG_S, this.storage);
	}
	
	protected void loaddata(CompoundTag tag) {
		if(tag.contains(TAG_S)) {
			this.storage = tag.getInt(TAG_S);
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

	public int getCapacity() {
		return this.getCapacitySize().getCapacity();
	}

	public CapacitySize getCapacitySize() {
		return CapacitySize.SMALL;
	} 
	
    @Override
    public boolean isConnectable() {
    	return false;
    }
    
	
}
