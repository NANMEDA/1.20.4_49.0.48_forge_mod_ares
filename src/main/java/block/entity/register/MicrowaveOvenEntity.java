package block.entity.register;

import org.antlr.v4.parse.ANTLRParser.finallyClause_return;

import com.item.register.ItemRegister;

import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SmokerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.SoundAction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.registries.ForgeRegistries;
import tags.register.TagkeyRegister;

public class MicrowaveOvenEntity extends PowerConsumerEntity{

	private short process_progress = -1;//这是倒着来着，看下面就知道了
	private boolean is_button = true;
	
	static protected int itemstack_number = 2;
	
	
	public MicrowaveOvenEntity(BlockPos pos, BlockState pBlockState) {
		super(BlockEntityRegister.microwaveoven_BLOCKENTITY.get(), pos, pBlockState);
		this.energy_consume = 4;
	}
	
    protected final ItemStackHandler item = new ItemStackHandler(itemstack_number) {//必须要在这里创建，ItemStackHandler不可被修改
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
	private final String tAG_IS_BUTTON = "button";
	
	protected void savedata(CompoundTag tag) {
		tag.put(TAG_NAME, item.serializeNBT());
		tag.putShort(TAG_PROGRESS, process_progress);
		tag.putBoolean(tAG_IS_BUTTON, is_button);
	}
	
	protected void loaddata(CompoundTag tag) {
		if(tag.contains(TAG_NAME)) {
			item.deserializeNBT(tag.getCompound(TAG_NAME));
		}
		if(tag.contains(TAG_PROGRESS)) {
			process_progress = tag.getShort(TAG_PROGRESS);
		}
		if(tag.contains(tAG_IS_BUTTON)) {
			is_button = tag.getBoolean(tAG_IS_BUTTON);
		}
	}
	

	@Override
	public boolean servertick(boolean u) {
		energy_consume = 0;
		if(process_progress>0) {
			//System.out.println("IS processing");
			process_progress -= (energy_supply > 75) ? 3 : ((energy_supply > 50) ? 2 : ((energy_supply > 25) ? 1 : 0));
			if(process_progress<0) {process_progress = 0;}
			energy_consume = 8;
			setChanged();
			return false;
		}
		if(!is_button) {
			System.out.println("not button");
			//return false;
		}
		ItemStack[] stack = new ItemStack[2];
		for (int i = 0; i < 2; i++) {
		    stack[i] = item.getStackInSlot(i);
		}
		if(stack[0]==ItemStack.EMPTY){
			//is_button = false;//按钮弹起
			//System.out.println("0 EMPTY");
			return false;
		}
		if(stack[1]!=ItemStack.EMPTY) {
			is_button = false;//按钮弹起
			//System.out.println("1 is nor EMEPTY");
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
		} else {
		    //for (int i = 1; i <= 9; i++) {
		        //if (uncookfood == ItemRegister.FOOD_ITEMS[i].get()) {
		        if(uncookfood.getDefaultInstance().is(TagkeyRegister.CAN_FOOD_TAG)) {//哪个更快捏？
		            return true;
		        }
		    //}
			if(uncookfood == Items.EGG ){
				return true;
			} 
			//System.out.println("food not UNCOOK");
			is_button = false;//按钮弹起
			return false;
		}
		int uncookfood_number = stack[0].getCount();

		if(process_progress==0) {
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
		    default:
		        return false;
		}
			item.setStackInSlot(1, cooked_food);
			process_progress=-1;
			is_button = false;//按钮弹起
			SoundEvent soundEvent = SoundEvents.VILLAGER_YES;
	            if (soundEvent != null) {
	            level.playSound(null, worldPosition, soundEvent,null, 1.0f, 1.0f);
	        }
		}else if(process_progress==-1) {
			process_progress = (short) ((uncookfood_number > 32) ? 3*30*20 : ((uncookfood_number > 16) ? 3*20*20 : ((uncookfood_number > 6) ? 3*10*20 : 3*5*20)));
			
		}
		setChanged();
		return false;
	}
	
	@Override
	public void clienttick() {
		return;
	}


}