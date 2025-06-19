package com.main.maring.item.tool.electric;

import com.main.maring.machine.energy.EnergyEntity;
import com.main.maring.util.net.EnergyNet;
import com.main.maring.util.net.EnergyNetProcess;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

import java.util.Map;
import java.util.Set;


/**
 * 这个是用来Cut电力网络的
 * @author NANMEDA
 * */
public class ElectricDebuggerStick extends Item {
	private BlockPos startPos = null;
	private long startNet = 0;

	public ElectricDebuggerStick(Properties p_41383_) {
		super(p_41383_);
	}

	public static final String global_name = "electric_debugger_stick";


	@Override
	public InteractionResult useOn(UseOnContext context) {
		Level level = context.getLevel();
		BlockPos pos = context.getClickedPos();

		if (!level.isClientSide) {
			if (level.getBlockEntity(pos) instanceof EnergyEntity blockentity) {
				long netId = blockentity.getNet();
				boolean hasNet = blockentity.haveNet();
				Map<BlockPos, Boolean> connections = blockentity.getConnections();

				context.getPlayer().sendSystemMessage(Component.translatable("electricdebuggerstick.maring.title", pos));
				context.getPlayer().sendSystemMessage(Component.translatable("electricdebuggerstick.maring.netid", netId));
				context.getPlayer().sendSystemMessage(Component.translatable("electricdebuggerstick.maring.hasnet", hasNet));
				context.getPlayer().sendSystemMessage(Component.translatable("electricdebuggerstick.maring.connections", connections.size()));

				if (!connections.isEmpty()) {
					context.getPlayer().sendSystemMessage(Component.translatable("electricdebuggerstick.maring.coordtitle"));
					for (BlockPos p : connections.keySet()) {
						context.getPlayer().sendSystemMessage(Component.translatable("electricdebuggerstick.maring.coordentry", p));
					}
				}
			} else {
				return InteractionResult.PASS;
			}
		}

		return InteractionResult.SUCCESS;
	}



}
