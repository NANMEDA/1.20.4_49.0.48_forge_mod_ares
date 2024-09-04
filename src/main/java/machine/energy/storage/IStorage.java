package machine.energy.storage;

import machine.energy.IEnergy;
import util.net.EnergyNet.EnergyEnum;

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
