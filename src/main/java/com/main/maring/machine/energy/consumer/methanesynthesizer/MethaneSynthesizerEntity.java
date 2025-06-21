package com.main.maring.machine.energy.consumer.methanesynthesizer;

import com.main.maring.fluid.hydrogen.HydrogenFluid;
import com.main.maring.fluid.mathane.MethaneFluid;
import com.main.maring.machine.energy.consumer.ConsumerEntity;
import com.main.maring.machine.energy.consumer.IConsumer;
import com.main.maring.machine.registry.MBlockEntityRegister;
import com.main.maring.util.net.EnergyNetProcess;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class MethaneSynthesizerEntity extends ConsumerEntity implements IConsumer {

	private static final int TANK_CAPACITY = 10000;
	private static final int ITEM_SLOT_COUNT = 1;

	private static final String TAG_ITEM = "Item";
	private static final String TAG_CARBON = "Carbon";
	private static final String TAG_TANK_METHANE = "Tank_Methane";
	private static final String TAG_TANK_HYDROGEN = "Tank_Hydrogen";

	public int carbon = 0;

	public MethaneSynthesizerEntity(BlockPos pos, BlockState state) {
		super(MBlockEntityRegister.METHANESYNTHESIZER_BE.get(), pos, state);
		this.FULL_ENERGY = 2;
	}

	public final FluidTank methaneTank = new FluidTank(TANK_CAPACITY/2) {
		@Override
		public boolean isFluidValid(FluidStack stack) {
			return stack.getFluid().isSame(MethaneFluid.SOURCE_METHANE_FLUID.get());
		}
	};

	public final FluidTank hydrogenTank = new FluidTank(TANK_CAPACITY) {
		@Override
		public boolean isFluidValid(FluidStack stack) {
			return stack.getFluid().isSame(HydrogenFluid.SOURCE_HYDROGEN_FLUID.get());
		}
	};

	protected final ItemStackHandler item = new ItemStackHandler(ITEM_SLOT_COUNT) {
		@Override
		protected void onContentsChanged(int slot) {
			setChanged();
		}
	};
	protected final LazyOptional<IItemHandler> itemHandler = LazyOptional.of(() -> item);

	public void addCarbon(int amount) {
		this.carbon = Math.min(this.carbon + amount, 1000);
		setChanged();
	}

	@Override
	protected void saveAdditional(CompoundTag tag) {
		super.saveAdditional(tag);
		tag.put(TAG_ITEM, item.serializeNBT());
		tag.putInt(TAG_CARBON, carbon);
		tag.put(TAG_TANK_METHANE, methaneTank.writeToNBT(new CompoundTag()));
		tag.put(TAG_TANK_HYDROGEN, hydrogenTank.writeToNBT(new CompoundTag()));
	}

	@Override
	public void load(CompoundTag tag) {
		super.load(tag);
		if (tag.contains(TAG_ITEM)) item.deserializeNBT(tag.getCompound(TAG_ITEM));
		if (tag.contains(TAG_CARBON)) carbon = tag.getInt(TAG_CARBON);
		methaneTank.readFromNBT(tag.getCompound(TAG_TANK_METHANE));
		hydrogenTank.readFromNBT(tag.getCompound(TAG_TANK_HYDROGEN));
	}

	@Override
	public CompoundTag getUpdateTag() {
		CompoundTag tag = super.getUpdateTag();
		tag.put(TAG_ITEM, item.serializeNBT());
		tag.putInt(TAG_CARBON, carbon);
		tag.put(TAG_TANK_METHANE, methaneTank.writeToNBT(new CompoundTag()));
		tag.put(TAG_TANK_HYDROGEN, hydrogenTank.writeToNBT(new CompoundTag()));
		return tag;
	}

	@Override
	public void handleUpdateTag(CompoundTag tag) {
		if (tag != null) {
			if (tag.contains(TAG_ITEM)) item.deserializeNBT(tag.getCompound(TAG_ITEM));
			if (tag.contains(TAG_CARBON)) carbon = tag.getInt(TAG_CARBON);
			methaneTank.readFromNBT(tag.getCompound(TAG_TANK_METHANE));
			hydrogenTank.readFromNBT(tag.getCompound(TAG_TANK_HYDROGEN));
		}
	}

	@Override
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@Override
	public void onDataPacket(Connection connection, ClientboundBlockEntityDataPacket packet) {
		CompoundTag tag = packet.getTag();
		if (tag != null) handleUpdateTag(tag);
	}

	@Override
	public void servertick() {
		if (this.level == null || this.level.isClientSide) return;

		int supplylevel = this.getEnergySupplyLevel();

		int[] carbonNeeds =    {8, 4, 3, 1};
		int[] hydrogenNeeds =  {40, 20, 15, 5};
		int[] methaneOutputs = {20, 10, 7, 2};

		for (int i = 0; i < hydrogenNeeds.length; i++) {
			boolean canUseTier =
					(i == 0 && supplylevel > 75) ||
							(i == 1 && supplylevel > 50) ||
							(i == 2 && supplylevel > 25) ||
							(i == 3 && supplylevel > 0);

			if (!canUseTier) continue;

			int carbonNeed = carbonNeeds[i];
			int hydrogenNeed = hydrogenNeeds[i];
			int methaneOut = methaneOutputs[i];

			if (carbon >= carbonNeed &&
					hydrogenTank.getFluidAmount() >= hydrogenNeed &&
					methaneTank.fill(new FluidStack(MethaneFluid.SOURCE_METHANE_FLUID.get(), methaneOut), IFluidHandler.FluidAction.SIMULATE) == methaneOut) {

				hydrogenTank.drain(hydrogenNeed, IFluidHandler.FluidAction.EXECUTE);
				carbon -= carbonNeed;
				methaneTank.fill(new FluidStack(MethaneFluid.SOURCE_METHANE_FLUID.get(), methaneOut), IFluidHandler.FluidAction.EXECUTE);
				this.setChanged();
				break; // 完成一次合成后退出循环
			}
		}
	}


	@Override public void clienttick() {}

	public void drop() {
		for (int slot = 0; slot < item.getSlots(); slot++) {
			ItemStack stack = item.getStackInSlot(slot);
			if (!stack.isEmpty()) {
				Containers.dropContents(this.level, this.worldPosition, NonNullList.of(ItemStack.EMPTY, stack));
			}
		}
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		if (cap == ForgeCapabilities.FLUID_HANDLER && side != null) {
			BlockState state = this.getBlockState();
			if (!state.hasProperty(BlockStateProperties.HORIZONTAL_FACING)) {
				return super.getCapability(cap, side); // 安全兜底
			}

			Direction facing = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
			Direction left = switch (facing) {
				case NORTH -> Direction.WEST;
				case SOUTH -> Direction.EAST;
				case WEST  -> Direction.SOUTH;
				case EAST  -> Direction.NORTH;
				default    -> Direction.NORTH;
			};
			Direction right = switch (facing) {
				case NORTH -> Direction.EAST;
				case SOUTH -> Direction.WEST;
				case WEST  -> Direction.NORTH;
				case EAST  -> Direction.SOUTH;
				default    -> Direction.SOUTH;
			};

			if (side == left || side == right) {
				return LazyOptional.of(() -> methaneTank).cast(); // 输出
			} else {
				return LazyOptional.of(() -> hydrogenTank).cast(); // 输入
			}
		}
		return super.getCapability(cap, side);
	}

	@Override public int getEnergyConsume() { return this.FULL_ENERGY; }

	@Override public int getEnergySupplyLevel() {
		return this.haveNet() ? EnergyNetProcess.getEnergyNet(this.getNet()).getSupplyLevel() : 0;
	}
}
