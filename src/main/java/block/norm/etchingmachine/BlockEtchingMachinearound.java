package block.norm.etchingmachine;


import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import util.json.BlockJSON;


public class BlockEtchingMachinearound extends Block{
	public static String global_name = "etching_machine_around"; 
	
	public BlockEtchingMachinearound(Properties properties) {
		super(properties);
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
		//return Block.box(0,0,0,16,16,16);
    }
	
	
	@Override
	public InteractionResult use(BlockState blockstate,Level level,BlockPos pos,Player player,InteractionHand interactionhand,BlockHitResult blockHitResult) {
		Direction direction = blockstate.getValue(BlockStateProperties.FACING).getOpposite();
		BlockPos apos = null;
		switch (direction) {
	    case SOUTH:
	        apos = pos.south();
	        break;
	    case NORTH:
	    	apos = pos.north();
	        break;
	    case EAST:
	    	apos = pos.east();
	        break;
	    case WEST:
	    	apos = pos.west();
	        break;
		default:
			break;
		}
		return level.getBlockState(apos).use(level, player, interactionhand, new BlockHitResult(blockHitResult.getLocation(), blockHitResult.getDirection(), apos, blockHitResult.isInside()));
	}
	
	
	@SuppressWarnings("deprecation")
	@Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
		Direction direction = pState.getValue(BlockStateProperties.FACING).getOpposite();
		switch (direction) {
	    case SOUTH:
	        pLevel.setBlock(pPos.south(), Blocks.AIR.defaultBlockState(), 2);
	        break;
	    case NORTH:
	        pLevel.setBlock(pPos.north(), Blocks.AIR.defaultBlockState(), 2);
	        break;
	    case EAST:
	        pLevel.setBlock(pPos.east(), Blocks.AIR.defaultBlockState(), 2);
	        break;
	    case WEST:
	        pLevel.setBlock(pPos.west(), Blocks.AIR.defaultBlockState(), 2);
	        break;
		default:
			break;
		}
		super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }
	
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.FACING);
    }
	
	 static {
	        BlockJSON.GenModelsJSONBasic(global_name,"empty");
	        BlockJSON.GenBlockStateJSONBasic(global_name);
	        BlockJSON.GenItemJSONBasic(global_name);
	        BlockJSON.GenLootTableJSONBasic(global_name,BlockEtchingMachine.global_name);
	 }
}

