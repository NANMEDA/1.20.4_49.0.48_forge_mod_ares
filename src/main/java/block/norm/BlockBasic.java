package block.norm;

import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.block.SoundType;

public class BlockBasic {
	public static int BLOCK_BASIC_NUMBER = 27;
	private static int k = 0;
	
    private static final String[] BLOCK_NAME = new String[BLOCK_BASIC_NUMBER];
    private static final MapColor[] MAP_COLORS = new MapColor[BLOCK_BASIC_NUMBER];
    private static final SoundType[] SOUNDS = new SoundType[BLOCK_BASIC_NUMBER];
    private static final float[][] STRENGTHS = new float[BLOCK_BASIC_NUMBER][];
    private static final Boolean[] NEED_TOOL = new Boolean[BLOCK_BASIC_NUMBER];
    private static final String[] TOOL = new String[BLOCK_BASIC_NUMBER];
    private static final Integer[] TOOL_MIN_LEVEL = new Integer[BLOCK_BASIC_NUMBER];
    
    //不一定准
    static float[] dirt_strength = new float[]{0.5F, 0.5F};
    static float[] stone_strength = new float[]{2.0F, 6.0F};
    static float[] ore_strength = new float[]{3.0F, 6.0F};
    static float[] deep_ore_strength = new float[]{4.0F, 6.0F};
    
    static{
        addBlock("mar_surface", MapColor.COLOR_ORANGE, SoundType.SAND,dirt_strength, false, "shovel", 0);
        addBlock("mar_baked_surface", MapColor.COLOR_RED, SoundType.SAND,dirt_strength, false, "shovel", 1);
        addBlock("mar_sand", MapColor.COLOR_ORANGE, SoundType.SAND,dirt_strength, false, "shovel", 0);
        addBlock("mar_mud", MapColor.COLOR_ORANGE, SoundType.SAND,dirt_strength, false, "shovel", 0);
        //4
        addBlock("mar_stone", MapColor.COLOR_ORANGE, SoundType.STONE,stone_strength, true, "pickaxe", 0);
        addBlock("mar_sedimentary_stone", MapColor.COLOR_ORANGE, SoundType.STONE,stone_strength, true, "pickaxe", 0);
        addBlock("mar_sulfur_stone", MapColor.COLOR_ORANGE, SoundType.STONE,stone_strength, true, "pickaxe", 0);
        addBlock("mar_saltstone", MapColor.COLOR_ORANGE, SoundType.STONE,stone_strength, true, "pickaxe", 0);        
        //4
        addBlock("mar_deep_stone",MapColor.COLOR_RED,SoundType.STONE,ore_strength,true,"pickaxe",0);
        addBlock("mar_deep_sulfur_stone",MapColor.COLOR_RED,SoundType.STONE,ore_strength,true,"pickaxe",0);
        //2
        addBlock("mar_iron_ore",MapColor.COLOR_LIGHT_GRAY,SoundType.STONE,ore_strength,true,"pickaxe",1);
        addBlock("mar_deep_iron_ore",MapColor.COLOR_GRAY,SoundType.STONE,deep_ore_strength,true,"pickaxe",1);
        addBlock("mar_gold_ore",MapColor.COLOR_YELLOW,SoundType.STONE,ore_strength,true,"pickaxe",2);
        addBlock("mar_deep_gold_ore",MapColor.COLOR_YELLOW,SoundType.STONE,deep_ore_strength,true,"pickaxe",2);
        addBlock("mar_copper_ore",MapColor.COLOR_ORANGE,SoundType.STONE,ore_strength,true,"pickaxe",1);
        addBlock("mar_deep_copper_ore",MapColor.COLOR_ORANGE,SoundType.STONE,deep_ore_strength,true,"pickaxe",1);
        addBlock("mar_lapis_ore",MapColor.COLOR_LIGHT_BLUE,SoundType.STONE,ore_strength,true,"pickaxe",1);
        addBlock("mar_deep_lapis_ore",MapColor.COLOR_BLUE,SoundType.STONE,deep_ore_strength,true,"pickaxe",1);
        addBlock("mar_redstone_ore",MapColor.COLOR_RED,SoundType.STONE,ore_strength,true,"pickaxe",2);
        addBlock("mar_deep_redstone_ore",MapColor.COLOR_RED,SoundType.STONE,deep_ore_strength,true,"pickaxe",2);
        addBlock("mar_deep_emerald_ore",MapColor.COLOR_GREEN,SoundType.STONE,deep_ore_strength,true,"pickaxe",2);  
        addBlock("mar_deep_ominous_ore",MapColor.COLOR_BLACK,SoundType.STONE,new float[]{20.0F, 50.0F},true,"pickaxe",3);
        //12
    }//22
    
    static {
    	addBlock("unbroken_cement",MapColor.TERRACOTTA_WHITE,SoundType.STONE,new float[]{-1.0F, -1.0F},false,null,0);
    	addBlock("unbroken_decoration_green",MapColor.COLOR_GREEN,SoundType.STONE,new float[]{-1.0F, -1.0F},false,null,0);
    	addBlock("unbroken_decoration_lightblue",MapColor.COLOR_LIGHT_BLUE,SoundType.STONE,new float[]{-1.0F, -1.0F},false,null,0);
    	addBlock("unbroken_void",MapColor.COLOR_GREEN,SoundType.EMPTY,new float[]{-1.0F, -1.0F},false,null,0);
    }
    
    static {
    	addBlock("methane_vents", MapColor.COLOR_ORANGE, SoundType.STONE, stone_strength, true, "pickaxe", 0);
    }
    

   
    private static void addBlock(String name, MapColor color, SoundType sound, float[] strength, Boolean tool, String tools, int level) {
    	BLOCK_NAME[k] = name;
        MAP_COLORS[k] = color;
        SOUNDS[k] = sound;
        STRENGTHS[k] = strength;
        NEED_TOOL[k] = tool;
        TOOL[k] = tools;
        TOOL_MIN_LEVEL[k] = level;
        k++;
    }
    
    static {
        for (int i=0;i<BLOCK_BASIC_NUMBER;i++) {
        	String name = BLOCK_NAME[i];
            BlockJSON.GenModelsJSONBasic(name);
            BlockJSON.GenBlockStateJSONBasic(name);
            BlockJSON.GenItemJSONBasic(name);
            BlockJSON.GenLootTableJSONBasic(name);
            BlockJSON.GenToolJSON(TOOL[i], name);
            BlockJSON.GenToolLevelJSON(TOOL_MIN_LEVEL[i], name);
        	}
    }

    public static String getBlockName(int id) {
        return BLOCK_NAME[id];
    }

    public static MapColor getBlockMapColor(int id) {
        return MAP_COLORS[id];
    }

    public static SoundType getBlockSound(int id) {
        return SOUNDS[id];
    }

    public static float[] getBlockStrength(int id) {
        return STRENGTHS[id];
    }

    public static Boolean needTool(int id) {
        return NEED_TOOL[id];
    }

    public static String getTool(int id) {
        return TOOL[id];
    }

    public static Integer getToolLevel(int id) {
        return TOOL_MIN_LEVEL[id];
    }
}
/*
public class BlockBasic {
    private static final Map<Integer, String> BLOCK_NAME = new HashMap<>();
    private static final Map<Integer, MapColor> MAP_COLORS = new HashMap<>();
    private static final Map<Integer, SoundType> SOUNDS = new HashMap<>();
    private static final Map<Integer, float[]> STRENGTHS = new HashMap<>();
    private static final Map<Integer, Boolean> NEED_TOOL = new HashMap<>();
    private static final Map<Integer, String> TOOL = new HashMap<>();
    private static final Map<Integer, Integer> TOOL_MIN_LEVEL = new HashMap<>();
    //用hashmap会不会太浪费了？
    
    //参考
    //泥土0.5 0.5
    //石头  2 6
    //末地岩 2 45
    //黑曜石 50 ？
    //矿石、深版岩 3 ？
    //好像都不是很准的样子,总体大差不差
    static float[] dirt_strength = new float[]{0.5F, 0.5F};
    static float[] stone_strength = new float[]{2.0F, 6.0F};
    static float[] ore_strength = new float[]{3.0F, 6.0F};
    static float[] deep_ore_strength = new float[]{4.0F, 6.0F};
    
    static {
        addBlock(0,"mar_surface",MapColor.COLOR_ORANGE,SoundType.SAND,dirt_strength,false,"shovel",0);
        addBlock(1,"mar_stone",MapColor.COLOR_RED,SoundType.STONE,stone_strength,true,"pickaxe",0);
        addBlock(2,"mar_deep_stone",MapColor.COLOR_RED,SoundType.STONE,ore_strength,true,"pickaxe",0);
        addBlock(3,"mar_iron_ore",MapColor.COLOR_LIGHT_GRAY,SoundType.STONE,ore_strength,true,"pickaxe",1);
        addBlock(4,"mar_deep_iron_ore",MapColor.COLOR_GRAY,SoundType.STONE,deep_ore_strength,true,"pickaxe",1);
        addBlock(5,"mar_gold_ore",MapColor.COLOR_LIGHT_GRAY,SoundType.STONE,ore_strength,true,"pickaxe",2);
        addBlock(6,"mar_deep_gold_ore",MapColor.COLOR_GRAY,SoundType.STONE,deep_ore_strength,true,"pickaxe",2);
        addBlock(7,"mar_copper_ore",MapColor.COLOR_ORANGE,SoundType.STONE,ore_strength,true,"pickaxe",1);
        addBlock(8,"mar_deep_copper_ore",MapColor.COLOR_ORANGE,SoundType.STONE,deep_ore_strength,true,"pickaxe",1);
        addBlock(9,"mar_deep_emerald_ore",MapColor.COLOR_GREEN,SoundType.STONE,deep_ore_strength,true,"pickaxe",2);    
    }
    
    static {
    	addBlock(10,"mar_deep_ominous_ore",MapColor.COLOR_BLACK,SoundType.STONE,new float[]{20.0F, 50.0F},true,"pickaxe",3);
    	addBlock(11,"unbroken_cement",MapColor.TERRACOTTA_WHITE,SoundType.STONE,new float[]{-1.0F, -1.0F},false,null,0);
    	addBlock(12,"unbroken_glass",MapColor.NONE,SoundType.GLASS,new float[]{-1.0F, -1.0F},false,null,0);
    	addBlock(13,"unbroken_decoration_green",MapColor.COLOR_GREEN,SoundType.STONE,new float[]{-1.0F, -1.0F},false,null,0);
    	addBlock(14,"unbroken_decoration_lightblue",MapColor.COLOR_LIGHT_BLUE,SoundType.STONE,new float[]{-1.0F, -1.0F},false,null,0);
    }
    
    public static int BLOCK_BASIC_NUMBER = BLOCK_NAME.size();
     
    static {
    for (Map.Entry<Integer, String> entry : BLOCK_NAME.entrySet()) {
        String name = entry.getValue();
        BlockJSON.GenModelsJSONBasic(name);
        BlockJSON.GenBlockStateJSONBasic(name);
        BlockJSON.GenItemJSONBasic(name);
        BlockJSON.GenLootTableJSONBasic(name);
        BlockJSON.GenToolJSON(Tools(entry.getKey()), name);
        BlockJSON.GenToolLevelJSON(getToolLevel(entry.getKey()), name);
    	}
    }
    
    private static void addBlock(
    		int id, String name, MapColor color, SoundType sound,float[] strength,Boolean tool,String tools,int level) {
    	BLOCK_NAME.put(id, name);
        MAP_COLORS.put(id, color);
        SOUNDS.put(id, sound);
        STRENGTHS.put(id, strength);
        NEED_TOOL.put(id, tool);
        TOOL.put(id, tools);
        TOOL_MIN_LEVEL.put(id,level);
    }

    public static String getBlockName(int id) {
        return BLOCK_NAME.get(id);
    }
    public static MapColor getBlockMapColor(int id) {
        return MAP_COLORS.get(id);
    }
    public static SoundType getBlockSound(int id) {
        return SOUNDS.get(id);
    }
    public static float[] getBlockStrength(int id) {
        return STRENGTHS.get(id);
    }
    public static Boolean needTool(int id) {
        return NEED_TOOL.get(id);
    }
    public static String Tools(int id) {
        return TOOL.get(id);
    }
    public static Integer getToolLevel(int id) {
        return TOOL_MIN_LEVEL.get(id);
    }
}
*/