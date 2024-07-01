package damage;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.level.Level;

public class DamageSourceRegistry {
	private static final String MODID = "maring";
	
    public static final ResourceKey<DamageType> DAMAGE_SOURCE_PRESSURE = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(MODID, "pressure"));

    public static DamageSource getDamageSource(Level level, ResourceKey<DamageType> key) {
        return new DamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(key));
    }
}
