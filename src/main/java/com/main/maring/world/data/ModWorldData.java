package com.main.maring.world.data;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class ModWorldData extends SavedData {
    private static final String IDENTIFIER = "maring_world_data";
    private static final String DOOMS_DAY_INFOS_TAG = "DoomsDayInfo";
    private static final String SPIDER_COMING_INFOS_TAG = "SpiderComingInfo";

    public boolean WARNING = false;
    public boolean DOOMS_DAY_OCCUR = false;
    public boolean SPIDER_DAY_OCCUR = false;
    public boolean SPIDER_DAY_INIT = false;

    @Nullable
    public static ModWorldData get(Level level) {
        if (level instanceof ServerLevel) {
            ServerLevel overWorld = level.getServer().getLevel(Level.OVERWORLD);
            if (overWorld == null) {
                return null;
            }
            DimensionDataStorage storage = overWorld.getDataStorage();
            ModWorldData data = storage.computeIfAbsent(ModWorldData::load, ModWorldData::new, IDENTIFIER);
            data.setDirty();
            return data;
        }
        return null;
    }

    public static ModWorldData load(CompoundTag tag) {
        ModWorldData data = new ModWorldData();
        if (tag.contains(DOOMS_DAY_INFOS_TAG, Tag.TAG_COMPOUND)) {
            CompoundTag doomsTag = tag.getCompound(DOOMS_DAY_INFOS_TAG);
            data.WARNING = doomsTag.getBoolean("Warning");
            data.DOOMS_DAY_OCCUR = doomsTag.getBoolean("DoomsDayOccur");
        }
        if (tag.contains(SPIDER_COMING_INFOS_TAG, Tag.TAG_COMPOUND)) {
            CompoundTag spiderTag = tag.getCompound(SPIDER_COMING_INFOS_TAG);
            data.SPIDER_DAY_OCCUR = spiderTag.getBoolean("SpiderDayOccur");
            data.SPIDER_DAY_INIT = spiderTag.getBoolean("SpiderDayInit");
        }
        return data;
    }

    @Override
    public @NotNull CompoundTag save(@NotNull CompoundTag tag) {
        CompoundTag doomsTag = new CompoundTag();
        doomsTag.putBoolean("Warning", WARNING);
        doomsTag.putBoolean("DoomsDayOccur", DOOMS_DAY_OCCUR);
        tag.put(DOOMS_DAY_INFOS_TAG, doomsTag);
        CompoundTag spiderTag = new CompoundTag();
        spiderTag.putBoolean("SpiderDayOccur", SPIDER_DAY_OCCUR);
        spiderTag.putBoolean("SpiderDayInit", SPIDER_DAY_INIT);
        tag.put(SPIDER_COMING_INFOS_TAG, spiderTag);
        return tag;
    }
}
