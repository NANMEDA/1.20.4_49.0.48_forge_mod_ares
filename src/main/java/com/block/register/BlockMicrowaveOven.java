package com.block.register;

import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.stringtemplate.v4.compiler.STParser.ifstat_return;

import com.menu.register.MicrowaveOvenMenuProvider;
import com.mojang.serialization.MapCodec;

import block.entity.register.MicrowaveOvenEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.extensions.IForgeServerPlayer;
import net.minecraftforge.registries.ForgeRegistries;


public class BlockMicrowaveOven extends Block implements EntityBlock{
	public static String global_name = "microwave_oven"; 
	
	public BlockMicrowaveOven(Properties properties) {
		super(properties);
	}
	
    

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState pBlockState) {
		return new MicrowaveOvenEntity(pos, pBlockState);
	}
	
	@Override
	public boolean isPathfindable(BlockState p_60475_, BlockGetter p_60476_, BlockPos p_60477_, PathComputationType p_60478_) {
		return true;
	}
	
	@Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
		if(p_60555_.getValue(BlockStateProperties.FACING)==Direction.NORTH) {
			return Block.box(2, 0, 5, 14, 6, 13);
		}else if(p_60555_.getValue(BlockStateProperties.FACING)==Direction.EAST) {
			return Block.box(3, 0, 2, 11, 6, 14);
		}else if(p_60555_.getValue(BlockStateProperties.FACING)==Direction.SOUTH) {
			return Block.box(2, 0, 3, 14, 6, 11);
		}else if(p_60555_.getValue(BlockStateProperties.FACING)==Direction.WEST) {
			return Block.box(5, 0, 2, 13, 6, 14);
		}else{
			return Block.box(5, 0, 2, 13, 6, 14);
		}
    }
	
	@Override
	public InteractionResult use(BlockState blockstate,Level level,BlockPos pos,Player player,InteractionHand interactionhand,BlockHitResult blockHitResult) {
		super.use(blockstate,level,pos,player,interactionhand,blockHitResult);
		if(!level.isClientSide()) {
			var BlockEntity = level.getBlockEntity(pos);
			if(BlockEntity instanceof MicrowaveOvenEntity entity) {
				player = (ServerPlayer) player;
				IForgeServerPlayer ifpe = (IForgeServerPlayer)player;
				ifpe.openMenu(new MicrowaveOvenMenuProvider(pos), pos);
			}else {
				throw new IllegalStateException("missing block-microwave oven");
			}
		}
		return InteractionResult.SUCCESS;
	}
	
	
	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pBlockState, BlockEntityType<T> pBlockEntityType) {
	     if(pLevel.isClientSide()) {
	    	 return (level,pos,state,blockentity) -> {
	    		 if(blockentity instanceof MicrowaveOvenEntity entity) {
	    			 entity.clienttick();
	    		 }
	    	 };
	     }else {
    		 return (level,pos,state,blockentity) -> {
	    		 if(blockentity instanceof MicrowaveOvenEntity entity) {
	    			 if(entity.servertick(true)) {
	    				 if (!level.isClientSide()) {
	    					    Explosion explosion = new Explosion(level, null, null, null, pos.getX(), pos.getY(), pos.getZ(), 3.0f, false, Explosion.BlockInteraction.DESTROY_WITH_DECAY, ParticleTypes.FLAME, null, SoundEvents.GENERIC_EXPLODE);
	    					    explosion.explode();
	    					    explosion.finalizeExplosion(true);  // This is necessary to apply the explosion effects
	    					}
	    			 }
	    		 }
	    	 };
	     }
	 }

	@Override
	protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
		return null;
	}
	
	@Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof MicrowaveOvenEntity) {
                ((MicrowaveOvenEntity) blockEntity).drop();
            }
        }

        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }
	
	
	
	@Override
	public BlockState playerWillDestroy(Level pLevel, BlockPos pBlockPos, BlockState pBlockState, Player player) {
		super.playerWillDestroy(pLevel, pBlockPos, pBlockState, player);
		return pBlockState;
	}
	
	@Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        if (context.getClickedFace() == Direction.DOWN||context.getClickedFace() == Direction.UP) {
        	Direction playerDirection = context.getNearestLookingDirection().getOpposite();
        	if(playerDirection==Direction.DOWN||playerDirection==Direction.UP) {
        		return super.defaultBlockState();
        	} 
            return this.defaultBlockState().setValue(BlockStateProperties.FACING, playerDirection);
        } else {
            return super.defaultBlockState();
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

