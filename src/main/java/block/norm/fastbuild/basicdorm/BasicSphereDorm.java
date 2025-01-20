package block.norm.fastbuild.basicdorm;

import block.norm.BlockRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import util.json.BlockJSON;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import block.norm.fastbuild.DormHelper;

/**
 * 球形穹顶 I
 * @author NANMEDA
 * */
public class BasicSphereDorm extends Block {
    public static final String global_name = "basic_sphere_dorm";
    
    private static final BlockState UNBROKEN_GLASS_STATE = BlockRegister.unbrokenglass_BLOCK.get().defaultBlockState();
    private static final BlockState UNBROKEN_CEMENT_STATE = BlockRegister.unbrokencement_BLOCK.get().defaultBlockState();
    private static final BlockState UNBROKEN_FOG_STATE = BlockRegister.unbrokenfog_BLOCK.get().defaultBlockState();
    private static final BlockState AIR_STATE = BlockRegister.A_AIR.get().defaultBlockState();

    public BasicSphereDorm(Properties properties) {
        super(properties);
    }

    @SuppressWarnings("deprecation")
    @Override
    public InteractionResult use(BlockState blockstate, Level level, BlockPos pos, Player player, InteractionHand interactionhand, BlockHitResult blockHitResult) {
        super.use(blockstate, level, pos, player, interactionhand, blockHitResult);
        if (!level.isClientSide()) {
        	if(DormHelper.checkPlaceDormOccupied(level, pos, 12, player)) return InteractionResult.FAIL;
            createGlassSphere(level, pos);
            createCementBase(level, pos);
            
            BlockPos control = DormHelper.fromCenterGetControlBlockPos(level, pos);
            level.setBlockAndUpdate(control, BlockRegister.dormcontrol_BLOCK.get().defaultBlockState());
            
            createJunctionBase(level,pos.below());
        }
        return InteractionResult.SUCCESS;
    }

    private void createJunctionBase(Level level, BlockPos pos) {
    	block.norm.fastbuild.JunctionHelper.BirthJuntionBase(level,pos.offset(0,0,12),6,pos);
    	block.norm.fastbuild.JunctionHelper.BirthJuntionBase(level,pos.offset(0,0,-12),2,pos);
    	block.norm.fastbuild.JunctionHelper.BirthJuntionBase(level,pos.offset(12,0,0),0,pos);
    	block.norm.fastbuild.JunctionHelper.BirthJuntionBase(level,pos.offset(-12,0,0),4,pos);
    	
    	block.norm.fastbuild.JunctionHelper.BirthJuntionBase(level,pos.offset(9,0,9),7,pos);
    	block.norm.fastbuild.JunctionHelper.BirthJuntionBase(level,pos.offset(9,0,-9),1,pos);
    	block.norm.fastbuild.JunctionHelper.BirthJuntionBase(level,pos.offset(-9,0,9),5,pos);
    	block.norm.fastbuild.JunctionHelper.BirthJuntionBase(level,pos.offset(-9,0,-9),3,pos);
    	
	}

	/***
     * 用来生成底座的
     * 一般认为
     * 底座是-2底层 圆盘
     * -1底层圆环，圆环半径 = 2
     * ***/
    private void createCementBase(Level world, BlockPos centerPos) {
    	int radius = 10;
        int radius_outer_sq = 100;
        int radius_inner_sq = 64;
        BlockPos start = centerPos.offset(-radius, -2, -radius);
        BlockPos end = centerPos.offset(0,-2,0);

        for (BlockPos pos : BlockPos.betweenClosed(start, end)) {
            int distSq = (int) pos.distSqr(centerPos);
            if (distSq <= radius_outer_sq) {
                BlockPos posX = new BlockPos(centerPos.getX() + (centerPos.getX() - pos.getX()), pos.getY(), pos.getZ());
                BlockPos posZ = new BlockPos(pos.getX(), pos.getY(), centerPos.getZ() + (centerPos.getZ() - pos.getZ()));
                BlockPos posXZ = new BlockPos(centerPos.getX() + (centerPos.getX() - pos.getX()), pos.getY(), centerPos.getZ() + (centerPos.getZ() - pos.getZ()));

                world.setBlockAndUpdate(posX, UNBROKEN_CEMENT_STATE);
                world.setBlockAndUpdate(posZ, UNBROKEN_CEMENT_STATE);
                world.setBlockAndUpdate(posXZ, UNBROKEN_CEMENT_STATE);
                world.setBlockAndUpdate(pos, UNBROKEN_CEMENT_STATE);
            }
        }
        start = centerPos.offset(-radius, -1, -radius);
        end = centerPos.offset(0,-1,0);

        for (BlockPos pos : BlockPos.betweenClosed(start, end)) {
            int distSq = (int) pos.distSqr(centerPos);
            if (distSq <= radius_outer_sq) {
                BlockPos posX = new BlockPos(centerPos.getX() + (centerPos.getX() - pos.getX()), pos.getY(), pos.getZ());
                BlockPos posZ = new BlockPos(pos.getX(), pos.getY(), centerPos.getZ() + (centerPos.getZ() - pos.getZ()));
                BlockPos posXZ = new BlockPos(centerPos.getX() + (centerPos.getX() - pos.getX()), pos.getY(), centerPos.getZ() + (centerPos.getZ() - pos.getZ()));
            	if(distSq >= radius_inner_sq) {
                world.setBlockAndUpdate(posX, UNBROKEN_CEMENT_STATE);
                world.setBlockAndUpdate(posZ, UNBROKEN_CEMENT_STATE);
                world.setBlockAndUpdate(posXZ, UNBROKEN_CEMENT_STATE);
                world.setBlockAndUpdate(pos, UNBROKEN_CEMENT_STATE);
            	}else {
                    world.setBlockAndUpdate(posX, AIR_STATE);
                    world.setBlockAndUpdate(posZ, AIR_STATE);
                    world.setBlockAndUpdate(posXZ, AIR_STATE);
                    world.setBlockAndUpdate(pos, AIR_STATE);
            	}
            }
        }
	}

    /***
     * 生成圆壳用的
     * 半径10生成的球的上半部分
     * 十字形基本骨架
     * 检测 1/4 另外 3/4 是 copy的，节省计算量
     * ***/
	private void createGlassSphere(Level world, BlockPos centerPos) {
        int radius = 10;
        int radius_outer_sq = 100;
        int radius_inner_sq = 81;

        BlockPos start = centerPos.offset(-radius, 0, -radius);
        BlockPos end = centerPos.offset(0, radius, 0);
        
        int dx = 0;
        int dz = 0;
        BlockPos posX;
        BlockPos posZ;
        BlockPos posXZ;
        for (BlockPos pos : BlockPos.betweenClosed(start, end)) {
            if (Math.abs(pos.getX() - centerPos.getX()) + Math.abs(pos.getY() - centerPos.getY()) + Math.abs(pos.getZ() - centerPos.getZ()) < 9) {
        		dx = centerPos.getX() - pos.getX();
        		dz = centerPos.getZ() - pos.getZ();
            	posX = new BlockPos(centerPos.getX() + dx, pos.getY(), pos.getZ());
                posZ = new BlockPos(pos.getX(), pos.getY(), centerPos.getZ() + dz);
                posXZ = new BlockPos(centerPos.getX() + dx, pos.getY(), centerPos.getZ() + dz);
                world.setBlockAndUpdate(posX, AIR_STATE);
                world.setBlockAndUpdate(posZ, AIR_STATE);
                world.setBlockAndUpdate(posXZ, AIR_STATE);
                world.setBlockAndUpdate(pos, AIR_STATE);
            	continue;
            }
            int distSq = (int) pos.distSqr(centerPos);
            if (distSq <= radius_outer_sq) {
        		dx = centerPos.getX() - pos.getX();
        		dz = centerPos.getZ() - pos.getZ();
                posX = new BlockPos(centerPos.getX() + dx, pos.getY(), pos.getZ());
                posZ = new BlockPos(pos.getX(), pos.getY(), centerPos.getZ() + dz);
                posXZ = new BlockPos(centerPos.getX() + dx, pos.getY(), centerPos.getZ() + dz);
            	if(distSq >= radius_inner_sq) {
	                if(Math.abs(dx)<3||Math.abs(dz)<3) {
	                	if(dx==0&&dz==0) {
			                world.setBlockAndUpdate(pos, UNBROKEN_FOG_STATE);
	                	}else {
	                		world.setBlockAndUpdate(posX, UNBROKEN_CEMENT_STATE);
			                world.setBlockAndUpdate(posZ, UNBROKEN_CEMENT_STATE);
			                world.setBlockAndUpdate(posXZ, UNBROKEN_CEMENT_STATE);
			                world.setBlockAndUpdate(pos, UNBROKEN_CEMENT_STATE);
	                	}
	                }else {
		                world.setBlockAndUpdate(posX, UNBROKEN_GLASS_STATE);
		                world.setBlockAndUpdate(posZ, UNBROKEN_GLASS_STATE);
		                world.setBlockAndUpdate(posXZ, UNBROKEN_GLASS_STATE);
		                world.setBlockAndUpdate(pos, UNBROKEN_GLASS_STATE);
	                }
            	}else {
                    world.setBlockAndUpdate(posX, AIR_STATE);
                    world.setBlockAndUpdate(posZ, AIR_STATE);
                    world.setBlockAndUpdate(posXZ, AIR_STATE);
                    world.setBlockAndUpdate(pos, AIR_STATE);
            	}
            }
        }
    }
/*
    private void setBiome(ServerLevel world, BlockPos pos, ResourceKey<Biome> biomeKey) {
    	if(true) {
    		
    	}
        Holder<Biome> holder = world.registryAccess().registryOrThrow(Registries.BIOME).getHolderOrThrow(biomeKey);
        ChunkAccess chunk = world.getChunk(pos);

        BoundingBox boundingBox = BoundingBox.fromCorners(pos, pos);
        MutableInt mutableInt = new MutableInt(0);
        chunk.fillBiomesFromNoise(makeResolver(mutableInt, chunk, boundingBox, holder), world.getChunkSource().randomState().sampler());
        chunk.setUnsaved(true);
    }

    private BiomeResolver makeResolver(MutableInt mutableInt, ChunkAccess chunk, BoundingBox boundingBox, Holder<Biome> holder) {
        return (quartX, quartY, quartZ, biomeNoise) -> {
            int blockX = QuartPos.toBlock(quartX);
            int blockY = QuartPos.toBlock(quartY);
            int blockZ = QuartPos.toBlock(quartZ);
            Holder<Biome> currentHolder = chunk.getNoiseBiome(quartX, quartY, quartZ);
            if (boundingBox.isInside(blockX, blockY, blockZ)) {
                mutableInt.increment();
                return holder;
            } else {
                return currentHolder;
            }
        };
    }
*/
    static {
        BlockJSON.GenModelsJSONBasic(global_name);
        BlockJSON.GenBlockStateJSONBasic(global_name);
        BlockJSON.GenItemJSONBasic(global_name);
        BlockJSON.GenLootTableJSONBasic(global_name);
    }
}
