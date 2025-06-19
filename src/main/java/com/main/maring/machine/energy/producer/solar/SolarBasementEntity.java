package com.main.maring.machine.energy.producer.solar;

import java.util.Map;
import java.util.HashMap;

import com.main.maring.machine.energy.producer.IProducer;
import net.minecraft.nbt.ListTag;
import com.main.maring.machine.energy.producer.ProducerEntity;
import com.main.maring.machine.registry.MBlockEntityRegister;
import com.main.maring.machine.registry.MBlockRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class SolarBasementEntity extends ProducerEntity implements IProducer {

	public SolarBasementEntity(BlockPos pos, BlockState pBlockState) {
		super(MBlockEntityRegister.SOLARBASEMENT_BE.get(), pos, pBlockState);
	}
	

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
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

	protected void savedata(CompoundTag tag) {
		super.savedata(tag);
	}
	
	protected void loaddata(CompoundTag tag) {
		super.loaddata(tag);
	}
	
	@Override
	public int provideEnergySupply() {
	    int lightLevel = this.level.getBrightness(LightLayer.SKY, this.worldPosition);
	    if (!this.level.isDay()) {
	        return 0;
	    } else if (this.level.isThundering() || this.level.isRaining()) {
	        lightLevel *= 0.5;
	    }
	    MutableBlockPos pos = this.worldPosition.above().mutable();
	    int count = 0;
	    if (this.level.getBlockState(pos).is(MBlockRegister.SOLARPILLAR_B.get())) {
	        count = getPanelCount(pos.above(), this.level);
	    }
	    return (count*lightLevel)/13;
	}
	
	private int getPanelCount(BlockPos pos, Level level) {
		double count = 0;
		for(int i=-1;i<2;i++) {
			for(int j=-1;j<2;j++) {
				if(level.getBlockState(pos.offset(i, 0, j)).is(MBlockRegister.SOLARPANEL_B.get())){
					count+=1.0;
				}else if(level.getBlockState(pos.offset(i, 0, j)).is(Blocks.DAYLIGHT_DETECTOR)){
					count+=0.5;
				}
			}
		}
		return (int)count;
	}
	

	
	@Override
	protected void servertick() {
		if(isDirty)
		level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
	}

	@Override
	protected void clienttick() {
	}

}
