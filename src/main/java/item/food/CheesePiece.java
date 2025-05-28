package item.food;

import java.util.Optional;

import item.itemFood;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import util.json.ItemJSON;

public class CheesePiece extends Item {

	public static final String global_name = "cheese_piece";

	public CheesePiece(Properties p_41383_) {
		super(p_41383_.food(new FoodProperties.Builder()
			    .nutrition(itemFood.getFoodNutrition(1))
			    .saturationMod(itemFood.getFoodFull(1))
			    .build()));
	}

	
	static {
		ItemJSON.GenJSON(global_name);
	}
}
