package com.main.maring.menu.fuelrefiner;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;

public class FuelRefinerMenuProvider implements MenuProvider{
	private final BlockPos pos;
	
	public FuelRefinerMenuProvider(BlockPos pBlockPos){
		this.pos = pBlockPos;
	}
	
	@Nullable
	@Override
	public AbstractContainerMenu createMenu(int pId, Inventory pInventory, Player player) {
		return new FuelRefinerMenu(pInventory, pId, pos);
	}

	@Override
	public Component getDisplayName() {
		return Component.translatable("menu.fuel_refiner");
	}

}
