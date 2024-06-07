package block.norm.glassbuilder;

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
	
    public static final RegistryObject<Block> glassbuilder_BLOCK = BlockRegister.BLOCKS.register(BlockGlassBuilder.global_name, () -> {
		return new BlockGlassBuilder(BlockBehaviour.Properties.of()
	            .sound(SoundType.STONE)
	            .strength(5f,5f)
	            .noOcclusion()
	            .mapColor(MapColor.COLOR_ORANGE)); 
	});
    public static final RegistryObject<Item> glassbuilder_BLOCK_ITEM = BlockRegister.BLOCK_ITEMS.register(BlockGlassBuilder.global_name,
    		() -> new BlockItem(glassbuilder_BLOCK.get(), new Item.Properties()));
    
    public static final RegistryObject<Block> glassbuilderup_BLOCK = BlockRegister.BLOCKS.register(BlockGlassBuilderup.global_name, () -> {
		return new BlockGlassBuilderup(BlockBehaviour.Properties.of()
	            .sound(SoundType.STONE)
	            .strength(5f,5f)
	            .noOcclusion()
	            .mapColor(MapColor.NONE)); 
	});
    public static final RegistryObject<Item> glassbuilderup_BLOCK_ITEM = BlockRegister.BLOCK_ITEMS.register(BlockGlassBuilderup.global_name, () -> new BlockItem(glassbuilderup_BLOCK.get(), new Item.Properties()));

    public static final RegistryObject<Block> glassbuilderleft_BLOCK = BlockRegister.BLOCKS.register(BlockGlassBuilderleft.global_name, () -> {
		return new BlockGlassBuilderleft(BlockBehaviour.Properties.of()
	            .sound(SoundType.STONE)
	            .strength(5f,5f)
	            .noOcclusion()
	            .mapColor(MapColor.NONE)); 
	});
    public static final RegistryObject<Item> glassbuilderleft_BLOCK_ITEM = BlockRegister.BLOCK_ITEMS.register(BlockGlassBuilderleft.global_name, () -> new BlockItem(glassbuilderleft_BLOCK.get(), new Item.Properties()));
	
}
