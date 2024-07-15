package block.norm.fastbuild;

import block.norm.BlockRegister;
import block.norm.fastbuild.basicdorm.BasicFlatSphereDorm;
import block.norm.fastbuild.basicdorm.BasicSphereDorm;
import block.norm.fuelrefiner.BlockFuelRefiner;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.RegistryObject;

public class Register {

	public static void init() {}
	
    public static final RegistryObject<Block> basicspheredorm_BLOCK = BlockRegister.BLOCKS.register(BasicSphereDorm.global_name, () -> {
		return new BasicSphereDorm(BlockBehaviour.Properties.of()
	            .sound(SoundType.AMETHYST)
	            .strength(5f,5f)
	            .noOcclusion()
	            .mapColor(MapColor.COLOR_ORANGE)); 
	});
    public static final RegistryObject<Item> basicspheredorm_BLOCK_ITEM = BlockRegister.BLOCK_ITEMS.register(BasicSphereDorm.global_name,
    		() -> new BlockItem(basicspheredorm_BLOCK.get(), new Item.Properties()));
    
    public static final RegistryObject<Block> basicflatspheredorm_BLOCK = BlockRegister.BLOCKS.register(BasicFlatSphereDorm.global_name, () -> {
		return new BasicFlatSphereDorm(BlockBehaviour.Properties.of()
	            .sound(SoundType.AMETHYST)
	            .strength(5f,5f)
	            .noOcclusion()
	            .mapColor(MapColor.COLOR_ORANGE)); 
	});
    public static final RegistryObject<Item> basicflatspheredorm_BLOCK_ITEM = BlockRegister.BLOCK_ITEMS.register(BasicFlatSphereDorm.global_name,
    		() -> new BlockItem(basicflatspheredorm_BLOCK.get(), new Item.Properties()));
    
}
