package machine.energy.trans;

import java.util.HashMap;
import java.util.Map;

import machine.registry.MBlockEntityRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.level.block.state.BlockState;
import util.net.EnergyNetProcess;

public class UnbrokenConductorEntity extends TransEntity implements ITrans {

	public UnbrokenConductorEntity(BlockPos pos, BlockState pBlockState) {
		super(MBlockEntityRegister.UNBROKENCONDUCTOR_BE.get(), pos, pBlockState);
	}

	@Override
	public int getEnergySupplyLevel() {
		if(this.haveNet()) {
			return EnergyNetProcess.getEnergyNet(this.getNet()).getSupplyLevel();
		}else {
			return 0;
		}
	}

	private final String TAG_ID = "id";
	private final String TAG_NET = "net";

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
	}
	
	@Override
	protected void clienttick() {
	}

}
