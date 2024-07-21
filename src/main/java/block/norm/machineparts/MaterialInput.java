package block.norm.machineparts;

import javax.annotation.Nullable;

import block.entity.machineparts.ElectronicPowerEntity;
import block.entity.machineparts.MaterialInputEntity;
import block.norm.BlockJSON;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class MaterialInput extends Block implements EntityBlock {
	
	public static final String global_name = "material_input";

	public MaterialInput(Properties p_49795_) {
		super(p_49795_);
	}
	
	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState pBlockState) {
		return new MaterialInputEntity(pos, pBlockState);
	}
	
	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pBlockState, BlockEntityType<T> pBlockEntityType) {
	     if(pLevel.isClientSide()) {
	    	 return (level,pos,state,blockentity) -> {
	    		 if(blockentity instanceof MaterialInputEntity entity) {
	    			 entity.clienttick();
	    		 }
	    	 };
	     }else {
    		 return (level,pos,state,blockentity) -> {
	    		 if(blockentity instanceof MaterialInputEntity entity) {
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
            if (blockEntity instanceof MaterialInputEntity) {
                ((MaterialInputEntity) blockEntity).drop();
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }
	
	@Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        if (context.getClickedFace() == Direction.DOWN||context.getClickedFace() == Direction.UP) {
        	Direction playerDirection = context.getNearestLookingDirection();
        	if(playerDirection==Direction.UP) {
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
