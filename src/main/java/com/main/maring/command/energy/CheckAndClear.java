package com.main.maring.command.energy;

import java.util.Set;

import com.main.maring.util.net.EnergyNet;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import com.main.maring.machine.energy.IEnergy;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import com.main.maring.util.net.EnergyNet.EnergyEnum;
import com.main.maring.util.net.EnergyNetProcess;
public class CheckAndClear implements Command<CommandSourceStack> {
    public static final Command<CommandSourceStack> INSTANCE = new CheckAndClear();
    
    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        var energyNets = EnergyNetProcess.getAllEnergyNets();
        var level = context.getSource().getLevel();
        if (energyNets != null && !energyNets.isEmpty()) {
        	for (EnergyNet energyNet : energyNets) {
        	    for (EnergyEnum ee : EnergyEnum.values()) { // 遍历 EnergyEnum 中的每个值
        	        Set<BlockPos> r = energyNet.getSet(ee); // 获取对应的 BlockPos 集合
        	        for (BlockPos pos : r) {
        	            if (level.dimension().location().equals(energyNet.getDimension())) { // 确保维度匹配
        	                if (level.getBlockEntity(pos) instanceof IEnergy) {
        	                    continue; // 如果是 IEnergy 类型，跳过
        	                } else {
        	                    // 否则移除 BlockPos 和所有相关的边
        	                    energyNet.removeBlockPos(pos, ee);
        	                    energyNet.removeAllEdgesFromPoint(pos);
        	                }
        	            }
        	        }
        	    }
        	    // 向玩家发送消息
        	    context.getSource().getPlayer().sendSystemMessage(
        	        Component.translatable("maring.command.energyNet.clear")
        	    );
        	}
        } else {
            context.getSource().getPlayer().sendSystemMessage(
                Component.translatable("maring.command.energyNet.display.noNets")
                    .setStyle(Style.EMPTY.withColor(ChatFormatting.RED))
            );
        }

        return 1;
    }

}