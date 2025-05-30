package com.main.maring.machine.registry;

import com.main.maring.Maring;
import com.main.maring.block.entity.neutral.dormcontrol.DomeControlEntity;
import com.main.maring.block.norm.BlockRegister;
import com.main.maring.block.norm.fastbuild.dormcontrol.DomeControl;
import com.main.maring.block.norm.unbroken.BlockUnbrokenConductor;
import com.main.maring.machine.energy.consumer.coredigger.CoreDigger;
import com.main.maring.machine.energy.consumer.coredigger.CoreDiggerEntity;
import com.main.maring.machine.energy.consumer.microwaveoven.MicrowaveOvenEntity;
import com.main.maring.machine.energy.producer.reactor.mar.MarReactor;
import com.main.maring.machine.energy.producer.reactor.mar.MarReactorEntity;
import com.main.maring.machine.energy.producer.solar.SolarBasement;
import com.main.maring.machine.energy.producer.solar.SolarBasementEntity;
import com.main.maring.machine.energy.storage.battery.BatteryBasement;
import com.main.maring.machine.energy.storage.battery.BatteryBasementEntity;
import com.main.maring.machine.energy.storage.battery.BatteryCapacity;
import com.main.maring.machine.energy.storage.battery.BatteryCapacityEntity;
import com.main.maring.machine.energy.trans.UnbrokenConductorEntity;
import com.main.maring.machine.energy.viewer.EnergyViewer;
import com.main.maring.machine.energy.viewer.EnergyViewerEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MBlockEntityRegister {
    public static final DeferredRegister<BlockEntityType<?>> BLOCKENTITIES =  DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Maring.MODID);
    
	public static final RegistryObject<BlockEntityType<SolarBasementEntity>>SOLARBASEMENT_BE = 
			BLOCKENTITIES.register(SolarBasement.global_name,
					() -> BlockEntityType.Builder.of(SolarBasementEntity::new, MBlockRegister.SOLARBASEMENT_B.get()).build(null));

	public static final RegistryObject<BlockEntityType<MicrowaveOvenEntity>> microwaveoven_BLOCKENTITY = 
			BLOCKENTITIES.register("microwave_oven", 
					() -> BlockEntityType.Builder.of(MicrowaveOvenEntity::new, MBlockRegister.MINCROWAVEOVEN_B.get()).build(null));

	public static final RegistryObject<BlockEntityType<BatteryCapacityEntity>> BATTERYCAPACITY_BE = 
			BLOCKENTITIES.register(BatteryCapacity.global_name, 
					() -> BlockEntityType.Builder.of(BatteryCapacityEntity::new, MBlockRegister.BATTERYCAPACITY_B.get()).build(null));

	public static final RegistryObject<BlockEntityType<BatteryBasementEntity>> BATTERTBASEMENT_BE = 
			BLOCKENTITIES.register(BatteryBasement.global_name, 
					() -> BlockEntityType.Builder.of(BatteryBasementEntity::new, MBlockRegister.BATTERTBASEMENT_B.get()).build(null));

	public static final RegistryObject<BlockEntityType<EnergyViewerEntity>> ENERGYVIEWER_BE = 
			BLOCKENTITIES.register(EnergyViewer.global_name, 
					() -> BlockEntityType.Builder.of(EnergyViewerEntity::new, MBlockRegister.ENERGYVIEWER_B.get()).build(null));

	public static final RegistryObject<BlockEntityType<CoreDiggerEntity>> COREDIGGER_BE = 
			BLOCKENTITIES.register(CoreDigger.global_name, 
					() -> BlockEntityType.Builder.of(CoreDiggerEntity::new, MBlockRegister.COREDIDDER_B.get()).build(null));

	public static final RegistryObject<BlockEntityType<DomeControlEntity>> DOMECONTROL_BE = 
			BLOCKENTITIES.register(DomeControl.global_name, 
					() -> BlockEntityType.Builder.of(DomeControlEntity::new, BlockRegister.dormcontrol_BLOCK.get()).build(null));

	public static final RegistryObject<BlockEntityType<UnbrokenConductorEntity>> UNBROKENCONDUCTOR_BE = 
			BLOCKENTITIES.register(BlockUnbrokenConductor.global_name, 
					() -> BlockEntityType.Builder.of(UnbrokenConductorEntity::new, BlockRegister.unbrokenconductor_BLOCK.get()).build(null));

	public static final RegistryObject<BlockEntityType<MarReactorEntity>> MARREACTOR_BE = 
			BLOCKENTITIES.register(MarReactor.global_name, 
					() -> BlockEntityType.Builder.of(MarReactorEntity::new, MBlockRegister.MARREACTOR_B.get()).build(null));

	
}