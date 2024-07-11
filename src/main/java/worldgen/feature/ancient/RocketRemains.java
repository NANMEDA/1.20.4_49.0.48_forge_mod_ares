package worldgen.feature.ancient;

import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import worldgen.feature.FeatureHelper;

public class RocketRemains extends Feature<NoneFeatureConfiguration> {
    
	private static final BlockState METAL = FeatureHelper.METAL.defaultBlockState();
	private static final BlockState CHEMICAL = FeatureHelper.CHEMICAL.defaultBlockState();
	private static final BlockState ELEC = FeatureHelper.ELEC.defaultBlockState();
	private static final BlockState ELEC_A = FeatureHelper.ELEC_A.defaultBlockState();
	private static final BlockState STRUCT = FeatureHelper.STRUCTURE.defaultBlockState();
	private static final BlockState STONE = FeatureHelper.MAR_DEEP_STONE.defaultBlockState();
	
	public RocketRemains(Codec<NoneFeatureConfiguration> p_65786_) {
		super(p_65786_);
	}
	
	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
	    BlockPos origin = context.origin();
	    RandomSource random = context.random();
	    WorldGenLevel world = context.level();

	    BlockPos pos = findBirthBlocks(world, origin, random);

        if (world.isEmptyBlock(pos)) {
            return false;
        }
        
        birthMainCab(world,pos,random);
        int distance = random.nextInt(6)+4;
        
        int direction = random.nextInt(4);
        switch (direction) {
            case 0:
                pos = pos.offset(4, 0, 0);
                break;
            case 1:
                pos = pos.offset(0, 0, 4);
                break;
            case 2:
                pos = pos.offset(-4, 0, 0);
                break;
            case 3:
                pos = pos.offset(0, 0, -4);
                break;
            default:
                break;
        }
        birthShell(world,pos,random,distance);
   
	    return true;
	}


	private void birthMainCab(WorldGenLevel world, BlockPos pos, RandomSource random) {
		world.setBlock(pos, CHEMICAL, 2);
		setInnerAround(world, pos, random);
	}
	
	
	private void birthShell(WorldGenLevel world, BlockPos pos, RandomSource random, int distance) {
		setOuterAround(world, pos, random);
	}

	private void setInnerAround(WorldGenLevel world, BlockPos pos, RandomSource random) {
	    
	    for (int xOffset = -1; xOffset <= 1; xOffset++) {
	        for (int yOffset = -1; yOffset <= 1; yOffset++) {
	            for (int zOffset = -1; zOffset <= 1; zOffset++) {
	                if (xOffset == 0 && yOffset == 0 && zOffset == 0) {
	                    continue;
	                }
	                BlockPos checkPos = pos.offset(xOffset, yOffset, zOffset);
	                
	                if (world.isEmptyBlock(checkPos)) {
	                    if (random.nextFloat() < 0.5f) {
	                    	setInnerShell(world, checkPos, random);
	                    }
	                }else {
	                	if (random.nextFloat() < 0.8f) {
	                    	setInnerShell(world, checkPos, random);
	                    }
	                }
	            }
	        }
	    }
	}
	
	private void setInnerShell(WorldGenLevel world, BlockPos pos, RandomSource random) {
		float rand = random.nextFloat();
		if (rand < 0.7f) {
			world.setBlock(pos, METAL, 2);
		}else if(rand < 0.9f) {
			world.setBlock(pos, ELEC, 2);
		}else {
			world.setBlock(pos, ELEC_A, 2);
		}
	}

	private void setOuterAround(WorldGenLevel world, BlockPos pos, RandomSource random) {
	    
	    for (int xOffset = -1; xOffset <= 1; xOffset++) {
	        for (int yOffset = -1; yOffset <= 1; yOffset++) {
	            for (int zOffset = -1; zOffset <= 1; zOffset++) {
	                BlockPos checkPos = pos.offset(xOffset, yOffset, zOffset);
	                
	                if (world.isEmptyBlock(checkPos)) {
	                    if (random.nextFloat() < 0.8f) {
	                    	setOuterShell(world, checkPos, random);
	                    }else {
	                    	world.setBlock(pos, STONE, 2);
	                    }
	                }else {
	                	if (random.nextFloat() < 0.3f) {
	                		setOuterShell(world, checkPos, random);
	                    }
	                }
	            }
	        }
	    }
	}
	
	private void setOuterShell(WorldGenLevel world, BlockPos pos, RandomSource random) {
		float rand = random.nextFloat();
		if (rand < 0.8f) {
			world.setBlock(pos, STRUCT, 2);
		}else {
			world.setBlock(pos, METAL, 2);
		}
	}
	
	private BlockPos findBirthBlocks(WorldGenLevel world, BlockPos pos, RandomSource rand) {
	    BlockPos positions = new BlockPos(pos.getX(),-60+rand.nextInt(40),pos.getZ());
	    return positions; // 返回所有找到的玻璃方块的位置
	}

}
