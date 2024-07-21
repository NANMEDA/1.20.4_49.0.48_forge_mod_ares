package block.norm.unbroken;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

/**
 * 结构玻璃
 * 不可破坏
 * @author NANMEDA
 * */
public class BlockUnbrokenGlass extends Block {

	public static final String global_name = "unbroken_glass";
	private boolean up = false;
	private boolean down = false;
	private boolean left = false;
	private boolean right = false;
	private boolean front = false;
	private boolean behind = false;
	
	//public static final BooleanProperty CUSTOM_TEXTURE = BooleanProperty.create("custom_texture");
	
	public BlockUnbrokenGlass(Properties properties) {
	    super(properties);
	    //this.registerDefaultState(this.stateDefinition.any().setValue(CUSTOM_TEXTURE, false));
	}
	/*
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
	    builder.add(CUSTOM_TEXTURE);
	}
	*/
	
	/***
	 * 试图做连接无边缘
	 * 类似optifine那种
	 * 但做不出来
	 * ***/
	@Override
	public void neighborChanged(BlockState pBlockState, Level pLevel, BlockPos pos, Block pBlock, BlockPos pos2, boolean don) {{}
		BlockPos upPos = pos.above();
	    BlockPos downPos = pos.below();
	    BlockPos leftPos = pos.west();
	    BlockPos rightPos = pos.east();
	    BlockPos frontPos = pos.north();
	    BlockPos behindPos = pos.south();
	    
	    // Check each direction and set the corresponding boolean
	    if (this.up != (pLevel.getBlockState(upPos).getBlock() == this)) {
	        //System.out.println("up has changed");
	        this.up = pLevel.getBlockState(upPos).getBlock() == this;
	        return;
	    } else if (this.down != (pLevel.getBlockState(downPos).getBlock() == this)) {
	        //System.out.println("down has changed");
	        this.down = pLevel.getBlockState(downPos).getBlock() == this;
	        return;
	    } else if (this.left != (pLevel.getBlockState(leftPos).getBlock() == this)) {
	        //System.out.println("left has changed");
	        this.left = pLevel.getBlockState(leftPos).getBlock() == this;
	        return;
	    } else if (this.right != (pLevel.getBlockState(rightPos).getBlock() == this)) {
	        //System.out.println("right has changed");
	        this.right = pLevel.getBlockState(rightPos).getBlock() == this;
	        return;
	    } else if (this.front != (pLevel.getBlockState(frontPos).getBlock() == this)) {
	        //System.out.println("front has changed");
	        this.front = pLevel.getBlockState(frontPos).getBlock() == this;
	        return;
	    } else if (this.behind != (pLevel.getBlockState(behindPos).getBlock() == this)) {
	        //System.out.println("behind has changed");
	        this.behind = pLevel.getBlockState(behindPos).getBlock() == this;
	        return;
	    }
	    
	    //changeTexture(pLevel,pBlockState,pos);
	}

	/*
	private void changeTexture(Level pLevel, BlockState pBlockState, BlockPos pos) {
		
	    BlockState newState;
	    
	    if (up) {
	    	newState = pBlockState.setValue(CUSTOM_TEXTURE, !pBlockState.getValue(CUSTOM_TEXTURE));
	    }
		System.out.println("glass neighbor change");
		pLevel.setBlockAndUpdate(pos, pBlockState);
	}
	 */
	
	

}
