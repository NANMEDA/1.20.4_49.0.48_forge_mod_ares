package block.norm.fuelrefiner;

import javax.annotation.Nullable;

import block.entity.neutral.fuelrefiner.FuelRefinerEntity;
import menu.fuelrefiner.FuelRefinerMenuProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.extensions.IForgeServerPlayer;
import util.json.BlockJSON;

/**
 * 燃料提取机
 * - 获得燃料
 * @author NANMEDA
 * */
public class BlockFuelRefiner extends Block implements EntityBlock{
	public static String global_name = "fuel_refiner"; 
	
	public BlockFuelRefiner(Properties properties) {
		super(properties);
	}
	
	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState pBlockState) {
		return new FuelRefinerEntity(pos, pBlockState);
	}
	
	@Override
	public boolean isPathfindable(BlockState p_60475_, BlockGetter p_60476_, BlockPos p_60477_, PathComputationType p_60478_) {
		return true;
	}
	
	@Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
		return Shapes.join(
				Block.box(0, 0, 0, 16, 16, 16), 
				Block.box(7, 0, 7, 9, 2, 9), 
			    BooleanOp.ONLY_FIRST
			);
    }
	
	@SuppressWarnings("deprecation")
	@Override
	public InteractionResult use(BlockState blockstate,Level level,BlockPos pos,Player player,InteractionHand interactionhand,BlockHitResult blockHitResult) {
		super.use(blockstate,level,pos,player,interactionhand,blockHitResult);
		if(!level.isClientSide()) {
			var BlockEntity = level.getBlockEntity(pos);
			if(BlockEntity instanceof FuelRefinerEntity entity) {
				player = (ServerPlayer) player;
				IForgeServerPlayer ifpe = (IForgeServerPlayer)player;
				ifpe.openMenu(new FuelRefinerMenuProvider(pos), pos);
			}else {
				throw new IllegalStateException("missing block-fuel refiner");
			}
		}
		return InteractionResult.SUCCESS;
	}
	
	
	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pBlockState, BlockEntityType<T> pBlockEntityType) {
	     if(pLevel.isClientSide()) {
	    	 return (level,pos,state,blockentity) -> {
	    		 if(blockentity instanceof FuelRefinerEntity entity) {
	    			 entity.clienttick();
	    		 }
	    	 };
	     }else {
    		 return (level,pos,state,blockentity) -> {
	    		 if(blockentity instanceof FuelRefinerEntity entity) {
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
            if (blockEntity instanceof FuelRefinerEntity) {
                ((FuelRefinerEntity) blockEntity).drop();
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }
	
	public void cleanAllBlock(BlockState pState, Level pLevel, BlockPos pPos) {
		BlockPos left = null;
		BlockPos leftup = null;
		BlockPos behind = null;
		BlockPos leftbehind = null;
		Direction direction = pState.getValue(BlockStateProperties.FACING).getOpposite();
		switch (direction) {
		    case NORTH:
			default:
		        left = pPos.north();
		        leftup = left.above();
		        leftbehind = left.east();
		        behind = leftbehind.south();
		        break;
		    case EAST:
		        left = pPos.east();
		        leftup = left.above();
		        leftbehind = left.south();
		        behind = leftbehind.west();
		        break;
		    case SOUTH:
		        left = pPos.south();
		        leftup = left.above();
		        leftbehind = left.west();
		        behind = leftbehind.north();
		        break;
		    case WEST:
		        left = pPos.west();
		        leftup = left.above();
		        leftbehind = left.north();
		        behind = leftbehind.east();
		        break;
		}
		pLevel.setBlock(left, Blocks.AIR.defaultBlockState(), 2);
		pLevel.setBlock(leftup, Blocks.AIR.defaultBlockState(), 2);
		pLevel.setBlock(behind, Blocks.AIR.defaultBlockState(), 2);
		pLevel.setBlock(leftbehind, Blocks.AIR.defaultBlockState(), 2);
	}
	
	
	@Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        if (context.getClickedFace() == Direction.DOWN||context.getClickedFace() == Direction.UP) {
        	Direction playerDirection = context.getNearestLookingDirection().getOpposite();
        	if(playerDirection==Direction.DOWN||playerDirection==Direction.UP) {
        		playerDirection = Direction.NORTH; 
        	} 
        	if(checkSpaceEnough(playerDirection,context)) {
        		return this.defaultBlockState().setValue(BlockStateProperties.FACING, playerDirection);
        	}
        	return null;
        } else {
            return null;
        }
    }
	
	private Boolean checkSpaceEnough(Direction direction,BlockPlaceContext context) {
		BlockPos left = null;
		BlockPos leftup = null;
		BlockPos behind = null;
		BlockPos leftbehind = null;
		direction = direction.getOpposite();
		switch (direction) {
		    case NORTH:
			default:
		        left = context.getClickedPos().north();
		        leftup = left.above();
		        leftbehind = left.east();
		        behind = leftbehind.south();
		        break;
		    case EAST:
		        left = context.getClickedPos().east();
		        leftup = left.above();
		        leftbehind = left.south();
		        behind = leftbehind.west();
		        break;
		    case SOUTH:
		        left = context.getClickedPos().south();
		        leftup = left.above();
		        leftbehind = left.west();
		        behind = leftbehind.north();
		        break;
		    case WEST:
		        left = context.getClickedPos().west();
		        leftup = left.above();
		        leftbehind = left.north();
		        behind = leftbehind.east();
		        break;
		}
		Level plevel = context.getLevel();
		if(plevel.isEmptyBlock(left)
				&&plevel.isEmptyBlock(leftup)
				&&plevel.isEmptyBlock(behind)
				&&plevel.isEmptyBlock(leftbehind)) {
			return true;
		}
		return false;
	}
	
	@Override
	public void setPlacedBy(Level pLevel, BlockPos pos, BlockState pstate, @Nullable LivingEntity p_49850_, ItemStack p_49851_) {
		super.setPlacedBy(pLevel, pos, pstate, p_49850_, p_49851_);
		if(!pLevel.isClientSide()) {
		BlockPos left = null;
		BlockPos leftup = null;
		BlockPos behind = null;
		BlockPos leftbehind = null;
		Direction direction = pstate.getValue(BlockStateProperties.FACING).getOpposite();
		switch (direction) {
		    case NORTH:
			default:
		        left = pos.north();
		        leftup = left.above();
		        leftbehind = left.east();
		        behind = leftbehind.south();
		        break;
		    case EAST:
		        left = pos.east();
		        leftup = left.above();
		        leftbehind = left.south();
		        behind = leftbehind.west();
		        break;
		    case SOUTH:
		        left = pos.south();
		        leftup = left.above();
		        leftbehind = left.west();
		        behind = leftbehind.north();
		        break;
		    case WEST:
		        left = pos.west();
		        leftup = left.above();
		        leftbehind = left.north();
		        behind = leftbehind.east();
		        break;
		}
		pLevel.setBlock(left, Register.fuelrefinerleft_BLOCK.get().defaultBlockState().setValue(BlockStateProperties.FACING, direction), 2);
		pLevel.setBlock(leftup, Register.fuelrefinerup_BLOCK.get().defaultBlockState(), 2);
		pLevel.setBlock(behind, Register.fuelrefinerbehind_BLOCK.get().defaultBlockState().setValue(BlockStateProperties.FACING, direction), 2);
		pLevel.setBlock(leftbehind, Register.fuelrefinerbehind_BLOCK.get().defaultBlockState().setValue(BlockStateProperties.FACING, direction), 2);
		}
	}
	
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.FACING);
    }
    
	@Override
    public VoxelShape getVisualShape(BlockState p_312193_, BlockGetter p_310654_, BlockPos p_310658_, CollisionContext p_311129_) {
	      return Shapes.empty();
	}
	
	@Override
	public float getShadeBrightness(BlockState p_312407_, BlockGetter p_310193_, BlockPos p_311965_) {
	   return 1.0F;
	}

	@Override
    public boolean propagatesSkylightDown(BlockState p_312717_, BlockGetter p_312877_, BlockPos p_312899_) {
       return true;
    }
	
	 static {
	        BlockJSON.GenModelsJSONBasic(global_name);
	        BlockJSON.GenBlockStateJSONBasic(global_name);
	        BlockJSON.GenItemJSONBasic(global_name);
	        BlockJSON.GenLootTableJSONBasic(global_name);
	 }
}

