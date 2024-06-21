package block.norm.crystalbuilder;

import javax.annotation.Nullable;

import com.item.register.ItemRegister;

import block.entity.neutral.crystalbuilder.CrystalBuilderEntity;
import block.norm.BlockJSON;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
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
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;


public class BlockCrystalBuilder extends Block implements EntityBlock{
	public static String global_name = "crystal_builder"; 
	
	public BlockCrystalBuilder(Properties properties) {
		super(properties);
	}
	
	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState pBlockState) {
		return new CrystalBuilderEntity(pos, pBlockState);
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
			if (BlockEntity instanceof CrystalBuilderEntity entity) {
			    ItemStack mainHandItem = player.getMainHandItem();
			    ItemStack entityStackSlot0 = entity.getItems().getStackInSlot(0);
			    ItemStack entityStackSlot1 = entity.getItems().getStackInSlot(1);
			    if (!entityStackSlot0.isEmpty() &&
			        (mainHandItem.isEmpty() ||
			         (mainHandItem.getItem() == ItemRegister.MATERIAL_ITEMS[5].get()))) {
			    	int count = mainHandItem.getCount() + entityStackSlot0.getCount();
			        player.setItemInHand(InteractionHand.MAIN_HAND, mainHandItem.isEmpty() ?
			                             entityStackSlot0 : new ItemStack(entityStackSlot0.getItem(), count<=64?count:64));
			        entity.getItems().setStackInSlot(0, count<=64?ItemStack.EMPTY : new ItemStack(entityStackSlot0.getItem(), count-64));
			        // level.sendBlockUpdated(pos, blockstate, blockstate, Block.UPDATE_ALL);
			        return InteractionResult.SUCCESS;
			    } else if (!mainHandItem.isEmpty()) {
			        Item holdItem = mainHandItem.getItem();
			        int holdCount = mainHandItem.getCount();
			        if (holdItem == Items.QUARTZ && entity.crystal < 960) {
			            entity.crystal += holdCount;
			            if (entity.crystal == holdCount) {
			                level.sendBlockUpdated(pos, blockstate, blockstate, Block.UPDATE_ALL);
			            }
			            player.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
			            return InteractionResult.SUCCESS;
			        } else if (holdItem == Items.QUARTZ_BLOCK && entity.crystal < 960) {
			            entity.crystal += holdCount * 9;
			            if (entity.crystal == holdCount * 9) {
			                level.sendBlockUpdated(pos, blockstate, blockstate, Block.UPDATE_ALL);
			            }
			            player.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
			            return InteractionResult.SUCCESS;
			        }
			        if (entityStackSlot1.isEmpty()) {
			            short accelerate, accelerateIndex;
			            if (holdItem == Items.DIAMOND) {
			                accelerate = 600; // 60 seconds
			                accelerateIndex = 11; // speed x 12 = 10s
			            } else if (holdItem == Items.EMERALD) {
			                accelerate = 60; // 3 seconds
			                accelerateIndex = 59; // speed x 60 = 2s
			            } else if (holdItem == Items.AMETHYST_SHARD) {
			                accelerate = 3600; // 3 minutes
			                accelerateIndex = 1; // speed x 2 = 60s
			            } else if (holdItem == ItemRegister.MATERIAL_ITEMS[0].get()) {
			                accelerate = 600; // 30 seconds
			                accelerateIndex = 239; // speed x 240 = 0.5s
			            } else {
			                return InteractionResult.PASS;
			            }
			            entity.accelerate = accelerate;
			            entity.accelerate_index = accelerateIndex;
			            player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(holdItem, holdCount - 1));
			            entity.getItems().setStackInSlot(1, new ItemStack(holdItem, 1));
			            return InteractionResult.SUCCESS;
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
	    		 if(blockentity instanceof CrystalBuilderEntity entity) {
	    			 entity.clienttick();
	    		 }
	    	 };
	     }else {
    		 return (level,pos,state,blockentity) -> {
	    		 if(blockentity instanceof CrystalBuilderEntity entity) {
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
            if (blockEntity instanceof CrystalBuilderEntity) {
                ((CrystalBuilderEntity) blockEntity).drop();
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
		if(plevel.getBlockState(left).getBlock()==Blocks.AIR
				&&plevel.getBlockState(up).getBlock()==Blocks.AIR
				&&plevel.getBlockState(leftup).getBlock()==Blocks.AIR) {
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
		pLevel.setBlock(up, Register.crystalbuilderup_BLOCK.get().defaultBlockState(), 2);
		pLevel.setBlock(left, Register.crystalbuilderleft_BLOCK.get().defaultBlockState().setValue(BlockStateProperties.FACING, direction), 2);
		pLevel.setBlock(leftup, Register.crystalbuilderup_BLOCK.get().defaultBlockState(), 2);
		}
	}
	
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.FACING);
    }
	
	 static {
	        BlockJSON.GenModelsJSONBasic(global_name);
	        BlockJSON.GenBlockStateJSONBasic(global_name);
	        BlockJSON.GenItemJSONBasic(global_name);
	        BlockJSON.GenLootTableJSONBasic(global_name);
	 }
}

