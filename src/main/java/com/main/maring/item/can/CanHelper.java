package com.main.maring.item.can;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.HashMap;
import java.util.Map;

/**
 * 注册了所有需要的食物的属性
 * 假如寻找不到，会返回一个默认值
 * 在 食品罐装机 的Entity中有用
 * @author NANMEDA
 * */
public class CanHelper {
	
    private static final Map<Item, Integer> vegetableMap = new HashMap<>();
    private static final Map<Item, Integer> meatMap = new HashMap<>();
    private static final Map<Item, Integer> fishMap = new HashMap<>();
    private static final Map<Item, Integer> cornMap = new HashMap<>();
    private static final Map<Item, Integer> fruitMap = new HashMap<>();
    private static final Map<Item, Integer> nutritionMap = new HashMap<>();
    private static final Map<Item, Float> saturationMap = new HashMap<>();

 // 初始化方法
    public static void init() {
    	//Vegetable Meat Fish Corn Fruit
    	//小份为1 大份为2
        // 蔬菜类食物
        registerValues(Items.CARROT, 2, 0, 0, 0, 0);
        registerValues(Items.PUMPKIN, 3, 0, 0, 0, 0);
        registerExtra(Items.PUMPKIN, 3, 1F);
        registerValues(Items.CARVED_PUMPKIN, 3, 0, 0, 0, 0);
        registerExtra(Items.CARVED_PUMPKIN, 3, 2F);
        registerValues(Items.BEETROOT, 1, 0, 0, 0, 0);
        registerValues(Items.KELP, 1, 0, 0, 0, 0);
        registerExtra(Items.KELP, 1, 0.3F);
        registerValues(Items.DRIED_KELP, 1, 0, 0, 0, 0);
        registerValues(Items.RED_MUSHROOM, 1, 0, 0, 0, 0);
        registerExtra(Items.RED_MUSHROOM, 1, 0.3F);
        registerValues(Items.BROWN_MUSHROOM, 1, 0, 0, 0, 0);
        registerExtra(Items.BROWN_MUSHROOM, 1, 0.3F);

        // 肉类食物
        registerValues(Items.BEEF, 0, 2, 0, 0, 0);
        registerValues(Items.COOKED_BEEF, 0, 2, 0, 0, 0);
        registerValues(Items.PORKCHOP, 0, 2, 0, 0, 0);
        registerValues(Items.COOKED_PORKCHOP, 0, 2, 0, 0, 0);
        registerValues(Items.CHICKEN, 0, 1, 0, 0, 0);
        registerValues(Items.COOKED_CHICKEN, 0, 1, 0, 0, 0);
        registerValues(Items.MUTTON, 0, 2, 0, 0, 0);
        registerValues(Items.COOKED_MUTTON, 0, 2, 0, 0, 0);
        registerValues(Items.RABBIT, 0, 1, 0, 0, 0);
        registerValues(Items.COOKED_RABBIT, 0, 1, 0, 0, 0);
        registerValues(Items.EGG, 0, 1, 0, 0, 0);
        registerExtra(Items.EGG, 1, 0.5F);

        // 鱼类食物
        registerValues(Items.COD, 0, 1, 1, 0, 0);
        registerValues(Items.COOKED_COD, 0, 1, 1, 0, 0);
        registerValues(Items.SALMON, 0, 1, 1, 0, 0);
        registerValues(Items.COOKED_SALMON, 0, 1, 1, 0, 0);
        registerValues(Items.AXOLOTL_BUCKET, 0, 0, 2, 0, 0);
        registerExtra(Items.AXOLOTL_BUCKET, 6, 4F);

        //主食类
        registerValues(Items.BREAD, 0, 0, 0, 2, 0);
        registerValues(Items.COOKIE, 0, 0, 0, 1, 0);
        registerValues(Items.POTATO, 1, 0, 0, 1, 0);
        registerValues(Items.BAKED_POTATO, 0, 0, 0, 2, 0);
        registerValues(Items.PUMPKIN_PIE, 1, 0, 0, 2, 0);
        
        //水果类
        registerValues(Items.APPLE, 0, 0, 0, 0, 1);
        registerValues(Items.GOLDEN_APPLE, 0, 0, 0, 0, 2);
        registerValues(Items.ENCHANTED_GOLDEN_APPLE, 0, 0, 0, 0, 4);
        registerValues(Items.MELON_SLICE, 0, 0, 0, 0, 1);

    }
    
    public static void registerValues(Item item, int vegetable, int meat, int fish, int corn, int fruit) {
        vegetableMap.put(item, vegetable);
        meatMap.put(item, meat);
        fishMap.put(item, fish);
        cornMap.put(item, corn);
        fruitMap.put(item, fruit);
    }
    
    public static void registerExtra(Item item, int nutrition, float saturation) {
    	nutritionMap.put(item, nutrition);
    	saturationMap.put(item, saturation);
    }

    public static int getVegetableValue(Item item) {
        return vegetableMap.getOrDefault(item, 0);
    }

    public static int getMeatValue(Item item) {
        return meatMap.getOrDefault(item, 0);
    }

    public static int getFishValue(Item item) {
        return fishMap.getOrDefault(item, 0);
    }

    public static int getCornValue(Item item) {
        return cornMap.getOrDefault(item, 0);
    }

    public static int getFruitValue(Item item) {
        return fruitMap.getOrDefault(item, 0);
    }
    
    /***
     * 设置模型
     * 根据不同属性设置model
     * ***/
    public static void setModel(ItemStack stack,int Vegetable,int Meat, int Fish,int Corn,int Fruit) {
        int max = Math.max(Math.max(Math.max(Math.max(Vegetable, Meat), Fish), Corn), Fruit);
        int custom_model_data;
        int maxCount = 0;
        if (Vegetable == max) maxCount++;
        if (Meat == max) maxCount++;
        if (Fish == max) maxCount++;
        if (Corn == max) maxCount++;
        if (Fruit == max) maxCount++;

        if (maxCount > 1) {
        	custom_model_data = 6;
        }

        if (Vegetable == max) {
        	custom_model_data = 1;
        } else if (Meat == max) {
        	custom_model_data = 2;
        } else if (Fish == max) {
        	custom_model_data = 3;
        } else if (Corn == max) {
        	custom_model_data = 4;
        } else if (Fruit == max) {
        	custom_model_data = 5;
        } else {
        	custom_model_data = 6; // 默认情况，虽然理论上不会执行到这里
        }
        stack.getOrCreateTag().putInt("CustomModelData", custom_model_data);
    }

    @SuppressWarnings("deprecation")
	public static int getNutrition(Item item) {
    	if(item.isEdible()) {
    		return item.getFoodProperties().getNutrition();
    	}else {
    		return nutritionMap.getOrDefault(item, 0);
    	}
    }
    
    @SuppressWarnings("deprecation")
	public static float getSaturation(Item item) {
    	if(item.isEdible()) {
    		return item.getFoodProperties().getSaturationModifier();
    	}else {
    		return saturationMap.getOrDefault(item, 0f);
    	}
    }
}