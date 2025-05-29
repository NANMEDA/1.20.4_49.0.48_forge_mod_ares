package com.main.maring.command.disaster;

import com.main.maring.ExtraConfig;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

public class DoomsSetDays implements Command<CommandSourceStack> {
	public static final Command<CommandSourceStack> INSTANCE = new DoomsSetDays();
	
    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        int day = context.getArgument("value", Integer.class);
        if(day<10||day>150) {
        	context.getSource().getPlayer().sendSystemMessage(Component.translatable("maring.command.doomsdaycome.set.inlegal")
        			.setStyle(Style.EMPTY.withColor(ChatFormatting.RED)));
        	return 1;
        }
        //DoomsDay.DOOMS_DAY_START = (day*ExtraConfig.DAY_TICKS + ExtraConfig.NIGHT_TICKS); 
        //DoomsDay.DOOMS_DAY_TOMORROW = (DoomsDay.DOOMS_DAY_START - ExtraConfig.DAY_TICKS);
        //DoomsDay.DOOMS_DAY_END = (DoomsDay.DOOMS_DAY_START + ExtraConfig.DOOM_EVENT_DURATION);
        
        ExtraConfig.DOOMS_DAY_START = (day*ExtraConfig.DAY_TICKS + ExtraConfig.NIGHT_TICKS); 
        ExtraConfig.DOOMS_DAY_TOMORROW = (ExtraConfig.DOOMS_DAY_START - ExtraConfig.DAY_TICKS);
        ExtraConfig.DOOMS_DAY_END = (ExtraConfig.DOOMS_DAY_START + ExtraConfig.DOOM_EVENT_DURATION);
        
        context.getSource().getPlayer().sendSystemMessage(Component.translatable("maring.command.doomsdaycome.set",day));
        ExtraConfig.save(context.getSource().getServer());
        return 1;
    }
}
