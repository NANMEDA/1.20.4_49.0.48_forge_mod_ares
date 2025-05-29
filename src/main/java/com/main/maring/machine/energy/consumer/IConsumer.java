package com.main.maring.machine.energy.consumer;

import com.main.maring.machine.energy.IEnergy;
import com.main.maring.util.net.EnergyNet.EnergyEnum;

public interface IConsumer extends IEnergy{
	
	abstract int getEnergyConsume();
	
	/**
	 * 获得供电水平, 以100为满供电
	 * @return
	 */
	abstract int getEnergySupplyLevel();
	
    @Override
    default EnergyEnum getEnergyKind(){
    	return EnergyEnum.CONSUMER;
    }
}
