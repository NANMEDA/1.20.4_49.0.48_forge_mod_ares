package com.main.maring.machine.registry;

import com.main.maring.Maring;
import com.main.maring.block.norm.machineparts.ElectronicPower;
import com.main.maring.machine.energy.consumer.electrolyticdevice.BlockElectrolyticDevice;
import com.main.maring.machine.energy.consumer.electrolyticdevice.ElectrolyticDeviceEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import com.main.maring.machine.energy.consumer.coredigger.CoreColdPipe;
import com.main.maring.machine.energy.consumer.coredigger.CoreDigger;
import com.main.maring.machine.energy.consumer.coredigger.CorePipe;
import com.main.maring.machine.energy.consumer.coredigger.CoreSucker;
import com.main.maring.machine.energy.consumer.microwaveoven.BlockMicrowaveOven;
import com.main.maring.machine.energy.producer.reactor.mar.MarReactor;
import com.main.maring.machine.energy.producer.solar.SolarBasement;
import com.main.maring.machine.energy.producer.solar.SolarPanel;
import com.main.maring.machine.energy.producer.solar.SolarPillar;
import com.main.maring.machine.energy.storage.battery.BatteryBasement;
import com.main.maring.machine.energy.storage.battery.BatteryCapacity;
import com.main.maring.machine.energy.viewer.EnergyViewer;

/**
 * @author NANMEDA
 * */
public class MBlockRegister {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Maring.MODID);
    public static final DeferredRegister<Item> BLOCK_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Maring.MODID);
    
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
    
    public static final RegistryObject<Block> MARREACTOR_B = BLOCKS.register(MarReactor.global_name, () -> {
		return new MarReactor(BlockBehaviour.Properties.of()); 
	});
    public static final RegistryObject<Item> MARREACTOR_I = BLOCK_ITEMS.register(MarReactor.global_name,
    		() -> new BlockItem(MARREACTOR_B.get(), new Item.Properties()));

	public static final RegistryObject<Block> ELECTROLYTICDEVICE_B = BLOCKS.register(BlockElectrolyticDevice.global_name, () -> {
		return new BlockElectrolyticDevice(BlockBehaviour.Properties.of());
	});
	public static final RegistryObject<Item> ELECTROLYTICDEVICE_I = BLOCK_ITEMS.register(BlockElectrolyticDevice.global_name,
			() -> new BlockItem(ELECTROLYTICDEVICE_B.get(), new Item.Properties()));
}
