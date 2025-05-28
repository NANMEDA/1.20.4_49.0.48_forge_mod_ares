package util.json;

import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class BlockJSON {
	private static final String toResource = "/forge-1.20.4-49.0.48-mdk/src/main/resources"; 
	
	public static void fastGen(String global_name) {
        GenModelsJSONBasic(global_name);
        GenBlockStateJSONBasic(global_name);
        GenItemJSONBasic(global_name);
        GenLootTableJSONBasic(global_name);
	}
	
	public static void fastGen(String global_name ,String father_name) {
        GenModelsJSONBasic(global_name,"empty");
        GenBlockStateJSONBasic(global_name);
        GenItemJSONBasic(global_name);
        GenLootTableJSONBasic(global_name,father_name);
	}
	
	/***
	 * 生成的是model里面的，默认
	 * ***/
	public static void GenModelsJSONBasic(String name) {
	    File file = new File(toResource+"/assets/maring/models/block/" + name + ".json");
	    if (file.exists()) {
	        return;
	    }
	    JsonObject jsonObject = new JsonObject();
	    jsonObject.addProperty("parent", "minecraft:block/cube_all");
	    
	    JsonObject textures = new JsonObject();
	    textures.addProperty("all", "maring:block/"+name);
	    jsonObject.add("textures", textures);
	    Gson gson = new GsonBuilder().setPrettyPrinting().create();
	    try (FileWriter fileWriter = new FileWriter(file)) {
	        gson.toJson(jsonObject, fileWriter);
	        System.out.println("JSON生成！Block-model:"+name);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	/***
	 * 农model
	 * ***/
	public static void GenModelsJSONFarm(String name) {
	    File file = new File(toResource + "/assets/maring/models/block/" + name + ".json");
	    if (file.exists()) {
	        return;
	    }

	    JsonObject jsonObject = new JsonObject();
	    
	    // Add render type and parent properties
	    jsonObject.addProperty("render_type", "minecraft:cutout");
	    jsonObject.addProperty("parent", "maring:block/crop_dense");

	    JsonObject textures = new JsonObject();
	    textures.addProperty("crop", "maring:block/" + name + "_block_stage0");  // Updated texture path

	    jsonObject.add("textures", textures);

	    Gson gson = new GsonBuilder().setPrettyPrinting().create();
	    try (FileWriter fileWriter = new FileWriter(file)) {
	        gson.toJson(jsonObject, fileWriter);
	        System.out.println("JSON生成！Block-model:" + name);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	
	/***
	 * 生成的use
	 * 比如说, 部分方块要用透明材质
	 * ***/
	public static void GenModelsJSONBasic(String name,String use) {
	    File file = new File(toResource + "/assets/maring/models/block/" + name + ".json");
	    if (file.exists()) {
	        return;
	    }
	    JsonObject jsonObject = new JsonObject();
	    if(use=="empty") {
	    	jsonObject.addProperty("render_type", "minecraft:cutout");
	    }
	    jsonObject.addProperty("parent", "minecraft:block/cube_all");
	    
	    JsonObject textures = new JsonObject();
	    textures.addProperty("all", "maring:block/"+use);
	    jsonObject.add("textures", textures);
	    Gson gson = new GsonBuilder().setPrettyPrinting().create();
	    try (FileWriter fileWriter = new FileWriter(file)) {
	        gson.toJson(jsonObject, fileWriter);
	        System.out.println("JSON生成！Block-model:"+name+":"+use);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	/***
	 * blockstate
	 * ***/
	public static void GenBlockStateJSONBasic(String name) {
	    File file = new File(toResource + "/assets/maring/blockstates/" + name + ".json");
	    if (file.exists()) {
	        return;
	    }
	    JsonObject jsonObject = new JsonObject();
	    
	    // 添加 variants 字段
	    JsonObject variants = new JsonObject();
	    JsonObject emptyVariant = new JsonObject();
	    emptyVariant.addProperty("model", "maring:block/"+name);
	    variants.add("", emptyVariant);
	    jsonObject.add("variants", variants);
	    
	    Gson gson = new GsonBuilder().setPrettyPrinting().create();
	    try (FileWriter fileWriter = new FileWriter(file)) {
	        gson.toJson(jsonObject, fileWriter);
	        System.out.println("JSON生成！Blockstate：" + name);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	/***
	 * blockstate-farm
	 * ***/
	public static void GenBlockStateJSONFarm(String name, int stages) {
	    File file = new File(toResource + "/assets/maring/blockstates/" + name + ".json");
	    if (file.exists()) {
	        return;
	    }
	    
	    JsonObject jsonObject = new JsonObject();
	    JsonObject variants = new JsonObject();
	    
	    // 循环生成每个stage对应的age值和模型路径
	    for (int i = 0; i <= stages; i++) {
	        JsonObject modelObject = new JsonObject();
	        modelObject.addProperty("model", "maring:block/" + name + "_stage" + i);
	        
	        // age值为i
	        variants.add("age=" + i, modelObject);
	    }
	    
	    jsonObject.add("variants", variants);
	    
	    Gson gson = new GsonBuilder().setPrettyPrinting().create();
	    try (FileWriter fileWriter = new FileWriter(file)) {
	        gson.toJson(jsonObject, fileWriter);
	        System.out.println("JSON生成！Blockstate：" + name);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	/***
	 * 物品item的模型
	 * 默认是handheld
	 * ***/
	public static void GenItemJSONBasic(String name) {
	    File file = new File(toResource + "/assets/maring/models/item/" + name + ".json");
	    if (file.exists()) {
	        return;
	    }
	    JsonObject jsonObject = new JsonObject();
	    
	    // 添加 parent 字段
	    jsonObject.addProperty("parent", "maring:block/"+name);
	    
	    Gson gson = new GsonBuilder().setPrettyPrinting().create();
	    try (FileWriter fileWriter = new FileWriter(file)) {
	        gson.toJson(jsonObject, fileWriter);
	        System.out.println("JSON生成！Item：" + name);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	/***
	 * 掉落物，默认自身
	 * ***/
    public static void GenLootTableJSONBasic(String name) {
        File file = new File(toResource + "/data/maring/loot_tables/blocks/" + name + ".json");
        if (file.exists()) {
            return;
        }

        JsonObject lootTable = new JsonObject();
        
        JsonArray poolsArray = new JsonArray();
        JsonObject poolsObject = new JsonObject();
        poolsObject.addProperty("rolls", 1);
        
        JsonArray entriesArray = new JsonArray();
        JsonObject entriesObject = new JsonObject();
        entriesObject.addProperty("type", "minecraft:item");
        entriesObject.addProperty("name", "maring:" + name);
        
        entriesArray.add(entriesObject);
        poolsObject.add("entries", entriesArray);
        
        poolsArray.add(poolsObject);
        lootTable.addProperty("type", "minecraft:block");
        lootTable.add("pools", poolsArray);
        
        try (FileWriter fileWriter = new FileWriter(file)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String jsonString = gson.toJson(lootTable);
            fileWriter.write(jsonString);
            System.out.println("JSON生成！战利表：" + name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /***
     * 掉落物
     * 一般用于
     * 像是机器，拆除旁边的，掉落本体 
     * ***/
    public static void GenLootTableJSONBasic(String name,String loot) {
        File file = new File(toResource + "/data/maring/loot_tables/blocks/" + name + ".json");
        if (file.exists()) {
            return;
        }

        JsonObject lootTable = new JsonObject();
        
        JsonArray poolsArray = new JsonArray();
        JsonObject poolsObject = new JsonObject();
        poolsObject.addProperty("rolls", 1);
        
        JsonArray entriesArray = new JsonArray();
        JsonObject entriesObject = new JsonObject();
        entriesObject.addProperty("type", "minecraft:item");
        entriesObject.addProperty("name", "maring:" + loot);
        
        entriesArray.add(entriesObject);
        poolsObject.add("entries", entriesArray);
        
        poolsArray.add(poolsObject);
        lootTable.addProperty("type", "minecraft:block");
        lootTable.add("pools", poolsArray);
        
        try (FileWriter fileWriter = new FileWriter(file)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String jsonString = gson.toJson(lootTable);
            fileWriter.write(jsonString);
            System.out.println("JSON生成！战利表：" +name+ ":" + loot);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /***
     * 挖掘所需的工具种类
     * ***/
    public static void GenToolJSON(String tool, String name) {
        if (tool == null) {
            return;
        }

        File file = new File(toResource + "/data/minecraft/tags/blocks/mineable/" + tool + ".json");

        if (!file.exists()) {
            //return;
        }

        JsonObject json = new JsonObject();
        json.addProperty("replace", false);

        JsonArray values = new JsonArray();

        try (FileReader reader = new FileReader(file)) {
            Gson gson = new Gson();
            JsonObject existingJson = gson.fromJson(reader, JsonObject.class);
            if (existingJson != null && existingJson.has("values")) {
                JsonArray existingValues = existingJson.getAsJsonArray("values");
                for (JsonElement element : existingValues) {
                    values.add(element);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        JsonPrimitive newElement = new JsonPrimitive("maring:" + name);
        if (!values.contains(newElement)) {
            values.add(newElement);
        }else {
        	return;
        }

        json.add("values", values);

        try (FileWriter fileWriter = new FileWriter(file)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(json, fileWriter);
            System.out.println("JSON 生成修改了"+tool);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /***
     * 挖掘所需的等级
     * ***/
    public static void GenToolLevelJSON(Integer level, String name) {
    	String tool;
    	switch (level) {
    	    case 0 -> tool = null;
    	    case 1 -> tool = "needs_stone_tool";
    	    case 2 -> tool = "needs_iron_tool";
    	    case 3 -> tool = "needs_diamond_tool";
    	    default -> throw new IllegalArgumentException("Unexpected tool_level needs: level = " + level);
    	}
        if(tool == null) return;
        File file = new File(toResource + "/data/minecraft/tags/blocks/" + tool + ".json");

        if (!file.exists()) {
            //return;
        }

        JsonObject json = new JsonObject();
        json.addProperty("replace", false);

        JsonArray values = new JsonArray();

        try (FileReader reader = new FileReader(file)) {
            Gson gson = new Gson();
            JsonObject existingJson = gson.fromJson(reader, JsonObject.class);
            if (existingJson != null && existingJson.has("values")) {
                JsonArray existingValues = existingJson.getAsJsonArray("values");
                for (JsonElement element : existingValues) {
                    values.add(element);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        JsonPrimitive newElement = new JsonPrimitive("maring:" + name);
        if (!values.contains(newElement)) {
            values.add(newElement);
        }else {
        	return;
        }

        json.add("values", values);

        try (FileWriter fileWriter = new FileWriter(file)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(json, fileWriter);
            System.out.println("JSON 生成修改了"+tool);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
