package machine.energy;

import java.util.Map;
import java.util.Set;
import java.util.HashSet;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import util.net.EnergyNet;
import util.net.EnergyNetProcess;

public abstract class EnergyEntity extends BlockEntity{
    protected int FULL_ENERGY;
    protected long NET;
	
	public EnergyEntity(BlockEntityType<?> p_155228_, BlockPos p_155229_, BlockState p_155230_) {
		super(p_155228_, p_155229_, p_155230_);
		this.NET = 0;
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
	}
	
    public void remove(Level level) {
        if (haveNet()) {
        	EnergyNet net = EnergyNetProcess.getEnergyNetNotCreate(this.NET);
        	if(net == null) {
        		System.out.println("ERROR,this block remove,it should have a NET but NET not exist!");
        		cleanNet();
        	}
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
