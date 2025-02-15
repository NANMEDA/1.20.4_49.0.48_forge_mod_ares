package block.norm.fastbuild.dormcontrol;

import javax.annotation.Nullable;

import block.entity.consumer.basicmetalmanufactor.BasicMetalManufactorEntity;
import block.entity.neutral.dormcontrol.DomeControlEntity;
import menu.basicmetalmanufactor.BasicMetalManufactorMenuProvider;
import menu.dormcontrol.DomeControlMenuProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.extensions.IForgeServerPlayer;
import util.json.BlockJSON;

public class DomeControl extends Block implements EntityBlock {

	public static final String global_name = "dome_control";


	public DomeControl(Properties p_49795_) {
		super(p_49795_);
	}
	
	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState pBlockState) {
		return new DomeControlEntity(pos, pBlockState);
	}
	
	@Override
	public boolean isPathfindable(BlockState p_60475_, BlockGetter p_60476_, BlockPos p_60477_, PathComputationType p_60478_) {
		return true;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public InteractionResult use(BlockState blockstate,Level level,BlockPos pos,Player player,InteractionHand interactionhand,BlockHitResult blockHitResult) {
		super.use(blockstate,level,pos,player,interactionhand,blockHitResult);
		if(!level.isClientSide()) {
			var BlockEntity = level.getBlockEntity(pos);
			if(BlockEntity instanceof DomeControlEntity entity) {
				IForgeServerPlayer ifpe = (IForgeServerPlayer)player;
				ifpe.openMenu(new DomeControlMenuProvider(pos), pos);
			}else {
				throw new IllegalStateException("missing block-dorm_control");
			}
		}
		return InteractionResult.SUCCESS;
	}
	
	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pBlockState, BlockEntityType<T> pBlockEntityType) {
	     if(pLevel.isClientSide()) {
	    	 return (level,pos,state,blockentity) -> {
	    		 if(blockentity instanceof DomeControlEntity entity) {
	    			 entity.clienttick();
	    		 }
	    	 };
	     }else {
    		 return (level,pos,state,blockentity) -> {
	    		 if(blockentity instanceof DomeControlEntity entity) {
	    			 entity.servertick();
	    		 }
	    	 };
	     }
	 }
	
	 static {
	        BlockJSON.GenModelsJSONBasic(global_name);
	        BlockJSON.GenBlockStateJSONBasic(global_name);
	        BlockJSON.GenItemJSONBasic(global_name);
	        BlockJSON.GenLootTableJSONBasic(global_name);
	 }
}
