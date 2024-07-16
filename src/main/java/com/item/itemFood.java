package com.item;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;
import com.effect.register.EffectRegister;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

public class itemFood {
    private static final Map<Integer, String> FOOD_NAME = new HashMap<>();
    private static final Map<Integer, Boolean> CAN_ALWAYS_EAT = new HashMap<>();
    private static final Map<Integer, Integer> NUTRITION = new HashMap<>();
    private static final Map<Integer, Float> FULL = new HashMap<>();
    private static final Map<Integer, Supplier<MobEffectInstance>> EFFECTS = new HashMap<>();
    //下面要用lambda表达式
    private static final Map<Integer, Float> EFFECTS_PROBA = new HashMap<>();

    private static int ticks = 20;
    private static int minutes = 1200;
    
    /***
     * 已废弃
     * 但尚未删除
     * ***/
    static {
        addFoodItem(0, "rubbish", true, 1, 1, () -> new MobEffectInstance(MobEffects.CONFUSION, ticks * 30), 1.0F);
        addFoodItem(1, "carrot_can", false, 3, 6, () -> new MobEffectInstance(EffectRegister.FULLING.get(), ticks * 10), 1.0F);
        addFoodItem(2, "potato_can", false, 5, 10, () -> new MobEffectInstance(EffectRegister.FULLING.get(), ticks * 20), 1.0F);
        addFoodItem(3, "beef_can", false, 8, 12, () -> new MobEffectInstance(EffectRegister.FULLING.get(), ticks * 60), 1.0F);
        addFoodItem(4, "pork_can", false, 8, 12, () -> new MobEffectInstance(EffectRegister.FULLING.get(), ticks * 60), 1.0F);
        addFoodItem(5, "lamb_can", false, 6, 15, () -> new MobEffectInstance(EffectRegister.FULLING.get(), ticks * 40), 1.0F);
        addFoodItem(6, "fish_can", true, 6, 10, () -> new MobEffectInstance(MobEffects.WATER_BREATHING, ticks * 90), 1.0F);
        addFoodItem(7, "bread_can", false, 5, 10, () -> new MobEffectInstance(EffectRegister.FULLING.get(), ticks * 30), 1.0F);
        addFoodItem(8, "rabbit_can", true, 5, 15, () -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, ticks * 60), 1.0F);
        addFoodItem(9, "chicken_can", true, 6, 12, () -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, ticks * 40), 1.0F);
    }

    static {
    	addFoodItem(10, "ominous_cake", true, 1, 20, () -> {
    	    int rand = new Random().nextInt(100) + 1;
    	    if (rand <= 30) {
    	        return new MobEffectInstance(EffectRegister.OMINOUSLUCK.get(), minutes * 7);
    	    } else if (rand <= 95) {
    	        return new MobEffectInstance(EffectRegister.MENTALABUSE.get(), ticks * 100);
    	    } else {
    	        return new MobEffectInstance(MobEffects.BAD_OMEN, minutes * 15);
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