package worldgen.feature;

import block.norm.decoration.Register;
import com.mojang.serialization.Codec;

import block.norm.BlockBasic;
import block.norm.BlockRegister;
import block.norm.decoration.environment.ExposedQuartz;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

/**
 * 石英根
 * */
public class QuartzRoot extends Feature<NoneFeatureConfiguration> {

    private static final BlockState QUARTZ = BlockRegister.COMMON_BLOCKS[BlockBasic.getIdFromName("quartz_stone")].get().defaultBlockState();
    private static final BlockState DEEP_QUARTZ = BlockRegister.COMMON_BLOCKS[BlockBasic.getIdFromName("deep_quartz_stone")].get().defaultBlockState();
    private static final Block EXPOSED_QUARTZ = Register.EXPOSEDQUARTZ_BLOCK.get();
    
    
    private static final Block STONE = BlockRegister.COMMON_BLOCKS[BlockBasic.getIdFromName("mar_stone")].get();
    private static final Block DEEP_STONE = BlockRegister.COMMON_BLOCKS[BlockBasic.getIdFromName("mar_deep_stone")].get();
  
	public QuartzRoot(Codec<NoneFeatureConfiguration> p_65786_) {
		super(p_65786_);
	}
	
	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> p_159882_) {
	    BlockPos blockpos = p_159882_.origin();
	    RandomSource randomsource = p_159882_.random();
	    WorldGenLevel worldgenlevel = p_159882_.level();
	    
		int depth = randomsource.nextInt(5, 10);
	    int thick = depth/2+randomsource.nextInt(1, 5);
	    int[] t = genThickFromDepth(depth,thick);
	    birthColumnFromDepth(t,blockpos.below(),worldgenlevel, randomsource);
	    genQuartzExposed(thick, blockpos, worldgenlevel, randomsource);
	    return true;
	}

	private void genQuartzExposed(int thick, BlockPos blockpos, WorldGenLevel worldgenlevel, RandomSource randomsource) {
	    for (int i = 0; i < 32; i++) {
	        int rx = randomsource.nextInt(13) - 6;
	        int rz = randomsource.nextInt(13) - 6;
	        int ry = randomsource.nextInt(7) - 3;
	        BlockPos offsetPos = blockpos.offset(rx, ry, rz);
	        if (isWithinWorldBounds(offsetPos) && worldgenlevel.isEmptyBlock(offsetPos) && !worldgenlevel.isEmptyBlock(offsetPos.below())) {
	            int kind = randomsource.nextInt(1, 16);
	            worldgenlevel.setBlock(offsetPos, EXPOSED_QUARTZ.defaultBlockState().setValue(ExposedQuartz.KIND, kind), 2);
	        }
	    }
	}

	private void birthColumnFromDepth(int[] t, BlockPos blockPos, WorldGenLevel level,RandomSource random) {
	    for (int i = 0; i < t.length; i++) {
	    	int radius = t[i];
	    	genQuartzFromRadius(radius,blockPos, level, random);
	    	blockPos = blockPos.below();
	    }
	}
	private boolean isWithinWorldBounds(BlockPos pos) {
	    return pos.getY() >= -61;
	}


	private void genQuartzFromRadius(int radius, BlockPos blockPos, WorldGenLevel level, RandomSource random) {
	    BlockPos start = blockPos.offset(-radius, 0, -radius);
	    for (int i = 0; i < 2 * radius + 1; i++) {
	        for (int j = 0; j < 2 * radius + 1; j++) {
	            if (!isWithinWorldBounds(start)) {
	                start = start.offset(1, 0, 0);
	                continue;
	            }
	       
	            if (level.isEmptyBlock(start)) {
	                start = start.offset(1, 0, 0);
	                continue;
	            }
	            
	            int dx = start.getX() - blockPos.getX();
	            int dz = start.getZ() - blockPos.getZ();
	            int dr2 = dx * dx + dz * dz;
	            if (dr2 <= radius) {
	            	int rd = random.nextInt(10);
	                if ( rd*rd >= dr2) {
	                	
	                    if (level.getBlockState(start).is(STONE)) {
	                        level.setBlock(start, QUARTZ, 2);
	                    } else if (level.getBlockState(start).is(DEEP_STONE)) {
	                        level.setBlock(start, DEEP_QUARTZ, 2);
	                    }
	                    /*
	                    if (start.getY()>=0) {
	                        level.setBlock(start, QUARTZ, 2);
	                    } else{
	                        level.setBlock(start, DEEP_QUARTZ, 2);
	                    }*/
	                }
	            }
	            start = start.offset(1, 0, 0);
	        }
	        start = start.offset(-2 * radius - 1, 0, 1);
	    }
	}

	private int[] genThickFromDepth(int depth,int thick) {
	    int[] result = new int[depth];
	    double offest = 0;
	    for(int i =0;i<depth;i++) {
	    	offest = ((double)(i-depth))*Math.PI/((double)depth);
	    	result[i] = (int) (thick * Math.sin(offest) * offest)+1;
	    }
	    return result;
	}

}
