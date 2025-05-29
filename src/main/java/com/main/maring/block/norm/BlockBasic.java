package com.main.maring.block.norm;

import net.minecraft.world.level.material.MapColor;
import com.main.maring.util.json.BlockJSON;
import net.minecraft.world.level.block.SoundType;
import java.util.HashMap;
import java.util.Map;

/**
 * 添加一些主要的方块
 * 尽量避免修改顺序
 * 因为前面的一部分寻找索引我是根据其位置来寻找的
 * 根据名称寻找索引是后来添加的，之后会删除掉 BLOCK_NAME 这个数组，直接用HASHMAP
 * @author NANMEDA
 * */
public class BlockBasic {
	public static int BLOCK_BASIC_NUMBER = 50;
	private static int k = 0;
	
    private static final String[] BLOCK_NAME = new String[BLOCK_BASIC_NUMBER];
    private static final MapColor[] MAP_COLORS = new MapColor[BLOCK_BASIC_NUMBER];
    private static final SoundType[] SOUNDS = new SoundType[BLOCK_BASIC_NUMBER];
    private static final float[][] STRENGTHS = new float[BLOCK_BASIC_NUMBER][];
    private static final Boolean[] NEED_TOOL = new Boolean[BLOCK_BASIC_NUMBER];
    private static final String[] TOOL = new String[BLOCK_BASIC_NUMBER];
    private static final Integer[] TOOL_MIN_LEVEL = new Integer[BLOCK_BASIC_NUMBER];
    
    static float[] dirt_strength = new float[]{0.5F, 0.5F};
    static float[] stone_strength = new float[]{1.5F, 6.0F};
    static float[] ore_strength = new float[]{3.0F, 3.0F};
    static float[] deep_ore_strength = new float[]{4.5F, 3.0F};
    
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
    	/*
    	addBlock("unbroken_cement",MapColor.TERRACOTTA_WHITE,SoundType.STONE,new float[]{-1.0F, 3600000.0F},false,null,0);
    	addBlock("unbroken_decoration_green",MapColor.COLOR_GREEN,SoundType.STONE,new float[]{-1.0F, 3600000.0F},false,null,0);
    	addBlock("unbroken_decoration_lightblue",MapColor.COLOR_LIGHT_BLUE,SoundType.STONE,new float[]{-1.0F, 3600000.0F},false,null,0);*/
    	addBlock("unbroken_void",MapColor.COLOR_GREEN,SoundType.EMPTY,new float[]{-1.0F, 3600000.0F},false,null,0);
    }//1
    
    static {
    	addBlock("methane_vents", MapColor.COLOR_YELLOW, SoundType.STONE, stone_strength, true, "pickaxe", 0);//还需要 active（在类里面）
    }
    
    /***
     * 洞穴相关的
     * ***/
    static {
    	addBlock("phosphor", MapColor.COLOR_GRAY, SoundType.STONE, stone_strength, true, "pickaxe", 0);	//磷光体
    	addBlock("deep_phosphor", MapColor.COLOR_GRAY, SoundType.STONE, ore_strength, true, "pickaxe", 0);	
    	addBlock("abundant_phosphor", MapColor.COLOR_GRAY, SoundType.STONE, stone_strength, true, "pickaxe", 0);	//富集磷光体
    	addBlock("deep_abundant_phosphor", MapColor.COLOR_GRAY, SoundType.STONE, ore_strength, true, "pickaxe", 0);
    	addBlock("slumber_phosphor", MapColor.COLOR_BLACK, SoundType.STONE, stone_strength, true, "pickaxe", 0);		//沉睡磷光体
    	addBlock("deep_slumber_phosphor", MapColor.COLOR_BLACK, SoundType.STONE, stone_strength, true, "pickaxe", 0);
    	addBlock("quartz_stone", MapColor.TERRACOTTA_WHITE, SoundType.AMETHYST, stone_strength, true, "pickaxe", 1);
    	addBlock("deep_quartz_stone", MapColor.TERRACOTTA_WHITE, SoundType.AMETHYST, stone_strength, true, "pickaxe", 1);
    	addBlock("dead_stone", MapColor.COLOR_BLACK, SoundType.STONE, dirt_strength, true, "pickaxe", 0);
    	addBlock("slumber_stone", MapColor.COLOR_BLACK, SoundType.STONE, stone_strength, true, "pickaxe", 1);
    	addBlock("dry_mucus", MapColor.COLOR_YELLOW, SoundType.SLIME_BLOCK, dirt_strength, false, "shovel", 0);	//干枯粘液
    	addBlock("moist_mucus", MapColor.COLOR_YELLOW, SoundType.HONEY_BLOCK, dirt_strength, false, "shovel", 0);	//湿润粘液
    }//12
    
    /***
     * 火山相关的
     * ***/
    static {
    	addBlock("dense_volcanic_ash", MapColor.COLOR_GRAY, SoundType.STONE, dirt_strength, false, "pickaxe", 0);
    	addBlock("dense_volcanic_ash_obsidian", MapColor.COLOR_GRAY, SoundType.STONE, dirt_strength, false, "pickaxe", 0);
    	addBlock("dense_volcanic_ash_gold", MapColor.COLOR_GRAY, SoundType.STONE, dirt_strength, false, "pickaxe", 0);
    	addBlock("volcanic_stone", MapColor.COLOR_GRAY, SoundType.STONE, stone_strength, true, "pickaxe", 1);
    }
    
    static {
        addBlock("mar_foolgold_ore",MapColor.COLOR_YELLOW,SoundType.STONE,ore_strength,true,"pickaxe",2);
        addBlock("mar_deep_foolgold_ore",MapColor.COLOR_YELLOW,SoundType.STONE,deep_ore_strength,true,"pickaxe",2);
    }
    
    /***
     * 奶酪相关的
     * 
     * ***/
    static {
        addBlock("mar_soft_cheese",MapColor.COLOR_YELLOW,SoundType.BAMBOO,dirt_strength,false,"shovel",0);
        addBlock("mar_hard_cheese",MapColor.COLOR_ORANGE,SoundType.BAMBOO,stone_strength,false,"shovel",0);
        addBlock("mar_glitter_cheese_yellow",MapColor.COLOR_YELLOW,SoundType.BAMBOO,stone_strength,false,"shovel",1);
        addBlock("mar_glitter_cheese_green",MapColor.COLOR_GREEN,SoundType.BAMBOO,stone_strength,false,"shovel",1);
        addBlock("mar_glitter_cheese_blue",MapColor.COLOR_LIGHT_BLUE,SoundType.BAMBOO,stone_strength,false,"shovel",1);
        addBlock("mar_glitter_cheese_red",MapColor.COLOR_RED,SoundType.BAMBOO,stone_strength,false,"shovel",1);
        addBlock("mar_glitter_cheese_orange",MapColor.COLOR_ORANGE,SoundType.BAMBOO,stone_strength,false,"shovel",1);
        addBlock("mar_glitter_cheese_purple",MapColor.COLOR_PURPLE,SoundType.BAMBOO,stone_strength,false,"shovel",1);
    }
    
    /***
     * 先这种通过名字查询id的Map可以多弄几个
     * 在别的像这样注册的类里面
     * 避免通过id查询，然后结果添加或者减少的时候导致 id 变了
     * ***/
    private static final Map<String, Integer> nameToIdMap = new HashMap<>();
    static {
        for (int i = 0; i < BLOCK_BASIC_NUMBER; i++) {
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