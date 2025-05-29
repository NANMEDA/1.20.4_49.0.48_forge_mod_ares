package com.main.maring.menu.advancedmetalmanufactor;

import com.main.maring.block.norm.advancedmetalmanufactor.Register;
import org.jetbrains.annotations.NotNull;

import com.main.maring.block.entity.consumer.advancedmetalmanufactor.AdvancedMetalManufactorEntity;
import com.main.maring.item.ItemRegister;
import com.main.maring.menu.BlockEntityMenuBasic;
import com.main.maring.menu.registry.MenuRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.items.SlotItemHandler;

public class AdvancedMetalManufactorMenu extends BlockEntityMenuBasic{

	private final AdvancedMetalManufactorEntity blockentity;
	
	public AdvancedMetalManufactorMenu( Inventory pInventory, int pID,BlockPos pos) {
		super(MenuRegister.ADVANCEDMETALMANUFACTOR_MENU.get(), pID, pos, Register.advancedmetalmanufactor_BLOCK.get(), 4, 0, 3);
		blockentity = (AdvancedMetalManufactorEntity) pInventory.player.level().getBlockEntity(pos);
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
	
	public AdvancedMetalManufactorEntity getBlockEntity() {
		return blockentity;
	}

}
