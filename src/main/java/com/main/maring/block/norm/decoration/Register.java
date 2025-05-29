package com.main.maring.block.norm.decoration;

import java.util.function.ToIntFunction;

import com.main.maring.block.norm.BlockRegister;
import com.main.maring.block.norm.decoration.artificial.LightPane;
import com.main.maring.block.norm.decoration.environment.ExposedIron;
import com.main.maring.block.norm.decoration.environment.ExposedQuartz;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.RegistryObject;

public class Register {
	
	public static void init() {}
	
    public static final RegistryObject<Block> EXPOSEDIRON_BLOCK = BlockRegister.BLOCKS.register(ExposedIron.global_name, () -> {
		return new ExposedIron(BlockBehaviour.Properties.of()
	            .strength(0.5f)
	            .noOcclusion()
	            .sound(SoundType.STONE)
	            .mapColor(MapColor.COLOR_GRAY));
	});
    public static final RegistryObject<Item> EXPOSEDIRON_BLOCK_ITEM = BlockRegister.BLOCK_ITEMS.register(ExposedIron.global_name,
    		() -> new BlockItem(EXPOSEDIRON_BLOCK.get(), new Item.Properties()));

    public static final RegistryObject<Block> EXPOSEDQUARTZ_BLOCK = BlockRegister.BLOCKS.register(ExposedQuartz.global_name, () -> {
		return new ExposedQuartz(BlockBehaviour.Properties.of()
	            .strength(1.0f)
	            .noOcclusion()
	            .sound(SoundType.AMETHYST_CLUSTER)
	            .mapColor(MapColor.COLOR_GRAY));
	});
    public static final RegistryObject<Item> EXPOSEDQUARTZ_BLOCK_ITEM = BlockRegister.BLOCK_ITEMS.register(ExposedQuartz.global_name,
    		() -> new BlockItem(EXPOSEDQUARTZ_BLOCK.get(), new Item.Properties()));
    
    public static final RegistryObject<Block> LIGHT_PANE_BLOCK = BlockRegister.BLOCKS.register(LightPane.global_name, () -> {
		return new LightPane(BlockBehaviour.Properties.of()
	            .strength(1.0f)
	            .noOcclusion()
	            .sound(SoundType.AMETHYST_CLUSTER)
	            .lightLevel(litBlockEmission(15))
	            .mapColor(MapColor.COLOR_GRAY));
		
	});
    public static final RegistryObject<Item> LIGHT_PANE_BLOCK_ITEM = BlockRegister.BLOCK_ITEMS.register(LightPane.global_name,
    		() -> new BlockItem(LIGHT_PANE_BLOCK.get(), new Item.Properties()));
    
    
    
    
    
    
    private static ToIntFunction<BlockState> litBlockEmission(int p_50760_) {
        return (p_50763_) -> {
           return p_50763_.getValue(BlockStateProperties.LIT) ? p_50760_ : 0;
        };
     }
}
