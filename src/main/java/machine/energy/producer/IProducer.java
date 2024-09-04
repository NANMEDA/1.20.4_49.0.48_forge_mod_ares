package machine.energy.producer;

import machine.energy.IEnergy;
import util.net.EnergyNet.EnergyEnum;

public interface IProducer extends IEnergy{

	/**
	 * 提供发电量
	 * <p>参考 一个木板 200-300 mJ
	 * @return int 发电量
	 */
    abstract int provideEnergySupply();
    
    @Override
    default EnergyEnum getEnergyKind(){
    	return EnergyEnum.PRODUCER;
    }
    
}
