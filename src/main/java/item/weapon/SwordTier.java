package item.weapon;

import java.util.function.Supplier;

import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

public enum SwordTier implements Tier {

    frenchBread(0, 114, 1.6F, 0.0F, 15, () -> {
	    return Ingredient.of(Items.BREAD);
	});
	
    private final int level;
    private final int uses;
    private final float speed;
    private final float damage;
    private final int enchantmentValue;
    private final Supplier<Ingredient> repairIngredient;
 
    private SwordTier(int level, int useTime, float speed, float damage, int p_i48458_7_, Supplier<Ingredient> repairSupplier)
    {
        this.level = level;		//好像是啥稀有等级来着
        this.uses = useTime;
        this.speed = speed;
        this.damage = damage;
        this.enchantmentValue = p_i48458_7_;
        this.repairIngredient = repairSupplier;
    }
	
    @Override
    public int getUses() {
        return this.uses;
    }

    @Override
    public float getSpeed() {
        return this.speed;
    }

    @Override
    public float getAttackDamageBonus() {
        return this.damage;
    }

    @Override
    public int getLevel() {
        return this.level;
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }
}
