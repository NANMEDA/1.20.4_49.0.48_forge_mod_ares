package com.main.maring.menu.canfoodmaker;

import com.main.maring.block.norm.canfoodmaker.Register;
import org.jetbrains.annotations.NotNull;

import com.main.maring.block.entity.consumer.canfoodmaker.CanfoodMakerEntity;
import com.main.maring.menu.BlockEntityMenuBasic;
import com.main.maring.menu.registry.MenuRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.items.SlotItemHandler;
import com.main.maring.tags.register.TagkeyRegistry;

public class CanfoodMakerMenu extends BlockEntityMenuBasic{

	private final CanfoodMakerEntity blockEntity;

	public CanfoodMakerMenu(Inventory pInventory, int pID,BlockPos pos) {
		super(MenuRegister.CANFOODMAKER_MENU.get(), pID, pos, Register.canfoodmaker_BLOCK.get(), 5, 0, 4);
		this.blockEntity = (CanfoodMakerEntity) pInventory.player.level().getBlockEntity(pos);
		//this.blockEntity = (CanfoodMakerEntity) pInventory.player.level().getBlockEntity(pos);
		if(pInventory.player.level().getBlockEntity(pos) instanceof CanfoodMakerEntity canfoodmakerEntity) {
			addSlot(new SlotItemHandler(canfoodmakerEntity.getItems(), 0, 35, 18) {
				 @Override
				    public boolean mayPlace(@NotNull ItemStack stack)
				    {
				    	if(stack.is(TagkeyRegistry.CAN_FOOD_MATERIAL_TAG)||stack.getItem().isEdible()) {
				    		return true;
				    	}
				        return false;
				    }
			});
			addSlot(new SlotItemHandler(canfoodmakerEntity.getItems(), 1, 56, 18){
				 @Override
				    public boolean mayPlace(@NotNull ItemStack stack)
				    {
				    	if(stack.is(TagkeyRegistry.CAN_FOOD_MATERIAL_TAG)||stack.getItem().isEdible()) {
				    		return true;
				    	}
				        return false;
				    }
			});
			addSlot(new SlotItemHandler(canfoodmakerEntity.getItems(), 2, 77, 18){
				 @Override
				    public boolean mayPlace(@NotNull ItemStack stack)
				    {
				    	if(stack.is(TagkeyRegistry.CAN_FOOD_MATERIAL_TAG)||stack.getItem().isEdible()) {
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

	public CanfoodMakerEntity getBlockEntity() {
		return this.blockEntity;
	}
	
}
