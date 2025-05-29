package com.main.maring.menu.coredigger;

import org.jetbrains.annotations.NotNull;

import com.main.maring.machine.energy.consumer.coredigger.CoreDiggerEntity;
import com.main.maring.machine.registry.MBlockRegister;
import com.main.maring.menu.BlockEntityMenuBasic;
import com.main.maring.menu.registry.MenuRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class CoreDiggerMenu extends BlockEntityMenuBasic{

	private final CoreDiggerEntity blockentity;

	public CoreDiggerMenu( Inventory pInventory, int pID,BlockPos pos) {
		super(MenuRegister.COREDIGGER_MENU.get(), pID, pos, MBlockRegister.COREDIDDER_B.get(), 6, 0, 6);
		this.blockentity = (CoreDiggerEntity) pInventory.player.level().getBlockEntity(pos);
		if(pInventory.player.level().getBlockEntity(pos) instanceof CoreDiggerEntity entity) {
			addSlot(new SlotItemHandler(entity.getItems(), 0, 35, 18) {
				@Override
			    public boolean mayPlace(@NotNull ItemStack stack)
			    {
			        return true;
			    }
			});
			addSlot(new SlotItemHandler(entity.getItems(), 1, 56, 18) {
				@Override
			    public boolean mayPlace(@NotNull ItemStack stack)
			    {
			        return true;
			    }
			});
			addSlot(new SlotItemHandler(entity.getItems(), 2, 77, 18) {
				@Override
			    public boolean mayPlace(@NotNull ItemStack stack)
			    {
			        return true;
			    }
			});
			addSlot(new SlotItemHandler(entity.getItems(), 3, 35, 38) {
				@Override
			    public boolean mayPlace(@NotNull ItemStack stack)
			    {
			        return true;
			    }
			});
			addSlot(new SlotItemHandler(entity.getItems(), 4, 56, 38) {
				@Override
			    public boolean mayPlace(@NotNull ItemStack stack)
			    {
			        return true;
			    }
			});
			addSlot(new SlotItemHandler(entity.getItems(), 5, 77, 38) {
				@Override
			    public boolean mayPlace(@NotNull ItemStack stack)
			    {
			        return true;
			    }
			});
		}
		addPlayerInventory(pInventory,8,84);
	}

	public CoreDiggerEntity getBlockEntity() {
		return blockentity;
	}		
}
