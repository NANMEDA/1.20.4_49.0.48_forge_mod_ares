package block.entity.neutral.fastbuild;

import block.entity.BlockEntityRegister;
import block.entity.consumer.PowerConsumerEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.state.BlockState;

/**
 * @author NANMEDA
 * */
public class DormJunctionControlEntity extends PowerConsumerEntity{
	private static int X ,Y,Z;

	static protected int itemstack_number = 0;
	
	
	public DormJunctionControlEntity(BlockPos pos, BlockState pBlockState) {
		super(BlockEntityRegister.stonewasher_BLOCKENTITY.get(), pos, pBlockState);
		SavePosData(pos);
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

	
	private final String TAG_X = "x";
	private final String TAG_Y = "y";
	private final String TAG_Z = "z";
	
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
	
	public BlockPos getOppositePos() {
		return new BlockPos(X,Y,Z);
	}
	public void SavePosData(BlockPos pos) {
		X = pos.getX();
		Y = pos.getY();
		Z = pos.getZ();
		setChanged();
	}
	
	@Override
	public void servertick() {
	}
	

	@Override
	public void clienttick() {
	}

}