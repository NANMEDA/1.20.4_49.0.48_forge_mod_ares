package com.main.maring.menu.blueprintbuilder;

import com.main.maring.block.norm.blueprintbuilder.Register;
import org.jetbrains.annotations.NotNull;

import com.main.maring.block.entity.neutral.blueprintbuilder.BlueprintBuilderEntity;
import com.main.maring.item.ItemRegister;
import com.main.maring.menu.BlockEntityMenuBasic;
import com.main.maring.menu.registry.MenuRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class BlueprintBuilderMenu extends BlockEntityMenuBasic{

	private final BlueprintBuilderEntity blockentity;
	
	public BlueprintBuilderMenu( Inventory pInventory, int pID,BlockPos pos) {
		super(MenuRegister.BLUEPRINTBUILDER_MENU.get(), pID, pos, Register.blueprintbuilder_BLOCK.get(), 8, 0, 7);
		blockentity = (BlueprintBuilderEntity) pInventory.player.level().getBlockEntity(pos);
		if(pInventory.player.level().getBlockEntity(pos) instanceof BlueprintBuilderEntity entity) {
			addSlot(new SlotItemHandler(entity.getItems(), 0, 136, 60) {
				 @Override
				    public boolean mayPlace(@NotNull ItemStack stack)
				    {
				    	if(stack.getItem()==ItemRegister.BUILDING_STRUCTURE.get()){
				    		return true;
				    	}
				        return false;
				    }
			});
			addSlot(new SlotItemHandler(entity.getItems(), 1, 18, 34) {
				 @Override
				    public boolean mayPlace(@NotNull ItemStack stack)
				    {
				    	if(stack.getItem()==ItemRegister.BASIC_METAL_PARTS.get()){
				    		return true;
				    	}
				        return false;
				    }
			});
			addSlot(new SlotItemHandler(entity.getItems(), 2, 39, 34) {
				 @Override
				    public boolean mayPlace(@NotNull ItemStack stack)
				    {
				    	if(stack.getItem()==ItemRegister.ADVANCED_METAL_PARTS.get()){
				    		return true;
				    	}
				        return false;
				    }
			});
			addSlot(new SlotItemHandler(entity.getItems(), 3, 60, 34) {
				 @Override
				    public boolean mayPlace(@NotNull ItemStack stack)
				    {
				    	if(stack.getItem()==ItemRegister.BIOPLASTIC_PARTS.get()){
				    		return true;
				    	}
				        return false;
				    }
			});
			addSlot(new SlotItemHandler(entity.getItems(), 4, 81, 34) {
				 @Override
				    public boolean mayPlace(@NotNull ItemStack stack)
				    {
					if(stack.getItem()==ItemRegister.SEMICONDUCTOR_PARTS.get()){
				    		return true;
				    	}
				        return false;
				    }
			});
			addSlot(new SlotItemHandler(entity.getItems(), 5, 102, 34) {
				 @Override
				    public boolean mayPlace(@NotNull ItemStack stack)
				    {
				    	if(stack.getItem()==ItemRegister.CRYSTAL_PARTS.get()){
				    		return true;
				    	}
				        return false;
				    }
			});
			addSlot(new SlotItemHandler(entity.getItems(), 6, 136, 11) {
				 @Override
				    public boolean mayPlace(@NotNull ItemStack stack)
				    {
				    	if(stack.getItem()==ItemRegister.BLUE_PRINT.get()){
				    		return true;
				    	}
				        return false;
				    }
			});
			
			addSlot(new SlotItemHandler(entity.getItems(), 7, 136, 35) {
				 @Override
				    public boolean mayPlace(@NotNull ItemStack stack)
				    {
				        return false;
				    }
			});
		}
		addPlayerInventory(pInventory,8,84);
	}
	
	public BlueprintBuilderEntity getBlockEntity() {
		return blockentity;
	}
	
}
