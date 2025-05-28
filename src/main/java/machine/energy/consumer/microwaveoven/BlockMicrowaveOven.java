package machine.energy.consumer.microwaveoven;

import javax.annotation.Nullable;

import menu.microwaveoven.MicrowaveOvenMenuProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import util.json.BlockJSON;

/**
 * 微波炉
 * @author NANMEDA
 * */
public class BlockMicrowaveOven extends Block implements EntityBlock{
	public static String global_name = "microwave_oven"; 
	
	public BlockMicrowaveOven(Properties properties) {
		super(properties
				.sound(SoundType.AMETHYST)
	            .strength(5f,5f)
	            .noOcclusion()
	            .mapColor(MapColor.COLOR_GRAY)
	            );
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
    public VoxelShape getShape(BlockState pBlockState, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
		if(pBlockState.getValue(BlockStateProperties.FACING)==Direction.NORTH) {
			return Block.box(2, 0, 5, 14, 6, 13);
		}else if(pBlockState.getValue(BlockStateProperties.FACING)==Direction.EAST) {
			return Block.box(3, 0, 2, 11, 6, 14);
		}else if(pBlockState.getValue(BlockStateProperties.FACING)==Direction.SOUTH) {
			return Block.box(2, 0, 3, 14, 6, 11);
		}else if(pBlockState.getValue(BlockStateProperties.FACING)==Direction.WEST) {
			return Block.box(5, 0, 2, 13, 6, 14);
		}else{
			return Block.box(5, 0, 2, 13, 6, 14);
		}
    }
	
	@SuppressWarnings("deprecation")
	@Override
	public InteractionResult use(BlockState blockstate,Level level,BlockPos pos,Player player,InteractionHand interactionhand,BlockHitResult blockHitResult) {
		super.use(blockstate,level,pos,player,interactionhand,blockHitResult);
		if(!level.isClientSide()) {
			var BlockEntity = level.getBlockEntity(pos);
			if(BlockEntity instanceof MicrowaveOvenEntity entity) {
				if(player.isCrouching()) {
					entity.is_button = true;
					return InteractionResult.SUCCESS;
				}
				ItemStack mainHandItemStack =  player.getMainHandItem();
				if(!entity.getItems().getStackInSlot(1).isEmpty()) {
					if(mainHandItemStack.isEmpty()) {
						player.setItemInHand(InteractionHand.MAIN_HAND, entity.getItems().getStackInSlot(1));
						entity.getItems().setStackInSlot(1, ItemStack.EMPTY);
						level.sendBlockUpdated(pos, blockstate, blockstate, Block.UPDATE_ALL);
						return InteractionResult.SUCCESS;
					}else if(mainHandItemStack.getItem()==entity.getItems().getStackInSlot(1).getItem()&&
							(mainHandItemStack.getCount()+entity.getItems().getStackInSlot(1).getCount())<=64) {
						ItemStack finalItemStack = new ItemStack(mainHandItemStack.getItem(),mainHandItemStack.getCount()+entity.getItems().getStackInSlot(1).getCount());
 						player.setItemInHand(InteractionHand.MAIN_HAND, finalItemStack);
 						entity.getItems().setStackInSlot(1, ItemStack.EMPTY);
 						level.sendBlockUpdated(pos, blockstate, blockstate, Block.UPDATE_ALL);
						return InteractionResult.SUCCESS;
					}
				}else if(entity.getItems().getStackInSlot(0).isEmpty() && !mainHandItemStack.isEmpty()) {
					Item item = mainHandItemStack.getItem();
			        if(item.isEdible()||item==Items.EGG) {
			        	entity.getItems().setStackInSlot(0, mainHandItemStack);
			        	player.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
						level.sendBlockUpdated(pos, blockstate, blockstate, Block.UPDATE_ALL);
						return InteractionResult.SUCCESS;
			        }
				}
				ServerPlayer ifpe = (ServerPlayer)player;
				ifpe.openMenu(new MicrowaveOvenMenuProvider(pos));
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
	    					    Explosion explosion = new Explosion(level, null, null, null, pos.getX(), pos.getY(), pos.getZ(), 3.0f, false, Explosion.BlockInteraction.DESTROY_WITH_DECAY);
	    					    explosion.explode();
	    					    explosion.finalizeExplosion(true);
	    					}
	    			 }
	    		 }
	    	 };
	     }
	 }
	
	@SuppressWarnings("deprecation")
	@Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof MicrowaveOvenEntity e) {
                e.drop();
                e.remove(pLevel);
                
            }
        }

        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
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

