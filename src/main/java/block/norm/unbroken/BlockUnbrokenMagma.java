package block.norm.unbroken;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import util.json.BlockJSON;

public class BlockUnbrokenMagma extends Block {
	public static final String global_name = "unbroken_magma";

	public BlockUnbrokenMagma(Properties p_49795_) {
		super(p_49795_
				.strength(-1.0F, 3600000.0f)
				.mapColor(MapColor.COLOR_RED)
				.sound(SoundType.STONE)
				);
	}
	
	@Override
	public void stepOn(Level p_153777_, BlockPos p_153778_, BlockState p_153779_, Entity p_153780_) {
		if (!p_153780_.isSteppingCarefully() && p_153780_ instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity)p_153780_)) {
				p_153780_.hurt(p_153777_.damageSources().hotFloor(), 1.0F);
		}
		super.stepOn(p_153777_, p_153778_, p_153779_, p_153780_);
	}
	
    @SuppressWarnings("deprecation")
    @Override
    public InteractionResult use(BlockState blockstate, Level level, BlockPos pos, Player player, InteractionHand interactionhand, BlockHitResult blockHitResult) {
        return super.use(blockstate, level, pos, player, interactionhand, blockHitResult);
    }
    
    static {
    	BlockJSON.fastGen(global_name);
    }
}
