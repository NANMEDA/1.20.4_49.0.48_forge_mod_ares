package block.entity.register;

import com.block.register.BlockRegister;
import com.block.register.PowerStationBurn;

import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntityRegister {
	private static final String MODID = "maring";
    public static final DeferredRegister<BlockEntityType<?>> BLOCKENTITIES =  DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MODID);
	
	public static final RegistryObject<BlockEntityType<PowerStationBurnEntity>> PowerStationBurn_BLOCKENTITY = 
			BLOCKENTITIES.register("powerstation_burn", () -> BlockEntityType.Builder.of(PowerStationBurnEntity::new, BlockRegister.PowerStationBurn_BLOCKS.get()).build(null));

	public static final RegistryObject<BlockEntityType<CanfoodMakerEntity>> canfoodmaker_BLOCKENTITY = 
			BLOCKENTITIES.register("canfood_maker", () -> BlockEntityType.Builder.of(CanfoodMakerEntity::new, BlockRegister.canfoodmaker_BLOCK.get()).build(null));

	public static final RegistryObject<BlockEntityType<MicrowaveOvenEntity>> microwaveoven_BLOCKENTITY = 
			BLOCKENTITIES.register("microwave_oven", () -> BlockEntityType.Builder.of(MicrowaveOvenEntity::new, BlockRegister.microwaveoven_BLOCK.get()).build(null));

}