package command.player;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import boime.BiomeEffectApplier;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;

public class PressureHurt implements Command<CommandSourceStack>{

	public static final Command<CommandSourceStack> INSTANCE = new PressureHurt();
	
    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        boolean value = context.getArgument("value", Boolean.class);
        BiomeEffectApplier.WILL_PRESSURE_HURT = value;
        if(value) {
        	context.getSource().getPlayer().sendSystemMessage(Component.translatable("maring.command.willpressurehurt.true"));
        }else {
        	context.getSource().getPlayer().sendSystemMessage(Component.translatable("maring.command.willpressurehurt.false"));
        }
        return 1;
    }
}
