package block.norm;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.stream.IntStream;

import block.norm.basicmetalmanufactor.BlockBasicMetalManufactor;
import block.norm.basicmetalmanufactor.BlockBasicMetalManufactorbehind;
import block.norm.basicmetalmanufactor.BlockBasicMetalManufactorbehindup;
import block.norm.basicmetalmanufactor.BlockBasicMetalManufactorleft;
import block.norm.basicmetalmanufactor.BlockBasicMetalManufactorup;
import block.norm.bioplasticbuilder.BlockBioplasticBuilder;
import block.norm.bioplasticbuilder.BlockBioplasticBuilderbehind;
import block.norm.bioplasticbuilder.BlockBioplasticBuilderleft;
import block.norm.bioplasticbuilder.BlockBioplasticBuilderup;
import block.norm.microwaveoven.BlockMicrowaveOven;
import block.norm.powerstation.burn.PowerStationBurn;
import block.norm.powerstation.sun.PowerStationSun;
import block.norm.unbroken.BlockUnbrokenCement;
import block.norm.unbroken.BlockUnbrokenFog;
import block.norm.unbroken.BlockUnbrokenGlass;
import block.norm.unbroken.BlockUnbrokenGreen;
import block.norm.unbroken.BlockUnbrokenLightblue;

/**
 * 所有方块和方块类物品的注册
 * 一般建议越复杂的放到越后面
 * 复杂的可能会用到简单的
 * @author NANMEDA
 * */
public class BlockRegister {
	private static final String MODID = "maring";
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static final DeferredRegister<Item> BLOCK_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    
    @SuppressWarnings("unchecked")
	public static final RegistryObject<Block>[] COMMON_BLOCKS = new RegistryObject[BlockBasic.BLOCK_BASIC_NUMBER];
    @SuppressWarnings("unchecked")
	public static final RegistryObject<Item>[] COMMON_BLOCK_ITEMS = new RegistryObject[BlockBasic.BLOCK_BASIC_NUMBER];
    @SuppressWarnings("unchecked")
	public static final RegistryObject<Block>[] ELECTRIC_BLOCKS = new RegistryObject[BlockElectricBasic.BLOCK_ELECTRIC_NUMBER];
    @SuppressWarnings("unchecked")
	public static final RegistryObject<Item>[] ELECTRIC_BLOCK_ITEMS = new RegistryObject[BlockElectricBasic.BLOCK_ELECTRIC_NUMBER];
    
    public static final RegistryObject<Block> PowerStationBurn_BLOCK;
    public static final RegistryObject<Item> PowerStationBurn_BLOCK_ITEM;
    public static final RegistryObject<Block> PowerStationSun_BLOCK;
    public static final RegistryObject<Item> PowerStationSun_BLOCK_ITEM;
    public static final RegistryObject<Block> awakeningstone_BLOCK;
    public static final RegistryObject<Item> awakeningstone_BLOCK_ITEM;
    //public static final RegistryObject<Block> basicmetalmanufactor_BLOCK;
    //public static final RegistryObject<Item> basicmetalmanufactor_BLOCK_ITEM;
    
    
    static {
    	int id_phosphor = BlockBasic.getIdFromName("phosphor");
    	int id_abundant_phosphor = BlockBasic.getIdFromName("abundant_phosphor");
    	int id_moist_mucus = BlockBasic.getIdFromName("moist_mucus");
        IntStream.range(0, BlockBasic.BLOCK_BASIC_NUMBER).forEach(i -> {
        	COMMON_BLOCKS[i] = BLOCKS.register(BlockBasic.getBlockName(i), () -> {
        	    BlockBehaviour.Properties properties = BlockBehaviour.Properties.of()
        	            .sound(BlockBasic.getBlockSound(i))
        	            .strength(BlockBasic.getBlockStrength(i)[0], BlockBasic.getBlockStrength(i)[1])
        	            .mapColor(BlockBasic.getBlockMapColor(i));
        	    if (BlockBasic.needTool(i)) {
        	        properties.requiresCorrectToolForDrops();
        	    }
        	    if (i == id_phosphor||i == (id_phosphor+1)) {
        	    	properties.lightLevel((blockstate)->6);
        	    }else if (i == id_abundant_phosphor||i == (id_abundant_phosphor+1)) {
        	    	properties.lightLevel((blockstate)->10);
        	    }else if (i==id_moist_mucus) {
        	    	properties.noOcclusion();
        	    }
        	    return new Block(properties);
        	});
        	COMMON_BLOCK_ITEMS[i] = BLOCK_ITEMS.register(BlockBasic.getBlockName(i), () -> new BlockItem(COMMON_BLOCKS[i].get(), new Item.Properties()));
        });
        
        
        IntStream.range(0, BlockElectricBasic.BLOCK_ELECTRIC_NUMBER).forEach(i -> {
        	ELECTRIC_BLOCKS[i] = BLOCKS.register(BlockElectricBasic.getBlockName(i), () -> {
        	    BlockBehaviour.Properties properties = BlockBehaviour.Properties.of()
        	    		//.noOcclusion()
        	            .sound(BlockElectricBasic.getBlockSound(i))
        	            .strength(BlockElectricBasic.getBlockStrength(i)[0], BlockElectricBasic.getBlockStrength(i)[1])
        	            .mapColor(BlockElectricBasic.getBlockMapColor(i));
        	    if (BlockElectricBasic.needTool(i)) {
        	        properties.requiresCorrectToolForDrops();
        	    }
        	    return new Block(properties) {
        	    };
        	});
        	ELECTRIC_BLOCK_ITEMS[i] = BLOCK_ITEMS.register(BlockElectricBasic.getBlockName(i), () -> new BlockItem(ELECTRIC_BLOCKS[i].get(), new Item.Properties()));
        });
        

    	PowerStationBurn_BLOCK = BLOCKS.register(PowerStationBurn.global_name, () -> {
    		return new PowerStationBurn(BlockBehaviour.Properties.of()
    	            .sound(SoundType.STONE)
    	            .strength(5f,5f)
    	            .mapColor(MapColor.COLOR_GRAY)); 
    	});
    	PowerStationBurn_BLOCK_ITEM = BLOCK_ITEMS.register(PowerStationBurn.global_name, () -> new BlockItem(PowerStationBurn_BLOCK.get(), new Item.Properties()));
    	
    	PowerStationSun_BLOCK = BLOCKS.register(PowerStationSun.global_name, () -> {
    		return new PowerStationSun(BlockBehaviour.Properties.of()
    	            .sound(SoundType.AMETHYST)
    	            .strength(5f,5f)
    	            .mapColor(MapColor.COLOR_BLUE)); 
    	});
    	PowerStationSun_BLOCK_ITEM = BLOCK_ITEMS.register(PowerStationSun.global_name, () -> new BlockItem(PowerStationSun_BLOCK.get(), new Item.Properties()));

    	
    	awakeningstone_BLOCK = BLOCKS.register(BlockAwakeningStone.global_name, () -> {
    		return new BlockAwakeningStone(BlockBehaviour.Properties.of()
    	            .sound(SoundType.STONE)
    	            .strength(1.5f,6f)
    	            .mapColor(MapColor.COLOR_GRAY)); 
    	});
    	awakeningstone_BLOCK_ITEM = BLOCK_ITEMS.register(BlockAwakeningStone.global_name, () -> new BlockItem(awakeningstone_BLOCK.get(), new Item.Properties()));
    
    	block.norm.decoration.Register.init();
    }
    
    
    public static final RegistryObject<Block> microwaveoven_BLOCK = BLOCKS.register(BlockMicrowaveOven.global_name, () -> {
		return new BlockMicrowaveOven(BlockBehaviour.Properties.of()
	            .sound(SoundType.AMETHYST)
	            .strength(5f,5f)
	            .noOcclusion()
	            .mapColor(MapColor.COLOR_GRAY)); 
	});
    public static final RegistryObject<Item> microwaveoven_BLOCK_ITEM = BLOCK_ITEMS.register(BlockMicrowaveOven.global_name, () -> new BlockItem(microwaveoven_BLOCK.get(), new Item.Properties()));

    public static final RegistryObject<Block> basicmetalmanufactor_BLOCK = BLOCKS.register(BlockBasicMetalManufactor.global_name, () -> {
		return new BlockBasicMetalManufactor(BlockBehaviour.Properties.of()
	            .sound(SoundType.STONE)
	            .strength(5f,5f)
	            .noOcclusion()
	            .mapColor(MapColor.COLOR_GRAY)); 
	});
    public static final RegistryObject<Item> basicmetalmanufactor_BLOCK_ITEM = BLOCK_ITEMS.register(BlockBasicMetalManufactor.global_name, () -> new BlockItem(basicmetalmanufactor_BLOCK.get(), new Item.Properties()));

    public static final RegistryObject<Block> basicmetalmanufactorup_BLOCK = BLOCKS.register(BlockBasicMetalManufactorup.global_name, () -> {
		return new BlockBasicMetalManufactorup(BlockBehaviour.Properties.of()
	            .sound(SoundType.STONE)
	            .strength(5f,5f)
	            .noOcclusion()
	            .mapColor(MapColor.NONE)); 
	});
    public static final RegistryObject<Item> basicmetalmanufactorup_BLOCK_ITEM = BLOCK_ITEMS.register(BlockBasicMetalManufactorup.global_name, () -> new BlockItem(basicmetalmanufactorup_BLOCK.get(), new Item.Properties()));

    public static final RegistryObject<Block> basicmetalmanufactorleft_BLOCK = BLOCKS.register(BlockBasicMetalManufactorleft.global_name, () -> {
		return new BlockBasicMetalManufactorleft(BlockBehaviour.Properties.of()
	            .sound(SoundType.STONE)
	            .strength(5f,5f)
	            .noOcclusion()
	            .mapColor(MapColor.NONE)); 
	});
    public static final RegistryObject<Item> basicmetalmanufactorleft_BLOCK_ITEM = BLOCK_ITEMS.register(BlockBasicMetalManufactorleft.global_name, () -> new BlockItem(basicmetalmanufactorleft_BLOCK.get(), new Item.Properties()));

    public static final RegistryObject<Block> basicmetalmanufactorbehind_BLOCK = BLOCKS.register(BlockBasicMetalManufactorbehind.global_name, () -> {
		return new BlockBasicMetalManufactorbehind(BlockBehaviour.Properties.of()
	            .sound(SoundType.STONE)
	            .strength(5f,5f)
	            .noOcclusion()
	            .mapColor(MapColor.NONE)); 
	});
    public static final RegistryObject<Item> basicmetalmanufactorbehind_BLOCK_ITEM = BLOCK_ITEMS.register(BlockBasicMetalManufactorbehind.global_name,
    		() -> new BlockItem(basicmetalmanufactorbehind_BLOCK.get(), new Item.Properties()));

    public static final RegistryObject<Block> basicmetalmanufactorbehindup_BLOCK = BLOCKS.register(BlockBasicMetalManufactorbehindup.global_name, () -> {
		return new BlockBasicMetalManufactorbehindup(BlockBehaviour.Properties.of()
	            .sound(SoundType.STONE)
	            .strength(5f,5f)
	            .noOcclusion()
	            .mapColor(MapColor.NONE)); 
	});
    public static final RegistryObject<Item> basicmetalmanufactorbehindup_BLOCK_ITEM = BLOCK_ITEMS.register(BlockBasicMetalManufactorbehindup.global_name,
    		() -> new BlockItem(basicmetalmanufactorbehindup_BLOCK.get(), new Item.Properties()));
    
    
    
    
    public static final RegistryObject<Block> bioplasticbuilder_BLOCK = BLOCKS.register(BlockBioplasticBuilder.global_name, () -> {
		return new BlockBioplasticBuilder(BlockBehaviour.Properties.of()
	            .sound(SoundType.STONE)
	            .strength(5f,5f)
	            .noOcclusion()
	            .mapColor(MapColor.COLOR_ORANGE)); 
	});
    public static final RegistryObject<Item> bioplasticbuilder_BLOCK_ITEM = BLOCK_ITEMS.register(BlockBioplasticBuilder.global_name,
    		() -> new BlockItem(bioplasticbuilder_BLOCK.get(), new Item.Properties()));
    
    public static final RegistryObject<Block> bioplasticbuilderup_BLOCK = BLOCKS.register(BlockBioplasticBuilderup.global_name, () -> {
		return new BlockBioplasticBuilderup(BlockBehaviour.Properties.of()
	            .sound(SoundType.STONE)
	            .strength(5f,5f)
	            .noOcclusion()
	            .mapColor(MapColor.NONE)); 
	});
    public static final RegistryObject<Item> bioplasticbuilderup_BLOCK_ITEM = BLOCK_ITEMS.register(BlockBioplasticBuilderup.global_name, () -> new BlockItem(bioplasticbuilderup_BLOCK.get(), new Item.Properties()));

    public static final RegistryObject<Block> bioplasticbuilderleft_BLOCK = BLOCKS.register(BlockBioplasticBuilderleft.global_name, () -> {
		return new BlockBioplasticBuilderleft(BlockBehaviour.Properties.of()
	            .sound(SoundType.STONE)
	            .strength(5f,5f)
	            .noOcclusion()
	            .mapColor(MapColor.NONE)); 
	});
    public static final RegistryObject<Item> bioplasticbuilderleft_BLOCK_ITEM = BLOCK_ITEMS.register(BlockBioplasticBuilderleft.global_name, () -> new BlockItem(bioplasticbuilderleft_BLOCK.get(), new Item.Properties()));

    public static final RegistryObject<Block> bioplasticbuilderbehind_BLOCK = BLOCKS.register(BlockBioplasticBuilderbehind.global_name, () -> {
		return new BlockBioplasticBuilderbehind(BlockBehaviour.Properties.of()
	            .sound(SoundType.STONE)
	            .strength(5f,5f)
	            .noOcclusion()
	            .mapColor(MapColor.NONE)); 
	});
    public static final RegistryObject<Item> bioplasticbuilderbehind_BLOCK_ITEM = BLOCK_ITEMS.register(BlockBioplasticBuilderbehind.global_name,
    		() -> new BlockItem(bioplasticbuilderbehind_BLOCK.get(), new Item.Properties()));
    
    public static final RegistryObject<Block> unbrokenfog_BLOCK = BLOCKS.register(BlockUnbrokenFog.global_name, () -> {
		return new BlockUnbrokenFog(BlockBehaviour.Properties.of()
	            .sound(SoundType.STONE)
	            .strength(-1.0f,360000.0f)
	            .noOcclusion()
	            .noCollission()
	            .mapColor(MapColor.NONE)); 
	});
    public static final RegistryObject<Item> unbrokenfog_BLOCK_ITEM = BLOCK_ITEMS.register(BlockUnbrokenFog.global_name,
    		() -> new BlockItem(unbrokenfog_BLOCK.get(), new Item.Properties()));
    
    /***
     * 不可破坏块
     * ***/
    public static final RegistryObject<Block> unbrokenglass_BLOCK = BLOCKS.register(BlockUnbrokenGlass.global_name, () -> {
		return new BlockUnbrokenGlass(BlockBehaviour.Properties.of()
	            .sound(SoundType.GLASS)
	            .strength(-1.0f,3600000.0f)
	            .noOcclusion()
	            .mapColor(MapColor.NONE)); 
	});
    public static final RegistryObject<Item> unbrokenglass_BLOCK_ITEM = BLOCK_ITEMS.register(BlockUnbrokenGlass.global_name, () -> new BlockItem(unbrokenglass_BLOCK.get(), new Item.Properties()));
    
    public static final RegistryObject<Block> unbrokencement_BLOCK = BLOCKS.register( BlockUnbrokenCement.global_name, () -> {
		return new BlockUnbrokenCement(BlockBehaviour.Properties.of()
				.sound(SoundType.STONE));
	});
    public static final RegistryObject<Item> unbrokencement_BLOCK_ITEM = BLOCK_ITEMS.register(BlockUnbrokenCement.global_name,
    		() -> new BlockItem(unbrokencement_BLOCK.get(), new Item.Properties()));
    
    public static final RegistryObject<Block> unbrokengreen_BLOCK = BLOCKS.register(BlockUnbrokenGreen.global_name, () -> {
		return new BlockUnbrokenGreen(BlockBehaviour.Properties.of()
				.sound(SoundType.STONE));
	});
    public static final RegistryObject<Item> unbrokengreen_BLOCK_ITEM = BLOCK_ITEMS.register(BlockUnbrokenGreen.global_name,
    		() -> new BlockItem(unbrokengreen_BLOCK.get(), new Item.Properties()));
    
    public static final RegistryObject<Block> unbrokenlightblue_BLOCK = BLOCKS.register(BlockUnbrokenLightblue.global_name, () -> {
		return new BlockUnbrokenLightblue(BlockBehaviour.Properties.of()
				.sound(SoundType.STONE));
	});
    public static final RegistryObject<Item> unbrokenlightblue_BLOCK_ITEM = BLOCK_ITEMS.register(BlockUnbrokenLightblue.global_name,
    		() -> new BlockItem(unbrokenlightblue_BLOCK.get(), new Item.Properties()));
    
    public static final RegistryObject<Block> A_AIR = BLOCKS.register("a_air",
    		() -> new BlockAAIR(BlockBehaviour.Properties.of().replaceable().noCollission().noLootTable().air()));
    
    /***
     * 推荐这样子注册机器
     * ***/
    static {
    	block.norm.crystalbuilder.Register.init();
    	block.norm.glassbuilder.Register.init();
    	block.norm.watergather.Register.init();
    	block.norm.researchtable.Register.init();
    	block.norm.advancedmetalmanufactor.Register.init();
    	block.norm.etchingmachine.Register.init();
    	block.norm.canfoodmaker.Register.init();
    	block.norm.stonewasher.Register.init();
    	block.norm.fuelrefiner.Register.init();
    }
    
    static {
    	block.norm.fastbuild.Register.init();
    	block.norm.blueprintbuilder.Register.init();
    }
}
