package com.main.maring.block.norm.blueprintbuilder;

import javax.annotation.Nullable;

import com.main.maring.block.entity.neutral.blueprintbuilder.BlueprintBuilderEntity;
import com.main.maring.menu.blueprintbuilder.BlueprintBuilderMenuProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import com.main.maring.util.json.BlockJSON;

/**
 * 蓝图制作台
 * @author NANMEDA
 * */
public class BlockBlueprintBuilder extends Block implements EntityBlock{
	public static final String global_name = "blueprint_builder"; 
	
	public BlockBlueprintBuilder(Properties properties) {
		super(properties);
	}
	
	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState pBlockState) {
		return new BlueprintBuilderEntity(pos, pBlockState);
	}
	
	@Override
	public boolean isPathfindable(BlockState p_60475_, BlockGetter p_60476_, BlockPos p_60477_, PathComputationType p_60478_) {
		return true;
	}
	
	@Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
		return Block.box(0,0,0,16,10,16);
    }
	
	@Override
	public InteractionResult use(BlockState blockstate,Level level,BlockPos pos,Player player,InteractionHand interactionhand,BlockHitResult blockHitResult) {
		if(!level.isClientSide()) {
			var BlockEntity = level.getBlockEntity(pos);
			if (BlockEntity instanceof BlueprintBuilderEntity entity) {
				ServerPlayer ifpe = (ServerPlayer)player;
				NetworkHooks.openScreen(ifpe, new BlueprintBuilderMenuProvider(pos), pos);
			}else {
				throw new IllegalStateException("missing block:"+global_name);
			}
		}
		return InteractionResult.SUCCESS;
	}
	
	
	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pBlockState, BlockEntityType<T> pBlockEntityType) {
	     if(pLevel.isClientSide()) {
	    	 return (level,pos,state,blockentity) -> {
	    		 if(blockentity instanceof BlueprintBuilderEntity entity) {
	    			 entity.clienttick();
	    		 }
	    	 };
	     }else {
    		 return (level,pos,state,blockentity) -> {
	    		 if(blockentity instanceof BlueprintBuilderEntity entity) {
	    			 entity.servertick();
	    		 }
	    	 };
	     }
	 }
	
	@SuppressWarnings("deprecation")
	@Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
		 BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
		if (blockEntity instanceof BlueprintBuilderEntity) {
            ((BlueprintBuilderEntity) blockEntity).drop();
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }
	
	
	
	@Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        if (context.getClickedFace() == Direction.DOWN||context.getClickedFace() == Direction.UP) {
        	Direction playerDirection = context.getNearestLookingDirection().getOpposite();
        	if(playerDirection==Direction.DOWN||playerDirection==Direction.UP) {
        		playerDirection=Direction.NORTH;
        	} 
            return this.defaultBlockState().setValue(BlockStateProperties.FACING, playerDirection);
        } else {
            return super.defaultBlockState().setValue(BlockStateProperties.FACING, Direction.NORTH);
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

