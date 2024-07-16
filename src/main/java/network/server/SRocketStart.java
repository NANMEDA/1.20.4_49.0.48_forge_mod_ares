package network.server;


import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.network.CustomPayloadEvent;

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

  @SuppressWarnings("resource")
public static void handle(SRocketStart msg, CustomPayloadEvent.Context ctx) {
    ctx.enqueueWork(() -> {
      LocalPlayer clientPlayer = Minecraft.getInstance().player;

      if (clientPlayer != null) {
        clientPlayer.containerMenu.setCarried(msg.stack);
      }
    });
    ctx.setPacketHandled(true);
  }
}
