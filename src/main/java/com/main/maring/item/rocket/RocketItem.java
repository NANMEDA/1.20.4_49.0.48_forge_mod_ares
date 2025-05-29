package com.main.maring.item.rocket;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import com.main.maring.vehicle.VehicleRegister;
import com.main.maring.vehicle.rocket.IRocketEntity;
import com.main.maring.vehicle.rocket.RocketEntity;

public class RocketItem extends IRocketItem {
    public RocketItem(Properties properties) {
        super(properties);
    }

    
    @OnlyIn(Dist.CLIENT)
    @Override
    public BlockEntityWithoutLevelRenderer getRenderer() {
        return RocketItemRender.ROCKET_TIER_1_ITEM_RENDERER;
    }

    @Override
    public EntityType<? extends IRocketEntity> getEntityType() {
        return VehicleRegister.ROCKET_ENTITY.get();
    }

    @Override
    public IRocketEntity getRocket(Level level) {
        return new RocketEntity(VehicleRegister.ROCKET_ENTITY.get(), level);
    }

    @Override
    public int getFuelBuckets() {
        return RocketEntity.staticGetBucketsOfFull();
    }

}