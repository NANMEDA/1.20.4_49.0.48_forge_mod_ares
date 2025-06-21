package com.main.maring.fluid.mathane;

import com.main.maring.Maring;
import com.main.maring.block.norm.BlockRegister;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MethaneFluid {

    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(ForgeRegistries.FLUIDS, Maring.MODID);

    public static final RegistryObject<FlowingFluid> SOURCE_METHANE_FLUID = FLUIDS.register("source_methane_fluid",
            () -> new ForgeFlowingFluid.Source(MethaneFluid.METHANE_FLUID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_METHANE_FLUID = FLUIDS.register("flowing_methane_fluid",
            () -> new ForgeFlowingFluid.Flowing(MethaneFluid.METHANE_FLUID_PROPERTIES));

    public static final ForgeFlowingFluid.Properties METHANE_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(
            MethaneFluidType.METHANE_FLUID_TYPE, SOURCE_METHANE_FLUID, FLOWING_METHANE_FLUID)
            .slopeFindDistance(2).levelDecreasePerBlock(2).block(BlockRegister.METHANE_FLUID_BLOCK);

}
