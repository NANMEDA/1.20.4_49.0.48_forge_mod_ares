package com.main.maring.block.entity;

import com.main.maring.Maring;
import com.main.maring.block.entity.consumer.advancedmetalmanufactor.AdvancedMetalManufactorEntity;
import com.main.maring.block.entity.consumer.basicmetalmanufactor.BasicMetalManufactorEntity;
import com.main.maring.block.entity.consumer.bioplasticbuilder.BioplasticBuilderEntity;
import com.main.maring.block.entity.consumer.canfoodmaker.CanfoodMakerEntity;
import com.main.maring.block.entity.consumer.etchingmachine.EtchingMachineEntity;
import com.main.maring.block.entity.consumer.glassbuilder.GlassBuilderEntity;
import com.main.maring.block.entity.consumer.stonewasher.StoneWasherEntity;
import com.main.maring.machine.energy.consumer.watergather.WaterGatherEntity;
import com.main.maring.block.entity.neutral.blueprintbuilder.BlueprintBuilderEntity;
import com.main.maring.block.entity.neutral.crystalbuilder.CrystalBuilderEntity;
import com.main.maring.block.entity.neutral.dormcontrol.DomeControlEntity;
import com.main.maring.block.entity.neutral.fastbuild.DormJunctionControlEntity;
import com.main.maring.block.entity.neutral.fuelrefiner.FuelRefinerEntity;
import com.main.maring.block.entity.neutral.researchtable.ResearchTableEntity;
import com.main.maring.block.entity.station.PowerStationBurnEntity;
import com.main.maring.block.norm.BlockRegister;
import com.main.maring.block.norm.advancedmetalmanufactor.Register;
import com.main.maring.block.norm.fastbuild.FastBuildRegister;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntityRegister {
    public static final DeferredRegister<BlockEntityType<?>> BLOCKENTITIES =  DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Maring.MODID);
	
	public static final RegistryObject<BlockEntityType<PowerStationBurnEntity>> PowerStationBurn_BLOCKENTITY = 
			BLOCKENTITIES.register("powerstation_burn", () -> BlockEntityType.Builder.of(PowerStationBurnEntity::new, BlockRegister.PowerStationBurn_BLOCK.get()).build(null));

	public static final RegistryObject<BlockEntityType<CanfoodMakerEntity>> canfoodmaker_BLOCKENTITY = 
			BLOCKENTITIES.register("canfood_maker", () -> BlockEntityType.Builder.of(CanfoodMakerEntity::new,  com.main.maring.block.norm.canfoodmaker.Register.canfoodmaker_BLOCK.get()).build(null));

	public static final RegistryObject<BlockEntityType<BasicMetalManufactorEntity>> basicmetalmanufactor_BLOCKENTITY = 
			BLOCKENTITIES.register("basicmetal_manufactor", () -> BlockEntityType.Builder.of(BasicMetalManufactorEntity::new, BlockRegister.basicmetalmanufactor_BLOCK.get()).build(null));

	public static final RegistryObject<BlockEntityType<BioplasticBuilderEntity>> bioplasticbuilder_BLOCKENTITY = 
			BLOCKENTITIES.register("bioplastic_builder", () -> BlockEntityType.Builder.of(BioplasticBuilderEntity::new, BlockRegister.bioplasticbuilder_BLOCK.get()).build(null));

	public static final RegistryObject<BlockEntityType<CrystalBuilderEntity>> crystalbuilder_BLOCKENTITY = 
			BLOCKENTITIES.register("crystal_builder", () -> BlockEntityType.Builder.of(CrystalBuilderEntity::new, com.main.maring.block.norm.crystalbuilder.Register.crystalbuilder_BLOCK.get()).build(null));

	public static final RegistryObject<BlockEntityType<GlassBuilderEntity>> glassbuilder_BLOCKENTITY = 
			BLOCKENTITIES.register("glass_builder", () -> BlockEntityType.Builder.of(GlassBuilderEntity::new, com.main.maring.block.norm.glassbuilder.Register.glassbuilder_BLOCK.get()).build(null));

	public static final RegistryObject<BlockEntityType<WaterGatherEntity>> watergather_BLOCKENTITY = 
			BLOCKENTITIES.register("water_gather", () -> BlockEntityType.Builder.of(WaterGatherEntity::new, com.main.maring.block.norm.watergather.Register.watergather_BLOCK.get()).build(null));

	public static final RegistryObject<BlockEntityType<ResearchTableEntity>> researchtable_BLOCKENTITY = 
			BLOCKENTITIES.register("research_table", () -> BlockEntityType.Builder.of(ResearchTableEntity::new, com.main.maring.block.norm.researchtable.Register.researchtable_BLOCK.get()).build(null));

	public static final RegistryObject<BlockEntityType<AdvancedMetalManufactorEntity>> advancedmetalmanufactor_BLOCKENTITY = 
			BLOCKENTITIES.register("advancedmetal_manufactor", () -> BlockEntityType.Builder.of(AdvancedMetalManufactorEntity::new, Register.advancedmetalmanufactor_BLOCK.get()).build(null));

	public static final RegistryObject<BlockEntityType<EtchingMachineEntity>> etchingmachine_BLOCKENTITY = 
			BLOCKENTITIES.register("etching_machine", () -> BlockEntityType.Builder.of(EtchingMachineEntity::new, com.main.maring.block.norm.etchingmachine.Register.etchingmachine_BLOCK.get()).build(null));

	public static final RegistryObject<BlockEntityType<StoneWasherEntity>> stonewasher_BLOCKENTITY = 
			BLOCKENTITIES.register("stone_washer", () -> BlockEntityType.Builder.of(StoneWasherEntity::new, com.main.maring.block.norm.stonewasher.Register.stonewasher_BLOCK.get()).build(null));

	public static final RegistryObject<BlockEntityType<FuelRefinerEntity>> fuelrefiner_BLOCKENTITY = 
			BLOCKENTITIES.register("fuel_refiner", () -> BlockEntityType.Builder.of(FuelRefinerEntity::new, com.main.maring.block.norm.fuelrefiner.Register.fuelrefiner_BLOCK.get()).build(null));

	public static final RegistryObject<BlockEntityType<DormJunctionControlEntity>> dormjunctioncontrol_BLOCKENTITY = 
			BLOCKENTITIES.register("dorm_junction_control", () -> BlockEntityType.Builder.of(DormJunctionControlEntity::new, FastBuildRegister.dormjunctioncontrol_BLOCK.get()).build(null));

	public static final RegistryObject<BlockEntityType<DomeControlEntity>> dormcontrol_BLOCKENTITY = 
			BLOCKENTITIES.register("dorm_control", () -> BlockEntityType.Builder.of(DomeControlEntity::new,  BlockRegister.dormcontrol_BLOCK.get()).build(null));

	
	public static final RegistryObject<BlockEntityType<BlueprintBuilderEntity>>blueprintbuilder_BLOCKENTITY = 
			BLOCKENTITIES.register("blueprint_builder", () -> BlockEntityType.Builder.of(BlueprintBuilderEntity::new, com.main.maring.block.norm.blueprintbuilder.Register.blueprintbuilder_BLOCK.get()).build(null));

}