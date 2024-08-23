package item;

import java.util.stream.IntStream;

import item.armor.ModArmor;
import item.armor.ModArmorMaterials;
import item.blueprint.ItemBlueprint;
import item.can.ItemCan;
import item.rocket.RocketItem;
import item.tool.OminousAxe;
import item.tool.OminousHoe;
import item.tool.OminousPickaxe;
import item.tool.OminousShovel;
import item.tool.electric.WireCreator;
import item.weapon.ItemFrenchBread;
import item.weapon.SwordTier;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * 除了方块物品
 * 所有Item在此注册
 * @author NANMEDA
 * */
public class ItemRegister {
	private static final String MODID = "maring";
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
	
	@SuppressWarnings("unchecked")
	public static final RegistryObject<Item>[] FOOD_ITEMS = new RegistryObject[itemFood.ITEM_FOOD_NUMBER];
	@SuppressWarnings("unchecked")
	public static final RegistryObject<Item>[] MATERIAL_ITEMS = new RegistryObject[itemMaterial.ITEM_MATERIAL_NUMBER];
	
	static {
        IntStream.range(0, itemFood.ITEM_FOOD_NUMBER).forEach(i -> {
        	FoodProperties.Builder foodBuilder = new FoodProperties.Builder()
        	        .nutrition(itemFood.getFoodNutrition(i))
        	        .saturationMod(itemFood.getFoodFull(i));
        	if (itemFood.getFoodEffect(i) != null) {foodBuilder.effect(itemFood.getFoodEffect(i), itemFood.getFoodEffectProbal(i));}
        	if (itemFood.getFoodEat(i)) {foodBuilder.alwaysEat();}
        	FOOD_ITEMS[i] = ITEMS.register(itemFood.getFoodName(i), () -> new Item(new Item.Properties()
        			.food(foodBuilder.build())
        			));
        });
        
        IntStream.range(0, itemMaterial.ITEM_MATERIAL_NUMBER).forEach(i -> {
        	if(i==0) {
        		MATERIAL_ITEMS[i] = ITEMS.register(itemMaterial.getMaterialName(i), () -> new Item(new Item.Properties().stacksTo(8)));
        	}else if(i<6) {
        		MATERIAL_ITEMS[i] = ITEMS.register(itemMaterial.getMaterialName(i), () -> new Item(new Item.Properties().stacksTo(16)));
        	}else {
        		MATERIAL_ITEMS[i] = ITEMS.register(itemMaterial.getMaterialName(i), () -> new Item(new Item.Properties()));
        	}
        });
	}
	
	public static final RegistryObject<Item> FRENCH_BREAD = ITEMS.register(ItemFrenchBread.global_name, () -> new ItemFrenchBread(SwordTier.frenchBread, 4, 1f, new Item.Properties()));

	public static final RegistryObject<Item> SPACESUIT_HELMET = ITEMS.register("spacesuit_helmet",
	//装备材质，装备类型，物品属性
			()->new ModArmor(ModArmorMaterials.SPACESUIT, ArmorItem.Type.HELMET,new Item.Properties().stacksTo(1)));
	//胸甲
	public static final RegistryObject<Item> SPACESUIT_CHESTPLATE = ITEMS.register("spacesuit_chestplate",
			()->new ModArmor(ModArmorMaterials.SPACESUIT, ArmorItem.Type.CHESTPLATE,new Item.Properties().stacksTo(1)));
	//裤子
	public static final RegistryObject<Item> SPACESUIT_LEGGINGS = ITEMS.register("spacesuit_leggings",
			()->new ModArmor(ModArmorMaterials.SPACESUIT, ArmorItem.Type.LEGGINGS,new Item.Properties().stacksTo(1)));
	
	//鞋子
	public static final RegistryObject<Item> SPACESUIT_BOOTS = ITEMS.register("spacesuit_boots",
			()->new ModArmor(ModArmorMaterials.SPACESUIT, ArmorItem.Type.BOOTS,new Item.Properties().stacksTo(1)));
	
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
            () -> new ItemChangeStick(new Item.Properties().stacksTo(1)));
    
    public static final RegistryObject<Item> JUNCTION_CONNECTOR = ITEMS.register(ItemJunctionConnector.global_name,
            () -> new ItemJunctionConnector(new Item.Properties().stacksTo(1)));
    
    public static final RegistryObject<Item> BLUE_PRINT = ITEMS.register(ItemBlueprint.global_name,
            () -> new ItemBlueprint(new Item.Properties().stacksTo(1)));
    
    public static final RegistryObject<Item> BUILDING_STRUCTURE = ITEMS.register(ItemBuildingStructure.global_name,
            () -> new ItemBuildingStructure(new Item.Properties().stacksTo(128)));
    
    public static final RegistryObject<Item> WIRE_CREATOR = ITEMS.register(WireCreator.global_name,
            () -> new WireCreator(new Item.Properties().stacksTo(1)));
}
