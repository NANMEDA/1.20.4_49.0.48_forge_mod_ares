package worldgen.feature;

import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

/**
 * cheese里的小池子
 * @author NANMEDA
 * */
public class CheesePool extends Feature<NoneFeatureConfiguration> {
 

	public CheesePool(Codec<NoneFeatureConfiguration> p_65786_) {
		super(p_65786_);
	}
	
	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
	    BlockPos origin = context.origin();
	    RandomSource random = context.random();
	    WorldGenLevel world = context.level();

	    BlockPos solidBlocks = findSolidBlocks(world, origin);

        if (solidBlocks.equals( BlockPos.ZERO)) {
            return false;
        }
        
        int depth = random.nextInt(1,4);
        int vast = random.nextInt(5,9);
        genPool(depth,vast,world, solidBlocks, random);
	    return true;
	}

	private void genPool(int depth,int vast,WorldGenLevel level,BlockPos pos,RandomSource random) {

	    for (int y = 0; y < depth; y++) {
	        int currentRadius = (y == 0) ? vast : vast / 2; // 第一层用 vast，后续用 vast/2

	        // 中心生成当前半径的圆形水体
	        generateCircle(level, pos.below(y), currentRadius, FeatureHelper.SOFT);

	        // 周边随机生成 vast / 2 半径的水体
	        if (y < depth - 1) { // 最后一层不生成周边部分
	            for (int dx = -currentRadius; dx <= currentRadius; dx += currentRadius) {
	                for (int dz = -currentRadius; dz <= currentRadius; dz += currentRadius) {
	                    if (random.nextBoolean()) { // 随机决定是否生成
	                        BlockPos randomPos = pos.offset(dx, -y, dz);
	                        if(!level.isEmptyBlock(pos))  generateCircle(level, randomPos, currentRadius / 2, FeatureHelper.SOFT);
	                    }
	                }
	            }
	        }
	    }
	}

	private void generateCircle(WorldGenLevel level, BlockPos center, int radius, Block water) {
	    for (int dx = -radius; dx <= radius; dx++) {
	        for (int dz = -radius; dz <= radius; dz++) {
	            if (dx * dx + dz * dz <= radius * radius) { // 确定圆形范围
	                BlockPos blockPos = center.offset(dx, 0, dz);
	                if(!level.isEmptyBlock(blockPos))  level.setBlock(blockPos, water.defaultBlockState(), 2); // 放置水块
	            }
	        }
	    }
	}

	private BlockPos findSolidBlocks(WorldGenLevel world, BlockPos pos) {
	    BlockPos mutable = new BlockPos(pos.getX(),64,pos.getZ()).mutable();

	    while (mutable.getY() > -52) {
	        if (world.getBlockState(mutable).is(FeatureHelper.HARD)&&world.isEmptyBlock(mutable.above())) {
	        	return mutable;
	        }
	        mutable = mutable.below();
	    }
	    
	    return BlockPos.ZERO;
	}

}
