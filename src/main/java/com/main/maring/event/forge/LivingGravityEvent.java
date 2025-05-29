package com.main.maring.event.forge;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraftforge.event.entity.living.LivingEvent;

/**
 * 代码主要来自
 * BeyondEarth
 * https://github.com/MrScautHD/Beyond-Earth
 * */
public class LivingGravityEvent extends LivingEvent {

    private final Attribute attribute;
    private final AttributeInstance attributeInstance;

    public LivingGravityEvent(LivingEntity entity, Attribute attribute, AttributeInstance attributeInstance) {
        super(entity);
        this.attribute= attribute;
        this.attributeInstance = attributeInstance;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public AttributeInstance getAttributeInstance() {
        return attributeInstance;
    }
}
