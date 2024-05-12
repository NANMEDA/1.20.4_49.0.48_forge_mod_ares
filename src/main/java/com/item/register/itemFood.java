package com.item.register;

import java.util.HashMap;
import com.item.register.ItemJSON;

import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;
import com.effect.register.EffectRegister;
import com.main.maring.Maring;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.RegistryObject;
import oshi.jna.platform.linux.LinuxLibc.Exit_status;

public class itemFood {
    private static final Map<Integer, String> FOOD_NAME = new HashMap<>();
    private static final Map<Integer, Boolean> CAN_ALWAYS_EAT = new HashMap<>();
    private static final Map<Integer, Integer> NUTRITION = new HashMap<>();
    private static final Map<Integer, Float> FULL = new HashMap<>();
    private static final Map<Integer, Supplier<MobEffectInstance>> EFFECTS = new HashMap<>();
    //下面要用lambda表达式
    private static final Map<Integer, Float> EFFECTS_PROBA = new HashMap<>();

    private static int tick = 20;
    
    static {
        addFoodItem(0, "rubbish", true, 1, 1, () -> new MobEffectInstance(MobEffects.CONFUSION, tick * 30), 1.0F);
        addFoodItem(1, "carrot_can", false, 3, 6, () -> new MobEffectInstance(EffectRegister.FULLING.get(), tick * 10), 1.0F);
        addFoodItem(2, "potato_can", false, 5, 10, () -> new MobEffectInstance(EffectRegister.FULLING.get(), tick * 20), 1.0F);
        addFoodItem(3, "beef_can", false, 8, 12, () -> new MobEffectInstance(EffectRegister.FULLING.get(), tick * 60), 1.0F);
        addFoodItem(4, "pork_can", false, 8, 12, () -> new MobEffectInstance(EffectRegister.FULLING.get(), tick * 60), 1.0F);
        addFoodItem(5, "lamb_can", false, 6, 15, () -> new MobEffectInstance(EffectRegister.FULLING.get(), tick * 40), 1.0F);
        addFoodItem(6, "fish_can", true, 6, 10, () -> new MobEffectInstance(MobEffects.WATER_BREATHING, tick * 90), 1.0F);
        addFoodItem(7, "bread_can", false, 5, 10, () -> new MobEffectInstance(EffectRegister.FULLING.get(), tick * 30), 1.0F);
        addFoodItem(8, "rabbit_can", true, 5, 15, () -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, tick * 60), 1.0F);
        addFoodItem(9, "chicken_can", true, 6, 12, () -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, tick * 40), 1.0F);
    }

    static {
    	addFoodItem(10, "ominous_cake", true, 1, 20, () -> {
    	    int rand = new Random().nextInt(100) + 1;
    	    if (rand <= 30) {
    	        return new MobEffectInstance(EffectRegister.OMINOUSLUCK.get(), tick * 420);
    	    } else if (rand <= 95) {
    	        return new MobEffectInstance(EffectRegister.MENTALABUSE.get(), tick * 100);
    	    } else {
    	        return new MobEffectInstance(MobEffects.BAD_OMEN, tick*900);
    	    }
    	}, 1.0F);
    }

    public static int ITEM_FOOD_NUMBER = FOOD_NAME.size();
    
    static {
    for (Map.Entry<Integer, String> entry : FOOD_NAME.entrySet()) {
        String name = entry.getValue();
        ItemJSON.GenJSON(name);
    }
    }
    
    public static void addFoodItem(int id, String name, boolean can_eat, int nutrition, float full, Supplier<MobEffectInstance> effectSupplier, float effect_time) {
        FOOD_NAME.put(id, name);
        CAN_ALWAYS_EAT.put(id, can_eat);
        NUTRITION.put(id, nutrition);
        FULL.put(id, full);
        EFFECTS.put(id, effectSupplier);
        EFFECTS_PROBA.put(id, effect_time);
    }
    public static String getFoodName(int id) {
        return FOOD_NAME.get(id);
    }

    public static Boolean getFoodEat(int id) {
        return CAN_ALWAYS_EAT.get(id);
    }

    public static Integer getFoodNutrition(int id) {
        return NUTRITION.get(id);
    }

    public static Float getFoodFull(int id) {
        return FULL.get(id);
    }

    public static Supplier<MobEffectInstance> getFoodEffect(int id) {
        return EFFECTS.get(id);
    }
    public static Float getFoodEffectProbal(int id) {
        return EFFECTS_PROBA.get(id);
    }
}