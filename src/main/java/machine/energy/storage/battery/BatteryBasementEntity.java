package machine.energy.storage.battery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import machine.energy.storage.EnergyStorageMode;
import machine.energy.storage.IStorage;
import machine.energy.storage.StorageEntity;
import machine.registry.MBlockEntityRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class BatteryBasementEntity extends StorageEntity implements IStorage {

	public BatteryBasementEntity(BlockPos pos, BlockState pBlockState) {
		super(MBlockEntityRegister.BATTERTBASEMENT_BE.get(), pos, pBlockState);
		this.FULL_ENERGY = 0;
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

	private final String TAG_ID = "id";
	private final String TAG_NET = "connection";
	
	protected void savedata(CompoundTag tag) {
		tag.putLong(TAG_ID, this.NET);
	    ListTag connectList = new ListTag();
	    for (Map.Entry<BlockPos, Boolean> entry : connectMap.entrySet()) {
	        CompoundTag entryTag = new CompoundTag();
	        entryTag.putLong("pos", entry.getKey().asLong());
	        // 存储 Boolean 值
	        entryTag.putBoolean("connected", entry.getValue());
	        connectList.add(entryTag);
	    }
	    tag.put(TAG_NET, connectList);
	 
	}
	
	protected void loaddata(CompoundTag tag) {
		if(tag.contains(TAG_ID)) {
			this.NET = tag.getLong(TAG_ID);
		}
	    if (tag.contains(TAG_NET)) {
	        ListTag connectList = tag.getList(TAG_NET, 10);
	        Map<BlockPos, Boolean> loadedMap = new HashMap<>();
	        for (int i = 0; i < connectList.size(); i++) {
	            CompoundTag entryTag = connectList.getCompound(i);
	            
	            BlockPos pos = BlockPos.of(entryTag.getLong("pos"));
	            boolean connected = entryTag.getBoolean("connected");
	            
	            loadedMap.put(pos, connected);
	        }
	        connectMap = loadedMap;
	    }
	}
	
	@Override
	protected void servertick() {
		if(isDirty)  level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);

	}

	@Override
	protected void clienttick() {
	}


	@Override
	public void addStorage(int e) {
		List<int[]> battery = this.getBatteryCount();
		if(battery!=null)
			distributeEnergy(battery,e);
	}
	
	private void distributeEnergy(List<int[]> batteries, int e) {
		int i=1;
		if(e>0) {
			for(int[] battery:batteries) {
				int s = battery[1] - battery[0];
				if(this.level.getBlockEntity(this.worldPosition.offset(0, i, 0)) instanceof BatteryCapacityEntity entity) {
					if(s>=e) {
						entity.addStorage(e);
						return;
					}else {
						entity.addStorage(s);
						e-=s;
					}
				}
				i++;
			}
		}else if(e<0){
			for(int[] battery:batteries) {
				int s = battery[0];
				if(this.level.getBlockEntity(this.worldPosition.offset(0, i, 0)) instanceof BatteryCapacityEntity entity) {
					if(s>=-e) {
						entity.addStorage(e);
						return;
					}else {
						entity.cleanStorage();
						e+=s;
					}
				}
				i++;
			}
		}
	}


	@Override
	public EnergyStorageMode getStorageMode() {
		return EnergyStorageMode.NORMAL;
	}


	/**
	 * @return 容量，存量，差值
	 */
	@Override
	public long[] getCSE() {
		List<int[]> batteryList = getBatteryCount();
		if(batteryList != null) {
			long capacity =0;
			long storage = 0;
			for(int[] battery : batteryList) {
				storage += battery[0];
				capacity += battery[1];
			}
			return new long[] {capacity,storage,capacity-storage};
		}
		return new long[]{0,0,0};
	}


	@Override
	public int transSpeed() {
		return 600;
	}
	
    public List<int[]> getBatteryCount() {
        BlockPos pos = this.worldPosition;
        List<int[]> batteryList = new ArrayList<>();
        for(int i=1;i<=10;i++) {
        	if(this.level.getBlockEntity(pos.offset(0, i, 0)) instanceof BatteryCapacityEntity entity) {
        		int[] e = {entity.getStorage(),entity.getCapacity()};
        		batteryList.add(e);
        	}else {
        		break;
        	}
        }
		return batteryList.isEmpty() ? null : batteryList;
    }
}
