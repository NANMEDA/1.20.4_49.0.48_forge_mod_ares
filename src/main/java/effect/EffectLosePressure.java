package effect;

import damage.DamageSourceRegistry;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

/**
 * 效果：失压
 * 火星相关
 * @author NANMEDA
 * */
public class EffectLosePressure extends EffectMain {
    private int amplified;
    
	public EffectLosePressure(MobEffectCategory type, int color, boolean isInstant, int amplified) {
        super(type, color, isInstant);
        this.amplified = amplified;
    }
    
    @Override
    protected boolean canApplyEffect(int remainingTicks, int level) {
        return remainingTicks % 8 == 0;//0.4s
    }
    
    @Override
    public void applyEffectTick(LivingEntity living, int amplified) {
        //if (living instanceof Mob mob) {
        	if(living.isAlive()) {
        		if(!living.level().isClientSide) {
        		    living.hurt(DamageSourceRegistry.getDamageSource(living.level(),DamageSourceRegistry.DAMAGE_SOURCE_PRESSURE), 4);
        		}
        	}
        //}
    }
    
    @Override
    public boolean isBeneficial() {
        return false;
    }
}
