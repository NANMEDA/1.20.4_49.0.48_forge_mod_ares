package com.main.maring.fluid.hydrogen;

import com.main.maring.Maring;
import com.main.maring.block.norm.BlockRegister;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class HydrogenFluid {

    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(ForgeRegistries.FLUIDS, Maring.MODID);

    public static final RegistryObject<FlowingFluid> SOURCE_HYDROGEN_FLUID = FLUIDS.register("source_hydrogen_fluid",
            () -> new ForgeFlowingFluid.Source(HydrogenFluid.HYDROGEN_FLUID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_HYDROGEN_FLUID = FLUIDS.register("flowing_hydrogen_fluid",
            () -> new ForgeFlowingFluid.Flowing(HydrogenFluid.HYDROGEN_FLUID_PROPERTIES));

    public static final ForgeFlowingFluid.Properties HYDROGEN_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(
            HydrogenFluidType.HYDROGEN_FLUID_TYPE, SOURCE_HYDROGEN_FLUID, FLOWING_HYDROGEN_FLUID)
            .slopeFindDistance(2).levelDecreasePerBlock(2).block(BlockRegister.HYDROGEN_FLUID_BLOCK);

}
