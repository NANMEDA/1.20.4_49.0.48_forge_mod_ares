package block.entity.neutral.fuelrefiner;


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
import net.minecraft.world.item.Item;
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
 * 10 小瓶甲烷（1-8） / 10 煤炭 -> 1 燃料 （1-64）
 * 可以发现提取后可以烧的东西变少了
 * 作为提取燃料的消耗
 * 火箭只能接受燃料
 * 20s
 * @author NANMEDA
 * */
public class FuelRefinerEntity extends PowerConsumerEntity{

	private int render = 0;
	private short process_progress = 0;
	private int fuel = 0;
	public boolean is_button = false;
	
	private static Item bottled_fuel = ItemRegister.MATERIAL_ITEMS[10].get();
	private static Item bottled_methane = ItemRegister.MATERIAL_ITEMS[6].get();
	
	static protected int itemstack_number = 4;
	
	public int getRenderDis() {
		return render;
	}
	
	public FuelRefinerEntity(BlockPos pos, BlockState pBlockState) {
		super(BlockEntityRegister.fuelrefiner_BLOCKENTITY.get(), pos, pBlockState);
		this.energy_consume = 0;
	}
	
    protected final ItemStackHandler item = new ItemStackHandler(itemstack_number) {//必须要在这里创建，ItemStackHandler不可被修改
        @Override
        public void onLoad() {
            super.onLoad();
            //System.out.println("entity is load");
        }

        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            /*
            if(slot==1) {
            	level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
            }*/
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
	private final String TAG_PROGRESS = "progress";
	private final String TAG_FUEL = "fuel";
	private final String TAG_RENDER = "render";	//不放在tag里面的不会被同步到client ！！！!!!即使设置同步
	
	protected void savedata(CompoundTag tag) {
		tag.put(TAG_NAME, item.serializeNBT());
		tag.putShort(TAG_PROGRESS, process_progress);
		tag.putInt(TAG_RENDER, render);
		tag.putInt(TAG_FUEL, fuel);
	}
	
	protected void loaddata(CompoundTag tag) {
		if(tag.contains(TAG_NAME)) {
			item.deserializeNBT(tag.getCompound(TAG_NAME));
		}
		if(tag.contains(TAG_PROGRESS)) {
			process_progress = tag.getShort(TAG_PROGRESS);
		}
		if(tag.contains(TAG_RENDER)) {
			render = tag.getShort(TAG_RENDER);
		}
		if(tag.contains(TAG_FUEL)) {
			fuel = tag.getShort(TAG_FUEL);
		}
	}
	
	private static boolean shouldUpdateClient = false; 
	

	@Override
	public void servertick() {
		energy_consume = 0;
		shouldUpdateClient = false;
		ItemStack fuelItem = item.getStackInSlot(0);
		if(fuel<24000) {
			if(fuelItem.getItem()==bottled_methane) {
				fuel += 200;
				if(fuel>24000) fuel = 24000;
				shouldUpdateClient = true;
				fuelItem.shrink(1);
				item.setStackInSlot(0, fuelItem);
				giveBucket();
			}else if(fuelItem.getItem()==Items.COAL) {
				fuel += 200;
				if(fuel>24000) fuel = 24000;
				shouldUpdateClient = true;
				fuelItem.shrink(1);
				item.setStackInSlot(0, fuelItem);
			}
		}
		ItemStack stack2 = item.getStackInSlot(2);
		ItemStack stack3 = item.getStackInSlot(3);
		if(fuel>=2000&&!stack2.isEmpty()&&stack3.getCount()<64) {
			process_progress += 1;
			if(process_progress>=400) {
				process_progress=0;
				fuel-=2000;
				produceOutput(stack2,stack3);
				shouldUpdateClient = true;
			}
			//int progress = process_progress/150;//除到10
			int progress = process_progress/40;//除到10
	        int render = progress * 22 / 10; //移动像素/距离
			if(render != this.render) {
				this.render = render;
				shouldUpdateClient = true;
			}
		}else {
			process_progress = 0;
			if(this.render!=0) {
				shouldUpdateClient = true;
				this.render = 0;
			}
		}
		if(shouldUpdateClient) {
			level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
		}
		setChanged();
		return;
	}
	
	private void giveBucket() {
		ItemStack stack1 = item.getStackInSlot(1);
		if(stack1.isEmpty()) {
			item.setStackInSlot(1, new ItemStack(Items.BUCKET));
		}else if(stack1.getCount()<64) {
			stack1.grow(1);
			item.setStackInSlot(1, stack1);
		}
		
	}

	private void produceOutput(ItemStack i2,ItemStack i3) {
		i2.shrink(1);
		if(i3.isEmpty()) {
			item.setStackInSlot(3, new ItemStack(bottled_fuel));
		}else {
			i3.grow(1);
			item.setStackInSlot(3,i3);
		}
		item.setStackInSlot(2, i2);
		
	}
	
	@Override
	public void clienttick() {}

}