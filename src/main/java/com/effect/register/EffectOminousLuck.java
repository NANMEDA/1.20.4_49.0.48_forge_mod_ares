package com.effect.register;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class EffectOminousLuck extends EffectMain {
    private int amplified;

	public EffectOminousLuck(MobEffectCategory type, int color, boolean isInstant, int amplified) {
        super(type, color, isInstant);
        this.amplified = amplified;
    }
    
    @Override
    protected boolean canApplyEffect(int remainingTicks, int level) {
        return remainingTicks % 10 == 0;//0.5s
    }
    
    @Override
    public void applyEffectTick(LivingEntity living, int amplified) {
        if (living instanceof Player) {
            Player player = (Player) living;
            float currentHealth = player.getHealth();
            if(currentHealth>10) {
            	float newHealth = currentHealth - 1F;
                player.setHealth(newHealth);
            }else if(currentHealth<=0) {
            }
            else if(currentHealth<4) {
                float newHealth = currentHealth + 3.0F;
                player.setHealth(newHealth);
            }else if(currentHealth<8){
            	float newHealth = currentHealth + 1.5F;
                player.setHealth(newHealth);
            }
            
        }
    }
    
    @Override
    public boolean isBeneficial() {
        return true;
    }
}
