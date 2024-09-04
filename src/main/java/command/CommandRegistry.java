package command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import command.disaster.DisasterDo;
import command.disaster.DoomsSetDays;
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
	
	/***
	 * maring 的 rule 相关指令
	 * whenDisasterCome - 最终灾难来临时间
	 * willDisasterCome - 是否有最终灾难(只影响最终那个)
	 * willPressureHurt - 火星生物伤害（不穿宇航服是否受伤）
	 * ***/
	 @SubscribeEvent
	    public static void onServerStarting(RegisterCommandsEvent event) {
	        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
	        
	        dispatcher.register(
	            Commands.literal(MODID)
	                .then(Commands.literal("rule")
	                    .requires(commandSourceStack -> commandSourceStack.hasPermission(2))
	                    .then(Commands.literal("whenDisasterCome")
		                        .then(Commands.argument("value", IntegerArgumentType.integer())
		                            .executes(DoomsSetDays.INSTANCE)))
	                    .then(Commands.literal("willDisasterCome")
	                        .then(Commands.argument("value", BoolArgumentType.bool())
	                            .executes(DisasterDo.INSTANCE)))
	                    .then(Commands.literal("willPressureHurt")
		                        .then(Commands.argument("value", BoolArgumentType.bool())
		                            .executes(PressureHurt.INSTANCE)))
	                    .then(Commands.literal("energyNet")
	                    		.then(Commands.literal("calcSpeed")
	                    				.then(Commands.argument("value", IntegerArgumentType.integer())
	                    						.executes(EnergyNet.INSTANCE))))
	                )
	        );
	    }
}
