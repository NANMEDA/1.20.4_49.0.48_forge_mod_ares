package effect;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

/**
 * 效果：饱腹
 * 每隔一段时间增加玩家的食物属性
 * @author NANMEDA
 * */
public class EffectFulling extends EffectMain {
    private int amplified;

	public EffectFulling(MobEffectCategory type, int color, boolean isInstant, int amplified) {
        super(type, color, isInstant);
        this.amplified = amplified;
    }
    
    @Override
    protected boolean canApplyEffect(int remainingTicks, int level) {
        return remainingTicks % 100 == 0;//5s加1
    }
    
    @Override
    public void applyEffectTick(LivingEntity living, int amplified) {
        if (living instanceof Player) {
            Player player = (Player) living;
            player.getFoodData().eat(1, 1f);
        }
    }
    
    @Override
    public boolean isBeneficial() {
        return true;
    }
}

