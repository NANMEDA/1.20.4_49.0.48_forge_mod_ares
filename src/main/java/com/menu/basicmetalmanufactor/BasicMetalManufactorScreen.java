package com.menu.basicmetalmanufactor;

import com.mojang.blaze3d.systems.RenderSystem;

import block.entity.consumer.basicmetalmanufactor.BasicMetalManufactorEntity;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;

public class BasicMetalManufactorScreen extends AbstractContainerScreen<BasicMetalManufactorMenu> {
	public static final String MODID = "maring";
	public static final ResourceLocation GUI = new ResourceLocation(MODID,"textures/gui/container/basicmetal_manufactor.png");
	public static final ResourceLocation GUI_DO = new ResourceLocation(MODID,"textures/gui/container/skyline.png");
	
	public BasicMetalManufactorScreen(BasicMetalManufactorMenu pMenu, Inventory pInventory, Component pComponent) {
		super(pMenu, pInventory, pComponent);
	}

	@Override
	protected void renderBg(GuiGraphics pGraphics, float pPartialTick, int pMousex, int pMousey) {
		
		BasicMetalManufactorEntity blockEntity =  this.getMenu().getBlockEntity();
        int renderDis = blockEntity.getRenderDis();
        //55/36 72/51
        
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, GUI_DO);
        
        int width = 44; // 渲染区域的宽度
        int height = 16;
        int xStart = this.leftPos + 100 - width; // 屏幕上的 X 坐标
        int yStart = this.topPos + 35; // 屏幕上的 Y 坐标
        int u = 0; // 纹理中的 X 坐标
        int v = 0; // 纹理中的 Y 坐标
        pGraphics.blit(GUI_DO, xStart + renderDis, yStart, u, v, width, height);
		
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, GUI);
		
		pGraphics.blit(GUI,this.leftPos,this.topPos,0,0,this.imageWidth,this.imageHeight);
	}
	
}