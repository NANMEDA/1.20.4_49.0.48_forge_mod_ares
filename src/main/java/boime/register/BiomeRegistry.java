package boime.register;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = "maring", bus = Mod.EventBusSubscriber.Bus.MOD)
public class BiomeRegistry {
	static final String MODID = "maring";
	/*
    public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, MODID);

    public static final RegistryObject<Biome> MARING_COLD_DRYRIVERBED = BIOMES.register("maring_cold_dryriverbed",
        () -> ForgeRegistries.BIOMES.getValue(new ResourceLocation(MODID, "worldgen/biome/maring_cold_dryriverbed"))
    );

    public static final RegistryObject<Biome> MARING_COLDGROUND = BIOMES.register("maring_coldground",
        () -> ForgeRegistries.BIOMES.getValue(new ResourceLocation(MODID, "worldgen/biome/maring_coldground"))
    );

    public static final RegistryObject<Biome> MARING_HOT_DRYRIVERBED = BIOMES.register("maring_hot_dryriverbed",
        () -> ForgeRegistries.BIOMES.getValue(new ResourceLocation(MODID, "worldgen/biome/maring_hot_dryriverbed"))
    );

    public static final RegistryObject<Biome> MARING_HOTGROUND = BIOMES.register("maring_hotground",
        () -> ForgeRegistries.BIOMES.getValue(new ResourceLocation(MODID, "worldgen/biome/maring_hotground"))
    );

    public static final RegistryObject<Biome> MARING_NORMAL_DRYRIVERBED = BIOMES.register("maring_normal_dryriverbed",
        () -> ForgeRegistries.BIOMES.getValue(new ResourceLocation(MODID, "worldgen/biome/maring_normal_dryriverbed"))
    );

    public static final RegistryObject<Biome> MARING_NORMALGROUND = BIOMES.register("maring_normalground",
        () -> ForgeRegistries.BIOMES.getValue(new ResourceLocation(MODID, "worldgen/biome/maring_normalground"))
    );

    public static final RegistryObject<Biome> MARING_VERY_HOTGROUND = BIOMES.register("maring_very_hotground",
            () -> ForgeRegistries.BIOMES.getValue(new ResourceLocation(MODID, "worldgen/biome/maring_very_hotground"))
    );*/
}
