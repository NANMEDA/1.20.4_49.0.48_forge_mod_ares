package tags.register;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class TagkeyJson {
	private static final String MODID = "maring";
	protected static String createPath(String path_simple,boolean is_vanilla) {
		if(is_vanilla) {
			return "/forge-1.20.4-49.0.48-mdk/src/main/resources/data/minecraft/tags/" + path_simple +".json";
		}else {
			return "/forge-1.20.4-49.0.48-mdk/src/main/resources/data/"+MODID+"/tags/" + path_simple +".json";	
		}
	}
	protected static String createName(String name_simple) {
		return MODID + ":" + name_simple;
	}
	
	protected static void TagCreate(String path, String name) {
        File file = new File(path);
        JsonObject tagJson = new JsonObject();
        Gson GSON = new GsonBuilder().setPrettyPrinting().create();
        if (file.exists()) {
            try (FileReader reader = new FileReader(file)) {
                tagJson = JsonParser.parseReader(reader).getAsJsonObject();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            file.getParentFile().mkdirs();
        }

        if (!tagJson.has("replace")) {
            tagJson.addProperty("replace", false);
        }

        JsonArray valuesArray;
        if (tagJson.has("values")) {
            valuesArray = tagJson.getAsJsonArray("values");
        } else {
            valuesArray = new JsonArray();
            tagJson.add("values", valuesArray);
        }

        boolean exists = false;
        for (JsonElement element : valuesArray) {
            if (element.getAsString().equals(name)) {
                exists = true;
                break;
            }
        }

        if (!exists) {
            valuesArray.add(name);
        }

        try (FileWriter writer = new FileWriter(file)) {
            GSON.toJson(tagJson, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
