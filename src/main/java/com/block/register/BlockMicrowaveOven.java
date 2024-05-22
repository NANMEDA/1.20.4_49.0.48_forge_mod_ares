package com.block.register;

import javax.annotation.Nullable;

import com.menu.register.MicrowaveOvenMenuProvider;
import com.mojang.serialization.MapCodec;

import block.entity.register.MicrowaveOvenEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.extensions.IForgeServerPlayer;

public class BlockMicrowaveOven extends HorizontalDirectionalBlock implements EntityBlock{
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
		//宽高厚
	      return Block.box(0,0,0,16,16,16);
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
	    			 entity.servertick();
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
	
	 static {
	        BlockJSON.GenModelsJSONBasic(global_name);
	        BlockJSON.GenBlockStateJSONBasic(global_name);
	        BlockJSON.GenItemJSONBasic(global_name);
	        BlockJSON.GenLootTableJSONBasic(global_name);
	    	}
}

