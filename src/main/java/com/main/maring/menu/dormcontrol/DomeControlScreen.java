package com.main.maring.menu.dormcontrol;

import com.main.maring.Maring;
import com.main.maring.block.entity.neutral.fastbuild.dormcontrol.DomeControlEntity;
import com.main.maring.menu.ScreenHelper;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import com.main.maring.network.NetworkHandler;
import com.main.maring.network.client.CDomeControl;
import net.minecraft.resources.ResourceLocation;

public class DomeControlScreen extends AbstractContainerScreen<DomeControlMenu> {
	public static final ResourceLocation GUI = new ResourceLocation(Maring.MODID,"textures/gui/container/dorm_control.png");
	public static final ResourceLocation O2_UI = new ResourceLocation(Maring.MODID,"textures/gui/addon/oxygen.png");
	public static final ResourceLocation H2O_UI = new ResourceLocation(Maring.MODID,"textures/gui/addon/water.png");
	
	private static int removeButtonStartPosX = 180;
	private static int removeButtonStartPosY = 127;
	private static int removeButtonWidth = 59;
	private static int removeButtonHeight = 18;
	
	private static int O2StartPosX = 137;
	private static int O2StartPosY = 144;//Buttom
	private static int O2Width = 25;
	private static int O2Height = 120;
	private static int O2unit = 16;
	
	private static int H2OStartPosX = 91;
	private static int H2OStartPosY = 144;//Buttom
	private static int H2OWidth = 25;
	private static int H2OHeight = 120;
	private static int H2Ounit = 16;
	
	public DomeControlScreen(DomeControlMenu pMenu, Inventory pInventory, Component pComponent) {
		super(pMenu, pInventory, pComponent);
		this.imageWidth = 256;
		this.imageHeight = 166;
	}

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTicks);
        DomeControlEntity blockEntity = (DomeControlEntity) this.getMenu().getBlockEntity();
        int[] O2 = blockEntity.getO2();
        int[] H2O = blockEntity.getH2O();
        int O2Recent = O2[2];
        int H2ORecent = H2O[2];
        
        ScreenHelper.column(graphics, H2O_UI, H2OStartPosX + leftPos, H2OStartPosY + topPos, H2OWidth, H2OHeight, H2Ounit, H2ORecent);
        ScreenHelper.column(graphics, O2_UI, O2StartPosX + leftPos, O2StartPosY + topPos, O2Width, O2Height, O2unit, O2Recent);
    }
	
	@Override
	protected void renderBg(GuiGraphics pGraphics, float pPartialTick, int pMousex, int pMousey) {
		pGraphics.blit(GUI,this.leftPos,this.topPos,0,0,this.imageWidth,this.imageHeight);
	}
	
    @Override
    protected void renderTooltip(GuiGraphics guiGraphics, int mouseX, int mouseY) {
    }
	
    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.drawString(this.font, this.title.getString(), this.titleLabelX, this.titleLabelY, 0xFFFFFF);
        guiGraphics.drawString(
        	    this.font, 
        	    Component.translatable("menu.domecontrol.o2"), 
        	    O2StartPosX  + O2Width / 2 - this.font.width(Component.translatable("menu.domecontrol.o2")) / 2, 
        	    O2StartPosY  + 5, // 5 像素的间距
        	    0xFFFFFF
        	);
        
        guiGraphics.drawString(
        	    this.font, 
        	    Component.translatable("menu.domecontrol.h2o"), 
        	    H2OStartPosX  + H2OWidth / 2 - this.font.width(Component.translatable("menu.domecontrol.h2o")) / 2, 
        	    H2OStartPosY  + 5, // 5 像素的间距
        	    0xFFFFFF
        	);
        guiGraphics.drawString(
        	    this.font, 
        	    Component.translatable("menu.domecontrol.remove"), 
        	    removeButtonStartPosX  + removeButtonWidth / 2 - this.font.width(Component.translatable("menu.domecontrol.remove")) / 2, 
        	    removeButtonStartPosY + removeButtonHeight/2 -5, // 5 像素的间距
        	    0xFF0000
        	);
        
    }
    
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
    	
        if (ScreenHelper.isButton(mouseX, mouseY, 
        		this.leftPos+removeButtonStartPosX, this.leftPos+removeButtonStartPosX+removeButtonWidth, 
        		this.topPos+removeButtonStartPosY, this.topPos+removeButtonStartPosY+40)) {
            DomeControlEntity blockEntity = (DomeControlEntity) this.getMenu().getBlockEntity();
            NetworkHandler.INSTANCE.sendToServer(new CDomeControl(blockEntity.getBlockPos()));
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

}