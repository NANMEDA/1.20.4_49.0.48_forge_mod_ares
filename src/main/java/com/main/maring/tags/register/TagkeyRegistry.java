package com.main.maring.tags.register;

import com.main.maring.Maring;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

/**
 * 自定义的tag在这里方便找
 * */
public class TagkeyRegistry {
	
	public static TagKey<Block> UNBREAKABLE_BLOCK_TAG;
	public static TagKey<Item> CAN_FOOD_TAG;
	public static TagKey<Item> CAN_FOOD_MATERIAL_TAG;
	public static TagKey<Item> FUEL_BUCKET_TAG;

	public static void init() {
		UNBREAKABLE_BLOCK_TAG = BlockTags.create(new ResourceLocation(Maring.MODID, "unbreakable_block"));
		
		
		CAN_FOOD_TAG = ItemTags.create(new ResourceLocation(Maring.MODID, "can_food"));
		CAN_FOOD_MATERIAL_TAG = ItemTags.create(new ResourceLocation(Maring.MODID, "can_food_material"));
		FUEL_BUCKET_TAG = ItemTags.create(new ResourceLocation(Maring.MODID, "fuel_bucket"));


	}
	
}