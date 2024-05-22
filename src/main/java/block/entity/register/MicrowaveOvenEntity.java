package block.entity.register;

import com.item.register.ItemRegister;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SmokerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;

public class MicrowaveOvenEntity extends PowerConsumerEntity{

	private short process_progress = -1;//这是倒着来着，看下面就知道了
	private boolean is_button = true;
	
	public MicrowaveOvenEntity(BlockPos pos, BlockState pBlockState) {
		super(BlockEntityRegister.microwaveoven_BLOCKENTITY.get(), pos, pBlockState);
		this.energy_consume = 15;
		this.itemstack_number = 2;
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
	
	@Override
	protected void savedata(CompoundTag tag) {
		tag.put(TAG_NAME, item.serializeNBT());
		tag.putShort(TAG_PROGRESS, process_progress);
	}
	@Override
	protected void loaddata(CompoundTag tag) {
		if(tag.contains(TAG_NAME)) {
			item.deserializeNBT(tag.getCompound(TAG_NAME));
		}
		if(tag.contains(TAG_PROGRESS)) {
			process_progress = tag.getShort(TAG_PROGRESS);
		}
	}
	

	@Override
	public void servertick() {
		energy_consume = 0;
		if(process_progress>0) {
			System.out.println("IS processing");
			process_progress -= (energy_supply > 75) ? 3 : ((energy_supply > 50) ? 2 : ((energy_supply > 25) ? 1 : 0));
			energy_consume = 4;
			setChanged();
			return;
		}
		if(!is_button) {
			System.out.println("not button");
			return;
		}
		ItemStack[] stack = new ItemStack[2];
		for (int i = 0; i < 2; i++) {
		    stack[i] = item.getStackInSlot(i);
		}
		if(stack[0]==ItemStack.EMPTY){
			//is_button = false;//按钮弹起
			System.out.println("0 EMPTY");
			return;
		}
		if(stack[0].getItem().getFoodProperties()==null) {
			is_button = false;//按钮弹起
			System.out.println("0 NOT FOOD");
		}
		if(stack[1]!=ItemStack.EMPTY) {
			is_button = false;//按钮弹起
			System.out.println("1 is nor EMEPTY");
			return;
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
			System.out.println("0 not UNCOOK");
			is_button = false;//按钮弹起
			return;
		}
		int uncookfood_number = stack[0].getCount();

		if(process_progress==0) {
			item.setStackInSlot(0, ItemStack.EMPTY);
			ItemStack cooked_food = ItemStack.EMPTY;
	        if (cook_food == 0) {
	            cooked_food = new ItemStack(Items.BAKED_POTATO, uncookfood_number);
	        } else if (cook_food == 1) {
	            cooked_food = new ItemStack(Items.COOKED_COD, uncookfood_number);
	        } else if (cook_food == 2) {
	            cooked_food = new ItemStack(Items.COOKED_SALMON, uncookfood_number);
	        } else if (cook_food == 3) {
	            cooked_food = new ItemStack(Items.COOKED_BEEF, uncookfood_number);
	        } else if (cook_food == 4) {
	            cooked_food = new ItemStack(Items.COOKED_CHICKEN, uncookfood_number);
	        } else if (cook_food == 5) {
	            cooked_food = new ItemStack(Items.COOKED_MUTTON, uncookfood_number);
	        } else if (cook_food == 6) {
	            cooked_food = new ItemStack(Items.COOKED_RABBIT, uncookfood_number);
	        } else if (cook_food == 7) {
	            cooked_food = new ItemStack(Items.COOKED_PORKCHOP, uncookfood_number);
	        }
			item.setStackInSlot(1, cooked_food);
			process_progress=-1;
			is_button = false;//按钮弹起
		}else if(process_progress==-1) {
			process_progress = (short) ((uncookfood_number > 32) ? 3*15*20 : ((uncookfood_number > 16) ? 3*10*20 : ((uncookfood_number > 6) ? 3*5*20 : 3*2*20)));
		}
		setChanged();
	}
	
	@Override
	public void clienttick() {
	}

}