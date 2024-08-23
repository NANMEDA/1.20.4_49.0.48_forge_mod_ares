package block.norm.watergather;

import javax.annotation.Nullable;


import block.entity.consumer.watergather.WaterGatherEntity;
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
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import util.json.BlockJSON;

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
	public InteractionResult use(BlockState blockstate,Level level,BlockPos pos,Player player,InteractionHand interactionhand,BlockHitResult blockHitResult) {
		if(!level.isClientSide()) {
			var BlockEntity = level.getBlockEntity(pos);
			if (BlockEntity instanceof WaterGatherEntity entity) {
			    ItemStack mainHandItem = player.getMainHandItem();
			    if(entity.water>=18000) {
				    if (mainHandItem.getItem()==Items.BUCKET&&mainHandItem.getCount()==1) {
			    		entity.water -= 18000;
			    		player.setItemInHand(InteractionHand.MAIN_HAND,new ItemStack(Items.WATER_BUCKET,1));
				    }else if(mainHandItem.getItem()==Items.SPONGE&&mainHandItem.getCount()==1) {
				    	entity.water = 0;
				    	player.setItemInHand(InteractionHand.MAIN_HAND,new ItemStack(Items.WET_SPONGE,1));
				    }
			    }
			}else {
				throw new IllegalStateException("missing block:"+global_name);
			}
		}
		return InteractionResult.SUCCESS;
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
            cleanAllBlock(pState, pLevel, pPos);
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof WaterGatherEntity) {
                ((WaterGatherEntity) blockEntity).drop();
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }
	
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
	
	
	@Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
    	if(checkSpaceEnough(context)) {
    		return this.defaultBlockState();
    	}
    	return null;
    }
	
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
	
	@Override
	public void setPlacedBy(Level pLevel, BlockPos pos, BlockState pstate, @Nullable LivingEntity p_49850_, ItemStack p_49851_) {
		super.setPlacedBy(pLevel, pos, pstate, p_49850_, p_49851_);
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
	}
	
	
	 static {
	        BlockJSON.GenModelsJSONBasic(global_name);
	        BlockJSON.GenBlockStateJSONBasic(global_name);
	        BlockJSON.GenItemJSONBasic(global_name);
	        BlockJSON.GenLootTableJSONBasic(global_name);
	 }
}

