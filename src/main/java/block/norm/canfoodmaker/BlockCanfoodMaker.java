package block.norm.canfoodmaker;

import javax.annotation.Nullable;

import com.menu.canfoodmaker.CanfoodMakerMenuProvider;
import com.mojang.serialization.MapCodec;

import block.entity.consumer.canfoodmaker.CanfoodMakerEntity;
import block.norm.BlockJSON;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.extensions.IForgeServerPlayer;

public class BlockCanfoodMaker extends HorizontalDirectionalBlock implements EntityBlock {
	public static String global_name = "canfood_maker"; 
	
	public BlockCanfoodMaker(Properties properties) {
		super(properties);
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState pBlockState) {
		return new CanfoodMakerEntity(pos, pBlockState);
	}
	
	@Override
	public boolean isPathfindable(BlockState p_60475_, BlockGetter p_60476_, BlockPos p_60477_, PathComputationType p_60478_) {
		return true;
	}
	
	@Override
	   public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
		//宽高厚
		return Shapes.join(
				Block.box(0, 0, 0, 16, 16, 16), 
				Block.box(7, 0, 7, 9, 2, 9), 
			    BooleanOp.ONLY_FIRST
			);
	   }
	
	@SuppressWarnings("deprecation")
	@Override
	public InteractionResult use(BlockState blockstate,Level level,BlockPos pos,Player player,InteractionHand interactionhand,BlockHitResult blockHitResult) {
		super.use(blockstate,level,pos,player,interactionhand,blockHitResult);
		if(!level.isClientSide()) {
			var BlockEntity = level.getBlockEntity(pos);
			if(BlockEntity instanceof CanfoodMakerEntity entity) {
				ItemStack hand = player.getMainHandItem();
				ItemStack slots = entity.getItems().getStackInSlot(4);
				if (!slots.isEmpty() &&
				    (hand.isEmpty() ||
				    (hand.getItem() == slots.getItem() &&
				    (hand.getCount() + slots.getCount() <= 64)))) {
						player.setItemInHand(InteractionHand.MAIN_HAND, hand.isEmpty() ? slots:new ItemStack(hand.getItem(),hand.getCount()+slots.getCount()));
						entity.getItems().setStackInSlot(4, ItemStack.EMPTY);
						level.sendBlockUpdated(pos, blockstate, blockstate, Block.UPDATE_ALL);
						return InteractionResult.SUCCESS;
				}
				IForgeServerPlayer ifpe = (IForgeServerPlayer)player;
				ifpe.openMenu(new CanfoodMakerMenuProvider(pos), pos);
				
			}else {
				throw new IllegalStateException("missing block-canfood_maker");
			}
		}
		return InteractionResult.SUCCESS;
	}
	
	
	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pBlockState, BlockEntityType<T> pBlockEntityType) {
	     if(pLevel.isClientSide()) {
	    	 return (level,pos,state,blockentity) -> {
	    		 if(blockentity instanceof CanfoodMakerEntity entity) {
	    			 entity.clienttick();
	    		 }
	    	 };
	     }else {
    		 return (level,pos,state,blockentity) -> {
	    		 if(blockentity instanceof CanfoodMakerEntity entity) {
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
            if (blockEntity instanceof CanfoodMakerEntity) {
                ((CanfoodMakerEntity) blockEntity).drop();
            }
        }

        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

	@Override
	protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
		return null;
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

