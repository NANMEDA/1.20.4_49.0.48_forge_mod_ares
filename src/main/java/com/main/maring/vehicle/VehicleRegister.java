package com.main.maring.vehicle;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import com.main.maring.vehicle.rocket.RocketEntity;

public class VehicleRegister {
	private static final String MODID = "maring";
	
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MODID);

    public static final RegistryObject<EntityType<RocketEntity>> ROCKET_ENTITY = ENTITIES.register("rocket_t1", () -> EntityType.Builder.of(RocketEntity::new, MobCategory.MISC)
    		.sized(1.1f, 4.4f)
    		.fireImmune()
    		.build(new ResourceLocation(MODID, "rocket_t1").toString()));

}
