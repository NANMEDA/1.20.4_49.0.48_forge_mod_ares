package com.main.maring.command.energy;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

public class EnergyNet implements Command<CommandSourceStack> {
	public static final Command<CommandSourceStack> INSTANCE = new EnergyNet();
	
    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        int speed = context.getArgument("value", Integer.class);
        if(speed<1) {
        	context.getSource().getPlayer().sendSystemMessage(Component.translatable("maring.command.energy.calcspeed.toofast")
        			.setStyle(Style.EMPTY.withColor(ChatFormatting.RED)));
        	return 1;
        }else if(speed>200) {
        	context.getSource().getPlayer().sendSystemMessage(Component.translatable("maring.command.energy.calcspeed.tooslow")
        			.setStyle(Style.EMPTY.withColor(ChatFormatting.RED)));
        	return 1;
        }
        
        context.getSource().getPlayer().sendSystemMessage(Component.translatable("maring.command.energy.calcspeed.set",speed));
        return 1;
    }
}
