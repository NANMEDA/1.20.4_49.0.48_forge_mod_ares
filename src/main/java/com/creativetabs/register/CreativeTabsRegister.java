package com.creativetabs.register;

import java.util.stream.IntStream;

import com.block.register.BlockBasic;
import com.block.register.BlockRegister;
import com.item.register.ItemRegister;
import com.item.register.itemFood;

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
}
