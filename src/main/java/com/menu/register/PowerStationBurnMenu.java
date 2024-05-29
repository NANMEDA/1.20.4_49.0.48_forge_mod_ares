package com.menu.register;

import net.minecraft.world.entity.player.Player;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.items.SlotItemHandler;
import block.entity.register.BlockEntityRegister;
import block.entity.register.PowerStationBurnEntity;

import org.antlr.v4.gui.SystemFontMetrics;

import com.block.register.BlockRegister;
import com.block.register.PowerStationBurn;
import net.minecraftforge.items.IItemHandler;


public class PowerStationBurnMenu extends MenuBasic {

	protected PowerStationBurnMenu( Inventory pInventory, int pID,BlockPos pos) {
		super(MenuRegister.POWERSTATIONBURN_MENU.get(), pID, pos, BlockRegister.PowerStationBurn_BLOCK.get(), 1, 0, 1);
		if(pInventory.player.level().getBlockEntity(pos) instanceof PowerStationBurnEntity powerstationburnEntity) {
			addSlot(new SlotItemHandler(powerstationburnEntity.getItems(), 0, 56, 53));
		}
		addPlayerInventory(pInventory,8,84);
	}
	
}