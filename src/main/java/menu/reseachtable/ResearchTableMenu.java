package menu.reseachtable;


import block.entity.neutral.researchtable.ResearchTableEntity;
import block.norm.researchtable.Register;
import menu.BlockEntityMenuBasic;
import menu.registry.MenuRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;

public class ResearchTableMenu extends BlockEntityMenuBasic {
    private final Container container;
	private ResearchTableEntity blockentity;

    public ResearchTableMenu(Inventory pInventory, int pID,BlockPos pos) {
        super(MenuRegister.RESEARCHTABLE_MENU.get(), pID, pos, Register.researchtable_BLOCK.get(), 0, 0, 0);
        this.blockentity = (ResearchTableEntity) pInventory.player.level().getBlockEntity(pos);
        this.container = pInventory;
    }

	public ResearchTableEntity getBlockEntity() {
		// TODO 自动生成的方法存根
		return blockentity;
	}

}
