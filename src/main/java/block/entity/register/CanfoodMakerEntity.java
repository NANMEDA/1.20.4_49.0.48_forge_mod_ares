package block.entity.register;

import org.stringtemplate.v4.compiler.STParser.ifstat_return;

import com.item.register.ItemRegister;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.Containers;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class CanfoodMakerEntity extends PowerConsumerEntity{
	
	private short process_progress = 0;//100 upborder
	//full = 100

	static protected int itemstack_number=5;
	
	public CanfoodMakerEntity(BlockPos pos, BlockState pBlockState) {
		super(BlockEntityRegister.canfoodmaker_BLOCKENTITY.get(), pos, pBlockState);
		this.energy_consume = 5;
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
	
	protected void savedata(CompoundTag tag) {
		tag.put(TAG_NAME, item.serializeNBT());
		tag.putShort(TAG_PROGRESS, process_progress);
	}

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
		//System.out.println("come");
		ItemStack[] stack = new ItemStack[5];
		for (int i = 0; i < 5; i++) {
		    stack[i] = item.getStackInSlot(i);
		    if(i<3) {
		    	Item temp_item = stack[i].getItem();
		    	if(temp_item==Items.POTATO) {stack[i] = new ItemStack(Items.BAKED_POTATO,stack[i].getCount());}
		    	else if(temp_item==Items.BEEF) {stack[i] = new ItemStack(Items.COOKED_BEEF,stack[i].getCount());}
		    	else if(temp_item==Items.PORKCHOP) {stack[i] = new ItemStack(Items.COOKED_PORKCHOP,stack[i].getCount());}
		    	else if(temp_item==Items.MUTTON) {stack[i] = new ItemStack(Items.COOKED_MUTTON,stack[i].getCount());}
		    	else if(temp_item==Items.COD||temp_item==Items.SALMON||temp_item==Items.COOKED_SALMON) {stack[i] = new ItemStack(Items.COOKED_COD,stack[i].getCount());}
		    	else if(temp_item==Items.RABBIT) {stack[i] = new ItemStack(Items.COOKED_RABBIT,stack[i].getCount());}
		    	else if(temp_item==Items.CHICKEN) {stack[i] = new ItemStack(Items.COOKED_CHICKEN,stack[i].getCount());}
		    }
		}
		if(!(stack[0].getItem()==stack[1].getItem())||!(stack[0].getItem()==stack[2].getItem())) {
			//System.out.println("not equal food");
			return;
		}
		if ((stack[3].getItem() == Items.IRON_NUGGET && stack[3].getCount()<9)||stack[3]==ItemStack.EMPTY) {
			//System.out.println("no iron");
			return;
		}
		if(energy_supply>0) {
			Item corresponding_item = null;
			Item item0 = stack[0].getItem();
			int foodItemId;
				
			if (item0 == Items.CARROT) {
			    foodItemId = 1;
			} else if (item0== Items.BAKED_POTATO) {
			    foodItemId = 2;
			} else if (item0==Items.COOKED_BEEF) {
			    foodItemId = 3;
			} else if (item0== Items.COOKED_PORKCHOP) {
			    foodItemId = 4;
			} else if (item0== Items.COOKED_MUTTON) {
			    foodItemId = 5;
			} else if (item0== Items.COOKED_COD) {
			    foodItemId = 6;
			} else if (item0== Items.BREAD) {
			    foodItemId = 7;
			} else if (item0== Items.COOKED_RABBIT) {
			    foodItemId = 8;
			} else if (item0== Items.COOKED_CHICKEN) {
			    foodItemId = 9;
			} else {
			    return;
			}
			
			corresponding_item = ItemRegister.FOOD_ITEMS[foodItemId].get();
			
			if(!(stack[4].getItem()==corresponding_item) && stack[4]!=ItemStack.EMPTY) {
				//System.out.println("not equal can");
				return;
			}
			if(stack[4].getCount()<64) {
				//System.out.println("into making");
				energy_consume = 5;
				if(process_progress>0) {
					//System.out.println("process!!");
					process_progress -= (energy_supply > 75) ? 3 : ((energy_supply > 50) ? 2 : ((energy_supply > 25) ? 1 : 0));
					
					setChanged();
					return;
				}
				process_progress = 20*15;
				
				ItemStack corresponding_items = new ItemStack(corresponding_item, stack[4].getCount()+1);
				ItemStack items0 = new ItemStack(item.getStackInSlot(0).getItem(), stack[0].getCount()-1);
				ItemStack items1 = new ItemStack(item.getStackInSlot(1).getItem(), stack[1].getCount()-1);
				ItemStack items2 = new ItemStack(item.getStackInSlot(2).getItem(), stack[2].getCount()-1);
				ItemStack canshell;
				if(stack[3].getItem()==Items.IRON_INGOT) {
					canshell = new ItemStack(Items.IRON_INGOT, stack[3].getCount()-1);
				}else {
					canshell = new ItemStack(Items.IRON_NUGGET, stack[3].getCount()-9);
				}
				
				item.setStackInSlot(0, items0);
				item.setStackInSlot(1, items1);
				item.setStackInSlot(2, items2);
				item.setStackInSlot(3, canshell);
				item.setStackInSlot(4, corresponding_items);
			}

		}

		//15s*20=300 maring J，10s*20=200 maring J
		//一个木板 = 200到300 maring J。
		
		setChanged();
	}
	
	@Override
	public void clienttick() {
	}

}
