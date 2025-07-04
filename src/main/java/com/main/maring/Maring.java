package com.main.maring;


import com.mojang.logging.LogUtils;

import animal.entity.MonsterRegister;
import animal.entity.villager.ModVillager;
import block.norm.*;
import creativetab.CreativeTabsRegister;
import effect.brew.BrewRigster;
import effect.registry.EffectRegister;
import event.forge.EnergyNetCalculation;
import item.*;
import item.can.CanHelper;
import machine.registry.MBlockEntityRegister;
import menu.registry.MenuRegister;
import menu.reseachtable.TechTreeLayout;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import network.NetworkHandler;
import tags.register.AddTag;
import tags.register.TagkeyRegistry;
import util.EntityGravity;
import util.ItemGravity;
import util.mar.EnvironmentDataManager;
import util.net.EnergyNetProcess;
import util.tech.TechManager;
import vehicle.VehicleRegister;
import worldgen.feature.FeatureRegistry;

import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Maring.MODID)
public class Maring
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "maring";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    // Create a Deferred Register to hold Blocks which will all be registered under the "examplemod" namespace
    static{
    	//AddTag.init();
    	TagkeyRegistry.init();
    }
    
    public Maring()
    {
    	
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        
        NetworkHandler.register();
        // Register the commonSetup method for modloading
        //modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);

        BlockRegister.BLOCKS.register(modEventBus);
        BlockRegister.BLOCK_ITEMS.register(modEventBus);
        machine.registry.MBlockRegister.BLOCKS.register(modEventBus);
        machine.registry.MBlockRegister.BLOCK_ITEMS.register(modEventBus);   
        
        EffectRegister.EFFECTS.register(modEventBus);
        
        ItemRegister.ITEMS.register(modEventBus);
        block.entity.BlockEntityRegister.BLOCKENTITIES.register(modEventBus);
        MBlockEntityRegister.BLOCKENTITIES.register(modEventBus);
        
        MenuRegister.MENU_TYPES.register(modEventBus);
        CreativeTabsRegister.CREATIVE_MODE_TABS.register(modEventBus);
        MonsterRegister.ENTITY_TYPES.register(modEventBus);
        FeatureRegistry.FEATURES.register(modEventBus);
        VehicleRegister.ENTITIES.register(modEventBus);
        
        ModVillager.POIRegister(modEventBus);
        ModVillager.VillagerRegister(modEventBus);
        //MinecraftForge.EVENT_BUS.register(new BiomeEffectApplier());
        MinecraftForge.EVENT_BUS.register(new EntityGravity());
        MinecraftForge.EVENT_BUS.register(new ItemGravity());
        MinecraftForge.EVENT_BUS.register(new ItemGravity());
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        
        BrewRigster.registerBrewingRecipes();
        CanHelper.init();
        
        TechManager.initiateTech();
        TechTreeLayout.genLayout(TechManager.getOriginalTechNodes());
        
        
        Plugin.detect();
        
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
        
        MinecraftForge.EVENT_BUS.register(EnvironmentDataManager.class);
      
        
        
    }
    

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);

        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
        
        //NetworkHandler.register();
        
        
    }
    
    private void clientSetup(final FMLClientSetupEvent event) {
        LOGGER.info("HELLO FROM CLIENT SETUP");
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
        
        ExtraConfig.load(event.getServer());
    }
    
    
    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    /*
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
            
        }
        
    }*/
    
    
    
}
