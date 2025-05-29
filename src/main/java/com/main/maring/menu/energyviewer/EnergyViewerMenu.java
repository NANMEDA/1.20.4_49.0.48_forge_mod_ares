package com.main.maring.menu.energyviewer;

import com.main.maring.machine.energy.viewer.EnergyViewerEntity;
import com.main.maring.machine.registry.MBlockRegister;
import com.main.maring.menu.BlockEntityMenuBasic;
import com.main.maring.menu.registry.MenuRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;

public class EnergyViewerMenu extends BlockEntityMenuBasic{

	private final EnergyViewerEntity blockEntity;

	public EnergyViewerMenu(Inventory pInventory, int pID,BlockPos pos) {
		super(MenuRegister.ENERGYVIEWER_MENU.get(), pID, pos, MBlockRegister.ENERGYVIEWER_B.get(), 0, 0, 0);
		this.blockEntity = (EnergyViewerEntity) pInventory.player.level().getBlockEntity(pos);
	}

	public EnergyViewerEntity getBlockEntity() {
		return this.blockEntity;
	}
	
}
