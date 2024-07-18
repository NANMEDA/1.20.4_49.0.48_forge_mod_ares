package event.forge;

import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.entity.EntityEvent;

/**
 * 代码主要来自
 * BeyondEarth
 * https://github.com/MrScautHD/Beyond-Earth
 * */
public class EntityTickEvent extends EntityEvent {

    public EntityTickEvent(Entity entity) {
        super(entity);
    }
}
