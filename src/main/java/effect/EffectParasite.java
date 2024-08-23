package effect;

import block.norm.BlockBasic;
import block.norm.BlockRegister;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

/**
 * 效果：寄生
 * 和火星孢子地下群系有关
 * @author NANMEDA
 * */
public class EffectParasite extends EffectMain {
	 private int amplified;
	 private static final BlockState AWAKEN = BlockRegister.awakeningstone_BLOCK.get().defaultBlockState();
	 private static final BlockState SLUMBER = BlockRegister.COMMON_BLOCKS[BlockBasic.getIdFromName("slumber_stone")].get().defaultBlockState();
	 private static final BlockState DEAD = BlockRegister.COMMON_BLOCKS[BlockBasic.getIdFromName("dead_stone")].get().defaultBlockState();
	 
	 

		public EffectParasite(MobEffectCategory type, int color, boolean isInstant, int amplified) {
	        super(type, color, isInstant);
	        this.amplified = amplified;
	    }
	    
	    @Override
	    protected boolean canApplyEffect(int remainingTicks, int level) {
	        return remainingTicks % 100 == 0;//5s加1
	    }
	    
	    @Override
	    public void applyEffectTick(LivingEntity living, int amplified) {
	        if (living instanceof Player player) {
	        	Level level = living.level();
	        	if(!level.isClientSide) {
	        		BlockPos pos = player.getOnPos();
	        		BlockState state = level.getBlockState(pos);
	        		if(state == DEAD||state == SLUMBER) {
	        			level.setBlockAndUpdate(pos, AWAKEN);
	        		}
	        	}
	        }
	    }
	    
	    @Override
	    public boolean isBeneficial() {
	        return true;
	    }
}
