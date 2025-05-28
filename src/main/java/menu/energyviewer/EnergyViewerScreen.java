package menu.energyviewer;

import machine.energy.viewer.EnergyViewerEntity;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import util.net.EnergyNet;
import util.net.EnergyNetProcess;
import net.minecraft.resources.ResourceLocation;

public class EnergyViewerScreen extends AbstractContainerScreen<EnergyViewerMenu> {
	public static final String MODID = "maring";
	public static final ResourceLocation GUI = new ResourceLocation(MODID,"textures/gui/container/energy_viewer.png");
	
	public EnergyViewerScreen(EnergyViewerMenu pMenu, Inventory pInventory, Component pComponent) {
		super(pMenu, pInventory, pComponent);
		
	}

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(graphics, mouseX, mouseY);
        EnergyViewerEntity blockEntity = (EnergyViewerEntity) this.getMenu().getBlockEntity();
        
        long[] information = blockEntity.getInformation();
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        int x_slice = this.imageWidth/4;
        int y_slice = 30;
        if(blockEntity.haveNet()) {
        	EnergyNet net = EnergyNetProcess.getEnergyNet(blockEntity.getNet());
        	graphics.drawString(this.font, Component.translatable("menu.energyviewer.supplylevel",net.getSupplyLevel()),x+x_slice, y+4*y_slice, 0xFFFFFF);
        }
        graphics.drawString(this.font, Component.translatable("menu.energyviewer.supply",information[1]),x+x_slice, y+y_slice, 0xFFFFFF);
        graphics.drawString(this.font, Component.translatable("menu.energyviewer.consume",information[2]),x+x_slice, y+2*y_slice, 0xFFFFFF);
        if(information[3]>=0) {
        	graphics.drawString(this.font, Component.translatable("menu.energyviewer.more",information[3]),x+x_slice, y+3*y_slice, 0xFFFFFF);
        }else {
        	graphics.drawString(this.font, Component.translatable("menu.energyviewer.dificit",information[3]),x+x_slice, y+3*y_slice, 0xFFFFFF);
        }
        
    	graphics.drawString(this.font, Component.translatable("menu.energyviewer.cap",information[5]),x+2*x_slice, y+y_slice, 0xFFFFFF);
    	graphics.drawString(this.font, Component.translatable("menu.energyviewer.storage",information[4]),x+2*x_slice, y+2*y_slice, 0xFFFFFF);
    	graphics.drawString(this.font, Component.translatable("menu.energyviewer.empty",information[6]),x+2*x_slice, y+3*y_slice, 0xFFFFFF);
    }
	
	@Override
	protected void renderBg(GuiGraphics pGraphics, float pPartialTick, int pMousex, int pMousey) {
		pGraphics.blit(GUI,this.leftPos,this.topPos,0,0,this.imageWidth,this.imageHeight);
	}
	
    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.drawString(this.font, this.title.getString(), this.titleLabelX, this.titleLabelY, 0xFFFFFF);
    }
	
}