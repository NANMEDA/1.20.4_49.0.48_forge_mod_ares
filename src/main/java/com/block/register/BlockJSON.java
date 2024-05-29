package com.block.register;

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
	
	public static void GenModelsJSONBasic(String name) {
	    File file = new File("/forge-1.20.4-49.0.48-mdk/src/main/resources/assets/maring/models/block/" + name + ".json");
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
	
	public static void GenBlockStateJSONBasic(String name) {
	    File file = new File("/forge-1.20.4-49.0.48-mdk/src/main/resources/assets/maring/blockstates/" + name + ".json");
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
	
	public static void GenItemJSONBasic(String name) {
	    File file = new File("/forge-1.20.4-49.0.48-mdk/src/main/resources/assets/maring/models/item/" + name + ".json");
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
	
    public static void GenLootTableJSONBasic(String name) {
        File file = new File("/forge-1.20.4-49.0.48-mdk/src/main/resources/data/maring/loot_tables/blocks/" + name + ".json");
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

    public static void GenToolJSON(String tool, String name) {
        if (tool == null) {
            return;
        }

        File file = new File("/forge-1.20.4-49.0.48-mdk/src/main/resources/data/minecraft/tags/blocks/mineable/" + tool + ".json");

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
    
    public static void GenToolLevelJSON(Integer level, String name) {
    	String tool;
    	switch (level) {
    	    case 0 -> tool = null;
    	    case 1 -> tool = "needs_stone_tool";
    	    case 2 -> tool = "needs_iron_tool";
    	    case 3 -> tool = "needs_diamond_tool";
    	    default -> throw new IllegalArgumentException("Unexpected tool_level needs: level = " + level);
    	}
        if(tool == null) {return;}
        File file = new File("/forge-1.20.4-49.0.48-mdk/src/main/resources/data/minecraft/tags/blocks/" + tool + ".json");

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
