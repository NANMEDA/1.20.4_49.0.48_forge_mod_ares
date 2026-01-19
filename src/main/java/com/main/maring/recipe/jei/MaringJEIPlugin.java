package com.main.maring.recipe.jei;

import com.main.maring.Maring;
import com.main.maring.item.ItemRegister;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

@JeiPlugin
public class MaringJEIPlugin implements IModPlugin {
    private static final ResourceLocation ID = new ResourceLocation(Maring.MODID, "jei_plugin");

    @Override
    public ResourceLocation getPluginUid() {
        return ID;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        registry.addRecipeCategories(new CultivateCategory(registry.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        MJEIRecipes modRecipes = new MJEIRecipes();
        registration.addRecipes(MJEIRecipeTypes.CULTIVATE, modRecipes.getCultivateRecipes());
    }


    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(ItemRegister.FROSTFIRE_FRUIT.get()), MJEIRecipeTypes.CULTIVATE);
    }

}

