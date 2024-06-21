package com.item.register;

import java.util.stream.IntStream;

import com.item.register.armor.ModArmor;
import com.item.register.armor.ModArmorMaterials;
import com.item.register.weapon.ItemFrenchBread;
import com.item.register.weapon.SwordTier;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemRegister {
	private static final String MODID = "maring";
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
	
	@SuppressWarnings("unchecked")
	public static final RegistryObject<Item>[] FOOD_ITEMS = new RegistryObject[itemFood.ITEM_FOOD_NUMBER];
	@SuppressWarnings("unchecked")
	public static final RegistryObject<Item>[] MATERIAL_ITEMS = new RegistryObject[itemMaterial.ITEM_MATERIAL_NUMBER];
	
	static {
        IntStream.range(0, itemFood.ITEM_FOOD_NUMBER).forEach(i -> {
        	FoodProperties.Builder foodBuilder = new FoodProperties.Builder()
        	        .nutrition(itemFood.getFoodNutrition(i))
        	        .saturationMod(itemFood.getFoodFull(i));
        	if (itemFood.getFoodEffect(i) != null) {foodBuilder.effect(itemFood.getFoodEffect(i), itemFood.getFoodEffectProbal(i));}
        	if (itemFood.getFoodEat(i)) {foodBuilder.alwaysEat();}
        	FOOD_ITEMS[i] = ITEMS.register(itemFood.getFoodName(i), () -> new Item(new Item.Properties()
        			.food(foodBuilder.build())
        			));
        });
        
        IntStream.range(0, itemMaterial.ITEM_MATERIAL_NUMBER).forEach(i -> {
        	if(i==0) {
        		MATERIAL_ITEMS[i] = ITEMS.register(itemMaterial.getMaterialName(i), () -> new Item(new Item.Properties().stacksTo(4)));
        	}else if(i<6) {
        		MATERIAL_ITEMS[i] = ITEMS.register(itemMaterial.getMaterialName(i), () -> new Item(new Item.Properties().stacksTo(16)));
        	}else {
        		MATERIAL_ITEMS[i] = ITEMS.register(itemMaterial.getMaterialName(i), () -> new Item(new Item.Properties()));
        	}
        });
	}
	
	public static final RegistryObject<Item> FRENCH_BREAD = ITEMS.register(ItemFrenchBread.global_name, () -> new ItemFrenchBread(SwordTier.frenchBread, 4, 1f, new Item.Properties()));

	public static final RegistryObject<Item> SPACESUIT_HELMET = ITEMS.register("spacesuit_helmet",
	//装备材质，装备类型，物品属性
			()->new ModArmor(ModArmorMaterials.SPACESUIT, ArmorItem.Type.HELMET,new Item.Properties().stacksTo(1)));
	//胸甲
	public static final RegistryObject<Item> SPACESUIT_CHESTPLATE = ITEMS.register("spacesuit_chestplate",
			()->new ModArmor(ModArmorMaterials.SPACESUIT, ArmorItem.Type.CHESTPLATE,new Item.Properties().stacksTo(1)));
	//裤子
	public static final RegistryObject<Item> SPACESUIT_LEGGINGS = ITEMS.register("spacesuit_leggings",
			()->new ModArmor(ModArmorMaterials.SPACESUIT, ArmorItem.Type.LEGGINGS,new Item.Properties().stacksTo(1)));
	
	//鞋子
	public static final RegistryObject<Item> SPACESUIT_BOOTS = ITEMS.register("spacesuit_boots",
			()->new ModArmor(ModArmorMaterials.SPACESUIT, ArmorItem.Type.BOOTS,new Item.Properties().stacksTo(1)));
	

}
