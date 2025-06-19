package com.main.maring.machine.energy.producer.reactor.mar;

import java.util.Map;

import com.main.maring.item.ItemRegister;

import java.util.HashMap;

import com.main.maring.machine.energy.producer.IProducer;
import net.minecraft.nbt.ListTag;
import com.main.maring.machine.energy.producer.ProducerEntity;
import com.main.maring.machine.registry.MBlockEntityRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class MarReactorEntity extends ProducerEntity implements IProducer {

	public MarReactorEntity(BlockPos pos, BlockState pBlockState) {
		super(MBlockEntityRegister.MARREACTOR_BE.get(), pos, pBlockState);
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
    

    protected final ItemStackHandler item = new ItemStackHandler(1) {//必须要在这里创建，ItemStackHandler不可被修改
        @Override
        public void onLoad() {
            super.onLoad();
            //System.out.println("entity is load");
        }

        @Override
        protected void onContentsChanged(int slot) {
        }
    };

    protected final LazyOptional<IItemHandler> itemHandler = LazyOptional.of(() -> item);

    public ItemStackHandler getItems() {
        return item;
    }

	private final String TAG_ID = "id";
	private final String TAG_NET = "connection";
	private final String TAG_ITEM = "com/main/maring/item";
	
	protected void savedata(CompoundTag tag) {
		super.savedata(tag);
	    tag.put(TAG_ITEM, item.serializeNBT());
	}
	
	protected void loaddata(CompoundTag tag) {
		super.loaddata(tag);
		if(tag.contains(TAG_ITEM)) {
			item.deserializeNBT(tag.getCompound(TAG_ITEM));
		}
	}
	
	
	
	
	@Override
	public int provideEnergySupply() {
		int gen = 100;
		ItemStack in = this.getItems().getStackInSlot(0);
		if(in.is(ItemRegister.OMINOUS_GEMSTONE_REACTOR.get())) {
			gen+= in.getCount() * 100;
		}
		return gen;
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
