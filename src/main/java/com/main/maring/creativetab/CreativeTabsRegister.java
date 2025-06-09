package com.main.maring.creativetab;

import com.main.maring.Maring;
import com.main.maring.block.norm.BlockBasic;
import com.main.maring.block.norm.BlockRegister;
import com.main.maring.block.norm.advancedmetalmanufactor.Register;
import com.main.maring.block.norm.fastbuild.FastBuildRegister;
import com.main.maring.item.ItemRegister;
import com.main.maring.machine.registry.MBlockRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.fml.ModList;

/**
 * All the item show in the creative tabs register there
 * 所有创造模式物品栏在此注册
 * */
public class CreativeTabsRegister {
	public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Maring.MODID);

    public static final RegistryObject<CreativeModeTab> MAR_MAIN_TAB = CREATIVE_MODE_TABS.register("mar_main_block_tab", () -> CreativeModeTab.builder()
    		.title(Component.translatable("mar_main_block_tab"))
    		.withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> BlockRegister.COMMON_BLOCK_ITEMS[0].get().getDefaultInstance())
            .displayItems((parameters, output) -> {
            	for(int i=0;i<BlockBasic.BLOCK_BASIC_NUMBER;i++) {
            		output.accept(BlockRegister.COMMON_BLOCK_ITEMS[i].get());
            	}
                output.accept(BlockRegister.awakeningstone_BLOCK_ITEM.get());
                output.accept(BlockRegister.unbrokenglass_BLOCK_ITEM.get());
                output.accept(com.main.maring.block.norm.deposit.Register.DEPOSIT_EMPTY_ITEM.get());
                output.accept(com.main.maring.block.norm.deposit.Register.DEPOSIT_ICE_ITEM.get());
                output.accept(com.main.maring.block.norm.deposit.Register.DEPOSIT_IRON_ITEM.get());
                output.accept(com.main.maring.block.norm.deposit.Register.DEPOSIT_GOLD_ITEM.get());
				output.accept(BlockRegister.unbrokenmagma_BLOCK_ITEM.get());
                
            }).build());
    
    public static final RegistryObject<CreativeModeTab> MAR_FOOD_TAB = CREATIVE_MODE_TABS.register("mar_food_tab", () -> CreativeModeTab.builder()
    		.title(Component.translatable("mar_food_tab"))
    		.withTabsBefore(MAR_MAIN_TAB.getId())
            .icon(() -> ItemRegister.CHEESE_PIECE.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
        		output.accept(ItemRegister.CHEESE_PIECE.get());
        		output.accept(ItemRegister.FROSTFIRE_FRUIT.get());
				output.accept(ItemRegister.FROSTFIRE_FRUIT_PIECE.get());
				output.accept(ItemRegister.ARTIFICIAL_DOUGH.get());
				if (ModList.get().isLoaded("farmersdelight")) {
					//for farmersdelight
					output.accept(ItemRegister.CHEESE_PIE.get());
				}
            }).build());
    
    public static final RegistryObject<CreativeModeTab> MAR_MACHINE_TAB = CREATIVE_MODE_TABS.register("mar_machine_tab", () -> CreativeModeTab.builder()
    		.title(Component.translatable("mar_machine_tab"))
    		.withTabsBefore(MAR_FOOD_TAB.getId())
            .icon(() -> MBlockRegister.MINCROWAVEOVEN_I.get().getDefaultInstance())
            .displayItems((parameters, output) -> {

				output.accept(BlockRegister.VILLAGER_HANDMAKE_TABLE_BLOCK_ITEMS.get());
				output.accept(BlockRegister.BROKEN_ELECTRONIC_BLOCK_ITEMS.get());
				output.accept(BlockRegister.BROKEN_CHEMICAL_BLOCK_ITEMS.get());
				output.accept(BlockRegister.BROKEN_ADVANCED_ELECTRONIC_BLOCK_ITEMS.get());
				output.accept(BlockRegister.BROKEN_STRUCTURE_BLOCK_ITEMS.get());
				output.accept(BlockRegister.BROKEN_METAL_BLOCK_ITEMS.get());
				output.accept(BlockRegister.VILLAGER_BURRIED_PACKAGE_BLOCK_ITEMS.get());
				output.accept(BlockRegister.UNBROKEN_DORM_JUNCTION_BLOCK_ITEMS.get());

                //output.accept(BlockRegister.PowerStationBurn_BLOCK_ITEM.get());

            	output.accept(MBlockRegister.SOLARBASEMENT_I.get());
            	output.accept(MBlockRegister.SOLARPILLAR_I.get());
            	output.accept(MBlockRegister.SOLARPANEL_I.get());
            	output.accept(MBlockRegister.BATTERTBASEMENT_I.get());
            	output.accept(MBlockRegister.BATTERYCAPACITY_I.get());
            	output.accept(MBlockRegister.COREDIDDER_I.get());
            	output.accept(MBlockRegister.CORESUCKER_I.get());
            	output.accept(MBlockRegister.COREPIPE_I.get());
            	output.accept(MBlockRegister.CORECOLDPIPE_I.get());
            	output.accept(MBlockRegister.ENERGYVIEWER_I.get());
            	output.accept(MBlockRegister.MARREACTOR_I.get());
                output.accept(com.main.maring.block.norm.canfoodmaker.Register.canfoodmaker_BLOCK_ITEM.get());
                output.accept(MBlockRegister.MINCROWAVEOVEN_I.get());
                output.accept(BlockRegister.basicmetalmanufactor_BLOCK_ITEM.get());
                output.accept(Register.advancedmetalmanufactor_BLOCK_ITEM.get());
                output.accept(BlockRegister.bioplasticbuilder_BLOCK_ITEM.get());
                output.accept(com.main.maring.block.norm.crystalbuilder.Register.crystalbuilder_BLOCK_ITEM.get());
                output.accept(com.main.maring.block.norm.etchingmachine.Register.etchingmachine_BLOCK_ITEM.get());
                output.accept(com.main.maring.block.norm.glassbuilder.Register.glassbuilder_BLOCK_ITEM.get());
                output.accept(com.main.maring.block.norm.watergather.Register.watergather_BLOCK_ITEM.get());
                output.accept(com.main.maring.block.norm.stonewasher.Register.stonewasher_BLOCK_ITEM.get());
                output.accept(com.main.maring.block.norm.fuelrefiner.Register.fuelrefiner_BLOCK_ITEM.get());
                output.accept(com.main.maring.block.norm.researchtable.Register.researchtable_BLOCK_ITEM.get());
                output.accept(com.main.maring.block.norm.blueprintbuilder.Register.blueprintbuilder_BLOCK_ITEM.get());

            }).build());
    
    public static final RegistryObject<CreativeModeTab> MAR_MATERIAL_TAB = CREATIVE_MODE_TABS.register("mar_material_tab", () -> CreativeModeTab.builder()
    		.title(Component.translatable("mar_material_tab"))
    		.withTabsBefore(MAR_MACHINE_TAB.getId())
            .icon(() -> ItemRegister.OMINOUS_GEMSTONE.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
				output.accept(ItemRegister.OMINOUS_GEMSTONE.get());
				output.accept(ItemRegister.BASIC_METAL_PARTS.get());
				output.accept(ItemRegister.ADVANCED_METAL_PARTS.get());
				output.accept(ItemRegister.BIOPLASTIC_PARTS.get());
				output.accept(ItemRegister.SEMICONDUCTOR_PARTS.get());
				output.accept(ItemRegister.CRYSTAL_PARTS.get());
				output.accept(ItemRegister.BOTTLED_METHANE.get());
				output.accept(ItemRegister.BIG_BOTTLED_METHANE.get());
				output.accept(ItemRegister.BOTTLED_OXYGEN.get());
				output.accept(ItemRegister.BIG_BOTTLED_OXYGEN.get());
				output.accept(ItemRegister.BOTTLED_FUEL.get());
				output.accept(ItemRegister.RAW_IRON_NUGGET.get());
				output.accept(ItemRegister.INSULATION_MATERIAL.get());
				output.accept(ItemRegister.INSULATION_LAYER.get());
				output.accept(ItemRegister.STRUCTURE_LAYER.get());
				output.accept(ItemRegister.RADIATION_LAYER.get());
				output.accept(ItemRegister.ROCKET_SHELL.get());
				output.accept(ItemRegister.ROCKET_FUEL_TANK.get());
				output.accept(ItemRegister.ROCKET_OXYGEN_TANK.get());
				output.accept(ItemRegister.ROCKET_ACTIVE_SPACE.get());
				output.accept(ItemRegister.ROCKET_COWLING.get());
				output.accept(ItemRegister.ROCKET_SPOUT.get());
				output.accept(ItemRegister.OMINOUS_UPGRADE_SMITHING_TEMPLATE.get());
				output.accept(ItemRegister.PIECE_RAWGOLD.get());
				output.accept(ItemRegister.PIECE_OBSIDIAN.get());
				output.accept(ItemRegister.MAGNET_SUPPRESSOR.get());
				output.accept(ItemRegister.SUCKER.get());
				output.accept(ItemRegister.OMINOUS_GEMSTONE_REACTOR.get());

				output.accept(ItemRegister.BUILDING_STRUCTURE.get());
                output.accept(ItemRegister.BATTERY_HEART.get());
            }).build());
    
    public static final RegistryObject<CreativeModeTab> MAR_TOOL_TAB = CREATIVE_MODE_TABS.register("mar_tool_tab", () -> CreativeModeTab.builder()
    		.title(Component.translatable("mar_tool_tab"))
    		.withTabsBefore(MAR_MATERIAL_TAB.getId())
            .icon(() -> ItemRegister.FRENCH_BREAD.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
            	output.accept(ItemRegister.SPACESUIT_BOOTS.get());
            	output.accept(ItemRegister.SPACESUIT_LEGGINGS.get());
            	output.accept(ItemRegister.SPACESUIT_CHESTPLATE.get());
            	output.accept(ItemRegister.SPACESUIT_HELMET.get());
            	output.accept(ItemRegister.OMINOUS_AXE.get());
            	output.accept(ItemRegister.OMINOUS_HOE.get());
            	output.accept(ItemRegister.OMINOUS_PICKAXE.get());
            	output.accept(ItemRegister.OMINOUS_SHOVEL.get());
                output.accept(ItemRegister.FRENCH_BREAD.get());
                output.accept(ItemRegister.CHANGE_STICK.get());
                output.accept(ItemRegister.JUNCTION_CONNECTOR.get());
                output.accept(ItemRegister.WIRE_CREATOR.get());
                output.accept(ItemRegister.WIRE_CUTOR.get());
            }).build());
    

    
    public static final RegistryObject<CreativeModeTab> MAR_DORM_TAB = CREATIVE_MODE_TABS.register("mar_dorm_tab", () -> CreativeModeTab.builder()
    		.title(Component.translatable("mar_dorm_tab"))
    		.withTabsBefore(MAR_TOOL_TAB.getId())
            .icon(() -> FastBuildRegister.basicspheredorm_BLOCK_ITEM.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
            	output.accept(FastBuildRegister.basicspheredorm_BLOCK_ITEM.get());
            	output.accept(FastBuildRegister.basicflatspheredorm_BLOCK_ITEM.get());
            	output.accept(FastBuildRegister.basiceclipsedorm_BLOCK_ITEM.get());
            	output.accept(FastBuildRegister.basicflateclipsedorm_BLOCK_ITEM.get());
            	output.accept(FastBuildRegister.spheredoor_BLOCK_ITEM.get());
            	output.accept(FastBuildRegister.basiccylinderdorm_BLOCK_ITEM.get());
            	output.accept(com.main.maring.block.norm.decoration.Register.LIGHT_PANE_BLOCK_ITEM.get());
            }).build());
}
