package event.client;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import vehicle.VehicleRegister;
import vehicle.rocket.RocketModel;
import vehicle.rocket.RocketTier1Renderer;
import animal.client.model.JumpSpiderModel;
import animal.client.render.JumpSpiderRenderer;
import animal.entity.MonsterRegister;
import block.entity.BlockEntityRegister;
import block.entity.consumer.advancedmetalmanufactor.AdvancedMetalManufactorEntityRender;
import block.entity.consumer.basicmetalmanufactor.BasicMetalManufactorEntityRender;
import block.entity.consumer.bioplasticbuilder.BioplasticBuilderEntityRender;
import block.entity.consumer.canfoodmaker.CanfoodMakerEntityRender;
import block.entity.consumer.etchingmachine.EtchingMachineEntityRender;
import block.entity.consumer.glassbuilder.GlassBuilderEntityRender;
import block.entity.consumer.stonewasher.StoneWasherEntityRender;
import block.entity.consumer.watergather.WaterGatherEntityRender;
import block.entity.neutral.crystalbuilder.CrystalBuilderEntityRender;
import block.entity.neutral.researchtable.ResearchTableEntityRender;
import machine.energy.consumer.render.CoreDiggerEntityRender;
import machine.energy.consumer.render.MicrowaveOvenEntityRender;
import machine.energy.producer.solar.render.MarReactorEntityRender;
import machine.energy.producer.solar.render.SolarBasementEntityRender;
import machine.energy.storage.render.BatteryBasementEntityRender;
import machine.energy.viewer.EnergyViewerEntityRender;
import machine.registry.MBlockEntityRegister;
import menu.advancedmetalmanufactor.AdvancedMetalManufactorScreen;
import menu.basicmetalmanufactor.BasicMetalManufactorScreen;
import menu.blueprintbuilder.BlueprintBuilderScreen;
import menu.canfoodmaker.CanfoodMakerScreen;
import menu.coredigger.CoreDiggerScreen;
import menu.dormcontrol.DomeControlScreen;
import menu.energyviewer.EnergyViewerScreen;
import menu.etchingmachine.EtchingMachineScreen;
import menu.fuelrefiner.FuelRefinerScreen;
import menu.marreactor.MarReactorScreen;
import menu.microwaveoven.MicrowaveOvenScreen;
import menu.playerextend.PlayerExtendScreen;
import menu.powerstationburn.PowerStationBurnScreen;
import menu.registry.MenuRegister;
import menu.reseachtable.ResearchTableScreen;
import menu.rocket.RocketrScreen;
import menu.show.ShowBlockScreen;
import menu.show.itemstack.ShowItemStackScreen;
import menu.stonewasher.StoneWasherScreen;

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
