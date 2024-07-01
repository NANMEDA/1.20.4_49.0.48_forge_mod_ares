package com.item.armor;

import com.item.ItemJSON;

import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;

public class ModArmor extends ArmorItem {

    public ModArmor(ArmorMaterial pMaterial, Type pType, Properties pProperties) {
        super(pMaterial, pType, pProperties);
    }

	static {
		ItemJSON.GenJSON("spacesuit_helmet");
		ItemJSON.GenJSON("spacesuit_chestplate");
		ItemJSON.GenJSON("spacesuit_leggings");
		ItemJSON.GenJSON("spacesuit_boots");
	}
	
}