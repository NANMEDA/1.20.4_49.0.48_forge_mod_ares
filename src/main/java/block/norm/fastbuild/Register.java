package block.norm.fastbuild;

import block.norm.BlockRegister;
import block.norm.fastbuild.basicdorm.BasicEclipseDorm;
import block.norm.fastbuild.basicdorm.BasicFlatEclipseDorm;
import block.norm.fastbuild.basicdorm.BasicFlatSphereDorm;
import block.norm.fastbuild.basicdorm.BasicSphereDorm;
import block.norm.fastbuild.basicdorm.SphereDoor;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.RegistryObject;

public class Register {

	public static void init() {}
	
    public static final RegistryObject<Block> dormjunction_BLOCK = BlockRegister.BLOCKS.register(DormJunction.global_name, () -> {
		return new DormJunction(BlockBehaviour.Properties.of()
	            .sound(SoundType.AMETHYST)
	            .strength(-1.0f,360000.0f)
	            .noOcclusion()
	            .mapColor(MapColor.COLOR_ORANGE)); 
	});
    public static final RegistryObject<Item> dormjunction_BLOCK_ITEM = BlockRegister.BLOCK_ITEMS.register(DormJunction.global_name,
    		() -> new BlockItem(dormjunction_BLOCK.get(), new Item.Properties()));
    
    public static final RegistryObject<Block> dormjunctioncontrol_BLOCK = BlockRegister.BLOCKS.register(DormJunctionControl.global_name, () -> {
		return new DormJunctionControl(BlockBehaviour.Properties.of()
	            .sound(SoundType.AMETHYST)
	            .strength(-1.0f,360000.0f)
	            .noOcclusion()
	            .mapColor(MapColor.COLOR_ORANGE)); 
	});
    public static final RegistryObject<Item> dormjunctioncontrol_BLOCK_ITEM = BlockRegister.BLOCK_ITEMS.register(DormJunctionControl.global_name,
    		() -> new BlockItem(dormjunctioncontrol_BLOCK.get(), new Item.Properties()));
 
	
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
    
    public static final RegistryObject<Block> basiceclipsedorm_BLOCK = BlockRegister.BLOCKS.register(BasicEclipseDorm.global_name, () -> {
		return new BasicEclipseDorm(BlockBehaviour.Properties.of()
	            .sound(SoundType.AMETHYST)
	            .strength(5f,5f)
	            .noOcclusion()
	            .mapColor(MapColor.COLOR_ORANGE)); 
	});
    public static final RegistryObject<Item> basiceclipsedorm_BLOCK_ITEM = BlockRegister.BLOCK_ITEMS.register(BasicEclipseDorm.global_name,
    		() -> new BlockItem(basiceclipsedorm_BLOCK.get(), new Item.Properties()));
    
    public static final RegistryObject<Block> basicflateclipsedorm_BLOCK = BlockRegister.BLOCKS.register(BasicFlatEclipseDorm.global_name, () -> {
		return new BasicFlatEclipseDorm(BlockBehaviour.Properties.of()
	            .sound(SoundType.AMETHYST)
	            .strength(5f,5f)
	            .noOcclusion()
	            .mapColor(MapColor.COLOR_ORANGE)); 
	});
    public static final RegistryObject<Item> basicflateclipsedorm_BLOCK_ITEM = BlockRegister.BLOCK_ITEMS.register(BasicFlatEclipseDorm.global_name,
    		() -> new BlockItem(basicflateclipsedorm_BLOCK.get(), new Item.Properties()));
    
    public static final RegistryObject<Block> spheredoor_BLOCK = BlockRegister.BLOCKS.register(SphereDoor.global_name, () -> {
		return new SphereDoor(BlockBehaviour.Properties.of()
	            .sound(SoundType.AMETHYST)
	            .strength(5f,5f)
	            .noOcclusion()
	            .mapColor(MapColor.COLOR_ORANGE)); 
	});
    public static final RegistryObject<Item> spheredoor_BLOCK_ITEM = BlockRegister.BLOCK_ITEMS.register(SphereDoor.global_name,
    		() -> new BlockItem(spheredoor_BLOCK.get(), new Item.Properties()));
    

}
