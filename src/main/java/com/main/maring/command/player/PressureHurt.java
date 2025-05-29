package com.main.maring.command.player;

import com.main.maring.ExtraConfig;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;

public class PressureHurt implements Command<CommandSourceStack>{

	public static final Command<CommandSourceStack> INSTANCE = new PressureHurt();
	
    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        boolean value = context.getArgument("value", Boolean.class);
        //BiomeEffectApplier.WILL_PRESSURE_HURT = value;
        ExtraConfig.WILL_PRESSURE_HURT = value;
        if(value) {
        	context.getSource().getPlayer().sendSystemMessage(Component.translatable("maring.command.willpressurehurt.true"));
        }else {
        	context.getSource().getPlayer().sendSystemMessage(Component.translatable("maring.command.willpressurehurt.false"));
        }
        ExtraConfig.save(context.getSource().getServer());
        return 1;
    }
}
