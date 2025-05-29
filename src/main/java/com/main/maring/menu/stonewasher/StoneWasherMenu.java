package com.main.maring.menu.stonewasher;

import com.main.maring.block.norm.stonewasher.Register;
import org.jetbrains.annotations.NotNull;

import com.main.maring.block.entity.consumer.stonewasher.StoneWasherEntity;
import com.main.maring.block.norm.BlockBasic;
import com.main.maring.block.norm.BlockRegister;
import com.main.maring.menu.BlockEntityMenuBasic;
import com.main.maring.menu.registry.MenuRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.items.SlotItemHandler;

public class StoneWasherMenu extends BlockEntityMenuBasic{
	
	private static Item mar_stone_item = BlockRegister.COMMON_BLOCK_ITEMS[BlockBasic.getIdFromName("mar_stone")].get();
	private static Item mar_deep_stone_item = BlockRegister.COMMON_BLOCK_ITEMS[BlockBasic.getIdFromName("mar_deep_stone")].get();


	private final StoneWasherEntity blockentity;

	public StoneWasherMenu( Inventory pInventory, int pID,BlockPos pos) {
		super(MenuRegister.STONEWASHER_MENU.get(), pID, pos, Register.stonewasher_BLOCK.get(), 4, 0, 2);
		this.blockentity = (StoneWasherEntity) pInventory.player.level().getBlockEntity(pos);
		if(pInventory.player.level().getBlockEntity(pos) instanceof StoneWasherEntity stonewasherentity) {
			addSlot(new SlotItemHandler(stonewasherentity.getItems(), 0, 56, 23) {
				@Override
			    public boolean mayPlace(@NotNull ItemStack stack)
			    {
					Item item = stack.getItem();
			        if(item!=mar_deep_stone_item&&item!=mar_stone_item) {
			        	return false;
			        }
			        return true;
			    }
			});
			addSlot(new SlotItemHandler(stonewasherentity.getItems(), 1, 56, 53) {
				@Override
			    public boolean mayPlace(@NotNull ItemStack stack)
			    {
			        if(stack.getItem()!=Items.WATER_BUCKET) {
			        	return false;
			        }
			        return true;
			    }
			});
			addSlot(new SlotItemHandler(stonewasherentity.getItems(), 2, 115, 34) {
			    @Override
			    public boolean mayPlace(@NotNull ItemStack stack)
			    {
			        return false;
			    }
			});
			addSlot(new SlotItemHandler(stonewasherentity.getItems(), 3, 143, 35) {
			    @Override
			    public boolean mayPlace(@NotNull ItemStack stack)
			    {
			        return false;
			    }
			});
		}
		addPlayerInventory(pInventory,8,84);
	}

	public StoneWasherEntity getBlockEntity() {
		return blockentity;
	}		
}
