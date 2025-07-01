package com.main.maring.item.tool;

import com.main.maring.block.entity.BlockEntityRegister;
import com.main.maring.block.entity.neutral.fastbuild.DormJunctionControlEntity;
import com.main.maring.block.norm.BlockRegister;
import com.main.maring.block.norm.fastbuild.FastBuildRegister;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ItemChangeStick extends Item {
    
    private static final BlockState UNBROKEN_CEMENT_BLOCKSTATE = BlockRegister.unbrokencement_BLOCK.get().defaultBlockState();
    private static final BlockState UNBROKEN_GLASS_BLOCKSTATE = BlockRegister.unbrokenglass_BLOCK.get().defaultBlockState();
	public static final String global_name = "change_stick";
            
    public ItemChangeStick(Properties p_41383_) {
        super(p_41383_.stacksTo(1));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState currentBlockState = level.getBlockState(pos);

        if (!level.isClientSide) {
            if (currentBlockState == UNBROKEN_CEMENT_BLOCKSTATE) {
                level.setBlockAndUpdate(pos, UNBROKEN_GLASS_BLOCKSTATE);
                return InteractionResult.SUCCESS;
            } else if (currentBlockState == UNBROKEN_GLASS_BLOCKSTATE) {
                level.setBlockAndUpdate(pos, UNBROKEN_CEMENT_BLOCKSTATE);
                return InteractionResult.SUCCESS;
            } else if(currentBlockState.getBlock() == FastBuildRegister.dormjunctioncontrol_BLOCK.get()){
                if(level.getBlockEntity(pos) instanceof DormJunctionControlEntity de){
                    BlockState recentState = level.getBlockState(pos.above());
                    if(recentState.isAir()){
                        de._genDoor(BlockRegister.unbrokenfog_BLOCK.get().defaultBlockState());
                        return InteractionResult.SUCCESS;
                    }else if(recentState == BlockRegister.unbrokenfog_BLOCK.get().defaultBlockState()){
                        de._genDoor(UNBROKEN_CEMENT_BLOCKSTATE);
                        return InteractionResult.SUCCESS;
                    }else{
                        if(de.connected()){
                            de._genDoor(Blocks.AIR.defaultBlockState());
                            return InteractionResult.SUCCESS;
                        }else {
                            de._genDoor(BlockRegister.unbrokenfog_BLOCK.get().defaultBlockState());
                            return InteractionResult.SUCCESS;
                        }
                    }
                }
            }
        }

        return InteractionResult.PASS;
    }
    
}
