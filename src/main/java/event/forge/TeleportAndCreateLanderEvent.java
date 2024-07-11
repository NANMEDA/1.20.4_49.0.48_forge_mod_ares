package event.forge;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.Event;
import vehicle.rocket.RocketEntity;

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
