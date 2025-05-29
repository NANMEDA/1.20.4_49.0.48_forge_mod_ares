package com.main.maring.menu.show;

import com.main.maring.menu.BlockEntityMenuBasic;
import com.main.maring.menu.registry.MenuRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

public class ShowBlockMenu extends BlockEntityMenuBasic{

	private final ItemStack itemStack;
	
	public ShowBlockMenu(Inventory pInventory, int pID,BlockPos pos) {
		super(MenuRegister.BLOCKSHOW_MENU.get(), pID, pos,pInventory.player.level().getBlockState(pos).getBlock() , 0, 0, 0);
		itemStack = new ItemStack(pInventory.player.level().getBlockState(pos).getBlock().asItem());
	}
	
	public ItemStack getItemStack() {
		return itemStack;
	}

}
