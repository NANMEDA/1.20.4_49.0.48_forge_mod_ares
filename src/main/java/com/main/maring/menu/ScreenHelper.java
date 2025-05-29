package com.main.maring.menu;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

/**
 * x1,y1,x2,y2传入需要是加上leftpos和toppos
 */
public interface ScreenHelper {

	public enum RenderColor {
	    RED(0xFFFF0000),    	// 红色
	    GREEN(0xFF00FF00),  	// 绿色
	    BLUE(0xFF0000FF),   	// 蓝色
	    YELLOW(0xFFFFFF00), 	// 黄色
	    CYAN(0xFF00FFFF),   	// 青色
	    MAGENTA(0xFFFF00FF),	// 品红
	    WHITE(0xFFFFFFFF),  	// 白色
	    BLACK(0xFF000000),  	// 黑色
		PURPLE(0xFF9821DD), 	// 紫色
		ORANGE(0xFFFFA800); 	// 橙色

	    private final int color;

	    RenderColor(int color) {
	        this.color = color;
	    }

	    public int getColor() {
	        return color;
	    }
	}
	
	static boolean isButton(double mouseX,double mouseY,int x1,int y1,int x2,int y2){
		int t;
		if(x1>x2) {
			t=x1;x1=x2;x2=t;
		}
		if(y1>y2) {
			t=y1;y1=y2;y2=t;
		}
		return mouseX>x1&&mouseY>y1
				&&mouseX<x2&&mouseY<y2;
	}
	
	/**
	 * 垂直
	 * @param startPosX -左下角
	 * @param startPosY -左下角
	 */
	static void column(GuiGraphics graphics,ResourceLocation IN,int startPosX,int startPosY,int Width,int Height,int size,double value) {
		if(IN==null) return;
		// 计算柱状图的实际高度
        int actualHeight = (int) (Height * (value / 100.0));
        int renderStartY = startPosY - actualHeight; // 从底部向上绘制

        // 渲染柱状图，材质平铺
        for (int x = 0; x < Width; x += size) { // 水平方向平铺
            for (int y = 0; y < actualHeight; y += size) { // 垂直方向平铺
                int drawWidth = Math.min(size, Width - x); // 当前绘制的宽度
                int drawHeight = Math.min(size, actualHeight - y); // 当前绘制的高度
                graphics.blit(IN, startPosX + x, renderStartY + y,
                        0, 0, drawWidth, drawHeight,size,size); // 纹理起点坐标和绘制区域大小
            }
        }
	}
	
	
	/**
	 * 垂直
	 * @param startPosX -左下角
	 * @param startPosY -左下角
	 */
	static void column(GuiGraphics graphics,ResourceLocation IN,int startPosX,int startPosY,int Width,int Height,int sizeX,int sizeY,double value) {
		if(IN==null) return;
		// 计算柱状图的实际高度
        int actualHeight = (int) (Height * (value / 100.0));
        int renderStartY = startPosY - actualHeight; // 从左上向下绘制

        // 渲染柱状图，材质平铺
        for (int x = 0; x < Width; x += sizeX) { // 水平方向平铺
            for (int y = 0; y < actualHeight; y += sizeY) { // 垂直方向平铺
                int drawWidth = Math.min(sizeX, Width - x); // 当前绘制的宽度
                int drawHeight = Math.min(sizeY, actualHeight - y); // 当前绘制的高度
                graphics.blit(IN, startPosX + x, renderStartY + y,
                        0, 0, drawWidth, drawHeight,sizeX,sizeY); // 纹理起点坐标和绘制区域大小
            }
        }
	}
	
	/**
	 * 水平
	 * @param startPosX -左下角
	 * @param startPosY -左下角
	 */
	static void bar(GuiGraphics graphics, ResourceLocation IN, int startPosX, int startPosY, int Width, int Height, int size, double value) {
	    if (IN == null) return;
	    // 计算条形图的实际宽度
	    int actualWidth = (int) (Width * (value / 100.0));

	    // 渲染条形图，材质平铺
	    for (int x = 0; x < actualWidth; x += size) { // 水平方向平铺
	        for (int y = 0; y < Height; y += size) { // 垂直方向平铺
	            int drawWidth = Math.min(size, actualWidth - x); // 当前绘制的宽度
	            int drawHeight = Math.min(size, Height - y); // 当前绘制的高度
	            graphics.blit(IN, startPosX + x, startPosY + y,
	                    0, 0, drawWidth, drawHeight, size, size); // 纹理起点坐标和绘制区域大小
	        }
	    }
	}
	

	static void rgbBox(GuiGraphics graphics,int x1,int y1,int x2,int y2,int color) {
		graphics.fill(x1, y1, x2, y2, color);
	}
	
	
	static void rgbBox(GuiGraphics graphics,int x1,int y1,int x2,int y2,RenderColor color) {
		graphics.fill(x1, y1, x2, y2, color.getColor());
	}
	
	
	static void lamp1(GuiGraphics graphics,int x1,int y1,int x2,int y2,int color,boolean light) {
		if(light) {
			rgbBox(graphics, x1, y1, x2, y2, color);
		}
	}
	
	static void lamp1(GuiGraphics graphics,int x1,int y1,int x2,int y2,RenderColor color,boolean light) {
		if(light) {
			rgbBox(graphics, x1, y1, x2, y2, color.getColor());
		}
	}
	
	/**
	 * true -> colora
	 * */
	static void lamp2(GuiGraphics graphics,int x1,int y1,int x2,int y2,int colora,int colorb,boolean select) {
		if(select) {
			rgbBox(graphics, x1, y1, x2, y2, colora);
		}else {
			rgbBox(graphics, x1, y1, x2, y2, colorb);
		}
	}
	
	/**
	 * true -> colora
	 * */
	static void lamp2(GuiGraphics graphics,int x1,int y1,int x2,int y2,RenderColor colora,RenderColor colorb,boolean select) {
		if(select) {
			rgbBox(graphics, x1, y1, x2, y2, colora.getColor());
		}else {
			rgbBox(graphics, x1, y1, x2, y2, colorb.getColor());
		}
	}
	

	/**
	 * 从0开始
	 */
	static void lampn(GuiGraphics graphics,int x1,int y1,int x2,int y2,int[] color,int select) {
		if (select<0||select>=color.length) return;
		rgbBox(graphics, x1, y1, x2, y2, color[select]);
	}
	
	/**
	 * 从0开始
	 */
	static void lampn(GuiGraphics graphics,int x1,int y1,int x2,int y2,RenderColor[] color,int select) {
		if (select<0||select>=color.length) return;
		rgbBox(graphics, x1, y1, x2, y2, color[select].getColor());
	}
	
	
	/**
	 * 需要将图上的像素信息保存，
	 * 然后每次更新重绘
	 * */
	static void lineChart(GuiGraphics graphics,int x1,int y1,int x2,int y2,int upValue,int buttomValue,int color,int[] value) {
	    int n = value.length; // 数据点的数量
	    if(n==0) return;
	    // 计算 y 值的比例，映射到图表的高度
	    float yScale = (float)(y2 - y1) / (upValue - buttomValue);
	    // 计算每个数据点的 x 坐标间隔
	    float xInterval = (float)(x2 - x1) / (n - 1);
	    
	    // 绘制折线
	    for (int i = 0; i < n - 1; i++) {
	        // 计算当前数据点和下一个数据点的坐标
	        int x1Data = (int)(x1 + i * xInterval);
	        int y1Data = (int)(y2 - (value[i] - buttomValue) * yScale);
	        int x2Data = (int)(x1 + (i + 1) * xInterval);
	        int y2Data = (int)(y2 - (value[i + 1] - buttomValue) * yScale);

	        // 绘制线段
	        //graphics.drawLine(x1Data, y1Data, x2Data, y2Data, color);
	    }

	}
	
	
	
}
