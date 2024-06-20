package worldgen;

import com.google.common.base.Suppliers;
import com.main.maring.Maring;

import block.norm.BlockRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public class ModConfiguredFeatures {
	/*
	public static final Supplier<List<OreConfiguration.TargetBlockState>> MARS_DIAMOND_ORE_REPLACEABLES = Suppliers.memoize(() -> List.of(
            OreConfiguration.target(new BlockMatchTest(BlockRegister.COMMON_BLOCKS[2].get()), BlockRegister.COMMON_BLOCKS[2].get().defaultBlockState())));

    public static final Supplier<List<OreConfiguration.TargetBlockState>> MARS_ICE_SHARD_ORE_REPLACEABLES = Suppliers.memoize(() -> List.of(
            OreConfiguration.target(new BlockMatchTest(BlockRegistry.MARS_STONE.get()), BlockRegistry.MARS_ICE_SHARD_ORE.get().defaultBlockState())));

    public static final Supplier<List<OreConfiguration.TargetBlockState>> MARS_IRON_ORE_REPLACEABLES = Suppliers.memoize(() -> List.of(
            OreConfiguration.target(new BlockMatchTest(BlockRegistry.MARS_STONE.get()), BlockRegistry.MARS_IRON_ORE.get().defaultBlockState())));

    public static final Supplier<List<OreConfiguration.TargetBlockState>> MARS_OSTRUM_ORE_REPLACEABLES = Suppliers.memoize(() -> List.of(
            OreConfiguration.target(new BlockMatchTest(BlockRegistry.MARS_STONE.get()), BlockRegistry.MARS_OSTRUM_ORE.get().defaultBlockState())));

    
    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        
        register(context, MARS_DIAMOND_ORE_KEY, Feature.ORE, new OreConfiguration(MARS_DIAMOND_ORE_REPLACEABLES.get(), 7));
        //register(context, MARS_ICE_SHARD_ORE_KEY, Feature.ORE, new OreConfiguration(MARS_ICE_SHARD_ORE_REPLACEABLES.get(), 10));
        //register(context, MARS_IRON_ORE_KEY, Feature.ORE, new OreConfiguration(MARS_IRON_ORE_REPLACEABLES.get(), 11));
        //register(context, MARS_OSTRUM_ORE_KEY, Feature.ORE, new OreConfiguration(MARS_OSTRUM_ORE_REPLACEABLES.get(), 8));
    }
*/

}
