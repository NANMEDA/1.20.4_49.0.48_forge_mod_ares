package com.item.tool;

import com.item.ItemJSON;

import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;

public class OminousHoe extends HoeItem{
	public static String global_name = "ominous_hoe";
	
	public OminousHoe(Tier p_41336_, int p_41337_, float p_41338_, Properties p_41339_) {
		super(ToolTier.OMINOUS, 0, -3.0F,p_41339_);
	}
	static {
		ItemJSON.GenJSON(global_name);
	}

}
