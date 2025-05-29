package com.main.maring.worldgen.feature;

import com.mojang.serialization.Codec;

import com.main.maring.block.norm.BlockBasic;
import com.main.maring.block.norm.BlockRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

/**
 * 尚未完成
 * */
public class BacterialBush extends Feature<NoneFeatureConfiguration> {

    private static final Block MAR_SURFACE = BlockRegister.COMMON_BLOCKS[BlockBasic.getIdFromName("mar_surface")].get();
    private static final Block METHANE_VENTS = BlockRegister.COMMON_BLOCKS[BlockBasic.getIdFromName("methane_vents")].get();
    
	public BacterialBush(Codec<NoneFeatureConfiguration> p_65786_) {
		super(p_65786_);
	}
	
	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
	    BlockPos origin = context.origin();
	    RandomSource random = context.random();
	    WorldGenLevel world = context.level();

	    BlockPos solidBlocks = findSolidBlock(world, origin);

	    if (!world.getBlockState(solidBlocks).is(MAR_SURFACE)) {
	        return false;
	    }

	    int totalLength = 3+random.nextInt(2);
	    
	    BlockPos pos = solidBlocks.offset(0, totalLength, 0);
	    world.setBlock(pos, METHANE_VENTS.defaultBlockState(), 2);
    	pos = pos.below();
    	int length = 1;
	    while(length<=totalLength) {
	    	
	    	length++;
	    }
	    
	    return true;
	}

	
	private BlockPos findSolidBlock(WorldGenLevel worldgenlevel, BlockPos blockpos) {
	    while (worldgenlevel.isEmptyBlock(blockpos) && blockpos.getY() > worldgenlevel.getMinBuildHeight() + 2) {
	        blockpos = blockpos.below();
	    }
	    return blockpos;
	}

}
