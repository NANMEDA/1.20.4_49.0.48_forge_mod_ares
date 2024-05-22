package com.menu.register;

import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.client.event.RegisterColorHandlersEvent.Item;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
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

}
