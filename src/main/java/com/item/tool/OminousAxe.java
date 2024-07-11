package com.item.tool;

import com.item.ItemJSON;

import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Tier;

public class OminousAxe extends AxeItem{
	public static String global_name = "ominous_axe";
	
	public OminousAxe(Tier p_41336_, float p_41337_, float p_41338_, Properties p_41339_) {
		super(ToolTier.OMINOUS, 6.0F, -3.0F, p_41339_);
	}
	static {
		ItemJSON.GenJSON(global_name);
	}

}
