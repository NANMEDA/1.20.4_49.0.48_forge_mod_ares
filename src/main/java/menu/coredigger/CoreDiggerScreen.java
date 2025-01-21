package menu.coredigger;


import com.mojang.blaze3d.systems.RenderSystem;

import machine.energy.consumer.coredigger.CoreDiggerEntity;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;

public class CoreDiggerScreen extends AbstractContainerScreen<CoreDiggerMenu> {
	public static final String MODID = "maring";
	public static final ResourceLocation GUI = new ResourceLocation(MODID,"textures/gui/container/core_digger.png");
	
	public CoreDiggerScreen(CoreDiggerMenu pMenu, Inventory pInventory, Component pComponent) {
		super(pMenu, pInventory, pComponent);
	}

	@Override
	protected void renderBg(GuiGraphics pGraphics, float pPartialTick, int pMousex, int pMousey) {     
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, GUI);
		pGraphics.blit(GUI,this.leftPos,this.topPos,0,0,this.imageWidth,this.imageHeight);

		CoreDiggerEntity e = this.getMenu().getBlockEntity();
		if(e.cantFindSucker) { 
			pGraphics.drawString(this.font, Component.translatable("menu.coredigger.cantfindsucker"),133, 25, 0xFFFFFF);
			return;
		}
		if(e.cantTouchCore) pGraphics.drawString(this.font, Component.translatable("menu.coredigger.cantouchcore"),133, 25, 0xFFFFFF);
	}
	
	@Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        //this.button.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }

    
}