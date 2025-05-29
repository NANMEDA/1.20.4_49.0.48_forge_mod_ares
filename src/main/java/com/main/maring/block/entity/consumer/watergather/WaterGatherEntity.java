package com.main.maring.block.entity.consumer.watergather;

import com.main.maring.block.entity.BlockEntityRegister;
import com.main.maring.block.entity.consumer.PowerConsumerEntity;
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
 * 获得水，满载10桶
 * 36000一桶
 * @author NANMEDA
 * */
public class WaterGatherEntity extends PowerConsumerEntity{

	public int water = 0;
	static protected int itemstack_number=1;//留个空
	
	public WaterGatherEntity(BlockPos pos, BlockState pBlockState) {
		super(BlockEntityRegister.watergather_BLOCKENTITY.get(), pos, pBlockState);
		this.FULL_ENERGY_CONSUPTION = 2;
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
		for (int slot = 0; slot < item.getSlots()-1; slot++) {//减一，不会掉落最后一个slot,催化剂不会返回
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
	private final String TAG_WATER = "Water";
	
	protected void savedata(CompoundTag tag) {
		tag.put(TAG_NAME, item.serializeNBT());
		tag.putInt(TAG_WATER, water);
	}
	
	protected void loaddata(CompoundTag tag) {
		if(tag.contains(TAG_NAME)) {
			item.deserializeNBT(tag.getCompound(TAG_NAME));
		}
		if(tag.contains(TAG_WATER)) {
			water = (tag.getInt(TAG_WATER));
		}
	}
	

	@Override
	public void servertick() {
		//ItemStack stack = item.getStackInSlot(0);
		if(water<360000) {//10 minute->1 bucket,10*60*20*3=36000
			energy_consume=FULL_ENERGY_CONSUPTION;
			water += (energy_supply > 75) ? 3 : ((energy_supply > 50) ? 2 : ((energy_supply > 25) ? 1 : 0));
			setChanged();
			return;
		}else {
			energy_consume = 0;
		}
		/***
		 * 要和level和weather有关
		 * ***/
	}
	
	@Override
	public void clienttick() {
		return;
	}

	

}