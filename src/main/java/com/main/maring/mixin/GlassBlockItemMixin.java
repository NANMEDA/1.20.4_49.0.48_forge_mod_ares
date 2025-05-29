package com.main.maring.mixin;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.core.BlockPos;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BlockItem.class)
public abstract class GlassBlockItemMixin {

    // We override the method that is called when the player right-clicks with the item.
    public InteractionResult useOn(InteractionHand hand, Level world, Player player, BlockPos pos) {
        // If the glass block is used, treat it like food and heal the player
    	
        if (!world.isClientSide) {
            player.heal(5.0F);

            // Remove one glass block from the player's inventory
            ItemStack stack = player.getItemInHand(hand);
            stack.shrink(1);  // Decrease the stack size by 1

            // Return success, indicating that the action was handled
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;  // If something else handles the action, pass it on
    }
}

