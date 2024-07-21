package com.item;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class ItemJunctionConnector extends Item {
    public static final String global_name = "junction_connector";
	private BlockPos startPos = null;
    private BlockPos endPos = null;
    private int startDirection = 0; // Assuming you might want to set directions later
    private int endDirection = 0;   // Assuming you might want to set directions later

    public ItemJunctionConnector(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Player player = context.getPlayer();
        BlockPos clickedPos = context.getClickedPos();

        if (!level.isClientSide) {
        	if(!level.getBlockState(clickedPos).is(block.norm.fastbuild.Register.dormjunctioncontrol_BLOCK.get())) {
        		player.sendSystemMessage(Component.literal("error"));
        		return InteractionResult.FAIL;
        	}
        	
            if (startPos == null) {
                startPos = clickedPos;
                startDirection = level.getBlockState(clickedPos).getValue(BlockStateProperties.LEVEL);
                player.sendSystemMessage(Component.literal(String.format("First Point X:%d, Y:%d, Z:%d; Direction:%d", startPos.getX(), startPos.getY(), startPos.getZ(),startDirection)));
            } else if (endPos == null) {
                endPos = clickedPos;
                endDirection = level.getBlockState(clickedPos).getValue(BlockStateProperties.LEVEL);
                player.sendSystemMessage(Component.literal(String.format("Second Point X:%d, Y:%d, Z:%d; Direction:%d", endPos.getX(), endPos.getY(), endPos.getZ(),endDirection)));
                // Perform the chain code operation
                block.norm.fastbuild.JunctionHelper.followChainCode(level,startPos, endPos, startDirection, endDirection, block.norm.fastbuild.JunctionHelper.birthConnection(startPos, startDirection, endPos, endDirection,player));

                // Clear the positions
                startPos = null;
                endPos = null;
            }
        }

        return InteractionResult.SUCCESS;
    }
}
