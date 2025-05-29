package com.main.maring.menu.marreactor;

import org.jetbrains.annotations.NotNull;

import com.main.maring.item.ItemRegister;
import com.main.maring.item.itemMaterial;
import com.main.maring.machine.energy.producer.reactor.mar.MarReactorEntity;
import com.main.maring.machine.registry.MBlockRegister;
import com.main.maring.menu.BlockEntityMenuBasic;
import com.main.maring.menu.registry.MenuRegister;
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
					if(stack.is(ItemRegister.OMINOUS_GEMSTONE_REACTOR.get())) {
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
