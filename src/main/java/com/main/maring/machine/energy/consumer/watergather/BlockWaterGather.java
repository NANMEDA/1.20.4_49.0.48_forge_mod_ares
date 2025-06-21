package com.main.maring.machine.energy.consumer.watergather;

import javax.annotation.Nullable;


import net.minecraft.core.BlockPos;
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
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.fluids.capability.IFluidHandler;

/**
 * 水分收集台
 * - 需要配套的水分阻隔栏
 * - 水分阻隔栏需要放在收集台上方3*3*3的范围内，并且要和收集台垂直方向上有连接
 * @author NANMEDA
 * */
public class BlockWaterGather extends Block implements EntityBlock{
	public static String global_name = "water_gather"; 
	
	public BlockWaterGather(Properties properties) {
		super(properties);
	}
	
	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState pBlockState) {
		return new WaterGatherEntity(pos, pBlockState);
	}
	
	@Override
	public boolean isPathfindable(BlockState p_60475_, BlockGetter p_60476_, BlockPos p_60477_, PathComputationType p_60478_) {
		return true;
	}
	
	@Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
		return Block.box(0,0,0,16,16,16);
    }

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		if (!level.isClientSide()) {
			var blockEntity = level.getBlockEntity(pos);
			if (blockEntity instanceof WaterGatherEntity entity) {
				ItemStack mainHand = player.getItemInHand(hand);

				// 玩家手上是空桶 && tank 水量 >= 1000mB
				if (mainHand.getItem() == Items.BUCKET && mainHand.getCount() == 1) {
					if (entity.tank.getFluidAmount() >= 1000) {
						// 从 tank 中真正 drain 出水
						entity.tank.drain(1000, IFluidHandler.FluidAction.EXECUTE);

						// 替换为水桶
						player.setItemInHand(hand, new ItemStack(Items.WATER_BUCKET));
						return InteractionResult.CONSUME;
					}
				}

				// 玩家手上是干海绵 && tank 中有水
				else if (mainHand.getItem() == Items.SPONGE && mainHand.getCount() == 1) {
					if (!entity.tank.isEmpty()) {
						entity.tank.drain(entity.tank.getFluidAmount(), IFluidHandler.FluidAction.EXECUTE);
						player.setItemInHand(hand, new ItemStack(Items.WET_SPONGE));
						return InteractionResult.CONSUME;
					}
				}
			} else {
				throw new IllegalStateException("Missing block entity at: " + pos);
			}
		}
		return InteractionResult.PASS;
	}
	
	
	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pBlockState, BlockEntityType<T> pBlockEntityType) {
	     if(pLevel.isClientSide()) {
	    	 return (level,pos,state,blockentity) -> {
	    		 if(blockentity instanceof WaterGatherEntity entity) {
	    			 entity.clienttick();
	    		 }
	    	 };
	     }else {
    		 return (level,pos,state,blockentity) -> {
	    		 if(blockentity instanceof WaterGatherEntity entity) {
	    			 entity.servertick();
	    		 }
	    	 };
	     }
	 }
	
	@SuppressWarnings("deprecation")
	@Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            //cleanAllBlock(pState, pLevel, pPos);
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof WaterGatherEntity) {
                ((WaterGatherEntity) blockEntity).drop();
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }
	/**
	public void cleanAllBlock(BlockState pState, Level pLevel, BlockPos pPos) {
		BlockPos north = pPos.north();
		BlockPos south = pPos.south();
		BlockPos east = pPos.east();
		BlockPos west = pPos.west();
		
		pLevel.setBlock(pPos.above(), Blocks.AIR.defaultBlockState(), 2);
		
		pLevel.setBlock(north, Blocks.AIR.defaultBlockState(), 2);
		pLevel.setBlock(south, Blocks.AIR.defaultBlockState(), 2);
		pLevel.setBlock(east, Blocks.AIR.defaultBlockState(), 2);
		pLevel.setBlock(west, Blocks.AIR.defaultBlockState(), 2);
		
		pLevel.setBlock(north.above(), Blocks.AIR.defaultBlockState(), 2);
		pLevel.setBlock(south.above(), Blocks.AIR.defaultBlockState(), 2);
		pLevel.setBlock(east.above(), Blocks.AIR.defaultBlockState(), 2);
		pLevel.setBlock(west.above(), Blocks.AIR.defaultBlockState(), 2);
		
		pLevel.setBlock(north.east(), Blocks.AIR.defaultBlockState(), 2);
		pLevel.setBlock(south.west(), Blocks.AIR.defaultBlockState(), 2);
		pLevel.setBlock(east.south(), Blocks.AIR.defaultBlockState(), 2);
		pLevel.setBlock(west.north(), Blocks.AIR.defaultBlockState(), 2);
		
		pLevel.setBlock(north.east().above(), Blocks.AIR.defaultBlockState(), 2);
		pLevel.setBlock(south.west().above(), Blocks.AIR.defaultBlockState(), 2);
		pLevel.setBlock(east.south().above(), Blocks.AIR.defaultBlockState(), 2);
		pLevel.setBlock(west.north().above(), Blocks.AIR.defaultBlockState(), 2);
		
	}
	*/
	
	@Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
		/**
    	if(checkSpaceEnough(context)) {
    		return this.defaultBlockState();
    	}
    	return null;*/
		return this.defaultBlockState();
    }

	/*
	private Boolean checkSpaceEnough(BlockPlaceContext context) {
		BlockPos center = context.getClickedPos();
		BlockPos north = center.north();
		BlockPos south = center.south();
		BlockPos east = center.east();
		BlockPos west = center.west();
		BlockPos[] all = {
				north,
				south,
				east,
				west,
				north.east(),
				south.west(), 
				east.south(),
				west.north(),
				center.above(),
				north.above(),
				south.above(),
				east.above(),
				west.above(),
				north.east().above(),
				south.west().above(), 
				east.south().above(),
				west.north().above()
		};
		Level plevel = context.getLevel();
		for(BlockPos pos : all ) {
			if(!plevel.isEmptyBlock(pos)) {
				return false;
			}
		}
		return true;
	}
	*/
	@Override
	public void setPlacedBy(Level pLevel, BlockPos pos, BlockState pstate, @Nullable LivingEntity p_49850_, ItemStack p_49851_) {
		super.setPlacedBy(pLevel, pos, pstate, p_49850_, p_49851_);
		/**
		if(!pLevel.isClientSide()) {
			BlockPos north = pos.north();
			BlockPos south = pos.south();
			BlockPos east = pos.east();
			BlockPos west = pos.west();
			
			pLevel.setBlock(pos.above(), Register.watergatherup_BLOCK.get().defaultBlockState(), 2);
			pLevel.setBlock(north.above(), Register.watergatherup_BLOCK.get().defaultBlockState(), 2);
			pLevel.setBlock(south.above(), Register.watergatherup_BLOCK.get().defaultBlockState(), 2);
			pLevel.setBlock(east.above(), Register.watergatherup_BLOCK.get().defaultBlockState(), 2);
			pLevel.setBlock(west.above(), Register.watergatherup_BLOCK.get().defaultBlockState(), 2);
			pLevel.setBlock(north.east().above(), Register.watergatherup_BLOCK.get().defaultBlockState(), 2);
			pLevel.setBlock(south.west().above(), Register.watergatherup_BLOCK.get().defaultBlockState(), 2);
			pLevel.setBlock(east.south().above(), Register.watergatherup_BLOCK.get().defaultBlockState(), 2);
			pLevel.setBlock(west.north().above(), Register.watergatherup_BLOCK.get().defaultBlockState(), 2);
			
			pLevel.setBlock(north, Register.watergatheraround_BLOCK.get().defaultBlockState().setValue(BlockStateProperties.FACING, Direction.NORTH), 2);
			pLevel.setBlock(south, Register.watergatheraround_BLOCK.get().defaultBlockState().setValue(BlockStateProperties.FACING, Direction.SOUTH), 2);
			pLevel.setBlock(east, Register.watergatheraround_BLOCK.get().defaultBlockState().setValue(BlockStateProperties.FACING, Direction.EAST), 2);
			pLevel.setBlock(west, Register.watergatheraround_BLOCK.get().defaultBlockState().setValue(BlockStateProperties.FACING, Direction.WEST), 2);
			
			pLevel.setBlock(north.east(), Register.watergathercorner_BLOCK.get().defaultBlockState().setValue(BlockStateProperties.FACING, Direction.NORTH), 2);
			pLevel.setBlock(south.west(), Register.watergathercorner_BLOCK.get().defaultBlockState().setValue(BlockStateProperties.FACING, Direction.SOUTH), 2);
			pLevel.setBlock(east.south(), Register.watergathercorner_BLOCK.get().defaultBlockState().setValue(BlockStateProperties.FACING, Direction.EAST), 2);
			pLevel.setBlock(west.north(), Register.watergathercorner_BLOCK.get().defaultBlockState().setValue(BlockStateProperties.FACING, Direction.WEST), 2);
		}
		*/
	}

}

