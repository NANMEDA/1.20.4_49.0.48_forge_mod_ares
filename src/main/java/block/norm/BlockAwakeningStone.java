package block.norm;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;


import net.minecraft.world.entity.player.Player;
import net.minecraft.world.effect.MobEffectInstance;

public class BlockAwakeningStone extends Block{
	public static String global_name = "awakening_stone"; 
	
	public BlockAwakeningStone(Properties p_49795_) {
		super(p_49795_);
	}
	
	@SuppressWarnings("deprecation")
	@Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
		
	    super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);

	    VoxelShape voxelShape = Shapes.create(
	            pPos.getX() - 2, pPos.getY() - 2, pPos.getZ() - 2,
	            pPos.getX() + 2, pPos.getY() + 2, pPos.getZ() + 2);

        pLevel.getEntitiesOfClass(Player.class, voxelShape.bounds())
                .forEach(player -> {
                    player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 200)); 
                });
    }
}
