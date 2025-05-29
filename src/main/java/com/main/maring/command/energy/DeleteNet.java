package com.main.maring.command.energy;

import java.util.Set;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import com.main.maring.machine.energy.EnergyEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import com.main.maring.util.net.EnergyNet;
import com.main.maring.util.net.EnergyNetProcess;
import com.main.maring.util.net.EnergyNet.EnergyEnum;

public class DeleteNet implements Command<CommandSourceStack> {
	public static final Command<CommandSourceStack> INSTANCE = new DeleteNet();
	
    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        long id = context.getArgument("value", Long.class);
        if(id<1) {
        	context.getSource().getPlayer().sendSystemMessage(Component.translatable("maring.command.energyNet.unableid")
        			.setStyle(Style.EMPTY.withColor(ChatFormatting.RED)));
        	return 1;
        }
        if(!EnergyNetProcess.checkEnergyNet(id)) {
        	context.getSource().getPlayer().sendSystemMessage(Component.translatable("maring.command.energyNet.unableid")
        			.setStyle(Style.EMPTY.withColor(ChatFormatting.RED)));
        	return 1;
        }
        EnergyNet net = EnergyNetProcess.getEnergyNet(id);
        if(net.getDimension().equals(context.getSource().getLevel().dimension().location())) {
    	    
        	for (EnergyEnum ee : EnergyEnum.values()) { // 遍历 EnergyEnum 中的每个值
    	        Set<BlockPos> r = net.getSet(ee); // 获取对应的 BlockPos 集合
    	        for (BlockPos pos : r) {
	                if (context.getSource().getLevel().getBlockEntity(pos) instanceof EnergyEntity eb) {
	                    eb.cleanNet();
	                } else {
	                }
    	        }
    	    }
        	EnergyNetProcess.deleteEnergyNet(id);
        	context.getSource().getPlayer().sendSystemMessage(Component.translatable("maring.command.energyNet.delete")
        			.setStyle(Style.EMPTY.withColor(ChatFormatting.WHITE)));
        	return 1;
        }else {
        	context.getSource().getPlayer().sendSystemMessage(Component.translatable("maring.command.energyNet.unabledimension")
        			.setStyle(Style.EMPTY.withColor(ChatFormatting.RED)));
        	return 1;
        }
    }
}
