package com.effect.brew;

import com.item.register.ItemRegister;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.common.brewing.IBrewingRecipe;

public class ominous implements IBrewingRecipe {

    public static final Potion SPEED_JUMP_POTION = new Potion(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200, 10), new MobEffectInstance(MobEffects.JUMP, 200, 3));

    @Override
    public boolean isInput(ItemStack stack) {
        Item item = stack.getItem();
        return item == Items.POTION || item == Items.SPLASH_POTION || item == Items.LINGERING_POTION || item == Items.GLASS_BOTTLE;
    }

    @Override
    public boolean isIngredient(ItemStack stack) {
        return PotionBrewing.isIngredient(stack) || stack.getItem() == ItemRegister.MATERIAL_ITEMS[0].get();
    }

    @Override
    public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
        if (!input.isEmpty() && !ingredient.isEmpty()) {
            if (ingredient.getItem() == ItemRegister.MATERIAL_ITEMS[0].get()) {
                ItemStack result = new ItemStack(input.getItem());
                PotionUtils.setPotion(result, SPEED_JUMP_POTION);
                return result;
            } else if (isIngredient(ingredient)) {
                ItemStack result = PotionBrewing.mix(ingredient, input);
                if (result != input) {
                    return result;
                }
            }
            return ItemStack.EMPTY;
        }

        return ItemStack.EMPTY;
    }
    
    
}
