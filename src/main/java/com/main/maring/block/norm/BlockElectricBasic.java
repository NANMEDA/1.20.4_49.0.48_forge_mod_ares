package com.main.maring.block.norm;

import net.minecraft.world.level.material.MapColor;
import com.main.maring.util.json.BlockJSON;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.world.level.block.SoundType;

/**
 * 添加一些主要的电子相关的方块
 * 尽量避免修改顺序
 * 因为前面的一部分寻找索引我是根据其位置来寻找的
 * 根据名称寻找索引是后来添加的，之后会删除掉 BLOCK_NAME 这个数组，直接用HASHMAP
 * @author NANMEDA
 * */
public class BlockElectricBasic {
	public static int BLOCK_ELECTRIC_NUMBER = 9;
	private static int k = 0;
	
    private static final String[] BLOCK_NAME = new String[BLOCK_ELECTRIC_NUMBER];
    private static final MapColor[] MAP_COLORS = new MapColor[BLOCK_ELECTRIC_NUMBER];
    private static final SoundType[] SOUNDS = new SoundType[BLOCK_ELECTRIC_NUMBER];
    private static final float[][] STRENGTHS = new float[BLOCK_ELECTRIC_NUMBER][];
    private static final Boolean[] NEED_TOOL = new Boolean[BLOCK_ELECTRIC_NUMBER];
    private static final String[] TOOL = new String[BLOCK_ELECTRIC_NUMBER];
    private static final Integer[] TOOL_MIN_LEVEL = new Integer[BLOCK_ELECTRIC_NUMBER];
    
    //private static float[] dirt_strength = new float[]{0.5F, 0.5F};
    //private static float[] stone_strength = new float[]{2.0F, 6.0F};
    private static float[] ore_strength = new float[]{3.0F, 6.0F};
    private static float[] deep_ore_strength = new float[]{4.0F, 6.0F};
    
    static{
    	addBlock("electric_oven",MapColor.COLOR_GRAY,SoundType.AMETHYST,deep_ore_strength,false,null,0);
    	addBlock("villager_handmake_table",MapColor.COLOR_GRAY,SoundType.STONE,deep_ore_strength,false,null,0);
    }
    
    static {
    	addBlock("broken_structure_block",MapColor.COLOR_GRAY,SoundType.AMETHYST,deep_ore_strength,false,null,0);
    	addBlock("broken_chemical_block",MapColor.COLOR_GRAY,SoundType.AMETHYST,ore_strength,false,null,0);
    	addBlock("broken_metal_block",MapColor.COLOR_GRAY,SoundType.AMETHYST,deep_ore_strength,false,null,0);
    	addBlock("broken_electronic_block",MapColor.COLOR_GRAY,SoundType.AMETHYST,ore_strength,false,null,0);
    	addBlock("broken_advanced_electronic_block",MapColor.COLOR_GRAY,SoundType.AMETHYST,deep_ore_strength,false,null,0);
    }
    
    static {
    	addBlock("villager_burried_package",MapColor.COLOR_GRAY,SoundType.WOOL,new float[]{0.2F, 0.5F},false,null,0);
    }
    
    static {
    	addBlock("unbroken_dorm_junction",MapColor.COLOR_GRAY,SoundType.WOOL,new float[]{-1.0F, 3600000.0F},false,null,0);
    }
    
    private static final Map<String, Integer> nameToIdMap = new HashMap<>();
    static {
        for (int i = 0; i < BLOCK_ELECTRIC_NUMBER; i++) {
            nameToIdMap.put(BLOCK_NAME[i], i);
        }
    }
    
    public static Integer getIdFromName(String name) {
        Integer id = nameToIdMap.get(name);
        return (id != null) ? id : 0;
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
	
}
