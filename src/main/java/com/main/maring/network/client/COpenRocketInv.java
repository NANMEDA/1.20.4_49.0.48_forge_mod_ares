package com.main.maring.network.client;


import com.main.maring.vehicle.rocket.RocketEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.UUID;
import java.util.function.Supplier;

public class COpenRocketInv {

  private final UUID carried;

  public COpenRocketInv(UUID uuid) {
    this.carried = uuid;
  }

  public static void encode(COpenRocketInv msg, FriendlyByteBuf buf) {
    buf.writeUUID(msg.carried);
  }

  public static COpenRocketInv decode(FriendlyByteBuf buf) {
    return new COpenRocketInv(buf.readUUID());
  }

  public static void handle(COpenRocketInv msg, Supplier<NetworkEvent.Context> contextSupplier) {
      NetworkEvent.Context ctx = contextSupplier.get();
	  ctx.enqueueWork(() -> {
		  ServerPlayer player = getPlayerByUUID(msg.carried);
          RocketEntity rocket = (RocketEntity) player.getVehicle();
          rocket.openCustomInventoryScreen(player);
      });
      ctx.setPacketHandled(true);
  }
  
  public static ServerPlayer getPlayerByUUID(UUID uuid) {       
	  MinecraftServer minecraftServer = ServerLifecycleHooks.getCurrentServer();
      if (minecraftServer != null) {
          return minecraftServer.getPlayerList().getPlayer(uuid);
      }
      return null;
  }
}
