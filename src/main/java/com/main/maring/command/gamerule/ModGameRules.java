package com.main.maring.command.gamerule;

import net.minecraft.world.level.GameRules;

public class ModGameRules {
    public static GameRules.Key<GameRules.BooleanValue> WILL_PRESSURE_HURT;

    public static void init() {
        WILL_PRESSURE_HURT = GameRules.register(
                "willPressureHurt",
                GameRules.Category.PLAYER,
                GameRules.BooleanValue.create(true)
        );
    }
}
