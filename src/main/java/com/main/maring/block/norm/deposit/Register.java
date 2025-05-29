package com.main.maring.block.norm.deposit;

import com.main.maring.block.norm.BlockRegister;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import com.main.maring.util.json.BlockJSON;

public class Register {
	private static final String f = "deposit";
	
    private static DeferredRegister<Block> BLOCKS = BlockRegister.BLOCKS;
    private static DeferredRegister<Item> BLOCK_ITEMS = BlockRegister.BLOCK_ITEMS;
    
    public static final RegistryObject<Block> DEPOSIT_EMPTY = BLOCKS.register(f+DepositEmpty.global_name, () ->
            new DepositEmpty(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BROWN)
    				.sound(SoundType.MUD)
                    .strength(0.5f, 0.5f)
                   ));
    public static final RegistryObject<Item> DEPOSIT_EMPTY_ITEM = BLOCK_ITEMS.register(f+DepositEmpty.global_name,
            () -> new BlockItem(DEPOSIT_EMPTY.get(), new Item.Properties()));
    
    public static final RegistryObject<Block> DEPOSIT_ICE = BLOCKS.register(f+DepositIce.global_name, () ->
    	new DepositIce(BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_BROWN)
			.sound(SoundType.MUD)
            .strength(0.5f, 0.5f)
           ));
    public static final RegistryObject<Item> DEPOSIT_ICE_ITEM = BLOCK_ITEMS.register(f+DepositIce.global_name,
    		() -> new BlockItem(DEPOSIT_ICE.get(), new Item.Properties()));
    
    public static final RegistryObject<Block> DEPOSIT_IRON = BLOCKS.register(f+DepositIron.global_name, () ->
		new DepositIron(BlockBehaviour.Properties.of()
	        .mapColor(MapColor.COLOR_BROWN)
			.sound(SoundType.MUD)
            .strength(0.5f, 0.5f)
	       ));
	public static final RegistryObject<Item> DEPOSIT_IRON_ITEM = BLOCK_ITEMS.register(f+DepositIron.global_name,
			() -> new BlockItem(DEPOSIT_IRON.get(), new Item.Properties()));
	
    public static final RegistryObject<Block> DEPOSIT_GOLD = BLOCKS.register(f+DepositGold.global_name, () ->
		new DepositGold(BlockBehaviour.Properties.of()
	        .mapColor(MapColor.COLOR_BROWN)
			.sound(SoundType.MUD)
            .strength(0.5f, 0.5f)
	       ));
	public static final RegistryObject<Item> DEPOSIT_GOLD_ITEM = BLOCK_ITEMS.register(f+DepositGold.global_name,
			() -> new BlockItem(DEPOSIT_GOLD.get(), new Item.Properties()));


    
    public static void init() {
		/*
    	gen(f+DepositEmpty.global_name);
    	gen(f+DepositIce.global_name);
    	gen(f+DepositIron.global_name);
    	gen(f+DepositGold.global_name);*/
    }
    
    private static void gen(String name) {
        BlockJSON.GenModelsJSONBasic(name);
        BlockJSON.GenBlockStateJSONBasic(name);
        BlockJSON.GenItemJSONBasic(name);
        BlockJSON.GenLootTableJSONBasic(name,"deposit_empty");
        BlockJSON.GenToolJSON("shovel", name);
        BlockJSON.GenToolLevelJSON(0, name);
    }
}
