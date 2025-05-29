package com.main.maring.menu.canfoodmaker;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;

public class CanfoodMakerMenuProvider implements MenuProvider{
	private final BlockPos pos;
	
	public CanfoodMakerMenuProvider(BlockPos pBlockPos){
		this.pos = pBlockPos;
	}
	
	@Nullable
	@Override
	public AbstractContainerMenu createMenu(int pId, Inventory pInventory, Player player) {
		return new CanfoodMakerMenu(pInventory, pId, pos);
	}

	@Override
	public Component getDisplayName() {
		return Component.translatable("menu.canfood_maker");
	}

}
