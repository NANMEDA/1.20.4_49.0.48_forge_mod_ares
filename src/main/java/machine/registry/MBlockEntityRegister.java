package machine.registry;

import machine.energy.consumer.microwaveoven.MicrowaveOvenEntity;
import machine.energy.producer.solar.SolarBasement;
import machine.energy.producer.solar.SolarBasementEntity;
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
			BLOCKENTITIES.register("microwave_oven", () -> BlockEntityType.Builder.of(MicrowaveOvenEntity::new, MBlockRegister.microwaveoven_BLOCK.get()).build(null));

}