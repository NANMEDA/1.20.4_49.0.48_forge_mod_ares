package worldgen.feature;

import com.mojang.serialization.Codec;

import block.norm.BlockBasic;
import block.norm.BlockRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

/**
 * 修改自冰刺
 * 替换成了火星表面
 * */
public class ErosionStick extends Feature<NoneFeatureConfiguration> {

    private static final Block MAR_SAND = BlockRegister.COMMON_BLOCKS[BlockBasic.getIdFromName("mar_sand")].get();
    
	public ErosionStick(Codec<NoneFeatureConfiguration> p_65786_) {
		super(p_65786_);
	}
	
	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> p_159882_) {
	    BlockPos blockpos = p_159882_.origin();
	    RandomSource randomsource = p_159882_.random();
	    WorldGenLevel worldgenlevel = p_159882_.level();

	    blockpos = findSolidBlock(worldgenlevel, blockpos);

	    if (!worldgenlevel.getBlockState(blockpos).is(MAR_SAND)) {
	        return false;
	    }

	    blockpos = blockpos.above(randomsource.nextInt(4));
	    int height = randomsource.nextInt(4) + 7;
	    int j = height / 4 + randomsource.nextInt(2);
	    if (j > 1 && randomsource.nextInt(30) == 0) {
	        blockpos = blockpos.above(10 + randomsource.nextInt(10));
	    }

	    generatePackedIce(worldgenlevel, blockpos, randomsource, height, j);

	    int k1 = Math.min(Math.max(j - 1, 0), 1);
	    generateAdditionalPackedIce(worldgenlevel, blockpos, k1, randomsource);

	    return true;
	}

	private BlockPos findSolidBlock(WorldGenLevel worldgenlevel, BlockPos blockpos) {
	    while (worldgenlevel.isEmptyBlock(blockpos) && blockpos.getY() > worldgenlevel.getMinBuildHeight() + 2) {
	        blockpos = blockpos.below();
	    }
	    return blockpos;
	}

	private void generatePackedIce(WorldGenLevel worldgenlevel, BlockPos blockpos, RandomSource randomsource, int height, int j) {
	    for (int k = 0; k < height; ++k) {
	        float f = (1.0F - (float) k / (float) height) * (float) j;
	        int l = Mth.ceil(f);

	        for (int i1 = -l; i1 <= l; ++i1) {
	            float f1 = (float) Mth.abs(i1) - 0.25F;

	            for (int j1 = -l; j1 <= l; ++j1) {
	                float f2 = (float) Mth.abs(j1) - 0.25F;
	                if ((i1 == 0 && j1 == 0 || !(f1 * f1 + f2 * f2 > f * f)) &&
	                        (i1 != -l && i1 != l && j1 != -l && j1 != l || !(randomsource.nextFloat() > 0.75F))) {
	                    BlockState blockstate = worldgenlevel.getBlockState(blockpos.offset(i1, k, j1));
	                    if (canPlacePackedMarSand(blockstate)) {
	                    	worldgenlevel.setBlock(blockpos.offset(i1, k, j1), MAR_SAND.defaultBlockState(), 2);
	                    }

	                    if (k != 0 && l > 1) {
	                        blockstate = worldgenlevel.getBlockState(blockpos.offset(i1, -k, j1));
	                        if (canPlacePackedMarSand(blockstate)) {
	                        	worldgenlevel.setBlock( blockpos.offset(i1, -k, j1), MAR_SAND.defaultBlockState(), 2);
	                        }
	                    }
	                }
	            }
	        }
	    }
	}

	private boolean canPlacePackedMarSand(BlockState blockstate) {
	    return blockstate.isAir() || isDirt(blockstate) || blockstate.is(MAR_SAND);
	}

	private void generateAdditionalPackedIce(WorldGenLevel worldgenlevel, BlockPos blockpos, int k1, RandomSource randomsource) {
	    for (int l1 = -k1; l1 <= k1; ++l1) {
	        for (int i2 = -k1; i2 <= k1; ++i2) {
	            BlockPos blockpos1 = blockpos.offset(l1, -1, i2);
	            int j2 = 50;
	            if (Math.abs(l1) == 1 && Math.abs(i2) == 1) {
	                j2 = randomsource.nextInt(5);
	            }

	            while (blockpos1.getY() > 50) {
	                BlockState blockstate1 = worldgenlevel.getBlockState(blockpos1);
	                if (!blockstate1.isAir() && !isDirt(blockstate1) && !blockstate1.is(MAR_SAND)) {
	                    break;
	                }

	                worldgenlevel.setBlock(blockpos1, MAR_SAND.defaultBlockState(), 2);
	                blockpos1 = blockpos1.below();
	                --j2;
	                if (j2 <= 0) {
	                    blockpos1 = blockpos1.below(randomsource.nextInt(5) + 1);
	                    j2 = randomsource.nextInt(5);
	                }
	            }
	        }
	    }
	}

}
