package com.main.maring.damage;

import com.main.maring.Maring;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.level.Level;

/**
 * 伤害来源注册
 * */
public class DamageSourceRegistry {
	
    public static final ResourceKey<DamageType> DAMAGE_SOURCE_PRESSURE = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(Maring.MODID, "pressure"));
    
    public static DamageSource getDamageSource(Level level, ResourceKey<DamageType> key) {
        return new DamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(key));
    }
}
