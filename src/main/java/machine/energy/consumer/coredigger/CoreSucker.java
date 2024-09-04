package machine.energy.consumer.coredigger;

import block.norm.BlockRegister;
import machine.registry.MBlockRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import util.json.BlockJSON;

/**
 * @author NANMEDA
 * */
@SuppressWarnings("deprecation")
public class CoreSucker extends Block{
	public static final String global_name = "core_sucker"; 
	
	private static LazyLoadedValue<Block> CORE = new LazyLoadedValue<>(()->BlockRegister.unbrokenmagma_BLOCK.get());
	private static LazyLoadedValue<Block> PIPE = new LazyLoadedValue<>(()->MBlockRegister.COREPIPE_B.get());
	private static LazyLoadedValue<Block> C_PIPE = new LazyLoadedValue<>(()->MBlockRegister.CORECOLDPIPE_B.get());
	
	public CoreSucker(Properties properties) {
		super(properties
				.sound(SoundType.AMETHYST)
	            .strength(5f,5f)
	            .noOcclusion()
	            .mapColor(MapColor.COLOR_GRAY)
	            );
	}
	
	@Override
    public VoxelShape getShape(BlockState pBlockState, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
		return Shapes.block();
    }
	
	@Override
	public InteractionResult use(BlockState blockstate,Level level,BlockPos pos,Player player,InteractionHand interactionhand,BlockHitResult blockHitResult) {
		return super.use(blockstate,level,pos,player,interactionhand,blockHitResult);
	}
	
	/**
	 * @return 返回距离核心的距离++,如：核心就在下面，则返回1，找不到或中断，返回-1;
	 */
	protected static int getCoreDis(Level level,BlockPos suckerPos) {	
		BlockPos pos;
		int c =  1;
		for(int i=1;i<=64;i++) {
			pos = suckerPos.offset(0, -i, 0);
			if(level.getBlockState(pos).is(PIPE.get())) {
				c++;
				continue;
			}else if(level.getBlockState(pos).is(C_PIPE.get())) {
				c+=7;
				continue;
			}else if(level.getBlockState(pos).is(CORE.get())) {
				return c;
			}
			else {
				break;
			}
		}
		return -1;
	}
	
	 static {
	        BlockJSON.fastGen(global_name);
	 }
}

