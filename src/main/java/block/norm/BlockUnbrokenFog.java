package block.norm;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * 一般生成在穹顶顶部
 * 无碰撞箱
 * 因为穹顶的外壳是不可破坏的，要防止玩家被困在穹顶内部
 * @author NANMEDA
 * */
public class BlockUnbrokenFog extends Block{
	public static String global_name = "unbroken_fog"; 
	
	public BlockUnbrokenFog(Properties p_49795_) {
		super(p_49795_);
	}
	
	@Override
	public VoxelShape getCollisionShape(BlockState p_154285_, BlockGetter p_154286_, BlockPos p_154287_, CollisionContext p_154288_) {
	     return Shapes.empty();
	}
		
    static {
        BlockJSON.GenModelsJSONBasic(global_name);
        BlockJSON.GenBlockStateJSONBasic(global_name);
        BlockJSON.GenItemJSONBasic(global_name);
        BlockJSON.GenLootTableJSONBasic(global_name);
    }
}
