package command.energy;

import java.util.Set;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
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
                        .setStyle(Style.EMPTY.withColor(ChatFormatting.YELLOW))
                );

                // Display Supply Level
                context.getSource().getPlayer().sendSystemMessage(
                    Component.translatable("maring.command.energyNet.display.level", energyNet.getSupplyLevel())
                        .setStyle(Style.EMPTY.withColor(ChatFormatting.YELLOW))
                );

                // Display all Sets using the getSet method and EnergyEnum
                displaySet(EnergyEnum.CONSUMER, energyNet, context,level);
                displaySet(EnergyEnum.PRODUCER, energyNet, context,level);
                displaySet(EnergyEnum.STORAGE, energyNet, context,level);
                displaySet(EnergyEnum.TRANS, energyNet, context,level);
                displaySet(EnergyEnum.NULL, energyNet, context,level);

                context.getSource().getPlayer().sendSystemMessage(
                    Component.translatable("maring.command.energyNet.display.separator")
                );
            }
        } else {
            context.getSource().getPlayer().sendSystemMessage(
                Component.translatable("maring.command.energyNet.display.noNets")
                    .setStyle(Style.EMPTY.withColor(ChatFormatting.RED))
            );
        }

        return 1;
    }

    private void displaySet(EnergyEnum energyEnum, util.net.EnergyNet energyNet, CommandContext<CommandSourceStack> context,Level level) {
        Set<BlockPos> set = energyNet.getSet(energyEnum);
        if (set != null && !set.isEmpty()) {
            // Display the set name
            String setName = energyEnum.name();
            context.getSource().getPlayer().sendSystemMessage(
                Component.translatable("maring.command.energyNet.display." + setName)
                    .setStyle(Style.EMPTY.withColor(ChatFormatting.GREEN))
            );

            int count = 0;
            StringBuilder sb = new StringBuilder();

            for (BlockPos pos : set) {
                if (count > 0 && count % 3 == 0) {
                    sb.append("\n"); // Add a newline after every 5th element
                }

                // Get the block at the position and its name
                String blockName = level.getBlockState(pos).getBlock().getName().getString();

                // Append position and block name
                sb.append("At ").append(pos.getX()).append(", ").append(pos.getY()).append(", ").append(pos.getZ())
                  .append(" - ").append(blockName).append("  ");
                count++;
            }
            context.getSource().getPlayer().sendSystemMessage(Component.literal(sb.toString()));
        }
    }
}