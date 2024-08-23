package block.entity.consumer.bioplasticbuilder;


import block.entity.BlockEntityRegister;
import block.entity.consumer.PowerConsumerEntity;
import item.ItemRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

/**
 * bio 来自方块的use
 * 100bio -> 生物塑料零件
 * 15s
 * @author NANMEDA
 * */
public class BioplasticBuilderEntity extends PowerConsumerEntity{

	private short process_progress = 900;
	public short bio = 0;
	static protected int itemstack_number=1;
	
	public BioplasticBuilderEntity(BlockPos pos, BlockState pBlockState) {
		super(BlockEntityRegister.bioplasticbuilder_BLOCKENTITY.get(), pos, pBlockState);
		this.FULL_ENERGY_CONSUPTION = 15;
	}
	
    protected final ItemStackHandler item = new ItemStackHandler(itemstack_number) {//必须要在这里创建，ItemStackHandler不可被修改
        @Override
        public void onLoad() {
            super.onLoad();
        }

        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(slot==0) {
            	if(item.getStackInSlot(slot).getCount()<=1) {
            		level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
            	}
            }
        }
    };

    protected final LazyOptional<IItemHandler> itemHandler = LazyOptional.of(() -> item);


    public ItemStackHandler getItems() {
        return item;
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
    
	public void drop() {
		for (int slot = 0; slot < item.getSlots(); slot++) {
		    ItemStack stackInSlot = item.getStackInSlot(slot);
		    if (!stackInSlot.isEmpty()) {
		        Containers.dropContents(this.level, this.worldPosition, NonNullList.of(ItemStack.EMPTY, stackInSlot));
		    }
		}
	}
	
	@Override
	public <T>LazyOptional<T> getCapability(Capability<T> cap,Direction side){
		if(cap == ForgeCapabilities.ITEM_HANDLER) {
			return itemHandler.cast();
		}else {
			return super.getCapability(cap, side);
		}
	}

	
	private final String TAG_NAME = "Item";
	private final String TAG_PROGRESS = "Progress";
	private final String TAG_BIO = "Bio";
	
	protected void savedata(CompoundTag tag) {
		tag.put(TAG_NAME, item.serializeNBT());
		tag.putShort(TAG_PROGRESS, process_progress);
		tag.putShort(TAG_BIO, bio);
	}
	
	protected void loaddata(CompoundTag tag) {
		if(tag.contains(TAG_NAME)) {
			item.deserializeNBT(tag.getCompound(TAG_NAME));
		}
		if(tag.contains(TAG_PROGRESS)) {
			process_progress = (tag.getShort(TAG_PROGRESS));
		}
		if(tag.contains(TAG_BIO)) {
			bio = (tag.getShort(TAG_BIO));
		}
	}
	

	@Override
	public void servertick() {
		ItemStack stack = ItemStack.EMPTY;
		if(energy_supply<25) {
			process_progress = 900;
			return;
		}
		if(bio<100) {
			process_progress = 900;
			return;
		}
		stack=item.getStackInSlot(0);
		if(stack.getCount()<64) {
			energy_consume = FULL_ENERGY_CONSUPTION;
			if(process_progress>0) {
				process_progress -= (energy_supply > 75) ? 3 : ((energy_supply > 50) ? 2 : ((energy_supply > 25) ? 1 : 0));
				setChanged();
				return;
			}
			process_progress = 20*15*3;
			item.setStackInSlot(0, new ItemStack(ItemRegister.MATERIAL_ITEMS[3].get(),stack.getCount()+1));
			bio -= 100;
			setChanged();
		}
	}
	
	@Override
	public void clienttick() {
		return;
	}

	

}