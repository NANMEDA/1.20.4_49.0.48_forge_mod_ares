package com.main.maring.recipe.jei;

import com.main.maring.recipe.CultivateRecipe;
import com.main.maring.recipe.ModRecipeTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;

public final class MJEIRecipes
{
    private final RecipeManager recipeManager;

    public MJEIRecipes() {
        Minecraft minecraft = Minecraft.getInstance();
        ClientLevel level = minecraft.level;

        if (level != null) {
            this.recipeManager = level.getRecipeManager();
        } else {
            throw new NullPointerException("minecraft world must not be null.");
        }
    }


    public List<CultivateRecipe> getCultivateRecipes() {
        List<CultivateRecipe> recipes = recipeManager.getAllRecipesFor(ModRecipeTypes.CULTIVATE.get()).stream().toList();
        return recipes;
    }
}