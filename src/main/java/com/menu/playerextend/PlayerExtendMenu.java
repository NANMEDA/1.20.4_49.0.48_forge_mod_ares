package com.menu.playerextend;

import org.jetbrains.annotations.NotNull;

import com.item.ItemRegister;
import com.menu.BlockEntityMenuBasic;
import com.menu.register.MenuRegister;

import block.entity.consumer.advancedmetalmanufactor.AdvancedMetalManufactorEntity;
import block.entity.consumer.basicmetalmanufactor.BasicMetalManufactorEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class PlayerExtendMenu extends BlockEntityMenuBasic{
	

	protected static final ItemStackHandler item = new ItemStackHandler(3) {//必须要在这里创建，ItemStackHandler不可被修改
        @Override
        public void onLoad() {
            super.onLoad();
        }
    };

	public PlayerExtendMenu( Inventory pInventory, int pID,BlockPos pos) {
		super(MenuRegister.ADVANCEDMETALMANUFACTOR_MENU.get(), pID, pos, block.norm.advancedmetalmanufactor.Register.advancedmetalmanufactor_BLOCK.get(), 4, 0, 3);
		
			addSlot(new SlotItemHandler(item, 0, 51, 11) {
				 @Override
				    public boolean mayPlace(@NotNull ItemStack stack)
				    {
				    	if(stack.getItem()==ItemRegister.MATERIAL_ITEMS[1].get()){
				    		return true;
				    	}
				        return false;
				    }
			});
			addSlot(new SlotItemHandler(item, 1, 28, 41) {
				 @Override
				    public boolean mayPlace(@NotNull ItemStack stack)
				    {
				    	if(stack.getItem()==Items.LAPIS_LAZULI){
				    		return true;
				    	}
				        return false;
				    }
			});
			addSlot(new SlotItemHandler(item, 2, 74, 41) {
				 @Override
				    public boolean mayPlace(@NotNull ItemStack stack)
				    {
				    	if(stack.getItem()==Items.DIAMOND){
				    		return true;
				    	}
				        return false;
				    }
			});
			addSlot(new SlotItemHandler(item, 3, 136, 35) {
				 @Override
				    public boolean mayPlace(@NotNull ItemStack stack)
				    {
				        return false;
				    }
			});
		addPlayerInventory(pInventory,8,84);
	}
	
}
