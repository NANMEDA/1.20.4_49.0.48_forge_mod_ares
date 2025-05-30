package com.main.maring.event.disaster.harm;

import com.main.maring.config.CommonConfig;
import com.main.maring.world.data.ModWorldData;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;


import com.main.maring.animal.entity.MonsterRegister;
import com.main.maring.animal.entity.jumpspider.JumpSpider;

@Mod.EventBusSubscriber
public class SpiderComing {

	public static final int SPAWN_INTERVAL_TICKS = 60;

    /***
     * 不间断的召唤抱子蜘蛛
     * @author NANMEDA
     * ***/
	@SubscribeEvent
    public static void spiderDay(PlayerTickEvent event) {
        Player player = event.player;
        Level level = player.level();
		if(!CommonConfig.DOOMS_WILL_ARRIVE.get()) return;
		//System.out.println("当前tick: "+ level.getDayTime());
        ModWorldData data = ModWorldData.get(level);
        if (data == null) {
            return;
        }
		if(!data.SPIDER_DAY_OCCUR) {
	        if (!level.isClientSide() && level.dimension()==Level.OVERWORLD) {//不要忘了这个level是player的level
	            long time = level.getDayTime();
	            if(time >= CommonConfig.DOOMS_DAY_TOMORROW.get()) {
	            	data.SPIDER_DAY_OCCUR = true;
	            	return;
	            }
	            if (time >= CommonConfig.SPIDER_EVENT_START.get() && time % SPAWN_INTERVAL_TICKS == 0) {
	            	if(!data.SPIDER_DAY_INIT) {
	            		data.SPIDER_DAY_INIT = true;
	            		player.sendSystemMessage(Component.translatable("maring.disaster.spider_day.init"));
	            	}
	                if (time<CommonConfig.SPIDER_EVENT_DURATION.get()+CommonConfig.SPIDER_EVENT_START.get()) {
	                	if(!spiderTooMuch((ServerLevel) level))
                            spawnEntityAroundPlayers((ServerLevel) level , player);
	                }else
	                {
	                	data.SPIDER_DAY_OCCUR = true;
	                }
	            }
	        }
		}
    }

    private static void spawnEntityAroundPlayers(ServerLevel world,Player player) {
            BlockPos pos = player.blockPosition();
            Random random = new Random();
            int offsetX = (int) ((random.nextDouble() - 0.5) * 30);
            int Y = Math.min(484,pos.getY()+120);
            int offsetZ = (int) ((random.nextDouble() - 0.5) * 30);
            BlockPos spawnPos = new BlockPos(pos.getX()+offsetX, Y, pos.getZ()+offsetZ);
            if(!world.isEmptyBlock(spawnPos)) {
            	return;
            }
            JumpSpider spider = new JumpSpider(MonsterRegister.JUMP_SPIDER.get(), world);
        spider.setPos(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ());
        world.addFreshEntityWithPassengers(spider);
    }

    public static boolean spiderTooMuch(ServerLevel world) {
        int count = 0;
        EntityType<?> entityType = MonsterRegister.JUMP_SPIDER.get();

        for (Entity entity : world.getAllEntities()) {
            if (entityType.equals(entity.getType())) {
                count++;
            }
            if(count>30)
            	return true;
        }
        return false;
    }
}
