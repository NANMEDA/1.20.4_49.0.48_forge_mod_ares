package effect.brew;

import item.ItemRegister;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.brewing.IBrewingRecipe;

public class ominous implements IBrewingRecipe {

    @Override
    public boolean isInput(ItemStack stack) {
        Item item = stack.getItem();
        return item == ItemRegister.CHEESE_PIECE.get();
    }

    @Override
    public boolean isIngredient(ItemStack stack) {
        return stack.getItem() == ItemRegister.MATERIAL_ITEMS[0].get();
    }

    @Override
    public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
        if (!input.isEmpty() && !ingredient.isEmpty()) {
            if (ingredient.getItem() == ItemRegister.MATERIAL_ITEMS[0].get()) {
                ItemStack result = new ItemStack(ItemRegister.FOOD_ITEMS[10].get(),8);
                return result;
            }
            
            return ItemStack.EMPTY;
        }

        return ItemStack.EMPTY;
    }
    
    
}
