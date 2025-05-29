 package com.main.maring.block.norm.stonewasher;

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
	
    public static final RegistryObject<Block> stonewasher_BLOCK = BlockRegister.BLOCKS.register(BlockStoneWasher.global_name, () -> {
		return new BlockStoneWasher(BlockBehaviour.Properties.of()
	            .sound(SoundType.AMETHYST)
	            .strength(5f,5f)
	            .noOcclusion()
	            .mapColor(MapColor.COLOR_ORANGE)); 
	});
    public static final RegistryObject<Item> stonewasher_BLOCK_ITEM = BlockRegister.BLOCK_ITEMS.register(BlockStoneWasher.global_name,
    		() -> new BlockItem(stonewasher_BLOCK.get(), new Item.Properties()));
    
    public static final RegistryObject<Block> stonewasherup_BLOCK = BlockRegister.BLOCKS.register(BlockStoneWasherup.global_name, () -> {
		return new BlockStoneWasherup(BlockBehaviour.Properties.of()
	            .sound(SoundType.AMETHYST)
	            .strength(5f,5f)
	            .noOcclusion()
	            .mapColor(MapColor.NONE)); 
	});
    public static final RegistryObject<Item> stonewasherup_BLOCK_ITEM = BlockRegister.BLOCK_ITEMS.register(BlockStoneWasherup.global_name, () -> new BlockItem(stonewasherup_BLOCK.get(), new Item.Properties()));

}
