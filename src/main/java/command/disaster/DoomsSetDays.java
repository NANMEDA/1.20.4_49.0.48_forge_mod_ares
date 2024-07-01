package command.disaster;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import event.disaster.DisasterConfig;
import event.disaster.bad.DoomsDay;
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
        DoomsDay.DOOMS_DAY_START = (day*DisasterConfig.DAY_TICKS + DisasterConfig.NIGHT_TICKS); 
        DoomsDay.DOOMS_DAY_TOMORROW = (DoomsDay.DOOMS_DAY_START - DisasterConfig.DAY_TICKS);
        DoomsDay.DOOMS_DAY_END = (DoomsDay.DOOMS_DAY_START + DisasterConfig.DOOM_EVENT_DURATION);
        
        context.getSource().getPlayer().sendSystemMessage(Component.translatable("maring.command.doomsdaycome.set",day));
        return 1; // 返回1表示命令成功执行
    }
}
