package machine.energy.producer.solar;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import util.json.BlockJSON;
import util.net.EnergyNetProcess;

/**
 * @author NANMEDA
 * */
public class SolarBasement extends Block implements EntityBlock{
	public static final String global_name = "solar_basement"; 
	
	public SolarBasement(Properties properties) {
		super(properties
	            .sound(SoundType.AMETHYST)
	            .strength(5f,5f)
	            .noOcclusion()
	            .mapColor(MapColor.COLOR_GRAY)
	            );
	}
	
	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState pBlockState) {
		return new SolarBasementEntity(pos, pBlockState);
	}
	
	@Override
	public boolean isPathfindable(BlockState p_60475_, BlockGetter p_60476_, BlockPos p_60477_, PathComputationType p_60478_) {
		return true;
	}
	
	@Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
		VoxelShape shape = Shapes.or(
				Block.box(1, 0, 2, 15, 16, 15),
				Block.box(0, 1, 1, 16, 15, 16),
				Block.box(14, 5, 0, 15, 15, 1),
				Block.box(12, 5, 0, 13, 15, 1),
				Block.box(10, 5, 0, 11, 15, 1)
			);
		
        switch (p_60555_.getValue(BlockStateProperties.FACING)) {
        case WEST:
            shape = rotateShape(shape, 270); 
            break;
        case EAST:
            shape = rotateShape(shape, 90); 
            break;
        case SOUTH:
            break;
        case NORTH:
        default:
            shape = rotateShape(shape, 180);
            break;
        }
        return shape;
    }
	
    private VoxelShape rotateShape(VoxelShape shape, int angle) {
        VoxelShape[] buffer = new VoxelShape[]{shape, Shapes.empty()};

        for (int i = 0; i < (angle / 90); i++) {
            buffer[0].forAllBoxes((x1, y1, z1, x2, y2, z2) -> {
                buffer[1] = Shapes.or(buffer[1], Shapes.box(z1, y1, 1 - x2, z2, y2, 1 - x1));
            });
            buffer[0] = buffer[1];
            buffer[1] = Shapes.empty();
        }

        return buffer[0];
    }
    
	@Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
		Direction direction = context.getNearestLookingDirection();
		if(direction==Direction.DOWN||direction == Direction.UP) direction = Direction.NORTH;
    	return this.defaultBlockState().setValue(BlockStateProperties.FACING, direction);
    }
	
	@SuppressWarnings("deprecation")
	@Override
	public InteractionResult use(BlockState blockstate,Level level,BlockPos pos,Player player,InteractionHand interactionhand,BlockHitResult blockHitResult) {
		return super.use(blockstate,level,pos,player,interactionhand,blockHitResult);
	}
	
	
	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pBlockState, BlockEntityType<T> pBlockEntityType) {
	     if(pLevel.isClientSide()) {
	    	 return (level,pos,state,blockentity) -> {
	    		 if(blockentity instanceof SolarBasementEntity entity) {
	    			 entity.clienttick();
	    		 }
	    	 };
	     }else {
    		 return (level,pos,state,blockentity) -> {
	    		 if(blockentity instanceof SolarBasementEntity entity) {
	    			 entity.servertick();
	    		 }
	    	 };
	     }
	 }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.FACING);
    }
    
    @SuppressWarnings("deprecation")
	@Override
    public void onRemove(BlockState oldState, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if(!level.isClientSide)
    	if (!oldState.is(newState.getBlock())) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof SolarBasementEntity entity) {
                // Call the remove method in the BlockEntity to clean up network references
            	entity.remove();
            }
            super.onRemove(oldState, level, pos, newState, isMoving);
        }
    }

	
	 static {
	        BlockJSON.GenModelsJSONBasic(global_name);
	        BlockJSON.GenBlockStateJSONBasic(global_name);
	        BlockJSON.GenItemJSONBasic(global_name);
	        BlockJSON.GenLootTableJSONBasic(global_name);
	 }
}

