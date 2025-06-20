package com.main.maring.fluid.oxygen;

import com.main.maring.Maring;
import com.main.maring.block.norm.BlockRegister;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class OxygenFluid {

    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(ForgeRegistries.FLUIDS, Maring.MODID);

    public static final RegistryObject<FlowingFluid> SOURCE_OXYGEN_FLUID = FLUIDS.register("source_oxygen_fluid",
            () -> new ForgeFlowingFluid.Source(OxygenFluid.OXYGEN_FLUID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_OXYGEN_FLUID = FLUIDS.register("flowing_oxygen_fluid",
            () -> new ForgeFlowingFluid.Flowing(OxygenFluid.OXYGEN_FLUID_PROPERTIES));

    public static final ForgeFlowingFluid.Properties OXYGEN_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(
            OxygenFluidType.OXYGEN_FLUID_TYPE, SOURCE_OXYGEN_FLUID, FLOWING_OXYGEN_FLUID)
            .slopeFindDistance(2).levelDecreasePerBlock(2).block(BlockRegister.OXYGEN_FLUID_BLOCK);

}
