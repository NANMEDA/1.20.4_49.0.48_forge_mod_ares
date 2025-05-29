package com.main.maring.command.disaster;

import com.main.maring.ExtraConfig;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;

public class DisasterDo implements Command<CommandSourceStack>{

	public static final Command<CommandSourceStack> INSTANCE = new DisasterDo();
	
    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        boolean value = context.getArgument("value", Boolean.class);
        //DoomsDay.DOOMS_WILL_ARRIVE = value;
        ExtraConfig.DOOMS_WILL_ARRIVE = value;
        if(value) {
        	context.getSource().getPlayer().sendSystemMessage(Component.translatable("maring.command.doomsdaycome.change.true"));
        }else {
        	context.getSource().getPlayer().sendSystemMessage(Component.translatable("maring.command.doomsdaycome.change.false"));
        }
        ExtraConfig.save(context.getSource().getServer());
        return 1; // 返回1表示命令成功执行
    }
}
