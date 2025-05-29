package com.main.maring.machine.energy.trans;

import com.main.maring.machine.energy.IEnergy;
import com.main.maring.util.net.EnergyNet.EnergyEnum;

public interface ITrans extends IEnergy{
	
	/**
	 * 获得供电水平, 以100为满供电
	 * @return
	 */
	abstract int getEnergySupplyLevel();
	
    @Override
    default EnergyEnum getEnergyKind(){
    	return EnergyEnum.TRANS;
    }
}
