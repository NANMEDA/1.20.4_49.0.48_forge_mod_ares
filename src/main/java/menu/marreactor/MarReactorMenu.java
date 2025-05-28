package menu.marreactor;

import org.jetbrains.annotations.NotNull;

import item.ItemRegister;
import item.itemMaterial;
import machine.energy.producer.reactor.mar.MarReactorEntity;
import machine.registry.MBlockRegister;
import menu.BlockEntityMenuBasic;
import menu.registry.MenuRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class MarReactorMenu extends BlockEntityMenuBasic{

	private final MarReactorEntity blockentity;

	public MarReactorMenu( Inventory pInventory, int pID,BlockPos pos) {
		super(MenuRegister.MARREACTOR_MENU.get(), pID, pos, MBlockRegister.MARREACTOR_B.get(), 6, 0, 6);
		this.blockentity = (MarReactorEntity) pInventory.player.level().getBlockEntity(pos);
		if(pInventory.player.level().getBlockEntity(pos) instanceof MarReactorEntity entity) {
			addSlot(new SlotItemHandler(entity.getItems(), 0, 79, 34) {
				@Override
			    public boolean mayPlace(@NotNull ItemStack stack)
			    {
					if(stack.is(ItemRegister.MATERIAL_ITEMS[itemMaterial.getMaterialId("ominous_gemstone_reactor")].get())) {
						return true;
					}
					return false;
			    }
			});
		}
		addPlayerInventory(pInventory,8,84);
	}

	public MarReactorEntity getBlockEntity() {
		return blockentity;
	}		
}
