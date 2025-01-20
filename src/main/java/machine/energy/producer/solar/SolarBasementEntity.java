package machine.energy.producer.solar;

import java.util.Map;
import java.util.HashMap;

import machine.energy.producer.IProducer;
import net.minecraft.nbt.ListTag;
import machine.energy.producer.ProducerEntity;
import machine.registry.MBlockEntityRegister;
import machine.registry.MBlockRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class SolarBasementEntity extends ProducerEntity implements IProducer {

	

	public SolarBasementEntity(BlockPos pos, BlockState pBlockState) {
		super(MBlockEntityRegister.SOLARBASEMENT_BE.get(), pos, pBlockState);
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
	
	private static final String TAG_NET = "net";
	
	protected void savedata(CompoundTag tag) {
	    // 创建一个列表，用于存储每个键值对
	    ListTag connectList = new ListTag();

	    // 遍历 Map，将每个 BlockPos 和 Boolean 对存入 ListTag 中
	    for (Map.Entry<BlockPos, Boolean> entry : connectMap.entrySet()) {
	        CompoundTag entryTag = new CompoundTag();
	        
	        // 将 BlockPos 转换为 Long 类型存储
	        entryTag.putLong("pos", entry.getKey().asLong());
	        // 存储 Boolean 值
	        entryTag.putBoolean("connected", entry.getValue());
	        connectList.add(entryTag);
	    }
	    // 将 ListTag 存入 CompoundTag
	    tag.put(TAG_NET, connectList);
	}
	
	protected void loaddata(CompoundTag tag) {
	    if (tag.contains(TAG_NET)) {
	        ListTag connectList = tag.getList(TAG_NET, 10); // 10 是 CompoundTag 的 ID 类型
	        
	        // 创建一个新的 Map 用来存储加载的值
	        Map<BlockPos, Boolean> loadedMap = new HashMap<>();
	        
	        // 遍历 ListTag，逐个加载
	        for (int i = 0; i < connectList.size(); i++) {
	            CompoundTag entryTag = connectList.getCompound(i);
	            
	            // 从 CompoundTag 中读取 BlockPos 和 Boolean
	            BlockPos pos = BlockPos.of(entryTag.getLong("pos"));
	            boolean connected = entryTag.getBoolean("connected");
	            
	            // 将读取的键值对存入 Map
	            loadedMap.put(pos, connected);
	        }

	        // 更新 connectMap 为加载后的数据
	        connectMap = loadedMap;
	    }
	}
	
	
	@Override
	public int provideEnergySupply() {
	    int lightLevel = this.level.getBrightness(LightLayer.SKY, this.worldPosition);
	    if (!this.level.isDay()) {
	        return 0;
	    } else if (this.level.isThundering() || this.level.isRaining()) {
	        lightLevel *= 0.5;
	    }
	    MutableBlockPos pos = this.worldPosition.above().mutable();
	    int count = 0;
	    if (this.level.getBlockState(pos).is(MBlockRegister.SOLARPILLAR_B.get())) {
	        count = getPanelCount(pos.above(), this.level);
	    }
	    return (count*lightLevel)/13;
	}
	
	private int getPanelCount(BlockPos pos, Level level) {
		double count = 0;
		for(int i=-1;i<2;i++) {
			for(int j=-1;j<2;j++) {
				if(level.getBlockState(pos.offset(i, 0, j)).is(MBlockRegister.SOLARPANEL_B.get())){
					count+=1.0;
				}else if(level.getBlockState(pos.offset(i, 0, j)).is(Blocks.DAYLIGHT_DETECTOR)){
					count+=0.5;
				}
			}
		}
		return (int)count;
	}
	

	
	@Override
	protected void servertick() {
		if(isDirty)
		level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
	}

	@Override
	protected void clienttick() {
	}

}
