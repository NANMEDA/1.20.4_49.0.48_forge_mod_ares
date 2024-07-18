package block.entity.consumer.basicmetalmanufactor;

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
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

/**
 * 4铁 + 1黄金 -> 基础金属零件
 * 10s
 * @author NANMEDA
 * */
public class BasicMetalManufactorEntity extends PowerConsumerEntity{

	private short process_progress = 0;
	private int render = 0;
	
	public int getRenderDis() {
		return render;
	}

	static protected int itemstack_number=6;
	
	public BasicMetalManufactorEntity(BlockPos pos, BlockState pBlockState) {
		super(BlockEntityRegister.basicmetalmanufactor_BLOCKENTITY.get(), pos, pBlockState);
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
            if(slot==5) {
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
	private final String TAG_RENDER = "render";	//不放在tag里面的不会被同步到client ！！！!!!即使设置同步
	
	protected void savedata(CompoundTag tag) {
		tag.put(TAG_NAME, item.serializeNBT());
		tag.putShort(TAG_PROGRESS, process_progress);
		tag.putInt(TAG_RENDER, render);
	}
	
	protected void loaddata(CompoundTag tag) {
		if(tag.contains(TAG_NAME)) {
			item.deserializeNBT(tag.getCompound(TAG_NAME));
		}
		if(tag.contains(TAG_PROGRESS)) {
			process_progress = (tag.getShort(TAG_PROGRESS));
		}
		if(tag.contains(TAG_RENDER)) {
			render = tag.getShort(TAG_RENDER);
		}
	}

	@Override
	public void servertick() {
		ItemStack[] stack = new ItemStack[6];
		if(energy_supply<25) {
			process_progress = 0;
			
	        int render = 0;
			if(render != this.render) {
				this.render = render;
				level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
			}
			
			return;
		}
		for (int i = 0; i < 5; i++) {//不要弄错成6了，最后一个是输出
		    stack[i] = item.getStackInSlot(i);
		    if(stack[i]==ItemStack.EMPTY) {
		    	process_progress = 0;
		        int render = 0;
				if(render != this.render) {
					this.render = render;
					level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
				}
		    	return;
		    }
		}
		stack[5]=item.getStackInSlot(5);
		if(stack[5].getCount()<64) {
			energy_consume = FULL_ENERGY_CONSUPTION;
			if(process_progress<600) {
				process_progress += (energy_supply > 75) ? 3 : ((energy_supply > 50) ? 2 : ((energy_supply > 25) ? 1 : 0));
				
				int progress = process_progress/60;//除到10
		        int render = progress * 22 / 10; //移动像素/距离
				if(render != this.render) {
					this.render = render;
					level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
				}
				
				setChanged();
				return;
			
			}
			process_progress = 0;
			stack[0].shrink(1);
			stack[1].shrink(1);
			stack[2].shrink(1);
			stack[3].shrink(1);
			stack[4].shrink(1);
			ItemStack items5 = new ItemStack(ItemRegister.MATERIAL_ITEMS[1].get(), stack[5].getCount()+1);
			
			item.setStackInSlot(0, stack[0]);
			item.setStackInSlot(1, stack[1]);
			item.setStackInSlot(2, stack[2]);
			item.setStackInSlot(3, stack[3]);
			item.setStackInSlot(4, stack[4]);
			item.setStackInSlot(5, items5);
			level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
			setChanged();
		}
		
	}
	
	@Override
	public void clienttick() {
		return;
	}
	
}