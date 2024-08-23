package menu.register;

import item.ItemRegister;
import menu.advancedmetalmanufactor.AdvancedMetalManufactorMenu;
import menu.basicmetalmanufactor.BasicMetalManufactorMenu;
import menu.blueprintbuilder.BlueprintBuilderMenu;
import menu.canfoodmaker.CanfoodMakerMenu;
import menu.etchingmachine.EtchingMachineMenu;
import menu.fuelrefiner.FuelRefinerMenu;
import menu.microwaveoven.MicrowaveOvenMenu;
import menu.playerextend.PlayerExtendMenu;
import menu.playerextend.PlayerExtendScreen;
import menu.powerstationburn.PowerStationBurnMenu;
import menu.reseachtable.ResearchTableMenu;
import menu.show.ShowBlockMenu;
import menu.show.itemstack.ShowItemStackMenu;
import menu.stonewasher.StoneWasherMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MenuRegister {
	public static final String MODID = "maring";
	public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, MODID);
    
    public static final RegistryObject<MenuType<PowerStationBurnMenu>> POWERSTATIONBURN_MENU = MENU_TYPES.register("powerstationburn_menu",
            () -> IForgeMenuType.create((windowId, inv, data) -> new PowerStationBurnMenu(inv, windowId, data.readBlockPos())));

    public static final RegistryObject<MenuType<CanfoodMakerMenu>> CANFOODMAKER_MENU = MENU_TYPES.register("canfoodmaker_menu",
            () -> IForgeMenuType.create((windowId, inv, data) -> new CanfoodMakerMenu(inv, windowId, data.readBlockPos())));

    public static final RegistryObject<MenuType<MicrowaveOvenMenu>> MICROWAVEOVEN_MENU = MENU_TYPES.register("microwaveoven_menu",
            () -> IForgeMenuType.create((windowId, inv, data) -> new MicrowaveOvenMenu(inv, windowId, data.readBlockPos())));

    public static final RegistryObject<MenuType<BasicMetalManufactorMenu>> BASICMETALMANUFACTOR_MENU = MENU_TYPES.register("basicmetalmanufactor_menu",
            () -> IForgeMenuType.create((windowId, inv, data) -> new BasicMetalManufactorMenu(inv, windowId, data.readBlockPos())));

    public static final RegistryObject<MenuType<AdvancedMetalManufactorMenu>> ADVANCEDMETALMANUFACTOR_MENU = MENU_TYPES.register("advancedmetalmanufactor_menu",
            () -> IForgeMenuType.create((windowId, inv, data) -> new AdvancedMetalManufactorMenu(inv, windowId, data.readBlockPos())));

    public static final RegistryObject<MenuType<EtchingMachineMenu>> ETCHINGMACHINE_MENU = MENU_TYPES.register("etchingmachine_menu",
            () -> IForgeMenuType.create((windowId, inv, data) -> new EtchingMachineMenu(inv, windowId, data.readBlockPos())));

    public static final RegistryObject<MenuType<PlayerExtendMenu>> PLAYEREXTEND_MENU = MENU_TYPES.register("player_extend",
            () -> IForgeMenuType.create((windowId, inv, data) -> new PlayerExtendMenu(inv, windowId, data.readBlockPos())));
    
    public static final RegistryObject<MenuType<StoneWasherMenu>> STONEWASHER_MENU = MENU_TYPES.register("stonewasher_menu",
            () -> IForgeMenuType.create((windowId, inv, data) -> new StoneWasherMenu(inv, windowId, data.readBlockPos())));
    
    public static final RegistryObject<MenuType<FuelRefinerMenu>> FUELREFINER_MENU = MENU_TYPES.register("fuelrefiner_menu",
            () -> IForgeMenuType.create((windowId, inv, data) -> new FuelRefinerMenu(inv, windowId, data.readBlockPos())));
    
    public static final RegistryObject<MenuType<ResearchTableMenu>> RESEARCHTABLE_MENU = MENU_TYPES.register("researchtable_menu",
    		() -> IForgeMenuType.create((windowId, inv, data) -> new ResearchTableMenu(inv, windowId, data.readBlockPos())));
    
    public static final RegistryObject<MenuType<BlueprintBuilderMenu>> BLUEPRINTBUILDER_MENU = MENU_TYPES.register("blueprintbuilder_menu",
    		() -> IForgeMenuType.create((windowId, inv, data) -> new BlueprintBuilderMenu(inv, windowId, data.readBlockPos())));
    
    public static final RegistryObject<MenuType<ShowBlockMenu>> BLOCKSHOW_MENU = MENU_TYPES.register("showblock_menu",
    		() -> IForgeMenuType.create((windowId, inv, data) -> new ShowBlockMenu(inv, windowId, data.readBlockPos())));
    
    public static final RegistryObject<MenuType<ShowItemStackMenu>> ITEMSTACKSHOW_MENU = MENU_TYPES.register("showitem_menu",
    		() -> IForgeMenuType.create((windowId, inv, data) -> new ShowItemStackMenu(inv, windowId, new ItemStack(ItemRegister.BLUE_PRINT.get()), data.readBlockPos())));
/*
    public static final RegistryObject<MenuType<PlayerExtendMenu>> ROCKET_MENU = MENU_TYPES.register("rocket_menu",
            () -> IForgeMenuType.create((windowId, inv, data) -> new RocketMenu(inv, windowId, Rocket::new)));*/

}
