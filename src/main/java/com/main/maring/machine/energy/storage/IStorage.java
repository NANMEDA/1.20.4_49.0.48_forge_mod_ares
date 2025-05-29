package com.main.maring.machine.energy.storage;

import com.main.maring.machine.energy.IEnergy;
import com.main.maring.util.net.EnergyNet.EnergyEnum;

public interface IStorage extends IEnergy{

    abstract void addStorage(int e);
    
    public abstract EnergyStorageMode getStorageMode();
    
	/**
	 * @return 容量，存量，差值
	 */
    abstract long[] getCSE();
    
    abstract int transSpeed();
    
    @Override
    default EnergyEnum getEnergyKind(){
    	return EnergyEnum.STORAGE;
    }
    
}
