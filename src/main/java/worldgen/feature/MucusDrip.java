package worldgen.feature;

import java.util.List;
import java.util.ArrayList;

import com.mojang.serialization.Codec;

import block.norm.BlockBasic;
import block.norm.BlockRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class MucusDrip extends Feature<NoneFeatureConfiguration> {

    private static final Block MOIST_MUCUS = BlockRegister.COMMON_BLOCKS[BlockBasic.getIdFromName("moist_mucus")].get();
    private static final Block DRY_MUCUS = BlockRegister.COMMON_BLOCKS[BlockBasic.getIdFromName("dry_mucus")].get();
    
	public MucusDrip(Codec<NoneFeatureConfiguration> p_65786_) {
		super(p_65786_);
	}
	
	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
	    BlockPos origin = context.origin();
	    RandomSource random = context.random();
	    WorldGenLevel world = context.level();

	    List<BlockPos> solidBlocks = findSolidBlocks(world, origin);

        if (solidBlocks.isEmpty()) {
            return false;
        }
        int length = 1;
        for (BlockPos position : solidBlocks) {
        	BlockPos pos = position.immutable();
        	length = 1;
        	while(world.isEmptyBlock(pos)&&pos.getY()>-10) {
        		setBlockRandomByLength(world, pos, length, random);
        		if(random.nextInt(8)==0) {
        			setAround(world, pos, length, random);
        		}
        		length++;
        		pos = pos.below();
        	}
        }	   
	    return true;
	}

	private void setBlockRandomByLength(WorldGenLevel world,BlockPos pos,int length,RandomSource random) {
		if(!world.isEmptyBlock(pos)) return;
		if(random.nextInt(length)<8) {
			world.setBlock(pos, MOIST_MUCUS.defaultBlockState(), 2);
		}else {
			world.setBlock(pos, DRY_MUCUS.defaultBlockState(), 2);
		}
	}
	
	private void setAround(WorldGenLevel world,BlockPos pos,int length,RandomSource random) {
		int direction = random.nextInt(4);
        switch (direction) {
            case 0:
                setBlockRandomByLength(world, pos.east(), length, random);
                break;
            case 1:
                setBlockRandomByLength(world, pos.west(), length, random);
                break;
            case 2:
                setBlockRandomByLength(world, pos.north(), length, random);
                break;
            case 3:
                setBlockRandomByLength(world, pos.south(), length, random);
                break;
            default:
                break;
        }
	}
	
	private List<BlockPos> findSolidBlocks(WorldGenLevel world, BlockPos pos) {
	    List<BlockPos> positions = new ArrayList<>();
	    BlockPos mutable = new BlockPos(pos.getX(),0,pos.getZ());

	    while (mutable.getY() < 256) {
	        if (world.getBlockState(mutable.above()).is(DRY_MUCUS)&&world.isEmptyBlock(mutable)) {
	            positions.add(mutable);
	            mutable = mutable.above();
	        }
	        mutable = mutable.above();
	    }
	    
	    return positions; // 返回所有找到的玻璃方块的位置
	}

}
