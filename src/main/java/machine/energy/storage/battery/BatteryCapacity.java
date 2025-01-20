package machine.energy.storage.battery;

import javax.annotation.Nullable;

import item.ItemRegister;
import machine.registry.MBlockEntityRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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
import net.minecraft.world.phys.BlockHitResult;
import util.json.BlockJSON;

public class BatteryCapacity extends Block implements EntityBlock {

	public static final String global_name = "battery_capacity";

	public BatteryCapacity(Properties p_49795_) {
		super(p_49795_.noOcclusion());
	}
	
	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState pBlockState) {
		return new BatteryCapacityEntity(pos, pBlockState);
	}
	

	@Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
		Direction direction = context.getNearestLookingDirection();
		if(direction==Direction.DOWN||direction == Direction.UP) direction = Direction.NORTH;
    	return this.defaultBlockState().setValue(BlockStateProperties.FACING, direction);
    }
	
    @SuppressWarnings("deprecation")
    @Override
	public InteractionResult use(BlockState blockstate, Level level, BlockPos pos, Player player, InteractionHand interactionhand, BlockHitResult blockHitResult) {
	    super.use(blockstate, level, pos, player, interactionhand, blockHitResult);
	    if(level.isClientSide) return  InteractionResult.PASS;
	    if(interactionhand != InteractionHand.MAIN_HAND) return InteractionResult.PASS; 
	    ItemStack handItemStack = player.getItemInHand(interactionhand);
	    if(handItemStack.getItem()==ItemRegister.BATTERY_HEART.get()) {
	    	if(level.getBlockEntity(pos,MBlockEntityRegister.BATTERYCAPACITY_BE.get()).get().addLevel()) {
	        	handItemStack.shrink(1);
	    		player.setItemInHand(InteractionHand.MAIN_HAND, handItemStack);
	    		return InteractionResult.SUCCESS;
	    	}
	    }else if(handItemStack.isEmpty()&&player.isCrouching()) {
	    	if(level.getBlockEntity(pos,MBlockEntityRegister.BATTERYCAPACITY_BE.get()).get().removeLevel()) {
	    		ItemStack stack = new ItemStack(ItemRegister.BATTERY_HEART.get(),1);
	    		player.setItemInHand(InteractionHand.MAIN_HAND, stack);
	    		return InteractionResult.SUCCESS;
	    	}
	    }
	    return InteractionResult.PASS;
	}
	
	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pBlockState, BlockEntityType<T> pBlockEntityType) {
	     if(pLevel.isClientSide()) {
	    	 return (level,pos,state,blockentity) -> {
	    		 if(blockentity instanceof BatteryCapacityEntity entity) {
	    			 entity.clienttick();
	    		 }
	    	 };
	     }else {
    		 return (level,pos,state,blockentity) -> {
	    		 if(blockentity instanceof BatteryCapacityEntity entity) {
	    			 entity.servertick();
	    		 }
	    	 };
	     }
	 }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.AGE_7);
        builder.add(BlockStateProperties.FACING);
    }
    
    @SuppressWarnings("deprecation")
	@Override
    public void onRemove(BlockState oldState, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if(!level.isClientSide)
    	if (!oldState.is(newState.getBlock())) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof BatteryCapacityEntity entity) {
                // Call the remove method in the BlockEntity to clean up network references
            	entity.remove(level);
            }
        }
        super.onRemove(oldState, level, pos, newState, isMoving);
    }
	
	 static {
	        BlockJSON.GenModelsJSONBasic(global_name);
	        BlockJSON.GenBlockStateJSONBasic(global_name);
	        BlockJSON.GenItemJSONBasic(global_name);
	        BlockJSON.GenLootTableJSONBasic(global_name);
	 }
}
