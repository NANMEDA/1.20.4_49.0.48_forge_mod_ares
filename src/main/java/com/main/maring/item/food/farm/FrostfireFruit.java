package com.main.maring.item.food.farm;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.level.block.Block;
import com.main.maring.util.json.ItemJSON;

public class FrostfireFruit extends ItemNameBlockItem {

	public static final String global_name = "frostfire_fruit";

	public FrostfireFruit(Block block,Properties p_41383_) {
		super(block,p_41383_.food(new FoodProperties.Builder()
			    .nutrition(3)
			    .saturationMod(1)
			    .build()));
	}

	static {
		ItemJSON.GenJSON(global_name);
	}
}
