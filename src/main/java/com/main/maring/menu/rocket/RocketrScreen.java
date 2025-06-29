package com.main.maring.menu.rocket;

import com.main.maring.Maring;
import com.main.maring.menu.ScreenHelper;
import com.main.maring.vehicle.rocket.RocketEntity;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;



public class RocketrScreen extends AbstractContainerScreen<RocketMenu> {

	public static final ResourceLocation GUI = new ResourceLocation(Maring.MODID,"textures/gui/container/rocket_menu.png");
	public static final ResourceLocation FUEL_UI = new ResourceLocation(Maring.MODID,"textures/gui/addon/fuel.png");

	private RocketEntity rocket;

	// 样例燃料条参数（位置请你填空）
	private static final int FUEL_POS_X = 100/** 填写 X 坐标 */;
	private static final int FUEL_POS_Y = 54/** 填写 Y 坐标（底部对齐） */;
	private static final int FUEL_WIDTH = 16;
	private static final int FUEL_HEIGHT = 38;
	private static final int FUEL_UNIT_HEIGHT = 16;

	public RocketrScreen(RocketMenu pMenu, Inventory pInventory, Component pComponent) {
		super(pMenu, pInventory, pComponent);
		this.rocket = pMenu.rocket();
	}

	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(graphics);
		super.render(graphics, mouseX, mouseY, partialTicks);

		int fuelLevel = 100*rocket.getFuel()/rocket.getFuelCapacity(); // 当前燃料量（单位数量）


		// 渲染燃料柱状条
		ScreenHelper.column(
				graphics,
				FUEL_UI,
				leftPos + FUEL_POS_X,
				topPos + FUEL_POS_Y,
				FUEL_WIDTH,
				FUEL_HEIGHT,
				FUEL_UNIT_HEIGHT,
				fuelLevel
		);
	}

	@Override
	protected void renderBg(GuiGraphics pGraphics, float pPartialTick, int pMousex, int pMousey) {
		pGraphics.blit(GUI, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
	}
}
