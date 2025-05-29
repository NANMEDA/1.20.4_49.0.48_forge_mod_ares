package com.main.maring.network.client;


import java.util.UUID;
import java.util.function.Supplier;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.server.ServerLifecycleHooks;
import com.main.maring.vehicle.rocket.RocketEntity;

public class CRocketStart {

  private final UUID carried;

  public CRocketStart(UUID uuid) {
    this.carried = uuid;
  }

  public static void encode(CRocketStart msg, FriendlyByteBuf buf) {
    buf.writeUUID(msg.carried);
  }

  public static CRocketStart decode(FriendlyByteBuf buf) {
    return new CRocketStart(buf.readUUID());
  }

  public static void handle(CRocketStart msg, Supplier<NetworkEvent.Context> contextSupplier) {
      NetworkEvent.Context ctx = contextSupplier.get();
	  ctx.enqueueWork(() -> {
		  ServerPlayer player = getPlayerByUUID(msg.carried);
          RocketEntity rocket = (RocketEntity) player.getVehicle();
          if (rocket != null) {
             rocket.startRocket();
          }
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
