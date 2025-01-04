package command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import command.disaster.DisasterDo;
import command.disaster.DoomsSetDays;
import command.energy.CheckAndClear;
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
     * Registers Maring's custom commands:
     * <br> `rule whenDisasterCome`: Set the final disaster time.
     * <br> `rule willDisasterCome`: Toggle if the final disaster will happen (affects only the final one).
     * <br> `rule willPressureHurt`: Toggle if Mars creatures are harmed without a spacesuit.
     * <br> `rule energyNet calcSpeed`: Set the calculation speed of the energy network.
     * <br> `rule energyNet display`: Display the energy network status.
     * <br> `rule energyNet clear`: Clear invalid energy network blocks and connections.
     */
    @SubscribeEvent
    public static void onServerStarting(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        dispatcher.register(
            Commands.literal(MODID)
                .then(Commands.literal("rule")
                    .requires(commandSourceStack -> commandSourceStack.hasPermission(2)) // Requires permission level 2
                    // Sub-command: whenDisasterCome
                    .then(Commands.literal("whenDisasterCome")
                        .then(Commands.argument("value", IntegerArgumentType.integer())
                            .executes(DoomsSetDays.INSTANCE))) // Set the disaster time
                    // Sub-command: willDisasterCome
                    .then(Commands.literal("willDisasterCome")
                        .then(Commands.argument("value", BoolArgumentType.bool())
                            .executes(DisasterDo.INSTANCE))) // Enable/disable disaster
                    // Sub-command: willPressureHurt
                    .then(Commands.literal("willPressureHurt")
                        .then(Commands.argument("value", BoolArgumentType.bool())
                            .executes(PressureHurt.INSTANCE))) // Enable/disable pressure harm
                    // Sub-command group: energyNet
                    .then(Commands.literal("energyNet")
                        // Sub-command: calcSpeed
                        .then(Commands.literal("calcSpeed")
                            .then(Commands.argument("value", IntegerArgumentType.integer())
                                .executes(EnergyNet.INSTANCE))) // Set energy net calculation speed
                        // Sub-command: display
                        .then(Commands.literal("display")
                            .executes(Display.INSTANCE)) // Display energy network status
                        // Sub-command: clear
                        .then(Commands.literal("clear")
                            .executes(CheckAndClear.INSTANCE)) // Clear invalid blocks in energy net
                    )
                )
        );
    }

}
