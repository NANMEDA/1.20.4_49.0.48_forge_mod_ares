package block.norm.machineparts;

import block.norm.BlockRegister;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.RegistryObject;

public class Register {
	
	public static void init() {}
	
    public static final RegistryObject<Block> ELECTRONICPOWER_BLOCK = BlockRegister.BLOCKS.register(ElectronicPower.global_name, () -> {
		return new ElectronicPower(BlockBehaviour.Properties.of()
	            .sound(SoundType.AMETHYST)
	            .strength(5f,5f)
	            .noOcclusion()
	            .mapColor(MapColor.COLOR_ORANGE)); 
	});
    public static final RegistryObject<Item> ELECTRONICPOWER_BLOCK_ITEM = BlockRegister.BLOCK_ITEMS.register(ElectronicPower.global_name,
    		() -> new BlockItem(ELECTRONICPOWER_BLOCK.get(), new Item.Properties()));

    public static final RegistryObject<Block> MATERIALINPUT_BLOCK = BlockRegister.BLOCKS.register(MaterialInput.global_name, () -> {
		return new MaterialInput(BlockBehaviour.Properties.of()
	            .sound(SoundType.AMETHYST)
	            .strength(5f,5f)
	            .noOcclusion()
	            .mapColor(MapColor.COLOR_ORANGE)); 
	});
    public static final RegistryObject<Item> MATERIALINPUT_BLOCK_ITEM = BlockRegister.BLOCK_ITEMS.register(MaterialInput.global_name,
    		() -> new BlockItem(MATERIALINPUT_BLOCK.get(), new Item.Properties()));

    
    public static final RegistryObject<Block> MATERIALOUTPUT_BLOCK = BlockRegister.BLOCKS.register(MaterialOutput.global_name, () -> {
		return new MaterialOutput(BlockBehaviour.Properties.of()
	            .sound(SoundType.AMETHYST)
	            .strength(5f,5f)
	            .noOcclusion()
	            .mapColor(MapColor.COLOR_ORANGE)); 
	});
    public static final RegistryObject<Item> MATERIALOUTPUT_BLOCK_ITEM = BlockRegister.BLOCK_ITEMS.register(MaterialOutput.global_name,
    		() -> new BlockItem(MATERIALOUTPUT_BLOCK.get(), new Item.Properties()));

}
