package com.main.maring.block.norm;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import com.main.maring.util.json.BlockJSON;

public class BlockAAIR extends Block {

    public BlockAAIR(Properties properties) {
        super(properties);
    }

    @SuppressWarnings("deprecation")
	@Override
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        super.neighborChanged(state, world, pos, block, fromPos, isMoving);
        propagateAAir(world, fromPos);
        
    }

    private void propagateAAir(Level world, BlockPos pos) {
        BlockPos neighborPos = pos;
        BlockState neighborState = world.getBlockState(neighborPos);
        if (neighborState.is(Blocks.AIR)) {
            world.setBlockAndUpdate(neighborPos, this.defaultBlockState());
        }
    }
    
    
    @Override
    public RenderShape getRenderShape(BlockState p_48758_) {
       return RenderShape.INVISIBLE;
    }
    
    
    @Override
    public VoxelShape getShape(BlockState p_48760_, BlockGetter p_48761_, BlockPos p_48762_, CollisionContext p_48763_) {
       return Shapes.empty();
    }

    static {
        BlockJSON.GenModelsJSONBasic("a_air");
        BlockJSON.GenBlockStateJSONBasic("a_air");
    }
}
