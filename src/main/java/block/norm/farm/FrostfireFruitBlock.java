package block.norm.farm;

import java.util.Random;

import item.ItemRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class FrostfireFruitBlock extends CropBlock {
	   private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{
			   Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D), 
			   Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D), 
			   Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D), 
			   Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D), 
			   Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D), 
			   Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D), 
			   Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D), 
			   Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)};
	   
	   public static final String global_name = "frostfire_fruit_block";
	   

	   public FrostfireFruitBlock(BlockBehaviour.Properties p_51328_) {
	      super(p_51328_);
	   }

	   protected ItemLike getBaseSeedId() {
	      return ItemRegister.FROSTFIRE_FRUIT.get();
	   }

	   public VoxelShape getShape(BlockState p_51330_, BlockGetter p_51331_, BlockPos p_51332_, CollisionContext p_51333_) {
	      return SHAPE_BY_AGE[this.getAge(p_51330_)];
	   }
	   
		@Override
		public InteractionResult use(BlockState blockstate,Level level,BlockPos pos,Player player,InteractionHand interactionhand,BlockHitResult blockHitResult) {
	
			if(this.getAge(blockstate)==this.getMaxAge()) {
				Random r = new Random();
				r.setSeed(pos.getX()+pos.getY()+pos.getZ());
				int output = r.nextInt(1,4);
				ItemStack outputItemStack = new ItemStack(ItemRegister.FROSTFIRE_FRUIT.get(),output);
				Containers.dropContents(level, pos, NonNullList.of(ItemStack.EMPTY, outputItemStack));
				level.setBlockAndUpdate(pos, blockstate.setValue(AGE, 4));
			}
			return InteractionResult.PASS;
		}

}
