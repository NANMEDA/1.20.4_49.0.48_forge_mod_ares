package block.norm.decoration.environment;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ExposedIron extends Block{

	public static final IntegerProperty KIND = IntegerProperty.create("kind", 1, 4);
	public static final String global_name = "exposed_iron";

	
    private static final VoxelShape SHAPE_KIND_1 = Shapes.join(
            Block.box(4, 0, 3, 6, 2, 5), 
            Shapes.join(
                Block.box(1, 0, 9, 5, 3, 13), 
                Shapes.join(
                    Block.box(8, 3, 8, 12, 6, 12), 
                    Block.box(7, 0, 3, 14, 3, 13), 
                    BooleanOp.OR
                ), 
                BooleanOp.OR
            ), 
            BooleanOp.OR
        );
    
    private static final VoxelShape SHAPE_KIND_2 = Shapes.join(
    		Block.box(3, 3, 7, 6, 5, 9),
            Shapes.join(
            	Block.box(2, 0.2, 2, 12, 3.2, 12),
                Shapes.join(
                	Block.box(7, 2.2, 4, 11, 7.2, 6),
                	Block.box(4, 0, 5, 14, 8, 14), 
                    BooleanOp.OR
                ), 
                BooleanOp.OR
            ), 
            BooleanOp.OR
        );

    private static final VoxelShape SHAPE_KIND_3 = Shapes.join(
    		Block.box(1, 0, 3, 4, 2, 5),
            Shapes.join(
            	Block.box(3, 0, 1, 10, 10, 11),
                Shapes.join(
                	Block.box(10, 0, 3, 12, 4, 7),
                	Block.box(7, 0, 5, 15, 6, 14), 
                    BooleanOp.OR
                ), 
                BooleanOp.OR
            ), 
            BooleanOp.OR
        );
    
    private static final VoxelShape SHAPE_KIND_4 = Shapes.join(
    		Block.box(8, 7, 10, 12, 8, 12),
            Shapes.join(
            	Block.box(4, 0, 1, 14, 14, 9),
                Shapes.join(
                	Block.box(1, 0, 4, 11, 12, 13),
                	Block.box(7, 0, 6, 15, 7, 14), 
                    BooleanOp.OR
                ), 
                BooleanOp.OR
            ), 
            BooleanOp.OR
        );
    
    
    public ExposedIron(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(KIND, 1));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(KIND);
    }
    
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, net.minecraft.world.phys.shapes.CollisionContext context) {
        int kind = state.getValue(KIND);
        switch (kind) {
            case 1:
                return SHAPE_KIND_1;
            case 2:
                return SHAPE_KIND_2;
            case 3:
                return SHAPE_KIND_3;
            case 4:
                return SHAPE_KIND_4;
            default:
                return Shapes.block();
        }
    }

}
