package menu.reseachtable;

import menu.ScreenHelper;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import network.NetworkHandler;
import network.client.CStartTech;

public class ResearchTableScreen extends AbstractContainerScreen<ResearchTableMenu> implements ScreenHelper,ResearchTableScreenHelper {

	private enum LAYER{
		TREE,DETAIL
	}
	
	private LAYER layer = LAYER.TREE;
	private boolean isScrolling = false; // 是否正在拖动
	private int dragOffsetX, dragOffsetY; // 拖动的偏移量
	private TechNode chooseNode = null;

	
	public ResearchTableScreen(ResearchTableMenu p_97741_, Inventory p_97742_, Component p_97743_) {
		super(p_97741_, p_97742_, p_97743_);
		this.leftPos = 0;
		this.topPos = 0;
	}

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
		if(this.layer==LAYER.TREE) {
			ResearchTableScreenHelper.renderTechTree(guiGraphics, this.getMenu().getBlockEntity().getTechTree(),this.font,new int[]{this.leftPos,this.topPos});
		}else {
			if(chooseNode==null) {
				this.layer=LAYER.TREE;
			}
			int value = this.getMenu().getBlockEntity().getTime();
			ResearchTableScreenHelper.renderDescription(guiGraphics, value, chooseNode,this.width, this.height, font,
					chooseNode.canUnlock(this.getMenu().getBlockEntity().getTechTree().getUnlocked()),
					this.getMenu().getBlockEntity().isResearching(this.chooseNode.getName()),
					this.getMenu().getBlockEntity().getTechTree().getUnlocked().contains(chooseNode),
					this.getMenu().getInventory()
					);
		}
	}
	
	@Override
	protected void renderTooltip(GuiGraphics p_283594_, int p_282171_, int p_281909_){
		return;
	}
	
	@Override
	protected void renderLabels(GuiGraphics p_281635_, int p_282681_, int p_283686_) {
		return;
	}
	
	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int button) {
	    // 释放鼠标时停止拖动
	    this.isScrolling = false;
	    
	    
	    return super.mouseReleased(mouseX, mouseY, button);
	}

	@Override
	public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
	    if (this.isScrolling&&layer==LAYER.TREE) {
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
	    if (button == 0&&layer==LAYER.TREE) { // 如果是左键点击
	   	 // 假设techtree是一个Techtree实例，包含Set<TechNode>
   		    for (TechNode techNode : this.getMenu().getBlockEntity().getTechTree().getAll()) {
   		        // 获取中心坐标
   		        double centerX = techNode.getX()+this.leftPos;
   		        double centerY = techNode.getY()+this.topPos;
   		        
   		        // 计算方框的边界
   		        double left = centerX - 96 / 2;
   		        double top = centerY - 36 / 2;
   		        double right = centerX + 96 / 2;
   		        double bottom = centerY + 36 / 2;
   		        
   		        // 检查鼠标是否在方框内
   		        if (mouseX >= left && mouseX <= right && mouseY >= top && mouseY <= bottom) {
   		            // 如果鼠标在方框内，返回对应的TechNode
   		        	this.chooseNode=techNode;
   		        	this.layer = LAYER.DETAIL;
   		    		this.leftPos = 0;
   		    		this.topPos = 0;
   		    		return super.mouseClicked(mouseX, mouseY, button);
   		        }
   		    }
	        // 记录当前的偏移量，用来计算拖动
	        dragOffsetX = (int) mouseX - this.leftPos;
	        dragOffsetY = (int) mouseY - this.topPos;
	        this.isScrolling = true; // 开始拖动
	        return true;
	    }else {
	    	if(ScreenHelper.isButton(mouseX, mouseY,
	    			width-standardBgSize[0]/2-16-standardBgSize[0]/4, height-standardBgSize[1]/2-16-standardBgSize[1]/4, 
	    			width-standardBgSize[0]/2-16+standardBgSize[0]/4, height-standardBgSize[1]/2-16+standardBgSize[1]/4))
	    	{
	    		this.layer = LAYER.TREE;
	    		return true;
	    	}else if(ScreenHelper.isButton(mouseX, mouseY,
	    			width/3+width/3-standardBgSize[0]/4, height*2/3-standardBgSize[1]/4, 
	    			width/3+width/3+standardBgSize[0]/4, height*2/3+standardBgSize[1]/4)) 
			{
	    		if(chooseNode.canUnlock(this.getMenu().getBlockEntity().getTechTree().getUnlocked())) {
	    			if(this.getMenu().getBlockEntity().isResearching(this.chooseNode.getName())) return false;
	    			if(chooseNode.getItem()==null||chooseNode.getItem().isEmpty()) {
	                    NetworkHandler.INSTANCE.sendToServer(new CStartTech(this.getMenu().getBlockEntity().getBlockPos(),chooseNode.getName(),chooseNode.getTime()));
	                    return true;
	    			}
	    			boolean[] enough = new boolean[chooseNode.getItem().size()];;
	    			int i = 0;
	    			for(ItemStack need : chooseNode.getItem()) {
	    				int count = 0;
	    				for(ItemStack have : this.getMenu().getInventory().items) {
	    					if(have.getItem().equals(need.getItem())) {
	    						count += have.getCount();
	    						if(count>=need.getCount()) {
	    							enough[i] = true;
	    							i++;
	    							break;
	    						}
	    					}
	    				}
	    			}
	    			for(boolean c : enough) {
	    				if(!c) {
	    					return super.mouseClicked(mouseX, mouseY, button);
	    				}
	    			}
                    NetworkHandler.INSTANCE.sendToServer(new CStartTech(this.getMenu().getBlockEntity().getBlockPos(),chooseNode.getName(),chooseNode.getTime()));
	    			for(ItemStack need : chooseNode.getItem()) {
	    				int count = need.getCount();
	    				for(ItemStack have : this.getMenu().getInventory().items) {
	    					if(have.getItem().equals(need.getItem())) {
	    						int del = Math.min(count, have.getCount());
	    						count-= del;
	    						have.shrink(del);
	    						if(count==0) break;
	    					}
	    				}
	    			}
	    		}
	    			
	    	}
	    }
	    System.out.println("x"+mouseX+"  y"+mouseY);
	    return super.mouseClicked(mouseX, mouseY, button);
	}

   
}
