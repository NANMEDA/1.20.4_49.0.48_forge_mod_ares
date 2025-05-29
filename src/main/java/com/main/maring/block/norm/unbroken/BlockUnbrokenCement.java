package com.main.maring.block.norm.unbroken;

import java.util.Random;

import com.main.maring.block.norm.BlockRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;

@SuppressWarnings("deprecation")
public class BlockUnbrokenCement extends Block {
	
	public static final String global_name = "unbroken_cement";
	
	private LazyLoadedValue<BlockState> Green = new LazyLoadedValue<>(()-> BlockRegister.unbrokengreen_BLOCK.get().defaultBlockState());
	private LazyLoadedValue<BlockState> Lightblue = new LazyLoadedValue<>(()-> BlockRegister.unbrokenlightblue_BLOCK.get().defaultBlockState());
	private LazyLoadedValue<BlockState> Conductor = new LazyLoadedValue<>(()-> BlockRegister.unbrokenconductor_BLOCK.get().defaultBlockState());
	
	public BlockUnbrokenCement(Properties p_49795_) {
		super(p_49795_
				.strength(-1.0F, 3600000.0f)
				.mapColor(MapColor.TERRACOTTA_WHITE)
				.sound(SoundType.STONE)
				);
	}
	
	
    @Override
    public InteractionResult use(BlockState blockstate, Level level, BlockPos pos, Player player, InteractionHand interactionhand, BlockHitResult blockHitResult) {
        super.use(blockstate, level, pos, player, interactionhand, blockHitResult);
        if(level.isClientSide) return  InteractionResult.PASS;
        if(interactionhand != InteractionHand.MAIN_HAND) return InteractionResult.PASS; 
        ItemStack handItemStack = player.getItemInHand(interactionhand);
        Random rd = new Random();
        if(handItemStack.getItem()==Items.GREEN_DYE) {
        	deleteDye(rd, player, handItemStack);
        	level.setBlockAndUpdate(pos, Green.get());
        	return InteractionResult.SUCCESS;
        }else if(handItemStack.getItem()==Items.LIGHT_BLUE_DYE) {
        	deleteDye(rd, player, handItemStack);
        	level.setBlockAndUpdate(pos, Lightblue.get());
        	return InteractionResult.SUCCESS;
        }else if(handItemStack.getItem()==Items.COPPER_INGOT) {
        	level.setBlockAndUpdate(pos, Conductor.get());
        	handItemStack.shrink(1);
    		player.setItemInHand(InteractionHand.MAIN_HAND, handItemStack);
        	return InteractionResult.SUCCESS;
        }
        else{
        	return InteractionResult.PASS;
        }
    }
    
    private void deleteDye(Random rd,Player player,ItemStack stack){
    	if(rd.nextInt(32)==1) {
    		stack.shrink(1);
    		player.setItemInHand(InteractionHand.MAIN_HAND, stack);
    		
    	}
    }
}
