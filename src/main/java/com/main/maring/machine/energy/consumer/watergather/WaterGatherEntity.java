package com.main.maring.machine.energy.consumer.watergather;

import com.main.maring.block.entity.BlockEntityRegister;
import com.main.maring.block.entity.consumer.PowerConsumerEntity;
import com.main.maring.machine.energy.consumer.ConsumerEntity;
import com.main.maring.machine.energy.consumer.IConsumer;
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

/**
 * 自动收集水的方块实体类，每产生36000单位water计为1桶(1000mB)，最多10桶。
 * 每tick根据能量供给累积water值。
 * @author NANMEDA
 */
public class WaterGatherEntity extends ConsumerEntity implements IFluidHandler, IConsumer {

	private int energy_consume = 0;

	private static final int MAX_WATER = 360000;         // 最多water值（10桶）
	private static final int TANK_CAPACITY = 10000;      // tank容量：10桶 = 10000mB
	private static final int WATER_PER_BUCKET = 36000;   // 36000water == 1000mB
	private static final int ITEM_SLOT_COUNT = 1;

	private static final String TAG_ITEM = "Item";
	private static final String TAG_WATER = "Water";
	private static final String TAG_TANK = "Tank";

	public int water = 0; // 当前water原始值
	public final FluidTank tank = new FluidTank(TANK_CAPACITY){
		@Override
		public boolean isFluidValid(FluidStack stack) {
			return stack.getFluid().isSame(Fluids.WATER); // 只接受水
		}
	};

	// 一个插槽用于扩展用途（可用于催化剂等）
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

	public WaterGatherEntity(BlockPos pos, BlockState state) {
		super(BlockEntityRegister.watergather_BLOCKENTITY.get(), pos, state);
		this.FULL_ENERGY = 2;
		this.energy_consume = this.FULL_ENERGY;
	}

	public ItemStackHandler getItems() {
		return item;
	}

	@Override
	protected void saveAdditional(CompoundTag tag) {
		super.saveAdditional(tag);
		tag.put(TAG_ITEM, item.serializeNBT());
		tag.putInt(TAG_WATER, water);
		tag.put(TAG_TANK, tank.writeToNBT(new CompoundTag())); // 保存流体信息
	}

	@Override
	public void load(CompoundTag tag) {
		super.load(tag);
		if (tag.contains(TAG_ITEM)) item.deserializeNBT(tag.getCompound(TAG_ITEM));
		if (tag.contains(TAG_WATER)) water = tag.getInt(TAG_WATER);
	}

	@Override
	public CompoundTag getUpdateTag() {
		CompoundTag tag = super.getUpdateTag();
		tag.put(TAG_ITEM, item.serializeNBT());
		tag.putInt(TAG_WATER, water);
		if (tag.contains(TAG_TANK)) {
			tank.readFromNBT(tag.getCompound(TAG_TANK)); // 加载流体信息
		}
		return tag;
	}

	@Override
	public void handleUpdateTag(CompoundTag tag) {
		if (tag != null) {
			if (tag.contains(TAG_ITEM)) item.deserializeNBT(tag.getCompound(TAG_ITEM));
			if (tag.contains(TAG_WATER)) water = tag.getInt(TAG_WATER);
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

	/**
	 * 每tick消耗能量收集水，根据能量供给决定增量。
	 * 当达到每36000单位water时，自动转为10mB水注入tank。
	 */
	@Override
	public void servertick() {
		if (water < MAX_WATER) {

			// 动态决定增长量
			int energy_supply = this.getEnergySupplyLevel();
			int gain = (energy_supply > 75) ? 5 : (energy_supply > 50) ? 3 : (energy_supply > 25) ? 2 : 1;
			water += gain;

			// 转换water为流体mB
			while (water >= WATER_PER_BUCKET / 100) { // 每360单位转为10mB
				if (tank.getFluidAmount() + 10 <= tank.getCapacity()) {
					FluidStack waterStack = new FluidStack(Fluids.WATER, 10); // 明确指定填充水
					tank.fill(waterStack, FluidAction.EXECUTE);
					water -= WATER_PER_BUCKET / 100;
				} else {
					water = 0;
				}
			}

			setChanged();
		}
	}

	@Override
	public void clienttick() {
		// 客户端不执行逻辑
	}

	/**
	 * 掉落除了最后一格以外的所有物品槽内容。
	 */
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
		if (cap == ForgeCapabilities.FLUID_HANDLER) {
			if (side != Direction.UP) {
				return LazyOptional.of(() -> tank).cast();
			} else {
				return LazyOptional.empty(); // 拒绝其他方向
			}
		}
		return super.getCapability(cap, side); // 留给 ITEM_HANDLER 或其他能力处理
	}

	// === IFluidHandler 接口实现 ===
	@Override
	public int getTanks() {
		return tank.getTanks();
	}

	@Override
	public @NotNull FluidStack getFluidInTank(int tank) {
		return this.tank.getFluidInTank(tank);
	}

	@Override
	public int getTankCapacity(int tank) {
		return this.tank.getTankCapacity(tank);
	}

	@Override
	public boolean isFluidValid(int tank, @NotNull FluidStack stack) {
		return this.tank.isFluidValid(stack);
	}

	@Override
	public int fill(FluidStack resource, FluidAction action) {
		return this.tank.fill(resource, action);
	}

	@Override
	public @NotNull FluidStack drain(FluidStack resource, FluidAction action) {
		return this.tank.drain(resource, action);
	}

	@Override
	public @NotNull FluidStack drain(int maxDrain, FluidAction action) {
		return this.tank.drain(maxDrain, action);
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
