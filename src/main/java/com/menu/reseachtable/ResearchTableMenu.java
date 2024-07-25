package com.menu.reseachtable;


import org.jetbrains.annotations.NotNull;

import com.menu.BlockEntityMenuBasic;
import com.menu.register.MenuRegister;

import block.entity.consumer.basicmetalmanufactor.BasicMetalManufactorEntity;
import block.entity.consumer.microwaveoven.MicrowaveOvenEntity;
import block.entity.neutral.researchtable.ResearchTableEntity;
import block.norm.researchtable.Register;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.items.SlotItemHandler;

public class ResearchTableMenu extends BlockEntityMenuBasic {
    private final Container container;
	private ResearchTableEntity blockentity;

    public ResearchTableMenu(Inventory pInventory, int pID,BlockPos pos) {
        super(MenuRegister.RESEARCHTABLE_MENU.get(), pID, pos, Register.researchtable_BLOCK.get(), 2, 0, 1);
        this.blockentity = (ResearchTableEntity) pInventory.player.level().getBlockEntity(pos);
        this.container = pInventory;
        if(pInventory.player.level().getBlockEntity(pos) instanceof ResearchTableEntity entity) {
			addSlot(new SlotItemHandler(entity.getItems(), 0, 60, 34) {
				 @Override
				    public boolean mayPlace(@NotNull ItemStack stack)
				    {
				    	if(stack.getItem()==Items.PAPER){
				    		return true;
				    	}
				        return false;
				    }
				@Override
				public boolean isHighlightable() {
					return false;
				}
			});
			
			addSlot(new SlotItemHandler(entity.getItems(), 1, 136, 35) {
				@Override
			    public boolean mayPlace(@NotNull ItemStack stack)
			    {
			        return false;
			    }
				
				@Override
				public boolean isHighlightable() {
					return false;
				}
			});
		}
        //addSlotLine(container, 0, 20, 20, 2, 18); // First line
        //addSlotLine(container, 2, 20, 38, 2, 18); // Second line
        //addSlotLine(container, 4, 20, 56, 2, 18); // Third line
        
        addPlayerInventoryNonHighlight(container, 8, 84); // Add player inventory
    }

	public ResearchTableEntity getBlockEntity() {
		// TODO 自动生成的方法存根
		return blockentity;
	}

}
