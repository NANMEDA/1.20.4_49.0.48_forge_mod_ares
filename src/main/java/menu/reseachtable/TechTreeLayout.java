package menu.reseachtable;

import java.util.*;

public class TechTreeLayout {
	static int referenceX = 196;
	static int referenceY = 96;
	static Random r = new Random();
	
	//这里还只是生成应该的xy
	public static void genLayout(Set<TechNode> nodes) {
		Set<TechNode> beginTechs = new HashSet<>();
		List<Integer> existLevel = new ArrayList<>(); 
		Map<Integer, List<TechNode>> levelTech = new HashMap<>();
		
		TechNode.prepare(nodes,beginTechs, existLevel, levelTech);
		if(existLevel.isEmpty()) return;
		
		int offsetY = 0;
		int offsetX = 0;
		int biggestLevelSize = beginTechs.size();
		
		int centerOffsetX = (int) ((((double) beginTechs.size()) - 1.0d)/2.0d * (double) referenceX);
		offsetX = - centerOffsetX;
		for(TechNode tech : beginTechs) {
			//先把初始节点定下来

			if(!tech.havePosition()) {
				tech.setPosition(offsetX, offsetY);
				offsetX+=referenceX;
			}
		}
		
        for (int level : existLevel) {
        	if(level==0) continue;//就是初始节点
        	offsetY+=referenceY;
        	List<TechNode> techNodesAtLevel = levelTech.get(level);
            centerOffsetX = (int) ((((double) techNodesAtLevel.size()) - 1.0d)/2.0d * (double) referenceX);
            offsetX = - centerOffsetX;
            if(biggestLevelSize<techNodesAtLevel.size()) biggestLevelSize=techNodesAtLevel.size();
            // 对每个级别的节点进行排序
            if (techNodesAtLevel != null) {
                techNodesAtLevel.sort((node1, node2) -> {
                    // 计算 node1 和 node2 的 prerequisites 和的大小
                    int sum1 = node1.getPrerequisites().stream().mapToInt(TechNode::getX).sum();
                    int sum2 = node2.getPrerequisites().stream().mapToInt(TechNode::getX).sum();
                    return Integer.compare(sum1, sum2);
                });
            }
            
            for(TechNode t:techNodesAtLevel) {
            	if(level<5) {
            		t.setPosition(t.getX()+offsetX+r.nextInt(-48,48), offsetY+ r.nextInt(-32,32));
            	}else {
            		t.setPosition(t.getX()+offsetX+r.nextInt(-16,16), offsetY);
            	}
            	offsetX+=referenceX;
            }
        }
        
      //最远中心X偏移
        int logestCenterOffsetX = (int) ((((double) biggestLevelSize) - 1.0d)/2.0d * (double) referenceX);
		for(TechNode node : nodes) {
			node.setPosition(node.getX()+logestCenterOffsetX, node.getY());
		}
			
		
	}
}
