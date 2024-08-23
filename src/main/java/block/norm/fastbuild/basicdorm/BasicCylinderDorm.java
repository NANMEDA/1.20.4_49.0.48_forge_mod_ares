package block.norm.fastbuild.basicdorm;

import javax.annotation.Nullable;

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
import util.json.BlockJSON;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

/**
 * 躺着的圆柱穹顶 I
 * @author NANMEDA
 * */
public class BasicCylinderDorm extends Block {
    public static final String global_name = "basic_cylinder_dorm";
    
    private static final BlockState UNBROKEN_GLASS_STATE = BlockRegister.unbrokenglass_BLOCK.get().defaultBlockState();
    private static final BlockState UNBROKEN_CEMENT_STATE = BlockRegister.unbrokencement_BLOCK.get().defaultBlockState();
    private static final BlockState AIR_STATE = BlockRegister.A_AIR.get().defaultBlockState();

    public BasicCylinderDorm(Properties properties) {
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
    		    case WEST:
    		    case EAST:
    		}
    		if(rotate){
    			if(DormHelper.checkPlaceDormOccupied(level, pos, new Vec3i(-12, -2, -7),new Vec3i(12, 14, 7), player)) return InteractionResult.FAIL;
    		}else {
    			if(DormHelper.checkPlaceDormOccupied(level, pos, new Vec3i(-7, -2, -12),new Vec3i(7, 14, 12), player)) return InteractionResult.FAIL;
    		}
            createGlassSphere(level, pos,rotate);
            createCementBase(level, pos,rotate);
            createJunctionBase(level,pos.below(),rotate);
        }
        return InteractionResult.SUCCESS;
    }
    
    private void createJunctionBase(Level level, BlockPos pos,boolean rotate) {
    	if(rotate) {
	    	block.norm.fastbuild.JunctionHelper.BirthJuntionBase(level,pos.offset(14,0,0),0);
	    	block.norm.fastbuild.JunctionHelper.BirthJuntionBase(level,pos.offset(-14,0,0),4);
    	}else {	    	
    		block.norm.fastbuild.JunctionHelper.BirthJuntionBase(level,pos.offset(0,0,14),6);
    		block.norm.fastbuild.JunctionHelper.BirthJuntionBase(level,pos.offset(0,0,-14),2);
    	}
    	
	}

	/***
     * 躺着的圆柱
     * ***/
    protected void createCementBase(Level world, BlockPos centerPos, Boolean rotate) {
    	
    	int x_r,z_r;
    	if(rotate) {
    		x_r = 12;
        	z_r = 7;
    	}else {
    		x_r = 7;
        	z_r = 12;
    	}
    	
        BlockPos start = centerPos.offset(-x_r, -2, -z_r);
        BlockPos end = centerPos.offset(0, -2, 0);

        for (BlockPos pos : BlockPos.betweenClosed(start, end)) {
            int dx = pos.getX() - centerPos.getX();
            int dz = pos.getZ() - centerPos.getZ();
            if (Math.abs(dx)<=x_r&&Math.abs(dz)<=z_r) {
                BlockPos posX = new BlockPos(centerPos.getX() - dx, pos.getY(), pos.getZ());
                BlockPos posZ = new BlockPos(pos.getX(), pos.getY(), centerPos.getZ() - dz);
                BlockPos posXZ = new BlockPos(centerPos.getX() - dx, pos.getY(), centerPos.getZ() - dz);

                world.setBlockAndUpdate(posX, UNBROKEN_CEMENT_STATE);
                world.setBlockAndUpdate(posZ, UNBROKEN_CEMENT_STATE);
                world.setBlockAndUpdate(posXZ, UNBROKEN_CEMENT_STATE);
                world.setBlockAndUpdate(pos, UNBROKEN_CEMENT_STATE);
            }
        }

        start = centerPos.offset(-x_r, -1, -z_r);
        end = centerPos.offset(0, -1, 0);

        for (BlockPos pos : BlockPos.betweenClosed(start, end)) {
            int dx = pos.getX() - centerPos.getX();
            int dz = pos.getZ() - centerPos.getZ();
            if (Math.abs(dx)<=x_r&&Math.abs(dz)<=z_r) {
                BlockPos posX = new BlockPos(centerPos.getX() - dx, pos.getY(), pos.getZ());
                BlockPos posZ = new BlockPos(pos.getX(), pos.getY(), centerPos.getZ() - dz);
                BlockPos posXZ = new BlockPos(centerPos.getX() - dx, pos.getY(), centerPos.getZ() - dz);
                if (Math.abs(dx)>=x_r-2||Math.abs(dz)>=z_r-2) {
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
     * 躺着的圆柱
     * ***/
    protected void createGlassSphere(Level world, BlockPos centerPos,Boolean rotate) {
    	int x_r,z_r,radius;
    	radius=7;
    	BlockPos start,end,axis;
    	if(rotate) {
    		x_r = 12;
        	z_r = 7;
        	start = centerPos.offset(-x_r+1, radius, -z_r);
        	end = centerPos.offset(-x_r+1, 0, 0);
    	}else {
    		x_r = 7;
        	z_r = 12;
        	start = centerPos.offset(-x_r, radius, -z_r+1);
        	end = centerPos.offset(0, 0, -z_r+1);
    	}
    	
    	
        for (BlockPos pos : BlockPos.betweenClosed(start, end)) {
        	int dx = Math.abs(pos.getX()-end.getX());
        	int dy = Math.abs(pos.getY()-end.getY());
        	int dz = Math.abs(pos.getZ()-end.getZ());
        	if(rotate) {
        		if(dy*dy+dz*dz<=49) {
        			if(dy*dy+dz*dz<=36) {
            			for(int i = 1;i<24;i++) {
            				world.setBlockAndUpdate(pos, AIR_STATE);
            				pos = pos.offset(0,0,2*dz);
            				world.setBlockAndUpdate(pos, AIR_STATE);
            				pos = pos.offset(1,0,-2*dz);
            			}
            		}else {
            			for(int i = 1;i<24;i++) {
            				world.setBlockAndUpdate(pos, UNBROKEN_GLASS_STATE);
            				pos = pos.offset(0,0,2*dz);
            				world.setBlockAndUpdate(pos, UNBROKEN_GLASS_STATE);
            				pos = pos.offset(1,0,-2*dz);
            			}
            		}
        		}
        	}else {
        		if(dx*dx+dy*dy<=49) {
        			if(dx*dx+dy*dy<=36) {
            			for(int i = 1;i<24;i++) {
            				world.setBlockAndUpdate(pos, AIR_STATE);
            				pos = pos.offset(2*dx,0,0);
            				world.setBlockAndUpdate(pos, AIR_STATE);
            				pos = pos.offset(-2*dx,0,1);
            			}
            		}else {
            			for(int i = 1;i<24;i++) {
            				world.setBlockAndUpdate(pos, UNBROKEN_GLASS_STATE);
            				pos = pos.offset(2*dx,0,0);
            				world.setBlockAndUpdate(pos, UNBROKEN_GLASS_STATE);
            				pos = pos.offset(-2*dx,0,1);
            			}
            		}
        		}
        	}     
        }
        
    	if(rotate) {
        	start = centerPos.offset(-x_r, radius, -z_r);
        	end = centerPos.offset(-x_r, 0, 0);
    	}else {
        	start = centerPos.offset(-x_r, radius, -z_r);
        	end = centerPos.offset(0, 0, -z_r);
    	}
    	for (BlockPos pos : BlockPos.betweenClosed(start, end)) {
    		int dx = Math.abs(pos.getX()-end.getX());
        	int dy = Math.abs(pos.getY()-end.getY());
        	int dz = Math.abs(pos.getZ()-end.getZ());
        	if(rotate) {
        		if(dy*dy+dz*dz<=49) {
    				world.setBlockAndUpdate(pos, UNBROKEN_CEMENT_STATE);
    				pos = pos.offset(0,0,2*dz);
    				world.setBlockAndUpdate(pos, UNBROKEN_CEMENT_STATE);
    				pos = pos.offset(24,0,0);
    				world.setBlockAndUpdate(pos, UNBROKEN_CEMENT_STATE);
    				pos = pos.offset(0,0,-2*dz);
    				world.setBlockAndUpdate(pos, UNBROKEN_CEMENT_STATE);
        		}
        	}else {
        		if(dx*dx+dy*dy<=49) {
					world.setBlockAndUpdate(pos, UNBROKEN_CEMENT_STATE);
					pos = pos.offset(2*dx,0,0);
					world.setBlockAndUpdate(pos, UNBROKEN_CEMENT_STATE);
    				pos = pos.offset(0,0,24);
    				world.setBlockAndUpdate(pos, UNBROKEN_CEMENT_STATE);
    				pos = pos.offset(-2*dx,0,0);
    				world.setBlockAndUpdate(pos, UNBROKEN_CEMENT_STATE);
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
