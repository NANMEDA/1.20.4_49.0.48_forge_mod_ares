package block.norm.basicmetalmanufactor;

import javax.annotation.Nullable;

import com.menu.basicmetalmanufactor.BasicMetalManufactorMenuProvider;

import block.entity.consumer.basicmetalmanufactor.BasicMetalManufactorEntity;
import block.norm.BlockJSON;
import block.norm.BlockRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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


public class BlockBasicMetalManufactor extends Block implements EntityBlock{
	public static String global_name = "basicmetal_manufactor"; 
	
	public BlockBasicMetalManufactor(Properties properties) {
		super(properties);
	}
	
	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState pBlockState) {
		return new BasicMetalManufactorEntity(pos, pBlockState);
	}
	
	@Override
	public boolean isPathfindable(BlockState p_60475_, BlockGetter p_60476_, BlockPos p_60477_, PathComputationType p_60478_) {
		return true;
	}
	
	@Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
		//return Block.box(-16, 0, 0, 16, 32, 32);
		//return Block.box(-4, 0, 0, 16, 24, 32);
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
			if(BlockEntity instanceof BasicMetalManufactorEntity entity) {
				if(player.getMainHandItem().isEmpty()&&!entity.getItems().getStackInSlot(5).isEmpty()) {
					player.setItemInHand(InteractionHand.MAIN_HAND, entity.getItems().getStackInSlot(5));
					entity.getItems().setStackInSlot(5, ItemStack.EMPTY);
					level.sendBlockUpdated(pos, blockstate, blockstate, Block.UPDATE_ALL);
					return InteractionResult.SUCCESS;
				}
				IForgeServerPlayer ifpe = (IForgeServerPlayer)player;
				ifpe.openMenu(new BasicMetalManufactorMenuProvider(pos), pos);
			}else {
				throw new IllegalStateException("missing block-basicmetal_manufactor");
			}
		}
		return InteractionResult.SUCCESS;
	}
	
	
	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pBlockState, BlockEntityType<T> pBlockEntityType) {
	     if(pLevel.isClientSide()) {
	    	 return (level,pos,state,blockentity) -> {
	    		 if(blockentity instanceof BasicMetalManufactorEntity entity) {
	    			 entity.clienttick();
	    		 }
	    	 };
	     }else {
    		 return (level,pos,state,blockentity) -> {
	    		 if(blockentity instanceof BasicMetalManufactorEntity entity) {
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
            if (blockEntity instanceof BasicMetalManufactorEntity) {
                ((BasicMetalManufactorEntity) blockEntity).drop();
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }
	
	public void cleanAllBlock(BlockState pState, Level pLevel, BlockPos pPos) {
        BlockPos up = pPos.above();
		BlockPos left = null;
		BlockPos leftup = null;
		BlockPos behind = null;
		BlockPos behindup = null;
		BlockPos leftbehind = null;
		BlockPos leftbehindup = null;
		Direction direction = pState.getValue(BlockStateProperties.FACING).getOpposite();
		switch (direction) {
		    case NORTH:
			default:
		        left = pPos.north();
		        leftup = left.above();
		        leftbehind = left.east();
		        leftbehindup = leftbehind.above();
		        behind = leftbehind.south();
		        behindup = behind.above();
		        break;
		    case EAST:
		        left = pPos.east();
		        leftup = left.above();
		        leftbehind = left.south();
		        leftbehindup = leftbehind.above();
		        behind = leftbehind.west();
		        behindup = behind.above();
		        break;
		    case SOUTH:
		        left = pPos.south();
		        leftup = left.above();
		        leftbehind = left.west();
		        leftbehindup = leftbehind.above();
		        behind = leftbehind.north();
		        behindup = behind.above();
		        break;
		    case WEST:
		        left = pPos.west();
		        leftup = left.above();
		        leftbehind = left.north();
		        leftbehindup = leftbehind.above();
		        behind = leftbehind.east();
		        behindup = behind.above();
		        break;
		}
		pLevel.setBlock(up, Blocks.AIR.defaultBlockState(), 2);
		pLevel.setBlock(left, Blocks.AIR.defaultBlockState(), 2);
		pLevel.setBlock(leftup, Blocks.AIR.defaultBlockState(), 2);
		pLevel.setBlock(behind, Blocks.AIR.defaultBlockState(), 2);
		pLevel.setBlock(behindup, Blocks.AIR.defaultBlockState(), 2);
		pLevel.setBlock(leftbehind, Blocks.AIR.defaultBlockState(), 2);
		pLevel.setBlock(leftbehindup, Blocks.AIR.defaultBlockState(), 2);
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
		BlockPos behind = null;
		BlockPos behindup = null;
		BlockPos leftbehind = null;
		BlockPos leftbehindup = null;
		direction = direction.getOpposite();
		switch (direction) {
		    case NORTH:
			default:
		        left = context.getClickedPos().north();
		        leftup = left.above();
		        leftbehind = left.east();
		        leftbehindup = leftbehind.above();
		        behind = leftbehind.south();
		        behindup = behind.above();
		        break;
		    case EAST:
		        left = context.getClickedPos().east();
		        leftup = left.above();
		        leftbehind = left.south();
		        leftbehindup = leftbehind.above();
		        behind = leftbehind.west();
		        behindup = behind.above();
		        break;
		    case SOUTH:
		        left = context.getClickedPos().south();
		        leftup = left.above();
		        leftbehind = left.west();
		        leftbehindup = leftbehind.above();
		        behind = leftbehind.north();
		        behindup = behind.above();
		        break;
		    case WEST:
		        left = context.getClickedPos().west();
		        leftup = left.above();
		        leftbehind = left.north();
		        leftbehindup = leftbehind.above();
		        behind = leftbehind.east();
		        behindup = behind.above();
		        break;
		}
		Level plevel = context.getLevel();
		if(plevel.getBlockState(left).getBlock()==Blocks.AIR
				&&plevel.getBlockState(up).getBlock()==Blocks.AIR
				&&plevel.getBlockState(leftup).getBlock()==Blocks.AIR
				&&plevel.getBlockState(behind).getBlock()==Blocks.AIR
				&&plevel.getBlockState(behindup).getBlock()==Blocks.AIR
				&&plevel.getBlockState(leftbehind).getBlock()==Blocks.AIR
				&&plevel.getBlockState(leftbehindup).getBlock()==Blocks.AIR) {
			return true;
		}
		return false;
	}
	
	@Override
	public void setPlacedBy(Level pLevel, BlockPos p_49848_, BlockState p_49849_, @Nullable LivingEntity p_49850_, ItemStack p_49851_) {
		super.setPlacedBy(pLevel, p_49848_, p_49849_, p_49850_, p_49851_);
		if(!pLevel.isClientSide()) {
		BlockPos up = p_49848_.above();
		BlockPos left = null;
		BlockPos leftup = null;
		BlockPos behind = null;
		BlockPos behindup = null;
		BlockPos leftbehind = null;
		BlockPos leftbehindup = null;
		Direction direction = p_49849_.getValue(BlockStateProperties.FACING).getOpposite();
		switch (direction) {
		    case NORTH:
			default:
		        left = p_49848_.north();
		        leftup = left.above();
		        leftbehind = left.east();
		        leftbehindup = leftbehind.above();
		        behind = leftbehind.south();
		        behindup = behind.above();
		        break;
		    case EAST:
		        left = p_49848_.east();
		        leftup = left.above();
		        leftbehind = left.south();
		        leftbehindup = leftbehind.above();
		        behind = leftbehind.west();
		        behindup = behind.above();
		        break;
		    case SOUTH:
		        left = p_49848_.south();
		        leftup = left.above();
		        leftbehind = left.west();
		        leftbehindup = leftbehind.above();
		        behind = leftbehind.north();
		        behindup = behind.above();
		        break;
		    case WEST:
		        left = p_49848_.west();
		        leftup = left.above();
		        leftbehind = left.north();
		        leftbehindup = leftbehind.above();
		        behind = leftbehind.east();
		        behindup = behind.above();
		        break;
		}
		pLevel.setBlock(up, BlockRegister.basicmetalmanufactorup_BLOCK.get().defaultBlockState(), 2);
		pLevel.setBlock(left, BlockRegister.basicmetalmanufactorleft_BLOCK.get().defaultBlockState().setValue(BlockStateProperties.FACING, direction), 2);
		pLevel.setBlock(leftup, BlockRegister.basicmetalmanufactorup_BLOCK.get().defaultBlockState(), 2);
		pLevel.setBlock(behind, BlockRegister.basicmetalmanufactorbehind_BLOCK.get().defaultBlockState().setValue(BlockStateProperties.FACING, direction), 2);
		pLevel.setBlock(leftbehind, BlockRegister.basicmetalmanufactorbehind_BLOCK.get().defaultBlockState().setValue(BlockStateProperties.FACING, direction), 2);
		pLevel.setBlock(behindup, BlockRegister.basicmetalmanufactorbehindup_BLOCK.get().defaultBlockState().setValue(BlockStateProperties.FACING, direction), 2);
		pLevel.setBlock(leftbehindup, BlockRegister.basicmetalmanufactorbehindup_BLOCK.get().defaultBlockState().setValue(BlockStateProperties.FACING, direction), 2);
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

