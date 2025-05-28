package block.entity.machineparts;

import block.norm.machineparts.ElectronicPower;
import block.norm.machineparts.MaterialInput;
import block.norm.machineparts.MaterialOutput;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class Register {
	private static DeferredRegister<BlockEntityType<?>> BLOCKENTITIES;

	public void init(DeferredRegister<BlockEntityType<?>> BLOCKENTITIES) {
		this.BLOCKENTITIES = BLOCKENTITIES;
	}
	
	public static RegistryObject<BlockEntityType<ElectronicPowerEntity>> ELECTRONICPOWER_BLOCKENTITY = 
				BLOCKENTITIES.register(ElectronicPower.global_name, () -> BlockEntityType.Builder.of(ElectronicPowerEntity::new,
						block.norm.machineparts.Register.ELECTRONICPOWER_BLOCK.get()).build(null));

	public static RegistryObject<BlockEntityType<MaterialInputEntity>> MATERIALINPUT_BLOCKENTITY = 
			BLOCKENTITIES.register(MaterialInput.global_name, () -> BlockEntityType.Builder.of(MaterialInputEntity::new,
					block.norm.machineparts.Register.MATERIALINPUT_BLOCK.get()).build(null));

	public static RegistryObject<BlockEntityType<MaterialOutputEntity>> MATERIALOUTPUT_BLOCKENTITY = 
			BLOCKENTITIES.register(MaterialOutput.global_name, () -> BlockEntityType.Builder.of(MaterialOutputEntity::new,
					block.norm.machineparts.Register.MATERIALOUTPUT_BLOCK.get()).build(null));

}
