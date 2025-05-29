package com.main.maring;


import com.main.maring.block.entity.BlockEntityRegister;
import com.main.maring.block.norm.BlockRegister;
import com.main.maring.item.ItemRegister;
import com.main.maring.machine.registry.MBlockRegister;
import com.mojang.logging.LogUtils;

import com.main.maring.animal.entity.MonsterRegister;
import com.main.maring.animal.entity.villager.ModVillager;
import com.main.maring.creativetab.CreativeTabsRegister;
import com.main.maring.effect.brew.BrewRigster;
import com.main.maring.effect.registry.EffectRegister;
import com.main.maring.item.can.CanHelper;
import com.main.maring.machine.registry.MBlockEntityRegister;
import com.main.maring.menu.registry.MenuRegister;
import com.main.maring.menu.reseachtable.TechTreeLayout;
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
import com.main.maring.network.NetworkHandler;
import com.main.maring.tags.register.TagkeyRegistry;
import com.main.maring.util.EntityGravity;
import com.main.maring.util.ItemGravity;
import com.main.maring.util.mar.EnvironmentDataManager;
import com.main.maring.util.tech.TechManager;
import com.main.maring.vehicle.VehicleRegister;
import com.main.maring.worldgen.feature.FeatureRegistry;

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
        MBlockRegister.BLOCKS.register(modEventBus);
        MBlockRegister.BLOCK_ITEMS.register(modEventBus);
        
        EffectRegister.EFFECTS.register(modEventBus);
        
        ItemRegister.ITEMS.register(modEventBus);
        BlockEntityRegister.BLOCKENTITIES.register(modEventBus);
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
