package com.main.maring.item;

import com.main.maring.Maring;
import com.main.maring.block.norm.BlockRegister;
import com.main.maring.block.norm.farm.FarmBlockRegistry;
import com.main.maring.effect.registry.EffectRegister;
import com.main.maring.fluid.cheese.CheeseFluid;
import com.main.maring.item.armor.ModArmorMaterials;
import com.main.maring.item.blueprint.ItemBlueprint;
import com.main.maring.item.can.ItemCan;
import com.main.maring.item.electric.BatteryHeart;
import com.main.maring.item.food.CheesePiece;
import com.main.maring.item.food.farm.FrostfireFruit;
import com.main.maring.item.rocket.RocketItem;
import com.main.maring.item.tool.ItemChangeStick;
import com.main.maring.item.tool.ItemJunctionConnector;
import com.main.maring.item.tool.OminousAxe;
import com.main.maring.item.tool.OminousHoe;
import com.main.maring.item.tool.OminousPickaxe;
import com.main.maring.item.tool.OminousShovel;
import com.main.maring.item.tool.electric.ElectricDebuggerStick;
import com.main.maring.item.tool.electric.WireCreator;
import com.main.maring.item.tool.electric.WireCutor;
import com.main.maring.item.weapon.ItemFrenchBread;
import com.main.maring.item.weapon.SwordTier;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Random;

/**
 * 除了方块物品
 * 所有Item在此注册
 * @author NANMEDA
 * */
public class ItemRegister {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Maring.MODID);
	
	static {
		System.out.println("here come Register the Item");
	}
	

	public static final RegistryObject<Item> OMINOUS_GEMSTONE = ITEMS.register("ominous_gemstone",
			() -> new Item(new Item.Properties().stacksTo(8)));

	public static final RegistryObject<Item> BASIC_METAL_PARTS = ITEMS.register("basic_metal_parts",
			() -> new Item(new Item.Properties().stacksTo(16))); 	// 深蓝色系
	public static final RegistryObject<Item> ADVANCED_METAL_PARTS = ITEMS.register("advanced_metal_parts",
			() -> new Item(new Item.Properties().stacksTo(16)));	// 浅蓝色系
	public static final RegistryObject<Item> BIOPLASTIC_PARTS = ITEMS.register("bioplastic_parts",
			() -> new Item(new Item.Properties().stacksTo(16)));	// 土黄色系
	public static final RegistryObject<Item> SEMICONDUCTOR_PARTS = ITEMS.register("semiconductor_parts",
			() -> new Item(new Item.Properties().stacksTo(16)));	// 紫灰色系
	public static final RegistryObject<Item> CRYSTAL_PARTS = ITEMS.register("crystal_parts",
			() -> new Item(new Item.Properties().stacksTo(16)));	// 橙红色系

	public static final RegistryObject<Item> BOTTLED_METHANE = ITEMS.register("bottled_methane",
			() -> new Item(new Item.Properties()));// 一瓶可以烧8个
	public static final RegistryObject<Item> BIG_BOTTLED_METHANE = ITEMS.register("big_bottled_methane",
			() -> new Item(new Item.Properties()));// 容量是上面的64倍，不能直接烧，需要气体压缩机
	public static final RegistryObject<Item> BOTTLED_OXYGEN = ITEMS.register("bottled_oxygen",
			() -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> BIG_BOTTLED_OXYGEN = ITEMS.register("big_bottled_oxygen",
			() -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> BOTTLED_FUEL = ITEMS.register("bottled_fuel",
			() -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> RAW_IRON_NUGGET = ITEMS.register("raw_iron_nugget",
			() -> new Item(new Item.Properties()));

	public static final RegistryObject<Item> INSULATION_MATERIAL = ITEMS.register("insulation_material",
			() -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> INSULATION_LAYER = ITEMS.register("insulation_layer",
			() -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> STRUCTURE_LAYER = ITEMS.register("sructure_layer",
			() -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> RADIATION_LAYER = ITEMS.register("radiation_layer",
			() -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> ROCKET_SHELL = ITEMS.register("rocket_shell",
			() -> new Item(new Item.Properties()));

	public static final RegistryObject<Item> ROCKET_FUEL_TANK = ITEMS.register("rocket_fuel_tank",
			() -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> ROCKET_OXYGEN_TANK = ITEMS.register("rocket_oxygen_tank",
			() -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> ROCKET_ACTIVE_SPACE = ITEMS.register("rocket_active_space",
			() -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> ROCKET_COWLING = ITEMS.register("rocket_cowling",
			() -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> ROCKET_SPOUT = ITEMS.register("rocket_spout",
			() -> new Item(new Item.Properties()));

	public static final RegistryObject<Item> OMINOUS_UPGRADE_SMITHING_TEMPLATE = ITEMS.register("ominous_upgrade_smithing_template",
			() -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> PIECE_RAWGOLD = ITEMS.register("piece_rawgold",
			() -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> PIECE_OBSIDIAN = ITEMS.register("piece_obsidian",
			() -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> MAGNET_SUPPRESSOR = ITEMS.register("magnet_suppressor",
			() -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> SUCKER = ITEMS.register("sucker",
			() -> new Item(new Item.Properties()));

	public static final RegistryObject<Item> OMINOUS_GEMSTONE_REACTOR = ITEMS.register("ominous_gemstone_reactor",
			() -> new Item(new Item.Properties()));

	public static final RegistryObject<Item> OMINOUS_CAKE = ITEMS.register("ominous_cake",() -> new Item(new Item.Properties()
			.food(new FoodProperties.Builder()
					.nutrition(1)
					.saturationMod(20)
					.effect( () -> {
						int rand = new Random().nextInt(100) + 1;
						if (rand <= 30) {
							return new MobEffectInstance(EffectRegister.OMINOUSLUCK.get(), 1200 * 7);
						} else if (rand <= 95) {
							return new MobEffectInstance(EffectRegister.MENTALABUSE.get(), 20 * 100);
						} else {
							return new MobEffectInstance(MobEffects.BAD_OMEN, 1200 * 15);
						}
					}, 1.0f)
					.build())
	));

	public static final RegistryObject<Item> FRENCH_BREAD = ITEMS.register(ItemFrenchBread.global_name, () -> new ItemFrenchBread(SwordTier.frenchBread, 4, 1f, new Item.Properties()));

	//头
	public static final RegistryObject<Item> SPACESUIT_HELMET = ITEMS.register("spacesuit_helmet",
			()->new ArmorItem(ModArmorMaterials.SPACESUIT, ArmorItem.Type.HELMET,new Item.Properties().stacksTo(1)));
	//胸
	public static final RegistryObject<Item> SPACESUIT_CHESTPLATE = ITEMS.register("spacesuit_chestplate",
			()->new ArmorItem(ModArmorMaterials.SPACESUIT, ArmorItem.Type.CHESTPLATE,new Item.Properties().stacksTo(1)));
	//裤
	public static final RegistryObject<Item> SPACESUIT_LEGGINGS = ITEMS.register("spacesuit_leggings",
			()->new ArmorItem(ModArmorMaterials.SPACESUIT, ArmorItem.Type.LEGGINGS,new Item.Properties().stacksTo(1)));
	
	//鞋
	public static final RegistryObject<Item> SPACESUIT_BOOTS = ITEMS.register("spacesuit_boots",
			()->new ArmorItem(ModArmorMaterials.SPACESUIT, ArmorItem.Type.BOOTS,new Item.Properties().stacksTo(1)));
	
	public static final RegistryObject<Item> CAN = ITEMS.register("cans", ItemCan::new);
    public static final RegistryObject<RocketItem> ROCKET_ITEM = ITEMS.register("rocket", 
    		() -> new RocketItem(new Item.Properties()));
	
    public static final RegistryObject<Item> OMINOUS_AXE = ITEMS.register(OminousAxe.global_name,
            () -> new OminousAxe(Tiers.GOLD, 6.0F, -3.0F, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> OMINOUS_HOE = ITEMS.register(OminousHoe.global_name,
            () -> new OminousHoe(Tiers.GOLD, 0, -3.0F, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> OMINOUS_PICKAXE = ITEMS.register(OminousPickaxe.global_name,
            () -> new OminousPickaxe(Tiers.GOLD, 1, -2.8F, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> OMINOUS_SHOVEL = ITEMS.register(OminousShovel.global_name,
            () -> new OminousShovel(Tiers.GOLD, 6.0F, -3.0F, new Item.Properties().stacksTo(1)));
    
    
    public static final RegistryObject<Item> CHANGE_STICK = ITEMS.register(ItemChangeStick.global_name,
            () -> new ItemChangeStick(new Item.Properties()));
    
    public static final RegistryObject<Item> JUNCTION_CONNECTOR = ITEMS.register(ItemJunctionConnector.global_name,
            () -> new ItemJunctionConnector(new Item.Properties()));
    
    public static final RegistryObject<Item> BLUE_PRINT = ITEMS.register(ItemBlueprint.global_name,
            () -> new ItemBlueprint(new Item.Properties().stacksTo(1)));
    
    public static final RegistryObject<Item> BUILDING_STRUCTURE = ITEMS.register(ItemBuildingStructure.global_name,
            () -> new ItemBuildingStructure(new Item.Properties().stacksTo(128)));
    
    public static final RegistryObject<Item> WIRE_CREATOR = ITEMS.register(WireCreator.global_name,
            () -> new WireCreator(new Item.Properties().stacksTo(1)));
    
    public static final RegistryObject<Item> WIRE_CUTOR = ITEMS.register(WireCutor.global_name,
            () -> new WireCutor(new Item.Properties().stacksTo(1)));

	public static final RegistryObject<Item> ELECTRIC_DEBUGGER_STICK = ITEMS.register(ElectricDebuggerStick.global_name,
			() -> new ElectricDebuggerStick(new Item.Properties().stacksTo(1)));
    
    public static final RegistryObject<Item> BATTERY_HEART = ITEMS.register(BatteryHeart.global_name,
            () -> new BatteryHeart(new Item.Properties()));
    
    public static final RegistryObject<Item> CHEESE_PIECE = ITEMS.register(CheesePiece.global_name,
            () -> new CheesePiece(new Item.Properties()));
    
    public static final RegistryObject<Item> FROSTFIRE_FRUIT = ITEMS.register(FrostfireFruit.global_name,
            () -> new FrostfireFruit(FarmBlockRegistry.FROSTFIRE_FRUIT_B.get(),new Item.Properties()));
	public static final RegistryObject<Item> FROSTFIRE_FRUIT_PIECE = ITEMS.register("frostfire_fruit_piece",
			() -> new Item(new Item.Properties().food(new FoodProperties.Builder()
					.nutrition(1)
					.saturationMod(0)
					.build()
			)));


	public static final RegistryObject<Item> ARTIFICIAL_DOUGH = ITEMS.register("artificial_dough",
			() -> new Item(new Item.Properties()));


	public static final RegistryObject<Item> CHEESE_PIE = ModList.get().isLoaded("farmersdelight")
			? ITEMS.register("cheese_pie",
			() -> new BlockItem(BlockRegister.CHEESE_PIE.get(), new Item.Properties().stacksTo(16)))
			: null;
	public static final RegistryObject<Item> CHEESE_PIE_SLICE = ModList.get().isLoaded("farmersdelight")
			? ITEMS.register("cheese_pie_slice",
			() -> new Item(new Item.Properties()
					.food(new FoodProperties.Builder()
							.nutrition(3)
							.saturationMod(5).build())
			))
			: null;

	public static final RegistryObject<Item> DEVELOPERS_PIE = ITEMS.register("developers_pie",
			() -> new BlockItem(BlockRegister.DEVELOPERS_PIE.get(), new Item.Properties().stacksTo(1)));

	public static final RegistryObject<Item> CHEESE_BUCKET = ITEMS.register("cheese_fluid_bucket",
			() -> new BucketItem(CheeseFluid.SOURCE_CHEESE_FLUID,
					new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));

	public static final RegistryObject<Item> PRESSURIZED_CAN = ITEMS.register("pressurized_can",
			() -> new Item(new Item.Properties()));


}
