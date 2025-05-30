package com.main.maring.event;

import com.main.maring.Maring;
import com.main.maring.event.client.MarSky;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterDimensionSpecialEffectsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class SkyRendererRegistry {

    @SubscribeEvent
    public static void register(RegisterDimensionSpecialEffectsEvent event) {
    	ResourceKey<DimensionType> locKey = ResourceKey.create(Registries.DIMENSION_TYPE, new ResourceLocation(Maring.MODID, "maringmar"));
        event.register(locKey.location(), new MarSky(192.0F, false, DimensionSpecialEffects.SkyType.NORMAL, false, false));
    }
}
