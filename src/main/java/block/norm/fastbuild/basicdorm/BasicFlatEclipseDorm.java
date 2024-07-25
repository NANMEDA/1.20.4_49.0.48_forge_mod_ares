package block.norm.fastbuild.basicdorm;

import javax.annotation.Nullable;

import block.norm.BlockBasic;
import block.norm.BlockJSON;
import block.norm.BlockRegister;
import block.norm.fastbuild.DormHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

/**
 * 扁椭球形穹顶 I
 * @author NANMEDA
 * */
public class BasicFlatEclipseDorm extends Block {
    public static final String global_name = "basic_flat_eclipse_dorm";
    
    private static final BlockState UNBROKEN_GLASS_STATE = BlockRegister.unbrokenglass_BLOCK.get().defaultBlockState();
    private static final BlockState UNBROKEN_CEMENT_STATE = BlockRegister.unbrokencement_BLOCK.get().defaultBlockState();
    private static final BlockState UNBROKEN_FOG_STATE = BlockRegister.unbrokenfog_BLOCK.get().defaultBlockState();
    private static final BlockState AIR_STATE = BlockRegister.A_AIR.get().defaultBlockState();

    public BasicFlatEclipseDorm(Properties properties) {
        super(properties);
    }

    @SuppressWarnings("deprecation")
    @Override
    public InteractionResult use(BlockState blockstate, Level level, BlockPos pos, Player player, InteractionHand interactionhand, BlockHitResult blockHitResult) {
        super.use(blockstate, level, pos, player, interactionhand, blockHitResult);
        boolean rotate = true;
        if (!level.isClientSide()) {
        	Direction direction = blockstate.getValue(BlockStateProperties.FACING);
    		switch (direction) {
    		    case NORTH:
    		    case SOUTH:
    			default:
    				rotate = false;
    			break;
    		}
    		if(rotate){
    			if(DormHelper.checkPlaceDormOccupied(level, pos, new Vec3i(-15, -2, -10),new Vec3i(15, 7, 10), player)) return InteractionResult.FAIL;
    		}else {
    			if(DormHelper.checkPlaceDormOccupied(level, pos, new Vec3i(-10, -2, -15),new Vec3i(10, 7, 15), player)) return InteractionResult.FAIL;
    		}
            createGlassSphere(level, pos,rotate);
            createCementBase(level, pos,rotate);
            createJunctionBase(level,pos.below(),rotate);
        }
        return InteractionResult.SUCCESS;
    }
    
    private void createJunctionBase(Level level, BlockPos pos,boolean rotate) {
    	if(rotate) {
	    	block.norm.fastbuild.JunctionHelper.BirthJuntionBase(level,pos.offset(0,0,12),6);
	    	block.norm.fastbuild.JunctionHelper.BirthJuntionBase(level,pos.offset(0,0,-12),2);
	    	block.norm.fastbuild.JunctionHelper.BirthJuntionBase(level,pos.offset(17,0,0),0);
	    	block.norm.fastbuild.JunctionHelper.BirthJuntionBase(level,pos.offset(-17,0,0),4);
    	}else {
        	block.norm.fastbuild.JunctionHelper.BirthJuntionBase(level,pos.offset(0,0,17),6);
        	block.norm.fastbuild.JunctionHelper.BirthJuntionBase(level,pos.offset(0,0,-17),2);
        	block.norm.fastbuild.JunctionHelper.BirthJuntionBase(level,pos.offset(12,0,0),0);
        	block.norm.fastbuild.JunctionHelper.BirthJuntionBase(level,pos.offset(-12,0,0),4);
    	}
    	
	}

    /***
     * 用来生成底座的
     * 一般认为
     * 底座是-2底层 圆盘
     * -1底层圆环，圆环半径 = 2
     * ***/
    private void createCementBase(Level world, BlockPos centerPos, Boolean rotate) {
    	int x_r,z_r,radiusX,radiusZ;
    	if(rotate) {
    		x_r = 9;
        	z_r = 4;
        	radiusX = 15;
        	radiusZ = 10;
    	}else {
    		x_r = 4;
        	z_r = 9;
        	radiusX = 10;
        	radiusZ = 15;
    	}
    	
        BlockPos start = centerPos.offset(-radiusX, -2, -radiusZ);
        BlockPos end = centerPos.offset(0, -2, 0);

        for (BlockPos pos : BlockPos.betweenClosed(start, end)) {
            int dx = pos.getX() - centerPos.getX();
            int dz = pos.getZ() - centerPos.getZ();
            if ((dx * dx) / x_r + (dz * dz) / z_r <= 25) {
                BlockPos posX = new BlockPos(centerPos.getX() - dx, pos.getY(), pos.getZ());
                BlockPos posZ = new BlockPos(pos.getX(), pos.getY(), centerPos.getZ() - dz);
                BlockPos posXZ = new BlockPos(centerPos.getX() - dx, pos.getY(), centerPos.getZ() - dz);

                world.setBlockAndUpdate(posX, UNBROKEN_CEMENT_STATE);
                world.setBlockAndUpdate(posZ, UNBROKEN_CEMENT_STATE);
                world.setBlockAndUpdate(posXZ, UNBROKEN_CEMENT_STATE);
                world.setBlockAndUpdate(pos, UNBROKEN_CEMENT_STATE);
            }
        }

        start = centerPos.offset(-radiusX, -1, -radiusZ);
        end = centerPos.offset(0, -1, 0);

        for (BlockPos pos : BlockPos.betweenClosed(start, end)) {
            int dx = pos.getX() - centerPos.getX();
            int dz = pos.getZ() - centerPos.getZ();
            if ((dx * dx) / x_r + (dz * dz) / z_r <= 25) {
                BlockPos posX = new BlockPos(centerPos.getX() - dx, pos.getY(), pos.getZ());
                BlockPos posZ = new BlockPos(pos.getX(), pos.getY(), centerPos.getZ() - dz);
                BlockPos posXZ = new BlockPos(centerPos.getX() - dx, pos.getY(), centerPos.getZ() - dz);
                if ((dx * dx) / x_r + (dz * dz) / z_r >= 16) {
                    world.setBlockAndUpdate(posX, UNBROKEN_CEMENT_STATE);
                    world.setBlockAndUpdate(posZ, UNBROKEN_CEMENT_STATE);
                    world.setBlockAndUpdate(posXZ, UNBROKEN_CEMENT_STATE);
                    world.setBlockAndUpdate(pos, UNBROKEN_CEMENT_STATE);
                } else {
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
     * 椭圆球壳，上半部分
     * 十字形基本骨架
     * 检测 1/4 另外 3/4 是 copy的，节省计算量
     * ***/
    private void createGlassSphere(Level world, BlockPos centerPos,Boolean rotate) {
    	int x_r,z_r,radiusX,radiusZ;
    	if(rotate) {
    		x_r = 9;
        	z_r = 4;
        	radiusX = 15;
        	radiusZ = 10;
    	}else {
    		x_r = 4;
        	z_r = 9;
        	radiusX = 10;
        	radiusZ = 15;
    	}
    	
    	int y_r = 2;
        int radiusY = 7;

        BlockPos start = centerPos.offset(-radiusX, 0, -radiusZ);
        BlockPos end = centerPos.offset(0, radiusY, 0);

        for (BlockPos pos : BlockPos.betweenClosed(start, end)) {
            int dx = pos.getX() - centerPos.getX();
            int dy = pos.getY() - centerPos.getY();
            int dz = pos.getZ() - centerPos.getZ();
            
            // Check if the point is inside the ellipsoid
            double equation = (dx * dx) / x_r + (dy * dy) / y_r + (dz * dz) / z_r;
            if (equation <= 25) {
                BlockPos posX = new BlockPos(centerPos.getX() - dx, pos.getY(), pos.getZ());
                BlockPos posZ = new BlockPos(pos.getX(), pos.getY(), centerPos.getZ() - dz);
                BlockPos posXZ = new BlockPos(centerPos.getX() - dx, pos.getY(), centerPos.getZ() - dz);
                if (equation >= 19.75) {
                    if (Math.abs(dx) < 3 || Math.abs(dz) < 3) {
                        if (dx == 0 && dz == 0) {
                            world.setBlockAndUpdate(pos, UNBROKEN_FOG_STATE);
                        } else {
                            world.setBlockAndUpdate(posX, UNBROKEN_CEMENT_STATE);
                            world.setBlockAndUpdate(posZ, UNBROKEN_CEMENT_STATE);
                            world.setBlockAndUpdate(posXZ, UNBROKEN_CEMENT_STATE);
                            world.setBlockAndUpdate(pos, UNBROKEN_CEMENT_STATE);
                        }
                    } else {
                        world.setBlockAndUpdate(posX, UNBROKEN_GLASS_STATE);
                        world.setBlockAndUpdate(posZ, UNBROKEN_GLASS_STATE);
                        world.setBlockAndUpdate(posXZ, UNBROKEN_GLASS_STATE);
                        world.setBlockAndUpdate(pos, UNBROKEN_GLASS_STATE);
                    }
                } else {
                    world.setBlockAndUpdate(posX, AIR_STATE);
                    world.setBlockAndUpdate(posZ, AIR_STATE);
                    world.setBlockAndUpdate(posXZ, AIR_STATE);
                    world.setBlockAndUpdate(pos, AIR_STATE);
                }
            }
        }
    }
    
	@Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        if (context.getClickedFace() == Direction.DOWN||context.getClickedFace() == Direction.UP) {
        	Direction playerDirection = context.getNearestLookingDirection().getOpposite();
        	if(playerDirection==Direction.DOWN||playerDirection==Direction.UP) {
        		playerDirection = Direction.NORTH; 
        	} 
        	return this.defaultBlockState().setValue(BlockStateProperties.FACING, playerDirection);
        } else {
            return null;
        }
    }
    
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.FACING);
    }

    static {
        BlockJSON.GenModelsJSONBasic(global_name);
        BlockJSON.GenBlockStateJSONBasic(global_name);
        BlockJSON.GenItemJSONBasic(global_name);
        BlockJSON.GenLootTableJSONBasic(global_name);
    }
}
