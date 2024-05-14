package com.block.register;

import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.stream.IntStream;

import javax.annotation.Nonnull;

import com.block.register.BlockBasic;
import com.block.register.BlockElectricMachine;
import com.item.register.itemFood;

public class BlockRegister {
	private static final String MODID = "maring";
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static final DeferredRegister<Item> BLOCK_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    
    public static final RegistryObject<Block>[] COMMON_BLOCKS = new RegistryObject[BlockBasic.BLOCK_BASIC_NUMBER];
    public static final RegistryObject<Item>[] COMMON_BLOCK_ITEMS = new RegistryObject[BlockBasic.BLOCK_BASIC_NUMBER];
    public static final RegistryObject<Block>[] ELECTRIC_BLOCKS = new RegistryObject[BlockElectricMachine.BLOCK_ELECTRIC_NUMBER];
    public static final RegistryObject<Item>[] ELECTRIC_BLOCK_ITEMS = new RegistryObject[BlockElectricMachine.BLOCK_ELECTRIC_NUMBER];
    
    public static final RegistryObject<Block> PowerStationBurn_BLOCKS;
    public static final RegistryObject<Item> PowerStationBurn_BLOCKS_ITEM;
    
    static {
        IntStream.range(0, BlockBasic.BLOCK_BASIC_NUMBER).forEach(i -> {
        	COMMON_BLOCKS[i] = BLOCKS.register(BlockBasic.getBlockName(i), () -> {
        	    BlockBehaviour.Properties properties = BlockBehaviour.Properties.of()
        	            .sound(BlockBasic.getBlockSound(i))
        	            .strength(BlockBasic.getBlockStrength(i)[0], BlockBasic.getBlockStrength(i)[1])
        	            .mapColor(BlockBasic.getBlockMapColor(i));
        	    if (BlockBasic.needTool(i)) {
        	        properties.requiresCorrectToolForDrops();
        	    }
        	    return new Block(properties);
        	});
        	COMMON_BLOCK_ITEMS[i] = BLOCK_ITEMS.register(BlockBasic.getBlockName(i), () -> new BlockItem(COMMON_BLOCKS[i].get(), new Item.Properties()));
        });
        IntStream.range(0, BlockElectricMachine.BLOCK_ELECTRIC_NUMBER).forEach(i -> {
        	ELECTRIC_BLOCKS[i] = BLOCKS.register(BlockElectricMachine.getBlockName(i), () -> {
        	    BlockBehaviour.Properties properties = BlockBehaviour.Properties.of()
        	    		//.noOcclusion()
        	            .sound(BlockElectricMachine.getBlockSound(i))
        	            .strength(BlockElectricMachine.getBlockStrength(i)[0], BlockElectricMachine.getBlockStrength(i)[1])
        	            .mapColor(BlockElectricMachine.getBlockMapColor(i));
        	    if (BlockElectricMachine.needTool(i)) {
        	        properties.requiresCorrectToolForDrops();
        	    }
        	    return new Block(properties) {
        	    };
        	});
        	ELECTRIC_BLOCK_ITEMS[i] = BLOCK_ITEMS.register(BlockElectricMachine.getBlockName(i), () -> new BlockItem(ELECTRIC_BLOCKS[i].get(), new Item.Properties()));
        });
        

    	PowerStationBurn_BLOCKS = BLOCKS.register("powerstation_burn", () -> {
    		return new PowerStationBurn(BlockBehaviour.Properties.of()
    	            .sound(SoundType.STONE)
    	            .strength(5f,5f)
    	            .mapColor(MapColor.COLOR_GRAY)); 
    	});
    	PowerStationBurn_BLOCKS_ITEM = BLOCK_ITEMS.register("powerstation_burn", () -> new BlockItem(PowerStationBurn_BLOCKS.get(), new Item.Properties()));
    }
}
