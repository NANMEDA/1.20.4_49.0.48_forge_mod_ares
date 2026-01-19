package com.main.maring.event.forge;

import com.main.maring.block.entity.neutral.fastbuild.DormJunctionControlEntity;
import com.main.maring.block.entity.neutral.fastbuild.dormcontrol.DomeControlEntity;
import com.main.maring.block.norm.fastbuild.HermitePlacerManager;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.LinkedList;
import java.util.Queue;

@Mod.EventBusSubscriber
public class HermiteTaskController {
    private static final Queue<HermitePlacerManager> queue = new LinkedList<>();

    public static void enqueue(HermitePlacerManager task) {
        queue.add(task);
    }

    @SubscribeEvent
    public static void HermitePlaceTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (queue.isEmpty()) return;

        HermitePlacerManager current = queue.peek();
        if (current == null) return;

        ServerLevel level = current.level;
        if (level == null) return;

        boolean done = current.tick();
        if (done) {
            for(BlockPos emp : current.drillHole()){
                level.setBlockAndUpdate(emp, Blocks.AIR.defaultBlockState());
            }
            if(level.getBlockEntity(current.startControlPos) instanceof DormJunctionControlEntity jces){
                jces._genDoor(Blocks.AIR.defaultBlockState());
                jces.savePosData(current.endControlPos);
                if(level.getBlockEntity(current.endControlPos) instanceof DormJunctionControlEntity jcee){
                    jcee._genDoor(Blocks.AIR.defaultBlockState());
                    jcee.savePosData(current.startControlPos);
                    ((DomeControlEntity) level.getBlockEntity(jces.father)).addConnetion(jcee.father);
                    ((DomeControlEntity) level.getBlockEntity(jcee.father)).addConnetion(jces.father);
                }
            }

            queue.poll();
        }
    }
}
