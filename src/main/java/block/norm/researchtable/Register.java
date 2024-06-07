package block.norm.researchtable;

import block.norm.BlockRegister;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.RegistryObject;

public class Register {
	
	@SuppressWarnings("unused")
	public static void init() {}
	
    public static final RegistryObject<Block> researchtable_BLOCK = BlockRegister.BLOCKS.register(BlockResearchTable.global_name, () -> {
		return new BlockResearchTable(BlockBehaviour.Properties.of()
	            .sound(SoundType.STONE)
	            .strength(5f,5f)
	            .noOcclusion()
	            .mapColor(MapColor.COLOR_BLUE)); 
	});
    public static final RegistryObject<Item> researchtable_BLOCK_ITEM = BlockRegister.BLOCK_ITEMS.register(BlockResearchTable.global_name,
    		() -> new BlockItem(researchtable_BLOCK.get(), new Item.Properties()));
    
}
