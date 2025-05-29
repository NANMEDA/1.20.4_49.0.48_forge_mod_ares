package com.main.maring.block.entity.neutral.dormcontrol;

import com.main.maring.block.entity.BlockEntityRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import com.main.maring.util.enums.DormEnum;

import java.util.Set;
import java.util.HashSet;
/**
 * 
 * @author NANMEDA
 * */
public class DomeControlEntity extends BlockEntity{
	private DormEnum dormType;
	
	/**
	 * 数组里面最后一个是现在的
	 * 首2个是状态
	 * 总之从左到右时间从旧到新
	 * */
	private int[] O2 = {0,0,0};
	private int[] H2O = {0,0,0};
	private Set<BlockPos> connectionSet = new HashSet<>();
	
	public int[] getO2(){
		return this.O2;
	}
	public int[] getH2O(){
		return this.H2O;
	}
	public DormEnum getEnum() {
		return this.dormType;
	}
	public Set<BlockPos> getConnetSet() {
		return this.connectionSet;
	}
	
	public void setO2(int[] o) {
		this.O2 = o;
	}
	public void setH2O(int[] h) {
		this.H2O = h;
	}
	public void setEnum(DormEnum e) {
		this.dormType =e;
	}
	public void addConnetion(BlockPos pos) {
		this.connectionSet.add(pos);
	}
	public void removeConnection(BlockPos pos) {
		this.connectionSet.remove(pos);
	}
	
	/**
	 * 除掉周边的和自己有关的链接
	 * 比较safe
	 * */
	public void removeSelfFromConnection() {
		if(this.getLevel().isClientSide()) return;
		if(this.connectionSet.isEmpty()) return;
		Level level = this.getLevel();
		for(BlockPos connectBlockPos : this.connectionSet) {
			BlockEntity e = level.getBlockEntity(connectBlockPos);
			if(e instanceof DomeControlEntity d) {
				d.removeConnection(this.getBlockPos());
			}
		}
	}
	
	public DomeControlEntity(BlockPos pos, BlockState pBlockState) {
		super(BlockEntityRegister.dormcontrol_BLOCKENTITY.get(), pos, pBlockState);
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

	
	private final String TAG_Dorm = "Dtype";
	private final String TAG_O2 = "DO2";
	private final String TAG_H2O = "DH2O";
	private final String TAG_CONNECTION_SET = "DconnectionSet";
	/**
	 * 虽然说可以用ordinal
	 * 但是用名字好一点
	 * 可能改变枚举的顺序
	 * */
	protected void savedata(CompoundTag tag) {
	    if (dormType != null) {
	        tag.putString(TAG_Dorm, dormType.name());
	    }
	    tag.putIntArray(TAG_O2, this.O2);
	    tag.putIntArray(TAG_H2O, this.H2O);

	    // 使用一个 CompoundTag 存储多个 BlockPos
	    CompoundTag connectionTag = new CompoundTag();
	    int index = 0; // 用于标识每个 BlockPos
	    for (BlockPos pos : this.connectionSet) {
	        CompoundTag posTag = new CompoundTag();
	        posTag.putInt("x", pos.getX());
	        posTag.putInt("y", pos.getY());
	        posTag.putInt("z", pos.getZ());
	        connectionTag.put("pos" + index, posTag); // 使用 pos+index 作为键
	        index++;
	    }
	    tag.put(TAG_CONNECTION_SET, connectionTag);
	}

	protected void loaddata(CompoundTag tag) {
	    if (tag.contains(TAG_Dorm)) {
	        String dormTypeName = tag.getString(TAG_Dorm);
	        try {
	            dormType = DormEnum.valueOf(dormTypeName);
	        } catch (IllegalArgumentException e) {
	            dormType = DormEnum.NUL; // 默认值
	        }
	    } else {
	        dormType = DormEnum.NUL; // 默认值
	    }

	    if (tag.contains(TAG_O2)) {
	        this.O2 = tag.getIntArray(TAG_O2);
	    }
	    if (tag.contains(TAG_H2O)) {
	        this.H2O = tag.getIntArray(TAG_H2O);
	    }

	    // 加载 BlockPos 集合
	    if (tag.contains(TAG_CONNECTION_SET)) {
	        this.connectionSet.clear();
	        CompoundTag connectionTag = tag.getCompound(TAG_CONNECTION_SET);
	        for (String key : connectionTag.getAllKeys()) { // 遍历所有的键
	            CompoundTag posTag = connectionTag.getCompound(key);
	            int x = posTag.getInt("x");
	            int y = posTag.getInt("y");
	            int z = posTag.getInt("z");
	            this.connectionSet.add(new BlockPos(x, y, z));
	        }
	    }
	}

	
	public void SavePosData(BlockPos pos) {
		setChanged();
	}
	
	public void clienttick() {
		
	}
	
	public void servertick() {
		
	}
	
	/**
	 * 是自定义的
	 * */
	public void remove() {
		this.removeSelfFromConnection();
		this.getLevel().removeBlock(worldPosition, false);
	}


}