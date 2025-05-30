package com.main.maring.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class CommonConfig {

    // Fixed constants (not configurable)
    public static final int DAY_TICKS = 24000;
    public static final int NIGHT_TICKS = 12000;
    public static final int DOOM_EVENT_DURATION = 400;

    // Configurable values
    public static ForgeConfigSpec.IntValue SPIDER_EVENT_START;
    public static ForgeConfigSpec.IntValue SPIDER_EVENT_DURATION;

    public static ForgeConfigSpec.IntValue SUICIDE_EVENT_START;
    public static ForgeConfigSpec.IntValue SUICIDE_EVENT_DURATION;

    public static ForgeConfigSpec.BooleanValue DOOMS_WILL_ARRIVE;
    public static ForgeConfigSpec.LongValue DOOMS_DAY_TOMORROW;

    public static ForgeConfigSpec init() {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.comment("Maring Config");
        builder.comment("Event settings").push("events");

        SPIDER_EVENT_START = builder
                .comment("When the spider event starts (in ticks)")
                .defineInRange("spiderEventStart", 25 * DAY_TICKS, 0, Integer.MAX_VALUE);

        SPIDER_EVENT_DURATION = builder
                .comment("Duration of the spider event (in ticks)")
                .defineInRange("spiderEventDuration", DAY_TICKS, 0, Integer.MAX_VALUE);

        SUICIDE_EVENT_START = builder
                .comment("When the suicide event starts (in ticks)")
                .defineInRange("suicideEventStart", 35 * DAY_TICKS, 0, Integer.MAX_VALUE);

        SUICIDE_EVENT_DURATION = builder
                .comment("Duration of the suicide event (in ticks)")
                .defineInRange("suicideEventDuration", 3 * DAY_TICKS, 0, Integer.MAX_VALUE);

        builder.pop();

        builder.comment("Doomsday settings").push("doomsday");

        DOOMS_WILL_ARRIVE = builder
                .comment("Whether the doomsday event is enabled")
                .define("doomsWillArrive", true);

        DOOMS_DAY_TOMORROW = builder
                .comment("When the doomsday warning occurs (in ticks)")
                .defineInRange("doomsDayTomorrow", 39 * DAY_TICKS + NIGHT_TICKS, 0, Long.MAX_VALUE);

        builder.pop();
        return builder.build();
    }
}
