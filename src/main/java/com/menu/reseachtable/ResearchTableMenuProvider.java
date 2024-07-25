package com.menu.reseachtable;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;

public class ResearchTableMenuProvider implements MenuProvider {
	private final BlockPos pos;
	
	public ResearchTableMenuProvider(BlockPos pBlockPos){
		this.pos = pBlockPos;
	}
	
    @Override
    public Component getDisplayName() {
        return Component.translatable("menu.research_table");
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player player) {
        return new ResearchTableMenu(playerInventory, id, pos);
    }
}
