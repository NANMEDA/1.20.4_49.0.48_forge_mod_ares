package com.main.maring.event.client;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import com.main.maring.vehicle.VehicleRegister;
import com.main.maring.vehicle.rocket.RocketModel;
import com.main.maring.vehicle.rocket.RocketTier1Renderer;
import com.main.maring.animal.client.model.JumpSpiderModel;
import com.main.maring.animal.client.render.JumpSpiderRenderer;
import com.main.maring.animal.entity.MonsterRegister;
import com.main.maring.block.entity.BlockEntityRegister;
import com.main.maring.block.entity.consumer.advancedmetalmanufactor.AdvancedMetalManufactorEntityRender;
import com.main.maring.block.entity.consumer.basicmetalmanufactor.BasicMetalManufactorEntityRender;
import com.main.maring.block.entity.consumer.bioplasticbuilder.BioplasticBuilderEntityRender;
import com.main.maring.block.entity.consumer.canfoodmaker.CanfoodMakerEntityRender;
import com.main.maring.block.entity.consumer.etchingmachine.EtchingMachineEntityRender;
import com.main.maring.block.entity.consumer.glassbuilder.GlassBuilderEntityRender;
import com.main.maring.block.entity.consumer.stonewasher.StoneWasherEntityRender;
import com.main.maring.block.entity.consumer.watergather.WaterGatherEntityRender;
import com.main.maring.block.entity.neutral.crystalbuilder.CrystalBuilderEntityRender;
import com.main.maring.block.entity.neutral.researchtable.ResearchTableEntityRender;
import com.main.maring.machine.energy.consumer.render.CoreDiggerEntityRender;
import com.main.maring.machine.energy.consumer.render.MicrowaveOvenEntityRender;
import com.main.maring.machine.energy.producer.solar.render.MarReactorEntityRender;
import com.main.maring.machine.energy.producer.solar.render.SolarBasementEntityRender;
import com.main.maring.machine.energy.storage.render.BatteryBasementEntityRender;
import com.main.maring.machine.energy.viewer.EnergyViewerEntityRender;
import com.main.maring.machine.registry.MBlockEntityRegister;
import com.main.maring.menu.advancedmetalmanufactor.AdvancedMetalManufactorScreen;
import com.main.maring.menu.basicmetalmanufactor.BasicMetalManufactorScreen;
import com.main.maring.menu.blueprintbuilder.BlueprintBuilderScreen;
import com.main.maring.menu.canfoodmaker.CanfoodMakerScreen;
import com.main.maring.menu.coredigger.CoreDiggerScreen;
import com.main.maring.menu.dormcontrol.DomeControlScreen;
import com.main.maring.menu.energyviewer.EnergyViewerScreen;
import com.main.maring.menu.etchingmachine.EtchingMachineScreen;
import com.main.maring.menu.fuelrefiner.FuelRefinerScreen;
import com.main.maring.menu.marreactor.MarReactorScreen;
import com.main.maring.menu.microwaveoven.MicrowaveOvenScreen;
import com.main.maring.menu.playerextend.PlayerExtendScreen;
import com.main.maring.menu.powerstationburn.PowerStationBurnScreen;
import com.main.maring.menu.registry.MenuRegister;
import com.main.maring.menu.reseachtable.ResearchTableScreen;
import com.main.maring.menu.rocket.RocketrScreen;
import com.main.maring.menu.show.ShowBlockScreen;
import com.main.maring.menu.show.itemstack.ShowItemStackScreen;
import com.main.maring.menu.stonewasher.StoneWasherScreen;

@Mod.EventBusSubscriber(modid = "maring",bus = Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class ClientListener{

	@SubscribeEvent
	public static void ScreenRegister(FMLClientSetupEvent event) {		
		event.enqueueWork(()->MenuScreens.register(MenuRegister.POWERSTATIONBURN_MENU.get(), PowerStationBurnScreen::new));
		
		event.enqueueWork(()->MenuScreens.register(MenuRegister.CANFOODMAKER_MENU.get(), CanfoodMakerScreen::new));
		
		event.enqueueWork(()->MenuScreens.register(MenuRegister.MICROWAVEOVEN_MENU.get(), MicrowaveOvenScreen::new));
		
		event.enqueueWork(()->MenuScreens.register(MenuRegister.BASICMETALMANUFACTOR_MENU.get(), BasicMetalManufactorScreen::new));
		
		event.enqueueWork(()->MenuScreens.register(MenuRegister.ADVANCEDMETALMANUFACTOR_MENU.get(), AdvancedMetalManufactorScreen::new));
		
		event.enqueueWork(()->MenuScreens.register(MenuRegister.ETCHINGMACHINE_MENU.get(), EtchingMachineScreen::new));
		
		event.enqueueWork(()->MenuScreens.register(MenuRegister.STONEWASHER_MENU.get(), StoneWasherScreen::new));
		
		event.enqueueWork(()->MenuScreens.register(MenuRegister.FUELREFINER_MENU.get(), FuelRefinerScreen::new));
		
		event.enqueueWork(()->MenuScreens.register(MenuRegister.PLAYEREXTEND_MENU.get(), PlayerExtendScreen::new));
		
		event.enqueueWork(()->MenuScreens.register(MenuRegister.RESEARCHTABLE_MENU.get(), ResearchTableScreen::new));
		
		event.enqueueWork(()->MenuScreens.register(MenuRegister.BLUEPRINTBUILDER_MENU.get(), BlueprintBuilderScreen::new));
		
		event.enqueueWork(()->MenuScreens.register(MenuRegister.BLOCKSHOW_MENU.get(), ShowBlockScreen::new));
		
		event.enqueueWork(()->MenuScreens.register(MenuRegister.ITEMSTACKSHOW_MENU.get(), ShowItemStackScreen::new));
		
		event.enqueueWork(()->MenuScreens.register(MenuRegister.ENERGYVIEWER_MENU.get(), EnergyViewerScreen::new));
		
		event.enqueueWork(()->MenuScreens.register(MenuRegister.DORMCONTROL_MENU.get(), DomeControlScreen::new));
		
		event.enqueueWork(()->MenuScreens.register(MenuRegister.COREDIGGER_MENU.get(), CoreDiggerScreen::new));
		
		event.enqueueWork(()->MenuScreens.register(MenuRegister.MARREACTOR_MENU.get(), MarReactorScreen::new));
		
		event.enqueueWork(()->MenuScreens.register(MenuRegister.ROCKET_MENU.get(), RocketrScreen::new));
		
		
        event.enqueueWork(()-> EntityRenderers.register(MonsterRegister.JUMP_SPIDER.get(), JumpSpiderRenderer::new)); 
        
        event.enqueueWork(()-> EntityRenderers.register(VehicleRegister.ROCKET_ENTITY.get(), RocketTier1Renderer::new)); 
	}
	
	@SubscribeEvent
	public static void registerBlockEntityRender(EntityRenderersEvent.RegisterRenderers event) {
		event.registerBlockEntityRenderer(MBlockEntityRegister.microwaveoven_BLOCKENTITY.get(), MicrowaveOvenEntityRender::new);
		
		event.registerBlockEntityRenderer(BlockEntityRegister.basicmetalmanufactor_BLOCKENTITY.get(), BasicMetalManufactorEntityRender::new);
	
		event.registerBlockEntityRenderer(BlockEntityRegister.bioplasticbuilder_BLOCKENTITY.get(), BioplasticBuilderEntityRender::new);

		event.registerBlockEntityRenderer(BlockEntityRegister.crystalbuilder_BLOCKENTITY.get(), CrystalBuilderEntityRender::new);

		event.registerBlockEntityRenderer(BlockEntityRegister.glassbuilder_BLOCKENTITY.get(), GlassBuilderEntityRender::new);

		event.registerBlockEntityRenderer(BlockEntityRegister.watergather_BLOCKENTITY.get(), WaterGatherEntityRender::new);

		event.registerBlockEntityRenderer(BlockEntityRegister.researchtable_BLOCKENTITY.get(), ResearchTableEntityRender::new);

		event.registerBlockEntityRenderer(BlockEntityRegister.advancedmetalmanufactor_BLOCKENTITY.get(), AdvancedMetalManufactorEntityRender::new);

		event.registerBlockEntityRenderer(BlockEntityRegister.etchingmachine_BLOCKENTITY.get(), EtchingMachineEntityRender::new);

		event.registerBlockEntityRenderer(BlockEntityRegister.stonewasher_BLOCKENTITY.get(), StoneWasherEntityRender::new);
		
		event.registerBlockEntityRenderer(BlockEntityRegister.canfoodmaker_BLOCKENTITY.get(), CanfoodMakerEntityRender::new);

		event.registerBlockEntityRenderer(MBlockEntityRegister.SOLARBASEMENT_BE.get(), SolarBasementEntityRender::new);
		
		event.registerBlockEntityRenderer(MBlockEntityRegister.COREDIGGER_BE.get(), CoreDiggerEntityRender::new);
		
		event.registerBlockEntityRenderer(MBlockEntityRegister.MARREACTOR_BE.get(), MarReactorEntityRender::new);
		
		event.registerBlockEntityRenderer(MBlockEntityRegister.BATTERTBASEMENT_BE.get(), BatteryBasementEntityRender::new);
		
		event.registerBlockEntityRenderer(MBlockEntityRegister.ENERGYVIEWER_BE.get(), EnergyViewerEntityRender::new);
		
	}
	
	@SubscribeEvent
    public static void registerEntityLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(JumpSpiderModel.LAYER_LOCATION,JumpSpiderModel::createBodyLayer);
        
        event.registerLayerDefinition(RocketModel.LAYER_LOCATION,RocketModel::createBodyLayer);
    } 
	
}
