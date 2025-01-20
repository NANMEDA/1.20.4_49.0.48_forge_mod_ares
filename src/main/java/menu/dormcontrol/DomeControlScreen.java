package menu.dormcontrol;

import block.entity.neutral.dormcontrol.DomeControlEntity;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.network.PacketDistributor;
import network.NetworkHandler;
import network.client.CDomeControl;
import net.minecraft.resources.ResourceLocation;

public class DomeControlScreen extends AbstractContainerScreen<DomeControlMenu> {
	public static final String MODID = "maring";
	public static final ResourceLocation GUI = new ResourceLocation(MODID,"textures/gui/container/dorm_control.png");
	public static final ResourceLocation O2_UI = new ResourceLocation(MODID,"textures/gui/addon/oxygen.png");
	public static final ResourceLocation H2O_UI = new ResourceLocation(MODID,"textures/gui/addon/water.png");
	
	public static int removeButtonStartPosX = 180;
	public static int removeButtonStartPosY = 127;
	public static int removeButtonWidth = 59;
	public static int removeButtonHeight = 18;
	
	public static int O2StartPosX = 137;
	public static int O2StartPosY = 144;//Buttom
	public static int O2Width = 25;
	public static int O2Height = 120;
	public static int O2unit = 16;
	
	public static int H2OStartPosX = 91;
	public static int H2OStartPosY = 144;//Buttom
	public static int H2OWidth = 25;
	public static int H2OHeight = 120;
	public static int H2Ounit = 16;
	
	public DomeControlScreen(DomeControlMenu pMenu, Inventory pInventory, Component pComponent) {
		super(pMenu, pInventory, pComponent);
		this.imageWidth = 256;
		this.imageHeight = 166;
	}

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(graphics, mouseX, mouseY, partialTicks);
        super.render(graphics, mouseX, mouseY, partialTicks);
        DomeControlEntity blockEntity = (DomeControlEntity) this.getMenu().getBlockEntity();
        int[] O2 = blockEntity.getO2();
        int[] H2O = blockEntity.getH2O();
        int O2Recent = O2[2];
        int H2ORecent = H2O[2];
        
        // 计算柱状图的实际高度
        int H2OactualHeight = (int) (H2OHeight * (H2ORecent / 100.0));
        // 渲染起始位置
        int H2OrenderStartX = H2OStartPosX + leftPos;
        int H2OrenderStartY = H2OStartPosY + topPos - H2OactualHeight; // 从底部向上绘制

        // 渲染柱状图，材质平铺
        for (int x = 0; x < H2OWidth; x += H2Ounit) { // 水平方向平铺
            for (int y = 0; y < H2OactualHeight; y += H2Ounit) { // 垂直方向平铺
                int drawWidth = Math.min(H2Ounit, H2OWidth - x); // 当前绘制的宽度
                int drawHeight = Math.min(H2Ounit, H2OactualHeight - y); // 当前绘制的高度
                graphics.blit(H2O_UI, H2OrenderStartX + x, H2OrenderStartY + y,
                        0, 0, drawWidth, drawHeight,O2unit,O2unit); // 纹理起点坐标和绘制区域大小
            }
        }

        // 计算柱状图的实际高度
        int O2actualHeight = (int) (O2Height * (O2Recent / 100.0));
        // 渲染起始位置
        int O2renderStartX = O2StartPosX + leftPos;
        int O2renderStartY = O2StartPosY + topPos - O2actualHeight; // 从底部向上绘制

        // 渲染柱状图，材质平铺
        for (int x = 0; x < O2Width; x += O2unit) { // 水平方向平铺
            for (int y = 0; y < O2actualHeight; y += O2unit) { // 垂直方向平铺
                int drawWidth = Math.min(O2unit, O2Width - x); // 当前绘制的宽度
                int drawHeight = Math.min(O2unit, O2actualHeight - y); // 当前绘制的高度
                graphics.blit(O2_UI, O2renderStartX + x, O2renderStartY + y,
                        0, 0, drawWidth, drawHeight,H2Ounit,H2Ounit); // 纹理起点坐标和绘制区域大小
            }
        }
        
        
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
    	System.out.println("Mouse clicked at: X = " + mouseX + ", Y = " + mouseY + ", Button = " + button);

        if (isMouseOverRemove(mouseX,mouseY)) {
            DomeControlEntity blockEntity = (DomeControlEntity) this.getMenu().getBlockEntity();
            NetworkHandler.INSTANCE.send(new CDomeControl(blockEntity.getBlockPos()),
                    PacketDistributor.SERVER.noArg());
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }
    
    

	private boolean isMouseOverRemove(double mouseX, double mouseY) {
		return mouseX>this.leftPos+removeButtonStartPosX&&mouseY>this.topPos+removeButtonStartPosY
				&&mouseX<this.leftPos+removeButtonStartPosX+removeButtonWidth&&mouseY<this.topPos+removeButtonStartPosY+40;
	}

}