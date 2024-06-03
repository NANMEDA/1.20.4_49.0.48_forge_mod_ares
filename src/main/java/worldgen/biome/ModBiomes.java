package worldgen.biome;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import worldgen.ModPlacedFeatures;

public class ModBiomes {
    public static final ResourceKey<Biome> TEST_BIOME0 = ResourceKey.create(Registries.BIOME,
            new ResourceLocation("maring", "maring_hotground"));
    public static final ResourceKey<Biome> TEST_BIOME1 = ResourceKey.create(Registries.BIOME,
            new ResourceLocation("maring", "maring_very_hotground"));
    public static final ResourceKey<Biome> TEST_BIOME2 = ResourceKey.create(Registries.BIOME,
            new ResourceLocation("maring", "maring_normalground"));
    public static final ResourceKey<Biome> TEST_BIOME3 = ResourceKey.create(Registries.BIOME,
            new ResourceLocation("maring", "maring_coldground"));
    public static final ResourceKey<Biome> TEST_BIOME4 = ResourceKey.create(Registries.BIOME,
            new ResourceLocation("maring", "maring_hot_dryriverbed "));
    public static final ResourceKey<Biome> TEST_BIOME5 = ResourceKey.create(Registries.BIOME,
            new ResourceLocation("maring", "maring_normal_dryriverbed"));
    public static final ResourceKey<Biome> TEST_BIOME6 = ResourceKey.create(Registries.BIOME,
            new ResourceLocation("maring", "maring_cold_dryriverbed"));

    public static void boostrap(BootstapContext<Biome> context) {
        context.register(TEST_BIOME0, testBiome(context));
        context.register(TEST_BIOME1, testBiome(context));
        context.register(TEST_BIOME2, testBiome(context));
        context.register(TEST_BIOME3, testBiome(context));
        context.register(TEST_BIOME4, testBiome(context));
        context.register(TEST_BIOME5, testBiome(context));
        context.register(TEST_BIOME6, testBiome(context));
    }

    public static void globalOverworldGeneration(BiomeGenerationSettings.Builder builder) {
        BiomeDefaultFeatures.addDefaultCarversAndLakes(builder);
        BiomeDefaultFeatures.addDefaultCrystalFormations(builder);
        BiomeDefaultFeatures.addDefaultMonsterRoom(builder);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(builder);
        BiomeDefaultFeatures.addDefaultSprings(builder);
        BiomeDefaultFeatures.addSurfaceFreezing(builder);
    }

    public static Biome testBiome(BootstapContext<Biome> context) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();

        spawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.WOLF, 5, 4, 4));

        BiomeDefaultFeatures.farmAnimals(spawnBuilder);
        BiomeDefaultFeatures.commonSpawns(spawnBuilder);

        BiomeGenerationSettings.Builder biomeBuilder =
                new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));
        //we need to follow the same order as vanilla biomes for the BiomeDefaultFeatures
        globalOverworldGeneration(biomeBuilder);
        BiomeDefaultFeatures.addMossyStoneBlock(biomeBuilder);
        BiomeDefaultFeatures.addForestFlowers(biomeBuilder);
        BiomeDefaultFeatures.addFerns(biomeBuilder);
        BiomeDefaultFeatures.addDefaultOres(biomeBuilder);
        BiomeDefaultFeatures.addExtraGold(biomeBuilder);

        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.TREES_PLAINS);

        BiomeDefaultFeatures.addDefaultMushrooms(biomeBuilder);
        BiomeDefaultFeatures.addDefaultExtraVegetation(biomeBuilder);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModPlacedFeatures.PINE_PLACED_KEY);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .downfall(0.8f)
                .temperature(0.7f)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .specialEffects((new BiomeSpecialEffects.Builder())
                        .waterColor(0xe82e3b)
                        .waterFogColor(0xbf1b26)
                        .skyColor(0x30c918)
                        .grassColorOverride(0x7f03fc)
                        .foliageColorOverride(0xd203fc)
                        .fogColor(0x22a1e6)
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        .build())
                .build();
    }
}
