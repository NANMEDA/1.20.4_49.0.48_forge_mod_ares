package menu.etchingmachine;

import org.jetbrains.annotations.NotNull;

import block.entity.consumer.etchingmachine.EtchingMachineEntity;
import item.ItemRegister;
import menu.BlockEntityMenuBasic;
import menu.registry.MenuRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.items.SlotItemHandler;

public class EtchingMachineMenu extends BlockEntityMenuBasic{

	private final EtchingMachineEntity blockentity;

	public EtchingMachineMenu( Inventory pInventory, int pID,BlockPos pos) {
		super(MenuRegister.ETCHINGMACHINE_MENU.get(), pID, pos, block.norm.etchingmachine.Register.etchingmachine_BLOCK.get(), 6, 0, 5);
		this.blockentity = (EtchingMachineEntity) pInventory.player.level().getBlockEntity(pos);
		if(pInventory.player.level().getBlockEntity(pos) instanceof EtchingMachineEntity etchingmachineEntity) {
			
			addSlot(new SlotItemHandler(etchingmachineEntity.getItems(), 0, 51, 11) {
				 @Override
				    public boolean mayPlace(@NotNull ItemStack stack)
				    {
				    	if(stack.getItem()==ItemRegister.MATERIAL_ITEMS[3].get()){
				    		return true;
				    	}
				        return false;
				    }
			});
			addSlot(new SlotItemHandler(etchingmachineEntity.getItems(), 1, 51, 30) {
				 @Override
				    public boolean mayPlace(@NotNull ItemStack stack)
				    {
				    	if(stack.getItem()==Items.DIAMOND){
				    		return true;
				    	}
				        return false;
				    }
			});
			addSlot(new SlotItemHandler(etchingmachineEntity.getItems(), 2, 51, 49) {
				 @Override
				    public boolean mayPlace(@NotNull ItemStack stack)
				    {
				    	if(stack.getItem()==Items.REDSTONE){
				    		return true;
				    	}
				        return false;
				    }
			});
			addSlot(new SlotItemHandler(etchingmachineEntity.getItems(), 3, 28, 41) {
				 @Override
				    public boolean mayPlace(@NotNull ItemStack stack)
				    {
				    	if(stack.getItem()==Items.GOLD_INGOT){
				    		return true;
				    	}
				        return false;
				    }
			});
			addSlot(new SlotItemHandler(etchingmachineEntity.getItems(), 4, 74, 41) {
				 @Override
				    public boolean mayPlace(@NotNull ItemStack stack)
				    {
				    	if(stack.getItem()==Items.COPPER_INGOT){
				    		return true;
				    	}
				        return false;
				    }
			});
			addSlot(new SlotItemHandler(etchingmachineEntity.getItems(), 5, 136, 35) {
				 @Override
				    public boolean mayPlace(@NotNull ItemStack stack)
				    {
				        return false;
				    }
			});
		}
		addPlayerInventory(pInventory,8,84);
	}

	public EtchingMachineEntity getBlockEntity() {
		return blockentity;
	}
	
}
