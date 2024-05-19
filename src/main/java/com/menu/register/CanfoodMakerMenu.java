package com.menu.register;

import com.block.register.BlockRegister;

import block.entity.register.CanfoodMakerEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.items.SlotItemHandler;

public class CanfoodMakerMenu extends MenuBasic{

	protected CanfoodMakerMenu( Inventory pInventory, int pID,BlockPos pos) {
		super(MenuRegister.CANFOODMAKER_MENU.get(), pID, pos, BlockRegister.canfoodmaker_BLOCK.get(), 5, 0, 5);
		if(pInventory.player.level().getBlockEntity(pos) instanceof CanfoodMakerEntity canfoodmakerEntity) {
			addSlot(new SlotItemHandler(canfoodmakerEntity.getItems(), 0, 35, 18));
			addSlot(new SlotItemHandler(canfoodmakerEntity.getItems(), 1, 56, 18));
			addSlot(new SlotItemHandler(canfoodmakerEntity.getItems(), 2, 77, 18));
			addSlot(new SlotItemHandler(canfoodmakerEntity.getItems(), 3, 56, 53));
			addSlot(new SlotItemHandler(canfoodmakerEntity.getItems(), 4, 126, 35));
		}
		addPlayerInventory(pInventory,8,84);
	}
	
}
