package com.main.maring.recipe;

import com.main.maring.Maring;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipeTypes {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, Maring.MODID);
    public static final RegistryObject<RecipeType<CultivateRecipe>> CULTIVATE =
            RECIPE_TYPES.register("cultivate",
            () -> registerRecipeType("cultivate"));

    public static <T extends Recipe<?>> RecipeType<T> registerRecipeType(final String identifier) {
        return new RecipeType<>()
        {
            public String toString() {
                return Maring.MODID + ":" + identifier;
            }
        };
    }
}
