package com.block.register;

import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.block.SoundType;

public class BlockElectricBasic {
	public static int BLOCK_ELECTRIC_NUMBER = 1;
	private static int k = 0;
	
    private static final String[] BLOCK_NAME = new String[BLOCK_ELECTRIC_NUMBER];
    private static final MapColor[] MAP_COLORS = new MapColor[BLOCK_ELECTRIC_NUMBER];
    private static final SoundType[] SOUNDS = new SoundType[BLOCK_ELECTRIC_NUMBER];
    private static final float[][] STRENGTHS = new float[BLOCK_ELECTRIC_NUMBER][];
    private static final Boolean[] NEED_TOOL = new Boolean[BLOCK_ELECTRIC_NUMBER];
    private static final String[] TOOL = new String[BLOCK_ELECTRIC_NUMBER];
    private static final Integer[] TOOL_MIN_LEVEL = new Integer[BLOCK_ELECTRIC_NUMBER];
    
    //不一定准
    private static float[] dirt_strength = new float[]{0.5F, 0.5F};
    private static float[] stone_strength = new float[]{2.0F, 6.0F};
    private static float[] ore_strength = new float[]{3.0F, 6.0F};
    private static float[] deep_ore_strength = new float[]{4.0F, 6.0F};
    
    static{
    	addBlock("electric_oven",MapColor.COLOR_GRAY,SoundType.AMETHYST,deep_ore_strength,false,null,0);
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
        for (int i=0;i<BLOCK_ELECTRIC_NUMBER;i++) {
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
	
	
	/*
    private static final Map<Integer, String> BLOCK_NAME = new HashMap<>();
    private static final Map<Integer, MapColor> MAP_COLORS = new HashMap<>();
    private static final Map<Integer, SoundType> SOUNDS = new HashMap<>();
    private static final Map<Integer, float[]> STRENGTHS = new HashMap<>();
    private static final Map<Integer, Boolean> NEED_TOOL = new HashMap<>();
    private static final Map<Integer, String> TOOL = new HashMap<>();
    private static final Map<Integer, Integer> TOOL_MIN_LEVEL = new HashMap<>();
    
    
    static float[] dirt_strength = new float[]{0.5F, 0.5F};
    static float[] stone_strength = new float[]{2.0F, 6.0F};
    static float[] ore_strength = new float[]{3.0F, 6.0F};
    static float[] deep_ore_strength = new float[]{4.0F, 6.0F};
    
    static {
        addBlock(0,"electric_oven",MapColor.COLOR_GRAY,SoundType.STONE,deep_ore_strength,false,"pickaxe",0);
    }
    
    public static int BLOCK_ELECTRIC_NUMBER = BLOCK_NAME.size();
     
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
    */
}
