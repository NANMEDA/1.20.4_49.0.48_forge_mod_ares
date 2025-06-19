package com.main.maring.machine.energy.consumer.coredigger;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.main.maring.item.ItemRegister;
import com.main.maring.machine.energy.consumer.ConsumerEntity;
import com.main.maring.machine.energy.consumer.IConsumer;
import com.main.maring.machine.registry.MBlockEntityRegister;
import com.main.maring.machine.registry.MBlockRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import com.main.maring.util.net.EnergyNetProcess;

/**
 * @author NANMEDA
 * */
@SuppressWarnings("deprecation")
public class CoreDiggerEntity extends ConsumerEntity implements IConsumer{

	private int energy_consume = 0;
	
	private static LazyLoadedValue<Block> SUCKER = new LazyLoadedValue<>(()->MBlockRegister.CORESUCKER_B.get());
	private static Random rd = new Random();
	static protected int itemstack_number = 6;
	public boolean cantFindSucker = true;
	public boolean cantTouchCore = true;
	
	@Override
    public boolean lineVisible() {
    	return true;
    }
	
	public CoreDiggerEntity(BlockPos pos, BlockState pBlockState) {
		super(MBlockEntityRegister.COREDIGGER_BE.get(), pos, pBlockState);
		this.FULL_ENERGY = 75;
	}
	
    protected final ItemStackHandler item = new ItemStackHandler(itemstack_number) {//必须要在这里创建，ItemStackHandler不可被修改
        @Override
        public void onLoad() {
            super.onLoad();
            //System.out.println("entity is load");
        }

        @Override
        protected void onContentsChanged(int slot) {
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
	private final String TAG_ID = "id";

	protected void savedata(CompoundTag tag) {
		super.savedata(tag);
		tag.put(TAG_NAME, item.serializeNBT());
	}
	
	protected void loaddata(CompoundTag tag) {
		super.loaddata(tag);
		if(tag.contains(TAG_NAME)) {
			item.deserializeNBT(tag.getCompound(TAG_NAME));
		}
	}
	
	
	@Override
	public void servertick() {
		this.energy_consume = 0;
		if(this.level.getBlockState(this.worldPosition.below()).is(SUCKER.get())){
			this.energy_consume = FULL_ENERGY;
			if(rd.nextInt(0,12*20)!=0) return;
			int supplylevel = getEnergySupplyLevel();
			int r = supplylevel>=100 ? 1 : supplylevel>=75 ? 2  : supplylevel>=50 ? 3 : supplylevel>=25 ? 4 : 16;
			if(rd.nextInt(r)==0) {
				int d = CoreSucker.getCoreDis(this.level,this.worldPosition.below());
				if(d>8) {
					ItemStack rdItemStack = getRandomOutput();
					for(int i=0;i<itemstack_number;i++) {
						ItemStack stack = item.getStackInSlot(i);
						if(stack.isEmpty()) {
							item.setStackInSlot(i, rdItemStack);
							setChanged();
							level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
							return;
						}else if(stack.is(rdItemStack.getItem())) {
							if(stack.getMaxStackSize()-stack.getCount()>=rdItemStack.getCount()) {
								stack.grow(rdItemStack.getCount());
								item.setStackInSlot(i, stack);
								return;
							}else {
								stack.setCount(stack.getMaxStackSize());
								item.setStackInSlot(i, stack);
								rdItemStack.shrink(stack.getMaxStackSize()-stack.getCount());
							}
						}
					}
					Containers.dropContents(this.level, this.worldPosition.above(), NonNullList.of(ItemStack.EMPTY, rdItemStack));
				}else if(d>0) {
					level.setBlockAndUpdate(this.worldPosition.below(), Blocks.LAVA_CAULDRON.defaultBlockState());
				}else {
					return;
				}
			}
		}
		else {
			this.energy_consume = 0;
		}
	}
	
	protected ItemStack getRandomOutput() {
		int i = rd.nextInt(1,100);
		int c = rd.nextInt(1,8);
		if(i<=35) {
			return new ItemStack(Items.IRON_INGOT,c);
		}else if(i<=40) {
			return new ItemStack(Items.IRON_NUGGET,c*2);
		}else if(i<=55) {
			return new ItemStack(Items.GOLD_INGOT,c);
		}else if(i<=60) {
			return new ItemStack(Items.GOLD_NUGGET,c*2);
		}else if(i<=65) {
			return new ItemStack(Items.OBSIDIAN,c);
		}else if(i<=69) {
			return new ItemStack(Items.CRYING_OBSIDIAN,c);
		}else if(i<=70) {
			return new ItemStack(Items.NETHERITE_SCRAP,Math.min(4, c));
		}else if(i<=71) {
			return new ItemStack(Items.NETHERITE_INGOT,Math.min(1, c));
		}else if(i<=90){
			return new ItemStack(ItemRegister.PIECE_OBSIDIAN.get(),c);
		}else {
			return ItemStack.EMPTY;
		}
	}

	@Override
	public void clienttick() {
		if(this.level.getBlockState(this.worldPosition.below()).is(SUCKER.get())){
			this.cantFindSucker = false;
			int d = CoreSucker.getCoreDis(this.level,this.worldPosition.below());
			if(d==-1) {
				this.cantTouchCore = true;
			}else {
				this.cantTouchCore = false;
			}
		}
		else {
			this.cantFindSucker = true;
		}
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

}