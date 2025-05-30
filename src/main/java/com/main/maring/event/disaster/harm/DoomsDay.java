package com.main.maring.event.disaster.harm;

import com.main.maring.Maring;
import com.main.maring.config.CommonConfig;
import com.main.maring.world.data.ModWorldData;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent.LevelTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class DoomsDay {

	private static ResourceKey<Level> limboKey = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(Maring.MODID, "limbo"));
	
	
	/***
	 * 最后的毁灭的日子
	 * 将所有主世界玩家传送到Limbo
	 * 提前一天要警告
	 * @author NANMEDA
	 * ***/
	@SubscribeEvent
	public static void doomsDay(LevelTickEvent event) {
		Level level = event.level;
		if(!CommonConfig.DOOMS_WILL_ARRIVE.get()) return;
		ModWorldData data = ModWorldData.get(level);
		if (data == null) {
			return;
		}
		if(data.DOOMS_DAY_OCCUR) {
			if(!level.isClientSide() && level.dimension()==Level.OVERWORLD) {
				ServerLevel limbo = level.getServer().getLevel(limboKey);
				if (limbo == null) {
					System.out.println("找不到Limbo");
					return;
				}
				level.getServer().getPlayerList().getPlayers().forEach(player -> {
                    
                        player.teleportTo(limbo, 0, -63, 0, player.getYRot(), player.getXRot());
                    });
			}
			return;
		}
		if(event.level.getDayTime()%20==0) {
			//System.out.println("当前距离doom tick: "+ (DOOMS_DAY_START - event.level.getDayTime()));
		}

		if(!level.isClientSide() && level.dimension()==Level.OVERWORLD) {
			long time = level.getDayTime();
			if (time > CommonConfig.DOOMS_DAY_TOMORROW.get() + CommonConfig.DAY_TICKS + CommonConfig.DOOM_EVENT_DURATION) {
				data.DOOMS_DAY_OCCUR = true;
				ServerLevel limbo = level.getServer().getLevel(limboKey);
				if (limbo == null) {
					System.out.println("找不到Limbo");
					return;
				}
				level.getServer().getPlayerList().getPlayers().forEach(player -> {
					player.teleportTo(limbo, 0, -63, 0, player.getYRot(), player.getXRot());
                    
                });
				//deleteOverworld(event.level.getServer());
			}else if(time >= CommonConfig.DOOMS_DAY_TOMORROW.get() + CommonConfig.DAY_TICKS) {
				//DOOMS_DAY_OCCUR = true;
				foreTime(time,level);
				lastTime(time,level);
			}else if(time >= CommonConfig.DOOMS_DAY_TOMORROW.get()) {
            	if(!data.WARNING) {
            		data.WARNING = true;
            		level.players().forEach(player -> player.sendSystemMessage(Component.translatable("maring.disaster.dooms_day.warning")));
            	}
			}
		}
	}
	
	
	private static void foreTime(long time, Level level) {
		
	}

	private static void lastTime(long time, Level level) {
		level.players().forEach(player -> player.getInventory().clearContent());
	}

}
