package effect.brew;

import net.minecraftforge.common.brewing.BrewingRecipeRegistry;


public class BrewRigster {

    public static void registerBrewingRecipes() {
        BrewingRecipeRegistry.addRecipe(new ominous());
    }
}
