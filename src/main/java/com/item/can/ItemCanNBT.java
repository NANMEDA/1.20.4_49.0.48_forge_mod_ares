package com.item.can;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class ItemCanNBT {

    public static final String TAG_NUTRITION = "Nutrition";
    public static final String TAG_SATURATION = "Full";
    public static final String TAG_VEGETABLE = "Vegetable";
    public static final String TAG_MEAT = "Meat";
    public static final String TAG_FISH = "Fish";
    public static final String TAG_CORN = "Corn";
    public static final String TAG_FRUIT = "Fruit";

    public static CompoundTag getTagSafe(ItemStack stack) {
        return stack.getOrCreateTag();
    }

    public static int getNutrition(ItemStack stack) {
        CompoundTag compound = getTagSafe(stack);
        return compound.getInt(TAG_NUTRITION);
    }

    public static void setNutrition(ItemStack stack, int nutrition) {
        getTagSafe(stack).putInt(TAG_NUTRITION, nutrition);
    }

    public static float getSaturation(ItemStack stack) {
        CompoundTag compound = getTagSafe(stack);
        return compound.getFloat(TAG_SATURATION);
    }

    public static void setSaturation(ItemStack stack, float full) {
        getTagSafe(stack).putFloat(TAG_SATURATION, full);
    }

    public static int getVegetable(ItemStack stack) {
        CompoundTag compound = getTagSafe(stack);
        return compound.getInt(TAG_VEGETABLE);
    }

    public static void setVegetable(ItemStack stack, int vegetable) {
        getTagSafe(stack).putInt(TAG_VEGETABLE, vegetable);
    }

    public static int getMeat(ItemStack stack) {
        CompoundTag compound = getTagSafe(stack);
        return compound.getInt(TAG_MEAT);
    }

    public static void setMeat(ItemStack stack, int meat) {
        getTagSafe(stack).putInt(TAG_MEAT, meat);
    }

    public static int getFish(ItemStack stack) {
        CompoundTag compound = getTagSafe(stack);
        return compound.getInt(TAG_FISH);
    }

    public static void setFish(ItemStack stack, int fish) {
        getTagSafe(stack).putInt(TAG_FISH, fish);
    }

    public static int getCorn(ItemStack stack) {
        CompoundTag compound = getTagSafe(stack);
        return compound.getInt(TAG_CORN);
    }

    public static void setCorn(ItemStack stack, int corn) {
        getTagSafe(stack).putInt(TAG_CORN, corn);
    }

    public static int getFruit(ItemStack stack) {
        CompoundTag compound = getTagSafe(stack);
        return compound.getInt(TAG_FRUIT);
    }

    public static void setFruit(ItemStack stack, int fruit) {
        getTagSafe(stack).putInt(TAG_FRUIT, fruit);
    }
}
