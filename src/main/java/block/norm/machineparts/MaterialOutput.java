package block.norm.machineparts;

import javax.annotation.Nullable;
import java.util.Random;

import block.entity.machineparts.MaterialOutputEntity;
import block.norm.BlockJSON;
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

public class MaterialOutput extends Block implements EntityBlock {
	
	public static final String global_name = "material_output";

	public MaterialOutput(Properties p_49795_) {
		super(p_49795_);
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState pBlockState) {
		return new MaterialOutputEntity(pos, pBlockState);
	}
	
	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pBlockState, BlockEntityType<T> pBlockEntityType) {
	     if(pLevel.isClientSide()) {
	    	 return (level,pos,state,blockentity) -> {
	    		 if(blockentity instanceof MaterialOutputEntity entity) {
	    			 entity.clienttick();
	    		 }
	    	 };
	     }else {
    		 return (level,pos,state,blockentity) -> {
	    		 if(blockentity instanceof MaterialOutputEntity entity) {
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
            if (blockEntity instanceof MaterialOutputEntity) {
                ((MaterialOutputEntity) blockEntity).drop();
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }
	
	@Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Random random = new Random();
        int randomLevel = random.nextInt(4); // Random level between 0 and 3
        return this.defaultBlockState().setValue(BlockStateProperties.LEVEL, randomLevel);
    }
	
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.LEVEL);
    }
	
	 static {
	        BlockJSON.GenModelsJSONBasic(global_name);
	        BlockJSON.GenBlockStateJSONBasic(global_name);
	        BlockJSON.GenItemJSONBasic(global_name);
	        BlockJSON.GenLootTableJSONBasic(global_name);
	 }

}
