package com.main.maring.item.electric;

import net.minecraft.world.item.Item;
import com.main.maring.util.json.ItemJSON;

public class BatteryHeart extends Item {

	public static final String global_name = "battery_heart";

	public BatteryHeart(Properties p_41383_) {
		super(p_41383_);
		// TODO 自动生成的构造函数存根
	}

	static {
		ItemJSON.GenJSON(global_name);
	}
}
