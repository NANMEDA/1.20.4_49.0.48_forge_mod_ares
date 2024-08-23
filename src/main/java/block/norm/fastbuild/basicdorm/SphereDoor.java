package block.norm.fastbuild.basicdorm;

import javax.annotation.Nullable;

import block.norm.BlockRegister;
import block.norm.fastbuild.DormHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import util.json.BlockJSON;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

/**
 * 出口穹顶
 * 有4个方向
 * 比如说，当玩家朝着北方放置该方块
 * 右键后，出口(方块方向)朝向南方（玩家背面）
 * @author NANMEDA
 * */
public class SphereDoor extends Block {
    public static final String global_name = "sphere_door";
    
    private static final BlockState UNBROKEN_GLASS_STATE = BlockRegister.unbrokenglass_BLOCK.get().defaultBlockState();
    private static final BlockState UNBROKEN_CEMENT_STATE = BlockRegister.unbrokencement_BLOCK.get().defaultBlockState();
    private static final BlockState UNBROKEN_FOG_STATE = BlockRegister.unbrokenfog_BLOCK.get().defaultBlockState();
    private static final BlockState AIR_STATE = BlockRegister.A_AIR.get().defaultBlockState();

    public SphereDoor(Properties properties) {
        super(properties);
    }

    @SuppressWarnings("deprecation")
    @Override
    public InteractionResult use(BlockState blockstate, Level level, BlockPos pos, Player player, InteractionHand interactionhand, BlockHitResult blockHitResult) {
        super.use(blockstate, level, pos, player, interactionhand, blockHitResult);
        Direction direction = blockstate.getValue(BlockStateProperties.FACING);
        if (!level.isClientSide()) {
        	if(DormHelper.checkPlaceDormOccupied(level, pos, 6, player)) return InteractionResult.FAIL;
            createGlassSphere(level, pos, direction);
            createCementBase(level, pos, direction);
            createJunctionBase(level,pos.below(),direction);
        }
        return InteractionResult.SUCCESS;
    }
    
    private void createJunctionBase(Level level, BlockPos pos,Direction direction) {
    	switch (direction) {
		case NORTH: 
			block.norm.fastbuild.JunctionHelper.BirthJuntionBase(level,pos.offset(0,0,5),6);
			break;
		case SOUTH: 
			block.norm.fastbuild.JunctionHelper.BirthJuntionBase(level,pos.offset(0,0,-5),2);
			break;
		case EAST: 
			block.norm.fastbuild.JunctionHelper.BirthJuntionBase(level,pos.offset(5,0,0),0);
			break;
		case WEST: 
			block.norm.fastbuild.JunctionHelper.BirthJuntionBase(level,pos.offset(-5,0,0),4);
			break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + direction);
		}
    	
	}

    /***
     * 用来生成底座的
     * 一般认为
     * 底座是-2底层 圆盘
     * -1底层圆环，圆环半径 = 2
     * radius+1是应为发现多出来的只有1格
     * ***/
    private void createCementBase(Level world, BlockPos centerPos,Direction direction) {
    	int radius = 5;
        int radius_outer_sq = 25;
        int radius_inner_sq = 16;
        BlockPos start = centerPos.offset(-radius+1, -2, -radius+1);
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
        if(direction==Direction.NORTH||direction==Direction.SOUTH) {
        	start = centerPos.offset(-radius+1, -1, 0);
            end = centerPos.offset(radius-1,-1,0);
        }else {
        	start = centerPos.offset(0, -1, -radius+1);
            end = centerPos.offset(0,-1, radius-1);
        }
        for (BlockPos pos : BlockPos.betweenClosed(start, end)) {
        	world.setBlockAndUpdate(pos, UNBROKEN_CEMENT_STATE);
        }
	}

    /***
     * 生成圆壳用的
     * 半径5生成的球的上半部分
     * 十字形基本骨架
     * 检测 1/4 另外 3/4 是 copy的，节省计算量
     * radius+1是应为发现多出来的只有1格
     * @param direction 
     * ***/
	private void createGlassSphere(Level world, BlockPos centerPos, Direction direction) {
        int radius = 5;
        int radius_outer_sq = 25;
        int radius_inner_sq = 16;
        
        BlockPos start = centerPos.offset(-radius+1, 0, -radius+1);
        BlockPos end = centerPos.offset(0, radius-1, 0);
        
        switch (direction) {
	        default:
		    case NORTH:
		    case SOUTH:
		    	start =  centerPos.offset(-radius+1, 0, -radius+2);
		    	break;
		    case WEST:
		    case EAST:
		    	start =  centerPos.offset(-radius+2, 0, -radius+1);
		    	break;
        }
        
        int dx = 0;
        int dz = 0;
        BlockPos posX;
        BlockPos posZ;
        BlockPos posXZ;

        for (BlockPos pos : BlockPos.betweenClosed(start, end)) {
            if (Math.abs(pos.getX() - centerPos.getX()) + Math.abs(pos.getY() - centerPos.getY()) + Math.abs(pos.getZ() - centerPos.getZ()) < radius-1) {
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
	                if(Math.abs(dx)<2||Math.abs(dz)<2) {
                		world.setBlockAndUpdate(posX, UNBROKEN_CEMENT_STATE);
		                world.setBlockAndUpdate(posZ, UNBROKEN_CEMENT_STATE);
		                world.setBlockAndUpdate(posXZ, UNBROKEN_CEMENT_STATE);
		                world.setBlockAndUpdate(pos, UNBROKEN_CEMENT_STATE);
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
        boolean n_s = false;
        switch (direction) {
	        default:
		    case NORTH:
		    	start =  centerPos.offset(-radius+1, 0, radius-2);
		    	end = centerPos.offset(0, radius-1, radius-1);
		    	n_s = true;
		    	break;
		    case SOUTH:
		    	start =  centerPos.offset(-radius+1, 0, -radius+2);
		    	end = centerPos.offset(0, radius-1, -radius+1);
		    	n_s = true;
		    	break;
		    case WEST:
		    	start =  centerPos.offset(radius-2, 0, -radius);
		    	end = centerPos.offset(radius-1, radius-1, 0);
		    	break;
		    case EAST:
		    	start =  centerPos.offset(-radius+2, 0, -radius);
		    	end = centerPos.offset(-radius+1, radius-1, 0);
		    	break;
        }
        if(n_s) {
        	for (BlockPos pos : BlockPos.betweenClosed(start, end)) {
                int distSq = (int) pos.distSqr(centerPos);
                if (distSq <= radius_outer_sq) {
            		dx = centerPos.getX() - pos.getX();
            		dz = centerPos.getZ() - pos.getZ();
                    posX = new BlockPos(centerPos.getX() + dx, pos.getY(), pos.getZ());
                    posZ = new BlockPos(pos.getX(), pos.getY(), centerPos.getZ() + dz);
                    posXZ = new BlockPos(centerPos.getX() + dx, pos.getY(), centerPos.getZ() + dz);
                	if(distSq >= radius_inner_sq) {
    	                if(Math.abs(dx)<2||Math.abs(dz)<2) {
                    		world.setBlockAndUpdate(posX, UNBROKEN_CEMENT_STATE);
    		                world.setBlockAndUpdate(posZ, UNBROKEN_FOG_STATE);
    		                world.setBlockAndUpdate(posXZ, UNBROKEN_FOG_STATE);
    		                world.setBlockAndUpdate(pos, UNBROKEN_CEMENT_STATE);
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
        }else {
        	for (BlockPos pos : BlockPos.betweenClosed(start, end)) {
                int distSq = (int) pos.distSqr(centerPos);
                if (distSq <= radius_outer_sq) {
            		dx = centerPos.getX() - pos.getX();
            		dz = centerPos.getZ() - pos.getZ();
                    posX = new BlockPos(centerPos.getX() + dx, pos.getY(), pos.getZ());
                    posZ = new BlockPos(pos.getX(), pos.getY(), centerPos.getZ() + dz);
                    posXZ = new BlockPos(centerPos.getX() + dx, pos.getY(), centerPos.getZ() + dz);
                	if(distSq >= radius_inner_sq) {
    	                if(Math.abs(dx)<2||Math.abs(dz)<2) {
                    		world.setBlockAndUpdate(posX, UNBROKEN_CEMENT_STATE);
    		                world.setBlockAndUpdate(posZ, UNBROKEN_FOG_STATE);
    		                world.setBlockAndUpdate(posXZ, UNBROKEN_CEMENT_STATE);
    		                world.setBlockAndUpdate(pos, UNBROKEN_FOG_STATE);
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
        
        switch (direction) {
	        default:
		    case NORTH:
		    case SOUTH:
		    	start =  centerPos.offset(-radius+1, 0, 0);
		    	end = centerPos.offset(0, radius-1, 0);
		    	n_s = true;
		    	break;
		    case WEST:
		    case EAST:
		    	start =  centerPos.offset(0, 0, -radius+1);
		    	end = centerPos.offset(0, radius-1, 0);
		    	break;
        }
    	for (BlockPos pos : BlockPos.betweenClosed(start, end)) {
            int distSq = (int) pos.distSqr(centerPos);
            if (distSq <= radius_inner_sq) {
        		dx = centerPos.getX() - pos.getX();
        		dz = centerPos.getZ() - pos.getZ();
                posX = new BlockPos(centerPos.getX() + dx, pos.getY(), pos.getZ());
                posZ = new BlockPos(pos.getX(), pos.getY(), centerPos.getZ() + dz);
                posXZ = new BlockPos(centerPos.getX() + dx, pos.getY(), centerPos.getZ() + dz);
            	if(distSq >= 9) {
            		world.setBlockAndUpdate(posX, UNBROKEN_CEMENT_STATE);
	                world.setBlockAndUpdate(posZ, UNBROKEN_CEMENT_STATE);
	                world.setBlockAndUpdate(posXZ, UNBROKEN_CEMENT_STATE);
	                world.setBlockAndUpdate(pos, UNBROKEN_CEMENT_STATE);
            	}else {
                    world.setBlockAndUpdate(posX, UNBROKEN_FOG_STATE);
                    world.setBlockAndUpdate(posZ, UNBROKEN_FOG_STATE);
                    world.setBlockAndUpdate(posXZ, UNBROKEN_FOG_STATE);
                    world.setBlockAndUpdate(pos, UNBROKEN_FOG_STATE);
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
        	return this.defaultBlockState().setValue(BlockStateProperties.FACING, Direction.NORTH);
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
