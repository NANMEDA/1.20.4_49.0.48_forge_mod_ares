package com.main.maring.block.norm.watergather;

import com.main.maring.block.norm.BlockRegister;
import com.main.maring.machine.energy.consumer.watergather.BlockWaterGather;
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
	
    public static final RegistryObject<Block> watergather_BLOCK = BlockRegister.BLOCKS.register(BlockWaterGather.global_name, () -> {
		return new BlockWaterGather(BlockBehaviour.Properties.of()
	            .sound(SoundType.STONE)
	            .strength(5f,5f)
	            .noOcclusion()
	            .mapColor(MapColor.COLOR_BLUE)); 
	});
    public static final RegistryObject<Item> watergather_BLOCK_ITEM = BlockRegister.BLOCK_ITEMS.register(BlockWaterGather.global_name,
    		() -> new BlockItem(watergather_BLOCK.get(), new Item.Properties()));
    
    public static final RegistryObject<Block> watergatherup_BLOCK = BlockRegister.BLOCKS.register(BlockWaterGatherup.global_name, () -> {
		return new BlockWaterGatherup(BlockBehaviour.Properties.of()
	            .sound(SoundType.STONE)
	            .strength(5f,5f)
	            .noOcclusion()
	            .mapColor(MapColor.NONE)); 
	});
    public static final RegistryObject<Item> watergatherup_BLOCK_ITEM = BlockRegister.BLOCK_ITEMS.register(BlockWaterGatherup.global_name, () -> new BlockItem(watergatherup_BLOCK.get(), new Item.Properties()));

    public static final RegistryObject<Block> watergatheraround_BLOCK = BlockRegister.BLOCKS.register(BlockWaterGatheraround.global_name, () -> {
		return new BlockWaterGatheraround(BlockBehaviour.Properties.of()
	            .sound(SoundType.STONE)
	            .strength(5f,5f)
	            .noOcclusion()
	            .mapColor(MapColor.NONE)); 
	});
    public static final RegistryObject<Item> watergatheraround_BLOCK_ITEM = BlockRegister.BLOCK_ITEMS.register(BlockWaterGatheraround.global_name, () -> new BlockItem(watergatheraround_BLOCK.get(), new Item.Properties()));
	
    public static final RegistryObject<Block> watergathercorner_BLOCK = BlockRegister.BLOCKS.register(BlockWaterGathercorner.global_name, () -> {
		return new BlockWaterGathercorner(BlockBehaviour.Properties.of()
	            .sound(SoundType.STONE)
	            .strength(5f,5f)
	            .noOcclusion()
	            .mapColor(MapColor.NONE)); 
	});
    public static final RegistryObject<Item> watergathercorner_BLOCK_ITEM = BlockRegister.BLOCK_ITEMS.register(BlockWaterGathercorner.global_name, () -> new BlockItem(watergathercorner_BLOCK.get(), new Item.Properties()));
	
}
