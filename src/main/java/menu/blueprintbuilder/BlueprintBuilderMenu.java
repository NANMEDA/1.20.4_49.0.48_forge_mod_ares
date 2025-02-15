package menu.blueprintbuilder;

import org.jetbrains.annotations.NotNull;

import block.entity.consumer.basicmetalmanufactor.BasicMetalManufactorEntity;
import block.entity.consumer.etchingmachine.EtchingMachineEntity;
import block.entity.neutral.blueprintbuilder.BlueprintBuilderEntity;
import block.norm.BlockRegister;
import item.ItemRegister;
import menu.BlockEntityMenuBasic;
import menu.registry.MenuRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.items.SlotItemHandler;

public class BlueprintBuilderMenu extends BlockEntityMenuBasic{

	private final BlueprintBuilderEntity blockentity;
	
	public BlueprintBuilderMenu( Inventory pInventory, int pID,BlockPos pos) {
		super(MenuRegister.BLUEPRINTBUILDER_MENU.get(), pID, pos, block.norm.blueprintbuilder.Register.blueprintbuilder_BLOCK.get(), 8, 0, 7);
		blockentity = (BlueprintBuilderEntity) pInventory.player.level().getBlockEntity(pos);
		if(pInventory.player.level().getBlockEntity(pos) instanceof BlueprintBuilderEntity entity) {
			addSlot(new SlotItemHandler(entity.getItems(), 0, 136, 60) {
				 @Override
				    public boolean mayPlace(@NotNull ItemStack stack)
				    {
				    	if(stack.getItem()==ItemRegister.BUILDING_STRUCTURE.get()){
				    		return true;
				    	}
				        return false;
				    }
			});
			addSlot(new SlotItemHandler(entity.getItems(), 1, 18, 34) {
				 @Override
				    public boolean mayPlace(@NotNull ItemStack stack)
				    {
				    	if(stack.getItem()==ItemRegister.MATERIAL_ITEMS[1].get()){
				    		return true;
				    	}
				        return false;
				    }
			});
			addSlot(new SlotItemHandler(entity.getItems(), 2, 39, 34) {
				 @Override
				    public boolean mayPlace(@NotNull ItemStack stack)
				    {
				    	if(stack.getItem()==ItemRegister.MATERIAL_ITEMS[2].get()){
				    		return true;
				    	}
				        return false;
				    }
			});
			addSlot(new SlotItemHandler(entity.getItems(), 3, 60, 34) {
				 @Override
				    public boolean mayPlace(@NotNull ItemStack stack)
				    {
				    	if(stack.getItem()==ItemRegister.MATERIAL_ITEMS[3].get()){
				    		return true;
				    	}
				        return false;
				    }
			});
			addSlot(new SlotItemHandler(entity.getItems(), 4, 81, 34) {
				 @Override
				    public boolean mayPlace(@NotNull ItemStack stack)
				    {
				    	if(stack.getItem()==ItemRegister.MATERIAL_ITEMS[4].get()){
				    		return true;
				    	}
				        return false;
				    }
			});
			addSlot(new SlotItemHandler(entity.getItems(), 5, 102, 34) {
				 @Override
				    public boolean mayPlace(@NotNull ItemStack stack)
				    {
				    	if(stack.getItem()==ItemRegister.MATERIAL_ITEMS[5].get()){
				    		return true;
				    	}
				        return false;
				    }
			});
			addSlot(new SlotItemHandler(entity.getItems(), 6, 136, 11) {
				 @Override
				    public boolean mayPlace(@NotNull ItemStack stack)
				    {
				    	if(stack.getItem()==ItemRegister.BLUE_PRINT.get()){
				    		return true;
				    	}
				        return false;
				    }
			});
			
			addSlot(new SlotItemHandler(entity.getItems(), 7, 136, 35) {
				 @Override
				    public boolean mayPlace(@NotNull ItemStack stack)
				    {
				        return false;
				    }
			});
		}
		addPlayerInventory(pInventory,8,84);
	}
	
	public BlueprintBuilderEntity getBlockEntity() {
		return blockentity;
	}
	
}
