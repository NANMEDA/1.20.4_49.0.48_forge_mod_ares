package com.creativetabs.register;

import java.util.stream.IntStream;

import com.item.register.ItemRegister;
import com.item.register.itemFood;
import com.item.register.itemMaterial;

import block.norm.BlockBasic;
import block.norm.BlockElectricBasic;
import block.norm.BlockRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class CreativeTabsRegister {
	private static final String MODID = "maring";
	public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public static final RegistryObject<CreativeModeTab> MAR_MAIN_TAB = CREATIVE_MODE_TABS.register("mar_main_block_tab", () -> CreativeModeTab.builder()
    		.title(Component.translatable("mar_main_block_tab"))
    		.withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> BlockRegister.COMMON_BLOCK_ITEMS[0].get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                IntStream.range(0, BlockBasic.BLOCK_BASIC_NUMBER).forEach(i -> {
                	output.accept(BlockRegister.COMMON_BLOCK_ITEMS[i].get());
                });
                output.accept(BlockRegister.unbrokenglass_BLOCK_ITEM.get());
            }).build());
    
    public static final RegistryObject<CreativeModeTab> MAR_FOOD_TAB = CREATIVE_MODE_TABS.register("mar_food_tab", () -> CreativeModeTab.builder()
    		.title(Component.translatable("mar_food_tab"))
    		.withTabsBefore(MAR_MAIN_TAB.getId())
            .icon(() -> ItemRegister.FOOD_ITEMS[0].get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                IntStream.range(0, itemFood.ITEM_FOOD_NUMBER).forEach(i -> {
                	output.accept(ItemRegister.FOOD_ITEMS[i].get());
                });
            }).build());
    
    public static final RegistryObject<CreativeModeTab> MAR_MACHINE_TAB = CREATIVE_MODE_TABS.register("mar_machine_tab", () -> CreativeModeTab.builder()
    		.title(Component.translatable("mar_machine_tab"))
    		.withTabsBefore(MAR_FOOD_TAB.getId())
            .icon(() -> BlockRegister.PowerStationBurn_BLOCK_ITEM.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                IntStream.range(0, BlockElectricBasic.BLOCK_ELECTRIC_NUMBER).forEach(i -> {
                	output.accept(BlockRegister.ELECTRIC_BLOCK_ITEMS[i].get());
                });
                output.accept(BlockRegister.PowerStationBurn_BLOCK_ITEM.get());
                output.accept(BlockRegister.PowerStationSun_BLOCK_ITEM.get());
                output.accept(BlockRegister.canfoodmaker_BLOCK_ITEM.get());
                output.accept(BlockRegister.microwaveoven_BLOCK_ITEM.get());
                output.accept(BlockRegister.basicmetalmanufactor_BLOCK_ITEM.get());
                output.accept(BlockRegister.bioplasticbuilder_BLOCK_ITEM.get());
                output.accept(block.norm.crystalbuilder.Register.crystalbuilder_BLOCK_ITEM.get());
            }).build());
    
    public static final RegistryObject<CreativeModeTab> MAR_MATERIAL_TAB = CREATIVE_MODE_TABS.register("mar_material_tab", () -> CreativeModeTab.builder()
    		.title(Component.translatable("mar_material_tab"))
    		.withTabsBefore(MAR_MACHINE_TAB.getId())
            .icon(() -> ItemRegister.MATERIAL_ITEMS[0].get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                IntStream.range(0, itemMaterial.ITEM_MATERIAL_NUMBER).forEach(i -> {
                	output.accept(ItemRegister.MATERIAL_ITEMS[i].get());
                });
            }).build());
}
