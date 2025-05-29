package com.main.maring.event.forge;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraftforge.event.entity.item.ItemEvent;
import net.minecraftforge.eventbus.api.Cancelable;

/**
 * 代码主要来自
 * BeyondEarth
 * https://github.com/MrScautHD/Beyond-Earth
 * */
@Cancelable
public class ItemGravityEvent extends ItemEvent {

    private final double gravity;

    public ItemGravityEvent(ItemEntity itemEntity, double gravity) {
        super(itemEntity);
        this.gravity = gravity;
    }

    public double getGravity() {
        return gravity;
    }
}
