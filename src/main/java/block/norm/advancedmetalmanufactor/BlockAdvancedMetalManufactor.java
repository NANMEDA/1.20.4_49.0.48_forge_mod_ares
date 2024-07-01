package block.norm.advancedmetalmanufactor;

import javax.annotation.Nullable;

import com.menu.advancedmetalmanufactor.AdvancedMetalManufactorMenuProvider;

import block.entity.consumer.advancedmetalmanufactor.AdvancedMetalManufactorEntity;
import block.norm.BlockJSON;
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


public class BlockAdvancedMetalManufactor extends Block implements EntityBlock{
	public static String global_name = "advancedmetal_manufactor"; 
	
	public BlockAdvancedMetalManufactor(Properties properties) {
		super(properties);
	}
	
	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState pBlockState) {
		return new AdvancedMetalManufactorEntity(pos, pBlockState);
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
	
	@Override
	public InteractionResult use(BlockState blockstate,Level level,BlockPos pos,Player player,InteractionHand interactionhand,BlockHitResult blockHitResult) {
		if(!level.isClientSide()) {
			var BlockEntity = level.getBlockEntity(pos);
			if(BlockEntity instanceof AdvancedMetalManufactorEntity entity) {
				ItemStack out = entity.getItems().getStackInSlot(3);
				if(!out.isEmpty()) {
					ItemStack hold = player.getMainHandItem();
					if(hold.isEmpty() || hold.getItem()==out.getItem()) {
						int count = 64-hold.getCount();
						if(count>=out.getCount()) {
							player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(out.getItem(),hold.getCount()+out.getCount()));
							entity.getItems().setStackInSlot(3, ItemStack.EMPTY);
							level.sendBlockUpdated(pos, blockstate, blockstate, Block.UPDATE_ALL);
							return InteractionResult.SUCCESS;
						}else {
							player.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(out.getItem(),64));
							entity.getItems().setStackInSlot(3, new ItemStack(out.getItem(),out.getCount()-count));
							level.sendBlockUpdated(pos, blockstate, blockstate, Block.UPDATE_ALL);
							return InteractionResult.SUCCESS;
						}
					}
				}
				IForgeServerPlayer ifpe = (IForgeServerPlayer)player;
				ifpe.openMenu(new AdvancedMetalManufactorMenuProvider(pos), pos);
			}else {
				throw new IllegalStateException("missing block:"+ global_name);
			}
		}
		return InteractionResult.SUCCESS;
	}
	
	
	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pBlockState, BlockEntityType<T> pBlockEntityType) {
	     if(pLevel.isClientSide()) {
	    	 return (level,pos,state,blockentity) -> {
	    		 if(blockentity instanceof AdvancedMetalManufactorEntity entity) {
	    			 entity.clienttick();
	    		 }
	    	 };
	     }else {
    		 return (level,pos,state,blockentity) -> {
	    		 if(blockentity instanceof AdvancedMetalManufactorEntity entity) {
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
            if (blockEntity instanceof AdvancedMetalManufactorEntity) {
                ((AdvancedMetalManufactorEntity) blockEntity).drop();
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
        if (context.getClickedFace() == Direction.DOWN||context.getClickedFace() == Direction.UP) {
        	Direction playerDirection = context.getNearestLookingDirection().getOpposite();
        	if(playerDirection==Direction.DOWN||playerDirection==Direction.UP) {
        		playerDirection = Direction.NORTH; 
        	} 
        	if(checkSpaceEnough(context)) {
        		return this.defaultBlockState().setValue(BlockStateProperties.FACING, playerDirection);
        	}
        	return null;
        } else {
            return null;
        }
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
			if(plevel.getBlockState(pos).getBlock()!=Blocks.AIR) {
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
			
			pLevel.setBlock(pos.above(), Register.advancedmetalmanufactorup_BLOCK.get().defaultBlockState(), 2);
			pLevel.setBlock(north.above(), Register.advancedmetalmanufactorup_BLOCK.get().defaultBlockState(), 2);
			pLevel.setBlock(south.above(), Register.advancedmetalmanufactorup_BLOCK.get().defaultBlockState(), 2);
			pLevel.setBlock(east.above(), Register.advancedmetalmanufactorup_BLOCK.get().defaultBlockState(), 2);
			pLevel.setBlock(west.above(), Register.advancedmetalmanufactorup_BLOCK.get().defaultBlockState(), 2);
			pLevel.setBlock(north.east().above(), Register.advancedmetalmanufactorup_BLOCK.get().defaultBlockState(), 2);
			pLevel.setBlock(south.west().above(), Register.advancedmetalmanufactorup_BLOCK.get().defaultBlockState(), 2);
			pLevel.setBlock(east.south().above(), Register.advancedmetalmanufactorup_BLOCK.get().defaultBlockState(), 2);
			pLevel.setBlock(west.north().above(), Register.advancedmetalmanufactorup_BLOCK.get().defaultBlockState(), 2);
			
			pLevel.setBlock(north, Register.advancedmetalmanufactoraround_BLOCK.get().defaultBlockState().setValue(BlockStateProperties.FACING, Direction.NORTH), 2);
			pLevel.setBlock(south, Register.advancedmetalmanufactoraround_BLOCK.get().defaultBlockState().setValue(BlockStateProperties.FACING, Direction.SOUTH), 2);
			pLevel.setBlock(east, Register.advancedmetalmanufactoraround_BLOCK.get().defaultBlockState().setValue(BlockStateProperties.FACING, Direction.EAST), 2);
			pLevel.setBlock(west, Register.advancedmetalmanufactoraround_BLOCK.get().defaultBlockState().setValue(BlockStateProperties.FACING, Direction.WEST), 2);
			
			pLevel.setBlock(north.east(), Register.advancedmetalmanufactorcorner_BLOCK.get().defaultBlockState().setValue(BlockStateProperties.FACING, Direction.NORTH), 2);
			pLevel.setBlock(south.west(), Register.advancedmetalmanufactorcorner_BLOCK.get().defaultBlockState().setValue(BlockStateProperties.FACING, Direction.SOUTH), 2);
			pLevel.setBlock(east.south(), Register.advancedmetalmanufactorcorner_BLOCK.get().defaultBlockState().setValue(BlockStateProperties.FACING, Direction.EAST), 2);
			pLevel.setBlock(west.north(), Register.advancedmetalmanufactorcorner_BLOCK.get().defaultBlockState().setValue(BlockStateProperties.FACING, Direction.WEST), 2);
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

