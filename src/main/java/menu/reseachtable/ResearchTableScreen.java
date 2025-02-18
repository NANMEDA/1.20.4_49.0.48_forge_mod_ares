package menu.reseachtable;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class ResearchTableScreen extends AbstractContainerScreen<ResearchTableMenu> {

	private boolean isScrolling = false; // 是否正在拖动
	private int dragOffsetX, dragOffsetY; // 拖动的偏移量

	
	public ResearchTableScreen(ResearchTableMenu p_97741_, Inventory p_97742_, Component p_97743_) {
		super(p_97741_, p_97742_, p_97743_);
	}

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
		ResearchTableScreenHelper.renderTechTree(guiGraphics, this.getMenu().getBlockEntity().getTechTree(),this.font,new int[]{this.leftPos,this.topPos});
			
	}
	
	@Override
	protected void renderTooltip(GuiGraphics p_283594_, int p_282171_, int p_281909_){
		return;
	}
	
	@Override
	protected void renderLabels(GuiGraphics p_281635_, int p_282681_, int p_283686_) {
		return;
	}
	
	/**
	 * 在这里检测是不是激活某个UI
	 * */
	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int button) {
	    // 释放鼠标时停止拖动
	    this.isScrolling = false;
	    
	    
	    
	    return super.mouseReleased(mouseX, mouseY, button);
	}

	@Override
	public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
	    if (this.isScrolling) {
	        // 计算拖动时的偏移量
	        int offsetX = (int) (mouseX - dragOffsetX);
	        int offsetY = (int) (mouseY - dragOffsetY);

	        // 更新窗口的位置
	        this.leftPos = offsetX;
	        this.topPos = offsetY;
	        return true;
	    }
	    return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
	    if (button == 0) { // 如果是左键点击
	        // 记录当前的偏移量，用来计算拖动
	        dragOffsetX = (int) mouseX - this.leftPos;
	        dragOffsetY = (int) mouseY - this.topPos;
	        this.isScrolling = true; // 开始拖动
	        return true;
	    }
	    System.out.println("x"+mouseX+"  y"+mouseY);
	    return super.mouseClicked(mouseX, mouseY, button);
	}
	
	
   
}
