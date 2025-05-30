package com.main.maring.menu.rocket;

import com.main.maring.Maring;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;

public class RocketrScreen extends AbstractContainerScreen<RocketMenu> {

	public static final ResourceLocation GUI = new ResourceLocation(Maring.MODID,"textures/gui/container/advancedmetal_manufactor.png");
	public static final ResourceLocation GUI_DO = new ResourceLocation(Maring.MODID,"textures/gui/container/skyline.png");
	
	public RocketrScreen(RocketMenu pMenu, Inventory pInventory, Component pComponent) {
		super(pMenu, pInventory, pComponent);
	}

	@Override
	protected void renderBg(GuiGraphics pGraphics, float pPartialTick, int pMousex, int pMousey) {

        //55/36 72/51
        
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, GUI_DO);
        
        int width = 44; // 渲染区域的宽度
        int height = 16;
        int xStart = this.leftPos + 100 - width; // 屏幕上的 X 坐标
        int yStart = this.topPos + 35; // 屏幕上的 Y 坐标
        int u = 0; // 纹理中的 X 坐标
        int v = 0; // 纹理中的 Y 坐标
        pGraphics.blit(GUI_DO, xStart + 0, yStart, u, v, width, height);
		
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, GUI);
		pGraphics.blit(GUI,this.leftPos,this.topPos,0,0,this.imageWidth,this.imageHeight);
	}
}
