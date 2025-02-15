package boime;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.effect.MobEffectInstance;

import com.main.maring.ExtraConfig;

import effect.registry.EffectRegister;
import item.ItemRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.event.entity.living.LivingEvent.LivingTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import util.mar.EnvironmentData;

/**
 * 火星大气伤害
 * @author NANMEDA
 * */
@Mod.EventBusSubscriber(modid = "maring", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BiomeEffectApplier {

    private static final int TICK_INTERVAL = 10; // 检测间隔
    //public static boolean WILL_PRESSURE_HURT = ; // 检测间隔
    //private static Supplier<BlockState>  A_AIR_STATE = () -> {return BlockRegister.A_AIR.get().defaultBlockState();};
    private static EnvironmentData environmentData = null;

    /***
     * 赋予药水效果
     * 检测玩家在火星群系的颜色 和 所处方块
     * @author NANMEDA
     * ***/
    @SubscribeEvent
    public static void onLivingUpdate(LivingTickEvent event) {
    	if(!ExtraConfig.WILL_PRESSURE_HURT) return;

    	Level level = event.getEntity().level();
        if (level.isClientSide) {
            return;
        }
    	if (environmentData==null) {
    	    environmentData = EnvironmentData.get((ServerLevel) level);
    	}
    	if(environmentData.suitableANIMAL()) return;
        LivingEntity entity = event.getEntity();
        if (level.getGameTime() % TICK_INTERVAL != 0) {
            return;
        }
        BlockPos entityPos = entity.blockPosition().above();
        
        /**
         * 这里需要修改
         * 根据getFoliageColor来判断是否在火星上虽然快，但是不合适
         */
        int theColor = level.getBiome(entityPos).get().getFoliageColor();
        if (theColor != 9334293) {
        	return;
        }
        
        if (level.getBlockState(entityPos.above())!=Blocks.AIR.defaultBlockState()) {
        	return;
        }
        if( entity instanceof Player) {
        	if(((Player) entity).isCreative()||entity.isSpectator()) {
        		return;
        	}
            Item helmet = entity.getItemBySlot(EquipmentSlot.HEAD).getItem();
            Item chestplate = entity.getItemBySlot(EquipmentSlot.CHEST).getItem();
            Item leggings = entity.getItemBySlot(EquipmentSlot.LEGS).getItem();
            Item boots = entity.getItemBySlot(EquipmentSlot.FEET).getItem();
        	if(helmet == ItemRegister.SPACESUIT_HELMET.get()
        			&&chestplate == ItemRegister.SPACESUIT_CHESTPLATE.get()
        			&&leggings == ItemRegister.SPACESUIT_LEGGINGS.get()
        			&&boots == ItemRegister.SPACESUIT_BOOTS.get()
        			) {
        		return;
        	}
        }
        entity.addEffect(new MobEffectInstance(EffectRegister.LOSEPRESSURE.get(), 20, 1));
    }
}
