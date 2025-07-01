package com.main.maring.block.entity.neutral.fastbuild;

import com.main.maring.block.entity.BlockEntityRegister;
import com.main.maring.block.entity.consumer.PowerConsumerEntity;
import com.main.maring.block.norm.BlockRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.*;

/**
 * 穹顶链接块
 * 假如有链接，保存对方的blockpos
 * 否则保存自己的
 * @author NANMEDA
 * */
public class DormJunctionControlEntity extends PowerConsumerEntity{
	private int X,Y,Z;

	
	public DormJunctionControlEntity(BlockPos pos, BlockState pBlockState) {
		super(BlockEntityRegister.dormjunctioncontrol_BLOCKENTITY.get(), pos, pBlockState);
		savePosData(this.getBlockPos());
	}

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        savedata(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        loaddata(tag);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        savedata(tag);
        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        if (tag != null) {
            loaddata(tag);
        }
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection connection, ClientboundBlockEntityDataPacket packet) {
        CompoundTag tag = packet.getTag();
        if (tag != null) {
            loaddata(tag);
        }
    }

	
	private final String TAG_X = "save_junc_x";
	private final String TAG_Y = "save_junc_y";
	private final String TAG_Z = "save_junc_z";
	
	protected void savedata(CompoundTag tag) {
		tag.putInt(TAG_X, X);
		tag.putInt(TAG_Y, Y);
		tag.putInt(TAG_Z, Z);
	}
	
	protected void loaddata(CompoundTag tag) {
		if(tag.contains(TAG_X)) {
			X = tag.getInt(TAG_X);
		}
		if(tag.contains(TAG_Y)) {
			Y = tag.getInt(TAG_Y);
		}		
		if(tag.contains(TAG_Z)) {
			Z = tag.getInt(TAG_Z);
		}
	}


	/**
	 * 获得对面的方块
	 * 假如穹顶链接块之间链接，保存对方的BlockPos，否则保存自己的
	 * @return
	 */
	public BlockPos getOppositePos() {
		return new BlockPos(X,Y,Z);
	}
	
	public void savePosData(BlockPos pos) {
		X = pos.getX();
		Y = pos.getY();
		Z = pos.getZ();
		setChanged();
	}

	public boolean connected(){
		BlockPos pos = this.getBlockPos();
		return X!=pos.getX()||Y!=pos.getY()&&Z!=pos.getZ();
	}
	
	@Override
	public void servertick() {
	}
	

	@Override
	public void clienttick() {
	}

	public void removeBlockConnetcions(){
		BlockPos pos = this.getBlockPos();
		if(X!=pos.getX()||Y!=pos.getY()&&Z!=pos.getZ()){
			BlockPos[] posDoor;
			BlockPos[] posAir;
			int direction = this.getBlockState().getValue(BlockStateProperties.LEVEL);
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

			Vec3i moving;

			switch (direction) {
				case 0:
					moving = new Vec3i(1, 0, 0);
					break;
				case 1:
					moving = new Vec3i(1, 0, -1);
					break;
				case 2:
					moving = new Vec3i(0, 0, -1);
					break;
				case 3:
					moving = new Vec3i(-1, 0, -1);
					break;
				case 4:
					moving = new Vec3i(-1, 0, 0);
					break;
				case 5:
					moving = new Vec3i(-1, 0, 1);
					break;
				case 6:
					moving = new Vec3i(0, 0, 1);
					break;
				case 7:
					moving = new Vec3i(1, 0, 1);
					break;
				default:
					moving = Vec3i.ZERO;
			}
			BlockPos startPos  = pos.offset(moving);


			// 限制最大数量
			final int MAX_BLOCKS = 5000;
			TagKey<Block> clean = BlockTags.create(new ResourceLocation("maring:unbreakable_block"));
			BlockState A_AIR_STATE = BlockRegister.A_AIR.get().defaultBlockState();
			// 初始化搜索
			Queue<BlockPos> toCheck = new ArrayDeque<>();
			Set<BlockPos> visited = new HashSet<>();
			List<BlockPos> result = new ArrayList<>();

			toCheck.add(startPos);
			visited.add(startPos);

			while (!toCheck.isEmpty() && result.size() < MAX_BLOCKS) {
				BlockPos current = toCheck.poll();

				// 判断当前方块是否属于自定义tag
				BlockState state = level.getBlockState(current);
				if (!state.is(clean)&&state!=A_AIR_STATE) continue;

				result.add(current);

				// 在3x3x3范围内扩展
				for (int dx = -1; dx <= 1; dx++) {
					for (int dy = -1; dy <= 1; dy++) {
						for (int dz = -1; dz <= 1; dz++) {
							BlockPos offset = current.offset(dx, dy, dz);
							if (visited.contains(offset)) continue;
							visited.add(offset);
							toCheck.add(offset);
						}
					}
				}
			}

			for (BlockPos c : result) {
				level.setBlock(c, Blocks.AIR.defaultBlockState(), 2);
			}

		}else{
			//not connections
		}
	}

	public void _genDoor(BlockState state){
		BlockPos pos = this.getBlockPos();
		BlockPos[] posAir;
		int direction = this.getBlockState().getValue(BlockStateProperties.LEVEL);
		int normalizedDirection = direction % 4;
		switch (normalizedDirection) {
			case 0:
				posAir = new BlockPos[]{
						pos.offset(0, 1, 1), pos.offset(0, 1, 0), pos.offset(0, 1, -1),
						pos.offset(0, 2, 1), pos.offset(0, 2, 0), pos.offset(0, 2, -1),
						pos.offset(0, 3, 1), pos.offset(0, 3, 0), pos.offset(0, 3, -1)
				};
				break;
			case 2:
				posAir = new BlockPos[]{
						pos.offset(-1, 1, 0), pos.offset(0, 1, 0), pos.offset(1, 1, 0),
						pos.offset(-1, 2, 0), pos.offset(0, 2, 0), pos.offset(1, 2, 0),
						pos.offset(-1, 3, 0), pos.offset(0, 3, 0), pos.offset(1, 3, 0)
				};
				break;
			case 1:
				posAir = new BlockPos[]{
						pos.offset(1, 1, 1), pos.offset(0, 1, 0), pos.offset(-1, 1, -1),
						pos.offset(1, 2, 1), pos.offset(0, 2, 0), pos.offset(-1, 2, -1),
						pos.offset(1, 3, 1), pos.offset(0, 3, 0), pos.offset(-1, 3, -1)
				};
				break;
			case 3:
				posAir = new BlockPos[]{
						pos.offset(-1, 1, 1), pos.offset(0, 1, 0), pos.offset(1, 1, -1),
						pos.offset(-1, 2, 1), pos.offset(0, 2, 0), pos.offset(1, 2, -1),
						pos.offset(-1, 3, 1), pos.offset(0, 3, 0), pos.offset(1, 3, -1)
				};
				break;
			default:
				posAir = new BlockPos[]{};
				break;
		}
		for(BlockPos gen : posAir){
			level.setBlock(gen, state,3);
		}
	}

}