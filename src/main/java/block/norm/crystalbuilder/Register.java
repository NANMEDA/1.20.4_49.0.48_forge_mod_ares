package block.norm.crystalbuilder;

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
	
    public static final RegistryObject<Block> crystalbuilder_BLOCK = BlockRegister.BLOCKS.register(BlockCrystalBuilder.global_name, () -> {
		return new BlockCrystalBuilder(BlockBehaviour.Properties.of()
	            .sound(SoundType.STONE)
	            .strength(5f,5f)
	            .noOcclusion()
	            .mapColor(MapColor.COLOR_ORANGE)); 
	});
    public static final RegistryObject<Item> crystalbuilder_BLOCK_ITEM = BlockRegister.BLOCK_ITEMS.register(BlockCrystalBuilder.global_name,
    		() -> new BlockItem(crystalbuilder_BLOCK.get(), new Item.Properties()));
    
    public static final RegistryObject<Block> crystalbuilderup_BLOCK = BlockRegister.BLOCKS.register(BlockCrystalBuilderup.global_name, () -> {
		return new BlockCrystalBuilderup(BlockBehaviour.Properties.of()
	            .sound(SoundType.STONE)
	            .strength(5f,5f)
	            .noOcclusion()
	            .mapColor(MapColor.NONE)); 
	});
    public static final RegistryObject<Item> crystalbuilderup_BLOCK_ITEM = BlockRegister.BLOCK_ITEMS.register(BlockCrystalBuilderup.global_name, () -> new BlockItem(crystalbuilderup_BLOCK.get(), new Item.Properties()));

    public static final RegistryObject<Block> crystalbuilderleft_BLOCK = BlockRegister.BLOCKS.register(BlockCrystalBuilderleft.global_name, () -> {
		return new BlockCrystalBuilderleft(BlockBehaviour.Properties.of()
	            .sound(SoundType.STONE)
	            .strength(5f,5f)
	            .noOcclusion()
	            .mapColor(MapColor.NONE)); 
	});
    public static final RegistryObject<Item> crystalbuilderleft_BLOCK_ITEM = BlockRegister.BLOCK_ITEMS.register(BlockCrystalBuilderleft.global_name, () -> new BlockItem(crystalbuilderleft_BLOCK.get(), new Item.Properties()));
	
}
