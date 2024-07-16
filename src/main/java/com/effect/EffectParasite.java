package com.effect;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

/**
 * 效果：寄生
 * 和火星孢子地下群系有关
 * @author NANMEDA
 * */
public class EffectParasite extends EffectMain{
	 private int amplified;

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
	        if (living instanceof Player) {
	        	
	        }
	    }
	    
	    @Override
	    public boolean isBeneficial() {
	        return true;
	    }
}
