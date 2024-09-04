package machine.energy.producer.solar;

import machine.energy.producer.IProducer;
import machine.energy.producer.ProducerEntity;
import machine.registry.MBlockEntityRegister;
import machine.registry.MBlockRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.state.BlockState;

public class SolarBasementEntity extends ProducerEntity implements IProducer {

	public SolarBasementEntity(BlockPos pos, BlockState pBlockState) {
		super(MBlockEntityRegister.SOLARBASEMENT_BE.get(), pos, pBlockState);
		this.FULL_ENERGY = 0;
		this.NET = 0;
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
	
	private static final String TAG_NET = "net";
	
	protected void savedata(CompoundTag tag) {
		tag.putLong(TAG_NET, this.getNet());
	}
	
	protected void loaddata(CompoundTag tag) {
		if(tag.contains(TAG_NET)) {
			this.setNet(tag.getLong(TAG_NET));
		}
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
		int count = 0;
		for(int i=-1;i<2;i++) {
			for(int j=-1;j<2;j++) {
				if(level.getBlockState(pos.offset(i, 0, j)).is(MBlockRegister.SOLARPANEL_B.get())){
					count++;
				}
			}
		}
		return count;
	}
	

	
	@Override
	protected void servertick() {
	}

	@Override
	protected void clienttick() {
	}

}
