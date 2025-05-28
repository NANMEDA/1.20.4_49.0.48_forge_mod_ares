package network.server;


import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SRocketStart {

  private final ItemStack stack;

  public SRocketStart(ItemStack stackIn) {
    this.stack = stackIn;
  }

  public static void encode(SRocketStart msg, FriendlyByteBuf buf) {
    buf.writeItem(msg.stack);
  }

  public static SRocketStart decode(FriendlyByteBuf buf) {
    return new SRocketStart(buf.readItem());
  }

public static void handle(SRocketStart msg, Supplier<NetworkEvent.Context> contextSupplier) {
    NetworkEvent.Context ctx = contextSupplier.get();
    ctx.enqueueWork(() -> {
      LocalPlayer clientPlayer = Minecraft.getInstance().player;

      if (clientPlayer != null) {
        clientPlayer.containerMenu.setCarried(msg.stack);
      }
    });
    ctx.setPacketHandled(true);
  }
}
