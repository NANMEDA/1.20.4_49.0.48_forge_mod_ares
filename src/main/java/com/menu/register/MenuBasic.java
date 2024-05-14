package com.menu.register;

import org.antlr.v4.parse.ANTLRParser.finallyClause_return;
import org.stringtemplate.v4.compiler.CodeGenerator.includeExpr_return;

import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.items.ItemStackHandler;

public abstract class MenuBasic extends AbstractContainerMenu{

	private final BlockPos pos;
	private final Block target_block;
	private final int slot_count,slot_normal,slot_normalcount;
	
	protected MenuBasic(MenuType<?> pMenuType, int pID,BlockPos pos,Block target_block, int slot_count, int slot_normal, int slot_normalcount) {
		super(pMenuType, pID);
		this.pos = pos;
		this.target_block = target_block;
		this.slot_count = slot_count;
		this.slot_normal = slot_normal;
		this.slot_normalcount = slot_normalcount;
		}
	
	@Override
	public ItemStack quickMoveStack(Player player,int ID) {
		var itemstack = ItemStack.EMPTY;
		var slot = this.slots.get(ID);
		if(slot.hasItem()) {
			var stack = slot.getItem();
			itemstack = stack.copy();
			if(ID < slot_count) {
				if(!this.moveItemStackTo(stack, slot_count, this.slots.size(), true)) {return ItemStack.EMPTY;}
			}else if(!this.moveItemStackTo(stack, slot_normal, slot_normalcount, true)) {return ItemStack.EMPTY;} 
			if(stack.isEmpty()) {slot.setByPlayer(ItemStack.EMPTY);}
			else {slot.setChanged();}
			if(stack.getCount()==itemstack.getCount()) {return ItemStack.EMPTY;}
			slot.onTake(player, stack);
		}
		return itemstack;
	} 
	
	public int addSlotLine(Container container,int index,int x,int y,int count,int dx) {
		for(int i = 0;i<count;i++) { 
		addSlot(new Slot(container, index, x, y));
			x += dx;
			index ++;
		}
		return index;
	}
	public int addSlotBox(Container container,int index,int x,int y,int x_count,int dx,int y_count,int dy) {
		for(int j = 0;j<y_count;j++) { 
			index = addSlotLine(container, index, x, y, x_count, dx);
			y += dy;
		}
		return index;
	}
	
	public void addPlayerInventory(Container inventory, int x,int y) {
		addSlotBox(inventory, 9, x, y, 9, 18, 3, 18);
		y += 58;
		addSlotLine(inventory, 0, x, y, 9, 18);
	}
	
	@Override
	public boolean stillValid(Player player) {
		return stillValid(ContainerLevelAccess.create(player.level(), pos),player,target_block);
	}
	
}
