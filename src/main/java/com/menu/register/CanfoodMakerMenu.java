package com.menu.register;

import org.jetbrains.annotations.NotNull;

import com.block.register.BlockRegister;

import block.entity.register.CanfoodMakerEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.items.SlotItemHandler;

public class CanfoodMakerMenu extends MenuBasic{

	protected CanfoodMakerMenu( Inventory pInventory, int pID,BlockPos pos) {
		super(MenuRegister.CANFOODMAKER_MENU.get(), pID, pos, BlockRegister.canfoodmaker_BLOCK.get(), 5, 0, 4);
		if(pInventory.player.level().getBlockEntity(pos) instanceof CanfoodMakerEntity canfoodmakerEntity) {
			addSlot(new SlotItemHandler(canfoodmakerEntity.getItems(), 0, 35, 18) {
				 @Override
				    public boolean mayPlace(@NotNull ItemStack stack)
				    {
				    	if(stack.getItem().isEdible()) {
				    		return true;
				    	}//这里检测了一遍isEdible(),在Entity那里也有检测getfood***，把Entity那里删掉
				        return false;
				    }
			});
			addSlot(new SlotItemHandler(canfoodmakerEntity.getItems(), 1, 56, 18){
				 @Override
				    public boolean mayPlace(@NotNull ItemStack stack)
				    {
				    	if(stack.getItem().isEdible()) {
				    		return true;
				    	}
				        return false;
				    }
			});
			addSlot(new SlotItemHandler(canfoodmakerEntity.getItems(), 2, 77, 18){
				 @Override
				    public boolean mayPlace(@NotNull ItemStack stack)
				    {
				    	if(stack.getItem().isEdible()) {
				    		return true;
				    	}
				        return false;
				    }
			});
			addSlot(new SlotItemHandler(canfoodmakerEntity.getItems(), 3, 56, 53) {
			    @Override
			    public boolean mayPlace(@NotNull ItemStack stack)
			    {
			    	if(stack.getItem()==Items.IRON_INGOT||stack.getItem()==Items.IRON_NUGGET) {
			    		return true;
			    	}
			        return false;
			    }
			});
			addSlot(new SlotItemHandler(canfoodmakerEntity.getItems(), 4, 126, 35) {
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
