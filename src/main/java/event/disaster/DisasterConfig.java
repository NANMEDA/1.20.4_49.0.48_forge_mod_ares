package event.disaster;

public class DisasterConfig {
    // Configuration variables/constants
    public static final int DAY_TICKS = 24000;
    public static final int NIGHT_TICKS = 12000;
    
    
    public static final int SPIDER_EVENT_START = 25 * DAY_TICKS;
    public static final int SPIDER_EVENT_DURATION = 1 * DAY_TICKS;
    
    public static final int SUICIDE_EVENT_START = 35 * DAY_TICKS;
    public static final int SUICIDE_EVENT_DURATION = 3 * DAY_TICKS;
    
    public static boolean DOOMS_WILL_ARRIVE = true;
    public static int DOOMS_DAY_TOMORROW = 39 * DAY_TICKS + NIGHT_TICKS;
    public static int DOOMS_DAY_START = DOOMS_DAY_TOMORROW + DAY_TICKS;
    public static int DOOMS_DAY_END = DOOMS_DAY_START + 400;
    public static final int DOOM_EVENT_DURATION = 400;
}