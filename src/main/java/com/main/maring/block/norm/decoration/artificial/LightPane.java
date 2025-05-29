package com.main.maring.block.norm.decoration.artificial;

import javax.annotation.Nullable;

import com.main.maring.util.json.BlockJSON;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class LightPane extends Block {

	public LightPane(Properties p_49795_) {
		super(p_49795_);
		// TODO 自动生成的构造函数存根
	}
	
	public static final BooleanProperty LIT = BlockStateProperties.LIT;
	
	public static String global_name = "light_pane"; 

	@Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        VoxelShape shape = Shapes.join(
        		Block.box(4, 14, 4, 12, 15, 12), 
        		Block.box(3, 15, 3, 13, 16, 13),
                BooleanOp.OR
        );

        Direction facing = state.getValue(BlockStateProperties.FACING);

        switch (facing) {
            case WEST:
                shape = rotateShape(shape, 270,'z');
                break;
            case EAST:
            	shape = rotateShape(shape, 90,'z');
                break;
            case NORTH:
            	shape = rotateShape(shape, 90,'x');
                break;
            case SOUTH:
            	shape = rotateShape(shape, 270,'x');
            	break;
            case UP:
            	shape = rotateShape(shape, 180,'x');
            	break;
            case DOWN:
            default:
                break; // No rotation needed for SOUTH
        }

        return shape;
    }

	private VoxelShape rotateShape(VoxelShape shape, int angle, char axis) {
	    // Rotating voxel shapes manually
	    VoxelShape[] buffer = new VoxelShape[]{shape, Shapes.empty()};

	    for (int i = 0; i < (angle / 90); i++) {
	        buffer[0].forAllBoxes((x1, y1, z1, x2, y2, z2) -> {
	            switch (axis) {
	                case 'x':
	                    // Rotation around X axis
	                    buffer[1] = Shapes.or(buffer[1], Shapes.box(x1, 1 - z2, y1, x2, 1 - z1, y2));
	                    break;
	                case 'y':
	                    // Rotation around Y axis (original case)
	                    buffer[1] = Shapes.or(buffer[1], Shapes.box(z1, y1, 1 - x2, z2, y2, 1 - x1));
	                    break;
	                case 'z':
	                    // Rotation around Z axis
	                    buffer[1] = Shapes.or(buffer[1], Shapes.box(1 - y2, x1, z1, 1 - y1, x2, z2));
	                    break;
	                default:
	                    throw new IllegalArgumentException("Invalid axis: " + axis);
	            }
	        });
	        buffer[0] = buffer[1];
	        buffer[1] = Shapes.empty();
	    }

	    return buffer[0];
	}

	
		
	@Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
		Direction direction = context.getClickedFace();
    	return this.defaultBlockState().setValue(BlockStateProperties.FACING, direction).setValue(LIT, Boolean.valueOf(false));
    }

	
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.FACING);
        builder.add(LIT);
    }
    
    @Override
	public InteractionResult use(BlockState blockstate,Level level,BlockPos pos,Player player,InteractionHand interactionhand,BlockHitResult blockHitResult) {
		if(!level.isClientSide)
			//if(interactionhand == InteractionHand.MAIN_HAND)
		    	level.setBlockAndUpdate(pos, this.defaultBlockState()
		    			.setValue(BlockStateProperties.FACING,blockstate.getValue(BlockStateProperties.FACING))
						.setValue(LIT, !blockstate.getValue(LIT).booleanValue()));
		return InteractionResult.SUCCESS;
	}

	
	 static {
	        BlockJSON.GenModelsJSONBasic(global_name);
	        BlockJSON.GenBlockStateJSONBasic(global_name);
	        BlockJSON.GenItemJSONBasic(global_name);
	        BlockJSON.GenLootTableJSONBasic(global_name);
	 }

}
