package com.main.maring.worldgen.feature;

import com.mojang.serialization.Codec;

import com.main.maring.block.norm.BlockBasic;
import com.main.maring.block.norm.BlockRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

/**
 * 陨石坑
 * 替换成了火星表面
 * */
public class MeteoriteCrater extends Feature<NoneFeatureConfiguration> {

    private static final Block BAKED = BlockRegister.COMMON_BLOCKS[BlockBasic.getIdFromName("mar_baked_surface")].get();
    
	public MeteoriteCrater(Codec<NoneFeatureConfiguration> p_65786_) {
		super(p_65786_);
	}
	
	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> p_159882_) {
	    BlockPos blockpos = p_159882_.origin();
	    RandomSource randomsource = p_159882_.random();
	    WorldGenLevel worldgenlevel = p_159882_.level();

	    int radius = 7 + randomsource.nextInt(5);
		deleteHole(worldgenlevel, blockpos, randomsource, radius);
	    genBaked(worldgenlevel, blockpos, randomsource, radius);
	    if(randomsource.nextBoolean()) {
	    	genMagma(worldgenlevel, blockpos, randomsource, radius);
	    } 
	    
	    return true;
	}

	private void deleteHole(WorldGenLevel worldgenlevel, BlockPos blockpos, RandomSource randomsource, int radius) {
		BlockPos start = blockpos.offset(-radius, -1, -radius);
		BlockPos end = blockpos.offset(0, -1, 0);
		int radius_s = radius*radius;
		for(BlockPos pos : BlockPos.betweenClosed(start,end)) {
            int dx = pos.getX() - blockpos.getX();
            int dz = pos.getZ() - blockpos.getZ();
            if ((dx * dx) + (dz * dz) <= radius_s) {
                BlockPos posX = new BlockPos(blockpos.getX() - dx, pos.getY(), pos.getZ());
                BlockPos posZ = new BlockPos(pos.getX(), pos.getY(), blockpos.getZ() - dz);
                BlockPos posXZ = new BlockPos(blockpos.getX() - dx, pos.getY(), blockpos.getZ() - dz);
                worldgenlevel.setBlock(posX, Blocks.AIR.defaultBlockState(), 2);
                worldgenlevel.setBlock(posZ, Blocks.AIR.defaultBlockState(), 2);
                worldgenlevel.setBlock(posXZ, Blocks.AIR.defaultBlockState(), 2);
                worldgenlevel.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
                worldgenlevel.setBlock(posX.offset(0,1,0), Blocks.AIR.defaultBlockState(), 2);
                worldgenlevel.setBlock(posZ.offset(0,1,0), Blocks.AIR.defaultBlockState(), 2);
                worldgenlevel.setBlock(posXZ.offset(0,1,0), Blocks.AIR.defaultBlockState(), 2);
                worldgenlevel.setBlock(pos.offset(0,1,0), Blocks.AIR.defaultBlockState(), 2);
                worldgenlevel.setBlock(posX.offset(0,2,0), Blocks.AIR.defaultBlockState(), 2);
                worldgenlevel.setBlock(posZ.offset(0,2,0), Blocks.AIR.defaultBlockState(), 2);
                worldgenlevel.setBlock(posXZ.offset(0,2,0), Blocks.AIR.defaultBlockState(), 2);
                worldgenlevel.setBlock(pos.offset(0,2,0), Blocks.AIR.defaultBlockState(), 2);
            }
		}
		int n_radius_s = (radius-3)*(radius-3);
		start = blockpos.offset(-radius+3, -2, -radius+3);
		end = blockpos.offset(0, -2, 0);
		for(BlockPos pos : BlockPos.betweenClosed(start,end)) {
            int dx = pos.getX() - blockpos.getX();
            int dz = pos.getZ() - blockpos.getZ();
            if ((dx * dx) + (dz * dz) <= n_radius_s) {
                BlockPos posX = new BlockPos(blockpos.getX() - dx, pos.getY(), pos.getZ());
                BlockPos posZ = new BlockPos(pos.getX(), pos.getY(), blockpos.getZ() - dz);
                BlockPos posXZ = new BlockPos(blockpos.getX() - dx, pos.getY(), blockpos.getZ() - dz);
                worldgenlevel.setBlock(posX, Blocks.AIR.defaultBlockState(), 2);
                worldgenlevel.setBlock(posZ, Blocks.AIR.defaultBlockState(), 2);
                worldgenlevel.setBlock(posXZ, Blocks.AIR.defaultBlockState(), 2);
                worldgenlevel.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
            }
		}
		
	}

	private void genBaked(WorldGenLevel worldgenlevel, BlockPos blockpos, RandomSource randomsource,int radius) {
		BlockPos start = blockpos.offset(-radius, -3, -radius);
		BlockPos end = blockpos.offset(radius, 0, radius);
		for(BlockPos pos : BlockPos.betweenClosed(start,end)) {
			if(randomsource.nextInt(8)<6) continue;
            if(!worldgenlevel.isEmptyBlock(pos)) {
            	worldgenlevel.setBlock(pos, BAKED.defaultBlockState(), 2);
            }
		}
	}
	
	private void genMagma(WorldGenLevel worldgenlevel, BlockPos blockpos, RandomSource randomsource,int radius) {
		BlockPos start = blockpos.offset(-radius+2, -3, -radius+2);
		BlockPos end = blockpos.offset(radius-2, 0, radius-2);
		for(BlockPos pos : BlockPos.betweenClosed(start,end)) {
			if(randomsource.nextInt(8)<6) continue;
            if(!worldgenlevel.isEmptyBlock(pos)) {
            	worldgenlevel.setBlock(pos, Blocks.MAGMA_BLOCK.defaultBlockState(), 2);
            }
		}
	}

}
