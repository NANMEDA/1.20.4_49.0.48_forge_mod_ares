package tags.register;

import com.item.register.itemFood;

public class AddTag {
	
	public static void init(){
		System.out.println("Come to init tags");
		
		/****************************/
		TagkeyJson.TagCreate(TagkeyJson.createPath("blocks/iron_ores", true),TagkeyJson.createName("mar_iron_ore"));
		TagkeyJson.TagCreate(TagkeyJson.createPath("blocks/iron_ores", true),TagkeyJson.createName("mar_deep_iron_ore"));
		TagkeyJson.TagCreate(TagkeyJson.createPath("blocks/gold_ores", true),TagkeyJson.createName("mar_gold_ore"));
		TagkeyJson.TagCreate(TagkeyJson.createPath("blocks/gold_ores", true),TagkeyJson.createName("mar_deep_gold_ore"));
		TagkeyJson.TagCreate(TagkeyJson.createPath("blocks/lapis_ores", true),TagkeyJson.createName("mar_lapis_ore"));
		TagkeyJson.TagCreate(TagkeyJson.createPath("blocks/lapis_ores", true),TagkeyJson.createName("mar_deep_lapis_ore"));
		TagkeyJson.TagCreate(TagkeyJson.createPath("blocks/redstone_ores", true),TagkeyJson.createName("mar_deep_redstone_ore"));
		TagkeyJson.TagCreate(TagkeyJson.createPath("blocks/redstone_ores", true),TagkeyJson.createName("mar_redstone_ore"));
		TagkeyJson.TagCreate(TagkeyJson.createPath("blocks/emerald_ores", true),TagkeyJson.createName("mar_deep_emerald_ore"));
		/***/
		TagkeyJson.TagCreate(TagkeyJson.createPath("items/stone_tool_materials", true),TagkeyJson.createName("mar_stone"));
		TagkeyJson.TagCreate(TagkeyJson.createPath("items/stone_tool_materials", true),TagkeyJson.createName("mar_deep_stone"));
		/****************************/
		
		/****************************/
		TagkeyJson.TagCreate(TagkeyJson.createPath("blocks/unbreakable_block", false),TagkeyJson.createName("unbroken_cement"));
		TagkeyJson.TagCreate(TagkeyJson.createPath("blocks/unbreakable_block", false),TagkeyJson.createName("unbroken_glass"));
		TagkeyJson.TagCreate(TagkeyJson.createPath("blocks/unbreakable_block", false),TagkeyJson.createName("unbroken_decoration_green"));
		TagkeyJson.TagCreate(TagkeyJson.createPath("blocks/unbreakable_block", false),TagkeyJson.createName("unbroken_decoration_lightblue"));
		/***/
		for(int i=1;i<=9;i++) {
			TagkeyJson.TagCreate(TagkeyJson.createPath("items/can_food", false),TagkeyJson.createName(itemFood.getFoodName(i)));
		}
		/****************************/
	}
	
}
