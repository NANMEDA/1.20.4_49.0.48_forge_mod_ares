package com.main.maring.block.norm.fastbuild;

import com.main.maring.Maring;
import com.main.maring.block.norm.BlockRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.*;

public class HermitePlacerManager {
    private static final TagKey<Block> UNBREAKABLE = TagKey.create(Registries.BLOCK, new ResourceLocation(Maring.MODID,"unbreakable_block"));
    public final BlockPos startPos;
    private final double[] startDir;
    public final BlockPos endPos;
    private final double[] endDir;
    public final BlockPos startControlPos;
    public final BlockPos endControlPos;

    private final List<BlockPos> sampledPoints = new ArrayList<>();
    private int currentIndex = 0;
    public ServerLevel level;

    public HermitePlacerManager(BlockPos p0, double[] dir0, BlockPos p1, double[] dir1,ServerLevel level,BlockPos cp0,BlockPos cp1) {
        this.startPos = p0;
        this.endPos = p1;
        this.startDir = dir0;
        this.endDir = dir1;
        this.level = level;
        this.startControlPos = cp0;
        this.endControlPos = cp1;
        computeHermiteSamples();
    }

    private void computeHermiteSamples() {
        double[] p0 = {startPos.getX(), startPos.getY(), startPos.getZ()};
        double[] p1 = {endPos.getX(), endPos.getY(), endPos.getZ()};

        double tLen = computeTangentLength(p0, p1, startDir, endDir, 1.0);
        double[] m0 = {startDir[0] * tLen, startDir[1] * tLen, startDir[2] * tLen};
        double[] m1 = {endDir[0] * tLen, endDir[1] * tLen, endDir[2] * tLen};

        int sampleCount = Math.max(2, (int)Math.ceil(computeDistance(p0, p1) / 0.5));
        sampledPoints.clear();
        for (int i = 0; i < sampleCount; i++) {
            double t = (double) i / (sampleCount - 1);
            double[] pos = cubicHermite3D(p0, p1, m0, m1, t);
            sampledPoints.add(BlockPos.containing(pos[0], pos[1], pos[2]));
        }

        currentIndex = 0;
    }

    public boolean tick() {
        if (currentIndex >= sampledPoints.size()) return true; // 已完成

        BlockPos center = sampledPoints.get(currentIndex);
        BlockState targetState = BlockRegister.unbrokenjunctioncement_BLOCK.get().defaultBlockState();

        // 获取当前需要放置的位置（已排序）
        int i=0;
        for (BlockPos.MutableBlockPos offset : pattern(center)) {
            BlockPos pos = offset.immutable();

            if (level.getBlockState(pos).is(UNBREAKABLE)) continue;

            if ((i == 0)&&(currentIndex==0)) {
                i++;
                level.setBlockAndUpdate(pos, targetState);
                continue;
            }

            boolean hasNeighbor = false;
            for (Direction dir : Direction.values()) {
                BlockPos neighborPos = pos.relative(dir);
                if (level.getBlockState(neighborPos).is(BlockRegister.unbrokenjunctioncement_BLOCK.get())) {
                    hasNeighbor = true;
                    break;
                }
            }

            if (hasNeighbor) {
                level.setBlockAndUpdate(pos, targetState);
            }
        }

        currentIndex++;
        return currentIndex >= sampledPoints.size();
    }

    public Set<BlockPos> drillHole() {
        Queue<BlockPos> queue = new LinkedList<>();
        Set<BlockPos> collectedPositions = new HashSet<>();
        Set<BlockPos> visited = new HashSet<>();

        queue.add(startPos);
        visited.add(startPos);
        BlockState startState = level.getBlockState(startPos);

        while (!queue.isEmpty()) {
            BlockPos pos = queue.poll();
            BlockState currentState = level.getBlockState(pos);

            if (!currentState.equals(startState)) {
                continue;
            }

            boolean allNeighborsUnbreakable = true;
            for (Direction direction : Direction.values()) {
                BlockPos neighborPos = pos.relative(direction);
                if (!level.getBlockState(neighborPos).is(UNBREAKABLE)) {
                    allNeighborsUnbreakable = false;
                    break;
                }
            }

            if (allNeighborsUnbreakable) {
                collectedPositions.add(pos.immutable());
            }

            for (Direction direction : Direction.values()) {
                BlockPos neighborPos = pos.relative(direction);
                if (!visited.contains(neighborPos) &&
                        level.getBlockState(neighborPos).equals(startState)) {
                    queue.add(neighborPos);
                    visited.add(neighborPos);
                }
            }
        }

        return collectedPositions;
    }

    public static double[] cubicHermite3D(double[] p0, double[] p1, double[] m0, double[] m1, double t) {
        double h00 = 2 * Math.pow(t, 3) - 3 * Math.pow(t, 2) + 1;
        double h10 = Math.pow(t, 3) - 2 * Math.pow(t, 2) + t;
        double h01 = -2 * Math.pow(t, 3) + 3 * Math.pow(t, 2);
        double h11 = Math.pow(t, 3) - Math.pow(t, 2);

        double[] result = new double[3];
        for (int i = 0; i < 3; i++) {
            result[i] = h00 * p0[i] + h10 * m0[i] + h01 * p1[i] + h11 * m1[i];
        }
        return result;
    }

    public static double computeTangentLength(double[] p0, double[] p1, double[] dir0, double[] dir1, double baseScale) {
        double dx = p1[0] - p0[0], dy = p1[1] - p0[1], dz = p1[2] - p0[2];
        double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);
        double dot = Math.max(-1.0, Math.min(1.0, dir0[0] * dir1[0] + dir0[1] * dir1[1] + dir0[2] * dir1[2]));
        double directionDiff = (1.0 - dot) / 2.0;
        return baseScale * distance * 2.0 * (1.0 + directionDiff / 3.0);
    }

    public static double computeDistance(double[] a, double[] b) {
        return Math.sqrt(
                Math.pow(a[0] - b[0], 2) +
                        Math.pow(a[1] - b[1], 2) +
                        Math.pow(a[2] - b[2], 2)
        );
    }

    public static BlockPos.MutableBlockPos[] pattern(BlockPos center) {
        List<BlockPos.MutableBlockPos> positions = new ArrayList<>();
        int cx = center.getX(), cy = center.getY(), cz = center.getZ();
        for (int dx = -2; dx <= 2; dx++) {
            for (int dy = -2; dy <= 2; dy++) {
                for (int dz = -2; dz <= 2; dz++) {
                    if (dy == 2 && (dx == -2 || dx == 2) && (dz == -2 || dz == 2)) continue;
                    positions.add(new BlockPos.MutableBlockPos(cx + dx, cy + dy, cz + dz));
                }
            }
        }
        for (int dy = -2; dy <= 1; dy++) {
            for (int dx = -1; dx <= 1; dx++) {
                positions.add(new BlockPos.MutableBlockPos(cx + dx, cy + dy, cz + 3));
                positions.add(new BlockPos.MutableBlockPos(cx + dx, cy + dy, cz - 3));
            }
        }
        for (int dy = -2; dy <= 1; dy++) {
            for (int dz = -1; dz <= 1; dz++) {
                positions.add(new BlockPos.MutableBlockPos(cx + 3, cy + dy, cz + dz));
                positions.add(new BlockPos.MutableBlockPos(cx - 3, cy + dy, cz + dz));
            }
        }

        positions.sort((a, b) -> {
            int da = manhattanDistance(center, a);
            int db = manhattanDistance(center, b);
            return Integer.compare(da, db);
        });

        return positions.toArray(BlockPos.MutableBlockPos[]::new);
    }

    private static int manhattanDistance(BlockPos a, BlockPos b) {
        return Math.abs(a.getX() - b.getX()) +
                Math.abs(a.getY() - b.getY()) +
                Math.abs(a.getZ() - b.getZ());
    }
}
