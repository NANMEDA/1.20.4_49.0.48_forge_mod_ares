package item.tool;

import block.norm.BlockRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class ItemChangeStick extends Item {
    
    private static final BlockState UNBROKEN_CEMENT_BLOCKSTATE = BlockRegister.unbrokencement_BLOCK.get().defaultBlockState();
    private static final BlockState UNBROKEN_GLASS_BLOCKSTATE = BlockRegister.unbrokenglass_BLOCK.get().defaultBlockState();
	public static final String global_name = "change_stick";
            
    public ItemChangeStick(Properties p_41383_) {
        super(p_41383_.stacksTo(1));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState currentBlockState = level.getBlockState(pos);

        if (!level.isClientSide) {
            if (currentBlockState == UNBROKEN_CEMENT_BLOCKSTATE) {
                level.setBlockAndUpdate(pos, UNBROKEN_GLASS_BLOCKSTATE);
            } else if (currentBlockState == UNBROKEN_GLASS_BLOCKSTATE) {
                level.setBlockAndUpdate(pos, UNBROKEN_CEMENT_BLOCKSTATE);
            }
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }
    
}
