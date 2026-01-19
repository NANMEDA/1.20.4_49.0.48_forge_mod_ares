package com.main.maring.recipe.jei;

import com.main.maring.Maring;
import com.main.maring.recipe.CultivateRecipe;
import mezz.jei.api.recipe.RecipeType;

public final class MJEIRecipeTypes
{
    public static final RecipeType<CultivateRecipe> CULTIVATE = RecipeType.create(Maring.MODID, "cultivate", CultivateRecipe.class);
}