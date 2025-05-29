package com.main.maring.event.forge;

import com.main.maring.block.norm.BlockRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
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

/**
 * 检测，
 * 假如在火星中放置，且周围没有人工空气
 * 火把，萤火，破坏
 * 草,花,树苗->枯树
 * 苔藓块,草块 -> 泥土
 * <br>TODO:<br> 放置水
 * @author NANMEDA
 * */
@Mod.EventBusSubscriber(modid = "maring", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MarPlaceEdit {
	
	private static ResourceKey<Level> marKey = ResourceKey.create(Registries.DIMENSION, new ResourceLocation("maring", "maringmar"));
	
	@SuppressWarnings("deprecation")
	private static final LazyLoadedValue<BlockState> A_AIR_BLOCK_STATE = 
		    new LazyLoadedValue<>(() -> BlockRegister.A_AIR.get().defaultBlockState());
	//private static Supplier<BlockState> A_AIR_BLOCK_STATE = 
	//		() -> {return BlockRegister.A_AIR.get().defaultBlockState();};
	
	@SubscribeEvent
    public static void onBlockPlaced(BlockEvent.EntityPlaceEvent event) {
		if(event.getLevel().isClientSide()) return;
		if(!event.getEntity().level().dimension().equals(marKey)) return;
 		if(checkAnyAAIR(event.getLevel(),event.getPos())) return;
		Block block = event.getPlacedBlock().getBlock();
		EnvironmentData environmentData=null;
    	if (event.getLevel() instanceof ServerLevel serverLevel) {
    	    // 现在可以安全地使用 serverLevel，调用 EnvironmentData.get(serverLevel)
    	    environmentData = EnvironmentData.get(serverLevel);
    	}
        if (posIsSapling(block)) {
        	if(environmentData!=null&&environmentData.suitablePLANTH()) return;
            event.getLevel().setBlock(event.getPos(), Blocks.DEAD_BUSH.defaultBlockState(), 2);
            return;
        }else if(block == Blocks.WALL_TORCH||block == Blocks.TORCH||block == Blocks.CAMPFIRE) {
    	    if(environmentData!=null&&environmentData.canBurn()) return;
        	event.getLevel().setBlock(event.getPos(), Blocks.AIR.defaultBlockState(), 2);
            ItemStack stickStack = new ItemStack(Items.STICK);
            Containers.dropContents(event.getEntity().level(),event.getPos(), NonNullList.of(ItemStack.EMPTY, stickStack));
            return;
        }else if(posIsFlower(block)) {
        	if(environmentData!=null&&environmentData.suitablePLANTL()) return;
        	event.getLevel().setBlock(event.getPos(), Blocks.DEAD_BUSH.defaultBlockState(), 2);
        	return;
        }else if(block == Blocks.GRASS_BLOCK||block == Blocks.MOSS_BLOCK||block == Blocks.MOSS_CARPET) {
        	if(environmentData!=null&&environmentData.suitableMOSS()) return;
        	event.getLevel().setBlock(event.getPos(), Blocks.DIRT.defaultBlockState(), 2);
        	return;
        }else if(block == Blocks.TURTLE_EGG||block == Blocks.SNIFFER_EGG) {
        	if(environmentData!=null&&environmentData.suitableANIMAL()) return;
        	event.getLevel().setBlock(event.getPos(), Blocks.AIR.defaultBlockState(), 2);
            ItemStack egg = new ItemStack(Items.EGG);
            Containers.dropContents(event.getEntity().level(),event.getPos(), NonNullList.of(ItemStack.EMPTY, egg));
        	return;
        }else if(block == Blocks.WATER||block == Blocks.ICE) {
        	if(environmentData!=null&&environmentData.iceMelt()) return;
        	event.getLevel().setBlock(event.getPos(), Blocks.PACKED_ICE.defaultBlockState(), 2);
        	return;
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
        return block == Blocks.OAK_SAPLING ||
                block == Blocks.SPRUCE_SAPLING ||
                block == Blocks.BIRCH_SAPLING ||
                block == Blocks.JUNGLE_SAPLING ||
                block == Blocks.ACACIA_SAPLING ||
                block == Blocks.DARK_OAK_SAPLING||
                block == Blocks.BAMBOO_SAPLING||
                block == Blocks.FLOWERING_AZALEA||
                block == Blocks.AZALEA||
                block == Blocks.CHERRY_SAPLING||
                block == Blocks.MANGROVE_PROPAGULE;
	}
	
	public static boolean posIsFlower(Block block) {
	    return block == Blocks.TORCHFLOWER ||
	           block == Blocks.CHORUS_FLOWER ||
	           block == Blocks.DANDELION ||
	           block == Blocks.POPPY ||
	           block == Blocks.BLUE_ORCHID ||
	           block == Blocks.ALLIUM ||
	           block == Blocks.AZURE_BLUET ||
	           block == Blocks.RED_TULIP ||
	           block == Blocks.ORANGE_TULIP ||
	           block == Blocks.WHITE_TULIP ||
	           block == Blocks.PINK_TULIP ||
	           block == Blocks.OXEYE_DAISY ||
	           block == Blocks.CORNFLOWER ||
	           block == Blocks.LILY_OF_THE_VALLEY ||
	           block == Blocks.SUNFLOWER ||
	           block == Blocks.LILAC ||
	           block == Blocks.ROSE_BUSH ||
	           block == Blocks.PEONY ||
	           block == Blocks.TALL_GRASS ||
	           block == Blocks.LARGE_FERN ||
    		   block == Blocks.PINK_PETALS ||
	           block == Blocks.FERN;
	}
}
