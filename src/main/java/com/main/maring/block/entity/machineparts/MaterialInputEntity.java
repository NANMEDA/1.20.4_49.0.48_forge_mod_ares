package com.main.maring.block.entity.machineparts;


import com.main.maring.block.entity.consumer.PowerConsumerEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

/**
 * @author NANMEDA
 * */
public class MaterialInputEntity extends PowerConsumerEntity{

	private Direction facing = Direction.NORTH;
	private ItemStack sendStack = ItemStack.EMPTY;
	
	static protected int itemstack_number = 0;
	
	public MaterialInputEntity(BlockPos pos, BlockState pBlockState) {
		super(Register.MATERIALINPUT_BLOCKENTITY.get(), pos, pBlockState);
		this.energy_consume = 0;
		this.facing = pBlockState.getValue(BlockStateProperties.FACING);
	}
	
    protected final ItemStackHandler item = new ItemStackHandler(itemstack_number) {//必须要在这里创建，ItemStackHandler不可被修改
        @Override
        public void onLoad() {
            super.onLoad();
        }

        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
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

	
	private static final String TAG_DIRECTION = "direction";

	protected void savedata(CompoundTag tag) {
		switch (facing) {
			case NORTH: 
			default:
				tag.putInt(TAG_DIRECTION, 0);
			case SOUTH:
				tag.putInt(TAG_DIRECTION, 1);
			case WEST:
				tag.putInt(TAG_DIRECTION, 2);
			case EAST:
				tag.putInt(TAG_DIRECTION, 3);
			case UP:
				tag.putInt(TAG_DIRECTION, 4);
			case DOWN:
				tag.putInt(TAG_DIRECTION, 5);
		}
	}
	
	protected void loaddata(CompoundTag tag) {
		if(tag.contains(TAG_DIRECTION)) {
			switch (tag.getInt(TAG_DIRECTION)) {
				case 0: 
				default:
					facing = Direction.NORTH;
				case 1:
					facing = Direction.SOUTH;
				case 2:
					facing = Direction.WEST;
				case 3:
					facing = Direction.EAST;
				case 4:
					facing = Direction.UP;
				case 5:
					facing = Direction.DOWN;
			}
		}
	}
	

	@Override
	public void servertick() {
		checkAnyReceive();
		checkOnItem();
		if(sendStack.isEmpty()) return;
		BlockPos intoPos = this.getBlockPos().relative(facing);
		BlockEntity intoEntity = this.level.getBlockEntity(intoPos);

		if(intoEntity instanceof PowerConsumerEntity) {
			((PowerConsumerEntity) intoEntity).receiveItemStack(sendStack);
			sendStack = ItemStack.EMPTY;
		}else {
			Containers.dropContents(this.level, this.worldPosition, NonNullList.of(ItemStack.EMPTY, sendStack));
			sendStack = ItemStack.EMPTY;
		}
	}
	
	private void checkAnyReceive() {
	}


	@Override
	public void clienttick() {}
	
    private void checkOnItem() {
        if (this.level != null) {
            BlockPos blockPos = this.getBlockPos();
            AABB boundingBox = new AABB(
                    blockPos.getX(), blockPos.getY(), blockPos.getZ(),
                    blockPos.getX() + 1, blockPos.getY() + 2, blockPos.getZ() + 1
                );

            for (ItemEntity itemEntity : this.level.getEntitiesOfClass(ItemEntity.class, boundingBox)) {
                ItemStack itemStack = itemEntity.getItem();
                if (!itemStack.isEmpty()) {
                	sendStack = itemStack;
                    itemEntity.remove(null);
                    break;
                }
            }
        }
    }

}