package keybinds;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.PacketDistributor;
import network.NetworkHandler;
import network.client.CRocketStart;
import vehicle.rocket.RocketEntity;

public class KeyMethods {

	/***
	 * 发射火箭的函数
	 * Client 2 Server
	 * @author NANMEDA
	 * ***/
    public static void startRocket(Player player) {
        if (player.isPassenger() && player.getVehicle() instanceof RocketEntity rocket) {
            //rocket.startRocket();
            NetworkHandler.INSTANCE.send(new CRocketStart(player.getUUID()),
                    PacketDistributor.SERVER.noArg());
        }
    }

}
