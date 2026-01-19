package com.main.maring.recipe;

import com.main.maring.Maring;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Maring.MODID);

    public static final RegistryObject<RecipeSerializer<?>> CULTIVATE = RECIPE_SERIALIZERS.register("cultivate", CultivateRecipe.CultivateRecipeSerializer::new);

   }
