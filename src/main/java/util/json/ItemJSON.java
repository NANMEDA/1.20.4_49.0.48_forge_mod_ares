package util.json;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class ItemJSON {
	private static final String toResource = "/forge-1.20.4-49.0.48-mdk/src/main/resources"; 
	
	/***
	 * 生成的是handheld的Item模型
	 * 和Block那里的不同
	 * 不可以混用
	 * ***/
    public static void GenJSON(String name) {
        File file = new File(toResource + "/assets/maring/models/item/"+name+".json");
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
