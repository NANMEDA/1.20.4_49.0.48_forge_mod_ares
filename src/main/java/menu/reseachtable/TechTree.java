package menu.reseachtable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.minecraft.nbt.CompoundTag;
import util.tech.TechManager;

public class TechTree {
    private Set<TechNode> allTechs; // 所有的科技节点
    private Set<TechNode> unlockedTechs; // 已解锁的科技
    private int[] originalPoint; //初始点
    
    public TechTree(int[] o) {
        this.allTechs = TechManager.getTechNodes();
        this.unlockedTechs = new HashSet<>();
        this.originalPoint = o;
    }
    
    public TechTree(Set<TechNode> unlockedTechs,int[] o) {
        this.allTechs = TechManager.getTechNodes();
        this.unlockedTechs = unlockedTechs;
        this.originalPoint = o;
    }
    
    public int[] getOffset() {
    	return this.originalPoint;
    }
    
    public void SetOffset(int[] offset) {
    	this.originalPoint[0]=offset[0];
    	this.originalPoint[1]=offset[1];
    }
    
    public boolean isEmpty() {
    	return allTechs.size()==0;
    }
    
    public Set<TechNode> getAll() {
    	return this.allTechs;
    }

    public void addTech(TechNode tech) {
        allTechs.add(tech);
    }

    public void unlockTech(TechNode tech) {
        if (tech.canUnlock(unlockedTechs)) {
            unlockedTechs.add(tech);
        } else {
        }
    }
    
    public void unlockAll() {
    	unlockedTechs = new HashSet<>(allTechs); 
    }

    public boolean isTechUnlocked(TechNode tech) {
        return unlockedTechs.contains(tech);
    }

	public Set<TechNode> getUnlocked() {
		return this.unlockedTechs;
	}
    
	
    public CompoundTag saveToNBT() {
        CompoundTag tag = new CompoundTag();

        // 保存 unlockedTechs
        List<CompoundTag> unlockedTechsList = new ArrayList<>();
        for (TechNode techNode : unlockedTechs) {
            CompoundTag techTag = new CompoundTag();
            techTag.putString("name", techNode.getName());  // 使用 getName() 获取标志名
            unlockedTechsList.add(techTag);
        }
        // 将 unlockedTechs 逐个添加到 NBT
        for (int i = 0; i < unlockedTechsList.size(); i++) {
            tag.put("unlockedTech_" + i, unlockedTechsList.get(i));
        }

        // 保存 originalPoint
        List<CompoundTag> originalPointList = new ArrayList<>();
        for (int point : originalPoint) {
            CompoundTag pointTag = new CompoundTag();
            pointTag.putInt("point", point);
            originalPointList.add(pointTag);
        }
        // 将 originalPoint 逐个添加到 NBT
        for (int i = 0; i < originalPointList.size(); i++) {
            tag.put("originalPoint_" + i, originalPointList.get(i));
        }

        return tag;
    }
    
    // 从 NBT 恢复 TechTree 对象
    public static TechTree loadFromNBT(CompoundTag tag) {
        // 恢复 unlockedTechs
        Set<TechNode> unlockedTechs = new HashSet<>();
        List<CompoundTag> unlockedTechsList = new ArrayList<>();
        int i = 0;
        while (tag.contains("unlockedTech_" + i)) {
            unlockedTechsList.add(tag.getCompound("unlockedTech_" + i));
            i++;
        }
        for (CompoundTag techTag : unlockedTechsList) {
            String name = techTag.getString("name");
            unlockedTechs.add(TechNode.fromName(name));  // 假设 TechNode 有 fromName 方法
        }

        // 恢复 originalPoint
        List<CompoundTag> originalPointList = new ArrayList<>();
        int j = 0;
        while (tag.contains("originalPoint_" + j)) {
            originalPointList.add(tag.getCompound("originalPoint_" + j));
            j++;
        }
        int[] originalPoint = new int[originalPointList.size()];
        for (int k = 0; k < originalPointList.size(); k++) {
            originalPoint[k] = originalPointList.get(k).getInt("point");
        }

        return new TechTree(unlockedTechs, originalPoint);
    }
	
}
