package com.main.maring.network.client;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import com.main.maring.block.entity.neutral.fastbuild.dormcontrol.DomeControlEntity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

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

    public static void handle(CDomeControl msg, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context ctx = contextSupplier.get();
        ctx.enqueueWork(() -> {
            ServerLevel level = (ServerLevel) ctx.getSender().level(); // Get the level from the context
            if (level != null && level.getBlockEntity(msg.pos) instanceof DomeControlEntity entity) {
            	entity.remove();
            }
        });
        ctx.setPacketHandled(true);
    }
}
