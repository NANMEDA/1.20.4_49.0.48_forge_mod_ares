package network.client;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.event.network.CustomPayloadEvent;
import block.entity.neutral.researchtable.ResearchTableEntity;
import menu.reseachtable.TechNode;

public class CTechTreeUpdate {

    private final BlockPos pos;
    private final String name;

    public CTechTreeUpdate(BlockPos pos, String name) {
        this.pos = pos;
        this.name = name;
    }

    public static void encode(CTechTreeUpdate msg, FriendlyByteBuf buf) {
        buf.writeBlockPos(msg.pos);
        buf.writeUtf(msg.name);
    }

    public static CTechTreeUpdate decode(FriendlyByteBuf buf) {
        return new CTechTreeUpdate(
            buf.readBlockPos(),
            buf.readUtf()
        );
    }

    public static void handle(CTechTreeUpdate msg, CustomPayloadEvent.Context ctx) {
        ctx.enqueueWork(() -> {
            ServerLevel level = (ServerLevel) ctx.getSender().level(); // Get the level from the context
            if (level != null && level.getBlockEntity(msg.pos) instanceof ResearchTableEntity entity) {
                entity.getTechTree().unlockTech(TechNode.fromName(msg.name));
                entity.setChanged();
            }
        });
        ctx.setPacketHandled(true);
    }
}
