package block.entity.consumer;

import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class PowerConsumerEntity extends BlockEntity {

    public PowerConsumerEntity(BlockEntityType<?> p_155228_, BlockPos p_155229_, BlockState p_155230_) {
		super(p_155228_, p_155229_, p_155230_);
	}

	protected int energy_consume;
    protected int FULL_ENERGY_CONSUPTION;
    protected short energy_supply = 100;
    
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    protected void servertick() {
    };
    
    protected boolean servertick(boolean u) {
    	return u;
    };

    protected abstract void clienttick();
    
    protected void getEnergySupply() {
    	
    }
}
