package block.entity.station;

import block.entity.BlockEntityRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;

public class PowerStationSunEntity extends PowerStationEntity {
	//public int energy_output=0;
	private int FULL_ENERGY_OUTPUT = 6;
	
	public PowerStationSunEntity(BlockPos pos, BlockState pBlockState) {
		super(BlockEntityRegister.PowerStationBurn_BLOCKENTITY.get(), pos, pBlockState);
	}
	
	private final ItemStackHandler item = new ItemStackHandler(1) {
		@Override
		public void onLoad() {
			super.onLoad();
			System.out.println("entity is onload");
		}
		@Override
		protected void onContentsChanged(int slot) {
			setChanged();
			//level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
		}
	};
	
	
	private final LazyOptional<IItemHandler> itemHandler = LazyOptional.of(() -> item);
	
	@Override
	public <T>LazyOptional<T> getCapability(Capability<T> cap,Direction side){
		if(cap == ForgeCapabilities.ITEM_HANDLER) {
			return itemHandler.cast();
		}else {
			return super.getCapability(cap, side);
		}
	}

	private final String TAG_NAME = "Item";
	
	protected void savedata(CompoundTag tag) {
		tag.put(TAG_NAME, item.serializeNBT());
	}
	
	protected void loaddata(CompoundTag tag) {
		if(tag.contains(TAG_NAME)) {
			item.deserializeNBT(tag.getCompound(TAG_NAME));
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
	public void servertick() {
		if(level.dimensionType().hasSkyLight() && !level.isRaining()) {
			int brightness = level.getBrightness(null, worldPosition);
			if(brightness>11) {
				this.energy_output = FULL_ENERGY_OUTPUT;
			}else if(brightness>7) {
				this.energy_output = (int) (FULL_ENERGY_OUTPUT *0.5);
			}else {
				this.energy_output = 0;
			}
			return;
		}
		this.energy_output = 0;
		return;
		//一个木板 = 200到300 maring J。
	    }
	@Override
	public void clienttick() {
	}
}