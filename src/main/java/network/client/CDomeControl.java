package network.client;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.event.network.CustomPayloadEvent;
import block.entity.neutral.dormcontrol.DomeControlEntity;

public class CDomeControl {

    private final BlockPos pos;
   

    public CDomeControl(BlockPos pos) {
        this.pos = pos;
    }

    public static void encode(CDomeControl msg, FriendlyByteBuf buf) {
        buf.writeBlockPos(msg.pos);
    }

    public static CDomeControl decode(FriendlyByteBuf buf) {
        return new CDomeControl(
            buf.readBlockPos()
        );
    }

    public static void handle(CDomeControl msg, CustomPayloadEvent.Context ctx) {
        ctx.enqueueWork(() -> {
            ServerLevel level = (ServerLevel) ctx.getSender().level(); // Get the level from the context
            if (level != null && level.getBlockEntity(msg.pos) instanceof DomeControlEntity entity) {
            	entity.remove();
            }
        });
        ctx.setPacketHandled(true);
    }
}
