package machine.registry;

import block.entity.neutral.dormcontrol.DomeControlEntity;
import block.norm.BlockRegister;
import block.norm.fastbuild.dormcontrol.DomeControl;
import block.norm.unbroken.BlockUnbrokenConductor;
import machine.energy.consumer.coredigger.CoreDigger;
import machine.energy.consumer.coredigger.CoreDiggerEntity;
import machine.energy.consumer.microwaveoven.MicrowaveOvenEntity;
import machine.energy.producer.reactor.mar.MarReactor;
import machine.energy.producer.reactor.mar.MarReactorEntity;
import machine.energy.producer.solar.SolarBasement;
import machine.energy.producer.solar.SolarBasementEntity;
import machine.energy.storage.battery.BatteryBasement;
import machine.energy.storage.battery.BatteryBasementEntity;
import machine.energy.storage.battery.BatteryCapacity;
import machine.energy.storage.battery.BatteryCapacityEntity;
import machine.energy.trans.UnbrokenConductorEntity;
import machine.energy.viewer.EnergyViewer;
import machine.energy.viewer.EnergyViewerEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MBlockEntityRegister {
	private static final String MODID = "maring";
    public static final DeferredRegister<BlockEntityType<?>> BLOCKENTITIES =  DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MODID);
    
	public static final RegistryObject<BlockEntityType<SolarBasementEntity>>SOLARBASEMENT_BE = 
			BLOCKENTITIES.register(SolarBasement.global_name,
					() -> BlockEntityType.Builder.of(SolarBasementEntity::new, machine.registry.MBlockRegister.SOLARBASEMENT_B.get()).build(null));

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