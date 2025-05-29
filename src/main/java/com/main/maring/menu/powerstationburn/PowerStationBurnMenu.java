package com.main.maring.menu.powerstationburn;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.core.BlockPos;
import net.minecraftforge.items.SlotItemHandler;
import com.main.maring.block.entity.station.PowerStationBurnEntity;
import com.main.maring.block.norm.BlockRegister;
import com.main.maring.menu.BlockEntityMenuBasic;
import com.main.maring.menu.registry.MenuRegister;


public class PowerStationBurnMenu extends BlockEntityMenuBasic {

	public PowerStationBurnMenu( Inventory pInventory, int pID,BlockPos pos) {
		super(MenuRegister.POWERSTATIONBURN_MENU.get(), pID, pos, BlockRegister.PowerStationBurn_BLOCK.get(), 1, 0, 1);
		if(pInventory.player.level().getBlockEntity(pos) instanceof PowerStationBurnEntity powerstationburnEntity) {
			addSlot(new SlotItemHandler(powerstationburnEntity.getItems(), 0, 56, 53));
		}
		addPlayerInventory(pInventory,8,84);
	}
	
}