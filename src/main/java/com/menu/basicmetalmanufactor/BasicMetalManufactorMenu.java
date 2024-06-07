package com.menu.basicmetalmanufactor;

import org.jetbrains.annotations.NotNull;

import com.menu.MenuBasic;
import com.menu.register.MenuRegister;

import block.entity.consumer.basicmetalmanufactor.BasicMetalManufactorEntity;
import block.norm.BlockRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.items.SlotItemHandler;

public class BasicMetalManufactorMenu extends MenuBasic{

	public BasicMetalManufactorMenu( Inventory pInventory, int pID,BlockPos pos) {
		super(MenuRegister.BASICMETALMANUFACTOR_MENU.get(), pID, pos, BlockRegister.basicmetalmanufactor_BLOCK.get(), 6, 0, 5);
		if(pInventory.player.level().getBlockEntity(pos) instanceof BasicMetalManufactorEntity basicmetalmanufactorEntity) {

			addSlot(new SlotItemHandler(basicmetalmanufactorEntity.getItems(), 0, 51, 11) {
				 @Override
				    public boolean mayPlace(@NotNull ItemStack stack)
				    {
				    	if(stack.getItem()==Items.IRON_INGOT){
				    		return true;
				    	}
				        return false;
				    }
			});
			addSlot(new SlotItemHandler(basicmetalmanufactorEntity.getItems(), 1, 28, 34) {
				 @Override
				    public boolean mayPlace(@NotNull ItemStack stack)
				    {
				    	if(stack.getItem()==Items.IRON_INGOT){
				    		return true;
				    	}
				        return false;
				    }
			});
			addSlot(new SlotItemHandler(basicmetalmanufactorEntity.getItems(), 2, 74, 34) {
				 @Override
				    public boolean mayPlace(@NotNull ItemStack stack)
				    {
				    	if(stack.getItem()==Items.IRON_INGOT){
				    		return true;
				    	}
				        return false;
				    }
			});
			addSlot(new SlotItemHandler(basicmetalmanufactorEntity.getItems(), 3, 51, 57) {
				 @Override
				    public boolean mayPlace(@NotNull ItemStack stack)
				    {
				    	if(stack.getItem()==Items.IRON_INGOT){
				    		return true;
				    	}
				        return false;
				    }
			});
			addSlot(new SlotItemHandler(basicmetalmanufactorEntity.getItems(), 4, 51, 34) {
				 @Override
				    public boolean mayPlace(@NotNull ItemStack stack)
				    {
				    	if(stack.getItem()==Items.GOLD_INGOT){
				    		return true;
				    	}
				        return false;
				    }
			});
			addSlot(new SlotItemHandler(basicmetalmanufactorEntity.getItems(), 5, 136, 35) {
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
