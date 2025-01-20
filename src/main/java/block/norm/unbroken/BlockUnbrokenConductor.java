package block.norm.unbroken;

import javax.annotation.Nullable;

import machine.energy.EnergyEntity;
import machine.energy.consumer.microwaveoven.MicrowaveOvenEntity;
import machine.energy.trans.UnbrokenConductorEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.pathfinder.PathComputationType;
import util.json.BlockJSON;

@SuppressWarnings("deprecation")
public class BlockUnbrokenConductor extends Block implements EntityBlock {
	
	public static final String global_name = "unbroken_conductor";

	public BlockUnbrokenConductor(Properties p_49795_) {
		super(p_49795_
				.strength(-1.0F, 3600000.0f)
				.mapColor(MapColor.TERRACOTTA_WHITE)
				.sound(SoundType.STONE)
				);
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState pBlockState) {
		return new UnbrokenConductorEntity(pos, pBlockState);
	}
	
	@Override
	public boolean isPathfindable(BlockState p_60475_, BlockGetter p_60476_, BlockPos p_60477_, PathComputationType p_60478_) {
		return true;
	}
	
	@SuppressWarnings("deprecation")
	@Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof EnergyEntity e) {
                e.remove(pLevel);
            }
        }

        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }
	
	
	 static {
	        BlockJSON.GenModelsJSONBasic(global_name);
	        BlockJSON.GenBlockStateJSONBasic(global_name);
	        BlockJSON.GenItemJSONBasic(global_name);
	        BlockJSON.GenLootTableJSONBasic(global_name);
	 }

}