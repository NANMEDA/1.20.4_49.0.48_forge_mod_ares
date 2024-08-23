package event.forge;

import java.util.Set;

import machine.energy.consumer.IConsumer;
import machine.energy.producer.IProducer;
import machine.energy.storage.EnergyStorageMode;
import machine.energy.storage.IStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.event.TickEvent.LevelTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import util.net.EnergyNet;
import util.net.EnergyNet.EnergyEnum;
import util.net.EnergyNetProcess;

@Mod.EventBusSubscriber(modid = "maring", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EnergyNetCalculation {
	
	private static boolean init = false;
	
	/***
	 * 计算电网耗电和分配电力
	 * 首先查看电网中所有的发电量
	 * 然后查看消耗量计算差值
	 * 假如有储存器，则根据储存器状态来确定是否充放电
	 * 根具最终情况，确定供电水平
	 * @author NANMEDA
	 * ***/
	@SubscribeEvent
	public static void EnergyNetCalculate(LevelTickEvent event) {
	    Level level = event.level;
	    if (!level.isClientSide) {
	        if (!init) {
	            EnergyNetProcess.init(level);
	            init = true;
	        }

	        if (EnergyNetProcess.getAllEnergyNets()==null||EnergyNetProcess.getAllEnergyNets().isEmpty()) return;

	        System.out.println("EnergyNet count: " + EnergyNetProcess.getAllEnergyNets().size());

	        for (EnergyNet energyNet : EnergyNetProcess.getAllEnergyNets()) {
	            Set<BlockPos> producer = energyNet.getSet(EnergyEnum.PRODUCER);
	            Set<BlockPos> consumer = energyNet.getSet(EnergyEnum.CONSUMER);
	            Set<BlockPos> storage = energyNet.getSet(EnergyEnum.STORAGE);
	            Set<BlockPos> trans = energyNet.getSet(EnergyEnum.TRANS); 
	            Set<BlockPos> nul = energyNet.getSet(EnergyEnum.NULL); 
	            if(producer.isEmpty()&&consumer.isEmpty()&&storage.isEmpty()&&trans.isEmpty()&&nul.isEmpty()) EnergyNetProcess.deleteEnergyNet(energyNet.getId());
	            
	            long output = 0;
	            long input = 0;

	            System.out.println("Processing EnergyNet ID: " + energyNet.getId());

	            if (!producer.isEmpty()) {
	                System.out.println("Producers:");
	                for (BlockPos pos : producer) {
	                    System.out.println("at: " + pos);
	                    BlockEntity entity = level.getBlockEntity(pos);
	                    if (entity instanceof IProducer pEntity) {
	                        output += pEntity.provideEnergySupply();
	                    }else {
	                    	//导致创建电网时奔溃
	                    	//energyNet.removeBlockPos(pos, EnergyEnum.PRODUCER);
	                    }
	                }
	            }

	            if (!consumer.isEmpty()) {
	                System.out.println("Consumers:");
	                for (BlockPos pos : consumer) {
	                    System.out.println("at: " + pos);
	                    BlockEntity entity = level.getBlockEntity(pos);
	                    if (entity instanceof IConsumer pEntity) {
	                        input += pEntity.getEnergyConsume();
	                    }else {
	                    	//energyNet.removeBlockPos(pos, EnergyEnum.CONSUMER);
	                    }
	                }
	            }

	            if (!storage.isEmpty()) {
	                System.out.println("Storage Blocks:");
	                if (output > input) {
	                    energyNet.setSupplyLevel(100);
	                    long sup = output - input;
	                    for (BlockPos pos : storage) {
	                        System.out.println("at: " + pos);
	                        BlockEntity entity = level.getBlockEntity(pos);
	                        if (entity instanceof IStorage sEntity) {
	                            EnergyStorageMode mode = sEntity.getEnum();
	                            if (mode == EnergyStorageMode.USE || (mode == EnergyStorageMode.DANU && !level.isDay()))
	                                continue;
	                            long capacityLeft = sEntity.getCapalicity() - sEntity.getStorage();
	                            if (capacityLeft > 0) {
	                                int transfer = (int) Math.min(sEntity.transSpeed(), Math.min(capacityLeft, sup));
	                                sEntity.addStorage(transfer);
	                                sup -= transfer;
	                            }
	                            if (sup <= 1) {
	                                return;
	                            }
	                        }else {
		                    	//energyNet.removeBlockPos(pos, EnergyEnum.STORAGE);
		                    }
	                    }
	                } else {
	                    long deficit = input - output;
	                    for (BlockPos pos : storage) {
	                        System.out.println("at: " + pos);
	                        BlockEntity entity = level.getBlockEntity(pos);
	                        if (entity instanceof IStorage sEntity) {
	                            EnergyStorageMode mode = sEntity.getEnum();
	                            if (mode != EnergyStorageMode.NORMAL && mode != EnergyStorageMode.USE || (mode == EnergyStorageMode.DANU && level.isDay()))
	                                continue;
	                            long storedEnergy = sEntity.getStorage();
	                            if (storedEnergy > 0) {
	                                int transfer = (int) Math.min(sEntity.transSpeed(), Math.min(storedEnergy, deficit));
	                                sEntity.addStorage(-transfer);
	                                deficit -= transfer;
	                            }
	                            if (deficit <= 1) {
	                                energyNet.setSupplyLevel(100);
	                                return;
	                            }
	                        }else {
		                    	//energyNet.removeBlockPos(pos, EnergyEnum.STORAGE);
		                    }
	                    }
	                    int supplyLevel = (int) ((double) 100 * output / (input + 1));
	                    energyNet.setSupplyLevel(supplyLevel);
	                }
	            } else {
	                int supplyLevel = (int) ((double) 100 * output / (input + 1));
	                energyNet.setSupplyLevel(supplyLevel);
	            }
	        }
	    }
	}


}
