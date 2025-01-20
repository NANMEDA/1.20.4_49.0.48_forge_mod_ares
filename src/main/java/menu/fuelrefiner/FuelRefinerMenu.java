package menu.fuelrefiner;

import org.jetbrains.annotations.NotNull;

import block.entity.neutral.fuelrefiner.FuelRefinerEntity;
import item.ItemRegister;
import menu.BlockEntityMenuBasic;
import menu.registry.MenuRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.items.SlotItemHandler;

public class FuelRefinerMenu extends BlockEntityMenuBasic{
	
	private static Item bottled_methane = ItemRegister.MATERIAL_ITEMS[6].get();

	private final FuelRefinerEntity blockentity;

	public FuelRefinerMenu( Inventory pInventory, int pID,BlockPos pos) {
		super(MenuRegister.FUELREFINER_MENU.get(), pID, pos, block.norm.fuelrefiner.Register.fuelrefiner_BLOCK.get(), 4, 0, 2);
		this.blockentity = (FuelRefinerEntity) pInventory.player.level().getBlockEntity(pos);
		if(pInventory.player.level().getBlockEntity(pos) instanceof FuelRefinerEntity entity) {
			addSlot(new SlotItemHandler(entity.getItems(), 0, 56, 23) {
				@Override
			    public boolean mayPlace(@NotNull ItemStack stack)
			    {
					Item item = stack.getItem();
			        if(item!=bottled_methane) {
			        	return false;
			        }
			        return true;
			    }
			});
			addSlot(new SlotItemHandler(entity.getItems(), 1, 56, 53) {
				@Override
			    public boolean mayPlace(@NotNull ItemStack stack)
			    {
			        return false;
			    }
			});
			addSlot(new SlotItemHandler(entity.getItems(), 2, 116, 10) {
			    @Override
			    public boolean mayPlace(@NotNull ItemStack stack)
			    {
			    	if(stack.getItem()!=Items.BUCKET) {
			        	return false;
			        }
			        return true;
			    }
			});
			addSlot(new SlotItemHandler(entity.getItems(), 3, 115, 34) {
			    @Override
			    public boolean mayPlace(@NotNull ItemStack stack)
			    {
			        return false;
			    }
			});
		}
		addPlayerInventory(pInventory,8,84);
	}

	public FuelRefinerEntity getBlockEntity() {
		return blockentity;
	}		
}
