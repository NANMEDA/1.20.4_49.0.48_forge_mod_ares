package effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class EffectMain extends MobEffect {
	private boolean instant;
    private boolean isRegistered = false;
    protected float resistancePerLevel = 0.5f;

    public EffectMain(MobEffectCategory type, int color, boolean isInstant) {
        super(type, color);
        this.instant = isInstant;
        
    }
    
    @Override
    public boolean shouldApplyEffectTickThisTick(int remainingTicks, int level) {
        if (isInstantenous()) {
            return true;
        }
        return canApplyEffect(remainingTicks, level);
    }
    
    protected boolean canApplyEffect(int remainingTicks, int level) {
        if (!isInstantenous()) {
            Thread.dumpStack();
        }
        return false;
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (isInstantenous()) {
            applyInstantenousEffect(null, null, entity, amplifier, 1.0d);
        }
    }

    public EffectMain onRegister() {
        isRegistered = true;
        return this;
    }

    public boolean isRegistered() {
        return isRegistered;
    }
	
}