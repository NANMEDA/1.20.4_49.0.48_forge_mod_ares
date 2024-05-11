package com.item.register;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class ItemJSON {
    
    public static void GenJSON(String name) {
        File file = new File("/forge-1.20.4-49.0.48-mdk/src/main/resources/assets/maring/models/item/"+name+".json");
        if (file.exists()) {
            return;
        }
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("parent", "minecraft:item/handheld");
        
        JsonObject textures = new JsonObject();
        textures.addProperty("layer0", "maring:item/" + name);
        jsonObject.add("textures", textures);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter fileWriter = new FileWriter(file)) {
            gson.toJson(jsonObject, fileWriter);
            System.out.println("JSON生成！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
