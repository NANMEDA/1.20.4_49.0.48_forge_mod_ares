package com.main.maring.fluid;

import com.main.maring.Maring;
import com.main.maring.fluid.cheese.CheeseFluid;
import com.main.maring.fluid.cheese.CheeseFluidType;
import com.main.maring.fluid.hydrogen.HydrogenFluid;
import com.main.maring.fluid.hydrogen.HydrogenFluidType;
import com.main.maring.fluid.oxygen.OxygenFluid;
import com.main.maring.fluid.oxygen.OxygenFluidType;
import net.minecraftforge.eventbus.api.IEventBus;

public class FluidRegistry {


    public static void register(IEventBus eventBus) {
        CheeseFluid.FLUIDS.register(eventBus);
        CheeseFluidType.FLUID_TYPES.register(eventBus);
        HydrogenFluid.FLUIDS.register(eventBus);
        HydrogenFluidType.FLUID_TYPES.register(eventBus);
        OxygenFluid.FLUIDS.register(eventBus);
        OxygenFluidType.FLUID_TYPES.register(eventBus);
    }
}
