package block.norm.etchingmachine;

import block.norm.BlockRegister;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.RegistryObject;

public class Register {
	
	public static void init() {}
	
	
    public static final RegistryObject<Block> etchingmachine_BLOCK = BlockRegister.BLOCKS.register(BlockEtchingMachine.global_name, () -> {
		return new BlockEtchingMachine(BlockBehaviour.Properties.of()
	            .sound(SoundType.STONE)
	            .strength(5f,5f)
	            .noOcclusion()
	            .mapColor(MapColor.COLOR_BLUE)); 
	});
    public static final RegistryObject<Item> etchingmachine_BLOCK_ITEM = BlockRegister.BLOCK_ITEMS.register(BlockEtchingMachine.global_name,
    		() -> new BlockItem(etchingmachine_BLOCK.get(), new Item.Properties()));
    
    public static final RegistryObject<Block> etchingmachineup_BLOCK = BlockRegister.BLOCKS.register(BlockEtchingMachineup.global_name, () -> {
		return new BlockEtchingMachineup(BlockBehaviour.Properties.of()
	            .sound(SoundType.STONE)
	            .strength(5f,5f)
	            .noOcclusion()
	            .mapColor(MapColor.NONE)); 
	});
    public static final RegistryObject<Item> etchingmachineup_BLOCK_ITEM = BlockRegister.BLOCK_ITEMS.register(BlockEtchingMachineup.global_name, () -> new BlockItem(etchingmachineup_BLOCK.get(), new Item.Properties()));

    public static final RegistryObject<Block> etchingmachinearound_BLOCK = BlockRegister.BLOCKS.register(BlockEtchingMachinearound.global_name, () -> {
		return new BlockEtchingMachinearound(BlockBehaviour.Properties.of()
	            .sound(SoundType.STONE)
	            .strength(5f,5f)
	            .noOcclusion()
	            .mapColor(MapColor.NONE)); 
	});
    public static final RegistryObject<Item> etchingmachinearound_BLOCK_ITEM = BlockRegister.BLOCK_ITEMS.register(BlockEtchingMachinearound.global_name, () -> new BlockItem(etchingmachinearound_BLOCK.get(), new Item.Properties()));
	
    public static final RegistryObject<Block> etchingmachinecorner_BLOCK = BlockRegister.BLOCKS.register(BlockEtchingMachinecorner.global_name, () -> {
		return new BlockEtchingMachinecorner(BlockBehaviour.Properties.of()
	            .sound(SoundType.STONE)
	            .strength(5f,5f)
	            .noOcclusion()
	            .mapColor(MapColor.NONE)); 
	});
    public static final RegistryObject<Item> etchingmachinecorner_BLOCK_ITEM = BlockRegister.BLOCK_ITEMS.register(BlockEtchingMachinecorner.global_name, () -> new BlockItem(etchingmachinecorner_BLOCK.get(), new Item.Properties()));
	
}
