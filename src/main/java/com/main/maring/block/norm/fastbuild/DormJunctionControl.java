package com.main.maring.block.norm.fastbuild;

import javax.annotation.Nullable;

import com.main.maring.block.entity.neutral.fastbuild.DormJunctionControlEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class DormJunctionControl extends Block implements EntityBlock {

	
	public static final String global_name = "dorm_junction_control";

	public DormJunctionControl(Properties p_49795_) {
		super(p_49795_);
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState pBlockState) {
		return new DormJunctionControlEntity(pos, pBlockState);
	}
	
	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pBlockState, BlockEntityType<T> pBlockEntityType) {
	     if(pLevel.isClientSide()) {
	    	 return (level,pos,state,blockentity) -> {
	    		 if(blockentity instanceof DormJunctionControlEntity entity) {
	    			 entity.clienttick();
	    		 }
	    	 };
	     }else {
    		 return (level,pos,state,blockentity) -> {
	    		 if(blockentity instanceof DormJunctionControlEntity entity) {
	    			 entity.servertick();
	    		 }
	    	 };
	     }
	 }
	
	@SuppressWarnings("deprecation")
	@Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof DormJunctionControlEntity entity) {
                BlockPos oppositePos = entity.getOppositePos();
                BlockEntity oppositeEntity = pLevel.getBlockEntity(oppositePos);
                if(oppositeEntity instanceof DormJunctionControlEntity oentity) {
                	oentity.savePosData(oppositePos);
                }
            }
            CleanAll();
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }
	
	private void CleanAll() {
	}

	@Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
		return this.defaultBlockState().setValue(BlockStateProperties.LEVEL, 0);
    }
	
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.LEVEL);
    }

}
