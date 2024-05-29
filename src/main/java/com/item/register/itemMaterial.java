package com.item.register;


public class itemMaterial {
	
    private static final String[] MATERIAL_NAMES = {
        "ominous_gemstone",
        
        "basic_metal_parts", // 金属铸机	4铁+1金|合成表8铁绕1金							//深蓝色系
        "advanced_metal_parts", // 精密金属铸机 2金属零件+2青金石	+钻石					//浅蓝色系
        "bioplastic_parts", // 塑料合成仪 nutrition土豆小麦面包/煤炭/log					//土黄色系
        "semiconductor_parts", // 电子组装仪	2塑料+钻+红石*8+2金+2铜						//紫灰色系
        "crystal_parts", //晶体生长仪 石英*16 慢+钻石,绿宝石,紫水金碎片可加速(不用电用水)	//橙红色系
        //这五个是最基础的，所有衍生的基本都应该可以由这5个构成
        //合成表尽量可以直接从这5种基本材料+原版材料出发， 避免合成了A，用A合成B，用B合成C，用C合成D这种长链的
        //举个例子，避免像IC 中 铜锭->铜板->铜线->绝缘导线->电子元件->高级电子元件->高级**材料->高级**机械 这种
        
        "bottled_methane", // 一瓶可以烧8个												//红色系
        "big_bottled_methane", // 建议容量是上面的64倍，不能直接拿来烧,put 气体压缩机
        "bottled_oxygen",
        "big_bottled_oxygen",
        "raw_iron_nugget"
    };

    public static final int ITEM_MATERIAL_NUMBER = MATERIAL_NAMES.length;

    static {
        for (String name : MATERIAL_NAMES) {
            ItemJSON.GenJSON(name);
        }
    }

    public static String getMaterialName(int id) {
        if (id >= 0 && id < ITEM_MATERIAL_NUMBER) {
            return MATERIAL_NAMES[id];
        }
        return null;
    }
    
    
    
    /*
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
     */
}
