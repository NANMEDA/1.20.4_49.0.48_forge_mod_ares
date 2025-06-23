package com.main.maring.event.forge;

import com.main.maring.Maring;
import com.main.maring.block.norm.BlockRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import com.main.maring.util.mar.EnvironmentData;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Predicate;

/**
 * 检测，
 * 假如在火星中放置，且周围没有人工空气
 * 火把，萤火，破坏
 * 草,花,树苗->枯树
 * 苔藓块,草块 -> 泥土
 * <br>TODO:<br> 放置水
 * @author NANMEDA
 * */
@Mod.EventBusSubscriber
public class MarPlaceEdit {
	
	private static ResourceKey<Level> marKey = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(Maring.MODID, "maringmar"));
	private static EnvironmentData environmentData = null;
	@SuppressWarnings("deprecation")
	private static final LazyLoadedValue<BlockState> A_AIR_BLOCK_STATE = 
		    new LazyLoadedValue<>(() -> BlockRegister.A_AIR.get().defaultBlockState());
	//private static Supplier<BlockState> A_AIR_BLOCK_STATE = 
	//		() -> {return BlockRegister.A_AIR.get().defaultBlockState();};


	public record ReplaceRule(
			Predicate<Block> blockPredicate,
			Predicate<EnvironmentData> envCondition,
			Block replacement,
			@Nullable ItemStack dropItem
	) {}


	static List<ReplaceRule> REPLACE_RULES = List.of(
			new ReplaceRule(
					MarPlaceEdit::posIsSapling,
					env -> env.suitablePLANTH(),
					Blocks.DEAD_BUSH,
					null
			),
			new ReplaceRule(
					block -> block == Blocks.WALL_TORCH || block == Blocks.TORCH || block == Blocks.CAMPFIRE,
					env -> env.canBurn(),
					Blocks.AIR,
					new ItemStack(Items.STICK)
			),
			new ReplaceRule(
					MarPlaceEdit::posIsFlower,
					env -> env.suitablePLANTL(),
					Blocks.DEAD_BUSH,
					null
			),
			new ReplaceRule(
					block -> block == Blocks.GRASS_BLOCK || block == Blocks.MOSS_BLOCK || block == Blocks.MOSS_CARPET,
					env -> env.suitableMOSS(),
					Blocks.DIRT,
					null
			),
			new ReplaceRule(
					block -> block == Blocks.TURTLE_EGG || block == Blocks.SNIFFER_EGG,
					env -> env.suitableANIMAL(),
					Blocks.AIR,
					new ItemStack(Items.EGG)
			),
			new ReplaceRule(
					block -> block == Blocks.WATER || block == Blocks.ICE,
					env -> env.iceMelt(),
					Blocks.PACKED_ICE,
					null
			)
	);

	@SubscribeEvent
	public static void onBlockPlaced(BlockEvent.EntityPlaceEvent event) {
		if(event.getLevel().isClientSide()) return;
		if(!event.getEntity().level().dimension().equals(marKey)) return;
		if(checkAnyAAIR(event.getLevel(),event.getPos())) return;

		Block block = event.getPlacedBlock().getBlock();

		if ( environmentData == null && event.getLevel() instanceof ServerLevel serverLevel) {
			environmentData = EnvironmentData.get(serverLevel);
		}
		if (environmentData == null) return;

		for (ReplaceRule rule : REPLACE_RULES) {
			if (rule.blockPredicate().test(block)) {
				if (rule.envCondition().test(environmentData)) {
					return; // 环境合适，保留
				}

				event.getLevel().setBlock(event.getPos(), rule.replacement().defaultBlockState(), 2);

				if (rule.dropItem() != null) {
					Containers.dropContents(event.getEntity().level(), event.getPos(),
							NonNullList.of(ItemStack.EMPTY, rule.dropItem()));
				}
				return;
			}
		}
	}


	private static boolean checkAnyAAIR(LevelAccessor levelAccessor,BlockPos pos) {
		BlockPos[] ps = {
				pos.above(),
				pos.below(),
				pos.east(),
				pos.west(),
				pos.south(),
				pos.north()
		};
		for(BlockPos p : ps) {
			if(levelAccessor.getBlockState(p)==A_AIR_BLOCK_STATE.get()) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean posIsSapling(Block block) {
        return block.defaultBlockState().is(BlockTags.SAPLINGS)||
				block == Blocks.BAMBOO;
	}
	
	public static boolean posIsFlower(Block block) {
	    return block.defaultBlockState().is(BlockTags.SMALL_FLOWERS) ||
				block.defaultBlockState().is(BlockTags.TALL_FLOWERS) ||
	            block == Blocks.TALL_GRASS ||
	            block == Blocks.LARGE_FERN ||
    		    block == Blocks.PINK_PETALS ||
	            block == Blocks.FERN;
	}
}
