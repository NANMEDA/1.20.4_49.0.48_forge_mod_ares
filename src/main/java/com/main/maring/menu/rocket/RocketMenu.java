package com.main.maring.menu.rocket;

import com.main.maring.vehicle.rocket.RocketEntity;
import org.jetbrains.annotations.NotNull;

import com.main.maring.menu.EntityMenuBasic;
import com.main.maring.menu.registry.MenuRegister;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.items.SlotItemHandler;
import com.main.maring.tags.register.TagkeyRegistry;
import com.main.maring.vehicle.rocket.IRocketEntity;

public class RocketMenu extends EntityMenuBasic {
	
	private final RocketEntity rocket;
	
	public RocketMenu(Inventory pInventory, int pID, int entityId) {
		super(MenuRegister.ROCKET_MENU.get(), pID, pInventory.player.level().getEntity(entityId), 8, 0, 8);
		this.rocket = (RocketEntity) pInventory.player.level().getEntity(entityId);
			addSlot(new SlotItemHandler(this.rocket.getInventory(), 0, 125, 18) {
				@Override
			    public boolean mayPlace(@NotNull ItemStack stack)
			    {
			        return stack.is(TagkeyRegistry.FUEL_BUCKET_TAG);
			    }
			});
			addSlot(new SlotItemHandler(this.rocket.getInventory(), 1, 125, 38) {
			    @Override
			    public boolean mayPlace(@NotNull ItemStack stack)
			    {
			        return false;
			    }
			});
			addSlot(new SlotItemHandler(this.rocket.getInventory(), 2, 34, 18) {
			});
			addSlot(new SlotItemHandler(this.rocket.getInventory(), 3, 55, 18) {
			});
			addSlot(new SlotItemHandler(this.rocket.getInventory(), 4, 76, 18) {
			});
			addSlot(new SlotItemHandler(this.rocket.getInventory(), 5, 34, 38) {
			});
			addSlot(new SlotItemHandler(this.rocket.getInventory(), 6, 55, 38) {
			});
			addSlot(new SlotItemHandler(this.rocket.getInventory(), 7, 76, 38) {
			});
		addPlayerInventory(pInventory,8,84);
	}
	
	public RocketEntity rocket() {
		return rocket;
	}
}
