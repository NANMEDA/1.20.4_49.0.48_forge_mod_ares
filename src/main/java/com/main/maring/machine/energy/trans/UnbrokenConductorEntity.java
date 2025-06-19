package com.main.maring.machine.energy.trans;

import java.util.HashMap;
import java.util.Map;

import com.main.maring.machine.registry.MBlockEntityRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.level.block.state.BlockState;
import com.main.maring.util.net.EnergyNetProcess;

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


	protected void savedata(CompoundTag tag) {
		super.savedata(tag);
	}
	
	protected void loaddata(CompoundTag tag) {
		super.loaddata(tag);
	}
	
	@Override
	protected void servertick() {
	}
	
	@Override
	protected void clienttick() {
	}

}
