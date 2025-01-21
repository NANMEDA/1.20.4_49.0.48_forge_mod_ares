package command.environment;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import util.mar.EnvironmentData;

public class EnvironmentDisplay implements Command<CommandSourceStack> {
    public static final Command<CommandSourceStack> INSTANCE = new EnvironmentDisplay();

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        EnvironmentData environmentData = EnvironmentData.get(context.getSource().getLevel());

        if (environmentData != null) {
            // Display separator
            context.getSource().getPlayer().sendSystemMessage(
                Component.translatable("maring.command.environment.display.separator")
                    .setStyle(Style.EMPTY.withColor(ChatFormatting.GRAY))
            );

            // Display each parameter
            displayParameter(context, "humid", environmentData.getHumid());
            displayParameter(context, "oxygen", environmentData.getOxygen());
            displayParameter(context, "pressure", environmentData.getPressure());
            displayParameter(context, "temperature", environmentData.getTemperature());
            displayParameter(context, "mag", environmentData.getMag());
        } else {
            context.getSource().getPlayer().sendSystemMessage(
                Component.translatable("maring.command.environment.display.noData")
                    .setStyle(Style.EMPTY.withColor(ChatFormatting.RED))
            );
        }

        return 1;
    }

    private void displayParameter(CommandContext<CommandSourceStack> context, String parameter, double value) {
        context.getSource().getPlayer().sendSystemMessage(
            Component.translatable("maring.command.environment.display." + parameter, String.format("%.6f", value))
                .setStyle(Style.EMPTY.withColor(ChatFormatting.GREEN))
        );
    }
}
