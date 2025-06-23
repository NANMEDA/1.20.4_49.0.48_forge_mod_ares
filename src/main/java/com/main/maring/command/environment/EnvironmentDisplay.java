package com.main.maring.command.environment;

import com.main.maring.util.enums.EnvironmentEnum;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import com.main.maring.util.mar.EnvironmentData;

public class EnvironmentDisplay implements Command<CommandSourceStack> {
    public static final Command<CommandSourceStack> INSTANCE = new EnvironmentDisplay();
    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        EnvironmentData environmentData = EnvironmentData.get(context.getSource().getLevel());

        if (environmentData == null) {
            context.getSource().getPlayer().sendSystemMessage(
                    Component.translatable("maring.command.environment.display.noData")
                            .withStyle(ChatFormatting.RED)
            );
            return 0;
        }

        // 显示分隔线
        context.getSource().getPlayer().sendSystemMessage(
                Component.literal("=== Environment Data ===").withStyle(ChatFormatting.GRAY)
        );

        // 依次展示参数
        displayParameter(context, "humid", environmentData.getHumid(), environmentData.getEnvironmentEnum("humid"));
        displayParameter(context, "oxygen", environmentData.getOxygen(), environmentData.getEnvironmentEnum("oxygen"));
        displayParameter(context, "pressure", environmentData.getPressure(), environmentData.getEnvironmentEnum("pressure"));
        displayParameter(context, "temperature", environmentData.getTemperature(), environmentData.getEnvironmentEnum("temperature"));
        displayParameter(context, "mag", environmentData.getMag(), EnvironmentEnum.NUL); // 没有enum判定

        // 总结展示总体适合情况
        context.getSource().getPlayer().sendSystemMessage(Component.literal(""));

        displaySuitability(context, "MOSS", environmentData.suitableMOSS());
        displaySuitability(context, "PLANTL", environmentData.suitablePLANTL());
        displaySuitability(context, "PLANTH", environmentData.suitablePLANTH());
        displaySuitability(context, "ANIMAL", environmentData.suitableANIMAL());

        return 1;
    }

    private void displayParameter(CommandContext<CommandSourceStack> context, String name, double value, EnvironmentEnum env) {
        ChatFormatting color = switch (env) {
            case PERFECT -> ChatFormatting.AQUA;
            case ALIVE -> ChatFormatting.GREEN;
            case STRUGGLE -> ChatFormatting.YELLOW;
            case DEAD -> ChatFormatting.RED;
            default -> ChatFormatting.GRAY;
        };

        String formatted = String.format("%.6f", value);
        context.getSource().getPlayer().sendSystemMessage(
                Component.translatable("maring.command.environment.display." + name, formatted, env.name())
                        .withStyle(color)
        );
    }

    private void displaySuitability(CommandContext<CommandSourceStack> context, String name, boolean ok) {
        ChatFormatting color = ok ? ChatFormatting.GREEN : ChatFormatting.RED;
        context.getSource().getPlayer().sendSystemMessage(
                Component.literal(name + " SUITABLE: " + (ok ? "YES" : "NO"))
                        .withStyle(color)
        );
    }
}
