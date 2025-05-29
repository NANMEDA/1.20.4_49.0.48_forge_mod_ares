package com.main.maring.machine.energy;

import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import com.main.maring.util.net.EnergyNet;
import com.main.maring.util.net.EnergyNetProcess;

public abstract class EnergyEntity extends BlockEntity{
    protected int FULL_ENERGY;
    protected long NET;
    protected Map<BlockPos, Boolean> connectMap;
    protected boolean isDirty = false;
	
	public EnergyEntity(BlockEntityType<?> p_155228_, BlockPos p_155229_, BlockState p_155230_) {
		super(p_155228_, p_155229_, p_155230_);
		this.NET = 0;
		this.connectMap = new HashMap<>();

	}
	
	/**
	 * 决定了是否渲染导线，在导线渲染层面，只有双方都渲染导线，才会有导线渲染
	 */
    public boolean lineVisible() {
    	return true;
    }
    
    public boolean isConnectable() {
    	return true;
    }
    
    public Map<BlockPos, Boolean> getConnections() {
    	return this.connectMap;
    }
    
    public void addConnection(BlockPos pos,boolean t) {
    	this.connectMap.put(pos,t);
    	this.isDirty = true;
    	this.setChanged();
    }
    
    public static void addMutualConnection(EnergyEntity e1,BlockPos p1,EnergyEntity e2,BlockPos p2,boolean t) {
    	e1.addConnection(p2,t);
    	e2.addConnection(p1,t);
    }
    public static void addMutualConnection(EnergyEntity e1,EnergyEntity e2) {
    	addMutualConnection(e1,e1.getBlockPos(),e2,e2.getBlockPos(),(e1.isConnectable()&&e2.isConnectable()));
    }
    
    public void removeConnection(BlockPos pos) {
    	this.connectMap.remove(pos);
    	this.isDirty = true;
    	this.setChanged();
    }
    
    public static void removeMutualConnection(EnergyEntity e1,BlockPos p1,EnergyEntity e2,BlockPos p2) {
    	e1.removeConnection(p2);
    	e2.removeConnection(p1);
    }
    public static void removeMutualConnection(EnergyEntity e1,EnergyEntity e2) {
    	removeMutualConnection(e1,e1.getBlockPos(),e2,e2.getBlockPos());
    }
    
    public void removeAllrelativeConnection() {
    	Level level = this.getLevel();
    	if(level.isClientSide()) return;
    	for(BlockPos pos : this.connectMap.keySet()) {
    		if(level.getBlockEntity(pos) instanceof EnergyEntity e) {
    			e.removeConnection(this.worldPosition);
    		}
    	}
    	this.connectMap.clear();
    }
    
	public long getNet() {
		return this.NET;
	}

	public boolean haveNet() {
		return this.NET!=0 && EnergyNetProcess.EnergyNetExist(this.NET);
	}

	public void cleanNet() {
		setNet(0);
	}

	public void setNet(long id) {
		this.NET = id;
		this.setChanged();
	}

	
    public void remove(Level level) {
        if (haveNet()) {
        	EnergyNet net = EnergyNetProcess.getEnergyNetNotCreate(this.NET);
        	if(net == null) {
        		System.out.println("ERROR,this block remove,it should have a NET but NET not exist!");
        		cleanNet();
        	}
        	this.removeAllrelativeConnection();//这里只删掉了自己储存的，没删除EnergyNet里的
        	Map<BlockPos, Set<BlockPos>> allEdge = net.getEdgeMap();
        	
        	//Set<BlockPos> affectPos = net.getEdges(this.worldPosition);
        	Set<BlockPos> affectPos = new HashSet<>(net.getEdges(this.worldPosition));
        	//不要用上面那个，下面修改了net，会导致affectPos变化，然后循环崩溃!!
        	
        	if(affectPos.isEmpty()) {
        		EnergyNetProcess.deleteEnergyNet(this.NET);
        		cleanNet();
        	}
        	
        	//net.removeAllEdgesFromPoint(this.worldPosition);
        	for(BlockPos pos : affectPos) {
        		if(EnergyNet.canStillConnect(allEdge,this.worldPosition,pos)) {
        			net.removeEdge(pos, this.worldPosition);
        		}else {
        			EnergyNetProcess.splitEnergyNet(pos,this.worldPosition ,level, net);
        		}
        	}
        	net.removeBlockPos(this.worldPosition, this);
            cleanNet();
        }
    }

	protected abstract void servertick();

	protected abstract void clienttick();
}
