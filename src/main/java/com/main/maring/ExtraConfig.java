package com.main.maring;

import net.minecraft.server.MinecraftServer;

public class ExtraConfig {
    // Configuration variables/constants
    public static final int DAY_TICKS = 24000;
    public static final int NIGHT_TICKS = 12000;
    public static final int DOOM_EVENT_DURATION = 400;

    public int spiderEventStart = 25 * DAY_TICKS;
    public int spiderEventDuration = 1 * DAY_TICKS;

    public int suicideEventStart = 35 * DAY_TICKS;
    public int suicideEventDuration = 3 * DAY_TICKS;

    public boolean doomsWillArrive = true;
    public long doomsDayTomorrow = 39 * DAY_TICKS + NIGHT_TICKS;
    public long doomsDayStart = doomsDayTomorrow + DAY_TICKS;
    public long doomsDayEnd = doomsDayStart + DOOM_EVENT_DURATION;
    public boolean warning = false;
    public boolean dooms_day_occur = false;
    
    public boolean willPressureHurt = true;

    // Static method to create an instance from the current static fields
    public static ExtraConfig fromStaticFields() {
        ExtraConfig config = new ExtraConfig();
        config.spiderEventStart = SPIDER_EVENT_START;
        config.spiderEventDuration = SPIDER_EVENT_DURATION;
        config.suicideEventStart = SUICIDE_EVENT_START;
        config.suicideEventDuration = SUICIDE_EVENT_DURATION;
        config.doomsWillArrive = DOOMS_WILL_ARRIVE;
        config.doomsDayTomorrow = DOOMS_DAY_TOMORROW;
        config.doomsDayStart = DOOMS_DAY_START;
        config.doomsDayEnd = DOOMS_DAY_END;
        config.warning = WARNING;
        config.dooms_day_occur = DOOMS_DAY_OCCUR;
        
        config.willPressureHurt = WILL_PRESSURE_HURT;
        return config;
    }

    // Static method to update the static fields from an instance
    public static void updateStaticFields(ExtraConfig config) {
        SPIDER_EVENT_START = config.spiderEventStart;
        SPIDER_EVENT_DURATION = config.spiderEventDuration;
        SUICIDE_EVENT_START = config.suicideEventStart;
        SUICIDE_EVENT_DURATION = config.suicideEventDuration;
        DOOMS_WILL_ARRIVE = config.doomsWillArrive;
        DOOMS_DAY_TOMORROW = config.doomsDayTomorrow;
        DOOMS_DAY_START = config.doomsDayStart;
        DOOMS_DAY_END = config.doomsDayEnd;
        WARNING = config.warning;
        DOOMS_DAY_OCCUR = config.dooms_day_occur;
        
        WILL_PRESSURE_HURT = config.willPressureHurt;
    }

    // Static fields to be used in the game logic
    public static int SPIDER_EVENT_START = 25 * DAY_TICKS;
    public static int SPIDER_EVENT_DURATION = 1 * DAY_TICKS;
    public static int SUICIDE_EVENT_START = 35 * DAY_TICKS;
    public static int SUICIDE_EVENT_DURATION = 3 * DAY_TICKS;
    public static boolean DOOMS_WILL_ARRIVE = true;
    public static long DOOMS_DAY_TOMORROW = 39 * DAY_TICKS + NIGHT_TICKS;
    public static long DOOMS_DAY_START = DOOMS_DAY_TOMORROW + DAY_TICKS;
    public static long DOOMS_DAY_END = DOOMS_DAY_START + DOOM_EVENT_DURATION;
    public static boolean WARNING = false;
    public static boolean DOOMS_DAY_OCCUR = false;
    
    public static boolean WILL_PRESSURE_HURT = true;
	
    
    public static void save(MinecraftServer server) {
        ExtraConfigSave.saveConfig(server);
    }

    public static void load(MinecraftServer server) {
        ExtraConfig config = ExtraConfigSave.loadConfig(server);
        updateStaticFields(config);
    }
}
