package com.main.maring.block.norm;

import com.main.maring.Maring;
import com.main.maring.tags.register.TagkeyJson;
import com.main.maring.util.mar.EnvironmentData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.lighting.LightEngine;

public class BlockMarGrass extends Block {

    public static String global_name = "mar_grass";
    private static EnvironmentData environmentData = null;
    public static final TagKey<Block> MAR_GRASS_SPREADABLE = BlockTags.create(
            new ResourceLocation(Maring.MODID, "mar_grass_spread")
    );
    private static ResourceKey<Level> marKey = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(Maring.MODID, "maringmar"));

    public BlockMarGrass(Properties p_49795_) {
        super(p_49795_);
    }

    private static boolean canSurviveAsMarsGrass(ServerLevel level, BlockPos pos) {
        BlockPos above = pos.above();
        BlockState aboveState = level.getBlockState(above);

        if (aboveState.is(Blocks.SNOW) && aboveState.getValue(SnowLayerBlock.LAYERS) == 1) {
            return true;
        }

        if (aboveState.getFluidState().getAmount() == 8) {
            return false;
        }

        int blockLightBlocked = LightEngine.getLightBlockInto(
                level, level.getBlockState(pos), pos,
                aboveState, above, Direction.UP,
                aboveState.getLightBlock(level, above)
        );

        return blockLightBlocked < level.getMaxLightLevel();
    }


    private static boolean canSpreadToDirt(ServerLevel level, BlockPos pos) {
        BlockPos above = pos.above();

        return canSurviveAsMarsGrass(level, pos)
                && !level.getFluidState(above).is(FluidTags.WATER);
    }


    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (environmentData == null) {
            environmentData = EnvironmentData.get(level);
        }

        if ((level.dimension().equals(marKey) && !environmentData.suitableMOSS())
                ||
                !canSurviveAsMarsGrass(level,pos)) {
            if (!level.isAreaLoaded(pos, 1)) return;
            level.setBlockAndUpdate(pos, BlockRegister.COMMON_BLOCKS[0].get().defaultBlockState());
            return;
        }

        if (!level.dimension().equals(marKey)
                ||
                (environmentData.suitablePLANTL() && level.getMaxLocalRawBrightness(pos.above()) >= 9)) {
            if (!level.isAreaLoaded(pos, 3)) return;

            BlockState newState = this.defaultBlockState();

            for (int i = 0; i < 2; ++i) { // 最多尝试2次传播
                BlockPos targetPos = pos.offset(
                        random.nextInt(3) - 1,  // X: [-1, 1]
                        random.nextInt(5) - 3,  // Y: [-3, 1]
                        random.nextInt(3) - 1   // Z: [-1, 1]
                );

                if (level.getBlockState(targetPos).is(MAR_GRASS_SPREADABLE)
                        && canSpreadToDirt(level, targetPos)) {
                    level.setBlockAndUpdate(targetPos, newState);
                }
            }
        }
    }

}
