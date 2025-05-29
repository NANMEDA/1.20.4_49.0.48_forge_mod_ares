package com.main.maring.effect;

import java.util.Random;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * 效果：精神
 * 播放音效
 * @author NANMEDA
 * */
public class EffectMentalAbuse extends EffectMain {
    private int amplified;

	public EffectMentalAbuse(MobEffectCategory type, int color, boolean isInstant, int amplified) {
        super(type, color, isInstant);
        this.amplified = amplified;
    }
    
    @Override
    protected boolean canApplyEffect(int remainingTicks, int level) {
        return remainingTicks % 60 == 0;//3s
    }
    
    @Override
    public void applyEffectTick(LivingEntity living, int amplified) {
        if (living instanceof Player) {
            Player player = (Player) living;
            int randomNum = new Random().nextInt(10) + 1;//1-10
            String soundPath = "maring:sounds/music/abuse" + randomNum;
            SoundEvent soundEvent = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(soundPath));
            if (soundEvent != null) {
            player.playSound(soundEvent,1.0f, 1.0f);
            }
        }
    }
    
    @Override
    public boolean isBeneficial() {
        return false;
    }
}
