package com.main.maring.block.norm.fastbuild;

import java.util.Random;

import com.main.maring.block.norm.BlockRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class DormHelper {
	
    private static final BlockState AIR_STATE = BlockRegister.A_AIR.get().defaultBlockState();
    private static final BlockState UNBROKEN_FOG_STATE = BlockRegister.unbrokenfog_BLOCK.get().defaultBlockState();
    
	/**
	 * 随机取点，检查是否占用
	 * @param level
	 * @param centerPos
	 * @param r 半径，默认是一个基座2格深度，上面一个半球体
	 * @param player
	 * @return
	 */
	public static boolean checkPlaceDormOccupied(Level level,BlockPos centerPos,int r, Player player) {
		return checkPlaceDormOccupied(level,centerPos, new Vec3i(-r, -2, -r), new Vec3i(r, r, r), player, 25);		
	}
	
	/**
	 * 随机取点，检查是否占用
	 * @param level
	 * @param centerPos
	 * @param offset0 默认对称
	 * @param player
	 * @return
	 */
	public static boolean checkPlaceDormOccupied(Level level,BlockPos centerPos,Vec3i offset0, Player player) {
		return checkPlaceDormOccupied(level,centerPos, offset0, new Vec3i(-offset0.getX(),-offset0.getY(),-offset0.getZ()), player, 50);		
	}
	
	public static boolean checkPlaceDormOccupied(Level level,BlockPos centerPos,Vec3i offset0,Vec3i offset1, Player player) {
		return checkPlaceDormOccupied(level,centerPos, offset0, offset1, player, 25);		
	}
	
	/**
	 * 随机取点，检查是否占用
	 * @param level
	 * @param centerPos 中心点
	 * @param offset0 
	 * @param offset1 这两个需要一正一负（一般）
	 * @param player 
	 * @param time 检测次数
	 * @return 被占用 - true
	 */
	public static boolean checkPlaceDormOccupied(Level level,BlockPos centerPos, Vec3i offset0, Vec3i offset1, Player player, int time) {
		if(centerPos.getY()+Math.max(offset0.getY(), offset1.getY())>=380) {
        	player.sendSystemMessage(Component.translatable("dorm.place.unable.toohigh"));
            return true;
		}
		if(centerPos.getY()+Math.min(offset0.getY(), offset1.getY())<-60) {
        	player.sendSystemMessage(Component.translatable("dorm.place.unable.toolow"));
            return true;
		}
		
	    Random random = new Random();
	    int minX = Math.min(offset0.getX(), offset1.getX());
	    int maxX = Math.max(offset0.getX(), offset1.getX());
	    int d_X = maxX-minX +1;
	    int minY = Math.min(offset0.getY(), offset1.getY());
	    int maxY = Math.max(offset0.getY(), offset1.getY());
	    int d_Y = maxY-minY +1;
	    int minZ = Math.min(offset0.getZ(), offset1.getZ());
	    int maxZ = Math.max(offset0.getZ(), offset1.getZ());
	    int d_Z = maxZ-minZ +1;
	    if(time>=d_X*d_Y*d_Z/2) time = d_X*d_Y*d_Z/2;
	    for (int i = 0; i < time; i++) {
	        int randomX = centerPos.getX() + minX + random.nextInt(d_X);
	        int randomY = centerPos.getY() + minY + random.nextInt(d_Y);
	        int randomZ = centerPos.getZ() + minZ + random.nextInt(d_Z);
	        BlockPos randomPos = new BlockPos(randomX, randomY, randomZ);

	        if (randomPos != centerPos && !level.getBlockState(randomPos).is(Blocks.AIR)) {
	        	player.sendSystemMessage(Component.translatable("dorm.place.unable.noenoughplace"));
	            return true;
	        }
	    }
	    player.sendSystemMessage(Component.translatable("dorm.place.able"));
	    return false;
	}
	
	public static BlockPos fromCenterGetControlBlockPos(Level level,BlockPos center) {
		int max = 64;
		BlockPos pos = center.mutable();
		BlockState state;
		while (max>0) {
			state = level.getBlockState(pos);
			if(!state.isAir()&&!state.is(AIR_STATE.getBlock())&&!state.is(UNBROKEN_FOG_STATE.getBlock())) {
				return pos;
			}
			pos = pos.below();
			max--;
		}
		return BlockPos.ZERO;
	}
}
