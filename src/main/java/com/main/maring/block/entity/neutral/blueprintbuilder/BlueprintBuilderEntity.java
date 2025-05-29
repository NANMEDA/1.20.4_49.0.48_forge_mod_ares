package com.main.maring.block.entity.neutral.blueprintbuilder;


import com.main.maring.block.entity.BlockEntityRegister;
import com.main.maring.block.entity.consumer.PowerConsumerEntity;
import com.main.maring.item.blueprint.ItemBlueprintNBT;
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
 * 蓝图合成台
 * @author NANMEDA
 * */
public class BlueprintBuilderEntity extends PowerConsumerEntity{

	
	static protected int itemstack_number=8;
	
	public BlueprintBuilderEntity(BlockPos pos, BlockState pBlockState) {
		super(BlockEntityRegister.blueprintbuilder_BLOCKENTITY.get(), pos, pBlockState);
		this.energy_consume = 0;
	}
	
    protected final ItemStackHandler item = new ItemStackHandler(itemstack_number) {//必须要在这里创建，ItemStackHandler不可被修改
        @Override
        public void onLoad() {
            super.onLoad();
        }

        @Override
        protected void onContentsChanged(int slot) {
        	setChanged();
        	level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
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

	
	private final String TAG_ITEM = "Item";
	
	protected void savedata(CompoundTag tag) {
		tag.put(TAG_ITEM, item.serializeNBT());
	}
	
	protected void loaddata(CompoundTag tag) {
		if(tag.contains(TAG_ITEM)) {
			item.deserializeNBT(tag.getCompound(TAG_ITEM));
		}
	}

	@Override
	public void servertick() {
		if(!item.getStackInSlot(7).isEmpty()) return;
		ItemStack blueprint = item.getStackInSlot(6);
		if(blueprint.isEmpty()) return;
		int tech = ItemBlueprintNBT.getTech(blueprint);
		int level = ItemBlueprintNBT.getLevel(blueprint);
		if(tech<0||level<0) return;
		int[] check = BlueprintBuilderHelper.getNeedMaterial(tech,level);
		for(int i=0;i<6;i++) {
			if(item.getStackInSlot(i).getCount()<check[i]) return;
		}
		for(int i=0;i<6;i++) {
			ItemStack stack = item.getStackInSlot(i);
			if(stack.getCount()<=0) continue;
			stack.shrink(check[i]);
			item.setStackInSlot(i, stack);
		}
		item.setStackInSlot(6, ItemStack.EMPTY);
		ItemStack out = BlueprintBuilderHelper.getOutput(tech,level);
		item.setStackInSlot(7, out);
	}
	
	@Override
	public void clienttick() {
		//System.out.println("cli is at tech" + atTech + " at level" + atLevel+ "tech1" + tech1);
	}
	
}