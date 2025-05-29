package com.main.maring.menu.canfoodmaker;

import com.mojang.blaze3d.systems.RenderSystem;

import com.main.maring.block.entity.consumer.canfoodmaker.CanfoodMakerEntity;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;

public class CanfoodMakerScreen extends AbstractContainerScreen<CanfoodMakerMenu> {
	public static final String MODID = "maring";
	public static final ResourceLocation GUI = new ResourceLocation(MODID,"textures/gui/container/canfood_maker.png");
	public static final ResourceLocation GUI_DO = new ResourceLocation(MODID,"textures/gui/container/vertical.png");
	
	public CanfoodMakerScreen(CanfoodMakerMenu pMenu, Inventory pInventory, Component pComponent) {
		super(pMenu, pInventory, pComponent);
	}

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(graphics, mouseX, mouseY);

    }
	
	@Override
	protected void renderBg(GuiGraphics pGraphics, float pPartialTick, int pMousex, int pMousey) {
        
        CanfoodMakerEntity blockEntity = (CanfoodMakerEntity) this.getMenu().getBlockEntity();
        int renderHeight = blockEntity.getRenderHeight();
        int pic_width = 18;
        //55/36 72/51
        
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, GUI_DO);
        
        int xStart = this.leftPos + 55; // 屏幕上的 X 坐标
        int yStart = this.topPos + 36-15; // 屏幕上的 Y 坐标
        int u = 0; // 纹理中的 X 坐标
        int v = 0; // 纹理中的 Y 坐标
        int width = 18; // 渲染区域的宽度
        pGraphics.blit(GUI_DO, xStart, yStart+renderHeight, u, v, pic_width, 15);
		
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, GUI);
		pGraphics.blit(GUI,this.leftPos,this.topPos,0,0,this.imageWidth,this.imageHeight);
	}
	
}