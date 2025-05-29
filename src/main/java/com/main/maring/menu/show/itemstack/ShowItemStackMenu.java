package com.main.maring.menu.show.itemstack;

import com.main.maring.menu.BlockEntityMenuBasic;
import com.main.maring.menu.registry.MenuRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;

public class ShowItemStackMenu extends BlockEntityMenuBasic{

	private final ItemStack itemStack;
	
	public ShowItemStackMenu( Inventory pInventory, int pID,ItemStack stack,BlockPos pos) {
		super(MenuRegister.ITEMSTACKSHOW_MENU.get(), pID, pos,Blocks.AIR , 0, 0, 0);
		itemStack = stack;
	}
	
	public ItemStack getItemStack() {
		return itemStack;
	}

}
