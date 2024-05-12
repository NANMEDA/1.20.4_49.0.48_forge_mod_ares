package com.main.maring;


import com.block.register.*;

import com.creativetabs.register.CreativeTabsRegister;
import com.effect.register.EffectRegister;
import com.item.register.*;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
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
    
    //public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    //public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    //public static final RegistryObject<Block>[] COMMON_BLOCKS = new RegistryObject[BlockBasic.BLOCK_NUMBER];
    //public static final RegistryObject<Item>[] COMMON_BLOCK_ITEMS = new RegistryObject[BlockBasic.BLOCK_NUMBER];

    
    /*
    static {
        IntStream.range(0, BlockBasic.BLOCK_NUMBER).forEach(i -> {
        	COMMON_BLOCKS[i] = BLOCKS.register(BlockBasic.getBlockName(i), () -> {
        	    BlockBehaviour.Properties properties = BlockBehaviour.Properties.of()
        	            .sound(BlockBasic.getBlockSound(i))
        	            .strength(BlockBasic.getBlockStrength(i)[0], BlockBasic.getBlockStrength(i)[1])
        	            .mapColor(BlockBasic.getBlockMapColor(i));
        	    if (BlockBasic.needTool(i)) {
        	        properties.requiresCorrectToolForDrops();
        	    }
        	    return new Block(properties);
        	});
        	COMMON_BLOCK_ITEMS[i] = ITEMS.register(BlockBasic.getBlockName(i), () -> new BlockItem(COMMON_BLOCKS[i].get(), new Item.Properties()));
        });
        
        IntStream.range(0, itemFood.ITEM_FOOD_NUMBER).forEach(i -> {
        	FoodProperties.Builder foodBuilder = new FoodProperties.Builder()
        	        .nutrition(itemFood.getFoodNutrition(i))
        	        .saturationMod(itemFood.getFoodFull(i));
        	if (itemFood.getFoodEffect(i) != null) {foodBuilder.effect(itemFood.getFoodEffect(i), itemFood.getFoodEffectProbal(i));}
        	if (itemFood.getFoodEat(i)) {foodBuilder.alwaysEat();}
        	FOOD_ITEMS[i] = ITEMS.register(itemFood.getFoodName(i), () -> new Item(new Item.Properties().food(foodBuilder.build())));
        });
    }
*/
    // Creates a creative tab with the id "examplemod:example_tab" for the example item, that is placed after the combat tab
    /*
    public static final RegistryObject<CreativeModeTab> MAR_MAIN_TAB = CREATIVE_MODE_TABS.register("mar_main_block_tab", () -> CreativeModeTab.builder()
    		.title(Component.translatable("mar_main_block_tab"))
    		.withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> BlockRegister.COMMON_BLOCKS[0].get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                IntStream.range(0, BlockBasic.BLOCK_NUMBER).forEach(i -> {
                	output.accept(COMMON_BLOCK_ITEMS[i].get());
                });
            }).build());
    public static final RegistryObject<CreativeModeTab> MAR_FOOD_TAB = CREATIVE_MODE_TABS.register("mar_food_tab", () -> CreativeModeTab.builder()
    		.title(Component.translatable("mar_food_tab"))
    		.withTabsBefore(MAR_MAIN_TAB.getId())
            .icon(() -> FOOD_ITEMS[0].get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                IntStream.range(0, itemFood.ITEM_FOOD_NUMBER).forEach(i -> {
                	output.accept(FOOD_ITEMS[i].get());
                });
            }).build());
    
    */
    
    
    public Maring()
    {
    	
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
            // Initialize EffectRegister class
       
        EffectRegister.EFFECTS.register(modEventBus);

        BlockRegister.BLOCKS.register(modEventBus);
        BlockRegister.BLOCK_ITEMS.register(modEventBus);
        ItemRegister.ITEMS.register(modEventBus);
        
        CreativeTabsRegister.CREATIVE_MODE_TABS.register(modEventBus);
        
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

    // Add the example block item to the building blocks tab
    /*
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS)
            event.accept(EXAMPLE_BLOCK_ITEM);
    }
     */
    // You can use SubscribeEvent and let the Event Bus discover methods to call
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
    }
}
