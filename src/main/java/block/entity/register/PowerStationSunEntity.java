package block.entity.register;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraft.core.Direction;

public class PowerStationSunEntity extends PowerStationEntity {
	//public int energy_output=0;
	private int FULL_ENERGY_OUTPUT = 6;
	
	public PowerStationSunEntity(BlockPos pos, BlockState pBlockState) {
		super(BlockEntityRegister.PowerStationBurn_BLOCKENTITY.get(), pos, pBlockState);
	}
	/*
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
	
	*/
	//private final LazyOptional<IItemHandler> itemHandler = LazyOptional.of(() -> item);
	
	@Override
	public <T>LazyOptional<T> getCapability(Capability<T> cap,Direction side){
		if(cap == ForgeCapabilities.ITEM_HANDLER) {
			return itemHandler.cast();
		}else {
			return super.getCapability(cap, side);
		}
	}
	/*
	public ItemStackHandler getItems() {
		return item;
	}
	*/
	private final String TAG_NAME = "Item";
	
	@Override
	protected void savedata(CompoundTag tag) {
		tag.put(TAG_NAME, item.serializeNBT());
	}
	@Override
	protected void loaddata(CompoundTag tag) {
		if(tag.contains(TAG_NAME)) {
			item.deserializeNBT(tag.getCompound(TAG_NAME));
		}
	}
	/*
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
		if(tag != null) {
			loaddata(tag);
		}
	}
	
	@Override
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}
	
	@Override
	public void onDataPacket(Connection connection,ClientboundBlockEntityDataPacket packet) {
		CompoundTag tag = packet.getTag();
		if(tag!= null){
			//handleUpdateTag(tag);
			loaddata(tag);
		}
	}
	*/
	
	@Override
	public void servertick() {
		ItemStack stack = item.getStackInSlot(0);
		if(level.getSkyDarken()>9) {
			energy_output = FULL_ENERGY_OUTPUT;
		}else if(level.getSkyDarken()>3) {
			//energy_output = (int) (FULL_ENERGY_OUTPUT*(level.getSkyDarken()-3)/6f);
			energy_output = level.getSkyDarken()-3;
		}else {
			energy_output = 0;
		}
		//一个木板 = 200到300 maring J。
		setChanged();
	    }
	@Override
	public void clienttick() {
	}
}