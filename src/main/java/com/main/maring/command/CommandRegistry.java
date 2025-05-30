package com.main.maring.command;

import com.main.maring.Maring;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;

import com.main.maring.command.energy.CheckAndClear;
import com.main.maring.command.energy.DeleteNet;
import com.main.maring.command.energy.Display;
import com.main.maring.command.energy.EnergyNet;
import com.main.maring.command.environment.EnvironmentDisplay;
import com.main.maring.command.environment.EnvironmentSet;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber
public class CommandRegistry {

    @SubscribeEvent
    public static void onServerStarting(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        // 注册 "maring rule" 命令及其子命令
        dispatcher.register(
            Commands.literal(Maring.MODID)
                .then(Commands.literal("rule")
                    .requires(commandSourceStack -> commandSourceStack.hasPermission(2)) // 需要权限等级 2
                    // 子命令组: energyNet
                    .then(Commands.literal("energyNet")
                        // 子命令: calcSpeed
                        .then(Commands.literal("calcSpeed")
                            .then(Commands.argument("value", IntegerArgumentType.integer())
                                .executes(EnergyNet.INSTANCE))) // 设置能源网络计算速度
                        // 子命令: display
                        .then(Commands.literal("display")
                            .executes(Display.INSTANCE)) // 显示能源网络状态
                        // 子命令: clear
                        .then(Commands.literal("clear")
                            .executes(CheckAndClear.INSTANCE)) // 清理能源网络中的无效方块
                        // 子命令: delete
                        .then(Commands.literal("delete")
                            .then(Commands.argument("value", LongArgumentType.longArg())
                                .executes(DeleteNet.INSTANCE))) // 删除特定能源网络 ID
                    )
                )
        );

        // 注册 "maring display" 和 "maring rule environment" 命令及其子命令
        dispatcher.register(
            Commands.literal(Maring.MODID)
                .then(Commands.literal("display")
                    .then(Commands.literal("environment")
                        .executes(EnvironmentDisplay.INSTANCE))) // 显示环境参数
                .then(Commands.literal("rule")
                    .requires(commandSourceStack -> commandSourceStack.hasPermission(2)) // 需要权限等级 2
                    .then(Commands.literal("environment")
                        .then(Commands.literal("set")
                            .then(Commands.argument("parameter", StringArgumentType.string())
                                .suggests((context, builder) -> net.minecraft.commands.SharedSuggestionProvider.suggest(
                                    new String[]{"humid", "oxygen", "pressure", "temperature", "mag"}, builder)) // 提供参数补全建议
                                .then(Commands.argument("value", IntegerArgumentType.integer())
                                    .executes(EnvironmentSet.INSTANCE))) // 设置环境参数
                        )
                    )
                )
        );
    }
}
