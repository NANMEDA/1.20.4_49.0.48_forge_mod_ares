package com.main.maring;


import com.block.register.*;

import com.creativetabs.register.CreativeTabsRegister;
import com.effect.register.EffectRegister;
import com.item.register.*;
import com.menu.register.MenuRegister;
import com.mojang.logging.LogUtils;

import animal.client.model.JumpSpiderModel;
import animal.client.render.JumpSpiderRenderer;
import animal.entity.MonsterRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
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
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import tags.register.AddTag;
import tags.register.TagkeyRegister;

import java.util.stream.IntStream;

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
    	AddTag.init();
    	TagkeyRegister.init();
    }
    
    public Maring()
    {
    	
    	
    	
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);
       
        EffectRegister.EFFECTS.register(modEventBus);

        BlockRegister.BLOCKS.register(modEventBus);
        BlockRegister.BLOCK_ITEMS.register(modEventBus);
        ItemRegister.ITEMS.register(modEventBus);
        block.entity.register.BlockEntityRegister.BLOCKENTITIES.register(modEventBus);
        MenuRegister.MENU_TYPES.register(modEventBus);
        CreativeTabsRegister.CREATIVE_MODE_TABS.register(modEventBus);
        MonsterRegister.ENTITY_TYPES.register(modEventBus);
        
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.logDirtBlock)
            LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));

        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);

        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }
    
    private void clientSetup(final FMLClientSetupEvent event) {
        LOGGER.info("HELLO FROM CLIENT SETUP");
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }
    
    
    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
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
        
        
		@SubscribeEvent
	    public static void registerEntityLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
	        event.registerLayerDefinition(JumpSpiderModel.LAYER_LOCATION,JumpSpiderModel::createBodyLayer);
	    } 
    }
}
