package menu.reseachtable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import menu.ScreenHelper;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

/**
 * x1,y1,x2,y2传入需要是加上leftpos和toppos
 */
public interface ResearchTableScreenHelper {
	
	static final String MODID = "maring";
	
	static int[] standardBgSize = {96,36};
	static int[] standardUISize = {32,32};
	static int[] standardUIOffset = {-standardBgSize[0]/2+standardUISize[0]/2+2,0};
	
	static int[] standardTextOffset = {standardUIOffset[0]+standardBgSize[0]/2-2,-4};
	
	//x4y16 "|" "——"
	static final ResourceLocation LINE1 = new ResourceLocation(MODID, "textures/gui/addon/line0.png");
	static final ResourceLocation LINE_ = new ResourceLocation(MODID, "textures/gui/addon/line1.png");
	
	//x16y16 like "L""""""" rotate clockly
	static final ResourceLocation LINE_CORNER0 = new ResourceLocation(MODID, "textures/gui/addon/line_c0.png");
	static final ResourceLocation LINE_CORNER1 = new ResourceLocation(MODID, "textures/gui/addon/line_c1.png");
	static final ResourceLocation LINE_CORNER2 = new ResourceLocation(MODID, "textures/gui/addon/line_c2.png");
	static final ResourceLocation LINE_CORNER3 = new ResourceLocation(MODID, "textures/gui/addon/line_c3.png");
	
	//x16y16 "point dovvn/up"
	static final ResourceLocation LINE_TIPD = new ResourceLocation(MODID, "textures/gui/addon/line_td.png");
	static final ResourceLocation LINE_TIPU = new ResourceLocation(MODID, "textures/gui/addon/line_tu.png");
	
	static final ResourceLocation STANDARD_TECH_BG_G = new ResourceLocation(MODID, "textures/gui/addon/tech_bg_g.png");
	static final ResourceLocation STANDARD_TECH_BG_Y = new ResourceLocation(MODID, "textures/gui/addon/tech_bg_y.png");
	static final ResourceLocation STANDARD_TECH_BG_R = new ResourceLocation(MODID, "textures/gui/addon/tech_bg_r.png");
	
	
	static void connection(GuiGraphics graphics,int[] start, int[] end) {
		if(end.length!=2||start.length!=2) return;
		if(Math.abs(end[0]-start[0])<32||Math.abs(end[1]-start[1])<32) return;
		
		if(end[1]>start[1]) {
			graphics.blit(LINE_TIPD, end[0] - 8, end[1]-8,
                0, 0, 16, 16,16,16);// 画箭头向下
			end[1] -= 8;
		}else {
			graphics.blit(LINE_TIPU, end[0] + 8, end[1] +8,
	                0, 0, 16, 16,16,16);// 画箭头向上
			end[1] += 8;
		}
		
		//保证start在上面
	    if (start[1] > end[1]) {
	        int[] temp = start;
	        start = end;
	        end = temp;
	    }
	    
	    int[] left, right;
	    left = new int[2];
	    right = new int[2];
	    // 现在 start 和 end 就是线段两端点
	    if (start[0] < end[0]) {
	        // 👉
	        left[0] = start[0];
	        left[1] = (start[1] + end[1]) / 2;
	        right[0] = end[0];
	        right[1] = left[1]; 
	        //也是端点
	        
			graphics.blit(LINE_CORNER0, left[0]-2, left[1] - 14,
	                0, 0, 16, 16,16,16);
			graphics.blit(LINE_CORNER2, right[0]-14, right[1]-2,
	                0, 0, 16, 16,16,16);
			
			ScreenHelper.column(graphics,LINE1,start[0]-2,left[1]-14,4,left[1]-14-start[1],4,16,100.0); 
			ScreenHelper.column(graphics,LINE1,end[0]-2,end[1],4,left[1]-14-start[1],4,16,100.0); 
			ScreenHelper.column(graphics,LINE_,left[0]+14,left[1]+2,right[0]-14-(left[0]+14),4,16,4,100.0); 
			
	    } else {
	        // 👈
	        left[0] = end[0];
	        left[1] = (start[1] + end[1]) / 2;
	        right[0] = start[0];
	        right[1] = left[1];
	        
			graphics.blit(LINE_CORNER1, left[0]-2, left[1]-2,
	                0, 0, 16, 16,16,16);
			graphics.blit(LINE_CORNER3, right[0]-14, right[1]-14,
	                0, 0, 16, 16,16,16);
			
			//从左到右
			ScreenHelper.column(graphics,LINE1,end[0]-2,end[1],4,-(left[1]+14)+end[1],4,16,100.0); 
			ScreenHelper.column(graphics,LINE1,start[0]-2,right[1]-14,4,right[1]-14-start[1],4,16,100.0); 
			ScreenHelper.column(graphics,LINE_,left[0]+14,left[1]+2,right[0]-14-(left[0]+14),4,16,4,100.0); 
	    }
	}
	
	/**
	 * ↖到↘
	 * */
	private static void logo(GuiGraphics graphics,int[] center,int width, int height,ResourceLocation IN) {
        graphics.blit(IN, center[0]-width/2, center[1]-height/2,
                0, 0, width, height,width,height); // 纹理起点坐标和绘制区域大小
	}
	
	/**
	 * ↖到↘
	 * */
	private static void logo(GuiGraphics graphics,int[] from,int[] to,int width, int height,ResourceLocation IN) {
        graphics.blit(IN, from[0], from[1],
                0, 0, width, height,to[0]-from[0],to[1]-from[1]); // 纹理起点坐标和绘制区域大小
	}
	
	static void renderTechTree(GuiGraphics graphics,TechTree tree,Font font,int[] offset) {
		if(tree.isEmpty()) return;
		tree.SetOffset(offset);
		for(TechNode techNode : tree.getAll()) {
			renderTechNode(graphics, techNode,tree.isTechUnlocked(techNode),techNode.canUnlock(tree.getUnlocked()),font, tree.getOffset());
		}
	}
	
	private static void renderTechNode(GuiGraphics graphics,TechNode node,boolean isUnlocked,boolean canBeUnlocked,Font font,int[] offset) {
		if(isUnlocked) {
			logo(graphics,node.getPosition(offset),standardBgSize[0],standardBgSize[1],STANDARD_TECH_BG_G);
		}else {
			if(canBeUnlocked) {
				logo(graphics,node.getPosition(offset),standardBgSize[0],standardBgSize[1],STANDARD_TECH_BG_Y);
			}else {
				logo(graphics,node.getPosition(offset),standardBgSize[0],standardBgSize[1],STANDARD_TECH_BG_R);
			}
		}
		logo(graphics,node.getPosition(new int[] {offset[0]+ standardUIOffset[0],offset[1]+ standardUIOffset[1]})
				,standardUISize[0],standardUISize[1],node.ui());
		String name = Component.translatable(node.getName()+".name").getString();
		graphics.drawString(font, name,
				node.getX()+offset[0]+standardTextOffset[0]-font.width(name)/2,node.getY()+offset[1]+standardTextOffset[1], 
				16777215, false);
		renderPointConnections(graphics, node, offset);
	}
	
	private static void renderPointConnections(GuiGraphics graphics,TechNode node,int[] offset) {
		int[] from = new int[] {node.getX(),node.getY()+standardBgSize[1]/2};
		for(TechNode toNode : node.getPoint()) {
			int[] to = askForBestPoint(from, toNode);
			connection(graphics, add(from,offset), add(to,offset));
		}
	}
	
	private static int[] askForBestPoint(int[] from,TechNode askNode) {
	    List<int[]> possiblePoints = provideAcceptPoint(askNode);
	    
	    int[] bestPoint = possiblePoints.get(0);
	    int minXDiff = Integer.MAX_VALUE;
	    
	    // 遍历所有的目标点
	    for (int[] point : possiblePoints) {
	        int xDiff = Math.abs(from[0] - point[0]);  // 计算当前点的 x 差距
	        
	        if (xDiff > 32 && xDiff < minXDiff) {
	            minXDiff = xDiff;
	            bestPoint = point;  // 更新最优点
	        }
	    }
	    return bestPoint;  // 返回最优点
	}
	
	private static List<int[]> provideAcceptPoint(TechNode askNode) {
		List<int[]> o = new ArrayList<int[]>() ;
		o.add(new int[] {askNode.getX(),askNode.getY()-standardBgSize[1]/2-14});
		o.add(new int[] {askNode.getX()-standardBgSize[0]/4,askNode.getY()-standardBgSize[1]/2-7});
		o.add(new int[] {askNode.getX()-standardBgSize[0]/3,askNode.getY()-standardBgSize[1]/2-7});
		o.add(new int[] {askNode.getX()+standardBgSize[0]/4,askNode.getY()-standardBgSize[1]/2-7});
		o.add(new int[] {askNode.getX()+standardBgSize[0]/3,askNode.getY()-standardBgSize[1]/2-7});
		return o;
	}
	
	private static int[] add(int[] array1, int[] array2) {
	    int[] result = new int[array1.length];
	    for (int i = 0; i < array1.length; i++) {
	        result[i] = array1[i] + array2[i];
	    }
	    
	    return result;
	}
	
}
