package menu.microwaveoven;

import org.jetbrains.annotations.NotNull;

import item.ItemRegister;
import machine.energy.consumer.microwaveoven.MicrowaveOvenEntity;
import machine.registry.MBlockRegister;
import menu.BlockEntityMenuBasic;
import menu.registry.MenuRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.items.SlotItemHandler;

public class MicrowaveOvenMenu extends BlockEntityMenuBasic{
	

	private final MicrowaveOvenEntity blockentity;

	public MicrowaveOvenMenu( Inventory pInventory, int pID,BlockPos pos) {
		super(MenuRegister.MICROWAVEOVEN_MENU.get(), pID, pos, MBlockRegister.MINCROWAVEOVEN_B.get(), 2, 0, 1);
		this.blockentity = (MicrowaveOvenEntity) pInventory.player.level().getBlockEntity(pos);
		if(pInventory.player.level().getBlockEntity(pos) instanceof MicrowaveOvenEntity microwaveovenEntity) {
			addSlot(new SlotItemHandler(microwaveovenEntity.getItems(), 0, 56, 35) {
				@Override
			    public boolean mayPlace(@NotNull ItemStack stack)
			    {
					Item item = stack.getItem();
			        if(!item.isEdible()&&item!=Items.EGG&&item!=Items.WET_SPONGE&&item!=ItemRegister.CAN.get()) {
			        	return false;
			        }
			        return true;
			    }
			});
			addSlot(new SlotItemHandler(microwaveovenEntity.getItems(), 1, 115, 34) {
			    @Override
			    public boolean mayPlace(@NotNull ItemStack stack)
			    {
			        return false;
			    }
			});
		}
		addPlayerInventory(pInventory,8,84);
	}

	public MicrowaveOvenEntity getBlockEntity() {
		return blockentity;
	}		
}
