package command.energy;

import java.util.Set;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.level.Level;
import util.net.EnergyNet.EnergyEnum;
import util.net.EnergyNetProcess;
public class Display implements Command<CommandSourceStack> {
    public static final Command<CommandSourceStack> INSTANCE = new Display();

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        var energyNets = EnergyNetProcess.getAllEnergyNets();
        var level = context.getSource().getLevel();
        if (energyNets != null && !energyNets.isEmpty()) {
            for (util.net.EnergyNet energyNet : energyNets) {
                // Displaying the EnergyNet info
                context.getSource().getPlayer().sendSystemMessage(
                    Component.translatable("maring.command.energyNet.display.separator")
                );

                // Display ID
                context.getSource().getPlayer().sendSystemMessage(
                    Component.translatable("maring.command.energyNet.display.id", energyNet.getId())
                        .setStyle(Style.EMPTY.withColor(ChatFormatting.GOLD))
                );

                // Display Supply Level
                context.getSource().getPlayer().sendSystemMessage(
                    Component.translatable("maring.command.energyNet.display.level", energyNet.getSupplyLevel())
                        .setStyle(Style.EMPTY.withColor(ChatFormatting.GOLD))
                );

                // Display all Sets using the getSet method and EnergyEnum
                displaySet(EnergyEnum.CONSUMER, energyNet, context,level);
                displaySet(EnergyEnum.PRODUCER, energyNet, context,level);
                displaySet(EnergyEnum.STORAGE, energyNet, context,level);
                displaySet(EnergyEnum.TRANS, energyNet, context,level);
                displaySet(EnergyEnum.NULL, energyNet, context,level);

            }
        } else {
            context.getSource().getPlayer().sendSystemMessage(
                Component.translatable("maring.command.energyNet.display.noNets")
                    .setStyle(Style.EMPTY.withColor(ChatFormatting.RED))
            );
        }

        return 1;
    }
    
    private void displaySet(EnergyEnum energyEnum, util.net.EnergyNet energyNet, CommandContext<CommandSourceStack> context, Level level) {
        Set<BlockPos> set = energyNet.getSet(energyEnum);
        if (set != null && !set.isEmpty()) {
            // Display the set name
            String setName = energyEnum.name();
            context.getSource().getPlayer().sendSystemMessage(
                Component.translatable("maring.command.energyNet.display." + setName)
                    .setStyle(Style.EMPTY.withColor(ChatFormatting.GREEN))
            );

            MutableComponent message = Component.empty(); // 创建一个空的 Component，用于构建消息
            int count = 0;

            for (BlockPos pos : set) {
                // 获取块在位置的名字
                String blockName = level.getBlockState(pos).getBlock().getName().getString();

                // 创建非坐标部分的文本信息
                String nonCoordinateText = " - " + blockName + "  ";

                // 创建坐标部分的文本信息并应用下划线
                String coordinateText = "[" + pos.getX() + ", " + pos.getY() + ", " + pos.getZ() + "] ";

                // 坐标部分的文本样式：下划线和黄色
                MutableComponent coordinateComponent = Component.literal(coordinateText)
                    .setStyle(Style.EMPTY.withUnderlined(true).withColor((count % 4 == 0 || count % 4 == 3) ? 
                            ChatFormatting.WHITE : ChatFormatting.YELLOW)); // 坐标部分下划线和黄色

                // 其他部分的文本样式：基于 count 的值
                Style style = (count % 4 == 0 || count % 4 == 3) ? Style.EMPTY.withColor(ChatFormatting.WHITE) : Style.EMPTY.withColor(ChatFormatting.YELLOW);

                // 创建非坐标部分的文本组件，并应用相应颜色样式
                MutableComponent nonCoordinateComponent = Component.literal(nonCoordinateText).setStyle(style);

                // 为坐标部分添加点击事件，建议命令到输入框
                coordinateComponent = coordinateComponent.setStyle(coordinateComponent.getStyle()
                    .withClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/tp @p " + pos.getX() + " " + (pos.getY()+1) + " " + pos.getZ())));

                // 将坐标部分和非坐标部分添加到消息中
                message = message.append(coordinateComponent).append(nonCoordinateComponent);

                // 每两个元素后添加换行符
                if (count % 2 == 1) {
                    message = message.append(Component.literal("\n"));
                }

                count++;
            }

            // 发送构建好的消息
            context.getSource().getPlayer().sendSystemMessage(message);
        }
    }

}