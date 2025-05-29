package com.main.maring.block.norm.advancedmetalmanufactor;

import com.main.maring.block.norm.BlockRegister;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.RegistryObject;

public class Register {
	
	public static void init() {}
	
	
    public static final RegistryObject<Block> advancedmetalmanufactor_BLOCK = BlockRegister.BLOCKS.register(BlockAdvancedMetalManufactor.global_name, () -> {
		return new BlockAdvancedMetalManufactor(BlockBehaviour.Properties.of()
	            .sound(SoundType.STONE)
	            .strength(5f,5f)
	            .noOcclusion()
	            .mapColor(MapColor.COLOR_BLUE)); 
	});
    public static final RegistryObject<Item> advancedmetalmanufactor_BLOCK_ITEM = BlockRegister.BLOCK_ITEMS.register(BlockAdvancedMetalManufactor.global_name,
    		() -> new BlockItem(advancedmetalmanufactor_BLOCK.get(), new Item.Properties()));
    
    public static final RegistryObject<Block> advancedmetalmanufactorup_BLOCK = BlockRegister.BLOCKS.register(BlockAdvancedMetalManufactorup.global_name, () -> {
		return new BlockAdvancedMetalManufactorup(BlockBehaviour.Properties.of()
	            .sound(SoundType.STONE)
	            .strength(5f,5f)
	            .noOcclusion()
	            .mapColor(MapColor.NONE)); 
	});
    public static final RegistryObject<Item> advancedmetalmanufactorup_BLOCK_ITEM = BlockRegister.BLOCK_ITEMS.register(BlockAdvancedMetalManufactorup.global_name, () -> new BlockItem(advancedmetalmanufactorup_BLOCK.get(), new Item.Properties()));

    public static final RegistryObject<Block> advancedmetalmanufactoraround_BLOCK = BlockRegister.BLOCKS.register(BlockAdvancedMetalManufactoraround.global_name, () -> {
		return new BlockAdvancedMetalManufactoraround(BlockBehaviour.Properties.of()
	            .sound(SoundType.STONE)
	            .strength(5f,5f)
	            .noOcclusion()
	            .mapColor(MapColor.NONE)); 
	});
    public static final RegistryObject<Item> advancedmetalmanufactoraround_BLOCK_ITEM = BlockRegister.BLOCK_ITEMS.register(BlockAdvancedMetalManufactoraround.global_name, () -> new BlockItem(advancedmetalmanufactoraround_BLOCK.get(), new Item.Properties()));
	
    public static final RegistryObject<Block> advancedmetalmanufactorcorner_BLOCK = BlockRegister.BLOCKS.register(BlockAdvancedMetalManufactorcorner.global_name, () -> {
		return new BlockAdvancedMetalManufactorcorner(BlockBehaviour.Properties.of()
	            .sound(SoundType.STONE)
	            .strength(5f,5f)
	            .noOcclusion()
	            .mapColor(MapColor.NONE)); 
	});
    public static final RegistryObject<Item> advancedmetalmanufactorcorner_BLOCK_ITEM = BlockRegister.BLOCK_ITEMS.register(BlockAdvancedMetalManufactorcorner.global_name, () -> new BlockItem(advancedmetalmanufactorcorner_BLOCK.get(), new Item.Properties()));
	
}
