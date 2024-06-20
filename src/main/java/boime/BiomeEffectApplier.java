package boime;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.effect.MobEffectInstance;

import com.effect.register.EffectRegister;

import net.minecraft.core.BlockPos;
import net.minecraftforge.event.entity.living.LivingEvent.LivingTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "maring", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BiomeEffectApplier {

    private static final int TICK_INTERVAL = 10; // 检测间隔

    @SuppressWarnings("resource")
	@SubscribeEvent
    public static void onLivingUpdate(LivingTickEvent event) {
        if (event.getEntity().level().isClientSide) {
            return;
        }
        LivingEntity entity = event.getEntity();
        if (entity.level().getGameTime() % TICK_INTERVAL != 0 || entity instanceof Player && (((Player) entity).isCreative() || ((Player) entity).isSpectator())) {
            return;
        }
        BlockPos entityPos = entity.blockPosition().above();
        int theColor = entity.level().getBiome(entityPos).get().getFoliageColor();
        if (theColor == 9334293) {
            entity.addEffect(new MobEffectInstance(EffectRegister.LOSEPRESSURE.get(), 20, 1));
        }
    }
}
