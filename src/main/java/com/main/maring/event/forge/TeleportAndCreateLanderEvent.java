package com.main.maring.event.forge;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.Event;
import com.main.maring.vehicle.rocket.RocketEntity;

/**
 * 火箭发射到达后
 * 生成新的火箭并绑定玩家
 * @author NANMEDA
 * */
public class TeleportAndCreateLanderEvent extends Event {

    private final RocketEntity rocketEntity;
    private final Player player;

    public TeleportAndCreateLanderEvent(RocketEntity rocketEntity, Player player) {
        this.rocketEntity = rocketEntity;
        this.player = player;
    }

    public RocketEntity getLanderEntity() {
        return rocketEntity;
    }

    public Player getPlayer() {
        return player;
    }
}
