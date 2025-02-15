package event.disaster.harm;

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

import com.main.maring.ExtraConfig;

import animal.entity.MonsterRegister;
import animal.entity.jumpspider.JumpSpider;

@Mod.EventBusSubscriber(modid = "maring", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SpiderComing {
	
	private static final boolean DOOMS_WILL_ARRIVE = ExtraConfig.DOOMS_WILL_ARRIVE;
	private static final long DOOMS_DAY_TOMORROW = ExtraConfig.DOOMS_DAY_TOMORROW;
	private static final long SPIDER_EVENT_START = ExtraConfig.SPIDER_EVENT_START;
	private static final int SPIDER_EVENT_DURATION = ExtraConfig.SPIDER_EVENT_DURATION;
	
	public static final int SPAWN_INTERVAL_TICKS = 60;
    public static boolean SPIDER_DAY_OCCUR = false;
    public static boolean SPIDER_DAY_INIT = false;
	
    /***
     * 不间断的召唤抱子蜘蛛
     * @author NANMEDA
     * ***/
	@SubscribeEvent
    public static void spiderDay(PlayerTickEvent event) {
		if(!DOOMS_WILL_ARRIVE) return;
		//System.out.println("当前tick: "+ level.getDayTime());
		if(!SPIDER_DAY_OCCUR) {
			Player player = event.player;
			Level level = player.level();
	        if (!level.isClientSide() && level.dimension()==Level.OVERWORLD) {//不要忘了这个level是player的level
	            long time = level.getDayTime();
	            if(time >= DOOMS_DAY_TOMORROW) {
	            	SPIDER_DAY_OCCUR = true;
	            	return;
	            }
	            if (time >= SPIDER_EVENT_START && time % SPAWN_INTERVAL_TICKS == 0) {
	            	if(!SPIDER_DAY_INIT) {
	            		SPIDER_DAY_INIT = true;
	            		player.sendSystemMessage(Component.translatable("maring.disaster.spider_day.init"));
	            	}
	                if (time<SPIDER_EVENT_DURATION+SPIDER_EVENT_START) {
	                	if(!spiderTooMuch((ServerLevel) level))
	                	spawnEntityAroundPlayers((ServerLevel) level , player);
	                }else 
	                {
	                	SPIDER_DAY_OCCUR = true;
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
            if (spider != null) {
            	spider.setPos(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ());
                world.addFreshEntityWithPassengers(spider);
            }
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
