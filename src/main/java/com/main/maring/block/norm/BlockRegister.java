package com.main.maring.block.norm;

import com.main.maring.Maring;
import com.main.maring.block.norm.advancedmetalmanufactor.Register;
import com.main.maring.block.norm.farm.FarmBlockRegistry;
import com.main.maring.block.norm.fastbuild.FastBuildRegister;
import com.main.maring.block.norm.food.PieBlock;
import com.main.maring.fluid.EvaporatingFluidBlock;
import com.main.maring.fluid.cheese.CheeseFluid;
import com.main.maring.fluid.hydrogen.HydrogenFluid;
import com.main.maring.fluid.mathane.MethaneFluid;
import com.main.maring.fluid.oxygen.OxygenFluid;
import com.main.maring.item.ItemRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.stream.IntStream;

import com.main.maring.block.norm.basicmetalmanufactor.BlockBasicMetalManufactor;
import com.main.maring.block.norm.basicmetalmanufactor.BlockBasicMetalManufactorbehind;
import com.main.maring.block.norm.basicmetalmanufactor.BlockBasicMetalManufactorbehindup;
import com.main.maring.block.norm.basicmetalmanufactor.BlockBasicMetalManufactorleft;
import com.main.maring.block.norm.basicmetalmanufactor.BlockBasicMetalManufactorup;
import com.main.maring.block.norm.bioplasticbuilder.BlockBioplasticBuilder;
import com.main.maring.block.norm.bioplasticbuilder.BlockBioplasticBuilderbehind;
import com.main.maring.block.norm.bioplasticbuilder.BlockBioplasticBuilderleft;
import com.main.maring.block.norm.bioplasticbuilder.BlockBioplasticBuilderup;
import com.main.maring.block.norm.fastbuild.dormcontrol.DomeControl;
import com.main.maring.block.norm.powerstation.burn.PowerStationBurn;
import com.main.maring.block.norm.unbroken.BlockUnbrokenCement;
import com.main.maring.block.norm.unbroken.BlockUnbrokenConductor;
import com.main.maring.block.norm.unbroken.BlockUnbrokenFog;
import com.main.maring.block.norm.unbroken.BlockUnbrokenGlass;
import com.main.maring.block.norm.unbroken.BlockUnbrokenGreen;
import com.main.maring.block.norm.unbroken.BlockUnbrokenLightblue;
import com.main.maring.block.norm.unbroken.BlockUnbrokenMagma;

import javax.annotation.Nullable;

/**
 * 所有方块和方块类物品的注册
 * 一般建议越复杂的放到越后面
 * 复杂的可能会用到简单的
 * 减少supplier的使用
 * @author NANMEDA
 * */
public class BlockRegister {
	static float[] dirt_strength = new float[]{0.5F, 0.5F};
	static float[] stone_strength = new float[]{1.5F, 6.0F};
	static float[] ore_strength = new float[]{3.0F, 3.0F};
	static float[] deep_ore_strength = new float[]{4.5F, 3.0F};


	private static final String MODID = Maring.MODID;
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static final DeferredRegister<Item> BLOCK_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    
    @SuppressWarnings("unchecked")
	public static final RegistryObject<Block>[] COMMON_BLOCKS = new RegistryObject[BlockBasic.BLOCK_BASIC_NUMBER];
    @SuppressWarnings("unchecked")
	public static final RegistryObject<Item>[] COMMON_BLOCK_ITEMS = new RegistryObject[BlockBasic.BLOCK_BASIC_NUMBER];
    @SuppressWarnings("unchecked")
    
    public static final RegistryObject<Block> PowerStationBurn_BLOCK;
    public static final RegistryObject<Item> PowerStationBurn_BLOCK_ITEM;
    public static final RegistryObject<Block> awakeningstone_BLOCK;
    public static final RegistryObject<Item> awakeningstone_BLOCK_ITEM;
    //public static final RegistryObject<Block> basicmetalmanufactor_BLOCK;
    //public static final RegistryObject<Item> basicmetalmanufactor_BLOCK_ITEM;
    
    
    static {
    	int id_phosphor = BlockBasic.getIdFromName("phosphor");
    	int id_abundant_phosphor = BlockBasic.getIdFromName("abundant_phosphor");
    	int id_moist_mucus = BlockBasic.getIdFromName("moist_mucus");
    	int id_light = BlockBasic.getIdFromName("mar_hard_cheese")+1;
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
        	    }else if (i>=id_light&&i<=id_light+6) {
        	    	properties.lightLevel((blockstate)->10);
        	    }
        	    return new Block(properties);
        	});
        	COMMON_BLOCK_ITEMS[i] = BLOCK_ITEMS.register(BlockBasic.getBlockName(i), () -> new BlockItem(COMMON_BLOCKS[i].get(), new Item.Properties()));
        });


    	PowerStationBurn_BLOCK = BLOCKS.register(PowerStationBurn.global_name, () -> {
    		return new PowerStationBurn(BlockBehaviour.Properties.of()
    	            .sound(SoundType.STONE)
    	            .strength(5f,5f)
    	            .mapColor(MapColor.COLOR_GRAY)); 
    	});
    	PowerStationBurn_BLOCK_ITEM = BLOCK_ITEMS.register(PowerStationBurn.global_name, () -> new BlockItem(PowerStationBurn_BLOCK.get(), new Item.Properties()));
    	
    	awakeningstone_BLOCK = BLOCKS.register(BlockAwakeningStone.global_name, () -> {
    		return new BlockAwakeningStone(BlockBehaviour.Properties.of()
    	            .sound(SoundType.STONE)
    	            .strength(1.5f,6f)
    	            .mapColor(MapColor.COLOR_GRAY)); 
    	});
    	awakeningstone_BLOCK_ITEM = BLOCK_ITEMS.register(BlockAwakeningStone.global_name, () -> new BlockItem(awakeningstone_BLOCK.get(), new Item.Properties()));
    
    	com.main.maring.block.norm.decoration.Register.init();
    }
	public static final RegistryObject<Block> VILLAGER_HANDMAKE_TABLE = BLOCKS.register("villager_handmake_table",
			() -> {
				return new Block(BlockBehaviour.Properties.of()
						.sound(SoundType.AMETHYST)
						.strength(deep_ore_strength[0], deep_ore_strength[1])
						.mapColor(MapColor.COLOR_GRAY)
				);
			});
	public static final RegistryObject<BlockItem> VILLAGER_HANDMAKE_TABLE_BLOCK_ITEMS =
			BLOCK_ITEMS.register("villager_handmake_table",
					() -> new BlockItem(VILLAGER_HANDMAKE_TABLE.get(), new Item.Properties()));
	public static final RegistryObject<Block> BROKEN_STRUCTURE_BLOCK = BLOCKS.register("broken_structure_block",
			() -> {
				return new Block(BlockBehaviour.Properties.of()
						.sound(SoundType.AMETHYST)
						.strength(deep_ore_strength[0], deep_ore_strength[1])
						.mapColor(MapColor.COLOR_GRAY)
				);
			});
	public static final RegistryObject<BlockItem> BROKEN_STRUCTURE_BLOCK_ITEMS =
			BLOCK_ITEMS.register("broken_structure_block",
					() -> new BlockItem(BROKEN_STRUCTURE_BLOCK.get(), new Item.Properties()));
	public static final RegistryObject<Block> BROKEN_CHEMICAL_BLOCK = BLOCKS.register("broken_chemical_block",
			() -> {
				return new Block(BlockBehaviour.Properties.of()
						.sound(SoundType.AMETHYST)
						.strength(ore_strength[0], ore_strength[1])
						.mapColor(MapColor.COLOR_GRAY)
				);
			});
	public static final RegistryObject<BlockItem> BROKEN_CHEMICAL_BLOCK_ITEMS =
			BLOCK_ITEMS.register("broken_chemical_block",
					() -> new BlockItem(BROKEN_CHEMICAL_BLOCK.get(), new Item.Properties()));
	public static final RegistryObject<Block> BROKEN_METAL_BLOCK = BLOCKS.register("broken_metal_block",
			() -> {
				return new Block(BlockBehaviour.Properties.of()
						.sound(SoundType.AMETHYST)
						.strength(deep_ore_strength[0], deep_ore_strength[1])
						.mapColor(MapColor.COLOR_GRAY)
				);
			});
	public static final RegistryObject<BlockItem> BROKEN_METAL_BLOCK_ITEMS =
			BLOCK_ITEMS.register("broken_metal_block",
					() -> new BlockItem(BROKEN_METAL_BLOCK.get(), new Item.Properties()));
	public static final RegistryObject<Block> BROKEN_ELECTRONIC_BLOCK = BLOCKS.register("broken_electronic_block",
			() -> {
				return new Block(BlockBehaviour.Properties.of()
						.sound(SoundType.AMETHYST)
						.strength(ore_strength[0], ore_strength[1])
						.mapColor(MapColor.COLOR_GRAY)
				);
			});
	public static final RegistryObject<BlockItem> BROKEN_ELECTRONIC_BLOCK_ITEMS =
			BLOCK_ITEMS.register("broken_electronic_block",
					() -> new BlockItem(BROKEN_ELECTRONIC_BLOCK.get(), new Item.Properties()));
	public static final RegistryObject<Block> BROKEN_ADVANCED_ELECTRONIC_BLOCK = BLOCKS.register("broken_advanced_electronic_block",
			() -> {
				return new Block(BlockBehaviour.Properties.of()
						.sound(SoundType.AMETHYST)
						.strength(deep_ore_strength[0], deep_ore_strength[1])
						.mapColor(MapColor.COLOR_GRAY)
				);
			});
	public static final RegistryObject<BlockItem> BROKEN_ADVANCED_ELECTRONIC_BLOCK_ITEMS =
			BLOCK_ITEMS.register("broken_advanced_electronic_block",
					() -> new BlockItem(BROKEN_ADVANCED_ELECTRONIC_BLOCK.get(), new Item.Properties()));
	public static final RegistryObject<Block> VILLAGER_BURRIED_PACKAGE = BLOCKS.register("villager_burried_package",
			() -> {
				return new Block(BlockBehaviour.Properties.of()
						.sound(SoundType.WOOL)
						.strength(0.2f, 0.5f)
						.mapColor(MapColor.COLOR_GRAY)
				);
			});
	public static final RegistryObject<BlockItem> VILLAGER_BURRIED_PACKAGE_BLOCK_ITEMS =
			BLOCK_ITEMS.register("villager_burried_package",
					() -> new BlockItem(VILLAGER_BURRIED_PACKAGE.get(), new Item.Properties()));
	public static final RegistryObject<Block> UNBROKEN_DORM_JUNCTION = BLOCKS.register("unbroken_dorm_junction",
			() -> {
				return new Block(BlockBehaviour.Properties.of()
						.sound(SoundType.AMETHYST)
						.strength(-1.0f, 3600000.0F)
						.mapColor(MapColor.COLOR_GRAY)
				);
			});
	public static final RegistryObject<BlockItem> UNBROKEN_DORM_JUNCTION_BLOCK_ITEMS =
			BLOCK_ITEMS.register("unbroken_dorm_junction",
					() -> new BlockItem(UNBROKEN_DORM_JUNCTION.get(), new Item.Properties()));


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
    
    public static final RegistryObject<Block> unbrokenmagma_BLOCK = BLOCKS.register(BlockUnbrokenMagma.global_name, () -> {
		return new BlockUnbrokenMagma(BlockBehaviour.Properties.of());
	});
    
    public static final RegistryObject<Item> unbrokenmagma_BLOCK_ITEM = BLOCK_ITEMS.register(BlockUnbrokenMagma.global_name,
    		() -> new BlockItem(unbrokenmagma_BLOCK.get(), new Item.Properties()));
    
    public static final RegistryObject<Block> A_AIR = BLOCKS.register("a_air",
    		() -> new BlockAAIR(BlockBehaviour.Properties.of().replaceable().noCollission().noLootTable().air()));
    
    public static final RegistryObject<Block> dormcontrol_BLOCK = BLOCKS.register(DomeControl.global_name, () -> {
		return new DomeControl(BlockBehaviour.Properties.of()
				.strength(-1.0f,360000.0f));
	});
    
    public static final RegistryObject<Item> dormcontrol_BLOCK_ITEM = BLOCK_ITEMS.register(DomeControl.global_name,
    		() -> new BlockItem(dormcontrol_BLOCK.get(), new Item.Properties()));
    
    public static final RegistryObject<Block> unbrokenconductor_BLOCK = BLOCKS.register(BlockUnbrokenConductor.global_name, () -> {
		return new BlockUnbrokenConductor(BlockBehaviour.Properties.of()
				.strength(-1.0f,360000.0f));
	});
    
    public static final RegistryObject<Item> unbrokenconductor_BLOCK_ITEM = BLOCK_ITEMS.register(BlockUnbrokenConductor.global_name,
    		() -> new BlockItem(unbrokenconductor_BLOCK.get(), new Item.Properties()));
    
    static {
    	com.main.maring.block.norm.deposit.Register.init();
    	FarmBlockRegistry.init();
    }
    
    /***
     * 推荐这样子注册机器
     * ***/
    static {
    	com.main.maring.block.norm.crystalbuilder.Register.init();
    	com.main.maring.block.norm.glassbuilder.Register.init();
    	com.main.maring.block.norm.watergather.Register.init();
    	com.main.maring.block.norm.researchtable.Register.init();
    	Register.init();
    	com.main.maring.block.norm.etchingmachine.Register.init();
    	com.main.maring.block.norm.canfoodmaker.Register.init();
    	com.main.maring.block.norm.stonewasher.Register.init();
    	com.main.maring.block.norm.fuelrefiner.Register.init();
    	FastBuildRegister.init();
    	com.main.maring.block.norm.blueprintbuilder.Register.init();
    }

	public static final RegistryObject<LiquidBlock> CHEESE_FLUID_BLOCK = BLOCKS.register("cheese_fluid_block",
			() -> new LiquidBlock(CheeseFluid.SOURCE_CHEESE_FLUID, BlockBehaviour.Properties.copy(Blocks.LAVA).lightLevel(state->0)));
	public static final RegistryObject<LiquidBlock> OXYGEN_FLUID_BLOCK = BLOCKS.register("oxygen_fluid_block",
			() -> new EvaporatingFluidBlock(
					OxygenFluid.SOURCE_OXYGEN_FLUID.get(),
					BlockBehaviour.Properties.copy(Blocks.LAVA).lightLevel(state -> 0),
					20
			));
	public static final RegistryObject<LiquidBlock> HYDROGEN_FLUID_BLOCK = BLOCKS.register("hydrogen_fluid_block",
			() -> new EvaporatingFluidBlock(
					HydrogenFluid.SOURCE_HYDROGEN_FLUID.get(),
					BlockBehaviour.Properties.copy(Blocks.LAVA).lightLevel(state -> 0),
					20
			));
	public static final RegistryObject<LiquidBlock> METHANE_FLUID_BLOCK = BLOCKS.register("methane_fluid_block",
			() -> new EvaporatingFluidBlock(
					MethaneFluid.SOURCE_METHANE_FLUID.get(),
					BlockBehaviour.Properties.copy(Blocks.LAVA).lightLevel(state -> 0),
					20
			));

	public static final RegistryObject<Block> CHEESE_PIE;
	static {
		if (ModList.get().isLoaded("farmersdelight")) {
			CHEESE_PIE = BLOCKS.register("cheese_pie",
					() -> new PieBlock(BlockBehaviour.Properties.of()
							.forceSolidOn()
							.strength(0.5F)
							.sound(SoundType.WOOL)
							.pushReaction(PushReaction.DESTROY),4,true,3,5f) {
						@Override
						public List<ItemStack> getSlice(BlockState state) {
							return List.of(new ItemStack(ItemRegister.CHEESE_PIE_SLICE.get(),max_bites - state.getValue(BITES)));
						}
					});
		} else {
			CHEESE_PIE = null;
		}
	}

	public static final RegistryObject<Block> DEVELOPERS_PIE = BLOCKS.register("developers_pie",
			() -> new PieBlock(BlockBehaviour.Properties.of()
					.forceSolidOn()
					.strength(0.5F)
					.sound(SoundType.WOOL)
					.pushReaction(PushReaction.DESTROY),
					1, false, 20, 20) {

				// 新增属性
				public static final IntegerProperty TYPE_PROPERTY = IntegerProperty.create("type", 0, 1);
				{
					this.registerDefaultState(this.defaultBlockState()
							.setValue(BITES, 0)
							.setValue(TYPE_PROPERTY, 0));
				}

				@Override
				protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
					super.createBlockStateDefinition(builder);
					builder.add(TYPE_PROPERTY); // 添加自定义属性
				}
				@Nullable
				@Override
				public BlockState getStateForPlacement(BlockPlaceContext context) {
					BlockPos pos = context.getClickedPos();
					int value = Math.abs(pos.getX()+pos.getY()+pos.getZ()) % 2; // 取 X 坐标模 4，确保在 0-3 范围
					return this.defaultBlockState()
							.setValue(BITES, 0)
							.setValue(TYPE_PROPERTY, value);
				}


			});
}
