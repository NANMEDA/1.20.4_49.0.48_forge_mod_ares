package block.entity.neutral.researchtable;


import com.item.ItemRegister;

import block.entity.BlockEntityRegister;
import block.entity.consumer.PowerConsumerEntity;
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

public class ResearchTableEntity extends PowerConsumerEntity{

	private short process_progress = 0;
	public short crystal = 0;
	public short accelerate = 0;
	public short accelerate_index = 0;
	static protected int itemstack_number=2;
	
	public ResearchTableEntity(BlockPos pos, BlockState pBlockState) {
		super(BlockEntityRegister.researchtable_BLOCKENTITY.get(), pos, pBlockState);
		this.energy_consume = 15;
	}
	
    protected final ItemStackHandler item = new ItemStackHandler(itemstack_number) {//必须要在这里创建，ItemStackHandler不可被修改
        @Override
        public void onLoad() {
            super.onLoad();
        }

        @Override
        protected void onContentsChanged(int slot) {
        	setChanged();
        	if(item.getStackInSlot(slot).getCount()<=1) {
        		level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
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
		for (int slot = 0; slot < item.getSlots()-1; slot++) {//减一，不会掉落最后一个slot,加速的不会返回
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
	private final String TAG_CRY = "Crystal";
	private final String TAG_ACC = "Accelerate";
	private final String TAG_ACC_I = "accelerateIndex";
	
	protected void savedata(CompoundTag tag) {
		tag.put(TAG_NAME, item.serializeNBT());
		tag.putShort(TAG_PROGRESS, process_progress);
		tag.putShort(TAG_CRY, crystal);
		tag.putShort(TAG_ACC, accelerate);
		tag.putShort(TAG_ACC_I, accelerate_index);
	}
	
	protected void loaddata(CompoundTag tag) {
		if(tag.contains(TAG_NAME)) {
			item.deserializeNBT(tag.getCompound(TAG_NAME));
		}
		if(tag.contains(TAG_PROGRESS)) {
			process_progress = (tag.getShort(TAG_PROGRESS));
		}
		if(tag.contains(TAG_CRY)) {
			crystal = (tag.getShort(TAG_CRY));
		}
		if(tag.contains(TAG_ACC)) {
			accelerate = (tag.getShort(TAG_ACC));
		}
		if(tag.contains(TAG_ACC_I)) {
			accelerate_index = (tag.getShort(TAG_ACC_I));
		}
	}
	

	@Override
	public void servertick() {
		ItemStack[] stack = new ItemStack[2];
		if(crystal<15) {
			process_progress = 0;
			return;
		}
		stack[0]=item.getStackInSlot(0);
		stack[1]=item.getStackInSlot(1);
		if(stack[0].getCount()<64) {
			if(process_progress<2400) {//2 minute
				process_progress +=1;
				if(accelerate>0) {
					process_progress += accelerate_index;
					accelerate--;
					if(accelerate==0) {
						item.setStackInSlot(1, ItemStack.EMPTY);
					}
				}
				setChanged();
				return;
			}
			process_progress -= 2400;
			item.setStackInSlot(0, new ItemStack(ItemRegister.MATERIAL_ITEMS[5].get(),stack[0].getCount()+1));
			crystal -= 15;
			setChanged();
		}
	}
	
	@Override
	public void clienttick() {
		return;
	}

	

}