package machine.energy;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import util.net.EnergyNetProcess;

public abstract class EnergyEntity extends BlockEntity{
    protected int FULL_ENERGY;
    protected long NET;
	
	public EnergyEntity(BlockEntityType<?> p_155228_, BlockPos p_155229_, BlockState p_155230_) {
		super(p_155228_, p_155229_, p_155230_);
	}

    public boolean lineVisible() {
    	return true;
    }
    
	public long getNet() {
		return this.NET;
	}


	public boolean haveNet() {
		return this.NET!=0;
	}


	public void cleanNet() {
		setNet(0);
	}


	public void setNet(long id) {
		this.NET = id;
	}
	
    public void remove() {
        if (haveNet()) {
            EnergyNetProcess.getEnergyNet(this.NET).removeBlockPos(this.worldPosition, this);
            cleanNet();  // Optionally, clean the NET value
        }
    }
}
