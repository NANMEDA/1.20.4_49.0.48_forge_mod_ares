package machine.energy.trans;

import machine.registry.MBlockEntityRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import util.net.EnergyNetProcess;

public class UnbrokenConductorEntity extends TransEntity implements ITrans {

	public UnbrokenConductorEntity(BlockPos pos, BlockState pBlockState) {
		super(MBlockEntityRegister.UNBROKENCONDUCTOR_BE.get(), pos, pBlockState);
	}

	@Override
	public int getEnergySupplyLevel() {
		if(this.haveNet()) {
			return EnergyNetProcess.getEnergyNet(this.getNet()).getSupplyLevel();
		}else {
			return 0;
		}
	}

	@Override
	protected void servertick() {
	}
	
	@Override
	protected void clienttick() {
	}

}
