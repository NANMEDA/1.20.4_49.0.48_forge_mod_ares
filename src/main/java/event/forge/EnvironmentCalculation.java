package event.forge;

import javax.swing.text.Segment;

import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.event.TickEvent.LevelTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import util.mar.EnvironmentConfig;
import util.mar.EnvironmentData;

@Mod.EventBusSubscriber(modid = "maring", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EnvironmentCalculation {
	
	static EnvironmentData data = null;
	static int Segment = 0;  // 当前计算分段
	static double[] delta = null;  // 记录每次计算的变化分量
	static final int TOTAL_TICKS = 1200;  // 每次计算的周期
	static final int SEGMENTS = 30;  // 将变化分为 30 段
	static final int TICKS_PER_SEGMENT = TOTAL_TICKS / SEGMENTS;  // 每段更新的 tick 数

	/***
	 * @author NANMEDA
	 ***/
	@SubscribeEvent
	public static void EnvironmentCalculate(LevelTickEvent event) {
	    // 每隔 20 ticks 执行一次
	    if (event.level.getDayTime() % TICKS_PER_SEGMENT == 1) {
	        if (event.level instanceof ServerLevel l) {
	            // 初始化数据对象
	            if (data == null) {
	                data = EnvironmentData.get(l);
	                return;
	            }
	            
	            // 当前是第一个 segment 或需要重新计算
	            if (Segment == 0) {
	                double[] current = data.getX();  // 获取当前状态
	                double[] next = MarEnvironmentCalculate(current);  // 执行计算
	                
	                // 计算每个段的增量 (delta)
	                delta = new double[5];
	                for (int i = 0; i < 5; i++) {
	                    delta[i] = (next[i] - current[i]) / SEGMENTS;
	                }
	                
	                // 记录计算结果，准备分段更新
	                data.setX(current);  // 重新设置当前状态
	            } else {
	                // 逐段更新状态
	                double[] current = data.getX();
	                for (int i = 0; i < current.length; i++) {
	                    current[i] += delta[i];  // 累计增量
	                }
	                data.setX(current);  // 更新状态
	            }
	            
	            // 更新段计数
	            Segment = (Segment + 1) % SEGMENTS;  // 循环 Segment 计数
	        }
	    }
	}


	private static double[] MarEnvironmentCalculate(double[] x) {
		return EnvironmentConfig.next(x);
	}

}
