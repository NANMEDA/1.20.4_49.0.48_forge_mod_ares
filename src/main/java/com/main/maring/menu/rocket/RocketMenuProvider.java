package com.main.maring.menu.rocket;

import javax.annotation.Nullable;

import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;

public class RocketMenuProvider implements MenuProvider{

	private final int entityId;
	
	public RocketMenuProvider(int id){
		this.entityId = id;
	}
	
	@Nullable
	@Override
	public AbstractContainerMenu createMenu(int pId, Inventory pInventory, Player player) {
		return new RocketMenu(pInventory, pId, entityId);
	}

	@Override
	public Component getDisplayName() {
		return Component.translatable("menu.rocket");
	}

}
