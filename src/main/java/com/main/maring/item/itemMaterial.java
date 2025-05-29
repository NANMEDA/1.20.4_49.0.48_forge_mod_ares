package com.main.maring.item;

import com.main.maring.util.json.ItemJSON;

import java.util.HashMap;
import java.util.Map;

public class itemMaterial {
/*
    private static final String[] MATERIAL_NAMES = {
        "ominous_gemstone",

        // 基础材料
        "basic_metal_parts",       // 金属铸机 4铁+1金 | 合成表8铁绕1金               // 深蓝色系
        "advanced_metal_parts",    // 精密金属铸机 2金属零件+2青金石 +钻石              // 浅蓝色系
        "bioplastic_parts",        // 塑料合成仪 nutrition 土豆小麦面包/煤炭/log        // 土黄色系
        "semiconductor_parts",     // 电子组装仪 2塑料+钻+红石*8+2金+2铜               // 紫灰色系
        "crystal_parts",           // 晶体生长仪 石英*16 慢+钻石,绿宝石,紫水金碎片可加速 // 橙红色系

        "bottled_methane",         // 一瓶可以烧8个                                    // 红色系
        "big_bottled_methane",     // 容量是上面的64倍，不能直接烧，需要气体压缩机
        "bottled_oxygen",
        "big_bottled_oxygen",
        "bottled_fuel",            // 上面的8倍
        "raw_iron_nugget",  //12

        "insulation_material",
        "insulation_layer",
        "sructure_layer",
        "radiation_layer",
        "rocket_shell",

        "rocket_fuel_tank",
        "rocket_oxygen_tank",
        "rocket_active_space",
        "rocket_cowling",
        "rocket_spout", //22

        "ominous_upgrade_smithing_template",
        "piece_rawgold",
        "piece_obsidian",
        "magnet_suppressor",
        "sucker",
        
        "ominous_gemstone_reactor"
    };

    public static final int ITEM_MATERIAL_NUMBER = MATERIAL_NAMES.length;
    private static final Map<String, Integer> NAME_TO_ID_MAP = new HashMap<>();

    // 静态块初始化 HashMap 和生成 JSON 文件
    static {
        for (int i = 0; i < MATERIAL_NAMES.length; i++) {
            NAME_TO_ID_MAP.put(MATERIAL_NAMES[i], i);
            ItemJSON.GenJSON(MATERIAL_NAMES[i]);
        }
    }

    /**
     * 获取材料名称通过 ID
     *
     * @param id 材料的 ID
     * @return 对应的材料名称，如果 ID 无效则返回 null
     *//*
    public static String getMaterialName(int id) {
        if (id >= 0 && id < ITEM_MATERIAL_NUMBER) {
            return MATERIAL_NAMES[id];
        }
        return null;
    }*/

    /**
     * 获取材料 ID 通过名称
     *
     * @param name 材料名称
     * @return 对应的 ID，如果名称不存在则返回 -1
     */
    /*
    public static int getMaterialId(String name) {
        return NAME_TO_ID_MAP.getOrDefault(name, -1);
    }
*/
    /**
     * 判断材料名称是否存在
     *
     * @param name 材料名称
     * @return 如果存在返回 true，否则返回 false
     */
    /*
    public static boolean isMaterialNameValid(String name) {
        return NAME_TO_ID_MAP.containsKey(name);
    }
*/
}