package event.listener;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import com.menu.advancedmetalmanufactor.AdvancedMetalManufactorScreen;
import com.menu.basicmetalmanufactor.BasicMetalManufactorScreen;
import com.menu.canfoodmaker.CanfoodMakerScreen;
import com.menu.etchingmachine.EtchingMachineScreen;
import com.menu.microwaveoven.MicrowaveOvenScreen;
import com.menu.playerextend.PlayerExtendScreen;
import com.menu.powerstationburn.PowerStationBurnScreen;
import com.menu.register.MenuRegister;

import animal.client.model.JumpSpiderModel;
import animal.client.render.JumpSpiderRenderer;
import animal.entity.MonsterRegister;
import block.entity.BlockEntityRegister;
import block.entity.consumer.advancedmetalmanufactor.AdvancedMetalManufactorEntity;
import block.entity.consumer.advancedmetalmanufactor.AdvancedMetalManufactorEntityRender;
import block.entity.consumer.basicmetalmanufactor.BasicMetalManufactorEntityRender;
import block.entity.consumer.bioplasticbuilder.BioplasticBuilderEntity;
import block.entity.consumer.bioplasticbuilder.BioplasticBuilderEntityRender;
import block.entity.consumer.etchingmachine.EtchingMachineEntityRender;
import block.entity.consumer.glassbuilder.GlassBuilderEntity;
import block.entity.consumer.glassbuilder.GlassBuilderEntityRender;
import block.entity.consumer.microwaveoven.MicrowaveOvenEntityRender;
import block.entity.consumer.watergather.WaterGatherEntity;
import block.entity.consumer.watergather.WaterGatherEntityRender;
import block.entity.neutral.crystalbuilder.CrystalBuilderEntity;
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
		
		event.enqueueWork(()->MenuScreens.register(MenuRegister.PLAYEREXTEND_MENU.get(), PlayerExtendScreen::new));
		
		
		
        event.enqueueWork(()-> EntityRenderers.register(MonsterRegister.JUMP_SPIDER.get(), JumpSpiderRenderer::new)); 
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

	}
	
	@SubscribeEvent
    public static void registerEntityLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(JumpSpiderModel.LAYER_LOCATION,JumpSpiderModel::createBodyLayer);
    } 
	
}
