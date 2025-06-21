package com.main.maring.machine.energy.consumer.electrolyticdevice;

import com.main.maring.block.entity.BlockEntityRegister;
import com.main.maring.fluid.hydrogen.HydrogenFluid;
import com.main.maring.fluid.oxygen.OxygenFluid;
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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

public class ElectrolyticDeviceEntity extends ConsumerEntity implements IConsumer {

	private static final int TANK_CAPACITY = 10000;
	private static final int ITEM_SLOT_COUNT = 1;

	private static final String TAG_ITEM = "Item";
	private static final String TAG_WATER = "Water";
	private static final String TAG_TANK = "Tank";

	public int water = 0;

	public ElectrolyticDeviceEntity(BlockPos pos, BlockState state) {
		super(MBlockEntityRegister.ELECTROLYTICDEVICE_BE.get(), pos, state);
		this.FULL_ENERGY = 16;
	}

	public final FluidTank inputTank = new FluidTank(TANK_CAPACITY) {
		@Override
		public boolean isFluidValid(FluidStack stack) {
			return stack.getFluid().isSame(Fluids.WATER);
		}
	};

	public final FluidTank oxygenTank = new FluidTank(TANK_CAPACITY/2) {
		@Override
		public boolean isFluidValid(FluidStack stack) {
			return stack.getFluid().isSame(OxygenFluid.SOURCE_OXYGEN_FLUID.get());
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
			if (item.getStackInSlot(slot).getCount() < 6) {
				level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
			}
		}
	};
	protected final LazyOptional<IItemHandler> itemHandler = LazyOptional.of(() -> item);


	public static Direction getLeft(Direction facing) {
		return switch (facing) {
			case NORTH -> Direction.WEST;
			case SOUTH -> Direction.EAST;
			case WEST -> Direction.SOUTH;
			case EAST -> Direction.NORTH;
			default -> Direction.NORTH;
		};
	}

	public static Direction getRight(Direction facing) {
		return switch (facing) {
			case NORTH -> Direction.EAST;
			case SOUTH -> Direction.WEST;
			case WEST -> Direction.NORTH;
			case EAST -> Direction.SOUTH;
			default -> Direction.SOUTH;
		};
	}

	@Override
	protected void saveAdditional(CompoundTag tag) {
		super.saveAdditional(tag);
		tag.put(TAG_ITEM, item.serializeNBT());
		tag.putInt(TAG_WATER, water);
		tag.put(TAG_TANK + "_IN", inputTank.writeToNBT(new CompoundTag()));
		tag.put(TAG_TANK + "_OXY", oxygenTank.writeToNBT(new CompoundTag()));
		tag.put(TAG_TANK + "_HYD", hydrogenTank.writeToNBT(new CompoundTag()));
	}

	@Override
	public void load(CompoundTag tag) {
		super.load(tag);
		if (tag.contains(TAG_ITEM)) item.deserializeNBT(tag.getCompound(TAG_ITEM));
		if (tag.contains(TAG_WATER)) water = tag.getInt(TAG_WATER);
		inputTank.readFromNBT(tag.getCompound(TAG_TANK + "_IN"));
		oxygenTank.readFromNBT(tag.getCompound(TAG_TANK + "_OXY"));
		hydrogenTank.readFromNBT(tag.getCompound(TAG_TANK + "_HYD"));
	}

	@Override
	public CompoundTag getUpdateTag() {
		CompoundTag tag = super.getUpdateTag();
		tag.put(TAG_ITEM, item.serializeNBT());
		tag.putInt(TAG_WATER, water);
		tag.put(TAG_TANK + "_IN", inputTank.writeToNBT(new CompoundTag()));
		tag.put(TAG_TANK + "_OXY", oxygenTank.writeToNBT(new CompoundTag()));
		tag.put(TAG_TANK + "_HYD", hydrogenTank.writeToNBT(new CompoundTag()));
		return tag;
	}

	@Override
	public void handleUpdateTag(CompoundTag tag) {
		if (tag != null) {
			if (tag.contains(TAG_ITEM)) item.deserializeNBT(tag.getCompound(TAG_ITEM));
			if (tag.contains(TAG_WATER)) water = tag.getInt(TAG_WATER);
			inputTank.readFromNBT(tag.getCompound(TAG_TANK + "_IN"));
			oxygenTank.readFromNBT(tag.getCompound(TAG_TANK + "_OXY"));
			hydrogenTank.readFromNBT(tag.getCompound(TAG_TANK + "_HYD"));
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
			handleUpdateTag(tag);
		}
	}


	@Override
	public void servertick() {
		if (this.level == null || this.level.isClientSide) return;

		int energy_supply = this.getEnergySupplyLevel();

		int maxElectrolyze = 0;
		if (energy_supply > 85) {
			maxElectrolyze = 50;
		} else if (energy_supply > 50) {
			maxElectrolyze = 30;
		} else if (energy_supply > 25) {
			maxElectrolyze = 10;
		} else {
			return; // 没电，直接跳过
		}

		int drainWater = Math.min(inputTank.getFluidAmount(), maxElectrolyze);

		if (drainWater > 0) {
			int hydrogenAmount = drainWater;
			int oxygenAmount = drainWater / 2;

			if (hydrogenTank.fill(new FluidStack(HydrogenFluid.SOURCE_HYDROGEN_FLUID.get(), hydrogenAmount), IFluidHandler.FluidAction.SIMULATE) == hydrogenAmount &&
					oxygenTank.fill(new FluidStack(OxygenFluid.SOURCE_OXYGEN_FLUID.get(), oxygenAmount), IFluidHandler.FluidAction.SIMULATE) == oxygenAmount) {

				inputTank.drain(drainWater, IFluidHandler.FluidAction.EXECUTE);
				hydrogenTank.fill(new FluidStack(HydrogenFluid.SOURCE_HYDROGEN_FLUID.get(), hydrogenAmount), IFluidHandler.FluidAction.EXECUTE);
				oxygenTank.fill(new FluidStack(OxygenFluid.SOURCE_OXYGEN_FLUID.get(), oxygenAmount), IFluidHandler.FluidAction.EXECUTE);

				this.setChanged();
			}
		}
	}


	@Override
	public void clienttick() {}

	public void drop() {
		for (int slot = 0; slot < item.getSlots() - 1; slot++) {
			ItemStack stack = item.getStackInSlot(slot);
			if (!stack.isEmpty()) {
				Containers.dropContents(this.level, this.worldPosition, NonNullList.of(ItemStack.EMPTY, stack));
			}
		}
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		if (cap == ForgeCapabilities.FLUID_HANDLER && side != null) {
			Direction facing = this.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING);
			Direction left = getLeft(facing);
			Direction right = getRight(facing);

			if (side == left) return LazyOptional.of(() -> hydrogenTank).cast();
			if (side == right) return LazyOptional.of(() -> oxygenTank).cast();
			return LazyOptional.of(() -> inputTank).cast();
		}
		return super.getCapability(cap, side);
	}

	@Override public int getEnergyConsume() { return this.FULL_ENERGY; }

	@Override public int getEnergySupplyLevel() {
		return this.haveNet() ? EnergyNetProcess.getEnergyNet(this.getNet()).getSupplyLevel() : 0;
	}
}
