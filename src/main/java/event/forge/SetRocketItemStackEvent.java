package event.forge;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.EntityEvent;
import vehicle.rocket.RocketEntity;

public class SetRocketItemStackEvent extends EntityEvent {

    private final ItemStack itemStack;

    public SetRocketItemStackEvent(RocketEntity entity, ItemStack itemStack) {
        super(entity);
        this.itemStack = itemStack;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }
}
