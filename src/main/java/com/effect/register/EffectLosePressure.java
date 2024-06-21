package com.effect.register;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

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
        		living.setHealth(living.getHealth()-3f);
        	}
        //}
    }
    
    @Override
    public boolean isBeneficial() {
        return false;
    }
}
