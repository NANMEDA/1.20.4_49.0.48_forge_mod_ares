package menu.rocket;

import org.jetbrains.annotations.NotNull;

import item.ItemRegister;
import machine.energy.consumer.microwaveoven.MicrowaveOvenEntity;
import menu.EntityMenuBasic;
import menu.registry.MenuRegister;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.items.SlotItemHandler;
import tags.register.TagkeyRegistry;
import vehicle.rocket.IRocketEntity;

public class RocketMenu extends EntityMenuBasic {
	
	private final IRocketEntity rocket;
	
	public RocketMenu(Inventory pInventory, int pID, int entityId) {
		super(MenuRegister.ROCKET_MENU.get(), pID, pInventory.player.level().getEntity(entityId), 8, 0, 8);
		this.rocket = (IRocketEntity) pInventory.player.level().getEntity(entityId);
			addSlot(new SlotItemHandler(this.rocket.getInventory(), 0, 56, 35) {
				@Override
			    public boolean mayPlace(@NotNull ItemStack stack)
			    {
			        return stack.is(TagkeyRegistry.FUEL_BUCKET_TAG);
			    }
			});
			addSlot(new SlotItemHandler(this.rocket.getInventory(), 1, 115, 34) {
			    @Override
			    public boolean mayPlace(@NotNull ItemStack stack)
			    {
			        return stack.is(Items.BUCKET);
			    }
			});
			addSlot(new SlotItemHandler(this.rocket.getInventory(), 2, 115, 34) {
			});
			addSlot(new SlotItemHandler(this.rocket.getInventory(), 3, 115, 34) {
			});
			addSlot(new SlotItemHandler(this.rocket.getInventory(), 4, 115, 34) {
			});
			addSlot(new SlotItemHandler(this.rocket.getInventory(), 5, 115, 34) {
			});
			addSlot(new SlotItemHandler(this.rocket.getInventory(), 6, 115, 34) {
			});
			addSlot(new SlotItemHandler(this.rocket.getInventory(), 7, 115, 34) {
			});
		addPlayerInventory(pInventory,8,84);
	}
	
	public IRocketEntity rocket() {
		return rocket;
	}
}
