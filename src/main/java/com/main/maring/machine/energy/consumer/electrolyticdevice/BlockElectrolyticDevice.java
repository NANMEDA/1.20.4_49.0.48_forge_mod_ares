package com.main.maring.machine.energy.consumer.electrolyticdevice;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

import javax.annotation.Nullable;

/**
 * 水分收集台
 * - 需要配套的水分阻隔栏
 * - 水分阻隔栏需要放在收集台上方3*3*3的范围内，并且要和收集台垂直方向上有连接
 * @author NANMEDA
 * */
public class BlockElectrolyticDevice extends Block implements EntityBlock {
	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
	public static String global_name = "electrolytic_device";

	public BlockElectrolyticDevice(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new ElectrolyticDeviceEntity(pos, state);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		// 面朝放置者的反方向
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
	}

	@Override
	public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
		super.setPlacedBy(level, pos, state, placer, stack);
	}

	@Override
	public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos, PathComputationType type) {
		return true;
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return Block.box(0, 0, 0, 16, 16, 16);
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		if (!level.isClientSide()) {
			BlockEntity be = level.getBlockEntity(pos);
			if (!(be instanceof ElectrolyticDeviceEntity entity)) {
				throw new IllegalStateException("Missing block entity at: " + pos);
			}

			ItemStack held = player.getItemInHand(hand);


			// === 玩家手持水桶，倒入水 ===
			if (held.getItem() == Items.WATER_BUCKET && held.getCount() == 1) {
				FluidStack waterStack = new FluidStack(Fluids.WATER, 1000);
				if (entity.inputTank.fill(waterStack, IFluidHandler.FluidAction.SIMULATE) == 1000) {
					entity.inputTank.fill(waterStack, IFluidHandler.FluidAction.EXECUTE);
					player.setItemInHand(hand, new ItemStack(Items.BUCKET));
					return InteractionResult.CONSUME;
				}
			}

			// === 玩家用干海绵吸水 ===
			else if (held.getItem() == Items.SPONGE && held.getCount() == 1) {
				if (!entity.inputTank.isEmpty()) {
					entity.inputTank.drain(entity.inputTank.getFluidAmount(), IFluidHandler.FluidAction.EXECUTE);
					player.setItemInHand(hand, new ItemStack(Items.WET_SPONGE));
					return InteractionResult.CONSUME;
				}
			}
		}
		return InteractionResult.SUCCESS;
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
		if (level.isClientSide()) {
			return (lvl, pos, st, be) -> {
				if (be instanceof ElectrolyticDeviceEntity entity) {
					entity.clienttick();
				}
			};
		} else {
			return (lvl, pos, st, be) -> {
				if (be instanceof ElectrolyticDeviceEntity entity) {
					entity.servertick();
				}
			};
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onRemove(BlockState oldState, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
		if (oldState.getBlock() != newState.getBlock()) {
			BlockEntity be = level.getBlockEntity(pos);
			if (be instanceof ElectrolyticDeviceEntity entity) {
				entity.drop();
			}
		}
		super.onRemove(oldState, level, pos, newState, isMoving);
	}
}


