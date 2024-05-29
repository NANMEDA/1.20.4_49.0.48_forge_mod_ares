package event.listener;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import com.menu.basicmetalmunufactor.BasicMetalManufactorMenu;
import com.menu.basicmetalmunufactor.BasicMetalManufactorScreen;
import com.menu.register.CanfoodMakerScreen;
import com.menu.register.MenuRegister;
import com.menu.register.MicrowaveOvenScreen;
import com.menu.register.PowerStationBurnScreen;

import animal.client.render.JumpSpiderRenderer;
import animal.entity.MonsterRegister;

@Mod.EventBusSubscriber(modid = "maring",bus = Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class ClientListener {
	@SubscribeEvent
	public static void ScreenRegister(FMLClientSetupEvent event) {
		event.enqueueWork(()->MenuScreens.register(MenuRegister.POWERSTATIONBURN_MENU.get(), PowerStationBurnScreen::new));
		
		event.enqueueWork(()->MenuScreens.register(MenuRegister.CANFOODMAKER_MENU.get(), CanfoodMakerScreen::new));
		
		event.enqueueWork(()->MenuScreens.register(MenuRegister.MICROWAVEOVEN_MENU.get(), MicrowaveOvenScreen::new));
		
		event.enqueueWork(()->MenuScreens.register(MenuRegister.BASICMETALMANUFACTOR_MENU.get(), BasicMetalManufactorScreen::new));
		
        event.enqueueWork(()-> EntityRenderers.register(MonsterRegister.JUMP_SPIDER.get(), JumpSpiderRenderer::new)); 
	}
	
}
