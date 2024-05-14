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

public class PowerStationBurnEntity extends BlockEntity {
	private float temperature=20.0f;
	private int heat=0;
	public int energy_output=0;
	private int FULL_ENERGY_OUTPUT = 20;
	
	public PowerStationBurnEntity(BlockPos pos, BlockState pBlockState) {
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
	public <T>LazyOptional<T> getCapability(Capability<T> cap,Direction side){
		if(cap == ForgeCapabilities.ITEM_HANDLER) {
			return itemHandler.cast();
		}else {
			return super.getCapability(cap, side);
		}
	}
	
	public ItemStackHandler getItems() {
		return item;
	}
	
	private final String TAG_NAME = "Item";
	private final String TAG_HEAT = "HEAT";
	private final String TAG_TEMPERATURE = "TAG_TEMPERATURE";
	
	private void savedata(CompoundTag tag) {
		tag.put(TAG_NAME, item.serializeNBT());
		tag.putInt(TAG_HEAT, heat);
		tag.putFloat(TAG_TEMPERATURE, temperature);
	}
	
	private void loaddata(CompoundTag tag) {
		if(tag.contains(TAG_NAME)) {
			item.deserializeNBT(tag.getCompound(TAG_NAME));
		}
		if(tag.contains(TAG_HEAT)) {
			heat = tag.getInt(TAG_HEAT);
		}
		if(tag.contains(TAG_TEMPERATURE)) {
			temperature = tag.getFloat(TAG_TEMPERATURE);
		}
		
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
	
	public void servertick() {
		ItemStack stack = item.getStackInSlot(0);
		if(!stack.isEmpty()) {
			int burntime = ForgeHooks.getBurnTime(stack, RecipeType.SMELTING);
			if(burntime>0 && heat < 10000) {
				heat += (int)burntime*2.0;
				item.extractItem(0, 1, false);
			}
		}
		if(heat>5000) {heat-=1;}
		if(heat>1000) {heat-=1;}
		else if(heat>0) {heat -= 1;}
		
		if(heat>7000) {
			if(temperature<100&&temperature>80) {
				temperature+=0.01f;
			}else if(temperature<=80&&temperature>60) {temperature+=0.03f;}
			else {temperature+=0.05f;}
		}
		else if(heat>4000) {
			if(temperature<100f&&temperature>70f) {
				temperature+=0.01f;
			}else if(temperature<=70f) {temperature+=0.03f;}
		}else if(heat>500) {
			if(temperature>80f) {
				temperature-=0.01f;
			}else if(temperature>50f) {
				temperature+=0.01f;
			}else {temperature+=0.02f;}
		}else {
			if(temperature>50f) {temperature-=0.03f;}
			else if(temperature>20f) {temperature-=0.01f;}
		}
		if(temperature>100f) {temperature=100f;}
		
		float steam = temperature-50.0f;
		if(steam>0f && steam<40f) {
			energy_output = (int) (steam/40.0*(FULL_ENERGY_OUTPUT-10)+10);
		}else if(steam>=40f) {
			energy_output = FULL_ENERGY_OUTPUT;
		}else {
			energy_output = 0;
		}
		
		//理论上，比如一个木板燃烧300tik，*2=600heat,假如维持Heat==4000；可以持续300tik==15s;假如维持Heat==8000；可以持续200tik==10s;
		//15s*20=300 maring J，10s*20=200 maring J
		//一个木板 = 200到300 maring J。
		
		System.out.println("heat = " + heat);
		System.out.println("temperature = " + temperature);
		System.out.println("energy_output = " + energy_output);
		setChanged();
	    }
	
	public void clienttick() {
	}
}