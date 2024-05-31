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
import com.block.register.BlockElectricBasic;
import com.block.register.BasicMetalManufactor.BlockBasicMetalManufactor;
import com.block.register.BasicMetalManufactor.BlockBasicMetalManufactorbehind;
import com.block.register.BasicMetalManufactor.BlockBasicMetalManufactorbehindup;
import com.block.register.BasicMetalManufactor.BlockBasicMetalManufactorleft;
import com.block.register.BasicMetalManufactor.BlockBasicMetalManufactorup;
import com.item.register.itemFood;

public class BlockRegister {
	private static final String MODID = "maring";
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static final DeferredRegister<Item> BLOCK_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    
    public static final RegistryObject<Block>[] COMMON_BLOCKS = new RegistryObject[BlockBasic.BLOCK_BASIC_NUMBER];
    public static final RegistryObject<Item>[] COMMON_BLOCK_ITEMS = new RegistryObject[BlockBasic.BLOCK_BASIC_NUMBER];
    public static final RegistryObject<Block>[] ELECTRIC_BLOCKS = new RegistryObject[BlockElectricBasic.BLOCK_ELECTRIC_NUMBER];
    public static final RegistryObject<Item>[] ELECTRIC_BLOCK_ITEMS = new RegistryObject[BlockElectricBasic.BLOCK_ELECTRIC_NUMBER];
    
    public static final RegistryObject<Block> PowerStationBurn_BLOCK;
    public static final RegistryObject<Item> PowerStationBurn_BLOCK_ITEM;
    public static final RegistryObject<Block> PowerStationSun_BLOCK;
    public static final RegistryObject<Item> PowerStationSun_BLOCK_ITEM;
    public static final RegistryObject<Block> canfoodmaker_BLOCK;
    public static final RegistryObject<Item> canfoodmaker_BLOCK_ITEM;
    //public static final RegistryObject<Block> basicmetalmanufactor_BLOCK;
    //public static final RegistryObject<Item> basicmetalmanufactor_BLOCK_ITEM;
    
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
        IntStream.range(0, BlockElectricBasic.BLOCK_ELECTRIC_NUMBER).forEach(i -> {
        	ELECTRIC_BLOCKS[i] = BLOCKS.register(BlockElectricBasic.getBlockName(i), () -> {
        	    BlockBehaviour.Properties properties = BlockBehaviour.Properties.of()
        	    		//.noOcclusion()
        	            .sound(BlockElectricBasic.getBlockSound(i))
        	            .strength(BlockElectricBasic.getBlockStrength(i)[0], BlockElectricBasic.getBlockStrength(i)[1])
        	            .mapColor(BlockElectricBasic.getBlockMapColor(i));
        	    if (BlockElectricBasic.needTool(i)) {
        	        properties.requiresCorrectToolForDrops();
        	    }
        	    return new Block(properties) {
        	    };
        	});
        	ELECTRIC_BLOCK_ITEMS[i] = BLOCK_ITEMS.register(BlockElectricBasic.getBlockName(i), () -> new BlockItem(ELECTRIC_BLOCKS[i].get(), new Item.Properties()));
        });
        

    	PowerStationBurn_BLOCK = BLOCKS.register(PowerStationBurn.global_name, () -> {
    		return new PowerStationBurn(BlockBehaviour.Properties.of()
    	            .sound(SoundType.STONE)
    	            .strength(5f,5f)
    	            .mapColor(MapColor.COLOR_GRAY)); 
    	});
    	PowerStationBurn_BLOCK_ITEM = BLOCK_ITEMS.register(PowerStationBurn.global_name, () -> new BlockItem(PowerStationBurn_BLOCK.get(), new Item.Properties()));
    	
    	PowerStationSun_BLOCK = BLOCKS.register(PowerStationSun.global_name, () -> {
    		return new PowerStationSun(BlockBehaviour.Properties.of()
    	            .sound(SoundType.AMETHYST)
    	            .strength(5f,5f)
    	            .mapColor(MapColor.COLOR_BLUE)); 
    	});
    	PowerStationSun_BLOCK_ITEM = BLOCK_ITEMS.register(PowerStationSun.global_name, () -> new BlockItem(PowerStationSun_BLOCK.get(), new Item.Properties()));

    	
    	
    	
    	canfoodmaker_BLOCK = BLOCKS.register(BlockCanfoodMaker.global_name, () -> {
    		return new BlockCanfoodMaker(BlockBehaviour.Properties.of()
    	            .sound(SoundType.STONE)
    	            .strength(5f,5f)
    	            .mapColor(MapColor.COLOR_GRAY)); 
    	});
    	canfoodmaker_BLOCK_ITEM = BLOCK_ITEMS.register(BlockCanfoodMaker.global_name, () -> new BlockItem(canfoodmaker_BLOCK.get(), new Item.Properties()));

    }
    
    
    public static final RegistryObject<Block> microwaveoven_BLOCK = BLOCKS.register(BlockMicrowaveOven.global_name, () -> {
		return new BlockMicrowaveOven(BlockBehaviour.Properties.of()
	            .sound(SoundType.AMETHYST)
	            .strength(5f,5f)
	            .noOcclusion()
	            .mapColor(MapColor.COLOR_GRAY)); 
	});
    public static final RegistryObject<Item> microwaveoven_BLOCK_ITEM = BLOCK_ITEMS.register(BlockMicrowaveOven.global_name, () -> new BlockItem(microwaveoven_BLOCK.get(), new Item.Properties()));

    public static final RegistryObject<Block> unbrokenglass_BLOCK = BLOCKS.register(BlockUnbrokenGlass.global_name, () -> {
		return new BlockUnbrokenGlass(BlockBehaviour.Properties.of()
	            .sound(SoundType.GLASS)
	            .strength(-1.0f,-1.0f)
	            .noOcclusion()
	            .mapColor(MapColor.NONE)); 
	});
    public static final RegistryObject<Item> unbrokenglass_BLOCK_ITEM = BLOCK_ITEMS.register(BlockUnbrokenGlass.global_name, () -> new BlockItem(unbrokenglass_BLOCK.get(), new Item.Properties()));

    public static final RegistryObject<Block> basicmetalmanufactor_BLOCK = BLOCKS.register(BlockBasicMetalManufactor.global_name, () -> {
		return new BlockBasicMetalManufactor(BlockBehaviour.Properties.of()
	            .sound(SoundType.STONE)
	            .strength(5f,5f)
	            .noOcclusion()
	            .mapColor(MapColor.COLOR_GRAY)); 
	});
    public static final RegistryObject<Item> basicmetalmanufactor_BLOCK_ITEM = BLOCK_ITEMS.register(BlockBasicMetalManufactor.global_name, () -> new BlockItem(basicmetalmanufactor_BLOCK.get(), new Item.Properties()));

    public static final RegistryObject<Block> basicmetalmanufactorup_BLOCK = BLOCKS.register(BlockBasicMetalManufactorup.global_name, () -> {
		return new BlockBasicMetalManufactorup(BlockBehaviour.Properties.of()
	            .sound(SoundType.STONE)
	            .strength(5f,5f)
	            .noOcclusion()
	            .mapColor(MapColor.NONE)); 
	});
    public static final RegistryObject<Item> basicmetalmanufactorup_BLOCK_ITEM = BLOCK_ITEMS.register(BlockBasicMetalManufactorup.global_name, () -> new BlockItem(basicmetalmanufactorup_BLOCK.get(), new Item.Properties()));

    public static final RegistryObject<Block> basicmetalmanufactorleft_BLOCK = BLOCKS.register(BlockBasicMetalManufactorleft.global_name, () -> {
		return new BlockBasicMetalManufactorleft(BlockBehaviour.Properties.of()
	            .sound(SoundType.STONE)
	            .strength(5f,5f)
	            .noOcclusion()
	            .mapColor(MapColor.NONE)); 
	});
    public static final RegistryObject<Item> basicmetalmanufactorleft_BLOCK_ITEM = BLOCK_ITEMS.register(BlockBasicMetalManufactorleft.global_name, () -> new BlockItem(basicmetalmanufactorleft_BLOCK.get(), new Item.Properties()));

    public static final RegistryObject<Block> basicmetalmanufactorbehind_BLOCK = BLOCKS.register(BlockBasicMetalManufactorbehind.global_name, () -> {
		return new BlockBasicMetalManufactorbehind(BlockBehaviour.Properties.of()
	            .sound(SoundType.STONE)
	            .strength(5f,5f)
	            .noOcclusion()
	            .mapColor(MapColor.NONE)); 
	});
    public static final RegistryObject<Item> basicmetalmanufactorbehind_BLOCK_ITEM = BLOCK_ITEMS.register(BlockBasicMetalManufactorbehind.global_name,
    		() -> new BlockItem(basicmetalmanufactorbehind_BLOCK.get(), new Item.Properties()));

    public static final RegistryObject<Block> basicmetalmanufactorbehindup_BLOCK = BLOCKS.register(BlockBasicMetalManufactorbehindup.global_name, () -> {
		return new BlockBasicMetalManufactorbehindup(BlockBehaviour.Properties.of()
	            .sound(SoundType.STONE)
	            .strength(5f,5f)
	            .noOcclusion()
	            .mapColor(MapColor.NONE)); 
	});
    public static final RegistryObject<Item> basicmetalmanufactorbehindup_BLOCK_ITEM = BLOCK_ITEMS.register(BlockBasicMetalManufactorbehindup.global_name,
    		() -> new BlockItem(basicmetalmanufactorbehindup_BLOCK.get(), new Item.Properties()));
    
}
