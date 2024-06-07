package com.item.register.weapon;

import com.item.register.ItemJSON;

import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

public class ItemFrenchBread extends SwordItem {
	public static String global_name = "french_bread";

	public ItemFrenchBread(Tier p_43269_, int p_43270_, float p_43271_, Properties p_43272_) {
		super(SwordTier.FrenchBread, 4, 1f, p_43272_);
	}
	
	static {
		ItemJSON.GenJSON(global_name);
	}
	
}
