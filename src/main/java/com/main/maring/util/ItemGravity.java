package com.main.maring.util;

import com.main.maring.Maring;
import com.main.maring.event.forge.ItemGravityEvent;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;

/**
 * 代码主要来自
 * BeyondEarth
 * https://github.com/MrScautHD/Beyond-Earth
 * */
public class ItemGravity {

    public static final double DEFAULT_ITEM_GRAVITY = 0.04;//注意这个初始值
    private static ResourceKey<Level> marKey = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(Maring.MODID, "maringmar"));

    
    public static void setGravities(ItemEntity itemEntity, Level level) {
        float itemGravity = 0.02f;
        if (level.dimension()==level.getServer().getLevel(marKey).dimension()) {
            setGravity(itemEntity, itemGravity);
        }else {
        	setGravity(itemEntity, DEFAULT_ITEM_GRAVITY);
        }
    }

    public static void setGravity(ItemEntity entity, double gravity) {
        if (!getCondition(entity)) {
            return;
        }

        if (MinecraftForge.EVENT_BUS.post(new ItemGravityEvent(entity, gravity))) {
            return;
        }
        float artificialGravity = EntityGravity.getArtificalGravityModifier(entity.level(), entity.blockPosition());
        gravity += artificialGravity * DEFAULT_ITEM_GRAVITY;
        // First removes the default gravity. Then we apply the -gravity to re-add ours.
        double dy = entity.getDeltaMovement().y / 0.98 + DEFAULT_ITEM_GRAVITY - gravity;
        entity.setDeltaMovement(entity.getDeltaMovement().x, dy, entity.getDeltaMovement().z);
    }

    /** GRAVITY CHECK */
    private static boolean getCondition(ItemEntity entity) {
        return !entity.isInFluidType() && !entity.isInLava() && !entity.isNoGravity();
    }
}
