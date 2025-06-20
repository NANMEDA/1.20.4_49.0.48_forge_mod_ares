package com.main.maring.fluid.cheese;

import com.main.maring.Maring;
import com.main.maring.fluid.BaseFluidType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.common.SoundAction;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.joml.Vector3f;


public class CheeseFluidType {

    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, Maring.MODID);

    public static final ResourceLocation CHEESE_FLUID_STILL_RL = new ResourceLocation("block/water_still");
    public static final ResourceLocation CHEESE_FLUID_FLOWING_RL = new ResourceLocation("block/water_flow");
    public static final ResourceLocation CHEESE_FLUID_OVERLAY_RL = new ResourceLocation(Maring.MODID, "misc/cheese_fluid");

    public static final RegistryObject<FluidType> CHEESE_FLUID_TYPE = register("cheese_fluid",
            FluidType.Properties.create().lightLevel(0).density(15).viscosity(5).sound(SoundAction.get("drink"),
                    SoundEvents.HONEY_DRINK));

    private static RegistryObject<FluidType> register(String name, FluidType.Properties properties) {
        return FLUID_TYPES.register(name, () -> new BaseFluidType(CHEESE_FLUID_STILL_RL, CHEESE_FLUID_FLOWING_RL, CHEESE_FLUID_OVERLAY_RL,
                0xA1EECF28, new Vector3f(224f / 255f, 56f / 255f, 208f / 255f), properties));
    }

}
