package util.mar;

import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * 世界加载和保存数据的事件注册。
 */
@Mod.EventBusSubscriber
public class EnvironmentDataManager {

    // 世界启动时初始化环境数据
    @SubscribeEvent
    public static void onServerStarting(ServerStartingEvent event) {
        ServerLevel level = event.getServer().overworld(); // 获取主世界
        EnvironmentData.getOrCreate(level); // 确保环境数据已初始化
        System.out.println("环境数据已初始化！");
    }

    // 世界停止时保存数据（可选）
    @SubscribeEvent
    public static void onServerStopped(ServerStoppedEvent event) {
        System.out.println("服务器已停止，环境数据保存完成！");
    }
}
