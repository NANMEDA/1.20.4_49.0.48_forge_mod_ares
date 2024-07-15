package block.entity;

import block.entity.consumer.advancedmetalmanufactor.AdvancedMetalManufactorEntity;
import block.entity.consumer.basicmetalmanufactor.BasicMetalManufactorEntity;
import block.entity.consumer.bioplasticbuilder.BioplasticBuilderEntity;
import block.entity.consumer.canfoodmaker.CanfoodMakerEntity;
import block.entity.consumer.etchingmachine.EtchingMachineEntity;
import block.entity.consumer.glassbuilder.GlassBuilderEntity;
import block.entity.consumer.microwaveoven.MicrowaveOvenEntity;
import block.entity.consumer.stonewasher.StoneWasherEntity;
import block.entity.consumer.watergather.WaterGatherEntity;
import block.entity.neutral.crystalbuilder.CrystalBuilderEntity;
import block.entity.neutral.fuelrefiner.FuelRefinerEntity;
import block.entity.neutral.researchtable.ResearchTableEntity;
import block.entity.station.PowerStationBurnEntity;
import block.norm.BlockRegister;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntityRegister {
	private static final String MODID = "maring";
    public static final DeferredRegister<BlockEntityType<?>> BLOCKENTITIES =  DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MODID);
	
	public static final RegistryObject<BlockEntityType<PowerStationBurnEntity>> PowerStationBurn_BLOCKENTITY = 
			BLOCKENTITIES.register("powerstation_burn", () -> BlockEntityType.Builder.of(PowerStationBurnEntity::new, BlockRegister.PowerStationBurn_BLOCK.get()).build(null));

	public static final RegistryObject<BlockEntityType<CanfoodMakerEntity>> canfoodmaker_BLOCKENTITY = 
			BLOCKENTITIES.register("canfood_maker", () -> BlockEntityType.Builder.of(CanfoodMakerEntity::new,  block.norm.canfoodmaker.Register.canfoodmaker_BLOCK.get()).build(null));

	public static final RegistryObject<BlockEntityType<MicrowaveOvenEntity>> microwaveoven_BLOCKENTITY = 
			BLOCKENTITIES.register("microwave_oven", () -> BlockEntityType.Builder.of(MicrowaveOvenEntity::new, BlockRegister.microwaveoven_BLOCK.get()).build(null));

	public static final RegistryObject<BlockEntityType<BasicMetalManufactorEntity>> basicmetalmanufactor_BLOCKENTITY = 
			BLOCKENTITIES.register("basicmetal_manufactor", () -> BlockEntityType.Builder.of(BasicMetalManufactorEntity::new, BlockRegister.basicmetalmanufactor_BLOCK.get()).build(null));

	public static final RegistryObject<BlockEntityType<BioplasticBuilderEntity>> bioplasticbuilder_BLOCKENTITY = 
			BLOCKENTITIES.register("bioplastic_builder", () -> BlockEntityType.Builder.of(BioplasticBuilderEntity::new, BlockRegister.bioplasticbuilder_BLOCK.get()).build(null));

	public static final RegistryObject<BlockEntityType<CrystalBuilderEntity>> crystalbuilder_BLOCKENTITY = 
			BLOCKENTITIES.register("crystal_builder", () -> BlockEntityType.Builder.of(CrystalBuilderEntity::new, block.norm.crystalbuilder.Register.crystalbuilder_BLOCK.get()).build(null));

	public static final RegistryObject<BlockEntityType<GlassBuilderEntity>> glassbuilder_BLOCKENTITY = 
			BLOCKENTITIES.register("glass_builder", () -> BlockEntityType.Builder.of(GlassBuilderEntity::new, block.norm.glassbuilder.Register.glassbuilder_BLOCK.get()).build(null));

	public static final RegistryObject<BlockEntityType<WaterGatherEntity>> watergather_BLOCKENTITY = 
			BLOCKENTITIES.register("water_gather", () -> BlockEntityType.Builder.of(WaterGatherEntity::new, block.norm.watergather.Register.watergather_BLOCK.get()).build(null));

	public static final RegistryObject<BlockEntityType<ResearchTableEntity>> researchtable_BLOCKENTITY = 
			BLOCKENTITIES.register("research_table", () -> BlockEntityType.Builder.of(ResearchTableEntity::new, block.norm.researchtable.Register.researchtable_BLOCK.get()).build(null));

	public static final RegistryObject<BlockEntityType<AdvancedMetalManufactorEntity>> advancedmetalmanufactor_BLOCKENTITY = 
			BLOCKENTITIES.register("advancedmetal_manufactor", () -> BlockEntityType.Builder.of(AdvancedMetalManufactorEntity::new, block.norm.advancedmetalmanufactor.Register.advancedmetalmanufactor_BLOCK.get()).build(null));

	public static final RegistryObject<BlockEntityType<EtchingMachineEntity>> etchingmachine_BLOCKENTITY = 
			BLOCKENTITIES.register("etching_machine", () -> BlockEntityType.Builder.of(EtchingMachineEntity::new, block.norm.etchingmachine.Register.etchingmachine_BLOCK.get()).build(null));

	public static final RegistryObject<BlockEntityType<StoneWasherEntity>> stonewasher_BLOCKENTITY = 
			BLOCKENTITIES.register("stone_washer", () -> BlockEntityType.Builder.of(StoneWasherEntity::new, block.norm.stonewasher.Register.stonewasher_BLOCK.get()).build(null));

	public static final RegistryObject<BlockEntityType<FuelRefinerEntity>> fuelrefiner_BLOCKENTITY = 
			BLOCKENTITIES.register("fuel_refiner", () -> BlockEntityType.Builder.of(FuelRefinerEntity::new, block.norm.fuelrefiner.Register.fuelrefiner_BLOCK.get()).build(null));

}