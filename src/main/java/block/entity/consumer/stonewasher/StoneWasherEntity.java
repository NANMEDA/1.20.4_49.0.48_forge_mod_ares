package block.entity.consumer.stonewasher;

import java.util.Random;

import block.entity.BlockEntityRegister;
import block.entity.consumer.PowerConsumerEntity;
import block.norm.BlockBasic;
import block.norm.BlockRegister;
import item.ItemRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
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
 * 获得原版石头
 * 一定概率有生铁粒
 * 2.5s 一般
 * @author NANMEDA
 * */
public class StoneWasherEntity extends PowerConsumerEntity{

	private int render = 0;
	private short process_progress = 0;
	private int water = 0;
	public boolean is_button = false;
	private boolean should_playsound = false;
	
	private static Item mar_stone_item = BlockRegister.COMMON_BLOCK_ITEMS[BlockBasic.getIdFromName("mar_stone")].get();
	private static Item mar_deep_stone_item = BlockRegister.COMMON_BLOCK_ITEMS[BlockBasic.getIdFromName("mar_deep_stone")].get();
	private static Item raw_iron_nugget = ItemRegister.MATERIAL_ITEMS[11].get();
	
	private static Random rd = new Random();
	
	static protected int itemstack_number = 4;
	
	public int getRenderDis() {
		return render;
	}
	
	public StoneWasherEntity(BlockPos pos, BlockState pBlockState) {
		super(BlockEntityRegister.stonewasher_BLOCKENTITY.get(), pos, pBlockState);
		this.energy_consume = 20;
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
	private final String TAG_WATER = "water";
	private final String TAG_RENDER = "render";	//不放在tag里面的不会被同步到client ！！！!!!即使设置同步
	
	protected void savedata(CompoundTag tag) {
		tag.put(TAG_NAME, item.serializeNBT());
		tag.putShort(TAG_PROGRESS, process_progress);
		tag.putInt(TAG_RENDER, render);
		tag.putInt(TAG_WATER, water);
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
		if(tag.contains(TAG_WATER)) {
			water = tag.getShort(TAG_WATER);
		}
	}
	
	private static boolean shouldUpdateClient = false; 
	

	@Override
	public void servertick() {
		energy_consume = 0;
		shouldUpdateClient = false;
		ItemStack waterItem = item.getStackInSlot(1);
		if(waterItem.getItem()==Items.WATER_BUCKET) {
			water += 1000;
			if(water>10000) water = 10000;
			shouldUpdateClient = true;
			waterItem = new ItemStack(Items.BUCKET,1);
			item.setStackInSlot(1, waterItem);
		}
		ItemStack stack0 = item.getStackInSlot(0);
		ItemStack stack2 = item.getStackInSlot(2);
		ItemStack stack3 = item.getStackInSlot(3);
		if(stackCanBeWashed(stack0,stack2,stack3)) {
			energy_consume = 20;
			process_progress += (energy_supply > 75) ? 3 : ((energy_supply > 50) ? 2 : ((energy_supply > 25) ? 1 : 0));
			if(energy_supply>25 && water > 1) {
				water -= 2;
				process_progress += 2;
				shouldUpdateClient = true;
			}
			if(process_progress>=150) {
				process_progress=0;
				produceOutput(stack0,stack2,stack3);
				shouldUpdateClient = true;
			}
			//int progress = process_progress/150;//除到10
			int progress = process_progress/15;//除到10
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
	
	private void produceOutput(ItemStack i0,ItemStack i2,ItemStack i3) {
		Item washed = i0.getItem();
		if(washed == mar_stone_item) {
			i0.shrink(1);
			if(!i2.isEmpty()) { 
				i2.grow(1);
			}else {
				i2 = new ItemStack(Items.COBBLESTONE,1);
			}
			if(rd.nextInt(16)==1) {
				if(!i3.isEmpty()) { 
					i3.grow(1);
				}else {
					i3 = new ItemStack(raw_iron_nugget,1);
				}
			}
		}else if(washed == mar_deep_stone_item) {
			i0.shrink(1);
			if(!i2.isEmpty()) { 
				i2.grow(1);
			}else {
				i2 = new ItemStack(Items.COBBLED_DEEPSLATE,1);
			}
			if(rd.nextInt(12)==1) {
				if(!i3.isEmpty()) { 
					i3.grow(1);
				}else {
					i3 = new ItemStack(raw_iron_nugget,1);
				}
			}
		}
		item.setStackInSlot(0, i0);
		item.setStackInSlot(2, i2);
		item.setStackInSlot(3, i3);
	}

	private boolean stackCanBeWashed(ItemStack i0,ItemStack i2,ItemStack i3) {
		Item washed = i0.getItem();
		if(washed == mar_stone_item) {
			if(i2.getCount()<64&&i3.getCount()<64) {
				if(i2.getItem()!=Items.COBBLED_DEEPSLATE) {
					return true;
				}
			}
		}
		else if(washed == mar_deep_stone_item) {
			if(i2.getCount()<64&&i3.getCount()<64) {
				if(i2.getItem()!=Items.COBBLESTONE) {
					return true;
				}
			}
		}
		return false;
	}
	

	@Override
	public void clienttick() {
		if(should_playsound) {
			should_playsound = false;
			SoundEvent soundEvent = SoundEvents.VILLAGER_YES;
	        if (soundEvent != null) {
	        	level.playLocalSound(worldPosition,soundEvent,null, 1.0f, 1.0f,true);
	        }
		}
		return;
	}

}