package machine.registry;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.stream.IntStream;

import block.norm.basicmetalmanufactor.BlockBasicMetalManufactor;
import block.norm.basicmetalmanufactor.BlockBasicMetalManufactorbehind;
import block.norm.basicmetalmanufactor.BlockBasicMetalManufactorbehindup;
import block.norm.basicmetalmanufactor.BlockBasicMetalManufactorleft;
import block.norm.basicmetalmanufactor.BlockBasicMetalManufactorup;
import block.norm.bioplasticbuilder.BlockBioplasticBuilder;
import block.norm.bioplasticbuilder.BlockBioplasticBuilderbehind;
import block.norm.bioplasticbuilder.BlockBioplasticBuilderleft;
import block.norm.bioplasticbuilder.BlockBioplasticBuilderup;
import block.norm.powerstation.burn.PowerStationBurn;
import block.norm.powerstation.sun.PowerStationSun;
import block.norm.unbroken.BlockUnbrokenCement;
import block.norm.unbroken.BlockUnbrokenFog;
import block.norm.unbroken.BlockUnbrokenGlass;
import block.norm.unbroken.BlockUnbrokenGreen;
import block.norm.unbroken.BlockUnbrokenLightblue;
import machine.energy.consumer.microwaveoven.BlockMicrowaveOven;
import machine.energy.producer.solar.SolarBasement;
import machine.energy.producer.solar.SolarPanel;
import machine.energy.producer.solar.SolarPillar;

/**
 * @author NANMEDA
 * */
public class MBlockRegister {
	private static final String MODID = "maring";
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static final DeferredRegister<Item> BLOCK_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    
    public static final RegistryObject<Block> SOLARBASEMENT_B = BLOCKS.register(SolarBasement.global_name, () -> {
		return new SolarBasement(BlockBehaviour.Properties.of()
	            .sound(SoundType.AMETHYST)
	            .strength(5f,5f)
	            .noOcclusion()
	            .mapColor(MapColor.COLOR_GRAY)); 
	});
    public static final RegistryObject<Item> SOLARBASEMENT_I = BLOCK_ITEMS.register(SolarBasement.global_name, 
    		() -> new BlockItem(SOLARBASEMENT_B.get(), new Item.Properties()));

    public static final RegistryObject<Block> SOLARPANEL_B = BLOCKS.register(SolarPanel.global_name, () -> {
		return new SolarPanel(BlockBehaviour.Properties.of()
	            .sound(SoundType.AMETHYST)
	            .strength(5f,5f)
	            .noOcclusion()
	            .mapColor(MapColor.COLOR_GRAY)); 
	});
    public static final RegistryObject<Item> SOLARPANEL_I = BLOCK_ITEMS.register(SolarPanel.global_name, 
    		() -> new BlockItem(SOLARPANEL_B.get(), new Item.Properties()));
    
    public static final RegistryObject<Block> SOLARPILLAR_B = BLOCKS.register(SolarPillar.global_name, () -> {
		return new SolarPillar(BlockBehaviour.Properties.of()
	            .sound(SoundType.AMETHYST)
	            .strength(5f,5f)
	            .noOcclusion()
	            .mapColor(MapColor.COLOR_GRAY)); 
	});
    public static final RegistryObject<Item> SOLARPILLAR_I = BLOCK_ITEMS.register(SolarPillar.global_name, 
    		() -> new BlockItem(SOLARPILLAR_B.get(), new Item.Properties()));

    public static final RegistryObject<Block> microwaveoven_BLOCK = BLOCKS.register(BlockMicrowaveOven.global_name, () -> {
		return new BlockMicrowaveOven(BlockBehaviour.Properties.of()
	            .sound(SoundType.AMETHYST)
	            .strength(5f,5f)
	            .noOcclusion()
	            .mapColor(MapColor.COLOR_GRAY)); 
	});
    public static final RegistryObject<Item> microwaveoven_BLOCK_ITEM = BLOCK_ITEMS.register(BlockMicrowaveOven.global_name, () -> new BlockItem(microwaveoven_BLOCK.get(), new Item.Properties()));

    
}
