package com.main.maring.fluid.cheese;

import com.main.maring.Maring;
import com.main.maring.block.norm.BlockRegister;
import com.main.maring.item.ItemRegister;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class CheeseFluid {
    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(ForgeRegistries.FLUIDS, Maring.MODID);

    public static final RegistryObject<FlowingFluid> SOURCE_CHEESE_FLUID = FLUIDS.register("source_cheese_fluid",
            () -> new ForgeFlowingFluid.Source(CheeseFluid.CHEESE_FLUID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_CHEESE_FLUID = FLUIDS.register("flowing_cheese_fluid",
            () -> new ForgeFlowingFluid.Flowing(CheeseFluid.CHEESE_FLUID_PROPERTIES));

    public static final ForgeFlowingFluid.Properties CHEESE_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(
            CheeseFluidType.CHEESE_FLUID_TYPE, SOURCE_CHEESE_FLUID, FLOWING_CHEESE_FLUID)
            .slopeFindDistance(2).levelDecreasePerBlock(2).block(BlockRegister.CHEESE_FLUID_BLOCK)
            .bucket(ItemRegister.CHEESE_BUCKET);

}
