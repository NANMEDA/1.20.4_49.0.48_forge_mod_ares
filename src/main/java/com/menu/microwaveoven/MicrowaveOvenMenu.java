package com.menu.microwaveoven;

import org.jetbrains.annotations.NotNull;

import com.menu.BlockEntityMenuBasic;
import com.menu.register.MenuRegister;

import block.entity.consumer.microwaveoven.MicrowaveOvenEntity;
import block.norm.BlockRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.items.SlotItemHandler;

public class MicrowaveOvenMenu extends BlockEntityMenuBasic{
	

	private final MicrowaveOvenEntity blockentity;

	public MicrowaveOvenMenu( Inventory pInventory, int pID,BlockPos pos) {
		super(MenuRegister.MICROWAVEOVEN_MENU.get(), pID, pos, BlockRegister.microwaveoven_BLOCK.get(), 2, 0, 1);
		this.blockentity = (MicrowaveOvenEntity) pInventory.player.level().getBlockEntity(pos);
		if(pInventory.player.level().getBlockEntity(pos) instanceof MicrowaveOvenEntity microwaveovenEntity) {
			addSlot(new SlotItemHandler(microwaveovenEntity.getItems(), 0, 56, 35) {
				@Override
			    public boolean mayPlace(@NotNull ItemStack stack)
			    {
					Item item = stack.getItem();
			        if(!item.isEdible()&&item!=Items.EGG) {
			        	return false;
			        }
			        return true;
			    }
			});
			addSlot(new SlotItemHandler(microwaveovenEntity.getItems(), 1, 115, 34) {
			    @Override
			    public boolean mayPlace(@NotNull ItemStack stack)
			    {
			        return false;
			    }
			});
		}
		addPlayerInventory(pInventory,8,84);
	}

	public MicrowaveOvenEntity getBlockEntity() {
		return blockentity;
	}		
}
