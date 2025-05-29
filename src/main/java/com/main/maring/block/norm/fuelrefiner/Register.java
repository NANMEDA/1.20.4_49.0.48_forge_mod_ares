 package com.main.maring.block.norm.fuelrefiner;

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
	
    public static final RegistryObject<Block> fuelrefiner_BLOCK = BlockRegister.BLOCKS.register(BlockFuelRefiner.global_name, () -> {
		return new BlockFuelRefiner(BlockBehaviour.Properties.of()
	            .sound(SoundType.AMETHYST)
	            .strength(5f,5f)
	            .noOcclusion()
	            .mapColor(MapColor.COLOR_ORANGE)); 
	});
    public static final RegistryObject<Item> fuelrefiner_BLOCK_ITEM = BlockRegister.BLOCK_ITEMS.register(BlockFuelRefiner.global_name,
    		() -> new BlockItem(fuelrefiner_BLOCK.get(), new Item.Properties()));
    
    public static final RegistryObject<Block> fuelrefinerbehind_BLOCK = BlockRegister.BLOCKS.register(BlockFuelRefinerbehind.global_name, () -> {
		return new BlockFuelRefinerbehind(BlockBehaviour.Properties.of()
	            .sound(SoundType.AMETHYST)
	            .strength(5f,5f)
	            .noOcclusion()
	            .mapColor(MapColor.NONE)); 
	});
    public static final RegistryObject<Item> fuelrefinerbehind_BLOCK_ITEM = BlockRegister.BLOCK_ITEMS.register(BlockFuelRefinerbehind.global_name, () -> new BlockItem(fuelrefinerbehind_BLOCK.get(), new Item.Properties()));

    public static final RegistryObject<Block> fuelrefinerleft_BLOCK = BlockRegister.BLOCKS.register(BlockFuelRefinerleft.global_name, () -> {
		return new BlockFuelRefinerleft(BlockBehaviour.Properties.of()
	            .sound(SoundType.AMETHYST)
	            .strength(5f,5f)
	            .noOcclusion()
	            .mapColor(MapColor.NONE)); 
	});
    public static final RegistryObject<Item> fuelrefinerleft_BLOCK_ITEM = BlockRegister.BLOCK_ITEMS.register(BlockFuelRefinerleft.global_name, () -> new BlockItem(fuelrefinerleft_BLOCK.get(), new Item.Properties()));

    public static final RegistryObject<Block> fuelrefinerup_BLOCK = BlockRegister.BLOCKS.register(BlockFuelRefinerup.global_name, () -> {
		return new BlockFuelRefinerup(BlockBehaviour.Properties.of()
	            .sound(SoundType.AMETHYST)
	            .strength(5f,5f)
	            .noOcclusion()
	            .mapColor(MapColor.NONE)); 
	});
    public static final RegistryObject<Item> fuelrefinerup_BLOCK_ITEM = BlockRegister.BLOCK_ITEMS.register(BlockFuelRefinerup.global_name, () -> new BlockItem(fuelrefinerup_BLOCK.get(), new Item.Properties()));

}
