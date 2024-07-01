package block.entity.consumer.canfoodmaker;

import com.item.ItemRegister;
import com.item.can.CanHelper;
import com.item.can.ItemCanNBT;

import block.entity.BlockEntityRegister;
import block.entity.consumer.PowerConsumerEntity;
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
import tags.register.TagkeyRegister;

public class CanfoodMakerEntity extends PowerConsumerEntity{
	
	public short process_progress;
	private int renderHeight;
	
	public int getRenderHeight() {
		return renderHeight;
	}

	static protected int itemstack_number=5;
	
	public CanfoodMakerEntity(BlockPos pos, BlockState pBlockState) {
		super(BlockEntityRegister.canfoodmaker_BLOCKENTITY.get(), pos, pBlockState);
		this.FULL_ENERGY_CONSUPTION = 5;
	}
	
    protected final ItemStackHandler item = new ItemStackHandler(itemstack_number) {//必须要在这里创建，ItemStackHandler不可被修改
        @Override
        public void onLoad() {
            super.onLoad();
        }

        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            /*
            if(slot==4) {
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
	private final String TAG_RENDER = "render";	//不放在tag里面的不会被同步到client ！！！!!!即使设置同步
	
	protected void savedata(CompoundTag tag) {
		tag.put(TAG_NAME, item.serializeNBT());
		tag.putShort(TAG_PROGRESS, process_progress);
		tag.putInt(TAG_RENDER, renderHeight);
	}

	protected void loaddata(CompoundTag tag) {
		if(tag.contains(TAG_NAME)) {
			item.deserializeNBT(tag.getCompound(TAG_NAME));
		}
		if(tag.contains(TAG_PROGRESS)) {
			process_progress = tag.getShort(TAG_PROGRESS);
		}
		if(tag.contains(TAG_RENDER)) {
			renderHeight = tag.getShort(TAG_RENDER);
		}
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void servertick() {
		energy_consume = 0;
		//System.out.println("come");
		ItemStack[] stack = new ItemStack[5];
		for (int i = 0; i < 5; i++) {
		    stack[i] = item.getStackInSlot(i);
		}
		if(!stack[0].is(TagkeyRegister.CAN_FOOD_MATERIAL_TAG)||!stack[1].is(TagkeyRegister.CAN_FOOD_MATERIAL_TAG)||!stack[2].is(TagkeyRegister.CAN_FOOD_MATERIAL_TAG)) {
			process_progress = 0;
	        int renderHeight = 0;
			if(renderHeight != this.renderHeight) {
				this.renderHeight = renderHeight;
				level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
			}
			return;
		}
		/*
		if(!(stack[0].getItem()==stack[1].getItem())||!(stack[0].getItem()==stack[2].getItem())) {
			//System.out.println("not equal food");
			process_progress = 0;
	        int renderHeight = 0;
			if(renderHeight != this.renderHeight) {
				this.renderHeight = renderHeight;
				level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
			}
			return;
		}*/
		
		if ((stack[3].getItem() == Items.IRON_NUGGET && stack[3].getCount()<9)||stack[3]==ItemStack.EMPTY) {
			//System.out.println("no iron");
			process_progress = 0;
	        int renderHeight = 0;
			if(renderHeight != this.renderHeight) {
				this.renderHeight = renderHeight;
				level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
			}
			return;
		}
		if(energy_supply>0) {

			if(stack[4].isEmpty()) {
				//System.out.println("into making");
				energy_consume = 5;
				if(process_progress<300) {
					//System.out.println("process!!");
					process_progress += (energy_supply > 75) ? 3 : ((energy_supply > 50) ? 2 : ((energy_supply > 25) ? 1 : 0));
					
			        int progress = process_progress/30;
			        int renderHeight = progress * 15 / 10;
					if(renderHeight != this.renderHeight) {
						this.renderHeight = renderHeight;
						level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
					}
					setChanged();
					return;
				}
				process_progress = 0;
				int renderHeight = 0;
				if(renderHeight != this.renderHeight) {
					this.renderHeight = renderHeight;
					level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
				}
				
				Item m0 = stack[0].getItem();
				Item m1 = stack[1].getItem();
				Item m2 = stack[2].getItem();
				int nutrition = CanHelper.getNutrition(m0) + CanHelper.getNutrition(m1) + CanHelper.getNutrition(m2);
				float saturation = CanHelper.getSaturation(m0) + CanHelper.getSaturation(m1) + CanHelper.getSaturation(m2);
				int vegetable = CanHelper.getVegetableValue(m0)+CanHelper.getVegetableValue(m1)+CanHelper.getVegetableValue(m2);
				int meat = CanHelper.getMeatValue(m0)+CanHelper.getMeatValue(m1)+CanHelper.getMeatValue(m2);
				int fish = CanHelper.getFishValue(m0)+CanHelper.getFishValue(m1)+CanHelper.getFishValue(m2);
				int fruit = CanHelper.getFruitValue(m0)+CanHelper.getFruitValue(m1)+CanHelper.getFruitValue(m2);
				int corn = CanHelper.getCornValue(m0)+CanHelper.getCornValue(m1)+CanHelper.getCornValue(m2);
				stack[0].shrink(1);
				stack[1].shrink(1);
				stack[2].shrink(1);
				item.setStackInSlot(0, stack[0]);
				item.setStackInSlot(1, stack[1]);
				item.setStackInSlot(2, stack[2]);
				if(stack[3].getItem()==Items.IRON_INGOT) {
					stack[3].shrink(1);
				}else {
					stack[3].shrink(9);
				}
				item.setStackInSlot(3, stack[3]);
				//item.setStackInSlot(4, corresponding_items);
				
				Item itemCan = ItemRegister.CAN.get();
				ItemStack can = new ItemStack(itemCan,1);
				
				ItemCanNBT.setNutrition(can, nutrition);
				ItemCanNBT.setSaturation(can, saturation);
				ItemCanNBT.setVegetable(can, vegetable);
				ItemCanNBT.setMeat(can, meat);
				ItemCanNBT.setFish(can, fish);
				ItemCanNBT.setCorn(can, corn);
				ItemCanNBT.setFruit(can, fruit);
				CanHelper.setModel(can, vegetable, meat, fish, corn, fruit);
				// 设置槽位4中的物品为itemStack
				item.setStackInSlot(4, can);
			}
		//15s*20=300 maring J，10s*20=200 maring J
		//一个木板 = 200到300 maring J。
		}
		setChanged();
	}
	
	@Override
	public void clienttick() {
	}

}
