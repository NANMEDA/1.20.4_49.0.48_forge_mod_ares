package machine.energy.trans;

import machine.energy.EnergyEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class TransEntity extends EnergyEntity {
	
    public TransEntity(BlockEntityType<?> p_155228_, BlockPos p_155229_, BlockState p_155230_) {
		super(p_155228_, p_155229_, p_155230_);
	}

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
    
    protected abstract void servertick();
    

    protected abstract void clienttick();
    
}
