package com.main.maring.worldgen.feature;

import java.util.ArrayList;

import com.mojang.serialization.Codec;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

/**
 * cheese里的闪光矿
 * @author NANMEDA
 * */
public class CheeseGlitter extends Feature<NoneFeatureConfiguration> {

	public CheeseGlitter(Codec<NoneFeatureConfiguration> p_65786_) {
		super(p_65786_);
		
	}
	
	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
	    BlockPos origin = context.origin();
	    RandomSource random = context.random();
	    WorldGenLevel world = context.level();

	    ArrayList<BlockPos> solidBlocks = findSolidBlocks(world, origin);

        if (solidBlocks.isEmpty()) {
            return false;
        }
        for(BlockPos pos : solidBlocks) {
        	genOre(world, pos, random);
        }
	    return true;
	}

	private void genOre(WorldGenLevel level, BlockPos pos, RandomSource random) {
	    int choice = random.nextInt(1, 13); // Generates a value between 1 (inclusive) and 7 (exclusive).
	    switch (choice) {
	        case 1,7 -> level.setBlock(pos, FeatureHelper.CY.defaultBlockState(), 3);
	        case 2 -> level.setBlock(pos, FeatureHelper.CG.defaultBlockState(), 3);
	        case 3 -> level.setBlock(pos, FeatureHelper.CB.defaultBlockState(), 3);
	        case 4,8 -> level.setBlock(pos, FeatureHelper.CR.defaultBlockState(), 3);
	        case 5,9 -> level.setBlock(pos, FeatureHelper.CO.defaultBlockState(), 3);
	        case 6 -> level.setBlock(pos, FeatureHelper.CP.defaultBlockState(), 3);
	        default -> {
	            // Do nothing
	        }
	    }
	}


	private ArrayList<BlockPos> findSolidBlocks(WorldGenLevel world, BlockPos pos) {
	    BlockPos mutable = new BlockPos(pos.getX(),32,pos.getZ()).mutable();
	    ArrayList<BlockPos> list = new ArrayList<>();
	    
	    while (mutable.getY() > -52) {
	        if (world.getBlockState(mutable).is(FeatureHelper.HARD)&&(world.isEmptyBlock(mutable.above())||world.isEmptyBlock(mutable.below()))) {
	        	list.add(mutable);
	        }
	        mutable = mutable.below();
	    }
	    
	    return list;
	}

}
