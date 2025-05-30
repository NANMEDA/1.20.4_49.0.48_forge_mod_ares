package com.main.maring;


import com.main.maring.block.entity.BlockEntityRegister;
import com.main.maring.block.norm.BlockRegister;
import com.main.maring.config.CommonConfig;
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
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
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
    
    public Maring()
    {
    	
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CommonConfig.init());

        TagkeyRegistry.init();

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
        
        MinecraftForge.EVENT_BUS.register(EnvironmentDataManager.class);
        
    }
}
