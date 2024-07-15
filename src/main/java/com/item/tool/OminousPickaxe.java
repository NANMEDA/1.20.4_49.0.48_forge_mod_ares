package com.item.tool;

import com.item.ItemJSON;

import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;

public class OminousPickaxe extends PickaxeItem{
	public static String global_name = "ominous_pickaxe";
	
	public OminousPickaxe(Tier p_41336_, int p_41337_, float p_41338_, Properties p_41339_) {
		super(ToolTier.OMINOUS, 1, -2.8F, p_41339_);
	}
	static {
		ItemJSON.GenJSON(global_name);
	}

}
