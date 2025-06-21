package com.main.maring.fluid.mathane;

import com.main.maring.Maring;
import com.main.maring.fluid.BaseFluidType;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.joml.Vector3f;

public class MethaneFluidType {
    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, Maring.MODID);

    public static final ResourceLocation FLUID_STILL_RL = new ResourceLocation("block/water_still");
    public static final ResourceLocation FLUID_FLOWING_RL = new ResourceLocation("block/water_flow");
    public static final ResourceLocation METHANE_FLUID_OVERLAY_RL = new ResourceLocation(Maring.MODID, "misc/methane_fluid");

    public static final RegistryObject<FluidType> METHANE_FLUID_TYPE = register("methane_fluid",
            FluidType.Properties.create().lightLevel(0).density(15).viscosity(5));

    private static RegistryObject<FluidType> register(String name, FluidType.Properties properties) {
        return FLUID_TYPES.register(name, () -> new BaseFluidType(FLUID_STILL_RL, FLUID_FLOWING_RL, METHANE_FLUID_OVERLAY_RL,
                0xA0FF7E82, new Vector3f(224f / 255f, 56f / 255f, 208f / 255f), properties));
    }

}
