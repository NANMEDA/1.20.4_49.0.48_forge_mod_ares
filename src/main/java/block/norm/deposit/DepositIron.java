package block.norm.deposit;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class DepositIron extends Deposit {
	public static final String global_name = "_iron";

	public DepositIron(Properties p_49795_) {
		super(p_49795_);
	}
	
	@SuppressWarnings("deprecation")
	@Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
		super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }
	
	
}
