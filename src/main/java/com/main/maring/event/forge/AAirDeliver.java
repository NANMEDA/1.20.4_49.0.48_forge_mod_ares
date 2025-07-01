package com.main.maring.event.forge;

import com.main.maring.animal.entity.villager.ModVillager;
import com.main.maring.block.norm.AAirState;
import com.main.maring.block.norm.BlockAAIR;
import com.main.maring.block.norm.BlockRegister;
import com.main.maring.item.ItemRegister;
import com.main.maring.util.mar.AAirManager;
import com.main.maring.util.mar.EnvironmentData;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;


@Mod.EventBusSubscriber
public class AAirDeliver {
    private static final Random RANDOM = new Random();

    @SubscribeEvent
    public static void AAirProcess(TickEvent.LevelTickEvent event) {
        if (RANDOM.nextDouble() > 0.33) {
            return;
        }
        Level level = event.level;
        int max_process = 128;

        if (level.isClientSide) return;

        if (event.phase != TickEvent.Phase.END) return;

        if (!(level instanceof ServerLevel serverLevel)) return;

        AAirManager manager = AAirManager.getOrCreate(serverLevel);

        int i = 0;
        for (BlockPos pos : Set.copyOf(manager.getDeactivateSet())) {
            i++;
            if(i>max_process) break;
            if (serverLevel.isLoaded(pos)) {
                BlockState state = serverLevel.getBlockState(pos);
                if (state.getBlock() instanceof BlockAAIR blockAAIR) {
                    boolean stillActive = blockAAIR.deactivate(serverLevel, pos);
                }
            }else{
                manager.remove(pos,AAirState.DEACTIVATE);
            }
        }

        i = 0;
        for (BlockPos pos : Set.copyOf(manager.getActivateSet())) {
            i++;
            if(i>max_process) break;
            if (serverLevel.isLoaded(pos)) {
                BlockState state = serverLevel.getBlockState(pos);
                if (state.getBlock() instanceof BlockAAIR blockAAIR) {
                    if (RANDOM.nextDouble() > 0.33) continue;
                    boolean stillActive = blockAAIR.activate(serverLevel, pos);
                }else{
                    manager.remove(pos,AAirState.ACTIVATE);
                }
            }
        }
        manager.setDirty();
    }


    @SubscribeEvent
    public static void onServerStartingUpdateAair(ServerStartingEvent event) {
        MinecraftServer server = event.getServer();
        for (ResourceKey<Level> key : server.levelKeys()) {
            ServerLevel level = server.getLevel(key);
            if (level != null) {
                AAirManager.getOrCreate(level);
            }
        }
    }

}
