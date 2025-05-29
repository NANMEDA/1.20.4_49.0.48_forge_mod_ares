package com.main.maring.keybinds;

import net.minecraft.world.entity.player.Player;
import com.main.maring.network.NetworkHandler;
import com.main.maring.network.client.CRocketStart;
import com.main.maring.vehicle.rocket.RocketEntity;

public class KeyMethods {

	/***
	 * 发射火箭的函数
	 * Client 2 Server
	 * @author NANMEDA
	 * ***/
    public static void startRocket(Player player) {
        if (player.isPassenger() && player.getVehicle() instanceof RocketEntity rocket) {
            //rocket.startRocket();
            NetworkHandler.INSTANCE.sendToServer(new CRocketStart(player.getUUID()));
        }
    }

}
