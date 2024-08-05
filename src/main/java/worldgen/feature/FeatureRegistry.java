package worldgen.feature;

import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FeatureRegistry {
	
	private static final String MODID = "maring";

	public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(
			ForgeRegistries.FEATURES, MODID);
	
	public static final RegistryObject<ErosionStick> EROSION_STICK = FEATURES.register
			("erosion_stick", () -> new ErosionStick(NoneFeatureConfiguration.CODEC));
	
	public static final RegistryObject<MucusDrip> MUCUS_DRIP = FEATURES.register
			("mucus_drip", () -> new MucusDrip(NoneFeatureConfiguration.CODEC));
	
	public static final RegistryObject<MucusDrip> ANCIENT_ROCKET = FEATURES.register
			("ancient_rocket", () -> new MucusDrip(NoneFeatureConfiguration.CODEC));
	
	public static final RegistryObject<MeteoriteCrater> METEORITE_CARTER = FEATURES.register
			("meteorite_crater", () -> new MeteoriteCrater(NoneFeatureConfiguration.CODEC));
}
