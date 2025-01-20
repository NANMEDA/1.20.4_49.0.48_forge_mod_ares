package event.disaster.harm;

import com.main.maring.ExtraConfig;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent.LevelTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "maring", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class DoomsDay {
	
	//public static boolean DOOMS_WILL_ARRIVE = ExtraConfig.DOOMS_WILL_ARRIVE;
	//public static long DOOMS_DAY_START = ExtraConfig.DOOMS_DAY_START;
	//public static long DOOMS_DAY_END = ExtraConfig.DOOMS_DAY_END;
	//public static long DOOMS_DAY_TOMORROW = ExtraConfig.DOOMS_DAY_TOMORROW;
	
	//public static boolean DOOMS_DAY_OCCUR = false;
	//public static boolean WARNING = false;
	private static ResourceKey<Level> limboKey = ResourceKey.create(Registries.DIMENSION, new ResourceLocation("maring", "limbo"));
	
	
	/***
	 * 最后的毁灭的日子
	 * 将所有主世界玩家传送到Limbo
	 * 提前一天要警告
	 * @author NANMEDA
	 * ***/
	@SubscribeEvent
	public static void doomsDay(LevelTickEvent event) {
		if(!ExtraConfig.DOOMS_WILL_ARRIVE) return;
		if(ExtraConfig.DOOMS_DAY_OCCUR) {
			Level level = event.level;
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
		Level level = event.level;
		if(!level.isClientSide() && level.dimension()==Level.OVERWORLD) {
			long time = level.getDayTime();
			if(time>ExtraConfig.DOOMS_DAY_END) {
				ExtraConfig.DOOMS_DAY_OCCUR = true;
				ExtraConfig.save(event.level.getServer());
				ServerLevel limbo = level.getServer().getLevel(limboKey);
				if (limbo == null) {
					System.out.println("找不到Limbo");
					return;
				}
				level.getServer().getPlayerList().getPlayers().forEach(player -> {
					player.teleportTo(limbo, 0, -63, 0, player.getYRot(), player.getXRot());
                    
                });
				//deleteOverworld(event.level.getServer());
			}else if(time>=ExtraConfig.DOOMS_DAY_START) {
				//DOOMS_DAY_OCCUR = true;
				foreTime(time,level);
				lastTime(time,level);
			}else if(time>=ExtraConfig.DOOMS_DAY_TOMORROW) {
            	if(!ExtraConfig.WARNING) {
            		ExtraConfig.WARNING = true;
            		ExtraConfig.save(event.level.getServer());
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
