package block.norm.fastbuild;

import java.util.*;

import block.norm.BlockRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

/**
 * 处理穹顶之间的连接
 * @author NANMEDA
 * */
public class JunctionHelper {
    
    private static final BlockState UNBROKEN_CEMENT_STATE = BlockRegister.unbrokencement_BLOCK.get().defaultBlockState();
    private static final BlockState UNBROKEN_FOG_STATE = BlockRegister.unbrokenfog_BLOCK.get().defaultBlockState();
    private static final BlockState AIR_STATE = BlockRegister.A_AIR.get().defaultBlockState();
    private static final BlockState JUNCTION_STATE = FastBuildRegister.dormjunction_BLOCK.get().defaultBlockState();
    private static final BlockState JUNCTION_CONTROL_STATE = FastBuildRegister.dormjunctioncontrol_BLOCK.get().defaultBlockState();
    
    /**
     * 从某个 pos 出发生成某个方向的连接基座
     * 0是 X-正
     * 6是 Z-正
     * <p>0,1,2,3,4,5,6,7代表方向
     * @param level
     * @param pos 
     * @param emitDirection 八方向
     */
    public static void BirthJuntionBase(Level level, BlockPos pos, int emitDirection) {
        BlockPos[] posDoor;
        BlockPos[] posAir;
        
        int normalizedDirection = emitDirection % 4;
        
        switch (normalizedDirection) {
            case 0:
                posDoor = new BlockPos[]{
                    pos, pos.north(), pos.north().north(), pos.south(), pos.south().south(),
                    pos.offset(0, 1, 2), pos.offset(0, 1, -2), pos.offset(0, 2, 2),
                    pos.offset(0, 2, -2), pos.offset(0, 3, 2), pos.offset(0, 3, -2),
                    pos.offset(0, 4, 1), pos.offset(0, 4, 0), pos.offset(0, 4, -1)
                };
                posAir = new BlockPos[]{
                    pos.offset(0, 1, 1), pos.offset(0, 1, 0), pos.offset(0, 1, -1),
                    pos.offset(0, 2, 1), pos.offset(0, 2, 0), pos.offset(0, 2, -1),
                    pos.offset(0, 3, 1), pos.offset(0, 3, 0), pos.offset(0, 3, -1)
                };
                break;
            case 2:
                posDoor = new BlockPos[]{
                    pos, pos.east(), pos.east().east(), pos.west(), pos.west().west(),
                    pos.offset(2, 1, 0), pos.offset(-2, 1, 0), pos.offset(2, 2, 0),
                    pos.offset(-2, 2, 0), pos.offset(2, 3, 0), pos.offset(-2, 3, 0),
                    pos.offset(1, 4, 0), pos.offset(0, 4, 0), pos.offset(-1, 4, 0)
                };
                posAir = new BlockPos[]{
                    pos.offset(-1, 1, 0), pos.offset(0, 1, 0), pos.offset(1, 1, 0),
                    pos.offset(-1, 2, 0), pos.offset(0, 2, 0), pos.offset(1, 2, 0),
                    pos.offset(-1, 3, 0), pos.offset(0, 3, 0), pos.offset(1, 3, 0)
                };
                break;
            case 1:
                posDoor = new BlockPos[]{
                    pos, pos.offset(1, 0, 1), pos.offset(-1, 0, -1), pos.offset(2, 0, 2),
                    pos.offset(-2, 0, -2), pos.offset(2, 1, 2), pos.offset(-2, 1, -2),
                    pos.offset(2, 2, 2), pos.offset(-2, 2, -2), pos.offset(2, 3, 2),
                    pos.offset(-2, 3, -2),
                    pos.offset(1, 4, 1), pos.offset(-1, 4, -1), pos.offset(0, 4, 0)
                };
                posAir = new BlockPos[]{
                    pos.offset(1, 1, 1), pos.offset(0, 1, 0), pos.offset(-1, 1, -1),
                    pos.offset(1, 2, 1), pos.offset(0, 2, 0), pos.offset(-1, 2, -1),
                    pos.offset(1, 3, 1), pos.offset(0, 3, 0), pos.offset(-1, 3, -1)
                };
                break;
            case 3:
                posDoor = new BlockPos[]{
                    pos, pos.offset(-1, 0, 1), pos.offset(1, 0, -1), pos.offset(-2, 0, 2),
                    pos.offset(2, 0, -2), pos.offset(-2, 1, 2), pos.offset(2, 1, -2),
                    pos.offset(-2, 2, 2), pos.offset(2, 2, -2), pos.offset(-2, 3, 2),
                    pos.offset(2, 3, -2),
                    pos.offset(-1, 4, 1), pos.offset(1, 4, -1), pos.offset(0, 4, 0)
                };
                posAir = new BlockPos[]{
                    pos.offset(-1, 1, 1), pos.offset(0, 1, 0), pos.offset(1, 1, -1),
                    pos.offset(-1, 2, 1), pos.offset(0, 2, 0), pos.offset(1, 2, -1),
                    pos.offset(-1, 3, 1), pos.offset(0, 3, 0), pos.offset(1, 3, -1)
                };
                break;
            default:
                posDoor = new BlockPos[]{};
                posAir = new BlockPos[]{};
                break;
        }
        
        for(BlockPos pos_d : posDoor) {
        	level.setBlockAndUpdate(pos_d, JUNCTION_STATE);
        }
        level.setBlockAndUpdate(pos, JUNCTION_CONTROL_STATE.setValue(BlockStateProperties.LEVEL, emitDirection));
   
        for(BlockPos pos_a : posAir) {
        	level.setBlockAndUpdate(pos_a, UNBROKEN_FOG_STATE);
        }
        
        // Handle additional emitDirections
        if (emitDirection == 1 || emitDirection == 5 || emitDirection == 3 || emitDirection == 7) {
            if (emitDirection == 1) {
                posDoor = extendPosDoor(posDoor, pos, new BlockPos[]{
                    pos.offset(0, 0, 1), pos.offset(-1, 0, 0), pos.offset(1, 0, 2),
                    pos.offset(-2, 0, -1), pos.offset(0, 4, 1), pos.offset(-1, 4, 0),
                    pos.offset(1, 4, 2), pos.offset(-2, 4, -1)
                });
                posAir = extendPosAir(posAir, pos, new BlockPos[]{
                    pos.offset(0, 1, 1), pos.offset(-1, 1, 0), pos.offset(1, 1, 2),
                    pos.offset(-2, 1, -1), pos.offset(0, 2, 1), pos.offset(-1, 2, 0),
                    pos.offset(1, 2, 2), pos.offset(-2, 2, -1), pos.offset(0, 3, 1),
                    pos.offset(-1, 3, 0), pos.offset(1, 3, 2), pos.offset(-2, 3, -1)
                });
            } else if (emitDirection == 5) {
                posDoor = extendPosDoor(posDoor, pos, new BlockPos[]{
                    pos.offset(0, 0, -1), pos.offset(1, 0, 0), pos.offset(-1, 0, -2),
                    pos.offset(2, 0, 1), pos.offset(0, 4, -1), pos.offset(1, 4, 0),
                    pos.offset(-1, 4, -2), pos.offset(2, 4, 1)
                });
                posAir = extendPosAir(posAir, pos, new BlockPos[]{
                    pos.offset(0, 1, -1), pos.offset(1, 1, 0), pos.offset(-1, 1, -2),
                    pos.offset(2, 1, 1),
                    pos.offset(0, 2, -1), pos.offset(1, 2, 0), pos.offset(-1, 2, -2),
                    pos.offset(2, 2, 1),
                    pos.offset(0, 3, -1), pos.offset(1, 3, 0), pos.offset(-1, 3, -2),
                    pos.offset(2, 3, 1),
                });
            } else if (emitDirection == 3) {
                posDoor = extendPosDoor(posDoor, pos, new BlockPos[]{
                    pos.offset(0, 0, 1), pos.offset(1, 0, 0), pos.offset(-1, 0, 2),
                    pos.offset(2, 0, -1), pos.offset(0, 4, 1), pos.offset(1, 4, 0),
                    pos.offset(-1, 4, 2), pos.offset(2, 4, -1)
                });
                posAir = extendPosAir(posAir, pos, new BlockPos[]{
                    pos.offset(0, 1, 1), pos.offset(1, 1, 0), pos.offset(-1, 1, 2),
                    pos.offset(2, 1, -1), pos.offset(0, 2, 1), pos.offset(1, 2, 0),
                    pos.offset(-1, 2, 2), pos.offset(2, 2, -1), pos.offset(0, 3, 1),
                    pos.offset(1, 3, 0), pos.offset(-1, 3, 2), pos.offset(2, 3, -1)
                });
            } else if (emitDirection == 7) {
                posDoor = extendPosDoor(posDoor, pos, new BlockPos[]{
                    pos.offset(0, 0, -1), pos.offset(-1, 0, 0), pos.offset(1, 0, -2),
                    pos.offset(-2, 0, 1), pos.offset(0, 4, -1), pos.offset(-1, 4, 0),
                    pos.offset(1, 4, -2), pos.offset(-2, 4, 1)
                });
                posAir = extendPosAir(posAir, pos, new BlockPos[]{
                    pos.offset(0, 1, -1), pos.offset(-1, 1, 0), pos.offset(1, 1, -2),
                    pos.offset(-2, 1, 1), pos.offset(0, 2, -1), pos.offset(-1, 2, 0),
                    pos.offset(1, 2, -2), pos.offset(-2, 2, 1), pos.offset(0, 3, -1),
                    pos.offset(-1, 3, 0), pos.offset(1, 3, -2), pos.offset(-2, 3, 1)
                });
            }
        }
        
        for(BlockPos pos_d : posDoor) {
        	if(level.getBlockState(pos_d)!=JUNCTION_STATE
        			&&level.getBlockState(pos_d)!=JUNCTION_CONTROL_STATE.setValue(BlockStateProperties.LEVEL, emitDirection)) {
        		level.setBlockAndUpdate(pos_d, UNBROKEN_CEMENT_STATE);
        	}
        }
        for(BlockPos pos_a : posAir) {
        	if(level.getBlockState(pos_a)!=UNBROKEN_FOG_STATE) {
        		level.setBlockAndUpdate(pos_a, AIR_STATE);
        	}
        }
        
        Vec3i moving;
        switch (emitDirection) {
            case 0: 
                moving = new Vec3i(-1, 0, 0);
                break;
            case 1: 
                moving = new Vec3i(-1, 0, 1);
                break;
            case 2: 
                moving = new Vec3i(0, 0, 1);
                break;
            case 3: 
                moving = new Vec3i(1, 0, 1);
                break;
            case 4: 
                moving = new Vec3i(1, 0, 0);
                break;
            case 5: 
                moving = new Vec3i(1, 0, -1);
                break;
            case 6: 
                moving = new Vec3i(0, 0, -1);
                break;
            case 7: 
                moving = new Vec3i(-1, 0, -1);
                break;
            default:
                throw new IllegalArgumentException("Unexpected value: " + emitDirection);
        }

        int stop = 0;
        boolean keep_moving;

        do {
        	keep_moving = false;
            stop++;
            for (int i = 0; i < posDoor.length; i++) {
                posDoor[i] = posDoor[i].offset(moving);
                BlockState currentState = level.getBlockState(posDoor[i]);
                if (currentState != AIR_STATE && currentState != UNBROKEN_CEMENT_STATE) {
                    level.setBlockAndUpdate(posDoor[i], UNBROKEN_CEMENT_STATE);
                    keep_moving = true;
                }
            }

            for (int i = 0; i < posAir.length; i++) {
                posAir[i] = posAir[i].offset(moving);
                if (level.getBlockState(posAir[i]) != AIR_STATE) {
                    level.setBlockAndUpdate(posAir[i], AIR_STATE);
                    keep_moving = true;
                }
            }
        } while (keep_moving && stop < 10);

    }
    
    /**
     * 先放斜线再放直线, 斜线不用延长; 
     * 直线段在斜线之前，要放2个延后; 
     * 直线段在斜线之后，要放2个在前面;
     */
    public static void followChainCode(Level level, BlockPos startPos, BlockPos endPos, int start_direction, int end_direction, List<Integer> chainCode) {
        List<List<Integer>> chainSegments = split(chainCode);
        int num = chainSegments.size();
        if (num <= 0) {
            return;
        }

        int height = endPos.getY() - startPos.getY();
        boolean goUp = height > 0;
        int totalSteps = chainCode.size();
        int[] down_id = null;

        if (height != 0) {
            down_id = new int[Math.abs(height)];
            for (int i = 0; i < Math.abs(height); i++) {
                down_id[i] = (i + 1) * totalSteps / (Math.abs(height) + 1);
            }
        }

        BlockPos[] poses = new BlockPos[num];
        poses[0] = startPos;

        List<List<Integer>> oddSegments = new ArrayList<>();
        List<List<Integer>> evenSegments = new ArrayList<>();
        List<List<Integer>> combinedSegments = new ArrayList<>();
        List<Integer> odd_id = new ArrayList<>();
        List<Integer> even_id = new ArrayList<>();

        // Separate odd and even segments
        int id = 0;
        for (List<Integer> segment : chainSegments) {
            if (segment.get(0) % 2 == 1) {
                oddSegments.add(segment);
                combinedSegments.add(segment);
                odd_id.add(id);
            } else {
                evenSegments.add(segment);
                combinedSegments.add(segment);
                even_id.add(id);
            }
            id++;
        }

        // Calculate positions for each segment
        for (int i = 1; i < num; i++) {
            List<Integer> previousSegment = combinedSegments.get(i - 1);
            BlockPos previousPos = poses[i - 1];
            BlockPos currentPos = previousPos;

            for (int direction : previousSegment) {
                Vec3i directionVector = getDirectionVector(direction);
                currentPos = currentPos.offset(directionVector.getX(), directionVector.getY(), directionVector.getZ());
            }

            poses[i] = currentPos;
        }

        // Place blocks for odd segments
        for (int i = 0; i < oddSegments.size(); i++) {
            BlockPos currentPos = poses[odd_id.get(i)];
            List<Integer> segment = oddSegments.get(i);
            boolean isFirst = odd_id.get(i) == 0;
            boolean isLast = odd_id.get(i) == combinedSegments.size() - 1;

            List<Integer> extendedSegment = extendSegment(segment, true, isFirst, isLast);

            int direction = extendedSegment.get(0);
            int length = extendedSegment.size();
            Vec3i directionVector = getDirectionVector(direction);

            setConnection(level, directionVector, currentPos, length, direction, down_id, goUp);
        }

        // Place blocks for even segments
        for (int i = 0; i < evenSegments.size(); i++) {
            BlockPos currentPos = poses[even_id.get(i)];
            List<Integer> segment = evenSegments.get(i);
            boolean isFirst = even_id.get(i) == 0;
            boolean isLast = even_id.get(i) == combinedSegments.size() - 1;

            List<Integer> extendedSegment = extendSegment(segment, false, isFirst, isLast);

            // Move the start point back if not the first segment and the segment is even
            if (!isFirst) {
                Vec3i directionVector = getDirectionVector(segment.get(0));
                currentPos = currentPos.offset(-2 * directionVector.getX(), -2 * directionVector.getY(), -2 * directionVector.getZ());
            }
            int direction = extendedSegment.get(0);
            int length = extendedSegment.size();
            Vec3i directionVector = getDirectionVector(direction);
            setConnection(level, directionVector, currentPos, length, direction, down_id, goUp);
        }

        fromBaseGrowConnection(level, start_direction, startPos, endPos, start_direction, end_direction);
    }

    private static void fromBaseGrowConnection(Level level, int direction, BlockPos startPos, BlockPos endPos, int start_direction, int end_direction) {
        Vec3i directionVector = getDirectionVector(start_direction);
        setConnection(level, directionVector, startPos.offset(-directionVector.getX(), -directionVector.getY(), -directionVector.getZ()), 3, start_direction, null, false);
        directionVector = getDirectionVector(end_direction);
        setConnection(level, directionVector, endPos.offset(-directionVector.getX(), -directionVector.getY(), -directionVector.getZ()), 3, end_direction, null, false);
    }

    /**
     * 这里就是最基础的，根据链码放置 Block
     * @param level
     * @param directionVector
     * @param currentPos
     * @param size
     * @param direction
     * @param down_id
     * @param goUp
     */
    private static void setConnection(Level level, Vec3i directionVector, BlockPos currentPos, int size, int direction, int[] down_id, boolean goUp) {
        BlockPos pos = currentPos;
        BlockPos[] posDoor;
        BlockPos[] posAir;
        int normalizedDirection = direction % 4;

        switch (normalizedDirection) {
            case 0:
                posDoor = new BlockPos[]{
                    pos, pos.north(), pos.north().north(), pos.south(), pos.south().south(),
                    pos.offset(0, 1, 2), pos.offset(0, 1, -2), pos.offset(0, 2, 2),
                    pos.offset(0, 2, -2), pos.offset(0, 3, 2), pos.offset(0, 3, -2),
                    pos.offset(0, 4, 1), pos.offset(0, 4, 0), pos.offset(0, 4, -1)
                };
                posAir = new BlockPos[]{
                    pos.offset(0, 1, 1), pos.offset(0, 1, 0), pos.offset(0, 1, -1),
                    pos.offset(0, 2, 1), pos.offset(0, 2, 0), pos.offset(0, 2, -1),
                    pos.offset(0, 3, 1), pos.offset(0, 3, 0), pos.offset(0, 3, -1)
                };
                break;
            case 2:
                posDoor = new BlockPos[]{
                    pos, pos.east(), pos.east().east(), pos.west(), pos.west().west(),
                    pos.offset(2, 1, 0), pos.offset(-2, 1, 0), pos.offset(2, 2, 0),
                    pos.offset(-2, 2, 0), pos.offset(2, 3, 0), pos.offset(-2, 3, 0),
                    pos.offset(1, 4, 0), pos.offset(0, 4, 0), pos.offset(-1, 4, 0)
                };
                posAir = new BlockPos[]{
                    pos.offset(-1, 1, 0), pos.offset(0, 1, 0), pos.offset(1, 1, 0),
                    pos.offset(-1, 2, 0), pos.offset(0, 2, 0), pos.offset(1, 2, 0),
                    pos.offset(-1, 3, 0), pos.offset(0, 3, 0), pos.offset(1, 3, 0)
                };
                break;
            case 1:
                posDoor = new BlockPos[]{
                    pos, pos.offset(1, 0, 1), pos.offset(-1, 0, -1), pos.offset(2, 0, 2),
                    pos.offset(-2, 0, -2), pos.offset(2, 1, 2), pos.offset(-2, 1, -2),
                    pos.offset(2, 2, 2), pos.offset(-2, 2, -2), pos.offset(2, 3, 2),
                    pos.offset(-2, 3, -2),
                    pos.offset(1, 4, 1), pos.offset(-1, 4, -1), pos.offset(0, 4, 0)
                };
                posAir = new BlockPos[]{
                    pos.offset(1, 1, 1), pos.offset(0, 1, 0), pos.offset(-1, 1, -1),
                    pos.offset(1, 2, 1), pos.offset(0, 2, 0), pos.offset(-1, 2, -1),
                    pos.offset(1, 3, 1), pos.offset(0, 3, 0), pos.offset(-1, 3, -1)
                };
                break;
            case 3:
                posDoor = new BlockPos[]{
                    pos, pos.offset(-1, 0, 1), pos.offset(1, 0, -1), pos.offset(-2, 0, 2),
                    pos.offset(2, 0, -2), pos.offset(-2, 1, 2), pos.offset(2, 1, -2),
                    pos.offset(-2, 2, 2), pos.offset(2, 2, -2), pos.offset(-2, 3, 2),
                    pos.offset(2, 3, -2),
                    pos.offset(-1, 4, 1), pos.offset(1, 4, -1), pos.offset(0, 4, 0)
                };
                posAir = new BlockPos[]{
                    pos.offset(-1, 1, 1), pos.offset(0, 1, 0), pos.offset(1, 1, -1),
                    pos.offset(-1, 2, 1), pos.offset(0, 2, 0), pos.offset(1, 2, -1),
                    pos.offset(-1, 3, 1), pos.offset(0, 3, 0), pos.offset(1, 3, -1)
                };
                break;
            default:
                posDoor = new BlockPos[]{};
                posAir = new BlockPos[]{};
                break;
        }

        // Handle additional emitDirections
        if (direction == 1 || direction == 5 || direction == 3 || direction == 7) {
            if (direction == 1) {
                posDoor = extendPosDoor(posDoor, pos, new BlockPos[]{
                    pos.offset(0, 0, 1), pos.offset(-1, 0, 0), pos.offset(1, 0, 2),
                    pos.offset(-2, 0, -1), pos.offset(0, 4, 1), pos.offset(-1, 4, 0),
                    pos.offset(1, 4, 2), pos.offset(-2, 4, -1)
                });
                posAir = extendPosAir(posAir, pos, new BlockPos[]{
                    pos.offset(0, 1, 1), pos.offset(-1, 1, 0), pos.offset(1, 1, 2),
                    pos.offset(-2, 1, -1), pos.offset(0, 2, 1), pos.offset(-1, 2, 0),
                    pos.offset(1, 2, 2), pos.offset(-2, 2, -1), pos.offset(0, 3, 1),
                    pos.offset(-1, 3, 0), pos.offset(1, 3, 2), pos.offset(-2, 3, -1)
                });
            } else if (direction == 5) {
                posDoor = extendPosDoor(posDoor, pos, new BlockPos[]{
                    pos.offset(0, 0, -1), pos.offset(1, 0, 0), pos.offset(-1, 0, -2),
                    pos.offset(2, 0, 1), pos.offset(0, 4, -1), pos.offset(1, 4, 0),
                    pos.offset(-1, 4, -2), pos.offset(2, 4, 1)
                });
                posAir = extendPosAir(posAir, pos, new BlockPos[]{
                    pos.offset(0, 1, -1), pos.offset(1, 1, 0), pos.offset(-1, 1, -2),
                    pos.offset(2, 1, 1),
                    pos.offset(0, 2, -1), pos.offset(1, 2, 0), pos.offset(-1, 2, -2),
                    pos.offset(2, 2, 1),
                    pos.offset(0, 3, -1), pos.offset(1, 3, 0), pos.offset(-1, 3, -2),
                    pos.offset(2, 3, 1),
                });
            } else if (direction == 3) {
                posDoor = extendPosDoor(posDoor, pos, new BlockPos[]{
                    pos.offset(0, 0, 1), pos.offset(1, 0, 0), pos.offset(-1, 0, 2),
                    pos.offset(2, 0, -1), pos.offset(0, 4, 1), pos.offset(1, 4, 0),
                    pos.offset(-1, 4, 2), pos.offset(2, 4, -1)
                });
                posAir = extendPosAir(posAir, pos, new BlockPos[]{
                    pos.offset(0, 1, 1), pos.offset(1, 1, 0), pos.offset(-1, 1, 2),
                    pos.offset(2, 1, -1), pos.offset(0, 2, 1), pos.offset(1, 2, 0),
                    pos.offset(-1, 2, 2), pos.offset(2, 2, -1), pos.offset(0, 3, 1),
                    pos.offset(1, 3, 0), pos.offset(-1, 3, 2), pos.offset(2, 3, -1)
                });
            } else if (direction == 7) {
                posDoor = extendPosDoor(posDoor, pos, new BlockPos[]{
                    pos.offset(0, 0, -1), pos.offset(-1, 0, 0), pos.offset(1, 0, -2),
                    pos.offset(-2, 0, 1), pos.offset(0, 4, -1), pos.offset(-1, 4, 0),
                    pos.offset(1, 4, -2), pos.offset(-2, 4, 1)
                });
                posAir = extendPosAir(posAir, pos, new BlockPos[]{
                    pos.offset(0, 1, -1), pos.offset(-1, 1, 0), pos.offset(1, 1, -2),
                    pos.offset(-2, 1, 1), pos.offset(0, 2, -1), pos.offset(-1, 2, 0),
                    pos.offset(1, 2, -2), pos.offset(-2, 2, 1), pos.offset(0, 3, -1),
                    pos.offset(-1, 3, 0), pos.offset(1, 3, -2), pos.offset(-2, 3, 1)
                });
            }
        }

        boolean shouldMoveY = false;
        boolean haveInit = false;
        
        int totalGo = 0;
        int isTry = 0;

        for (int i = 1; i <= size; i++) {
            if (!haveInit) {
                haveInit = true;
                if (down_id != null) {
                    if (isTry > 0) {
                        for (int j = 0; j < posDoor.length; j++) {
                            posDoor[j] = posDoor[j].offset(0, goUp ? isTry : -isTry, 0);
                        }
                        for (int j = 0; j < posAir.length; j++) {
                            posAir[j] = posAir[j].offset(0, goUp ? isTry : -isTry, 0);
                        }
                    }
                }
            }

            totalGo++;
            if (down_id != null && isTry < down_id.length && totalGo == down_id[isTry]) {
                isTry++;
                shouldMoveY = true;
            }

            for (int j = 0; j < posDoor.length; j++) {
                if (down_id != null && shouldMoveY) {
                    posDoor[j] = posDoor[j].offset(0, goUp ? 1 : -1, 0);
                }
                posDoor[j] = posDoor[j].offset(directionVector);
                BlockState currentState = level.getBlockState(posDoor[j]);
                if (currentState != JUNCTION_STATE
                        && !currentState.is(JUNCTION_CONTROL_STATE.getBlock())
                        && currentState != AIR_STATE) {
                    level.setBlockAndUpdate(posDoor[j], UNBROKEN_CEMENT_STATE);
                }
            }
            for (int j = 0; j < posAir.length; j++) {
                if (down_id != null && shouldMoveY) {
                    posAir[j] = posAir[j].offset(0, goUp ? 1 : -1, 0);
                }
                posAir[j] = posAir[j].offset(directionVector);
                BlockState currentState = level.getBlockState(posAir[j]);
                if (currentState == UNBROKEN_FOG_STATE || currentState == Blocks.AIR.defaultBlockState()) {
                    level.setBlockAndUpdate(posAir[j], AIR_STATE);
                }
            }
            shouldMoveY = false;
        }
    }


	private static List<Integer> extendSegment(List<Integer> segment, boolean isOdd, boolean isFirst, boolean isLast) {
        List<Integer> extendedSegment = new ArrayList<>(segment);

        if (!isOdd) {
            if (!isLast) {
                extendedSegment.add(segment.get(segment.size() - 1));
                extendedSegment.add(segment.get(segment.size() - 1));
            }
            if (!isFirst) {
                extendedSegment.add(segment.get(segment.size() - 1));
                extendedSegment.add(segment.get(segment.size() - 1));
            }
        }

        return extendedSegment;
    }
    
    public static List<List<Integer>> split(List<Integer> chainCode) {
        List<List<Integer>> segments = new ArrayList<>();
        if (chainCode.isEmpty()) {
            return segments;
        }

        List<Integer> currentSegment = new ArrayList<>();
        int previousValue = chainCode.get(0);

        for (int value : chainCode) {
            if (value == previousValue) {
                currentSegment.add(value);
            } else {
                segments.add(new ArrayList<>(currentSegment));
                currentSegment.clear();
                currentSegment.add(value);
            }
            previousValue = value;
        }

        if (!currentSegment.isEmpty()) {
            segments.add(currentSegment);
        }

        return segments;
    }
    
    public static List<Integer> birthConnection(BlockPos startPos, int start_direction, BlockPos endPos, int end_direction, Player player) {
        Vec3i start = new Vec3i(startPos.getX(), startPos.getY(), startPos.getZ());
        Vec3i end = new Vec3i(endPos.getX(), endPos.getY(), endPos.getZ());
        if (!checkChainCode(start, start_direction, end, end_direction,player)) {
            return Collections.emptyList(); // No valid connection
        }
        return birthChainCode(start, start_direction, end, end_direction,player);
    }

    /**
     * 根据起始点和终止点，生成链码，指示连接的移动方式
     * <p>链码要求：
     * <p>1.首末方向根据 穹顶连接基座的方向
     * <p>2.中途方向变化的幅度不能大于1
     * <p>3.尽量少的变化
     * <p>目前使用A*寻路
     * @param start
     * @param start_direction
     * @param end
     * @param end_direction
     * @param player
     * @return 链码 List<Integer>
     */
    private static List<Integer> birthChainCode(Vec3i start, int start_direction, Vec3i end, int end_direction, Player player) {
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingDouble(Node::getEstimatedTotalCost));
        Map<Vec3i, Node> allNodes = new HashMap<>();
        Node startNode = new Node(start, new ArrayList<>(), 0, heuristic(start, end), start_direction);
        openSet.add(startNode);
        allNodes.put(start, startNode);

        int nodesProcessed = 0;

        while (!openSet.isEmpty()) {
            if (nodesProcessed > 720) {
            	player.sendSystemMessage(Component.translatable("dorm.connection.unable.toomuchnode"));
                return Collections.emptyList(); // Stop if too many nodes are processed
            }

            Node currentNode = openSet.poll();
            Vec3i currentPos = currentNode.position;
            List<Integer> currentPath = currentNode.path;
            double currentDistance = currentNode.gCost;
            int currentDirection = currentNode.direction;

            if (currentPos.getX()==end.getX()&&currentPos.getZ()==end.getZ()) {
                if (currentPath.size() > 100) {
                	player.sendSystemMessage(Component.translatable("dorm.connection.unable.toolongpath"));
                    return Collections.emptyList(); // Path is too long
                }
                if (!currentPath.isEmpty()) {
                    currentPath.remove(currentPath.size() - 1); // Remove the last element because the last is already exist
                }
                player.sendSystemMessage(Component.translatable("dorm.connection.able"));
                return currentPath;
            }

            for (int direction = 0; direction < 8; direction++) {
                if (Math.abs(currentDirection - direction) <= 1 || Math.abs(currentDirection - direction) == 7) {
                    Vec3i directionVector = getDirectionVector(direction);
                    Vec3i nextPos = currentPos.offset(directionVector.getX(), directionVector.getY(), directionVector.getZ());
                    double directionCost = (direction % 2 == 1) ? 1.4 : 1.0;
                    double preferenceFactor = 1.0;

                    if (currentPath.size() < 5 && direction == start_direction) {
                        preferenceFactor = 0.5; // Lower cost for start direction in the first 5 steps
                    }

                    double distanceToEnd = heuristic(nextPos, end);
                    if (distanceToEnd < 5 && direction == end_direction) {
                        preferenceFactor = 0.5; // Lower cost for end direction when close to the end
                    }

                    double directionChangePenalty = (direction != currentDirection) ? 0.5 : 0.0; // Penalty for direction change

                    double tentativeGCost = currentDistance + directionCost * preferenceFactor + directionChangePenalty;

                    Node nextNode = allNodes.getOrDefault(nextPos, new Node(nextPos, new ArrayList<>(currentPath), Double.MAX_VALUE, heuristic(nextPos, end), direction));

                    if (tentativeGCost < nextNode.gCost) {
                        nextNode.gCost = tentativeGCost;
                        nextNode.path = new ArrayList<>(currentPath);
                        nextNode.path.add(direction);
                        nextNode.direction = direction;
                        openSet.add(nextNode);
                        allNodes.put(nextPos, nextNode);
                    }
                }
            }

            nodesProcessed++;
        }
        player.sendSystemMessage(Component.translatable("dorm.connection.unable.nopathfind"));
        return Collections.emptyList(); // No path found
    }

    /**
     * 启发
     * @param a
     * @param b
     * @return
     */
    private static double heuristic(Vec3i a, Vec3i b) {
        // Improved heuristic: Manhattan distance with slight bias for straight moves
        int dx = Math.abs(a.getX() - b.getX());
        int dz = Math.abs(a.getZ() - b.getZ());
        int straightMoves = Math.abs(dx - dz);
        int diagonalMoves = Math.min(dx, dz);
        return straightMoves + 1.4 * diagonalMoves; // 1.4 approximates sqrt(2) for diagonals
    }

    private static class Node {
        Vec3i position;
        List<Integer> path;
        double gCost; // Cost from start to this node
        double hCost; // Heuristic cost to the goal
        int direction;

        Node(Vec3i position, List<Integer> path, double gCost, double hCost, int direction) {
            this.position = position;
            this.path = path;
            this.gCost = gCost;
            this.hCost = hCost;
            this.direction = direction;
        }

        double getEstimatedTotalCost() {
            return gCost + hCost;
        }
    }

    /**
     * 是对一些基本情况，无法生成链码或者生成的链码容易出现bug进行排除
     * @param start
     * @param start_direction
     * @param end
     * @param end_direction
     * @param player
     * @return 无法生成 - false
     */
    private static boolean checkChainCode(Vec3i start, int start_direction, Vec3i end, int end_direction, Player player) {
    	end_direction = (end_direction+4)%8;
        int directionDifference = Math.abs(start_direction - end_direction);
        if (directionDifference > 1 && directionDifference != 7) {
        	player.sendSystemMessage(Component.translatable("dorm.connection.unable.anglechangetoofast"));
            return false;
        }

        // If start and end directions are the same, handle direct path checks
        boolean easy = false;
        if (start_direction == end_direction && start.getY() == end.getY()) {
            switch (start_direction) {
                case 0: easy= start.getZ() == end.getZ() && start.getX() < end.getX(); break; // East
                case 1: easy= (start.getX() - end.getX()) + (start.getZ() - end.getZ()) == 0; break; // East-North
                case 2: easy= start.getZ() == end.getZ() && start.getX() == end.getX() && start.getZ() > end.getZ(); break; // North
                case 3: easy= (start.getX() - end.getX()) - (start.getZ() - end.getZ()) == 0; break; // West-North
                case 4: easy= start.getZ() == end.getZ() && start.getX() > end.getX(); break; // West
                case 5: easy= (start.getX() - end.getX()) + (start.getZ() - end.getZ()) == 0; break; // West-South
                case 6: easy= start.getZ() == end.getZ() && start.getX() == end.getX() && start.getZ() < end.getZ(); break; // South
                case 7: easy= (start.getX() - end.getX()) - (start.getZ() - end.getZ()) == 0; break; // East-South
                default:
                    throw new IllegalArgumentException("Unexpected value: " + start_direction);
            }
        }
        if(easy) {
        	return true;
        }

        int m_dis = Math.abs(start.getX() - end.getX()) + Math.abs(start.getZ() - end.getZ());
        if (m_dis < 8) { 
        	player.sendSystemMessage(Component.translatable("dorm.connection.unable.tooclose"));
        	return false;
        }

        if (m_dis < 6 * Math.abs(start.getY() - end.getY())) {
        	player.sendSystemMessage(Component.translatable("dorm.connection.unable.toosteep"));
            return false;
        }
        
        if(m_dis>100) { 
        	player.sendSystemMessage(Component.translatable("dorm.connection.unable.toofar"));
        	return false;
        }

        Vec3i startVector = getDirectionVector(start_direction);
        Vec3i endVector = getDirectionVector((end_direction + 4) % 8); // Opposite direction

        int maxSteps = 50;

        for (int i = 0; i < maxSteps; i++) {
            Vec3i startRayPoint = new Vec3i(start.getX() + startVector.getX() * i, start.getY(), start.getZ() + startVector.getZ() * i);
            for (int j = 0; j < maxSteps; j++) {
                Vec3i endRayPoint = new Vec3i(end.getX() + endVector.getX() * j, end.getY(), end.getZ() + endVector.getZ() * j);
                if (Math.abs(startRayPoint.getX() - endRayPoint.getX()) + Math.abs(startRayPoint.getZ() - endRayPoint.getZ()) <= 1) {
                    return true; // Intersection found
                }
            }
        }
        
        player.sendSystemMessage(Component.translatable("dorm.connection.unable.anglenotgood"));
        return false;
    }

    
    private static Vec3i getDirectionVector(int direction) {
        switch (direction%8) {
            case 0: return new Vec3i(1, 0, 0);  // East
            case 1: return new Vec3i(1, 0, -1); // East-North
            case 2: return new Vec3i(0, 0, -1); // North
            case 3: return new Vec3i(-1, 0, -1);// West-North
            case 4: return new Vec3i(-1, 0, 0); // West
            case 5: return new Vec3i(-1, 0, 1); // West-South
            case 6: return new Vec3i(0, 0, 1);  // South
            case 7: return new Vec3i(1, 0, 1);  // East-South
            default: throw new IllegalArgumentException("Invalid direction: " + direction);
        }
    }

    private static BlockPos[] extendPosDoor(BlockPos[] original, BlockPos pos, BlockPos[] extensions) {
        BlockPos[] newArray = new BlockPos[original.length + extensions.length];
        System.arraycopy(original, 0, newArray, 0, original.length);
        System.arraycopy(extensions, 0, newArray, original.length, extensions.length);
        return newArray;
    }

    private static BlockPos[] extendPosAir(BlockPos[] original, BlockPos pos, BlockPos[] extensions) {
        BlockPos[] newArray = new BlockPos[original.length + extensions.length];
        System.arraycopy(original, 0, newArray, 0, original.length);
        System.arraycopy(extensions, 0, newArray, original.length, extensions.length);
        return newArray;
    }

    
    
}