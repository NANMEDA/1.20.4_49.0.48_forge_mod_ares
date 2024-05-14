package com.block.register;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;

public class BlockElectricMachine {
    private static final Map<Integer, String> BLOCK_NAME = new HashMap<>();
    private static final Map<Integer, MapColor> MAP_COLORS = new HashMap<>();
    private static final Map<Integer, SoundType> SOUNDS = new HashMap<>();
    private static final Map<Integer, float[]> STRENGTHS = new HashMap<>();
    private static final Map<Integer, Boolean> NEED_TOOL = new HashMap<>();
    private static final Map<Integer, String> TOOL = new HashMap<>();
    private static final Map<Integer, Integer> TOOL_MIN_LEVEL = new HashMap<>();
    
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
}
