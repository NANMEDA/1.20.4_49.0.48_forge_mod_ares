package com.main.maring.menu.dormcontrol;

import com.main.maring.block.entity.neutral.fastbuild.dormcontrol.DomeControlEntity;
import com.main.maring.block.norm.BlockRegister;
import com.main.maring.menu.BlockEntityMenuBasic;
import com.main.maring.menu.registry.MenuRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;

public class DomeControlMenu extends BlockEntityMenuBasic{

	private final DomeControlEntity blockEntity;

	public DomeControlMenu(Inventory pInventory, int pID,BlockPos pos) {
		super(MenuRegister.DORMCONTROL_MENU.get(), pID, pos, BlockRegister.dormcontrol_BLOCK.get(), 0, 0, 0);
		this.blockEntity = (DomeControlEntity) pInventory.player.level().getBlockEntity(pos);
	}

	public DomeControlEntity getBlockEntity() {
		return this.blockEntity;
	}
	
}
