package block.norm.decoration.environment;

import menu.show.ShowBlockMenuProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ExposedIron extends Block{

	public static final IntegerProperty KIND = IntegerProperty.create("kind", 1, 16);
	public static final String global_name = "exposed_iron";

	
    private static final VoxelShape SHAPE_KIND_1 = Shapes.or(	
	            Block.box(4, 0, 3, 6, 2, 5), 
	            Block.box(1, 0, 9, 5, 3, 13), 
	            Block.box(8, 3, 8, 12, 6, 12), 
	            Block.box(7, 0, 3, 14, 3, 13)
            );
    
    private static final VoxelShape SHAPE_KIND_2 = Shapes.or(	
		    		Block.box(3, 3, 7, 6, 5, 9),
		    		Block.box(2, 0.2, 2, 12, 3.2, 12),
		    		Block.box(7, 2.2, 4, 11, 7.2, 6),
		    		Block.box(4, 0, 5, 14, 8, 14)
                );

    private static final VoxelShape SHAPE_KIND_3 = Shapes.or(	
		    		Block.box(1, 0, 3, 4, 2, 5),
		    		Block.box(3, 0, 1, 10, 10, 11),
		    		Block.box(10, 0, 3, 12, 4, 7),
		    		Block.box(7, 0, 5, 15, 6, 14)
    			);
    
    private static final VoxelShape SHAPE_KIND_4 = Shapes.or(	
		    		Block.box(8, 7, 10, 12, 8, 12),
		    		Block.box(4, 0, 1, 14, 14, 9),
		        	Block.box(1, 0, 4, 11, 12, 13),
		        	Block.box(7, 0, 6, 15, 7, 14)
				);
    
    public ExposedIron(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(KIND, 1));
    }
    
    @Override
	public InteractionResult use(BlockState blockstate,Level level,BlockPos pos,Player player,InteractionHand interactionhand,BlockHitResult blockHitResult) {
		if(!level.isClientSide()) {
			ServerPlayer ifpe = (ServerPlayer)player;
			ifpe.openMenu(new ShowBlockMenuProvider(pos));
		}
		return InteractionResult.SUCCESS;
	}

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
