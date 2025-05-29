package com.main.maring.util;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class Methods {

    public static void setEntityRotation(Entity vehicle, float rotation) {
        vehicle.setYRot(vehicle.getYRot() + rotation);
        vehicle.setYBodyRot(vehicle.getYRot());
        vehicle.yRotO = vehicle.getYRot();
    }

	public static void sendVehicleHasNoFuelMessage(Player player) {
		player.sendSystemMessage(Component.translatable("entity.rocket.message.nofuel"));		
	}
	
}
