package event.forge;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraftforge.event.entity.item.ItemEvent;

public class ItemEntityTickAtEndEvent extends ItemEvent {

    public ItemEntityTickAtEndEvent(ItemEntity entity) {
        super(entity);
    }
}
