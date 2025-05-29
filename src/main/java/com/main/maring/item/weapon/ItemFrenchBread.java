package com.main.maring.item.weapon;

import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import com.main.maring.util.json.ItemJSON;

public class ItemFrenchBread extends SwordItem {
	public static String global_name = "french_bread";

	public ItemFrenchBread(Tier p_43269_, int p_43270_, float p_43271_, Properties p_43272_) {
		super(SwordTier.frenchBread, 4, 1f, p_43272_);
	}
	
	static {
		ItemJSON.GenJSON(global_name);
	}
	
}
