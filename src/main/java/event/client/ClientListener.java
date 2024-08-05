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

import com.menu.advancedmetalmanufactor.AdvancedMetalManufactorScreen;
import com.menu.basicmetalmanufactor.BasicMetalManufactorScreen;
import com.menu.blueprintbuilder.BlueprintBuilderScreen;
import com.menu.canfoodmaker.CanfoodMakerScreen;
import com.menu.etchingmachine.EtchingMachineScreen;
import com.menu.fuelrefiner.FuelRefinerScreen;
import com.menu.microwaveoven.MicrowaveOvenScreen;
import com.menu.playerextend.PlayerExtendScreen;
import com.menu.powerstationburn.PowerStationBurnScreen;
import com.menu.register.MenuRegister;
import com.menu.reseachtable.ResearchTableScreen;
import com.menu.show.ShowBlockScreen;
import com.menu.show.itemstack.ShowItemStackScreen;
import com.menu.stonewasher.StoneWasherScreen;

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
import block.entity.consumer.microwaveoven.MicrowaveOvenEntityRender;
import block.entity.consumer.stonewasher.StoneWasherEntityRender;
import block.entity.consumer.watergather.WaterGatherEntityRender;
import block.entity.neutral.crystalbuilder.CrystalBuilderEntityRender;
import block.entity.neutral.researchtable.ResearchTableEntityRender;

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
		
		
		
        event.enqueueWork(()-> EntityRenderers.register(MonsterRegister.JUMP_SPIDER.get(), JumpSpiderRenderer::new)); 
        
        event.enqueueWork(()-> EntityRenderers.register(VehicleRegister.ROCKET_ENTITY.get(), RocketTier1Renderer::new)); 
	}
	
	@SubscribeEvent
	public static void registerBlockEntityRender(EntityRenderersEvent.RegisterRenderers event) {
		event.registerBlockEntityRenderer(BlockEntityRegister.microwaveoven_BLOCKENTITY.get(), MicrowaveOvenEntityRender::new);
		
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

	}
	
	@SubscribeEvent
    public static void registerEntityLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(JumpSpiderModel.LAYER_LOCATION,JumpSpiderModel::createBodyLayer);
        
        event.registerLayerDefinition(RocketModel.LAYER_LOCATION,RocketModel::createBodyLayer);
    } 
	
}
