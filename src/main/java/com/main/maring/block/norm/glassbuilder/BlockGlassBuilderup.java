package com.main.maring.block.norm.glassbuilder;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import com.main.maring.util.json.BlockJSON;


public class BlockGlassBuilderup extends Block{
	public static String global_name = "glass_builder_up"; 
	
	public BlockGlassBuilderup(Properties properties) {
		super(properties);
	}
	
	@Override
	public boolean isPathfindable(BlockState p_60475_, BlockGetter p_60476_, BlockPos p_60477_, PathComputationType p_60478_) {
		return true;
	}
	
	@Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
		return Shapes.join(
				Block.box(0, 0, 0, 16, 14, 16), 
				Block.box(7, 0, 7, 9, 2, 9),  
			    BooleanOp.ONLY_FIRST
			);
    }
	
	@Override
	public InteractionResult use(BlockState blockstate,Level level,BlockPos pos,Player player,InteractionHand interactionhand,BlockHitResult blockHitResult) {
		return level.getBlockState(pos.below()).use(level, player, interactionhand, new BlockHitResult(blockHitResult.getLocation(), blockHitResult.getDirection(), pos.below(), blockHitResult.isInside()));
	}
	
	
	@Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
		pLevel.setBlock(pPos.below(), Blocks.AIR.defaultBlockState(), 2);
    }
	
	
	 static {
	        BlockJSON.GenModelsJSONBasic(global_name,"empty");
	        BlockJSON.GenBlockStateJSONBasic(global_name);
	        BlockJSON.GenItemJSONBasic(global_name);
	        BlockJSON.GenLootTableJSONBasic(global_name,BlockGlassBuilder.global_name);
	 }
}

