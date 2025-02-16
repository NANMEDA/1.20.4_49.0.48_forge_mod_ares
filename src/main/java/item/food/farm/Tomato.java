package item.food.farm;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.level.block.Block;
import util.json.ItemJSON;

public class Tomato extends ItemNameBlockItem {

	public static final String global_name = "mar_tomato";

	public Tomato(Block block,Properties p_41383_) {
		super(block,p_41383_.food(new FoodProperties.Builder()
			    .nutrition(3)
			    .saturationMod(1)
			    .build()));
	}

	static {
		ItemJSON.GenJSON(global_name);
	}
}
