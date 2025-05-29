package com.main.maring.event.forge;

import net.minecraft.world.item.context.UseOnContext;
import net.minecraftforge.event.entity.EntityEvent;
import com.main.maring.vehicle.rocket.IRocketEntity;

/**
 * 代码主要来自
 * BeyondEarth
 * https://github.com/MrScautHD/Beyond-Earth
 * */
public class PlaceRocketEvent extends EntityEvent {

    private final UseOnContext context;

    public PlaceRocketEvent(IRocketEntity entity, UseOnContext context) {
        super(entity);
        this.context = context;
    }

    public UseOnContext getContext() {
        return context;
    }
}
