package com.effect.register;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class EffectFulling extends EffectMain {
    private int amplified;

	public EffectFulling(MobEffectCategory type, int color, boolean isInstant, int amplified) {
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
            Player player = (Player) living;
            int currentHunger = player.getFoodData().getFoodLevel();
            int newHunger = currentHunger + (int)(1F * this.amplified);
            float currentSaturation = player.getFoodData().getSaturationLevel();
            float newSaturation = currentSaturation + (int)(1F * this.amplified);
            player.getFoodData().setFoodLevel(Math.min(newHunger, 20));
            player.getFoodData().setSaturation(Math.min(newSaturation, 20));
        }
    }
    
    @Override
    public boolean isBeneficial() {
        return true;
    }
}

