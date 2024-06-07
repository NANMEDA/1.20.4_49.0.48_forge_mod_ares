package com.item.register.weapon;

import java.util.function.Supplier;

import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

public enum SwordTier implements Tier {

    FrenchBread(0, 100, 1.6F, 0.0F, 15, () -> {
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
        this.level = level;
        this.uses = useTime;
        this.speed = speed;
        this.damage = damage;
        this.enchantmentValue = p_i48458_7_;
        this.repairIngredient = repairSupplier;
    }
	
	@Override
	public int getUses() {
		// TODO 自动生成的方法存根
		return 0;
	}

	@Override
	public float getSpeed() {
		// TODO 自动生成的方法存根
		return 0;
	}

	@Override
	public float getAttackDamageBonus() {
		// TODO 自动生成的方法存根
		return 0;
	}

	@Override
	public int getLevel() {
		// TODO 自动生成的方法存根
		return 0;
	}

	@Override
	public int getEnchantmentValue() {
		// TODO 自动生成的方法存根
		return 0;
	}

	@Override
	public Ingredient getRepairIngredient() {
		// TODO 自动生成的方法存根
		return null;
	}

}
