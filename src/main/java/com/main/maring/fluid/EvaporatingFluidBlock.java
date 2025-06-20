package com.main.maring.fluid;

import com.main.maring.block.norm.BlockRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;

public class EvaporatingFluidBlock extends LiquidBlock {
    private final int lifeTick;

    public EvaporatingFluidBlock(FlowingFluid fluid, Properties properties, int lifeTick) {
        super(fluid, properties);
        this.lifeTick = lifeTick;
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onPlace(state, level, pos, oldState, isMoving);
        level.scheduleTick(pos, this, lifeTick);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, net.minecraft.util.RandomSource random) {
        if (!level.isClientSide) {
            int aAirCount = 0;

            // 遍历 3x3x3 立方体区域（跳过自身）
            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    for (int dz = -1; dz <= 1; dz++) {
                        if (dx == 0 && dy == 0 && dz == 0) continue; // 跳过自身

                        BlockPos checkPos = pos.offset(dx, dy, dz);
                        BlockState neighborState = level.getBlockState(checkPos);

                        if (neighborState.is(BlockRegister.A_AIR.get())) {
                            level.setBlock(pos, BlockRegister.A_AIR.get().defaultBlockState(), 3);
                            return;
                        }
                    }
                }
            }
            level.setBlock(pos, net.minecraft.world.level.block.Blocks.AIR.defaultBlockState(), 3);

        }
    }
}
