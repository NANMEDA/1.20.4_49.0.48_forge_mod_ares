package machine.registry;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import machine.energy.consumer.coredigger.CoreColdPipe;
import machine.energy.consumer.coredigger.CoreDigger;
import machine.energy.consumer.coredigger.CorePipe;
import machine.energy.consumer.coredigger.CoreSucker;
import machine.energy.consumer.microwaveoven.BlockMicrowaveOven;
import machine.energy.producer.solar.SolarBasement;
import machine.energy.producer.solar.SolarPanel;
import machine.energy.producer.solar.SolarPillar;
import machine.energy.storage.battery.BatteryBasement;
import machine.energy.storage.battery.BatteryCapacity;
import machine.energy.viewer.EnergyViewer;

/**
 * @author NANMEDA
 * */
public class MBlockRegister {
	private static final String MODID = "maring";
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static final DeferredRegister<Item> BLOCK_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    
    public static final RegistryObject<Block> SOLARBASEMENT_B = BLOCKS.register(SolarBasement.global_name, () -> {
		return new SolarBasement(BlockBehaviour.Properties.of()); 
	});
    public static final RegistryObject<Item> SOLARBASEMENT_I = BLOCK_ITEMS.register(SolarBasement.global_name, 
    		() -> new BlockItem(SOLARBASEMENT_B.get(), new Item.Properties()));

    public static final RegistryObject<Block> SOLARPANEL_B = BLOCKS.register(SolarPanel.global_name, () -> {
		return new SolarPanel(BlockBehaviour.Properties.of()); 
	});
    public static final RegistryObject<Item> SOLARPANEL_I = BLOCK_ITEMS.register(SolarPanel.global_name, 
    		() -> new BlockItem(SOLARPANEL_B.get(), new Item.Properties()));
    
    public static final RegistryObject<Block> SOLARPILLAR_B = BLOCKS.register(SolarPillar.global_name, () -> {
		return new SolarPillar(BlockBehaviour.Properties.of()); 
	});
    public static final RegistryObject<Item> SOLARPILLAR_I = BLOCK_ITEMS.register(SolarPillar.global_name, 
    		() -> new BlockItem(SOLARPILLAR_B.get(), new Item.Properties()));

    public static final RegistryObject<Block> MINCROWAVEOVEN_B = BLOCKS.register(BlockMicrowaveOven.global_name, () -> {
		return new BlockMicrowaveOven(BlockBehaviour.Properties.of()); 
	});
    public static final RegistryObject<Item> MINCROWAVEOVEN_I = BLOCK_ITEMS.register(BlockMicrowaveOven.global_name,
    		() -> new BlockItem(MINCROWAVEOVEN_B.get(), new Item.Properties()));

    public static final RegistryObject<Block> BATTERYCAPACITY_B = BLOCKS.register(BatteryCapacity.global_name, () -> {
		return new BatteryCapacity(BlockBehaviour.Properties.of()); 
	});
    public static final RegistryObject<Item> BATTERYCAPACITY_I = BLOCK_ITEMS.register(BatteryCapacity.global_name,
    		() -> new BlockItem(BATTERYCAPACITY_B.get(), new Item.Properties()));
    
    public static final RegistryObject<Block> BATTERTBASEMENT_B = BLOCKS.register(BatteryBasement.global_name, () -> {
		return new BatteryBasement(BlockBehaviour.Properties.of()); 
	});
    public static final RegistryObject<Item> BATTERTBASEMENT_I = BLOCK_ITEMS.register(BatteryBasement.global_name,
    		() -> new BlockItem(BATTERTBASEMENT_B.get(), new Item.Properties()));
 
    public static final RegistryObject<Block> ENERGYVIEWER_B = BLOCKS.register(EnergyViewer.global_name, () -> {
		return new EnergyViewer(BlockBehaviour.Properties.of()); 
	});
    public static final RegistryObject<Item> ENERGYVIEWER_I = BLOCK_ITEMS.register(EnergyViewer.global_name,
    		() -> new BlockItem(ENERGYVIEWER_B.get(), new Item.Properties()));
    		
    public static final RegistryObject<Block> COREDIDDER_B = BLOCKS.register(CoreDigger.global_name, () -> {
		return new CoreDigger(BlockBehaviour.Properties.of()); 
	});
    public static final RegistryObject<Item> COREDIDDER_I = BLOCK_ITEMS.register(CoreDigger.global_name,
    		() -> new BlockItem(COREDIDDER_B.get(), new Item.Properties()));
    
    public static final RegistryObject<Block> CORESUCKER_B = BLOCKS.register(CoreSucker.global_name, () -> {
		return new CoreSucker(BlockBehaviour.Properties.of()); 
	});
    public static final RegistryObject<Item> CORESUCKER_I = BLOCK_ITEMS.register(CoreSucker.global_name,
    		() -> new BlockItem(CORESUCKER_B.get(), new Item.Properties()));
    
    public static final RegistryObject<Block> COREPIPE_B = BLOCKS.register(CorePipe.global_name, () -> {
		return new CorePipe(BlockBehaviour.Properties.of()); 
	});
    public static final RegistryObject<Item> COREPIPE_I = BLOCK_ITEMS.register(CorePipe.global_name,
    		() -> new BlockItem(COREPIPE_B.get(), new Item.Properties()));
 
    public static final RegistryObject<Block> CORECOLDPIPE_B = BLOCKS.register(CoreColdPipe.global_name, () -> {
		return new CoreColdPipe(BlockBehaviour.Properties.of()); 
	});
    public static final RegistryObject<Item> CORECOLDPIPE_I = BLOCK_ITEMS.register(CoreColdPipe.global_name,
    		() -> new BlockItem(CORECOLDPIPE_B.get(), new Item.Properties()));
}
