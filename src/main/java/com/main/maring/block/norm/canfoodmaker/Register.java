 package com.main.maring.block.norm.canfoodmaker;

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
	
    public static final RegistryObject<Block> canfoodmaker_BLOCK = BlockRegister.BLOCKS.register(BlockCanfoodMaker.global_name, () -> {
		return new BlockCanfoodMaker(BlockBehaviour.Properties.of()
	            .sound(SoundType.AMETHYST)
	            .strength(5f,5f)
	            .noOcclusion()
	            .mapColor(MapColor.COLOR_ORANGE)); 
	});
    public static final RegistryObject<Item> canfoodmaker_BLOCK_ITEM = BlockRegister.BLOCK_ITEMS.register(BlockCanfoodMaker.global_name,
    		() -> new BlockItem(canfoodmaker_BLOCK.get(), new Item.Properties()));
    
    public static final RegistryObject<Block> canfoodmakerleft_BLOCK = BlockRegister.BLOCKS.register(BlockCanfoodMakerleft.global_name, () -> {
		return new BlockCanfoodMakerleft(BlockBehaviour.Properties.of()
	            .sound(SoundType.AMETHYST)
	            .strength(5f,5f)
	            .noOcclusion()
	            .mapColor(MapColor.NONE)); 
	});
    public static final RegistryObject<Item> canfoodmakerleft_BLOCK_ITEM = BlockRegister.BLOCK_ITEMS.register(BlockCanfoodMakerleft.global_name, () -> new BlockItem(canfoodmakerleft_BLOCK.get(), new Item.Properties()));
	
}
