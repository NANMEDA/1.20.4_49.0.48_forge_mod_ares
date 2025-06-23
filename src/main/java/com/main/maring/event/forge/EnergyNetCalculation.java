package com.main.maring.event.forge;

import java.util.Collection;
import java.util.Random;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

import com.main.maring.machine.energy.consumer.IConsumer;
import com.main.maring.machine.energy.producer.IProducer;
import com.main.maring.machine.energy.storage.EnergyStorageMode;
import com.main.maring.machine.energy.storage.IStorage;
import com.main.maring.machine.energy.viewer.EnergyViewerEntity;
import com.main.maring.util.net.EnergyNetSavedData;
import net.minecraft.client.telemetry.events.WorldUnloadEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.event.TickEvent.LevelTickEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import com.main.maring.util.net.EnergyNet;
import com.main.maring.util.net.EnergyNet.EnergyEnum;
import com.main.maring.util.net.EnergyNetProcess;

@Mod.EventBusSubscriber
public class EnergyNetCalculation {

	public static int speed = 1;
	public static Random rd = new Random();
	private static boolean debug = false;
	
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
	        if (!EnergyNetProcess.initialized) {
	            EnergyNetProcess.init(level);
				EnergyNetProcess.initialized = true;
	        }
	        Collection<EnergyNet> energySet = EnergyNetProcess.getAllEnergyNets();
	        if (energySet==null) return;

	        if(speed>1) {
	        	if(rd.nextInt(speed)!=0) {
	        		return;
	        	}
	        }
	        if(debug)
	        System.out.println("EnergyNet count: " + energySet.size());
	        List<Long> deletedIds = new ArrayList<>();
	        ResourceLocation dl = level.dimension().location();
	        for (EnergyNet energyNet : energySet) {
	        	//假如有Dimension同时不是对应的
	        	if(energyNet.haveDimension()&&!energyNet.getDimension().equals(dl)) continue;
	            Set<BlockPos> producer = energyNet.getSet(EnergyEnum.PRODUCER);
	            Set<BlockPos> consumer = energyNet.getSet(EnergyEnum.CONSUMER);
	            Set<BlockPos> storage = energyNet.getSet(EnergyEnum.STORAGE);
	            Set<BlockPos> trans = energyNet.getSet(EnergyEnum.TRANS); 
	            Set<BlockPos> nul = energyNet.getSet(EnergyEnum.NULL); 
	            
	            if(energyNet._checkEmpty()||!energyNet.haveDimension()) {
	            	if(debug)
	            	System.out.println("delete a NET ID: energyNet.getId()" );
	                long id = energyNet.getId(); 
	                deletedIds.add(id);          // 记录到列表中
	            	continue;
	            }
	            if(debug)
		        System.out.println("AT LEVEL: " + level.dimension().location());
	            
	            long supply = 0;
	            long consume = 0;
	            if(debug)
	            System.out.println("Processing EnergyNet ID: " + energyNet.getId());

	            if (!producer.isEmpty()) {
	            	if(debug)
	                System.out.println("Producers:");
	                for (BlockPos pos : producer) {
	                    System.out.println("at: " + pos);
	                    BlockEntity entity = level.getBlockEntity(pos);
	                    if (entity instanceof IProducer pEntity) {
	                        supply += pEntity.provideEnergySupply();
	                    }else {
	                    	//导致创建电网时奔溃
	                    	//energyNet.removeBlockPos(pos, EnergyEnum.PRODUCER);
	                    }
	                }
	            }

	            if (!consumer.isEmpty()) {
	            	if(debug)
	                System.out.println("Consumers:");
	                for (BlockPos pos : consumer) {
	                    System.out.println("at: " + pos);
	                    BlockEntity entity = level.getBlockEntity(pos);
	                    if (entity instanceof IConsumer pEntity) {
	                        consume += pEntity.getEnergyConsume();
	                    }else {
	                    	//energyNet.removeBlockPos(pos, EnergyEnum.CONSUMER);
	                    }
	                }
	            }
	            if(debug)
	            System.out.println("AT LAST GET P: " + supply + "; GET C: " + consume);
          
	            if (!storage.isEmpty()) {
	            	long deficit=0,sup=0;
	            	long[] cse_t = {0,0,0};
	            	if(debug)
	                System.out.println("Storage Blocks:");
	                if (supply >= consume) {
	                    sup = supply - consume;
	                    for (BlockPos pos : storage) {
	                        System.out.println("at: " + pos);
	                        BlockEntity entity = level.getBlockEntity(pos);
	                        if (entity instanceof IStorage sEntity) {
	                            long[] cse = sEntity.getCSE();
	                            cse_t = addCSE(cse, cse_t);
	                            if(sup>0) {
		                            EnergyStorageMode mode = sEntity.getStorageMode();
		                            if (mode == EnergyStorageMode.USE || (mode == EnergyStorageMode.DANU && !level.isDay()))
		                                continue;
		                            long capacityLeft = cse[2];
		                            if (capacityLeft > 0) {
		                                int transfer = (int) Math.min(sEntity.transSpeed(), Math.min(capacityLeft, sup));
		                                sEntity.addStorage(transfer);
		                                sup -= transfer;
		                            }
	                            }
	                        }else {
		                    	//energyNet.removeBlockPos(pos, EnergyEnum.STORAGE);
		                    }
	                    }
	                    int supplyLevel = (int) ( 100.0 * (double)(supply) / (double) (supply-sup+1));
	                    energyNet.setSupplyLevel((supplyLevel > 250) ? 50 : (supplyLevel > 150) ? 80 : (supplyLevel > 100) ? 100 :supplyLevel);
	                    findViewerAndSet(level, nul, supply, supply-sup,cse_t[0], cse_t[1]);
	                } else {
	                    deficit = consume - supply;
	                    for (BlockPos pos : storage) {
	                        System.out.println("at: " + pos);
	                        BlockEntity entity = level.getBlockEntity(pos);
	                        if (entity instanceof IStorage sEntity) {
	                            long[] cse = sEntity.getCSE();
	                            cse_t = addCSE(cse, cse_t);
	                            if(deficit>0) {
		                            EnergyStorageMode mode = sEntity.getStorageMode();
		                            if (mode != EnergyStorageMode.NORMAL && mode != EnergyStorageMode.USE || (mode == EnergyStorageMode.DANU && level.isDay()))
		                                continue;
		                            long storedEnergy =  cse[1];
		                            if (storedEnergy > 0) {
		                                int transfer = (int) Math.min(sEntity.transSpeed(), Math.min(storedEnergy, deficit));
		                                sEntity.addStorage(-transfer);
		                                deficit -= transfer;
		                            }
	                            }
	                        }else {
		                    	//energyNet.removeBlockPos(pos, EnergyEnum.STORAGE);
		                    }
	                    }
	                    int supplyLevel = (int) ( 100 * (double)(consume-deficit) / (double) (consume+1));
	                    energyNet.setSupplyLevel((supplyLevel > 250) ? 50 : (supplyLevel > 150) ? 80 : (supplyLevel > 100) ? 100 :supplyLevel);
	                    findViewerAndSet(level, nul, consume-deficit, consume,cse_t[0], cse_t[1]);
	                }
	                //没有储存
	            } else {
	                int supplyLevel = (int) (100 * (double)supply / (double) (consume + 1));
	                energyNet.setSupplyLevel((supplyLevel > 250) ? 50 : (supplyLevel > 150) ? 80 : (supplyLevel > 100) ? 100 :supplyLevel);
	                findViewerAndSet(level, nul, supply, consume,0, 0);
	            }
	        }
	        if(!deletedIds.isEmpty()) {
		        for (Long id : deletedIds) {
		            EnergyNetProcess.deleteEnergyNet(id);
		        }
	        }
	        
	    }
	}

	@SubscribeEvent
	public static void onWorldUnload(LevelEvent.Unload event) {
		if (!event.getLevel().isClientSide()) {
			EnergyNetProcess.initialized = false;  // 重置初始化状态
			// 如果你还想清理数据：
			EnergyNetSavedData data = EnergyNetProcess.savedData;
			data.setDirty();
		}
	}

	@SubscribeEvent
	public static void onWorldload(LevelEvent.Load event) {
		if (!event.getLevel().isClientSide()) {
			EnergyNetProcess.savedData=null;
		}
	}


	private static void findViewerAndSet(Level level,Set<BlockPos> nul,long supply,long consume,long cap,long storage) {
		if(nul.isEmpty()||nul==null) return;
		for(BlockPos n:nul) {
			if(level.getBlockEntity(n) instanceof EnergyViewerEntity entity) {
				entity.setInformation(supply, consume, storage, cap);
			}
		}
	}
	
	private static long[] addCSE(long[] l1,long[] l2) {
		return new long[] {l1[0]+l2[0],l1[1]+l2[1],l1[2]+l2[2]};
	}
}
