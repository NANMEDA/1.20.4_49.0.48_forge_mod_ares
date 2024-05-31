package block.entity.register;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;

public abstract class PowerStationEntity extends BlockEntity {
	
    public PowerStationEntity(BlockEntityType<?> p_155228_, BlockPos p_155229_, BlockState p_155230_) {
		super(p_155228_, p_155229_, p_155230_);
	}

	protected int energy_output;
    protected int FULL_ENERGY_OUTPUT;

    /*
    protected final ItemStackHandler item = new ItemStackHandler(1) {
        @Override
        public void onLoad() {
            super.onLoad();
            //System.out.println("entity is onload");
        }

        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            // level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
        }
    };

    protected final LazyOptional<IItemHandler> itemHandler = LazyOptional.of(() -> item);

    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
    	return null;
    }

    public ItemStackHandler getItems() {
        return item;
    }

    protected void savedata(CompoundTag tag) {
        tag.put("Item", item.serializeNBT());
    }

    protected void loaddata(CompoundTag tag) {
        if (tag.contains("Item")) {
            item.deserializeNBT(tag.getCompound("Item"));
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
    }*/

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
/*
    @Override
    public void onDataPacket(Connection connection, ClientboundBlockEntityDataPacket packet) {
        CompoundTag tag = packet.getTag();
        if (tag != null) {
            loaddata(tag);
        }
    }*/

    protected abstract  void servertick();
    

    protected abstract  void clienttick();
    
    
}
