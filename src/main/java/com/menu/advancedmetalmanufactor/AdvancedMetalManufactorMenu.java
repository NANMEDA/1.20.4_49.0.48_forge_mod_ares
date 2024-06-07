package com.menu.advancedmetalmanufactor;

import org.jetbrains.annotations.NotNull;

import com.item.register.ItemRegister;
import com.menu.MenuBasic;
import com.menu.register.MenuRegister;

import block.entity.consumer.advancedmetalmanufactor.AdvancedMetalManufactorEntity;
import block.entity.consumer.basicmetalmanufactor.BasicMetalManufactorEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.items.SlotItemHandler;

public class AdvancedMetalManufactorMenu extends MenuBasic{

	public AdvancedMetalManufactorMenu( Inventory pInventory, int pID,BlockPos pos) {
		super(MenuRegister.ADVANCEDMETALMANUFACTOR_MENU.get(), pID, pos, block.norm.advancedmetalmanufactor.Register.advancedmetalmanufactor_BLOCK.get(), 4, 0, 3);
		if(pInventory.player.level().getBlockEntity(pos) instanceof AdvancedMetalManufactorEntity advancedmetalmanufactorEntity) {

			addSlot(new SlotItemHandler(advancedmetalmanufactorEntity.getItems(), 0, 51, 11) {
				 @Override
				    public boolean mayPlace(@NotNull ItemStack stack)
				    {
				    	if(stack.getItem()==ItemRegister.MATERIAL_ITEMS[1].get()){
				    		return true;
				    	}
				        return false;
				    }
			});
			addSlot(new SlotItemHandler(advancedmetalmanufactorEntity.getItems(), 1, 28, 41) {
				 @Override
				    public boolean mayPlace(@NotNull ItemStack stack)
				    {
				    	if(stack.getItem()==Items.LAPIS_LAZULI){
				    		return true;
				    	}
				        return false;
				    }
			});
			addSlot(new SlotItemHandler(advancedmetalmanufactorEntity.getItems(), 2, 74, 41) {
				 @Override
				    public boolean mayPlace(@NotNull ItemStack stack)
				    {
				    	if(stack.getItem()==Items.DIAMOND){
				    		return true;
				    	}
				        return false;
				    }
			});
			addSlot(new SlotItemHandler(advancedmetalmanufactorEntity.getItems(), 3, 136, 35) {
				 @Override
				    public boolean mayPlace(@NotNull ItemStack stack)
				    {
				        return false;
				    }
			});
		}
		addPlayerInventory(pInventory,8,84);
	}
	
}
