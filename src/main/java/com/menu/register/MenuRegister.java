package com.menu.register;

import com.menu.advancedmetalmanufactor.AdvancedMetalManufactorMenu;
import com.menu.basicmetalmanufactor.BasicMetalManufactorMenu;
import com.menu.blueprintbuilder.BlueprintBuilderMenu;
import com.menu.canfoodmaker.CanfoodMakerMenu;
import com.menu.etchingmachine.EtchingMachineMenu;
import com.menu.fuelrefiner.FuelRefinerMenu;
import com.menu.microwaveoven.MicrowaveOvenMenu;
import com.menu.playerextend.PlayerExtendMenu;
import com.menu.playerextend.PlayerExtendScreen;
import com.menu.powerstationburn.PowerStationBurnMenu;
import com.menu.reseachtable.ResearchTableMenu;
import com.menu.stonewasher.StoneWasherMenu;

import net.minecraft.world.inventory.MenuType;
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
/*
    public static final RegistryObject<MenuType<PlayerExtendMenu>> ROCKET_MENU = MENU_TYPES.register("rocket_menu",
            () -> IForgeMenuType.create((windowId, inv, data) -> new RocketMenu(inv, windowId, Rocket::new)));*/

}
