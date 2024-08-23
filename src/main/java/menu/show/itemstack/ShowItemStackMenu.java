package menu.show.itemstack;

import menu.BlockEntityMenuBasic;
import menu.register.MenuRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;

public class ShowItemStackMenu extends BlockEntityMenuBasic{

	private final ItemStack itemStack;
	
	public ShowItemStackMenu( Inventory pInventory, int pID,ItemStack stack,BlockPos pos) {
		super(MenuRegister.ITEMSTACKSHOW_MENU.get(), pID, pos,Blocks.AIR , 0, 0, 0);
		itemStack = stack;
	}
	
	public ItemStack getItemStack() {
		return itemStack;
	}

}
