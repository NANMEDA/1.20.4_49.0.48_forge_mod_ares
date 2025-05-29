package com.main.maring.network.client;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import com.main.maring.block.entity.neutral.researchtable.ResearchTableEntity;
import com.main.maring.menu.reseachtable.TechNode;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class CStartTech {

    private final BlockPos pos;
    private final String name;
    private final int tick;

    public CStartTech(BlockPos pos, String name,int tick) {
        this.pos = pos;
        this.name = name;
        this.tick = tick;
    }

    public static void encode(CStartTech msg, FriendlyByteBuf buf) {
        buf.writeBlockPos(msg.pos);
        buf.writeUtf(msg.name);
        buf.writeInt(msg.tick);
    }

    public static CStartTech decode(FriendlyByteBuf buf) {
        return new CStartTech(
            buf.readBlockPos(),
            buf.readUtf(),
            buf.readInt()
        );
    }

    public static void handle(CStartTech msg, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context ctx = contextSupplier.get();
        ctx.enqueueWork(() -> {
            ServerLevel level = (ServerLevel) ctx.getSender().level(); // Get the level from the context
            if (level != null && level.getBlockEntity(msg.pos) instanceof ResearchTableEntity entity) {
                entity.setResearch(TechNode.fromName(msg.name));
                entity.setChanged();
                entity.startTech();
            }
        });
        ctx.setPacketHandled(true);
    }
}
