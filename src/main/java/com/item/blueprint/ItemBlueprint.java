package com.item.blueprint;

import com.item.ItemJSON;
import com.item.can.ItemCanNBT;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ItemBlueprint extends Item {

	public static final String global_name = "blue_print";
	public String content;
	
	public ItemBlueprint(Properties p_41383_) {
		super(p_41383_);
		this.content = null;
	}
	
	@Override
    public Component getName(ItemStack stack) {
    	String content = ItemBlueprintNBT.getContent(stack);
    	MutableComponent name = Component.empty();
    	
    	if(content!=null && content!= "null") {
    		name.append(Component.translatable(content));
    	}
        return name.append(Component.translatable("blueprint.last"));
     }
	
	static {
		ItemJSON.GenJSON(global_name);
	}

}