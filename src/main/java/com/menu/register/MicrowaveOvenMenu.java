package com.menu.register;

import com.block.register.BlockRegister;

import block.entity.register.MicrowaveOvenEntity;
import net.minecraft.client.gui.components.Button;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.items.SlotItemHandler;

public class MicrowaveOvenMenu extends MenuBasic{

	protected MicrowaveOvenMenu( Inventory pInventory, int pID,BlockPos pos) {
		super(MenuRegister.MICROWAVEOVEN_MENU.get(), pID, pos, BlockRegister.microwaveoven_BLOCK.get(), 2, 0, 1);
		if(pInventory.player.level().getBlockEntity(pos) instanceof MicrowaveOvenEntity microwaveovenEntity) {
			addSlot(new SlotItemHandler(microwaveovenEntity.getItems(), 0, 56, 35));
			addSlot(new SlotItemHandler(microwaveovenEntity.getItems(), 1, 115, 34));
		}
		addPlayerInventory(pInventory,8,84);
	}		
}
