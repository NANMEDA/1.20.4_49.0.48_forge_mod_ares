package com.effect;

import com.google.j2objc.annotations.ReflectionSupport.Level;

import damage.DamageSourceRegistry;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import vehicle.rocket.RocketEntity;


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
