package machine.energy.consumer.microwaveoven;

import java.util.HashMap;
import java.util.Map;

import item.ItemRegister;
import machine.energy.consumer.ConsumerEntity;
import machine.energy.consumer.IConsumer;
import machine.registry.MBlockEntityRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
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
import tags.register.TagkeyRegistry;
import util.net.EnergyNetProcess;

/**
 * 加热食物或海绵
 * 加热罐头或者鸡蛋导致爆炸
 * 时间不定
 * @author NANMEDA
 * */
public class MicrowaveOvenEntity extends ConsumerEntity implements IConsumer{

	private int energy_consume = 0;
	private int energy_supply = 100;
	private int pg_max = 100;
	private int render = 0;
	private short process_progress = -1;//这是倒着来着，看下面就知道了
	public boolean is_button = false;
	private boolean should_playsound = false;
	
	static protected int itemstack_number = 2;
	
	public int getRenderDis() {
		return render;
	}
	
	@Override
    public boolean lineVisible() {
    	return false;
    }
	
	public MicrowaveOvenEntity(BlockPos pos, BlockState pBlockState) {
		super(MBlockEntityRegister.microwaveoven_BLOCKENTITY.get(), pos, pBlockState);
		this.FULL_ENERGY = 8;
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
            if(slot==1) {
            	//不可以检测大于一不更新，因为一加热会加热一堆，而不是一个一个
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
	
	private static final String TAG_ID = "id";
	private static final String TAG_NET = "connection";
	private static final String TAG_NAME = "Item";
	private static final String TAG_PROGRESS = "progress";
	private static final String tAG_IS_BUTTON = "button";
	private static final String TAG_RENDER = "render";	//不放在tag里面的不会被同步到client ！！！!!!即使设置同步
	
	protected void savedata(CompoundTag tag) {
		tag.put(TAG_NAME, item.serializeNBT());
		tag.putShort(TAG_PROGRESS, process_progress);
		tag.putBoolean(tAG_IS_BUTTON, is_button);
		tag.putInt(TAG_RENDER, render);
		
		tag.putLong(TAG_ID, this.NET);
	    ListTag connectList = new ListTag();
	    for (Map.Entry<BlockPos, Boolean> entry : connectMap.entrySet()) {
	        CompoundTag entryTag = new CompoundTag();
	        entryTag.putLong("pos", entry.getKey().asLong());
	        // 存储 Boolean 值
	        entryTag.putBoolean("connected", entry.getValue());
	        connectList.add(entryTag);
	    }
	    tag.put(TAG_NET, connectList);
	 
	}
	
	protected void loaddata(CompoundTag tag) {
		if(tag.contains(TAG_NAME)) {
			item.deserializeNBT(tag.getCompound(TAG_NAME));
		}
		if(tag.contains(TAG_PROGRESS)) {
			this.process_progress = tag.getShort(TAG_PROGRESS);
		}
		if(tag.contains(tAG_IS_BUTTON)) {
			this.is_button = tag.getBoolean(tAG_IS_BUTTON);
		}
		if(tag.contains(TAG_RENDER)) {
			this.render = tag.getShort(TAG_RENDER);
		}
		
		if(tag.contains(TAG_ID)) {
			this.NET = tag.getLong(TAG_ID);
		}
	    if (tag.contains(TAG_NET)) {
	        ListTag connectList = tag.getList(TAG_NET, 10);
	        Map<BlockPos, Boolean> loadedMap = new HashMap<>();
	        for (int i = 0; i < connectList.size(); i++) {
	            CompoundTag entryTag = connectList.getCompound(i);
	            
	            BlockPos pos = BlockPos.of(entryTag.getLong("pos"));
	            boolean connected = entryTag.getBoolean("connected");
	            
	            loadedMap.put(pos, connected);
	        }
	        connectMap = loadedMap;
	    }
	}
	
	@Override
	public boolean servertick(boolean u) {
		this.energy_consume = 0;
		this.energy_supply = getEnergySupplyLevel();
		ItemStack[] stack = new ItemStack[2];
		for (int i = 0; i < 2; i++) {
		    stack[i] = item.getStackInSlot(i);
		}
		if(stack[0]==ItemStack.EMPTY){
			this.is_button = false;			//按钮弹起
			this.process_progress = -1;		//-1状态就是准备好了的状态
			
	        int render = 0;
			if(render != this.render) {
				this.render = render;
				level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
			}
			return false;
		}
		if(stack[1]!=ItemStack.EMPTY) {
			this.is_button = false;//按钮弹起
			this.process_progress = -1;
	        int render = 0;
			if(render != this.render) {
				this.render = render;
				level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
			}
			//System.out.println("1 is nor EMEPTY");
			return false;
		}
		if(this.process_progress>0) {
			//System.out.println("IS processing");
			this.process_progress -= (energy_supply > 75) ? 3 : ((energy_supply > 50) ? 2 : ((energy_supply > 25) ? 1 : 0));
			
			if(this.energy_supply==0) {
				this.is_button = false;
				this.process_progress = -1;
		        int render = 0;
				if(render != this.render) {
					this.render = render;
					level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
				}
				return false;
			}
			
			if(this.process_progress<0) {this.process_progress = 0;}
			this.energy_consume = this.FULL_ENERGY;
			
			//int progress = process_progress/150;//除到10
			int progress = 10 - 10*process_progress/pg_max;
	        int render = progress * 22 / 10; //移动像素/距离
			if(render != this.render) {
				this.render = render;
			}
			
			setChanged();
			level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
			return false;
		}
		if(!is_button) {
			//System.out.println("not button");
			return false;
		}
		Item uncookfood = stack[0].getItem();
		short cook_food;
		if (uncookfood == Items.POTATO) {cook_food=0;
		} else if (uncookfood == Items.COD) {cook_food=1;
		} else if (uncookfood == Items.SALMON) {cook_food=2;
		} else if (uncookfood == Items.BEEF) {cook_food=3;
		} else if (uncookfood == Items.CHICKEN) {cook_food=4;
		} else if (uncookfood == Items.MUTTON) {cook_food=5;
		} else if (uncookfood == Items.RABBIT) {cook_food=6;
		} else if (uncookfood == Items.PORKCHOP) {cook_food=7;
		} else if (uncookfood == Items.WET_SPONGE) {cook_food=8;
		} else {
		    //for (int i = 1; i <= 9; i++) {
		        //if (uncookfood == ItemRegister.FOOD_ITEMS[i].get()) {
		        if(uncookfood.getDefaultInstance().is(TagkeyRegistry.CAN_FOOD_TAG)) {//哪个更快捏？
		            return true;
		        }
		    //}
			if(uncookfood == Items.EGG||uncookfood == ItemRegister.CAN.get()){
				return true;
			} 
			//System.out.println("food not UNCOOK");
			this.is_button = false;//按钮弹起
			this.process_progress = -1;
			
	        int render = 0;
			if(render != this.render) {
				this.render = render;
				level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
			}
			
			return false;
		}
		int uncookfood_number = stack[0].getCount();

		if(this.process_progress==0) {
			item.setStackInSlot(0, ItemStack.EMPTY);
			ItemStack cooked_food = ItemStack.EMPTY;
			switch (cook_food) {
		    case 0:
		        cooked_food = new ItemStack(Items.BAKED_POTATO, uncookfood_number);
		        break;
		    case 1:
		        cooked_food = new ItemStack(Items.COOKED_COD, uncookfood_number);
		        break;
		    case 2:
		        cooked_food = new ItemStack(Items.COOKED_SALMON, uncookfood_number);
		        break;
		    case 3:
		        cooked_food = new ItemStack(Items.COOKED_BEEF, uncookfood_number);
		        break;
		    case 4:
		        cooked_food = new ItemStack(Items.COOKED_CHICKEN, uncookfood_number);
		        break;
		    case 5:
		        cooked_food = new ItemStack(Items.COOKED_MUTTON, uncookfood_number);
		        break;
		    case 6:
		        cooked_food = new ItemStack(Items.COOKED_RABBIT, uncookfood_number);
		        break;
		    case 7:
		        cooked_food = new ItemStack(Items.COOKED_PORKCHOP, uncookfood_number);
		        break;
		    case 8:
		        cooked_food = new ItemStack(Items.SPONGE, uncookfood_number);
		        break;
		    default:
		        return false;
		}
			item.setStackInSlot(1, cooked_food);
			this.process_progress=-1;
			this.is_button = false;//按钮弹起
			this.should_playsound = true;
			this.render = 0;
			this.level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
		}else if(process_progress==-1) {
			this.process_progress = (short) ((uncookfood_number > 32) ? 30*60 : ((uncookfood_number > 16) ? 20*60 : ((uncookfood_number > 6) ? 10*60 : 5*60)));
			//为什么要这么做呢，3*是因为当满电时，process_progress减的速度是3;  *20因为一秒20tick,3*20=60
			this.pg_max = process_progress;
		}
		setChanged();
		return false;
	}
	
	@Override
	public void clienttick() {
		if(this.should_playsound) {
			this.should_playsound = false;
			SoundEvent soundEvent = SoundEvents.VILLAGER_YES;
	        if (soundEvent != null) {
	        	level.playLocalSound(worldPosition,soundEvent,null, 1.0f, 1.0f,true);
	        }
		}
		return;
	}
	
	@Override
	public int getEnergyConsume() {
		return this.energy_consume;
	}
	
	@Override
	public int getEnergySupplyLevel() {
		if(this.haveNet()) {
			return EnergyNetProcess.getEnergyNet(this.getNet()).getSupplyLevel();
		}else {
			return 0;
		}
	}

	@Override
	protected void servertick() {
	}

}