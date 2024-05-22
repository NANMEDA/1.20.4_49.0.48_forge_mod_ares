package com.item.register;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import com.effect.register.EffectRegister;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

public class itemMaterial {
	   private static final Map<Integer, String> MATERIAL_NAME = new HashMap<>();
	   
	   static {
		   addMaterialItem(0, "ominous_gemstone");
		   addMaterialItem(1, "basic_metal_parts");//金属铸机
		   addMaterialItem(2, "advanced_metal_parts");//精密金属铸机
		   addMaterialItem(3, "bioplastic_parts");//塑料合成仪
		   addMaterialItem(4, "semiconductor_parts");//电子组装仪
		   addMaterialItem(5, "bottled_methane");//一瓶可以烧8个
		   addMaterialItem(6, "big_bottled_methane");//建议容量是上面的64倍，不能直接拿来烧
		   addMaterialItem(7, "bottled_oxygen");
		   addMaterialItem(8, "big_bottled_oxygen");
	    }
	   
	   public static int ITEM_MATERIAL_NUMBER = MATERIAL_NAME.size();
	    
	   static {
	    	for (Map.Entry<Integer, String> entry : MATERIAL_NAME.entrySet()) {	
	    		String name = entry.getValue();
	        	ItemJSON.GenJSON(name);
	    	}
	    }
	    
	    public static void addMaterialItem(int id, String name) {
	    	MATERIAL_NAME.put(id, name);
	    }
	    public static String getMaterialName(int id) {
	        return MATERIAL_NAME.get(id);
	    }
}
