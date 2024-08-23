package menu.show.itemstack;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

public class ShowItemStackMenuProvider implements MenuProvider{
	private final ItemStack stack;
	private final BlockPos pos;
	
	public ShowItemStackMenuProvider(ItemStack stack,BlockPos pos){
		this.stack = stack;
		this.pos = pos;
	}
	
	@Nullable
	@Override
	public AbstractContainerMenu createMenu(int pId, Inventory pInventory, Player player) {
		return new ShowItemStackMenu(pInventory, pId, stack,pos);
	}

	@Override
	public Component getDisplayName() {
		return Component.translatable("menu.showitemstack");
	}

}
