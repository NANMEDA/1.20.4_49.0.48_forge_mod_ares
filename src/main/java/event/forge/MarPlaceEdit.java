package event.forge;

import net.minecraft.core.NonNullList;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "maring", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MarPlaceEdit {
	
	@SubscribeEvent
    public static void onBlockPlaced(BlockEvent.EntityPlaceEvent event) {
		if(event.getLevel().isClientSide()) return;
		if(event.getLevel().getBiome(event.getPos()).get().getFoliageColor() != 9334293) return;
		Block block = event.getPlacedBlock().getBlock();
        if (posIsSapling(block)) {
            event.getLevel().setBlock(event.getPos(), Blocks.DEAD_BUSH.defaultBlockState(), 2);
            return;
        }else if(block == Blocks.TORCH||block == Blocks.CAMPFIRE) {
        	event.getLevel().setBlock(event.getPos(), Blocks.AIR.defaultBlockState(), 2);
            ItemStack stickStack = new ItemStack(Items.STICK);
            
            //ItemEntity itemEntity = new ItemEntity(event.getLevel(), event.getPos().getX(), event.getPos().getY(), event.getPos().getZ(), stickStack);
            //event.getLevel().addFreshEntity(itemEntity)
            Containers.dropContents(event.getEntity().level(),event.getPos(), NonNullList.of(ItemStack.EMPTY, stickStack));
            return;
        }else if(posIsFlower(block)) {
        	event.getLevel().setBlock(event.getPos(), Blocks.DEAD_BUSH.defaultBlockState(), 2);
        	return;
        }else if(block == Blocks.GRASS_BLOCK||block == Blocks.MOSS_BLOCK) {
        	event.getLevel().setBlock(event.getPos(), Blocks.DIRT.defaultBlockState(), 2);
        	return;
        }else if(block == Blocks.TURTLE_EGG||block == Blocks.SNIFFER_EGG) {
        	event.getLevel().setBlock(event.getPos(), Blocks.AIR.defaultBlockState(), 2);
            ItemStack egg = new ItemStack(Items.EGG);
            Containers.dropContents(event.getEntity().level(),event.getPos(), NonNullList.of(ItemStack.EMPTY, egg));
        	return;
        }
        
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
	           block == Blocks.LARGE_FERN ||
	           block == Blocks.SHORT_GRASS ||
    		   block == Blocks.PINK_PETALS ||
	           block == Blocks.FERN;
	}
}
