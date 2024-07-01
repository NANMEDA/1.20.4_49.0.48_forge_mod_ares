package boime;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.effect.MobEffectInstance;

import com.effect.register.EffectRegister;
import com.item.ItemRegister;

import net.minecraft.core.BlockPos;
import net.minecraftforge.event.entity.living.LivingEvent.LivingTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "maring", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BiomeEffectApplier {

    private static final int TICK_INTERVAL = 10; // 检测间隔
    public static boolean WILL_PRESSURE_HURT = true; // 检测间隔

    @SuppressWarnings("resource")
	@SubscribeEvent
    public static void onLivingUpdate(LivingTickEvent event) {
    	if(!WILL_PRESSURE_HURT) return;
        if (event.getEntity().level().isClientSide) {
            return;
        }
        LivingEntity entity = event.getEntity();
        if (entity.level().getGameTime() % TICK_INTERVAL != 0) {
            return;
        }
        BlockPos entityPos = entity.blockPosition().above();
        int theColor = entity.level().getBiome(entityPos).get().getFoliageColor();
        if (theColor != 9334293) {
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
