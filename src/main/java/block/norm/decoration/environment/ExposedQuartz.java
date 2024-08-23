package block.norm.decoration.environment;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ExposedQuartz extends Block{

	public static final IntegerProperty KIND = IntegerProperty.create("kind", 1, 16);
	public static final String global_name = "exposed_quartz";
	
	public ExposedQuartz(Properties p_51021_) {
		super(p_51021_);
	}
	

    private static final VoxelShape SHAPE_KIND_1 = Shapes.or(	
	    		Block.box(6, 0, 7, 9, 8, 8),
	    		Block.box(7, 1, 6, 8, 9, 10),
	    		Block.box(11, 0, 3, 12, 5, 7),
	    		Block.box(8, 0, 4, 13, 6, 5),
	    		Block.box(4, 0, 11, 6, 8, 12),
	    		Block.box(6, 0, 8, 9, 1, 12)
            );
    
    private static final VoxelShape SHAPE_KIND_2 = Shapes.or(	
	    		Block.box(9, 0, 6, 11, 12, 8),
	    		Block.box(7, 0, 6, 8, 11, 9),
	    		Block.box(6, 4, 8, 7, 9, 10),
	    		Block.box(3, 0, 10, 5, 6, 11),
	    		Block.box(6, 0, 11, 14, 2, 12),
	    		Block.box(10, 0, 10, 13, 8, 11)
            );

    private static final VoxelShape SHAPE_KIND_3 = Shapes.or(	
	    		Block.box(3, 1, 8, 9, 7, 9),
	    		Block.box(1, 0, 5, 11, 2, 11),
	    		Block.box(8, 0, 3, 12, 1, 8),
	    		Block.box(4, 1, 3, 5, 4, 7),
	    		Block.box(6, 1, 7, 7, 8, 12)
			);
    
    private static final VoxelShape SHAPE_KIND_4 = Shapes.or(	
	    		Block.box(10, 0, 3, 12, 11, 5),
	    		Block.box(2, 0, 0, 7, 1, 4),
	    		Block.box(5, 0, 8, 11, 1, 15),
	    		Block.box(3, 1, 1, 4, 2, 2),
	    		Block.box(9, 1, 10, 10, 12, 13),
	    		Block.box(6, 1, 11, 12, 11, 12)
			);
    

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(KIND);
    }
    
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, net.minecraft.world.phys.shapes.CollisionContext context) {
        int kind = state.getValue(KIND);
        VoxelShape shape;
        switch (kind%4) {
            case 1:
            	shape= SHAPE_KIND_1;
            	break;
            case 2:
            	shape= SHAPE_KIND_2;
            	break;
            case 3:
            	shape= SHAPE_KIND_3;
            	break;
            case 0:
            	shape= SHAPE_KIND_4;
            	break;
            default:
            	shape= Shapes.empty();
        }
        switch ((kind-1)/4) {
            case 2:
                shape = rotateShape(shape, 270); // Rotate -90 degrees
                break;
            case 0:
                shape = rotateShape(shape, 90);  // Rotate 90 degrees
                break;
            case 3:
                shape = rotateShape(shape, 180); // Rotate 180 degrees
                break;
            case 1:
            default:
                break; // No rotation needed for SOUTH
        }

        return shape;
    }

    private VoxelShape rotateShape(VoxelShape shape, int angle) {
        // Rotating voxel shapes manually
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

	
}
