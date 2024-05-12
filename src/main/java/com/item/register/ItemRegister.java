package com.item.register;

import java.util.stream.IntStream;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemRegister {
	private static final String MODID = "maring";
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
	
	public static final RegistryObject<Item>[] FOOD_ITEMS = new RegistryObject[itemFood.ITEM_FOOD_NUMBER];
	static {
        IntStream.range(0, itemFood.ITEM_FOOD_NUMBER).forEach(i -> {
        	FoodProperties.Builder foodBuilder = new FoodProperties.Builder()
        	        .nutrition(itemFood.getFoodNutrition(i))
        	        .saturationMod(itemFood.getFoodFull(i));
        	if (itemFood.getFoodEffect(i) != null) {foodBuilder.effect(itemFood.getFoodEffect(i), itemFood.getFoodEffectProbal(i));}
        	if (itemFood.getFoodEat(i)) {foodBuilder.alwaysEat();}
        	FOOD_ITEMS[i] = ITEMS.register(itemFood.getFoodName(i), () -> new Item(new Item.Properties().food(foodBuilder.build())));
        });
	}
}
