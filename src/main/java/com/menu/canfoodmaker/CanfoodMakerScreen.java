package com.menu.canfoodmaker;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;

public class CanfoodMakerScreen extends AbstractContainerScreen<CanfoodMakerMenu> {
	public static final String MODID = "maring";
	public static final ResourceLocation GUI = new ResourceLocation(MODID,"textures/gui/container/canfood_maker.png");
	
	public CanfoodMakerScreen(CanfoodMakerMenu pMenu, Inventory pInventory, Component pComponent) {
		super(pMenu, pInventory, pComponent);
	}

	@Override
	protected void renderBg(GuiGraphics pGraphics, float pPartialTick, int pMousex, int pMousey) {
		pGraphics.blit(GUI,this.leftPos,this.topPos,0,0,this.imageWidth,this.imageHeight);
	}
	
}