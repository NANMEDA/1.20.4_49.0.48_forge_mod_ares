package command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;

import command.disaster.DisasterDo;
import command.disaster.DoomsSetDays;
import command.energy.CheckAndClear;
import command.energy.DeleteNet;
import command.energy.Display;
import command.energy.EnergyNet;
import command.environment.EnvironmentDisplay;
import command.environment.EnvironmentSet;
import command.player.PressureHurt;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(modid = "maring")
public class CommandRegistry {

    private static final String MODID = "maring";
    @SubscribeEvent
    public static void onServerStarting(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        // 注册 "maring rule" 命令及其子命令
        dispatcher.register(
            Commands.literal(MODID)
                .then(Commands.literal("rule")
                    .requires(commandSourceStack -> commandSourceStack.hasPermission(2)) // 需要权限等级 2
                    // 子命令: whenDisasterCome
                    .then(Commands.literal("whenDisasterCome")
                        .then(Commands.argument("value", IntegerArgumentType.integer())
                            .executes(DoomsSetDays.INSTANCE))) // 设置灾难时间
                    // 子命令: willDisasterCome
                    .then(Commands.literal("willDisasterCome")
                        .then(Commands.argument("value", BoolArgumentType.bool())
                            .executes(DisasterDo.INSTANCE))) // 切换灾难开关
                    // 子命令: willPressureHurt
                    .then(Commands.literal("willPressureHurt")
                        .then(Commands.argument("value", BoolArgumentType.bool())
                            .executes(PressureHurt.INSTANCE))) // 切换压力伤害开关
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
            Commands.literal(MODID)
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
