package menu.energyviewer;

import machine.energy.viewer.EnergyViewerEntity;
import machine.registry.MBlockRegister;
import menu.BlockEntityMenuBasic;
import menu.registry.MenuRegister;
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
