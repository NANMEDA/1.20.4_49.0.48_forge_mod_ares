package com.main.maring;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.storage.LevelResource;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class ExtraConfigSave {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String FILE_NAME = "config.json";
    private static final String MOD_ID = "maring";

    public static Path getConfigFilePath(MinecraftServer server) {
        return server.getWorldPath(LevelResource.ROOT).resolve(MOD_ID).resolve(FILE_NAME);
    }

    public static void saveConfig(MinecraftServer server) {
        File file = getConfigFilePath(server).toFile();
        if (!file.exists()) {
            file.getParentFile().mkdirs();
        }
        try (FileWriter writer = new FileWriter(file)) {
            GSON.toJson(ExtraConfig.fromStaticFields(), writer); // Save the current state of DisasterConfig
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ExtraConfig loadConfig(MinecraftServer server) {
        File file = getConfigFilePath(server).toFile();
        if (!file.exists()) {
            saveConfig(server); // Create the default config file if it doesn't exist
            return new ExtraConfig(); // Return default config
        }
        try (FileReader reader = new FileReader(file)) {
            return GSON.fromJson(reader, ExtraConfig.class);
        } catch (JsonIOException | JsonSyntaxException | IOException e) {
            e.printStackTrace();
            return new ExtraConfig(); // Return default config if there is an error
        }
    }
}
