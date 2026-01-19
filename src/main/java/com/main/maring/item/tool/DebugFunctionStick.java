package com.main.maring.item.tool;

import com.main.maring.block.entity.neutral.fastbuild.DormJunctionControlEntity;
import com.main.maring.block.norm.BlockRegister;
import com.main.maring.block.norm.fastbuild.FastBuildRegister;
import com.main.maring.block.norm.fastbuild.HermitePlacerManager;
import com.main.maring.block.norm.fastbuild.JunctionHelper;
import com.main.maring.event.forge.HermiteTaskController;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class DebugFunctionStick extends Item {

  	public static final String global_name = "debug_function_stick";
    private BlockPos startPos = null;
    private BlockPos endPos = null;
    private int startDirection = 0;
    private int endDirection = 0;


    public DebugFunctionStick(Properties p_41383_) {
        super(p_41383_.stacksTo(1));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Player player = context.getPlayer();
        BlockPos clickedPos = context.getClickedPos();

        if (!level.isClientSide) {
            if(!level.getBlockState(clickedPos).is(FastBuildRegister.dormjunctioncontrol_BLOCK.get())) {
                player.sendSystemMessage(Component.literal("error"));
                return InteractionResult.CONSUME;
            }

            if (startPos == null) {
                startPos = clickedPos;
                startDirection = level.getBlockState(clickedPos).getValue(BlockStateProperties.LEVEL);
                player.sendSystemMessage(Component.literal(String.format("First Point X:%d, Y:%d, Z:%d; Direction:%d", startPos.getX(), startPos.getY(), startPos.getZ(),startDirection)));
                return InteractionResult.SUCCESS;
            } else if (endPos == null) {
                endPos = clickedPos;
                endDirection = level.getBlockState(clickedPos).getValue(BlockStateProperties.LEVEL);
                player.sendSystemMessage(Component.literal(String.format("Second Point X:%d, Y:%d, Z:%d; Direction:%d", endPos.getX(), endPos.getY(), endPos.getZ(),endDirection)));
                double[] dir0 = getDirectionUnitVector(startDirection);
                double[] dir1 = getDirectionUnitVector((endDirection+4)%8);

                if(level instanceof ServerLevel serverLevel) HermiteTaskController.enqueue(
                        new HermitePlacerManager(
                                startPos.above().above().offset(JunctionHelper.getDirectionVector(startDirection)),
                                dir0,
                                endPos.above().above().offset(JunctionHelper.getDirectionVector(endDirection)),
                                dir1, serverLevel,startPos,endPos));
                startPos = null;
                endPos = null;
                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.PASS;
    }

    private static double[] getDirectionUnitVector(int direction) {
        Vec3i vec = switch (direction % 8) {
            case 0 -> new Vec3i(1, 0, 0);    // East
            case 1 -> new Vec3i(1, 0, -1);   // East-North
            case 2 -> new Vec3i(0, 0, -1);   // North
            case 3 -> new Vec3i(-1, 0, -1);  // West-North
            case 4 -> new Vec3i(-1, 0, 0);   // West
            case 5 -> new Vec3i(-1, 0, 1);   // West-South
            case 6 -> new Vec3i(0, 0, 1);    // South
            case 7 -> new Vec3i(1, 0, 1);    // East-South
            default -> throw new IllegalArgumentException("Invalid direction: " + direction);
        };

        double length = Math.sqrt(vec.getX() * vec.getX() + vec.getZ() * vec.getZ()); // 只考虑水平分量
        return new double[] { vec.getX() / length, 0, vec.getZ() / length };
    }



}
