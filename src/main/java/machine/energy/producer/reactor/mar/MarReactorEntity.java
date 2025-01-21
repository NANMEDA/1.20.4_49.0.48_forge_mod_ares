package machine.energy.producer.reactor.mar;

import java.util.Map;

import item.ItemRegister;
import item.itemMaterial;

import java.util.HashMap;

import machine.energy.producer.IProducer;
import net.minecraft.nbt.ListTag;
import machine.energy.producer.ProducerEntity;
import machine.registry.MBlockEntityRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class MarReactorEntity extends ProducerEntity implements IProducer {

	public MarReactorEntity(BlockPos pos, BlockState pBlockState) {
		super(MBlockEntityRegister.MARREACTOR_BE.get(), pos, pBlockState);
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
    

    protected final ItemStackHandler item = new ItemStackHandler(1) {//必须要在这里创建，ItemStackHandler不可被修改
        @Override
        public void onLoad() {
            super.onLoad();
            //System.out.println("entity is load");
        }

        @Override
        protected void onContentsChanged(int slot) {
        }
    };

    protected final LazyOptional<IItemHandler> itemHandler = LazyOptional.of(() -> item);

    public ItemStackHandler getItems() {
        return item;
    }

	private final String TAG_ID = "id";
	private final String TAG_NET = "connection";
	private final String TAG_ITEM = "item";
	
	protected void savedata(CompoundTag tag) {

		tag.putLong(TAG_ID, this.NET);
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
	    tag.put(TAG_ITEM, item.serializeNBT());
	}
	
	protected void loaddata(CompoundTag tag) {
		
		if(tag.contains(TAG_ID)) {
			this.NET = tag.getLong(TAG_ID);
		}
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
		if(tag.contains(TAG_ITEM)) {
			item.deserializeNBT(tag.getCompound(TAG_ITEM));
		}
	}
	
	
	
	
	@Override
	public int provideEnergySupply() {
		int gen = 100;
		ItemStack in = this.getItems().getStackInSlot(0);
		if(in.is(ItemRegister.MATERIAL_ITEMS[itemMaterial.getMaterialId("ominous_gemstone_reactor")].get())) {
			gen+= in.getCount() * 100;
		}
		return gen;
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
