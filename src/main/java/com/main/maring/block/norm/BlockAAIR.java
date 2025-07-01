package com.main.maring.block.norm;

import com.main.maring.util.mar.AAirManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
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

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlockAAIR extends Block {

    public static final EnumProperty<AAirState> STATE = EnumProperty.create("state", AAirState.class);

    public BlockAAIR(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(STATE, AAirState.STILL));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(STATE);
    }

    public boolean activate(ServerLevel world, BlockPos pos) {
        boolean found = false;
        AAirManager manager = AAirManager.getOrCreate(world);

        for (Direction dir : Direction.values()) {
            BlockPos neighborPos = pos.relative(dir);
            if (world.getBlockState(neighborPos).getBlock()==Blocks.AIR) {
                world.setBlockAndUpdate(neighborPos, this.defaultBlockState().setValue(STATE, AAirState.ACTIVATE));
                manager.add(neighborPos,AAirState.ACTIVATE);
                found = true;
            }
        }
        if (!found) {
            BlockState current = world.getBlockState(pos);
            world.setBlockAndUpdate(pos, current.setValue(STATE, AAirState.STILL));
            manager.remove(pos,AAirState.ACTIVATE);
        }

        return found;
    }


    public boolean deactivate(ServerLevel world, BlockPos pos) {
        boolean found = false;
        AAirManager manager = AAirManager.getOrCreate(world);

        for (Direction dir : Direction.values()) {
            BlockPos neighborPos = pos.relative(dir);
            BlockState neighborState = world.getBlockState(neighborPos);
            //is A_AIR and is NOT DEACTIVATE(or it will always find and exists forever)uwu
            if (neighborState.getBlock() == BlockRegister.A_AIR.get()&&neighborState.getValue(STATE)!=AAirState.DEACTIVATE) {
                if(neighborState.getValue(STATE)==AAirState.ACTIVATE){
                    manager.remove(neighborPos,AAirState.ACTIVATE);
                }
                world.setBlock(neighborPos, neighborState.setValue(STATE, AAirState.DEACTIVATE), 3);
                manager.add(neighborPos,AAirState.DEACTIVATE);
                found = true;
            }
        }
        //as if dispear immediately , some bugs found in used
        if(!found) {
            world.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
            manager.remove(pos, AAirState.DEACTIVATE);
        }
        return found;
    }


    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public VoxelShape getShape(BlockState p_48760_, BlockGetter p_48761_, BlockPos p_48762_, CollisionContext p_48763_) {
        return Shapes.empty();
    }


    @SuppressWarnings("deprecation")
    @Override
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        super.neighborChanged(state, world, pos, block, fromPos, isMoving);
        if(world.isClientSide()) return;
        if(state.getValue(STATE)==AAirState.DEACTIVATE) return;
        if(world instanceof ServerLevel level) {
            if(level.getBlockState(fromPos).getBlock()==Blocks.AIR) {
                AAirManager manager = AAirManager.getOrCreate(level);
                level.setBlockAndUpdate(pos,this.defaultBlockState().setValue(STATE, AAirState.ACTIVATE));
                manager.add(pos,AAirState.ACTIVATE);
            }
        }
    }

}
