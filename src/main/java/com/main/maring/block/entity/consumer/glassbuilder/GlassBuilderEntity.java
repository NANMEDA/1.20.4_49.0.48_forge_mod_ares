package com.main.maring.block.entity.consumer.glassbuilder;


import com.main.maring.block.entity.BlockEntityRegister;
import com.main.maring.block.entity.consumer.PowerConsumerEntity;
import com.main.maring.item.ItemRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

/**
 * 生物塑料零件 -> 3玻璃
 * 晶体零件 -> 5玻璃
 * 12s
 * @author NANMEDA
 * */
public class GlassBuilderEntity extends PowerConsumerEntity{

	public short process_progress = 0;
	static protected int itemstack_number=2;
	
	public GlassBuilderEntity(BlockPos pos, BlockState pBlockState) {
		super(BlockEntityRegister.glassbuilder_BLOCKENTITY.get(), pos, pBlockState);
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
        	if(item.getStackInSlot(slot).getCount()<6) {
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
	
	protected void savedata(CompoundTag tag) {
		tag.put(TAG_NAME, item.serializeNBT());
		tag.putShort(TAG_PROGRESS, process_progress);
	}
	
	protected void loaddata(CompoundTag tag) {
		if(tag.contains(TAG_NAME)) {
			item.deserializeNBT(tag.getCompound(TAG_NAME));
		}
		if(tag.contains(TAG_PROGRESS)) {
			process_progress = (tag.getShort(TAG_PROGRESS));
		}
	}
	

	@Override
	public void servertick() {
		if(energy_supply<25) {
			process_progress = 0;
			return;
		}
		ItemStack[] stack = new ItemStack[2];
		stack[0] = item.getStackInSlot(0);
		if(stack[0].isEmpty()) {
			if(process_progress!=0) {level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);}
			process_progress = 0;
			return;
		}
		stack[1] = item.getStackInSlot(1);
		if(stack[1].getCount()<60) {
			if(process_progress<720) {//12s*3
				if(process_progress==0) {level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);}
				process_progress += (energy_supply > 75) ? 3 : ((energy_supply > 50) ? 2 : ((energy_supply > 25) ? 1 : 0));
				setChanged();
				return;
			}
			process_progress -= 720;
			int add = stack[0].getItem()==ItemRegister.MATERIAL_ITEMS[3].get() ? 3 : 5;
			item.setStackInSlot(0, new ItemStack(stack[0].getItem(),stack[0].getCount()-1));
			item.setStackInSlot(1, new ItemStack(Items.GLASS,stack[1].getCount()+add));
			setChanged();
		}
	}
	
	@Override
	public void clienttick() {
		return;
	}

	

}