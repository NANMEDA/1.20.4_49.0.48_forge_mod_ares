package com.effect.register;

import com.effect.EffectFulling;
import com.effect.EffectLosePressure;
import com.effect.EffectMentalAbuse;
import com.effect.EffectOminousLuck;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EffectRegister {
	public static final String MODID = "maring";
	
	public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS,MODID);
	
    public static final RegistryObject<MobEffect> FULLING = EFFECTS.register("fulling", () ->
    new EffectFulling(MobEffectCategory.BENEFICIAL, 0xff3333, false,1));
    public static final RegistryObject<MobEffect> MENTALABUSE = EFFECTS.register("mental_abuse", () ->
    new EffectMentalAbuse(MobEffectCategory.HARMFUL, 0xff0033, false,1));
    public static final RegistryObject<MobEffect> OMINOUSLUCK = EFFECTS.register("ominous_luck", () ->
    new EffectOminousLuck(MobEffectCategory.BENEFICIAL, 0x000033, false,1));
    public static final RegistryObject<MobEffect> LOSEPRESSURE = EFFECTS.register("lose_pressure", () ->
    new EffectLosePressure(MobEffectCategory.HARMFUL, 0x000033, false,1));
    
    
}
