package event.forge;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraftforge.event.entity.item.ItemEvent;

/**
 * 代码主要来自
 * BeyondEarth
 * https://github.com/MrScautHD/Beyond-Earth
 * */
public class ItemEntityTickAtEndEvent extends ItemEvent {

    public ItemEntityTickAtEndEvent(ItemEntity entity) {
        super(entity);
    }
}
