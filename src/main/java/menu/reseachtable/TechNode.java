package menu.reseachtable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.minecraft.resources.ResourceLocation;
import util.tech.TechManager;

public class TechNode {
	static final String MODID = "maring";
    private String name; // 科技名称
    private String description; // 科技描述
    private Set<TechNode> prerequisites; // 需要解锁的前置科技
    private Set<TechNode> point; // 指向
    private Set<TechNode> conflict; // 冲突科技
    private boolean isBegin; // 是否是起始科技
    private boolean isEnd; // 是否是结束科技
    private int referenceLevel; // 参考级别，用于排序等用途,!!必须是线性的，尽量避免出现某个level需要后面的level做前置
    private ResourceLocation UI;
    private int x; // 渲染时的 X 坐标
    private int y; // 渲染时的 Y 坐标

    /**
     * add Begin Node
     * */
    public TechNode(String name, String description, ResourceLocation ui) {
        this.name = name;
        this.description = description;
        this.prerequisites = new HashSet<>();
        this.point = new HashSet<>();
        this.conflict = new HashSet<>();
        this.isBegin = true;
        this.isEnd = false;
        this.referenceLevel = 0;
        if(ui==null) { 
        	this.UI = new ResourceLocation(MODID, "textures/gui/addon/tech_ui_default.png");
        }else {
        	this.UI = ui;
        }
        this.x = -1;
        this.y = -1;
    }
    
    /**
     * 
     * */
    public TechNode(String name, String description, boolean isEnd, int referenceLevel,ResourceLocation ui) {
        this.name = name;
        this.description = description;
        this.prerequisites = new HashSet<>();
        this.point = new HashSet<>();
        this.conflict = new HashSet<>();
        this.isBegin = false;
        this.isEnd = isEnd;
        this.referenceLevel = referenceLevel;
        if(ui==null) { 
        	this.UI = new ResourceLocation(MODID, "textures/gui/addon/tech_ui_default.png");
        }else {
        	this.UI = ui;
        }
        this.x = -1;
        this.y = -1;
    }
    
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public int[] getPosition() {
    	return new int[] {x,y};
    }
    
    public int[] getPosition(int[] offset) {
    	return new int[] {offset[0]+x,offset[1]+y};
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Set<TechNode> getPrerequisites() {
        return prerequisites;
    }

    // 添加前置科技，并且保证指向与前置科技的对称关系
    public void addPrerequisite(TechNode prerequisite) {
        prerequisites.add(prerequisite);
        prerequisite.addPoint(this); // 对称操作
    }

    public Set<TechNode> getConflict() {
        return conflict;
    }

    public void addConflict(TechNode conflictingTech) {
        conflict.add(conflictingTech);
    }
    
    public void setUI(ResourceLocation ui) {
    	this.UI = ui;
    }
    
    public ResourceLocation ui() {
    	return UI;
    }

    public boolean canUnlock(Set<TechNode> unlockedTechs) {
        // 判断当前科技是否可以解锁，确保满足前置条件并且没有冲突
        return prerequisites.isEmpty()||( unlockedTechs.containsAll(prerequisites) && !unlockedTechs.stream().anyMatch(conflict::contains));
    }

    @Override
    public String toString() {
        return name;
    }

    // 获取是否为起始科技
    public boolean isBegin() {
        return isBegin;
    }

    public void setBegin(boolean begin) {
        isBegin = begin;
    }

    // 获取是否为结束科技
    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd(boolean end) {
        isEnd = end;
    }

    public int getReferenceLevel() {
        return referenceLevel;
    }

    public void setReferenceLevel(int referenceLevel) {
        this.referenceLevel = referenceLevel;
    }

    /**
     * 没有对称,对称在addPrerequisite
     * */
    public void addPoint(TechNode target) {
        point.add(target);
        //target.addPrerequisite(this); // 对称操作
    }

    public Set<TechNode> getPoint() {
        return point;
    }

	public boolean havePosition() {
		return this.x>0&&this.y>0;
	}
	
	public static TechNode fromName(String name) {
		if(TechManager.findTechNodeByName(name)!=null) {
			return TechManager.findTechNodeByName(name);
		}else {
			return null;
		}
	}
	

    public static void prepare(Set<TechNode> allTechs,Set<TechNode> beginTechs, List<Integer> existLevel, Map<Integer, List<TechNode>> levelTech) {
        for (TechNode tech : allTechs) {
            // 收集所有 "isBegin" 的节点
            if (tech.isBegin()) {
                beginTechs.add(tech);
            }
            
            int level = tech.getReferenceLevel();
            if (!existLevel.contains(level)) {
                existLevel.add(level); // 添加唯一的 referenceLevel
            }
            
            levelTech.computeIfAbsent(level, k -> new ArrayList<>()).add(tech);
        }
        Collections.sort(existLevel);
    }
}