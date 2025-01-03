package command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import command.disaster.DisasterDo;
import command.disaster.DoomsSetDays;
import command.energy.Display;
import command.energy.EnergyNet;
import command.player.PressureHurt;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "maring")
public class CommandRegistry {

    private static final String MODID = "maring";

    /**
     * Maring's rule related commands:
     * - whenDisasterCome: Set the final disaster time
     * - willDisasterCome: Toggle if the final disaster will happen (only affects the final one)
     * - willPressureHurt: Toggle if Mars creatures will be hurt (whether they are harmed without spacesuit)
     */
    @SubscribeEvent
    public static void onServerStarting(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        dispatcher.register(
            Commands.literal(MODID)
                .then(Commands.literal("rule")
                    .requires(commandSourceStack -> commandSourceStack.hasPermission(2)) // Permission level 2 required
                    .then(Commands.literal("whenDisasterCome")
                        .then(Commands.argument("value", IntegerArgumentType.integer())
                            .executes(DoomsSetDays.INSTANCE))) // Command for setting disaster time
                    .then(Commands.literal("willDisasterCome")
                        .then(Commands.argument("value", BoolArgumentType.bool())
                            .executes(DisasterDo.INSTANCE))) // Command for enabling/disabling disaster
                    .then(Commands.literal("willPressureHurt")
                        .then(Commands.argument("value", BoolArgumentType.bool())
                            .executes(PressureHurt.INSTANCE))) // Command for enabling/disabling pressure harm
                    .then(Commands.literal("energyNet")
                        .then(Commands.literal("calcSpeed")
                            .then(Commands.argument("value", IntegerArgumentType.integer())
                                .executes(EnergyNet.INSTANCE))) // Command for energy net calculation speed
                        .then(Commands.literal("display")
                            .executes(Display.INSTANCE))) // Command for displaying energy net status
                )
        );
    }
}
