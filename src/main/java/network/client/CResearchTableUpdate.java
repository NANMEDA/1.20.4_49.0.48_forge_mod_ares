package network.client;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import block.entity.neutral.researchtable.ResearchTableEntity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class CResearchTableUpdate {

    private final BlockPos pos;
    private final int atLevel;
    private final int atTech;
    private final int tech1;
    private final int tech2;
    private final int tech3;

    public CResearchTableUpdate(BlockPos pos, int atTech, int atLevel, int tech1, int tech2, int tech3) {
        this.pos = pos;
        this.atTech = atTech;
        this.atLevel = atLevel;
        this.tech1 = tech1;
        this.tech2 = tech2;
        this.tech3 = tech3;
    }

    public static void encode(CResearchTableUpdate msg, FriendlyByteBuf buf) {
        buf.writeBlockPos(msg.pos);
        buf.writeInt(msg.atTech);
        buf.writeInt(msg.atLevel);
        buf.writeInt(msg.tech1);
        buf.writeInt(msg.tech2);
        buf.writeInt(msg.tech3);
    }

    public static CResearchTableUpdate decode(FriendlyByteBuf buf) {
        return new CResearchTableUpdate(
            buf.readBlockPos(),
            buf.readInt(),
            buf.readInt(),
            buf.readInt(),
            buf.readInt(),
            buf.readInt()
        );
    }

    public static void handle(CResearchTableUpdate msg, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context ctx = contextSupplier.get();
        ctx.enqueueWork(() -> {
            ServerLevel level = (ServerLevel) ctx.getSender().level(); // Get the level from the context
            if (level != null && level.getBlockEntity(msg.pos) instanceof ResearchTableEntity entity) {
                entity.atTech = msg.atTech;
                entity.atLevel = msg.atLevel;
                entity.tech1 = msg.tech1;
                entity.tech2 = msg.tech2;
                entity.tech3 = msg.tech3;
                entity.setChanged();
            }
        });
        ctx.setPacketHandled(true);
    }
}
