package command.environment;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import util.mar.EnvironmentData;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;


public class EnvironmentSet implements Command<CommandSourceStack> {
    public static final Command<CommandSourceStack> INSTANCE = new EnvironmentSet();

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        String parameter = StringArgumentType.getString(context, "parameter");
        int value = IntegerArgumentType.getInteger(context, "value");

        EnvironmentData environmentData = EnvironmentData.get(context.getSource().getLevel());

        if (environmentData != null) {
            boolean updated = updateParameter(environmentData, parameter, value);

            if (updated) {
                context.getSource().getPlayer().sendSystemMessage(
                    Component.translatable("maring.command.environment.set.success", parameter, value)
                        .setStyle(Style.EMPTY.withColor(ChatFormatting.GREEN))
                );
            } else {
                context.getSource().getPlayer().sendSystemMessage(
                    Component.translatable("maring.command.environment.set.invalid", parameter)
                        .setStyle(Style.EMPTY.withColor(ChatFormatting.RED))
                );
            }
        } else {
            context.getSource().getPlayer().sendSystemMessage(
                Component.translatable("maring.command.environment.set.noData")
                    .setStyle(Style.EMPTY.withColor(ChatFormatting.RED))
            );
        }

        return 1;
    }

    private boolean updateParameter(EnvironmentData environmentData, String parameter, int value) {
        switch (parameter) {
            case "humid":
                environmentData.setHumid(value);
                return true;
            case "oxygen":
                environmentData.setOxygen(value);
                return true;
            case "pressure":
                environmentData.setPressure(value);
                return true;
            case "temperature":
                environmentData.setTemperature(value);
                return true;
            case "mag":
                environmentData.setMag(value);
                return true;
            default:
                return false;
        }
    }
}
