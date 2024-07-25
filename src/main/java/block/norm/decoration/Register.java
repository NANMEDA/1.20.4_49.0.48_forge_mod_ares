package block.norm.decoration;

import block.norm.BlockRegister;
import block.norm.decoration.environment.ExposedIron;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.RegistryObject;

public class Register {
	
	public static void init() {}
	
    public static final RegistryObject<Block> EXPOSEDIRON_BLOCK = BlockRegister.BLOCKS.register(ExposedIron.global_name, () -> {
		return new ExposedIron(BlockBehaviour.Properties.of()
	            .strength(0.5f)
	            .noOcclusion()
	            .sound(SoundType.STONE)
	            .mapColor(MapColor.COLOR_GRAY));
	});
    public static final RegistryObject<Item> EXPOSEDIRON_BLOCK_ITEM = BlockRegister.BLOCK_ITEMS.register(ExposedIron.global_name,
    		() -> new BlockItem(EXPOSEDIRON_BLOCK.get(), new Item.Properties()));

}
