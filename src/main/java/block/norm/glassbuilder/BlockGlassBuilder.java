package block.norm.glassbuilder;

import javax.annotation.Nullable;

import block.entity.consumer.glassbuilder.GlassBuilderEntity;
import item.ItemRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
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
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import util.json.BlockJSON;

/**
 * 玻璃合成机
 * - 火星没有沙子
 * @author NANMEDA
 * */
public class BlockGlassBuilder extends Block implements EntityBlock{
	public static String global_name = "glass_builder"; 
	
	public BlockGlassBuilder(Properties properties) {
		super(properties);
	}
	
	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState pBlockState) {
		return new GlassBuilderEntity(pos, pBlockState);
	}
	
	@Override
	public boolean isPathfindable(BlockState p_60475_, BlockGetter p_60476_, BlockPos p_60477_, PathComputationType p_60478_) {
		return true;
	}
	
	@Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
		return Block.box(0,0,0,16,16,16);
    }
	
	@SuppressWarnings("deprecation")
	@Override
	public InteractionResult use(BlockState blockstate,Level level,BlockPos pos,Player player,InteractionHand interactionhand,BlockHitResult blockHitResult) {
		super.use(blockstate,level,pos,player,interactionhand,blockHitResult);
		if(!level.isClientSide()) {
			var BlockEntity = level.getBlockEntity(pos);
			if (BlockEntity instanceof GlassBuilderEntity entity) {
			    ItemStack mainHandItem = player.getMainHandItem();
			    ItemStack entityStackSlot0 = entity.getItems().getStackInSlot(0);
			    ItemStack entityStackSlot1 = entity.getItems().getStackInSlot(1);
			    if (mainHandItem.isEmpty()) {
			    	if(!entityStackSlot1.isEmpty()) {
			    		player.setItemInHand(InteractionHand.MAIN_HAND, entityStackSlot1);
			    		entity.getItems().setStackInSlot(1,ItemStack.EMPTY);
			    		return InteractionResult.SUCCESS;
			    	}
			    	if(!entityStackSlot0.isEmpty()) {
			    		player.setItemInHand(InteractionHand.MAIN_HAND, entityStackSlot0);
			    		entity.getItems().setStackInSlot(0,ItemStack.EMPTY);
			    		return InteractionResult.SUCCESS;
			    	}
			       	// level.sendBlockUpdated(pos, blockstate, blockstate, Block.UPDATE_ALL);
			        return InteractionResult.PASS;
			    } else {
			        Item holdItem = mainHandItem.getItem();
			        if(holdItem!=ItemRegister.MATERIAL_ITEMS[3].get()&&holdItem!=ItemRegister.MATERIAL_ITEMS[5].get()) {
			        	return InteractionResult.PASS;
			        }
			        if (entityStackSlot0.isEmpty()) {
			        	entity.getItems().setStackInSlot(0,mainHandItem);
			        	if(mainHandItem.getCount()>=6) {
			    			level.sendBlockUpdated(pos, blockstate, blockstate, Block.UPDATE_ALL);
			    		}
			    		player.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
			            return InteractionResult.SUCCESS;
			        }
			        return InteractionResult.PASS;
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
	    		 if(blockentity instanceof GlassBuilderEntity entity) {
	    			 entity.clienttick();
	    		 }
	    	 };
	     }else {
    		 return (level,pos,state,blockentity) -> {
	    		 if(blockentity instanceof GlassBuilderEntity entity) {
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
            if (blockEntity instanceof GlassBuilderEntity) {
                ((GlassBuilderEntity) blockEntity).drop();
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }
	
	public void cleanAllBlock(BlockState pState, Level pLevel, BlockPos pPos) {
        BlockPos up = pPos.above();
		BlockPos left = null;
		BlockPos leftup = null;
		Direction direction = pState.getValue(BlockStateProperties.FACING).getOpposite();
		switch (direction) {
		    case NORTH:
			default:
		        left = pPos.north();
		        leftup = left.above();
			break;
		    case EAST:
		        left = pPos.east();
		        leftup = left.above();
		        break;
		    case SOUTH:
		        left = pPos.south();
		        leftup = left.above();
		        break;
		    case WEST:
		        left = pPos.west();
		        leftup = left.above();
		        break;
		}
		pLevel.setBlock(up, Blocks.AIR.defaultBlockState(), 2);
		pLevel.setBlock(left, Blocks.AIR.defaultBlockState(), 2);
		pLevel.setBlock(leftup, Blocks.AIR.defaultBlockState(), 2);
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
		BlockPos up = context.getClickedPos().above();
		BlockPos left = null;
		BlockPos leftup = null;
		direction = direction.getOpposite();
		switch (direction) {
		    case NORTH:
			default:
		        left = context.getClickedPos().north();
		        leftup = left.above();
		        break;
		    case EAST:
		        left = context.getClickedPos().east();
		        leftup = left.above();
		        break;
		    case SOUTH:
		        left = context.getClickedPos().south();
		        leftup = left.above();
		        break;
		    case WEST:
		        left = context.getClickedPos().west();
		        leftup = left.above();
		        break;
		}
		Level plevel = context.getLevel();
		if(plevel.isEmptyBlock(left)
				&&plevel.isEmptyBlock(up)
				&&plevel.isEmptyBlock(leftup)) {
			return true;
		}
		return false;
	}
	
	@Override
	public void setPlacedBy(Level pLevel, BlockPos pos, BlockState pstate, @Nullable LivingEntity p_49850_, ItemStack p_49851_) {
		super.setPlacedBy(pLevel, pos, pstate, p_49850_, p_49851_);
		if(!pLevel.isClientSide()) {
		BlockPos up = pos.above();
		BlockPos left = null;
		BlockPos leftup = null;
		Direction direction = pstate.getValue(BlockStateProperties.FACING).getOpposite();
		switch (direction) {
		    case NORTH:
			default:
		        left = pos.north();
		        leftup = left.above();
		        break;
		    case EAST:
		        left = pos.east();
		        leftup = left.above();
		        break;
		    case SOUTH:
		        left = pos.south();
		        leftup = left.above();
		        break;
		    case WEST:
		        left = pos.west();
		        leftup = left.above();
		        break;
		}
		pLevel.setBlock(up, Register.glassbuilderup_BLOCK.get().defaultBlockState(), 2);
		pLevel.setBlock(left, Register.glassbuilderleft_BLOCK.get().defaultBlockState().setValue(BlockStateProperties.FACING, direction), 2);
		pLevel.setBlock(leftup, Register.glassbuilderup_BLOCK.get().defaultBlockState(), 2);
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

